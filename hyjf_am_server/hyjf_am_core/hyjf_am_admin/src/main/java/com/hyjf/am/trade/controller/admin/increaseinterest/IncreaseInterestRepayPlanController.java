package com.hyjf.am.trade.controller.admin.increaseinterest;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.IncreaseInterestRepayPlanResponse;
import com.hyjf.am.resquest.admin.IncreaseInterestRepayPlanRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.IncreaseInterestRepayInfoListService;
import com.hyjf.am.trade.service.admin.IncreaseInterestRepayPlanService;
import com.hyjf.am.vo.admin.IncreaseInterestRepayDetailVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wenxin
 * @version IncreaseInterestRepayPlanController, v0.1 2018/8/30
 */
@Api(value = "产品中心-汇直投-加息还款计划")
@RestController
@RequestMapping("/am-trade/increaseinterest")
public class IncreaseInterestRepayPlanController extends BaseController {
    @Autowired
    IncreaseInterestRepayPlanService increaseInterestRepayPlanService;

    @RequestMapping(value = "/getIncreaseInterestRepayPlanCount",method = RequestMethod.POST)
    public IncreaseInterestRepayPlanResponse getIncreaseInterestRepayPlanCount(@RequestBody IncreaseInterestRepayPlanRequest request){
        IncreaseInterestRepayPlanResponse response = new IncreaseInterestRepayPlanResponse();
        int count = increaseInterestRepayPlanService.getIncreaseInterestRepayPlanCount(request);
        response.setCount(count);
        return response;
    }

    @RequestMapping(value = "/getIncreaseInterestRepayPlanList",method = RequestMethod.POST)
    public IncreaseInterestRepayPlanResponse getIncreaseInterestRepayPlanList(@RequestBody IncreaseInterestRepayPlanRequest request){
        IncreaseInterestRepayPlanResponse response = new IncreaseInterestRepayPlanResponse();
        List<IncreaseInterestRepayDetailVO> list = increaseInterestRepayPlanService.getIncreaseInterestRepayPlanList(request);
        String returnCode = Response.FAIL;
        if (null != list && list.size() > 0) {
            response.setResultList(list);
            returnCode = Response.SUCCESS;
        }
        response.setRtn(returnCode);
        return response;
    }
}
