package com.hyjf.cs.trade.bean;

import com.hyjf.am.vo.trade.tradedetail.WebUserRechargeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserTradeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserWithdrawListCustomizeVO;
import com.hyjf.cs.common.bean.result.WebResult;

import java.util.List;

/**
 * @author pangchengchao
 * @version TradeDetailBean, v0.1 2018/6/27 11:45
 */
public class TradeDetailBean extends WebResult {

    //交易明细
    private List<WebUserTradeListCustomizeVO> tradeList;

    // 充值明细
    private List<WebUserRechargeListCustomizeVO> rechargeList;

    // 取现明细
    private List<WebUserWithdrawListCustomizeVO> withdrawList;
    // 数组类型
    private String listType;

    public List<WebUserTradeListCustomizeVO> getTradeList() {
        return tradeList;
    }

    public void setTradeList(List<WebUserTradeListCustomizeVO> tradeList) {
        this.tradeList = tradeList;
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

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }
}
