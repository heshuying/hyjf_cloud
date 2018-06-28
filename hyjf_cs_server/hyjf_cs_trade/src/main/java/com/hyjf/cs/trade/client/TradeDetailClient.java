package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.TradeDetailBeanRequest;
import com.hyjf.am.vo.trade.AccountTradeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserRechargeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserTradeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserWithdrawListCustomizeVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version TradeDetail, v0.1 2018/6/27 10:44
 */
public interface TradeDetailClient {
    /**
     * @Description 获取交易类型列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    List<AccountTradeVO> selectTradeTypes();
    /**
     * @Description "获取用户收支明细列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    int countUserTradeRecordTotal(TradeDetailBeanRequest form);
    /**
     * @Description 获取用户收支明细列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    List<WebUserTradeListCustomizeVO> searchUserTradeList(TradeDetailBeanRequest form);
    /**
     * @Description 获取用户充值列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    int countUserRechargeRecordTotal(TradeDetailBeanRequest form);
    /**
     * @Description 获取用户充值列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    List<WebUserRechargeListCustomizeVO> searchUserRechargeList(TradeDetailBeanRequest form);

    int countUserWithdrawRecordTotal(TradeDetailBeanRequest form);

    List<WebUserWithdrawListCustomizeVO> searchUserWithdrawList(TradeDetailBeanRequest form);
}
