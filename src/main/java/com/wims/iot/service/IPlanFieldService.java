package com.wims.iot.service;

import com.wims.iot.model.entity.PlanField;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wims.iot.model.vo.PlanFieldVo;

import java.util.List;

/**
 * <p>
 * 预案类别字段表 服务类
 * </p>
 *
 * @author tdw
 * @since 2025-04-15
 */
public interface IPlanFieldService extends IService<PlanField> {

    PlanFieldVo addPlanField(String categoryId, PlanField planField);

    List<PlanField> getPlanFields(String categoryId);
}
