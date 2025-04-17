package com.wims.iot.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wims.iot.model.entity.PlanFile;
import com.wims.iot.model.query.PlanFileQuery;
import com.wims.iot.model.query.PlanMatchQuery;
import com.wims.iot.service.IPlanFileService;
import com.wims.iot.service.IPlanMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanMatchServiceImpl implements IPlanMatchService {

    @Autowired
    IPlanFileService planFileService;

    @Override
    public IPage<PlanFile> planSimilarityMatchList(PlanMatchQuery query) {
        //TODO:elasticSearch反向查找逻辑，查找出符合条件的预案记录
        //整理成List<String> 元素：t_plan_file的id
        List<String> matchedIds = new ArrayList();
        PlanFileQuery planFileQuery = new PlanFileQuery();
        planFileQuery.setIds(matchedIds);
        planFileQuery.setPage(query.getPage());
        planFileQuery.setPageSize(query.getPageSize());
        return  planFileService.getPlanFileByIds(planFileQuery);
    }
}
