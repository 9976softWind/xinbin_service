package com.wims.iot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wims.iot.model.entity.PlanDirectory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wims.iot.model.query.PlanDirectoryQuery;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 预案知识库目录 Mapper 接口
 * </p>
 *
 * @author tdw
 * @since 2025-04-14
 */
@Mapper
public interface PlanDirectoryMapper extends BaseMapper<PlanDirectory> {

    Page<PlanDirectory>  getPlanDirectoryPage(Page<PlanDirectory> page, PlanDirectoryQuery query);
}
