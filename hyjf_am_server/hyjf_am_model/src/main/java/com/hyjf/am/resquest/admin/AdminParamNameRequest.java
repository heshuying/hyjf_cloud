/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: sunpeikai
 * @version: AdminParamNameRequest, v0.1 2018/9/6 11:07
 */
@ApiModel(value = "系统中心-数据字典列表请求参数")
public class AdminParamNameRequest extends BasePage implements Serializable {
    private static final long serialVersionUID = 387630498860089654L;
    @ApiModelProperty(value = "字典区分(查询条件)")
    private String nameClassSrch;
    @ApiModelProperty(value = "字典编号(查询条件)")
    private String nameCdSrch;
    @ApiModelProperty(value = "字典名称(查询条件)")
    private String nameSrch;

    /**
     * 分页变量
     */
    private int limitStart = -1;
    private int limitEnd = -1;

    public String getNameClassSrch() {
        return nameClassSrch;
    }

    public void setNameClassSrch(String nameClassSrch) {
        this.nameClassSrch = nameClassSrch;
    }

    public String getNameCdSrch() {
        return nameCdSrch;
    }

    public void setNameCdSrch(String nameCdSrch) {
        this.nameCdSrch = nameCdSrch;
    }

    public String getNameSrch() {
        return nameSrch;
    }

    public void setNameSrch(String nameSrch) {
        this.nameSrch = nameSrch;
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
