package com.hyjf.cs.user.bean;

import io.swagger.annotations.ApiModelProperty;


/**
 *
 * @author Zha Daojian
 * @date 2018/12/14 9:32
 * @param
 * @return
 **/
public class AemsUserPostRequsettBean extends BaseBean {

    @ApiModelProperty(value = "加密的AEMS用户ID")
    private String bindUniqueIdScy;

    /** 返回Url */
    @ApiModelProperty(value = "加密的AEMS用户ID")
    private String retUrl;

    /** 平台id */
    @ApiModelProperty(value = "加密的AEMS用户ID")
    private Integer pid;

    /** 手机号码*/
    @ApiModelProperty(value = "加密的AEMS用户ID")
    private String mobile;

    /** 身份证号码*/
    @ApiModelProperty(value = "加密的AEMS用户ID")
    private String idCard;

    /** 姓名*/
    @ApiModelProperty(value = "加密的AEMS用户ID")
    private String name;

    /**
     * 验签
     */
    @ApiModelProperty(value = "验签")
    private String chkValue;
    /**
     * 当前时间戳（10位）
     */
    @ApiModelProperty(value = "当前时间戳（10位）")
    private Long timestamp;

    public String getBindUniqueIdScy() {
        return bindUniqueIdScy;
    }

    public void setBindUniqueIdScy(String bindUniqueIdScy) {
        this.bindUniqueIdScy = bindUniqueIdScy;
    }

    public String getRetUrl() {
        return retUrl;
    }

    public void setRetUrl(String retUrl) {
        this.retUrl = retUrl;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChkValue() {
        return chkValue;
    }

    public void setChkValue(String chkValue) {
        this.chkValue = chkValue;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
	
}
