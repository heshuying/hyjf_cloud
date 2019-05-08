package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Borrow implements Serializable {
    private Integer id;

    /**
     * 借款的识别名
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 0汇保贷 1汇典贷 2汇小贷 3汇车贷 4新手标
     *
     * @mbggenerated
     */
    private Integer projectType;

    /**
     * 用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 借款用户名称
     *
     * @mbggenerated
     */
    private String borrowUserName;

    /**
     * 状态(0:备案中,1:初审中,2:投资中,3:复审中(满标),4:还款中,5:已还款,6:流标,7:受托,8:逾期中)
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 借贷总金额
     *
     * @mbggenerated
     */
    private BigDecimal account;

    /**
     * 借款有效时间
     *
     * @mbggenerated
     */
    private Integer borrowValidTime;

    /**
     * 还款方式
     *
     * @mbggenerated
     */
    private String borrowStyle;

    /**
     * 借款期限
     *
     * @mbggenerated
     */
    private Integer borrowPeriod;

    /**
     * 借款利率
     *
     * @mbggenerated
     */
    private BigDecimal borrowApr;

    /**
     * 标签ID
     *
     * @mbggenerated
     */
    private Integer labelId;

    /**
     * 是否展示(隐藏测试标用:0:显示,1:不显示)
     *
     * @mbggenerated
     */
    private Integer isShow;

    /**
     * 是否调用标的分配规则引擎 0:否,1:是
     *
     * @mbggenerated
     */
    private Integer isEngineUsed;

    /**
     * 是否是分期标 0:否,1:是
     *
     * @mbggenerated
     */
    private Integer isInstallment;

    /**
     * 天标/月标 0:天标,1:月标
     *
     * @mbggenerated
     */
    private Integer isMonth;

    /**
     * 银行备案状态
     *
     * @mbggenerated
     */
    private Integer registStatus;

    /**
     * 备案时间
     *
     * @mbggenerated
     */
    private Date registTime;

    /**
     * 备案用户userId
     *
     * @mbggenerated
     */
    private Integer registUserId;

    /**
     * 备案用户名
     *
     * @mbggenerated
     */
    private String registUserName;

    /**
     * 0未交保证金 1已交保证金 2暂不发布 3定时发标 4立即发标
     *
     * @mbggenerated
     */
    private Integer verifyStatus;

    /**
     * 初审通过时间
     *
     * @mbggenerated
     */
    private Integer verifyOverTime;

    /**
     * 初审时间（发标时间）
     *
     * @mbggenerated
     */
    private Integer verifyTime;

    /**
     * 审核人
     *
     * @mbggenerated
     */
    private String verifyUserid;

    /**
     * 初审用户名（admin后台用户）
     *
     * @mbggenerated
     */
    private String verifyUserName;

    private String verifyRemark;

    /**
     * 审核备注
     *
     * @mbggenerated
     */
    private String verifyContents;

    /**
     * 是否可以进行借款
     *
     * @mbggenerated
     */
    private Integer borrowStatus;

    /**
     * 定时发标
     *
     * @mbggenerated
     */
    private Integer ontime;

    /**
     * 借款到期时间
     *
     * @mbggenerated
     */
    private String borrowEndTime;

    /**
     * 计划nid
     *
     * @mbggenerated
     */
    private String planNid;

    /**
     * 满标审核状态
     *
     * @mbggenerated
     */
    private Integer borrowFullStatus;

    /**
     * 满标时间
     *
     * @mbggenerated
     */
    private Integer borrowFullTime;

    /**
     * 投标的次数
     *
     * @mbggenerated
     */
    private Integer tenderTimes;

    /**
     * 已借到的金额
     *
     * @mbggenerated
     */
    private BigDecimal borrowAccountYes;

    private BigDecimal borrowAccountWait;

    /**
     * 借贷的完成率
     *
     * @mbggenerated
     */
    private BigDecimal borrowAccountScale;

    /**
     * 账户管理费
     *
     * @mbggenerated
     */
    private String borrowService;

    /**
     * 0初始 1放款请求中 2放款请求成功 3放款校验成功 4放款校验失败 5放款失败 6放款成功 7放款请求失败 8放款部分成功
     *
     * @mbggenerated
     */
    private Integer reverifyStatus;

    /**
     * 复审时间
     *
     * @mbggenerated
     */
    private Integer reverifyTime;

    /**
     * 审核人
     *
     * @mbggenerated
     */
    private String reverifyUserid;

    /**
     * 复审用户名（admin后台用户）
     *
     * @mbggenerated
     */
    private String reverifyUserName;

    private String reverifyRemark;

    /**
     * 审核复审标注
     *
     * @mbggenerated
     */
    private String reverifyContents;

    /**
     * 最后一笔的放款完成时间
     *
     * @mbggenerated
     */
    private Integer recoverLastTime;

    /**
     * 最后还款时间
     *
     * @mbggenerated
     */
    private Integer repayLastTime;

    /**
     * 下一笔还款时间
     *
     * @mbggenerated
     */
    private Integer repayNextTime;

    /**
     * 0初始 1还款请求中 2还款请求成功 3还款校验成功 4还款校验失败 5还款失败 6还款成功 7还款请求失败 8还款部分成功
     *
     * @mbggenerated
     */
    private Integer repayStatus;

    /**
     * 是否已经还完
     *
     * @mbggenerated
     */
    private Integer repayFullStatus;

    /**
     * 正常还款费用
     *
     * @mbggenerated
     */
    private BigDecimal repayFeeNormal;

    /**
     * 应还款总额
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountAll;

    /**
     * 总还款利息
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountInterest;

    /**
     * 总还款本金
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountCapital;

    /**
     * 已还款总额
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountYes;

    /**
     * 已还款利息
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountInterestYes;

    /**
     * 已还款本金
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountCapitalYes;

    /**
     * 未还款总额
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountWait;

    /**
     * 未还款利息
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountInterestWait;

    /**
     * 未还款本金
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountCapitalWait;

    /**
     * 融资服务费
     *
     * @mbggenerated
     */
    private String borrowManager;

    /**
     * 服务费费率
     *
     * @mbggenerated
     */
    private String serviceFeeRate;

    /**
     * 管理费费率
     *
     * @mbggenerated
     */
    private String manageFeeRate;

    /**
     * 收益差率
     *
     * @mbggenerated
     */
    private String differentialRate;

    /**
     * 逾期利率(汇计划用)
     *
     * @mbggenerated
     */
    private String lateInterestRate;

    /**
     * 逾期免息天数(汇计划用)
     *
     * @mbggenerated
     */
    private Integer lateFreeDays;

    /**
     * 创建用户-添加标的人员（账户操作人）
     *
     * @mbggenerated
     */
    private String createUserName;

    /**
     * create ip
     *
     * @mbggenerated
     */
    private String addIp;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    private Date updatetime;

    /**
     * 产品加息标志位(0:不加息,1:加息)
     *
     * @mbggenerated
     */
    private Integer increaseInterestFlag;

    /**
     * 等额本息保证金的回滚方式 0：到期回滚 1：分期回滚 2：不回滚
     *
     * @mbggenerated
     */
    private Integer repayCapitalType;

    /**
     * 标的投资等级
     *
     * @mbggenerated
     */
    private String investLevel;

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

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName == null ? null : borrowUserName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public Integer getBorrowValidTime() {
        return borrowValidTime;
    }

    public void setBorrowValidTime(Integer borrowValidTime) {
        this.borrowValidTime = borrowValidTime;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle == null ? null : borrowStyle.trim();
    }

    public Integer getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(Integer borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public BigDecimal getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(BigDecimal borrowApr) {
        this.borrowApr = borrowApr;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getIsEngineUsed() {
        return isEngineUsed;
    }

    public void setIsEngineUsed(Integer isEngineUsed) {
        this.isEngineUsed = isEngineUsed;
    }

    public Integer getIsInstallment() {
        return isInstallment;
    }

    public void setIsInstallment(Integer isInstallment) {
        this.isInstallment = isInstallment;
    }

    public Integer getIsMonth() {
        return isMonth;
    }

    public void setIsMonth(Integer isMonth) {
        this.isMonth = isMonth;
    }

    public Integer getRegistStatus() {
        return registStatus;
    }

    public void setRegistStatus(Integer registStatus) {
        this.registStatus = registStatus;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    public Integer getRegistUserId() {
        return registUserId;
    }

    public void setRegistUserId(Integer registUserId) {
        this.registUserId = registUserId;
    }

    public String getRegistUserName() {
        return registUserName;
    }

    public void setRegistUserName(String registUserName) {
        this.registUserName = registUserName == null ? null : registUserName.trim();
    }

    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public Integer getVerifyOverTime() {
        return verifyOverTime;
    }

    public void setVerifyOverTime(Integer verifyOverTime) {
        this.verifyOverTime = verifyOverTime;
    }

    public Integer getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Integer verifyTime) {
        this.verifyTime = verifyTime;
    }

    public String getVerifyUserid() {
        return verifyUserid;
    }

    public void setVerifyUserid(String verifyUserid) {
        this.verifyUserid = verifyUserid == null ? null : verifyUserid.trim();
    }

    public String getVerifyUserName() {
        return verifyUserName;
    }

    public void setVerifyUserName(String verifyUserName) {
        this.verifyUserName = verifyUserName == null ? null : verifyUserName.trim();
    }

    public String getVerifyRemark() {
        return verifyRemark;
    }

    public void setVerifyRemark(String verifyRemark) {
        this.verifyRemark = verifyRemark == null ? null : verifyRemark.trim();
    }

    public String getVerifyContents() {
        return verifyContents;
    }

    public void setVerifyContents(String verifyContents) {
        this.verifyContents = verifyContents == null ? null : verifyContents.trim();
    }

    public Integer getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(Integer borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public Integer getOntime() {
        return ontime;
    }

    public void setOntime(Integer ontime) {
        this.ontime = ontime;
    }

    public String getBorrowEndTime() {
        return borrowEndTime;
    }

    public void setBorrowEndTime(String borrowEndTime) {
        this.borrowEndTime = borrowEndTime == null ? null : borrowEndTime.trim();
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid == null ? null : planNid.trim();
    }

    public Integer getBorrowFullStatus() {
        return borrowFullStatus;
    }

    public void setBorrowFullStatus(Integer borrowFullStatus) {
        this.borrowFullStatus = borrowFullStatus;
    }

    public Integer getBorrowFullTime() {
        return borrowFullTime;
    }

    public void setBorrowFullTime(Integer borrowFullTime) {
        this.borrowFullTime = borrowFullTime;
    }

    public Integer getTenderTimes() {
        return tenderTimes;
    }

    public void setTenderTimes(Integer tenderTimes) {
        this.tenderTimes = tenderTimes;
    }

    public BigDecimal getBorrowAccountYes() {
        return borrowAccountYes;
    }

    public void setBorrowAccountYes(BigDecimal borrowAccountYes) {
        this.borrowAccountYes = borrowAccountYes;
    }

    public BigDecimal getBorrowAccountWait() {
        return borrowAccountWait;
    }

    public void setBorrowAccountWait(BigDecimal borrowAccountWait) {
        this.borrowAccountWait = borrowAccountWait;
    }

    public BigDecimal getBorrowAccountScale() {
        return borrowAccountScale;
    }

    public void setBorrowAccountScale(BigDecimal borrowAccountScale) {
        this.borrowAccountScale = borrowAccountScale;
    }

    public String getBorrowService() {
        return borrowService;
    }

    public void setBorrowService(String borrowService) {
        this.borrowService = borrowService == null ? null : borrowService.trim();
    }

    public Integer getReverifyStatus() {
        return reverifyStatus;
    }

    public void setReverifyStatus(Integer reverifyStatus) {
        this.reverifyStatus = reverifyStatus;
    }

    public Integer getReverifyTime() {
        return reverifyTime;
    }

    public void setReverifyTime(Integer reverifyTime) {
        this.reverifyTime = reverifyTime;
    }

    public String getReverifyUserid() {
        return reverifyUserid;
    }

    public void setReverifyUserid(String reverifyUserid) {
        this.reverifyUserid = reverifyUserid == null ? null : reverifyUserid.trim();
    }

    public String getReverifyUserName() {
        return reverifyUserName;
    }

    public void setReverifyUserName(String reverifyUserName) {
        this.reverifyUserName = reverifyUserName == null ? null : reverifyUserName.trim();
    }

    public String getReverifyRemark() {
        return reverifyRemark;
    }

    public void setReverifyRemark(String reverifyRemark) {
        this.reverifyRemark = reverifyRemark == null ? null : reverifyRemark.trim();
    }

    public String getReverifyContents() {
        return reverifyContents;
    }

    public void setReverifyContents(String reverifyContents) {
        this.reverifyContents = reverifyContents == null ? null : reverifyContents.trim();
    }

    public Integer getRecoverLastTime() {
        return recoverLastTime;
    }

    public void setRecoverLastTime(Integer recoverLastTime) {
        this.recoverLastTime = recoverLastTime;
    }

    public Integer getRepayLastTime() {
        return repayLastTime;
    }

    public void setRepayLastTime(Integer repayLastTime) {
        this.repayLastTime = repayLastTime;
    }

    public Integer getRepayNextTime() {
        return repayNextTime;
    }

    public void setRepayNextTime(Integer repayNextTime) {
        this.repayNextTime = repayNextTime;
    }

    public Integer getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
    }

    public Integer getRepayFullStatus() {
        return repayFullStatus;
    }

    public void setRepayFullStatus(Integer repayFullStatus) {
        this.repayFullStatus = repayFullStatus;
    }

    public BigDecimal getRepayFeeNormal() {
        return repayFeeNormal;
    }

    public void setRepayFeeNormal(BigDecimal repayFeeNormal) {
        this.repayFeeNormal = repayFeeNormal;
    }

    public BigDecimal getRepayAccountAll() {
        return repayAccountAll;
    }

    public void setRepayAccountAll(BigDecimal repayAccountAll) {
        this.repayAccountAll = repayAccountAll;
    }

    public BigDecimal getRepayAccountInterest() {
        return repayAccountInterest;
    }

    public void setRepayAccountInterest(BigDecimal repayAccountInterest) {
        this.repayAccountInterest = repayAccountInterest;
    }

    public BigDecimal getRepayAccountCapital() {
        return repayAccountCapital;
    }

    public void setRepayAccountCapital(BigDecimal repayAccountCapital) {
        this.repayAccountCapital = repayAccountCapital;
    }

    public BigDecimal getRepayAccountYes() {
        return repayAccountYes;
    }

    public void setRepayAccountYes(BigDecimal repayAccountYes) {
        this.repayAccountYes = repayAccountYes;
    }

    public BigDecimal getRepayAccountInterestYes() {
        return repayAccountInterestYes;
    }

    public void setRepayAccountInterestYes(BigDecimal repayAccountInterestYes) {
        this.repayAccountInterestYes = repayAccountInterestYes;
    }

    public BigDecimal getRepayAccountCapitalYes() {
        return repayAccountCapitalYes;
    }

    public void setRepayAccountCapitalYes(BigDecimal repayAccountCapitalYes) {
        this.repayAccountCapitalYes = repayAccountCapitalYes;
    }

    public BigDecimal getRepayAccountWait() {
        return repayAccountWait;
    }

    public void setRepayAccountWait(BigDecimal repayAccountWait) {
        this.repayAccountWait = repayAccountWait;
    }

    public BigDecimal getRepayAccountInterestWait() {
        return repayAccountInterestWait;
    }

    public void setRepayAccountInterestWait(BigDecimal repayAccountInterestWait) {
        this.repayAccountInterestWait = repayAccountInterestWait;
    }

    public BigDecimal getRepayAccountCapitalWait() {
        return repayAccountCapitalWait;
    }

    public void setRepayAccountCapitalWait(BigDecimal repayAccountCapitalWait) {
        this.repayAccountCapitalWait = repayAccountCapitalWait;
    }

    public String getBorrowManager() {
        return borrowManager;
    }

    public void setBorrowManager(String borrowManager) {
        this.borrowManager = borrowManager == null ? null : borrowManager.trim();
    }

    public String getServiceFeeRate() {
        return serviceFeeRate;
    }

    public void setServiceFeeRate(String serviceFeeRate) {
        this.serviceFeeRate = serviceFeeRate == null ? null : serviceFeeRate.trim();
    }

    public String getManageFeeRate() {
        return manageFeeRate;
    }

    public void setManageFeeRate(String manageFeeRate) {
        this.manageFeeRate = manageFeeRate == null ? null : manageFeeRate.trim();
    }

    public String getDifferentialRate() {
        return differentialRate;
    }

    public void setDifferentialRate(String differentialRate) {
        this.differentialRate = differentialRate == null ? null : differentialRate.trim();
    }

    public String getLateInterestRate() {
        return lateInterestRate;
    }

    public void setLateInterestRate(String lateInterestRate) {
        this.lateInterestRate = lateInterestRate == null ? null : lateInterestRate.trim();
    }

    public Integer getLateFreeDays() {
        return lateFreeDays;
    }

    public void setLateFreeDays(Integer lateFreeDays) {
        this.lateFreeDays = lateFreeDays;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp == null ? null : addIp.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getIncreaseInterestFlag() {
        return increaseInterestFlag;
    }

    public void setIncreaseInterestFlag(Integer increaseInterestFlag) {
        this.increaseInterestFlag = increaseInterestFlag;
    }

    public Integer getRepayCapitalType() {
        return repayCapitalType;
    }

    public void setRepayCapitalType(Integer repayCapitalType) {
        this.repayCapitalType = repayCapitalType;
    }

    public String getInvestLevel() {
        return investLevel;
    }

    public void setInvestLevel(String investLevel) {
        this.investLevel = investLevel == null ? null : investLevel.trim();
    }
}