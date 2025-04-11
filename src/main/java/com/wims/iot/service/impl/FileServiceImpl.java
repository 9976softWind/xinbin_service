package com.wims.iot.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wims.iot.model.entity.File;
import com.wims.iot.mapper.FileMapper;
import com.wims.iot.model.query.FilePageQuery;
import com.wims.iot.service.IFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author tdw
 * @since 2025-04-10
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

    @Override
    public IPage<File> getFilePage(FilePageQuery filePageQuery) {
        Page<File> page = new Page<>(filePageQuery.getPageNum(), filePageQuery.getPageSize());
        this.baseMapper.getFilePage(page,filePageQuery);
        return page;
    }
}
