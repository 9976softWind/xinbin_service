package com.wims.iot.model.form;


import lombok.Data;

import java.util.List;

@Data
public class FileBindEntityForm {

    //文件 ID关联的主体id
    private List<String> categoryIds;

}
