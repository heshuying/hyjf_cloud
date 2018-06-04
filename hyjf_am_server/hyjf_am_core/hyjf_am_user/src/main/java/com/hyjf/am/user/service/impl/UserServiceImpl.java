package com.hyjf.am.user.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.resquest.user.RegisterUserRequest;
import com.hyjf.am.resquest.user.UsersContractRequest;
import com.hyjf.am.user.dao.mapper.auto.AccountChinapnrMapper;
import com.hyjf.am.user.dao.mapper.auto.HjhUserAuthLogMapper;
import com.hyjf.am.user.dao.mapper.auto.HjhUserAuthMapper;
import com.hyjf.am.user.dao.mapper.auto.PreRegistMapper;
import com.hyjf.am.user.dao.mapper.auto.SpreadsUserMapper;
import com.hyjf.am.user.dao.mapper.auto.UserEvalationResultMapper;
import com.hyjf.am.user.dao.mapper.auto.UserInfoMapper;
import com.hyjf.am.user.dao.mapper.auto.UserLogMapper;
import com.hyjf.am.user.dao.mapper.auto.UserLoginLogMapper;
import com.hyjf.am.user.dao.mapper.auto.UserMapper;
import com.hyjf.am.user.dao.mapper.auto.UsersContactMapper;
import com.hyjf.am.user.dao.model.auto.AccountChinapnr;
import com.hyjf.am.user.dao.model.auto.AccountChinapnrExample;
import com.hyjf.am.user.dao.model.auto.HjhUserAuth;
import com.hyjf.am.user.dao.model.auto.HjhUserAuthExample;
import com.hyjf.am.user.dao.model.auto.HjhUserAuthLog;
import com.hyjf.am.user.dao.model.auto.HjhUserAuthLogExample;
import com.hyjf.am.user.dao.model.auto.PreRegist;
import com.hyjf.am.user.dao.model.auto.PreRegistExample;
import com.hyjf.am.user.dao.model.auto.SpreadsUser;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserEvalationResult;
import com.hyjf.am.user.dao.model.auto.UserEvalationResultExample;
import com.hyjf.am.user.dao.model.auto.UserExample;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.dao.model.auto.UserInfoExample;
import com.hyjf.am.user.dao.model.auto.UserLog;
import com.hyjf.am.user.dao.model.auto.UserLoginLog;
import com.hyjf.am.user.dao.model.auto.UserLoginLogExample;
import com.hyjf.am.user.dao.model.auto.UsersContact;
import com.hyjf.am.user.dao.model.auto.UtmReg;
import com.hyjf.am.user.mq.AccountProducer;
import com.hyjf.am.user.mq.Producer;
import com.hyjf.am.user.service.UserInfoService;
import com.hyjf.am.user.service.UserService;
import com.hyjf.am.user.utils.BankCallConstant;
import com.hyjf.am.user.utils.UploadFileUtils;
import com.hyjf.am.vo.borrow.AccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
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
	private UserMapper usersMapper;
	@Autowired
	private UserLoginLogMapper userLoginLogMapper;
	@Autowired
	private PreRegistMapper preRegistMapper;

	@Autowired
	private UserInfoMapper usersInfoMapper;

	@Autowired
	private AccountProducer accountProducer;

	@Autowired
	private SpreadsUserMapper spreadsUsersMapper;

	@Autowired
	private UserLogMapper usersLogMapper;

	@Autowired
	UserInfoService userInfoService;

	@Autowired
	HjhUserAuthMapper hjhUserAuthMapper;

	@Autowired
	HjhUserAuthLogMapper hjhUserAuthLogMapper;

	@Autowired
	UserEvalationResultMapper userEvalationResultMapper;

	@Autowired
	AccountChinapnrMapper accountChinapnrMapper;
	
	@Autowired
	UsersContactMapper usersContactMapper;

	@Value("${file.domain.head.url}")
	private String fileHeadUrl;
	@Value("${file.upload.head.path}")
	private String fileHeadPath;
	@Value("${hyjf.ip.taobo.url}")
	private String ipInfoUrl;

	/**
	 * 注册
	 * 
	 * @param userRequest
	 * @return
	 * @throws MQException
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public User register(RegisterUserRequest userRequest) throws MQException {

		String mobile = userRequest.getMobilephone();
		String loginIp = userRequest.getLoginIp();
		String reffer = userRequest.getReffer();
		String platform = userRequest.getPlatform();
		String password = userRequest.getPassword();
		String utmId = userRequest.getUtmId();
		int instType = userRequest.getInstCode();
		Integer refferUserId = null;
		String refferUserName = null;
		Integer attribute = null;
		// 获取推荐人表
		User refferUser = getRefferUsers(mobile, reffer);
		if (refferUser != null) {
			UserInfo refferUserInfo = findUsersInfo(refferUser.getUserId());
			if (refferUserInfo != null) {
				// 如果该用户的上级不为空
				if (Validator.isNotNull(refferUserInfo.getAttribute())) {
					refferUserId = refferUser.getUserId();
					refferUserName = refferUser.getUsername();
					if (Arrays.asList(2, 3).contains(refferUserInfo.getAttribute())) {
						// 有推荐人且推荐人为员工(Attribute=2或3)时才设置为有主单
						attribute = 1;
					}
				}
			}
		}

		// 1. 写入用户信息表
		User users = this.insertUsers(mobile, password, loginIp, platform, refferUserId, refferUserName);
		int userId = users.getUserId();

		// 2. 写入用户详情表
		this.insertUsersInfo(userId,instType, loginIp, attribute);

		// 3. 写入用户账户表
		this.insertAccount(userId);

		// 4. 有推荐人，保存推荐人信息
		if (refferUser != null) {
			this.insertSpreadsUser(userId, refferUser.getUserId(), loginIp);
		}

		// 5. 有推广，插入utmReg表
		if (StringUtils.isNotEmpty(utmId) && Validator.isNumber(utmId)) {
			this.insertUtmReg(userId, utmId);
		}

		// 6. 保存用户注册日志
		this.insertRegLog(userId, loginIp);

		// 7. 注册成功默认登录
		this.updateLoginUser(userId, loginIp);

		return users;
	}

	@Override
	public User findUserByUserId(int userId) {
		UserExample usersExample = new UserExample();
		usersExample.createCriteria().andUserIdEqualTo(userId);
		List<User> usersList = usersMapper.selectByExample(usersExample);
		if (!CollectionUtils.isEmpty(usersList)) {
			return usersList.get(0);
		}
		return null;
	}

	/**
	 * 根据userId查询userInfo
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public UserInfo findUsersInfo(int userId) {
		UserInfoExample example = new UserInfoExample();
		UserInfoExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		List<UserInfo> list = usersInfoMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/** 获取唯一username */
	@Override
	public String generateUniqueUsername(String mobile) {
		String username = "hyjf" + mobile.substring(mobile.length() - 6, mobile.length());
		// 第一规则
		UserExample ue = new UserExample();
		UserExample.Criteria cr = ue.createCriteria();
		cr.andUsernameEqualTo(username);
		int cn1 = usersMapper.countByExample(ue);
		if (cn1 > 0) {
			// 第二规则
			UserExample ue2 = new UserExample();
			UserExample.Criteria cr2 = ue2.createCriteria();
			username = "hyjf" + mobile;
			cr2.andUsernameEqualTo(username);
			int cn2 = usersMapper.countByExample(ue2);
			if (cn2 > 0) {
				// 第三规则
				int i = 0;
				while (true) {
					i++;
					UserExample ue3 = new UserExample();
					UserExample.Criteria cr3 = ue3.createCriteria();
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
	public User findUserByMobile(String mobile) {
		UserExample usersExample = new UserExample();
		usersExample.createCriteria().andMobileEqualTo(mobile);
		List<User> usersList = usersMapper.selectByExample(usersExample);
		if (!CollectionUtils.isEmpty(usersList)) {
			return usersList.get(0);
		}
		return null;
	}

	@Override
	public User findUserByUsernameOrMobile(String condition) {
		UserExample mobileExample = new UserExample();
		mobileExample.createCriteria().andMobileEqualTo(condition);
		List<User> usersList1 = usersMapper.selectByExample(mobileExample);
		if (!CollectionUtils.isEmpty(usersList1)) {
			return usersList1.get(0);
		}

		UserExample usernameExample = new UserExample();
		usernameExample.createCriteria().andUsernameEqualTo(condition);
		List<User> usersList2 = usersMapper.selectByExample(usernameExample);
		if (!CollectionUtils.isEmpty(usersList2)) {
			return usersList2.get(0);
		}
		return null;
	}

	@Override
	public User findUserByRecommendName(String reffer) {
		UserExample usersExample = new UserExample();
		UserExample.Criteria criteria = usersExample.createCriteria();
		if (Validator.isMobile(reffer)) {
			criteria.andMobileEqualTo(reffer);
		} else {
			criteria.andUserIdEqualTo(Integer.valueOf(reffer));
		}
		List<User> usersList = usersMapper.selectByExample(usersExample);
		if (!CollectionUtils.isEmpty(usersList)) {
			return usersList.get(0);
		}
		return null;
	}

	@Override
	public UserVO assembleUserVO(UserVO userVO) {
		UserInfo usersInfo = userInfoService.findUserInfoById(userVO.getUserId());
		if (usersInfo != null) {
			userVO.setSex(usersInfo.getSex());
			userVO.setNickname(usersInfo.getNickname());
			userVO.setTruename(usersInfo.getTruename());
			userVO.setIdcard(usersInfo.getIdcard());
			userVO.setRoleId(usersInfo.getRoleId() + "");
			userVO.setBorrowerType(usersInfo.getBorrowerType());
		}
		userVO.setIconurl(this.assembleIconUrl(userVO));

		// todo 银行 汇付开户账号 紧急联系人 usersContract
		userVO.setOpenAccount(null);
		userVO.setBankOpenAccount(null);
		return userVO;
	}

	@Override
	public void updateLoginUser(int userId, String ip) {
		UserLoginLog userLoginLog = findUserLoginLogByUserId(userId);
		if (userLoginLog == null) {
			userLoginLog = new UserLoginLog();
			userLoginLog.setUserId(userId);
			userLoginLog.setLoginIp(ip);
			userLoginLog.setLoginTime(new Date());
			userLoginLog.setLastIp(userLoginLog.getLoginIp());
			userLoginLog.setLastTime(userLoginLog.getLoginTime());
			userLoginLog.setLoginTimes(1);
			userLoginLog.setCreateTime(new Date());
			userLoginLogMapper.insertSelective(userLoginLog);
		} else {
			if (userLoginLog.getLoginIp() != null) {
				userLoginLog.setLastIp(userLoginLog.getLoginIp());
			}
			if (userLoginLog.getLoginTime() != null) {
				userLoginLog.setLastTime(userLoginLog.getLoginTime());
			}
			userLoginLog.setLoginIp(ip);
			userLoginLog.setLoginTime(new Date());
            // 登录次数
			userLoginLog.setLoginTimes(userLoginLog.getLoginTimes() + 1);
			userLoginLog.setUpdateTime(new Date());
			userLoginLogMapper.updateByPrimaryKeySelective(userLoginLog);
		}
	}

	/**
	 * 组装图像url
	 *
	 * @param userVO
	 * @return
	 */
	private String assembleIconUrl(UserVO userVO) {
		String iconUrl = userVO.getIconurl();
		if (org.apache.commons.lang3.StringUtils.isNotBlank(iconUrl)) {
			String imghost = UploadFileUtils.getDoPath(fileHeadUrl);
			imghost = imghost.substring(0, imghost.length() - 1);
			String fileUploadTempPath = UploadFileUtils.getDoPath(fileHeadPath);
			return imghost + fileUploadTempPath + iconUrl;
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
	private void getAddress(String ip, UserInfo usersInfo) {
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
			if ("0".equals(resultCode)) {
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
	 * 注册查询推荐人信息
	 * 
	 * @param mobile
	 * @param reffer
	 * @return
	 */
	private User getRefferUsers(String mobile, String reffer) {
		List<User> recommends = null;
		// 以下语句用来设置用户有无主单开始 2015年12月30日18:28:34 孙亮
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
			UserExample exampleUser = new UserExample();
			if (Validator.isMobile(reffer)) {
				UserExample.Criteria criteria = exampleUser.createCriteria();
				criteria.andMobileEqualTo(reffer);
			} else {
				UserExample.Criteria criteria1 = exampleUser.createCriteria();
				Integer recommend = Integer.valueOf(reffer);
				criteria1.andUserIdEqualTo(recommend);
			}

			recommends = usersMapper.selectByExample(exampleUser);
		}
		if (!CollectionUtils.isEmpty(recommends)) {
			return recommends.get(0);
		}
		return null;
	}

	/**
	 * 注册写用户表
	 * 
	 * @param mobile
	 * @param password
	 * @param loginIp
	 * @param platform
	 * @param refferUserId
	 * @param refferUsername
	 * @return
	 */
	private User insertUsers(String mobile, String password, String loginIp, String platform, Integer refferUserId,
			String refferUsername) {
		User user = new User();
		String userName = generateUniqueUsername(mobile);
		user.setInstCode("10000000");
		user.setIsInstFlag(0);
		user.setUsername(userName);
		user.setMobile(mobile);
		user.setRechargeSms(0);
		user.setWithdrawSms(0);
		user.setInvestflag(0);
		user.setInvestSms(0);
		user.setRecieveSms(0);
		user.setVersion(new BigDecimal("0"));
		user.setUserType(0);
		user.setIsSetPassword(0);
		int time = GetDate.getNowTime10();
		String codeSalt = GetCode.getRandomCode(6);
		user.setPassword(MD5Utils.MD5(password + codeSalt));
		user.setRegIp(loginIp);
		user.setRegTime(new Date());
		user.setStatus(0);
		user.setSalt(codeSalt);
		user.setBorrowSms(0);
		user.setPid(0);
		user.setUsernamep("");
		user.setPtype(0);
		user.setOpenAccount(0);
		user.setBankOpenAccount(0);

		user.setReferrer(refferUserId);
		user.setReferrerUserName(refferUsername);

		if (StringUtils.isNotBlank(platform)) {
			user.setRegEsb(Integer.parseInt(platform));
			// 账户开通平台 0pc 1微信 2安卓 3IOS 4其他
		}
		usersMapper.insertSelective(user);
		return user;
	}

	/**
	 * 注册写用户信息表
	 * 
	 * @param userId
	 * @param loginIp
	 * @param attribute
	 */
	private void insertUsersInfo(int userId,int instType, String loginIp, Integer attribute) {
		UserInfo userInfo = new UserInfo();
		userInfo.setAttribute(0);
		// 默认为无主单
		// 根据ip获取注册地址
		if (StringUtils.isNotEmpty(loginIp)) {
			getAddress(loginIp, userInfo);
		}
		userInfo.setUserId(userId);
		userInfo.setRoleId(instType);
		userInfo.setMobileIsapprove(1);
		userInfo.setTruenameIsapprove(0);
		userInfo.setEmailIsapprove(0);
		userInfo.setUpdateTime(new Date());
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
		userInfo.setAttribute(attribute);
		logger.info("注册插入userInfo：{}", JSON.toJSONString(userInfo));
		usersInfoMapper.insertSelective(userInfo);
	}

	/**
	 * 注册保存账户表
	 * 
	 * @param userId
	 * @throws MQException
	 */
	private void insertAccount(int userId) throws MQException {
		AccountVO account = new AccountVO();
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
		account.setBankWaitRepayOrg(BigDecimal.ZERO);
		account.setBankAwaitOrg(BigDecimal.ZERO);
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
		account.setPlanAccedeTotal(BigDecimal.ZERO);
		account.setPlanBalance(BigDecimal.ZERO);
		account.setPlanFrost(BigDecimal.ZERO);
		account.setPlanAccountWait(BigDecimal.ZERO);
		account.setPlanCapitalWait(BigDecimal.ZERO);
		account.setPlanInterestWait(BigDecimal.ZERO);
		account.setPlanRepayInterest(BigDecimal.ZERO);
		account.setVersion(BigDecimal.ZERO);
		logger.info("注册插入account：{}", JSON.toJSONString(account));
		accountProducer.messageSend(new Producer.MassageContent(MQConstant.ACCOUNT_TOPIC, JSON.toJSONBytes(account)));
	}

	/**
	 * 注册保存推荐人
	 * 
	 * @param userId
	 *            注册用户
	 * @param referer
	 *            推荐人
	 * @param loginIp
	 */
	private void insertSpreadsUser(int userId, int referer, String loginIp) {
		SpreadsUser spreadUser = new SpreadsUser();
		spreadUser.setUserId(userId);
		spreadUser.setSpreadsUserId(referer);
		spreadUser.setCreateIp(loginIp);
		spreadUser.setCreateTime(new Date());
		spreadUser.setType("reg");
		spreadUser.setOpernote("reg");
		spreadUser.setOperation(userId + "");
		logger.info("注册插入spreadUser：{}", JSON.toJSONString(spreadUser));
		spreadsUsersMapper.insertSelective(spreadUser);
	}

	/**
	 * 注册保存推广信息
	 * 
	 * @param userId
	 * @param utmId
	 */
	private void insertUtmReg(int userId, String utmId) {
		UtmReg utmReg = new UtmReg();
		utmReg.setCreateTime(new Date());
		utmReg.setUtmId(Integer.parseInt(utmId));
		utmReg.setUserId(userId);
		utmReg.setOpenAccount(0);
		utmReg.setBindCard(0);
		// todo save reg
		logger.info("注册插入utmReg：{}", JSON.toJSONString(utmReg));
	}

	/**
	 * 注册保存日志
	 * 
	 * @param userId
	 * @param loginIp
	 */
	private void insertRegLog(int userId, String loginIp) {
		UserLog userLog = new UserLog();
		userLog.setUserId(userId);
		userLog.setIp(loginIp);
		userLog.setEvent("register");
		userLog.setContent("注册成功");
		logger.info("注册插入userLog：{}", JSON.toJSONString(userLog));
		usersLogMapper.insertSelective(userLog);
	}

	/**
	 * 根据userId查询登录日志
	 *
	 * @param userId
	 * @return
	 */
	private UserLoginLog findUserLoginLogByUserId(int userId) {
		UserLoginLogExample example = new UserLoginLogExample();
		UserLoginLogExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		List<UserLoginLog> list = userLoginLogMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}


	/**
	 *
	 * 根据用户id查询用户签约授权信息
	 * @param userId
	 * @return
	 */
	@Override
	public HjhUserAuth getHjhUserAuthByUserId(Integer userId) {
		HjhUserAuthExample example = new HjhUserAuthExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<HjhUserAuth> list = hjhUserAuthMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void insertSelective(HjhUserAuthLog hjhUserAuthLog){
		hjhUserAuthLogMapper.insertSelective(hjhUserAuthLog);
	}

	@Override
	public HjhUserAuthLog selectByExample(String orderId){
		HjhUserAuthLogExample example=new HjhUserAuthLogExample();
		example.createCriteria().andOrderIdEqualTo(orderId);
		List<HjhUserAuthLog>  hjhUserAuthLogList = hjhUserAuthLogMapper.selectByExample(example);
		if(hjhUserAuthLogList!=null&&hjhUserAuthLogList.size()>0) {
			return hjhUserAuthLogList.get(0);
		}else {
			return null;
		}
	}

	@Override
	public int updateByPrimaryKeySelective(HjhUserAuthLog record){

		return hjhUserAuthLogMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(HjhUserAuth record){
		return hjhUserAuthMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(HjhUserAuth record){
		return hjhUserAuthMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 修改用户表By主键
	 * @param record
	 * @return int
	 */
	@Override
	public int updateUserById(User record){
		return usersMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public void updateUserAuthInves( BankRequest bean) {
		Integer userId = Integer.parseInt(bean.getLogUserId());
		Date nowTime= GetDate.getNowTime();
		HjhUserAuthLog hjhUserAuthLog=this.selectByExample(bean.getOrderId());
		//更新用户签约授权日志表
		if(hjhUserAuthLog!=null){
			hjhUserAuthLog.setUpdateTime(nowTime);
			hjhUserAuthLog.setUpdateUserId(userId);
			hjhUserAuthLog.setOrderStatus(1);
			hjhUserAuthLog.setAuthCreateTime(GetDate.getNowTime10());
			this.updateByPrimaryKeySelective(hjhUserAuthLog);
		}
		// 这里同步异步一起进来会导致重复插入的异常，加一个同步锁
		HjhUserAuth hjhUserAuth=this.getHjhUserAuthByUserId(userId);
		// 更新用户签约授权状态信息表
		if (hjhUserAuth == null) {
			User user= this.findUserByUserId(userId);
			hjhUserAuth = new HjhUserAuth();
			// 设置状态
			setAuthType(hjhUserAuth, bean);
			hjhUserAuth.setAutoWithdrawStatus(0);
			hjhUserAuth.setAutoConsumeStatus(0);
			hjhUserAuth.setUserId(user.getUserId());
			hjhUserAuth.setUserName(user.getUsername());
			hjhUserAuth.setCreateUserId(user.getUserId());
			hjhUserAuth.setCreateTime(nowTime);
			hjhUserAuth.setUpdateTime(nowTime);
			hjhUserAuth.setUpdateUserId(userId);
			hjhUserAuth.setDelFlg(0);
			this.insertSelective(hjhUserAuth);
		} else {
			HjhUserAuth updateHjhUserAuth = new HjhUserAuth();
			// 设置状态
			setAuthType(hjhUserAuth, bean);
			updateHjhUserAuth.setId(hjhUserAuth.getId());
			updateHjhUserAuth.setUpdateTime(nowTime);
			updateHjhUserAuth.setUpdateUserId(userId);
			this.updateByPrimaryKeySelective(hjhUserAuth);
		}
	}
	/**
	 * 设置状态
	 * @param hjhUserAuth
	 * @param bean
	 */
	private void setAuthType(HjhUserAuth hjhUserAuth, BankRequest bean) {
		// 授权类型
		String txcode = bean.getTxCode();
		if(BankCallConstant.TXCODE_AUTO_BID_AUTH_PLUS.equals(txcode)){
			hjhUserAuth.setAutoInvesStatus(1);
			hjhUserAuth.setAutoOrderId(bean.getOrderId());
			hjhUserAuth.setAutoBidTime(GetDate.getNowTime10());
			hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
			hjhUserAuth.setAutoBidEndTime(bean.getDeadline());
		}else if(BankCallConstant.TXCODE_AUTO_CREDIT_INVEST_AUTH_PLUSS.equals(txcode)){
			hjhUserAuth.setAutoCreditStatus(1);
			hjhUserAuth.setAutoCreditOrderId(bean.getOrderId());
			hjhUserAuth.setAutoCreditTime(GetDate.getNowTime10());
			hjhUserAuth.setAutoCreateTime(GetDate.getNowTime10());
		}else if(BankCallConstant.TXCODE_CREDIT_AUTH_QUERY.equals(txcode)){
			//根据银行查询投资人签约状态
			if(BankCallConstant.QUERY_TYPE_1.equals(bean.getType())){
				hjhUserAuth.setAutoInvesStatus(1);
				hjhUserAuth.setAutoOrderId(bean.getOrderId());
				hjhUserAuth.setAutoBidTime(GetDate.getNowTime10());
				hjhUserAuth.setAutoBidEndTime(bean.getBidDeadline());
			}else if(BankCallConstant.QUERY_TYPE_2.equals(bean.getType())){
				hjhUserAuth.setAutoCreditStatus(1);
				hjhUserAuth.setAutoCreditOrderId(bean.getOrderId());
				hjhUserAuth.setAutoCreditTime(GetDate.getNowTime10());
			}
		}
		// 新增缴费授权和还款授权
		else if(BankCallConstant.TXCODE_PAYMENT_AUTH_PAGE.equals(txcode)){
			hjhUserAuth.setAutoPaymentStatus(1);
			hjhUserAuth.setAutoPaymentEndTime(bean.getDeadline());
			hjhUserAuth.setAutoPaymentTime(GetDate.getNowTime10());
		}else if(BankCallConstant.TXCODE_REPAY_AUTH_PAGE.equals(txcode)){
			hjhUserAuth.setAutoRepayStatus(1);
			hjhUserAuth.setAutoRepayEndTime(bean.getDeadline());
			hjhUserAuth.setAutoRepayTime(GetDate.getNowTime10());
		}

		// 客户授权功能查询接口
		else if(BankCallConstant.TXCODE_TERMS_AUTH_QUERY.equals(txcode)){
			//自动投标功能开通标志
			String autoBidStatus = bean.getAutoBid();
			//自动债转功能开通标志
			String autoTransfer = bean.getAutoTransfer();
			//缴费授权
			String paymentAuth = bean.getPaymentAuth();
			//还款授权
			String repayAuth = bean.getRepayAuth();
			if(org.apache.commons.lang3.StringUtils.isNotBlank(autoBidStatus)){
				hjhUserAuth.setAutoInvesStatus(Integer.parseInt(autoBidStatus));
			}
			if(org.apache.commons.lang3.StringUtils.isNotBlank(autoTransfer)){
				hjhUserAuth.setAutoCreditStatus(Integer.parseInt(autoTransfer));
			}
			if(org.apache.commons.lang3.StringUtils.isNotBlank(paymentAuth)){
				hjhUserAuth.setAutoPaymentStatus(Integer.parseInt(paymentAuth));
				hjhUserAuth.setAutoPaymentEndTime(bean.getPaymentDeadline());
			}
			if(org.apache.commons.lang3.StringUtils.isNotBlank(repayAuth)){
				hjhUserAuth.setAutoRepayStatus(Integer.parseInt(repayAuth));
				hjhUserAuth.setAutoRepayEndTime(bean.getRepayDeadline());
			}
			//自动投标到期日
			if(org.apache.commons.lang3.StringUtils.isNotBlank(bean.getAutoBidDeadline())) {
				hjhUserAuth.setAutoBidEndTime(bean.getAutoBidDeadline());
			}
		}

	}

	@Override
	public UserEvalationResult selectUserEvalationResultByUserId(Integer userId) {
		UserEvalationResultExample example = new UserEvalationResultExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<UserEvalationResult> userEvalationResult = userEvalationResultMapper.selectByExample(example);
		if (userEvalationResult != null && userEvalationResult.size() > 0) {
			return userEvalationResult.get(0);
		} else {
			return null;
		}
	}

	@Override
	public AccountChinapnr getAccountChinapnr(Integer userId) {
		AccountChinapnr accountChinapnr = null;
		if (userId == null) {
			return null;
		}
		AccountChinapnrExample example = new AccountChinapnrExample();
		AccountChinapnrExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		List<AccountChinapnr> list = accountChinapnrMapper.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			accountChinapnr = list.get(0);
		}
		return accountChinapnr;
	}
	
	/**
	 * 保存紧急联系人信息
	 * @param record
	 * @return
	 */
	@Override
	public int updateUserContact(UsersContractRequest record){
		if(record.getUserId() == null) {
			return 0;
		}
		
		UsersContact contact = new UsersContact();
		BeanUtils.copyProperties(record, contact);
		usersMapper.deleteByPrimaryKey(record.getUserId());
		return usersContactMapper.insertSelective(contact);
	}

}
