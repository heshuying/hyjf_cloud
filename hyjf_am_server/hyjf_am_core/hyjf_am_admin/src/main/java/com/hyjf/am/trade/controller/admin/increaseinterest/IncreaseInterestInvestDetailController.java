package com.hyjf.am.trade.controller.admin.increaseinterest;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.IncreaseInterestInvestDetailResponse;
import com.hyjf.am.resquest.admin.IncreaseInterestInvestDetailRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.IncreaseInterestInvestDetailService;
import com.hyjf.am.vo.admin.IncreaseInterestInvestVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wenxin
 * @version IncreaseInterestInvestDetailController, v0.1 2018/8/30
 */
@Api(value = "产品中心-汇直投-加息投资明细")
@RestController
@RequestMapping("/am-trade/increaseinterest")
public class IncreaseInterestInvestDetailController extends BaseController {
    @Autowired
    IncreaseInterestInvestDetailService increaseInterestInvestDetailService;

    @RequestMapping(value = "/getIncreaseInterestInvestDetaiCount",method = RequestMethod.POST)
    public IncreaseInterestInvestDetailResponse getIncreaseInterestInvestDetaiCount(@RequestBody IncreaseInterestInvestDetailRequest request){
        IncreaseInterestInvestDetailResponse response = new IncreaseInterestInvestDetailResponse();
        int count = increaseInterestInvestDetailService.getIncreaseInterestInvestDetaiCount(request);
        response.setCount(count);
        return response;
    }

    @RequestMapping(value = "/getIncreaseInterestInvestDetaiList",method = RequestMethod.POST)
    public IncreaseInterestInvestDetailResponse getIncreaseInterestInvestDetaiList(@RequestBody IncreaseInterestInvestDetailRequest request){
        IncreaseInterestInvestDetailResponse response = new IncreaseInterestInvestDetailResponse();
        List<IncreaseInterestInvestVO> list = increaseInterestInvestDetailService.getIncreaseInterestInvestDetaiList(request);
        /*--------add by LSY START-----------*/
        String sumAccount = this.increaseInterestInvestDetailService.sumAccount(request);
        response.setSumAccount(sumAccount);
        /*--------add by LSY END-----------*/
        String returnCode = Response.FAIL;
        if (null != list && list.size() > 0) {
            response.setResultList(list);
            returnCode = Response.SUCCESS;
        }
        response.setRtn(returnCode);
        return response;
    }

    @RequestMapping(value = "/getInvestDetailSumAccount",method = RequestMethod.POST)
    public IncreaseInterestInvestDetailResponse getSumAccount(@RequestBody IncreaseInterestInvestDetailRequest request){
        IncreaseInterestInvestDetailResponse response = new IncreaseInterestInvestDetailResponse();
        /*--------add by LSY START-----------*/
        String sumAccount = this.increaseInterestInvestDetailService.sumAccount(request);
        //response.setSumAccount(sumAccount);
        /*--------add by LSY END-----------*/
        String returnCode = Response.FAIL;
        if (null != sumAccount) {
            response.setSumAccount(sumAccount);
            returnCode = Response.SUCCESS;
        }
        response.setRtn(returnCode);
        return response;
    }


}
