package com.wims.iot.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "文件对象")
@Data
public class FileInfo {

    @Schema(description = "文件名称")
    private String name;

    @Schema(description = "文件URL")
    private String url;

    @Schema(description = "文件真实名称")
    private String realName;

    @Schema(description = "文件大小")
    private Long fileSize;

}
