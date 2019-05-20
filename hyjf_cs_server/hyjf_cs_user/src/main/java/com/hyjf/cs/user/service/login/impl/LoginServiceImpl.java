/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.login.impl;

import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.vo.admin.AdminBankAccountCheckCustomizeVO;
import com.hyjf.am.vo.admin.locked.LockedUserInfoVO;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.am.vo.user.HjhUserAuthConfigVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.*;
import com.hyjf.common.util.calculate.DateUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.AuthBean;
import com.hyjf.cs.user.bean.BaseDefine;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmMarketClient;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.config.locked.LockedConfigManager;
import com.hyjf.cs.user.constants.VipImageUrlEnum;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.service.auth.AuthService;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.login.LoginService;
import com.hyjf.cs.user.service.synbalance.SynBalanceService;
import com.hyjf.cs.user.vo.UserParameters;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author zhangqingqing
 * @version LoginServiceImpl, v0.1 2018/6/11 15:32
 */
@Service
public class LoginServiceImpl extends BaseUserServiceImpl implements LoginService {

	private static DecimalFormat DF_FOR_VIEW = new DecimalFormat("#,##0.00");
	@Value("${am.user.service.name}")
	private String userService;

    @Autowired
    private SynBalanceService synBalanceService;

	// 服务费授权描述
	private static final String paymentAuthDesc = "应合规要求，出借、提现等交易前需进行以下授权：\n服务费授权。";
	//三合一授权描述
	private static final String mergeAuthDesc = "应合规要求，出借、提现等交易前需进行以下授权：\n自动投标，自动债转，服务费授权。";
	private static final String checkUserRoleDesc = "仅限出借人进行出借";
	@Autowired
	private AmUserClient amUserClient;

	@Autowired
	private AmTradeClient amTradeClient;

	@Autowired
	private AmConfigClient amConfigClient;

	@Autowired
	private AmMarketClient amMarketClient;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private AuthService authService;

	@Autowired
	private CommonProducer commonProducer;
	/**
	 * 登录
	 *
	 * @param loginUserName
	 *            手机号
	 * @param loginPassword
	 * @param ip
	 */
	@Override
	public WebViewUserVO login(String loginUserName, String loginPassword, String ip, String channel,UserVO userVO) {
	    //logger.info("登陆参数，用户名："+loginUserName+"；密码："+loginPassword);
		if (checkMaxLength(loginUserName, 16) || checkMaxLength(loginPassword, 32)) {
			CheckUtil.check(false, MsgEnum.ERR_USER_LOGIN);
		}
		return this.doLogin(loginUserName, loginPassword, ip, channel,userVO);
	}

	/**
	 * 通过密码验证后的纯登录操作
	 * @auth sunpeikai
	 * @param
	 * @return
	 */
	@Override
	public WebViewUserVO loginOperationOnly(UserVO userVO,String loginUserName,String ip,String channel) {
		int userId = userVO.getUserId();
		// 是否禁用
		if (userVO.getStatus() == 1) {
			throw new CheckException(MsgEnum.ERR_USER_INVALID);
		}
		// 更新登录信息
		amUserClient.updateUser(userVO, ip);
		// 1. 登录成功将登陆密码错误次数的key删除
		RedisUtils.del(RedisConstants.PASSWORD_ERR_COUNT_ALL + userId);
		WebViewUserVO webViewUserVO = this.getWebViewUserByUserId(userVO.getUserId(),channel);
		// 2. 缓存
		webViewUserVO = setToken(webViewUserVO);
		String accountId = webViewUserVO.getBankAccount();
		if (accountId != null && StringUtils.isNoneBlank(accountId)) {
			//synBalanceService.synBalance(accountId, ip);
			Map<String, String> params = new HashMap<String, String>();
			params.put("accountId", accountId);
			params.put("ip", ip);
			try {
				commonProducer.messageSend(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.SYNBALANCE_TAG, UUID.randomUUID().toString(),params));
			} catch (MQException e) {
				logger.error("同步线下充值异常:"+e.getMessage());
			}

		}
		if (channel.equals(BankCallConstant.CHANNEL_WEI)) {
			String sign = SecretUtil.createToken(userId, loginUserName, accountId);
			webViewUserVO.setToken(sign);
		}
		return webViewUserVO;
	}

	/**
	 * 登录处理
	 *
	 * @param loginUserName
	 * @param loginPassword
	 * @return
	 */
	private WebViewUserVO doLogin(String loginUserName, String loginPassword, String ip, String channel,UserVO userVO) {
		logger.info("登陆获取loginUserName:"+loginUserName+";userVO:"+(userVO==null));
		WebViewUserVO webViewUserVO = new WebViewUserVO();
		CheckUtil.check(userVO != null, MsgEnum.ERR_USER_NOT_EXISTS);
		String codeSalt = userVO.getSalt();
		logger.info("salt:"+codeSalt);
		String passwordDb = userVO.getPassword();
		// 页面传来的密码
		String password = "";
		if (channel.equals(BankCallConstant.CHANNEL_PC)) {
			password = MD5Utils.MD5(loginPassword + codeSalt);
		}else {
			password = MD5Utils.MD5(MD5Utils.MD5(loginPassword) + codeSalt);
		}
		logger.info("passwordDB:[{}],password:[{}],相等:[{}]",passwordDb,password,password.equals(passwordDb));
		if (password.equals(passwordDb)) {
			webViewUserVO = loginOperationOnly(userVO,loginUserName,ip,channel);
		} else {
			CheckUtil.check(false, MsgEnum.ERR_USER_LOGIN);
		}
		return webViewUserVO;
	}

	/**
	 * 校验app参数
	 *
	 * @param
	 * @return
	 */
	@Override
	public void checkForApp(String version, String platform, String netStatus) {
		CheckUtil.check(null != version && null != platform && null != netStatus, MsgEnum.STATUS_CE000001);
		CheckUtil.check(StringUtils.isNotEmpty(version), MsgEnum.STATUS_CE000014);
		CheckUtil.check(StringUtils.isNotEmpty(platform) && StringUtils.isNotEmpty(netStatus), MsgEnum.STATUS_CE000001);
		if (version.length() >= 5) {
			version = version.substring(0, 5);
		}
		CheckUtil.check(version.compareTo("1.4.0") > 0, MsgEnum.STATUS_CE000014);

	}

	/**
	 * 退出清空MobileCod
	 *
	 * @param userId
	 * @param sign
	 * @author pcc
	 */
	@Override
	public void clearMobileCode(Integer userId, String sign) {
		amUserClient.clearMobileCode(userId, sign);
	}

	/**
	 * 字符串长度检查
	 *
	 * @param value
	 * @param max
	 * @return
	 */
	private boolean checkMaxLength(String value, int max) {
		if (StringUtils.isEmpty(value)) {
			return true;
		}
		if (value.length() > max) {
			return true;
		}
		return false;
	}

	/**
	 * 获取各种用户属性
	 *
	 * @param userId
	 * @param platform
	 * @param request
	 * @return
	 */
	@Override
	public UserParameters getUserParameters(Integer userId, String platform, HttpServletRequest request) {
		UserParameters result = new UserParameters();
		String imghost = UploadFileUtils.getDoPath(systemConfig.getAppFrontHost());
		imghost = imghost.substring(0, imghost.length() - 1);
		String apphost = UploadFileUtils.getDoPath(systemConfig.getAppFrontHost())
				+ BaseDefine.REQUEST_HOME.substring(1, BaseDefine.REQUEST_HOME.length()) + "/";
		apphost = apphost.substring(0, apphost.length() - 1);
		String iconUrl = "";
		{
			UserVO user = this.getUsersById(userId);
			// 当borrowSms、investSms、reChargeSms与receiveSms中有一个为开启状态，则开启短信发送状态为1; 0:未开启，1：开启
			int smsOpenStatus = (user.getWithdrawSms() == null ? 0 : user.getWithdrawSms())
					+ (user.getInvestSms() == null ? 0 : user.getInvestSms())
					+ (user.getRechargeSms() == null ? 0 : user.getRechargeSms())
					+ (user.getRecieveSms() == null ? 0 : user.getRecieveSms());
			result.setUserId(String.valueOf(userId));
			if (smsOpenStatus == 4) {
				result.setSmsOpenStatus("0");
			} else {
				result.setSmsOpenStatus("1");
			}
			Integer isSmtp = user.getIsSmtp();
			if (isSmtp != null && isSmtp.intValue() == 0) {
				result.setEmailOpenStatus("1");
			} else {
				result.setEmailOpenStatus("0");
			}
			// 开户了
			if (user.getBankOpenAccount() != null && user.getBankOpenAccount() == 1) {
				BigDecimal principal = new BigDecimal("0.00");
				// 获取汇天利用户本金
				result.setOpenAccount(CustomConstants.FLAG_OPENACCOUNT_YES);
				ProductSearchForPageVO productSearchForPage = new ProductSearchForPageVO();
				productSearchForPage.setUserId(userId);
				productSearchForPage = selectUserPrincipal(productSearchForPage);
				// 获取用户电话号码
				if (user.getMobile() != null) {
					result.setBindMobile(user.getMobile().substring(0, 3) + "****"
							+ user.getMobile().substring(user.getMobile().length() - 4));
				} else {
					result.setBindMobile("未绑定");
				}

				List<AdminBankAccountCheckCustomizeVO> accountList = amUserClient.queryAllBankOpenAccount(userId);
				String account = "";
				if (accountList != null && accountList.size() > 0) {
					for (int i = 0; i < accountList.size(); i++) {
						account = accountList.get(i).getAccountId();
					}
				}
				result.setJiangxiAccount(account);
				if (productSearchForPage != null) {
					principal = productSearchForPage.getPrincipal();
					if (principal == null) {
						principal = new BigDecimal("0.00");
					} else {
						principal = principal.divide(BigDecimal.ONE, 2, BigDecimal.ROUND_DOWN);
					}
				} else {
					principal = new BigDecimal("0.00");
				}
				if (request.getParameter("version").startsWith("1.1.0")) {
					// 汇天利后边的描述文字
					result.setHtlDescription(principal + "元");
				} else {
					// 汇天利后边的描述文字
					result.setHtlDescription(DF_FOR_VIEW.format(principal) + "元");
				}
				// 银行已开户，江西银行账户描述
				// 江西银行账户账号不需要脱敏
				result.setJiangxiDesc(account);

				// 用户已开户，显示某先生/女士
				UserInfoVO usersInfo = this.getUserInfo(userId);
				if (usersInfo.getSex() != null && usersInfo.getTruename() != null && usersInfo.getSex() == 1) {
					result.setNickname(usersInfo.getTruename().substring(0, 1) + "先生");
				} else if (usersInfo.getSex() != null && usersInfo.getTruename() != null && usersInfo.getSex() == 2) {
					result.setNickname(usersInfo.getTruename().substring(0, 1) + "女士");
				} else {
					result.setNickname(user.getUsername());
				}
				// 用户角色判断 合规接口改造新增
				result.setRoleId(usersInfo.getRoleId() + "");
			} else {
				// 银行未开户
				result.setJiangxiDesc("未开户");
				result.setToubiaoDesc("请先开户");
				// 银行未开户，汇付未开户
				result.setOpenAccount(CustomConstants.FLAG_OPENACCOUNT_NO);
				// 银行未开户但有汇付天下账户认证，显示真实姓名和身份证号
				if (user.getOpenAccount() != null && user.getOpenAccount() == 1) {
					UserInfoVO usersInfo = this.getUserInfo(userId);
					String trueName = null;
					String idcard = null;
					result.setNickname(user.getUsername());
					if (usersInfo.getTruename() != null && usersInfo.getIdcard() != null) {
						idcard = usersInfo.getIdcard().substring(0, 3) + "***********"
								+ usersInfo.getIdcard().substring(usersInfo.getIdcard().length() - 4);
						trueName = usersInfo.getTruename().replaceFirst(usersInfo.getTruename().substring(0, 1), "*");
						result.setInfoDesc(trueName + "|" + idcard);
					}
				}
			}

			// 汇付开户标识 大版本前端发版的时候是否可以删除
			if (user.getOpenAccount() != null && user.getOpenAccount() == 1) {
				// 汇付开户
				result.setHuifuOpenAccount(CustomConstants.FLAG_OPENACCOUNT_YES);
			} else {// 未开户
				result.setHuifuOpenAccount(CustomConstants.FLAG_OPENACCOUNT_NO);
			}
			// 未绑卡
			int bingCardStatus = ClientConstants.BANK_BINDCARD_STATUS_FAIL;
			List<BankCardVO> bankCardList = amUserClient.getTiedCardForBank(user.getUserId());
			if (bankCardList != null && bankCardList.size() > 0) {
				bingCardStatus = ClientConstants.BANK_BINDCARD_STATUS_SUCCESS;
			}
			result.setJiangxiBindBankStatus(bingCardStatus + "");

			result.setUsername(user.getUsername());
			result.setMobile(user.getMobile());
			result.setReffer(user.getUserId() + "");
			result.setSetupPassword(String.valueOf(user.getIsSetPassword()));
			result.setUserType(String.valueOf(user.getUserType()));
			if ("0".equals(result.getSetupPassword())) {
				//设置交易密码
				result.setChangeTradePasswordUrl(systemConfig.getAppFrontHost()  +"/public/formsubmit?requestType=" + CommonConstant.APP_BANK_REQUEST_TYPE_SET_PASSWORD + packageStrForm(request));
			} else {
				//重置交易密码
				result.setChangeTradePasswordUrl(systemConfig.getAppFrontHost() +"/public/formsubmit?requestType=" + CommonConstant.APP_BANK_REQUEST_TYPE_RESET_PASSWORD + packageStrForm(request));
			}

			iconUrl = user.getIconUrl();

			if (user.getIfReceiveNotice() != null && user.getIfReceiveNotice() == 1) {
				result.setStartOrStopPush(CustomConstants.FLAG_PUSH_YES);
			} else {
				result.setStartOrStopPush(CustomConstants.FLAG_PUSH_NO);
			}
		}
		{
			UserInfoVO userInfo = this.getUserInfo(userId);
			String trueName = null;
			String idcard = null;
			if (userInfo != null) {
				if (userInfo.getTruename() != null && userInfo.getTruename().length() >= 2) {
					trueName = userInfo.getTruename();
					if (userInfo.getIdcard() != null && userInfo.getIdcard().length() > 15) {
						idcard = userInfo.getIdcard().substring(0, 3) + "***********"
								+ userInfo.getIdcard().substring(userInfo.getIdcard().length() - 4);
						result.setIdcard(idcard);
						// 获取实名信息
						result.setInfoDesc(trueName.replaceFirst(trueName.substring(0, 1), "*") + "|" + idcard);
					}
				}

				// 上传文件的CDNURL
				if (StringUtils.isNotEmpty(iconUrl)) {
					// 实际物理路径前缀2
					String fileUploadTempPath = UploadFileUtils.getDoPath(systemConfig.getFileUpload(ClientConstants.APP_CLIENT));
					result.setIconUrl(imghost + fileUploadTempPath + iconUrl);
				} else {
					result.setIconUrl(apphost + "/img/" + "icon.png");
				}
				if (userInfo.getVipId() != null && userInfo.getVipId() > 0) {
					result.setIsVip("1");
					VipInfoVO vipInfo = amUserClient.findVipInfoById(userInfo.getVipId());
					// vip名称显示图片
					result.setVipPictureUrl(apphost + "/img/" + VipImageUrlEnum.getName(vipInfo.getVipLevel()));
					// vip等级显示图片
					result.setVipLevel(vipInfo.getVipName());
					// 初始化跳转路径
					result.setVipJumpUrl(apphost + ClientConstants.USER_VIP_DETAIL_ACTIVE_INIT + packageStr(request));
				} else {
					result.setIsVip("0");
					result.setVipLevel("尊享特权");
					// vip名称显示图片
					result.setVipPictureUrl(apphost + "/img/" + VipImageUrlEnum.getName(0));
					// vip等级显示图片
					result.setVipJumpUrl(apphost + ClientConstants.APPLY_INIT + packageStr(request));
				}
				// 用户角色：1出借人2借款人3担保机构
				Integer roleId = userInfo.getRoleId();
				result.setRoleId(roleId == null ? "" : String.valueOf(roleId));
			} else {
				throw new RuntimeException("获取用户属性异常:不存在详细信息");
			}
		}
		{
			AccountVO account = amTradeClient.getAccount(userId);
			BigDecimal planInterestWait = BigDecimal.ZERO;
			BigDecimal planCapitalWait = BigDecimal.ZERO;
			if (account != null) {
				if (account.getBalance() == null) {
					result.setBalance("0.00");
				} else {

					if (request.getParameter("version").startsWith("1.1.0")) {
						result.setBalance(account.getBankBalance() + "");

					} else {
						result.setBalance(account.getBankBalance() + "");
						if (account.getBankBalance() != null) {
							result.setBalance(DF_FOR_VIEW.format(account.getBankBalance()));
						}
						if (account.getPlanAccountWait() != null) {
							result.setPlanDesc(DF_FOR_VIEW.format(account.getPlanAccountWait()));
						}
						if (account.getBankAwait() != null) {
							result.setBorrowDesc(DF_FOR_VIEW.format(account.getBankAwait()));
						}
						if (account.getBankAwait() != null || account.getPlanAccountWait() != null) {
							result.setAwaitTotal(
									DF_FOR_VIEW.format(account.getBankAwait().add(account.getPlanAccountWait())));
						}
					}
				}
				if (account.getPlanCapitalWait() != null) {
					planCapitalWait = account.getPlanCapitalWait();
				}
				if (account.getPlanInterestWait() != null) {
					planInterestWait = account.getPlanInterestWait();
				}

			} else {
				result.setBalance("0.00");
			}
			// add by pcc
			// 前端汇付账户不显示  大版本更新app发版时候是否删除参数
			result.setHuifuBalance("");
			WebPandectRecoverMoneyCustomizeVO pr = amTradeClient.queryRecoverMoney(userId);
			WebPandectRecoverMoneyCustomizeVO prRtb = amTradeClient.queryRecoverMoneyForRtb(userId);
			BigDecimal RecoverInterest = BigDecimal.ZERO;
			if (pr != null) {
				if (prRtb != null && prRtb.getRecoverInterest() != null) {
					// 累计利息
					RecoverInterest = pr.getRecoverInterest() == null ? new BigDecimal(0)
							: pr.getRecoverInterest().add(prRtb.getRecoverInterest());
				} else {
					// 累计利息
					RecoverInterest = pr.getRecoverInterest() == null ? new BigDecimal(0) : pr.getRecoverInterest();
				}
			}
			WebPandectWaitMoneyCustomizeVO pw = amTradeClient.queryWaitMoney(userId);
			BigDecimal WaitInterest = BigDecimal.ZERO;
			BigDecimal waitCapital = BigDecimal.ZERO;
			if (pw != null) {
				WebPandectWaitMoneyCustomizeVO pwRtb = amTradeClient.queryWaitMoneyForRtb(userId);
				if (pwRtb != null && pwRtb.getRecoverInterest() != null) {
					// 待收利息
					WaitInterest = pw.getRecoverInterest() == null ? new BigDecimal(0)
							: pw.getRecoverInterest().add(pwRtb.getRecoverInterest());
				} else {
					// 待收利息
					WaitInterest = pw.getRecoverInterest() == null ? new BigDecimal(0) : pw.getRecoverInterest();
				}
				// 待收本金
				waitCapital = pw.getWaitCapital() == null ? new BigDecimal(0) : pw.getWaitCapital();
			}
			BigDecimal htlRestAmount = amTradeClient.queryHtlSumRestAmount(userId);

			// 待收利息 (待收收益)
			// 优惠券总收益 add by hesy 优惠券相关 start
			BigDecimal couponInterestTotalWaitDec = BigDecimal.ZERO;
			String couponInterestTotalWait = amTradeClient.selectCouponInterestTotal(userId);
			if (StringUtils.isNotEmpty(couponInterestTotalWait)) {
				couponInterestTotalWaitDec = new BigDecimal(couponInterestTotalWait);
			}
			// 债转统计
			WebPandectCreditTenderCustomizeVO creditTender = amTradeClient.queryCreditInfo(userId);
			// 去掉待收已债转
			WebPandectBorrowRecoverCustomizeVO recoverWaitInfo = amTradeClient.queryRecoverInfo(userId, 0);
			// 去掉已债转
			WebPandectBorrowRecoverCustomizeVO recoverYesInfo = amTradeClient.queryRecoverInfo(userId, 1);
			BigDecimal CreditInterestWait = BigDecimal.ZERO;
			BigDecimal CreditCapitalWait = BigDecimal.ZERO;
			if (creditTender != null) {
				CreditInterestWait = creditTender.getCreditInterestWait();
				CreditCapitalWait = creditTender.getCreditCapitalWait();
			}
			BigDecimal CreditInterestAmount = BigDecimal.ZERO;
			BigDecimal CreditAmount = BigDecimal.ZERO;
			if (recoverWaitInfo != null) {
				CreditInterestAmount = recoverWaitInfo.getCreditInterestAmount();
				CreditAmount = recoverWaitInfo.getCreditAmount();
			}
			if (htlRestAmount == null) {
				htlRestAmount = BigDecimal.ZERO;
			}
			// 待收本金
			waitCapital = waitCapital.add(htlRestAmount).add(CreditCapitalWait).subtract(CreditAmount)
					.add(planCapitalWait);
			BigDecimal waitInterest = WaitInterest.add(couponInterestTotalWaitDec).add(CreditInterestWait)
					.subtract(CreditInterestAmount).add(planInterestWait);

			// 汇天利总收益
			BigDecimal interestall = amTradeClient.queryHtlSumInterest(userId);
			if (interestall == null) {
				interestall = new BigDecimal(0);
			}
			// 优惠券总收益 add by hesy 优惠券相关 start
			BigDecimal couponInterestTotalDec = BigDecimal.ZERO;
			String couponInterestTotal = amTradeClient.selectCouponReceivedInterestTotal(userId);
			if (StringUtils.isNotEmpty(couponInterestTotal)) {
				couponInterestTotalDec = new BigDecimal(couponInterestTotal);
			}
			BigDecimal CreditInterestYes = BigDecimal.ZERO;
			if (creditTender != null) {
				CreditInterestYes = creditTender.getCreditInterestYes();
			}
			BigDecimal CreditInterestAmountYes = BigDecimal.ZERO;
			if (recoverYesInfo != null) {
				CreditInterestAmountYes = recoverYesInfo.getCreditInterestAmount();
			}
			// 已回收的利息 (累计收益)
			BigDecimal recoverInterest = RecoverInterest.add(interestall)
					// +汇天利
					.add(couponInterestTotalDec).add(CreditInterestYes)
					// +债转
					.subtract(CreditInterestAmountYes);
			// -已债转
			if (null != account) {
				if (request.getParameter("version").startsWith("1.1.0")) {
					result.setWaitInterest(account.getBankAwaitInterest().add(account.getPlanInterestWait()) + "");
					result.setInterestTotle(account.getBankInterestSum() + "");
				} else {
					result.setWaitInterest(
							DF_FOR_VIEW.format(account.getBankAwaitInterest().add(account.getPlanInterestWait())));
					result.setInterestTotle(DF_FOR_VIEW.format(account.getBankInterestSum()));
				}
			} else {
				result.setWaitInterest(DF_FOR_VIEW.format(0));
				result.setInterestTotle(DF_FOR_VIEW.format(0));
			}

			BigDecimal bankTotal = (null==account||account.getBankTotal() == null) ? BigDecimal.ZERO : account.getBankTotal();
			result.setAccountTotle(DF_FOR_VIEW.format(bankTotal));

		}
		{
			UserVO userInfoVO = this.getUsersById(userId);

			// 初始 用户类型
			Integer userType = 0;

			if (userInfoVO != null){
				userType = userInfoVO.getUserType();
			}

			// 江西银行 开户 URL
			String jxBankOpenUrl = "";
			// 非企业用户开户地址
			if (userType != 1) {
				// 开户url
				result.setHuifuOpenAccountUrl("");
				// 江西银行开户url
				jxBankOpenUrl = systemConfig.getAppFrontHost() + ClientConstants.BANKOPEN_OPEN_ACTION + packageStr(request);
				result.setOpenAccountUrl(jxBankOpenUrl);
			}
			// 企业用户开户地址
			else {
				// 开户url
				result.setHuifuOpenAccountUrl("");
				// 江西银行开户url add by huanghui
				// 企业开户指南url 由前端提供.
				jxBankOpenUrl = systemConfig.getAppFrontHost() +  "/open/enterpriseguide";
				result.setOpenAccountUrl(jxBankOpenUrl);
				logger.info("jxBankOpenUrl:" + jxBankOpenUrl);
			}
		}
		{
			UsersContactVO userContract = amUserClient.selectUserContact(userId);
			if (userContract != null) {
				// 联系人关系映射
				Map<String, String> relation = CacheUtil.getParamNameMap("USER_RELATION");
				Set<String> relationKey = relation.keySet();
				for (String key : relationKey) {
					if (relation.get(key).equals(userContract.getRelation() + "")) {
						result.setRelation(relation.get(key));
					}
				}
				result.setRl_name(userContract.getRlName());
				result.setRl_phone(userContract.getRlPhone());
			}
		}
		{
			List<AccountBankVO> list = amUserClient.selectAccountBank(userId, 0);
			result.setIsBindQuickPayment(CustomConstants.FLAG_BINDQUICKPAYMENT_NO);
			result.setBankCardAccount("");
			result.setBankCardAccountLogoUrl("");
			result.setBankCardCode("");
			if (list != null && list.size() > 0) {
				result.setBankCardCount(list.size() + "");
				for (AccountBankVO accountBank : list) {
					Boolean hasQuick = false;
					if ("2".equals(accountBank.getCardType())) {
						hasQuick = true;
						result.setIsBindQuickPayment(CustomConstants.FLAG_BINDQUICKPAYMENT_YES);
					}
					BankConfigVO bankConfig = amConfigClient.getBankConfigByCode(accountBank.getBank());
					if (bankConfig != null) {
						result.setBankCardAccount(bankConfig.getName());
						result.setBankCardAccountLogoUrl(imghost + bankConfig.getAppLogo());
					}
					result.setBankCardCode(accountBank.getAccount());
					if (hasQuick) {
						result.setBankCardCount("1");
						break;
					}
				}
			} else {
				result.setBankCardCount("0");
			}
		}
		{
		    // 汇付数据  大版本更新时app强更是否删除
			AccountChinapnrVO accountChinapnrVO = amUserClient.getAccountChinapnr(userId);
			// 江西银行绑卡接口修改
			Integer urlType = amConfigClient.getBankInterfaceFlagByType("BIND_CARD");
			// 江西银行绑卡接口修改
			if (accountChinapnrVO != null) {
				// 汇付数据  大版本更新时app强更是否删除
				result.setChinapnrUsrcustid(accountChinapnrVO.getChinapnrUsrid() + "");
				// 汇付天下账户描述
				result.setHuifuDesc(
						accountChinapnrVO.getChinapnrUsrid().substring(0, 3) + "**************" + accountChinapnrVO
								.getChinapnrUsrid().substring(accountChinapnrVO.getChinapnrUsrid().length() - 3));
				// 绑卡url
				result.setHuifuBindBankCardUrl(apphost + ClientConstants.USER_BIND_CARD + packageStr(request));
				// 江西银行绑卡url
				// 江西银行绑卡接口修改
				/*if (urlType == 1) {
					// 绑卡接口类型为新接口
					result.setBindBankCardUrl(apphost + ClientConstants.REQUEST_BINDCARDPAGE + packageStr(request));
				} else {
					// 绑卡接口类型为旧接口
					result.setBindBankCardUrl(
							systemConfig.getAppFrontHost() + ClientConstants.BINDCARD_ACTION + packageStr(request));
				}*/
				// 目前不需要切换了 add by hesy 2019-03-04
				result.setBindBankCardUrl(
						systemConfig.getAppFrontHost() + ClientConstants.BINDCARD_ACTION + packageStr(request));
				// 江西银行绑卡接口修改
			} else {
				// 江西银行绑卡url
				// 江西银行绑卡接口修改
				/*if (urlType == 1) {
					// 绑卡接口类型为新接口
					result.setBindBankCardUrl(apphost + ClientConstants.REQUEST_BINDCARDPAGE + packageStr(request));
				} else {
					// 绑卡接口类型为旧接口
					result.setBindBankCardUrl(
							systemConfig.getAppFrontHost() + ClientConstants.BINDCARD_ACTION + packageStr(request));
				}*/

				// 目前不需要切换了 add by hesy 2019-03-04
				result.setBindBankCardUrl(
						systemConfig.getAppFrontHost() + ClientConstants.BINDCARD_ACTION + packageStr(request));
			}
		}
		{
			//通过用户ID 获取用户关联渠道 add by huanghui
			// 合规自查添加
			// 20181205 产品需求, 屏蔽渠道,只保留用户ID
			String linkUrl = null;
			UserUtmInfoCustomizeVO userUtmInfoCustomizeVO = amUserClient.getUserUtmInfo(userId);
//			if (userUtmInfoCustomizeVO != null){
//				linkUrl = systemConfig.getWechatQrcodeUrl() + "refferUserId=" + userId + "&utmId=" + userUtmInfoCustomizeVO.getSourceId().toString() + "&utmSource=" + userUtmInfoCustomizeVO.getSourceName();
//			}else {
				linkUrl = systemConfig.getWechatQrcodeUrl() + "refferUserId=" + userId;
//			}
			// 二维码
			result.setQrCodeUrl(linkUrl);
		}
		{
			// 风险测评结果
			UserEvalationResultVO userEvalationResult = amUserClient.selectUserEvalationResultByUserId(userId);
			if (userEvalationResult != null) {
				//从user表获取用户测评到期日
				UserVO user = amUserClient.findUserById(userId);
				// 获取评测时间加一年的毫秒数18.2.2评测 19.2.2
				Long lCreate = user.getEvaluationExpiredTime().getTime();
				// 获取当前时间加一天的毫秒数 19.2.1以后需要再评测19.2.2
				Long lNow = System.currentTimeMillis();
				if (lCreate <= lNow) {
					// 已过期需要重新评测
					result.setAnswerStatus("2");
					result.setAnswerResult("点击测评");
					result.setFengxianDesc("已过期");
				} else {
					// 进行过风险测评
					result.setAnswerStatus("1");
					result.setAnswerResult(userEvalationResult.getEvalType());
					// 风险描述
					result.setFengxianDesc(userEvalationResult.getEvalType());
					result.setEvalationSummary(userEvalationResult.getSummary());
					result.setEvalationScore(userEvalationResult.getScoreCount() + "");
				}
			} else {
				result.setAnswerStatus("0");
				result.setAnswerResult("点击测评");
				result.setFengxianDesc("未测评");
				// 活动有效期校验
				String resultActivity = checkActivityIfAvailable(systemConfig.getActivityId());
				// 终端平台校验
				String resultPlatform = checkActivityPlatform(systemConfig.getActivityId(),
						request.getParameter("platform"));
				if ((resultActivity == null || "".equals(resultActivity))
						&& (resultPlatform == null || "".equals(resultPlatform))) {
					result.setAnswerResult("评测送券");
				}
			}
			result.setAnswerUrl(
					CommonUtils.concatReturnUrl(request, systemConfig.getAppServerHost() + ClientConstants.USER_RISKTEST));
		}

		{
			Integer couponValidCount = amTradeClient.countCouponValid(userId);
			if (couponValidCount != null && couponValidCount > 0) {
				result.setCouponDescription(String.valueOf(couponValidCount));
				List<CouponUserCustomizeVO> coupons = amTradeClient.selectLatestCouponValidUNReadList(userId);
				if (coupons != null && !coupons.isEmpty()) {
					result.setReadFlag("1");
				} else {
					result.setReadFlag("0");
				}
			} else {
				result.setCouponDescription("暂无可用");
				result.setReadFlag("0");
			}
		}
		// 我的账户更新标识(0未更新，1已更新)
		if ("0".equals(result.getReadFlag())) {
			result.setIsUpdate("0");
		} else {
			result.setIsUpdate("1");
		}
		// 邮箱红点显示
		result.setRedFlag("1");
		boolean isNewHand = checkNewUser(userId);
		result.setIsNewHand(isNewHand ? "1" : "0");
		result.setRewardDesc("邀请好友");
		result.setRewardUrl(
				CommonUtils.concatReturnUrl(request, systemConfig.getAppServerHost() + ClientConstants.REWARD_URL));
		{
			//自动投标授权状态 0: 未授权    1:已授权
			if (authService.checkIsAuth(userId,AuthBean.AUTH_TYPE_AUTO_BID)) {
				result.setAutoInvesStatus("1");//1:已授权
				result.setNewAutoInvesStatus("1"); //1:已授权
				result.setToubiaoDesc("已授权");
			} else {
				result.setAutoInvesStatus("0");//0:未授权
				result.setNewAutoInvesStatus("0");//0:未授权
				result.setToubiaoDesc("未授权");
			}
			//自动债转授权状态 0：未授权    1：已授权
			if (authService.checkIsAuth(userId,AuthBean.AUTH_TYPE_AUTO_CREDIT)) {
				result.setNewAutoCreditStatus("1");//1:已授权
			} else {
				result.setNewAutoCreditStatus("0");//0:未授权
			}

			// 缴费授权 0：未授权    1：已授权
			if (authService.checkIsAuth(userId,AuthBean.AUTH_TYPE_PAYMENT_AUTH)) {
				result.setPaymentAuthStatus("1");//1:已授权
			} else {
				result.setPaymentAuthStatus("0");//0:未授权
			}
			if (authService.checkIsAuth(userId,AuthBean.AUTH_TYPE_REPAY_AUTH)) {
				result.setRepayStatus("1");//1:已授权
			} else {
				result.setRepayStatus("0");//0:未授权
			}
		}
		{

			// 服务费授权
			HjhUserAuthConfigVO paymenthCconfig = authService.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH);
			HjhUserAuthConfigVO invesCconfig = authService.getAuthConfigFromCache(RedisConstants.KEY_AUTO_TENDER_AUTH);;
			HjhUserAuthConfigVO creditCconfig = authService.getAuthConfigFromCache(RedisConstants.KEY_AUTO_CREDIT_AUTH);;
			// 设置服务费授权开关
			result.setPaymentAuthOn(paymenthCconfig.getEnabledStatus() + "");
			// 设置自动出借授权开关
			result.setInvesAuthOn(invesCconfig.getEnabledStatus() + "");
			// 设置自动债转授权开关
			result.setCreditAuthOn(creditCconfig.getEnabledStatus() + "");
			// 是否校验用户角色
			result.setIsCheckUserRole(systemConfig.getRoleIsopen());
			// 校验用户角色的描述
			result.setCheckUserRoleDesc(checkUserRoleDesc);


			//三合一授权过期标识0未授权，1正常，2即将过期，3已过期
			result.setMergeAuthExpire(authService.checkAuthExpire(userId, AuthBean.AUTH_TYPE_MERGE_AUTH)+"");
			//三合一授权过期标识0未授权，1正常，2即将过期，3已过期
			result.setPaymentAuthExpire(authService.checkAuthExpire(userId, AuthBean.AUTH_TYPE_PAYMENT_AUTH)+"");
			//二合一授权过期标识（服务费授权、还款授权） 0未授权，1正常，2即将过期，3已过期
			result.setPayRepayAuthExpire(authService.checkAuthExpire(userId, AuthBean.AUTH_TYPE_PAY_REPAY_AUTH)+"");
			String imminentExpiryDesc="您授权的服务期限过短，请重新授权。\n请勿随意修改您的授权额度和有效期。";
			String expireDesc="您授权的服务期限过期，请重新授权。\n请勿随意修改您的授权额度和有效期";
			/*String mergeImminentExpiryDesc="您授权的服务期限过短，请重新授权。\n请勿随意修改您的授权额度和有效期。";
			String mergeExpireDesc="您授权的服务期限过期，请重新授权。\n请勿随意修改您的授权额度和有效期";
			String paymentImminentExpiryDesc="您授权的服务期限过短，请重新授权。\n请勿随意修改您的授权额度和有效期。";
			String paymentExpireDesc="您授权的服务期限过期，请重新授权。\n请勿随意修改您的授权额度和有效期";
			String payRepayImminentExpiryDesc="您授权的服务期限过短，请重新授权。\n请勿随意修改您的授权额度和有效期。";
			String payRepayExpireDesc="您授权的服务期限过期，请重新授权。\n请勿随意修改您的授权额度和有效期";*/
			// 三合一授权描述
			result.setMergeAuthDesc(mergeAuthDesc);
			/*if("3".equals(result.getMergeAuthExpire())){
				result.setMergeAuthDesc(expireDesc);
			}else if("2".equals(result.getMergeAuthExpire())){
				result.setMergeAuthDesc(imminentExpiryDesc);
			}*/
			// 服务费授权描述
			result.setPaymentAuthDesc(paymentAuthDesc);
			/*if("3".equals(result.getPaymentAuthExpire())){
				result.setPaymentAuthDesc(expireDesc);
			}else if("2".equals(result.getPaymentAuthExpire())){
				result.setPaymentAuthDesc(imminentExpiryDesc);
			}*/
			// 二合一授权描述
			/*if("3".equals(result.getPayRepayAuthExpire())){
				result.setMergeAuthDesc(expireDesc);
			}else if("2".equals(result.getPayRepayAuthExpire())){
				result.setMergeAuthDesc(imminentExpiryDesc);
			}*/
			String sign=request.getParameter("sign");

			/*
			注释 By dangzw -->start
			// 自动投标授权URL
			result.setAutoInvesUrl(systemConfig.getAppFrontHost()+"/public/formsubmit?sign="+sign+"&requestType="+CommonConstant.APP_BANK_REQUEST_TYPE_AUTHMERGE);// 0:未授权
			// 自动投标授权URL
			result.setMergeAuthUrl(systemConfig.getAppFrontHost()+"/public/formsubmit?sign="+sign+"&requestType="+CommonConstant.APP_BANK_REQUEST_TYPE_AUTHMERGE);// 0:未授权
			// 服务费授权Url
			result.setPaymentAuthUrl(systemConfig.getAppFrontHost()+"/public/formsubmit?sign="+sign+"&requestType="+CommonConstant.APP_BANK_REQUEST_TYPE_AUTHPAYMENT);
			// 还款授权URL
			result.setRepayAuthUrl(systemConfig.getAppFrontHost()+"/public/formsubmit?sign="+sign+"&requestType="+CommonConstant.APP_BANK_REQUEST_TYPE_AUTHREPAY);
			注释 By dangzw -->end
			*/
		}
		// add by pcc app3.1.1追加 20180823 start
		// 我的计划列表退出中标签显示标识（临时使用，功能上线以后可以删除）
		result.setExitLabelShowFlag(systemConfig.getExitLabelShowFlag());
		// add by pcc app3.1.1追加 20180823 end
		result.setInvitationCode(userId);
		return result;
	}

	private ProductSearchForPageVO selectUserPrincipal(ProductSearchForPageVO productSearchForPage) {
		ProductSearchForPageVO result = amTradeClient.selectUserPrincipal(productSearchForPage);
		return result;
	}

	@Override
	public void updateUserIconImg(Integer userId, String iconUrl) {
		UserVO userVO = new UserVO();
		userVO.setUserId(userId);
		userVO.setIconUrl(iconUrl);
		amUserClient.updateUserById(userVO);

	}

	/**
	 * 检查是否是新手(未登录或已登录未出借)
	 *
	 * @param userId
	 * @return
	 */
	public boolean checkNewUser(Integer userId) {
		// 未登录则认为是新用户
		if (userId == null || userId <= 0) {
			return true;
		}
		int tenderCount = amTradeClient.selectUserTenderCount(userId);
		return tenderCount <= 0;
	}

	/**
	 * 活动是否过期
	 */
	public String checkActivityIfAvailable(String activityId) {
		if (activityId == null) {
			return ClientConstants.ACTIVITYID_IS_NULL;
		}
		ActivityListVO activityList = amMarketClient.selectActivityList(new Integer(activityId));
		if (activityList == null) {
			return ClientConstants.ACTIVITY_ISNULL;
		}
		if (activityList.getTimeStart() > GetDate.getNowTime10()) {
			return ClientConstants.ACTIVITY_TIME_NOT_START;
		}
		if (activityList.getTimeEnd() < GetDate.getNowTime10()) {
			return ClientConstants.ACTIVITY_TIME_END;
		}
		return "";
	}

	public String checkActivityPlatform(String activityId, String platform) {
		if (activityId == null) {
			return ClientConstants.ACTIVITYID_IS_NULL;
		}
		ActivityListVO activityList = amMarketClient.selectActivityList(new Integer(activityId));
		if (activityList == null) {
			return ClientConstants.ACTIVITY_ISNULL;
		}
		if (activityList.getPlatform().indexOf(platform) == -1) {
			// 操作平台
			Map<String, String> clients = CacheUtil.getParamNameMap("CLIENT");
			// 被选中操作平台
			String clientSed[] = StringUtils.split(activityList.getPlatform(), ",");
			StringBuffer selectedClientDisplayBuffer = new StringBuffer();

			for (String client : clientSed) {
				// 被选中的平台编号
				Set<String> keys = clients.keySet();
				for (String key : keys) {
					if (StringUtils.equals(key, client)) {
						// 被选中的平台名称 表示用
						selectedClientDisplayBuffer.append(clients.get(key));
						selectedClientDisplayBuffer.append("/");
					}
				}
			}
			String str = "";
			if (selectedClientDisplayBuffer.toString().length() > 0) {
				str = selectedClientDisplayBuffer.substring(0, selectedClientDisplayBuffer.length() - 1);
			}
			return ClientConstants.PLATFORM_LIMIT.replace("***", str);
		}
		return "";
	}

	@Override
	public UserVO getUser(String loginUserName) {
		return amUserClient.findUserByUserNameOrMobile(loginUserName);
	}

	/**
	 * 根据绑定信息取得用户id
	 *
	 * @param bindUniqueId
	 * @return
	 */
	@Override
	public Integer getUserIdByBind(int bindUniqueId, int bindPlatformId) {
		// 检索条件

		return amUserClient.getUserIdByBind(bindUniqueId, bindPlatformId);
	}

	/**
	 * 根据绑定信息取得用户id
	 *
	 * @param
	 * @return
	 */
	@Override
	public String getBindUniqueIdByUserId(int userId, int bindPlatformId) {

		return amUserClient.getBindUniqueIdByUserId(userId, bindPlatformId);
	}

	@Override
	public Boolean bindThirdUser(Integer userId, int bindUniqueId, Integer pid) {
		return amUserClient.bindThirdUser(userId, bindUniqueId, pid);
	}

	/**
	 * 根据身份证查询User
	 * @auth sunpeikai
	 * @param idCard 身份证号码
	 * @return
	 */
	@Override
	public UserVO getUserByIdCard(String idCard) {
		UserInfoVO userInfoVO = amUserClient.getUserByIdNo(idCard);
		if (userInfoVO != null) {
			return amUserClient.findUserById(userInfoVO.getUserId());

		}
		return null;
	}

	/**
	 *
	 * @param userName 用户名，手机号
	 * @param loginPassword 登录密码
	 * @param channel 渠道
	 * @return
	 */
	@Override
	public Map<String, String> insertErrorPassword(String userName,String loginPassword,String channel,UserVO userVO) {
		Map<String, String> r=new HashMap<>();
		CheckUtil.check(userVO!=null,MsgEnum.ERR_USER_NOT_EXISTS);
		if(userVO!=null){
			//1.获取该用户密码错误次数
			String passwordErrorNum=RedisUtils.get(RedisConstants.PASSWORD_ERR_COUNT_ALL + userVO.getUserId());
			//2.获取用户允许输入的最大错误次数
			Integer maxLoginErrorNum=LockedConfigManager.getInstance().getWebConfig().getMaxLoginErrorNum();
			//3.redis配置的超限有效时间
			long retTime  = RedisUtils.ttl(RedisConstants.PASSWORD_ERR_COUNT_ALL + userVO.getUserId());
			String codeSalt = userVO.getSalt();
			String passwordDb = userVO.getPassword();
			// 页面传来的密码
			String password = "";
			if (channel.equals(BankCallConstant.CHANNEL_PC)) {
				password = MD5Utils.MD5(loginPassword + codeSalt);
			}else {
				password = MD5Utils.MD5(MD5Utils.MD5(loginPassword) + codeSalt);
			}
			logger.info("passwordDB:[{}],password:[{}],相等:[{}]",passwordDb,password,password.equals(passwordDb));
			// 是否禁用
			if (userVO.getStatus() == 1) {
				r.put("info","该用户已被禁用");
				return r;
			}
			//判断密码错误次数是否超限
			if (!StringUtils.isEmpty(passwordErrorNum)&&Integer.parseInt(passwordErrorNum)>=maxLoginErrorNum) {
//			CheckUtil.check(false, MsgEnum.ERR_PASSWORD_ERROR_TOO_MAX,DateUtils.SToHMSStr(retTime));
				r.put("info","您的登录失败次数超限，请"+DateUtils.SToHMSStr(retTime)+"之后重试!");
				return  r;
			}
			if (!password.equals(passwordDb)) {
				long value = this.insertPassWordCount(RedisConstants.PASSWORD_ERR_COUNT_ALL+ userVO.getUserId());//以用户手机号为key
				for (int i=1;i<4;i++){
					if (maxLoginErrorNum-value == i){
//					CheckUtil.check(false, MsgEnum.ERR_PASSWORD_ERROR_MAX,i);
						r.put("info","登录失败,您的登录机会还剩"+i+"次!");
					}
				}
				if (maxLoginErrorNum - value == 0){
					logger.info("插入密码超限用户信息开始","-----userId:"+userVO.getUserId());
					Integer	loginLockTime=LockedConfigManager.getInstance().getWebConfig().getLockLong();//获取Redis配置的登录错误次数有效时间
					// 同步输错密码超限锁定用户信息接口
					LockedUserInfoVO lockedUserInfoVO=new LockedUserInfoVO();
					lockedUserInfoVO.setUserid(userVO.getUserId());
					lockedUserInfoVO.setUsername(userVO.getUsername());
					lockedUserInfoVO.setMobile(userVO.getMobile());
					lockedUserInfoVO.setLockTime(new Date());
					lockedUserInfoVO.setUnlockTime(DateUtils.nowDateAddDate(loginLockTime));
					lockedUserInfoVO.setFront(1);
					lockedUserInfoVO.setUnlocked(0);
					amUserClient.inserLockedUser(lockedUserInfoVO);
					r.put("info","您的登录失败次数超限，请"+DateUtils.SToHMSStr(retTime)+"之后重试!");
					logger.info("插入密码超限用户信息结束","-----userId:"+userVO.getUserId());
				}
			}
		}
		return  r;
	}
	/**
	 * redis增加
	 * @param key
	 */
	private long insertPassWordCount(String key) {
		long retValue  = RedisUtils.incr(key);
//		RedisUtils.expire(key,RedisUtils.getRemainMiao());//给key设置过期时间
		Integer	loginErrorConfigManager=LockedConfigManager.getInstance().getWebConfig().getLockLong();
		//.获取用户允许输入的最大错误次数
		Integer maxLoginErrorNum=LockedConfigManager.getInstance().getWebConfig().getMaxLoginErrorNum();
		if(retValue<=maxLoginErrorNum){
			RedisUtils.expire(key,loginErrorConfigManager*3600);//给key设置过期时间
		}
		return retValue;
	}
	@Override
	public Map<String, String> updateLoginInAction(String userName, String password, String ipAddr) {
		Map<String, String> r=new HashMap<>();
		r.put("stt", "0");
		String codeSalt = "";
		String passwordDb = "";
		Integer userId = null;
		String usernameString=null;
		String mobileString=null;

		UserVO u = amUserClient.findUserByUserNameOrMobile(userName);
		if (u == null) {
			r.put("stt", "-1");
			return r;
		} else {
			r.put("userId", u.getUserId().toString());
			userId = u.getUserId();
			codeSalt = u.getSalt();
			passwordDb =u.getPassword();
			usernameString=u.getUsername();
			mobileString=u.getMobile();
			if (u.getStatus() == 1) {
				r.put("stt", "-4");
				return r;
			}
		}

		//1.获取该用户密码错误次数
		String passwordErrorNum=RedisUtils.get(RedisConstants.PASSWORD_ERR_COUNT_ALL + userId);
		//2.获取用户允许输入的最大错误次数
		Integer maxLoginErrorNum=LockedConfigManager.getInstance().getWebConfig().getMaxLoginErrorNum();//获取Redis配置的额登录最大错误次数
		//判断密码错误次数是否超限
		if (!StringUtils.isEmpty(passwordErrorNum)&&Integer.parseInt(passwordErrorNum)>maxLoginErrorNum) {
			r.put("stt", "-5");
			return r;//密码错误次数已达上限
		}
		// 验证用的password
		password = MD5Utils.MD5(MD5Utils.MD5(password) + codeSalt);
		// 密码正确时
		if (Validator.isNotNull(userId) && Validator.isNotNull(password) && password.equals(passwordDb)) {
			// 更新登录信息
			amUserClient.updateUser(u,ipAddr);
			// 1. 登录成功将登陆密码错误次数的key删除
			RedisUtils.del(RedisConstants.PASSWORD_ERR_COUNT_ALL + userId);
			BankOpenAccountVO account = this.getBankOpenAccount(userId);
			String accountId = null;
			if (account != null && StringUtils.isNoneBlank(account.getAccount())) {
                synBalanceService.synBalance(accountId,ipAddr);
			}
			String sign = SecretUtil.createToken(userId, usernameString, accountId);
			r.put("sign", sign);
			return r;
		} else {
			//增加密码错误次数
			RedisUtils.incr(RedisConstants.PASSWORD_ERR_COUNT_ALL + userId);;//以用户userId为key
			//1.获取该用户密码错误次数，2.判断是否错误超过错误次数
			if((Integer.valueOf(passwordErrorNum)+1) < maxLoginErrorNum){
				r.put("stt", "-3");
				return r;
			}else{
				r.put("stt", "-5");
				return r;//用户当天密码错误次数已达上限
			}
		}
	}



	/**
	 * 发送神策数据统计MQ
	 *
	 * @param sensorsDataBean
	 * @throws MQException
	 */
	@Override
	public void sendSensorsDataMQ(SensorsDataBean sensorsDataBean) throws MQException {
		this.commonProducer.messageSendDelay(new MessageContent(MQConstant.SENSORSDATA_LOGIN_TOPIC, UUID.randomUUID().toString(), sensorsDataBean), 2);
	}
}
