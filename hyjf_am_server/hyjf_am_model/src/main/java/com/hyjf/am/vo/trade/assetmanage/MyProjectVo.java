package com.hyjf.am.vo.trade.assetmanage;

import java.io.Serializable;

/**
 * @author pangchegnchao
 */
public class MyProjectVo implements Serializable{

    /** 序列化ID */
    private static final long serialVersionUID = 5496018681332748227L;
    /** 计划的状态 */
    private String type = "";
    /** 项目id */
    private String borrowNid = "";
    /** 项目名称 */
    private String borrowName = "";

    /**标的第一项 */
    private String borrowTheFirst = "";
    /**标的第一项描述 */
    private String borrowTheFirstDesc = "";
    /**标的第二项 */
    private String borrowTheSecond = "";
    /**标的第二项描述 */
    private String borrowTheSecondDesc = "";
    /**标的第三项 */
    private String borrowTheThird = "";
    /**标的第三项描述*/
    private String borrowTheThirdDesc = "";
    /** 项目详情url */
    private String borrowUrl = "";

    /** 标签：无，加息券，体验金 */
    private String label = "";

    /** 是否可转让 0代表没有 1代表有 */
    private String isDisplay = "";

    /** url：转让的H5页面url */
    private String url = "";

    private String statusName = "";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getBorrowTheFirst() {
        return borrowTheFirst;
    }

    public void setBorrowTheFirst(String borrowTheFirst) {
        this.borrowTheFirst = borrowTheFirst;
    }

    public String getBorrowTheFirstDesc() {
        return borrowTheFirstDesc;
    }

    public void setBorrowTheFirstDesc(String borrowTheFirstDesc) {
        this.borrowTheFirstDesc = borrowTheFirstDesc;
    }

    public String getBorrowTheSecond() {
        return borrowTheSecond;
    }

    public void setBorrowTheSecond(String borrowTheSecond) {
        this.borrowTheSecond = borrowTheSecond;
    }

    public String getBorrowTheSecondDesc() {
        return borrowTheSecondDesc;
    }

    public void setBorrowTheSecondDesc(String borrowTheSecondDesc) {
        this.borrowTheSecondDesc = borrowTheSecondDesc;
    }

    public String getBorrowTheThird() {
        return borrowTheThird;
    }

    public void setBorrowTheThird(String borrowTheThird) {
        this.borrowTheThird = borrowTheThird;
    }

    public String getBorrowTheThirdDesc() {
        return borrowTheThirdDesc;
    }

    public void setBorrowTheThirdDesc(String borrowTheThirdDesc) {
        this.borrowTheThirdDesc = borrowTheThirdDesc;
    }

    public String getBorrowUrl() {
        return borrowUrl;
    }

    public void setBorrowUrl(String borrowUrl) {
        this.borrowUrl = borrowUrl;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(String isDisplay) {
        this.isDisplay = isDisplay;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
