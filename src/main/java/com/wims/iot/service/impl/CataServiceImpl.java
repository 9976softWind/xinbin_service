package com.wims.iot.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wims.iot.common.constant.SystemConstants;
import com.wims.iot.common.enums.StatusEnum;
import com.wims.iot.common.model.Option;
import com.wims.iot.converter.CataConverter;
import com.wims.iot.mapper.CataMapper;
import com.wims.iot.model.entity.Cata;
import com.wims.iot.model.form.CataForm;
import com.wims.iot.model.query.CataQuery;
import com.wims.iot.model.vo.CataVO;
import com.wims.iot.service.ICataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 目录表 服务实现类
 * </p>
 *
 * @author tdw
 * @since 2025-04-10
 */
@Service
@RequiredArgsConstructor
public class CataServiceImpl extends ServiceImpl<CataMapper, Cata> implements ICataService {


    private final CataConverter cataConverter;

    /**
     * 目录列表
     */
    @Override
    public List<CataVO> listCatas(CataQuery queryParams) {
        // 查询参数
        String keywords = queryParams.getKeywords();
        Integer status = queryParams.getStatus();

        // 查询数据
        List<Cata> cataList = this.list(
                new LambdaQueryWrapper<Cata>()
                        .like(StrUtil.isNotBlank(keywords), Cata::getName, keywords)
                        .eq(status != null, Cata::getStatus, status)
                        .orderByAsc(Cata::getSort)
        );

        Set<Long> cataIds = cataList.stream()
                .map(Cata::getId)
                .collect(Collectors.toSet());

        Set<Long> parentIds = cataList.stream()
                .map(Cata::getParentId)
                .collect(Collectors.toSet());

        List<Long> rootIds = CollectionUtil.subtractToList(parentIds, cataIds);
        List<CataVO> list = new ArrayList<>();
        for (Long rootId : rootIds) {
            list.addAll(recurCataList(rootId, cataList));
        }
        return list;
    }

    /**
     * 递归生成目录树形列表
     *
     * @param parentId
     * @param cataList
     * @return
     */
    public List<CataVO> recurCataList(Long parentId, List<Cata> cataList) {
        return cataList.stream()
                .filter(cata -> cata.getParentId().equals(parentId))
                .map(cata -> {
                    CataVO cataVO = cataConverter.entity2Vo(cata);
                    List<CataVO> children = recurCataList(cata.getId(), cataList);
                    cataVO.setChildren(children);
                    return cataVO;
                }).collect(Collectors.toList());
    }

    /**
     * 目录下拉选项
     *
     * @return 目录下拉List集合
     */
    @Override
    public List<Option> listCataOptions() {

        List<Cata> cataList = this.list(new LambdaQueryWrapper<Cata>()
                .eq(Cata::getStatus, StatusEnum.ENABLE.getValue())
                .select(Cata::getId, Cata::getParentId, Cata::getName)
                .orderByAsc(Cata::getSort)
        );

        Set<Long> parentIds = cataList.stream()
                .map(Cata::getParentId)
                .collect(Collectors.toSet());

        Set<Long> cataIds = cataList.stream()
                .map(Cata::getId)
                .collect(Collectors.toSet());

        List<Long> rootIds = CollectionUtil.subtractToList(parentIds, cataIds);

        List<Option> list = new ArrayList<>();
        for (Long rootId : rootIds) {
            list.addAll(recurCataTreeOptions(rootId, cataList));
        }
        return list;
    }

    @Override
    public Long saveCata(CataForm formData) {
        Cata entity = cataConverter.form2Entity(formData);
        // 部门路径
        String treePath = generateCataTreePath(formData.getParentId());
        entity.setTreePath(treePath);
        // 保存部门并返回部门ID
        this.save(entity);
        return entity.getId();
    }

    @Override
    public Long updateCata(Long cataId, CataForm formData) {
        // form->entity
        Cata entity = cataConverter.form2Entity(formData);
        entity.setId(cataId);
        // 部门路径
        String treePath = generateCataTreePath(formData.getParentId());
        entity.setTreePath(treePath);
        // 保存部门并返回部门ID
        this.updateById(entity);
        return entity.getId();
    }

    /**
     * 递归生成目录表格层级列表
     *
     * @param parentId
     * @param cataList
     * @return
     */
    public static List<Option> recurCataTreeOptions(long parentId, List<Cata> cataList) {
        List<Option> list = CollectionUtil.emptyIfNull(cataList).stream()
                .filter(cata -> cata.getParentId().equals(parentId))
                .map(cata -> {
                    Option option = new Option(cata.getId(), cata.getName());
                    List<Option> children = recurCataTreeOptions(cata.getId(), cataList);
                    if (CollectionUtil.isNotEmpty(children)) {
                        option.setChildren(children);
                    }
                    return option;
                })
                .collect(Collectors.toList());
        return list;
    }


    /**
     * 删除目录
     *
     * @param ids 目录ID，多个以英文逗号,拼接字符串
     * @return
     */
    @Override
    public boolean deleteByIds(String ids) {
        // 删除部门及子部门
        if (StrUtil.isNotBlank(ids)) {
            String[] menuIds = ids.split(",");
            for (String cataId : menuIds) {
                this.remove(new LambdaQueryWrapper<Cata>()
                        .eq(Cata::getId, cataId)
                        .or()
                        .apply("CONCAT (',',tree_path,',') LIKE CONCAT('%,',{0},',%')", cataId));
            }
        }
        return true;
    }

    /**
     * 获取部门详情
     *
     * @param cataId
     * @return
     */
    @Override
    public CataForm getCataForm(Long cataId) {

        Cata entity = this.getOne(new LambdaQueryWrapper<Cata>()
                .eq(Cata::getId, cataId)
                .select(
                        Cata::getId,
                        Cata::getName,
                        Cata::getParentId,
                        Cata::getStatus,
                        Cata::getSort
                ));

        return cataConverter.entity2Form(entity);
    }


    /**
     * 目录路径生成
     *
     * @param parentId 父ID
     * @return 父节点路径以英文逗号(, )分割，eg: 1,2,3
     */
    private String generateCataTreePath(Long parentId) {
        String treePath = null;
        if (SystemConstants.ROOT_NODE_ID.equals(parentId)) {
            treePath = String.valueOf(parentId);
        } else {
            Cata parent = this.getById(parentId);
            if (parent != null) {
                treePath = parent.getTreePath() + "," + parent.getId();
            }
        }
        return treePath;
    }
}
