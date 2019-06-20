/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.vo;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author nxl
 * @version TemplateConfigCustomizeVO, v0.1 2018/6/19 17:36
 * 推广中心-着陆页管理(列表）
 */
public class TemplateConfigCustomizeVO extends BaseVO implements Serializable {
    //主键
    @ApiModelProperty(value = "主键")
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 模板类型
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "模板类型")
    private Integer tempType;

    /**
     * 模板名称
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "模板名称")
    private String tempName;

    /**
     * 页面title
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "着陆页title")
    private String tempTitle;

    /**
     * 状态，1启用，0禁用
     *
     * @mbggenerated
     */
    @ApiModelProperty(value = "状态，1启用，0禁用")
    private String statusStr;

    @ApiModelProperty(value = "添加时间")
    private String createTimeStr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTempType() {
        return tempType;
    }

    public void setTempType(Integer tempType) {
        this.tempType = tempType;
    }

    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public String getTempTitle() {
        return tempTitle;
    }

    public void setTempTitle(String tempTitle) {
        this.tempTitle = tempTitle;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }
}
