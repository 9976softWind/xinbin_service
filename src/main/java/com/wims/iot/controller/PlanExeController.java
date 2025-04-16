package com.wims.iot.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wims.iot.common.result.KgPageResult;
import com.wims.iot.model.entity.PlanExe;
import com.wims.iot.model.query.PlanExeQuery;
import com.wims.iot.service.IPlanExeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 预案执行表 前端控制器
 * </p>
 *
 * @author tdw
 * @since 2025-04-16
 */
@RestController
@RequestMapping("/v1/plans/executions")
public class PlanExeController {

    @Autowired
    IPlanExeService planExeService;

    /**
     * 查询预案执行实例的列表，支持筛选和分页
     * @param planFileId    关联的预案文件 ID
     * @param status        执行状态
     * @param executor      执行主体 ID
     * @param startTime     开始日期筛选 (yyyy-MM-dd HH:mm:ss)
     * @param endTime       结束日期筛选 (yyyy-MM-dd HH:mm:ss)
     * @param sortBy        排序字段 (start_time,status)
     * @param sortOrder     排序顺序 (asc,desc)
     * @param page          页码 (从 1 开始)
     * @param pageSize      每页数量 (默认 20)
     * @return
     */
    @GetMapping
    public KgPageResult<PlanExe> getPlanExeList(@RequestParam(value = "planFileId",required = false) String planFileId,
                                                @RequestParam(value = "status",required = false) String status,
                                                @RequestParam(value = "executor",required = false) String executor,
                                                @RequestParam(value = "startTime",required = false) String startTime,
                                                @RequestParam(value = "endTime",required = false) String endTime,
                                                @RequestParam(value = "sortBy",required = false) String sortBy,
                                                @RequestParam(value = "sortOrder",required = false) String sortOrder,
                                                @RequestParam(value = "page",required = true) Integer page,
                                                @RequestParam(value = "pageSize",required = true) Integer pageSize){
        PlanExeQuery query = new PlanExeQuery();
        query.setPlanFileId(planFileId);
        query.setStatus(status);
        query.setExecutor(executor);
        query.setStartTime(startTime);
        query.setEndTime(endTime);
        query.setSortBy(sortBy);
        query.setSortOrder(sortOrder);
        query.setPage(page);
        query.setPageSize(pageSize);
        IPage<PlanExe> result = planExeService.getPlanExeList(query);
        return KgPageResult.success(result);
    }

}
