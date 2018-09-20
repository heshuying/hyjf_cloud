/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.tender.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.api.AutoTenderComboRequest;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsDetailVO;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.TenderBgVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserInfoCrmVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.UtmRegVO;
import com.hyjf.cs.trade.client.AccountClient;
import com.hyjf.cs.trade.client.AmMongoClient;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.client.BankOpenClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.mq.producer.AppChannelStatisticsDetailProducer;
import com.hyjf.cs.trade.mq.producer.CalculateInvestInterestProducer;
import com.hyjf.cs.trade.mq.producer.CouponTenderProducer;
import com.hyjf.cs.trade.mq.producer.UtmRegProducer;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.tender.TenderService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.calculate.FinancingServiceChargeUtils;
import com.hyjf.common.validator.Validator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
/**
 * @author libin
 * @version TenderServiceImpl.java, v0.1 2018年8月24日 上午10:42:54
 */
@Service
public class TenderServiceImpl extends BaseTradeServiceImpl implements TenderService{

    @Autowired
    BankOpenClient bankOpenClient;
    @Autowired
    AmUserClient amUserClient;
    @Autowired
    AccountClient accountClient;
    @Autowired
    AmTradeClient amTradeClient;
    @Autowired
    SystemConfig systemConfig;
    @Autowired
    private AmMongoClient amMongoClient;
    @Autowired
    private UtmRegProducer utmRegProducer;
    @Autowired
    private CalculateInvestInterestProducer calculateInvestInterestProducer;
    @Autowired
    private AppChannelStatisticsDetailProducer appChannelStatisticsProducer;
    @Autowired
    private CouponTenderProducer couponTenderProducer;
	public static JedisPool pool = RedisUtils.getPool();
    
	@Override
	public JSONObject checkAutoTenderParam(String borrowNid, String account, String bizAccount, String platform,
			String couponGrantId) {
		/*原CouponConfigCustomizeV2VO cuc = null;*/
		CouponUserVO cuc = null;
		// -1:有可用的优惠券，但是投资时不选择优惠券 空或null：用户没有可用优惠券
		/*
		 * if(StringUtils.isNotEmpty(couponGrantId) && !StringUtils.equals("-1",
		 * couponGrantId)){ cuc = this.getCouponUser(couponGrantId); }
		 */
		/*原BankOpenAccount accountChinapnrTender = this.getBankOpenAccount(bizAcount);   使用 crt.andAccountEqualTo(bankAccount);*/
		BankOpenAccountVO accountChinapnrTender = bankOpenClient.selectByAccountId(bizAccount);//原子也是 andAccountEqualTo
		
		// 用户未在平台开户
		if (accountChinapnrTender == null) {
			return jsonMessage("用户开户信息不存在", "1");
		}
		// 判断借款人开户信息是否存在
		if (StringUtils.isEmpty(accountChinapnrTender.getAccount())) {
			return jsonMessage("用户银行客户号不存在", "1");
		}
		
		Integer userIdInt = accountChinapnrTender.getUserId();
		String userId = "";
		
		if (userIdInt == null) {
            return jsonMessage("账户信息异常", "1");
        } else {
            userId = String.valueOf(userIdInt.intValue());
        }
		
		/*原UsersInfo usersInfo = this.getUserInfo(userIdInt); usersInfoExampleCriteria.andUserIdEqualTo(userId);*/
		UserInfoVO usersInfo = amUserClient.findUsersInfoById(userIdInt);// andUserIdEqualTo(userId);
		
		if (null != usersInfo) {
			if (usersInfo.getRoleId() == 3) {// 担保机构用户
				return jsonMessage("担保机构用户不能进行投资", "1");
			}
			/*if (usersInfo.getRoleId() == 2) {// 借款人不能投资
			    return jsonMessage("仅限出借人进行投资业务", "1");
            }*/
		} else {
			return jsonMessage("账户信息异常", "1");
		}
		
		// 判断用户userId是否存在
		if (StringUtils.isEmpty(userId)) {
			return jsonMessage("账户信息异常", "1");
		}
		
		/*原Users user = this.getUsers(Integer.parseInt(userId));*/
		UserVO user = amUserClient.findUserById(Integer.parseInt(userId));
		
		if (StringUtils.isNotEmpty(couponGrantId) && !StringUtils.equals("-1", couponGrantId)) {
			/*原cuc = this.getCouponUser(couponGrantId, userIdInt);*/
			cuc = amTradeClient.getCouponUser(Integer.valueOf(couponGrantId), userIdInt);
		}
		// 判断用户信息是否存在
		if (user == null) {
			return jsonMessage("用户信息不存在", "1");
		}

		// 判断用户是否设置了交易密码
		if (user.getIsSetPassword() == 0) {
			return jsonMessage("该用户未设置交易密码", "1");
		}

		// 缴费授权状态
//		if (this.checkPaymentAuth(userIdInt) != 1) {
//			return jsonMessage("该用户未缴费授权", "1");
//		}
		/*if (user.getPaymentAuthStatus() != 1) {
			return jsonMessage("该用户未缴费授权", "1");
		}*/
		
		// 判断是否自动投资授权
		/*原HjhUserAuth hjhUserAuth = this.getUserAuthInfo(userIdInt); hjhUserAuthExampleCriteria.andUserIdEqualTo(userId);*/
		HjhUserAuthVO hjhUserAuth = amTradeClient.getUserAuthByUserId(userIdInt); // andUserIdEqualTo(userId);
		
		if (hjhUserAuth == null || hjhUserAuth.getAutoInvesStatus() == null 
				|| hjhUserAuth.getAutoInvesStatus().intValue() != 1 
				|| hjhUserAuth.getAutoOrderId() == null) {
			return jsonMessage("您没有开通自动授权", "1");
		}
		
		// 判断用户测评有效期
		if (user.getIsEvaluationFlag() == 0) {
			return jsonMessage("根据监管要求，投资前必须进行风险测评", "1");// 未测评
		} else {
			if(user.getIsEvaluationFlag()==1 && null != user.getEvaluationExpiredTime()){
				//测评到期日
				Long lCreate = user.getEvaluationExpiredTime().getTime();
				//当前日期
				Long lNow = new Date().getTime();
				if (lCreate <= lNow) {
					//已过期需要重新评测
					return jsonMessage("根据监管要求，投资前必须进行风险测评", "1");// 测评过期
				}
	        } else {
	        	return jsonMessage("根据监管要求，投资前必须进行风险测评", "1");// 未测评
	        }
		}
		
		// 判断借款编号是否存在
		if (StringUtils.isEmpty(borrowNid)) {
			return jsonMessage("借款项目不存在", "1");
		}
		
		/*原Borrow borrow = this.getBorrowByNid(borrowNid);*/
		BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(borrowNid);
		
		// 判断借款信息是否存在
		if (borrow == null || borrow.getId() == null) {
			return jsonMessage("借款项目不存在", "1");
		}
		if (borrow.getUserId() == null) {
			return jsonMessage("借款人不存在", "1");
		}
		// 看标的是否关联计划 ，防止别有用心的guy从散标列表投资汇计划标的
		if(borrow.getIsShow() != null && borrow.getIsShow().intValue() ==1){
			if(StringUtils.isNotBlank(borrow.getPlanNid())){
				return jsonMessage("该标的绑定了计划", "1");
			}
		}

		Integer projectType = borrow.getProjectType();// 0，51老用户；1，新用户；2，全部用户
		if (projectType == null) {
			return jsonMessage("未设置该投资项目的项目类型", "1");
		}
		
		// 投资项目的配置信息
		/*原BorrowProjectType borrowProjectType = this.getBorrowProjectType(String.valueOf(projectType)); criteria2.andBorrowCdEqualTo(projectType);*/
		BorrowProjectTypeVO borrowProjectType = this.getProjectType(String.valueOf(projectType));//selectBorrowProjectByBorrowCd
		
		if (borrowProjectType == null) {
			return jsonMessage("未查询到该投资项目的配置信息", "1");
		}
		
		// 51老用户标  目前微服务info表中已经没有51用户字段了。此判断已经不需要了
/*		if (borrowProjectType.getInvestUserType().equals("0")) {
			boolean is51User = this.checkIs51UserCanInvest(Integer.parseInt(userId));
			if (!is51User) {
				return jsonMessage("该项目只能51老用户投资", "1");
			}
		}*/
		
		if (borrowProjectType.getInvestUserType().equals("1")) {
			boolean newUser = this.checkIsNewUserCanInvest(Integer.parseInt(userId));
			if (!newUser) {
				return jsonMessage("该项目只能新手投资", "1");
			}
		}

		// 项目投资客户端
		if (platform.equals("0") && borrow.getCanTransactionPc().equals("0")) {
			String tmpInfo = "";
			if (borrow.getCanTransactionAndroid().equals("1")) {
				tmpInfo += " Android端  ";
			}
			if (borrow.getCanTransactionIos().equals("1")) {
				tmpInfo += " Ios端  ";
			}
			if (borrow.getCanTransactionWei().equals("1")) {
				tmpInfo += " 微信端  ";
			}
			return jsonMessage("此项目只能在" + tmpInfo + "投资", "1");
		} else if (platform.equals("1") && borrow.getCanTransactionWei().equals("0")) {
			String tmpInfo = "";
			if (borrow.getCanTransactionAndroid().equals("1")) {
				tmpInfo += " Android端  ";
			}
			if (borrow.getCanTransactionIos().equals("1")) {
				tmpInfo += " Ios端  ";
			}
			if (borrow.getCanTransactionPc().equals("1")) {
				tmpInfo += " Pc端  ";
			}
			return jsonMessage("此项目只能在" + tmpInfo + "投资", "1");
		} else if (platform.equals("2") && borrow.getCanTransactionAndroid().equals("0")) {
			String tmpInfo = "";
			if (borrow.getCanTransactionPc().equals("1")) {
				tmpInfo += " PC端  ";
			}
			if (borrow.getCanTransactionIos().equals("1")) {
				tmpInfo += " Ios端  ";
			}
			if (borrow.getCanTransactionWei().equals("1")) {
				tmpInfo += " 微信端  ";
			}
			return jsonMessage("此项目只能在" + tmpInfo + "投资", "1");
		} else if (platform.equals("3") && borrow.getCanTransactionIos().equals("0")) {
			String tmpInfo = "";
			if (borrow.getCanTransactionPc().equals("1")) {
				tmpInfo += " PC端  ";
			}
			if (borrow.getCanTransactionAndroid().equals("1")) {
				tmpInfo += " Android端  ";
			}
			if (borrow.getCanTransactionWei().equals("1")) {
				tmpInfo += " 微信端  ";
			}
			return jsonMessage("此项目只能在" + tmpInfo + "投资", "1");
		}
		
		/*原BankOpenAccount accountChinapnrBorrower = this.getBankOpenAccount(borrow.getUserId()); crt.andUserIdEqualTo(userId);*/
		BankOpenAccountVO accountChinapnrBorrower = this.amUserClient.selectBankAccountById(borrow.getUserId());// crt.andUserIdEqualTo(userId);
		
		if (accountChinapnrBorrower == null) {
			return jsonMessage("借款人未开户", "1");
		}
		if (StringUtils.isEmpty(accountChinapnrBorrower.getAccount())) {
			return jsonMessage("借款人汇付客户号不存在", "1");
		}
		if (userId.equals(String.valueOf(borrow.getUserId()))) {
			return jsonMessage("借款人不可以投资自己的项目", "1");
		}
		// 判断借款是否流标
		if (borrow.getStatus() == 6) { // 流标
			return jsonMessage("此项目已经流标", "1");
		}
		// 已满标
		if (borrow.getBorrowFullStatus() == 1) {
			return jsonMessage("此项目已经满标", "1");
		}
		// 判断用户投资金额是否为空
		if (!(StringUtils.isNotEmpty(account) || (StringUtils.isEmpty(account) && cuc != null && cuc.getCouponType() == 3)
				|| (StringUtils.isEmpty(account) && cuc != null && cuc.getCouponType() == 1 && cuc.getAddFlg() == 1))) {
			return jsonMessage("请输入投资金额", "1");
		}
		// 还款金额是否数值
		try {
			// 投资金额必须是整数
			Long accountInt = Long.parseLong(account);
			if ((accountInt == 0 && cuc == null) || (accountInt == 0 && cuc != null && cuc.getCouponType() == 2)) {
				return jsonMessage("投资金额不能为0元", "1");
			}
			if (accountInt != 0 && cuc != null && cuc.getCouponType() == 1 && cuc.getAddFlg() == 1) {
				return jsonMessage("该优惠券只能单独使用", "1");
			}
			if (accountInt < 0) {
				return jsonMessage("投资金额不能为负数", "1");
			}
			// 新需求判断顺序变化
			// 将投资金额转化为BigDecimal
			BigDecimal accountBigDecimal = new BigDecimal(account);
			// String balance = RedisUtils.get(borrowNid);
			String balance = "10000";
			if (StringUtils.isEmpty(balance)) {
				return jsonMessage("您来晚了，下次再来抢吧", "1");
			}
			// 剩余可投金额
			Integer min = borrow.getTenderAccountMin();
			// 当剩余可投金额小于最低起投金额，不做最低起投金额的限制
			if (min != null && min != 0 && new BigDecimal(balance).compareTo(new BigDecimal(min)) == -1) {
				if (accountBigDecimal.compareTo(new BigDecimal(balance)) == 1) {
					return jsonMessage("剩余可投金额为" + balance + "元", "1");
				}
				if (accountBigDecimal.compareTo(new BigDecimal(balance)) != 0) {
					return jsonMessage("剩余可投只剩" + balance + "元，须全部购买", "1");
				}
			} else {
				// 项目的剩余金额大于最低起投金额
				if (accountBigDecimal.compareTo(new BigDecimal(min)) == -1) {
					if (accountBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
						if (cuc != null && cuc.getCouponType() != 3 && cuc.getCouponType() != 1) {
							return jsonMessage(borrow.getTenderAccountMin() + "元起投", "1");
						}
					} else {
						return jsonMessage(borrow.getTenderAccountMin() + "元起投", "1");
					}
				} else {
					Integer max = borrow.getTenderAccountMax();
					if (max != null && max != 0 && accountBigDecimal.compareTo(new BigDecimal(max)) == 1) {
						return jsonMessage("项目最大投资额为" + max + "元", "1");
					}
				}
			}
			if (accountBigDecimal.compareTo(borrow.getAccount()) > 0) {
				return jsonMessage("投资金额不能大于项目总额", "1");
			}
			
			// 投资人记录
			/*原Account tenderAccount = this.getAccount(Integer.parseInt(userId)); criteria.andUserIdEqualTo(userId);*/
			AccountVO tenderAccount = accountClient.getAccountByUserId(Integer.parseInt(userId));//criteria.andUserIdEqualTo(userId);
			
			if (tenderAccount.getBankBalance().compareTo(accountBigDecimal) < 0) {
				return jsonMessage("余额不足，请充值！", "1");
			}
			// 判断用户是否禁用
			if (user.getStatus() == 1) {// 0启用，1禁用
				return jsonMessage("该用户已被禁用", "1");
			}
			// redis剩余金额不足
			if (accountBigDecimal.compareTo(new BigDecimal(balance)) == 1) {
				return jsonMessage("剩余可投金额为" + balance + "元", "1");
			}
			
			// 根据项目标号获取相应的项目信息
			/*原WebProjectDetailCustomize borrowDetail = webProjectListCustomizeMapper.selectProjectDetail(borrowNid);*/
			/*WebProjectDetailCustomize 和 ProjectCustomeDetailVO 所含字段一致 查询也一致*/
			ProjectCustomeDetailVO borrowDetail = amTradeClient.selectProjectDetail(borrowNid);
			
			// add by cwyang
			// 在只使用代金券和体验金,并且没有本金的情况下,不进行投资递增金额的判断,在投资金额等于最大可投金额时也不做递增金额的判断
			if (!(cuc != null && (cuc.getCouponType() == 3 || cuc.getCouponType() == 1) && accountInt == 0)) {
				if (borrowDetail.getIncreaseMoney() != null && (accountInt - min) % Integer.parseInt(borrowDetail.getIncreaseMoney()) != 0 && accountBigDecimal.compareTo(new BigDecimal(balance)) == -1
						&& accountInt.compareTo(Long.parseLong(borrow.getTenderAccountMax() + "")) == -1) {
					return jsonMessage("投资递增金额须为" + borrowDetail.getIncreaseMoney() + " 元的整数倍", "1");
				}
			}
			// 如果验证没问题，则返回投资人借款人的汇付账号
			String borrowerUsrcustid = accountChinapnrBorrower.getAccount();
			String tenderUsrcustid = accountChinapnrTender.getAccount();
			JSONObject jsonMessage = new JSONObject();
			jsonMessage.put("error", "0");
			jsonMessage.put("borrowerUsrcustid", borrowerUsrcustid);
			jsonMessage.put("tenderUsrcustid", tenderUsrcustid);
			jsonMessage.put("borrowId", borrow.getId());
			jsonMessage.put("tenderUserName", user.getUsername());
			jsonMessage.put("tenderAutoOrderId", hjhUserAuth.getAutoOrderId());
			jsonMessage.put("userId", userId);
			return jsonMessage;
		} catch (Exception e) {
			logger.error("投资校验异常...", e);
			return jsonMessage("投资金额必须为整数", "1");
		}
	}
	
	/**
	 * 拼装返回信息
	 * 
	 * @param data
	 * @param error
	 * @return
	 */
	public JSONObject jsonMessage(String data, String error) {
		JSONObject jo = null;
		if (Validator.isNotNull(data)) {
			jo = new JSONObject();
			jo.put("error", error);
			jo.put("data", data);
		}
		return jo;
	}
	
    /**
     * 获取项目类型
     * @param projectType
     * @return
     */
    private BorrowProjectTypeVO getProjectType(String projectType) {
        BorrowProjectTypeVO borrowProjectType = null;
        List<BorrowProjectTypeVO> projectTypes = amTradeClient.selectBorrowProjectByBorrowCd(projectType);
        if (projectTypes != null && projectTypes.size() == 1) {
            borrowProjectType = projectTypes.get(0);
        }
        return borrowProjectType;
    }

	/**
	 * 
	 * 投资预插入
	 * 
	 * @param borrowNid
	 * @param orderId
	 * @param userId
	 * @param account
	 * @param ip
	 * @return
	 * @author Administrator
	 * @throws Exception
	 */
	@Override
	public boolean updateTenderLog(AutoTenderComboRequest autoTenderComboRequest) {
		boolean tenderTmpInfoFlag = amTradeClient.updateTenderLog(autoTenderComboRequest);
		return tenderTmpInfoFlag;
	}

	/**
	 * 投资失败后,投标申请撤销
	 * 
	 * @param borrowUserId
	 * @param investUserId
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean bidCancel(int investUserId, String productId, String orgOrderId, String txAmount) throws Exception {
		// 投资人的账户信息
		logger.info(this.getClass().getName(), "bidCancel","投资失败,开始撤销订单号为:" + orgOrderId + "的投资!");
		/*原BankOpenAccount outCust = this.getBankOpenAccount(investUserId);*/
		BankOpenAccountVO outCust = this.getBankOpenAccount(investUserId);
		if (outCust == null) {
			throw new Exception("投资人未开户。[投资人ID：" + investUserId + "]，" + "[投资订单号：" + orgOrderId + "]");
		}
		String tenderAccountId = outCust.getAccount();
		// 调用交易查询接口(投资撤销)
		BankCallBean queryTransStatBean = bidCancel(investUserId, tenderAccountId, productId, orgOrderId, txAmount);
		
		if (queryTransStatBean == null) {
			throw new Exception("调用投标申请撤销失败。" + ",[投资订单号：" + orgOrderId + "]");
		} else {
			String queryRespCode = queryTransStatBean.getRetCode();
			logger.info(this.getClass().getName(), "bidCancel","投资失败交易接口查询接口返回码：" + queryRespCode);
  			// 调用接口失败时(000以外)
			if (!BankCallConstant.RESPCODE_SUCCESS.equals(queryRespCode)) {
				String message = queryTransStatBean == null ? "" : queryTransStatBean.getRetMsg();
				logger.error(this.getClass().getName(), "bidCancel", "调用交易查询接口(解冻)失败。" + message + ",[投资订单号：" + orgOrderId + "]", null);
				throw new Exception("调用投标申请撤销失败。" + queryRespCode + "：" + message + ",[投资订单号：" + orgOrderId + "]");
			}else if (queryRespCode.equals(BankCallConstant.RETCODE_BIDAPPLY_NOT_EXIST1) || queryRespCode.equals(BankCallConstant.RETCODE_BIDAPPLY_NOT_EXIST2)) {
				logger.info("===============冻结记录不存在,不予处理========");
				/*原deleteBorrowTenderTmp(orgOrderId);   example.createCriteria().andNidEqualTo(orgOrderId);*/
				amTradeClient.deleteBorrowTenderTmp(orgOrderId);
				return true;
			} else if (queryRespCode.equals(BankCallConstant.RETCODE_BIDAPPLY_NOT_RIGHT)) {
				logger.info("===============只能撤销投标状态为投标中的标的============");
				return false;
			} else {
				/*deleteBorrowTenderTmp(orgOrderId);*/
				amTradeClient.deleteBorrowTenderTmp(orgOrderId);
				return true;
			}
		}
	}
	
	/**
	 * 投标失败后,调用投资撤销接口
	 * 
	 * @param productId
	 * @param orgOrderId
	 * @param investUserAccountId
	 * @return
	 */
	private BankCallBean bidCancel(Integer investUserId, String investUserAccountId, String productId, String orgOrderId, String txAmount) {
		String methodName = "bidCancel";
		// 调用汇付接口(交易状态查询)
		BankCallBean bean = new BankCallBean();
		String orderId = GetOrderIdUtils.getOrderId2(investUserId);
		/*String bankCode = PropUtils.getSystem(BankCallConstant.BANK_BANKCODE);*/
		String bankCode = systemConfig.getBankBankcode();
		/*String instCode = PropUtils.getSystem(BankCallConstant.BANK_INSTCODE);*/
		String instCode = systemConfig.getBankInstcode();
		UserVO investUser = this.getUsers(investUserId);
		bean.setVersion(BankCallConstant.VERSION_10); // 版本号(必须)
		bean.setTxCode(BankCallMethodConstant.TXCODE_BID_CANCEL); // 交易代码
		bean.setInstCode(instCode);
		bean.setBankCode(bankCode);
		bean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
		bean.setTxTime(GetOrderIdUtils.getTxTime()); // 交易时间
		bean.setSeqNo(GetOrderIdUtils.getSeqNo(6)); // 交易流水号
		bean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
		bean.setAccountId(investUserAccountId);// 电子账号
		bean.setOrderId(orderId); // 订单号(必须)
		bean.setTxAmount(CustomUtil.formatAmount(txAmount));// 交易金额
		bean.setProductId(productId);// 标的号
		bean.setOrgOrderId(orgOrderId);// 原标的订单号
		bean.setLogOrderId(orderId);// 订单号
		bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单日期
		bean.setLogUserId(String.valueOf(investUserId));// 用户Id
		bean.setLogUserName(investUser == null ? "" : investUser.getUsername()); // 用户名
		bean.setLogRemark("投标申请撤销"); // 备注
		// 调用汇付接口
		BankCallBean chinapnrBean = BankCallUtils.callApiBg(bean);
		if (chinapnrBean == null) {
			logger.error(this.getClass().getName(), methodName, new Exception("调用交易状态查询接口失败![参数：" + bean.getAllParams() + "]"));
			return null;
		}
		return chinapnrBean;
	}

	/**
	 * 删除投资记录临时表
	 * 
	 * @param userId
	 * @param borrowNid
	 * @return
	 * @author Administrator
	 */
	@Override
	public boolean deleteBorrowTenderTmpByParam(int userId, String borrowNid, String orderId) {
		boolean tenderTempFlag = amTradeClient.deleteBorrowTenderTmpByParam(userId,borrowNid,orderId) > 0 ? true : false;
		return tenderTempFlag;
	}

	@Override
	public JSONObject userAutoTender(BorrowAndInfoVO borrow, BankCallBean bean,String couponGrantId) throws Exception {
		// 1.获取封装的信息
		Integer userId = Integer.parseInt(bean.getLogUserId());// 投资人id
		String txAmount = bean.getTxAmount();// 借款金额
		String borrowNid = borrow.getBorrowNid();// 项目编号
		// 2.准备返回消息
		JSONObject result = null;
		// 3.redis扣减
		result = this.redisTender(userId, borrowNid, txAmount);
		// redis结果状态，只有当 status = 1时为成功可以继续，为0时意味着redis失败
		String redisStatus = result.getString("status");
		if (redisStatus.equals("1")) {
	        // 4.当前面的redis操作成功后，才能操作user和trade的各种表(原来是user，trade合在一起，微服务后拆分)
			result = this.borrowTender(borrow, bean);
	        logger.info("用户:{},投资成功，金额：{}，优惠券开始调用ID：{}" ,userId, txAmount,couponGrantId);
	        // 如果用了优惠券
	        if (StringUtils.isNotEmpty(couponGrantId)) {
	            // 开始使用优惠券
	            Map<String, String> params = new HashMap<String, String>();
	            params.put("mqMsgId", GetCode.getRandomCode(10));
	            // 真实投资金额
	            params.put("money", bean.getTxAmount());
	            // 借款项目编号
	            params.put("borrowNid", borrowNid);
	            // 平台
	            params.put("platform", bean.getLogClient()+"");
	            // 优惠券id
	            params.put("couponGrantId", couponGrantId);
	            // ip
	            params.put("ip", bean.getLogIp());
	            // 真实投资订单号
	            params.put("ordId", bean.getLogOrderId());
	            // 用户编号
	            params.put("userId", userId+"");
	            try {
	                couponTenderProducer.messageSend(new MessageContent(MQConstant.HZT_COUPON_TENDER_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(params)));
	            } catch (MQException e) {
	                logger.error("使用优惠券异常,userId:{},ordId:{},couponGrantId:{},borrowNid:{}",userId,bean.getLogOrderId(),couponGrantId,borrowNid);
	                e.printStackTrace();
	            }
	        }
		}
		return result;
	}
	
	private JSONObject redisTender(int userId, String borrowNid, String txAmount) {
		Jedis jedis = pool.getResource();
		String status = "0"; // 发送状态
		JSONObject info = new JSONObject();
		BigDecimal accountDecimal = new BigDecimal(txAmount);// 冻结前验证
		/*原String accountRedisWait = RedisUtils.get(borrowNid);*/
		String accountRedisWait = RedisUtils.get(RedisConstants.BORROW_NID+borrowNid);
		if (StringUtils.isNotBlank(accountRedisWait)) {
			// 操作redis
			while ("OK".equals(jedis.watch(borrowNid))) {
				/*accountRedisWait = RedisUtils.get(borrowNid);*/
				accountRedisWait = RedisUtils.get(RedisConstants.BORROW_NID+borrowNid);
				if (StringUtils.isNotBlank(accountRedisWait)) {
					logger.info("PC用户:" + userId + "***冻结前可投金额：" + accountRedisWait + "，标的号:" + borrowNid);
					if (new BigDecimal(accountRedisWait).compareTo(BigDecimal.ZERO) == 0) {
						info.put("message", "您来晚了，下次再来抢吧！");
						info.put("status", status);
						break;
					} else {
						if (new BigDecimal(accountRedisWait).compareTo(accountDecimal) < 0) {
							info.put("message", "可投剩余金额为" + accountRedisWait + "元！");
							info.put("status", status);
							break;
						} else {
							Transaction transaction = jedis.multi();
							BigDecimal lastAccount = new BigDecimal(accountRedisWait).subtract(accountDecimal);
							transaction.set(borrowNid, lastAccount.toString());
							List<Object> result = transaction.exec();
							if (result == null || result.isEmpty()) {
								jedis.unwatch();
							} else {
								String ret = (String) result.get(0);
								if (ret != null && ret.equals("OK")) {
									status = "1";
									info.put("message", "redis操作成功！");
									info.put("status", status);
									logger.info("PC用户:" + userId + "***冻结前减redis：" + accountDecimal + "，扣减成功，标的号:" + borrowNid);
									break;
								} else {
									jedis.unwatch();
								}
							}
						}
					}
				} else {
					info.put("message", "您来晚了，下次再来抢吧！");
					info.put("status", status);
					break;
				}
			}
		} else {
			info.put("message", "您来晚了，下次再来抢吧！");
			info.put("status", status);
		}
		return info;
	}
	
	/**
     * 操作数据库表
     *
     * @param borrow
     * @param bean
     */
    private JSONObject borrowTender(BorrowAndInfoVO borrow, BankCallBean bean) {
    	/*1.用于返回成功失败消息*/
    	JSONObject msg = new JSONObject();
    	/*2.准备borrowtender表实体，因为表中涉及到user信息提前准备*/
        TenderBgVO tenderBg = new TenderBgVO();
    	BigDecimal accountDecimal = new BigDecimal(bean.getTxAmount());// 将投资金额转成小数
        tenderBg.setAccountDecimal(accountDecimal);
        tenderBg.setAccountId(bean.getAccountId());
        tenderBg.setBorrowNid(borrow.getBorrowNid());
        tenderBg.setOrderId(bean.getOrderId());
        tenderBg.setRetCode(bean.getRetCode());
        tenderBg.setUserId(Integer.parseInt(bean.getLogUserId()));
        tenderBg.setIp(bean.getLogIp());
        tenderBg.setTxDate(bean.getTxDate());
        tenderBg.setTxTime(bean.getTxTime());
        tenderBg.setSeqNo(bean.getSeqNo());
    	
        UserInfoVO userInfo = amUserClient.findUsersInfoById(Integer.parseInt(bean.getLogUserId()));
        tenderBg.setTenderUserAttribute(userInfo.getAttribute());
        tenderBg.setClient(bean.getLogClient());
        Integer attribute = null;
        
        /*查询涉及到用户推荐人以及部分的信息，根据用户属性不同予以区分插入 start*/
        if (userInfo != null) {
            // 获取投资用户的用户属性
            attribute = userInfo.getAttribute();
            if (attribute != null) {
                // 投资人用户属性
                tenderBg.setTenderUserAttribute(attribute);
                // 如果是线上员工或线下员工，推荐人的userId和username不插
                if (attribute == 2 || attribute == 3) {
                    UserInfoCrmVO userInfoCustomize = amUserClient.queryUserCrmInfoByUserId(Integer.parseInt(bean.getLogUserId()));
                    if (userInfoCustomize != null) {
                        tenderBg.setInviteRegionId(userInfoCustomize.getRegionId());
                        tenderBg.setInviteRegionName(userInfoCustomize.getRegionName());
                        tenderBg.setInviteBranchId(userInfoCustomize.getBranchId());
                        tenderBg.setInviteBranchName(userInfoCustomize.getBranchName());
                        tenderBg.setInviteDepartmentId(userInfoCustomize.getDepartmentId());
                        tenderBg.setInviteDepartmentName(userInfoCustomize.getDepartmentName());
                    }
                } else if (attribute == 1) {
                    UserVO spreadsUsers = amUserClient.getSpreadsUsersByUserId(Integer.parseInt(bean.getLogUserId()));
                    if (spreadsUsers != null) {
                        int refUserId = spreadsUsers.getUserId();
                        // 查找用户推荐人
                        tenderBg.setInviteUserId(spreadsUsers.getUserId());
                        tenderBg.setInviteUserName(spreadsUsers.getUsername());
                        // 推荐人信息
                        UserInfoVO refUsers = amUserClient.findUsersInfoById(refUserId);
                        // 推荐人用户属性
                        if (refUsers != null) {
                            tenderBg.setInviteUserAttribute(refUsers.getAttribute());
                        }
                        // 查找用户推荐人部门
                        UserInfoCrmVO userInfoCustomize = amUserClient.queryUserCrmInfoByUserId(refUserId);
                        if (userInfoCustomize != null) {
                            tenderBg.setInviteRegionId(userInfoCustomize.getRegionId());
                            tenderBg.setInviteRegionName(userInfoCustomize.getRegionName());
                            tenderBg.setInviteBranchId(userInfoCustomize.getBranchId());
                            tenderBg.setInviteBranchName(userInfoCustomize.getBranchName());
                            tenderBg.setInviteDepartmentId(userInfoCustomize.getDepartmentId());
                            tenderBg.setInviteDepartmentName(userInfoCustomize.getDepartmentName());
                        }
                    }
                } else if (attribute == 0) {
                    UserVO spreadsUsers = amUserClient.getSpreadsUsersByUserId(Integer.parseInt(bean.getLogUserId()));
                    if (spreadsUsers != null) {
                        int refUserId = spreadsUsers.getUserId();
                        // 查找推荐人
                        tenderBg.setInviteUserId(spreadsUsers.getUserId());
                        tenderBg.setInviteUserName(spreadsUsers.getUsername());
                        // 推荐人信息
                        UserInfoVO refUsers = amUserClient.findUsersInfoById(refUserId);
                        // 推荐人用户属性
                        if (refUsers != null) {
                            tenderBg.setInviteUserAttribute(refUsers.getAttribute());
                        }
                    }
                }
            }
        }
        /*查询涉及到用户推荐人以及部分的信息，根据用户属性不同予以区分插入 end*/
        
        /*计算单笔投资的融资服务费 start*/
        String borrowStyle = borrow.getBorrowStyle();
        BigDecimal perService = new BigDecimal(0);
        // 服务费率
        BigDecimal serviceFeeRate = Validator.isNull(borrow.getServiceFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getServiceFeeRate());
        if (StringUtils.isNotEmpty(borrow.getBorrowStyle())) {
            BigDecimal serviceScale = serviceFeeRate;
            // 到期还本还息end/先息后本endmonth/等额本息month/等额本金principal
            if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_END.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                    || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
                perService = FinancingServiceChargeUtils.getMonthsPrincipalServiceCharge(accountDecimal, serviceScale);
            }
            // 按天计息到期还本还息
            else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                perService = FinancingServiceChargeUtils.getDaysPrincipalServiceCharge(accountDecimal, serviceScale, borrow.getBorrowPeriod());
            }
        }
        tenderBg.setPerService(perService);
        /*计算单笔投资的融资服务费 end*/
        
        
        /*投资授权码 投资结果授权码 start*/
        if (StringUtils.isNotBlank(bean.getAuthCode())) {
            tenderBg.setAuthCode(bean.getAuthCode());
        }
        /*投资授权码 投资结果授权码 start*/
        /*3.borrowtender表数据准备好，更新trade库所有相关的表*/
        boolean insertFlag = amTradeClient.borrowTender(tenderBg);
		if (!insertFlag) {
			msg.put("message", "投资失败！");
			msg.put("status", 0);
			return msg;
		}
		logger.info("trade库更新完毕！");
        if (insertFlag) {
        	/*4.更新user库相关的表*/
            updateUtm(Integer.parseInt(bean.getLogUserId()), tenderBg.getAccountDecimal(), GetDate.getNowTime10(), borrow);
            logger.info("user库更新完毕！");
            

            // 原属于trade更表的的 网站累计投资追加 投资、收益统计表 不在原子层做，转到组合层发MQ
            JSONObject params = new JSONObject();
            params.put("tenderSum", accountDecimal);
            params.put("nowTime", GetDate.getDate(GetDate.getNowTime10()));
            // 投资修改mongodb运营数据
            params.put("type", 1);
            params.put("money", accountDecimal);
            try {
                // 网站累计投资追加
                // 投资修改mongodb运营数据
                calculateInvestInterestProducer.messageSend(new MessageContent(MQConstant.STATISTICS_CALCULATE_INVEST_INTEREST_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(params)));
                // 满标发短信在原子层
            } catch (MQException e) {
                e.printStackTrace();
            }
        }
        return msg;
    }
    
    private void updateUtm(Integer userId, BigDecimal accountDecimal, Integer nowTime, BorrowAndInfoVO borrow) {
        //更新汇计划列表成功的前提下
        // 更新渠道统计用户累计投资
        // 投资人信息
        UserVO users = amUserClient.findUserById(userId);
        if (users != null) {
            // 更新渠道统计用户累计投资 从mongo里面查询
            AppChannelStatisticsDetailVO appChannelStatisticsDetails = amMongoClient.getAppChannelStatisticsDetailByUserId(users.getUserId());
            if (appChannelStatisticsDetails != null) {
                Map<String, Object> params = new HashMap<String, Object>();
                // 认购本金
                params.put("accountDecimal", accountDecimal);
                // 投资时间
                params.put("investTime", nowTime);
                // 项目类型
                if (borrow.getProjectType() == 13) {
                    params.put("projectType", "汇金理财");
                } else {
                    params.put("projectType", "汇直投");
                }
                // 首次投标项目期限
                String investProjectPeriod = "";
                String borrowStyle = borrow.getBorrowStyle();
                if ("endday".equals(borrowStyle)) {
                    investProjectPeriod = borrow.getBorrowPeriod() + "天";
                } else {
                    investProjectPeriod = borrow.getBorrowPeriod() + "月";
                }
                params.put("investProjectPeriod", investProjectPeriod);
                //根据investFlag标志位来决定更新哪种投资
                params.put("investFlag", checkIsNewUserCanInvest(userId));
                //压入消息队列
                try {
                    appChannelStatisticsProducer.messageSend(new MessageContent(MQConstant.APP_CHANNEL_STATISTICS_DETAIL_TOPIC,
                            MQConstant.APP_CHANNEL_STATISTICS_DETAIL_INVEST_TAG, UUID.randomUUID().toString(), JSON.toJSONBytes(params)));
                } catch (MQException e) {
                    e.printStackTrace();
                    logger.error("渠道统计用户累计投资推送消息队列失败！！！");
                }
            } else {
                // 更新huiyingdai_utm_reg的首投信息
                UtmRegVO utmReg = amUserClient.findUtmRegByUserId(userId);
                if (utmReg != null) {
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("id", utmReg.getId());
                    params.put("accountDecimal", accountDecimal);
                    // 投资时间
                    params.put("investTime", nowTime);
                    // 项目类型
                    params.put("projectType", "汇计划");
                    String investProjectPeriod = "";
                    // 首次投标项目期限// 还款方式
                    String borrowStyle = borrow.getBorrowStyle();
                    if ("endday".equals(borrowStyle)) {
                        investProjectPeriod = borrow.getBorrowPeriod() + "天";
                    } else {
                        investProjectPeriod = borrow.getBorrowPeriod() + "月";
                    }
                    // 首次投标项目期限
                    params.put("investProjectPeriod", investProjectPeriod);
                    // 更新渠道统计用户累计投资
                    try {
                        if(this.checkIsNewUserCanInvest(userId)){
                            utmRegProducer.messageSend(new MessageContent(MQConstant.STATISTICS_UTM_REG_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(params)));
                        }
                    } catch (MQException e) {
                        e.printStackTrace();
                        logger.error("更新huiyingdai_utm_reg的首投信息失败");
                    }
                }
            }
        }
    }
	
}
