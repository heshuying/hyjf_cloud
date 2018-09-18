package com.hyjf.am.bean.admin;

import com.hyjf.am.vo.trade.borrow.BorrowCommonCarVO;
import com.hyjf.am.vo.trade.borrow.BorrowCommonCompanyAuthenVO;
import com.hyjf.am.vo.trade.borrow.BorrowCommonImageVO;
import com.hyjf.am.vo.trade.borrow.BorrowCommonNameAccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowCompanyAuthenVO;
import com.hyjf.am.vo.trade.borrow.BorrowHousesVO;
import com.hyjf.common.paginator.Paginator;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @package com.hyjf.admin.maintenance.AlllBorrowCustomize;
 * @author GOGTZ-Z
 * @date 2015/07/09 17:00
 * @version V1.0  
 */
public class BorrowCommonBean implements Serializable {

	/**
	 * serialVersionUID
	 */

	private static final long serialVersionUID = 387630498860089653L;
	
	private String pageUrl;
	
	private int isMonth;
	
	public int getIsMonth() {
		return isMonth;
	}

	public void setIsMonth(int isMonth) {
		this.isMonth = isMonth;
	}

	public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }


    //add by xiashuing 20171129 begin
	/**
	 * 定向标
	 */
	private String publishInstCode;
	//add by xiashuing 20171129 end


    // 达飞金融用
	/**
	 * 资产包编号
	 */
	private String consumeId;

	/**
	 * 资产包中“已通过”状态的借款人的个数
	 */
	private String userCount;

	/**
	 * 达飞金融的个数
	 */
	private String cunsumeCount;

	/**
	 * 达飞金融的个数错误
	 */
	private String cunsumeCountError;

	/**
	 * 检索条件 画面迁移标识
	 */
	private String moveFlag;

	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;

	/**
	 * 列表画面自定义标签上的用翻页对象：paginator
	 */
	private Paginator paginator;

	/**
	 * 检索条件 借款编号
	 */
	private String borrowNidSrch;

	/**
	 * 检索条件 借款标题
	 */
	private String borrowNameSrch;
	/**
	 * 检索条件 用户名
	 */
	private String usernameSrch;

	/**
	 * 检索条件 状态
	 */
	private String statusSrch;

	/**
	 * 检索条件 还款方式
	 */
	private String borrowStyleSrch;

	/**
	 * 检索条件 项目类型
	 */
	private String projectTypeSrch;

	/**
	 * 检索条件 时间开始
	 */
	private String timeStartSrch;

	/**
	 * 检索条件 时间结束
	 */
	private String timeEndSrch;

	/**
	 * 检索条件 是否交保证金
	 */
	private String isBailSrch;
	/**
	 * 借款编码
	 */
	private String borrowNid;

	/**
	 * 借款预编码
	 */
	private String borrowPreNid;
	/**
	 * 借款状态
	 */
	private String status;
	/**
	 * 是否拆标
	 */
	private String isChaibiao;

	/**
	 * 是否调用标的分配规则引擎 0:否 , 1：是
	 */
	private String isEngineUsed;

	/**
	 * 借款标题
	 */
	private String name;
	/**
	 * 借款用户
	 */
	private String username;
	/**
	 * 垫付机构用户名
	 */
	private String repayOrgName;

	/**
	 * 垫付机构用户ID
	 */
	private Integer repayOrgUserId;

	/**
	 * 是否需要垫付机构还款Flag
	 */
	private boolean isRepayOrgFlag;

	/**
	 * 借款金额
	 */
	private String account;
	/**
	 * 年利率
	 */
	private String borrowApr;
	/**
	 * 还款方式
	 */
	private String borrowStyle;
	/**
	 * 借款期限
	 */
	private String borrowPeriod;
	/**
	 * 最低投标金额
	 */
	private String tenderAccountMin;
	/**
	 * 最高投标金额
	 */
	private String tenderAccountMax;
	/**
	 * 有效时间
	 */
	private String borrowValidTime;
	/**
	 * 标的备案时间
	 */
	private String bankRegistDays;
	/**
	 * 借款用途
	 */
	private String borrowUse;
	/**
	 * 担保方式
	 */
	private String guaranteeType;
	/**
	 * 项目类型
	 */
	private Integer projectType;

	/**
	 * 资产来源
	 */
	private String instCode;

	/**
	 * 借款方式
	 */
	private String type;
	/**
	 * 初审意见
	 */
	private String verify;
	/**
	 * 发标方式
	 */
	private String verifyStatus;
	/**
	 * 初审备注
	 */
	private String verifyRemark;
	/**
	 * 担保机构
	 */
	private String borrowMeasuresInstit;
	/**
	 * 机构介绍
	 */
	private String borrowCompanyInstruction;
	/**
	 * 操作流程
	 */
	private String borrowOperatingProcess;
	/**
	 * 抵押物信息
	 */
	private String borrowMeasuresMort;
	/**
	 * 本息保障
	 */
	private String borrowMeasuresMea;
	/**
	 * 财务状况
	 */
	private String accountContents;
	/**
	 * 项目描述
	 */
	private String borrowContents;
	/**
	 * 融资服务费
	 */
	private String borrowServiceScale;
	/**
	 * 账户管理费率
	 */
	private String borrowManagerScale;

	/**
	 * 收益差率
	 */
	private String borrowReturnScale;

	/**
	 * 账户管理费率（下限制）
	 */
	private String borrowManagerScaleEnd;
	/**
	 * 定时发标
	 */
	private String ontime;
	/**
	 * 可投资平台_PC
	 */
	private String canTransactionPc;
	/**
	 * 可投资平台_微网站
	 */
	private String canTransactionWei;
	/**
	 * 可投资平台_IOS
	 */
	private String canTransactionIos;
	/**
	 * 可投资平台_Android
	 */
	private String canTransactionAndroid;
	/**
	 * 运营标签
	 */
	private String operationLabel;

	private List<BorrowCommonNameAccountVO> borrowCommonNameAccountList = new ArrayList<BorrowCommonNameAccountVO>();
	/**
	 * 公司还是个人
	 */
	private Integer companyOrPersonal;

	/**
	 * 售价预估
	 */
	private String disposalPriceEstimate;
	/**
	 * 处置周期
	 */
	private String disposalPeriod;

	/**
	 * 处置渠道
	 */
	private String disposalChannel;

	/**
	 * 处置结果预案
	 */
	private String disposalResult;

	/**
	 * 备注说明
	 */
	private String disposalNote;

	/**
	 * 项目名称
	 */
	private String disposalProjectName;

	/**
	 * 项目类型
	 */
	private String disposalProjectType;

	/**
	 * 所在地区
	 */
	private String disposalArea;

	/**
	 * 预估价值
	 */
	private String disposalPredictiveValue;

	/**
	 * 权属类别
	 */
	private String disposalOwnershipCategory;

	/**
	 * 资产成因
	 */
	private String disposalAssetOrigin;

	/**
	 * 附件信息
	 */
	private String disposalAttachmentInfo;

	// ----------------------借款人信息borrow maininfo------------------------
	/**
	 * 借款人姓名
	 */
	private String manname;
	/**
	 * 借款人性别
	 */
	private String sex;
	/**
	 * 借款人年龄
	 */
	private String old;
	/**
	 * 借款人岗位职业
	 */
	private String position;
	/**
	 * 借款人婚姻状况
	 */
	private String merry;
	/**
	 * 借款人省
	 */
	private String location_p;
	/**
	 * 借款人市
	 */
	private String location_c;
	/**
	 * 借款人行业
	 */
	private String industry;
	/**
	 * 借款人公司规模
	 */
	private String size;
	/**
	 * 借款人公司月营业额
	 */
	private String business;
	/**
	 * 现单位工作时间
	 */
	private String wtime;

	/**
	 * 授信额度
	 */
	private String userCredit;
	/**
	 * 借款人评级
	 */
	private String borrowLevel;
	
	/**
	 * 涉诉情况
	 */
	private String litigation;
	/**
	 * 身份证号
	 */
	private String cardNo;
	/**
	 * 户籍地
	 */
	private String domicile;
	/**
	 * 在平台逾期次数
	 */
	private String overdueTimes;
	/**
	 * 在平台逾期金额
	 */
	private String overdueAmount;

	/** * ------信批需求新增(个人)------ */
	/**
	 * 个人年收入:10万以内；10万以上
	 */
	private String annualIncome;

	/**
	 * 个人征信报告逾期情况:暂未提供；无；已处理
	 */
	private String overdueReport;

	/**
	 * 个人重大负债状况:无
	 */
	private String debtSituation;

	/**
	 * 个人其他平台借款情况:无
	 */
	private String otherBorrowed;

	/**
	 * 个人借款资金运用情况：0不正常,1正常
	 */
	private String isFunds;

	/**
	 * 个人借款人经营状况及财务状况：0不正常,1正常
	 */
	private String isManaged;

	/**
	 * 个人借款人还款能力变化情况：0不正常,1正常
	 */
	private String isAbility;

	/**
	 * 借款人逾期情况：0暂无,1有
	 */
	private String isOverdue;

	/**
	 * 借款人涉诉情况：0暂无,1有
	 */
	private String isComplaint;

	/**
	 * 借款人受行政处罚情况：0暂无,1有
	 */
	private String isPunished;
	/** * -------信批需求新增(个人)----- */

	/**
	 * 个贷审核信息 身份证 0未审核 1已审核
	 */
	private String isCard;
	/**
	 * 个贷审核信息 收入状况 0未审核 1已审核
	 */
	private String isIncome;
	/**
	 * 个贷审核信息 信用状况 0未审核 1已审核
	 */
	private String isCredit;
	/**
	 * 个贷审核信息 资产状况  0未审核 1已审核
	 */
	private String isAsset;
	/**
	 * 个贷审核信息  车辆状况  0未审核 1已审核
	 */
	private String isVehicle;
	/**
	 * 个贷审核信息 行驶证 0未审核 1已审核
	 */
	private String isDrivingLicense;
	/**
	 * 个贷审核信息 车辆登记证 0未审核 1已审核
	 */
	private String isVehicleRegistration;
	/**
	 * 个贷审核信息 婚姻状况 0未审核 1已审核
	 */
	private String isMerry;
	
	/**
	 * 个贷审核信息 工作状况  0未审核 1已审核
	 */
	private String isWork;
	/**
	 * 个贷审核信息 户口本 0未审核 1已审核
	 */
	private String isAccountBook;
	//2180705 add by nxl 添加借款人地址(互金需求)
	private String address;
	// ----------------------借款人------------------------

	// ----------------------车辆信息------------------------

	/**
	 * 车辆信息 列表
	 */
	private List<BorrowCommonCarVO> borrowCarinfoList = new ArrayList<BorrowCommonCarVO>();

	/**
	 * 车辆抵押
	 */
	private String typeCar;
	/**
	 * 房产抵押
	 */
	private String typeHouse;
	// ----------------------车辆信息------------------------
	// ----------------------企业用户信息 borrow users------------------------
	/**
	 * 企业名称
	 */
	private String comName;
	/**
	 * 所在地区 省
	 */
	private String comLocationProvince;
	/**
	 * 所在地区 市
	 */
	private String comLocationCity;
	/**
	 * 所在地区 区
	 */
	private String comLocationArea;
	/**
	 * 所属行业
	 */
	private String comUserIndustry;
	/**
	 * 注册资本
	 */
	private String comRegCaptial;
	/**
     * 注册资本-货币类型
     */
    private String currencyName;
	/**
	 * 涉诉情况
	 */
	private String comLitigation;
	/**
	 * 征信记录
	 */
	private String comCreReport;
	/**
	 * 授信额度
	 */
	private String comCredit;
	/**
	 * 员工人数
	 */
	private String comStaff;
	/**
	 * 企业注册时间
	 */
	private String comRegTime;
	/**
	 * 其他资料
	 */
	private String comOtherInfo;
	/**
	 * 统一社会信用代码
	 */
	private String comSocialCreditCode;
	/**
	 * 注册号
	 */
    private String comRegistCode;
	/**
	 * 法人
	 */
    private String comLegalPerson;
	/**
	 * 主营业务
	 */
    private String comMainBusiness;
	/**
	 * 在平台逾期次数
	 */
    private String comOverdueTimes;
	/**
	 * 在平台逾期金额
	 */
    private String comOverdueAmount;

	/** * --------信批需求新增(企业)---- */
    /**
     * 征信报告逾期情况:暂未提供；无；已处理
     */
    private String comOverdueReport;

    /**
     * 重大负债状况:无
     */
    private String comDebtSituation;

    /**
     * 其他平台借款情况:无
     */
    private String comOtherBorrowed;


    /**
     * 借款资金运用情况：0不正常,1正常
     */
    private String comIsFunds;

    /**
     * 借款人经营状况及财务状况：0不正常,1正常
     */
    private String comIsManaged;

    /**
     * 借款人还款能力变化情况：0不正常,1正常
     */
    private String comIsAbility;

    /**
     * 借款人逾期情况：0暂无,1有
     */
    private String comIsOverdue;

    /**
     * 借款人涉诉情况：0暂无,1有
     */
    private String comIsComplaint;

    /**
     * 借款人受行政处罚情况：0暂无,1有
     */
    private String comIsPunished;
    /** * --------信批需求新增(企业)---- */
	/**
	 * 企贷审核信息 企业证件 0未审核 1已审核
	 */
    private String comIsCertificate;
	/**
	 * 企贷审核信息 经营状况 0未审核 1已审核
	 */
    private String comIsOperation;
	/**
	 * 企贷审核信息 财务状况 0未审核 1已审核
	 */
    private String comIsFinance;
	/**
	 * 企贷审核信息 企业信用 0未审核 1已审核
	 */
    private String comIsEnterpriseCreidt;
	/**
	 * 企贷审核信息 法人信息 0未审核 1已审核
	 */
    private String comIsLegalPerson;
	/**
	 * 企贷审核信息 资产状况 0未审核 1已审核
	 */
    private String comIsAsset;
	/**
	 * 企贷审核信息 购销合同 0未审核 1已审核
	 */
    private String comIsPurchaseContract;
	/**
	 * 企贷审核信息 供销合同 0未审核 1已审核
	 */
    private String comIsSupplyContract;
	//20180705 add by nxl 添加企业组织机构代码和企业注册地(互金需求)
	private String corporateCode;
	private String registrationAddress;
	
	// ----------------------企业用户信息 borrow users------------------------
	// ----------------------房产信息------------------------
	/**
	 * 房产信息 列表
	 */
	private List<BorrowHousesVO> borrowHousesList = new ArrayList<BorrowHousesVO>();
	// ----------------------房产信息------------------------
	// ----------------------认证信息------------------------
	/**
	 * 认证信息 列表
	 */
	private List<BorrowCompanyAuthenVO> borrowCompanyAuthenList = new ArrayList<BorrowCompanyAuthenVO>();
	private List<BorrowCommonCompanyAuthenVO> borrowCommonCompanyAuthenList = new ArrayList<BorrowCommonCompanyAuthenVO>();
	// ----------------------认证信息------------------------

	// ----------------------项目资料------------------------
	/**
	 * 项目资料
	 */
	private List<BorrowCommonImageVO> borrowCommonImageList = new ArrayList<BorrowCommonImageVO>();
	// ----------------------项目资料------------------------

	// ----------------------风险缓释金添加------------------------
	/**
	 * 资产编号
	 */
	private String borrowAssetNumber;
	/**
	 * 项目来源
	 */
	private String borrowProjectSource;
	/**
	 * 起息时间
	 */
	private String borrowInterestTime;
	/**
	 * 到期时间
	 */
	private String borrowDueTime;
	/**
	 * 保障方式
	 */
	private String borrowSafeguardWay;
	/**
	 * 收益说明
	 */
	private String borrowIncomeDescription;
	/**
	 * 发行人
	 */
	private String borrowPublisher;
	/**
	 * 产品加息收益率
	 */
	private String borrowExtraYield;
	// ----------------------风险缓释金添加end------------------------

	/**
	 * 标题信息 错误(暂时没用)
	 */
	private String nameError;

	/**
	 * 图片信息 错误(暂时没用)
	 */
	private String imageError;

	/**
	 * 车辆信息 错误(暂时没用)
	 */
	private String carError;
	/**
	 * 房产信息 错误(暂时没用)
	 */
	private String housesError;
	/**
	 * 项目资料 错误(暂时没用)
	 */
	private String authenError;
	/**
	 * 协议期限
	 */
	private String contractPeriod;
	
	/**
	 * 项目标题
	 */
	private String projectName;
	
	/**
	 * 融资用途
	 */
	private String financePurpose;
	/**
	 * 月薪收入
	 */
	private String monthlyIncome;
	/**
	 * 还款来源
	 */
	private String payment;
	/**
	 * 第一还款来源
	 */
	private String firstPayment;
	/**
	 * 第二还款来源
	 */
	private String secondPayment;
	/**
	 * 费用说明
	 */
	private String costIntrodution;
	/**
	 * 财务状况
	 */
	private String fianceCondition;
	
	public Long getBorrowIncreaseMoney() {
		return borrowIncreaseMoney;
	}

	public void setBorrowIncreaseMoney(Long borrowIncreaseMoney) {
		this.borrowIncreaseMoney = borrowIncreaseMoney;
	}

	/**
	 * 备注
	 */
	private String remark;
	
	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
	 * 项目申请人
	 */
	private String applicant;

	/**
	 * 递增金额
	 */
	private Long borrowIncreaseMoney;

	/**
	 * 优惠券
	 */
	private Integer borrowInterestCoupon;

	/**
	 * 体验金
	 */
	private Integer borrowTasteMoney;

	/**
	 * 预约开始时间
	 */
	private String bookingBeginTime;

	/**
	 * 预约截止时间
	 */
	private String bookingEndTime;

	/**
	 * 受托支付标志 
	 */
	private String entrustedFlg;//0 否；1是
	/**
	 * 资产属性 1:抵押标 2:质押标 3:信用标 4:债权转让标 5:净值标
	 */
	private String assetAttributes;
	
	
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAssetAttributes() {
		return assetAttributes;
	}

	public void setAssetAttributes(String assetAttributes) {
		this.assetAttributes = assetAttributes;
	}


	/**
	 * 
	 */
	private String entrustedUsername;

	public Integer getBorrowTasteMoney() {
		return borrowTasteMoney;
	}

	public void setBorrowTasteMoney(Integer borrowTasteMoney) {
		this.borrowTasteMoney = borrowTasteMoney;
	}

	/**
	 * userCredit
	 * 
	 * @return the userCredit
	 */

	public String getUserCredit() {
		return userCredit;
	}

	/**
	 * @param userCredit
	 *            the userCredit to set
	 */

	public void setUserCredit(String userCredit) {
		this.userCredit = userCredit;
	}

	/**
	 * nameError
	 * 
	 * @return the nameError
	 */

	public String getNameError() {
		return nameError;
	}

	/**
	 * @param nameError
	 *            the nameError to set
	 */

	public void setNameError(String nameError) {
		this.nameError = nameError;
	}

	/**
	 * imageError
	 * 
	 * @return the imageError
	 */

	public String getImageError() {
		return imageError;
	}

	/**
	 * @param imageError
	 *            the imageError to set
	 */

	public void setImageError(String imageError) {
		this.imageError = imageError;
	}

	/**
	 * carError
	 * 
	 * @return the carError
	 */

	public String getCarError() {
		return carError;
	}

	/**
	 * @param carError
	 *            the carError to set
	 */

	public void setCarError(String carError) {
		this.carError = carError;
	}

	/**
	 * housesError
	 * 
	 * @return the housesError
	 */

	public String getHousesError() {
		return housesError;
	}

	/**
	 * @param housesError
	 *            the housesError to set
	 */

	public void setHousesError(String housesError) {
		this.housesError = housesError;
	}

	/**
	 * authenError
	 * 
	 * @return the authenError
	 */

	public String getAuthenError() {
		return authenError;
	}

	/**
	 * @param authenError
	 *            the authenError to set
	 */

	public void setAuthenError(String authenError) {
		this.authenError = authenError;
	}

	/**
	 * 图片
	 */
	private String borrowImageJson;
	/**
	 * 标题
	 */
	private String borrowNameJson;
	/**
	 * 车辆
	 */
	private String borrowCarJson;
	/**
	 * 房产
	 */
	private String borrowHousesJson;
	/**
	 * 认证
	 */
	private String borrowAuthenJson;

	/**
	 * borrowNameJson
	 * 
	 * @return the borrowNameJson
	 */

	public String getBorrowNameJson() {
		return borrowNameJson;
	}

	/**
	 * @param borrowNameJson
	 *            the borrowNameJson to set
	 */

	public void setBorrowNameJson(String borrowNameJson) {
		this.borrowNameJson = borrowNameJson;
	}

	/**
	 * borrowImageJson
	 * 
	 * @return the borrowImageJson
	 */

	public String getBorrowImageJson() {
		return borrowImageJson;
	}

	/**
	 * @param borrowImageJson
	 *            the borrowImageJson to set
	 */

	public void setBorrowImageJson(String borrowImageJson) {
		this.borrowImageJson = borrowImageJson;
	}

	/**
	 * borrowCarJson
	 * 
	 * @return the borrowCarJson
	 */

	public String getBorrowCarJson() {
		return borrowCarJson;
	}

	/**
	 * @param borrowCarJson
	 *            the borrowCarJson to set
	 */

	public void setBorrowCarJson(String borrowCarJson) {
		this.borrowCarJson = borrowCarJson;
	}

	/**
	 * borrowHousesJson
	 * 
	 * @return the borrowHousesJson
	 */

	public String getBorrowHousesJson() {
		return borrowHousesJson;
	}

	/**
	 * @param borrowHousesJson
	 *            the borrowHousesJson to set
	 */

	public void setBorrowHousesJson(String borrowHousesJson) {
		this.borrowHousesJson = borrowHousesJson;
	}

	/**
	 * borrowAuthenJson
	 * 
	 * @return the borrowAuthenJson
	 */

	public String getBorrowAuthenJson() {
		return borrowAuthenJson;
	}

	/**
	 * @param borrowAuthenJson
	 *            the borrowAuthenJson to set
	 */

	public void setBorrowAuthenJson(String borrowAuthenJson) {
		this.borrowAuthenJson = borrowAuthenJson;
	}

	/**
	 * accountContents
	 * 
	 * @return the accountContents
	 */

	public String getAccountContents() {
		return accountContents;
	}

	/**
	 * borrowCommonImageList
	 * 
	 * @return the borrowCommonImageList
	 */

	public List<BorrowCommonImageVO> getBorrowCommonImageList() {
		return borrowCommonImageList;
	}

	/**
	 * @param borrowCommonImageList
	 *            the borrowCommonImageList to set
	 */

	public void setBorrowCommonImageList(List<BorrowCommonImageVO> borrowCommonImageList) {
		this.borrowCommonImageList = borrowCommonImageList;
	}

	/**
	 * borrowCommonCompanyAuthenList
	 * 
	 * @return the borrowCommonCompanyAuthenList
	 */

	public List<BorrowCommonCompanyAuthenVO> getBorrowCommonCompanyAuthenList() {
		return borrowCommonCompanyAuthenList;
	}

	/**
	 * @param borrowCommonCompanyAuthenList
	 *            the borrowCommonCompanyAuthenList to set
	 */

	public void setBorrowCommonCompanyAuthenList(List<BorrowCommonCompanyAuthenVO> borrowCommonCompanyAuthenList) {
		this.borrowCommonCompanyAuthenList = borrowCommonCompanyAuthenList;
	}

	/**
	 * borrowHousesList
	 * 
	 * @return the borrowHousesList
	 */

	public List<BorrowHousesVO> getBorrowHousesList() {
		return borrowHousesList;
	}

	/**
	 * @param borrowHousesList
	 *            the borrowHousesList to set
	 */

	public void setBorrowHousesList(List<BorrowHousesVO> borrowHousesList) {
		this.borrowHousesList = borrowHousesList;
	}

	/**
	 * borrowCompanyAuthenList
	 * 
	 * @return the borrowCompanyAuthenList
	 */

	public List<BorrowCompanyAuthenVO> getBorrowCompanyAuthenList() {
		return borrowCompanyAuthenList;
	}

	/**
	 * @param borrowCompanyAuthenList
	 *            the borrowCompanyAuthenList to set
	 */

	public void setBorrowCompanyAuthenList(List<BorrowCompanyAuthenVO> borrowCompanyAuthenList) {
		this.borrowCompanyAuthenList = borrowCompanyAuthenList;
	}




	/**
	 * comRegTime
	 * 
	 * @return the comRegTime
	 */

	public String getComRegTime() {
		return comRegTime;
	}

	/**
	 * @param comRegTime
	 *            the comRegTime to set
	 */

	private String pageToken;

	public void setComRegTime(String comRegTime) {
		this.comRegTime = comRegTime;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getComLocationProvince() {
		return comLocationProvince;
	}

	public void setComLocationProvince(String comLocationProvince) {
		this.comLocationProvince = comLocationProvince;
	}

	public String getComLocationCity() {
		return comLocationCity;
	}

	public void setComLocationCity(String comLocationCity) {
		this.comLocationCity = comLocationCity;
	}

	public String getComLocationArea() {
		return comLocationArea;
	}

	public void setComLocationArea(String comLocationArea) {
		this.comLocationArea = comLocationArea;
	}

	public String getComUserIndustry() {
		return comUserIndustry;
	}

	public void setComUserIndustry(String comUserIndustry) {
		this.comUserIndustry = comUserIndustry;
	}

	public String getComRegCaptial() {
		return comRegCaptial;
	}

	public void setComRegCaptial(String comRegCaptial) {
		this.comRegCaptial = comRegCaptial;
	}

	public String getComLitigation() {
		return comLitigation;
	}

	public void setComLitigation(String comLitigation) {
		this.comLitigation = comLitigation;
	}

	public String getComCreReport() {
		return comCreReport;
	}

	public void setComCreReport(String comCreReport) {
		this.comCreReport = comCreReport;
	}

	public String getComCredit() {
		return comCredit;
	}

	public void setComCredit(String comCredit) {
		this.comCredit = comCredit;
	}

	public String getComStaff() {
		return comStaff;
	}

	public void setComStaff(String comStaff) {
		this.comStaff = comStaff;
	}

	public String getComOtherInfo() {
		return comOtherInfo;
	}

	public void setComOtherInfo(String comOtherInfo) {
		this.comOtherInfo = comOtherInfo;
	}

	public String getComSocialCreditCode() {
		return comSocialCreditCode;
	}

	public void setComSocialCreditCode(String comSocialCreditCode) {
		this.comSocialCreditCode = comSocialCreditCode;
	}

	public String getComRegistCode() {
		return comRegistCode;
	}

	public void setComRegistCode(String comRegistCode) {
		this.comRegistCode = comRegistCode;
	}

	public String getComMainBusiness() {
		return comMainBusiness;
	}

	public void setComMainBusiness(String comMainBusiness) {
		this.comMainBusiness = comMainBusiness;
	}

	public String getComOverdueTimes() {
		return comOverdueTimes;
	}

	public void setComOverdueTimes(String comOverdueTimes) {
		this.comOverdueTimes = comOverdueTimes;
	}

	public String getComOverdueAmount() {
		return comOverdueAmount;
	}

	public void setComOverdueAmount(String comOverdueAmount) {
		this.comOverdueAmount = comOverdueAmount;
	}

	public String getComIsCertificate() {
		return comIsCertificate;
	}

	public void setComIsCertificate(String comIsCertificate) {
		this.comIsCertificate = comIsCertificate;
	}

	public String getComIsOperation() {
		return comIsOperation;
	}

	public void setComIsOperation(String comIsOperation) {
		this.comIsOperation = comIsOperation;
	}

	public String getComIsFinance() {
		return comIsFinance;
	}

	public void setComIsFinance(String comIsFinance) {
		this.comIsFinance = comIsFinance;
	}

	public String getComIsEnterpriseCreidt() {
		return comIsEnterpriseCreidt;
	}

	public void setComIsEnterpriseCreidt(String comIsEnterpriseCreidt) {
		this.comIsEnterpriseCreidt = comIsEnterpriseCreidt;
	}

	public String getComIsLegalPerson() {
		return comIsLegalPerson;
	}

	public void setComIsLegalPerson(String comIsLegalPerson) {
		this.comIsLegalPerson = comIsLegalPerson;
	}

	public String getComIsAsset() {
		return comIsAsset;
	}

	public void setComIsAsset(String comIsAsset) {
		this.comIsAsset = comIsAsset;
	}

	public String getComIsPurchaseContract() {
		return comIsPurchaseContract;
	}

	public void setComIsPurchaseContract(String comIsPurchaseContract) {
		this.comIsPurchaseContract = comIsPurchaseContract;
	}

	public String getComIsSupplyContract() {
		return comIsSupplyContract;
	}

	public void setComIsSupplyContract(String comIsSupplyContract) {
		this.comIsSupplyContract = comIsSupplyContract;
	}

	/**
	 * manname
	 * 
	 * @return the manname
	 */

	public String getManname() {
		return manname;
	}

	/**
	 * @param manname
	 *            the manname to set
	 */

	public void setManname(String manname) {
		this.manname = manname;
	}

	/**
	 * sex
	 * 
	 * @return the sex
	 */

	public String getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */

	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * old
	 * 
	 * @return the old
	 */

	public String getOld() {
		return old;
	}

	/**
	 * @param old
	 *            the old to set
	 */

	public void setOld(String old) {
		this.old = old;
	}

	/**
	 * merry
	 * 
	 * @return the merry
	 */

	public String getMerry() {
		return merry;
	}

	/**
	 * @param merry
	 *            the merry to set
	 */

	public void setMerry(String merry) {
		this.merry = merry;
	}

	/**
	 * location_p
	 * 
	 * @return the location_p
	 */

	public String getLocation_p() {
		return location_p;
	}

	/**
	 * @param location_p
	 *            the location_p to set
	 */

	public void setLocation_p(String location_p) {
		this.location_p = location_p;
	}

	/**
	 * location_c
	 * 
	 * @return the location_c
	 */

	public String getLocation_c() {
		return location_c;
	}

	/**
	 * @param location_c
	 *            the location_c to set
	 */

	public void setLocation_c(String location_c) {
		this.location_c = location_c;
	}

	/**
	 * industry
	 * 
	 * @return the industry
	 */

	public String getIndustry() {
		return industry;
	}

	/**
	 * @param industry
	 *            the industry to set
	 */

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	/**
	 * size
	 * 
	 * @return the size
	 */

	public String getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */

	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * business
	 * 
	 * @return the business
	 */

	public String getBusiness() {
		return business;
	}

	/**
	 * @param business
	 *            the business to set
	 */

	public void setBusiness(String business) {
		this.business = business;
	}

	/**
	 * wtime
	 * 
	 * @return the wtime
	 */

	public String getWtime() {
		return wtime;
	}

	/**
	 * @param wtime
	 *            the wtime to set
	 */

	public void setWtime(String wtime) {
		this.wtime = wtime;
	}

	/**
	 * @param accountContents
	 *            the accountContents to set
	 */

	public void setAccountContents(String accountContents) {
		this.accountContents = accountContents;
	}

	/**
	 * borrowContents
	 * 
	 * @return the borrowContents
	 */

	public String getBorrowContents() {
		return borrowContents;
	}

	/**
	 * @param borrowContents
	 *            the borrowContents to set
	 */

	public void setBorrowContents(String borrowContents) {
		this.borrowContents = borrowContents;
	}

	/**
	 * borrowMeasuresInstit
	 * 
	 * @return the borrowMeasuresInstit
	 */

	public String getBorrowMeasuresInstit() {
		return borrowMeasuresInstit;
	}

	/**
	 * @param borrowMeasuresInstit
	 *            the borrowMeasuresInstit to set
	 */

	public void setBorrowMeasuresInstit(String borrowMeasuresInstit) {
		this.borrowMeasuresInstit = borrowMeasuresInstit;
	}

	/**
	 * borrowMeasuresMort
	 * 
	 * @return the borrowMeasuresMort
	 */

	public String getBorrowMeasuresMort() {
		return borrowMeasuresMort;
	}

	/**
	 * @param borrowMeasuresMort
	 *            the borrowMeasuresMort to set
	 */

	public void setBorrowMeasuresMort(String borrowMeasuresMort) {
		this.borrowMeasuresMort = borrowMeasuresMort;
	}

	/**
	 * borrowMeasuresMea
	 * 
	 * @return the borrowMeasuresMea
	 */

	public String getBorrowMeasuresMea() {
		return borrowMeasuresMea;
	}

	/**
	 * @param borrowMeasuresMea
	 *            the borrowMeasuresMea to set
	 */

	public void setBorrowMeasuresMea(String borrowMeasuresMea) {
		this.borrowMeasuresMea = borrowMeasuresMea;
	}

	/**
	 * guaranteeType
	 * 
	 * @return the guaranteeType
	 */

	public String getGuaranteeType() {
		return guaranteeType;
	}

	/**
	 * @param guaranteeType
	 *            the guaranteeType to set
	 */

	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}

	/**
	 * projectType
	 * 
	 * @return the projectType
	 */

	public Integer getProjectType() {
		return projectType;
	}

	/**
	 * @param projectType
	 *            the projectType to set
	 */

	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	/**
	 * type
	 * 
	 * @return the type
	 */

	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * tenderAccountMin
	 * 
	 * @return the tenderAccountMin
	 */

	public String getTenderAccountMin() {
		return tenderAccountMin;
	}

	/**
	 * @param tenderAccountMin
	 *            the tenderAccountMin to set
	 */

	public void setTenderAccountMin(String tenderAccountMin) {
		this.tenderAccountMin = tenderAccountMin;
	}

	/**
	 * tenderAccountMax
	 * 
	 * @return the tenderAccountMax
	 */

	public String getTenderAccountMax() {
		return tenderAccountMax;
	}

	/**
	 * @param tenderAccountMax
	 *            the tenderAccountMax to set
	 */

	public void setTenderAccountMax(String tenderAccountMax) {
		this.tenderAccountMax = tenderAccountMax;
	}

	/**
	 * borrowValidTime
	 * 
	 * @return the borrowValidTime
	 */

	public String getBorrowValidTime() {
		return borrowValidTime;
	}

	/**
	 * @param borrowValidTime
	 *            the borrowValidTime to set
	 */

	public void setBorrowValidTime(String borrowValidTime) {
		this.borrowValidTime = borrowValidTime;
	}

	/**
	 * borrowUse
	 * 
	 * @return the borrowUse
	 */

	public String getBorrowUse() {
		return borrowUse;
	}

	/**
	 * @param borrowUse
	 *            the borrowUse to set
	 */

	public void setBorrowUse(String borrowUse) {
		this.borrowUse = borrowUse;
	}

	/**
	 * borrowApr
	 * 
	 * @return the borrowApr
	 */

	public String getBorrowApr() {
		return borrowApr;
	}

	/**
	 * @param borrowApr
	 *            the borrowApr to set
	 */

	public void setBorrowApr(String borrowApr) {
		this.borrowApr = borrowApr;
	}

	/**
	 * borrowStyle
	 * 
	 * @return the borrowStyle
	 */

	public String getBorrowStyle() {
		return borrowStyle;
	}

	/**
	 * @param borrowStyle
	 *            the borrowStyle to set
	 */

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	/**
	 * borrowPeriod
	 * 
	 * @return the borrowPeriod
	 */

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	/**
	 * @param borrowPeriod
	 *            the borrowPeriod to set
	 */

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	/**
	 * name
	 * 
	 * @return the name
	 */

	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * username
	 * 
	 * @return the username
	 */

	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * account
	 * 
	 * @return the account
	 */

	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */

	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * borrowPreNid
	 * 
	 * @return the borrowPreNid
	 */

	public String getBorrowPreNid() {
		return borrowPreNid;
	}

	/**
	 * @param borrowPreNid
	 *            the borrowPreNid to set
	 */

	public void setBorrowPreNid(String borrowPreNid) {
		this.borrowPreNid = borrowPreNid;
	}

	/**
	 * borrowNidSrch
	 * 
	 * @return the borrowNidSrch
	 */

	public String getBorrowNidSrch() {
		return borrowNidSrch;
	}

	/**
	 * @param borrowNidSrch
	 *            the borrowNidSrch to set
	 */

	public void setBorrowNidSrch(String borrowNidSrch) {
		this.borrowNidSrch = borrowNidSrch;
	}

	/**
	 * usernameSrch
	 * 
	 * @return the usernameSrch
	 */

	public String getUsernameSrch() {
		return usernameSrch;
	}

	/**
	 * @param usernameSrch
	 *            the usernameSrch to set
	 */

	public void setUsernameSrch(String usernameSrch) {
		this.usernameSrch = usernameSrch;
	}

	/**
	 * statusSrch
	 * 
	 * @return the statusSrch
	 */

	public String getStatusSrch() {
		return statusSrch;
	}

	/**
	 * @param statusSrch
	 *            the statusSrch to set
	 */

	public void setStatusSrch(String statusSrch) {
		this.statusSrch = statusSrch;
	}

	/**
	 * borrowStyleSrch
	 * 
	 * @return the borrowStyleSrch
	 */

	public String getBorrowStyleSrch() {
		return borrowStyleSrch;
	}

	/**
	 * @param borrowStyleSrch
	 *            the borrowStyleSrch to set
	 */

	public void setBorrowStyleSrch(String borrowStyleSrch) {
		this.borrowStyleSrch = borrowStyleSrch;
	}

	/**
	 * projectTypeSrch
	 * 
	 * @return the projectTypeSrch
	 */

	public String getProjectTypeSrch() {
		return projectTypeSrch;
	}

	/**
	 * @param projectTypeSrch
	 *            the projectTypeSrch to set
	 */

	public void setProjectTypeSrch(String projectTypeSrch) {
		this.projectTypeSrch = projectTypeSrch;
	}

	/**
	 * timeStartSrch
	 * 
	 * @return the timeStartSrch
	 */

	public String getTimeStartSrch() {
		return timeStartSrch;
	}

	/**
	 * @param timeStartSrch
	 *            the timeStartSrch to set
	 */

	public void setTimeStartSrch(String timeStartSrch) {
		this.timeStartSrch = timeStartSrch;
	}

	/**
	 * timeEndSrch
	 * 
	 * @return the timeEndSrch
	 */

	public String getTimeEndSrch() {
		return timeEndSrch;
	}
	

	public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    /**
	 * @param timeEndSrch
	 *            the timeEndSrch to set
	 */

	public void setTimeEndSrch(String timeEndSrch) {
		this.timeEndSrch = timeEndSrch;
	}

	/**
	 * borrowNameSrch
	 * 
	 * @return the borrowNameSrch
	 */

	public String getBorrowNameSrch() {
		return borrowNameSrch;
	}

	/**
	 * @param borrowNameSrch
	 *            the borrowNameSrch to set
	 */

	public void setBorrowNameSrch(String borrowNameSrch) {
		this.borrowNameSrch = borrowNameSrch;
	}

	/**
	 * borrowNid
	 * 
	 * @return the borrowNid
	 */

	public String getBorrowNid() {
		return borrowNid;
	}

	/**
	 * @param borrowNid
	 *            the borrowNid to set
	 */

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}

	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	/**
	 * serialversionuid
	 * 
	 * @return the serialversionuid
	 */

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * borrowServiceScale
	 * 
	 * @return the borrowServiceScale
	 */

	public String getBorrowServiceScale() {
		return borrowServiceScale;
	}

	/**
	 * @param borrowServiceScale
	 *            the borrowServiceScale to set
	 */
	public void setBorrowServiceScale(String borrowServiceScale) {
		this.borrowServiceScale = borrowServiceScale;
	}

	/**
	 * ontime
	 * 
	 * @return the ontime
	 */

	public String getOntime() {
		return ontime;
	}

	/**
	 * @param ontime
	 *            the ontime to set
	 */

	public void setOntime(String ontime) {
		this.ontime = ontime;
	}

	/**
	 * verifyStatus
	 * 
	 * @return the verifyStatus
	 */

	public String getVerifyStatus() {
		return verifyStatus;
	}

	/**
	 * @param verifyStatus
	 *            the verifyStatus to set
	 */

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	/**
	 * verifyRemark
	 * 
	 * @return the verifyRemark
	 */

	public String getVerifyRemark() {
		return verifyRemark;
	}

	/**
	 * @param verifyRemark
	 *            the verifyRemark to set
	 */

	public void setVerifyRemark(String verifyRemark) {
		this.verifyRemark = verifyRemark;
	}

	/**
	 * moveFlag
	 * 
	 * @return the moveFlag
	 */

	public String getMoveFlag() {
		return moveFlag;
	}

	/**
	 * @param moveFlag
	 *            the moveFlag to set
	 */

	public void setMoveFlag(String moveFlag) {
		this.moveFlag = moveFlag;
	}

	/**
	 * isBailSrch
	 * 
	 * @return the isBailSrch
	 */

	public String getIsBailSrch() {
		return isBailSrch;
	}

	/**
	 * @param isBailSrch
	 *            the isBailSrch to set
	 */

	public void setIsBailSrch(String isBailSrch) {
		this.isBailSrch = isBailSrch;
	}

	/**
	 * isChaibiao
	 * 
	 * @return the isChaibiao
	 */

	public String getIsChaibiao() {
		return isChaibiao;
	}

	/**
	 * @param isChaibiao
	 *            the isChaibiao to set
	 */

	public void setIsChaibiao(String isChaibiao) {
		this.isChaibiao = isChaibiao;
	}

	/**
	 * borrowCarinfoList
	 * 
	 * @return the borrowCarinfoList
	 */

	public List<BorrowCommonCarVO> getBorrowCarinfoList() {
		return borrowCarinfoList;
	}

	/**
	 * @param borrowCarinfoList
	 *            the borrowCarinfoList to set
	 */

	public void setBorrowCarinfoList(List<BorrowCommonCarVO> borrowCarinfoList) {
		this.borrowCarinfoList = borrowCarinfoList;
	}

	/**
	 * borrowManagerScale
	 * 
	 * @return the borrowManagerScale
	 */

	public String getBorrowManagerScale() {
		return borrowManagerScale;
	}

	/**
	 * @param borrowManagerScale
	 *            the borrowManagerScale to set
	 */

	public void setBorrowManagerScale(String borrowManagerScale) {
		this.borrowManagerScale = borrowManagerScale;
	}

	public String getBorrowReturnScale() {
		return borrowReturnScale;
	}

	public void setBorrowReturnScale(String borrowReturnScale) {
		this.borrowReturnScale = borrowReturnScale;
	}

	/**
	 * canTransactionPc
	 * 
	 * @return the canTransactionPc
	 */

	public String getCanTransactionPc() {
		return canTransactionPc;
	}

	/**
	 * @param canTransactionPc
	 *            the canTransactionPc to set
	 */

	public void setCanTransactionPc(String canTransactionPc) {
		this.canTransactionPc = canTransactionPc;
	}

	/**
	 * canTransactionWei
	 * 
	 * @return the canTransactionWei
	 */

	public String getCanTransactionWei() {
		return canTransactionWei;
	}

	/**
	 * @param canTransactionWei
	 *            the canTransactionWei to set
	 */

	public void setCanTransactionWei(String canTransactionWei) {
		this.canTransactionWei = canTransactionWei;
	}

	/**
	 * canTransactionIos
	 * 
	 * @return the canTransactionIos
	 */

	public String getCanTransactionIos() {
		return canTransactionIos;
	}

	/**
	 * @param canTransactionIos
	 *            the canTransactionIos to set
	 */

	public void setCanTransactionIos(String canTransactionIos) {
		this.canTransactionIos = canTransactionIos;
	}

	/**
	 * canTransactionAndroid
	 * 
	 * @return the canTransactionAndroid
	 */

	public String getCanTransactionAndroid() {
		return canTransactionAndroid;
	}

	/**
	 * @param canTransactionAndroid
	 *            the canTransactionAndroid to set
	 */

	public void setCanTransactionAndroid(String canTransactionAndroid) {
		this.canTransactionAndroid = canTransactionAndroid;
	}

	/**
	 * operationLabel
	 * 
	 * @return the operationLabel
	 */

	public String getOperationLabel() {
		return operationLabel;
	}

	/**
	 * @param operationLabel
	 *            the operationLabel to set
	 */

	public void setOperationLabel(String operationLabel) {
		this.operationLabel = operationLabel;
	}

	/**
	 * borrowCompanyInstruction
	 * 
	 * @return the borrowCompanyInstruction
	 */

	public String getBorrowCompanyInstruction() {
		return borrowCompanyInstruction;
	}

	/**
	 * @param borrowCompanyInstruction
	 *            the borrowCompanyInstruction to set
	 */

	public void setBorrowCompanyInstruction(String borrowCompanyInstruction) {
		this.borrowCompanyInstruction = borrowCompanyInstruction;
	}

	/**
	 * borrowOperatingProcess
	 * 
	 * @return the borrowOperatingProcess
	 */

	public String getBorrowOperatingProcess() {
		return borrowOperatingProcess;
	}

	/**
	 * @param borrowOperatingProcess
	 *            the borrowOperatingProcess to set
	 */

	public void setBorrowOperatingProcess(String borrowOperatingProcess) {
		this.borrowOperatingProcess = borrowOperatingProcess;
	}

	/**
	 * typeCar
	 * 
	 * @return the typeCar
	 */

	public String getTypeCar() {
		return typeCar;
	}

	/**
	 * @param typeCar
	 *            the typeCar to set
	 */

	public void setTypeCar(String typeCar) {
		this.typeCar = typeCar;
	}

	/**
	 * typeHouse
	 * 
	 * @return the typeHouse
	 */

	public String getTypeHouse() {
		return typeHouse;
	}

	/**
	 * @param typeHouse
	 *            the typeHouse to set
	 */

	public void setTypeHouse(String typeHouse) {
		this.typeHouse = typeHouse;
	}

	/**
	 * borrowCommonNameAccountList
	 * 
	 * @return the borrowCommonNameAccountList
	 */

	public List<BorrowCommonNameAccountVO> getBorrowCommonNameAccountList() {
		return borrowCommonNameAccountList;
	}

	/**
	 * @param borrowCommonNameAccountList
	 *            the borrowCommonNameAccountList to set
	 */

	public void setBorrowCommonNameAccountList(List<BorrowCommonNameAccountVO> borrowCommonNameAccountList) {
		this.borrowCommonNameAccountList = borrowCommonNameAccountList;
	}

	/**
	 * verify
	 * 
	 * @return the verify
	 */

	public String getVerify() {
		return verify;
	}

	/**
	 * @param verify
	 *            the verify to set
	 */

	public void setVerify(String verify) {
		this.verify = verify;
	}

	/**
	 * companyOrPersonal
	 * 
	 * @return the companyOrPersonal
	 */

	public Integer getCompanyOrPersonal() {
		return companyOrPersonal;
	}

	/**
	 * @param companyOrPersonal
	 *            the companyOrPersonal to set
	 */

	public void setCompanyOrPersonal(Integer companyOrPersonal) {
		this.companyOrPersonal = companyOrPersonal;
	}

	/**
	 * status
	 * 
	 * @return the status
	 */

	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * consumeId
	 * 
	 * @return the consumeId
	 */

	public String getConsumeId() {
		return consumeId;
	}

	/**
	 * @param consumeId
	 *            the consumeId to set
	 */

	public void setConsumeId(String consumeId) {
		this.consumeId = consumeId;
	}

	/**
	 * userCount
	 * 
	 * @return the userCount
	 */

	public String getUserCount() {
		return userCount;
	}

	/**
	 * cunsumeCount
	 * 
	 * @return the cunsumeCount
	 */

	public String getCunsumeCount() {
		return cunsumeCount;
	}

	/**
	 * @param cunsumeCount
	 *            the cunsumeCount to set
	 */

	public void setCunsumeCount(String cunsumeCount) {
		this.cunsumeCount = cunsumeCount;
	}

	/**
	 * @param userCount
	 *            the userCount to set
	 */

	public void setUserCount(String userCount) {
		this.userCount = userCount;
	}

	/**
	 * cunsumeCountError
	 * 
	 * @return the cunsumeCountError
	 */

	public String getCunsumeCountError() {
		return cunsumeCountError;
	}

	/**
	 * @param cunsumeCountError
	 *            the cunsumeCountError to set
	 */

	public void setCunsumeCountError(String cunsumeCountError) {
		this.cunsumeCountError = cunsumeCountError;
	}

	/**
	 * borrowManagerScaleEnd
	 * 
	 * @return the borrowManagerScaleEnd
	 */

	public String getBorrowManagerScaleEnd() {
		return borrowManagerScaleEnd;
	}

	/**
	 * @param borrowManagerScaleEnd
	 *            the borrowManagerScaleEnd to set
	 */

	public void setBorrowManagerScaleEnd(String borrowManagerScaleEnd) {
		this.borrowManagerScaleEnd = borrowManagerScaleEnd;
	}

	/**
	 * disposalPriceEstimate
	 * 
	 * @return the disposalPriceEstimate
	 */

	public String getDisposalPriceEstimate() {
		return disposalPriceEstimate;
	}

	/**
	 * @param disposalPriceEstimate
	 *            the disposalPriceEstimate to set
	 */

	public void setDisposalPriceEstimate(String disposalPriceEstimate) {
		this.disposalPriceEstimate = disposalPriceEstimate;
	}

	/**
	 * disposalPeriod
	 * 
	 * @return the disposalPeriod
	 */

	public String getDisposalPeriod() {
		return disposalPeriod;
	}

	/**
	 * @param disposalPeriod
	 *            the disposalPeriod to set
	 */

	public void setDisposalPeriod(String disposalPeriod) {
		this.disposalPeriod = disposalPeriod;
	}

	/**
	 * disposalChannel
	 * 
	 * @return the disposalChannel
	 */

	public String getDisposalChannel() {
		return disposalChannel;
	}

	/**
	 * @param disposalChannel
	 *            the disposalChannel to set
	 */

	public void setDisposalChannel(String disposalChannel) {
		this.disposalChannel = disposalChannel;
	}

	/**
	 * disposalResult
	 * 
	 * @return the disposalResult
	 */

	public String getDisposalResult() {
		return disposalResult;
	}

	/**
	 * @param disposalResult
	 *            the disposalResult to set
	 */

	public void setDisposalResult(String disposalResult) {
		this.disposalResult = disposalResult;
	}

	/**
	 * disposalNote
	 * 
	 * @return the disposalNote
	 */

	public String getDisposalNote() {
		return disposalNote;
	}

	/**
	 * @param disposalNote
	 *            the disposalNote to set
	 */

	public void setDisposalNote(String disposalNote) {
		this.disposalNote = disposalNote;
	}

	/**
	 * disposalProjectName
	 * 
	 * @return the disposalProjectName
	 */

	public String getDisposalProjectName() {
		return disposalProjectName;
	}

	/**
	 * @param disposalProjectName
	 *            the disposalProjectName to set
	 */

	public void setDisposalProjectName(String disposalProjectName) {
		this.disposalProjectName = disposalProjectName;
	}

	/**
	 * disposalProjectType
	 * 
	 * @return the disposalProjectType
	 */

	public String getDisposalProjectType() {
		return disposalProjectType;
	}

	/**
	 * @param disposalProjectType
	 *            the disposalProjectType to set
	 */

	public void setDisposalProjectType(String disposalProjectType) {
		this.disposalProjectType = disposalProjectType;
	}

	/**
	 * disposalArea
	 * 
	 * @return the disposalArea
	 */

	public String getDisposalArea() {
		return disposalArea;
	}

	/**
	 * @param disposalArea
	 *            the disposalArea to set
	 */

	public void setDisposalArea(String disposalArea) {
		this.disposalArea = disposalArea;
	}

	/**
	 * disposalPredictiveValue
	 * 
	 * @return the disposalPredictiveValue
	 */

	public String getDisposalPredictiveValue() {
		return disposalPredictiveValue;
	}

	/**
	 * @param disposalPredictiveValue
	 *            the disposalPredictiveValue to set
	 */

	public void setDisposalPredictiveValue(String disposalPredictiveValue) {
		this.disposalPredictiveValue = disposalPredictiveValue;
	}

	/**
	 * disposalOwnershipCategory
	 * 
	 * @return the disposalOwnershipCategory
	 */

	public String getDisposalOwnershipCategory() {
		return disposalOwnershipCategory;
	}

	/**
	 * @param disposalOwnershipCategory
	 *            the disposalOwnershipCategory to set
	 */

	public void setDisposalOwnershipCategory(String disposalOwnershipCategory) {
		this.disposalOwnershipCategory = disposalOwnershipCategory;
	}

	/**
	 * disposalAssetOrigin
	 * 
	 * @return the disposalAssetOrigin
	 */

	public String getDisposalAssetOrigin() {
		return disposalAssetOrigin;
	}

	/**
	 * @param disposalAssetOrigin
	 *            the disposalAssetOrigin to set
	 */

	public void setDisposalAssetOrigin(String disposalAssetOrigin) {
		this.disposalAssetOrigin = disposalAssetOrigin;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * disposalAttachmentInfo
	 * 
	 * @return the disposalAttachmentInfo
	 */

	public String getDisposalAttachmentInfo() {
		return disposalAttachmentInfo;
	}

	/**
	 * @param disposalAttachmentInfo
	 *            the disposalAttachmentInfo to set
	 */

	public void setDisposalAttachmentInfo(String disposalAttachmentInfo) {
		this.disposalAttachmentInfo = disposalAttachmentInfo;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public Integer getBorrowInterestCoupon() {
		return borrowInterestCoupon;
	}

	public void setBorrowInterestCoupon(Integer borrowInterestCoupon) {
		this.borrowInterestCoupon = borrowInterestCoupon;
	}

	public String getBookingBeginTime() {
		return bookingBeginTime;
	}

	public void setBookingBeginTime(String bookingBeginTime) {
		this.bookingBeginTime = bookingBeginTime;
	}

	public String getBookingEndTime() {
		return bookingEndTime;
	}

	public void setBookingEndTime(String bookingEndTime) {
		this.bookingEndTime = bookingEndTime;
	}

	public String getBorrowAssetNumber() {
		return borrowAssetNumber;
	}

	public void setBorrowAssetNumber(String borrowAssetNumber) {
		this.borrowAssetNumber = borrowAssetNumber;
	}

	public String getBorrowProjectSource() {
		return borrowProjectSource;
	}

	public void setBorrowProjectSource(String borrowProjectSource) {
		this.borrowProjectSource = borrowProjectSource;
	}

	public String getBorrowInterestTime() {
		return borrowInterestTime;
	}

	public void setBorrowInterestTime(String borrowInterestTime) {
		this.borrowInterestTime = borrowInterestTime;
	}

	public String getBorrowDueTime() {
		return borrowDueTime;
	}

	public void setBorrowDueTime(String borrowDueTime) {
		this.borrowDueTime = borrowDueTime;
	}

	public String getBorrowSafeguardWay() {
		return borrowSafeguardWay;
	}

	public void setBorrowSafeguardWay(String borrowSafeguardWay) {
		this.borrowSafeguardWay = borrowSafeguardWay;
	}

	public String getBorrowIncomeDescription() {
		return borrowIncomeDescription;
	}

	public void setBorrowIncomeDescription(String borrowIncomeDescription) {
		this.borrowIncomeDescription = borrowIncomeDescription;
	}

	public String getBorrowPublisher() {
		return borrowPublisher;
	}

	public void setBorrowPublisher(String borrowPublisher) {
		this.borrowPublisher = borrowPublisher;
	}

	public String getBorrowExtraYield() {
		return borrowExtraYield;
	}

	public void setBorrowExtraYield(String borrowExtraYield) {
		this.borrowExtraYield = borrowExtraYield;
	}

	public String getContractPeriod() {
		return contractPeriod;
	}

	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}

	public String getRepayOrgName() {
		return repayOrgName;
	}

	public void setRepayOrgName(String repayOrgName) {
		this.repayOrgName = repayOrgName;
	}

	public Integer getRepayOrgUserId() {
		return repayOrgUserId;
	}

	public void setRepayOrgUserId(Integer repayOrgUserId) {
		this.repayOrgUserId = repayOrgUserId;
	}

	public boolean isRepayOrgFlag() {
		return isRepayOrgFlag;
	}

	public void setRepayOrgFlag(boolean isRepayOrgFlag) {
		this.isRepayOrgFlag = isRepayOrgFlag;
	}

	public String getBorrowLevel() {
		return borrowLevel;
	}

	public void setBorrowLevel(String borrowLevel) {
		this.borrowLevel = borrowLevel;
	}

	public String getBankRegistDays() {
		return bankRegistDays;
	}

	public void setBankRegistDays(String bankRegistDays) {
		this.bankRegistDays = bankRegistDays;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getFinancePurpose() {
		return financePurpose;
	}

	public void setFinancePurpose(String financePurpose) {
		this.financePurpose = financePurpose;
	}

	public String getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(String monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getFirstPayment() {
		return firstPayment;
	}

	public void setFirstPayment(String firstPayment) {
		this.firstPayment = firstPayment;
	}

	public String getSecondPayment() {
		return secondPayment;
	}

	public void setSecondPayment(String secondPayment) {
		this.secondPayment = secondPayment;
	}

	public String getCostIntrodution() {
		return costIntrodution;
	}

	public void setCostIntrodution(String costIntrodution) {
		this.costIntrodution = costIntrodution;
	}

	public String getFianceCondition() {
		return fianceCondition;
	}

	public void setFianceCondition(String fianceCondition) {
		this.fianceCondition = fianceCondition;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getDomicile() {
		return domicile;
	}

	public void setDomicile(String domicile) {
		this.domicile = domicile;
	}

	public String getOverdueTimes() {
		return overdueTimes;
	}

	public void setOverdueTimes(String overdueTimes) {
		this.overdueTimes = overdueTimes;
	}

	public String getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(String overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public String getIsCard() {
		return isCard;
	}

	public void setIsCard(String isCard) {
		this.isCard = isCard;
	}

	public String getIsIncome() {
		return isIncome;
	}

	public void setIsIncome(String isIncome) {
		this.isIncome = isIncome;
	}

	public String getIsCredit() {
		return isCredit;
	}

	public void setIsCredit(String isCredit) {
		this.isCredit = isCredit;
	}

	public String getIsAsset() {
		return isAsset;
	}

	public void setIsAsset(String isAsset) {
		this.isAsset = isAsset;
	}

	public String getIsVehicle() {
		return isVehicle;
	}

	public void setIsVehicle(String isVehicle) {
		this.isVehicle = isVehicle;
	}

	public String getIsDrivingLicense() {
		return isDrivingLicense;
	}

	public void setIsDrivingLicense(String isDrivingLicense) {
		this.isDrivingLicense = isDrivingLicense;
	}

	public String getIsVehicleRegistration() {
		return isVehicleRegistration;
	}

	public void setIsVehicleRegistration(String isVehicleRegistration) {
		this.isVehicleRegistration = isVehicleRegistration;
	}

	public String getIsMerry() {
		return isMerry;
	}

	public void setIsMerry(String isMerry) {
		this.isMerry = isMerry;
	}

	public String getIsWork() {
		return isWork;
	}

	public void setIsWork(String isWork) {
		this.isWork = isWork;
	}

	public String getIsAccountBook() {
		return isAccountBook;
	}

	public void setIsAccountBook(String isAccountBook) {
		this.isAccountBook = isAccountBook;
	}

	public String getLitigation() {
		return litigation;
	}

	public void setLitigation(String litigation) {
		this.litigation = litigation;
	}

	public String getComLegalPerson() {
		return comLegalPerson;
	}

	public void setComLegalPerson(String comLegalPerson) {
		this.comLegalPerson = comLegalPerson;
	}

	public String getInstCode() {
		return instCode;
	}

	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}

	public String getEntrustedFlg() {
		return entrustedFlg;
	}

	public void setEntrustedFlg(String entrustedFlg) {
		this.entrustedFlg = entrustedFlg;
	}

	public String getEntrustedUsername() {
		return entrustedUsername;
	}

	public void setEntrustedUsername(String entrustedUsername) {
		this.entrustedUsername = entrustedUsername;
	}

	public String getIsEngineUsed() {
		return isEngineUsed;
	}

	public void setIsEngineUsed(String isEngineUsed) {
		this.isEngineUsed = isEngineUsed;
	}

	public String getPublishInstCode() {
		return publishInstCode;
	}

	public void setPublishInstCode(String publishInstCode) {
		this.publishInstCode = publishInstCode;
	}

    public String getPageToken() {
        return pageToken;
    }

    public void setPageToken(String pageToken) {
        this.pageToken = pageToken;
    }

	public String getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(String annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getOverdueReport() {
		return overdueReport;
	}

	public void setOverdueReport(String overdueReport) {
		this.overdueReport = overdueReport;
	}

	public String getDebtSituation() {
		return debtSituation;
	}

	public void setDebtSituation(String debtSituation) {
		this.debtSituation = debtSituation;
	}

	public String getOtherBorrowed() {
		return otherBorrowed;
	}

	public void setOtherBorrowed(String otherBorrowed) {
		this.otherBorrowed = otherBorrowed;
	}

	public String getIsFunds() {
		return isFunds;
	}

	public void setIsFunds(String isFunds) {
		this.isFunds = isFunds;
	}

	public String getIsManaged() {
		return isManaged;
	}

	public void setIsManaged(String isManaged) {
		this.isManaged = isManaged;
	}

	public String getIsAbility() {
		return isAbility;
	}

	public void setIsAbility(String isAbility) {
		this.isAbility = isAbility;
	}

	public String getIsOverdue() {
		return isOverdue;
	}

	public void setIsOverdue(String isOverdue) {
		this.isOverdue = isOverdue;
	}

	public String getIsComplaint() {
		return isComplaint;
	}

	public void setIsComplaint(String isComplaint) {
		this.isComplaint = isComplaint;
	}

	public String getIsPunished() {
		return isPunished;
	}

	public void setIsPunished(String isPunished) {
		this.isPunished = isPunished;
	}

	public String getComOverdueReport() {
		return comOverdueReport;
	}

	public void setComOverdueReport(String comOverdueReport) {
		this.comOverdueReport = comOverdueReport;
	}

	public String getComDebtSituation() {
		return comDebtSituation;
	}

	public void setComDebtSituation(String comDebtSituation) {
		this.comDebtSituation = comDebtSituation;
	}

	public String getComOtherBorrowed() {
		return comOtherBorrowed;
	}

	public void setComOtherBorrowed(String comOtherBorrowed) {
		this.comOtherBorrowed = comOtherBorrowed;
	}

	public String getComIsFunds() {
		return comIsFunds;
	}

	public void setComIsFunds(String comIsFunds) {
		this.comIsFunds = comIsFunds;
	}

	public String getComIsManaged() {
		return comIsManaged;
	}

	public void setComIsManaged(String comIsManaged) {
		this.comIsManaged = comIsManaged;
	}

	public String getComIsAbility() {
		return comIsAbility;
	}

	public void setComIsAbility(String comIsAbility) {
		this.comIsAbility = comIsAbility;
	}

	public String getComIsOverdue() {
		return comIsOverdue;
	}

	public void setComIsOverdue(String comIsOverdue) {
		this.comIsOverdue = comIsOverdue;
	}

	public String getComIsComplaint() {
		return comIsComplaint;
	}

	public void setComIsComplaint(String comIsComplaint) {
		this.comIsComplaint = comIsComplaint;
	}

	public String getComIsPunished() {
		return comIsPunished;
	}

	public void setComIsPunished(String comIsPunished) {
		this.comIsPunished = comIsPunished;
	}

	public String getCorporateCode() {
		return corporateCode;
	}

	public void setCorporateCode(String corporateCode) {
		this.corporateCode = corporateCode;
	}

	public String getRegistrationAddress() {
		return registrationAddress;
	}

	public void setRegistrationAddress(String registrationAddress) {
		this.registrationAddress = registrationAddress;
	}
	private Integer labelId;

	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}
	
}