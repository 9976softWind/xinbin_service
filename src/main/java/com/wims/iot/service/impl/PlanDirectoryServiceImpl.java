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
import java.util.Optional;

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
        return this.baseMapper.insert(planDirectory) == 1 ;
    }

    @Override
    public Boolean setPlanDirectory(String directoryId, String name) {
        if(hasReName(name)){
            throw new KGBusinessException(KgpResultCode.DIRECTORY_NAME_CONFLICT);
        }
        PlanDirectory planDirectory = new PlanDirectory();
        planDirectory.setName(name);
        planDirectory.setUpdatedAt(new Date());
        return this.baseMapper.update(planDirectory, new QueryWrapper<PlanDirectory>().eq("id", directoryId)) == 1 ;
    }

    @Override
    public Boolean deletePlanDirectory(String directoryId, Boolean isForce) {
        Boolean dirHasFiles = planFileService.isDirHasFiles(directoryId);
        boolean forceDelete = Optional.ofNullable(isForce).orElse(false);
        // 非强制删除且目录下有文件时，拒绝删除
        if (!forceDelete && dirHasFiles) {
            throw new KGBusinessException(KgpResultCode.DIRECTORY_NOT_EMPTY);
        }
        // 执行删除（只有两种允许删除的情况：强制删除，或非强制但目录下无文件）
        return this.baseMapper.delete(new QueryWrapper<PlanDirectory>().eq("id", directoryId)) == 1;
    }

    @Override
    public Boolean transferPlanDirectory(String directoryId, String newDirectoryId) {
        Boolean isSuccess = planFileService.transferPlanDirectory(directoryId, newDirectoryId);
        if(isSuccess){
            return this.baseMapper.delete(new QueryWrapper<PlanDirectory>().eq("id",directoryId)) == 1;
        }else{
            throw new KGBusinessException(KgpResultCode.SYSTEM_EXECUTION_ERROR);
        }
    }

    @Override
    public IPage<PlanFile> getPlanDirectoryFileList(PlanDirectoryFileQuery query) {
        return planFileService.getPlanDirectoryFileList(query);
    }

    public Boolean hasReName(String directoryName){
        return this.baseMapper.exists(new QueryWrapper<PlanDirectory>().eq("name",directoryName));
    }

}
