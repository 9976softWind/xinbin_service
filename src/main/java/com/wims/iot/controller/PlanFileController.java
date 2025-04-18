package com.wims.iot.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.wims.iot.common.exception.KGBusinessException;
import com.wims.iot.common.result.KgpResult;
import com.wims.iot.model.entity.PlanFile;
import com.wims.iot.model.form.FileBindEntityForm;
import com.wims.iot.model.form.FileEntityForm;
import com.wims.iot.model.form.PlanFileAddForm;
import com.wims.iot.model.form.PlanFileSetForm;
import com.wims.iot.model.vo.PlanCategoryVo;
import com.wims.iot.service.IPlanFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 预案文件表 前端控制器
 * </p>
 *
 * @author tdw
 * @since 2025-04-14
 */
@RestController
@RequestMapping("/v1/plans/files")
public class PlanFileController {

    @Autowired
    IPlanFileService planFileService;

    @PostMapping
    public KgpResult<Boolean> addPlanFile(@RequestBody PlanFile planFile){
        try {
            return KgpResult.judge(planFileService.addPlanFile(planFile));
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode());
        }
    }

    @PostMapping("/batch")
    public KgpResult<Boolean> addPlanFileIntoDic(@RequestBody PlanFileAddForm planFileAddForm){
        try {
            return KgpResult.judge(planFileService.addPlanFileIntoDic(planFileAddForm));
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode(),e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public KgpResult<Boolean> deletePlanFile(@PathVariable String id){
        try {
            return  KgpResult.judge(planFileService.deletePlanFile(id)) ;
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode(),e.getMessage());
        }
    }

    @PostMapping("/{id}")
    public KgpResult<Boolean> setPlanFileIntoDic(@PathVariable String id ,@RequestBody PlanFileSetForm form){
        try {
            return KgpResult.judge(planFileService.setPlanFileIntoDic(id,form));
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode(),e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public KgpResult<Boolean> setPlanFileBasicInfo(@PathVariable String id,@RequestBody PlanFile planFile){
        try {
            return KgpResult.judge(planFileService.setPlanFileBasicInfo(id,planFile)) ;
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode());
        }
    }

    @PutMapping("/{id}/category/bind")
    public KgpResult<Boolean> setFileBindEntityInfo(@PathVariable String id,@RequestBody FileBindEntityForm form){
        try {
            return KgpResult.judge(planFileService.setFileBindEntityInfo(id,form)) ;
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode());
        }
    }

    @PutMapping("/{id}/entityAttributes")
    public KgpResult<Boolean> setPlanFileEntityInfo(@PathVariable String id,@RequestBody FileEntityForm entityInfo){
        try {
            return KgpResult.judge(planFileService.setPlanFileEntityInfo(id,entityInfo)) ;
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode());
        }
    }

    @GetMapping("/{id}/entityCategory")
    public KgpResult<List<PlanCategoryVo>> getPlanFileCategoryInfo(@PathVariable String id){
        return KgpResult.success(planFileService.getPlanFileCategoryInfo(id)) ;
    }

    /**
     * 预案发送
     * @param id    要发送的文件 ID
     * @param recipients    接收者标识列表（用户 ID、部门 ID、接口地址等）
     * @param channel 发送渠道
     * @param message 附带的消息内容
     * @return
     */
    @PostMapping("/{id}/send")
    public KgpResult<Boolean> sendPlanFile(@PathVariable String id,
                                           @RequestParam(value = "recipients",required = true) String recipients,
                                           @RequestParam(value = "channel",required = false) String channel,
                                           @RequestParam(value = "message",required = false) String message){
        try {
            return KgpResult.judge(planFileService.sendPlanFile(id,recipients,channel,message)) ;
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode());
        }
    }

    @GetMapping("/{id}/preview")
    public KgpResult<JsonNode> previewPlanFile(@PathVariable String id){
        try {
            return KgpResult.success(planFileService.previewPlanFile(id));
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode());
        }

    }

}
