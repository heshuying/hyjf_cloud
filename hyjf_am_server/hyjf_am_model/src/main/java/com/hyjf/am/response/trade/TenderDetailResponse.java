package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserRechargeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserTradeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserWithdrawListCustomizeVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version TenderDetailResponse, v0.1 2018/6/27 15:38
 */
public class TenderDetailResponse  extends Response<BaseVO> {
    //用户交易明细列表
    private List<WebUserTradeListCustomizeVO> userTrades;
    //用户充值明细列表
    private List<WebUserRechargeListCustomizeVO> rechargeList;
    //用户提现明细列表
    private List<WebUserWithdrawListCustomizeVO> withdrawList;

    //用户交易明细数量
    private Integer userTradesCount=0;
    //用户充值明细数量
    private Integer rechargeListCount=0;
    ///用户提现明细数量
    private Integer withdrawListCount=0;

    public List<WebUserTradeListCustomizeVO> getUserTrades() {
        return userTrades;
    }

    public void setUserTrades(List<WebUserTradeListCustomizeVO> userTrades) {
        this.userTrades = userTrades;
    }

    public List<WebUserRechargeListCustomizeVO> getRechargeList() {
        return rechargeList;
    }

    public void setRechargeList(List<WebUserRechargeListCustomizeVO> rechargeList) {
        this.rechargeList = rechargeList;
    }

    public List<WebUserWithdrawListCustomizeVO> getWithdrawList() {
        return withdrawList;
    }

    public void setWithdrawList(List<WebUserWithdrawListCustomizeVO> withdrawList) {
        this.withdrawList = withdrawList;
    }

    public Integer getUserTradesCount() {
        return userTradesCount;
    }

    public void setUserTradesCount(Integer userTradesCount) {
        this.userTradesCount = userTradesCount;
    }

    public Integer getRechargeListCount() {
        return rechargeListCount;
    }

    public void setRechargeListCount(Integer rechargeListCount) {
        this.rechargeListCount = rechargeListCount;
    }

    public Integer getWithdrawListCount() {
        return withdrawListCount;
    }

    public void setWithdrawListCount(Integer withdrawListCount) {
        this.withdrawListCount = withdrawListCount;
    }
}
