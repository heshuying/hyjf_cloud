/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.bean;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: sunpeikai
 * @version: NmcfLoginRequestBean, v0.1 2018/8/23 10:46
 */
@ApiModel(value = "纳觅财富第三方用户自动登录请求参数")
public class NmcfLoginRequestBean extends BaseVO implements Serializable {

    private static final long serialVersionUID = -20545325472232432L;

    @ApiModelProperty(value = "当前时间戳")
    private String timestamp;

    @ApiModelProperty(value = "机构编号")
    private String instCode;

    @ApiModelProperty(value = "第三方用户ID")
    private String userId;

    @ApiModelProperty(value = "跳转URL")
    private String retUrl;

    @ApiModelProperty(value = "标的编号")
    private String borrowNid;

    @ApiModelProperty(value = "签名")
    private String chkValue;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRetUrl() {
        return retUrl;
    }

    public void setRetUrl(String retUrl) {
        this.retUrl = retUrl;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getChkValue() {
        return chkValue;
    }

    public void setChkValue(String chkValue) {
        this.chkValue = chkValue;
    }
}
