package com.hyjf.am.trade.service.front.account.impl;

import com.hyjf.am.trade.dao.model.auto.AccountTrade;
import com.hyjf.am.trade.dao.model.auto.AccountTradeExample;
import com.hyjf.am.trade.dao.model.customize.app.AppAccountTradeListCustomize;
import com.hyjf.am.trade.service.front.account.AccountTradeService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pangchengchao
 * @version AccountTradeServiceImpl, v0.1 2018/6/27 11:10
 */
@Service
public class AccountTradeServiceImpl extends BaseServiceImpl implements AccountTradeService {

    @Override
    public List<AccountTrade> selectTradeTypes() {
        AccountTradeExample example = new AccountTradeExample();
        AccountTradeExample.Criteria crt=example.createCriteria();
        crt.andStatusEqualTo(1);
        return accountTradeMapper.selectByExample(example);
    }

    @Override
    public List<AppAccountTradeListCustomize> searchAppTradeTypes() {
        List<AppAccountTradeListCustomize> list=appAccountTradeCustomizeMapper.selectTradeTypeList();
        /**创建优惠券回款相关交易明细查询条件*/
        AppAccountTradeListCustomize appAccountTradeListCustomize=new AppAccountTradeListCustomize();
        appAccountTradeListCustomize.setName("优惠券收益");
        appAccountTradeListCustomize.setValue("coupon_profit");
        list.add(appAccountTradeListCustomize);
        return list;
    }
}
