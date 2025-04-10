package com.wims.iot.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 文件表
 * </p>
 *
 * @author tdw
 * @since 2025-04-10
 */
@Getter
@Setter
@TableName("t_file")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID，非空自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文件id
     */
    private String fileId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件存储路径
     */
    private String filePath;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件大小
     */
    private Integer fileSize;

    /**
     * 所属目录（引用t_cata的主键Id）
     */
    private Integer cataId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
