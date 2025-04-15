package com.wims.iot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wims.iot.model.entity.PlanFile;
import com.wims.iot.model.query.PlanDirectoryFileQuery;
import com.wims.iot.model.vo.PlanCategoryVo;

/**
 * <p>
 * 预案文件表 服务类
 * </p>
 *
 * @author tdw
 * @since 2025-04-14
 */
public interface IPlanFileService extends IService<PlanFile> {

    Boolean addPlanFile(PlanFile planFile);

    /**
     * 判断目录下是否有文件
     * @param directoryId 目录id
     * @return
     */
    Boolean isDirHasFiles(String directoryId);

    Boolean deletePlanFile(String fileId);

    Boolean setPlanFileBasicInfo(String id, PlanFile planFile);

    Boolean setPlanFileEntityInfo(String id, String entityInfo);

    PlanCategoryVo getPlanFileCategoryInfo(String id);

    IPage<PlanFile> getPlanDirectoryFileList(PlanDirectoryFileQuery query);
}
