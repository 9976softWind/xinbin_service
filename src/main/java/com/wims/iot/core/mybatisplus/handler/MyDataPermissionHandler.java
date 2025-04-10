package com.wims.iot.core.mybatisplus.handler;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.wims.iot.common.base.IBaseEnum;
import com.wims.iot.common.enums.DataScopeEnum;
import com.wims.iot.common.util.SecurityUtils;
import com.wims.iot.core.mybatisplus.annotation.DataPermission;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;

import java.lang.reflect.Method;

/**
 * 数据权限控制器
 *
 * @author zc
 * @since 2021-12-10 13:28
 */
@Slf4j
public class MyDataPermissionHandler implements DataPermissionHandler {

    @Override
    @SneakyThrows
    public Expression getSqlSegment(Expression where, String mappedStatementId) {

        Class<?> clazz = Class.forName(mappedStatementId.substring(0, mappedStatementId.lastIndexOf(StringPool.DOT)));
        String methodName = mappedStatementId.substring(mappedStatementId.lastIndexOf(StringPool.DOT) + 1);
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            DataPermission annotation = method.getAnnotation(DataPermission.class);
            // 如果没有注解或者是超级管理员，直接返回
            if (annotation == null || SecurityUtils.isRoot()) {
                return where;
            }
            if (method.getName().equals(methodName) || (method.getName() + "_COUNT").equals(methodName)) {
                return dataScopeFilter(annotation.deptAlias(), annotation.deptIdColumnName(), annotation.userAlias(), annotation.userIdColumnName(), where);
            }
        }
        return where;
    }

    /**
     * 构建过滤条件
     *
     * @param where 当前查询条件
     * @return 构建后查询条件
     */
    @SneakyThrows
    public static Expression dataScopeFilter(String deptAlias, String deptIdColumnName, String userAlias, String userIdColumnName, Expression where) {


        String deptColumnName = StrUtil.isNotBlank(deptAlias) ? (deptAlias + StringPool.DOT + deptIdColumnName) : deptIdColumnName;
        String userColumnName = StrUtil.isNotBlank(userAlias) ? (userAlias + StringPool.DOT + userIdColumnName) : userIdColumnName;

        // 获取当前用户的数据权限
        Integer dataScope = SecurityUtils.getDataScope();

        DataScopeEnum dataScopeEnum = IBaseEnum.getEnumByValue(dataScope, DataScopeEnum.class);

        Long deptId, userId;
        String appendSqlStr;
        switch (dataScopeEnum) {
            case ALL:
                return where;
            case DEPT:
                deptId = SecurityUtils.getDeptId();

                // 根据站点权限
                if ("hcs".equals(deptAlias)) {
                    appendSqlStr = deptColumnName + " in (SELECT st_id from hhu_cm_station where dept_id = " + deptId + ")";
                } else {
                    appendSqlStr = deptColumnName + StringPool.EQUALS + deptId;
                }

                break;
            case SELF:
                userId = SecurityUtils.getUserId();
                appendSqlStr = userColumnName + StringPool.EQUALS + userId;
                break;
            // 默认部门及子部门数据权限
            default:
                deptId = SecurityUtils.getDeptId();

                // 根据站点权限
                if ("hcs".equals(deptAlias)) {
                    appendSqlStr = deptColumnName + " in (SELECT st_id from hhu_cm_station " +
                            "where dept_id in (SELECT id FROM sys_dept WHERE id = " + deptId + " OR FIND_IN_SET( " + deptId + " , tree_path )))";
                } else {
                    appendSqlStr = deptColumnName + " IN ( SELECT id FROM sys_dept WHERE id = " + deptId + " OR FIND_IN_SET( " + deptId + " , tree_path ) )";
                }
                break;
        }

        if (StrUtil.isBlank(appendSqlStr)) {
            return where;
        }

        Expression appendExpression = CCJSqlParserUtil.parseCondExpression(appendSqlStr);

        if (where == null) {
            return appendExpression;
        }

        return new AndExpression(where, appendExpression);
    }


}

