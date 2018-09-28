/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigLogCustomizeVO, v0.1 2018/9/28 9:51
 */
public class BailConfigLogCustomizeVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "机构名称")
    private String instName;

    @ApiModelProperty(value = "修改字段")
    private String modifyColumn;

    @ApiModelProperty(value = "前值")
    private String beforeValue;

    @ApiModelProperty(value = "后值")
    private String afterValue;

    @ApiModelProperty(value = "修改人")
    private String createUserName;

    @ApiModelProperty(value = "修改日期")
    private String createTimeStr;

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

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getModifyColumn() {
        return modifyColumn;
    }

    public void setModifyColumn(String modifyColumn) {
        this.modifyColumn = modifyColumn;
    }

    public String getBeforeValue() {
        return beforeValue;
    }

    public void setBeforeValue(String beforeValue) {
        this.beforeValue = beforeValue;
    }

    public String getAfterValue() {
        return afterValue;
    }

    public void setAfterValue(String afterValue) {
        this.afterValue = afterValue;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}
