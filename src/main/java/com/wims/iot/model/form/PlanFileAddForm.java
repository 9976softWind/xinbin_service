package com.wims.iot.model.form;


import lombok.Data;

import java.util.List;
import java.util.Map;


@Data
public class PlanFileAddForm {

    private List<String> fileId;

    private String directoryId;

    private String preplanPriority;

    private String disasterType;

    private String applicableArea;

    private String description;

    private List<AttributeDTO> attributes;

    @Data
    public static class  AttributeDTO {

        private String categoryId;

        private Map<String, String> fields;

    }

}
