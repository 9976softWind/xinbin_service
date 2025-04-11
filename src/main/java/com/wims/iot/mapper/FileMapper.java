package com.wims.iot.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wims.iot.model.entity.File;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wims.iot.model.query.FilePageQuery;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 文件表 Mapper 接口
 * </p>
 *
 * @author tdw
 * @since 2025-04-10
 */
@Mapper
public interface FileMapper extends BaseMapper<File> {

    Page<File>  getFilePage(Page<File> page, FilePageQuery filePageQuery);
}
