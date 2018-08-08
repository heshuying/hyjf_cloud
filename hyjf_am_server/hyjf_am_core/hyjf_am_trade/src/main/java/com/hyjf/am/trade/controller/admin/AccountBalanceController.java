package com.hyjf.am.trade.controller.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhAccountBalanceResponse;
import com.hyjf.am.resquest.admin.HjhAccountBalanceRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.AccountBalanceService;
import com.hyjf.am.vo.admin.HjhAccountBalanceVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/7  11:41
 */
@Api(value = "数据中心-汇计划统计")
@RestController
@RequestMapping("/am-trade/manager/statis")
public class AccountBalanceController  extends BaseController {

    @Autowired
    private AccountBalanceService accountBalanceService;

    @RequestMapping("/getHjhAccountBalanceMonthCountNew")
    public HjhAccountBalanceResponse getHjhAccountBalanceMonthCountNew(@RequestBody HjhAccountBalanceRequest request){
        HjhAccountBalanceResponse response = new HjhAccountBalanceResponse();
        int count = accountBalanceService.getHjhAccountBalanceMonthCountNew(request);
        response.setCount(count);
        return response;
    }

    @RequestMapping("/getHjhAccountBalanceMonthCount")
    public HjhAccountBalanceResponse getHjhAccountBalanceMonthCount(@RequestBody HjhAccountBalanceRequest request){
        HjhAccountBalanceResponse response = new HjhAccountBalanceResponse();
        int count = accountBalanceService.getHjhAccountBalanceMonthCount(request);
        response.setCount(count);
        return response;
    }

    @RequestMapping("/getHjhAccountBalanceMonthList")
    public HjhAccountBalanceResponse getHjhAccountBalanceMonthList(@RequestBody HjhAccountBalanceRequest request){
        HjhAccountBalanceResponse response = new HjhAccountBalanceResponse();
        List<HjhAccountBalanceVO> list = accountBalanceService.getHjhAccountBalanceMonthList(request);
        String returnCode = Response.FAIL;
        if (null != list && list.size() > 0) {
            response.setResultList(list);
            returnCode = Response.SUCCESS;
        }
        response.setRtn(returnCode);
        return response;
    }

    @RequestMapping("/getHjhAccountBalancecountByDay")
    public HjhAccountBalanceResponse getHjhAccountBalancecountByDay(@RequestBody HjhAccountBalanceRequest request){
        HjhAccountBalanceResponse response = new HjhAccountBalanceResponse();
        int count = accountBalanceService.getHjhAccountBalancecountByDay(request);
        response.setCount(count);
        return response;
    }

    @RequestMapping("/getHjhAccountBalanceListByDay")
    public HjhAccountBalanceResponse getHjhAccountBalanceListByDay(@RequestBody HjhAccountBalanceRequest request){
        HjhAccountBalanceResponse response = new HjhAccountBalanceResponse();
        List<HjhAccountBalanceVO> list = accountBalanceService.getHjhAccountBalanceListByDay(request);
        String returnCode = Response.FAIL;
        if (null != list && list.size() > 0) {
            response.setResultList(list);
            returnCode = Response.SUCCESS;
        }
        response.setRtn(returnCode);
        return response;
    }

}
