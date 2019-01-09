package com.hyjf.am.trade.controller.front.hjh;

import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.trade.PlanInvestResponse;
import com.hyjf.am.response.trade.PlanLockResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.front.hjh.HtjService;
import com.hyjf.am.vo.trade.PlanInvestCustomizeVO;
import com.hyjf.am.vo.trade.PlanLockCustomizeVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/am-trade/htj")
public class HtjController extends BaseController {

    @Autowired
    private HtjService htjService;

    @PostMapping("/selectInvestCreditList")
    public PlanInvestResponse selectInvestCreditList(@RequestBody Map<String,Object> param) {
        PlanInvestResponse response = new PlanInvestResponse();
        List<PlanInvestCustomizeVO> planInvestCustomizeList = htjService.selectInvestCreditList(param);
        if (!CollectionUtils.isEmpty(planInvestCustomizeList)) {
            response.setResultList(planInvestCustomizeList);
        }
        return response;
    }

    @PostMapping("/selectCreditCreditList")
    public PlanInvestResponse selectCreditCreditList(@RequestBody Map<String,Object> param) {
        PlanInvestResponse response = new PlanInvestResponse();
        List<PlanInvestCustomizeVO> planInvestCustomizeList = htjService.selectCreditCreditList(param);
        if (!CollectionUtils.isEmpty(planInvestCustomizeList)) {
            response.setResultList(planInvestCustomizeList);
        }
        return response;
    }



    @PostMapping("/selectUserProjectListCapital")
    public PlanLockResponse selectUserProjectListCapital(@RequestBody Map<String,Object> param) {
        PlanLockResponse response = new PlanLockResponse();
        List<PlanLockCustomizeVO> planInvestCustomizeList = htjService.selectUserProjectListCapital(param);
        if (!CollectionUtils.isEmpty(planInvestCustomizeList)) {
            response.setResultList(planInvestCustomizeList);
        }
        return response;
    }



    @PostMapping("/selectPlanInfoSum/{accedeOrderId}")
    public StringResponse selectPlanInfoSum(@PathVariable String accedeOrderId) {
        StringResponse response = new StringResponse();
        String investTotal = htjService.selectPlanInfoSum(accedeOrderId);
        if (StringUtils.isNotBlank(investTotal)) {
            response.setResultStr(investTotal);
        }
        return response;
    }
}
