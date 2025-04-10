package com.wims.iot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wims.iot.common.model.Option;
import com.wims.iot.common.result.Result;
import com.wims.iot.model.entity.SysDept;
import com.wims.iot.model.form.DeptForm;
import com.wims.iot.model.query.DeptQuery;
import com.wims.iot.model.vo.DeptVO;
import com.wims.iot.plugin.dupsubmit.annotation.PreventDuplicateSubmit;
import com.wims.iot.service.SysDeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 部门控制器
 *
 * @author haoxr
 * @since 2020/11/6
 */
@Tag(name = "05.部门接口")
@RestController
@RequestMapping("/api/v1/dept")
@RequiredArgsConstructor
public class SysDeptController {
    private final SysDeptService deptService;
    @Operation(summary = "获取部门列表")
    @GetMapping
    @PreAuthorize("@ss.hasPerm('sys:dept:get')")
    public Result<List<DeptVO>> listDepartments(
            DeptQuery queryParams
    ) {
        List<DeptVO> list = deptService.listDepartments(queryParams);
        return Result.success(list);
    }

    @Operation(summary = "获取部门下拉选项")
    @GetMapping("/options")
    public Result<List<Option>> listDeptOptions() {
        List<Option> list = deptService.listDeptOptions();
        return Result.success(list);
    }

    @Operation(summary = "获取部门表单数据")
    @GetMapping("/{deptId}/form")
    public Result<DeptForm> getDeptForm(
            @Parameter(description ="部门ID") @PathVariable Long deptId
    ) {
        DeptForm deptForm = deptService.getDeptForm(deptId);
        return Result.success(deptForm);
    }

    @Operation(summary = "新增部门")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:dept:add')")
    @PreventDuplicateSubmit
    public Result saveDept(
            @Valid @RequestBody DeptForm formData
    ) {
        Long id = deptService.saveDept(formData);
        return Result.success(id);
    }

    @Operation(summary = "修改部门")
    @PutMapping(value = "/{deptId}")
    @PreAuthorize("@ss.hasPerm('sys:dept:edit')")
    public Result updateDept(
            @PathVariable Long deptId,
            @Valid @RequestBody DeptForm formData
    ) {
        deptId = deptService.updateDept(deptId, formData);
        return Result.success(deptId);
    }

    @Operation(summary = "删除部门")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:dept:delete')")
    public Result deleteDepartments(
            @Parameter(description ="部门ID，多个以英文逗号(,)分割") @PathVariable("ids") String ids
    ) {
        boolean result = deptService.deleteByIds(ids);
        return Result.judge(result);
    }

    @Operation(summary = "获取所有部门")
    @GetMapping("/all")
    public Result<List<SysDept>> getAllDept() {
        List<SysDept> result = deptService.list(new LambdaQueryWrapper<SysDept>().select(SysDept::getId, SysDept::getName));
        return Result.success(result);
    }

}
