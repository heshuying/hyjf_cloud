package com.hyjf.am.response.trade.account;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;

import java.math.BigDecimal;

public class AccountRechargeResponse  extends Response<AccountRechargeVO> {

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
