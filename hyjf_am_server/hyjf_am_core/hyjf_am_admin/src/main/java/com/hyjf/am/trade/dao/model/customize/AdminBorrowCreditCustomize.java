package com.hyjf.am.trade.dao.model.customize;

public class AdminBorrowCreditCustomize  {

    /**
     * 序号
     */
    private String creditId;

    /**
     * 债转编号
     */
    private String creditNid;

    /**
     * 项目编号
     */
    private String bidNid;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 债权本金
     */
    private String creditCapital;

    /**
     * 转让本金
     */
    private String creditCapitalPrice;

    /**
     * 折让率
     */
    private String creditDiscount;

    /**
     * 转让价格
     */
    private String creditPrice;

    /**
     * 已转让金额
     */
    private String creditCapitalAssigned;

    /**
     * 转让状态
     */
    private String creditStatus;

    /**
     * 发布时间
     */
    private String addTime;

    /**
     * 还款时间
     */
    private String repayLastTime;

    /**
     * 订单号
     */
    private String assignNid;

    /**
     * 出让人
     */
    private String creditUserName;

    /**
     * 转让本金
     */
    private String assignCapital;

    /**
     * 转让价格
     */
    private String assignCapitalPrice;

    /**
     * 认购金额
     */
    private String assignPrice;

    /**
     * 垫付利息
     */
    private String assignInterestAdvance;
    /**
     * 服务费
     */
    private String creditFee;
    /**
     * 认购时间
     */
    private String assignPay;
    /**
     * 状态名称
     */
    private String creditStatusName;
    /**
     * 状态名称
     */
    private String repayStatusName;
    /**
     * 客户端 0pc 1ios 2Android 3微信
     */
    private String client;

    /**
     * 承接用户推荐人用户名
     */
    private String recommendName;

    /**
     * 承接用户推荐人属性
     */
    private String recommendAttr;

    /**
     * 承接人部门信息
     */
    private String regionName;

    /**
     * 承接人部门信息
     */
    private String branchName;

    /**
     * 承接人部门信息
     */
    private String departmentName;

    /**
     * 出让用户推荐人用户名
     */
    private String recommendNameCredit;

    /**
     * 出让用户推荐人属性
     */
    private String recommendAttrCredit;

    /**
     * 出让人部门信息
     */
    private String regionNameCredit;

    /**
     * 出让人部门信息
     */
    private String branchNameCredit;

    /**
     * 出让人部门信息
     */
    private String departmentNameCredit;

    /**
     * 承接人承接时推荐人信息
     */
    private String inviteUserName;
    private String inviteUserAttribute;
    private String inviteUseRegionname;
    private String inviteUserBranchname;
    private String inviteUserDepartmentName;
    /**
     * 出让人承接时推荐人信息
     */
    private String inviteUserCreditName;
    private String inviteUserCreditAttribute;
    private String inviteUserCreditRegionName;
    private String inviteUserCreditBranchName;
    private String inviteUserCreditDepartmentName;

    /**
     * 承接人用户属性及部门信息
     */
    private String recommendAttrSelf;
    private String regionNameSelf;
    private String branchNameSelf;
    private String departmentNameSelf;

    /**
     * 出让人用户属性及部门信息
     */
    private String recommendAttrCreditSelf;
    private String regionNameCreditSelf;
    private String branchNameCreditSelf;
    private String departmentNameCreditSelf;

    /*------add by LSY START-----------*/
    /**
     * 金额合计取得信息
     */
    private String sumCreditCapital;
    private String sumCreditCapitalPrice;
    private String sumCreditPrice;
    private String sumCreditCapitalAssigned;
    private String sumAssignCapital;
    private String sumAssignCapitalPrice;
    private String sumAssignPrice;
    private String sumAssignInterestAdvance;
    private String sumCreditFee;
    private String sumAssignPay;
    /*------add by LSY END-----------*/


    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public String getBidNid() {
        return bidNid;
    }

    public void setBidNid(String bidNid) {
        this.bidNid = bidNid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreditCapital() {
        return creditCapital;
    }

    public void setCreditCapital(String creditCapital) {
        this.creditCapital = creditCapital;
    }

    public String getCreditCapitalPrice() {
        return creditCapitalPrice;
    }

    public void setCreditCapitalPrice(String creditCapitalPrice) {
        this.creditCapitalPrice = creditCapitalPrice;
    }

    public String getCreditDiscount() {
        return creditDiscount;
    }

    public void setCreditDiscount(String creditDiscount) {
        this.creditDiscount = creditDiscount;
    }

    public String getCreditPrice() {
        return creditPrice;
    }

    public void setCreditPrice(String creditPrice) {
        this.creditPrice = creditPrice;
    }

    public String getCreditCapitalAssigned() {
        return creditCapitalAssigned;
    }

    public void setCreditCapitalAssigned(String creditCapitalAssigned) {
        this.creditCapitalAssigned = creditCapitalAssigned;
    }

    public String getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(String creditStatus) {
        this.creditStatus = creditStatus;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getRepayLastTime() {
        return repayLastTime;
    }

    public void setRepayLastTime(String repayLastTime) {
        this.repayLastTime = repayLastTime;
    }

    public String getAssignNid() {
        return assignNid;
    }

    public void setAssignNid(String assignNid) {
        this.assignNid = assignNid;
    }

    public String getCreditUserName() {
        return creditUserName;
    }

    public void setCreditUserName(String creditUserName) {
        this.creditUserName = creditUserName;
    }

    public String getAssignCapital() {
        return assignCapital;
    }

    public void setAssignCapital(String assignCapital) {
        this.assignCapital = assignCapital;
    }

    public String getAssignCapitalPrice() {
        return assignCapitalPrice;
    }

    public void setAssignCapitalPrice(String assignCapitalPrice) {
        this.assignCapitalPrice = assignCapitalPrice;
    }

    public String getAssignPrice() {
        return assignPrice;
    }

    public void setAssignPrice(String assignPrice) {
        this.assignPrice = assignPrice;
    }

    public String getAssignInterestAdvance() {
        return assignInterestAdvance;
    }

    public void setAssignInterestAdvance(String assignInterestAdvance) {
        this.assignInterestAdvance = assignInterestAdvance;
    }

    public String getCreditFee() {
        return creditFee;
    }

    public void setCreditFee(String creditFee) {
        this.creditFee = creditFee;
    }

    public String getAssignPay() {
        return assignPay;
    }

    public void setAssignPay(String assignPay) {
        this.assignPay = assignPay;
    }

    public String getCreditStatusName() {
        return creditStatusName;
    }

    public void setCreditStatusName(String creditStatusName) {
        this.creditStatusName = creditStatusName;
    }

    public String getRepayStatusName() {
        return repayStatusName;
    }

    public void setRepayStatusName(String repayStatusName) {
        this.repayStatusName = repayStatusName;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }

    public String getRecommendAttr() {
        return recommendAttr;
    }

    public void setRecommendAttr(String recommendAttr) {
        this.recommendAttr = recommendAttr;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getRecommendNameCredit() {
        return recommendNameCredit;
    }

    public void setRecommendNameCredit(String recommendNameCredit) {
        this.recommendNameCredit = recommendNameCredit;
    }

    public String getRecommendAttrCredit() {
        return recommendAttrCredit;
    }

    public void setRecommendAttrCredit(String recommendAttrCredit) {
        this.recommendAttrCredit = recommendAttrCredit;
    }

    public String getRegionNameCredit() {
        return regionNameCredit;
    }

    public void setRegionNameCredit(String regionNameCredit) {
        this.regionNameCredit = regionNameCredit;
    }

    public String getBranchNameCredit() {
        return branchNameCredit;
    }

    public void setBranchNameCredit(String branchNameCredit) {
        this.branchNameCredit = branchNameCredit;
    }

    public String getDepartmentNameCredit() {
        return departmentNameCredit;
    }

    public void setDepartmentNameCredit(String departmentNameCredit) {
        this.departmentNameCredit = departmentNameCredit;
    }

    public String getInviteUserName() {
        return inviteUserName;
    }

    public void setInviteUserName(String inviteUserName) {
        this.inviteUserName = inviteUserName;
    }

    public String getInviteUserAttribute() {
        return inviteUserAttribute;
    }

    public void setInviteUserAttribute(String inviteUserAttribute) {
        this.inviteUserAttribute = inviteUserAttribute;
    }

    public String getInviteUseRegionname() {
        return inviteUseRegionname;
    }

    public void setInviteUseRegionname(String inviteUseRegionname) {
        this.inviteUseRegionname = inviteUseRegionname;
    }

    public String getInviteUserBranchname() {
        return inviteUserBranchname;
    }

    public void setInviteUserBranchname(String inviteUserBranchname) {
        this.inviteUserBranchname = inviteUserBranchname;
    }

    public String getInviteUserDepartmentName() {
        return inviteUserDepartmentName;
    }

    public void setInviteUserDepartmentName(String inviteUserDepartmentName) {
        this.inviteUserDepartmentName = inviteUserDepartmentName;
    }

    public String getInviteUserCreditName() {
        return inviteUserCreditName;
    }

    public void setInviteUserCreditName(String inviteUserCreditName) {
        this.inviteUserCreditName = inviteUserCreditName;
    }

    public String getInviteUserCreditAttribute() {
        return inviteUserCreditAttribute;
    }

    public void setInviteUserCreditAttribute(String inviteUserCreditAttribute) {
        this.inviteUserCreditAttribute = inviteUserCreditAttribute;
    }

    public String getInviteUserCreditRegionName() {
        return inviteUserCreditRegionName;
    }

    public void setInviteUserCreditRegionName(String inviteUserCreditRegionName) {
        this.inviteUserCreditRegionName = inviteUserCreditRegionName;
    }

    public String getInviteUserCreditBranchName() {
        return inviteUserCreditBranchName;
    }

    public void setInviteUserCreditBranchName(String inviteUserCreditBranchName) {
        this.inviteUserCreditBranchName = inviteUserCreditBranchName;
    }

    public String getInviteUserCreditDepartmentName() {
        return inviteUserCreditDepartmentName;
    }

    public void setInviteUserCreditDepartmentName(String inviteUserCreditDepartmentName) {
        this.inviteUserCreditDepartmentName = inviteUserCreditDepartmentName;
    }

    public String getRecommendAttrSelf() {
        return recommendAttrSelf;
    }

    public void setRecommendAttrSelf(String recommendAttrSelf) {
        this.recommendAttrSelf = recommendAttrSelf;
    }

    public String getRegionNameSelf() {
        return regionNameSelf;
    }

    public void setRegionNameSelf(String regionNameSelf) {
        this.regionNameSelf = regionNameSelf;
    }

    public String getBranchNameSelf() {
        return branchNameSelf;
    }

    public void setBranchNameSelf(String branchNameSelf) {
        this.branchNameSelf = branchNameSelf;
    }

    public String getDepartmentNameSelf() {
        return departmentNameSelf;
    }

    public void setDepartmentNameSelf(String departmentNameSelf) {
        this.departmentNameSelf = departmentNameSelf;
    }

    public String getRecommendAttrCreditSelf() {
        return recommendAttrCreditSelf;
    }

    public void setRecommendAttrCreditSelf(String recommendAttrCreditSelf) {
        this.recommendAttrCreditSelf = recommendAttrCreditSelf;
    }

    public String getRegionNameCreditSelf() {
        return regionNameCreditSelf;
    }

    public void setRegionNameCreditSelf(String regionNameCreditSelf) {
        this.regionNameCreditSelf = regionNameCreditSelf;
    }

    public String getBranchNameCreditSelf() {
        return branchNameCreditSelf;
    }

    public void setBranchNameCreditSelf(String branchNameCreditSelf) {
        this.branchNameCreditSelf = branchNameCreditSelf;
    }

    public String getDepartmentNameCreditSelf() {
        return departmentNameCreditSelf;
    }

    public void setDepartmentNameCreditSelf(String departmentNameCreditSelf) {
        this.departmentNameCreditSelf = departmentNameCreditSelf;
    }

    public String getSumCreditCapital() {
        return sumCreditCapital;
    }

    public void setSumCreditCapital(String sumCreditCapital) {
        this.sumCreditCapital = sumCreditCapital;
    }

    public String getSumCreditCapitalPrice() {
        return sumCreditCapitalPrice;
    }

    public void setSumCreditCapitalPrice(String sumCreditCapitalPrice) {
        this.sumCreditCapitalPrice = sumCreditCapitalPrice;
    }

    public String getSumCreditPrice() {
        return sumCreditPrice;
    }

    public void setSumCreditPrice(String sumCreditPrice) {
        this.sumCreditPrice = sumCreditPrice;
    }

    public String getSumCreditCapitalAssigned() {
        return sumCreditCapitalAssigned;
    }

    public void setSumCreditCapitalAssigned(String sumCreditCapitalAssigned) {
        this.sumCreditCapitalAssigned = sumCreditCapitalAssigned;
    }

    public String getSumAssignCapital() {
        return sumAssignCapital;
    }

    public void setSumAssignCapital(String sumAssignCapital) {
        this.sumAssignCapital = sumAssignCapital;
    }

    public String getSumAssignCapitalPrice() {
        return sumAssignCapitalPrice;
    }

    public void setSumAssignCapitalPrice(String sumAssignCapitalPrice) {
        this.sumAssignCapitalPrice = sumAssignCapitalPrice;
    }

    public String getSumAssignPrice() {
        return sumAssignPrice;
    }

    public void setSumAssignPrice(String sumAssignPrice) {
        this.sumAssignPrice = sumAssignPrice;
    }

    public String getSumAssignInterestAdvance() {
        return sumAssignInterestAdvance;
    }

    public void setSumAssignInterestAdvance(String sumAssignInterestAdvance) {
        this.sumAssignInterestAdvance = sumAssignInterestAdvance;
    }

    public String getSumCreditFee() {
        return sumCreditFee;
    }

    public void setSumCreditFee(String sumCreditFee) {
        this.sumCreditFee = sumCreditFee;
    }

    public String getSumAssignPay() {
        return sumAssignPay;
    }

    public void setSumAssignPay(String sumAssignPay) {
        this.sumAssignPay = sumAssignPay;
    }
}
