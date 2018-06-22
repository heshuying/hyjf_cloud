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
	ERR_OBJECT_UNMATCH("ETY000104","{0}不符合接口要求，请重新传入"),

	ERR_SIGN("ETY000101","签名验证失败"),
	ERR_PARAM_TYPE("ETY000102","传入参数类型错误"),
	ERR_JSON("ETY000103","传入JSON错误"),
	ERR_PARAM_NUM("ETY000104", "请求参数不全"),
	ERR_SYSTEM("ETY000199","接口调用发生异常，请联系服务方"),
	ERR_PAGE_MAX("ETY000202","单次检索记录数不能超过{0}条"),
	ERR_DATA_MAX("ETY000202","单次检索记录时间不能超过{0}天"),


	ERR_IP_VISIT_TOO_MANNY("ETY000301", "IP访问次数超限"),
	ERR_INSTCODE("ETY000401","机构编号错误"),
	ERR_AUTHORIZE_REPEAT("ETY000501","用户已授权,无需重复授权"),
	ERR_CHECK_USER_INFO("ETY000601", "根据电子账户号查询用户信息失败"),

	// 格式错误信息ERR_FMT_
	ERR_FMT_NAME("EFM000101", "联系人姓名格式错误"),
	ERR_FMT_PHONE("EFM000102", "联系人手机号码格式错误"),
	ERR_FMT_MOBILE("EFM000103", "手机号格式不正确"),
	ERR_FMT_EMAIL("EFM000104", "邮箱格式不正确"),
	ERR_FMT_IDCARDNO("EFM000105", "身份证号格式错误"),//孙帅帅新加-----已修改
	ERR_FMT_PASSWORD("EFM000106", "密码必须由数字和字母组成，如abc123"),

	// 用户相关错误信息ERR_USER_
	ERR_USER_NOT_LOGIN("EUS000001", "用户未登录"),
	ERR_USER_LOGIN("EUS000002", "登录失败,账号或密码错误"),
	ERR_USER_NOT_EXISTS("EUS000003", "不存在用户"),
	ERR_USER_INVALID("EUS000004", "抱歉，您的账户已被禁用，如有疑问请联系客服！"),
	ERR_USER_REGISTER("EUS000005","注册失败"),

	// 资金相关错误信息ERR_AMT_

	// 银行相关错误信息ERR_BANK_
	ERR_BANK_CALL("EBK000001", "调用银行接口失败"),
	ERR_BANK_ACCOUNT_OPEN("EBK000002", "开户失败"),
	ERR_BANK_ACCOUNT_NOT_OPEN("EBK000003", "用户未开户"),
	ERR_BANK_ACCOUNT_ALREADY_OPEN("EBK000004","用户已开户"),//孙帅帅新加-----已修改


	// 银行卡相关错误信息ERR_CARD_
	ERR_CARD_UNBIND_HAVE_BALANCE("ECD000001", "账户尚有余额，不能解绑银行卡"),
	ERR_CARD_SAVE("ECD000002", "银行卡信息保存失败"),
	ERR_CARD_NOT_EXIST("ECD000003", "没有要解绑的银行卡"),
	ERR_CARD_DELETE("ECD000004", "银行卡删除失败"),


	// 邮件相关错误信息ERR_EMAIL_
	ERR_EMAIL_USED("EEM000001", "邮箱已被占用"),
	//邮件激活相关错误信息ERR_EMAIL_ACTIVE_
	ERR_EMAIL_ACTIVE_SEND("EEM000002", "激活邮件发送失败"),
	ERR_EMAIL_ACTIVE_NOT_VALIDATE("EEM000003", "激活邮件未验证"),
	ERR_EMAIL_ACTIVE_ALREADY_VALIDATE("EEM000004", "激活邮件已验证"),
	ERR_EMAIL_ACTIVE_OVERDUE("EEM000005", "激活邮件已过期"),
	ERR_EMAIL_ACTIVE_NOT_EXIST("EEM000006", "激活邮件不存在"),
	ERR_EMAIL_ACTIVE("EEM000007", "激活失败"),

	// 手机号相关错误信息ERR_MOBILE
	ERR_MOBILE_NEED_SAME("EMB000001", "获取验证码手机号与注册手机号不一致!"),
	ERR_MOBILE_NEED_DIFFERENT("EMB000002", "修改手机号与原手机号不能相同!"),//different
	ERR_MOBILE_IS_NOT_REAL("EMB000003", "请填写您的真实手机号码"),
	ERR_MOBILE_EXISTS("EMB000004", "手机号已存在"),

	//密码相关错误信息ERR_PASSWORD_
	ERR_PASSWORD_ERROR_TOO_MANEY("EPW000001", "登录失败,当日密码错误已打上限，请明日再试！"),
	ERR_PASSWORD_LENGTH("EPW000002", "密码长度6-16位"),
	ERR_PASSWORD_NO_NUMBER("EPW000003", "密码必须包含数字"),
	ERR_PASSWORD_NOT_SET("EPW000004", "未设置交易密码"),


	// 验证码相关错误信息ERR_SMSCODE_
	ERR_SMSCODE_SEND_TOO_FAST("ESC000002", "请求验证码操作过快"),
	ERR_SMSCODE_SEND_TOO_MANNY("ESC000003", "该设备短信请求次数超限，请明日再试"),




	// 紧急联系人错误信息ERR_CONTACT_
	ERR_CONTACT_SAVE("ECT000002", "紧急联系人保存错误"),



	//活动错误信息ERR_ACTIVITY_
	ERR_ACTIVITY_NOT_EXIST("EAC000001","该活动不存在"),
	ERR_ACTIVITY_NOT_START("EAC000002","该活动还未开始"),
	ERR_ACTIVITY_END("EAC000003","该活动已结束"),


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

	// 还款信息接口项目编号不能为空
	STATUS_ZC000018("ZC000018","资产编号不能为空"),
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





	ERR_SUCCESS("0", ""),
	// 枚举终结
	ERR_ENUM("", "");


	private String msg;
	private String code;

	private MsgEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getMsg() { return this.msg; }

	public String getCode() {
		return this.code;
	}
}
