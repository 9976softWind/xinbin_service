package com.wims.iot.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wims.iot.common.result.KgPageResult;
import com.wims.iot.model.entity.PlanFile;
import com.wims.iot.model.query.PlanMatchQuery;
import com.wims.iot.service.IPlanMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/plans/match")
public class PlanMatchController {

    @Autowired
    IPlanMatchService planMatchService;

    @GetMapping("/similarity")
    public KgPageResult<PlanFile> planSimilarityMatchList(@RequestParam(value = "matchContent") String matchContent,
                                                          @RequestParam(value = "page") Integer page,
                                                          @RequestParam(value = "pageSize") Integer pageSize){
        PlanMatchQuery query = new PlanMatchQuery();
        query.setMatchContent(matchContent);
        query.setPage(page);
        query.setPageSize(pageSize);
        IPage<PlanFile> result = planMatchService.planSimilarityMatchList(query);
        return KgPageResult.success(result);
    }
}
