/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.trade.BankCreditEndVO;

/**
 * @author liubin
 * @version UpdateBankCreditEndForStatusRequest, v0.1 2018/7/11 14:51
 */
public class UpdateBankCreditEndForStatusRequest extends BaseVO {
    BankCreditEndVO bankCreditEndVO;
    int status;

    public UpdateBankCreditEndForStatusRequest(BankCreditEndVO bankCreditEndVO, int status) {
        this.bankCreditEndVO = bankCreditEndVO;
        this.status = status;
    }

    public BankCreditEndVO getBankCreditEndVO() {
        return bankCreditEndVO;
    }

    public void setBankCreditEndVO(BankCreditEndVO bankCreditEndVO) {
        this.bankCreditEndVO = bankCreditEndVO;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
