package com.hyjf.am.response.trade.account;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.account.AccountRechargeCustomizeVO;

import java.math.BigDecimal;

/**
 * @Author : huanghui
 */
public class AccountRechargeCustomizeResponse extends Response<AccountRechargeCustomizeVO> {

    private int count;

    /** 充值金额 */
    BigDecimal rechargePrice;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getRechargePrice() {
        return rechargePrice;
    }

    public void setRechargePrice(BigDecimal rechargePrice) {
        this.rechargePrice = rechargePrice;
    }
}
