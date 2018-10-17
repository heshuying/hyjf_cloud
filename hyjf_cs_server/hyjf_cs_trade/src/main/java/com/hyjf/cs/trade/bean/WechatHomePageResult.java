/*
 * Copyright(c) 2012-2016 JD Pharma.Ltd. All Rights Reserved.
 */
package com.hyjf.cs.trade.bean;


import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.trade.WechatHomeProjectListVO;
import com.hyjf.cs.trade.bean.app.AppModuleBean;

import java.util.List;

/**
 * 获取微信首页VO对象
 *
 * @author zhangyk
 * @date 2018/7/23 16:24
 */
public class WechatHomePageResult extends BaseResultBean {

    private static final long serialVersionUID = 1L;

    private String warning;// 警告语(市场有风险投资需谨慎)

    private String totalAssets;// 资产总额

    private String availableBalance;// 可用余额(资产余额)

    private String accumulatedEarnings;// 累计收益

    private String totalInvestmentAmount;// 平台累计投资总额

    private String moduleTotal;// 对应下方的列表

    private List<AppModuleBean> moduleList; // 存放安全保障,关于我们(后台维护)

    private List<AppAdsCustomizeVO> picList;//页面顶端的轮播图

    private String picTotal;//集合中的条目数

    private Integer isOpenAccount;//是否开户

    private String adPicUrl;//立即注册上方的图片链接

    private String adClickPicUrl;//立即注册上方图片点击后的跳转

    private String adDesc;//立即注册位置显示的内容

    private Object userId;//用户id

    private Integer isSetPassword;//是否设置交易密码

    private Integer isEvaluationFlag;//是否已进行风险测评

    private Integer autoInvesStatus;//自动投标授权状态

    private Integer autoCreditStatus;//自动债转授权状态

    private Integer paymentAuthStatus;//服务费授权状态

    private Integer userStatus;//用户是否锁定,0未锁定,1锁定
    private boolean isCheckUserRole;//角色认证是否打开,true认证,false不认证

    private Integer paymentAuthOn;//缴费是否打开,true认证,false不认证

    private Integer invesAuthOn;//自动投资开关是否打开,true认证,false不认证
    private Integer creditAuthOn;//自动债转开关是否打开,true认证,false不认证

    private Integer roleId;//角色id

    private Integer currentPage;//当前页
    private Integer pageSize;//每页显示条数

    private String request;

    private int endPage;

    private List<WechatHomeProjectListVO> HomeProjectList;

    private List<WechatHomeProjectListVO> HomeXshProjectList;



    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getAdDesc() {
        return adDesc;
    }

    public void setAdDesc(String adDesc) {
        this.adDesc = adDesc;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(String totalAssets) {
        this.totalAssets = totalAssets;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getAccumulatedEarnings() {
        return accumulatedEarnings;
    }

    public void setAccumulatedEarnings(String accumulatedEarnings) {
        this.accumulatedEarnings = accumulatedEarnings;
    }

    public String getTotalInvestmentAmount() {
        return totalInvestmentAmount;
    }

    public void setTotalInvestmentAmount(String totalInvestmentAmount) {
        this.totalInvestmentAmount = totalInvestmentAmount;
    }

    public String getModuleTotal() {
        return moduleTotal;
    }

    public void setModuleTotal(String moduleTotal) {
        this.moduleTotal = moduleTotal;
    }


    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Integer getIsOpenAccount() {
        return isOpenAccount;
    }

    public void setIsOpenAccount(Integer isOpenAccount) {
        this.isOpenAccount = isOpenAccount;
    }

    public Integer getIsSetPassword() {
        return isSetPassword;
    }

    public void setIsSetPassword(Integer isSetPassword) {
        this.isSetPassword = isSetPassword;
    }

    public Integer getIsEvaluationFlag() {
        return isEvaluationFlag;
    }

    public void setIsEvaluationFlag(Integer isEvaluationFlag) {
        this.isEvaluationFlag = isEvaluationFlag;
    }

    public String getPicTotal() {
        return picTotal;
    }

    public void setPicTotal(String picTotal) {
        this.picTotal = picTotal;
    }

    public String getAdPicUrl() {
        return adPicUrl;
    }

    public void setAdPicUrl(String adPicUrl) {
        this.adPicUrl = adPicUrl;
    }

    public String getAdClickPicUrl() {
        return adClickPicUrl;
    }

    public void setAdClickPicUrl(String adClickPicUrl) {
        this.adClickPicUrl = adClickPicUrl;
    }

    public Integer getAutoInvesStatus() {
        return autoInvesStatus;
    }

    public void setAutoInvesStatus(Integer autoInvesStatus) {
        this.autoInvesStatus = autoInvesStatus;
    }

    public Integer getAutoCreditStatus() {
        return autoCreditStatus;
    }

    public void setAutoCreditStatus(Integer autoCreditStatus) {
        this.autoCreditStatus = autoCreditStatus;
    }

    public Integer getPaymentAuthStatus() {
        return paymentAuthStatus;
    }

    public void setPaymentAuthStatus(Integer paymentAuthStatus) {
        this.paymentAuthStatus = paymentAuthStatus;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }


    public List<AppModuleBean> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<AppModuleBean> moduleList) {
        this.moduleList = moduleList;
    }

    public List<AppAdsCustomizeVO> getPicList() {
        return picList;
    }

    public void setPicList(List<AppAdsCustomizeVO> picList) {
        this.picList = picList;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public List<WechatHomeProjectListVO> getHomeProjectList() {
        return HomeProjectList;
    }

    public void setHomeProjectList(List<WechatHomeProjectListVO> homeProjectList) {
        HomeProjectList = homeProjectList;
    }


    public List<WechatHomeProjectListVO> getHomeXshProjectList() {
        return HomeXshProjectList;
    }

    public void setHomeXshProjectList(List<WechatHomeProjectListVO> homeXshProjectList) {
        HomeXshProjectList = homeXshProjectList;
    }

    public boolean getIsCheckUserRole() {
        return isCheckUserRole;
    }

    public void setIsCheckUserRole(boolean checkUserRole) {
        isCheckUserRole = checkUserRole;
    }

    public Integer getPaymentAuthOn() {
        return paymentAuthOn;
    }

    public void setPaymentAuthOn(Integer paymentAuthOn) {
        this.paymentAuthOn = paymentAuthOn;
    }

    public Integer getInvesAuthOn() {
        return invesAuthOn;
    }

    public void setInvesAuthOn(Integer invesAuthOn) {
        this.invesAuthOn = invesAuthOn;
    }

    public Integer getCreditAuthOn() {
        return creditAuthOn;
    }

    public void setCreditAuthOn(Integer creditAuthOn) {
        this.creditAuthOn = creditAuthOn;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
