package com.wims.iot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wims.iot.model.entity.PlanDirectory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wims.iot.model.query.PlanDirectoryQuery;

/**
 * <p>
 * 预案知识库目录 服务类
 * </p>
 *
 * @author tdw
 * @since 2025-04-14
 */
public interface IPlanDirectoryService extends IService<PlanDirectory> {

    IPage<PlanDirectory> getPlanDirectoryList(PlanDirectoryQuery query);

    Boolean addPlanDirectory(String name);

    Boolean setPlanDirectory(String directoryId, String name);

    Boolean deletePlanDirectory(String directoryId, Boolean isForce);

    PlanDirectory transferPlanDirectory(String directoryId, String newParentId);

}
