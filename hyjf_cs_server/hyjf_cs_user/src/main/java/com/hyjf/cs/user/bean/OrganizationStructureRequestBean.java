/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.bean;

/**
 * @author: sunpeikai
 * @version: OrganizationStructureRequestBean, v0.1 2018/6/27 9:41
 */
public class OrganizationStructureRequestBean extends BaseBean {
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
