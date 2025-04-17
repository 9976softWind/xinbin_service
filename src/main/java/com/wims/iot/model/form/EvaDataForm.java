package com.wims.iot.model.form;


import lombok.Data;

import java.util.List;

@Data
public class EvaDataForm {
    //预案完整性评估结果
    private List<Integer> completenessRating;
    //预案连续性评估结果
    private List<Integer> continuityRating;
    //预案成效评估结果
    private List<Integer> effectivenessRating;
    //预案风险评估结果
    private List<Integer> riskRating;
    //评估意见或评语
    private String comments;
    //评估人信息
    private String evaluator;

}
