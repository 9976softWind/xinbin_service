package com.wims.iot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.JsonNode;
import com.wims.iot.model.entity.PlanFile;
import com.wims.iot.model.form.FileBindEntityForm;
import com.wims.iot.model.form.FileEntityForm;
import com.wims.iot.model.query.PlanDirectoryFileQuery;
import com.wims.iot.model.vo.PlanCategoryVo;

import java.util.List;

/**
 * <p>
 * 预案文件表 服务类
 * </p>
 *
 * @author tdw
 * @since 2025-04-14
 */
public interface IPlanFileService extends IService<PlanFile> {

    PlanFile getPlanFileById(String id);

    Boolean addPlanFile(PlanFile planFile);

    /**
     * 判断目录下是否有文件
     * @param directoryId 目录id
     * @return
     */
    Boolean isDirHasFiles(String directoryId);

    /**
     * 判断预案类别是否有关联的文件
     * @param categoryId 类别id
     * @return
     */
    Boolean hasRelevanceFiles(String categoryId);

    Boolean deletePlanFile(String fileId);

    Boolean setPlanFileBasicInfo(String id, PlanFile planFile);

    Boolean setPlanFileEntityInfo(String id, FileEntityForm entityInfo);

    List<PlanCategoryVo> getPlanFileCategoryInfo(String id);

    IPage<PlanFile> getPlanDirectoryFileList(PlanDirectoryFileQuery query);

    Boolean transferPlanDirectory(String directoryId, String newDirectoryId);

    Boolean sendPlanFile(String id, String recipients, String channel, String message);

    JsonNode previewPlanFile(String id);

    Boolean setFileBindEntityInfo(String id, FileBindEntityForm form);
}
