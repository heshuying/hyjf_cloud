/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.app;

import com.hyjf.am.bean.app.AppNewAgreementBean;
import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.List;

/**
 * @author dangzw
 * @version AppNewAgreementVO, v0.1 2018/7/31 17:06
 */
public class AppNewAgreementVO extends BaseVO implements Serializable {

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = 5825234430507653546L;
    private Object info;
    private List<AppNewAgreementBean> list;
    private List<String> request;
    private String agreementImages;

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public List<AppNewAgreementBean> getList() {
        return list;
    }

    public void setList(List<AppNewAgreementBean> list) {
        this.list = list;
    }

    public String getAgreementImages() {
        return agreementImages;
    }

    public void setAgreementImages(String agreementImages) {
        this.agreementImages = agreementImages;
    }

    public List<String> getRequest() {
        return request;
    }

    public void setRequest(List<String> request) {
        this.request = request;
    }
}
