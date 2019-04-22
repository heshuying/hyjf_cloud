/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 * @author: lb
 * @version: 1.0
 * Created at: 2017年10月12日 上午9:13:24
 * Modification History:
 * Modified by : 
 */

package com.hyjf.common.enums;

import com.hyjf.common.constants.MsgCode;

/**
 * 信息代码和信息本体的枚举
 * @author liubin
 */

public enum MsgEnum implements MsgCode {
//	// ----------BaseResult信息------------
//	INFO_SUCCESS_0("0","成功"),
//	INFO_SUCCESS_000("000","成功"),
//	ERR_ERROR("-1","异常"),
//	ERR_FAIL("1","失败"),
//	ERR_NO_PERMISSION("2","无权限"),

	// ----------正常信息INFO------------
	//通用正常信息
	INFO_BUILING("ITY000001","系统处理中，请稍后"),
	INFO_BANK_WITHDRAW_LOSE_LIST_HANDLE_SUCCESS("ITY000002","江西银行提现掉单处理成功"),
	// ----------警告信息WARN------------
	//通用警告信息
	WARN_BUILING("WTY000001","接口访问量过大"),

	// 已确定删除的错误信息




	// ----------错误信息ERR-------------
	// 通用错误信息
	ERR_OBJECT_REQUIRED("ETY000001","{0}不能为空"),
	ERR_OBJECT_BLANK("ETY000002","{0}未填写"),//**未填写错误
	ERR_OBJECT_DIGIT("ETY000003","{0}不能存在非数字"),
	ERR_OBJECT_DATE("ETY000004","{0}非日期格式"),
	ERR_OBJECT_MAIL("ETY000005","{0}非法邮件地址"),
	ERR_OBJECT_VALUE("ETY000006","传入参数{0}的值非法"),
	ERR_OBJECT_GET("ETY000007","获取{0}失败"),//提取通用
	ERR_OBJECT_INVALID("ETY000008","无效的{0}"),//提取通用
	ERR_OBJECT_UNMATCH("ETY000009","{0}不符合接口要求，请重新传入"),
	ERR_OBJECT_EXCEED_LIMIT("ETY000010","{0}超出限制长度"),
	ERR_OBJECT_DECRYPT("ETY000011","{0}解密失败"),
	ERR_OBJECT_EXISTS("ETY000012","{0}已存在"),

	ERR_SIGN("ETY000101","签名验证失败"),
	ERR_PARAM_TYPE("ETY000102","传入参数类型错误"),
	ERR_JSON("ETY000103","传入JSON错误"),
	ERR_PARAM_NUM("ETY000104", "请求参数不全"),
	ERR_PARAM("ETY000105", "参数错误，请重试"),
	ERR_AM_SYSTEM("ETY000198","接口(am)调用发生异常，请联系服务方"),
	ERR_SYSTEM("ETY000199","接口调用发生异常，请联系服务方"),
	ERR_PAGE_MAX("ETY000201","单次检索记录数不能超过{0}条"),
	ERR_DATA_MAX("ETY000202","单次检索记录时间不能超过{0}天"),

	ERR_DATA_VERIFICATION("EDT000101","数据验证失败"),
	ERR_DATA_NOT_EXISTS("EDT000102","查无数据"),

	ERR_IP_VISIT_TOO_MANNY("ETY000301", "IP访问次数超限"),
	ERR_INSTCODE("ETY000401","机构编号错误"),
	ERR_BANK_WITHDRAW_LOSE_LIST_HANDLE("ETY000501","江西银行提现掉单处理出错"),

	// 格式错误信息ERR_FMT_
	ERR_FMT_NAME("EFM000101", "联系人姓名格式错误"),
	ERR_FMT_PHONE("EFM000102", "联系人手机号码格式错误"),
	ERR_FMT_MOBILE("EFM000103", "手机号格式不正确"),
	ERR_FMT_EMAIL("EFM000104", "邮箱格式不正确"),
	ERR_FMT_IDCARDNO("EFM000105", "身份证号格式错误"),//孙帅帅新加-----已修改
	ERR_FMT_PASSWORD("EFM000106", "必须包含数字、字母、符号至少两种"),
	ERR_FMT_MONEY("EFM000107", "金额格式不正确"),
	ERR_FMT_NICK("EFM000108","昵称2~16 位"),

	// 用户相关错误信息ERR_USER_
	ERR_USER_NOT_LOGIN("EUS000001", "用户未登录"),
	ERR_USER_LOGIN("EUS000002", "登录失败,账号或密码错误"),
	ERR_USER_NOT_EXISTS("EUS000003", "不存在用户"),
	ERR_USER_INVALID("EUS000004", "该用户已被禁用"),
	ERR_USER_REGISTER("1","注册失败"),
	ERR_USER_LOGIN_RETRY("EUS000006", "登录失败,请重新登陆"),
	ERR_USER_UNUSUAL("EUS000007", "你的账户信息存在异常，请联系客服人员处理"),
	ERR_USER_INFO_GET("EUS000008","查询用户失败"),
	ERR_USER_INFO_CHECK("EUS000009", "根据电子账户号查询用户信息失败"),
	ERR_USER_LOGIN_EXPIRE("EUS000010","登录失效，请重新登陆"),
	ERR_USER_USERNAME_AND_PASSWORD_REQUIRED("EUS000011","用户名或密码不能为空"),
	ERR_USER_RECOMMEND_INVALID("EUS000012","无效的推荐人"),
	ERR_USER_AUTH("EUS000013","用户认证失败"),
	ERR_USER_AUTHORITY("EUS000014","没有权限使用此接口"),

	// 资金相关错误信息ERR_AMT_
	ERR_AMT_NO_MONEY("EAM000001","账户余额不足"),
	ERR_AMT_MONEY("EAM000002","未查询到正确的余额信息"),
	// 资金-提现相关错误信息ERR_AMT_WITHDRAW_
	ERR_AMT_WITHDRAW_AMOUNT("EAM000101","请输入提现金额"),
	ERR_AMT_WITHDRAW_AMOUNT_GREATER_THAN_ONE("EAM000102","提现金额需大于1元"),
	ERR_AMT_WITHDRAW_CARD("EAM000103","银行卡号不正确，请确认后再次提现"),
	ERR_AMT_WITHDRAW_BANK_ALLIANCE_CODE_REQUIRED("EAM000104","大额提现时,开户行号不能为空"),
	ERR_AMT_WITHDRAW_BANK_MORETHEN_BANLANCE("EAM000105","提现金额大于可用余额，请确认后再次提现"),
	ERR_GET_WITHDRAW_CONFIG("EAM000106","获取提现配置失败"),


	// 资金-出借相关错误信息ERR_AMT_TENDER_
	ERR_AMT_TENDER_IN_PROGRESS("EAM000201","用户正在出借中"),
	ERR_AMT_TENDER_ONLY_LENDERS("EAM000202","仅限出借人进行出借"),
	ERR_AMT_TENDER_NEED_AUTO_INVEST("EAM000203","该产品需开通自动投标功能"),
	ERR_AMT_TENDER_NEED_AUTO_DEBT("EAM000204","该产品需开通自动债转功能"),
	ERR_AMT_TENDER_NEED_PAYMENT_AUTH("EAM000205","该产品需开通服务费授权功能"),
	ERR_AMT_TENDER_PLAN_CLOSE("EAM000206","此计划项目已经关闭"),
	ERR_AMT_TENDER_PLAN_NOT_EXIST("EAM000207","计划项目不存在"),
	ERR_AMT_TENDER_MONEY_BLANK("EAM000208","请输入加入金额"),
	ERR_AMT_TENDER_MONEY_ZERO("EAM000209","出借金额不能为0元"),
	ERR_AMT_TENDER_COUPON_USE_ALONE("EAM000210","该优惠券只能单独使用"),
	ERR_AMT_TENDER_YOU_ARE_LATE("EAM000211","您来晚了，下次再来抢吧"),
	ERR_AMT_TENDER_MONEY_REMAIN("EAM000212","剩余可加入金额为{0}元"),
	ERR_AMT_TENDER_MONEY_LESS_NEED_BUY_ALL("EAM000213","剩余可加入只剩{0}元，须全部购买"),
	ERR_AMT_TENDER_MIN_INVESTMENT("EAM000214","{0}元起投"),
	ERR_AMT_TENDER_MAX_INVESTMENT("EAM000215","项目最大出借金额为{0}元"),
	ERR_AMT_TENDER_GREATER_THAN_OPEN_LINE("EAM000216","加入金额不能大于开放额度"),
	ERR_AMT_TENDER_MONEY_NOT_ENOUGH("EAM000217","可用金额不足"),
	ERR_AMT_TENDER_MONEY_INTEGER_MULTIPLE("EAM000218", "加入递增金额须为{0}元的整数倍"),
	ERR_AMT_TENDER_NEED_RISK_ASSESSMENT("EAM000219","根据监管要求，出借前必须进行风险测评"),
	ERR_AMT_TENDER_INVESTMENT("EAM000220","抱歉，出借失败，请重试"),
	ERR_AMT_TENDER_INVESTMENT_WITH_COUPON("EAM000221","优惠券出借失败"),//孙帅帅
	ERR_AMT_TENDER_BORROW_NOT_EXIST("EAM000222","标的信息不存在"),
	ERR_AMT_TENDER_FIND_CREDIT_SUCCESS_MESS_ERROR("EAM000223","查询债转结果失败"),
	ERR_AMT_TENDER_SUCCESS("EAM000000","投标成功"),
	ERR_AMT_TENDER_BIND_PLAN_ERROR("EAM000224","该标的绑定了计划"),

	// 资金-充值相关错误信息ERR_AMT_RECHARGE_
	ERR_AMT_RECHARGE_BANK_CARD_GET("EAM000301","查询银行卡信息失败"),
	ERR_AMT_RECHARGE_MONEY_REQUIRED("EAM000302","充值金额不能为空"),
	ERR_AMT_RECHARGE_MONEY_MORE_DECIMAL("EAM000303","充值值金额不能大于两位小数"),
	ERR_AMT_RECHARGE("EAM000304","充值失败"),

	//资金-转账相关错误信息ERR_AMT_TRANSFER_
	ERR_AMT_TRANSFER("EAM000401","转账发生异常"),

	//资金-还款相关错误信息ERR_AMT_REPAY_
	ERR_AMT_REPAY_AUTO_CREDIT("EAM000501","系统正在处理项目债转，请{0}后发起还款！"),
	ERR_AMT_REPAY_FAIL_CREDIT("EAM000502","还款提交失败，请联系汇盈金服业务部门核实处理！"),

	//授权相关错误信息ERR_AUTHORIZE_
	ERR_AUTHORIZE_REPEAT("EAU000001","用户已授权,无需重复授权"),
	ERR_AUTHORIZE_STATE("EAU000002","授权状态查询接口失败"),
	ERR_AUTHORIZE_CODE_BLANK("EAU000003","短信授权码为空"),
	ERR_AUTHORIZE_STATUS_ERROR("EAU000004","标的不是未授权状态"),
	ERR_AUTHORIZE_STZH_WHITELIST_ERROR("EAU000005","未在受托支付白名单中"),
	ERR_AUTHORIZE_STZH_WHITELIST_STATUS_ERROR("EAU000006","受托支付白名单状态错误"),

	// 银行相关错误信息ERR_BANK_
	ERR_BANK_CALL("EBK000001", "调用银行接口失败"),
	ERR_BANK_UPDATE_AFTER_CALL("EBK000002", "调用银行成功后,更新数据失败"),
	// 开户相关ERR_BANK_ACCOUNT_
	ERR_BANK_ACCOUNT_OPEN("EBK000002", "开户失败"),
	ERR_BANK_ACCOUNT_NOT_OPEN("EBK000003", "用户未开户"),
	ERR_BANK_ACCOUNT_ALREADY_OPEN("EBK000004","用户已开户"),//孙帅帅新加-----已修改
	ERR_BANK_ACCOUNT_ERROR("EBK000011","开户成功，设置交易密码失败"),
	ERR_BANK_ACCOUNT_REALNAME_REQUIRED("EBK000005","真实姓名不能为空"),
	ERR_BANK_ACCOUNT_REALNAME_CONTAIN_SPACE("EBK000006","真实姓名不能包含空格"),
	ERR_BANK_ACCOUNT_REALNAME_MORE_THAN_TEN("EBK000007","真实姓名不能超过十位"),
	ERR_BANK_ACCOUNT_IDCARDNO_REQUIRED("EBK000008","身份证不能为空"),
	ERR_BANK_ACCOUNT_IDCARDNO_EXIST("EBK000009","身份证号已存在"),
	ERR_BANK_ACCOUNT_NOT_EXIST("EBK0000010","userId={0}没有账户信息！"),
	ERROR_BANK_SEND_CODE("EBK0000010", "短信验证码发送失败，请稍后再试！"),

	// 银行卡相关错误信息ERR_CARD_
	ERR_CARD_UNBIND_HAVE_BALANCE("ECD000001", "抱歉，请先清空当前余额和待收后，再申请解绑。"),
	ERR_CARD_SAVE("ECD000002", "银行卡信息保存失败"),
	ERR_CARD_NOT_EXIST("ECD000003", "没有要解绑的银行卡"),
	ERR_CARD_DELETE("ECD000004", "银行卡删除失败"),
	ERR_CARD_NOT_BIND("ECD000005","用户未绑卡"),
	ERR_CARD_BLANK("ECD000006","银行卡号未填写"),
	//add by nxl 企业用户解绑信息提示
	ERR_CARD_ENTERPRISE("ECD000007","企业用户解绑请联系客服"),

	ERR_AUTH_USER_PAYMENT("EAU000001","请先进行服务费授权"),
	ERR_AUTH_USER_REPAYMENT("EAU000002","请先进行还款授权"),
	ERR_AUTH_USER_AUTO_BID("EAU000003","请先进行自动出借授权"),
	ERR_AUTH_USER_AUTO_CREDIT("EAU000004","请先进行自动债转授权"),
	ERR_AUTH_REPEAT("CE000009","已授权,请勿重复授权！"),
	// 邮件相关错误信息ERR_EMAIL_
	ERR_EMAIL_USED("EEM000001", "邮箱已被占用"),
	//邮件激活相关错误信息ERR_EMAIL_ACTIVE_
	ERR_EMAIL_ACTIVE_SEND("EEM000002", "激活邮件发送失败"),
	ERR_EMAIL_ACTIVE_NOT_VALIDATE("EEM000003", "激活邮件未验证"),
	ERR_EMAIL_ACTIVE_ALREADY_VALIDATE("EEM000004", "激活邮件已验证"),
	ERR_EMAIL_ACTIVE_OVERDUE("EEM000005", "激活邮件已过期"),
	ERR_EMAIL_ACTIVE_NOT_EXIST("EEM000006", "激活邮件不存在"),
	ERR_EMAIL_ACTIVE("EEM000007", "激活失败"),
	ERR_EMAIL_REQUIRED("EEM000008","待绑定的邮箱不能为空"),




	// 手机号相关错误信息ERR_MOBILE
	ERR_MOBILE_NEED_SAME("EMB000001", "获取验证码手机号与注册手机号不一致!"),
	ERR_MOBILE_NEED_DIFFERENT("EMB000002", "修改手机号与原手机号不能相同!"),//different
	ERR_MOBILE_IS_NOT_REAL("EMB000003", "请填写您的真实手机号码"),
	ERR_MOBILE_EXISTS("EMB000004", "手机号已存在"),
	ERR_MOBILE_BLANK("EMB000005", "手机号未填写"),
	ERR_MOBILE_INCORRECT("EMB000006","手机号码错误"),
	ERR_MOBILE_REPEAT("EMB000007","手机号码重复"),


	//密码相关错误信息ERR_PASSWORD_
	ERR_PASSWORD_ERROR_TOO_MANEY("EPW000001", "登录失败,当日密码错误已达上限，请明日再试！"),
	ERR_PASSWORD_LENGTH("EPW000002", "密码长度8-16位"),
	ERR_PASSWORD_NO_NUMBER("EPW000003", "密码必须包含数字"),
	ERR_PASSWORD_NEW_REQUIRED("EPW000004","新密码不能为空"),
	ERR_PASSWORD_TWO_DIFFERENT_PASSWORD("EPW000005","两次密码不一致"),
	ERR_PASSWORD_TWO_SAME_PASSWORD("EPW000006","新密码不能与原密码相同"),
	ERR_PASSWORD_OLD_INCORRECT("EPW000007","旧密码不正确"),
	ERR_PASSWORD_MODIFY("EPW000008","修改密码失败,未作任何操作"),
	ERR_PASSWORD_OLD_REQUIRED("EPW000009","原始登录密码不能为空"),
	ERR_PASSWORD_INVALID("EPW000010","输入的密码不正确"),
	ERR_PASSWORD_ERROR_TOO_MAX("EPW000011", "您的登录失败次数超限，请{0}之后重试！"),
	ERR_PASSWORD_ERROR_MAX("EPW0000012", "登录失败,您的登录机会还剩{0}次！"),
	//交易密码相关错误信息ERR_TRADE_PASSWORD_
	ERR_TRADE_PASSWORD_NOT_SET("EPW000101", "未设置交易密码"),
	ERR_TRADE_PASSWORD_ALREADY_SET("EPW000102","已设置交易密码"),
	ERR_TRADE_PASSWORD_SET_FAIL("EPW000103", "交易密码设置失败"),
	ERR_TRADE_PASSWORD("EPW000104", "交易密码错误"),


	// 验证码相关错误信息ERR_SMSCODE_
	ERR_SMSCODE_SEND_TOO_FAST("ESC000002", "请求验证码操作过快"),
	ERR_SMSCODE_SEND_TOO_MANNY("ESC000003", "该设备短信请求次数超限，请明日再试"),
	ERR_SMSCODE_BLANK("ESC000004", "短信验证码未填写"),


	// 紧急联系人错误信息ERR_CONTACT_
	ERR_CONTACT_RELATIONSHIP_INVALID("ECT000001","无效的紧急联系人关系"),
	ERR_CONTACT_SAVE("ECT000002", "紧急联系人保存错误"),
	ERR_CONTACT_RELATIONSHIP_NOT_EXIST("ECT000003","紧急联系人关系数据不存在"),


	//活动错误信息ERR_ACTIVITY_
	ERR_ACTIVITY_NOT_EXIST("EAC000001","该活动不存在"),
	ERR_ACTIVITY_NOT_START("EAC000002","该活动还未开始"),
	ERR_ACTIVITY_END("EAC000003","该活动已结束"),


	// 系统错误信息ERR_SYSTEM_
	ERR_SYSTEM_UNKNOWN("ESY000001","未知错误，请稍后再试"),
	ERR_SYSTEM_API_CALL("ESY000002","微服务调用异常，请稍后重试"),
	ERR_SYSTEM_UNUSUAL("ESY000003","系统异常"),
	ERR_SYSTEM_BUSY("ESY000004","系统繁忙，请稍后再试"),

	// 订单相关ERR_ORDER_
	ERR_ORDER_VERIFY("EOD000001","订单验证失败"),



	// 时间日期相关错误信息ERR_DATE_

	// DB相关错误信息ERR_DB_
	// ----------通用警告信息------------



	// ----------共通机能用错误信息------------
	STATUS_CE000001("CE000001","请求参数异常"),
	STATUS_CE000002("CE000002","系统验签失败"),
	STATUS_CE000003("CE000003","根据手机号查询用户信息失败"),
	STATUS_CE000004("CE000004","根据电子账户号查询用户信息失败"),
	STATUS_CE000005("CE000005","银行处理中，请稍后查看"),
	STATUS_CE000006("CE000006","没有用户信息"),
	STATUS_CE000007("CE000007","没有用户开户信息"),
	STATUS_CE000008("CE000008","请求日期格式错误"),
	STATUS_CE000009("CE000009","请求开始日期大于结束日期"),
	STATUS_CE000010("CE000010","请求手机号格式错误"),
	STATUS_CE000011("CE000011","请求手机号不存在"),
	STATUS_CE000012("CE000012","请求用户电子账号不存在"),
	STATUS_CE000013("CE000013","请求项目编号不存在"),
	STATUS_CE000014("CE000014","此版本暂不可用，请更新至最新版本"),
	STATUS_CE000015("CE000015","平台账户可用余额不足"),
	STATUS_CE000016("CE000016","银行账户可用余额不足"),
	STATUS_CE000017("CE000017","该用户无银行开户信息"),
	STATUS_CE999999("CE999999","系统异常"),
	// ----------注册机能用错误信息------------
	STATUS_ZC000001("ZC000001","手机号不能为空"),
	STATUS_ZC000002("ZC000002","机构编号不能为空"),
	STATUS_ZC000003("ZC000003","请输入您的真实手机号码"),
	STATUS_ZC000004("ZC000004","机构编号错误"),
	STATUS_ZC000005("ZC000005","手机号已在平台注册"),
	STATUS_ZC000006("ZC000006","渠道不能为空"),
	STATUS_ZC000007("ZC000007","姓名不能为空"),
	STATUS_ZC000008("ZC000008","身份证号不能为空"),
	STATUS_ZC000009("ZC000009","银行卡号不能为空"),
	STATUS_ZC000010("ZC000010","手机验证码不能为空"),
	STATUS_ZC000011("ZC000011","短信发送订单号为空"),
	STATUS_ZC000012("ZC000012","真实姓名不能包含空格"),
	STATUS_ZC000013("ZC000013","真实姓名不能超过10位"),
	STATUS_ZC000014("ZC000014","身份证已存在"),
	STATUS_ZC000015("ZC000015","验证码错误"),
	STATUS_ZC000016("ZC000016","银行卡与姓名不符"),
	STATUS_ZC000017("ZC000017","银行卡与证件不符"),
	STATUS_ZC000018("ZC000018","平台不能为空"),
	STATUS_ZC000019("ZC000019","渠道不能为空"),
	STATUS_ZC000020("ZC000020","渠道非法"),

	// 还款信息接口项目编号不能为空
	//STATUS_ZC000018("ZC000018","资产编号不能为空"),
	// ----------资产机能用错误信息------------
	STATUS_ZT000001("ZT000001","没有用户信息"),
	STATUS_ZT000002("ZT000002","没有用户开户信息"),
	STATUS_ZT000003("ZT000003","用户不是借款人"),
	STATUS_ZT000004("ZT000004","系统异常,资产未进库"),
	STATUS_ZT000005("ZT000005","不支持这种还款方式"),
	STATUS_ZT000006("ZT000006","资产金额超过一百万"),
	STATUS_ZT000007("ZT000007","资产信息不正确"),
	STATUS_ZT000008("ZT000008","资产已入库"),
	STATUS_ZT000009("ZT000009","资产不存在"),
	// ----------免密提现机能用错误信息------------
	STATUS_NC000001("NC000001","免密提现-用户暂未开通该服务"),
	STATUS_NC000002("NC000002","免密提现-用户银行卡信息不一致"),
	STATUS_NC000003("NC000003","免密提现-查询用户失败"),
	STATUS_NC000004("NC000004","免密提现-查询用户详细信息失败"),
	STATUS_NC000005("NC000005","免密提现-查询电子帐号失败"),
	STATUS_NC000006("NC000006","免密提现-提现金额不能小于一元"),
	STATUS_NC000007("NC000007","免密提现-提现失败"),
	STATUS_NC000008("NC000008","免密提现-查询用户银行卡信息失败"),
	// ----------绑定银行卡机能用错误信息------------
	STATUS_BC000001("BC000001","用户已绑定银行卡,请先解除绑定,然后重新操作！"),
	STATUS_BC000002("BC000002","获取用户银行卡信息失败"),
	STATUS_BC000003("BC000003","银行处理中，请稍后查看"),
	STATUS_BC000004("BC000004","解绑失败，余额大于0元是不能解绑银行卡"),
	// ----------交易密码机能用错误信息------------
	STATUS_TP000001("TP000001","已设置交易密码"),
	STATUS_TP000002("TP000002","未设置过交易密码，请先设置交易密码"),
	// ----------用户测评错误信息------------
	STATUS_EV000001("EV000001","未找到对应测评结果"),
	STATUS_EV000003("EV000003", "用户已测评"),
	STATUS_EV000004("EV000004", "用户测评已过期"),
	STATUS_EV000005("EV000005","测评限额超额"),
	STATUS_EV000008("EV000008","测评限额超额（代收本金）"),
	STATUS_EV000006("EV000006","测评标的用户类型不匹配"),
	STATUS_EV000007("EV000007","达到标的设置类型及以上才可以出借此项目"),
	//----------上传用户头像错误信息-------------
	STATUS_EV000002("EV000002","头像修改失败,请刷新重试！"),



	//   优惠券相关
	COUPON_TENDER_FAIL_ERROR("EV100001","优惠券出借失败"),
	FIND_BORROW_ERROR("EV100002","未查询到该项目"),
	FIND_PLAN_ERROR("EV100003","未查询到该计划"),



	ERR_TRADE_BORROR_USER_NOT_EXIST("EV100004","借款人不存在"),
	ERR_TRADE_BORROR_PROJECTTYPE_NOT_EXIST("EV100005","未设置该出借项目的项目类型"),
	ERR_TRADE_51_OLD_USER("EV100006","该项目只能51老用户出借"),
	ERR_TRADE_NEW_USER("EV100007","该项目只能新手出借"),
	ERR_TENDER_ALLOWED_PC("EV100008","该项目只能在PC端出借"),
	ERR_TENDER_ALLOWED_IOS("EV100009","该项目只能在IOS端出借"),
	ERR_TENDER_ALLOWED_WEI("EV100010","该项目只能在微信端出借"),
	ERR_TENDER_ALLOWED_ANDROID("EV100011","该项目只能在Android端出借"),
	ERR_TENDER_ALLOWED_PLAT("EV100015","该项目只能在{0}端出借"),
	ERR_TENDER_YOURSELF("EV100012","借款人不可以自己出借项目"),
	ERR_TENDER_BIDDERS("EV100013","此项目已经流标"),
	ERR_TENDER_FULL_STANDARD("EV100014","此项目已经满标"),
	ERR_AMT_TENDER_MONEY_FORMAT("EAM000209","出借金额格式错误"),
	ERR_AMT_TENDER_MONEY_INT("EAM000209","出借金额必须为整数"),
	ERR_AMT_TENDER_MONEY_NEGATIVE("EAM000209","出借金额不能为负数"),
	ERR_AMT_TENDER_MONEY_BIG("EAM000209","出借金额不能大于项目剩余"),
	ERR_AMT_TENDER_BORROW_MONEY_LESS_NEED_BUY_ALL("EAM000213","剩余可投只剩{0}元，须全部购买"),
	ERR_AMT_TENDER_GREATER_THAN_TOTAL("EAM000216","出借金额不能大于项目总额"),
	ERR_AMT_TENDER_MONEY_INCREMENTING("EAM000216","出借递增金额须为{0}元的整数倍"),
	ERR_AMT_TENDER_HANDING("EAM000216","出借处理中"),
	ERR_AMT_TENDER_MONEY_LESS("EAM000213","可投剩余金额为{0}元"),

	//渠道
	NAME_REPEAT("REPEAT","{0}不能重复"),

	// 债转
	ERR_ALLOW_CHANNEL_ATTORN("1","用户所处渠道不允许债转"),
	ERROR_CREDIT_PARAM("1","转让失败,无法获取借款和出借编号"),
	ERROR_CREDIT_NOT_EXIST("1","获取债转数据为空"),
	ERROR_CREDIT_DATA_ERROR("1","债转数据错误"),
	ERROR_CREDIT_THREE("1","今天的转让次数已满3次,请明天再试"),
	ERROR_CREDIT_CREDIT_DISCOUNT_NULL("1","折让率不能为空"),
	ERROR_CREDIT_DISCOUNT_ERROR("1","折让率范围错误"),
	ERROR_CREDIT_DISCOUNT_FORMAT_ERROR("1","折让率格式错误"),
	ERROR_CREDIT_QUERY_ERROR("1","查询债转状态异常"),
	ERROR_CREDIT_UPDATE_ERROR("1","更新债转数据异常"),
	ERROR_CREDIT_FIND_LOG_ERROR("1","未查询到债转承接记录"),
	ERROR_CREDIT_AUTH_CODE_ERROR("1","未查询到债转授权码"),
	ERROR_CREDIT_CONFIG_NULL_ERROR("1","配置表无数据请配置"),

	ERROR_SMS_SEND("1","发送验证码失败，请稍后再试"),
	ERROR_CREDIT_NID_CAPITAL_NULL("1","债转编号和承接本金不能为空"),
	ERROR_CREDIT_NID_CAPITAL_NUMBER("1","债转编号和承接本金必须是数字格式"),
	ERROR_CREDIT_NO_MONEY("1","余额不足,请充值"),
	ERROR_CREDIT_CANT_BBY_YOURSELF("1","不可以承接自己出让的债权"),
	ERROR_CREDIT_NO_BORROW_RECOVER("1","未查询到用户的放款记录"),
	ERROR_CREDIT_NO_BORROW("1","当前认购人数太多,提交的认购债权本金已经失效,或者可以稍后再试"),
	ERR_LOGIN_INVALID("EUS000013", "登录过期,请重新登录!"),
	ERR_SUCCESS("0", ""),
	ERR_BIND("1", "授权失败，请仔细阅读并同意《汇盈金服授权协议》"),
	ERR_BIND_REPEAT("1","重复绑定"),
	ERR_DIC_NO_MATCH("ENM000001","字典表中没有值={0}的平台"),

	ERR_USERNAME_NOT_EXIST ("99","受托人用户名不存在。"),
	ERR_USERNAME_NOT_ACCOUNTS("99","受托人用户名必须已在江西银行开户。"),
	ERR_USERNAME_NOT_USES("99","受托人用户名已经被禁用"),
	ERR_USERNAME_NOT_IN("99","受托人用户名不在配置表中。"),
	ERR_USERNAME_NOT_RELEVANT("99","受托人用户名与资产来源不一致。"),
	ERR_USERNAME_IS_DISABLE("99","受托人在白名单中被禁用。"),
	ERR_PAYMENT_AUTH("99","未进行服务费授权。"),



	// 枚举终结
	ERR_ENUM("", "");

	private String msg;
	private String code;

	private MsgEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public String getMsg() { return this.msg; }

	@Override
	public String getCode() {
		return this.code;
	}
	public void replaceMsg(String msg) {
		this.msg = msg.replace("{}",msg);
	}
}
