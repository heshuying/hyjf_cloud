/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;

import java.util.Map;

/**
 * @author liubin
 * @version UpdateCreditForAutoTenderRequest, v0.1 2018/7/5 18:12
 */
public class UpdateCreditForAutoTenderRequest {
    private HjhDebtCreditVO hjhDebtCreditVO;
    private HjhAccedeVO hjhAccedeVO;
    private HjhPlanVO hjhPlanVO;
    private BankCallBeanVO bankCallBeanVO;
    private String tenderUsrcustid;
    private String sellerUsrcustid;
    private Map<String, Object> resultMap;

    public UpdateCreditForAutoTenderRequest(HjhDebtCreditVO hjhDebtCreditVO, HjhAccedeVO hjhAccedeVO, HjhPlanVO hjhPlanVO, BankCallBeanVO bankCallBeanVO, String tenderUsrcustid, String sellerUsrcustid, Map<String, Object> resultMap) {
        this.hjhDebtCreditVO = hjhDebtCreditVO;
        this.hjhAccedeVO = hjhAccedeVO;
        this.hjhPlanVO = hjhPlanVO;
        this.bankCallBeanVO = bankCallBeanVO;
        this.tenderUsrcustid = tenderUsrcustid;
        this.sellerUsrcustid = sellerUsrcustid;
        this.resultMap = resultMap;
    }

    public HjhDebtCreditVO getHjhDebtCreditVO() {
        return hjhDebtCreditVO;
    }

    public void setHjhDebtCreditVO(HjhDebtCreditVO hjhDebtCreditVO) {
        this.hjhDebtCreditVO = hjhDebtCreditVO;
    }

    public HjhAccedeVO getHjhAccedeVO() {
        return hjhAccedeVO;
    }

    public void setHjhAccedeVO(HjhAccedeVO hjhAccedeVO) {
        this.hjhAccedeVO = hjhAccedeVO;
    }

    public HjhPlanVO getHjhPlanVO() {
        return hjhPlanVO;
    }

    public void setHjhPlanVO(HjhPlanVO hjhPlanVO) {
        this.hjhPlanVO = hjhPlanVO;
    }

    public BankCallBeanVO getBankCallBeanVO() {
        return bankCallBeanVO;
    }

    public void setBankCallBeanVO(BankCallBeanVO bankCallBeanVO) {
        this.bankCallBeanVO = bankCallBeanVO;
    }

    public String getTenderUsrcustid() {
        return tenderUsrcustid;
    }

    public void setTenderUsrcustid(String tenderUsrcustid) {
        this.tenderUsrcustid = tenderUsrcustid;
    }

    public String getSellerUsrcustid() {
        return sellerUsrcustid;
    }

    public void setSellerUsrcustid(String sellerUsrcustid) {
        this.sellerUsrcustid = sellerUsrcustid;
    }

    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }
}
