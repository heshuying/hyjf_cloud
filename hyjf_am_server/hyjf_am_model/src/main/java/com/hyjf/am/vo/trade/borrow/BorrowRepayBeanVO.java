/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.borrow;

import com.hyjf.am.trade.dao.model.auto.BorrowRecover;
import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowRepayVO, v0.1 2018/6/22 15:59
 */
public class BorrowRepayBeanVO extends BorrowRepayVO implements Serializable {

    private static final long serialVersionUID = -3598731835082249723L;
    private List<BorrowRecover> recoverList;
    private String repayTimeStr;

    private String borrowStatus;

    private String ip;

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

    public List<BorrowRecover> getRecoverList() {
        return recoverList;
    }

    public void setRecoverList(List<BorrowRecover> recoverList) {
        this.recoverList = recoverList;
    }
}
