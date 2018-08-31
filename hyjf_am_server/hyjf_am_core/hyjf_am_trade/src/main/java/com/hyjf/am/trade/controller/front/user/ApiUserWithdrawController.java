/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.user;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.trade.account.AccountWithdrawResponse;
import com.hyjf.am.resquest.trade.ApiUserWithdrawRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.AccountWithdraw;
import com.hyjf.am.trade.service.front.withdraw.UserWithdrawService;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: ApiUserWithdrawController, v0.1 2018/8/30 15:26
 */
@RestController
@RequestMapping(value = "/am-trade/withdraw")
public class ApiUserWithdrawController extends BaseController {

    @Autowired
    private UserWithdrawService userWithdrawService;

    /**
     * 用户提现更新数据表
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/updateBeforeCash")
    public IntegerResponse updateBeforeCash(@RequestBody ApiUserWithdrawRequest request){
        IntegerResponse response = new IntegerResponse();
        int ret = userWithdrawService.updateBeforeCash(request);
        response.setResultInt(ret);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 根据orderId查询出status=2的账户提现信息
     * @auth sunpeikai
     * @param orderId 订单号
     * @return
     */
    @GetMapping(value = "/getAccountWithdrawByOrderId/{orderId}")
    public AccountWithdrawResponse getAccountWithdrawByOrderId(@PathVariable String orderId){
        AccountWithdrawResponse response = new AccountWithdrawResponse();
        AccountWithdraw accountWithdraw = userWithdrawService.getAccountWithdrawByOrderId(orderId);
        if(accountWithdraw != null){
            AccountWithdrawVO accountWithdrawVO = CommonUtils.convertBean(accountWithdraw,AccountWithdrawVO.class);
            response.setResult(accountWithdrawVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 执行提现后处理
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/handlerAfterCash")
    public StringResponse handlerAfterCash(@RequestBody ApiUserWithdrawRequest request) throws Exception {
        StringResponse response = new StringResponse();
        String result = userWithdrawService.handlerAfterCash(request);
        response.setResultStr(result);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 查询某用户 id 的提现记录，带分页
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/searchAccountWithdrawPaginate")
    public AccountWithdrawResponse searchAccountWithdrawPaginate(@RequestBody ApiUserWithdrawRequest request){
        AccountWithdrawResponse response = new AccountWithdrawResponse();
        List<AccountWithdraw> accountWithdrawList = userWithdrawService.searchAccountWithdrawPaginate(request);
        if(!CollectionUtils.isEmpty(accountWithdrawList)){
            List<AccountWithdrawVO> accountWithdrawVOList = CommonUtils.convertBeanList(accountWithdrawList,AccountWithdrawVO.class);
            response.setResultList(accountWithdrawVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
}
