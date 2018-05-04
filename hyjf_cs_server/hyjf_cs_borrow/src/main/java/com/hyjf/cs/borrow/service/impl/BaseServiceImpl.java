package com.hyjf.cs.borrow.service.impl;

import com.hyjf.am.user.dao.model.auto.BankOpenAccount;
import com.hyjf.am.user.dao.model.auto.BankOpenAccountExample;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.cs.borrow.client.RechargeClient;
import com.hyjf.cs.borrow.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseServiceImpl  implements BaseService {

	Logger _log = LoggerFactory.getLogger(BaseServiceImpl.class);

	@Autowired
	RechargeClient rechargeClient;
	
/*	@Override
	public Users getUsers(Integer userId) {
		return usersMapper.selectByPrimaryKey(userId);
	}

	*//**
	 * 添加短信记录表
	 *
	 * @param content
	 *            短信内容
	 * @param mobile
	 *            手机号码
	 * @param checkCode
	 *            验证码(没有验证码，设为空)
	 * @param remark
	 *            发送备注，如 注册填写“注册”
	 * @param status
	 *            发送状态，第三方返回状态
	 * @return
	 *//*

	@Override
	public boolean AddMessage(String content, String mobile, String checkCode, String remark, int status) {

		if (StringUtils.isNotEmpty(mobile) && StringUtils.isNotEmpty(content)) {
			// 带验证码
			if (checkCode != null) {
				SmsCode smsCode = new SmsCode();
				smsCode.setMobile(mobile);
				smsCode.setCheckfor(MD5.toMD5Code(mobile + checkCode));
				smsCode.setCheckcode(checkCode);
				smsCode.setPosttime(GetDate.getNowTime10());
				smsCode.setStatus(status);
				smsCodeMapper.insertSelective(smsCode);
			}

			// 插入短信记录
			SmsLogWithBLOBs smsLog = new SmsLogWithBLOBs();

			smsLog.setMobile(mobile);
			smsLog.setContent(content);
			smsLog.setPosttime(GetDate.getNowTime10());
			smsLog.setStatus(status);
			smsLog.setType(remark);
			smsLogMapper.insertSelective(smsLog);
			return true;
		}
		return false;
	}

	*//**
	 * 获取用户的汇付信息
	 *
	 * @param userId
	 * @return 用户的汇付信息
	 *//*

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

	*//**
	 * 获取用户信息
	 *
	 * @param username
	 * @param password
	 * @return 获取用户信息
	 *//*
	@Override
	public Users getUsers(String username, String password) {
		UsersExample example = new UsersExample();
		UsersExample.Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);

		List<Users> usersList = this.usersMapper.selectByExample(example);
		if (usersList != null && usersList.size() > 0) {
			return usersList.get(0);
		}
		return null;
	}

	*//**
	 * 获取用户的账户信息
	 *
	 * @param userId
	 * @return 获取用户的账户信息
	 *//*
	@Override
	public Account getAccount(Integer userId) {
		AccountExample example = new AccountExample();
		AccountExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		List<Account> listAccount = this.accountMapper.selectByExample(example);
		if (listAccount != null && listAccount.size() > 0) {
			return listAccount.get(0);
		}
		return null;
	}

	*//**
	 * 获取系统配置
	 *
	 * @return
	 *//*
	@Override
	public String getBorrowConfig(String configCd) {
		BorrowConfig borrowConfig = this.borrowConfigMapper.selectByPrimaryKey(configCd);
		return borrowConfig.getConfigValue();
	}

	*//**
	 * 根据用户ID取得用户信息
	 *
	 * @param userId
	 * @return
	 *//*
	@Override
	public UsersInfo getUsersInfoByUserId(Integer userId) {
		if (userId != null) {
			UsersInfoExample example = new UsersInfoExample();
			example.createCriteria().andUserIdEqualTo(userId);
			List<UsersInfo> usersInfoList = this.usersInfoMapper.selectByExample(example);
			if (usersInfoList != null && usersInfoList.size() > 0) {
				return usersInfoList.get(0);
			}
		}
		return null;
	}

	*//**
	 * 获取数据字典表的下拉列表
	 *
	 * @return
	 *//*
	@Override
	public List<ParamName> getParamNameList(String nameClass) {
		ParamNameExample example = new ParamNameExample();
		ParamNameExample.Criteria cra = example.createCriteria();
		cra.andNameClassEqualTo(nameClass);
		cra.andDelFlagEqualTo(CustomConstants.FLAG_NORMAL);
		example.setOrderByClause(" sort ASC ");
		return this.paramNameMapper.selectByExample(example);
	}

	*//** 获取唯一username *//*
	@Override
	public String getUniqueUsername(String mobile) {
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

	*//**
	 * 为加强版发送验证码
	 *
	 * @param request
	 * @param form
	 * @return Map<String, Object> {success: 1,message:调用验证码接口成功,srvAuthCode:
	 *         srvAuthCode}
	 *//*
	@Override
	public BankCallBean sendSms(Integer userId, String srvTxCode, String mobile, String client) {
		// 调用存管接口发送验证码
		BankCallBean bean = new BankCallBean();
		bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
		bean.setTxCode(BankCallMethodConstant.TXCODE_SMSCODE_APPLY);// 交易代码cardBind
		bean.setInstCode(PropUtils.getSystem(BankCallConstant.BANK_INSTCODE));// 机构代码
		bean.setBankCode(PropUtils.getSystem(BankCallConstant.BANK_BANKCODE));// 银行代码
		bean.setTxDate(GetOrderIdUtils.getOrderDate());// 交易日期
		bean.setTxTime(GetOrderIdUtils.getOrderTime());// 交易时间
		bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号6位
		bean.setChannel(client);// 交易渠道000001手机APP 000002网页
		bean.setReqType("1");
		bean.setSrvTxCode(srvTxCode);
		bean.setMobile(mobile);// 交易渠道
		bean.setSmsType("1");
		bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));// 订单号
		bean.setLogUserId(String.valueOf(userId));// 请求用户名
		try {
			BankCallBean mobileBean = BankCallUtils.callApiBg(bean);
			if (Validator.isNull(mobileBean)) {
				return null;
			}
			// 短信发送返回结果码
			String retCode = mobileBean.getRetCode();
			if (!BankCallConstant.RESPCODE_SUCCESS.equals(retCode) && !BankCallConstant.RESPCODE_MOBILE_REPEAT.equals(retCode)) {
				return null;
			}
			if (Validator.isNull(mobileBean.getSrvAuthCode()) && !BankCallConstant.RESPCODE_MOBILE_REPEAT.equals(retCode)) {
				return null;
			}
			// 业务授权码
			String srvAuthCode = mobileBean.getSrvAuthCode();
			if (Validator.isNotNull(mobileBean.getSrvAuthCode())) {
				boolean smsFlag = this.updateBankSmsLog(userId, srvTxCode, srvAuthCode);
				if (smsFlag) {
					return mobileBean;
				} else {
					return null;
				}
			} else {
				// 保存用户开户日志
				srvAuthCode = this.selectBankSmsLog(userId, srvTxCode, srvAuthCode);
				if (Validator.isNull(srvAuthCode)) {
					return null;
				} else {
					mobileBean.setSrvAuthCode(srvAuthCode);
					return mobileBean;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	*//**
	 * 查询用户的授权码
	 * 
	 * @param userId
	 * @param srvTxCode
	 * @param srvAuthCode
	 * @return
	 *//*
	protected String selectBankSmsLog(Integer userId, String srvTxCode, String srvAuthCode) {
		BankSmsAuthCodeExample example = new BankSmsAuthCodeExample();
		example.createCriteria().andUserIdEqualTo(userId).andSrvTxCodeEqualTo(srvTxCode);
		List<BankSmsAuthCode> smsAuthCodeList = bankSmsAuthCodeMapper.selectByExample(example);
		if (smsAuthCodeList != null && smsAuthCodeList.size() == 1) {
			BankSmsAuthCode smsAuthCode = smsAuthCodeList.get(0);
			return smsAuthCode.getSrvAuthCode();
		}
		return null;
	}

	*//**
	 * 保存用户的相应的授权代码
	 * 
	 * @param userId
	 * @param srvTxCode
	 * @param srvAuthCode
	 * @return
	 *//*
	protected boolean updateBankSmsLog(Integer userId, String srvTxCode, String srvAuthCode) {

		Date nowDate = new Date();
		Users user = this.getUsers(userId);
		BankSmsAuthCodeExample example = new BankSmsAuthCodeExample();
		example.createCriteria().andUserIdEqualTo(userId).andSrvTxCodeEqualTo(srvTxCode);
		List<BankSmsAuthCode> smsAuthCodeList = bankSmsAuthCodeMapper.selectByExample(example);
		if (smsAuthCodeList != null && smsAuthCodeList.size() == 1) {
			BankSmsAuthCode smsAuthCode = smsAuthCodeList.get(0);
			smsAuthCode.setSrvAuthCode(srvAuthCode);
			smsAuthCode.setUpdateTime(nowDate);
			smsAuthCode.setUpdateUserId(userId);
			smsAuthCode.setUpdateUserName(user.getUsername());
			boolean smsAuthCodeUpdateFlag = this.bankSmsAuthCodeMapper.updateByPrimaryKeySelective(smsAuthCode) > 0 ? true : false;
			if (smsAuthCodeUpdateFlag) {
				return true;
			} else {
				return false;
			}
		} else {
			this.bankSmsAuthCodeMapper.deleteByExample(example);
			BankSmsAuthCode smsAuthCode = new BankSmsAuthCode();
			smsAuthCode.setUserId(userId);
			smsAuthCode.setSrvTxCode(srvTxCode);
			smsAuthCode.setSrvAuthCode(srvAuthCode);
			smsAuthCode.setStatus(1);
			smsAuthCode.setCreateTime(nowDate);
			smsAuthCode.setCreateUserId(userId);
			smsAuthCode.setCreateUserName(user.getUsername());
			boolean smsAuthCodeInsertFlag = this.bankSmsAuthCodeMapper.insertSelective(smsAuthCode) > 0 ? true : false;
			if (smsAuthCodeInsertFlag) {
				return true;
			} else {
				return false;
			}
		}
	}

	*//**
	 * 保存用户的相应的授权代码
	 * 
	 * @param userId
	 * @param srvTxCode
	 * @param srvAuthCode
	 * @return
	 *//*
	protected boolean updateBankSmsLog(Integer userId, String srvTxCode,String smsSeq, String srvAuthCode) {

		Date nowDate = new Date();
		Users user = this.getUsers(userId);
		BankSmsAuthCodeExample example = new BankSmsAuthCodeExample();
		example.createCriteria().andUserIdEqualTo(userId).andSrvTxCodeEqualTo(srvTxCode);
		List<BankSmsAuthCode> smsAuthCodeList = bankSmsAuthCodeMapper.selectByExample(example);
		if (smsAuthCodeList != null && smsAuthCodeList.size() == 1) {
			BankSmsAuthCode smsAuthCode = smsAuthCodeList.get(0);
			smsAuthCode.setSrvAuthCode(srvAuthCode);
			smsAuthCode.setSmsSeq(smsSeq);
			smsAuthCode.setUpdateTime(nowDate);
			smsAuthCode.setUpdateUserId(userId);
			smsAuthCode.setUpdateUserName(user.getUsername());
			boolean smsAuthCodeUpdateFlag = this.bankSmsAuthCodeMapper.updateByPrimaryKeySelective(smsAuthCode) > 0 ? true : false;
			if (smsAuthCodeUpdateFlag) {
				return true;
			} else {
				return false;
			}
		} else {
			this.bankSmsAuthCodeMapper.deleteByExample(example);
			BankSmsAuthCode smsAuthCode = new BankSmsAuthCode();
			smsAuthCode.setUserId(userId);
			smsAuthCode.setSrvTxCode(srvTxCode);
			smsAuthCode.setSrvAuthCode(srvAuthCode);
			smsAuthCode.setSmsSeq(smsSeq);
			smsAuthCode.setStatus(1);
			smsAuthCode.setCreateTime(nowDate);
			smsAuthCode.setCreateUserId(userId);
			smsAuthCode.setCreateUserName(user.getUsername());
			boolean smsAuthCodeInsertFlag = this.bankSmsAuthCodeMapper.insertSelective(smsAuthCode) > 0 ? true : false;
			if (smsAuthCodeInsertFlag) {
				return true;
			} else {
				return false;
			}
		}
	}

	*//**
	 * 查询检证日志
	 *
	 * @return
	 *//*
	@Override
	public ChinapnrExclusiveLogWithBLOBs selectChinapnrExclusiveLog(long id) {
		return chinapnrExclusiveLogMapper.selectByPrimaryKey(id);
	}

	*//**
	 * 更新检证日志
	 *
	 * @return
	 *//*
	@Override
	public int updateChinapnrExclusiveLog(ChinapnrExclusiveLogWithBLOBs record) {
		ChinapnrExclusiveLogExample example = new ChinapnrExclusiveLogExample();
		example.createCriteria().andIdEqualTo(record.getId()).andUpdatetimeEqualTo(record.getUpdatetime()).andUpdateuserNotEqualTo("callback2");
		record.setUpdatetime(String.valueOf(GetDate.getMyTimeInMillis()));
		record.setUpdateuser("callback2");
		return chinapnrExclusiveLogMapper.updateByExampleSelective(record, example);
	}

	*//**
	 * 更新检证状态
	 *
	 * @return
	 *//*
	public int updateChinapnrExclusiveLogStatus(long uuid, String status) {
		ChinapnrExclusiveLogWithBLOBs record = new ChinapnrExclusiveLogWithBLOBs();
		record.setId(uuid);
		record.setStatus(status);
		return chinapnrExclusiveLogMapper.updateByPrimaryKeySelective(record);
	}

	*//**
	 * 根据汇付账户查询user_id
	 *
	 * @param chinapnrUsrcustid
	 * @return
	 *//*
	public Integer selectUserIdByUsrcustid(Long chinapnrUsrcustid) {
		return this.accountChinapnrCustomizeMapper.selectUserIdByUsrcustid(chinapnrUsrcustid);
	}

	*//**
	 * 根据用户ID取得用户信息
	 *
	 * @param userId
	 * @return
	 *//*
	public Users getUsersByUserId(Integer userId) {
		if (userId != null) {
			UsersExample example = new UsersExample();
			example.createCriteria().andUserIdEqualTo(userId);
			List<Users> usersList = this.usersMapper.selectByExample(example);
			if (usersList != null && usersList.size() > 0) {
				return usersList.get(0);
			}
		}
		return null;
	}

	*//**
	 * 根据用户ID取得用户的推荐人信息
	 * 
	 * @param userId
	 * @return
	 *//*
	public SpreadsUsers getSpreadsUsersByUserId(Integer userId) {
		if (userId != null) {
			SpreadsUsersExample example = new SpreadsUsersExample();
			example.createCriteria().andUserIdEqualTo(userId);
			List<SpreadsUsers> list = this.spreadsUsersMapper.selectByExample(example);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		}
		return null;
	}

	*//**
	 * 获取数据字典名称
	 *
	 * @return
	 *//*
	public String getParamName(String nameClass, String nameCd) {
		ParamNameExample example = new ParamNameExample();
		ParamNameExample.Criteria cra = example.createCriteria();
		cra.andNameClassEqualTo(nameClass);
		cra.andNameCdEqualTo(nameCd);
		cra.andDelFlagEqualTo(CustomConstants.FLAG_NORMAL);
		List<ParamName> paramNameList = this.paramNameMapper.selectByExample(example);
		if (paramNameList != null && paramNameList.size() > 0) {
			return paramNameList.get(0).getName();
		}

		return "";
	}

	*//**
	 * 
	 * 获取链接跳转前缀信息
	 * 
	 * @author hsy
	 * @param userId
	 * @return
	 *//*
	@Override
	public String getLinkJumpPrefix(String version) {
		if (StringUtils.isEmpty(version)) {
			return "hyjf://";
		}

		String pcode = "";
		String vers[] = version.split("\\."); // 取渠道号
		if (vers != null && vers.length > 3) {
			pcode = vers[3];
		}

		if (StringUtils.isEmpty(pcode)) {
			return "hyjf://";
		}

		if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_39)) {
			return "hyjf://";

		} else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_YXB)) {
			return "hyjfYXB://";

		} else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZNB)) {
			return "hyjfZNB://";

		} else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZYB)) {
			return "hyjfZYB://";

		} else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_ZZB)) {
			return "hyjfZZB://";

		} else if (pcode.equals(CustomConstants.MSG_PUSH_PACKAGE_CODE_TEST)) {
			return "hyjfTEST://";

		} else {
			return "hyjf://";
		}

	}

	*//**
	 * 
	 * 特殊字符编码
	 * 
	 * @author renxingchen
	 * @param token
	 * @return
	 * @throws Exception
	 *//*
	public String strEncode(String str) {
		try {
			str = URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	*//***
	 * 获取用户在银行的开户信息
	 */
	@Override
	public  BankOpenAccountVO getBankOpenAccount(Integer userId) {
		BankOpenAccountExample accountExample = new BankOpenAccountExample();
		BankOpenAccountExample.Criteria crt = accountExample.createCriteria();
		crt.andUserIdEqualTo(userId);
		BankOpenAccountVO bankAccount = this.rechargeClient.selectByExample(accountExample);
		return bankAccount;
	}

	/**
	 * 获取短信配置
	 * 
	 * @return
	 * @author Michael
	 *//*

	@Override
	public SmsConfig getSmsConfig() {
		SmsConfigExample example = new SmsConfigExample();
		List<SmsConfig> list = smsConfigMapper.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public UserCouponConfigCustomize getBestCouponById(String couponId) {
		UserCouponConfigCustomize couponConfig = couponConfigCustomizeMapper.getBestCouponById(couponId);
		return couponConfig;
	}

	public BorrowProjectType getProjectTypeByBorrowNid(String borrowNid) {
		BorrowProjectTypeExample example = new BorrowProjectTypeExample();
		BorrowProjectTypeExample.Criteria cra = example.createCriteria();
		cra.andStatusEqualTo(CustomConstants.FLAG_NORMAL).andBorrowClassEqualTo(borrowNid.substring(0, 3));
		List<BorrowProjectType> borrowProjectTypes = this.borrowProjectTypeMapper.selectByExample(example);
		BorrowProjectType borrowProjectType = new BorrowProjectType();
		if (borrowProjectTypes != null && borrowProjectTypes.size() > 0) {
			borrowProjectType = borrowProjectTypes.get(0);
		}
		return borrowProjectType;
	}

	@Override
	public UserCouponConfigCustomize getBestCoupon(String borrowNid, Integer userId, String money, String platform) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		// 查询项目信息
		Borrow borrow = this.getBorrowByNid(borrowNid);

		BorrowProjectType borrowProjectType = getProjectTypeByBorrowNid(borrowNid);
		String style = borrow.getBorrowStyle();
		// 加息券是否启用 0禁用 1启用
		Integer couponFlg = borrow.getBorrowInterestCoupon();
		// 体验金是否启用 0禁用 1启用
		Integer moneyFlg = borrow.getBorrowTasteMoney();
		List<UserCouponConfigCustomize> couponConfigs = couponConfigCustomizeMapper.getCouponConfigList(map);
		// 排序
		Collections.sort(couponConfigs, new ComparatorCouponBean());
		for (UserCouponConfigCustomize userCouponConfigCustomize : couponConfigs) {
			// 验证项目加息券或体验金是否可用
			if (couponFlg != null && couponFlg == 0) {
				if (userCouponConfigCustomize.getCouponType() == 2) {
					continue;
				}
			}
			if (moneyFlg != null && moneyFlg == 0) {
				if (userCouponConfigCustomize.getCouponType() == 1) {
					continue;
				}
			}
			// 验证项目期限、
			Integer type = userCouponConfigCustomize.getProjectExpirationType();
			if ("endday".equals(style)) {
				if (type == 1) {
					if ((userCouponConfigCustomize.getProjectExpirationLength() * 30) != borrow.getBorrowPeriod()) {
						continue;
					}
				} else if (type == 3) {
					if ((userCouponConfigCustomize.getProjectExpirationLength() * 30) > borrow.getBorrowPeriod()) {
						continue;
					}
				} else if (type == 4) {
					if ((userCouponConfigCustomize.getProjectExpirationLength() * 30) < borrow.getBorrowPeriod()) {
						continue;
					}
				} else if (type == 2) {
					if ((userCouponConfigCustomize.getProjectExpirationLengthMin() * 30) > borrow.getBorrowPeriod()
							|| (userCouponConfigCustomize.getProjectExpirationLengthMax() * 30) < borrow.getBorrowPeriod()) {
						continue;
					}
				}
			} else {
				if (type == 1) {
					if (userCouponConfigCustomize.getProjectExpirationLength() != borrow.getBorrowPeriod()) {
						continue;
					}
				} else if (type == 3) {
					if (userCouponConfigCustomize.getProjectExpirationLength() > borrow.getBorrowPeriod()) {
						continue;
					}
				} else if (type == 4) {
					if (userCouponConfigCustomize.getProjectExpirationLength() < borrow.getBorrowPeriod()) {
						continue;
					}
				} else if (type == 2) {
					if (userCouponConfigCustomize.getProjectExpirationLengthMin() > borrow.getBorrowPeriod() || userCouponConfigCustomize.getProjectExpirationLengthMax() < borrow.getBorrowPeriod()) {
						continue;
					}
				}
			}
			// 验证项目金额
			Integer tenderQuota = userCouponConfigCustomize.getTenderQuotaType();
			if (tenderQuota == 1) {
				if (userCouponConfigCustomize.getTenderQuotaMin() > new Double(money) || userCouponConfigCustomize.getTenderQuotaMax() < new Double(money)) {
					continue;
				}
			} else if (tenderQuota == 2) {
				if (userCouponConfigCustomize.getTenderQuota() > new Double(money)) {
					continue;
				}
			}
			// 验证优惠券适用的项目 新逻辑 pcc20160715
			String projectType = userCouponConfigCustomize.getProjectType();
			boolean ifprojectType = true;
			if (projectType.indexOf("-1") != -1) {
				if (!"RTB".equals(borrowProjectType.getBorrowClass())) {
					ifprojectType = false;
				}
			} else {
				if ("HXF".equals(borrowProjectType.getBorrowClass())) {
					if (projectType.indexOf("2") != -1) {
						ifprojectType = false;
					}
				} else if ("NEW".equals(borrowProjectType.getBorrowClass())) {
					if (projectType.indexOf("3") != -1) {
						ifprojectType = false;
					}
				} else if ("ZXH".equals(borrowProjectType.getBorrowClass())) {
					if (projectType.indexOf("4") != -1) {
						ifprojectType = false;
					}
				} else {
					if (projectType.indexOf("1") != -1) {
						if (!"RTB".equals(borrowProjectType.getBorrowClass())) {
							ifprojectType = false;
						}
					}
				}
			}
			if (ifprojectType) {
				continue;
			}
			
			*//**************逻辑修改 pcc start***************//*
            //是否与本金公用
            boolean addFlg = false;
            if(userCouponConfigCustomize.getAddFlg()==1&&!"0".equals(money)){
                addFlg = true;
            }
            if(addFlg){
                continue;
            }
            *//**************逻辑修改 pcc end***************//*
			
			
			// 验证使用平台
			String couponSystem = userCouponConfigCustomize.getCouponSystem();
			String[] couponSystemArr = couponSystem.split(",");
			for (String couponSystemString : couponSystemArr) {
				if ("-1".equals(couponSystemString)) {
					return userCouponConfigCustomize;
				}
				if (platform.equals(couponSystemString)) {
					return userCouponConfigCustomize;
				}
			}
		}
		return null;
	}

	public BorrowWithBLOBs getBorrowByNid(String borrowNid) {
		BorrowExample example = new BorrowExample();
		BorrowExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		List<BorrowWithBLOBs> list = borrowMapper.selectByExampleWithBLOBs(example);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	*//**
	 * 根据借款Id检索标的信息
	 * 
	 * @param borrowId
	 * @return
	 *//*
	@Override
	public BorrowWithBLOBs selectBorrowById(int borrowId) {
		BorrowExample example = new BorrowExample();
		BorrowExample.Criteria cra = example.createCriteria();
		cra.andIdEqualTo(borrowId);
		List<BorrowWithBLOBs> result = this.borrowMapper.selectByExampleWithBLOBs(example);
		if (result != null && result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	public class ComparatorCouponBean implements Comparator<UserCouponConfigCustomize> {

		@Override
		public int compare(UserCouponConfigCustomize couponBean1, UserCouponConfigCustomize couponBean2) {
			if (1 == couponBean1.getCouponType()) {
				couponBean1.setCouponType(4);
			}
			if (1 == couponBean2.getCouponType()) {
				couponBean2.setCouponType(4);
			}
			int flag = couponBean1.getCouponType() - couponBean2.getCouponType();
			if (4 == couponBean1.getCouponType()) {
				couponBean1.setCouponType(1);
			}
			if (4 == couponBean2.getCouponType()) {
				couponBean2.setCouponType(1);
			}
			return flag;
		}
	}

	*//**
	 * 根据电子账号查询用户在江西银行的可用余额
	 * 
	 * @param accountId
	 * @return
	 *//*
	@Override
	public BigDecimal getBankBalance(Integer userId, String accountId) {
		// 账户可用余额
		BigDecimal balance = BigDecimal.ZERO;
		BankCallBean bean = new BankCallBean();
		bean.setVersion(BankCallConstant.VERSION_10);// 版本号
		bean.setTxCode(BankCallMethodConstant.TXCODE_BALANCE_QUERY);// 交易代码
		bean.setTxDate(GetOrderIdUtils.getTxDate()); // 交易日期
		bean.setTxTime(GetOrderIdUtils.getTxTime()); // 交易时间
		bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号
		bean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
		bean.setAccountId(accountId);// 电子账号
		bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));// 订单号
		bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
		bean.setLogUserId(String.valueOf(userId));
		bean.setLogRemark("电子账户余额查询");
		bean.setLogClient(0);// 平台
		try {
			BankCallBean resultBean = BankCallUtils.callApiBg(bean);
			if (resultBean != null && BankCallStatusConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
				balance = new BigDecimal(resultBean.getAvailBal().replace(",", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return balance;
	}

	@Override
	public String getBankRetMsg(String retCode) {
		//如果错误码不为空
		if (StringUtils.isNotBlank(retCode)) {
			BankReturnCodeConfigExample example = new BankReturnCodeConfigExample();
			example.createCriteria().andRetCodeEqualTo(retCode);
			List<BankReturnCodeConfig> retCodes = this.bankReturnCodeConfigMapper.selectByExample(example);
			if (retCodes != null && retCodes.size() == 1) {
				String retMsg = retCodes.get(0).getErrorMsg();
				if (StringUtils.isNotBlank(retMsg)) {
					return retMsg;
				} else {
					return "请联系客服！";
				}
			} else {
				return "请联系客服！";
			}
		} else {
			return "操作失败！";
		}
	}
	
	
	*//**
	 * 根据银行卡号查询银行ID
	 * 
	 * @param cardNo
	 * @return
	 *//*
	@Override
	public String getBankIdByCardNo(String cardNo) {
		String bankId = null;
		if (cardNo == null || cardNo.length() < 14 || cardNo.length() > 19) {
			return "";
		}
		// 把常用的卡BIN放到最前面
		// 6位卡BIN
		String cardBin_6 = cardNo.substring(0, 6);
		bankId = this.getBankId(6, cardBin_6);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 7位卡BIN
		String cardBin_7 = cardNo.substring(0, 7);
		bankId = this.getBankId(7, cardBin_7);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 8位卡BIN
		String cardBin_8 = cardNo.substring(0, 8);
		bankId = this.getBankId(8, cardBin_8);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 9位卡BIN
		String cardBin_9 = cardNo.substring(0, 9);
		bankId = this.getBankId(9, cardBin_9);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 2位卡BIN
		String cardBin_2 = cardNo.substring(0, 2);
		bankId = this.getBankId(2, cardBin_2);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 3位卡BIN
		String cardBin_3 = cardNo.substring(0, 3);
		bankId = this.getBankId(3, cardBin_3);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 4位卡BIN
		String cardBin_4 = cardNo.substring(0, 4);
		bankId = this.getBankId(4, cardBin_4);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 5位卡BIN
		String cardBin_5 = cardNo.substring(0, 5);
		bankId = this.getBankId(5, cardBin_5);
		if (StringUtils.isNotBlank(bankId)) {
			return bankId;
		}
		// 10位卡BIN
		String cardBin_10 = cardNo.substring(0, 10);
		bankId = this.getBankId(10, cardBin_10);
		if (StringUtils.isNotBlank(cardBin_10)) {
			return bankId;
		}
		return bankId;
	}

	private String getBankId(int cardBinLength, String cardBin) {
		CardBinExample example = new CardBinExample();
		CardBinExample.Criteria cra = example.createCriteria();
		cra.andBinLengthEqualTo(cardBinLength);
		cra.andBinValueEqualTo(cardBin);
		List<CardBin> list = this.cardBinMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0).getBankId();
		}
		return null;
	}

	*//**
	 * 根据银行Id查询所属银行名称
	 * 
	 * @param bankId
	 * @return
	 *//*
	@Override
	public String getBankNameById(String bankId) {
		BanksConfig bankConfig = banksConfigMapper.selectByPrimaryKey(Integer.parseInt(bankId));
		if (bankConfig != null) {
			return bankConfig.getBankName();
		}
		return null;
	}
	
	*//**
	 * 调用江西银行查询联行号
	 * @param cardNo
	 * @return
	 *//*
	@Override
	public BankCallBean payAllianceCodeQuery(String cardNo,Integer userId) {
		BankCallBean bean = new BankCallBean();
		String channel = BankCallConstant.CHANNEL_PC;
		String orderDate = GetOrderIdUtils.getOrderDate();
		String txDate = GetOrderIdUtils.getTxDate();
		String txTime = GetOrderIdUtils.getTxTime();
		String seqNo = GetOrderIdUtils.getSeqNo(6);
		bean.setVersion(BankCallConstant.VERSION_10);// 版本号
		bean.setTxCode(BankCallConstant.TXCODE_PAY_ALLIANCE_CODE_QUERY);// 交易代码
		bean.setTxDate(txDate);
		bean.setTxTime(txTime);
		bean.setSeqNo(seqNo);
		bean.setChannel(channel);
		bean.setAccountId(cardNo);
		bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
		bean.setLogOrderDate(orderDate);
		bean.setLogUserId(String.valueOf(userId));
		bean.setLogRemark("联行号查询");
		bean.setLogClient(0);
		return BankCallUtils.callApiBg(bean);
	}

	*//**
	 * 根据银行Id查询本地存的银联行号
	 * @param bankId
	 * @return
	 *//*
	@Override
	public String getPayAllianceCodeByBankId(String bankId) {
		BanksConfig bankConfig = banksConfigMapper.selectByPrimaryKey(Integer.parseInt(bankId));
		if (bankConfig != null) {
			return bankConfig.getPayAllianceCode();
		}
		return null;
	}
	
	*//**
     * 
     * 根据电子账户查询开户信息
     * @author pcc
     * @param bankAccount
     * @return
     *//*
	@Override
    public BankOpenAccount getBankOpenAccount(String bankAccount) {
        BankOpenAccountExample accountExample = new BankOpenAccountExample();
        BankOpenAccountExample.Criteria crt = accountExample.createCriteria();
        crt.andAccountEqualTo(bankAccount);
        List<BankOpenAccount> bankAccounts = this.bankOpenAccountMapper.selectByExample(accountExample);
        if (bankAccounts != null && bankAccounts.size() == 1) {
            return bankAccounts.get(0);
        }
        return null;
    }
	
	*//**
	 * 获取银行卡配置信息
	 *//*
	@Override
	public BanksConfig getBanksConfigByBankId(Integer bankId) {
		if(bankId == null){
			return null;
		}
		
		BanksConfigExample example = new BanksConfigExample();
		example.createCriteria().andIdEqualTo(bankId).andDelFlgEqualTo(0);
		
		List<BanksConfig> banksConfigList = banksConfigMapper.selectByExample(example);
		
		if(banksConfigList != null && !banksConfigList.isEmpty()){
			return banksConfigList.get(0);
		}
		return null;
	}
	
	 *//**
     * 
     * 获取用户注册时的渠道名称
     * @author hsy
     * @param userId
     * @return
     *//*
    public String getChannelNameByUserId(Integer userId){
        
        String channelName = channelCustomizeMapper.selectChannelNameByUserId(userId);
        
        return channelName;
    }
    
    *//**
     * 根据用户名获取用户
     * @param userName
     * @return
     *//*
    @Override
	public Users getUserByUserName(String userName){
    	if(StringUtils.isEmpty(userName)){
    		return null;
    	}
    	
    	UsersExample example = new UsersExample();
    	example.createCriteria().andUsernameEqualTo(userName);
    	List<Users> userList = usersMapper.selectByExample(example);
    	
    	if(userList != null && userList.size() == 1){
    		return userList.get(0);
    	}
    	
    	return null;
    }
    
    *//**
     * 查询活动有效性 0：活动不存在 1：活动未开始 2：活动已结束 3：活动进行中
     * @param activityId
     * @return
     *//*
    @Override
	public Integer checkActivityStatus(String activityId) {
        if(activityId==null){
            return 0;
        }
        ActivityList activityList=activityListMapper.selectByPrimaryKey(new Integer(activityId));
        if(activityList==null){
            return 0;
        }
        if(activityList.getTimeStart()>GetDate.getNowTime10()){
            return 1;
        }
        if(activityList.getTimeEnd()<GetDate.getNowTime10()){
            return 2;
        }
        return 3;
    }
    
	*//**
	 * 通过账户信息获取用户信息
	 * @param accountId
	 * @return
	 * @author PC-LIUSHOUYI
	 *//*
		
	@Override
	public String getUserNameByAccount(String accountId) {
		Map<String, Object> param = new HashMap<String, Object>();
		// 原交易流水号
		param.put("accountId", accountId);
		String userName = this.manualReverseCustomizeMapper.selectUserNamebyAccountId(param);
		return userName;
	}

	*//**
	 * 根据用户Id查询用户CA认证相关信息
	 *
	 * @param userId
	 * @return
	 *//*
	@Override
	public CertificateAuthority selectCAInfoByUserId(Integer userId) {
		CertificateAuthorityExample example = new CertificateAuthorityExample();
		CertificateAuthorityExample.Criteria cra = example.createCriteria();
		cra.andUserIdEqualTo(userId);
		List<CertificateAuthority> list = this.certificateAuthorityMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

    *//**
     * 检测用户是否开通缴费授权
     * @param userId
     * @return 0 成功，1 未授权，2 超过授权时间
     *//*
    @Override
    public Integer checkPaymentAuth(Integer userId) {
    	// 默认未授权
    	Integer result = 1 ;
    	
    	if(userId == null) {
    		return result;
    	}
    	
    	HjhUserAuth hjhUserAuth = null;
    	HjhUserAuthExample example = new HjhUserAuthExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<HjhUserAuth> list = hjhUserAuthMapper.selectByExample(example);
		
		if (list != null && list.size() > 0) {
			hjhUserAuth = list.get(0);
		} else {
			_log.info("用户没有授权记录 "+userId);
			return result;
		}

        // 缴费授权
		if(hjhUserAuth == null || hjhUserAuth.getAutoPaymentStatus() == null || 
				hjhUserAuth.getAutoPaymentStatus().intValue() != 1) {
			return result;
		}
		
		try {
	        String nowTime = GetDate.date2Str(new Date(),GetDate.yyyyMMdd);
	        String endTime = hjhUserAuth.getAutoPaymentEndTime();
	        // 超过授权时间
	        if (Integer.parseInt(endTime) - Integer.parseInt(nowTime) < 0) {
	            return 2;
	        }
	        
	        // 成功
	        return 0;
			
		} catch (Exception e) {
			_log.error(e.getMessage());
			return result;
		}
    }

    @Override
    public HjhUserAuth getHjhUserAuthByUserId(Integer userId) {
        HjhUserAuth hjhUserAuth = null;
        HjhUserAuthExample example = new HjhUserAuthExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<HjhUserAuth> list = hjhUserAuthMapper.selectByExample(example);
        
        if (list != null && list.size() > 0) {
            hjhUserAuth = list.get(0);
        } 
        return hjhUserAuth;
    }

	@Override
	public Borrow getBorrowByBorrowNid(String borrowNid) {
		BorrowExample example = new BorrowExample();
		BorrowExample.Criteria criteria = example.createCriteria();
		criteria.andBorrowNidEqualTo(borrowNid);
		List<BorrowWithBLOBs> list = borrowMapper.selectByExampleWithBLOBs(example);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}*/
}
