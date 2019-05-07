/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.model.customize;

import java.io.Serializable;

/**
 * @author fq
 * @version SmsCountCustomize, v0.1 2018/8/20 16:09
 */
public class SmsCountCustomize implements Serializable {
    private static final long serialVersionUID = 5615413541221L;

    private Integer id;

    //分公司ID
    private Integer departmentId;

    //分公司名字
    private String departmentName;

    //短信发送数量
    private Integer smsNumber;

    //短信发送时间
    private String posttime;

    //创建时间
    private String createTime;

    //短信金额
    private String smsMoney;

    /**
     * 提交时间区间查询开始时间
     */
    private String post_time_begin;

    /**
     * 提交时间区间查询结束时间
     */
    private String post_time_end;

    //
    private String[] combotreeListSrch;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getSmsNumber() {
        return smsNumber;
    }

    public void setSmsNumber(Integer smsNumber) {
        this.smsNumber = smsNumber;
    }

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String[] getCombotreeListSrch() {
        return combotreeListSrch;
    }

    public void setCombotreeListSrch(String[] combotreeListSrch) {
        this.combotreeListSrch = combotreeListSrch;
    }

    public String getSmsMoney() {
        return smsMoney;
    }

    public void setSmsMoney(String smsMoney) {
        this.smsMoney = smsMoney;
    }
}
