package com.wims.iot.model.vo;

import com.wims.iot.model.entity.PlanExe;
import com.wims.iot.model.entity.PlanFeedback;
import com.wims.iot.model.entity.PlanFile;
import lombok.Data;

import java.util.List;

/**
 * @author tdw
 * @date 2025.4.16
 */
@Data
public class PlanFileExeFeedBackVo {

    private String id;

    private PlanFile planFile;
    private PlanExe planExe;
    private List<PlanFeedback> feedbackList;
}
