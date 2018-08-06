package com.hyjf.am.trade.controller.front.account;

import com.hyjf.am.response.trade.account.AccountTradeResponse;
import com.hyjf.am.response.trade.account.AppAccountTradeListCustomizeResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.AccountTrade;
import com.hyjf.am.trade.dao.model.customize.app.AppAccountTradeListCustomize;
import com.hyjf.am.trade.service.front.account.AccountTradeService;
import com.hyjf.am.vo.trade.AccountTradeVO;
import com.hyjf.am.vo.trade.account.AppAccountTradeListCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author pangchengchao
 * @version AccountTradeController, v0.1 2018/6/27 11:06
 */
@RestController
@RequestMapping("/am-trade/accountTrade")
public class AccountTradeController extends BaseController {

    @Autowired
    private AccountTradeService accountTradeService;

    @GetMapping("/selectTradeTypes/{borrowUserId}")
    public AccountTradeResponse selectTradeTypes(Integer borrowUserId) {
        AccountTradeResponse response = new AccountTradeResponse();
        List<AccountTrade> list = accountTradeService.selectTradeTypes();
        if(!CollectionUtils.isEmpty(list)){
            List<AccountTradeVO> voList = CommonUtils.convertBeanList(list, AccountTradeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    @GetMapping("/searchAppTradeTypes")
    public AppAccountTradeListCustomizeResponse searchAppTradeTypes(){
        AppAccountTradeListCustomizeResponse response = new AppAccountTradeListCustomizeResponse();
        List<AppAccountTradeListCustomize> list = accountTradeService.searchAppTradeTypes();
        if (CollectionUtils.isNotEmpty(list)){
            response.setResultList(CommonUtils.convertBeanList(list,AppAccountTradeListCustomizeVO.class));
        }
        return response;
    }

}
