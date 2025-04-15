package com.wims.iot.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author tdw
 * @since 2025-04-15
 */
@Getter
@Setter
@TableName("col_file")
public class ColFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  文件自增id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文件id
     */
    private String fileId;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 文件存储路径
     */
    private String filepath;

    /**
     * 文件类型
     */
    private String filetype;

    /**
     * 文件大小
     */
    private Long filesize;

    /**
     * 所属机构
     */
    private String institution;

    /**
     * 上传状态
     */
    private String upstate;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private LocalDateTime createtime;

    /**
     * 假删除
     */
    private Boolean isdelete;

    /**
     * 目录路径
     */
    private String catapath;

    /**
     * 所属权限
     */
    private String permission;

    /**
     * 水利业务标签
     */
    private String business;

    /**
     * 水利行政标签
     */
    private String administration;

    /**
     * 业务阶段标签
     */
    private String stage;


}
