package com.hyjf.am.trade.service.front.borrow.impl;

import com.hyjf.am.bean.admin.BorrowCommonBean;
import com.hyjf.am.trade.bean.BorrowWithBLOBs;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.front.borrow.BorrowCommonService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.borrow.BorrowCommonCarVO;
import com.hyjf.am.vo.trade.borrow.BorrowCommonCompanyAuthenVO;
import com.hyjf.am.vo.trade.borrow.BorrowCompanyAuthenVO;
import com.hyjf.am.vo.trade.borrow.BorrowHousesVO;
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
import java.util.Date;
import java.util.List;

@Service
public class BorrowCommonServiceImpl extends BaseServiceImpl implements BorrowCommonService {
	private Logger logger = LoggerFactory.getLogger(BorrowCommonServiceImpl.class);
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
		BeanUtils.copyProperties(this.getBorrowByNid(borrowNid), bwb);

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