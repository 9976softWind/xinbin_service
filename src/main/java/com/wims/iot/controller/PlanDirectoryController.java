package com.wims.iot.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wims.iot.common.result.KgPageResult;
import com.wims.iot.common.result.KgpResult;
import com.wims.iot.common.result.KgpResultCode;
import com.wims.iot.model.entity.PlanDirectory;
import com.wims.iot.model.query.PlanDirectoryQuery;
import com.wims.iot.service.IPlanDirectoryService;
import io.swagger.v3.oas.annotations.Operation;
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

    //TODO:获取预案目录：获取预案目录列表，支持按父目录筛选、搜索和分页，常用于构建目录树或显示特
    //定层级的目录。



    @Operation(summary = "分页查询预案目录")
    @GetMapping
    public KgPageResult<PlanDirectory> getPlanDirectoryPage(@RequestParam(value = "parentId",required = false) String parentId,
                                                            @RequestParam(value = "name",required = false) String name,
                                                            @RequestParam(value = "sortBy",required = false) String sortBy,
                                                            @RequestParam(value = "sortOrder",required = false) String sortOrder,
                                                            @RequestParam(value = "page",required = true) Integer page,
                                                            @RequestParam(value = "pageSize",required = true) Integer pageSize){
        PlanDirectoryQuery query = new PlanDirectoryQuery();
        query.setParentId(parentId);
        query.setName(name);
        query.setSortBy(sortBy);
        query.setSortOrder(sortOrder);
        query.setPage(page);
        query.setPageSize(pageSize);
        IPage<PlanDirectory> result = planDirectoriesService.getPlanDirectoryPage(query);
        return KgPageResult.success(result);
    }


    /**
     * 新增预案目录
     * @param name 新目录的名称，同一父目录下唯一
     * @param parentId 父目录的 ID，若为根目录则不传
     * @return
     */
    @PostMapping
    public KgpResult<PlanDirectory> addPlanDirectory(@RequestParam(value = "name",required = true) String name,
                                                     @RequestParam(value = "parentId",required = false) String parentId){
        PlanDirectory insertResult = planDirectoriesService.addPlanDirectory(name,parentId);
        return ObjectUtil.isNull(insertResult) ? KgpResult.failed(KgpResultCode.SYSTEM_EXECUTION_ERROR) : KgpResult.success(insertResult);
    }

    /**
     * 编辑目录名称
     * @param directoryId   目录的 ID
     * @param name  新的名称
     * @return
     */
    @PutMapping("/{directoryId}")
    public KgpResult<PlanDirectory> setPlanDirectory(@PathVariable String directoryId, @RequestParam(value = "name") String name){
        PlanDirectory updateResult = planDirectoriesService.setPlanDirectory(directoryId,name);
        return ObjectUtil.isNull(updateResult) ? KgpResult.failed(KgpResultCode.SYSTEM_EXECUTION_ERROR) : KgpResult.success(updateResult);
    }

    /**
     * 删除目录
     * @param directoryId 要删除的目录 ID
     * @param isForce 是否强制删除非空目录（及其内容)
     * @return
     */
    @DeleteMapping("/{directoryId}")
    public KgpResult<PlanDirectory> deletePlanDirectory(@PathVariable String directoryId, @RequestParam(value = "force",required = false) Boolean isForce){
        Boolean deleteResult = planDirectoriesService.deletePlanDirectory(directoryId, isForce);
        return deleteResult ? KgpResult.success() : KgpResult.failed();
    }

    @PutMapping("/{directoryId}/move")
    public KgpResult<PlanDirectory> transferPlanDirectory(@PathVariable String directoryId,@RequestParam(value = "newParentId") String newParentId ){
        PlanDirectory transferResult = planDirectoriesService.transferPlanDirectory(directoryId,newParentId);
        return ObjectUtil.isNull(transferResult) ? KgpResult.failed(KgpResultCode.SYSTEM_EXECUTION_ERROR) : KgpResult.success(transferResult);
    }



}
