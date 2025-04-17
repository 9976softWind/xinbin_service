package com.wims.iot.mapper;

import com.wims.iot.model.entity.PlanEva;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wims.iot.model.vo.PlanEvaVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 预案评估表 Mapper 接口
 * </p>
 *
 * @author tdw
 * @since 2025-04-17
 */
@Mapper
public interface PlanEvaMapper extends BaseMapper<PlanEva> {

    PlanEvaVo getPlanFileEva(String executionId);
}
