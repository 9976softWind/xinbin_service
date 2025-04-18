package com.wims.iot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wims.iot.model.entity.PlanExe;
import com.wims.iot.model.query.PlanFileExeFeedBackQuery;
import com.wims.iot.model.vo.PlanFileExeFeedEvaVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 预案执行表 Mapper 接口
 * </p>
 *
 * @author tdw
 * @since 2025-04-16
 */
@Mapper
public interface PlanExeMapper extends BaseMapper<PlanExe> {

    Page<PlanFileExeFeedEvaVo> getPlanFileExeFeedBackList(Page<PlanFileExeFeedEvaVo> page, PlanFileExeFeedBackQuery query);

}
