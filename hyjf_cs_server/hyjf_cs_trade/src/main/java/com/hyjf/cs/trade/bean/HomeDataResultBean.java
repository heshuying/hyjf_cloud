package com.hyjf.cs.trade.bean;

import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.trade.WebProjectListCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanCustomizeVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 首页数据实体bean
 * @author zhangyk
 * @date 2018/7/4 14:01
 */
public class HomeDataResultBean implements Serializable {

    /*是否登录*/
    private String loginFlag;

    /*优惠券数量*/
    private int couponCount;

    /*是否开户*/
    private String openFlag;

    /*用户收益*/
    private BigDecimal userInterest;

    /*欢迎语*/
    private String helloWord;

    /*欢迎标志*/
    private int helloFlag;

    /*投资总额*/
    private BigDecimal tenderSum;

    /*收益总额*/
    private BigDecimal interestSum;

    /*上线总年数*/
    private Integer yearSum;

    /*首页轮播图*/
    private List<AppAdsCustomizeVO> bannerList;

    /*公告*/
    private Object  noticeInfo;

    /*首页新手专区项目信息  只查第一条*/
    private List<WebProjectListCustomizeVO> newProjectList;

    /*首页散标专区项目信息 前四条*/
    private List<WebProjectListCustomizeVO> projectList;

    /*首页汇计划列表 前四条*/
    private List<HjhPlanCustomizeVO> hjhPlanList;

    /*首页公司动态 第一条特殊处理*/
    private ContentArticleVO companyArticle;

    /*首页公司动态 第234条*/
    private List<ContentArticleVO> companyDynamicsList;

    /*当前系统时间*/
    private String  nowTime;


    public String getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
    }

    public int getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(int couponCount) {
        this.couponCount = couponCount;
    }

    public String getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(String openFlag) {
        this.openFlag = openFlag;
    }

    public BigDecimal getUserInterest() {
        return userInterest;
    }

    public void setUserInterest(BigDecimal userInterest) {
        this.userInterest = userInterest;
    }

    public String getHelloWord() {
        return helloWord;
    }

    public void setHelloWord(String helloWord) {
        this.helloWord = helloWord;
    }

    public BigDecimal getTenderSum() {
        return tenderSum;
    }

    public void setTenderSum(BigDecimal tenderSum) {
        this.tenderSum = tenderSum;
    }

    public BigDecimal getInterestSum() {
        return interestSum;
    }

    public void setInterestSum(BigDecimal interestSum) {
        this.interestSum = interestSum;
    }

    public Integer getYearSum() {
        return yearSum;
    }

    public void setYearSum(Integer yearSum) {
        this.yearSum = yearSum;
    }

    public List<AppAdsCustomizeVO> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<AppAdsCustomizeVO> bannerList) {
        this.bannerList = bannerList;
    }

    public Object getNoticeInfo() {
        return noticeInfo;
    }

    public void setNoticeInfo(Object noticeInfo) {
        this.noticeInfo = noticeInfo;
    }

    public List<WebProjectListCustomizeVO> getNewProjectList() {
        return newProjectList;
    }

    public void setNewProjectList(List<WebProjectListCustomizeVO> newProjectList) {
        this.newProjectList = newProjectList;
    }

    public List<WebProjectListCustomizeVO> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<WebProjectListCustomizeVO> projectList) {
        this.projectList = projectList;
    }

    public List<HjhPlanCustomizeVO> getHjhPlanList() {
        return hjhPlanList;
    }

    public void setHjhPlanList(List<HjhPlanCustomizeVO> hjhPlanList) {
        this.hjhPlanList = hjhPlanList;
    }

    public ContentArticleVO getCompanyArticle() {
        return companyArticle;
    }

    public void setCompanyArticle(ContentArticleVO companyArticle) {
        this.companyArticle = companyArticle;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public int getHelloFlag() {
        return helloFlag;
    }

    public void setHelloFlag(int helloFlag) {
        this.helloFlag = helloFlag;
    }

    public List<ContentArticleVO> getCompanyDynamicsList() {
        return companyDynamicsList;
    }

    public void setCompanyDynamicsList(List<ContentArticleVO> companyDynamicsList) {
        this.companyDynamicsList = companyDynamicsList;
    }
}
