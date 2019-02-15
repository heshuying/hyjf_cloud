package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class DebtDeleteLog implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 项目编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 项目标题
     *
     * @mbggenerated
     */
    private String borrowName;

    /**
     * 借款人人名
     *
     * @mbggenerated
     */
    private String username;

    /**
     * 借贷总金额
     *
     * @mbggenerated
     */
    private String account;

    /**
     * 已借到的金额
     *
     * @mbggenerated
     */
    private String borrowAccountYes;

    /**
     * 剩余金额
     *
     * @mbggenerated
     */
    private String borrowAccountWait;

    /**
     * 借贷的完成率
     *
     * @mbggenerated
     */
    private String borrowAccountScale;

    /**
     * 还款方式
     *
     * @mbggenerated
     */
    private String borrowStyle;

    /**
     * 名称
     *
     * @mbggenerated
     */
    private String borrowStyleName;

    /**
     * 0汇保贷 1汇典贷 2汇小贷 3汇车贷 4新手标
     *
     * @mbggenerated
     */
    private Integer projectType;

    /**
     * 名称
     *
     * @mbggenerated
     */
    private String projectTypeName;

    /**
     * 借款期限
     *
     * @mbggenerated
     */
    private String borrowPeriod;

    /**
     * 借款利率
     *
     * @mbggenerated
     */
    private String borrowApr;

    /**
     * 项目状态
     *
     * @mbggenerated
     */
    private String status;

    /**
     * 满标时间
     *
     * @mbggenerated
     */
    private String borrowFullTime;

    /**
     * 放款完成时间
     *
     * @mbggenerated
     */
    private String recoverLastTime;

    /**
     * 保证金
     *
     * @mbggenerated
     */
    private Long bailNum;

    /**
     * 操作人
     *
     * @mbggenerated
     */
    private Integer operaterUid;

    /**
     * 操作人名称
     *
     * @mbggenerated
     */
    private String operaterUser;

    /**
     * 操作时间
     *
     * @mbggenerated
     */
    private Integer operaterTime;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}