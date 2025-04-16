package com.wims.iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wims.iot.mapper.PlanExeMapper;
import com.wims.iot.model.entity.PlanExe;
import com.wims.iot.model.query.PlanExeQuery;
import com.wims.iot.service.IPlanExeService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 预案执行表 服务实现类
 * </p>
 *
 * @author tdw
 * @since 2025-04-16
 */
@Service
public class PlanExeServiceImpl extends ServiceImpl<PlanExeMapper, PlanExe> implements IPlanExeService {

    @Override
    public IPage<PlanExe> getPlanExeList(PlanExeQuery query) {
        Page<PlanExe> page = new Page<>(query.getPage(), query.getPageSize());
        this.baseMapper.getPlanExeList(page,query);
        return page;
    }

    @Override
    public Boolean updateStatus(String executionId, String exeStatus) {
        PlanExe planExe = new PlanExe();
        planExe.setStatus(exeStatus);
        planExe.setLastFeedBackTime(new Date());
        return this.baseMapper.update(planExe,new QueryWrapper<PlanExe>().eq("id",executionId)) == 1;
    }

    @Override
    public Boolean updateFeedBackTime(String executionId) {
        PlanExe planExe = new PlanExe();
        planExe.setLastFeedBackTime(new Date());
        return this.baseMapper.update(planExe,new QueryWrapper<PlanExe>().eq("id",executionId)) == 1;
    }

}
