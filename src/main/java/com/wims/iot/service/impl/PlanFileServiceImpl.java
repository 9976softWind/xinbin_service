package com.wims.iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mysql.cj.util.StringUtils;
import com.wims.iot.common.exception.KGBusinessException;
import com.wims.iot.common.result.KgpResultCode;
import com.wims.iot.common.util.RandomStringGenerator;
import com.wims.iot.mapper.PlanFileMapper;
import com.wims.iot.model.entity.ColFile;
import com.wims.iot.model.entity.PlanField;
import com.wims.iot.model.entity.PlanFile;
import com.wims.iot.service.IColFileService;
import com.wims.iot.service.IPlanFieldService;
import com.wims.iot.service.IPlanFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 预案文件表 服务实现类
 * </p>
 *
 * @author tdw
 * @since 2025-04-14
 */
@Service
@RequiredArgsConstructor
public class PlanFileServiceImpl extends ServiceImpl<PlanFileMapper, PlanFile> implements IPlanFileService {

    @Autowired
    IColFileService colFileService;

    @Autowired
    IPlanFieldService planFieldService;

    private final ObjectMapper mapper;

    @Override
    public Boolean addPlanFile(PlanFile planFile) {
        if(isFileHasAdded(planFile.getFileId())){
            throw new KGBusinessException(KgpResultCode.FILE_ADD_CONFLICT);
        }
        ColFile colFileInfo = colFileService.getColFileById(planFile.getFileId());
        planFile.setId("file_"+ RandomStringGenerator.generate(6));
        planFile.setName(colFileInfo.getFilename());
        planFile.setFileId(colFileInfo.getFileId());
        if(!StringUtils.isNullOrEmpty(planFile.getEntityCategoryId())){
            List<PlanField> planFields = planFieldService.getPlanFields(planFile.getEntityCategoryId());
            List<String> collectFiledName = planFields.stream().map(PlanField::getName).collect(Collectors.toList());
            ObjectNode entityProperty = mapper.createObjectNode();
            collectFiledName.forEach(f->{
                entityProperty.set(f,null);
            });
            planFile.setEntityCategoryProperty(entityProperty.toString());
        }
        planFile.setCreatedAt(new Date());
        if(this.baseMapper.insert(planFile) == 1){
            return true;
        }else{
            throw new KGBusinessException(KgpResultCode.SYSTEM_EXECUTION_ERROR);
        }
    }

    @Override
    public Boolean deletePlanFile(String fileId) {
        return this.baseMapper.delete(new QueryWrapper<PlanFile>().eq("id",fileId)) == 1;
    }

    @Override
    public Boolean setPlanFileBasicInfo(String id, PlanFile planFile) {
        if(!StringUtils.isNullOrEmpty(planFile.getEntityCategoryId())){
            List<PlanField> planFields = planFieldService.getPlanFields(planFile.getEntityCategoryId());
            List<String> collectFiledName = planFields.stream().map(PlanField::getName).collect(Collectors.toList());
            ObjectNode entityProperty = mapper.createObjectNode();
            collectFiledName.forEach(f->{
                entityProperty.set(f,null);
            });
            planFile.setEntityCategoryProperty(entityProperty.toString());
        }
        planFile.setUpdatedAt(new Date());
        return this.baseMapper.update(planFile,new QueryWrapper<PlanFile>().eq("id",id)) == 1;
    }

    @Override
    public Boolean setPlanFileEntityInfo(String id, String entityInfo) {
        PlanFile planFile = new PlanFile();
        planFile.setEntityCategoryProperty(entityInfo);
        planFile.setUpdatedAt(new Date());
        return this.baseMapper.update(planFile,new QueryWrapper<PlanFile>().eq("id",id)) == 1;
    }

    @Override
    public Boolean isDirHasFiles(String directoryId) {
        return this.baseMapper.exists(new QueryWrapper<PlanFile>().eq("directory_id",directoryId));
    }

    public Boolean isFileHasAdded(String fileId ){
        return this.baseMapper.exists(new QueryWrapper<PlanFile>().eq("file_id",fileId));
    }
}
