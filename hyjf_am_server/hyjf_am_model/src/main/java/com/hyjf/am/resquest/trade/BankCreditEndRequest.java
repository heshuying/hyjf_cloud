/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.trade.BankCreditEndVO;

/**
 * @author liubin
 * @version BankCreditEndRequest, v0.1 2018/7/6 18:16
 */
public class BankCreditEndRequest extends BaseVO {

    private BankCreditEndVO bankCreditEndVO;

    public BankCreditEndRequest(BankCreditEndVO bankCreditEndVO) {
        this.bankCreditEndVO = bankCreditEndVO;
    }

    public BankCreditEndVO getBankCreditEndVO() {
        return bankCreditEndVO;
    }

    public void setBankCreditEndVO(BankCreditEndVO bankCreditEndVO) {
        this.bankCreditEndVO = bankCreditEndVO;
    }
}
