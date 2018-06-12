/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.beans;

public class RepaymentDetailDefine extends BaseDefine {

    /** @RequestMapping值 */
    public static final String REQUEST_MAPPING = "/repaymentdetail";
   
    /** 按照用户名/手机号查询还款明细（直投产品，含承接的债权） */
    public static final String GET_HZT_REPAYMENT_DETAIL_LIST_ACTION = "getHztRepaymentDetailList";

    /** 按照用户名/手机号查询还款明细（汇添金） */
    public static final String GET_HTJ_REPAYMENT_DETAIL_LIST_ACTION = "getHtjRepaymentDetailList";
    
}
