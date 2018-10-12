/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.market;

/**
 * @author zhangqingqing
 * @version AdsRequest, v0.1 2018/6/23 15:23
 */
public class AdsRequest {
    private int limitStart;
    private int limitEnd;
    private String host;
    private String code;

    private Integer typeId;

    private Integer isIndex;

    private String platformType;

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getIsIndex() {
        return isIndex;
    }

    public void setIsIndex(Integer isIndex) {
        this.isIndex = isIndex;
    }
}
