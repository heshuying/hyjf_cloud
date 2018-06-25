package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class DebtDeleteLog implements Serializable {
    private Integer id;

    private String borrowNid;

    private String borrowName;

    private String username;

    private String account;

    private String borrowAccountYes;

    private String borrowAccountWait;

    private String borrowAccountScale;

    private String borrowStyle;

    private String borrowStyleName;

    private Integer projectType;

    private String projectTypeName;

    private String borrowPeriod;

    private String borrowApr;

    private String status;

    private String addtime;

    private String borrowFullTime;

    private String recoverLastTime;

    private Long bailNum;

    private Integer operaterUid;

    private String operaterUser;

    private Integer operaterTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName == null ? null : borrowName.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getBorrowAccountYes() {
        return borrowAccountYes;
    }

    public void setBorrowAccountYes(String borrowAccountYes) {
        this.borrowAccountYes = borrowAccountYes == null ? null : borrowAccountYes.trim();
    }

    public String getBorrowAccountWait() {
        return borrowAccountWait;
    }

    public void setBorrowAccountWait(String borrowAccountWait) {
        this.borrowAccountWait = borrowAccountWait == null ? null : borrowAccountWait.trim();
    }

    public String getBorrowAccountScale() {
        return borrowAccountScale;
    }

    public void setBorrowAccountScale(String borrowAccountScale) {
        this.borrowAccountScale = borrowAccountScale == null ? null : borrowAccountScale.trim();
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle == null ? null : borrowStyle.trim();
    }

    public String getBorrowStyleName() {
        return borrowStyleName;
    }

    public void setBorrowStyleName(String borrowStyleName) {
        this.borrowStyleName = borrowStyleName == null ? null : borrowStyleName.trim();
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName == null ? null : projectTypeName.trim();
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod == null ? null : borrowPeriod.trim();
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr == null ? null : borrowApr.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? null : addtime.trim();
    }

    public String getBorrowFullTime() {
        return borrowFullTime;
    }

    public void setBorrowFullTime(String borrowFullTime) {
        this.borrowFullTime = borrowFullTime == null ? null : borrowFullTime.trim();
    }

    public String getRecoverLastTime() {
        return recoverLastTime;
    }

    public void setRecoverLastTime(String recoverLastTime) {
        this.recoverLastTime = recoverLastTime == null ? null : recoverLastTime.trim();
    }

    public Long getBailNum() {
        return bailNum;
    }

    public void setBailNum(Long bailNum) {
        this.bailNum = bailNum;
    }

    public Integer getOperaterUid() {
        return operaterUid;
    }

    public void setOperaterUid(Integer operaterUid) {
        this.operaterUid = operaterUid;
    }

    public String getOperaterUser() {
        return operaterUser;
    }

    public void setOperaterUser(String operaterUser) {
        this.operaterUser = operaterUser == null ? null : operaterUser.trim();
    }

    public Integer getOperaterTime() {
        return operaterTime;
    }

    public void setOperaterTime(Integer operaterTime) {
        this.operaterTime = operaterTime;
    }
}