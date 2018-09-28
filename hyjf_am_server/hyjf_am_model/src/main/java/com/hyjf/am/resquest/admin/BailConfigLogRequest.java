/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigLogRequest, v0.1 2018/9/28 9:42
 */
public class BailConfigLogRequest extends BasePage implements Serializable {

    @ApiModelProperty(value = "机构编号查询")
    private String instCodeSrch;

    @ApiModelProperty(value = "修改字段查询")
    private String modifyColumnSrch;

    @ApiModelProperty(value = "修改人查询")
    private String createUserNameSrch;

    @ApiModelProperty(value = "添加时间开始查询")
    private String startDate;

    @ApiModelProperty(value = "添加时间结束查询")
    private String endDate;

    /**
     * 分页变量
     */
    private int limitStart = -1;
    private int limitEnd = -1;

    private int initQuery;

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

    public int getInitQuery() {
        return initQuery;
    }

    public void setInitQuery(int initQuery) {
        this.initQuery = initQuery;
    }

    public String getInstCodeSrch() {
        return instCodeSrch;
    }

    public void setInstCodeSrch(String instCodeSrch) {
        this.instCodeSrch = instCodeSrch;
    }

    public String getModifyColumnSrch() {
        return modifyColumnSrch;
    }

    public void setModifyColumnSrch(String modifyColumnSrch) {
        this.modifyColumnSrch = modifyColumnSrch;
    }

    public String getCreateUserNameSrch() {
        return createUserNameSrch;
    }

    public void setCreateUserNameSrch(String createUserNameSrch) {
        this.createUserNameSrch = createUserNameSrch;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
