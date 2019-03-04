
package com.hyjf.cs.trade.util;

/**
 * 首页属性定义
 */
public class HomePageDefine{

	/** 首页接口  @RequestMapping值 */
	public static final String REQUEST_MAPPING = "/hyjf-app/homepage";

	public static final String REQUEST_HOME = "/hyjf-app";

	/** 指定类型的项目 @RequestMapping值 */
	public static final String PROJECT_REQUEST_MAPPING = "/project";

	/** 项目详情 @RequestMapping值 */
	public static final String PROJECT_DETAIL_ACTION = "/getProjectDetail";

	/** 首页项目列表  @RequestMapping值 */
	public static final String PROJECT_LIST_ACTION = "/getProjectList";

	/** 400客服电话  @RequestMapping值 */
	public static final String SERVICE_PHONE_NUMBER_ACTION = "/getServicePhoneNumber";
	
	/** 获取JumpCommend接口  @RequestMapping值 */
    public static final String GET_JUMP_COMMEND_ACTION = "/getJumpCommend";
	
	
	/** app banner 分页 limit起始 */
	public static final int BANNER_SIZE_LIMIT_START = 0;

	/** app banner 分页limitsize */
	public static final int BANNER_SIZE_LIMIT_END = 5;
	
	/** app banner 分页 limit起始 */
	public static final int PROJECT_SIZE_LIMIT_START = 0;

	/** app banner 分页limitsize */
	public static final int PROJECT_SIZE_LIMIT_END = 50;
	

	/** 获取统计数据 */
	public static final String TOTALSTATICS_ACTION = "/getTotalStatics";
	
	/** 关于我们  - 资质文件 */
	public static final String CONTENT_QUALIFY_ACTION = "/getCompanyQualify";
	
	/** 关于我们  - 平台数据 */
	public static final String PLATFORM_DATA_ACTION = "/getPlatformData";

	/** url */
	public static final String APP_NEW_TEST = "http://appnewtest4.hyjf.com";

	/** 关于我们 */
	public static final String ABOUTS = "/aboutus";

	/** 运营报告 */
	public static final String REPORT = "/find/report";

	/** 资金存管 */
	public static final String DEPOSITORY = "/jsp/jx-bank-intr.jsp";

	/** 散标链接 */
	public static final String BORROW = "/borrow/";

	/** 计划链接 */
	public static final String PLAN = "/plan/";

	/** 项目详情 */
	public static final String HJH_PLAN_DETAIL_ACTION = "{planNid}";

	/** 首页module地址 */
    public static String MODULE_URL = "/data/upfiles/appmodule/";

    public static String  INVEST_INVEREST_AMOUNT_URL = "http://CS-MESSAGE/cs-message/search/getTotalInvestAndInterestEntity";

    // WECHAT START

    public static String WECHAT_HOME_PROJECT_LIST_URL = "http://AM-TRADE/am-trade/projectlist/wechat/searchHomeProejctList";

	public static String WECHAT_HOME_PLAN_LATER_URL = "http://AM-TRADE/am-trade/projectlist/wechat/selectHomeHjhOpenLaterList";

	public static String WECHAT_HOME_REPAYMENT_URL = "http://AM-TRADE/am-trade/projectlist/wechat/selectHomeRepaymentsProjectList";

	public static final String WECHAT_REQUEST_MAPPING = "/hyjf-wechat/homepage";

	public static final String WECHAT_DETAIL_REQUEST_MAPPING = "/hyjf-wechat/project";

	public static final String WECHAT_DETAIL_METHOD = "getProjectDetail";
	/*首页统计数据方法*/
	public static final String WECHAT_HOME_INDEX_DATA_METHOD = "/getIndexData.do";
	/*首页项目列表*/
	public static final String WECHAT_HOME_PROJECT_LIST_METHOD = "/getHomeProjectList.do";

	public static final  String WECHAT_STATUS_SUCCESS = "000";

	public static final  String WECHAT_STATUC_DESC = "成功";

	/** 首页推荐标的类型：可投标的和定时标的*/
	public static final int INDEX_PROJECT_SHOW_TYPE_TENDER = 1;

	/** 首页推荐标的类型：复审中标的*/
	public static final int INDEX_PROJECT_SHOW_TYPE_FULL = 2;

	/** 首页推荐标的类型：还款中标的*/
	public static final int INDEX_PROJECT_SHOW_TYPE_REPAY = 3;


}
