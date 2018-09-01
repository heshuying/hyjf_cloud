package com.hyjf.am.trade.controller.admin.increaseinterest;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.IncreaseInterestRepayDetailResponse;
import com.hyjf.am.resquest.admin.IncreaseInterestRepayDetailRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.IncreaseInterestRepayDetailService;
import com.hyjf.am.trade.service.admin.IncreaseInterestRepayService;
import com.hyjf.am.vo.admin.AdminIncreaseInterestRepayCustomizeVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wenxin
 * @version IncreaseInterestRepayDetailController, v0.1 2018/8/30
 */
@Api(value = "产品中心-汇直投-加息还款明细")
@RestController
@RequestMapping("/am-trade/increaseinterest")
public class IncreaseInterestRepayDetailController extends BaseController {
    @Autowired
    IncreaseInterestRepayDetailService increaseInterestRepayDetailService;

    @RequestMapping(value = "/getIncreaseInterestRepayDetailCount",method = RequestMethod.POST)
    public IncreaseInterestRepayDetailResponse getIncreaseInterestRepayDetailCount(@RequestBody IncreaseInterestRepayDetailRequest request){
        IncreaseInterestRepayDetailResponse response = new IncreaseInterestRepayDetailResponse();
        int count = increaseInterestRepayDetailService.getIncreaseInterestRepayDetailCount(request);
        response.setCount(count);
        return response;
    }

    @RequestMapping(value = "/getIncreaseInterestRepayDetailList",method = RequestMethod.POST)
    public IncreaseInterestRepayDetailResponse getIncreaseInterestRepayDetailList(@RequestBody IncreaseInterestRepayDetailRequest request){
        IncreaseInterestRepayDetailResponse response = new IncreaseInterestRepayDetailResponse();
        List<AdminIncreaseInterestRepayCustomizeVO> list = increaseInterestRepayDetailService.getIncreaseInterestRepayDetailList(request);
        /*--------add by LSY START-----------*/
        AdminIncreaseInterestRepayCustomizeVO sumAccount = this.increaseInterestRepayDetailService.sumBorrowRepaymentInfo(request);
        if (null != sumAccount) {
            response.setSumRepayCapital(sumAccount.getSumRepayCapital());
            response.setSumRepayInterest(sumAccount.getSumRepayInterest());
        }
        /*--------add by LSY END-----------*/
        String returnCode = Response.FAIL;
        if (null != list && list.size() > 0) {
            response.setResultList(list);
            returnCode = Response.SUCCESS;
        }
        response.setRtn(returnCode);
        return response;
    }

    @RequestMapping(value = "/getSumBorrowRepaymentInfo",method = RequestMethod.POST)
    public IncreaseInterestRepayDetailResponse getSumBorrowRepaymentInfo(@RequestBody IncreaseInterestRepayDetailRequest request){
        IncreaseInterestRepayDetailResponse response = new IncreaseInterestRepayDetailResponse();
        /*--------add by LSY START-----------*/
        AdminIncreaseInterestRepayCustomizeVO sumAccount = this.increaseInterestRepayDetailService.sumBorrowRepaymentInfo(request);
        //response.setSumAccount(sumAccount);
        /*--------add by LSY END-----------*/
        String returnCode = Response.FAIL;
        if (null != sumAccount) {
            response.setResult(sumAccount);
            returnCode = Response.SUCCESS;
        }
        response.setRtn(returnCode);
        return response;
    }
}
