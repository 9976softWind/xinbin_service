package com.wims.iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wims.iot.model.entity.ColFile;
import com.wims.iot.mapper.ColFileMapper;
import com.wims.iot.service.IColFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tdw
 * @since 2025-04-15
 */
@Service
public class ColFileServiceImpl extends ServiceImpl<ColFileMapper, ColFile> implements IColFileService {


    @Override
    public ColFile getColFileById(String fileId) {
        return this.baseMapper.selectOne(new QueryWrapper<ColFile>().eq("file_id",fileId));
    }
}
