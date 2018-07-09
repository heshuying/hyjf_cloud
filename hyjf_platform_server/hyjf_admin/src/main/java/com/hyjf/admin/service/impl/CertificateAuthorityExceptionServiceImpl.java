package com.hyjf.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.hyjf.admin.client.CertificateAuthorityExceptionClient;
import com.hyjf.admin.service.CertificateAuthorityExceptionService;
import com.hyjf.am.response.user.CertificateAuthorityResponse;
import com.hyjf.am.resquest.user.CertificateAuthorityRequest;

public class CertificateAuthorityExceptionServiceImpl implements CertificateAuthorityExceptionService {
	@Autowired
	private CertificateAuthorityExceptionClient certificateAuthorityExceptionClient;
	@Override
	public CertificateAuthorityResponse getRecordList(CertificateAuthorityRequest aprlr) {
		
		return certificateAuthorityExceptionClient.getRecordList(aprlr);
	}

	@Override
	public CertificateAuthorityResponse updateUserCAMQ(String userId) {

		return certificateAuthorityExceptionClient.updateUserCAMQ(Integer.valueOf(userId));
	}

	

}
