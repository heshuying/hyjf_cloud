package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.CreditTenderVO;

import java.math.BigDecimal;

/**
 * @author jun
 * @since 20180620
 */
public class CreditTenderResponse extends Response<CreditTenderVO> {
    /** 汇转让投资金额 */
    private BigDecimal hzrTenderPrice;

    public BigDecimal getHzrTenderPrice() {
        return hzrTenderPrice;
    }

    public void setHzrTenderPrice(BigDecimal hzrTenderPrice) {
        this.hzrTenderPrice = hzrTenderPrice;
    }
}
