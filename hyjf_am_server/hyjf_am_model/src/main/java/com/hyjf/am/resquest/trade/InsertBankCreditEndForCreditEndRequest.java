/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;

/**
 * @author liubin
 * @version InsertBankCreditEndForCreditEndRequest, v0.1 2018/7/6 18:16
 */
public class InsertBankCreditEndForCreditEndRequest extends BaseVO {

    private HjhDebtCreditVO hjhDebtCreditVO;
    private String tenderAccountId;
    private String tenderAuthCode;

    public InsertBankCreditEndForCreditEndRequest() {
    }

    public InsertBankCreditEndForCreditEndRequest(HjhDebtCreditVO hjhDebtCreditVO, String tenderAccountId, String tenderAuthCode) {
        this.hjhDebtCreditVO = hjhDebtCreditVO;
        this.tenderAccountId = tenderAccountId;
        this.tenderAuthCode = tenderAuthCode;
    }

    public HjhDebtCreditVO getHjhDebtCreditVO() {
        return hjhDebtCreditVO;
    }

    public void setHjhDebtCreditVO(HjhDebtCreditVO hjhDebtCreditVO) {
        this.hjhDebtCreditVO = hjhDebtCreditVO;
    }

    public String getTenderAccountId() {
        return tenderAccountId;
    }

    public void setTenderAccountId(String tenderAccountId) {
        this.tenderAccountId = tenderAccountId;
    }

    public String getTenderAuthCode() {
        return tenderAuthCode;
    }

    public void setTenderAuthCode(String tenderAuthCode) {
        this.tenderAuthCode = tenderAuthCode;
    }
}
