package com.wims.iot.service.impl;

import com.wims.iot.model.entity.File;
import com.wims.iot.mapper.FileMapper;
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

}
