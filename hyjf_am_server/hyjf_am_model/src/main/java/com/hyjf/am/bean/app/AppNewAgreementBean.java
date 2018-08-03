/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.bean.app;

/**
 * @author dangzw
 * @version AppNewAgreementBean, v0.1 2018/7/31 17:08
 */
public class AppNewAgreementBean {

    //协议名称
    private String name;
    //	协议地址
    private String url;

    public AppNewAgreementBean(String name,String url){
        this.name=name;
        this.url=resetUrl(url);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    private String resetUrl(String partUrl) {
        return partUrl;
    }
}
