/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.admin.MerchantAccountVO;

import java.util.List;

/**
 * @author zhangqingqing
 * @version MerchantAccountListRequest, v0.1 2018/7/5 15:04
 */
public class MerchantAccountListRequest {

    /** serialVersionUID */
    private static final long serialVersionUID = 7768418442884796575L;

    private String accountBalanceSum;

    private String availableBalanceSum;

    private String frostSum;

    private List<MerchantAccountVO> recordList;

    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;


    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    /**
     * 注册时间 开始
     */
    public String preRegTimeStart;
    /**
     * 注册时间 结束
     */
    public String preRegTimeEnd;

    public String getPreRegTimeStart() {
        return preRegTimeStart;
    }

    public void setPreRegTimeStart(String preRegTimeStart) {
        this.preRegTimeStart = preRegTimeStart;
    }

    public String getPreRegTimeEnd() {
        return preRegTimeEnd;
    }

    public void setPreRegTimeEnd(String preRegTimeEnd) {
        this.preRegTimeEnd = preRegTimeEnd;
    }

    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }

    public List<MerchantAccountVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<MerchantAccountVO> recordList) {
        this.recordList = recordList;
    }

    public String getAccountBalanceSum() {
        return accountBalanceSum;
    }

    public void setAccountBalanceSum(String accountBalanceSum) {
        this.accountBalanceSum = accountBalanceSum;
    }

    public String getAvailableBalanceSum() {
        return availableBalanceSum;
    }

    public void setAvailableBalanceSum(String availableBalanceSum) {
        this.availableBalanceSum = availableBalanceSum;
    }

    public String getFrostSum() {
        return frostSum;
    }

    public void setFrostSum(String frostSum) {
        this.frostSum = frostSum;
    }

}
