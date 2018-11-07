package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.admin.WebUserInvestListCustomizeVO;

import java.io.Serializable;

/**
 * @author pangchengchao
 * @version UserInvestListBeanRequest, v0.1 2018/11/6 9:35
 */
public class UserInvestListBeanRequest extends WebUserInvestListCustomizeVO implements Serializable {

    // 项目编号
    public String borrowNid;
    // 用户id
    public String userId;
    //-------融通宝添加-----------
    //项目类型
    public String projectType;
    //资产编号
    public String assetNumber;

    //借款 金额
    public String account;

    public String flag;

    //承接人id
    public String creditUserId;

    public int limitStart;
    public int limitEnd;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    //-------融通宝添加end-----------
    /**
     *
     */
    private static final long serialVersionUID = 3278149257478770256L;

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

    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }


    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getCreditUserId() {
        return creditUserId;
    }

    public void setCreditUserId(String creditUserId) {
        this.creditUserId = creditUserId;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }
}