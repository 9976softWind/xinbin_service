package com.wims.iot.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mysql.cj.util.StringUtils;
import com.wims.iot.common.exception.KGBusinessException;
import com.wims.iot.common.result.KgpResultCode;
import com.wims.iot.common.util.RandomStringGenerator;
import com.wims.iot.mapper.PlanFileMapper;
import com.wims.iot.model.entity.ColFile;
import com.wims.iot.model.entity.PlanField;
import com.wims.iot.model.entity.PlanFile;
import com.wims.iot.model.form.FileBindEntityForm;
import com.wims.iot.model.form.FileEntityForm;
import com.wims.iot.model.form.PlanFileAddForm;
import com.wims.iot.model.form.PlanFileSetForm;
import com.wims.iot.model.query.PlanDirectoryFileQuery;
import com.wims.iot.model.query.PlanFileQuery;
import com.wims.iot.model.vo.PlanCategoryVo;
import com.wims.iot.service.IColFileService;
import com.wims.iot.service.IPlanFieldService;
import com.wims.iot.service.IPlanFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    public PlanFile getPlanFileById(String id) {
        return this.baseMapper.selectOne(new QueryWrapper<PlanFile>().eq("id",id));
    }

    @Override
    public Boolean addPlanFile(PlanFile planFile) {
        if(isFileHasAdded(planFile.getFileId())){
            throw new KGBusinessException(KgpResultCode.FILE_ADD_CONFLICT);
        }
        ColFile colFileInfo = colFileService.getColFileById(planFile.getFileId());
        planFile.setId("file_"+ RandomStringGenerator.generate(6));
        planFile.setName(colFileInfo.getFilename());
        planFile.setFileId(colFileInfo.getFileId());
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
        planFile.setUpdatedAt(new Date());
        return this.baseMapper.update(planFile,new QueryWrapper<PlanFile>().eq("id",id)) == 1;
    }

    @Override
    public Boolean setFileBindEntityInfo(String id, FileBindEntityForm form) {
        PlanFile planFile = this.baseMapper.selectOne(new QueryWrapper<PlanFile>().eq("id", id));
        try {
            PlanFile updateFile = new PlanFile();
            ArrayNode entityToBindIds = mapper.createArrayNode();
            List<String> categoryIds = form.getCategoryIds();
            categoryIds.forEach(entityToBindIds::add);

            Iterator<JsonNode> iterator = entityToBindIds.iterator();
            ObjectNode entityProperty = mapper.createObjectNode();
            while (iterator.hasNext()) {
                JsonNode element = iterator.next();
                String categoryId = element.asText();
                List<PlanField> planFields = planFieldService.getPlanFields(categoryId);
                List<String> collectFiledName = planFields.stream().map(PlanField::getName).collect(Collectors.toList());
                ObjectNode property = mapper.createObjectNode();
                collectFiledName.forEach(f->{
                    property.set(f,null);
                });
                entityProperty.set(categoryId,property);
            }
            if(StringUtils.isNullOrEmpty(planFile.getEntityCategoryId())){
                //初次绑定主体
                updateFile.setEntityCategoryId(entityToBindIds.toString());
                updateFile.setEntityCategoryProperty(entityProperty.toString());
            }else{
                ArrayNode entityCatIds = (ArrayNode) mapper.readTree(planFile.getEntityCategoryId());
                Set<String> catIdSet = StreamSupport.stream(entityCatIds.spliterator(), false)
                        .map(JsonNode::asText)
                        .collect(Collectors.toSet());
                boolean hasCommon = StreamSupport.stream(entityToBindIds.spliterator(), false)
                        .map(JsonNode::asText)
                        .anyMatch(catIdSet::contains);
                if(hasCommon){
                    throw new KGBusinessException(KgpResultCode.FILE_CATEGORY_BIND_CONFLICT);
                }
                entityCatIds.addAll(entityToBindIds);
                updateFile.setEntityCategoryId(entityCatIds.toString());
                ObjectNode entityCatProperty =  (ObjectNode) mapper.readTree(planFile.getEntityCategoryProperty());
                entityCatProperty.setAll(entityProperty);
                updateFile.setEntityCategoryProperty(entityCatProperty.toString());
            }
            updateFile.setUpdatedAt(new Date());
            return this.baseMapper.update(updateFile,new QueryWrapper<PlanFile>().eq("id",id)) == 1;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw new KGBusinessException(KgpResultCode.SYSTEM_EXECUTION_ERROR);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new KGBusinessException(KgpResultCode.SYSTEM_EXECUTION_ERROR);
        }
    }

    @Override
    @Transactional
    public Boolean addPlanFileIntoDic(PlanFileAddForm planFileAddForm) {
        List<String> fileIds = planFileAddForm.getFileId();
        PlanFile planFile = new PlanFile();
        planFile.setDirectoryId(planFileAddForm.getDirectoryId());
        planFile.setDescription(planFileAddForm.getDescription());
        planFile.setPreplanPriority(planFileAddForm.getPreplanPriority());
        planFile.setDisasterType(planFileAddForm.getDisasterType());
        planFile.setApplicableArea(planFileAddForm.getApplicableArea());
        ArrayNode categoryIds = mapper.createArrayNode();
        ObjectNode categoryProperty = mapper.createObjectNode();
        List<PlanFileAddForm.AttributeDTO> attributes = planFileAddForm.getAttributes();
        attributes.forEach(attributeDTO -> {
            String categoryId = attributeDTO.getCategoryId();
            Map<String, String> fields = attributeDTO.getFields();
            categoryIds.add(categoryId);
            ObjectNode property = mapper.createObjectNode();
            for (Map.Entry<String, String> entry : fields.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                property.put(key,value);
            }
            categoryProperty.set(categoryId,property);

        });
        planFile.setEntityCategoryId(categoryIds.toString());
        planFile.setEntityCategoryProperty(categoryProperty.toString());
        planFile.setCreatedAt(new Date());

        for(String fileId:fileIds){
            ColFile colFileInfo = colFileService.getColFileById(fileId);
            if(this.baseMapper.exists(new QueryWrapper<PlanFile>().eq("directory_id",planFileAddForm.getDirectoryId()).eq("file_id",fileId))){
                throw new KGBusinessException(KgpResultCode.FILE_ADD_CONFLICT,KgpResultCode.FILE_ADD_CONFLICT.getMsg() + "：" + colFileInfo.getFilename());
            }
            planFile.setId("file_"+ RandomStringGenerator.generate(6));
            planFile.setName(colFileInfo.getFilename());
            planFile.setFileId(colFileInfo.getFileId());
            if(this.baseMapper.insert(planFile) != 1){
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean setPlanFileIntoDic(String id, PlanFileSetForm form) {
        PlanFile planFile = new PlanFile();
        planFile.setDirectoryId(form.getDirectoryId());
        planFile.setDescription(form.getDescription());
        planFile.setPreplanPriority(form.getPreplanPriority());
        planFile.setDisasterType(form.getDisasterType());
        planFile.setApplicableArea(form.getApplicableArea());
        ArrayNode categoryIds = mapper.createArrayNode();
        ObjectNode categoryProperty = mapper.createObjectNode();
        List<PlanFileSetForm.AttributeDTO> attributes = form.getAttributes();
        attributes.forEach(attributeDTO -> {
            String categoryId = attributeDTO.getCategoryId();
            Map<String, String> fields = attributeDTO.getFields();
            categoryIds.add(categoryId);
            ObjectNode property = mapper.createObjectNode();
            for (Map.Entry<String, String> entry : fields.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                property.put(key,value);
            }
            categoryProperty.set(categoryId,property);

        });
        planFile.setEntityCategoryId(categoryIds.toString());
        planFile.setEntityCategoryProperty(categoryProperty.toString());
        planFile.setUpdatedAt(new Date());
        return this.baseMapper.update(planFile,new QueryWrapper<PlanFile>().eq("id",id)) == 1;
    }

    @Override
    public Boolean setPlanFileEntityInfo(String id, FileEntityForm entityInfo) {
        PlanFile planFile = this.baseMapper.selectOne(new QueryWrapper<PlanFile>().eq("id", id));
        try {
            PlanFile updateFile = new PlanFile();
            ObjectNode entityCats = (ObjectNode) mapper.readTree(planFile.getEntityCategoryProperty());
            if(!entityCats.has(entityInfo.getCategoryId())){
                throw new KGBusinessException(KgpResultCode.FILE_CATEGORY_NOT_FOUND);
            }else{
                ObjectNode updateField = (ObjectNode) mapper.readTree(entityInfo.getFields());
                entityCats.set(entityInfo.getCategoryId(),updateField);
                updateFile.setEntityCategoryProperty(entityCats.toString());
                updateFile.setUpdatedAt(new Date());
                return this.baseMapper.update(updateFile,new QueryWrapper<PlanFile>().eq("id",id)) == 1;
            }
        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw new KGBusinessException(KgpResultCode.SYSTEM_EXECUTION_ERROR);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new KGBusinessException(KgpResultCode.SYSTEM_EXECUTION_ERROR);
        }
    }

    @Override
    public List<PlanCategoryVo> getPlanFileCategoryInfo(String id) {
        PlanFile planFile = this.baseMapper.selectOne(new QueryWrapper<PlanFile>().eq("id", id));
        List<PlanCategoryVo> result = new ArrayList();
        if(ObjectUtil.isNull(planFile) || ObjectUtil.isNull(planFile.getEntityCategoryId())){
            return result;
        }else{
            try {
                if(StringUtils.isNullOrEmpty(planFile.getEntityCategoryId())){
                    return result;
                }
                ArrayNode EntityCategory = (ArrayNode) mapper.readTree(planFile.getEntityCategoryId());
                Iterator<JsonNode> iterator = EntityCategory.iterator();
                while (iterator.hasNext()){
                    PlanCategoryVo planCategoryVo = this.baseMapper.getPlanFileCategoryInfo(iterator.next().asText());
                    result.add(planCategoryVo);
                }
            } catch (JsonMappingException e) {
                e.printStackTrace();
                throw new KGBusinessException(KgpResultCode.SYSTEM_EXECUTION_ERROR);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new KGBusinessException(KgpResultCode.SYSTEM_EXECUTION_ERROR);
            }
        }
        return result;
    }

    @Override
    public IPage<PlanFile> getPlanDirectoryFileList(PlanDirectoryFileQuery query) {
        Page<PlanFile> page = new Page<>(query.getPage(), query.getPageSize());
        this.baseMapper.getPlanDirectoryFileList(page,query);
        return page;
    }

    @Override
    public IPage<PlanFile> getPlanFileByIds(PlanFileQuery query) {
        Page<PlanFile> page = new Page<>(query.getPage(), query.getPageSize());
        this.baseMapper.getPlanFileByIds(page,query);
        return page;
    }


    @Override
    public Boolean transferPlanDirectory(String directoryId, String newDirectoryId) {
        Long total = this.baseMapper.selectCount(new QueryWrapper<PlanFile>().eq("directory_id", directoryId));
        if(total == 0){
            return true;
        }
        PlanFile planFile = new PlanFile();
        planFile.setDirectoryId(newDirectoryId);
        planFile.setUpdatedAt(new Date());
        int updateCount = this.baseMapper.update(planFile, new QueryWrapper<PlanFile>().eq("directory_id", directoryId));
        return total.equals((long) updateCount);
    }

    @Override
    public Boolean sendPlanFile(String id, String recipients, String channel, String message) {
        //TODO:预案发送接口
        return true;
    }

    @Override
    public JsonNode previewPlanFile(String id) {
        ObjectNode result = mapper.createObjectNode();
        PlanFile planFile = this.baseMapper.selectOne(new QueryWrapper<PlanFile>().eq("id", id));
        ColFile colFile = colFileService.getColFileById(planFile.getFileId());
        String fileUrl = colFile.getFilepath();
        result.put("previewUrl",fileUrl);
        result.put("expiresIn",-1);
        return result;
    }


    @Override
    public Boolean isDirHasFiles(String directoryId) {
        return this.baseMapper.exists(new QueryWrapper<PlanFile>().eq("directory_id",directoryId));
    }

    @Override
    public Boolean hasRelevanceFiles(String categoryId) {
        return this.baseMapper.exists(new QueryWrapper<PlanFile>().eq("entity_category_id",categoryId));
    }

    public Boolean isFileHasAdded(String fileId ){
        return this.baseMapper.exists(new QueryWrapper<PlanFile>().eq("file_id",fileId));
    }
}
