package com.wims.iot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wims.iot.model.dto.FileInfo;
import com.wims.iot.model.entity.File;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wims.iot.model.query.FilePageQuery;

import java.util.List;

/**
 * <p>
 * 文件表 服务类
 * </p>
 *
 * @author tdw
 * @since 2025-04-10
 */
public interface IFileService extends IService<File> {

    IPage<File> getFilePage(FilePageQuery filePageQuery);


    Integer insertMany(Integer cataId,List<FileInfo> fileInfos);

    void deleteFiles(String ids);

}
