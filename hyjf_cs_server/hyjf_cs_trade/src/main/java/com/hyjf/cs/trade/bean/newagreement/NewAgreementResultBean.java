/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.bean.newagreement;

import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;

import java.util.List;

/**
 * @author libin
 * @version NewAgreementResultBean.java, v0.1 2018年7月25日 下午2:36:44
 */
public class NewAgreementResultBean extends BaseResultBeanFrontEnd {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
    private Object info;
    private List<NewAgreementBean> list;
    private List<String> request;
    private String agreementImages;

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public List<NewAgreementBean> getList() {
        return list;
    }

    public void setList(List<NewAgreementBean> list) {
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
