package com.hyjf.am.user.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.RegisterUserRequest;
import com.hyjf.am.user.dao.mapper.auto.*;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.service.UserService;
import com.hyjf.common.exception.ServiceException;
import com.hyjf.common.http.HttpDeal;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.MD5Utils;
import com.hyjf.common.validator.Validator;

/**
 * @author xiasq
 * @version UserServiceImpl, v0.1 2018/1/21 22:42
 */

@Service
public class UserServiceImpl implements UserService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UsersMapper usersMapper;

	@Autowired
	private PreRegistMapper preRegistMapper;

	@Autowired
	private UsersInfoMapper usersInfoMapper;

	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	private SpreadsUsersMapper spreadsUsersMapper;

	@Autowired
	private UsersLogMapper usersLogMapper;

	@Value("${hyjf.ip.taobo.url}")
	private String ipInfoUrl;

	@Override
	public Users register(RegisterUserRequest userRequest) throws ServiceException {

		int userId = 0;
		String mobile = userRequest.getMobilephone();
		String loginIp = userRequest.getLoginIp();
		String reffer = userRequest.getReffer();
		String platform = userRequest.getPlatform();
		String password = userRequest.getPassword();
		String utmId = userRequest.getUtm_id();
		try {
			// 写入用户信息表
			Users user = new Users();
			String userName = generateUniqueUsername(mobile);
			user.setInstCode("10000000");
			user.setIsInstFlag(0);
			user.setUsername(userName);
			user.setMobile(mobile);
			user.setLoginIp(loginIp);
			user.setRechargeSms(0);
			user.setWithdrawSms(0);
			user.setInvestflag(0);
			user.setInvestSms(0);
			user.setRecieveSms(0);
			user.setVersion(new BigDecimal("0"));
			user.setUserType(0);
			user.setIsSetPassword(0);// 是否设置了交易密码 0未设置
			// user.setEmail(ru.getEmail());
			List<Users> recommends = null;
			// 写入用户详情表
			UsersInfo userInfo = new UsersInfo();
			// 以下语句用来设置用户有无主单开始 2015年12月30日18:28:34 孙亮
			userInfo.setAttribute(0);// 默认为无主单
			// 1.注册成功时，推荐人关联
			// B1、用户在注册时，没有填写推荐人，也没有预注册，或预注册时没有关联推荐人，则推荐人为空；
			// B2、用户注册时，填写了推荐人，忽略关联推荐人，以填写的推荐人为准；
			// B3、用户注册时，没有填写推荐人，预注册表中，有关联推荐人，以关联的推荐人为准；
			PreRegistExample preRegistExample = new PreRegistExample();
			preRegistExample.createCriteria().andMobileEqualTo(mobile);
			List<PreRegist> preRegistList = preRegistMapper.selectByExample(preRegistExample);
			if (preRegistList != null && preRegistList.size() != 0) {
				for (int i = 0; i < preRegistList.size(); i++) {
					PreRegist model = preRegistList.get(i);
					model.setRegistFlag(1);
					model.setRegistTime(GetDate.getMyTimeInMillis());
					preRegistMapper.updateByPrimaryKey(model);
				}
			}
			if (StringUtils.isEmpty(reffer)) {
				if (preRegistList != null && preRegistList.size() != 0) {
					reffer = preRegistList.get(0).getReferrer();
				}
			}
			if (StringUtils.isNotEmpty(reffer)) {
				UsersExample exampleUser = new UsersExample();
				if (Validator.isMobile(reffer)) {
					UsersExample.Criteria criteria = exampleUser.createCriteria();
					criteria.andMobileEqualTo(reffer);
				} else {
					UsersExample.Criteria criteria1 = exampleUser.createCriteria();
					Integer recommend = Integer.valueOf(reffer);
					criteria1.andUserIdEqualTo(recommend);
				}
				recommends = usersMapper.selectByExample(exampleUser);
				if (recommends != null && recommends.size() == 1) {
					UsersInfoExample puie = new UsersInfoExample();
					UsersInfoExample.Criteria puipec = puie.createCriteria();
					puipec.andUserIdEqualTo(recommends.get(0).getUserId());
					List<UsersInfo> pUsersInfoList = usersInfoMapper.selectByExample(puie);
					if (pUsersInfoList != null && pUsersInfoList.size() == 1) {
						// 如果该用户的上级不为空
						UsersInfo parentInfo = pUsersInfoList.get(0);
						if (Validator.isNotNull(parentInfo) && Validator.isNotNull(parentInfo.getAttribute())) {
							user.setReferrer(recommends.get(0).getUserId());
							user.setReferrerUserName(recommends.get(0).getUsername());
							if (Validator.equals(parentInfo.getAttribute(), new Integer(2))
									|| Validator.equals(parentInfo.getAttribute(), new Integer(3))) {
								// 有推荐人且推荐人为员工(Attribute=2或3)时才设置为有主单
								userInfo.setAttribute(1);
							}
						}
					}
				}
			} else {

			}
			// 以上语句用来设置用户有无主单结束 2015年12月30日18:28:34 孙亮
			if (StringUtils.isNotBlank(platform)) {
				user.setRegEsb(Integer.parseInt(platform)); // 账户开通平台 0pc 1微信
				// 2安卓 3IOS 4其他
			}

			int time = GetDate.getNowTime10();
			String codeSalt = GetCode.getRandomCode(6);
			user.setPassword(MD5Utils.MD5(MD5Utils.MD5(password) + codeSalt));
			user.setRegIp(loginIp);
			user.setRegTime(time);
			user.setLoginIp(loginIp);
			user.setLoginTime(time);
			user.setLastIp(loginIp);
			user.setLastTime(time);
			user.setLogintime(1);// 登录次数
			user.setStatus(0);

			user.setSalt(codeSalt);
			user.setBorrowSms(0);
			// user.setAccountEsb(0);
			user.setPid(0);
			user.setUsernamep("");
			user.setPtype(0);
			user.setOpenAccount(0);
			user.setBankOpenAccount(0);
			usersMapper.insertSelective(user);

			// 根据ip获取注册地址
			if (StringUtils.isNotEmpty(loginIp)) {
				getAddress(loginIp, userInfo);
			}

			userId = user.getUserId();
			// userInfo表
			userInfo.setUserId(userId);
			userInfo.setRoleId(1);
			userInfo.setMobileIsapprove(1);
			userInfo.setTruenameIsapprove(0);
			userInfo.setEmailIsapprove(0);
			userInfo.setUpdateTime(time);
			userInfo.setPromoteway(0);
			userInfo.setIs51(0);
			userInfo.setIsStaff(0);
			userInfo.setDepartmentId(0);
			userInfo.setNickname("");
			userInfo.setBirthday("");
			userInfo.setSex(1);
			userInfo.setIdcard("");
			userInfo.setEducation("");
			userInfo.setAddress("");
			userInfo.setIntro("");
			userInfo.setInterest("");
			userInfo.setParentId(0);
			userInfo.setTruenameIsapprove(0);
			userInfo.setEmailIsapprove(0);
			userInfo.setPromoteway(0);
			userInfo.setIsContact(false);
			logger.info("saveRegistUser***********************************预插入userInfo：{}", JSON.toJSONString(userInfo));
			usersInfoMapper.insertSelective(userInfo);
			// 写入用户账户表
			{
				Account account = new Account();
				account.setUserId(userId);
				// 银行存管相关
				account.setBankBalance(BigDecimal.ZERO);
				account.setBankBalanceCash(BigDecimal.ZERO);
				account.setBankFrost(BigDecimal.ZERO);
				account.setBankFrostCash(BigDecimal.ZERO);
				account.setBankInterestSum(BigDecimal.ZERO);
				account.setBankInvestSum(BigDecimal.ZERO);
				account.setBankWaitCapital(BigDecimal.ZERO);
				account.setBankWaitInterest(BigDecimal.ZERO);
				account.setBankWaitRepay(BigDecimal.ZERO);
				account.setBankTotal(BigDecimal.ZERO);
				account.setBankAwaitCapital(BigDecimal.ZERO);
				account.setBankAwaitInterest(BigDecimal.ZERO);
				account.setBankAwait(BigDecimal.ZERO);
				account.setBankWaitRepayOrg(BigDecimal.ZERO);// add by cwyang
																// account表添加字段
				account.setBankAwaitOrg(BigDecimal.ZERO);// add by cwyang
															// account表添加字段

				// 汇付相关
				account.setTotal(BigDecimal.ZERO);
				account.setIncome(BigDecimal.ZERO);
				account.setExpend(BigDecimal.ZERO);
				account.setBalance(BigDecimal.ZERO);
				account.setBalanceCash(BigDecimal.ZERO);
				account.setBalanceFrost(BigDecimal.ZERO);
				account.setFrost(BigDecimal.ZERO);
				account.setAwait(BigDecimal.ZERO);
				account.setRepay(BigDecimal.ZERO);
				account.setFrostCash(BigDecimal.ZERO);
				account.setRecMoney(BigDecimal.ZERO);
				account.setFee(BigDecimal.ZERO);
				account.setInMoney(BigDecimal.ZERO);
				account.setInMoneyFlag(0);
				// 注册时为account表增加
				// planTotal,planBalance,planFrost,planAccountWait,planCapitalWait,planInterestWait,planRepayInterest默认值
				account.setPlanAccedeTotal(BigDecimal.ZERO);
				account.setPlanBalance(BigDecimal.ZERO);
				account.setPlanFrost(BigDecimal.ZERO);
				account.setPlanAccountWait(BigDecimal.ZERO);
				account.setPlanCapitalWait(BigDecimal.ZERO);
				account.setPlanInterestWait(BigDecimal.ZERO);
				account.setPlanRepayInterest(BigDecimal.ZERO);
				account.setVersion(BigDecimal.ZERO);
				logger.info("saveRegistUser***********************************预插入account：{}",
						JSON.toJSONString(account));
				accountMapper.insertSelective(account);
			}
			if (recommends != null && recommends.size() == 1) {
				int referer = recommends.get(0).getUserId();
				SpreadsUsers spreadUser = new SpreadsUsers();
				spreadUser.setUserId(userId);
				spreadUser.setSpreadsUserid(referer);
				spreadUser.setAddip(loginIp);
				spreadUser.setAddtime(String.valueOf(time));
				spreadUser.setType("reg");
				spreadUser.setOpernote("reg");
				spreadUser.setOperation(userId + "");
				logger.info("saveRegistUser***********************************预插入spreadUser：{}",
						JSON.toJSONString(spreadUser));
				spreadsUsersMapper.insertSelective(spreadUser);
			}

			// 插入utmReg表
			if (StringUtils.isNotEmpty(utmId)) {
				if (Validator.isNumber(utmId)) {
					UtmReg utmReg = new UtmReg();
					utmReg.setCreateTime(GetDate.getNowTime10());
					utmReg.setUtmId(Integer.parseInt(utmId));
					utmReg.setUserId(userId);
					utmReg.setOpenAccount(0);
					utmReg.setBindCard(0);
					saveUtmReg(utmReg);
					logger.info("updateRegistUser***********************************预插入utmReg：{}",
							JSON.toJSONString(utmReg));
				}
			}
			// 保存用户注册日志
			{
				UsersLog userLog = new UsersLog();
				userLog.setUserId(userId);
				userLog.setIp(loginIp);
				userLog.setEvent("register");
				userLog.setContent("注册成功");
				logger.info("saveRegistUser***********************************预插入userLog：{}",
						JSON.toJSONString(userLog));
				usersLogMapper.insertSelective(userLog);
			}
		} catch (Exception e) {
			logger.error("注册失败...", e);
		}

		return null;
	}

	@Override
	public Users findUserByUserId(int userId) {
		UsersExample usersExample = new UsersExample();
		usersExample.createCriteria().andUserIdEqualTo(userId);
		List<Users> usersList = usersMapper.selectByExample(usersExample);
		if (!CollectionUtils.isEmpty(usersList)) {
			return usersList.get(0);
		}
		return null;
	}

	/** 获取唯一username */
	@Override
	public String generateUniqueUsername(String mobile) {
		String username = "hyjf" + mobile.substring(mobile.length() - 6, mobile.length());
		// 第一规则
		UsersExample ue = new UsersExample();
		UsersExample.Criteria cr = ue.createCriteria();
		cr.andUsernameEqualTo(username);
		int cn1 = usersMapper.countByExample(ue);
		if (cn1 > 0) {
			// 第二规则
			UsersExample ue2 = new UsersExample();
			UsersExample.Criteria cr2 = ue2.createCriteria();
			username = "hyjf" + mobile;
			cr2.andUsernameEqualTo(username);
			int cn2 = usersMapper.countByExample(ue2);
			if (cn2 > 0) {
				// 第三规则
				int i = 0;
				while (true) {
					i++;
					UsersExample ue3 = new UsersExample();
					UsersExample.Criteria cr3 = ue3.createCriteria();
					username = "hyjf" + mobile.substring(mobile.length() - 6, mobile.length()) + i;
					cr3.andUsernameEqualTo(username);
					int cn3 = usersMapper.countByExample(ue3);
					if (cn3 == 0) {
						break;
					}
				}
			}
		}
		return username;
	}

	@Override
	public Users findUserByMobile(String mobile) {
		UsersExample usersExample = new UsersExample();
		usersExample.createCriteria().andMobileEqualTo(mobile);
		List<Users> usersList = usersMapper.selectByExample(usersExample);
		if (!CollectionUtils.isEmpty(usersList)) {
			return usersList.get(0);
		}
		return null;
	}

	@Override
	public Users findUserByRecommendName(String reffer) {
		UsersExample usersExample = new UsersExample();
		UsersExample.Criteria criteria = usersExample.createCriteria();
		if (Validator.isMobile(reffer)) {
			criteria.andMobileEqualTo(reffer);
		} else {
			criteria.andUserIdEqualTo(Integer.valueOf(reffer));
		}
		List<Users> usersList = usersMapper.selectByExample(usersExample);
		if (!CollectionUtils.isEmpty(usersList)) {
			return usersList.get(0);
		}
		return null;
	}

	/**
	 *
	 * 根据ip地址获取注册所在地
	 *
	 * @author hsy
	 * @param ip
	 * @param usersInfo
	 */
	private void getAddress(String ip, UsersInfo usersInfo) {
		if (StringUtils.isEmpty(ip)) {
			return;
		}
		try {
			logger.info("根据ip获取注册地请求url：" + ipInfoUrl + "?ip=" + ip);
			String result = HttpDeal.get(ipInfoUrl + "?ip=" + ip);
			logger.info("根据ip获取注册地返回结果：{}", result);

			if (StringUtils.isEmpty(result)) {
				return;
			}
			JSONObject resultObj = (JSONObject) JSONObject.parse(result);
			if (resultObj == null) {
				return;
			}
			String resultCode = resultObj.getString("code");
			if (resultCode.equals("0")) { // 查询成功
				String region = resultObj.getJSONObject("data").getString("region");
				String city = resultObj.getJSONObject("data").getString("city");
				String county = resultObj.getJSONObject("data").getString("county");
				usersInfo.setProvince(StringUtils.isEmpty(region) ? "" : region);
				usersInfo.setCity(StringUtils.isEmpty(city) ? "" : city);
				usersInfo.setArea(StringUtils.isEmpty(county) ? "" : county);
			} else {
				logger.info("根据ip地址获取所在地失败，ip：{}, 返回信息：{}", ip, result);
			}
		} catch (Exception e) {
			logger.error("根据ip地址获取所在地出错，ip：" + ip, e);
		}
	}

	/**
	 * 保存utmReg表
	 * 
	 * @param utmReg
	 */
	private void saveUtmReg(UtmReg utmReg) {
		// todo
	}

}
