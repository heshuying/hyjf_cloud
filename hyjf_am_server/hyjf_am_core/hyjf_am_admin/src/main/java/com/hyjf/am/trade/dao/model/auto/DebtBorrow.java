package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DebtBorrow implements Serializable {
    private Integer id;

    /**
     * 用户名称
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 项目申请人
     *
     * @mbggenerated
     */
    private String applicant;

    /**
     * 担保机构用户名
     *
     * @mbggenerated
     */
    private String repayOrgName;

    /**
     * 是否可用担保机构还款(0:否,1:是)
     *
     * @mbggenerated
     */
    private Integer isRepayOrgFlag;

    /**
     * 担保机构用户ID
     *
     * @mbggenerated
     */
    private Integer repayOrgUserId;

    /**
     * 标题
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 状态
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 排序
     *
     * @mbggenerated
     */
    private Integer order;

    private String borrowPic;

    /**
     * 点击次数
     *
     * @mbggenerated
     */
    private Integer hits;

    private Integer commentCount;

    /**
     * 缩略图
     *
     * @mbggenerated
     */
    private String litpic;

    /**
     * 属性
     *
     * @mbggenerated
     */
    private String flag;

    /**
     * 1房贷2车贷
     *
     * @mbggenerated
     */
    private String type;

    private String viewType;

    /**
     * create ip
     *
     * @mbggenerated
     */
    private String addIp;

    /**
     * 冻结的额度
     *
     * @mbggenerated
     */
    private BigDecimal amountAccount;

    /**
     * 额度类型
     *
     * @mbggenerated
     */
    private String amountType;

    private Integer cashStatus;

    /**
     * 借贷总金额
     *
     * @mbggenerated
     */
    private BigDecimal account;

    private Integer otherWebStatus;

    /**
     * 借款类型
     *
     * @mbggenerated
     */
    private String borrowType;

    /**
     * 借款密码
     *
     * @mbggenerated
     */
    private String borrowPassword;

    /**
     * 借款种类
     *
     * @mbggenerated
     */
    private String borrowFlag;

    /**
     * 是否可以进行借款
     *
     * @mbggenerated
     */
    private Integer borrowStatus;

    /**
     * 满标审核状态
     *
     * @mbggenerated
     */
    private Integer borrowFullStatus;

    /**
     * 借款的识别名
     *
     * @mbggenerated
     */
    private String borrowNid;

    private Integer borrowPreNid;

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
     * 用途
     *
     * @mbggenerated
     */
    private String borrowUse;

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
     * 流转标的期数
     *
     * @mbggenerated
     */
    private Integer borrowPeriodRoam;

    private Integer borrowDay;

    /**
     * 借款利率
     *
     * @mbggenerated
     */
    private BigDecimal borrowApr;

    /**
     * 借款冻结金额
     *
     * @mbggenerated
     */
    private BigDecimal borrowFrostAccount;

    /**
     * 冻结资金比例
     *
     * @mbggenerated
     */
    private String borrowFrostScale;

    /**
     * 秒标复审冻结
     *
     * @mbggenerated
     */
    private BigDecimal borrowFrostSecond;

    /**
     * 借款有效时间
     *
     * @mbggenerated
     */
    private Integer borrowValidTime;

    /**
     * 借款成功时间
     *
     * @mbggenerated
     */
    private Integer borrowSuccessTime;

    /**
     * 借款到期时间
     *
     * @mbggenerated
     */
    private String borrowEndTime;

    /**
     * 是否部分借款
     *
     * @mbggenerated
     */
    private Integer borrowPartStatus;

    /**
     * 撤销用户
     *
     * @mbggenerated
     */
    private Integer cancelUserid;

    /**
     * 是否撤销
     *
     * @mbggenerated
     */
    private Integer cancelStatus;

    /**
     * 撤回时间
     *
     * @mbggenerated
     */
    private String cancelTime;

    /**
     * 撤销理由
     *
     * @mbggenerated
     */
    private String cancelRemark;

    /**
     * 撤销管理备注
     *
     * @mbggenerated
     */
    private String cancelContents;

    /**
     * 最小的投资额
     *
     * @mbggenerated
     */
    private Integer tenderAccountMin;

    /**
     * 最大的投资额
     *
     * @mbggenerated
     */
    private Integer tenderAccountMax;

    /**
     * 投标的次数
     *
     * @mbggenerated
     */
    private Integer tenderTimes;

    /**
     * 最后投资时间
     *
     * @mbggenerated
     */
    private String tenderLastTime;

    /**
     * 是否提前还款
     *
     * @mbggenerated
     */
    private Integer repayAdvanceStatus;

    /**
     * 提前还款时间
     *
     * @mbggenerated
     */
    private String repayAdvanceTime;

    /**
     * 提前还款阶段
     *
     * @mbggenerated
     */
    private Integer repayAdvanceStep;

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
     * 还款的次数
     *
     * @mbggenerated
     */
    private Integer repayAccountTimes;

    /**
     * 每月还款金额
     *
     * @mbggenerated
     */
    private Integer repayMonthAccount;

    /**
     * 最后还款时间
     *
     * @mbggenerated
     */
    private String repayLastTime;

    /**
     * 每次还款的时间
     *
     * @mbggenerated
     */
    private String repayEachTime;

    /**
     * 下一笔还款时间
     *
     * @mbggenerated
     */
    private Integer repayNextTime;

    /**
     * 下一笔还款金额
     *
     * @mbggenerated
     */
    private BigDecimal repayNextAccount;

    /**
     * 还款次数
     *
     * @mbggenerated
     */
    private Integer repayTimes;

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
     * 提前还款费用
     *
     * @mbggenerated
     */
    private BigDecimal repayFeeAdvance;

    /**
     * 逾期还款费用
     *
     * @mbggenerated
     */
    private BigDecimal repayFeeLate;

    /**
     * 逾期利息
     *
     * @mbggenerated
     */
    private BigDecimal lateInterest;

    /**
     * 逾期催缴费
     *
     * @mbggenerated
     */
    private BigDecimal lateForfeit;

    /**
     * 是否是担保
     *
     * @mbggenerated
     */
    private Integer vouchStatus;

    private Integer vouchAdvanceStatus;

    /**
     * 担保人担保状态
     *
     * @mbggenerated
     */
    private Integer vouchUserStatus;

    /**
     * 担保人列表
     *
     * @mbggenerated
     */
    private String vouchUsers;

    /**
     * 总担保的金额
     *
     * @mbggenerated
     */
    private BigDecimal vouchAccount;

    /**
     * 已担保的金额
     *
     * @mbggenerated
     */
    private BigDecimal vouchAccountYes;

    private BigDecimal vouchAccountWait;

    /**
     * 已担保的比例
     *
     * @mbggenerated
     */
    private Long vouchAccountScale;

    /**
     * 担保次数
     *
     * @mbggenerated
     */
    private Integer vouchTimes;

    /**
     * 是否设置担保奖励
     *
     * @mbggenerated
     */
    private Integer vouchAwardStatus;

    /**
     * 担保比例
     *
     * @mbggenerated
     */
    private BigDecimal vouchAwardScale;

    /**
     * 总付出的担保奖励
     *
     * @mbggenerated
     */
    private BigDecimal vouchAwardAccount;

    private String voucherName;

    private String voucherLianxi;

    private String voucherAtt;

    private String vouchjgName;

    private String vouchjgLianxi;

    private String vouchjgJs;

    private String vouchjgXy;

    private Integer fastStatus;

    private Integer vouchstatus;

    /**
     * 1圈内;0全见
     *
     * @mbggenerated
     */
    private Integer groupStatus;

    /**
     * 圈子编号
     *
     * @mbggenerated
     */
    private Integer groupId;

    /**
     * 是否奖励
     *
     * @mbggenerated
     */
    private Integer awardStatus;

    /**
     * 投资失败是否也奖励
     *
     * @mbggenerated
     */
    private Integer awardFalse;

    /**
     * 奖励金额
     *
     * @mbggenerated
     */
    private BigDecimal awardAccount;

    /**
     * 按比例奖励
     *
     * @mbggenerated
     */
    private BigDecimal awardScale;

    /**
     * 投标奖励总额
     *
     * @mbggenerated
     */
    private BigDecimal awardAccountAll;

    /**
     * 公开我的帐户资金情况
     *
     * @mbggenerated
     */
    private Integer openAccount;

    /**
     * 公开我的借款资金情况
     *
     * @mbggenerated
     */
    private Integer openBorrow;

    /**
     * 公开我的投标资金情况
     *
     * @mbggenerated
     */
    private Integer openTender;

    /**
     * 公开我的信用额度情况
     *
     * @mbggenerated
     */
    private Integer openCredit;

    /**
     * 是否可以评论
     *
     * @mbggenerated
     */
    private Integer commentStaus;

    /**
     * 评论次数
     *
     * @mbggenerated
     */
    private Integer commentTimes;

    /**
     * 可评论的用户
     *
     * @mbggenerated
     */
    private String commentUsertype;

    private String borrowPawnApp;

    private String borrowPawnAppUrl;

    private String borrowPawnAuth;

    private String borrowPawnAuthUrl;

    private String borrowPawnFormalities;

    private String borrowPawnFormalitiesUrl;

    private String borrowPawnType;

    private String borrowPawnTime;

    private String borrowPawnValue;

    private String borrowPawnXin;

    /**
     * 置顶时间
     *
     * @mbggenerated
     */
    private String orderTop;

    /**
     * 审核人
     *
     * @mbggenerated
     */
    private String verifyUserid;

    /**
     * 审核时间
     *
     * @mbggenerated
     */
    private String verifyTime;

    private String verifyRemark;

    /**
     * 审核备注
     *
     * @mbggenerated
     */
    private String verifyContents;

    private Integer verifyStatus;

    /**
     * 审核人
     *
     * @mbggenerated
     */
    private String reverifyUserid;

    /**
     * 审核时间
     *
     * @mbggenerated
     */
    private String reverifyTime;

    private String reverifyRemark;

    private Integer reverifyStatus;

    /**
     * 审核复审标注
     *
     * @mbggenerated
     */
    private String reverifyContents;

    /**
     *  发标上传图片
     *
     * @mbggenerated
     */
    private String upfilesId;

    private String xmupfilesId;

    private String dyupfilesId;

    /**
     * 担保方式0抵押+担保  1抵押 2担保
     *
     * @mbggenerated
     */
    private Integer guaranteeType;

    /**
     * 0汇保贷 1汇典贷 2汇小贷 3汇车贷 4新手标
     *
     * @mbggenerated
     */
    private Integer projectType;

    /**
     * 定时发标
     *
     * @mbggenerated
     */
    private Integer ontime;

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
     * 可投资平台_PC
     *
     * @mbggenerated
     */
    private String canTransactionPc;

    /**
     * 可投资平台_微网站
     *
     * @mbggenerated
     */
    private String canTransactionWei;

    /**
     * 可投资平台_IOS
     *
     * @mbggenerated
     */
    private String canTransactionIos;

    /**
     * 可投资平台_Android
     *
     * @mbggenerated
     */
    private String canTransactionAndroid;

    /**
     * 运营标签
     *
     * @mbggenerated
     */
    private String operationLabel;

    /**
     * 借款人信息 借款类型 1：公司 2：个人
     *
     * @mbggenerated
     */
    private Integer companyOrPersonal;

    /**
     * 融资服务费
     *
     * @mbggenerated
     */
    private String borrowManager;

    /**
     * 账户管理费
     *
     * @mbggenerated
     */
    private String borrowService;

    /**
     * 账户管理费率下线
     *
     * @mbggenerated
     */
    private String borrowManagerScaleEnd;

    /**
     * 满标时间
     *
     * @mbggenerated
     */
    private Integer borrowFullTime;

    /**
     * 最后一笔的放款完成时间
     *
     * @mbggenerated
     */
    private Integer recoverLastTime;

    /**
     * 资产包编号
     *
     * @mbggenerated
     */
    private String consumeId;

    /**
     * 初审通过时间
     *
     * @mbggenerated
     */
    private Integer verifyOverTime;

    /**
     * 借款人人名
     *
     * @mbggenerated
     */
    private String borrowUserName;

    /**
     * 售价预估    
     *
     * @mbggenerated
     */
    private String disposalPriceEstimate;

    /**
     * 处置周期    
     *
     * @mbggenerated
     */
    private String disposalPeriod;

    /**
     * 处置渠道    
     *
     * @mbggenerated
     */
    private String disposalChannel;

    /**
     * 处置结果预案
     *
     * @mbggenerated
     */
    private String disposalResult;

    /**
     * 备注说明    
     *
     * @mbggenerated
     */
    private String disposalNote;

    /**
     * 项目名称    
     *
     * @mbggenerated
     */
    private String disposalProjectName;

    /**
     * 项目类型    
     *
     * @mbggenerated
     */
    private String disposalProjectType;

    /**
     * 所在地区    
     *
     * @mbggenerated
     */
    private String disposalArea;

    /**
     * 预估价值    
     *
     * @mbggenerated
     */
    private String disposalPredictiveValue;

    /**
     * 权属类别    
     *
     * @mbggenerated
     */
    private String disposalOwnershipCategory;

    /**
     * 资产成因    
     *
     * @mbggenerated
     */
    private String disposalAssetOrigin;

    /**
     * 附件信息    
     *
     * @mbggenerated
     */
    private String disposalAttachmentInfo;

    /**
     * 递增金额
     *
     * @mbggenerated
     */
    private Long borrowIncreaseMoney;

    /**
     * 优惠券
     *
     * @mbggenerated
     */
    private Integer borrowInterestCoupon;

    /**
     * 体验金
     *
     * @mbggenerated
     */
    private Integer borrowTasteMoney;

    /**
     * 标的是否已录入银行托管 0未托管 1已托管 王坤添加
     *
     * @mbggenerated
     */
    private Integer bankInputFlag;

    /**
     * 预约开始时间
     *
     * @mbggenerated
     */
    private Integer bookingBeginTime;

    /**
     * 预约截止时间
     *
     * @mbggenerated
     */
    private Integer bookingEndTime;

    /**
     * 预约状态 0初始 1预约中 2预约结束
     *
     * @mbggenerated
     */
    private Integer bookingStatus;

    /**
     * 预约标等待预约金额
     *
     * @mbggenerated
     */
    private BigDecimal borrowAccountWaitAppoint;

    /**
     * 预约进度
     *
     * @mbggenerated
     */
    private BigDecimal borrowAccountScaleAppoint;

    /**
     * 是否已有计划关联:0:否,1:是
     *
     * @mbggenerated
     */
    private Integer borrowPlanSelected;

    /**
     * 前台还款扣除的金额
     *
     * @mbggenerated
     */
    private BigDecimal borrowRepayWebAdvance;

    /**
     * 借款人评级
     *
     * @mbggenerated
     */
    private String borrowLevel;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant == null ? null : applicant.trim();
    }

    public String getRepayOrgName() {
        return repayOrgName;
    }

    public void setRepayOrgName(String repayOrgName) {
        this.repayOrgName = repayOrgName == null ? null : repayOrgName.trim();
    }

    public Integer getIsRepayOrgFlag() {
        return isRepayOrgFlag;
    }

    public void setIsRepayOrgFlag(Integer isRepayOrgFlag) {
        this.isRepayOrgFlag = isRepayOrgFlag;
    }

    public Integer getRepayOrgUserId() {
        return repayOrgUserId;
    }

    public void setRepayOrgUserId(Integer repayOrgUserId) {
        this.repayOrgUserId = repayOrgUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getBorrowPic() {
        return borrowPic;
    }

    public void setBorrowPic(String borrowPic) {
        this.borrowPic = borrowPic == null ? null : borrowPic.trim();
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getLitpic() {
        return litpic;
    }

    public void setLitpic(String litpic) {
        this.litpic = litpic == null ? null : litpic.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType == null ? null : viewType.trim();
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp == null ? null : addIp.trim();
    }

    public BigDecimal getAmountAccount() {
        return amountAccount;
    }

    public void setAmountAccount(BigDecimal amountAccount) {
        this.amountAccount = amountAccount;
    }

    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType == null ? null : amountType.trim();
    }

    public Integer getCashStatus() {
        return cashStatus;
    }

    public void setCashStatus(Integer cashStatus) {
        this.cashStatus = cashStatus;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public Integer getOtherWebStatus() {
        return otherWebStatus;
    }

    public void setOtherWebStatus(Integer otherWebStatus) {
        this.otherWebStatus = otherWebStatus;
    }

    public String getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(String borrowType) {
        this.borrowType = borrowType == null ? null : borrowType.trim();
    }

    public String getBorrowPassword() {
        return borrowPassword;
    }

    public void setBorrowPassword(String borrowPassword) {
        this.borrowPassword = borrowPassword == null ? null : borrowPassword.trim();
    }

    public String getBorrowFlag() {
        return borrowFlag;
    }

    public void setBorrowFlag(String borrowFlag) {
        this.borrowFlag = borrowFlag == null ? null : borrowFlag.trim();
    }

    public Integer getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(Integer borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public Integer getBorrowFullStatus() {
        return borrowFullStatus;
    }

    public void setBorrowFullStatus(Integer borrowFullStatus) {
        this.borrowFullStatus = borrowFullStatus;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public Integer getBorrowPreNid() {
        return borrowPreNid;
    }

    public void setBorrowPreNid(Integer borrowPreNid) {
        this.borrowPreNid = borrowPreNid;
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

    public String getBorrowUse() {
        return borrowUse;
    }

    public void setBorrowUse(String borrowUse) {
        this.borrowUse = borrowUse == null ? null : borrowUse.trim();
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

    public Integer getBorrowPeriodRoam() {
        return borrowPeriodRoam;
    }

    public void setBorrowPeriodRoam(Integer borrowPeriodRoam) {
        this.borrowPeriodRoam = borrowPeriodRoam;
    }

    public Integer getBorrowDay() {
        return borrowDay;
    }

    public void setBorrowDay(Integer borrowDay) {
        this.borrowDay = borrowDay;
    }

    public BigDecimal getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(BigDecimal borrowApr) {
        this.borrowApr = borrowApr;
    }

    public BigDecimal getBorrowFrostAccount() {
        return borrowFrostAccount;
    }

    public void setBorrowFrostAccount(BigDecimal borrowFrostAccount) {
        this.borrowFrostAccount = borrowFrostAccount;
    }

    public String getBorrowFrostScale() {
        return borrowFrostScale;
    }

    public void setBorrowFrostScale(String borrowFrostScale) {
        this.borrowFrostScale = borrowFrostScale == null ? null : borrowFrostScale.trim();
    }

    public BigDecimal getBorrowFrostSecond() {
        return borrowFrostSecond;
    }

    public void setBorrowFrostSecond(BigDecimal borrowFrostSecond) {
        this.borrowFrostSecond = borrowFrostSecond;
    }

    public Integer getBorrowValidTime() {
        return borrowValidTime;
    }

    public void setBorrowValidTime(Integer borrowValidTime) {
        this.borrowValidTime = borrowValidTime;
    }

    public Integer getBorrowSuccessTime() {
        return borrowSuccessTime;
    }

    public void setBorrowSuccessTime(Integer borrowSuccessTime) {
        this.borrowSuccessTime = borrowSuccessTime;
    }

    public String getBorrowEndTime() {
        return borrowEndTime;
    }

    public void setBorrowEndTime(String borrowEndTime) {
        this.borrowEndTime = borrowEndTime == null ? null : borrowEndTime.trim();
    }

    public Integer getBorrowPartStatus() {
        return borrowPartStatus;
    }

    public void setBorrowPartStatus(Integer borrowPartStatus) {
        this.borrowPartStatus = borrowPartStatus;
    }

    public Integer getCancelUserid() {
        return cancelUserid;
    }

    public void setCancelUserid(Integer cancelUserid) {
        this.cancelUserid = cancelUserid;
    }

    public Integer getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(Integer cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime == null ? null : cancelTime.trim();
    }

    public String getCancelRemark() {
        return cancelRemark;
    }

    public void setCancelRemark(String cancelRemark) {
        this.cancelRemark = cancelRemark == null ? null : cancelRemark.trim();
    }

    public String getCancelContents() {
        return cancelContents;
    }

    public void setCancelContents(String cancelContents) {
        this.cancelContents = cancelContents == null ? null : cancelContents.trim();
    }

    public Integer getTenderAccountMin() {
        return tenderAccountMin;
    }

    public void setTenderAccountMin(Integer tenderAccountMin) {
        this.tenderAccountMin = tenderAccountMin;
    }

    public Integer getTenderAccountMax() {
        return tenderAccountMax;
    }

    public void setTenderAccountMax(Integer tenderAccountMax) {
        this.tenderAccountMax = tenderAccountMax;
    }

    public Integer getTenderTimes() {
        return tenderTimes;
    }

    public void setTenderTimes(Integer tenderTimes) {
        this.tenderTimes = tenderTimes;
    }

    public String getTenderLastTime() {
        return tenderLastTime;
    }

    public void setTenderLastTime(String tenderLastTime) {
        this.tenderLastTime = tenderLastTime == null ? null : tenderLastTime.trim();
    }

    public Integer getRepayAdvanceStatus() {
        return repayAdvanceStatus;
    }

    public void setRepayAdvanceStatus(Integer repayAdvanceStatus) {
        this.repayAdvanceStatus = repayAdvanceStatus;
    }

    public String getRepayAdvanceTime() {
        return repayAdvanceTime;
    }

    public void setRepayAdvanceTime(String repayAdvanceTime) {
        this.repayAdvanceTime = repayAdvanceTime == null ? null : repayAdvanceTime.trim();
    }

    public Integer getRepayAdvanceStep() {
        return repayAdvanceStep;
    }

    public void setRepayAdvanceStep(Integer repayAdvanceStep) {
        this.repayAdvanceStep = repayAdvanceStep;
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

    public Integer getRepayAccountTimes() {
        return repayAccountTimes;
    }

    public void setRepayAccountTimes(Integer repayAccountTimes) {
        this.repayAccountTimes = repayAccountTimes;
    }

    public Integer getRepayMonthAccount() {
        return repayMonthAccount;
    }

    public void setRepayMonthAccount(Integer repayMonthAccount) {
        this.repayMonthAccount = repayMonthAccount;
    }

    public String getRepayLastTime() {
        return repayLastTime;
    }

    public void setRepayLastTime(String repayLastTime) {
        this.repayLastTime = repayLastTime == null ? null : repayLastTime.trim();
    }

    public String getRepayEachTime() {
        return repayEachTime;
    }

    public void setRepayEachTime(String repayEachTime) {
        this.repayEachTime = repayEachTime == null ? null : repayEachTime.trim();
    }

    public Integer getRepayNextTime() {
        return repayNextTime;
    }

    public void setRepayNextTime(Integer repayNextTime) {
        this.repayNextTime = repayNextTime;
    }

    public BigDecimal getRepayNextAccount() {
        return repayNextAccount;
    }

    public void setRepayNextAccount(BigDecimal repayNextAccount) {
        this.repayNextAccount = repayNextAccount;
    }

    public Integer getRepayTimes() {
        return repayTimes;
    }

    public void setRepayTimes(Integer repayTimes) {
        this.repayTimes = repayTimes;
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

    public BigDecimal getRepayFeeAdvance() {
        return repayFeeAdvance;
    }

    public void setRepayFeeAdvance(BigDecimal repayFeeAdvance) {
        this.repayFeeAdvance = repayFeeAdvance;
    }

    public BigDecimal getRepayFeeLate() {
        return repayFeeLate;
    }

    public void setRepayFeeLate(BigDecimal repayFeeLate) {
        this.repayFeeLate = repayFeeLate;
    }

    public BigDecimal getLateInterest() {
        return lateInterest;
    }

    public void setLateInterest(BigDecimal lateInterest) {
        this.lateInterest = lateInterest;
    }

    public BigDecimal getLateForfeit() {
        return lateForfeit;
    }

    public void setLateForfeit(BigDecimal lateForfeit) {
        this.lateForfeit = lateForfeit;
    }

    public Integer getVouchStatus() {
        return vouchStatus;
    }

    public void setVouchStatus(Integer vouchStatus) {
        this.vouchStatus = vouchStatus;
    }

    public Integer getVouchAdvanceStatus() {
        return vouchAdvanceStatus;
    }

    public void setVouchAdvanceStatus(Integer vouchAdvanceStatus) {
        this.vouchAdvanceStatus = vouchAdvanceStatus;
    }

    public Integer getVouchUserStatus() {
        return vouchUserStatus;
    }

    public void setVouchUserStatus(Integer vouchUserStatus) {
        this.vouchUserStatus = vouchUserStatus;
    }

    public String getVouchUsers() {
        return vouchUsers;
    }

    public void setVouchUsers(String vouchUsers) {
        this.vouchUsers = vouchUsers == null ? null : vouchUsers.trim();
    }

    public BigDecimal getVouchAccount() {
        return vouchAccount;
    }

    public void setVouchAccount(BigDecimal vouchAccount) {
        this.vouchAccount = vouchAccount;
    }

    public BigDecimal getVouchAccountYes() {
        return vouchAccountYes;
    }

    public void setVouchAccountYes(BigDecimal vouchAccountYes) {
        this.vouchAccountYes = vouchAccountYes;
    }

    public BigDecimal getVouchAccountWait() {
        return vouchAccountWait;
    }

    public void setVouchAccountWait(BigDecimal vouchAccountWait) {
        this.vouchAccountWait = vouchAccountWait;
    }

    public Long getVouchAccountScale() {
        return vouchAccountScale;
    }

    public void setVouchAccountScale(Long vouchAccountScale) {
        this.vouchAccountScale = vouchAccountScale;
    }

    public Integer getVouchTimes() {
        return vouchTimes;
    }

    public void setVouchTimes(Integer vouchTimes) {
        this.vouchTimes = vouchTimes;
    }

    public Integer getVouchAwardStatus() {
        return vouchAwardStatus;
    }

    public void setVouchAwardStatus(Integer vouchAwardStatus) {
        this.vouchAwardStatus = vouchAwardStatus;
    }

    public BigDecimal getVouchAwardScale() {
        return vouchAwardScale;
    }

    public void setVouchAwardScale(BigDecimal vouchAwardScale) {
        this.vouchAwardScale = vouchAwardScale;
    }

    public BigDecimal getVouchAwardAccount() {
        return vouchAwardAccount;
    }

    public void setVouchAwardAccount(BigDecimal vouchAwardAccount) {
        this.vouchAwardAccount = vouchAwardAccount;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName == null ? null : voucherName.trim();
    }

    public String getVoucherLianxi() {
        return voucherLianxi;
    }

    public void setVoucherLianxi(String voucherLianxi) {
        this.voucherLianxi = voucherLianxi == null ? null : voucherLianxi.trim();
    }

    public String getVoucherAtt() {
        return voucherAtt;
    }

    public void setVoucherAtt(String voucherAtt) {
        this.voucherAtt = voucherAtt == null ? null : voucherAtt.trim();
    }

    public String getVouchjgName() {
        return vouchjgName;
    }

    public void setVouchjgName(String vouchjgName) {
        this.vouchjgName = vouchjgName == null ? null : vouchjgName.trim();
    }

    public String getVouchjgLianxi() {
        return vouchjgLianxi;
    }

    public void setVouchjgLianxi(String vouchjgLianxi) {
        this.vouchjgLianxi = vouchjgLianxi == null ? null : vouchjgLianxi.trim();
    }

    public String getVouchjgJs() {
        return vouchjgJs;
    }

    public void setVouchjgJs(String vouchjgJs) {
        this.vouchjgJs = vouchjgJs == null ? null : vouchjgJs.trim();
    }

    public String getVouchjgXy() {
        return vouchjgXy;
    }

    public void setVouchjgXy(String vouchjgXy) {
        this.vouchjgXy = vouchjgXy == null ? null : vouchjgXy.trim();
    }

    public Integer getFastStatus() {
        return fastStatus;
    }

    public void setFastStatus(Integer fastStatus) {
        this.fastStatus = fastStatus;
    }

    public Integer getVouchstatus() {
        return vouchstatus;
    }

    public void setVouchstatus(Integer vouchstatus) {
        this.vouchstatus = vouchstatus;
    }

    public Integer getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(Integer groupStatus) {
        this.groupStatus = groupStatus;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getAwardStatus() {
        return awardStatus;
    }

    public void setAwardStatus(Integer awardStatus) {
        this.awardStatus = awardStatus;
    }

    public Integer getAwardFalse() {
        return awardFalse;
    }

    public void setAwardFalse(Integer awardFalse) {
        this.awardFalse = awardFalse;
    }

    public BigDecimal getAwardAccount() {
        return awardAccount;
    }

    public void setAwardAccount(BigDecimal awardAccount) {
        this.awardAccount = awardAccount;
    }

    public BigDecimal getAwardScale() {
        return awardScale;
    }

    public void setAwardScale(BigDecimal awardScale) {
        this.awardScale = awardScale;
    }

    public BigDecimal getAwardAccountAll() {
        return awardAccountAll;
    }

    public void setAwardAccountAll(BigDecimal awardAccountAll) {
        this.awardAccountAll = awardAccountAll;
    }

    public Integer getOpenAccount() {
        return openAccount;
    }

    public void setOpenAccount(Integer openAccount) {
        this.openAccount = openAccount;
    }

    public Integer getOpenBorrow() {
        return openBorrow;
    }

    public void setOpenBorrow(Integer openBorrow) {
        this.openBorrow = openBorrow;
    }

    public Integer getOpenTender() {
        return openTender;
    }

    public void setOpenTender(Integer openTender) {
        this.openTender = openTender;
    }

    public Integer getOpenCredit() {
        return openCredit;
    }

    public void setOpenCredit(Integer openCredit) {
        this.openCredit = openCredit;
    }

    public Integer getCommentStaus() {
        return commentStaus;
    }

    public void setCommentStaus(Integer commentStaus) {
        this.commentStaus = commentStaus;
    }

    public Integer getCommentTimes() {
        return commentTimes;
    }

    public void setCommentTimes(Integer commentTimes) {
        this.commentTimes = commentTimes;
    }

    public String getCommentUsertype() {
        return commentUsertype;
    }

    public void setCommentUsertype(String commentUsertype) {
        this.commentUsertype = commentUsertype == null ? null : commentUsertype.trim();
    }

    public String getBorrowPawnApp() {
        return borrowPawnApp;
    }

    public void setBorrowPawnApp(String borrowPawnApp) {
        this.borrowPawnApp = borrowPawnApp == null ? null : borrowPawnApp.trim();
    }

    public String getBorrowPawnAppUrl() {
        return borrowPawnAppUrl;
    }

    public void setBorrowPawnAppUrl(String borrowPawnAppUrl) {
        this.borrowPawnAppUrl = borrowPawnAppUrl == null ? null : borrowPawnAppUrl.trim();
    }

    public String getBorrowPawnAuth() {
        return borrowPawnAuth;
    }

    public void setBorrowPawnAuth(String borrowPawnAuth) {
        this.borrowPawnAuth = borrowPawnAuth == null ? null : borrowPawnAuth.trim();
    }

    public String getBorrowPawnAuthUrl() {
        return borrowPawnAuthUrl;
    }

    public void setBorrowPawnAuthUrl(String borrowPawnAuthUrl) {
        this.borrowPawnAuthUrl = borrowPawnAuthUrl == null ? null : borrowPawnAuthUrl.trim();
    }

    public String getBorrowPawnFormalities() {
        return borrowPawnFormalities;
    }

    public void setBorrowPawnFormalities(String borrowPawnFormalities) {
        this.borrowPawnFormalities = borrowPawnFormalities == null ? null : borrowPawnFormalities.trim();
    }

    public String getBorrowPawnFormalitiesUrl() {
        return borrowPawnFormalitiesUrl;
    }

    public void setBorrowPawnFormalitiesUrl(String borrowPawnFormalitiesUrl) {
        this.borrowPawnFormalitiesUrl = borrowPawnFormalitiesUrl == null ? null : borrowPawnFormalitiesUrl.trim();
    }

    public String getBorrowPawnType() {
        return borrowPawnType;
    }

    public void setBorrowPawnType(String borrowPawnType) {
        this.borrowPawnType = borrowPawnType == null ? null : borrowPawnType.trim();
    }

    public String getBorrowPawnTime() {
        return borrowPawnTime;
    }

    public void setBorrowPawnTime(String borrowPawnTime) {
        this.borrowPawnTime = borrowPawnTime == null ? null : borrowPawnTime.trim();
    }

    public String getBorrowPawnValue() {
        return borrowPawnValue;
    }

    public void setBorrowPawnValue(String borrowPawnValue) {
        this.borrowPawnValue = borrowPawnValue == null ? null : borrowPawnValue.trim();
    }

    public String getBorrowPawnXin() {
        return borrowPawnXin;
    }

    public void setBorrowPawnXin(String borrowPawnXin) {
        this.borrowPawnXin = borrowPawnXin == null ? null : borrowPawnXin.trim();
    }

    public String getOrderTop() {
        return orderTop;
    }

    public void setOrderTop(String orderTop) {
        this.orderTop = orderTop == null ? null : orderTop.trim();
    }

    public String getVerifyUserid() {
        return verifyUserid;
    }

    public void setVerifyUserid(String verifyUserid) {
        this.verifyUserid = verifyUserid == null ? null : verifyUserid.trim();
    }

    public String getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(String verifyTime) {
        this.verifyTime = verifyTime == null ? null : verifyTime.trim();
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

    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public String getReverifyUserid() {
        return reverifyUserid;
    }

    public void setReverifyUserid(String reverifyUserid) {
        this.reverifyUserid = reverifyUserid == null ? null : reverifyUserid.trim();
    }

    public String getReverifyTime() {
        return reverifyTime;
    }

    public void setReverifyTime(String reverifyTime) {
        this.reverifyTime = reverifyTime == null ? null : reverifyTime.trim();
    }

    public String getReverifyRemark() {
        return reverifyRemark;
    }

    public void setReverifyRemark(String reverifyRemark) {
        this.reverifyRemark = reverifyRemark == null ? null : reverifyRemark.trim();
    }

    public Integer getReverifyStatus() {
        return reverifyStatus;
    }

    public void setReverifyStatus(Integer reverifyStatus) {
        this.reverifyStatus = reverifyStatus;
    }

    public String getReverifyContents() {
        return reverifyContents;
    }

    public void setReverifyContents(String reverifyContents) {
        this.reverifyContents = reverifyContents == null ? null : reverifyContents.trim();
    }

    public String getUpfilesId() {
        return upfilesId;
    }

    public void setUpfilesId(String upfilesId) {
        this.upfilesId = upfilesId == null ? null : upfilesId.trim();
    }

    public String getXmupfilesId() {
        return xmupfilesId;
    }

    public void setXmupfilesId(String xmupfilesId) {
        this.xmupfilesId = xmupfilesId == null ? null : xmupfilesId.trim();
    }

    public String getDyupfilesId() {
        return dyupfilesId;
    }

    public void setDyupfilesId(String dyupfilesId) {
        this.dyupfilesId = dyupfilesId == null ? null : dyupfilesId.trim();
    }

    public Integer getGuaranteeType() {
        return guaranteeType;
    }

    public void setGuaranteeType(Integer guaranteeType) {
        this.guaranteeType = guaranteeType;
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public Integer getOntime() {
        return ontime;
    }

    public void setOntime(Integer ontime) {
        this.ontime = ontime;
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

    public String getCanTransactionPc() {
        return canTransactionPc;
    }

    public void setCanTransactionPc(String canTransactionPc) {
        this.canTransactionPc = canTransactionPc == null ? null : canTransactionPc.trim();
    }

    public String getCanTransactionWei() {
        return canTransactionWei;
    }

    public void setCanTransactionWei(String canTransactionWei) {
        this.canTransactionWei = canTransactionWei == null ? null : canTransactionWei.trim();
    }

    public String getCanTransactionIos() {
        return canTransactionIos;
    }

    public void setCanTransactionIos(String canTransactionIos) {
        this.canTransactionIos = canTransactionIos == null ? null : canTransactionIos.trim();
    }

    public String getCanTransactionAndroid() {
        return canTransactionAndroid;
    }

    public void setCanTransactionAndroid(String canTransactionAndroid) {
        this.canTransactionAndroid = canTransactionAndroid == null ? null : canTransactionAndroid.trim();
    }

    public String getOperationLabel() {
        return operationLabel;
    }

    public void setOperationLabel(String operationLabel) {
        this.operationLabel = operationLabel == null ? null : operationLabel.trim();
    }

    public Integer getCompanyOrPersonal() {
        return companyOrPersonal;
    }

    public void setCompanyOrPersonal(Integer companyOrPersonal) {
        this.companyOrPersonal = companyOrPersonal;
    }

    public String getBorrowManager() {
        return borrowManager;
    }

    public void setBorrowManager(String borrowManager) {
        this.borrowManager = borrowManager == null ? null : borrowManager.trim();
    }

    public String getBorrowService() {
        return borrowService;
    }

    public void setBorrowService(String borrowService) {
        this.borrowService = borrowService == null ? null : borrowService.trim();
    }

    public String getBorrowManagerScaleEnd() {
        return borrowManagerScaleEnd;
    }

    public void setBorrowManagerScaleEnd(String borrowManagerScaleEnd) {
        this.borrowManagerScaleEnd = borrowManagerScaleEnd == null ? null : borrowManagerScaleEnd.trim();
    }

    public Integer getBorrowFullTime() {
        return borrowFullTime;
    }

    public void setBorrowFullTime(Integer borrowFullTime) {
        this.borrowFullTime = borrowFullTime;
    }

    public Integer getRecoverLastTime() {
        return recoverLastTime;
    }

    public void setRecoverLastTime(Integer recoverLastTime) {
        this.recoverLastTime = recoverLastTime;
    }

    public String getConsumeId() {
        return consumeId;
    }

    public void setConsumeId(String consumeId) {
        this.consumeId = consumeId == null ? null : consumeId.trim();
    }

    public Integer getVerifyOverTime() {
        return verifyOverTime;
    }

    public void setVerifyOverTime(Integer verifyOverTime) {
        this.verifyOverTime = verifyOverTime;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName == null ? null : borrowUserName.trim();
    }

    public String getDisposalPriceEstimate() {
        return disposalPriceEstimate;
    }

    public void setDisposalPriceEstimate(String disposalPriceEstimate) {
        this.disposalPriceEstimate = disposalPriceEstimate == null ? null : disposalPriceEstimate.trim();
    }

    public String getDisposalPeriod() {
        return disposalPeriod;
    }

    public void setDisposalPeriod(String disposalPeriod) {
        this.disposalPeriod = disposalPeriod == null ? null : disposalPeriod.trim();
    }

    public String getDisposalChannel() {
        return disposalChannel;
    }

    public void setDisposalChannel(String disposalChannel) {
        this.disposalChannel = disposalChannel == null ? null : disposalChannel.trim();
    }

    public String getDisposalResult() {
        return disposalResult;
    }

    public void setDisposalResult(String disposalResult) {
        this.disposalResult = disposalResult == null ? null : disposalResult.trim();
    }

    public String getDisposalNote() {
        return disposalNote;
    }

    public void setDisposalNote(String disposalNote) {
        this.disposalNote = disposalNote == null ? null : disposalNote.trim();
    }

    public String getDisposalProjectName() {
        return disposalProjectName;
    }

    public void setDisposalProjectName(String disposalProjectName) {
        this.disposalProjectName = disposalProjectName == null ? null : disposalProjectName.trim();
    }

    public String getDisposalProjectType() {
        return disposalProjectType;
    }

    public void setDisposalProjectType(String disposalProjectType) {
        this.disposalProjectType = disposalProjectType == null ? null : disposalProjectType.trim();
    }

    public String getDisposalArea() {
        return disposalArea;
    }

    public void setDisposalArea(String disposalArea) {
        this.disposalArea = disposalArea == null ? null : disposalArea.trim();
    }

    public String getDisposalPredictiveValue() {
        return disposalPredictiveValue;
    }

    public void setDisposalPredictiveValue(String disposalPredictiveValue) {
        this.disposalPredictiveValue = disposalPredictiveValue == null ? null : disposalPredictiveValue.trim();
    }

    public String getDisposalOwnershipCategory() {
        return disposalOwnershipCategory;
    }

    public void setDisposalOwnershipCategory(String disposalOwnershipCategory) {
        this.disposalOwnershipCategory = disposalOwnershipCategory == null ? null : disposalOwnershipCategory.trim();
    }

    public String getDisposalAssetOrigin() {
        return disposalAssetOrigin;
    }

    public void setDisposalAssetOrigin(String disposalAssetOrigin) {
        this.disposalAssetOrigin = disposalAssetOrigin == null ? null : disposalAssetOrigin.trim();
    }

    public String getDisposalAttachmentInfo() {
        return disposalAttachmentInfo;
    }

    public void setDisposalAttachmentInfo(String disposalAttachmentInfo) {
        this.disposalAttachmentInfo = disposalAttachmentInfo == null ? null : disposalAttachmentInfo.trim();
    }

    public Long getBorrowIncreaseMoney() {
        return borrowIncreaseMoney;
    }

    public void setBorrowIncreaseMoney(Long borrowIncreaseMoney) {
        this.borrowIncreaseMoney = borrowIncreaseMoney;
    }

    public Integer getBorrowInterestCoupon() {
        return borrowInterestCoupon;
    }

    public void setBorrowInterestCoupon(Integer borrowInterestCoupon) {
        this.borrowInterestCoupon = borrowInterestCoupon;
    }

    public Integer getBorrowTasteMoney() {
        return borrowTasteMoney;
    }

    public void setBorrowTasteMoney(Integer borrowTasteMoney) {
        this.borrowTasteMoney = borrowTasteMoney;
    }

    public Integer getBankInputFlag() {
        return bankInputFlag;
    }

    public void setBankInputFlag(Integer bankInputFlag) {
        this.bankInputFlag = bankInputFlag;
    }

    public Integer getBookingBeginTime() {
        return bookingBeginTime;
    }

    public void setBookingBeginTime(Integer bookingBeginTime) {
        this.bookingBeginTime = bookingBeginTime;
    }

    public Integer getBookingEndTime() {
        return bookingEndTime;
    }

    public void setBookingEndTime(Integer bookingEndTime) {
        this.bookingEndTime = bookingEndTime;
    }

    public Integer getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(Integer bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public BigDecimal getBorrowAccountWaitAppoint() {
        return borrowAccountWaitAppoint;
    }

    public void setBorrowAccountWaitAppoint(BigDecimal borrowAccountWaitAppoint) {
        this.borrowAccountWaitAppoint = borrowAccountWaitAppoint;
    }

    public BigDecimal getBorrowAccountScaleAppoint() {
        return borrowAccountScaleAppoint;
    }

    public void setBorrowAccountScaleAppoint(BigDecimal borrowAccountScaleAppoint) {
        this.borrowAccountScaleAppoint = borrowAccountScaleAppoint;
    }

    public Integer getBorrowPlanSelected() {
        return borrowPlanSelected;
    }

    public void setBorrowPlanSelected(Integer borrowPlanSelected) {
        this.borrowPlanSelected = borrowPlanSelected;
    }

    public BigDecimal getBorrowRepayWebAdvance() {
        return borrowRepayWebAdvance;
    }

    public void setBorrowRepayWebAdvance(BigDecimal borrowRepayWebAdvance) {
        this.borrowRepayWebAdvance = borrowRepayWebAdvance;
    }

    public String getBorrowLevel() {
        return borrowLevel;
    }

    public void setBorrowLevel(String borrowLevel) {
        this.borrowLevel = borrowLevel == null ? null : borrowLevel.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}