package com.hyjf.am.trade.service.front.borrow.impl;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.bean.admin.BorrowCommonBean;
import com.hyjf.am.trade.bean.BorrowCommonFile;
import com.hyjf.am.trade.bean.BorrowCommonFileData;
import com.hyjf.am.trade.bean.BorrowWithBLOBs;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.front.borrow.BorrowCommonService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class BorrowCommonServiceImpl extends BaseServiceImpl implements BorrowCommonService {
	private Logger logger = LoggerFactory.getLogger(BorrowCommonServiceImpl.class);

	@Value("${file.domain.url}")
	private String url;
	@Value("${file.physical.path}")
	private String physical;
	@Value("${file.upload.real.path}")
	private String real;

	/**
	 * 项目类型
	 * 
	 * @return
	 * @author Administrator
	 */
	@Override
	public String getBorrowProjectClass(String borrowCd) {
		BorrowProjectTypeExample example = new BorrowProjectTypeExample();
		BorrowProjectTypeExample.Criteria cra = example.createCriteria();
		cra.andStatusEqualTo(Integer.valueOf(CustomConstants.FLAG_NORMAL));
		cra.andBorrowCdEqualTo(Integer.valueOf(borrowCd));
		// 不查询融通宝相关
		cra.andBorrowNameNotEqualTo(CustomConstants.RTB);
		List<BorrowProjectType> list = this.borrowProjectTypeMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0).getBorrowClass();
		}
		return "";
	}

	/**
	 *
	 * 获取借款信息
	 * 
	 * @param borrowBean
	 * @return
	 */
	@Override
	public BorrowCommonBean getBorrow(BorrowCommonBean borrowBean) {
		// 借款信息数据获取
		BorrowWithBLOBs borrowWithBLOBs = this.getBorrowWithBLOBs(borrowBean.getBorrowNid());
		if (borrowWithBLOBs != null) {
			// 借款信息数据放置
			this.getBorrowCommonFiled(borrowBean, borrowWithBLOBs);
			if (borrowBean.getCompanyOrPersonal() == 0) {
				if (StringUtils.isNotEmpty(borrowBean.getComName())) {
					borrowBean.setCompanyOrPersonal(1);
				} else if (StringUtils.isNotEmpty(borrowBean.getManname())) {
					borrowBean.setCompanyOrPersonal(2);
				}
			}
		}
		return borrowBean;

	}

	/**
	 * 借款信息数据获取
	 * 
	 * @param borrowBean
	 * @param borrowWithBLOBs
	 * @author Administrator
	 */
	@Override
	public void getBorrowCommonFiled(BorrowCommonBean borrowBean, BorrowWithBLOBs borrowWithBLOBs) {
		NumberFormat numberFormat = new DecimalFormat("####");
		borrowBean.setRemark(this.getValue(String.valueOf(borrowWithBLOBs.getRemark())));
		// 借款预编码（1411001意为2014年11月份第1单借款标，后借款标分期编号HDD141100101）
		borrowBean.setBorrowPreNid(this.getValue(String.valueOf(borrowWithBLOBs.getBorrowPreNid())));
		// 借款状态
		borrowBean.setStatus(this.getValue(String.valueOf(borrowWithBLOBs.getStatus())));
		// 借款编码
		borrowBean.setBorrowNid(this.getValue(String.valueOf(borrowWithBLOBs.getBorrowNid())));
		// 借款标题
		borrowBean.setName(this.getValue(borrowWithBLOBs.getName()));
		// 项目标题
		borrowBean.setProjectName(this.getValue(borrowWithBLOBs.getProjectName()));
		// 借款金额
		borrowBean.setAccount(this.getValue(numberFormat.format(borrowWithBLOBs.getAccount())));
		// 借款用户
		RUser users = this.rUserMapper.selectByPrimaryKey(borrowWithBLOBs.getUserId());
		if (users != null) {
			borrowBean.setUsername(this.getValue(users.getUsername()));
		}
		// 项目申请人
		borrowBean.setApplicant(borrowWithBLOBs.getApplicant());
		// 垫付机构用户名
		borrowBean.setRepayOrgName(borrowWithBLOBs.getRepayOrgName());
		// 年利率
		borrowBean.setBorrowApr(this.getValue(String.valueOf(borrowWithBLOBs.getBorrowApr())));
		// 借款期限
		borrowBean.setBorrowPeriod(this.getValue(String.valueOf(borrowWithBLOBs.getBorrowPeriod())));
		// 还款方式
		borrowBean.setBorrowStyle(this.getValue(borrowWithBLOBs.getBorrowStyle()));
		// 借款用途
		borrowBean.setBorrowUse(this.getValue(borrowWithBLOBs.getBorrowUse()));
		// 担保方式
		// borrowBean.setGuaranteeType(this.getValue(String.valueOf(borrowWithBLOBs.getGuaranteeType())));
		// 项目类型
		borrowBean.setProjectType((borrowWithBLOBs.getProjectType()));
		// 资产类型
		borrowBean.setInstCode(this.getValue(String.valueOf(borrowWithBLOBs.getInstCode())));
		// 借款方式
		borrowBean.setType(this.getValue(String.valueOf(borrowWithBLOBs.getType())));
		// 借款方式
		borrowBean.setBorrowLevel(this.getValue(String.valueOf(borrowWithBLOBs.getBorrowLevel())));

		if ("BORROW_FIRST".equals(borrowBean.getMoveFlag())) {
			// 立即发标 20171101修改为"借款初审内，每个标的点开默认暂不发标"--查道健
			borrowBean.setVerifyStatus("2");
		}
		// 资产属性
		borrowBean.setAssetAttributes(this.getValue(String.valueOf(borrowWithBLOBs.getAssetAttributes())));
		// 担保方式
		// 担保机构
		borrowBean.setBorrowMeasuresInstit(this.getValue(borrowWithBLOBs.getBorrowMeasuresInstit()));
		// 抵押物信息
		borrowBean.setBorrowMeasuresMort(this.getValue(borrowWithBLOBs.getBorrowMeasuresMort()));
		// 本息保障
		borrowBean.setBorrowMeasuresMea(this.getValue(borrowWithBLOBs.getBorrowMeasuresMea()));
		// 机构介绍
		borrowBean.setBorrowCompanyInstruction(this.getValue(borrowWithBLOBs.getBorrowCompanyInstruction()));
		// 操作流程
		borrowBean.setBorrowOperatingProcess(this.getValue(borrowWithBLOBs.getBorrowOperatingProcess()));
		// 财务状况
		borrowBean.setAccountContents(this.getValue(borrowWithBLOBs.getAccountContents()));
		// 项目描述
		borrowBean.setBorrowContents(this.getValue(borrowWithBLOBs.getBorrowContents()));
		// 资产编号
		borrowBean.setBorrowAssetNumber(this.getValue(borrowWithBLOBs.getBorrowAssetNumber()));
		// 新增协议期限字段
		// 协议期限
		if (borrowWithBLOBs.getContractPeriod() != null) {
			borrowBean.setContractPeriod(String.valueOf(borrowWithBLOBs.getContractPeriod()));
		}
		// 项目来源
		borrowBean.setBorrowProjectSource(this.getValue(borrowWithBLOBs.getBorrowProjectSource()));
		// 起息时间
		borrowBean.setBorrowInterestTime(this.getValue(borrowWithBLOBs.getBorrowInterestTime()));
		// 到期时间
		borrowBean.setBorrowDueTime(this.getValue(borrowWithBLOBs.getBorrowDueTime()));
		// 保障方式
		borrowBean.setBorrowSafeguardWay(this.getValue(borrowWithBLOBs.getBorrowSafeguardWay()));
		// 收益说明
		borrowBean.setBorrowIncomeDescription(this.getValue(borrowWithBLOBs.getBorrowIncomeDescription()));
		// 发行人
		borrowBean.setBorrowPublisher(this.getValue(borrowWithBLOBs.getBorrowPublisher()));
		// 融资用途
		borrowBean.setFinancePurpose(this.getValue(borrowWithBLOBs.getFinancePurpose()));
		// 月薪收入
		borrowBean.setMonthlyIncome(this.getValue(borrowWithBLOBs.getMonthlyIncome()));
		// 还款来源
		borrowBean.setPayment(this.getValue(borrowWithBLOBs.getPayment()));
		// 第一还款来源
		borrowBean.setFirstPayment(this.getValue(borrowWithBLOBs.getFirstPayment()));
		// 第二还款来源
		borrowBean.setSecondPayment(this.getValue(borrowWithBLOBs.getSecondPayment()));
		// 费用说明
		borrowBean.setCostIntrodution(this.getValue(borrowWithBLOBs.getCostIntrodution()));
		// 财务状况
		borrowBean.setFianceCondition(this.getValue(borrowWithBLOBs.getFianceCondition()));

		// 产品加息收益率
		borrowBean.setBorrowExtraYield(this.getValue(String.valueOf(borrowWithBLOBs.getBorrowExtraYield())));
		// 最低投标金额
		if (borrowWithBLOBs.getTenderAccountMin() == null || borrowWithBLOBs.getTenderAccountMin() == 0) {
			borrowBean.setTenderAccountMin(StringUtils.EMPTY);
		} else {
			borrowBean.setTenderAccountMin(this.getValue(numberFormat.format(borrowWithBLOBs.getTenderAccountMin())));
		}

		// 最高投标金额
		if (borrowWithBLOBs.getTenderAccountMax() == null || borrowWithBLOBs.getTenderAccountMax() == 0) {
			borrowBean.setTenderAccountMax(StringUtils.EMPTY);
		} else {
			borrowBean.setTenderAccountMax(this.getValue(numberFormat.format(borrowWithBLOBs.getTenderAccountMax())));
		}

		// 有效时间
		if (borrowWithBLOBs.getBorrowValidTime() == null || borrowWithBLOBs.getBorrowValidTime() == 0) {
			borrowBean.setBorrowValidTime(StringUtils.EMPTY);
		} else {
			borrowBean.setBorrowValidTime(this.getValue(String.valueOf(borrowWithBLOBs.getBorrowValidTime())));
		}
		borrowBean.setBankRegistDays(borrowWithBLOBs.getBankRegistDays().toString());
		// 融资服务费
		borrowBean.setBorrowServiceScale(this.getValue(borrowWithBLOBs.getServiceFeeRate()));
		// 账户管理费率
		borrowBean.setBorrowManagerScale(this.getValue(borrowWithBLOBs.getManageFeeRate()));
		// 账户管理费率(上限)
		borrowBean.setBorrowManagerScaleEnd(this.getValue(borrowWithBLOBs.getBorrowManagerScaleEnd()));
		// 可出借平台_PC
		borrowBean.setCanTransactionPc(this.getValue(borrowWithBLOBs.getCanTransactionPc()));
		// 可出借平台_微网站
		borrowBean.setCanTransactionWei(this.getValue(borrowWithBLOBs.getCanTransactionWei()));
		// 可出借平台_IOS
		borrowBean.setCanTransactionIos(this.getValue(borrowWithBLOBs.getCanTransactionIos()));
		// 可出借平台_Android
		borrowBean.setCanTransactionAndroid(this.getValue(borrowWithBLOBs.getCanTransactionAndroid()));
		// 运营标签
		borrowBean.setOperationLabel(this.getValue(borrowWithBLOBs.getOperationLabel()));
		// 公司个人区分
		borrowBean.setCompanyOrPersonal(borrowWithBLOBs.getCompanyOrPersonal());
		// 定向标
		borrowBean.setPublishInstCode(this.getValue(borrowWithBLOBs.getPublishInstCode()));
		// 项目资料
		if (StringUtils.isNotEmpty(borrowWithBLOBs.getFiles())) {
			List<BorrowCommonFile> borrowCommonFileList = JSONArray.parseArray(borrowWithBLOBs.getFiles(),
					BorrowCommonFile.class);
			if (borrowCommonFileList != null && borrowCommonFileList.size() > 0) {
				// domain URL
				String fileDomainUrl = UploadFileUtils.getDoPath(url);

				List<BorrowCommonImageVO> borrowCommonImageList = new ArrayList<BorrowCommonImageVO>();

				for (BorrowCommonFile borrowCommonFile : borrowCommonFileList) {
					List<BorrowCommonFileData> fileDataList = borrowCommonFile.getData();
					if (fileDataList != null && fileDataList.size() > 0) {
						int i = 0;
						for (BorrowCommonFileData borrowCommonFileData : fileDataList) {
							BorrowCommonImageVO borrowCommonImage = new BorrowCommonImageVO();
							borrowCommonImage.setImageName(borrowCommonFileData.getName());
							if (StringUtils.isNotEmpty(borrowCommonFileData.getFileRealName())) {
								borrowCommonImage.setImageRealName(borrowCommonFileData.getFileRealName());
							} else {
								borrowCommonImage.setImageRealName(borrowCommonFileData.getFilename());
							}
							if (StringUtils.isEmpty(borrowCommonFileData.getImageSort().trim())) {
								borrowCommonImage.setImageSort(String.valueOf(i));
							} else {
								borrowCommonImage.setImageSort(borrowCommonFileData.getImageSort().trim());
							}

							borrowCommonImage.setImagePath(borrowCommonFileData.getFileurl());
							borrowCommonImage.setImageSrc(fileDomainUrl + borrowCommonFileData.getFileurl());
							borrowCommonImageList.add(borrowCommonImage);
							i++;
						}
					}
				}

				Collections.sort(borrowCommonImageList, (o1, o2) -> {
					if (o1 != null && o2 != null) {
						Integer sort1 = Integer.valueOf(o1.getImageSort().trim());
						Integer sort2 = Integer.valueOf(o2.getImageSort().trim());
						return sort1.compareTo(sort2);
					}
					return 0;
				});
				borrowBean.setBorrowCommonImageList(borrowCommonImageList);
			}
		}

		// 售价预估
		borrowBean.setDisposalPriceEstimate(this.getValue(borrowWithBLOBs.getDisposalPriceEstimate()));
		// 处置周期
		borrowBean.setDisposalPeriod(this.getValue(borrowWithBLOBs.getDisposalPeriod()));
		// 处置渠道
		borrowBean.setDisposalChannel(this.getValue(borrowWithBLOBs.getDisposalChannel()));
		// 处置结果预案
		borrowBean.setDisposalResult(this.getValue(borrowWithBLOBs.getDisposalResult()));
		// 备注说明
		borrowBean.setDisposalNote(this.getValue(borrowWithBLOBs.getDisposalNote()));

		// 项目名称
		borrowBean.setDisposalProjectName(this.getValue(borrowWithBLOBs.getDisposalProjectName()));
		// 项目类型
		borrowBean.setDisposalProjectType(this.getValue(borrowWithBLOBs.getDisposalProjectType()));
		// 所在地区
		borrowBean.setDisposalArea(this.getValue(borrowWithBLOBs.getDisposalArea()));
		// 预估价值
		borrowBean.setDisposalPredictiveValue(this.getValue(borrowWithBLOBs.getDisposalPredictiveValue()));
		// 权属类别
		borrowBean.setDisposalOwnershipCategory(this.getValue(borrowWithBLOBs.getDisposalOwnershipCategory()));
		// 资产成因
		borrowBean.setDisposalAssetOrigin(this.getValue(borrowWithBLOBs.getDisposalAssetOrigin()));
		// 附件信息
		borrowBean.setDisposalAttachmentInfo(this.getValue(borrowWithBLOBs.getDisposalAttachmentInfo()));

		// 借款人信息数据获取
		this.getBorrowManinfo(borrowBean);
		// 车辆 房屋 认证信息 数据获取
		this.getBorrowCarinfo(borrowBean);
		// 用户信息数据获取
		this.getBorrowUsers(borrowBean);
	}

	/**
	 * 借款人信息数据获取
	 * 
	 * @param borrowBean
	 * @return
	 * @author Administrator
	 */

	@Override
	public void getBorrowManinfo(BorrowCommonBean borrowBean) {
		BorrowManinfoExample example = new BorrowManinfoExample();
		BorrowManinfoExample.Criteria cra = example.createCriteria();
		cra.andBorrowNidEqualTo(borrowBean.getBorrowNid());
		List<BorrowManinfo> borrowManinfoList = this.borrowManinfoMapper.selectByExample(example);

		if (borrowManinfoList != null && borrowManinfoList.size() > 0) {
			for (BorrowManinfo record : borrowManinfoList) {
				// 借款人姓名
				if (StringUtils.isNotEmpty(record.getName())) {
					borrowBean.setManname(this.getValue(record.getName()));
				} else {
					borrowBean.setManname(StringUtils.EMPTY);
				}

				// 借款人性别
				if (record.getSex() == null || record.getSex() == 0) {
					borrowBean.setSex(StringUtils.EMPTY);
				} else {
					borrowBean.setSex(this.getValue(String.valueOf(record.getSex())));
				}
				// 借款人年龄
				if (record.getOld() == null || record.getOld() == 0) {
					borrowBean.setOld(StringUtils.EMPTY);
				} else {
					borrowBean.setOld(this.getValue(String.valueOf(record.getOld())));
				}
				// 岗位职业
				if (StringUtils.isNotEmpty(record.getPosition())) {
					borrowBean.setPosition(record.getPosition());
				}

				// 借款人婚姻状况
				if (record.getMerry() == null || record.getMerry() == 0) {
					borrowBean.setMerry(StringUtils.EMPTY);
				} else {
					borrowBean.setMerry(this.getValue(String.valueOf(record.getMerry())));
				}

				// 借款人省
				if (StringUtils.isNotEmpty(record.getPro())) {
					borrowBean.setLocation_p(this.getValue(record.getPro()));
				} else {
					borrowBean.setLocation_p(StringUtils.EMPTY);
				}

				// 借款人市
				if (StringUtils.isNotEmpty(record.getCity())) {
					borrowBean.setLocation_c(this.getValue(record.getCity()));
				} else {
					borrowBean.setLocation_c(StringUtils.EMPTY);
				}

				// 借款人行业
				if (StringUtils.isNotEmpty(record.getIndustry())) {
					borrowBean.setIndustry(this.getValue(record.getIndustry()));
				} else {
					borrowBean.setIndustry(StringUtils.EMPTY);
				}

				// 借款人公司规模
				if (StringUtils.isNotEmpty(record.getSize())) {
					borrowBean.setSize(this.getValue(record.getSize()));
				} else {
					borrowBean.setSize(StringUtils.EMPTY);
				}

				// 借款人公司月营业额
				if (record.getBusiness() == null || BigDecimal.ZERO.equals(record.getBusiness())) {
					borrowBean.setBusiness(StringUtils.EMPTY);
				} else {
					borrowBean.setBusiness(this.getValue(String.valueOf(record.getBusiness())));
				}

				// 现单位工作时间
				if (StringUtils.isNotEmpty(record.getWtime())) {
					borrowBean.setWtime(this.getValue(record.getWtime()));
				} else {
					borrowBean.setWtime(StringUtils.EMPTY);
				}

				// 授信额度
				if (record.getCredit() != null && !Integer.valueOf(0).equals(record.getCredit())) {
					borrowBean.setUserCredit(String.valueOf((record.getCredit())));
				} else {
					borrowBean.setUserCredit(StringUtils.EMPTY);
				}
				// 身份证号
				if (StringUtils.isNotEmpty(record.getCardNo())) {
					borrowBean.setCardNo(record.getCardNo());
				} else {
					borrowBean.setCardNo(StringUtils.EMPTY);
				}
				// 户籍地
				if (StringUtils.isNotEmpty(record.getDomicile())) {
					borrowBean.setDomicile(record.getDomicile());
				} else {
					borrowBean.setDomicile(StringUtils.EMPTY);
				}
				// 在平台逾期次数
				if (StringUtils.isNotEmpty(record.getOverdueTimes())) {
					borrowBean.setOverdueTimes(record.getOverdueTimes());
				} else {
					borrowBean.setOverdueTimes(StringUtils.EMPTY);
				}
				// 在平台逾期金额
				if (StringUtils.isNotEmpty(record.getOverdueAmount())) {
					borrowBean.setOverdueAmount(record.getOverdueAmount());
				} else {
					borrowBean.setOverdueAmount(StringUtils.EMPTY);
				}
				// 涉诉情况
				if (StringUtils.isNotEmpty(record.getLitigation())) {
					borrowBean.setLitigation(record.getLitigation());
				} else {
					borrowBean.setLitigation(StringUtils.EMPTY);
				}
				// 个贷审核信息 身份证 0未审核 1已审核
				if (record.getIsCard() != null) {
					borrowBean.setIsCard(String.valueOf(record.getIsCard()));
				} else {
					borrowBean.setIsCard(StringUtils.EMPTY);
				}
				// 个贷审核信息 收入状况 0未审核 1已审核
				if (record.getIsIncome() != null) {
					borrowBean.setIsIncome(String.valueOf(record.getIsIncome()));
				} else {
					borrowBean.setIsIncome(StringUtils.EMPTY);
				}
				// 个贷审核信息 信用状况 0未审核 1已审核
				if (record.getIsCredit() != null) {
					borrowBean.setIsCredit(String.valueOf(record.getIsCredit()));
				} else {
					borrowBean.setIsCredit(StringUtils.EMPTY);
				}
				// 个贷审核信息 资产状况 0未审核 1已审核
				if (record.getIsAsset() != null) {
					borrowBean.setIsAsset(String.valueOf(record.getIsAsset()));
				} else {
					borrowBean.setIsAsset(StringUtils.EMPTY);
				}
				// 个贷审核信息 车辆状况 0未审核 1已审核
				if (record.getIsVehicle() != null) {
					borrowBean.setIsVehicle(String.valueOf(record.getIsVehicle()));
				} else {
					borrowBean.setIsVehicle(StringUtils.EMPTY);
				}
				// 个贷审核信息 行驶证 0未审核 1已审核
				if (record.getIsDrivingLicense() != null) {
					borrowBean.setIsDrivingLicense(String.valueOf(record.getIsDrivingLicense()));
				} else {
					borrowBean.setIsDrivingLicense(StringUtils.EMPTY);
				}
				// 个贷审核信息 车辆登记证 0未审核 1已审核
				if (record.getIsVehicleRegistration() != null) {
					borrowBean.setIsVehicleRegistration(String.valueOf(record.getIsVehicleRegistration()));
				} else {
					borrowBean.setIsVehicleRegistration(StringUtils.EMPTY);
				}
				// 个贷审核信息 婚姻状况 0未审核 1已审核
				if (record.getIsMerry() != null) {
					borrowBean.setIsMerry(String.valueOf(record.getIsMerry()));
				} else {
					borrowBean.setIsMerry(StringUtils.EMPTY);
				}
				// 个贷审核信息 工作状况 0未审核 1已审核
				if (record.getIsWork() != null) {
					borrowBean.setIsWork(String.valueOf(record.getIsWork()));
				} else {
					borrowBean.setIsWork(StringUtils.EMPTY);
				}
				// 个贷审核信息 户口本 0未审核 1已审核
				if (record.getIsAccountBook() != null) {
					borrowBean.setIsAccountBook(String.valueOf(record.getIsAccountBook()));
				} else {
					borrowBean.setIsAccountBook(StringUtils.EMPTY);
				}
				/** 信批需求新增(个人) start */
				// 个人年收入:10万以内；10万以上
				if (StringUtils.isNotEmpty(record.getAnnualIncome())) {
					borrowBean.setAnnualIncome(record.getAnnualIncome());
				} else {
					borrowBean.setAnnualIncome(StringUtils.EMPTY);
				}
				// 征信报告逾期情况:暂未提供；无；已处理
				if (StringUtils.isNotEmpty(record.getOverdueReport())) {
					borrowBean.setOverdueReport(record.getOverdueReport());
				} else {
					borrowBean.setOverdueReport(StringUtils.EMPTY);
				}
				// 重大负债状况:无
				if (StringUtils.isNotEmpty(record.getDebtSituation())) {
					borrowBean.setDebtSituation(record.getDebtSituation());
				} else {
					borrowBean.setDebtSituation(StringUtils.EMPTY);
				}
				// 其他平台借款情况:无
				if (StringUtils.isNotEmpty(record.getOtherBorrowed())) {
					borrowBean.setOtherBorrowed(record.getOtherBorrowed());
				} else {
					borrowBean.setOtherBorrowed(StringUtils.EMPTY);
				}
				// 借款资金运用情况：0不正常,1正常
				if (StringUtils.isNotEmpty(record.getIsFunds())) {
					borrowBean.setIsFunds(record.getIsFunds());
				} else {
					borrowBean.setIsFunds(StringUtils.EMPTY);
				}
				// 借款人经营状况及财务状况：0不正常,1正常
				if (StringUtils.isNotEmpty(record.getIsManaged())) {
					borrowBean.setIsManaged(record.getIsManaged());
				} else {
					borrowBean.setIsManaged(StringUtils.EMPTY);
				}
				// 借款人还款能力变化情况：0不正常,1正常
				if (StringUtils.isNotEmpty(record.getIsAbility())) {
					borrowBean.setIsAbility(record.getIsAbility());
				} else {
					borrowBean.setIsAbility(StringUtils.EMPTY);
				}
				// 借款人逾期情况：0暂无,1有
				if (StringUtils.isNotEmpty(record.getIsOverdue())) {
					borrowBean.setIsOverdue(record.getIsOverdue());
				} else {
					borrowBean.setIsOverdue(StringUtils.EMPTY);
				}
				// 借款人涉诉情况：0暂无,1有
				if (StringUtils.isNotEmpty(record.getIsComplaint())) {
					borrowBean.setIsComplaint(record.getIsComplaint());
				} else {
					borrowBean.setIsComplaint(StringUtils.EMPTY);
				}
				// 借款人受行政处罚情况：0暂无,1有
				if (StringUtils.isNotEmpty(record.getIsPunished())) {
					borrowBean.setIsPunished(record.getIsPunished());
				} else {
					borrowBean.setIsPunished(StringUtils.EMPTY);
				}
				/** 信批需求新增(个人) end */
				if (StringUtils.isNotEmpty(record.getAddress())) {
					borrowBean.setAddress(this.getValue(record.getAddress()));
				} else {
					borrowBean.setAddress(StringUtils.EMPTY);
				}
			}
		}
	}

	/**
	 * 车辆信息数据获取
	 * 
	 * @param borrowBean
	 * @return
	 * @author Administrator
	 */

	@Override
	public void getBorrowCarinfo(BorrowCommonBean borrowBean) {

		NumberFormat numberFormat = new DecimalFormat("####");

		BorrowCarinfoExample example = new BorrowCarinfoExample();
		BorrowCarinfoExample.Criteria cra = example.createCriteria();
		cra.andBorrowNidEqualTo(borrowBean.getBorrowNid());
		List<BorrowCarinfo> borrowCarinfoList = this.borrowCarinfoMapper.selectByExample(example);

		List<BorrowCommonCarVO> borrowCarList = new ArrayList<BorrowCommonCarVO>();
		if (borrowCarinfoList != null && borrowCarinfoList.size() > 0) {
			for (BorrowCarinfo record : borrowCarinfoList) {
				BorrowCommonCarVO borrowCar = new BorrowCommonCarVO();
				// 车辆信息 品牌
				borrowCar.setBrand(this.getValue(record.getBrand()));
				// 车辆信息 型号
				borrowCar.setModel(this.getValue(record.getModel()));
				// 车辆信息 车系
				borrowCar.setCarseries(this.getValue(record.getCarseries()));
				// 车辆信息 颜色
				borrowCar.setColor(this.getValue(record.getColor()));
				// 车辆信息 出厂年份
				borrowCar.setYear(this.getValue(record.getYear()));
				// 车辆信息 产地
				borrowCar.setPlace(this.getValue(record.getPlace()));
				// 车辆信息 购买日期
				if (record.getBuytime() != null && record.getBuytime() != 0) {
					borrowCar.setBuytime(GetDate.formatDate(Long.valueOf(record.getBuytime()) * 1000));
				} else {
					borrowCar.setBuytime(StringUtils.EMPTY);
				}
				// 车辆信息 1有保险2无保险
				if (record.getIsSafe() != null && record.getIsSafe() != 0) {
					borrowCar.setIsSafe(this.getValue(String.valueOf(record.getIsSafe())));
				} else {
					borrowCar.setIsSafe(StringUtils.EMPTY);
				}
				// 车辆信息 购买价
				if (record.getPrice() != null) {
					borrowCar.setPrice(this.getValue(numberFormat.format(record.getPrice())));
				} else {
					borrowCar.setPrice(StringUtils.EMPTY);
				}

				// 车辆信息 评估价
				if (record.getToprice() != null) {
					borrowCar.setToprice(this.getValue(numberFormat.format(record.getToprice())));
				} else {
					borrowCar.setToprice(StringUtils.EMPTY);
				}

				// 车辆信息 车牌号
				if (record.getNumber() != null) {
					borrowCar.setNumber(this.getValue(record.getNumber()));
				} else {
					borrowCar.setNumber(StringUtils.EMPTY);
				}
				// 车辆信息 车辆登记地
				if (record.getRegistration() != null) {
					borrowCar.setRegistration(this.getValue(record.getRegistration()));
				} else {
					borrowCar.setRegistration(StringUtils.EMPTY);
				}
				// 车辆信息 车架号
				if (record.getVin() != null) {
					borrowCar.setVin(this.getValue(record.getVin()));
				} else {
					borrowCar.setVin(StringUtils.EMPTY);
				}

				borrowCarList.add(borrowCar);
			}
		}

		borrowBean.setBorrowCarinfoList(borrowCarList);

		BorrowHousesExample borrowHousesExample = new BorrowHousesExample();
		BorrowHousesExample.Criteria borrowHousesCra = borrowHousesExample.createCriteria();
		borrowHousesCra.andBorrowNidEqualTo(borrowBean.getBorrowNid());
		List<BorrowHouses> borrowHousesList = this.borrowHousesMapper.selectByExample(borrowHousesExample);
		if (borrowHousesList != null && borrowHousesList.size() > 0) {
			borrowBean.setBorrowHousesList(CommonUtils.convertBeanList(borrowHousesList, BorrowHousesVO.class));
		}

		BorrowCompanyAuthenExample borrowCompanyAuthenExample = new BorrowCompanyAuthenExample();
		BorrowCompanyAuthenExample.Criteria borrowCompanyAuthenCra = borrowCompanyAuthenExample.createCriteria();
		borrowCompanyAuthenCra.andBorrowNidEqualTo(borrowBean.getBorrowNid());
		borrowCompanyAuthenExample.setOrderByClause(" authen_sort_key ASC ");
		List<BorrowCompanyAuthen> borrowCompanyAuthenList = this.borrowCompanyAuthenMapper
				.selectByExample(borrowCompanyAuthenExample);
		List<BorrowCommonCompanyAuthenVO> borrowCommonCompanyAuthenList = new ArrayList<BorrowCommonCompanyAuthenVO>();
		if (borrowCompanyAuthenList != null && borrowCompanyAuthenList.size() > 0) {
			borrowBean.setBorrowCompanyAuthenList(
					CommonUtils.convertBeanList(borrowCompanyAuthenList, BorrowCompanyAuthenVO.class));
			for (BorrowCompanyAuthen borrowCompanyAuthen : borrowCompanyAuthenList) {
				BorrowCommonCompanyAuthenVO borrowCommonCompanyAuthen = new BorrowCommonCompanyAuthenVO();
				borrowCommonCompanyAuthen.setAuthenName(this.getValue(borrowCompanyAuthen.getAuthenName()));
				borrowCommonCompanyAuthen.setAuthenTime(this.getValue(borrowCompanyAuthen.getAuthenTime()));
				borrowCommonCompanyAuthen
						.setAuthenSortKey(this.getValue(String.valueOf(borrowCompanyAuthen.getAuthenSortKey())));
				borrowCommonCompanyAuthenList.add(borrowCommonCompanyAuthen);
				borrowBean.setBorrowCommonCompanyAuthenList(borrowCommonCompanyAuthenList);
			}
		}
	}

	/**
	 * 用户信息数据获取
	 * 
	 * @param borrowBean
	 * @return
	 * @author Administrator
	 */

	@Override
	public void getBorrowUsers(BorrowCommonBean borrowBean) {
		BorrowUserExample example = new BorrowUserExample();
		BorrowUserExample.Criteria cra = example.createCriteria();
		cra.andBorrowNidEqualTo(borrowBean.getBorrowNid());
		List<BorrowUser> borrowUsersList = this.borrowUserMapper.selectByExampleWithBLOBs(example);

		if (borrowUsersList != null && borrowUsersList.size() > 0) {
			for (BorrowUser record : borrowUsersList) {
				// 用户信息 用户名称
				borrowBean.setComName(this.getValue(record.getUsername()));
				// 用户信息 所在地区 省
				// borrowBean.setComLocationProvince(this.getValue(record.getProvince()));
				// 用户信息 所在地区 市
				// borrowBean.setComLocationCity(this.getValue(record.getCity()));
				// 用户信息 所在地区 区
				borrowBean.setComLocationArea(this.getValue(record.getArea()));
				// 用户信息 注册资本
				borrowBean.setComRegCaptial(this.getValue(record.getRegCaptial()));
				// 用户信息 注册资本--货币类型
				borrowBean.setCurrencyName(this.getValue(record.getCurrencyName()));
				// 用户信息 所属行业
				borrowBean.setComUserIndustry(this.getValue(record.getIndustry()));
				// 用户信息 涉诉情况
				borrowBean.setComLitigation(this.getValue(record.getLitigation()));
				// 用户信息 征信记录
				borrowBean.setComCreReport(this.getValue(record.getCreReport()));
				// 用户信息 授信额度
				if (record.getCredit() != null && !Integer.valueOf(0).equals(record.getCredit())) {
					borrowBean.setComCredit(this.getValue(String.valueOf(record.getCredit())));
				} else {
					borrowBean.setComCredit(StringUtils.EMPTY);
				}
				// 用户信息 员工人数
				if (record.getStaff() != null && !Integer.valueOf(0).equals(record.getStaff())) {
					borrowBean.setComStaff(this.getValue(String.valueOf(record.getStaff())));
				} else {
					borrowBean.setComStaff(StringUtils.EMPTY);
				}
				// 用户信息 企业注册时间
				borrowBean.setComRegTime(this.getValue(record.getComRegTime()));
				// 用户信息 其他资料
				borrowBean.setComOtherInfo(this.getValue(record.getOtherInfo()));
				// 统一社会信用代码
				borrowBean.setComSocialCreditCode(this.getValue(record.getSocialCreditCode()));
				// 注册号
				borrowBean.setComRegistCode(this.getValue(record.getRegistCode()));
				// 法人
				borrowBean.setComLegalPerson(this.getValue(record.getLegalPerson()));
				// 主营业务
				borrowBean.setComMainBusiness(this.getValue(record.getMainBusiness()));
				// 在平台逾期次数
				borrowBean.setComOverdueTimes(this.getValue(record.getOverdueTimes()));
				// 在平台逾期金额
				borrowBean.setComOverdueAmount(this.getValue(record.getOverdueAmount()));
				// 企贷审核信息 企业证件 0未审核 1已审核
				if (record.getIsCertificate() != null) {
					borrowBean.setComIsCertificate(String.valueOf(record.getIsCertificate()));
				} else {
					borrowBean.setComIsCertificate(StringUtils.EMPTY);
				}
				// 企贷审核信息 经营状况 0未审核 1已审核
				if (record.getIsOperation() != null) {
					borrowBean.setComIsOperation(String.valueOf(record.getIsOperation()));
				} else {
					borrowBean.setComIsOperation(StringUtils.EMPTY);
				}
				// 企贷审核信息 财务状况 0未审核 1已审核
				if (record.getIsFinance() != null) {
					borrowBean.setComIsFinance(String.valueOf(record.getIsFinance()));
				} else {
					borrowBean.setComIsFinance(StringUtils.EMPTY);
				}
				// 企贷审核信息 企业信用 0未审核 1已审核
				if (record.getIsEnterpriseCreidt() != null) {
					borrowBean.setComIsEnterpriseCreidt(String.valueOf(record.getIsEnterpriseCreidt()));
				} else {
					borrowBean.setComIsEnterpriseCreidt(StringUtils.EMPTY);
				}
				// 企贷审核信息 法人信息 0未审核 1已审核
				if (record.getIsLegalPerson() != null) {
					borrowBean.setComIsLegalPerson(String.valueOf(record.getIsLegalPerson()));
				} else {
					borrowBean.setComIsLegalPerson(StringUtils.EMPTY);
				}
				// 企贷审核信息 法人信息 0未审核 1已审核
				if (record.getIsLegalPerson() != null) {
					borrowBean.setComIsLegalPerson(String.valueOf(record.getIsLegalPerson()));
				} else {
					borrowBean.setComIsLegalPerson(StringUtils.EMPTY);
				}
				// 企贷审核信息 资产状况 0未审核 1已审核
				if (record.getIsAsset() != null) {
					borrowBean.setComIsAsset(String.valueOf(record.getIsAsset()));
				} else {
					borrowBean.setComIsAsset(StringUtils.EMPTY);
				}
				// 企贷审核信息 购销合同 0未审核 1已审核
				if (record.getIsPurchaseContract() != null) {
					borrowBean.setComIsPurchaseContract(String.valueOf(record.getIsPurchaseContract()));
				} else {
					borrowBean.setComIsPurchaseContract(StringUtils.EMPTY);
				}
				// 企贷审核信息 供销合同 0未审核 1已审核
				if (record.getIsSupplyContract() != null) {
					borrowBean.setComIsSupplyContract(String.valueOf(record.getIsSupplyContract()));
				} else {
					borrowBean.setComIsSupplyContract(StringUtils.EMPTY);
				}
				/** 信批需求新增(企业) start */
				// 征信报告逾期情况:暂未提供；无；已处理
				if (record.getOverdueReport() != null) {
					borrowBean.setComOverdueReport(record.getOverdueReport());
				} else {
					borrowBean.setComOverdueReport(StringUtils.EMPTY);
				}
				// 重大负债状况:无
				if (record.getDebtSituation() != null) {
					borrowBean.setComDebtSituation(record.getDebtSituation());
				} else {
					borrowBean.setComDebtSituation(StringUtils.EMPTY);
				}
				// 其他平台借款情况:无
				if (record.getOtherBorrowed() != null) {
					borrowBean.setComOtherBorrowed(record.getOtherBorrowed());
				} else {
					borrowBean.setComOtherBorrowed(StringUtils.EMPTY);
				}
				// 借款资金运用情况：0不正常,1正常
				if (record.getIsFunds() != null) {
					borrowBean.setComIsFunds(record.getIsFunds());
				} else {
					borrowBean.setComIsFunds(StringUtils.EMPTY);
				}
				// 借款人经营状况及财务状况：0不正常,1正常
				if (record.getIsManaged() != null) {
					borrowBean.setComIsManaged(record.getIsManaged());
				} else {
					borrowBean.setComIsManaged(StringUtils.EMPTY);
				}
				// 借款人还款能力变化情况：0不正常,1正常
				if (record.getIsAbility() != null) {
					borrowBean.setComIsAbility(record.getIsAbility());
				} else {
					borrowBean.setComIsAbility(StringUtils.EMPTY);
				}
				// 借款人逾期情况：0暂无,1有
				if (record.getIsOverdue() != null) {
					borrowBean.setComIsOverdue(record.getIsOverdue());
				} else {
					borrowBean.setComIsOverdue(StringUtils.EMPTY);
				}
				// 借款人涉诉情况：0暂无,1有
				if (record.getIsComplaint() != null) {
					borrowBean.setComIsComplaint(record.getIsComplaint());
				} else {
					borrowBean.setComIsComplaint(StringUtils.EMPTY);
				}
				// 借款人受行政处罚情况：0暂无,1有
				if (record.getIsPunished() != null) {
					borrowBean.setComIsPunished(record.getIsPunished());
				} else {
					borrowBean.setComIsPunished(StringUtils.EMPTY);
				}
				/** 信批需求新增(企业) end */
				/** add by nxl 添加企业注册地址和企业组织机构代码 Start */
				borrowBean.setCorporateCode(this.getValue(record.getCorporateCode()));
				borrowBean.setRegistrationAddress(this.getValue(record.getRegistrationAddress()));
				/** add by nxl 添加企业注册地址和企业组织机构代码 end */
			}
		}
	}

	private String getValue(String value) {
		if (StringUtils.isNotEmpty(value)) {
			return value;
		}
		return "";
	}

	/**
	 * 借款预编码
	 * 
	 * @return
	 */
	@Override
	public String getBorrowPreNid() {
		String yyyymm = GetDate.getServerDateTime(13, new Date());
		String mmdd = yyyymm.substring(2);
		String borrowPreNid = this.borrowCustomizeMapper.getBorrowPreNid(mmdd);
		if (StringUtils.isEmpty(borrowPreNid)) {
			return mmdd + "0001";
		}
		if (borrowPreNid.length() == 7) {
			return mmdd + "0001";
		}
		return String.valueOf(Long.valueOf(borrowPreNid) + 1);
	}

	/**
	 * 根据借款标号查询标的详情
	 * 
	 * @param borrowNid
	 * @return
	 */
	@Override
	public BorrowWithBLOBs getBorrowWithBLOBs(String borrowNid) {
		if (StringUtils.isEmpty(borrowNid)) {
			return null;
		}
		BorrowWithBLOBs bwb = new BorrowWithBLOBs();
		BeanUtils.copyProperties(this.getBorrowInfoByNid(borrowNid), bwb);
		BeanUtils.copyProperties(this.getBorrow(borrowNid), bwb);

		return bwb;
	}

	/**
	 * 获取系统配置
	 *
	 * @return
	 */
	@Override
	public String getBorrowConfig(String configCd) {
		BorrowConfig borrowConfig = this.borrowConfigMapper.selectByPrimaryKey(configCd);
		return borrowConfig.getConfigValue();
	}

}