package com.hyjf.cs.trade.bean;


import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;
import com.hyjf.am.vo.app.AppTenderCreditInvestListCustomizeVO;

import java.util.List;

public class TransferInvestRecordResultBean extends BaseResultBeanFrontEnd {
    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = 5825234430507653546L;
    private String userCount;
    private String  account;
    private boolean isEnd;
    private List<AppTenderCreditInvestListCustomizeVO> list;
    public String getUserCount() {
        return userCount;
    }
    public void setUserCount(String userCount) {
        this.userCount = userCount;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public boolean getIsEnd() {
        return isEnd;
    }
    public void setIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }
    public List<AppTenderCreditInvestListCustomizeVO> getList() {
        return list;
    }
    public void setList(List<AppTenderCreditInvestListCustomizeVO> list) {
        this.list = list;
    }

    
    
    
}
