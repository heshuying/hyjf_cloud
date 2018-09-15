package com.hyjf.am.trade.controller.admin.increaseinterest;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.IncreaseInterestRepayInfoListResponse;
import com.hyjf.am.resquest.admin.IncreaseInterestRepayInfoListRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.IncreaseInterestRepayInfoListService;
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
 * @version IncreaseInterestRepayInfoListController, v0.1 2018/8/30
 */
@Api(value = "产品中心-汇直投-加息投资明细详情")
@RestController
@RequestMapping("/am-trade/increaseinterest")
public class IncreaseInterestRepayInfoListController extends BaseController {
    @Autowired
    IncreaseInterestRepayInfoListService increaseInterestRepayInfoListService;

    @RequestMapping(value = "/getIncreaseInterestRepayInfoListCount",method = RequestMethod.POST)
    public IncreaseInterestRepayInfoListResponse getIncreaseInterestRepayInfoListCount(@RequestBody IncreaseInterestRepayInfoListRequest request){
        IncreaseInterestRepayInfoListResponse response = new IncreaseInterestRepayInfoListResponse();
        int count = increaseInterestRepayInfoListService.getIncreaseInterestRepayInfoListCount(request);
        response.setCount(count);
        return response;
    }

    @RequestMapping(value = "/getIncreaseInterestRepayInfoListList",method = RequestMethod.POST)
    public IncreaseInterestRepayInfoListResponse getIncreaseInterestRepayDetailList(@RequestBody IncreaseInterestRepayInfoListRequest request){
        IncreaseInterestRepayInfoListResponse response = new IncreaseInterestRepayInfoListResponse();
        List<AdminIncreaseInterestRepayCustomizeVO> list = increaseInterestRepayInfoListService.getIncreaseInterestRepayInfoListList(request);
        /*--------add by LSY START-----------*/
        AdminIncreaseInterestRepayCustomizeVO sumAccount = this.increaseInterestRepayInfoListService.sumBorrowLoanmentInfo(request);
        if(null != sumAccount){
            response.setSumLoanInterest(sumAccount.getSumLoanInterest());
            response.setSumRepayCapital(sumAccount.getSumRepayCapital());
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

    @RequestMapping(value = "/getSumBorrowLoanmentInfo",method = RequestMethod.POST)
    public IncreaseInterestRepayInfoListResponse getSumBorrowLoanmentInfo(@RequestBody IncreaseInterestRepayInfoListRequest request){
        IncreaseInterestRepayInfoListResponse response = new IncreaseInterestRepayInfoListResponse();
        /*--------add by LSY START-----------*/
        AdminIncreaseInterestRepayCustomizeVO sumAccount = this.increaseInterestRepayInfoListService.sumBorrowLoanmentInfo(request);
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
