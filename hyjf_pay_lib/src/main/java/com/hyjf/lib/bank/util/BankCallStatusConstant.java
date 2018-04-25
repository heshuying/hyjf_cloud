/**
 * Description:银行存管接口相关常量
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: GOGTZ-T
 * @version: 1.0
 * Created at: 2015年11月23日 上午11:48:46
 * Modification History:
 * Modified by :
 */
package com.hyjf.lib.bank.util;

import java.io.Serializable;

public class BankCallStatusConstant extends BankCallParamConstant implements Serializable {
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 2111347846000200908L;
	// 状态
	/** 状态 0:处理成功 */
	public static final String STATUS_SUCCESS = "0";
	/** 状态 1:请求中 */
	public static final String STATUS_SENDING = "1";
	/** 状态 2:处理中 */
	public static final String STATUS_RUNNING = "2";
	/** 状态 3:验签成功 */
	public static final String STATUS_VERTIFY_OK = "3";
	/** 状态 4:验签失败 */
	public static final String STATUS_VERTIFY_NG = "4";
	/** 状态 9:处理失败 */
	public static final String STATUS_FAIL = "9";
	// 返回状态
	/** 状态 00000000:成功 */
	public static final String RESPCODE_SUCCESS = "00000000";
	/** 批次查询批次号不存在*/
	public static final String RESPCODE_BATCHNO_NOTEXIST = "JX900612";
	/** 实时放款重复*/
	public static final String RESPCODE_REALTIMELOAN_REPEAT = "JX900780";
	/** 重复的短信验证码发送请求 */
	public static final String RESPCODE_MOBILE_REPEAT = "JX900651";
	/** 接收结果 success:成功 */
	public static final String RECEIVED_SUCCESS = "success";
	/** 接收结果 fail:失败 */
	public static final String RECEIVED_FAIL = "fail";
	/** 状态 070: 请求数据不存在 */
	public static final String RESPCODE_NOTEXIST = "070";
	/** 状态 107:重复交易 */
	public static final String RESPCODE_REPEAT_DEAL = "107";
	/** 状态 334:已放款金额加本次放款金额超过投资人原单中的投资金额 */
	public static final String RESPCODE_ACCOUNT_OUT = "334";
	/** 状态 349:本次还款金额加上已还款金额超过还款总额 */
	public static final String RESPCODE_REPAY_OUT = "349";
	/** 状态 345:重复的放款请求 */
	public static final String RESPCODE_REPEAT_LOANS = "345";
	/** 状态 358:冻结失败 */
	public static final String RESPCODE_FREEZE_FAIL = "358";
	/** 状态 351:重复的还款请求 */
	public static final String RESPCODE_REPEAT_REPAY = "351";
	/** 状态 359:解冻失败 */
	public static final String RESPCODE_UNFREEZE_FAIL = "359";
	/** 状态 359:解冻失败 */
	public static final String RESPCODE_TENDER_FAIL = "363";
	/** 状态 400:取现失败 */
	public static final String RESPCODE_WITHDRAW_FAIL = "400";
	/** 状态 421:借款交易记录不存在 */
	public static final String RESPCODE_NO_LOANS_RECORD = "421";
	/** 状态 422:还款交易记录不存在 */
	public static final String RESPCODE_NO_REPAY_RECORD = "422";
	/** 状态 999:审核中 */
	public static final String RESPCODE_CHECK = "999";
	/** 状态 1000:查询数据不存在 */
	public static final String RESPCODE_NO_RESULT = "1000";
	/** 状态 215:充值手续费不足 */
	public static final String RESPCODE_YUE0_FAIL = "215";
	/** 状态 343:出账用户余额不足 */
	public static final String RESPCODE_YUE1_FAIL = "343";
	/** 状态 364:余额不足 */
	public static final String RESPCODE_YUE2_FAIL = "364";

	/** 状态 216:企业用户注册信息不存在 */
	public static final String RESPCODE_216 = "216";
	/** 状态 217:企业用户注册审核拒绝 */
	public static final String RESPCODE_217 = "217";
	/** 状态 218:企业用户注册审核中 */
	public static final String RESPCODE_218 = "218";
	/** 状态 219:企业用户注册已提交待审核 */
	public static final String RESPCODE_219 = "219";

	/** 提现状态 */
	// 提现中
	public static final int WITHDRAW_STATUS_DEFAULT = 0;
	// 提现失败
	public static final int WITHDRAW_STATUS_FAIL = 1;
	// 提现成功
	public static final int WITHDRAW_STATUS_SUCCESS = 2;

	/** 交易状态:S-成功 */
	public static final String TXSTATE_SUCCESS = "S";
	/** 交易状态:F-失败 */
	public static final String TXSTATE_FAILED = "F";
	/** 交易状态:D-待处理 */
	public static final String TXSTATE_WAIT = "D";

	/** 状态 364:可用余额不足 */
	public static final String RETCODE_BIDAPPLY_YUE_FAIL = "CA101121";
	
	/** 余额不足 */
    public static final String RETCODE_YUE_FAIL = "CA51";
	
	/**投资记录不存在*/
	public static final String RETCODE_BIDAPPLY_NOT_EXIST1 ="CA110112";
	/**投标申请记录不存在*/
	public static final String RETCODE_BIDAPPLY_NOT_EXIST2 ="JX900141";
	/**只能撤销状态是投标中的投标申请*/
	public static final String RETCODE_BIDAPPLY_NOT_RIGHT ="JX900146";
	
	/**该账户没有冻结资金记录*/
	public static final String RETCODE_UNFREEZE_NOT_EXIST ="JX900061";
	/**该账户冻结操作未完成或失败*/
	public static final String RETCODE_FREEZE_FAIL="JX900064";
	/**只能撤销状态是投标中的投标申请*/
	public static final String RETCODE_UNFREEZE_ALREADY ="JX900662";

	/** 投资是否冻结 0不冻结 */
	public static final String DEBT_FRZFLAG_UNFREEZE = "0";
	/** 投资是否冻结 1冻结 */
	public static final String DEBT_FRZFLAG_FREEZE = "1";

	/** 批次处理状态 A待处理 */
	public static final String BATCHSTATE_TYPE_AWAIT = "A";
	/** 批次处理状态 D处理中 */
	public static final String BATCHSTATE_TYPE_DOING = "D";
	/** 批次处理状态 S处理结束 */
	public static final String BATCHSTATE_TYPE_SUCCESS = "S";
	/** 批次处理状态 F处理失败 */
	public static final String BATCHSTATE_TYPE_FAIL = "F";
	/** 批次处理状态 C已撤销 */
	public static final String BATCHSTATE_TYPE_CANCEL = "C";

	/** 批次详情交易状态 D待处理 */
	public static final String BATCH_TXSTATE_TYPE_WAIT = "D";
	/** 批次详情交易状态 S成功 */
	public static final String BATCH_TXSTATE_TYPE_SUCCESS = "S";
	/** 批次详情交易状态 F失败 */
	public static final String BATCH_TXSTATE_TYPE_FAIL = "F";

}
