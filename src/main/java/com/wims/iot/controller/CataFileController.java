package com.wims.iot.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wims.iot.common.result.PageResult;
import com.wims.iot.common.result.Result;
import com.wims.iot.model.dto.FileInfo;
import com.wims.iot.model.entity.File;
import com.wims.iot.model.query.FilePageQuery;
import com.wims.iot.service.IFileService;
import com.wims.iot.service.OssService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 目录文件表 前端控制器
 * </p>
 *
 * @author tdw
 * @since 2025-04-10
 */
@RestController
@RequestMapping("/api/v1/cata-file")
@RequiredArgsConstructor
public class CataFileController {

    private final IFileService iFileService;

    private final OssService ossService;


    @Operation(summary = "分页查询目录文件列表")
    @GetMapping("/list")
    public PageResult<File> getFilePage(@RequestParam(value = "fileName",required = false) String fileName,
                                        @RequestParam(value = "cataId",required = false) Long cataId,
                                           @RequestParam(value = "pageNum",required = true) Integer pageNum,
                                           @RequestParam(value = "pageSize",required = true) Integer pageSize){
        FilePageQuery filePageQuery = new FilePageQuery();
        filePageQuery.setFileName(fileName);
        filePageQuery.setCataId(cataId);
        filePageQuery.setPageNum(pageNum);
        filePageQuery.setPageSize(pageSize);
        IPage<File> result = iFileService.getFilePage(filePageQuery);
        return PageResult.success(result);
    }



    @Operation(summary = "上传文件")
    @PostMapping
    public Result<Integer> uploadMultiFile(
            @RequestParam(value = "cataId") String cataId,
             @RequestParam(value = "files") List<MultipartFile> files
    ) {
        List<FileInfo> fileInfos = ossService.uploadMultiFile(files);
        Integer result = iFileService.insertMany(Integer.parseInt(cataId), fileInfos);
        return Result.success(result);
    }

    @Operation(summary = "删除文件")
    @DeleteMapping("/{idsStr}")
    public Result deleteFiles(
            @Parameter(description = "文件ID，多个以英文逗号(,)分割") @PathVariable String idsStr
    ) {
        iFileService.deleteFiles(idsStr);
        return Result.success();
    }

}
