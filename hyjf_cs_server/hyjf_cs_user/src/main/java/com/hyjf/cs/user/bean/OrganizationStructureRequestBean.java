/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: sunpeikai
 * @version: OrganizationStructureRequestBean, v0.1 2018/6/27 9:41
 */
@ApiModel(value = "集团组织结构请求参数")
public class OrganizationStructureRequestBean extends BaseBean {
    @ApiModelProperty(value = "机构编号")
    private String instCode;

    @Override
    public String getInstCode() {
        return instCode;
    }

    @Override
    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }
}
