package com.wims.iot.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wims.iot.common.exception.BusinessException;
import com.wims.iot.mapper.FileMapper;
import com.wims.iot.model.dto.FileInfo;
import com.wims.iot.model.entity.File;
import com.wims.iot.model.query.FilePageQuery;
import com.wims.iot.service.IFileService;
import com.wims.iot.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Autowired
    private OssService ossService;

    @Override
    public IPage<File> getFilePage(FilePageQuery filePageQuery) {
        Page<File> page = new Page<>(filePageQuery.getPageNum(), filePageQuery.getPageSize());
        this.baseMapper.getFilePage(page,filePageQuery);
        return page;
    }

    @Override
    public Integer insertMany(Integer cataId,List<FileInfo> fileInfos) {
        Integer count = 0;
        for (FileInfo fileInfo : fileInfos) {
            File file = new File();
            file.setFileId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
            file.setFileName(fileInfo.getRealName());
            file.setFileType("txt");
            file.setFileSize(fileInfo.getFileSize());
            file.setFilePath(fileInfo.getUrl());
            file.setCataId(cataId);
            file.setCreateTime(new Date());
            int insert = this.baseMapper.insert(file);
            count += insert;
        }
        return count;
    }

    @Override
    @Transactional
    public void deleteFiles(String idsStr) {
        Assert.isTrue(StrUtil.isNotBlank(idsStr), "删除的数据为空");
        List<Long> ids = Arrays.asList(idsStr.split(",")).stream()
                .map(idStr -> Long.parseLong(idStr)).collect(Collectors.toList());
        List<File> files = this.baseMapper.selectList(new QueryWrapper<File>().in("id", ids));
        files.forEach(file->{
            if (ossService.deleteFile(file.getFilePath())){
                if(this.baseMapper.deleteById(file.getId()) == 0){
                    throw new BusinessException(file.getFileName() + "文件删除失败：");
                }
            }
        });
    }


}
