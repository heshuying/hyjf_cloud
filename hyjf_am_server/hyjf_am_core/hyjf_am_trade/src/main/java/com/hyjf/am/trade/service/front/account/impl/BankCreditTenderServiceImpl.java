/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.account.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.user.EmployeeCustomizeResponse;
import com.hyjf.am.resquest.trade.BorrowCreditRequest;
import com.hyjf.am.resquest.trade.CreditTenderRequest;
import com.hyjf.am.resquest.trade.MyCreditListQueryRequest;
import com.hyjf.am.trade.dao.mapper.customize.BorrowCreditTenderCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.TenderCreditCustomize;
import com.hyjf.am.trade.dao.model.customize.TenderToCreditDetailCustomize;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.front.account.BankCreditTenderService;
import com.hyjf.am.trade.service.front.borrow.BorrowRecoverService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.calculate.AccountManagementFeeUtils;
import com.hyjf.common.util.calculate.BeforeInterestAfterPrincipalUtils;
import com.hyjf.common.util.calculate.CalculatesUtil;
import com.hyjf.common.util.calculate.DuePrincipalAndInterestUtils;
import com.hyjf.common.validator.Validator;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * 银行债转异常处理
 * @author jijun
 * @since 20180620
 */
@Service
public class BankCreditTenderServiceImpl extends BaseServiceImpl implements BankCreditTenderService {

	private static final Logger logger = LoggerFactory.getLogger(BankCreditTenderServiceImpl.class);

	@Autowired
	private CommonProducer commonProducer;
	@Autowired
    private BorrowCreditTenderCustomizeMapper creditTenderCustomizeMapper;

	@Autowired
	private BorrowRecoverService borrowRecoverService;

	private static DecimalFormat DF_FOR_VIEW = new DecimalFormat("#,##0.00");

	private static DecimalFormat DF_COM_VIEW = new DecimalFormat("######0.00");
	
	/**
	 * 获取债转出借异常记录数据
	 * @return
	 */
	@Override
	public List<CreditTenderLog> selectCreditTenderLogs() {
		CreditTenderLogExample example = new CreditTenderLogExample();
		CreditTenderLogExample.Criteria cra = example.createCriteria();
		cra.andStatusEqualTo(0);
		// 添加时间 <当前时间-5分钟
		Date nowDate = GetDate.getNowTime();
		cra.andCreateTimeLessThan(DateUtils.addMinutes(nowDate,-5));//当前时间-5分钟
		cra.andCreateTimeGreaterThanOrEqualTo(DateUtils.addDays(nowDate,-2));//两天之前
		return creditTenderLogMapper.selectByExample(example);
	}

	/**
	 * 根据承接订单号查询债转出借表
	 * @param assignNid
	 * @return
	 */
	@Override
	public List<CreditTender> selectCreditTender(String assignNid) {
		CreditTenderExample example = new CreditTenderExample();
		CreditTenderExample.Criteria cra = example.createCriteria();
		cra.andAssignNidEqualTo(assignNid);
		return this.creditTenderMapper.selectByExample(example);
	}

	/**
	 * 更新債轉記錄
	 * @param creditTenderLog
	 * @return
	 */
	@Override
	public int updateCreditTenderLog(CreditTenderLogVO creditTenderLog) {
		return this.creditTenderLogMapper.updateByPrimaryKeySelective(CommonUtils.convertBean(creditTenderLog,CreditTenderLog.class));
	}

	/**
	 * 同步回调收到后,根据logOrderId检索出借记录表
	 * @param logOrderId
	 * @return
	 */
	@Override
	public CreditTenderLog selectCreditTenderLogByOrderId(String logOrderId) {
		CreditTenderLogExample example = new CreditTenderLogExample();
		CreditTenderLogExample.Criteria cra = example.createCriteria();
		cra.andLogOrderIdEqualTo(logOrderId);
		List<CreditTenderLog> list = this.creditTenderLogMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}


	/**
	 * 获取CreditTenderLog信息
	 * @param assignOrderId
	 * @param userId
	 * @return
	 */
	@Override
	public List<CreditTenderLog> selectByOrderIdAndUserId(String assignOrderId, Integer userId) {
		CreditTenderLogExample tenderLogExample = new CreditTenderLogExample();
		CreditTenderLogExample.Criteria tenderLogCra = tenderLogExample.createCriteria();
		tenderLogCra.andAssignNidEqualTo(assignOrderId).andUserIdEqualTo(userId);
		return this.creditTenderLogMapper.selectByExample(tenderLogExample);
	}

	/**
	 * 刪除
	 * @param assignOrderId
	 * @param userId
	 * @return
	 */
	@Override
	public int deleteByOrderIdAndUserId(String assignOrderId, Integer userId) {
		CreditTenderLogExample tenderLogExample = new CreditTenderLogExample();
		CreditTenderLogExample.Criteria tenderLogCra = tenderLogExample.createCriteria();
		tenderLogCra.andAssignNidEqualTo(assignOrderId).andUserIdEqualTo(userId);
		return this.creditTenderLogMapper.deleteByExample(tenderLogExample);
	}


	/**
	 * 查询相应的承接记录
	 * @param assignNid
	 * @param userId
	 * @return
	 */
	@Override
	public CreditTender selectByAssignNidAndUserId(String assignNid, Integer userId) {
		// 获取债转出借信息
		CreditTenderExample creditTenderExample = new CreditTenderExample();
		CreditTenderExample.Criteria creditTenderCra = creditTenderExample.createCriteria();
		creditTenderCra.andAssignNidEqualTo(assignNid).andUserIdEqualTo(userId);
		List<CreditTender> creditTenderList = this.creditTenderMapper.selectByExample(creditTenderExample);
		if (creditTenderList != null && creditTenderList.size() > 0) {
			CreditTender creditTender = creditTenderList.get(0);
			return creditTender;
		} else {
			return null;
		}
	}


	/**
	 * 调用银行结束债权接口
	 *
	 * @param borrowRecover
	 * @return
	 */
	private boolean requestDebtEnd(BorrowRecover borrowRecover, String tenderAccountId) {
		// 出借人用户Id
		Integer tenderUserId = borrowRecover.getUserId();
		// 借款人用户Id
		Integer borrowUserId = borrowRecover.getBorrowUserid();
		String orderId = GetOrderIdUtils.getOrderId2(tenderUserId);
		Account borrowUserAccount=this.getAccount(borrowUserId);
		logger.info(borrowRecover.getBorrowNid() + " 承接结束债权  借款人: " + borrowUserId + "-" + borrowUserAccount == null ? "" : borrowUserAccount.getAccountId() + " 出借人: "
				+ tenderUserId + "-" + tenderAccountId + " 授权码: " + borrowRecover == null ? "" : borrowRecover.getAuthCode() + " 原始订单号: " + borrowRecover == null ? "" : borrowRecover.getNid());

		BankCreditEnd record = new BankCreditEnd();
		record.setUserId(borrowUserId);
		record.setTenderUserId(tenderUserId);
		
		record.setAccountId(borrowUserAccount.getAccountId());
		record.setTenderAccountId(tenderAccountId);
		record.setOrderId(orderId);
		record.setBorrowNid(borrowRecover.getBorrowNid());
		record.setAuthCode(borrowRecover.getAuthCode());
		record.setCreditEndType(2); // 结束债权类型（1:还款，2:散标债转，3:计划债转）'
		record.setStatus(0);
		record.setOrgOrderId(borrowRecover.getNid());

		record.setCreateUser(tenderUserId);
		Date nowDate =GetDate.getDate();
		record.setCreateTime(nowDate);
		record.setUpdateUser(tenderUserId);
		record.setUpdateTime(nowDate);

		this.bankCreditEndMapper.insertSelective(record);
		return true;

	}


	
	/**
	 * 向出让人推送债转完全承接消息
	 * 
	 * @param borrowCredit
	 * @throws MQException 
	 */
	private void sendCreditFullMessage(BorrowCreditVO borrowCredit,UserVO webUser,UserInfoVO usersInfo) throws MQException {
		// 满标
		if (webUser != null) {
			Map<String, String> param = new HashMap<String, String>();
			if (usersInfo.getTruename() != null && usersInfo.getTruename().length() > 1) {
				param.put("val_name", usersInfo.getTruename().substring(0, 1));
			} else {
				param.put("val_name", usersInfo.getTruename());
			}
			if (usersInfo.getSex() == 1) {
				param.put("val_sex", "先生");
			} else if (usersInfo.getSex() == 2) {
				param.put("val_sex", "女士");
			} else {
				param.put("val_sex", "");
			}
			param.put("val_amount", borrowCredit.getCreditCapital() + "");
			param.put("val_profit", borrowCredit.getCreditInterestAdvanceAssigned() + "");
			// 发送短信验证码
			SmsMessage smsMessage = new SmsMessage(null, param, webUser.getMobile(), null, MessageConstant.SMS_SEND_FOR_MOBILE, null, CustomConstants.PARAM_TPL_ZZQBZRCG,
					CustomConstants.CHANNEL_TYPE_NORMAL);
			commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));
			
			AppMsMessage appMsMessage = new AppMsMessage(null, param, webUser.getMobile(), MessageConstant.APP_MS_SEND_FOR_MOBILE, CustomConstants.JYTZ_TPL_ZHUANRANGJIESHU);
			commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), appMsMessage));
		}
	}

	/**
	 * 债转汇付交易成功后回调处理
	 * 1.插入credit_tender
	 * 2.处理承接人account表和account_list表
	 * 3.处理出让人account表和account_list表
	 * 4.添加网站收支明细
	 * 5.更新borrow_credit
	 * 6.更新Borrow_recover
	 * 7.生成还款信息
	 */
	@Override
	public boolean updateTenderCreditInfo(CreditTenderRequest request) throws Exception{
		String assignOrderId = request.getAssignNid();
		Integer userId = request.getUserId();
		String authCode = request.getAuthCode();
		BankOpenAccountVO sellerBankAccount = request.getSellerBankAccount();
		BankOpenAccountVO assignBankAccount = request.getAssignBankAccount();
		UserInfoCustomizeVO userInfoCustomizeRefCj =request.getUserInfoCustomizeRefCj();
		SpreadsUserVO spreadsUsers=request.getSpreadsUsers();
		UserInfoCustomizeVO userInfoCustomize =request.getUserInfoCustomize();
		SpreadsUserVO spreadsUsersSeller=request.getSpreadsUsersSeller();
		UserInfoCustomizeVO userInfoCustomizeRefCr = request.getUserInfoCustomizeRefCr();
		UserInfoCustomizeVO userInfoCustomizeSeller = request.getUserInfoCustomizeSeller();
        int nowTime=request.getNowTime();

		List<CreditTenderLogVO> creditTenderLogs = request.getCreditTenderLogs();
		if (creditTenderLogs != null && creditTenderLogs.size() == 1) {

			CreditTenderLogExample tenderLogExample = new CreditTenderLogExample();
			CreditTenderLogExample.Criteria tenderLogCra = tenderLogExample.createCriteria();
			tenderLogCra.andAssignNidEqualTo(assignOrderId).andUserIdEqualTo(userId);
			boolean tenderLogFlag = this.creditTenderLogMapper.deleteByExample(tenderLogExample) > 0 ? true : false;

			if (!tenderLogFlag) {
				throw new Exception("删除相应的承接log表失败，承接订单号：" + assignOrderId + ",用户userId:" + userId);
			}
			CreditTenderLogVO creditTenderLog = creditTenderLogs.get(0);
			// 债权结束标志位
			Integer debtEndFlag = 0;
			// 出让人userId
			int sellerUserId = creditTenderLog.getCreditUserId();
			// 原始出借订单号
			String tenderOrderId = creditTenderLog.getCreditTenderNid();
			// 项目编号
			String borrowNid = creditTenderLog.getBidNid();
			// 债转编号
			String creditNid = creditTenderLog.getCreditNid();
			// 出让人账户信息
			Account sellerAccount = this.getAccount(sellerUserId);
			if (sellerAccount == null) {
				throw new Exception("查询出让人账户信息失败，承接订单号：" + assignOrderId + ",用户userId:" + userId);
			}
			// 承接人账户信息
			Account assignAccount = this.getAccount(userId);
			if (sellerAccount == null) {
				throw new Exception("查询承接人账户信息失败，承接订单号：" + assignOrderId + ",用户userId:" + userId);
			}
			// 项目详情
			Borrow borrow = this.getBorrowByNid(borrowNid);
			if (borrow == null) {
				throw new Exception("查询borrow失败，承接订单号：" + assignOrderId + ",用户userId:" + userId);
			}
			// 还款方式
			String borrowStyle = borrow.getBorrowStyle();
			// 项目总期数
			Integer borrowPeriod = borrow.getBorrowPeriod()==null ? 1 : borrow.getBorrowPeriod();
			// 管理费率
			BigDecimal feeRate = StringUtils.isBlank(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
			// 差异费率
			BigDecimal differentialRate = StringUtils.isBlank(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
			// 初审时间
			int borrowVerifyTime = borrow.getVerifyTime()==null ? 0 : borrow.getVerifyTime();
			// 是否月标(true:月标, false:天标)
			boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
					|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
			// 管理费
			BigDecimal perManageSum = BigDecimal.ZERO;
			// 获取BorrowCredit信息
			List<BorrowCreditVO> borrowCreditList = request.getBorrowCreditList();
			// 5.更新borrow_credit
			if (borrowCreditList != null && borrowCreditList.size() == 1) {
				// 获取BorrowRecover信息
				BorrowRecoverExample borrowRecoverExample = new BorrowRecoverExample();
				BorrowRecoverExample.Criteria borrowRecoverCra = borrowRecoverExample.createCriteria();
				borrowRecoverCra.andBorrowNidEqualTo(creditTenderLog.getBidNid()).andNidEqualTo(creditTenderLog.getCreditTenderNid());
				List<BorrowRecover> borrowRecoverList = this.borrowRecoverMapper.selectByExample(borrowRecoverExample);

				BorrowCreditVO borrowCredit = borrowCreditList.get(0);
				borrowCredit.setCreditIncome(borrowCredit.getCreditIncome().add(creditTenderLog.getAssignPay()));// 总收入,本金+垫付利息
				borrowCredit.setCreditCapitalAssigned(borrowCredit.getCreditCapitalAssigned().add(creditTenderLog.getAssignCapital()));// 已认购本金
				borrowCredit.setCreditInterestAdvanceAssigned(borrowCredit.getCreditInterestAdvanceAssigned().add(creditTenderLog.getAssignInterestAdvance()));// 已垫付利息
				borrowCredit.setCreditInterestAssigned(borrowCredit.getCreditInterestAssigned().add(creditTenderLog.getAssignInterest()));// 已承接利息
				borrowCredit.setCreditFee(borrowCredit.getCreditFee().add(creditTenderLog.getCreditFee()));// 服务费
				borrowCredit.setAssignTime(GetDate.getNowTime10());// 认购时间
				borrowCredit.setAssignNum(borrowCredit.getAssignNum() + 1);// 出借次数
				// 完全承接的情况
				if (borrowCredit.getCreditCapitalAssigned().compareTo(borrowCredit.getCreditCapital()) == 0) {
						// add 合规数据上报 埋点 liubin 20181122 start
						// 推送数据到MQ 承接（完全）散
						JSONObject params = new JSONObject();
						params.put("creditNid", borrowCredit.getCreditNid()+"");
						params.put("flag", "1"); //1（散）2（智投）
						params.put("status", "2"); //2承接（完全）
						commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.UNDERTAKE_ALL_SUCCESS_TAG, UUID.randomUUID().toString(), params),
								MQConstant.HG_REPORT_DELAY_LEVEL);
						// add 合规数据上报 埋点 liubin 20181122 end

					if (borrowRecoverList != null && borrowRecoverList.size() == 1) {
						BorrowRecover borrowRecover = borrowRecoverList.get(0);
						// 调用银行结束债权接口
						try {
							boolean isDebtEndFlag = this.requestDebtEnd(borrowRecover, sellerBankAccount.getAccount());
							if (isDebtEndFlag) {
								// 债权结束成功
								debtEndFlag = 1;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						throw new Exception("未查询到相应的borrowRecover数据!" + "，用户userId：" + sellerUserId + "，出借订单号：" + tenderOrderId);
					}
					// 发送承接完成短信
					this.sendCreditFullMessage(borrowCredit,request.getWebUser(),request.getUserInfo());
					borrowCredit.setCreditStatus(2);
				}
				boolean borrowCreditFlag = borrowCreditMapper.updateByPrimaryKeySelective(CommonUtils.convertBean(borrowCredit, BorrowCredit.class)) > 0 ? true : false;
				if (!borrowCreditFlag) {
					throw new Exception("更新相应的borrowCredit表失败，承接订单号：" + assignOrderId + ",用户userId:" + userId);
				}
				// 1.插入credit_tender
				CreditTender creditTender = new CreditTender();
				creditTender.setAssignCreateDate(creditTenderLog.getAssignCreateDate());// 认购日期
				creditTender.setAssignPay(creditTenderLog.getAssignPay());// 支付金额
				creditTender.setCreditFee(creditTenderLog.getCreditFee());// 服务费
				creditTender.setAssignCapital(creditTenderLog.getAssignCapital());// 出借本金
				creditTender.setUserId(userId);// 用户名称
				creditTender.setCreditUserId(sellerUserId);// 出让人id
				creditTender.setStatus(0);// 状态
				creditTender.setBidNid(creditTenderLog.getBidNid());// 原标标号
				creditTender.setCreditNid(creditTenderLog.getCreditNid());// 债转标号
				creditTender.setCreditTenderNid(creditTenderLog.getCreditTenderNid());// 债转投标单号
				creditTender.setAssignNid(creditTenderLog.getAssignNid());// 认购单号
				creditTender.setAssignAccount(creditTenderLog.getAssignAccount());// 回收总额
				creditTender.setAssignInterest(creditTenderLog.getAssignInterest());// 债转利息
				creditTender.setAssignInterestAdvance(creditTenderLog.getAssignInterestAdvance());// 垫付利息
				creditTender.setAssignPrice(creditTenderLog.getAssignPrice());// 购买价格
				creditTender.setAssignRepayAccount(creditTenderLog.getAssignRepayAccount());// 已还总额
				creditTender.setAssignRepayCapital(creditTenderLog.getAssignRepayCapital());// 已还本金
				creditTender.setAssignRepayInterest(creditTenderLog.getAssignRepayInterest());// 已还利息
				creditTender.setAssignRepayEndTime(creditTenderLog.getAssignRepayEndTime());// 最后还款日
				creditTender.setAssignRepayLastTime(creditTenderLog.getAssignRepayLastTime());// 上次还款时间
				creditTender.setAssignRepayNextTime(creditTenderLog.getAssignRepayNextTime());// 下次还款时间
				creditTender.setAssignRepayYesTime(creditTenderLog.getAssignRepayYesTime());// 最终实际还款时间
				creditTender.setAssignRepayPeriod(creditTenderLog.getAssignRepayPeriod());// 还款期数
				creditTender.setAddIp(creditTenderLog.getAddIp());// ip
				creditTender.setClient(0);// 客户端
				creditTender.setAuthCode(authCode);// 银行存管新增授权码
				creditTender.setRecoverPeriod(borrowCredit.getRecoverPeriod());// 已还款期数

				// add by hesy  添加承接人承接时推荐人信息-- 开始
				if (spreadsUsers != null){
					if (userInfoCustomizeRefCj!=null) {
						creditTender.setInviteUserName(userInfoCustomizeRefCj.getUserName());
						creditTender.setInviteUserAttribute(userInfoCustomizeRefCj.getAttribute());
						creditTender.setInviteUserRegionname(userInfoCustomizeRefCj.getRegionName());
						creditTender.setInviteUserBranchname(userInfoCustomizeRefCj.getBranchName());
						creditTender.setInviteUserDepartmentname(userInfoCustomizeRefCj.getDepartmentName());
					}
				}else if (userInfoCustomize.getAttribute() == 2 || userInfoCustomize.getAttribute() ==3 ){
					creditTender.setInviteUserName(userInfoCustomize.getUserName());
					creditTender.setInviteUserAttribute(userInfoCustomize.getAttribute());
					creditTender.setInviteUserRegionname(userInfoCustomize.getRegionName());
					creditTender.setInviteUserBranchname(userInfoCustomize.getBranchName());
					creditTender.setInviteUserDepartmentname(userInfoCustomize.getDepartmentName());
				}

				// add by hesy  添加出让人承接时推荐人信息-- 开始
				if (spreadsUsersSeller != null) {
					// 查找用户推荐人详情信息
					if (userInfoCustomizeRefCr!=null) {
						creditTender.setInviteUserCreditName(userInfoCustomizeRefCr.getUserName());
						creditTender.setInviteUserCreditAttribute(userInfoCustomizeRefCr.getAttribute());
						creditTender.setInviteUserCreditRegionname(userInfoCustomizeRefCr.getRegionName());
						creditTender.setInviteUserCreditBranchname(userInfoCustomizeRefCr.getBranchName());
						creditTender.setInviteUserCreditDepartmentname(userInfoCustomizeRefCr.getDepartmentName());
					}

				}else if(userInfoCustomizeSeller.getAttribute() == 2 || userInfoCustomizeSeller.getAttribute() ==3 ){
					creditTender.setInviteUserCreditName(userInfoCustomizeSeller.getUserName());
					creditTender.setInviteUserCreditAttribute(userInfoCustomizeSeller.getAttribute());
					creditTender.setInviteUserCreditRegionname(userInfoCustomizeSeller.getRegionName());
					creditTender.setInviteUserCreditBranchname(userInfoCustomizeSeller.getBranchName());
					creditTender.setInviteUserCreditDepartmentname(userInfoCustomizeSeller.getDepartmentName());
				}

				// creditTender插入数据库
				boolean tenderLog = this.creditTenderMapper.insertSelective(creditTender) > 0 ? true : false;
				if (!tenderLog) {
					throw new Exception("插入credittender表失败，承接订单号：" + assignOrderId + ",用户userId:" + userId);
				}
				// 2.处理承接人account表和account_list表
				// 承接人账户信息
				Account assignAccountNew = new Account();
				assignAccountNew.setUserId(userId);
				assignAccountNew.setBankBalance(creditTender.getAssignPay());
				assignAccountNew.setBankTotal(creditTender.getAssignCapital().add(creditTender.getAssignInterest()).subtract(creditTender.getAssignPay()));
				assignAccountNew.setBankAwait(creditTender.getAssignAccount());// 银行待收+承接金额
				assignAccountNew.setBankAwaitCapital(creditTender.getAssignCapital());// 银行待收本金+承接本金
				assignAccountNew.setBankAwaitInterest(creditTender.getAssignInterest());// 银行待收利息+承接利息
				assignAccountNew.setBankInvestSum(creditTender.getAssignCapital());// 累计出借+承接本金
				// 更新账户信息
				boolean isAccountCrediterFlag = this.adminAccountCustomizeMapper.updateCreditAssignSuccess(assignAccountNew) > 0 ? true : false;
				if (!isAccountCrediterFlag) {
					throw new Exception("承接人承接债转后,更新承接人账户账户信息失败!承接订单号：" + assignOrderId + ",用户userId:" + userId);
				}
				// 重新获取出让人用户账户信息
				assignAccount = this.getAccount(userId);
				AccountList assignAccountList = new AccountList();
				assignAccountList.setNid(creditTender.getAssignNid());
				assignAccountList.setUserId(userId);
				assignAccountList.setAmount(creditTender.getAssignPay());
				assignAccountList.setType(2);
				assignAccountList.setTrade("creditassign");
				assignAccountList.setTradeCode("balance");
				assignAccountList.setTotal(assignAccount.getTotal());
				assignAccountList.setBalance(assignAccount.getBalance());
				assignAccountList.setBankBalance(assignAccount.getBankBalance());
				assignAccountList.setBankAwait(assignAccount.getBankAwait());
				assignAccountList.setBankAwaitCapital(assignAccount.getBankAwaitCapital());
				assignAccountList.setBankAwaitInterest(assignAccount.getBankAwaitInterest());
				assignAccountList.setBankInvestSum(assignAccount.getBankInvestSum());
				assignAccountList.setBankInterestSum(assignAccount.getBankInterestSum());
				assignAccountList.setBankFrost(assignAccount.getBankFrost());
				assignAccountList.setBankInterestSum(assignAccount.getBankInterestSum());
				assignAccountList.setPlanBalance(assignAccount.getPlanBalance());//汇计划账户可用余额
				assignAccountList.setPlanFrost(assignAccount.getPlanFrost());;
				assignAccountList.setBankTotal(assignAccount.getBankTotal());
				assignAccountList.setSeqNo(String.valueOf(creditTenderLog.getSeqNo()));
				assignAccountList.setTxDate(creditTenderLog.getTxDate());
				assignAccountList.setTxTime(creditTenderLog.getTxTime());
				assignAccountList.setBankSeqNo(String.valueOf(creditTenderLog.getTxDate()) + String.valueOf(creditTenderLog.getTxTime()) + String.valueOf(creditTenderLog.getSeqNo()));
				assignAccountList.setAccountId(assignBankAccount.getAccount());// 承接人电子账户号
				assignAccountList.setFrost(assignAccount.getFrost());
				assignAccountList.setAwait(assignAccount.getAwait());
				assignAccountList.setRepay(assignAccount.getRepay());
				assignAccountList.setRemark("购买债权");
				assignAccountList.setCreateTime(GetDate.getDate(nowTime));
				assignAccountList.setOperator(String.valueOf(userId));
				assignAccountList.setIp(creditTender.getAddIp());
				assignAccountList.setWeb(0);
				assignAccountList.setIsBank(1);
				assignAccountList.setCheckStatus(0);
				// 插入交易明细
				boolean assignAccountListFlag = this.accountListMapper.insertSelective(assignAccountList) > 0 ? true : false;
				if (!assignAccountListFlag) {
					throw new Exception("承接债转后,插入承接人交易明细accountList失败!承接订单号：" + assignOrderId + ",用户userId:" + userId);
				}
				// 3.处理出让人account表和account_list表
				Account sellerAccountNew = new Account();
				sellerAccountNew.setUserId(sellerUserId);
				sellerAccountNew.setBankBalance(creditTender.getAssignPay().subtract(creditTender.getCreditFee()));// 银行可用余额
				sellerAccountNew.setBankTotal(creditTender.getAssignPay().subtract(creditTender.getCreditFee()).subtract(creditTender.getAssignAccount()));// 银行总资产
				sellerAccountNew.setBankAwait(creditTender.getAssignAccount());// 出让人待收金额
				sellerAccountNew.setBankAwaitCapital(creditTender.getAssignCapital());// 出让人待收本金
				sellerAccountNew.setBankAwaitInterest(creditTender.getAssignInterest());// 出让人待收利息
				sellerAccountNew.setBankInterestSum(creditTender.getAssignInterestAdvance());// 出让人累计收益
				sellerAccountNew.setBankBalanceCash(creditTender.getAssignPay().subtract(creditTender.getCreditFee()));
				// 更新账户信息
				boolean isAccountFlag = this.adminAccountCustomizeMapper.updateCreditSellerSuccess(sellerAccountNew) > 0 ? true : false;
				if (!isAccountFlag) {
					throw new Exception("出借人承接债转后,更新出让人账户账户信息失败!承接订单号：" + assignOrderId);
				}
				// 重新获取用户账户信息
				sellerAccount = this.getAccount(sellerUserId);
				if(sellerAccount==null){
					throw new Exception("重新获取用户账户信息错误!承接订单号：" + assignOrderId);
				}
				AccountList sellerAccountList = new AccountList();
				sellerAccountList.setNid(creditTender.getAssignNid());
				sellerAccountList.setUserId(creditTender.getCreditUserId());
				sellerAccountList.setAmount(creditTender.getAssignPay().subtract(creditTender.getCreditFee()));
				sellerAccountList.setType(1);
				sellerAccountList.setTrade("creditsell");
				sellerAccountList.setTradeCode("balance");
				sellerAccountList.setTotal(sellerAccount.getTotal());
				sellerAccountList.setBalance(sellerAccount.getBalance());
				sellerAccountList.setBankBalance(sellerAccount.getBankBalance());
				sellerAccountList.setBankAwait(sellerAccount.getBankAwait());
				sellerAccountList.setBankAwaitCapital(sellerAccount.getBankAwaitCapital());
				sellerAccountList.setBankAwaitInterest(sellerAccount.getBankAwaitInterest());
				sellerAccountList.setBankInterestSum(sellerAccount.getBankInterestSum());
				sellerAccountList.setBankInvestSum(sellerAccount.getBankInvestSum());
				sellerAccountList.setBankFrost(sellerAccount.getBankFrost());
				sellerAccountList.setBankTotal(sellerAccount.getBankTotal());
				sellerAccountList.setPlanBalance(sellerAccount.getPlanBalance());//汇计划账户可用余额
				sellerAccountList.setPlanFrost(sellerAccount.getPlanFrost());
				sellerAccountList.setSeqNo(String.valueOf(creditTenderLog.getSeqNo()));
				sellerAccountList.setTxDate(creditTenderLog.getTxDate());
				sellerAccountList.setTxTime(creditTenderLog.getTxTime());
				sellerAccountList.setBankSeqNo(String.valueOf(creditTenderLog.getTxDate()) + String.valueOf(creditTenderLog.getTxTime()) + String.valueOf(creditTenderLog.getSeqNo()));
				sellerAccountList.setAccountId(sellerBankAccount.getAccount());// 出让人电子账户号
				sellerAccountList.setFrost(sellerAccount.getFrost());
				sellerAccountList.setAwait(sellerAccount.getAwait());
				sellerAccountList.setRepay(sellerAccount.getRepay());
				sellerAccountList.setRemark("出让债权");
				sellerAccountList.setCreateTime(GetDate.getDate(nowTime));
				sellerAccountList.setOperator(String.valueOf(creditTenderLog.getCreditUserId()));
				sellerAccountList.setIp(creditTenderLog.getAddIp());
				sellerAccountList.setWeb(0);
				sellerAccountList.setIsBank(1);
				sellerAccountList.setCheckStatus(0);
				boolean sellerAccountListFlag = this.accountListMapper.insertSelective(sellerAccountList) > 0 ? true : false;// 插入交易明细
				if (!sellerAccountListFlag) {
					throw new Exception("承接债转后,插入出让人交易明细accountList失败!承接订单号：" + assignOrderId);
				}
				// 4.添加网站收支明细
				// 服务费大于0时,插入网站收支明细
				if (creditTender.getCreditFee().compareTo(BigDecimal.ZERO) > 0) {
					// 插入网站收支明细记录
					AccountWebListVO accountWebList = new AccountWebListVO();
					accountWebList.setOrdid(assignOrderId);
					accountWebList.setBorrowNid(creditTender.getBidNid());
					accountWebList.setAmount(Double.valueOf(creditTender.getCreditFee().toString()));
					accountWebList.setType(1);
					accountWebList.setTrade("CREDITFEE");
					accountWebList.setTradeType("债转服务费");
					accountWebList.setUserId(creditTender.getUserId());
					accountWebList.setUsrcustid(assignBankAccount.getAccount());

					EmployeeCustomizeResponse employeeCustomizeResponse = request.getEmployeeCustomizeResponse();
					if(employeeCustomizeResponse!=null && employeeCustomizeResponse.getResult()!=null) {
						EmployeeCustomizeVO employeeCustomize=employeeCustomizeResponse.getResult();
						accountWebList.setRegionName(employeeCustomize.getRegionName());
						accountWebList.setBranchName(employeeCustomize.getBranchName());
						accountWebList.setDepartmentName(employeeCustomize.getDepartmentName());
						accountWebList.setTruename(employeeCustomizeResponse.getTruename());
					}	
					accountWebList.setRemark(creditTender.getCreditNid());
					accountWebList.setNote(null);
					accountWebList.setCreateTime(nowTime);
					accountWebList.setOperator(null);
					accountWebList.setFlag(1);

					commonProducer.messageSend(new MessageContent(MQConstant.ACCOUNT_WEB_LIST_TOPIC, UUID.randomUUID().toString(), accountWebList));
				}
				// 6.更新Borrow_recover
				if (borrowRecoverList != null && borrowRecoverList.size() == 1) {
					BorrowRecover borrowRecover = borrowRecoverList.get(0);
					// 不分期
					if (!isMonth) {
						// 管理费
						BigDecimal perManage = BigDecimal.ZERO;
						// 如果是完全承接
						if (borrowCredit.getCreditStatus() == 2) {
							perManage = borrowRecover.getRecoverFee().subtract(borrowRecover.getCreditManageFee());
						} else {
							// 按月计息，到期还本还息end
							if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
								perManage = AccountManagementFeeUtils.getDueAccountManagementFeeByMonth(creditTender.getAssignCapital(), feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
							}
							// 按天计息到期还本还息
							else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
								perManage = AccountManagementFeeUtils.getDueAccountManagementFeeByDay(creditTender.getAssignCapital(), feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
							}
						}
						perManageSum = perManage;
						CreditRepay creditRepay = new CreditRepay();
						creditRepay.setUserId(userId);// 用户名称
						creditRepay.setCreditUserId(creditTender.getCreditUserId());// 出让人id
						creditRepay.setStatus(0);// 状态
						creditRepay.setBidNid(creditTender.getBidNid());// 原标标号
						creditRepay.setCreditNid(creditTender.getCreditNid());// 债转标号
						creditRepay.setCreditTenderNid(creditTender.getCreditTenderNid());// 债转投标单号
						creditRepay.setAssignNid(creditTender.getAssignNid());// 认购单号
						creditRepay.setAssignCapital(creditTender.getAssignCapital());// 应还本金
						creditRepay.setAssignAccount(creditTender.getAssignAccount());// 应还总额
						creditRepay.setAssignInterest(creditTender.getAssignInterest());// 应还利息
						creditRepay.setAssignInterestAdvance(creditTender.getAssignInterestAdvance());// 垫付利息
						creditRepay.setAssignPrice(creditTender.getAssignPrice());// 购买价格
						creditRepay.setAssignPay(creditTender.getAssignPay());// 支付金额
						creditRepay.setAssignRepayAccount(BigDecimal.ZERO);// 已还总额
						creditRepay.setAssignRepayCapital(BigDecimal.ZERO);// 已还本金
						creditRepay.setAssignRepayInterest(BigDecimal.ZERO);// 已还利息
						creditRepay.setAssignRepayEndTime(creditTender.getAssignRepayEndTime());// 最后还款日
						creditRepay.setAssignRepayLastTime(creditTender.getAssignRepayLastTime());// 上次还款时间
						creditRepay.setAssignRepayNextTime(creditTender.getAssignRepayNextTime());// 下次还款时间
						creditRepay.setAssignRepayYesTime(0);// 最终实际还款时间
						creditRepay.setAssignRepayPeriod(1);// 还款期数
						creditRepay.setAssignCreateDate(creditTender.getAssignCreateDate());// 认购日期
						creditRepay.setCreateTime(GetDate.getDate(nowTime));// 添加时间
						creditRepay.setAddIp(creditTender.getAddIp());// ip
						creditRepay.setClient(0);// 客户端
						creditRepay.setRecoverPeriod(1);// 原标还款期数
						creditRepay.setAdvanceStatus(0);
						creditRepay.setChargeDays(0);
						creditRepay.setChargeInterest(BigDecimal.ZERO);
						creditRepay.setDelayDays(0);
						creditRepay.setDelayInterest(BigDecimal.ZERO);
						creditRepay.setLateDays(0);
						creditRepay.setLateInterest(BigDecimal.ZERO);
						creditRepay.setManageFee(perManage);// 管理费
						creditRepay.setAuthCode(authCode);// 授权码
						creditRepayMapper.insertSelective(creditRepay);
					} else {
						// 管理费
						if (creditTender.getAssignRepayPeriod() > 0) {
							// 先息后本
							if (CalculatesUtil.STYLE_ENDMONTH.equals(borrowStyle)) {
								// 总的利息
								BigDecimal sumMonthInterest = BigDecimal.ZERO;
								// 每月偿还的利息
								BigDecimal perMonthInterest = BigDecimal.ZERO;
								for (int i = 1; i <= creditTender.getAssignRepayPeriod(); i++) {
									BigDecimal perManage = BigDecimal.ZERO;
									int periodNow = borrow.getBorrowPeriod() - borrowRecover.getRecoverPeriod() + i;
									// 获取borrow_recover_plan更新每次还款时间
									BorrowRecoverPlanExample borrowRecoverPlanExample = new BorrowRecoverPlanExample();
									BorrowRecoverPlanExample.Criteria borrowRecoverPlanCra = borrowRecoverPlanExample.createCriteria();
									borrowRecoverPlanCra.andBorrowNidEqualTo(creditTender.getBidNid()).andNidEqualTo(creditTender.getCreditTenderNid()).andRecoverPeriodEqualTo(periodNow);
									List<BorrowRecoverPlan> borrowRecoverPlanList = this.borrowRecoverPlanMapper.selectByExample(borrowRecoverPlanExample);
									if (borrowRecoverPlanList != null && borrowRecoverPlanList.size() > 0) {
										BorrowRecoverPlan borrowRecoverPlan = borrowRecoverPlanList.get(0);
										CreditRepay creditRepay = new CreditRepay();
										if (borrowCredit.getCreditStatus() == 2) {
											// 如果是最后一笔
											perManage = borrowRecoverPlan.getRecoverFee().subtract(borrowRecoverPlan.getCreditManageFee());
											perMonthInterest = borrowRecoverPlan.getRecoverInterest().subtract(borrowRecoverPlan.getCreditInterest());
											if (i == creditTender.getAssignRepayPeriod()) {
												creditRepay.setAssignCapital(creditTender.getAssignCapital());// 应还本金
												creditRepay.setAssignAccount(creditTender.getAssignCapital().add(perMonthInterest));// 应还总额
												creditRepay.setAssignInterest(perMonthInterest);// 应还利息
											} else {
												creditRepay.setAssignCapital(BigDecimal.ZERO);// 应还本金
												creditRepay.setAssignAccount(perMonthInterest);// 应还总额
												creditRepay.setAssignInterest(perMonthInterest);// 应还利息
											}
										} else {
											// 如果不是最后一笔
											if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
												if (periodNow == borrowPeriod.intValue()) {
													perManage = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate, borrowPeriod,
															borrowPeriod, differentialRate, 1, borrowVerifyTime);
												} else {
													perManage = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate, borrowPeriod,
															borrowPeriod, differentialRate, 0, borrowVerifyTime);
												}
											}
											if (i == creditTender.getAssignRepayPeriod()) {
												BigDecimal lastPeriodInterest = creditTender.getAssignInterest().subtract(sumMonthInterest);
												creditRepay.setAssignCapital(creditTender.getAssignCapital());// 应还本金
												creditRepay.setAssignAccount(creditTender.getAssignCapital().add(lastPeriodInterest));// 应还总额
												creditRepay.setAssignInterest(lastPeriodInterest);// 应还利息
											} else {
												// 每月偿还的利息
												perMonthInterest = BeforeInterestAfterPrincipalUtils.getPerTermInterest(creditTender.getAssignCapital(),
														borrow.getBorrowApr().divide(new BigDecimal(100)), borrow.getBorrowPeriod(), borrow.getBorrowPeriod());
												sumMonthInterest = sumMonthInterest.add(perMonthInterest);// 总的还款利息
												creditRepay.setAssignCapital(BigDecimal.ZERO);// 应还本金
												creditRepay.setAssignAccount(perMonthInterest);// 应还总额
												creditRepay.setAssignInterest(perMonthInterest);// 应还利息
											}
										}

										creditRepay.setUserId(userId);// 用户名称
										creditRepay.setCreditUserId(creditTender.getCreditUserId());// 出让人id
										creditRepay.setStatus(0);// 状态
										creditRepay.setBidNid(creditTender.getBidNid());// 原标标号
										creditRepay.setCreditNid(creditTender.getCreditNid());// 债转标号
										creditRepay.setCreditTenderNid(creditTender.getCreditTenderNid());// 债转投标单号
										creditRepay.setAssignNid(creditTender.getAssignNid());// 认购单号

										if (i == 1) {
											creditRepay.setAssignInterestAdvance(creditTender.getAssignInterestAdvance());// 垫付利息
										} else {
											creditRepay.setAssignInterestAdvance(BigDecimal.ZERO);// 垫付利息
										}
										creditRepay.setAssignPrice(creditTender.getAssignPrice());// 购买价格
										creditRepay.setAssignPay(creditTender.getAssignPay());// 支付金额
										creditRepay.setAssignRepayAccount(BigDecimal.ZERO);// 已还总额
										creditRepay.setAssignRepayCapital(BigDecimal.ZERO);// 已还本金
										creditRepay.setAssignRepayInterest(BigDecimal.ZERO);// 已还利息
										creditRepay.setAssignRepayYesTime(0);// 最终实际还款时间
										creditRepay.setAssignRepayPeriod(i);// 还款期数
										creditRepay.setAssignCreateDate(creditTender.getAssignCreateDate());// 认购日期
										creditRepay.setCreateTime(GetDate.getDate(nowTime));// 添加时间
										creditRepay.setAddIp(creditTender.getAddIp());// ip
										creditRepay.setClient(0);// 客户端
										creditRepay.setManageFee(BigDecimal.ZERO);// 管理费
										creditRepay.setAuthCode(authCode);// 授权码
										creditRepay.setAdvanceStatus(0);
										creditRepay.setChargeDays(0);
										creditRepay.setChargeInterest(BigDecimal.ZERO);
										creditRepay.setDelayDays(0);
										creditRepay.setDelayInterest(BigDecimal.ZERO);
										creditRepay.setLateDays(0);
										creditRepay.setLateInterest(BigDecimal.ZERO);
										creditRepay.setAssignRepayEndTime(creditTender.getAssignRepayEndTime());// 最后还款日
										creditRepay.setAssignRepayLastTime(creditTender.getAssignRepayLastTime());// 上次还款时间
										creditRepay.setAssignRepayNextTime(borrowRecoverPlan.getRecoverTime());// 下次还款时间
										creditRepay.setRecoverPeriod(borrowRecoverPlan.getRecoverPeriod());// 原标还款期数
										creditRepay.setManageFee(perManage);// 管理费
										creditRepayMapper.insertSelective(creditRepay);
										// 更新borrowRecover
										// 承接本金
										borrowRecoverPlan.setCreditAmount(borrowRecoverPlan.getCreditAmount().add(creditRepay.getAssignCapital()));
										// 垫付利息
										borrowRecoverPlan.setCreditInterestAmount(borrowRecoverPlan.getCreditInterestAmount().add(creditRepay.getAssignInterestAdvance()));
										// 债转状态
										borrowRecoverPlan.setCreditStatus(borrowCredit.getCreditStatus());
										borrowRecoverPlan.setCreditManageFee(borrowRecoverPlan.getCreditManageFee().add(perManage));
										borrowRecoverPlan.setCreditInterest(borrowRecoverPlan.getCreditInterest().add(perMonthInterest));//
										this.borrowRecoverPlanMapper.updateByPrimaryKeySelective(borrowRecoverPlan);
									}
									perManageSum = perManageSum.add(perManage);
								}
							}
						}
					}
					borrowRecover.setCreditAmount(borrowRecover.getCreditAmount().add(creditTender.getAssignCapital()));
					borrowRecover.setCreditInterestAmount(borrowRecover.getCreditInterestAmount().add(creditTender.getAssignInterestAdvance()));
					borrowRecover.setCreditStatus(borrowCredit.getCreditStatus());
					borrowRecover.setCreditManageFee(borrowRecover.getCreditManageFee().add(perManageSum));// 已收债转管理费
					borrowRecover.setDebtStatus(debtEndFlag);// 债权是否结束状态
					boolean borrowRecoverFlag = borrowRecoverMapper.updateByPrimaryKeySelective(borrowRecover) > 0 ? true : false;
					if (!borrowRecoverFlag) {
						throw new Exception("更新相应的放款信息表borrowrecover失败!" + "[出借订单号：" + tenderOrderId + "]");
					}
					// add 合规数据上报 埋点 liubin 20181122 start
					JSONObject params = new JSONObject();
					params.put("assignOrderId", creditTender.getAssignNid());
					params.put("flag", "1");//1（散）2（智投）
					params.put("status", "1"); //1承接（每笔）
					// 推送数据到MQ 承接（每笔）散
					commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.UNDERTAKE_SINGLE_SUCCESS_TAG, UUID.randomUUID().toString(), params),
							MQConstant.HG_REPORT_DELAY_LEVEL);
					// add 合规数据上报 埋点 liubin 20181122 end

					//向承接人推送承接成功消息
					this.sendCreditSuccessMessage(creditTender,request.getWebUser(),request.getUserInfo());
					return true;
				} else {
					throw new Exception("未查询到相应的borrowRecover数据!" + "，用户userId：" + sellerUserId + "，出借订单号：" + tenderOrderId);
				}
			} else {
				throw new Exception("未查询到相应的borrowCredit数据!" + "，用户userId：" + sellerUserId + "，出借订单号：" + tenderOrderId);
			}
		} else {
			throw new Exception("查询相应的承接log表失败，承接订单号：" + assignOrderId);
		}

	}


	private void sendCreditSuccessMessage(CreditTender creditTender, UserVO webUser, UserInfoVO userInfo) throws MQException {
		if (webUser != null) {
			Map<String, String> param = new HashMap<String, String>();
			if (userInfo.getTruename() != null && userInfo.getTruename().length() > 1) {
				param.put("val_name", userInfo.getTruename().substring(0, 1));
			} else {
				param.put("val_name", userInfo.getTruename());
			}
			if (userInfo.getSex() == 1) {
				param.put("val_sex", "先生");
			} else if (userInfo.getSex() == 2) {
				param.put("val_sex", "女士");
			} else {
				param.put("val_sex", "");
			}
			param.put("val_title", creditTender.getCreditNid() + "");
			param.put("val_balance", creditTender.getAssignPay() + "");
			param.put("val_profit", creditTender.getAssignInterest() + "");
			param.put("val_amount", creditTender.getAssignAccount() + "");
			commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, UUID.randomUUID().toString(), param));
		}
	}

	/**
	 * 向承接人推送承接成功消息
	 * @param request
	 * @return
	 */
	@Override
	public List<BorrowCredit> getBorrowCreditList(BorrowCreditRequest request) {
		String creditNid=request.getCreditNid();
		int sellerUserId=request.getSellerUserId();
		String tenderOrderId = request.getTenderOrderId();
		BorrowCreditExample borrowCreditExample = new BorrowCreditExample();
		BorrowCreditExample.Criteria borrowCreditCra = borrowCreditExample.createCriteria();
		borrowCreditCra.andCreditNidEqualTo(Integer.parseInt(creditNid)).andCreditUserIdEqualTo(sellerUserId).andTenderNidEqualTo(tenderOrderId);
		return this.borrowCreditMapper.selectByExample(borrowCreditExample);
	}


	@Override
	public List<CreditTender> getCreditTenderList(CreditTenderRequest request) {
		CreditTenderExample creditTenderExample = new CreditTenderExample();
		CreditTenderExample.Criteria cra = creditTenderExample.createCriteria();
		if(StringUtils.isNotBlank(request.getAssignNid())) {
			cra.andAssignNidEqualTo(request.getAssignNid());
		}
		if(StringUtils.isNotBlank(request.getBidNid())) {
			cra.andBidNidEqualTo(request.getBidNid());
		}
		if(StringUtils.isNotBlank(request.getCreditNid())) {
			cra.andCreditNidEqualTo(request.getCreditNid());
		}
		if(StringUtils.isNotBlank(request.getCreditTenderNid())) {
			cra.andCreditTenderNidEqualTo(request.getCreditTenderNid());
		}
		return this.creditTenderMapper.selectByExample(creditTenderExample);
	}

	@Override
	public List<TenderToCreditDetailCustomize> selectWebCreditTenderDetailForContract(Map<String, Object> params) {
		return this.tenderCreditCustomizeMapper.selectWebCreditTenderDetailForContract(params);
	}

	@Override
	public List<TenderToCreditDetailCustomize> selectHJHWebCreditTenderDetail(Map<String, Object> params) {
		//查汇计划债转详情
		return tenderCreditCustomizeMapper.selectHJHWebCreditTenderDetail(params);
	}

	/**
	 * 获取我要转让页面的金额
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public CreditPageVO selectCreditPageMoneyTotal(Integer userId) {
		Map<String, Object> params = new HashedMap();
		params.put("userId",userId);
		params.put("nowTime",LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
		BigDecimal canCreditMoney = tenderCreditCustomizeMapper.selectCanCreditMoneyTotal(params);
		BigDecimal inCreditMoney = tenderCreditCustomizeMapper.selectInCreditMoneyTotal(params);
		BigDecimal creditSuccessMoney = tenderCreditCustomizeMapper.selectCreditSuccessMoneyTotal(params);

		CreditPageVO result = new CreditPageVO();
		result.setCanCreditMoney(canCreditMoney);
		result.setCreditSuccessMoney(creditSuccessMoney);
		result.setInCreditMoney(inCreditMoney);
		return result;
	}

	/**
	 * 查询我可转让列表数量
	 *
	 * @param request
	 * @return
	 */
	@Override
	public int searchCreditListCount(MyCreditListQueryRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", request.getUserId());
		params.put("nowTime", GetDate.getNowTime10());
		int total = tenderCreditCustomizeMapper.countTenderToCredit(params);
		return total;
	}

	/**
	 * 查询我可转让列表数量
	 *
	 * @param request
	 * @return
	 */
	@Override
	public List<TenderCreditCustomize> searchCreditList(MyCreditListQueryRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", request.getUserId());
		params.put("tenderTimeSort", request.getTenderTimeSort());
		params.put("creditAccountSort", request.getCreditAccountSort());
		params.put("tenderPeriodSort", request.getTenderPeriodSort());
		params.put("remainDaysSort", request.getRemainDaysSort());
		params.put("limitStart", request.getLimitStart());
		params.put("limitEnd", request.getLimitEnd());
		params.put("nowTime", GetDate.getNowTime10());
		List<TenderCreditCustomize> list = tenderCreditCustomizeMapper.selectTenderToCreditList(params);
		return list;
	}

	/**
	 * 查询债转详情
	 *
	 * @param userId
	 * @param borrowNid
	 * @param tenderNid
	 * @return
	 */
	@Override
	public TenderCreditCustomize selectTenderToCreditDetail(Integer userId, String borrowNid, String tenderNid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("nowTime", GetDate.getNowTime10());
		params.put("borrowNid", borrowNid);
		params.put("tenderNid", tenderNid);
		List<TenderCreditCustomize> list = tenderCreditCustomizeMapper.selectTenderToCreditList(params);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}

	/**
	 * 债转详细预计服务费计算
	 *
	 * @param borrowNid
	 * @param tenderNid
	 * @return
	 */
	@Override
	public ExpectCreditFeeVO selectExpectCreditFee(String borrowNid, String tenderNid) {
		String creditDiscount = "0.5";
		int nowTime = GetDate.getNowTime10();
		ExpectCreditFeeVO result = new ExpectCreditFeeVO();
		// 获取借款信息
		BorrowExample borrowExample = new BorrowExample();
		BorrowExample.Criteria borrowCra = borrowExample.createCriteria();
		borrowCra.andBorrowNidEqualTo(borrowNid);
		List<Borrow> borrowList = this.borrowMapper.selectByExample(borrowExample);
		// 获取borrow_recover数据
		BorrowRecoverExample borrowRecoverExample = new BorrowRecoverExample();
		BorrowRecoverExample.Criteria borrowRecoverCra = borrowRecoverExample.createCriteria();
		borrowRecoverCra.andBorrowNidEqualTo(borrowNid).andNidEqualTo(tenderNid);
		List<BorrowRecover> borrowRecoverList = this.borrowRecoverMapper.selectByExample(borrowRecoverExample);
		// 债转本息
		BigDecimal creditAccount = BigDecimal.ZERO;
		// 债转期全部利息
		BigDecimal creditInterest = BigDecimal.ZERO;
		// 垫付利息 垫息总额=债权本金*年化收益÷360*融资期限-债权本金*年化收益÷360*剩余期限
		BigDecimal assignInterestAdvance = BigDecimal.ZERO;
		// 债转利息
		BigDecimal assignPayInterest = BigDecimal.ZERO;
		// 实付金额 承接本金*（1-折价率）+应垫付利息
		BigDecimal assignPay = BigDecimal.ZERO;
		// 预计收益 承接人债转本息—实付金额
		BigDecimal assignInterest = BigDecimal.ZERO;
		// 预计收益 出让人预期收益 =本金+本金持有期利息-本金*折让率-服务费
		BigDecimal expectInterest = BigDecimal.ZERO;
		// 可转本金
		BigDecimal creditCapital = BigDecimal.ZERO;
		// 折后价格
		BigDecimal creditPrice = BigDecimal.ZERO;
		// 服务费
		BigDecimal creditFee = BigDecimal.ZERO;
		if (borrowList != null && borrowList.size() > 0) {
			Borrow borrow = borrowList.get(0);
			String borrowStyle = borrow.getBorrowStyle();
			// 年利率
			BigDecimal yearRate = borrow.getBorrowApr().divide(new BigDecimal("100"));
			if (borrowRecoverList != null && borrowRecoverList.size() > 0) {
				BorrowRecover borrowRecover = borrowRecoverList.get(0);
				// 可转本金
				creditCapital = borrowRecover.getRecoverCapital().subtract(borrowRecover.getRecoverCapitalYes()).subtract(borrowRecover.getCreditAmount());
				// 折后价格
				creditPrice = creditCapital.multiply(new BigDecimal(1).subtract(new BigDecimal(creditDiscount).divide(new BigDecimal(100)))).setScale(2, BigDecimal.ROUND_DOWN);
				// 到期还本还息和按天计息，到期还本还息
				if (borrowStyle.equals(CalculatesUtil.STYLE_END) || borrowStyle.equals(CalculatesUtil.STYLE_ENDDAY)) {
					int lastDays = 0;
					try {
						String nowDate = GetDate.getDateTimeMyTimeInMillis(nowTime);
						String recoverDate = GetDate.getDateTimeMyTimeInMillis(Integer.valueOf(borrowRecover.getRecoverTime()));
						lastDays = GetDate.daysBetween(nowDate, recoverDate);
					} catch (Exception e) {
						throw new RuntimeException("债转日期错误");
					}
					// 剩余天数
					// 按天
					if (borrowStyle.equals(CalculatesUtil.STYLE_ENDDAY)) {
						// 债转本息
						creditAccount = DuePrincipalAndInterestUtils.getDayPrincipalInterest(creditCapital, yearRate, borrow.getBorrowPeriod());
						// 债转期全部利息
						creditInterest = DuePrincipalAndInterestUtils.getDayInterest(creditCapital, yearRate, borrow.getBorrowPeriod());
						// 垫付利息 垫息总额=债权本金*年化收益÷360*融资期限-债权本金*年化收益÷360*剩余期限
						assignInterestAdvance = DuePrincipalAndInterestUtils.getDayAssignInterestAdvance(creditCapital, yearRate, borrow.getBorrowPeriod(), new BigDecimal(lastDays + ""));
						// 债转利息
						assignPayInterest = creditInterest;
						// 实付金额 承接本金*（1-折价率）+应垫付利息
						assignPay = creditPrice.add(assignInterestAdvance);
						// 预计收益 承接人债转本息—实付金额   计算出借收益
						assignInterest = creditAccount.subtract(assignPay);
						// 预计收益 出让人预期收益 =本金+本金持有期利息-本金*折让率-服务费
						expectInterest = creditCapital.add(assignInterestAdvance).subtract(creditCapital.multiply(new BigDecimal(creditDiscount).divide(new BigDecimal(100))))
								.subtract(assignPay.multiply(BigDecimal.valueOf(0.01)).setScale(2, BigDecimal.ROUND_DOWN));
					} else {// 按月
						// 债转本息
						creditAccount = DuePrincipalAndInterestUtils.getMonthPrincipalInterest(creditCapital, yearRate, borrow.getBorrowPeriod());
						// 债转期全部利息
						creditInterest = DuePrincipalAndInterestUtils.getMonthInterest(creditCapital, yearRate, borrow.getBorrowPeriod());
						// 垫付利息 垫息总额=债权本金*年化收益÷12*融资期限-债权本金*年化收益÷360*剩余天数
						assignInterestAdvance = DuePrincipalAndInterestUtils.getMonthAssignInterestAdvance(creditCapital, yearRate, borrow.getBorrowPeriod(), new BigDecimal(lastDays + ""));
						// 债转利息
						assignPayInterest = creditInterest;
						// 实付金额 承接本金*（1-折价率）+应垫付利息
						assignPay = creditPrice.add(assignInterestAdvance);
						// 预计收益 承接人债转本息—实付金额   计算出借收益
						assignInterest = creditAccount.subtract(assignPay);
						// 预计收益 出让人预期收益 =本金+本金持有期利息-本金*折让率-服务费
						expectInterest = creditCapital.add(assignInterestAdvance).subtract(creditCapital.multiply(new BigDecimal(creditDiscount).divide(new BigDecimal(100))))
								.subtract(assignPay.multiply(BigDecimal.valueOf(0.01)).setScale(2, BigDecimal.ROUND_DOWN));
					}
				}
				// 等额本息和等额本金和先息后本
				if (borrowStyle.equals(CalculatesUtil.STYLE_MONTH) || borrowStyle.equals(CalculatesUtil.STYLE_PRINCIPAL) || borrowStyle.equals(CalculatesUtil.STYLE_ENDMONTH)) {
					String bidNid = borrow.getBorrowNid();

					BorrowRepayPlanExample example = new BorrowRepayPlanExample();
					BorrowRepayPlanExample.Criteria cra = example.createCriteria();
					cra.andBorrowNidEqualTo(bidNid).andRepayPeriodEqualTo(borrow.getBorrowPeriod() - borrowRecover.getRecoverPeriod() + 1);
					List<BorrowRepayPlan> borrowRepayPlans = this.borrowRepayPlanMapper.selectByExample(example);
					int lastDays = 0;
					if (borrowRepayPlans != null && borrowRepayPlans.size() > 0) {
						try {
							String nowDate = GetDate.getDateTimeMyTimeInMillis(nowTime);
							String recoverDate = GetDate.getDateTimeMyTimeInMillis(borrowRepayPlans.get(0).getRepayTime());
							lastDays = GetDate.daysBetween(nowDate, recoverDate);
						} catch (Exception e) {
							throw new RuntimeException("债转日期错误");
						}
					}
					// 债转本息
					creditAccount = BeforeInterestAfterPrincipalUtils.getPrincipalInterestCount(creditCapital, yearRate, borrow.getBorrowPeriod(), borrowRecover.getRecoverPeriod());
					// 每月应还利息
					BigDecimal interest = BeforeInterestAfterPrincipalUtils.getPerTermInterest(creditCapital, borrow.getBorrowApr().divide(new BigDecimal(100)), borrow.getBorrowPeriod(),
							borrow.getBorrowPeriod());
					// 债转期全部利息
					creditInterest = BeforeInterestAfterPrincipalUtils.getInterestCount(creditCapital, yearRate, borrow.getBorrowPeriod(), borrowRecover.getRecoverPeriod());
					// 垫付利息 垫息总额=出借人认购本金/出让人转让本金*出让人本期利息）-（债权本金*年化收益÷360*本期剩余天数
					assignInterestAdvance = BeforeInterestAfterPrincipalUtils.getAssignInterestAdvance(creditCapital, creditCapital, yearRate, interest, new BigDecimal(lastDays + ""));
					// 债转利息
					assignPayInterest = creditInterest;
					// 实付金额 承接本金*（1-折价率）+应垫付利息
					assignPay = creditPrice.add(assignInterestAdvance);
					// 预计收益 承接人债转本息—实付金额  计算出借收益
					assignInterest = creditAccount.subtract(assignPay);
					// 预计收益 出让人预期收益 =本金+本金持有期利息-本金*折让率-服务费
					expectInterest = creditCapital.add(assignInterestAdvance).subtract(creditCapital.multiply(new BigDecimal(creditDiscount).divide(new BigDecimal(100))))
							.subtract(assignPay.multiply(BigDecimal.valueOf(0.01)).setScale(2, BigDecimal.ROUND_DOWN));
				}
				// 服务费
				creditFee = assignPay.multiply(BigDecimal.valueOf(0.01)).setScale(2, BigDecimal.ROUND_DOWN);
			}
		}
		// 债转本息
		result.setCreditAccount(DF_COM_VIEW.format(creditAccount.setScale(2, BigDecimal.ROUND_DOWN)));
		// 债转期全部利息
		result.setCreditInterest(DF_COM_VIEW.format(creditInterest.setScale(2, BigDecimal.ROUND_DOWN)));
		// 垫付利息
		result.setAssignInterestAdvance(DF_COM_VIEW.format(assignInterestAdvance.setScale(2, BigDecimal.ROUND_DOWN)));
		// 债转利息
		result.setAssignPayInterest(DF_COM_VIEW.format(assignPayInterest.setScale(2, BigDecimal.ROUND_DOWN)));
		// 实付金额
		result.setAssignPay(DF_COM_VIEW.format(assignPay.setScale(2, BigDecimal.ROUND_DOWN)));
		// 预计收益
		result.setAssignInterest(DF_COM_VIEW.format(assignInterest.setScale(2, BigDecimal.ROUND_DOWN)));
		// 可转本金
		result.setCreditCapital(DF_COM_VIEW.format(creditCapital.setScale(2, BigDecimal.ROUND_DOWN)));
		// 折后价格
		result.setCreditPrice(DF_COM_VIEW.format(creditPrice.setScale(2, BigDecimal.ROUND_DOWN)));
		// 预期收益
		result.setExpectInterest(DF_COM_VIEW.format(expectInterest.setScale(2, BigDecimal.ROUND_DOWN)));
		// 服务费
		result.setCreditFee(DF_COM_VIEW.format(creditFee));
		return result;
	}

	/**
	 * 出借人当天是否可以债转
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public Integer tenderAbleToCredit(Integer userId) {
		// 获取当前时间
		// 获取当前时间的日期
		String nowDate = (GetDate.yyyyMMdd.format(new Date()) != null && !"".equals(GetDate.yyyyMMdd.format(new Date()))) ? GetDate.yyyyMMdd.format(new Date()) : "0";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nowDate", nowDate);
		if(userId!=null&&userId.intValue()>0){
			params.put("userId", userId);
		}
		Integer creditedNum = tenderCreditCustomizeMapper.tenderAbleToCredit(params);
		return creditedNum;
	}

	/**
	 * 查询债转还款列表
	 *
	 * @param tenderNid
	 * @return
	 */
	@Override
	public List<CreditRepay> selectCreditRepayList(String tenderNid) {
		CreditRepayExample example = new CreditRepayExample();
		CreditRepayExample.Criteria cra = example.createCriteria();
		cra.andCreditTenderNidEqualTo(tenderNid);
		cra.andStatusEqualTo(0);
		return this.creditRepayMapper.selectByExample(example);
	}


	@Override
	public List<CreditRepay> selectCreditRepayListByAssignNid(String assignNid) {
		CreditRepayExample example = new CreditRepayExample();
		CreditRepayExample.Criteria cra = example.createCriteria();
		cra.andAssignNidEqualTo(assignNid);
		return this.creditRepayMapper.selectByExample(example);
	}

	@Override
	public List<CreditRepay> selectCreditRepayList(String borrowNid, String tenderOrderId, Integer periodNow, Integer status) {
		CreditRepayExample example = new CreditRepayExample();
		CreditRepayExample.Criteria crt = example.createCriteria();
		if(StringUtils.isNotBlank(borrowNid)){
			crt.andBidNidEqualTo(borrowNid);
		}
		if(StringUtils.isNotBlank(tenderOrderId)){
			crt.andCreditTenderNidEqualTo(tenderOrderId);
		}
		if(periodNow != null && periodNow >= 0){
			crt.andRecoverPeriodEqualTo(periodNow);
		}
		if(status != null && status >= 0){
			crt.andStatusEqualTo(status);
		}
		example.setOrderByClause("id ASC");
		List<CreditRepay> creditRepayList = this.creditRepayMapper.selectByExample(example);
		return creditRepayList;
	}

	/**
	 * 我要债转
	 *
	 * @param request
	 * @return
	 */
	@Override
	public Integer saveCreditTender(BorrowCreditVO request) {
		BorrowCredit borrowCredit = CommonUtils.convertBean(request, BorrowCredit.class);
		boolean insertResultFlag = this.borrowCreditMapper.insertSelective(borrowCredit) > 0 ? true : false;
		if (insertResultFlag) {
			// 获取borrow_recover数据
			BorrowRecoverExample borrowRecoverExample = new BorrowRecoverExample();
			BorrowRecoverExample.Criteria borrowRecoverCra = borrowRecoverExample.createCriteria();
			borrowRecoverCra.andBorrowNidEqualTo(request.getBidNid()).andNidEqualTo(request.getTenderNid());
			List<BorrowRecover> borrowRecoverList = this.borrowRecoverMapper.selectByExample(borrowRecoverExample);
			BorrowRecover borrowRecover = borrowRecoverList.get(0);
			// 还款表更新债转时间
			logger.info("修改债转表数据 addtime {} " ,request.getAddTime());
			borrowRecover.setCreditTime(request.getAddTime());
			boolean isUpdateFlag = this.borrowRecoverMapper.updateByPrimaryKey(borrowRecover) > 0 ? true : false;
			if (!isUpdateFlag) {
				throw new RuntimeException("更新huiyingdai_borrow_recover表数据失败~!");
			}
			return borrowCredit.getCreditNid();
		}
		return null;
	}

	/**
	 * 前端Web页面出借可债转输入出借金额后收益提示 用户未登录 (包含查询条件)
	 *
	 * @param creditNid
	 * @param assignCapital
	 * @param userId
	 * @return
	 */
	@Override
	public TenderToCreditAssignCustomizeVO getInterestInfo(String creditNid, String assignCapital, Integer userId) {
		// 获取债转数据
		BorrowCreditExample borrowCreditExample = new BorrowCreditExample();
		BorrowCreditExample.Criteria borrowCreditCra = borrowCreditExample.createCriteria();
		borrowCreditCra.andCreditNidEqualTo(Integer.parseInt(creditNid));
		List<BorrowCredit> borrowCreditList = borrowCreditMapper.selectByExample(borrowCreditExample);
		if (borrowCreditList != null && borrowCreditList.size() == 1) {
			BorrowCredit borrowCredit = borrowCreditList.get(0);
			// 计算折后价格
			BigDecimal assignPrice = new BigDecimal(assignCapital).setScale(2, BigDecimal.ROUND_DOWN)
					.subtract(new BigDecimal(assignCapital).multiply(borrowCredit.getCreditDiscount().divide(new BigDecimal("100"), 18, BigDecimal.ROUND_DOWN)).setScale(2, BigDecimal.ROUND_DOWN));
			// 获取borrow_recover数据
			BorrowRecoverExample borrowRecoverExample = new BorrowRecoverExample();
			BorrowRecoverExample.Criteria borrowRecoverCra = borrowRecoverExample.createCriteria();
			borrowRecoverCra.andBorrowNidEqualTo(borrowCredit.getBidNid()).andNidEqualTo(borrowCredit.getTenderNid());
			List<BorrowRecover> borrowRecoverList = this.borrowRecoverMapper.selectByExample(borrowRecoverExample);
			if (borrowRecoverList != null && borrowRecoverList.size() == 1) {
				// 获取借款数据
				Borrow borrow = this.getBorrowByNid(borrowCredit.getBidNid());
				TenderToCreditAssignCustomizeVO tenderToCreditAssign = new TenderToCreditAssignCustomizeVO();
				BigDecimal yearRate = borrowCredit.getBidApr().divide(new BigDecimal("100"));
				if (Validator.isNotNull(borrow)) {
					String borrowStyle = borrow.getBorrowStyle();
					// 剩余待承接本金
					BigDecimal sellerCapitalWait = borrowCredit.getCreditCapital().subtract(borrowCredit.getCreditCapitalAssigned());
					// 待承接的待收收益
					BigDecimal sellerInterestWait = borrowCredit.getCreditInterest().subtract(borrowCredit.getCreditInterestAssigned());
					// 待垫付的垫付利息
					BigDecimal sellerInterestAdvanceWait = borrowCredit.getCreditInterestAdvance().subtract(borrowCredit.getCreditInterestAdvanceAssigned());
					// 到期还本还息和按天计息，到期还本还息
					if (borrowStyle.equals(CalculatesUtil.STYLE_END) || borrowStyle.equals(CalculatesUtil.STYLE_ENDDAY)) {
						// 剩余天数
						int lastDays = borrowCredit.getCreditTerm();
						// 按天
						if (borrowStyle.equals(CalculatesUtil.STYLE_ENDDAY)) {
							BigDecimal creditAccount = DuePrincipalAndInterestUtils.getDayPrincipalInterestAfterCredit(new BigDecimal(assignCapital), sellerCapitalWait, sellerInterestWait, yearRate,
									borrow.getBorrowPeriod());
							// 垫付利息
							// 垫息总额=债权本金*年化收益÷360*融资期限-债权本金*年化收益÷360*剩余期限
							BigDecimal assignInterestAdvance = DuePrincipalAndInterestUtils.getDayAssignInterestAdvanceAfterCredit(new BigDecimal(assignCapital), sellerCapitalWait,
									sellerInterestAdvanceWait, yearRate, borrow.getBorrowPeriod(), new BigDecimal(lastDays));
							// 实付金额 承接本金*（1-折价率）+应垫付利息
							BigDecimal assignPay = assignPrice.add(assignInterestAdvance);
							logger.info("计算债转收益：assignInterestAdvance {}  assignPay {} ",assignInterestAdvance,assignPay);
							String assignPayText = assignCapital + "✕(1-" + DF_FOR_VIEW.format(borrowCredit.getCreditDiscount()) + "%)+" + DF_FOR_VIEW.format(assignInterestAdvance) + "="
									+ DF_FOR_VIEW.format(assignPay) + "元";
							// 预计收益 承接人债转本息—实付金额 计算出借收益
							BigDecimal assignInterest = creditAccount.subtract(assignPay);
							tenderToCreditAssign.setBorrowNid(borrow.getBorrowNid());
							tenderToCreditAssign.setCreditNid(String.valueOf(borrowCredit.getCreditNid()));
							tenderToCreditAssign.setTenderNid(borrowCredit.getTenderNid());
							tenderToCreditAssign.setCreditCapital(DF_FOR_VIEW.format(borrowCredit.getCreditCapital()));
							tenderToCreditAssign.setAssignCapital(sellerCapitalWait.toString());
							tenderToCreditAssign.setCreditDiscount(DF_FOR_VIEW.format(borrowCredit.getCreditDiscount()));
							tenderToCreditAssign.setAssignPrice(DF_FOR_VIEW.format(assignPrice));
							tenderToCreditAssign.setAssignPay(DF_FOR_VIEW.format(assignPay));
							tenderToCreditAssign.setAssignInterest(DF_FOR_VIEW.format(assignInterest));
							tenderToCreditAssign.setAssignInterestAdvance(DF_FOR_VIEW.format(assignInterestAdvance));
							tenderToCreditAssign.setAssignPayText(assignPayText);
							tenderToCreditAssign.setAssignTotal(assignPay.toString());
						} else {
							// 按月
							// 债转本息
							BigDecimal creditAccount = DuePrincipalAndInterestUtils.getMonthPrincipalInterestAfterCredit(new BigDecimal(assignCapital), sellerCapitalWait, sellerInterestWait, yearRate,
									borrow.getBorrowPeriod());
							// 垫付利息
							// 垫息总额=债权本金*年化收益÷12*融资期限-债权本金*年化收益÷360*剩余天数
							BigDecimal assignInterestAdvance = DuePrincipalAndInterestUtils.getMonthAssignInterestAdvanceAfterCredit(new BigDecimal(assignCapital), sellerCapitalWait,
									sellerInterestAdvanceWait, yearRate, borrow.getBorrowPeriod(), new BigDecimal(lastDays));
							// 实付金额 承接本金*（1-折价率）+应垫付利息
							BigDecimal assignPay = assignPrice.add(assignInterestAdvance);
							String assignPayText = assignCapital + "✕(1-" + DF_FOR_VIEW.format(borrowCredit.getCreditDiscount()) + "%)+" + DF_FOR_VIEW.format(assignInterestAdvance) + "="
									+ DF_FOR_VIEW.format(assignPay) + "元";
							// 预计收益 承接人债转本息—实付金额 // 计算出借收益
							BigDecimal assignInterest = creditAccount.subtract(assignPay);
							tenderToCreditAssign.setBorrowNid(borrow.getBorrowNid());
							tenderToCreditAssign.setCreditNid(String.valueOf(borrowCredit.getCreditNid()));
							tenderToCreditAssign.setTenderNid(borrowCredit.getTenderNid());
							tenderToCreditAssign.setCreditCapital(DF_FOR_VIEW.format(borrowCredit.getCreditCapital().setScale(2, BigDecimal.ROUND_DOWN)));
							tenderToCreditAssign.setAssignCapital(sellerCapitalWait.toString());
							tenderToCreditAssign.setCreditDiscount(DF_FOR_VIEW.format(borrowCredit.getCreditDiscount().setScale(2, BigDecimal.ROUND_DOWN)));
							tenderToCreditAssign.setAssignPrice(DF_FOR_VIEW.format(assignPrice.setScale(2, BigDecimal.ROUND_DOWN)));
							tenderToCreditAssign.setAssignPay(DF_FOR_VIEW.format(assignPay.setScale(2, BigDecimal.ROUND_DOWN)));
							tenderToCreditAssign.setAssignInterest(DF_FOR_VIEW.format(assignInterest.setScale(2, BigDecimal.ROUND_DOWN)));
							tenderToCreditAssign.setAssignInterestAdvance(DF_FOR_VIEW.format(assignInterestAdvance.setScale(2, BigDecimal.ROUND_DOWN)));
							tenderToCreditAssign.setAssignPayText(assignPayText);
							tenderToCreditAssign.setAssignTotal(assignPay.toString());
						}
					}
					// 等额本息和等额本金和先息后本
					if (borrowStyle.equals(CalculatesUtil.STYLE_MONTH) || borrowStyle.equals(CalculatesUtil.STYLE_PRINCIPAL) || borrowStyle.equals(CalculatesUtil.STYLE_ENDMONTH)) {
						int lastDays = 0;
						String bidNid = borrow.getBorrowNid();
						BorrowRepayPlanExample example = new BorrowRepayPlanExample();
						BorrowRepayPlanExample.Criteria cra = example.createCriteria();
						cra.andBorrowNidEqualTo(bidNid).andRepayPeriodEqualTo(borrowCredit.getRecoverPeriod() + 1);
						List<BorrowRepayPlan> borrowRepayPlans = this.borrowRepayPlanMapper.selectByExample(example);
						if (borrowRepayPlans != null && borrowRepayPlans.size() > 0) {
							try {
								Integer date = (int) (borrowCredit.getCreateTime().getTime() / 1000);
								lastDays = GetDate.daysBetween(GetDate.getDateTimeMyTimeInMillis(date),
										GetDate.getDateTimeMyTimeInMillis(borrowRepayPlans.get(0).getRepayTime()));
							} catch (Exception e) {
								logger.error("计算出错,borrowRepayPlans:"+JSONObject.toJSONString(borrowRepayPlans.get(0))+"---------"+JSONObject.toJSONString(borrowCredit),e);
							}
						}
						// 已还多少期
						int repayPeriod = borrowCredit.getRecoverPeriod();
						// 应还总本息
						BigDecimal creditAccount = BigDecimal.ZERO;
						// 实付金额 承接本金*（1-折价率）+应垫付利息
						BigDecimal assignPay = BigDecimal.ZERO;
						// 垫付利息
						// 垫息总额=出借人认购本金/出让人转让本金*出让人本期利息）-（债权本金*年化收益÷360*本期剩余天数
						BigDecimal assignInterestAdvance = BigDecimal.ZERO;
						if (new BigDecimal(assignCapital).compareTo(sellerCapitalWait) == 0) {
							// 最后一笔承接
							creditAccount = sellerCapitalWait.add(sellerInterestWait);
							// 剩余垫付利息
							assignInterestAdvance = sellerInterestAdvanceWait;
							// 实付金额 承接本金*（1-折价率）+应垫付利息
							assignPay = assignPrice.add(assignInterestAdvance);
						} else {
							// 应还总本息
							creditAccount = BeforeInterestAfterPrincipalUtils.getPrincipalInterestCount(new BigDecimal(assignCapital), yearRate, borrow.getBorrowPeriod(), borrow.getBorrowPeriod());
							// 承接人每月应还利息
							BigDecimal interestAssign = BeforeInterestAfterPrincipalUtils.getPerTermInterest(new BigDecimal(assignCapital), borrowCredit.getBidApr().divide(new BigDecimal(100)),
									borrow.getBorrowPeriod(), borrow.getBorrowPeriod());
							// 出让人每月应还利息
							BigDecimal interest = BeforeInterestAfterPrincipalUtils.getPerTermInterest(borrowCredit.getCreditCapital(), borrowCredit.getBidApr().divide(new BigDecimal(100)),
									borrow.getBorrowPeriod(), borrow.getBorrowPeriod());
							// 应还总额
							creditAccount = creditAccount.subtract(interestAssign.multiply(new BigDecimal(repayPeriod)));
							// 垫付利息
							// 垫息总额=出借人认购本金/出让人转让本金*出让人本期利息）-（债权本金*年化收益÷360*本期剩余天数
							assignInterestAdvance = BeforeInterestAfterPrincipalUtils.getAssignInterestAdvance(new BigDecimal(assignCapital), borrowCredit.getCreditCapital(), yearRate, interest,
									new BigDecimal(lastDays + ""));
							// 实付金额 承接本金*（1-折价率）+应垫付利息
							assignPay = assignPrice.add(assignInterestAdvance);
						}
						String assignPayText = assignCapital + "✕(1-" + DF_FOR_VIEW.format(borrowCredit.getCreditDiscount()) + "%)+" + DF_FOR_VIEW.format(assignInterestAdvance) + "="
								+ DF_FOR_VIEW.format(assignPay) + "元";
						// 预计收益 承接人债转本息—实付金额  计算出借收益
						BigDecimal assignInterest = creditAccount.subtract(assignPay);
						tenderToCreditAssign.setBorrowNid(borrow.getBorrowNid());
						tenderToCreditAssign.setCreditNid(String.valueOf(borrowCredit.getCreditNid()));
						tenderToCreditAssign.setTenderNid(borrowCredit.getTenderNid());
						tenderToCreditAssign.setCreditCapital(DF_FOR_VIEW.format(borrowCredit.getCreditCapital().setScale(2, BigDecimal.ROUND_DOWN)));
						tenderToCreditAssign.setAssignCapital(sellerCapitalWait.toString());
						tenderToCreditAssign.setCreditDiscount(DF_FOR_VIEW.format(borrowCredit.getCreditDiscount().setScale(2, BigDecimal.ROUND_DOWN)));
						tenderToCreditAssign.setAssignPrice(DF_FOR_VIEW.format(assignPrice.setScale(2, BigDecimal.ROUND_DOWN)));
						tenderToCreditAssign.setAssignPay(DF_FOR_VIEW.format(assignPay.setScale(2, BigDecimal.ROUND_DOWN)));
						tenderToCreditAssign.setAssignInterest(DF_FOR_VIEW.format(assignInterest.setScale(2, BigDecimal.ROUND_DOWN)));
						tenderToCreditAssign.setAssignInterestAdvance(DF_FOR_VIEW.format(assignInterestAdvance.setScale(2, BigDecimal.ROUND_DOWN)));
						tenderToCreditAssign.setAssignPayText(assignPayText);
						tenderToCreditAssign.setAssignTotal(assignPay.toString());
					}
					// 全投的计算
					if (Validator.isNotNull(userId)) {
						Account account = this.getAccount(userId);
						if (Validator.isNotNull(account)) {
							BigDecimal balance = account.getBankBalance();
							BigDecimal creditDiscount = new BigDecimal(1).subtract(borrowCredit.getCreditDiscount().divide(new BigDecimal(100)));
							BigDecimal sum = sellerCapitalWait.multiply(creditDiscount).add(sellerInterestAdvanceWait);
							logger.info("sum:{}   creditDiscount{}   balance{}",sum,creditDiscount,creditDiscount);
							BigDecimal max = sellerCapitalWait.multiply(balance).divide(sum, 8, RoundingMode.DOWN);
							if (max.compareTo(sellerCapitalWait) > 0) {
								// 全投金额
								tenderToCreditAssign.setAssignCapitalMax((String.valueOf(sellerCapitalWait.intValue())));
							} else {
								// 全投金额
								tenderToCreditAssign.setAssignCapitalMax(String.valueOf(max.intValue()));
							}
						}
					}
					return tenderToCreditAssign;
				}
			}
		}
		return null;
	}

	/**
	 * 根据creditNid查询
	 *
	 * @param creditNid
	 * @return
	 */
	@Override
	public BorrowCreditVO getBorrowCreditByCreditNid(String creditNid) {
		BorrowCreditExample borrowCreditExample = new BorrowCreditExample();
		BorrowCreditExample.Criteria borrowCreditCra = borrowCreditExample.createCriteria();
		borrowCreditCra.andCreditNidEqualTo(Integer.parseInt(creditNid));
		List<BorrowCredit> borrowCreditList = this.borrowCreditMapper.selectByExample(borrowCreditExample);
		if (CollectionUtils.isEmpty(borrowCreditList)) {
			return null;
		}
		return CommonUtils.convertBean(borrowCreditList.get(0), BorrowCreditVO.class);
	}

	/**
	 * 保存债转异步数据
	 *
	 * @param request
	 */
	@Override
	public void insertCreditBgData(CreditTenderBgVO request) {
		CreditTenderLogVO creditTenderLog = request.getCreditTenderLog();
		// 删除tmp表
		logger.info("债转异步处理  删除tmp表{}",JSONObject.toJSONString(request));
		deleteByOrderIdAndUserId(request.getCreditTenderLog().getLogOrderId(),request.getCreditTenderLog().getUserId());
		CreditTender creditTender = CommonUtils.convertBean(request.getCreditTender(),CreditTender.class);
		// 插入credit_tender
		logger.info("插入credit_tender，{}",JSONObject.toJSONString(creditTender));
		creditTenderMapper.insertSelective(creditTender);
		// 处理承接人account表和account_list表
		Account assignAccountNew = CommonUtils.convertBean(request.getAssignAccountNew(),Account.class);
		logger.info("处理承接人account表  {} ",JSONObject.toJSONString(assignAccountNew));
		this.adminAccountCustomizeMapper.updateCreditAssignSuccess(assignAccountNew) ;
		insertAssignAccountList(creditTender,assignAccountNew,creditTenderLog);

		// 处理出让人的 account表和account_list表
		Account sellerAccountNew = CommonUtils.convertBean(request.getSellerAccountNew(),Account.class);
		logger.info("处理出让人的 account表{} ",JSONObject.toJSONString(sellerAccountNew));
		this.adminAccountCustomizeMapper.updateCreditSellerSuccess(sellerAccountNew) ;
		insertSellerAccountList(creditTender,sellerAccountNew,creditTenderLog);

		// 插入 creditRepay
		if(request.getCreditRepayVO()!=null){
			logger.info("插入 creditRepay");
			CreditRepay creditRepay = CommonUtils.convertBean(request.getCreditRepayVO(),CreditRepay.class);
			creditRepayMapper.insertSelective(creditRepay);
		}

		// 分期的
		if (creditTender.getAssignRepayPeriod() > 0) {
			updateCreditPlan(request);
		}
		// 更新Borrow_recover
		logger.info("更新Borrow_recover");
		BorrowRecover borrowRecover = CommonUtils.convertBean(request.getBorrowRecover(),BorrowRecover.class);
		this.borrowRecoverMapper.updateByPrimaryKeySelective(borrowRecover) ;

		// 更新Borrow_recover_plan
		logger.info("更新Borrow_recover_plan");
		if(request.getBorrowRecoverPlan()!=null){
			BorrowRecoverPlan borrowRecoverPlan = CommonUtils.convertBean(request.getBorrowRecoverPlan(),BorrowRecoverPlan.class);
			this.borrowRecoverPlanMapper.updateByPrimaryKeySelective(borrowRecoverPlan);
		}

		// 调用银行结束债转接口
		BorrowCredit borrowCredit = CommonUtils.convertBean(request.getBorrowCredit(),BorrowCredit.class);
		// 更新borrowCredit
		logger.info("borrowCredit:{}",JSONObject.toJSONString(borrowCredit));
		Integer borrowCreditResult = this.borrowCreditMapper.updateByPrimaryKeySelective(borrowCredit);
		logger.info("更新borrowCredit结果:{}",borrowCreditResult);
		logger.info("borrowCredit.getCreditCapitalAssigned():{}    borrowCredit.getCreditCapital():{} ",borrowCredit.getCreditCapitalAssigned(),borrowCredit.getCreditCapital());

		// add 合规数据上报 埋点 liubin 20181122 start
		JSONObject params = new JSONObject();
		params.put("assignOrderId", creditTender.getAssignNid());
		params.put("flag", "1");//1（散）2（智投）
		params.put("status", "1"); //1承接（每笔）
		// 推送数据到MQ 承接（每笔）散
		commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.UNDERTAKE_SINGLE_SUCCESS_TAG, UUID.randomUUID().toString(), params),
				MQConstant.HG_REPORT_DELAY_LEVEL);
		// add 合规数据上报 埋点 liubin 20181122 end

		if (borrowCredit.getCreditCapitalAssigned().compareTo(borrowCredit.getCreditCapital()) == 0) {
			// add 合规数据上报 埋点 liubin 20181122 start
			// 推送数据到MQ 承接（完全）散
			params = new JSONObject();
			params.put("creditNid", borrowCredit.getCreditNid()+"");
			params.put("flag", "1"); //1（散）2（智投）
			params.put("status", "2"); //2承接（完全）
			commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.UNDERTAKE_ALL_SUCCESS_TAG, UUID.randomUUID().toString(), params),
					MQConstant.HG_REPORT_DELAY_LEVEL);
			// add 合规数据上报 埋点 liubin 20181122 end

			logger.info("调用银行结束债转接口");
			this.requestDebtEnd(borrowRecover, request.getSellerBankAccount().getAccount());
		}
	}

	// 处理出让人的account_list
	private void insertSellerAccountList(CreditTender creditTender, Account sellerAccountNew, CreditTenderLogVO creditTenderLog) {
		sellerAccountNew = getAccount(sellerAccountNew.getUserId());
		AccountList sellerAccountList = new AccountList();
		sellerAccountList.setNid(creditTender.getAssignNid());
		sellerAccountList.setUserId(creditTender.getCreditUserId());
		//操作金额
		sellerAccountList.setAmount(creditTender.getAssignPay().subtract(creditTender.getCreditFee()));
		// 收支类型1收入2支出3冻结
		sellerAccountList.setType(1);
		// 交易类型
		sellerAccountList.setTrade("creditsell");
		// 操作识别码 balance余额操作 frost冻结操作 await待收操作
		sellerAccountList.setTradeCode("balance");
		// 资金总额
		sellerAccountList.setTotal(sellerAccountNew.getTotal());
		// 可用金额
		sellerAccountList.setBalance(sellerAccountNew.getBalance());
		// 银行存管可用余额
		sellerAccountList.setBankBalance(sellerAccountNew.getBankBalance());
		// 银行待收总额
		sellerAccountList.setBankAwait(sellerAccountNew.getBankAwait());
		// 银行待还本金
		sellerAccountList.setBankAwaitCapital(sellerAccountNew.getBankAwaitCapital());
		// 银行待还利息
		sellerAccountList.setBankAwaitInterest(sellerAccountNew.getBankAwaitInterest());
		// 银行累计收益
		sellerAccountList.setBankInterestSum(sellerAccountNew.getBankInterestSum());
		// 银行累计出借
		sellerAccountList.setBankInvestSum(sellerAccountNew.getBankInvestSum());
		// 银行存管冻结金额
		sellerAccountList.setBankFrost(sellerAccountNew.getBankFrost());
		// 银行总资产
		sellerAccountList.setBankTotal(sellerAccountNew.getBankTotal());
		//汇计划账户可用余额
		sellerAccountList.setPlanBalance(sellerAccountNew.getPlanBalance());
		// 汇添金账户冻结金额
		sellerAccountList.setPlanFrost(sellerAccountNew.getPlanFrost());
		sellerAccountList.setSeqNo(String.valueOf(creditTenderLog.getSeqNo()));
		sellerAccountList.setTxDate(creditTenderLog.getTxDate());
		sellerAccountList.setTxTime(creditTenderLog.getTxTime());
		sellerAccountList.setBankSeqNo(String.valueOf(creditTenderLog.getTxDate()) + String.valueOf(creditTenderLog.getTxTime()) + String.valueOf(creditTenderLog.getSeqNo()));
		// 出让人电子账户号
		sellerAccountList.setAccountId(sellerAccountNew.getAccountId());
		sellerAccountList.setFrost(sellerAccountNew.getFrost());
		sellerAccountList.setAwait(sellerAccountNew.getAwait());
		sellerAccountList.setRepay(sellerAccountNew.getRepay());
		sellerAccountList.setRemark("出让债权");
		sellerAccountList.setCreateTime(new Date());
		sellerAccountList.setOperator(String.valueOf(creditTenderLog.getCreditUserId()));
		sellerAccountList.setIp(creditTenderLog.getAddIp());
		sellerAccountList.setWeb(0);
		sellerAccountList.setIsBank(1);
		sellerAccountList.setCheckStatus(0);

		this.accountListMapper.insertSelective(sellerAccountList);
	}

	// 处理承接人account_list
	private void insertAssignAccountList(CreditTender creditTender,Account assignAccount,CreditTenderLogVO creditTenderLog ) {
		// 重新获取承接人用户账户信息
		assignAccount = getAccount(assignAccount.getUserId());
		AccountList assignAccountList = new AccountList();
		assignAccountList.setNid(creditTender.getAssignNid());
		assignAccountList.setUserId(assignAccount.getUserId());
		assignAccountList.setAmount(creditTender.getAssignPay());
		assignAccountList.setType(2);
		assignAccountList.setTrade("creditassign");
		assignAccountList.setTradeCode("balance");
		assignAccountList.setTotal(assignAccount.getTotal());
		assignAccountList.setBalance(assignAccount.getBalance());
		assignAccountList.setBankBalance(assignAccount.getBankBalance());
		assignAccountList.setBankAwait(assignAccount.getBankAwait());
		assignAccountList.setBankAwaitCapital(assignAccount.getBankAwaitCapital());
		assignAccountList.setBankAwaitInterest(assignAccount.getBankAwaitInterest());
		assignAccountList.setBankInvestSum(assignAccount.getBankInvestSum());
		assignAccountList.setBankInterestSum(assignAccount.getBankInterestSum());
		assignAccountList.setBankFrost(assignAccount.getBankFrost());
		assignAccountList.setBankInterestSum(assignAccount.getBankInterestSum());
		// 银行总资产
		logger.info("assignAccountList.setBankTotal:   "+assignAccount.getBankTotal());
		assignAccountList.setBankTotal(assignAccount.getBankTotal());
		//汇计划账户可用余额
		assignAccountList.setPlanBalance(assignAccount.getPlanBalance());
		assignAccountList.setPlanFrost(assignAccount.getPlanFrost());
		assignAccountList.setSeqNo(String.valueOf(creditTenderLog.getSeqNo()));
		assignAccountList.setTxDate(creditTenderLog.getTxDate());
		assignAccountList.setTxTime(creditTenderLog.getTxTime());
		assignAccountList.setBankSeqNo(String.valueOf(creditTenderLog.getTxDate()) + String.valueOf(creditTenderLog.getTxTime()) + String.valueOf(creditTenderLog.getSeqNo()));
		// 承接人电子账户号
		assignAccountList.setAccountId(assignAccount.getAccountId());
		assignAccountList.setFrost(assignAccount.getFrost());
		assignAccountList.setAwait(assignAccount.getAwait());
		assignAccountList.setRepay(assignAccount.getRepay());
		assignAccountList.setRemark("购买债权");
		assignAccountList.setCreateTime(new Date());
		assignAccountList.setOperator(String.valueOf(assignAccount.getUserId()));
		assignAccountList.setIp(creditTender.getAddIp());
		assignAccountList.setWeb(0);
		assignAccountList.setIsBank(1);
		assignAccountList.setCheckStatus(0);
		this.accountListMapper.insertSelective(assignAccountList);
	}

	// 分期的标的
	private void updateCreditPlan(CreditTenderBgVO request) {
		String BorrowNid = request.getCreditTenderLog().getBidNid();
		Borrow borrow = getBorrowByNid(BorrowNid);
		String borrowStyle = borrow.getBorrowStyle();
		BorrowRecoverVO borrowRecover = request.getBorrowRecover();
		boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
				|| CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
		if (isMonth) {
			logger.info("开始操作 分期的 债转  request  {} ",JSONObject.toJSONString(request));
			CreditTenderVO creditTender = request.getCreditTender();
			BorrowCreditVO borrowCredit = request.getBorrowCredit();
			Integer userId = request.getCreditTenderLog().getUserId();
			// 项目总期数
			Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
			// 管理费率
			BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
			// 差异费率
			BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
			// 初审时间
			int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : borrow.getVerifyTime();
			String authCode = request.getCreditTender().getAuthCode();
			// 管理费
			if (creditTender.getAssignRepayPeriod() > 0) {
				// 先息后本
				if (CalculatesUtil.STYLE_ENDMONTH.equals(borrowStyle)) {
					// 总的利息
					BigDecimal sumMonthInterest = BigDecimal.ZERO;
					// 每月偿还的利息
					BigDecimal perMonthInterest = BigDecimal.ZERO;
					for (int i = 1; i <= creditTender.getAssignRepayPeriod(); i++) {
						BigDecimal perManage = BigDecimal.ZERO;
						int periodNow = borrow.getBorrowPeriod() - borrowRecover.getRecoverPeriod() + i;
						// 获取borrow_recover_plan更新每次还款时间
						BorrowRecoverPlan borrowRecoverPlan = this.borrowRecoverService.getPlanByBidTidPeriod(creditTender.getBidNid(), creditTender.getCreditTenderNid(), periodNow);
						if (borrowRecoverPlan != null) {
							CreditRepay creditRepay = new CreditRepay();
							if (borrowCredit.getCreditStatus() == 2) {
								// 如果是最后一笔
								perManage = borrowRecoverPlan.getRecoverFee().subtract(borrowRecoverPlan.getCreditManageFee());
								perMonthInterest = borrowRecoverPlan.getRecoverInterest().subtract(borrowRecoverPlan.getCreditInterest());
								if (i == creditTender.getAssignRepayPeriod()) {
									// 应还本金
									creditRepay.setAssignCapital(creditTender.getAssignCapital());
									// 应还总额
									creditRepay.setAssignAccount(creditTender.getAssignCapital().add(perMonthInterest));
									// 应还利息
									creditRepay.setAssignInterest(perMonthInterest);
								} else {
									// 应还本金
									creditRepay.setAssignCapital(BigDecimal.ZERO);
									// 应还总额
									creditRepay.setAssignAccount(perMonthInterest);
									// 应还利息
									creditRepay.setAssignInterest(perMonthInterest);
								}
							} else {
								// 如果不是最后一笔
								if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
									if (periodNow == borrowPeriod.intValue()) {
										perManage = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate, borrowPeriod,
												borrowPeriod, differentialRate, 1, borrowVerifyTime);
									} else {
										perManage = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate, borrowPeriod,
												borrowPeriod, differentialRate, 0, borrowVerifyTime);
									}
								}
								if (i == creditTender.getAssignRepayPeriod()) {
									BigDecimal lastPeriodInterest = creditTender.getAssignInterest().subtract(sumMonthInterest);
									// 应还本金
									creditRepay.setAssignCapital(creditTender.getAssignCapital());
									// 应还总额
									creditRepay.setAssignAccount(creditTender.getAssignCapital().add(lastPeriodInterest));
									// 应还利息
									creditRepay.setAssignInterest(lastPeriodInterest);
								} else {
									// 每月偿还的利息
									perMonthInterest = BeforeInterestAfterPrincipalUtils.getPerTermInterest(creditTender.getAssignCapital(),
											borrow.getBorrowApr().divide(new BigDecimal(100)), borrow.getBorrowPeriod(), borrow.getBorrowPeriod());
									// 总的还款利息
									sumMonthInterest = sumMonthInterest.add(perMonthInterest);
									// 应还本金
									creditRepay.setAssignCapital(BigDecimal.ZERO);
									// 应还总额
									creditRepay.setAssignAccount(perMonthInterest);
									// 应还利息
									creditRepay.setAssignInterest(perMonthInterest);
								}
							}
							// 用户名称
							creditRepay.setUserId(userId);
							// 出让人id
							creditRepay.setCreditUserId(creditTender.getCreditUserId());
							// 状态
							creditRepay.setStatus(0);
							// 原标标号
							creditRepay.setBidNid(creditTender.getBidNid());
							// 债转标号
							creditRepay.setCreditNid(creditTender.getCreditNid());
							// 债转投标单号
							creditRepay.setCreditTenderNid(creditTender.getCreditTenderNid());
							// 认购单号
							creditRepay.setAssignNid(creditTender.getAssignNid());

							if (i == 1) {
								// 垫付利息
								creditRepay.setAssignInterestAdvance(creditTender.getAssignInterestAdvance());
							} else {
								// 垫付利息
								creditRepay.setAssignInterestAdvance(BigDecimal.ZERO);
							}
							// 购买价格
							creditRepay.setAssignPrice(creditTender.getAssignPrice());
							// 支付金额
							creditRepay.setAssignPay(creditTender.getAssignPay());
							// 已还总额
							creditRepay.setAssignRepayAccount(BigDecimal.ZERO);
							// 已还本金
							creditRepay.setAssignRepayCapital(BigDecimal.ZERO);
							// 已还利息
							creditRepay.setAssignRepayInterest(BigDecimal.ZERO);
							// 最终实际还款时间
							creditRepay.setAssignRepayYesTime(0);
							// 还款期数
							creditRepay.setAssignRepayPeriod(i);
							// 认购日期
							creditRepay.setAssignCreateDate(creditTender.getAssignCreateDate());
							// ip
							creditRepay.setAddIp(creditTender.getAddIp());
							// 客户端
							creditRepay.setClient(creditTender.getClient());
							// 管理费
							creditRepay.setManageFee(BigDecimal.ZERO);
							// 唯一nid
							//creditRepay.setUniqueNid(creditTender.getAssignNid() + "_" + String.valueOf(i));
							// 授权码
							creditRepay.setAuthCode(authCode);
							creditRepay.setAdvanceStatus(0);
							creditRepay.setChargeDays(0);
							creditRepay.setChargeInterest(BigDecimal.ZERO);
							creditRepay.setDelayDays(0);
							creditRepay.setDelayInterest(BigDecimal.ZERO);
							creditRepay.setLateDays(0);
							creditRepay.setLateInterest(BigDecimal.ZERO);
							// 最后还款日
							creditRepay.setAssignRepayEndTime(creditTender.getAssignRepayEndTime());
							// 上次还款时间
							creditRepay.setAssignRepayLastTime(creditTender.getAssignRepayLastTime());
							// 下次还款时间
							creditRepay.setAssignRepayNextTime(borrowRecoverPlan.getRecoverTime());
							// 原标还款期数
							creditRepay.setRecoverPeriod(borrowRecoverPlan.getRecoverPeriod());
							// 管理费
							creditRepay.setUserName(creditTender.getUserName());
							creditRepay.setCreditUserName(creditTender.getCreditUserName());
							creditRepay.setManageFee(perManage);
							creditRepayMapper.insertSelective(creditRepay);
							// 更新borrowRecover
							// 承接本金
							borrowRecoverPlan.setCreditAmount(borrowRecoverPlan.getCreditAmount().add(creditRepay.getAssignCapital()));
							// 垫付利息
							borrowRecoverPlan.setCreditInterestAmount(borrowRecoverPlan.getCreditInterestAmount().add(creditRepay.getAssignInterestAdvance()));
							// 债转状态
							borrowRecoverPlan.setCreditStatus(borrowCredit.getCreditStatus());
							borrowRecoverPlan.setCreditManageFee(borrowRecoverPlan.getCreditManageFee().add(perManage));
							borrowRecoverPlan.setCreditInterest(borrowRecoverPlan.getCreditInterest().add(perMonthInterest));
							this.borrowRecoverPlanMapper.updateByPrimaryKeySelective(borrowRecoverPlan);
						}
					}
				}
			}
			logger.info("操作分期债转完成");
		}
	}

	/**
	 * 修改债转结果
	 *
	 * @param logOrderId
	 * @param logUserId
	 * @param retCode
	 * @param retMsg
	 * @return
	 */
	@Override
	public Integer updateCreditTenderResult(String logOrderId, String logUserId, String retCode, String retMsg) {
		CreditTenderLogExample example = new CreditTenderLogExample();
		CreditTenderLogExample.Criteria cra = example.createCriteria();
		cra.andLogOrderIdEqualTo(logOrderId).andUserIdEqualTo(Integer.parseInt(logUserId));
		List<CreditTenderLog> list = this.creditTenderLogMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(list)){
			CreditTenderLog log = list.get(0);
			log.setRetCode(retCode);
			log.setRetMsg(retMsg);
			this.creditTenderLogMapper.updateByPrimaryKeySelective(log);
		}
		return 1;
	}

	/**
	 * 查询债转失败原因
	 *
	 * @param logOrderId
	 * @param logUserId
	 * @return
	 */
	@Override
	public String getFailResult(String logOrderId, String logUserId) {
		CreditTenderLogExample example = new CreditTenderLogExample();
		CreditTenderLogExample.Criteria cra = example.createCriteria();
		cra.andLogOrderIdEqualTo(logOrderId).andUserIdEqualTo(Integer.parseInt(logUserId));
		List<CreditTenderLog> list = this.creditTenderLogMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0).getRetMsg();
		}
		return "";
	}

	/**
	 * 查询债转信息
	 *
	 * @param logOrderId
	 * @param logUserId
	 * @return
	 */
	@Override
	public CreditTenderVO getCreditTenderByUserIdOrdId(String logOrderId, String logUserId) {
		CreditTenderExample example = new CreditTenderExample();
		CreditTenderExample.Criteria cra = example.createCriteria();
		cra.andAssignNidEqualTo(logOrderId).andUserIdEqualTo(Integer.parseInt(logUserId));
		List<CreditTender> list = this.creditTenderMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			return CommonUtils.convertBean(list.get(0), CreditTenderVO.class);
		}
		return null;
	}

	@Override
	public BigDecimal getHzrTenderPrice(List<Integer> list) {
        String dayStart = GetDate.getDayStart(GetDate.date2Str(GetDate.date_sdf));
        String dayEnd = GetDate.getDayEnd(GetDate.date2Str(GetDate.date_sdf));
		return creditTenderCustomizeMapper.getHzrTenderPrice(list, dayStart, dayEnd);
	}



	/**
	 * 查询承接总记录数
	 * @author zhangyk
	 * @date 2018/7/25 17:19
	 */
	@Override
	public int getCreditTenderCount(Map<String, Object> params) {
		return creditTenderCustomizeMapper.getCreditListCount(params);
	}


	/**
	 * 查询承接list
	 * @author zhangyk
	 * @date 2018/7/25 17:28
	 */
	@Override
	public List<CreditTenderListCustomizeVO> getCreditTenderList(Map<String, Object> params) {
		return creditTenderCustomizeMapper.getCreditTenderList(params);
	}

	@Override
	public CreditTenderVO getCreditTenderByAssignNid(String assignNid) {
		CreditTenderVO vo = new CreditTenderVO();
		CreditTender tender = new CreditTender();
        CreditTenderExample example = new CreditTenderExample();
        CreditTenderExample.Criteria criteria = example.createCriteria();
        criteria.andAssignNidEqualTo(assignNid);
        List<CreditTender> list = creditTenderMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
        	tender = list.get(0);
        	if(tender != null){
        		BeanUtils.copyProperties(tender, vo);
        		return vo;
        	}
        }
		return null;
	}
}
