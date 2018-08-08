package com.hyjf.cs.trade.service.trade;

import com.hyjf.am.resquest.app.AppTradeDetailBeanRequest;
import com.hyjf.am.resquest.trade.TradeDetailBeanRequest;
import com.hyjf.am.vo.trade.AccountTradeVO;
import com.hyjf.am.vo.trade.account.AppAccountTradeListCustomizeVO;
import com.hyjf.cs.trade.bean.TradeDetailBean;
import com.hyjf.cs.trade.bean.app.AppTradeDetailBean;
import com.hyjf.cs.trade.service.BaseTradeService;

import java.util.List;

/**
 * @author pangchengchao
 * @version TradeDetailService, v0.1 2018/6/27 10:12
 */
public interface TradeDetailService extends BaseTradeService {
    List<AccountTradeVO> selectTradeTypes();

    TradeDetailBean searchUserTradeList(TradeDetailBeanRequest form);

    TradeDetailBean searchUserRechargeList(TradeDetailBeanRequest form);

    TradeDetailBean searchUserWithdrawList(TradeDetailBeanRequest form);

    List<AppAccountTradeListCustomizeVO> searchAppTradeTypes();
    /**
     * 创建用户收支明细列表分页数据
     */
    AppTradeDetailBean createTradeDetailListPage(AppTradeDetailBeanRequest trade);
}
