package com.wims.iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wims.iot.common.exception.KGBusinessException;
import com.wims.iot.common.result.KgpResultCode;
import com.wims.iot.common.util.RandomStringGenerator;
import com.wims.iot.mapper.PlanDirectoryMapper;
import com.wims.iot.model.entity.PlanDirectory;
import com.wims.iot.model.entity.PlanFile;
import com.wims.iot.model.query.PlanDirectoryFileQuery;
import com.wims.iot.model.query.PlanDirectoryQuery;
import com.wims.iot.service.IPlanDirectoryService;
import com.wims.iot.service.IPlanFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 预案知识库目录 服务实现类
 * </p>
 *
 * @author tdw
 * @since 2025-04-14
 */
@Service
public class PlanDirectoryServiceImpl extends ServiceImpl<PlanDirectoryMapper, PlanDirectory> implements IPlanDirectoryService {

    @Autowired
    IPlanFileService planFileService;

    @Override
    public IPage<PlanDirectory> getPlanDirectoryList(PlanDirectoryQuery query) {
        Page<PlanDirectory> page = new Page<>(query.getPage(), query.getPageSize());
        this.baseMapper.getPlanDirectoryPage(page,query);
        return page;
    }

    @Override
    public Boolean addPlanDirectory(String name) {
        if(hasReName(name)){
            throw new KGBusinessException(KgpResultCode.DIRECTORY_NAME_CONFLICT);
        }
        PlanDirectory planDirectory = new PlanDirectory();
        planDirectory.setId("dir_" + RandomStringGenerator.generate(6));
        planDirectory.setName(name);
        planDirectory.setCreatedAt(new Date());
        return this.baseMapper.insert(planDirectory) == 1 ? true : false;
    }

    @Override
    public Boolean setPlanDirectory(String directoryId, String name) {
        if(hasReName(name)){
            throw new KGBusinessException(KgpResultCode.DIRECTORY_NAME_CONFLICT);
        }
        PlanDirectory planDirectory = new PlanDirectory();
        planDirectory.setName(name);
        planDirectory.setUpdatedAt(new Date());
        return this.baseMapper.update(planDirectory, new QueryWrapper<PlanDirectory>().eq("id", directoryId)) == 1 ? true : false;
    }

    @Override
    public Boolean deletePlanDirectory(String directoryId, Boolean isForce) {
        //TODO:级联删除相关文件，文件关联的主体
        Boolean dirHasFiles = planFileService.isDirHasFiles(directoryId);
        if(!dirHasFiles) {
            return this.baseMapper.delete(new QueryWrapper<PlanDirectory>().eq("id", directoryId)) == 1;
        }else{
            if(!isForce){
                throw new KGBusinessException(KgpResultCode.DIRECTORY_NOT_EMPTY);
            }else{
                return this.baseMapper.delete(new QueryWrapper<PlanDirectory>().eq("id", directoryId)) != 0;
            }
        }
    }

    @Override
    public PlanDirectory transferPlanDirectory(String directoryId, String newParentId) {
        PlanDirectory directory = this.baseMapper.selectOne(new QueryWrapper<PlanDirectory>().eq("id", directoryId));
        directory.setUpdatedAt(new Date());
        boolean transfer = this.update(directory, new QueryWrapper<PlanDirectory>().eq("id", directoryId));
        return transfer ? directory : null;
    }

    @Override
    public IPage<PlanFile> getPlanDirectoryFileList(PlanDirectoryFileQuery query) {
        return planFileService.getPlanDirectoryFileList(query);
    }

    public Boolean hasReName(String directoryName){
        return this.baseMapper.exists(new QueryWrapper<PlanDirectory>().eq("name",directoryName));
    }

}
