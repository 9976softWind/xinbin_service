package com.wims.iot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wims.iot.model.entity.PlanExe;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wims.iot.model.query.PlanExeQuery;

/**
 * <p>
 * 预案执行表 服务类
 * </p>
 *
 * @author tdw
 * @since 2025-04-16
 */
public interface IPlanExeService extends IService<PlanExe> {

    IPage<PlanExe> getPlanExeList(PlanExeQuery query);

    Boolean updateStatus(String executionId, String exeStatus);

    Boolean updateFeedBackTime(String executionId);
}
