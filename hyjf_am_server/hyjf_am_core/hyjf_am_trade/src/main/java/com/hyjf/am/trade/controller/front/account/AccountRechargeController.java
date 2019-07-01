/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.account;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.account.AccountRechargeResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.front.account.AccountRecharge;
import com.hyjf.am.vo.admin.AccountRechargeVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author fuqiang
 * @version AccountRechargeController, v0.1 2018/7/17 11:00
 */
@RestController
@RequestMapping("/am-trade/accountrecharge")
public class AccountRechargeController extends BaseController {
    @Autowired
    private AccountRecharge accountRecharge;


    /**
     * 获取充值金额
     * @param list 用户id集合
     * @return
     */
    @RequestMapping("/getrechargeprice")
    public AccountRechargeResponse getRechargePrice(@RequestBody List<Integer> list) {
        AccountRechargeResponse response = new AccountRechargeResponse();
        BigDecimal rechargePrice = accountRecharge.getRechargePrice(list);
        if (rechargePrice != null) {
            response.setRechargePrice(rechargePrice);
            return response;
        }
        return null;
    }

    /**
     * 根据用户id获取用户充值
     * @param userId
     * @return
     */
    @GetMapping("/getAccountRechargeByUserId/{userId}")
    public com.hyjf.am.response.admin.AccountRechargeResponse getAccountRechargeByUserId(@PathVariable Integer userId) {
        com.hyjf.am.response.admin.AccountRechargeResponse response = new com.hyjf.am.response.admin.AccountRechargeResponse();
        List<com.hyjf.am.trade.dao.model.auto.AccountRecharge> list = accountRecharge.getAccountRechargeByUserId(userId);
        if (CollectionUtils.isNotEmpty(list)) {
            List<AccountRechargeVO> voList = CommonUtils.convertBeanList(list,AccountRechargeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 根据用户ID查询用户充值成功记录
     *
     * @param userId
     * @return
     */
    @RequestMapping("/selectRechargeListByUserId/{userId}")
    public AccountRechargeResponse selectRechargeListByUserId(@PathVariable Integer userId){
        AccountRechargeResponse response = new AccountRechargeResponse();
        List<com.hyjf.am.trade.dao.model.auto.AccountRecharge> list = accountRecharge.selectRechargeListByUserId(userId);
        if (CollectionUtils.isNotEmpty(list)) {
            List<com.hyjf.am.vo.trade.account.AccountRechargeVO> voList = CommonUtils.convertBeanList(list,com.hyjf.am.vo.trade.account.AccountRechargeVO.class);
            response.setResultList(voList);
        }
        return response;

    }

    /**
     * 根据用户Id查询用户第一笔充值记录
     *
     * @param userId
     * @return
     */
    @GetMapping("/selectAccountRechargeByUserId/{userId}")
    public AccountRechargeResponse selectAccountRechargeByUserId(@PathVariable Integer userId) {
        AccountRechargeResponse response = new AccountRechargeResponse();
        com.hyjf.am.trade.dao.model.auto.AccountRecharge accountRecharge = this.accountRecharge.selectAccountRechargeByUserId(userId);
        if (accountRecharge != null) {
            com.hyjf.am.vo.trade.account.AccountRechargeVO accountRechargeVO = new com.hyjf.am.vo.trade.account.AccountRechargeVO();
            BeanUtils.copyProperties(accountRecharge, accountRechargeVO);
            response.setResult(accountRechargeVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
}
