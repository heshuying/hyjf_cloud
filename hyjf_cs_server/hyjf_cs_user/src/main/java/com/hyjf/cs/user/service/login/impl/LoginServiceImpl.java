/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.login.impl;

import com.hyjf.am.vo.admin.AdminBankAccountCheckCustomizeVO;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.user.bean.BaseDefine;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmMarketClient;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.VipImageUrlEnum;
import com.hyjf.cs.user.vo.UserParameters;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.login.LoginService;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangqingqing
 * @version LoginServiceImpl, v0.1 2018/6/11 15:32
 */
@Service
public class LoginServiceImpl extends BaseUserServiceImpl implements LoginService {

	private static DecimalFormat DF_FOR_VIEW = new DecimalFormat("#,##0.00");
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

	/**
	 * 登录
	 *
	 * @param loginUserName
	 *            手机号
	 * @param loginPassword
	 * @param ip
	 */
	@Override
	public WebViewUserVO login(String loginUserName, String loginPassword, String ip, String channel) {
		if (checkMaxLength(loginUserName, 16) || checkMaxLength(loginPassword, 32)) {
			CheckUtil.check(false, MsgEnum.ERR_USER_LOGIN);
		}
		// 获取密码错误次数
		String errCount = RedisUtils.get(RedisConstants.PASSWORD_ERR_COUNT + loginUserName);
		if (StringUtils.isNotBlank(errCount) && Integer.parseInt(errCount) > 6) {
			CheckUtil.check(false, MsgEnum.ERR_PASSWORD_ERROR_TOO_MANEY);
		}
		return this.doLogin(loginUserName, loginPassword, ip, channel);
	}

	/**
	 * 登录处理
	 *
	 * @param loginUserName
	 * @param loginPassword
	 * @return
	 */
	private WebViewUserVO doLogin(String loginUserName, String loginPassword, String ip, String channel) {
		UserVO userVO = amUserClient.findUserByUserNameOrMobile(loginUserName);
		WebViewUserVO webViewUserVO = new WebViewUserVO();
		CheckUtil.check(userVO != null, MsgEnum.ERR_USER_LOGIN);
		int userId = userVO.getUserId();
		String codeSalt = userVO.getSalt();
		String passwordDb = userVO.getPassword();
		// 页面传来的密码
		String password = MD5Utils.MD5(MD5Utils.MD5(loginPassword) + codeSalt);

		if (password.equals(passwordDb)) {
			// 是否禁用
			if (userVO.getStatus() == 1) {
				throw new ReturnMessageException(MsgEnum.ERR_USER_INVALID);
			}
			// 更新登录信息
			amUserClient.updateLoginUser(userId, ip);
			updateUserByUserId(userVO);
			// 1. 登录成功将登陆密码错误次数的key删除
			RedisUtils.del(RedisConstants.PASSWORD_ERR_COUNT + loginUserName);
			webViewUserVO = this.getWebViewUserByUserId(userVO.getUserId());
			// 2. 缓存
			webViewUserVO = setToken(webViewUserVO);
			BankOpenAccountVO account = this.getBankOpenAccount(userId);
			String accountId = null;
			if (account != null && StringUtils.isNoneBlank(account.getAccount())) {
				accountId = account.getAccount();
				this.synBalance(accountId, systemConfig.getInstcode(), "http://CS-TRADE",
						systemConfig.getAopAccesskey());
			}
			if (channel.equals(BankCallConstant.CHANNEL_WEI)) {
				String sign = SecretUtil.createToken(userId, loginUserName, accountId);
				webViewUserVO.setToken(sign);
			}
		} else {
			// 密码错误，增加错误次数
			RedisUtils.incr(RedisConstants.PASSWORD_ERR_COUNT + loginUserName);
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
		String imghost = UploadFileUtils.getDoPath(systemConfig.getFileDomainUrl());
		imghost = imghost.substring(0, imghost.length() - 1);
		String apphost = UploadFileUtils.getDoPath(systemConfig.getAppServerHost())
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
				result.setJiangxiDesc(
						account.substring(0, 3) + "************" + account.substring(account.length() - 4));

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
			if (user.getOpenAccount() != null && user.getOpenAccount() == 1) {
				// 汇付开户
				result.setHuifuOpenAccount(CustomConstants.FLAG_OPENACCOUNT_YES);
			} else {// 未开户
				result.setHuifuOpenAccount(CustomConstants.FLAG_OPENACCOUNT_NO);
			}
			// 未绑卡
			int bingCardStatus = ClientConstants.BANK_BINDCARD_STATUS_FAIL;
			List<BankCardVO> bankCardList = amUserClient.getBankOpenAccountById(user.getUserId());
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
				result.setChangeTradePasswordUrl(apphost + ClientConstants.SETPASSWORD_ACTION + packageStr(request));
			} else {
				result.setChangeTradePasswordUrl(apphost + ClientConstants.RESETPASSWORD_ACTION + packageStr(request));
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
					String fileUploadTempPath = UploadFileUtils.getDoPath(systemConfig.getUploadHeadPath());
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
				// 用户角色：1投资人2借款人3担保机构
				Integer roleId = userInfo.getRoleId();
				result.setRoleId(roleId == null ? "" : String.valueOf(roleId));
			} else {
				throw new RuntimeException("获取用户属性异常:不存在详细信息");
			}
		}
		{
			AccountVO account = amTradeClient.getAccount(userId);
			BigDecimal balance = BigDecimal.ZERO;
			BigDecimal frost = BigDecimal.ZERO;
			BigDecimal planInterestWait = BigDecimal.ZERO;
			BigDecimal planCapitalWait = BigDecimal.ZERO;
			BigDecimal planAccountWait = BigDecimal.ZERO;
			BigDecimal bankWait = BigDecimal.ZERO;
			BigDecimal awaitTotal = BigDecimal.ZERO;
			if (account != null) {
				if (account.getBalance() == null) {
					result.setBalance("0.00");
				} else {

					if (request.getParameter("version").startsWith("1.1.0")) {
						// add by cwyang 增加汇付余额

						result.setHuifuBalance(account.getBalance() + "");
						result.setBalance(account.getBankBalance() + "");

						BigDecimal indexbigD = new BigDecimal(0);
						// add by cwyang
						// 如果汇付余额为0,则将返回信息置空,用来区分前台页面是显示汇付余额还是江西银行余额
						if (account.getBalance() == null || indexbigD.compareTo(account.getBalance()) == 0) {
							result.setHuifuBalance("");
						}
					} else {
						result.setHuifuBalance(account.getBalance() + "");
						if (account.getBalance() != null) {
							result.setHuifuBalance(DF_FOR_VIEW.format(account.getBalance()));
						}
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
						BigDecimal indexbigD = new BigDecimal(0);
						// add by cwyang
						// 如果汇付余额为0,则将返回信息置空,用来区分前台页面是显示汇付余额还是江西银行余额
						if (account.getBalance() == null || indexbigD.compareTo(account.getBalance()) == 0) {
							result.setHuifuBalance("");
						}
					}
					balance = account.getBankBalance();
					if (balance == null) {
						balance = BigDecimal.ZERO;
					}
				}
				if (account.getFrost() != null) {
					frost = account.getFrost();
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
			if (StringUtils.isNotEmpty(result.getMobile())) {
				// 开户url
				result.setHuifuOpenAccountUrl("");
				// 江西银行开户url
				result.setOpenAccountUrl(systemConfig.getAppHost() + ClientConstants.BANKOPEN_OPEN_ACTION
						+ packageStr(request) + "&mobile=" + result.getMobile());
			} else {
				// 开户url
				result.setHuifuOpenAccountUrl("");
				// 江西银行开户url
				result.setOpenAccountUrl(
						systemConfig.getAppHost() + ClientConstants.BANKOPEN_OPEN_ACTION + packageStr(request));
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
					BankConfigVO bankConfig = amConfigClient.selectBankConfigByCode(accountBank.getBank());
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
			AccountChinapnrVO accountChinapnrVO = amUserClient.getAccountChinapnr(userId);
			// 江西银行绑卡接口修改
			Integer urlType = amConfigClient.getBankInterfaceFlagByType("BIND_CARD");
			// 江西银行绑卡接口修改
			if (accountChinapnrVO != null) {
				result.setChinapnrUsrcustid(accountChinapnrVO.getChinapnrUsrid() + "");
				// 汇付天下账户描述
				result.setHuifuDesc(
						accountChinapnrVO.getChinapnrUsrid().substring(0, 3) + "**************" + accountChinapnrVO
								.getChinapnrUsrid().substring(accountChinapnrVO.getChinapnrUsrid().length() - 3));
				// 绑卡url
				result.setHuifuBindBankCardUrl(apphost + ClientConstants.USER_BIND_CARD + packageStr(request));
				// 江西银行绑卡url
				// 江西银行绑卡接口修改
				if (urlType == 1) {
					// 绑卡接口类型为新接口
					result.setBindBankCardUrl(apphost + ClientConstants.REQUEST_BINDCARDPAGE + packageStr(request));
				} else {
					// 绑卡接口类型为旧接口
					result.setBindBankCardUrl(
							systemConfig.getAppHost() + ClientConstants.BINDCARD_ACTION + packageStr(request));
				}
				// 江西银行绑卡接口修改
			} else {
				// 江西银行绑卡url
				// 江西银行绑卡接口修改
				if (urlType == 1) {
					// 绑卡接口类型为新接口
					result.setBindBankCardUrl(apphost + ClientConstants.REQUEST_BINDCARDPAGE + packageStr(request));
				} else {
					// 绑卡接口类型为旧接口
					result.setBindBankCardUrl(
							systemConfig.getAppHost() + ClientConstants.BINDCARD_ACTION + packageStr(request));
				}
			}
		}
		{
			// 二维码
			result.setQrCodeUrl(systemConfig.getWechatQrcodeUrl().replace("{userId}", String.valueOf(userId)));
		}
		{
			// 风险测评结果
			UserEvalationResultVO userEvalationResult = amUserClient.selectUserEvalationResultByUserId(userId);
			if (userEvalationResult != null) {
				// 获取评测时间加一年的毫秒数18.2.2评测 19.2.2
				Long lCreate = GetDate.countDate(userEvalationResult.getCreateTime(), 1, 1).getTime();
				// 获取当前时间加一天的毫秒数 19.2.1以后需要再评测19.2.2
				Long lNow = GetDate.countDate(new Date(), 5, 1).getTime();
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
			// 自动投标授权状态 0: 未授权 1:已授权
			HjhUserAuthVO hjhUserAuthVO = this.getHjhUserAuth(userId);
			if (hjhUserAuthVO != null && hjhUserAuthVO.getAutoInvesStatus() == 1) {
				result.setAutoInvesStatus("1");
				result.setNewAutoInvesStatus("1");
				result.setToubiaoDesc("已授权");
			} else {
				result.setAutoInvesStatus("0");
				result.setNewAutoInvesStatus("0");
				result.setToubiaoDesc("未授权");
			}
			// 自动债转授权状态 0：未授权 1：已授权
			if (hjhUserAuthVO != null && hjhUserAuthVO.getAutoCreditStatus() == 1) {
				result.setNewAutoCreditStatus("1");
			} else {
				result.setNewAutoCreditStatus("0");
			}
			// 缴费授权 0：未授权 1：已授权
			if (hjhUserAuthVO != null && hjhUserAuthVO.getAutoPaymentStatus() == 1) {
				result.setPaymentAuthStatus("1");
			} else {
				result.setPaymentAuthStatus("0");
			}
		}
		{
			// 自动投标授权URL
			result.setAutoInvesUrl(CommonUtils.concatReturnUrl(request, systemConfig.getAppHost()
					+ BaseDefine.REQUEST_HOME + ClientConstants.USER_AUTH_INVES_ACTION + ".do?1=1"));
			// 缴费授权Url
			result.setPaymentAuthUrl(CommonUtils.concatReturnUrl(request, systemConfig.getAppHost()
					+ BaseDefine.REQUEST_HOME + ClientConstants.PAYMENT_AUTH_ACTION + ".do?1=1"));
		}
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

	// 组装url
	private String packageStr(HttpServletRequest request) {
		StringBuffer sbUrl = new StringBuffer();
		// 版本号
		String version = request.getParameter("version");
		// 网络状态
		String netStatus = request.getParameter("netStatus");
		// 平台
		String platform = request.getParameter("platform");
		// token
		String token = request.getParameter("token");
		// 唯一标识
		String sign = request.getParameter("sign");
		// 随机字符串
		String randomString = request.getParameter("randomString");
		// Order
		String order = request.getParameter("order");
		sbUrl.append("?").append("version").append("=").append(version);
		sbUrl.append("&").append("netStatus").append("=").append(netStatus);
		sbUrl.append("&").append("platform").append("=").append(platform);
		sbUrl.append("&").append("randomString").append("=").append(randomString);
		sbUrl.append("&").append("sign").append("=").append(sign);
		sbUrl.append("&").append("token").append("=").append(strEncode(token));
		sbUrl.append("&").append("order").append("=").append(strEncode(order));
		return sbUrl.toString();
	}

	/**
	 * 检查是否是新手(未登录或已登录未投资)
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
}
