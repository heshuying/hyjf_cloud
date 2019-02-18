/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.borrow.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.assetpush.InfoBean;
import com.hyjf.am.vo.admin.BailConfigInfoCustomizeVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectRepayVO;
import com.hyjf.am.vo.trade.hjh.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.IdCard15To18;
import com.hyjf.cs.trade.bean.assetpush.PushBean;
import com.hyjf.cs.trade.bean.assetpush.PushRequestBean;
import com.hyjf.cs.trade.bean.assetpush.PushResultBean;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.auth.AuthService;
import com.hyjf.cs.trade.service.borrow.ApiAssetPushService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.util.ErrorCodeConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author fuqiang
 * @version ApiAssetPushServcieImpl, v0.1 2018/6/11 18:06
 */
@Service
public class ApiAssetPushServiceImpl extends BaseTradeServiceImpl implements ApiAssetPushService {
	private static final Logger logger = LoggerFactory.getLogger(ApiAssetPushServiceImpl.class);
	private static final Long MAX_ASSET_MONEY = 1000000L;
	@Autowired
	private CommonProducer commonProducer;
	@Autowired
	private AuthService authService;
	/**
	 * 资产推送成功标志
	 */
	private ThreadLocal<Boolean> pushFlag = ThreadLocal.withInitial(() -> Boolean.FALSE);

	@Override
	public PushResultBean assetPush(PushRequestBean pushRequestBean) {
		logger.info("个人资产推送开始, pushRequestBean is: {}", pushRequestBean);
		// 机构编号
		String instCode = pushRequestBean.getInstCode();
		// 资产类别
		Integer assetType = pushRequestBean.getAssetType();

		PushResultBean resultBean = new PushResultBean();
		// 查看机构表是否存在
		HjhAssetBorrowTypeVO assetBorrow = amTradeClient.selectAssetBorrowType(instCode, assetType);
		if (assetBorrow == null) {
			logger.warn(instCode + "  " + assetType + " ------机构编号不存在");
			resultBean.setStatusDesc("机构编号不存在");
			return resultBean;
		}

		// 授信期内校验
		BailConfigInfoCustomizeVO bailConfig = amTradeClient.selectBailConfigByInstCode(instCode);
		if (bailConfig == null) {
			logger.warn("合作机构额度配置不存在:{}", instCode);
			resultBean.setStatusDesc("保证金配置不存在:{" + instCode + "}");
			return resultBean;
		}
		if (GetDate.getNowTime10() < GetDate.getDayStart10(bailConfig.getTimestart())
				|| GetDate.getNowTime10() > GetDate.getDayEnd10(bailConfig.getTimeend())) {
			logger.warn("未在授信期内，不能推标");
			resultBean.setStatusDesc("未在授信期内，不能推标");
			return resultBean;
		}

		// 获取还款方式,项目类型
		List<BorrowProjectRepayVO> projectRepays = amTradeClient.selectProjectRepay(assetBorrow.getBorrowCd() + "");

		// 检查请求资产总参数
		List<PushBean> assets = pushRequestBean.getReqData();
		if (CollectionUtils.isEmpty(assets)) {
			resultBean.setStatusDesc("推送资产不能为空");
			return resultBean;
		}
		if (assets.size() > 1000) {
			resultBean.setStatusDesc("请求参数过长");
			logger.warn("------请求参数过长-------");
			return resultBean;
		}
		logger.debug("资产详情, list is : {}", JSONObject.toJSONString(assets));

		// 返回结果
		List<PushBean> retAssets = new ArrayList<>();

		for (PushBean pushBean : assets) {
			try {
				pushBean = processAsset(pushBean, instCode, assetType, projectRepays);
			} catch (Exception e) {
				if (e instanceof DuplicateKeyException) {
					logger.warn("资产已入库，数据重复, assetId is: {}", pushBean.getAssetId());
					pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000008);
					pushBean.setRetMsg("资产已入库");
				} else {
					logger.error("系统异常,资产未进库, assetId is: {}", pushBean.getAssetId());
					pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000004);
					if (StringUtils.isBlank(pushBean.getRetMsg())) {
						pushBean.setRetMsg("系统异常,资产未进库");
					}
				}
			} finally {
				retAssets.add(pushBean);
			}
		}

		logger.info(instCode + " 结束推送资产....");
		return assembleResult(resultBean, retAssets, pushRequestBean.getRiskInfo());
	}

	/**
	 * 企业资产推送
	 *
	 * @param pushRequestBean
	 * @return
	 */
	@Override
	public PushResultBean companyAssetPush(PushRequestBean pushRequestBean) {
		logger.info("企业资产推送开始, pushRequestBean is: {}", pushRequestBean);
		// 机构编号
		String instCode = pushRequestBean.getInstCode();
		// 资产类别
		Integer assetType = pushRequestBean.getAssetType();

		PushResultBean resultBean = new PushResultBean();
		// 查看机构表是否存在
		HjhAssetBorrowTypeVO assetBorrow = amTradeClient.selectAssetBorrowType(instCode, assetType);
		if (assetBorrow == null) {
			logger.info(instCode + "  " + assetType + " ------机构编号不存在");
			resultBean.setStatus(ErrorCodeConstant.STATUS_CE000001);
			resultBean.setStatusDesc("机构编号不存在！");
			return resultBean;
		}

		// 授信期内校验
		BailConfigInfoCustomizeVO bailConfig = amTradeClient.selectBailConfigByInstCode(instCode);
		if (bailConfig == null) {
			logger.info("合作机构额度配置不存在:{}", instCode);
			resultBean.setStatusDesc("保证金配置不存在:{" + instCode + "}");
			return resultBean;
		}
		if (GetDate.getNowTime10() < GetDate.getDayStart10(bailConfig.getTimestart())
				|| GetDate.getNowTime10() > GetDate.getDayEnd10(bailConfig.getTimeend())) {
			logger.info("未在授信期内，不能推标");
			resultBean.setStatusDesc("未在授信期内，不能推标");
			return resultBean;
		}

		// 获取还款方式,项目类型
		List<BorrowProjectRepayVO> projectRepays = amTradeClient.selectProjectRepay(assetBorrow.getBorrowCd() + "");

		// 检查请求资产总参数
		List<PushBean> reqData = pushRequestBean.getReqData();
		if (CollectionUtils.isEmpty(reqData)) {
			resultBean.setStatusDesc("推送资产不能为空");
			return resultBean;
		}
		if (reqData.size() > 1000) {
			resultBean.setStatusDesc("请求参数过长");
			logger.error("------请求参数过长-------");
			return resultBean;
		}

		logger.debug("资产详情, list is : {}", JSONObject.toJSONString(reqData));
		// 返回结果
		List<PushBean> retAssets = new ArrayList<>();
		for (PushBean pushBean : reqData) {
			try {
				pushBean = processCompanyAsset(pushBean, instCode, assetType, projectRepays);
			} catch (Exception e) {
				logger.warn(this.getClass().getName(), "企业推送资产时，[assetId]重复，请更换");
				if (e instanceof DuplicateKeyException) {
					pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000008);
					pushBean.setRetMsg("资产已入库");
				} else {
					logger.error("系统异常,资产未进库, assetId is: {}", pushBean.getAssetId());
					pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000004);
					if (StringUtils.isBlank(pushBean.getRetMsg())) {
						pushBean.setRetMsg("系统异常,资产未进库");
					}
				}
			} finally {
				// 返回推送提示
				retAssets.add(pushBean);
			}

		}
		logger.info(instCode + " 推送资产结束");

		return assembleResult(resultBean, retAssets, pushRequestBean.getRiskInfo());
	}

	/**
	 * 导入商家信息，并返回资产推送结果
	 *
	 * @param resultBean
	 * @param retAssets
	 * @param riskInfo
	 * @return
	 */
	private PushResultBean assembleResult(PushResultBean resultBean, List<PushBean> retAssets,
										  List<InfoBean> riskInfo) {
		// 商家信息未解析版
		logger.info("----------------商家信息导入开始-----------------------");
		if (!CollectionUtils.isEmpty(riskInfo)) {
			logger.debug("riskInfo is : {}", JSONObject.toJSONString(riskInfo));
			if (riskInfo.size() > 1000) {
				resultBean.setStatusDesc("商家信息数量超限");
				logger.error("------------商家信息数量超限-----------");
				return resultBean;
			}
			amTradeClient.insertRiskInfo(riskInfo);
		}
		logger.info("----------------商家信息导入完成-----------------------");

		if (pushFlag.get()) {
			resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
			resultBean.setStatusDesc("资产推送成功");
			resultBean.setData(retAssets);
		} else {
			resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
			// 目前推送只有一条，如果多条不能这样取
			resultBean.setStatusDesc(retAssets.get(0).getRetMsg());
			resultBean.setData(retAssets);
		}
		return resultBean;
	}

	/**
	 * 企业资产数据处理
	 * @param pushBean
	 * @param instCode
	 * @param assetType
	 * @param projectRepays
	 * @return
	 */
	private PushBean processCompanyAsset(PushBean pushBean, String instCode, Integer assetType,
										 List<BorrowProjectRepayVO> projectRepays) {
		/*
		 * if (pushBean.getAccount() > MAX_ASSET_MONEY) {
		 * pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000006);
		 * pushBean.setRetMsg("资产金额超过一百万"); continue; }
		 */
		// 校验还款方式
		if (!checkIsMonthStyle(pushBean.getBorrowStyle(), pushBean.getIsMonth())
				|| !checkBorrowStyle(projectRepays, pushBean.getBorrowStyle())) {
			logger.info(instCode + " 还款方式不正确 " + projectRepays);
			pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000005);
			pushBean.setRetMsg("还款方式不正确,不支持这种还款方式");
			return pushBean;
		}

		// 受托支付
		STZHWhiteListVO stzAccount = null;
		if (pushBean.getEntrustedFlg() != null && pushBean.getEntrustedFlg().intValue() == 1) {
			// 校验
			if (org.apache.commons.lang.StringUtils.isBlank(pushBean.getEntrustedAccountId())) {
				pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000011);
				pushBean.setRetMsg("受托支付电子账户为空");
				return pushBean;
			}
			stzAccount = amTradeClient.selectStzfWhiteList(instCode, pushBean.getEntrustedAccountId());
			if (stzAccount == null) {
				pushBean.setRetCode("ZT000012");
				pushBean.setRetMsg("受托支付电子账户未授权");
				return pushBean;
			}
			// 校验受托人是否授权
			if (!authService.checkPaymentAuthStatus(stzAccount.getUserId())) {
				pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000011);
				pushBean.setRetMsg("受托账户未进行服务费授权");
				return pushBean;
			}
		}

		// 征信报告逾期情况
		if (StringUtils.isBlank(pushBean.getOverdueReport())) {
			pushBean.setOverdueReport("暂无数据");
		}
		// 重大负债状况
		if (StringUtils.isBlank(pushBean.getDebtSituation())) {
			pushBean.setDebtSituation("无");
		}
		// 其他平台借款情况
		if (StringUtils.isBlank(pushBean.getOtherBorrowed())) {
			pushBean.setOtherBorrowed("暂无数据");
		}
		// 借款资金运用情况
		if (StringUtils.isBlank(pushBean.getIsFunds())) {
			pushBean.setIsFunds("正常");
		}
		// 借款方经营状况及财务状况
		if (StringUtils.isBlank(pushBean.getIsManaged())) {
			pushBean.setIsManaged("正常");
		}
		// 借款方还款能力变化情况
		if (StringUtils.isBlank(pushBean.getIsAbility())) {
			pushBean.setIsAbility("正常");
		}
		// 借款人逾期情况
		if (StringUtils.isBlank(pushBean.getIsOverdue())) {
			pushBean.setIsOverdue("暂无");
		}
		// 借款人涉诉情况
		if (StringUtils.isBlank(pushBean.getIsComplaint())) {
			pushBean.setIsComplaint("暂无");
		}
		// 借款人受行政处罚情况
		if (StringUtils.isBlank(pushBean.getIsPunished())) {
			pushBean.setIsPunished("暂无");
		}

		// add by nxl 20180710互金系统,新添加企业注册地址,企业注册编码Start
		if (StringUtils.isBlank(pushBean.getRegistrationAddress()) || pushBean.getRegistrationAddress().length() > 99) {
			pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000013);
			pushBean.setRetMsg("企业注册地址信息不正确");
			return pushBean;
		}
		if (StringUtils.isBlank(pushBean.getCorporateCode())) {
			pushBean.setCorporateCode("");
		}
		// add by nxl 20180710互金系统,新添企业注册地址,企业注册编码End

		// 企业推送-必传字段非空校验
		if (!checkCompanyPushInfo(pushBean)) {
			logger.warn(instCode + "必传字段未传");
			pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000001);
			pushBean.setRetMsg("必传字段未传，请检查！");
			return pushBean;
		}

		// 通过用户名获得用户的详细信息
		UserVO users = amUserClient.selectUserInfoByUsername(pushBean.getUserName());
		// 判断用户是否注册
		if (users == null) {
			// 自动注册
			pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000001);
			pushBean.setRetMsg("没有用户信息，请注册！--users");
			return pushBean;
		}
		if (users.getUserType() == 0) {
			pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000001);
			pushBean.setRetMsg("用户类型不是企业用户");
			return pushBean;
		}
		// 通过用户id获得用户真实姓名和身份证号
		UserInfoVO userInfo = amUserClient.selectUserInfoByUserId(users.getUserId());
		if (userInfo == null) {
			pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000001);
			pushBean.setRetMsg("没有用户信息，请注册！--userinfo");
			return pushBean;
		}
		// 通过用户id获得借款人的开户电子账号
		BankOpenAccountVO bankOpenAccount = amUserClient.selectBankAccountById(users.getUserId());
		// 判断用户是否开户（汇盈金服、江西银行）
		if (bankOpenAccount == null) {
			pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000002);
			pushBean.setRetMsg("没有用户开户信息，请在线下开户！");
			return pushBean;
		}
		// 判断借款用户是否是机构合作用户
		if (users.getIsInstFlag() == 0 || users.getIsInstFlag().equals("0")) {
			pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000007);
			pushBean.setRetMsg("借款用户不是机构合作用户！");
			return pushBean;
		}
		// 查看用户对应的企业编号
		CorpOpenAccountRecordVO userCorpOpenAccountRecordInfo = amUserClient
				.selectUserBusiNameByUsername(pushBean.getUserName());
		if (userCorpOpenAccountRecordInfo == null) {
			pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000001);
			pushBean.setRetMsg("企业用户未录入对应的企业信息！");
			return pushBean;
		}
		// 判断借款人用户名所属企业与传入的企业名称是否一致
		if (!userCorpOpenAccountRecordInfo.getBusiName().equals(pushBean.getBorrowCompanyName())) {
			pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000001);
			pushBean.setRetMsg("借款人用户名所属企业与传入的企业名称不一致！");
			return pushBean;
		}

		Integer paymentAuth = users.getPaymentAuthStatus();
		HjhUserAuthVO hjhUserAuth = this.amUserClient.getHjhUserAuthByUserId(users.getUserId());
		Integer repayAuth = hjhUserAuth == null ? 0 : hjhUserAuth.getAutoRepayStatus();
		Integer authResult = authService.checkAuthStatus(repayAuth, paymentAuth);
		if (authResult == 5) {
			pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000011);
			pushBean.setRetMsg("借款人必须服务费授权");
			return pushBean;
		} else if (authResult == 6) {
			pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000012);
			pushBean.setRetMsg("借款人必须还款授权");
			return pushBean;
		} else if (authResult == 7) {
			pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000014);
			pushBean.setRetMsg("借款人必须服务费授权和还款授权");
			return pushBean;
		}

		logger.info(instCode + " 审核完成，开始推送资产 ");
		// 检查是否存在重复资产
		List<HjhPlanAssetVO> duplicateAssetId = amTradeClient.checkDuplicateAssetId(pushBean.getAssetId());
		if (!CollectionUtils.isEmpty(duplicateAssetId)) {
			logger.info(this.getClass().getName(), "企业推送资产时，[assetId]重复，请更换");
			pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000008);
			pushBean.setRetMsg("资产已入库");
			return pushBean;
		}

		return saveAssetAndSendNext(pushBean, instCode, assetType, userInfo.getIdcard(), userInfo.getUserId(),
				users.getMobile(), users.getUsername(), bankOpenAccount.getAccount(), userInfo.getTruename(),
				stzAccount, 1);
	}

	/**
	 * 个人资产数据处理
	 * @param pushBean
	 * @param instCode
	 * @param assetType
	 * @param projectRepays
	 * @return
	 */
	private PushBean processAsset(PushBean pushBean, String instCode, Integer assetType,
								  List<BorrowProjectRepayVO> projectRepays) {
		String truename = pushBean.getTruename();
		String idcard = pushBean.getIdcard();

		// 金额不是100以及100的整数倍时将通过接口拒绝接收资产
		if (pushBean.getAccount() == null || (pushBean.getAccount() % 10) != 0 || pushBean.getBorrowPeriod() == null
				|| StringUtils.isBlank(truename) || StringUtils.isBlank(idcard)
				|| StringUtils.isBlank(pushBean.getBorrowStyle())) {
			pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000007);
			pushBean.setRetMsg("资产信息不正确");
			return pushBean;
		}
		if (pushBean.getWorkCity() != null && pushBean.getWorkCity().length() > 20) {
			pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000007);
			pushBean.setRetMsg("资产信息不正确(工作城市过长)");
			return pushBean;
		}
		if (pushBean.getAccount() > MAX_ASSET_MONEY) {
			pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000006);
			pushBean.setRetMsg("资产金额超过一百万");
			return pushBean;
		}
		// 查询用户信息（user表）
		UserInfoVO userInfo = amUserClient.selectUserInfoByNameAndCard(truename, idcard);
		if (userInfo == null) {
			pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000001);
			pushBean.setRetMsg("没有用户信息");
			return pushBean;
		} else if (userInfo.getRoleId() != 2) {
			pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000003);
			pushBean.setRetMsg("用户不是借款人");
			return pushBean;
		}

		int userId = userInfo.getUserId();
		BankOpenAccountVO bankOpenAccount = amUserClient.selectBankAccountById(userId);
		if (bankOpenAccount == null) {
			pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000002);
			pushBean.setRetMsg("没有用户开户信息");
			return pushBean;
		}
		// 查询用户信息（userInfo表）
		UserVO user = amUserClient.selectUsersById(userId);
		if (user == null) {
			pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000001);
			pushBean.setRetMsg("没有用户信息");
			return pushBean;
		}
		// 校验还款方式
		if (!checkIsMonthStyle(pushBean.getBorrowStyle(), pushBean.getIsMonth())
				|| !checkBorrowStyle(projectRepays, pushBean.getBorrowStyle())) {
			logger.warn(instCode + " 还款方式不正确 " + projectRepays);
			pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000005);
			pushBean.setRetMsg("不支持这种还款方式");
			return pushBean;
		}
		// 受托支付
		STZHWhiteListVO stzAccount = null;
		if (pushBean.getEntrustedFlg() != null && pushBean.getEntrustedFlg().intValue() == 1) {
			// 校验
			if (StringUtils.isBlank(pushBean.getEntrustedAccountId())) {
				pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000011);
				pushBean.setRetMsg("受托支付电子账户为空");
				return pushBean;
			}
			// 查询用户受托支付电子账户是否授权
			stzAccount = amTradeClient.selectStzfWhiteList(instCode, pushBean.getEntrustedAccountId());
			if (stzAccount == null) {
				pushBean.setRetCode("ZT000012");
				pushBean.setRetMsg("受托支付电子账户未授权");
				return pushBean;
			}
			// 校验受托人是否授权
			if (!authService.checkPaymentAuthStatus(stzAccount.getUserId())) {
				pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000011);
				pushBean.setRetMsg("受托账户未进行服务费授权");
				// retAssets.add(pushBean);// 返回提示
				return pushBean;
			}
		}

		Integer paymentAuth = user.getPaymentAuthStatus();
		HjhUserAuthVO hjhUserAuth = this.amUserClient.getHjhUserAuthByUserId(userId);
		Integer repayAuth = hjhUserAuth == null ? 0 : hjhUserAuth.getAutoRepayStatus();
		Integer authResult = authService.checkAuthStatus(repayAuth, paymentAuth);
		if (authResult == 5) {
			pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000011);
			pushBean.setRetMsg("借款人必须服务费授权");
			return pushBean;
		} else if (authResult == 6) {
			pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000012);
			pushBean.setRetMsg("借款人必须还款授权");
			return pushBean;
		} else if (authResult == 7) {
			pushBean.setRetCode(ErrorCodeConstant.STATUS_CE000014);
			pushBean.setRetMsg("借款人必须服务费授权和还款授权");
			return pushBean;
		}

		// 信批需求(资产只有个人,若第三方不传则为默认值插入资产表中) start
		// // 年收入
		// if (StringUtils.isBlank(pushBean.getAnnualIncome())) {
		// pushBean.setAnnualIncome("10万以内");
		// }
		//

		// update by wj 2018-05-24 年收入，月收入非空校验 start
		if (StringUtils.isBlank(pushBean.getAnnualIncome())) {
			pushBean.setRetCode("ZT000013");
			pushBean.setRetMsg("年收入为空");
			return pushBean;
		}
		if (StringUtils.isBlank(pushBean.getMonthlyIncome())) {
			pushBean.setRetCode("ZT000014");
			pushBean.setRetMsg("月收入为空");
			return pushBean;
		}
		// update by wj 2018-05-24 年收入，月收入非空校验 end

		// 征信报告逾期情况
		if (StringUtils.isBlank(pushBean.getOverdueReport())) {
			pushBean.setOverdueReport("暂无数据");
		}
		// 重大负债状况
		if (StringUtils.isBlank(pushBean.getDebtSituation())) {
			pushBean.setDebtSituation("无");
		}
		// 其他平台借款情况
		if (StringUtils.isBlank(pushBean.getOtherBorrowed())) {
			pushBean.setOtherBorrowed("暂无数据");
		}
		// 借款资金运用情况
		if (StringUtils.isBlank(pushBean.getIsFunds())) {
			pushBean.setIsFunds("正常");
		}
		// 借款方经营状况及财务状况
		if (StringUtils.isBlank(pushBean.getIsManaged())) {
			pushBean.setIsManaged("正常");
		}
		// 借款方还款能力变化情况
		if (StringUtils.isBlank(pushBean.getIsAbility())) {
			pushBean.setIsAbility("正常");
		}
		// 借款人逾期情况
		if (StringUtils.isBlank(pushBean.getIsOverdue())) {
			pushBean.setIsOverdue("暂无");
		}
		// 借款人涉诉情况
		if (StringUtils.isBlank(pushBean.getIsComplaint())) {
			pushBean.setIsComplaint("暂无");
		}
		// 借款人受行政处罚情况
		if (StringUtils.isBlank(pushBean.getIsPunished())) {
			pushBean.setIsPunished("暂无");
		}
		// 信批需求(资产只有个人) end
		// add by nxl 20180710互金系统,新添加借款人地址 Start
		if (pushBean.getAddress() == null || pushBean.getAddress().length() > 99) {
			pushBean.setRetCode("ZT000015");
			pushBean.setRetMsg("借款人地址信息不正确");
			return pushBean;
		}
		// add by nxl 20180710互金系统,新添加借款人地址End

		return saveAssetAndSendNext(pushBean, instCode, assetType, idcard, userId, user.getMobile(), user.getUsername(),
				bankOpenAccount.getAccount(), userInfo.getTruename(), stzAccount, 0);
	}

	/**
	 * 保存资产信息
	 * @param pushBean
	 * @param instCode
	 * @param assetType
	 * @param idcard
	 * @param userId
	 * @param mobile
	 * @param username
	 * @param accountId
	 * @param trueName
	 * @param stzAccount
	 * @param borrowType
	 * @return
	 */
	private PushBean saveAssetAndSendNext(PushBean pushBean, String instCode, Integer assetType, String idcard,
										  int userId, String mobile, String username, String accountId, String trueName, STZHWhiteListVO stzAccount,
										  int borrowType) {

		HjhPlanAssetVO record = buildHjhPlanAsset(pushBean, instCode, assetType, idcard, userId, mobile, username,
				accountId, trueName, stzAccount, borrowType);
		// 推送资产
		int result = amTradeClient.insertAssert(record);
		if (result == 1) {
			pushFlag.set(Boolean.TRUE);
			pushBean.setRetCode(ErrorCodeConstant.SUCCESS);
			pushBean.setRetMsg("资产已入库");
			// 送到自动录标队列
			this.sendToAutoIssueRecoverQueue(record);
			logger.info("资产推送成功, 发送至自动录标队列, assetId is : {}", pushBean.getAssetId());
		} else {
			pushBean.setRetCode(ErrorCodeConstant.STATUS_ZT000004);
			pushBean.setRetMsg("系统异常,资产未进库");
			logger.warn("系统异常,资产未进库, assetId is : {}", pushBean.getAssetId());
		}
		return pushBean;
	}

	/**
	 * 发送录标队列
	 * @param hjhPlanAsset
	 */
	private void sendToAutoIssueRecoverQueue(HjhPlanAssetVO hjhPlanAsset) {
		JSONObject params = new JSONObject();
		params.put("assetId", hjhPlanAsset.getAssetId());
		params.put("instCode", hjhPlanAsset.getInstCode());
		try {
			// modify by liushouyi 防止队列触发太快，导致无法获得本事务变泵的数据，延时级别为2 延时5秒
			commonProducer.messageSendDelay(new MessageContent(MQConstant.AUTO_ISSUE_RECOVER_TOPIC,
					MQConstant.AUTO_ISSUE_RECOVER_PUSH_TAG, hjhPlanAsset.getAssetId(), params), 2);
		} catch (MQException e) {
			logger.error("自动录标发送消息失败...", e);
		}
	}

	/**
	 * 构建资产信息vo
	 * @param pushBean
	 * @param instCode
	 * @param assetType
	 * @param idcard
	 * @param userId
	 * @param mobile
	 * @param username
	 * @param accountId
	 * @param trueName
	 * @param stzAccount
	 * @param borrowType
	 * @return
	 */
	private HjhPlanAssetVO buildHjhPlanAsset(PushBean pushBean, String instCode, Integer assetType, String idcard,
											 int userId, String mobile, String username, String accountId, String trueName, STZHWhiteListVO stzAccount,
											 int borrowType) {
		// 包装资产信息
		HjhPlanAssetVO record = new HjhPlanAssetVO();
		// 信批需求新增字段属于选填(string)不加校验
		BeanUtils.copyProperties(pushBean, record);
		// 默认审核通过
		record.setVerifyStatus(1);
		// 状态
		record.setStatus(0);
		// 当前时间
		int nowTime = GetDate.getNowTime10();
		record.setRecieveTime(nowTime);
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		// 默认系统用户
		record.setCreateUserId(1);
		record.setUpdateUserId(1);
		// 受托支付
		if (stzAccount != null) {
			record.setEntrustedFlg(1);
			record.setEntrustedUserId(stzAccount.getStUserId());
			record.setEntrustedUserName(stzAccount.getStUserName());
			record.setEntrustedAccountId(stzAccount.getStAccountId());
		}

		// 0：个人，1：企业
		if (borrowType == 0) {
			record.setBorrowType(0);
			// 性别,如果没传，用身份证的
			String idCard = pushBean.getIdcard();
			// 性别
			int sexInt = Integer.parseInt(idCard.substring(16, 17));
			if (sexInt % 2 == 0) {
				sexInt = 2;
			} else {
				sexInt = 1;
			}

			record.setSex(sexInt);
			// 年纪，如果没传，用身份证的，从user表获取
			record.setAge(IdCard15To18.getAgeById(idcard));

			record.setFirstPayment("个人收入");
			record.setUseage("个人资金周转");
		} else if (borrowType == 1) {
			record.setBorrowType(1);
			record.setFirstPayment("经营收入");
			record.setUseage("经营资金周转");
		}

		record.setIdcard(idcard);
		record.setAccountId(accountId);
		record.setTruename(trueName);
		record.setInstCode(instCode);
		record.setAssetType(assetType);
		record.setUserId(userId);
		record.setUserName(username);
		record.setMobile(mobile);

		// 平台直接默认填写：写死字段
		record.setCreditLevel("AA");
		record.setCostIntrodution(
				StringUtils.isBlank(pushBean.getCostIntrodution()) ? "加入费用0元" : pushBean.getCostIntrodution());
		record.setLitigation("无或已处理");
		record.setOverdueTimes("0");
		record.setOverdueAmount("0");
		record.setSecondPayment(
				StringUtils.isBlank(pushBean.getSecondPayment()) ? "第三方保障" : pushBean.getSecondPayment());
		record.setOverdueTimes(StringUtils.isBlank(pushBean.getOverdueTimes()) ? "0" : pushBean.getOverdueTimes());
		record.setOverdueAmount(StringUtils.isBlank(pushBean.getOverdueAmount()) ? "0" : pushBean.getOverdueAmount());
		return record;
	}

	private boolean checkBorrowStyle(List<BorrowProjectRepayVO> projectRepays, String borrowStyle) {
		if (projectRepays == null) {
			return false;
		}
		for (BorrowProjectRepayVO borrowProjectRepayVO : projectRepays) {
			if (borrowStyle.equals(borrowProjectRepayVO.getRepayMethod())) {
				return true;
			}
		}

		return false;
	}

	private boolean checkIsMonthStyle(String borrowSytle, int isMontth) {
		if (isMontth == 0) {
			if ("endday".equals(borrowSytle)) {
				return true;
			} else {
				return false;
			}
		} else if (isMontth == 1) {
			if ("endday".equals(borrowSytle)) {
				return false;
			} else {
				return true;
			}
		}

		return false;
	}

	/**
	 * 企业资产推送，必填字段判断
	 *
	 * @param pushBean
	 * @return
	 */
	private boolean checkCompanyPushInfo(PushBean pushBean) {
		if (StringUtils.isBlank(pushBean.getBorrowCompanyName()) || StringUtils.isBlank(pushBean.getAssetId())
				|| pushBean.getBorrowPeriod() == null || pushBean.getIsMonth() == null
				|| StringUtils.isBlank(pushBean.getBorrowStyle()) || StringUtils.isBlank(pushBean.getUserName())
				|| StringUtils.isBlank(pushBean.getIndustryInvolved())
				|| StringUtils.isBlank(pushBean.getOverdueTimes()) || pushBean.getAccount() == null
				|| StringUtils.isBlank(pushBean.getUnifiedSocialCreditCode())
				|| StringUtils.isBlank(pushBean.getOverdueAmount())) {

			return false;
		}
		return true;
	}
}
