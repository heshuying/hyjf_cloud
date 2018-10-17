package com.hyjf.cs.trade.bean.app;

import java.io.Serializable;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/11
 * @Description: app3.0.9 首页推荐项目显示用实体
 */
public class AppHomePageRecommendProject implements Serializable {

    /**
     * 标题 "推荐产品"
     */
    private String title;
    /**
     * 标签文案 "优质资产"
     */
    private String tag;
    /**
     * 项目id
     */
    private String borrowNid;
    /**
     * 项目状态
     */
    private String status;
    /**
     * 项目详情URL
     */
    private String borrowUrl;
    /**
     * 项目名称
     */
    private String borrowName;
    /**
     * 项目类型
     */
    private String borrowType;
    /**
     * 历史年回报率
     */
    private String borrowApr;
    /**
     * 项目期限/计划锁定期限: 项目期限30天
     */
    private String borrowPeriod;
    /**
     * 项目剩余100,000.00 / 开放额度1,000,011.00 / xxx起投，最高投资yyy
     */
    private String borrowDesc;
    /**
     * 立即投资、立即加入
     */
    private String buttonText;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBorrowUrl() {
        return borrowUrl;
    }

    public void setBorrowUrl(String borrowUrl) {
        this.borrowUrl = borrowUrl;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(String borrowType) {
        this.borrowType = borrowType;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getBorrowDesc() {
        return borrowDesc;
    }

    public void setBorrowDesc(String borrowDesc) {
        this.borrowDesc = borrowDesc;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }
}
