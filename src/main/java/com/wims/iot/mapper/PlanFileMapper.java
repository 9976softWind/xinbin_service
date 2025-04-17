package com.wims.iot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wims.iot.model.entity.PlanFile;
import com.wims.iot.model.query.PlanDirectoryFileQuery;
import com.wims.iot.model.query.PlanFileQuery;
import com.wims.iot.model.vo.PlanCategoryVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 预案文件表 Mapper 接口
 * </p>
 *
 * @author tdw
 * @since 2025-04-14
 */
@Mapper
public interface PlanFileMapper extends BaseMapper<PlanFile> {

    PlanCategoryVo getPlanFileCategoryInfo(String id);

    Page<PlanFile> getPlanDirectoryFileList(Page<PlanFile> page, PlanDirectoryFileQuery query);

    Page<PlanFile>  getPlanFileByIds(Page<PlanFile> page, PlanFileQuery query);
}
