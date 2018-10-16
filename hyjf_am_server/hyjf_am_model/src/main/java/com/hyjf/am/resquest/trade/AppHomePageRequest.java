package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.BaseVO;

/**
 * @Auther:yangchangwei
 * @Date:2018/10/16
 * @Description: app首页获取内容用
 */
public class AppHomePageRequest extends BaseVO {

    private int limitStart;

    private int limitEnd;

    private String host;

    private String userId;

    private String version;

    private String publishInstCode;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPublishInstCode() {
        return publishInstCode;
    }

    public void setPublishInstCode(String publishInstCode) {
        this.publishInstCode = publishInstCode;
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
