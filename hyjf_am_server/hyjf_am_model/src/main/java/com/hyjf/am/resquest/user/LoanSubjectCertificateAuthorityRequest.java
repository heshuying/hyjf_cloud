/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.user;

import com.hyjf.am.resquest.Request;

/**
 * @author jun
 * @version LoanSubjectCertificateAuthorityRequest, v0.1 2018/6/27 15:27
 */
public class LoanSubjectCertificateAuthorityRequest extends Request {

	private String name;
	private Integer idType;
	private String idNo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
}
