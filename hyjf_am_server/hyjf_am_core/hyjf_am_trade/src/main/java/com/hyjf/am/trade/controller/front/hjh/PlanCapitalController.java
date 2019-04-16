/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.hjh;

import com.hyjf.am.response.admin.HjhPlanCapitalPredictionResponse;
import com.hyjf.am.response.admin.HjhPlanCapitalResponse;
import com.hyjf.am.trade.service.front.trade.PlanCapitalService;
import com.hyjf.am.vo.trade.HjhPlanCapitalPredictionVO;
import com.hyjf.am.vo.trade.HjhPlanCapitalVO;
import com.hyjf.common.util.GetDate;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liubin
 * @version PlanCapitalController, v0.1 2018/7/31 15:24
 */
@Api(value = "汇计划资本按天统计及预估表")
@RestController
@RequestMapping("/am-trade/planCapitalController")
public class PlanCapitalController {
    // logger
    private static final Logger logger = LoggerFactory.getLogger(PlanCapitalController.class);

    @Autowired
    private PlanCapitalService planCapitalService;

    /**
     * 获取该日期的实际债转和复投金额
     * @param date
     * @return
     */
    @GetMapping("/getPlanCapitalForActList/{date}")
    public HjhPlanCapitalResponse getPlanCapitalForActList(@PathVariable(value = "date") String date){
        HjhPlanCapitalResponse response = new HjhPlanCapitalResponse();
        List<HjhPlanCapitalVO> list = this.planCapitalService.getPlanCapitalForActList(GetDate.stringToDate2(date));
        if (!CollectionUtils.isEmpty(list)) {
            response.setResultList(list);
        }
        return response;
    }

    /**
     * 获取该期间的预估债转和复投金额
     * @param fromDate
     * @param toDate
     * @return
     */
    @GetMapping("/getPlanCapitalForProformaList/{fromDate}/{toDate}")
    public HjhPlanCapitalResponse getPlanCapitalForProformaList(@PathVariable(value = "fromDate") String fromDate,
                                                                @PathVariable(value = "toDate") String toDate){
        HjhPlanCapitalResponse response = new HjhPlanCapitalResponse();
        List<HjhPlanCapitalVO> list = this.planCapitalService.getPlanCapitalForProformaList(GetDate.stringToDate2(fromDate), GetDate.stringToDate2(toDate));
        if (!CollectionUtils.isEmpty(list)) {
            response.setResultList(list);
        }
        return response;
    }

    /**
     * 获取该期间的预计当日新增复投额
     * @param date
     * @return
     */
    @GetMapping("/getPlanCapitalPredictionForProformaList/{date}")
    public HjhPlanCapitalPredictionResponse getPlanCapitalPredictionForProformaList(@PathVariable(value = "date") String date){
        HjhPlanCapitalPredictionResponse response = new HjhPlanCapitalPredictionResponse();
        List<HjhPlanCapitalPredictionVO> list = this.planCapitalService.getPlanCapitalPredictionForProformaList(GetDate.stringToDate2(date));
        if (!CollectionUtils.isEmpty(list)) {
            response.setResultList(list);
        }
        return response;
    }

    /**
     * 获取该期间的预计当日新增债转
     * @param date
     * @return
     */
    @GetMapping("/getPlanCapitalForCreditList/{date}/{dualDate}")
    public HjhPlanCapitalPredictionResponse getPlanCapitalForCreditList(@PathVariable(value = "date") String date,@PathVariable(value = "dualDate") String dualDate){
        HjhPlanCapitalPredictionResponse response = new HjhPlanCapitalPredictionResponse();
        List<HjhPlanCapitalPredictionVO> list = this.planCapitalService.getPlanCapitalForCreditList(GetDate.stringToDate2(date),GetDate.stringToDate2(dualDate));
        if (!CollectionUtils.isEmpty(list)) {
            response.setResultList(list);
        }
        return response;
    }

}
