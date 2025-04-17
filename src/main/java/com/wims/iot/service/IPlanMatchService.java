package com.wims.iot.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wims.iot.model.entity.PlanFile;
import com.wims.iot.model.query.PlanMatchQuery;

public interface IPlanMatchService {

    IPage<PlanFile> planSimilarityMatchList(PlanMatchQuery query);
}
