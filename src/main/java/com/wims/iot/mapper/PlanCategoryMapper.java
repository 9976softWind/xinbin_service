package com.wims.iot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wims.iot.model.entity.PlanCategory;
import com.wims.iot.model.query.PlanCategoryQuery;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 预案类别表 Mapper 接口
 * </p>
 *
 * @author tdw
 * @since 2025-04-15
 */
@Mapper
public interface PlanCategoryMapper extends BaseMapper<PlanCategory> {

    Page<PlanCategory>  getPlanCategoryList(Page<PlanCategory> page, PlanCategoryQuery query);
}
