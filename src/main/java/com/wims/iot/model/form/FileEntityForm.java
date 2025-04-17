package com.wims.iot.model.form;


import lombok.Data;

@Data
public class FileEntityForm {

    //文件 ID关联的主体id
    private String categoryId;
    //主体字段信息（全量更新）
    private String fields;

}
