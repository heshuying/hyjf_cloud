/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author fq
 * @version SmsCountRequestBean, v0.1 2018/8/17 14:17
 */
public class SmsCountRequestBean extends BaseRequest {
    private String ids;
    /**
     * 提交时间区间查询开始时间
     */
    @ApiModelProperty(name = "提交时间区间查询开始时间")
    private String post_time_begin;

    /**
     * 提交时间区间查询结束时间
     */
    @ApiModelProperty(name = "提交时间区间查询结束时间")
    private String post_time_end;

    /**
     * 分公司ID
     */
    @ApiModelProperty(name = "分公司ID")
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

    private int limitStart = -1;
    private int limitEnd = -1;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getPost_time_begin() {
        return post_time_begin;
    }

    public void setPost_time_begin(String post_time_begin) {
        this.post_time_begin = post_time_begin;
    }

    public String getPost_time_end() {
        return post_time_end;
    }

    public void setPost_time_end(String post_time_end) {
        this.post_time_end = post_time_end;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCombotreeSrch() {
        return combotreeSrch;
    }

    public void setCombotreeSrch(String combotreeSrch) {
        this.combotreeSrch = combotreeSrch;
    }

    public String[] getCombotreeListSrch() {
        return combotreeListSrch;
    }

    public void setCombotreeListSrch(String[] combotreeListSrch) {
        this.combotreeListSrch = combotreeListSrch;
    }

    public Integer getSmsNumber() {
        return smsNumber;
    }

    public void setSmsNumber(Integer smsNumber) {
        this.smsNumber = smsNumber;
    }

    public String getSmsNumberMoney() {
        return smsNumberMoney;
    }

    public void setSmsNumberMoney(String smsNumberMoney) {
        this.smsNumberMoney = smsNumberMoney;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }
}
