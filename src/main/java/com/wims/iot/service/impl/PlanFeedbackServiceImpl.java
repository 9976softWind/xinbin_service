package com.wims.iot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.wims.iot.common.exception.KGBusinessException;
import com.wims.iot.common.result.KgpResultCode;
import com.wims.iot.common.util.RandomStringGenerator;
import com.wims.iot.mapper.PlanFeedbackMapper;
import com.wims.iot.model.entity.PlanFeedback;
import com.wims.iot.service.IPlanExeService;
import com.wims.iot.service.IPlanFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 预案反馈表 服务实现类
 * </p>
 *
 * @author tdw
 * @since 2025-04-16
 */
@Service
public class PlanFeedbackServiceImpl extends ServiceImpl<PlanFeedbackMapper, PlanFeedback> implements IPlanFeedbackService {

    @Autowired
    IPlanExeService planExeService;

    @Override
    @Transactional
    public Boolean planExeFeedBack(String executionId, PlanFeedback planFeedback) {
        planFeedback.setId("feed_" + RandomStringGenerator.generate(6));
        planFeedback.setExeId(executionId);
        planFeedback.setFeedBackTime(new Date());
        if(!StringUtils.isNullOrEmpty(planFeedback.getExeStatus())){
            //更新执行实例的状态
            if(planExeService.updateStatus(executionId,planFeedback.getExeStatus())){
                if(this.baseMapper.insert(planFeedback) == 1){
                    Boolean aBoolean = planExeService.updateFeedBackTime(executionId);
                    if(aBoolean){
                        return true;
                    }else{
                        throw new KGBusinessException(KgpResultCode.SYSTEM_EXECUTION_ERROR);
                    }
                }else{
                    throw new KGBusinessException(KgpResultCode.SYSTEM_EXECUTION_ERROR);
                }
            }else{
                throw new KGBusinessException(KgpResultCode.UPDATE_EXE_STATUS_ERROR);
            }
        }else{
            if(this.baseMapper.insert(planFeedback) == 1){
                Boolean aBoolean = planExeService.updateFeedBackTime(executionId);
                if(aBoolean){
                    return true;
                }else{
                    throw new KGBusinessException(KgpResultCode.SYSTEM_EXECUTION_ERROR);
                }
            }else{
                throw new KGBusinessException(KgpResultCode.SYSTEM_EXECUTION_ERROR);
            }
        }
    }
}
