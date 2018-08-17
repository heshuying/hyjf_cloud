/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;

/**
 * @author fq
 * @version SmsCountRequestBean, v0.1 2018/8/17 14:17
 */
public class SmsCountRequestBean extends BaseRequest {
    private String ids;
    /**
     * 提交时间区间查询开始时间
     */
    private String post_time_begin;

    /**
     * 提交时间区间查询结束时间
     */
    private String post_time_end;

    /**
     * 分公司ID
     */
    private Integer departmentId;

    /** 部门 */
    private String departmentName;
    /** 部门 */
    private String combotreeSrch;
    /** 部门 */
    private String[] combotreeListSrch;

    /** 短信总条数 */
    private Integer smsNumber;

    /** 短信总金额 */
    private String smsNumberMoney;
}
