/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.bean.newagreement;

/**
 * @author libin
 * @version NewAgreementBean.java, v0.1 2018年7月25日 下午2:31:48
 */
public class NewAgreementBean {
	//	协议名称
	private String name;
	//	协议地址
	private String url;
	
	public NewAgreementBean(String name,String url){
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
