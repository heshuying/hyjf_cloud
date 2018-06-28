package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.AccountResponse;
import com.hyjf.am.response.trade.AccountTradeResponse;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.AccountTrade;
import com.hyjf.am.trade.service.AccountService;
import com.hyjf.am.trade.service.AccountTradeService;
import com.hyjf.am.vo.trade.AccountTradeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldObligatoryRightListCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author pangchengchao
 * @version AccountTradeController, v0.1 2018/6/27 11:06
 */
@RestController
@RequestMapping("am-trade/accounttrade")
public class AccountTradeController {

    @Autowired
    private AccountTradeService accountTradeService;

    @GetMapping("/selectTradeTypes")
    public AccountTradeResponse selectTradeTypes(Integer borrowUserId) {
        AccountTradeResponse response = new AccountTradeResponse();
        List<AccountTrade> list = accountTradeService.selectTradeTypes();
        if(!CollectionUtils.isEmpty(list)){
            List<AccountTradeVO> voList = CommonUtils.convertBeanList(list, AccountTradeVO.class);
            response.setResultList(voList);
        }
        return response;
    }
}
