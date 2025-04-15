package com.wims.iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.wims.iot.common.util.RandomStringGenerator;
import com.wims.iot.mapper.PlanDirectoryMapper;
import com.wims.iot.model.entity.PlanDirectory;
import com.wims.iot.model.query.PlanDirectoryQuery;
import com.wims.iot.service.IPlanDirectoryService;
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


    @Override
    public IPage<PlanDirectory> getPlanDirectoryPage(PlanDirectoryQuery query) {
        Page<PlanDirectory> page = new Page<>(query.getPage(), query.getPageSize());
        this.baseMapper.getPlanDirectoryPage(page,query);
        return page;
    }

    @Override
    public PlanDirectory addPlanDirectory(String name, String parentId) {
        PlanDirectory planDirectory = new PlanDirectory();
        planDirectory.setId("dir_" + RandomStringGenerator.generate(6));
        planDirectory.setName(name);
        planDirectory.setCreatedAt(new Date());
        if(!StringUtils.isNullOrEmpty(parentId)){
            planDirectory.setParentId(parentId);
            planDirectory.setTreePath(parentId+","+ planDirectory.getId());
        }else{
            planDirectory.setParentId(planDirectory.getId());
            planDirectory.setTreePath(planDirectory.getId());
        }
        int insert = this.baseMapper.insert(planDirectory);
        return insert == 1 ? planDirectory : null;
    }

    @Override
    public PlanDirectory setPlanDirectory(String directoryId, String name) {
        PlanDirectory planDirectory = new PlanDirectory();
        planDirectory.setName(name);
        planDirectory.setUpdatedAt(new Date());
        int update = this.baseMapper.update(planDirectory, new QueryWrapper<PlanDirectory>().eq("id", directoryId));
        return update == 1 ? this.baseMapper.selectById(directoryId) : null;
    }

    @Override
    public Boolean deletePlanDirectory(String directoryId, Boolean isForce) {
        // 相关的文件做外键约束，级联删除，就不在编码中体现了
        if(this.dirIsEmpty(directoryId)) {
            return this.baseMapper.delete(new QueryWrapper<PlanDirectory>().eq("id", directoryId)) == 1;
        }else{
            if(!isForce){
                return false;
            }else{
                return this.baseMapper.delete(new QueryWrapper<PlanDirectory>().eq("parent_id", directoryId)) != 0;
            }
        }
    }

    @Override
    public PlanDirectory transferPlanDirectory(String directoryId, String newParentId) {
        PlanDirectory directory = this.baseMapper.selectOne(new QueryWrapper<PlanDirectory>().eq("id", directoryId));
        directory.setParentId(newParentId);
        String treePath = directory.getTreePath();
        treePath.replace(directory.getParentId(),newParentId);
        directory.setTreePath(treePath);
        directory.setUpdatedAt(new Date());
        boolean transfer = this.update(directory, new QueryWrapper<PlanDirectory>().eq("id", directoryId));
        return transfer ? directory : null;
    }

    public Boolean dirIsEmpty(String directoryId){
        return this.baseMapper.selectCount(new QueryWrapper<PlanDirectory>().eq("parent_id",directoryId).ne("id",directoryId)) == 0;
    }

}
