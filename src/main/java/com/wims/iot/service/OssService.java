package com.wims.iot.service;

import com.wims.iot.model.dto.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 对象存储服务接口层
 *
 * @author haoxr
 * @since 2022/11/19
 */
public interface OssService {

    /**
     * 上传文件
     * @param file 表单文件对象
     * @return 文件信息
     */
    FileInfo uploadFile(MultipartFile file);

    /**
     * 删除文件
     *
     * @param filePath 文件完整URL
     * @return 删除结果
     */
    boolean deleteFile(String filePath);


    List<FileInfo> uploadMultiFile(List<MultipartFile> files);
}
