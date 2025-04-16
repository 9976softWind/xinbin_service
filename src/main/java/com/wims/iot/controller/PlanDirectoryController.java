package com.wims.iot.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wims.iot.common.exception.KGBusinessException;
import com.wims.iot.common.result.KgPageResult;
import com.wims.iot.common.result.KgpResult;
import com.wims.iot.model.entity.PlanDirectory;
import com.wims.iot.model.entity.PlanFile;
import com.wims.iot.model.query.PlanDirectoryFileQuery;
import com.wims.iot.model.query.PlanDirectoryQuery;
import com.wims.iot.service.IPlanDirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 预案知识库目录 前端控制器
 * </p>
 *
 * @author tdw
 * @since 2025-04-14
 */
@RestController
@RequestMapping("/v1/plans/directories")
public class PlanDirectoryController {

    @Autowired
    IPlanDirectoryService planDirectoriesService;

    /**
     * 获取所有预案目录列表，支持按名称筛选、搜索和分页。不支持多层目录结构，所有目录为一级目录。
     * @param name
     * @param sortBy
     * @param sortOrder
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping
    public KgPageResult<PlanDirectory> getPlanDirectoryList(@RequestParam(value = "name",required = false) String name,
                                                            @RequestParam(value = "sortBy",required = false) String sortBy,
                                                            @RequestParam(value = "sortOrder",required = false) String sortOrder,
                                                            @RequestParam(value = "page",required = true) Integer page,
                                                            @RequestParam(value = "pageSize",required = true) Integer pageSize){
        PlanDirectoryQuery query = new PlanDirectoryQuery();
        query.setName(name);
        query.setSortBy(sortBy);
        query.setSortOrder(sortOrder);
        query.setPage(page);
        query.setPageSize(pageSize);
        IPage<PlanDirectory> result = planDirectoriesService.getPlanDirectoryList(query);
        return KgPageResult.success(result);
    }

    /**
     * 目录下的文件列表查询
     * @param directoryId
     * @param keyword
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/{directoryId}/files")
    public KgPageResult<PlanFile> getPlanDirectoryFileList(@PathVariable String directoryId,
                                                           @RequestParam(value = "keyword",required = false) String keyword,
                                                           @RequestParam(value = "page",required = true) Integer page,
                                                           @RequestParam(value = "pageSize",required = true) Integer pageSize){
        PlanDirectoryFileQuery query = new PlanDirectoryFileQuery();
        query.setDirectoryId(directoryId);
        query.setKeyword(keyword);
        query.setPage(page);
        query.setPageSize(pageSize);
        IPage<PlanFile> result = planDirectoriesService.getPlanDirectoryFileList(query);
        return KgPageResult.success(result);
    }

    /**
     * 新增预案目录
     * @param name 新目录的名称，在系统中必须唯一
     * @return
     */
    @PostMapping
    public KgpResult<Boolean> addPlanDirectory(@RequestParam(value = "name",required = true) String name){
        try {
            return KgpResult.judge(planDirectoriesService.addPlanDirectory(name));
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode());
        }
    }

    /**
     * 编辑目录名称
     * @param directoryId   目录的 ID
     * @param name  新的名称
     * @return
     */
    @PutMapping("/{directoryId}")
    public KgpResult<Boolean> setPlanDirectory(@PathVariable String directoryId, @RequestParam(value = "name") String name){
        try {
            return KgpResult.judge(planDirectoriesService.setPlanDirectory(directoryId,name));
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode());
        }
    }

    /**
     * 删除目录
     * @param directoryId 要删除的目录 ID
     * @param isForce 是否强制删除非空目录（及其内容)
     * @return
     */
    @DeleteMapping("/{directoryId}")
    public KgpResult<Boolean> deletePlanDirectory(@PathVariable String directoryId, @RequestParam(value = "force") Boolean isForce){
        try {
            return KgpResult.judge(planDirectoriesService.deletePlanDirectory(directoryId, isForce));
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode());
        }
    }


    @PutMapping("/{directoryId}/move")
    public KgpResult<Boolean> transferPlanDirectory(@PathVariable String directoryId,@RequestParam(value = "newDirectoryId") String newDirectoryId ){
        try {
            return KgpResult.judge(planDirectoriesService.transferPlanDirectory(directoryId, newDirectoryId));
        } catch (KGBusinessException e){
            return KgpResult.failed(e.getResultCode());
        }
    }



}
