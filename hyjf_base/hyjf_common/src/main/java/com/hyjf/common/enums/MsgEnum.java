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


	// ----------错误信息ERR-------------
	// 通用错误信息
	ERR_OBJECT_REQUIRED("ETY000001","{0}不能为空"),
	ERR_OBJECT_DIGIT("ETY000002","{0}不能存在非数字"),
	ERR_OBJECT_DATE("ETY000003","{0}非日期格式"),
	ERR_OBJECT_MAIL("ETY000004","{0}非法邮件地址"),
	ERR_OBJECT_VALUE("ETY000005","传入参数{0}的值非法"),
	ERR_SIGN("ETY000101","签名验证失败"),
	ERR_PARAM_TYPE("ETY000102","传入参数类型错误"),
	ERR_JSON("ETY000103","传入JSON错误"),
	ERR_PARAM_NUM("ETY000104", "请求参数不全"),
	ERR_SYSTEM("ETY000199","接口调用发生异常，请联系服务方"),
	ERR_PAGE_MAX("ETY000202","单次检索记录数不能超过{0}条"),
	ERR_DATA_MAX("ETY000202","单次检索记录时间不能超过{0}天"),
	ERR_OBJECT_UNMATCH("ETY000104","{0}不符合接口要求，请重新传入"),
	// 格式错误信息ERR_FMT_

	// 用户相关错误信息ERR_USER_
	ERR_USER_NOT_LOGIN("EUS000001", "用户未登录"),
	ERR_USER_LOGIN("EUS000002", "登录失败,请重新登陆"),


	// 资金相关错误信息ERR_AMT_



	ERR_CANNOT_REPEAT("1", "用户已授权,无需重复授权"),
	ERR_CALL_BANK("1", "调用银行接口失败"),
	ERR_CHECK_USER_INFO("1", "根据电子账户号查询用户信息失败"),
	ERR_GET_USER("1", "查询用户失败"),
	ERR_NOT_PASSWD("1", "未设置交易密码"),
	ERR_AUTH("1", "授权状态查询接口失败！"),
	ERR_CARD_NO("1", "银行卡号未填写"),
	ERR_MOBILE("1", "手机号未填写"),
	ERR_SMSCODE("1", "短信验证码未填写"),
	ERR_AUTH_CODE("1", "短信授权码为空"),
	ERR_BANK_NOT_OPEN("1", "用户未开户"),
	ERR_BANK_CALL("1", "请求银行接口失败"),
	ERR_CARD_SAVE("1", "银行卡信息保存失败"),
	ERR_BANK_BALANCE("1", "账户尚有余额，不能解绑银行卡"),
	ERR_CARD_NOT_EXIST("1", "没有要解绑的银行卡"),
	ERR_CARD_DELETE("1", "银行卡删除失败"),
	ERR_EMAIL_EMPTY("1", "待绑定的邮箱不能为空"),
	ERR_EMAIL_USED("1", "邮箱已被占用"),
	ERR_EMAIL_FORMAT("1", "邮箱格式不正确"),
	ERR_EMAIL_ACTIVE_SEND("1", "发送激活邮件失败"),
	ERR_EMAIL_ACTIVE_ERROR_1("1", "激活邮件未验证"),
	ERR_EMAIL_ACTIVE_ERROR_2("1", "激活邮件已验证"),
	ERR_EMAIL_ACTIVE_ERROR_3("1", "激活邮件已过期"),
	ERR_EMAIL_ACTIVE_ERROR_4("1", "激活邮件不存在"),
	ERR_EMAIL_ACTIVE("1", "激活失败"),
	ERR_NAME_FORMAT("1", "联系人姓名格式错误"),
	ERR_PHONE_FORMAT("1", "联系人手机号码格式错误"),
	ERR_RELATION_NOTEXIST("1", "无效的紧急联系人关系"),
	ERR_CONTRACT_SAVE("1", "紧急联系人保存错误"),
	ERR_USER_INVALID("1", "抱歉，您的账户已被禁用，如有疑问请联系客服！"),
	ERR_CHECK_NULL("1", "用户名或密码不能为空！"),
	ERR_REFFER_INVALID("1", "无效的推荐人！"),
	ERR_PWD_ERROR_TOO_MANEY("1", "登录失败,当日密码错误已打上限，请明日再试！"),
	ERR_SUCCESS("0", ""),

	ERR("1", "开户失败"),
	ERR_REGISTER("1", "注册失败"),
	ERR_PARAM_ERROR("1", "登录失败,账号或密码错误"),
	ERR_MOBILE_IS_NOT_NULL("1", "请填写手机号"),
	ERR_SMSCODE_IS_NOT_NULL("1", "验证码不能为空"),
	ERR_PASSWORD_IS_NOT_NULL("1", "密码不能为空"),
	ERR_MOBILE_IS_NOT_REAL("1", "请填写您的真实手机号码"),
	ERR_MOBILE_EXISTS("1", "手机号已存在"),
	ERR_PASSWORD_LENGTH("1", "密码长度6-16位"),
	ERR_PASSWORD_NO_NUMBER("1", "密码必须包含数字"),
	ERR_PASSWORD_FORMAT("1", "密码必须由数字和字母组成，如abc123"),
	ERR_SMSCODE_INVALID("1", "验证码无效"),
	ERR_MOBILE_NEED_SAME("1", "获取验证码手机号与注册手机号不一致!"),
	ERR_MOBILE_MODIFY("1", "修改手机号与原手机号不能相同!"),
	ERR_SEND_SMSCODE_TOO_FAST("1", "请求验证码操作过快!"),
	ERR_SEND_SMSCODE_TOO_MANNY("1", "该设备短信请求次数超限，请明日再试!"),
	ERR_IP_VISIT_TOO_MANNY("1", "IP访问次数超限!"),
	ERR_FIND_SMSCONFIG("1", "获取短信配置失败!"),
	ERR_INSTCODE("1","机构编号错误"),
	ERR_CODETYPE_INVALID("1", "无效的验证码类型!"),
	ERR_MOBILE_FORMAT("1", "手机号格式不正确"),
	ERR_USER_NOT_EXISTS("1", "不存在用户"),
	ERR_INST_CODE("1", "机构编号不能为空"),
	ERR_PLATEFORM("1", "注册平台不能为空"),
	ERR_UTMID("1", "推广渠道不能为空"),

	//活动编号不能为空
	ERR_ACTIVITYID_IS_NULL("1","活动编号不能为空"),
	//该活动不存在
	ERR_ACTIVITY_ISNULL("1","该活动不存在"),
	//该活动不存在
	ERR_ACTIVITY_TIME_NOT_START("1","该活动还未开始"),
	//该活动已结束
	ERR_ACTIVITY_TIME_END("1","您来晚了，活动已过期~~"),
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

	