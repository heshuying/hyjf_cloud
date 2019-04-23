package com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common;

import java.io.Serializable;

/**
 * 
 * //合规数据上报 CERT 常量
 * @author sunss
 * @version hyjf 1.0
 * @since hyjf 1.0 2018年11月26日
 * @see 上午11:01:58
 */
public class CertCallConstant implements Serializable {
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -7131277696965280498L;

	// 接口地址相关 start
	public static final String CERT_CALL_USER_INFO_URL = "/p2p/userInfo";// 用户
	public static final String CERT_CALL_SCATTER_INVEST_URL = "/p2p/scatterInvest";// 散标
    public static final String CERT_CALL_STATUS_URL = "/p2p/status";// 散标状态
    public static final String CERT_CALL_REPAY_PLAN_URL = "/p2p/repayPlan";// 还款计划
    public static final String CERT_CALL_FINANCE_URL = "/p2p/finance";// 产品信息
    public static final String CERT_CALL_FINANCE_SCATTER_CONFIG_URL = "/p2p/financeScatterConfig";// 产品散标配置
    public static final String CERT_CALL_CREDITOR_URL = "/p2p/creditor";// 债权信息
    public static final String CERT_CALL_TRANSFER_PROJECT_URL = "/p2p/transferProject";// 转让项目
    public static final String CERT_CALL_TRANSFER_STATUS_URL = "/p2p/transferStatus";// 转让状态
    public static final String CERT_CALL_UNDER_TAKE_URL = "/p2p/underTake";// 承接信息
    public static final String CERT_CALL_TRANSACT_URL = "/p2p/transact";// 交易流水

    // 产品信息
    public static final String CERT_CALL_LENDPRODUCT_URL = "/p2p/lendProduct";
    //产品配置信息
    public static final String CERT_CALL_LENDPRODUCTCONFIG_URL = "/p2p/lendProductConfig";

    // 接口地址相关 end
    
    // 版本号
    public final static String CERT_CALL_VERSION = "1.5";


    // 请求类型  start
    /**用户数据推送 1*/
    public static final String CERT_INF_TYPE_USER_INFO  = "1";
    /**散标 2*/
    public static final String CERT_INF_TYPE_SCATTER_INVEST  = "2";
    /**散标状态 6*/
    public static final String CERT_INF_TYPE_STATUS  = "6";
    /**还款计划 81*/
    public static final String CERT_INF_TYPE_REPAY_PLAN  = "81";
    /**产品信息 3*/
    public static final String CERT_INF_TYPE_FINANCE  = "86";
    /**产品散标配置 10*/
    public static final String CERT_INF_TYPE_FINANCE_SCATTER_CONFIG  = "87";
    /**债权信息 82*/
    public static final String CERT_INF_TYPE_CREDITOR  = "82";
    /**转让项目 83*/
    public static final String CERT_INF_TYPE_TRANSFER_PROJECT  = "83";
    /**转让状态 84*/
    public static final String CERT_INF_TYPE_TRANSFER_STATUS  = "84";
    /**承接信息 85*/
    public static final String CERT_INF_TYPE_UNDER_TAKE  = "85";
    /**交易流水 4*/
    public static final String CERT_INF_TYPE_TRANSACT  = "4";
    /**投资明细 88*/
    public static final String CERT_INF_TYPE_INVEST_DETAIL  = "88";
    // 请求类型  end


    // 响应状态码 start
    // 成功
    public static final String CERT_RESPONSE_SUCCESS  = "0000";

    // 查询对账响应码
    // 为入库成功
    public static final String CERT_QUERY_RESPONSE_SUCCESS  = "success";
    // 为入库失败，企业需要将该批次号下面的数据重新报送，批次号可以和原来的一样
    public static final String CERT_QUERY_RESPONSE_FAILED  = "failed";
    // 为等待处理，企业需要隔段时间调用该接口
    public static final String CERT_QUERY_RESPONSE_HOLD  = "hold";
    // 为批次号无效
    public static final String CERT_QUERY_RESPONSE_ISNOT  = "isNot";
    // 无响应
    public static final String CERT_QUERY_RESPONSE_NO  = "NO";

    // 响应状态码 end



    //返回状态   start

    //上报结果 0初始，1成功，9失败 99无响应
    public static final String CERT_RETURN_STATUS_INIT = "0";
    public static final String CERT_RETURN_STATUS_SUCCESS  = "1";
    public static final String CERT_RETURN_STATUS_FAIL  = "9";
    public static final String CERT_RETURN_STATUS_NO  = "99";

    // 查询结果 0初始  1成功  8批次号无效 9失败  2等待处理  99 无响应
    public static final Integer CERT_QUERY_RETURN_STATUS_INIT = 0;
    public static final Integer CERT_QUERY_RETURN_STATUS_SUCCESS  = 1;
    public static final Integer CERT_QUERY_RETURN_STATUS_HOLD  = 2;
    public static final Integer CERT_QUERY_RETURN_STATUS_ISNOT = 8;
    public static final Integer CERT_QUERY_RETURN_STATUS_FAILED  = 9;
    public static final Integer CERT_QUERY_RETURN_STATUS_NO  = 99;
    //返回状态  end


    //配置文件相关   start
    /** //合规数据上报 CERT 国家互联网应急中心 请求地址 https://api.ifcert.org.cn * 正式地址和测试地址区别 去掉【/test】 例 p2p/userInfo/test**/
//    public static final String CERT_SEVER_PATH = PropUtils.getSystem("hyjf.cert.sever.path");
    /** 测试环境后缀 */
    public static final String CERT_TEST_URL = "/test";
    /** //合规数据上报 CERT 国家互联网应急中心是否测试环境 true 测试环境 false 生产环境 */
/*    public static final String CERT_IS_TEST = PropUtils.getSystem("hyjf.cert.is.test");
    *//** //合规数据上报 CERT 国家互联网应急中心 企业自己的sourceCode*//*
    public static final String CERT_SOURCE_CODE = PropUtils.getSystem("hyjf.cert.source.code");
    *//** //合规数据上报 CERT 国家互联网应急中心 企业自己的apiKey *//*
    public static final String CERT_API_KEY = PropUtils.getSystem("hyjf.cert.api.key");
    *//**合规数据上报 CERT 国家互联网应急中心 金协议地址*//*
    public static final String CERT_CRT_PATH= PropUtils.getSystem("hyjf.cert.crt.path");
    *//**合规数据上报 CERT web工程的路径 http://testweb3.hyjf.com*//*
    public static final String CERT_WEB_HOST= PropUtils.getSystem("hyjf.web.host");
    *//** 查询批次数据入库消息 *//*
    public static final String CERT_WEB_YIBU= PropUtils.getSystem("hyjf.cert.yibu.path");*/

    //配置文件相关   end



    // 状态等全局变量  start

    // 用户状态编码 1-新增／2-变更／3-失效
    public static final String CERT_PARAM_USER_STATUS_ADD = "1";
    public static final String CERT_PARAM_USER_STATUS_UPD = "2";
    public static final String CERT_PARAM_USER_STATUS_REM = "3";

    // 状态等全局变量  end
}
