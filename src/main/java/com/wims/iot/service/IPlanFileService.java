package com.wims.iot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wims.iot.model.entity.PlanFile;

/**
 * <p>
 * 预案文件表 服务类
 * </p>
 *
 * @author tdw
 * @since 2025-04-14
 */
public interface IPlanFileService extends IService<PlanFile> {

    PlanFile addPlanFile(PlanFile planFile);

    /**
     * 判断目录下是否有文件
     * @param directoryId 目录id
     * @return
     */
    Boolean isDirHasFiles(String directoryId);
}
