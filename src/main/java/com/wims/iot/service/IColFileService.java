package com.wims.iot.service;

import com.wims.iot.model.entity.ColFile;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tdw
 * @since 2025-04-15
 */
public interface IColFileService extends IService<ColFile> {

    ColFile getColFileById(String fileId);

}
