package com.wims.iot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wims.iot.common.model.Option;
import com.wims.iot.common.result.Result;
import com.wims.iot.model.entity.Cata;
import com.wims.iot.model.form.CataForm;
import com.wims.iot.model.query.CataQuery;
import com.wims.iot.model.vo.CataVO;
import com.wims.iot.service.ICataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 目录表 前端控制器
 * </p>
 *
 * @author tdw
 * @since 2025-04-10
 */
@RestController
@RequestMapping("/api/v1/cata")
@RequiredArgsConstructor
public class CataController {

    private final ICataService cataService;

    @Operation(summary = "获取目录列表")
    @GetMapping
    public Result<List<CataVO>> listCatalogues(
            CataQuery queryParams
    ) {
        List<CataVO> list = cataService.listCatas(queryParams);
        return Result.success(list);
    }

    @Operation(summary = "获取目录下拉选项")
    @GetMapping("/options")
    public Result<List<Option>> listCataOptions() {
        List<Option> list = cataService.listCataOptions();
        return Result.success(list);
    }

    @Operation(summary = "获取目录表单数据")
    @GetMapping("/{cataId}/form")
    public Result<CataForm> getCataForm(
            @Parameter(description ="目录ID") @PathVariable Long cataId
    ) {
        CataForm cataForm = cataService.getCataForm(cataId);
        return Result.success(cataForm);
    }

    @Operation(summary = "新增目录")
    @PostMapping
    public Result saveCata(
            @Valid @RequestBody CataForm formData
    ) {
        Long id = cataService.saveCata(formData);
        return Result.success(id);
    }

    @Operation(summary = "修改目录")
    @PutMapping(value = "/{cataId}")
    public Result updateCata(
            @PathVariable Long cataId,
            @Valid @RequestBody CataForm formData
    ) {
        cataId = cataService.updateCata(cataId, formData);
        return Result.success(cataId);
    }

    @Operation(summary = "删除目录")
    @DeleteMapping("/{ids}")
    public Result deleteCatas(
            @Parameter(description ="部门ID，多个以英文逗号(,)分割") @PathVariable("ids") String ids
    ) {
        boolean result = cataService.deleteByIds(ids);
        return Result.judge(result);
    }

    @Operation(summary = "获取所有目录")
    @GetMapping("/all")
    public Result<List<Cata>> getAllCata() {
        List<Cata> result = cataService.list(new LambdaQueryWrapper<Cata>().select(Cata::getId, Cata::getName));
        return Result.success(result);
    }
}
