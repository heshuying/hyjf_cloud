package com.hyjf.am.trade.dao.model.customize.admin;

import com.hyjf.am.trade.dao.model.auto.BorrowRecover;
import com.hyjf.am.trade.dao.model.auto.BorrowRepay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRepayBean, v0.1 2018/7/6 11:06
 */
public class BorrowRepayBean extends BorrowRepay {
    /** 用户还款详情 */
    private List<BorrowRecover> recoverList = new ArrayList<BorrowRecover>();

    private String repayTimeStr;

    private String borrowStatus;

    private String ip;

    public List<BorrowRecover> getRecoverList() {
        return recoverList;
    }

    public void setRecoverList(List<BorrowRecover> recoverList) {
        this.recoverList = recoverList;
    }

    public String getRepayTimeStr() {
        return repayTimeStr;
    }

    public void setRepayTimeStr(String repayTimeStr) {
        this.repayTimeStr = repayTimeStr;
    }

    public String getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(String borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
