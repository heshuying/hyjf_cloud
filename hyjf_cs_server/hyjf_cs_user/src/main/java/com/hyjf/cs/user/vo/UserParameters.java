package com.hyjf.cs.user.vo;

/**
 * 用户属性Parameters模型
 * 
 * @author
 * @since 2016年2月3日 上午8:40:44
 */
public class UserParameters {
	private String iconUrl = "";// -- 头像的url,hyjf_users表的iconUrl字段
	private String nickname = "";// -- 用户昵称,hyjf_usersinfo表的nickname字段
	private String balance = "";// -- 可用余额,huiyingdai_account表的balance字段
	private String htlDescription = "";// -- 我的汇天利后面的文字,AppUserDefine中写死
	private String bankCardCount = "";// --
										// 银行卡张数,huiyingdai_account_bank表中用户的卡的个数
	private String openAccount = "";// --
									// 是否开户(0未开户,1已开户),huiyingdai_users表的open_account字段
	private String huifuOpenAccountUrl = "";// -- 汇付天下开户url,王坤给
	private String isBindQuickPayment = "";// --
											// 是否绑定快捷支付,huiyingdai_account_bank是否存在card_type=2的卡
	private String huifuBindBankCardUrl = "";// 汇付天下绑定银行卡url,待修改,不知道从哪来
	private String bankCardAccount = "";// --
										// 银行卡开户行,huiyingdai_account_bank只有用户存在快捷卡时才存在
	private String bankCardAccountLogoUrl = "";// --
												// 银行卡开户行logo的url,huiyingdai_bank_config表,huiyingdai_account_bank只有用户存在快捷卡时才存在
	private String bankCardCode = "";// --
										// 银行卡卡号,huiyingdai_account_bank只有用户存在快捷卡时才存在
	private String username = "";// -- 用户名,huiyingdai_users表的username字段
	private String mobile = "";// -- 手机号,huiyingdai_users表的mobile字段
	private String rl_name = "";// -- 紧急联系人姓名huiyingdai_users_contact表的rl_name字段
	private String rl_phone = "";// --
									// 紧急联系人电话huiyingdai_users_contact表的rl_phone字段
	private String relation = "";// --
									// 紧急联系人关系huiyingdai_users_contact表的relation字段
	private String qrCodeUrl = "";// 二维码url,找国玺要
	private String reffer = "";// -- 邀请码(推荐人),huiyingdai_users表的reffer字段
	private String chinapnrUsrcustid = "";// --
											// 汇付天下账号,huiyingdai_account_chinapnr表的chinapnr_usrcustid字段
	private String truename = "";// -- 真实姓名,huiyingdai_users_info表的truename字段
									// truename
	private String idcard = "";// -- 身份证号,huiyingdai_users_info表的idcard字段
	private String startOrStopPush = "";// --
										// 是否开启推送服务(0：不接收，1：接收),hyjf_users表的if_receive_notice字段
	private String answerStatus = "";// 是否进行过风险测评(0未答题,1已答题),huiyingdai_user_evalation_result表是否存在数据
	private String answerResult = "";// 风险测评结果类型(答题结果),huiyingdai_user_evalation_result表的type字段,只有进行过风险测评才有该值
	private String evalationSummary = "";// 风险测评结果字符串,huiyingdai_user_evalation_result表的summary字段,只有进行过风险测评才有该值
	private String evalationScore = "";// 风险测评得分,huiyingdai_user_evalation_result表的score_count字段,只有进行过风险测评才有该值
	private String answerUrl = "";// 答题url地址

	private String readFlag; // 优惠券更新标识

	private String couponDescription = "福利多多哒"; // 优惠券描述

	private String isUpdate; // 我的账户更新标识(0未更新，1已更新)

	private String isVip; // 用户是否是vip(0不是vip，1是vip)

	private String vipPictureUrl; // vip名称图片路径

	private String vipLevel; // vip等级图片路径

	private String vipJumpUrl; // vip按钮跳转路径

	private String accountTotle; // 账户总资产

	private String waitInterest; // 待收收益

	private String interestTotle; // 累计收益

	// 邮箱红点显示
	private String redFlag;
	private String setupPassword;// 是否设置交易密码
	private String changeTradePasswordUrl;// 设置交易密码url

	private String huifuBalance;// 汇付账户余额
	private String jiangxiAccount;// 江西银行存管账户
	private String openAccountUrl;// 江西银行开户url
	private String bindBankCardUrl; // 江西银行绑卡url
	private String jiangxiBindBankStatus;// 江西银行绑卡标志
	private String huifuOpenAccount;// 汇付开户标示
	private String userType; // 是否是企业用户
	private String isNewHand; //是否新手（0代表不是新手  1代表是新手）
	
	private String autoInvesStatus; //自动投标授权状态 0: 未授权    1:已授权
	private String autoInvesUrl; //自动投标授权URL


	// add by yaoyong 20171204 app2.1改版 begin
	private String planDesc; //我的计划描述对应表中计划累计待收总额

	private String borrowDesc; //我的散标描述对应表中银行待收总额

	private String awaitTotal; //待收总额

	private String rewardDesc; //我的奖励

	private String bindMobile; //是否绑定手机号

	private String infoDesc;//实名描述

	private String HuifuDesc; //汇付天下账户描述

	private String JiangxiDesc; //江西银行账户描述

	private String fengxianDesc; //风险评估描述

	private String toubiaoDesc; //自动投标描述

	private Integer invitationCode; //邀请码

	private String smsOpenStatus; //开启发送短信状态

	private String emailOpenStatus; //开启发送邮件状态

	private String rewardUrl;

	private String newAutoInvesStatus; //自动投标授权状态 0: 未授权    1:已授权

	private String newAutoCreditStatus; //自动债转授权状态 0: 未授权    1:已授权
	// add by yaoyong 20171204 app2.1改版 end
	// 用户角色：1出借人2借款人3担保机构
	private String roleId;
	// 缴费授权状态  0: 未授权    1:已授权
	private String paymentAuthStatus;
	// 缴费授权Url
	private String paymentAuthUrl;
	// 三合一授权Url
	private String mergeAuthUrl;
	// 自动出借授权是否开启  0未开启  1已开启
	private String invesAuthOn;
	// 自动债转费授权是否开启  0未开启  1已开启
	private String creditAuthOn;
	// 服务费授权是否开启  0未开启  1已开启
	private String paymentAuthOn;
	// 还款授权状态  0: 未授权    1:已授权
	private String repayStatus;
	// 还款授权Url
	private String repayAuthUrl;
	// 是否校验用户角色
	private String isCheckUserRole;
	// 校验用户角色的描述
	private String checkUserRoleDesc;
	/**
	 * 三合一授权固定消息
	 */
	private String mergeAuthDesc = "自动投标，自动债转，服务费授权。";
	//三合一授权过期标识0未授权，1正常，2即将过期，3已过期
	private String mergeAuthExpire;
	//缴费授权过期标识0未授权，1正常，2即将过期，3已过期
	private String paymentAuthExpire;
	/**
	 *二合一授权过期标识（服务费授权、还款授权） 0未授权，1正常，2即将过期，3已过期
	 */
	private String payRepayAuthExpire;

	public String getPayRepayAuthExpire() {
		return payRepayAuthExpire;
	}

	public void setPayRepayAuthExpire(String payRepayAuthExpire) {
		this.payRepayAuthExpire = payRepayAuthExpire;
	}
	/**
	 * 缴费授权固定消息
	 */
	private String paymentAuthDesc = "部分交易过程中，会收取相应费用，请进行授权。\n例如：提现手续费，债转服务费等。";
	// add by pcc app3.1.1追加 20180823 start
	// 我的计划列表退出中标签显示标识（临时使用，功能上线以后可以删除）
	private String exitLabelShowFlag;

	// 用户ID
	private String userId;

	/** 积分商城用：用户当前积分 */
	private Integer pointsCurrent;
	/** 积分商城用：前端积分明细H5url */
	private String pointsDetailUrl;

	public String getHuifuOpenAccount() {
		return huifuOpenAccount;
	}

	public void setHuifuOpenAccount(String huifuOpenAccount) {
		this.huifuOpenAccount = huifuOpenAccount;
	}

	public String getJiangxiBindBankStatus() {
		return jiangxiBindBankStatus;
	}

	public void setJiangxiBindBankStatus(String jiangxiBindBankStatus) {
		this.jiangxiBindBankStatus = jiangxiBindBankStatus;
	}

	public String getIconUrl() {
		if (iconUrl == null) {
			iconUrl = "";
		}
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getNickname() {
		if (nickname == null) {
			nickname = "";
		}
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getBalance() {
		if (balance == null) {
			balance = "";
		}
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getHtlDescription() {
		if (htlDescription == null) {
			htlDescription = "";
		}
		return htlDescription;
	}

	public void setHtlDescription(String htlDescription) {
		this.htlDescription = htlDescription;
	}

	public String getBankCardCount() {
		if (bankCardCount == null) {
			bankCardCount = "";
		}
		return bankCardCount;
	}

	public void setBankCardCount(String bankCardCount) {
		this.bankCardCount = bankCardCount;
	}

	public String getOpenAccount() {
		if (openAccount == null) {
			openAccount = "";
		}
		return openAccount;
	}

	public void setOpenAccount(String openAccount) {
		this.openAccount = openAccount;
	}

	public String getHuifuOpenAccountUrl() {
		if (huifuOpenAccountUrl == null) {
			huifuOpenAccountUrl = "";
		}
		return huifuOpenAccountUrl;
	}

	public void setHuifuOpenAccountUrl(String huifuOpenAccountUrl) {
		this.huifuOpenAccountUrl = huifuOpenAccountUrl;
	}

	public String getIsBindQuickPayment() {
		if (isBindQuickPayment == null) {
			isBindQuickPayment = "";
		}
		return isBindQuickPayment;
	}

	public void setIsBindQuickPayment(String isBindQuickPayment) {
		this.isBindQuickPayment = isBindQuickPayment;
	}

	public String getHuifuBindBankCardUrl() {
		if (huifuBindBankCardUrl == null) {
			huifuBindBankCardUrl = "";
		}
		return huifuBindBankCardUrl;
	}

	public void setHuifuBindBankCardUrl(String huifuBindBankCardUrl) {
		this.huifuBindBankCardUrl = huifuBindBankCardUrl;
	}

	public String getBankCardAccount() {
		if (bankCardAccount == null) {
			bankCardAccount = "";
		}
		return bankCardAccount;
	}

	public void setBankCardAccount(String bankCardAccount) {
		this.bankCardAccount = bankCardAccount;
	}

	public String getBankCardAccountLogoUrl() {
		if (bankCardAccountLogoUrl == null) {
			bankCardAccountLogoUrl = "";
		}
		return bankCardAccountLogoUrl;
	}

	public void setBankCardAccountLogoUrl(String bankCardAccountLogoUrl) {
		this.bankCardAccountLogoUrl = bankCardAccountLogoUrl;
	}

	public String getBankCardCode() {
		if (bankCardCode == null) {
			bankCardCode = "";
		}
		return bankCardCode;
	}

	public void setBankCardCode(String bankCardCode) {
		this.bankCardCode = bankCardCode;
	}

	public String getUsername() {
		if (username == null) {
			username = "";
		}
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		if (mobile == null) {
			mobile = "";
		}
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRl_name() {
		if (rl_name == null) {
			rl_name = "";
		}
		return rl_name;
	}

	public void setRl_name(String rl_name) {
		this.rl_name = rl_name;
	}

	public String getRl_phone() {
		if (rl_phone == null) {
			rl_phone = "";
		}
		return rl_phone;
	}

	public void setRl_phone(String rl_phone) {
		this.rl_phone = rl_phone;
	}

	public String getRelation() {
		if (relation == null) {
			relation = "";
		}
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getQrCodeUrl() {
		if (qrCodeUrl == null) {
			qrCodeUrl = "";
		}
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public String getReffer() {
		if (reffer == null) {
			reffer = "";
		}
		return reffer;
	}

	public void setReffer(String reffer) {
		this.reffer = reffer;
	}

	public String getChinapnrUsrcustid() {
		if (chinapnrUsrcustid == null) {
			chinapnrUsrcustid = "";
		}
		return chinapnrUsrcustid;
	}

	public void setChinapnrUsrcustid(String chinapnrUsrcustid) {
		this.chinapnrUsrcustid = chinapnrUsrcustid;
	}

	public String getTruename() {
		if (truename == null) {
			truename = "";
		}
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getIdcard() {
		if (idcard == null) {
			idcard = "";
		}
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getStartOrStopPush() {
		if (startOrStopPush == null) {
			startOrStopPush = "";
		}
		return startOrStopPush;
	}

	public void setStartOrStopPush(String startOrStopPush) {
		this.startOrStopPush = startOrStopPush;
	}

	public String getAnswerStatus() {
		if (answerStatus == null) {
			return answerStatus;
		}
		return answerStatus;
	}

	public void setAnswerStatus(String answerStatus) {
		this.answerStatus = answerStatus;
	}

	public String getAnswerResult() {
		if (answerResult == null) {
			return answerResult;
		}
		return answerResult;
	}

	public void setAnswerResult(String answerResult) {
		this.answerResult = answerResult;
	}

	public String getEvalationSummary() {
		if (evalationSummary == null) {
			return evalationSummary;
		}
		return evalationSummary;
	}

	public void setEvalationSummary(String evalationSummary) {
		this.evalationSummary = evalationSummary;
	}

	public String getEvalationScore() {
		if (evalationScore == null) {
			return evalationScore;
		}
		return evalationScore;
	}

	public void setEvalationScore(String evalationScore) {
		this.evalationScore = evalationScore;
	}

	public String getAnswerUrl() {
		if (answerUrl == null) {
			return answerUrl;
		}
		return answerUrl;
	}

	public void setAnswerUrl(String answerUrl) {
		this.answerUrl = answerUrl;
	}

	public String getReadFlag() {
		if (readFlag == null) {
			readFlag = "";
		}
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	public String getCouponDescription() {
		if (couponDescription == null) {
			couponDescription = "";
		}
		return couponDescription;
	}

	public void setCouponDescription(String couponDescription) {
		this.couponDescription = couponDescription;
	}

	public String getIsUpdate() {
		if (isUpdate == null) {
			isUpdate = "";
		}
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}

	public String getIsVip() {
		return isVip;
	}

	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}

	public String getVipPictureUrl() {
		return vipPictureUrl;
	}

	public void setVipPictureUrl(String vipPictureUrl) {
		this.vipPictureUrl = vipPictureUrl;
	}

	public String getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(String vipLevel) {
		this.vipLevel = vipLevel;
	}

	public String getVipJumpUrl() {
		return vipJumpUrl;
	}

	public void setVipJumpUrl(String vipJumpUrl) {
		this.vipJumpUrl = vipJumpUrl;
	}

	public String getAccountTotle() {
		return accountTotle;
	}

	public void setAccountTotle(String accountTotle) {
		this.accountTotle = accountTotle;
	}

	public String getWaitInterest() {
		return waitInterest;
	}

	public void setWaitInterest(String waitInterest) {
		this.waitInterest = waitInterest;
	}

	public String getInterestTotle() {
		return interestTotle;
	}

	public void setInterestTotle(String interestTotle) {
		this.interestTotle = interestTotle;
	}

	public String getRedFlag() {
		return redFlag;
	}

	public void setRedFlag(String redFlag) {
		this.redFlag = redFlag;
	}

	public String getSetupPassword() {
		return setupPassword;
	}

	public void setSetupPassword(String setupPassword) {
		this.setupPassword = setupPassword;
	}

	public String getChangeTradePasswordUrl() {
		return changeTradePasswordUrl;
	}

	public void setChangeTradePasswordUrl(String changeTradePasswordUrl) {
		this.changeTradePasswordUrl = changeTradePasswordUrl;
	}

	public String getOpenAccountUrl() {
		return openAccountUrl;
	}

	public void setOpenAccountUrl(String openAccountUrl) {
		this.openAccountUrl = openAccountUrl;
	}

	public String getBindBankCardUrl() {
		return bindBankCardUrl;
	}

	public void setBindBankCardUrl(String bindBankCardUrl) {
		this.bindBankCardUrl = bindBankCardUrl;
	}

	public String getHuifuBalance() {
		return huifuBalance;
	}

	public void setHuifuBalance(String huifuBalance) {
		this.huifuBalance = huifuBalance;
	}

	public String getJiangxiAccount() {
		return jiangxiAccount;
	}

	public void setJiangxiAccount(String jiangxiAccount) {
		this.jiangxiAccount = jiangxiAccount;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

    public String getIsNewHand() {
        return isNewHand;
    }

    public void setIsNewHand(String isNewHand) {
        this.isNewHand = isNewHand;
    }

	public String getAutoInvesStatus() {
		return autoInvesStatus;
	}

	public void setAutoInvesStatus(String autoInvesStatus) {
		this.autoInvesStatus = autoInvesStatus;
	}

	public String getAutoInvesUrl() {
		return autoInvesUrl;
	}

	public void setAutoInvesUrl(String autoInvesUrl) {
		this.autoInvesUrl = autoInvesUrl;
	}

	public String getPlanDesc() {
		return planDesc;
	}

	public void setPlanDesc(String planDesc) {
		this.planDesc = planDesc;
	}

	public String getBorrowDesc() {
		return borrowDesc;
	}

	public void setBorrowDesc(String borrowDesc) {
		this.borrowDesc = borrowDesc;
	}

	public String getRewardDesc() {
		return rewardDesc;
	}

	public void setRewardDesc(String rewardDesc) {
		this.rewardDesc = rewardDesc;
	}

	public String getBindMobile() {
		return bindMobile;
	}

	public void setBindMobile(String bindMobile) {
		this.bindMobile = bindMobile;
	}

	public String getInfoDesc() {
		return infoDesc;
	}

	public void setInfoDesc(String infoDesc) {
		this.infoDesc = infoDesc;
	}

	public String getHuifuDesc() {
		return HuifuDesc;
	}

	public void setHuifuDesc(String huifuDesc) {
		HuifuDesc = huifuDesc;
	}

	public String getJiangxiDesc() {
		return JiangxiDesc;
	}

	public void setJiangxiDesc(String jiangxiDesc) {
		JiangxiDesc = jiangxiDesc;
	}

	public String getFengxianDesc() {
		return fengxianDesc;
	}

	public void setFengxianDesc(String fengxianDesc) {
		this.fengxianDesc = fengxianDesc;
	}

	public String getToubiaoDesc() {
		return toubiaoDesc;
	}

	public void setToubiaoDesc(String toubiaoDesc) {
		this.toubiaoDesc = toubiaoDesc;
	}

	public Integer getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(Integer invitationCode) {
		this.invitationCode = invitationCode;
	}

	public String getSmsOpenStatus() {
		return smsOpenStatus;
	}

	public void setSmsOpenStatus(String smsOpenStatus) {
		this.smsOpenStatus = smsOpenStatus;
	}

	public String getEmailOpenStatus() {
		return emailOpenStatus;
	}

	public void setEmailOpenStatus(String emailOpenStatus) {
		this.emailOpenStatus = emailOpenStatus;
	}

	public String getAwaitTotal() {
		return awaitTotal;
	}

	public void setAwaitTotal(String awaitTotal) {
		this.awaitTotal = awaitTotal;
	}

	public String getRewardUrl() {
		return rewardUrl;
	}

	public void setRewardUrl(String rewardUrl) {
		this.rewardUrl = rewardUrl;
	}

	public String getNewAutoInvesStatus() {
		return newAutoInvesStatus;
	}

	public void setNewAutoInvesStatus(String newAutoInvesStatus) {
		this.newAutoInvesStatus = newAutoInvesStatus;
	}

	public String getNewAutoCreditStatus() {
		return newAutoCreditStatus;
	}

	public void setNewAutoCreditStatus(String newAutoCreditStatus) {
		this.newAutoCreditStatus = newAutoCreditStatus;
	}

	public String getPaymentAuthStatus() {
		return paymentAuthStatus;
	}

	public void setPaymentAuthStatus(String paymentAuthStatus) {
		this.paymentAuthStatus = paymentAuthStatus;
	}

	public String getPaymentAuthUrl() {
		return paymentAuthUrl;
	}

	public void setPaymentAuthUrl(String paymentAuthUrl) {
		this.paymentAuthUrl = paymentAuthUrl;
	}

	public String getPaymentAuthDesc() {
		return paymentAuthDesc;
	}

	public void setPaymentAuthDesc(String paymentAuthDesc) {
		this.paymentAuthDesc = paymentAuthDesc;
	}

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

	public String getMergeAuthUrl() {
		return mergeAuthUrl;
	}

	public void setMergeAuthUrl(String mergeAuthUrl) {
		this.mergeAuthUrl = mergeAuthUrl;
	}

	public String getInvesAuthOn() {
		return invesAuthOn;
	}

	public void setInvesAuthOn(String invesAuthOn) {
		this.invesAuthOn = invesAuthOn;
	}

	public String getCreditAuthOn() {
		return creditAuthOn;
	}

	public void setCreditAuthOn(String creditAuthOn) {
		this.creditAuthOn = creditAuthOn;
	}

	public String getPaymentAuthOn() {
		return paymentAuthOn;
	}

	public void setPaymentAuthOn(String paymentAuthOn) {
		this.paymentAuthOn = paymentAuthOn;
	}

	public String getRepayStatus() {
		return repayStatus;
	}

	public void setRepayStatus(String repayStatus) {
		this.repayStatus = repayStatus;
	}

	public String getRepayAuthUrl() {
		return repayAuthUrl;
	}

	public void setRepayAuthUrl(String repayAuthUrl) {
		this.repayAuthUrl = repayAuthUrl;
	}

	public String getIsCheckUserRole() {
		return isCheckUserRole;
	}

	public void setIsCheckUserRole(String isCheckUserRole) {
		this.isCheckUserRole = isCheckUserRole;
	}

	public String getCheckUserRoleDesc() {
		return checkUserRoleDesc;
	}

	public void setCheckUserRoleDesc(String checkUserRoleDesc) {
		this.checkUserRoleDesc = checkUserRoleDesc;
	}

	public String getMergeAuthDesc() {
		return mergeAuthDesc;
	}

	public void setMergeAuthDesc(String mergeAuthDesc) {
		this.mergeAuthDesc = mergeAuthDesc;
	}

	public String getMergeAuthExpire() {
		return mergeAuthExpire;
	}

	public void setMergeAuthExpire(String mergeAuthExpire) {
		this.mergeAuthExpire = mergeAuthExpire;
	}

	public String getPaymentAuthExpire() {
		return paymentAuthExpire;
	}

	public void setPaymentAuthExpire(String paymentAuthExpire) {
		this.paymentAuthExpire = paymentAuthExpire;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getExitLabelShowFlag() {
		return exitLabelShowFlag;
	}

	public void setExitLabelShowFlag(String exitLabelShowFlag) {
		this.exitLabelShowFlag = exitLabelShowFlag;
	}

	public Integer getPointsCurrent() {
		return pointsCurrent;
	}

	public void setPointsCurrent(Integer pointsCurrent) {
		this.pointsCurrent = pointsCurrent;
	}

	public String getPointsDetailUrl() {
		return pointsDetailUrl;
	}

	public void setPointsDetailUrl(String pointsDetailUrl) {
		this.pointsDetailUrl = pointsDetailUrl;
	}
}
