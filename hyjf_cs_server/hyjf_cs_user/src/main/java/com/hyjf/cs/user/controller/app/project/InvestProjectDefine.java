/**
 * Description:我的投资常量定义
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */
package com.hyjf.cs.user.controller.app.project;

import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.user.bean.BaseDefine;

public class InvestProjectDefine extends BaseDefine {

	/** 我的投资 @RequestMapping值 */
	public static final String REQUEST_MAPPING = "/user/invest";

	/** 我的投资项目列表(回款中) @RequestMapping值 */
	public static final String REPAY_LIST_ACTION = "/getRepayList";

	/** 我的投资项目列表(投资中) @RequestMapping值 */
	public static final String INVEST_LIST_ACTION = "/getInvestList";

	/** 我的投资项目列表(已回款) @RequestMapping值 */
	public static final String ALREADY_REPAY_LIST_ACTION = "/getAlreadyRepayList";

	/** (回款中)还款计划 @RequestMapping值 */
	public static final String REPAY_PLAN_LIST_ACTION = "/getRepayPlan";

	/** (回款中)合同@RequestMapping值 */
	public static final String REPAY_CONTACT_ACTION = "/getRepayContact";

	/** 项目详情 @Path值 */
	public static final String PROJECT_HXF_CONTRACT_PTAH = "contract/hxfcontract";

	/** 项目详情 @Path值 */
	public static final String PROJECT_HZT_CONTRACT_PTAH = "contract/hztcontract";;
	/** 嘉诺融通宝投资协议 @Path值 */
	public static final String PROJECT_RTB_CONTRACT_PTAH = "invest/rtb-agreement";;
	/** 中商储融通宝投资协议 @Path值 */
	public static final String PROJECT_RTB_CONTRACT_ZSC_PTAH = "invest/rtb-agreement-zsc";;

	/** 回款中项目详情 @RequestMapping */
	public static final String REPAY_PROJECT_DETAIL_ACTION = "/repayProjectDetail";

	/** 错误页面 @Path值 */
	public static final String ERROR_PTAH = "error";

	/** 回款中项目详情 @Path值 */
	public static final String REPAY_PROJECT_DETAIL_PATH = "/investproject/repayproject_detail";

	/** 投资中项目详情 @RequestMapping */
	public static final String INVEST_PROJECT_DETAIL_ACTION = "/investProjectDetail";

	/** 投资中项目详情 @Path值 */
	public static final String INVEST_PROJECT_DETAIL_PATH = "/investproject/investproject_detail";

	/** 已回款项目详情 @RequestMapping */
	public static final String REPAYED_PROJECT_DETAIL_ACTION = "/repayedProjectDetail";

	/** 已回款项目详情 @RequestMapping */
	public static final String REPAYED_PROJECT_DETAIL_PATH = "/investproject/repayedproject_detail";

	/** log日志controller类名 */
	public static final String THIS_CLASS = InvestProjectController.class.getName();

	public static final String MYPROJECT_LIST_ACTION = "/getMyProject";

	public static final String MYPROJECT_LIST_REQUEST = REQUEST_HOME + REQUEST_MAPPING + MYPROJECT_LIST_ACTION;
	/** 我的散标详情  */
	public static final String MY_BORROW_PAGE_URL =  CustomConstants.HOST  + "/user/borrow";
	/** 我的债转详情  */
	public static final String MY_CREDIT_PAGE_URL =  CustomConstants.HOST  + "/user/transfer";
	/** 转让  */
	public static final String TO_CREDIT_PAGE_URL =  CustomConstants.HOST  + "/user/borrow/transfer/setting";
}
