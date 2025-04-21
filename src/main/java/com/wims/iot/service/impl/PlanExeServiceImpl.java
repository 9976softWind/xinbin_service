package com.wims.iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.wims.iot.common.exception.KGBusinessException;
import com.wims.iot.common.result.KgpResultCode;
import com.wims.iot.common.util.RandomStringGenerator;
import com.wims.iot.mapper.PlanExeMapper;
import com.wims.iot.mapper.PlanFeedbackMapper;
import com.wims.iot.model.entity.ColFile;
import com.wims.iot.model.entity.PlanExe;
import com.wims.iot.model.entity.PlanFeedback;
import com.wims.iot.model.entity.PlanFile;
import com.wims.iot.model.query.PlanFileExeFeedBackQuery;
import com.wims.iot.model.vo.PlanFileExeFeedEvaVo;
import com.wims.iot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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

    @Autowired
    IPlanFileService planFileService;

    @Autowired
    IColFileService colFileService;

    @Autowired
    IPlanEvaService planEvaService;

    @Autowired
    PlanFeedbackMapper planFeedbackMapper;

    @Override
    public Boolean updateStatus(String executionId, String exeStatus) {
        PlanExe planExe = new PlanExe();
        planExe.setStatus(exeStatus);
        if("completed".equals(exeStatus) || "failed".equals(exeStatus)){
            planExe.setEndTime(new Date());
        }
        planExe.setLastFeedBackTime(new Date());
        return this.baseMapper.update(planExe,new QueryWrapper<PlanExe>().eq("id",executionId)) == 1;
    }

    @Override
    public Boolean updateFeedBackTime(String executionId) {
        PlanExe planExe = new PlanExe();
        planExe.setLastFeedBackTime(new Date());
        return this.baseMapper.update(planExe,new QueryWrapper<PlanExe>().eq("id",executionId)) == 1;
    }

    @Override
    public IPage<PlanFileExeFeedEvaVo> getPlanFileExeFeedBackList(PlanFileExeFeedBackQuery query) {
        Page<PlanFileExeFeedEvaVo> page = new Page<>(query.getPage(), query.getPageSize());
        this.baseMapper.getPlanFileExeFeedBackList(page,query);
        List<PlanFileExeFeedEvaVo> records = page.getRecords();
        records.forEach(record->{
            String exeId = record.getVId();
            record.setPlanEva(planEvaService.getPlanFileEvaById(exeId));
            record.setFeedbackList(planFeedbackMapper.selectList(new QueryWrapper<PlanFeedback>().eq("exe_id",exeId)));
        });
        return page;
    }

    @Override
    @Transactional
    public Boolean planFileExec(String planFileId, String executor) {
        PlanFile planFileById = planFileService.getPlanFileById(planFileId);
        ColFile colFileById = colFileService.getColFileById(planFileById.getFileId());
        PlanExe planExe = new PlanExe();
        planExe.setId("exe_" + RandomStringGenerator.generate(6));
        planExe.setStatus("running");
        planExe.setPlanFileId(planFileId);
        planExe.setPlanFileName(StringUtils.isNullOrEmpty(colFileById.getFilename()) ? null : colFileById.getFilename());
        planExe.setStartTime(new Date());
        if(this.baseMapper.insert(planExe) == 1){
            return planEvaService.addPlanFileEva(planExe.getId());
        }else{
            throw new KGBusinessException(KgpResultCode.SYSTEM_EXECUTION_ERROR);
        }
    }

}
