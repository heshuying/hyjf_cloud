/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: Administrator
 * @version: 1.0
 *           Created at: 2015年11月18日 下午2:38:03
 *           Modification History:
 *           Modified by :
 */

package com.hyjf.admin.common.util;

/**
 * @author Administrator
 */

public class ShiroConstants {

	/** 用户角色 */
	public static final String ROLE_NORMAL_USER = "NORMAL_USER";

	/** 查看权限 */
	public static final String PERMISSION_VIEW = "VIEW";

	/** 修改权限 */
	public static final String PERMISSION_MODIFY = "MODIFY";

	/** 转发权限 */
	public static final String PERMISSION_RESEND = "RESEND";

	/** 更新全部 */
	public static final String PERMISSION_MODIFYALL = "MODIFYALL";

	/** 删除权限 */
	public static final String PERMISSION_DELETE = "DELETE";

	/** 删除权限 */
	public static final String PERMISSION_RESETPWD = "RESETPWD";

	/** 添加权限 */
	public static final String PERMISSION_ADD = "ADD";

	/** 列表查看权限 */
	public static final String PERMISSION_LIST = "LIST";

	/** 列表查询权限 */
	public static final String PERMISSION_SEARCH = "SEARCH";

	/** 列表导入权限 */
	public static final String PERMISSION_IMPORT = "IMPORT";

	/** 列表导入权限 */
	public static final String PERMISSION_EXPORT = "EXPORT";
	
	/**批次交易明细*/
	public static final String PERMISSION_QUERY_BATCHDETAIL = "QUERYBATCHDETAIL";

	/** 发送协议权限 */
	public static final String PERMISSION_EXPORT_AGREEMENT = "EXPORTAGREEMENT";

	/** 详情查看权限 */
	public static final String PERMISSION_INFO = "INFO";

	/** 修改权限 */
	public static final String PERMISSION_MODIFYRE = "MODIFYRE";
	/** 修改权限 */
	public static final String PERMISSION_MODIFYIDCARD = "MODIFYIDCARD";
	
	/** 授权权限 */
	public static final String PERMISSION_AUTH = "AUTH";

	/** 审核 */
	public static final String PERMISSION_AUDIT = "AUDIT";

	/** 已交保证金权限 */
	public static final String PERMISSIONS_BORROW_BAIL = "BORROW_BAIL";

	/** 标的备案权限 */
	public static final String PERMISSIONS_DEBT_REGIST = "DEBT_REGIST";

	/** 初审 */
	public static final String PERMISSIONS_BORROW_FIRST_AUDIT = "BORROW_FIRST_AUDIT";

	/** 发标 */
	public static final String PERMISSIONS_BORROW_FIRE = "BORROW_FIRE";

	/** 复审 */
	public static final String BORROW_FULL = "BORROW_FULL";

	/** 流标 */
	public static final String BORROW_OVER = "BORROW_OVER";

	/** 返手续费权限 */
	public static final String PERMISSION_RETURNCASH = "RETURNCASH";

	/** 确认充值权限 */
	public static final String PERMISSION_FIX = "FIX";

	/** 提现确认权限 */
	public static final String PERMISSION_WITHDRAW_CONFIRM = "WITHDRAW_CONFIRM";

	/** 确认操作权限 */
	public static final String PERMISSION_CONFIRM = "CONFIRM";

	/** 还款操作权限 */
	public static final String PERMISSION_REPAY = "REPAY";

	/** 延期操作权限 */
	public static final String PERMISSION_AFTER_REPAY = "AFTER_REPAY";

	/** 汇直投取消权限 */
	public static final String PERMISSION_CANCEL = "CANCEL";

	/** 计算 */
	public static final String PERMISSION_CALCULATE = "CALCULATE";

	/** 更新 */
	public static final String PERMISSION_UPDATE = "UPDATE";

	/** 打包 */
	public static final String PERMISSION_PACKAGE = "PACKAGE";

	/** 打包 */
	public static final String PERMISSION_CHULI = "CHULI";

	/** 更新2015-08-19到2015-12-16之后的推荐人信息数据 权限 */
	public static final String PERMISSION_UPDATEINVITE = "UPDATEINVITE";
	/**会员管理中的企业用户信息补录权限*/
	public static final String PERMISSION_NSERT_CONPANYINFO = "COMPANY";

	/** 列表导入权限 */
	public static final String PERMISSION_CONFIRM_ACCOUNT = "CONFIRM_ACCOUNT";

	/** 提审 */
	public static final String PERMISSION_TISHEN = "TISHEN";

	/** 上传证照 */
	public static final String PERMISSION_SHANGCHUANZHENGZHAO = "SHANGCHUANZHENGZHAO";

	// 异常处理用权限
	/** 充值掉单异常修复权限 */
	public static final String PERMISSION_RECHARGE_EXCEPTION = "RECHARGE_EXCEPTION";

	/** 放款异常修复权限 */
	public static final String PERMISSION_RECOVER_EXCEPTION = "RECOVER_EXCEPTION";

	/** redis写入权限  */
	public static final String PERMISSION_WRITE_REDIS = "WRITE_REDIS";

	/** 修复用户出借数据 */
	public static final String PERMISSION_REPAIR_TENDER = "REPAIR_TENDER";

	/** 债转发送邮件 */
	public static final String PERMISSION_TRANSFER_SEND_EMAIL = "TRANSFER_SEND_EMAIL";

	/** 债转复制链接 */
	public static final String PERMISSION_TRANSFER_COPY_URL = "TRANSFER_COPY_URL";

	/** 借款列表预览权限 */
	public static final String PERMISSION_PREVIEW = "PREVIEW";

	/** 转账异常修复权限 */
	public static final String PERMISSION_TRANSFER_EXCEPTION = "TRANSFER_EXCEPTION";

	/** 计划清算权限 */
	public static final String PERMISSION_LIQUIDATION = "LIQUIDATION";

	/** 单发权限 */
	public static final String PERMISSION_SINGLE = "SINGLE";

	/** 群发权限 */
	public static final String PERMISSION_MASS = "MASS";

	/** 资金明细异常修复 */
	public static final String PERMISSION_DATA_REPAIR = "DATA_REPAIR";

	/** 标的撤销权限 */
	public static final String PERMISSIONS_REVOKE = "REVOKE";

	/** 银行对账权限 */
	public static final String PERMISSIONS_ACCOUNTCHECK = "ACCOUNTCHECK";

	/** 脱敏数据查看权限 */
	public static final String PERMISSION_HIDDEN_SHOW = "HIDDEN_SHOW";

	/** 短信内容查看权限 */
	public static final String PERMISSION_SMSCONTENT_SHOW = "SMS_CONTENT_SHOW";

	/** 标的备案异常处理 */
	public static final String PERMISSIONS_DEBTREGISTEXCEP = "DEBT_REGIST_EXCEP";

	/** 红包账户发放 */
	public static final String PERMISSIONS_REDPOCKETSEND = "REDPOCKETSEND";
	
	/** 出借撤销*/
	public static final String PERMISSIONS_BIDCANCEL = "BIDCANCEL";
	
	/** 出借人债权明细查询权限 */
	public static final String PERMISSION_DEBTCHECK = "DEBTCHECK";

	/** PDF签署权限 */
	public static final String PERMISSION_PDF_SIGN = "PDFSIGN";

	/** PDF脱敏图片预览权限 */
	public static final String PERMISSION_PDF_PREVIEW = "PDFPREVIEW";

	/** 出借人投标申请查询权限 */
	public static final String PERMISSION_QUERY_INVEST_DEBT_VIEW = "QUERY_INVEST_DEBT";

	/** 用户销户权限 */
	public static final String PERMISSION_BANK_CANCELLATION_ACCOUNT ="BANK_CANCELLATION_ACCOUNT";



}
