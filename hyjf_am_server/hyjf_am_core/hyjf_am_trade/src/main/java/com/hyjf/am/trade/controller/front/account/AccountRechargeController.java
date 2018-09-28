/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.account;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.account.AccountRechargeCustomizeResponse;
import com.hyjf.am.response.trade.account.AccountRechargeResponse;
import com.hyjf.am.resquest.admin.AccountRechargeRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.AccountRechargeCustomize;
import com.hyjf.am.trade.service.front.account.AccountRecharge;
import com.hyjf.am.vo.admin.AccountRechargeVO;
import com.hyjf.am.vo.trade.account.AccountRechargeCustomizeVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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


}
