package com.hyjf.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.CertificateAuthorityExceptionService;
import com.hyjf.am.response.user.CertificateAuthorityResponse;
import com.hyjf.am.resquest.user.CertificateAuthorityExceptionRequest;
import com.hyjf.am.resquest.user.CertificateAuthorityRequest;

@Service
public class CertificateAuthorityExceptionServiceImpl implements CertificateAuthorityExceptionService {
	@Autowired
	private AmUserClient certificateAuthorityExceptionClient;
	@Override
	public CertificateAuthorityResponse getRecordList(CertificateAuthorityExceptionRequest aprlr) {
		
		return certificateAuthorityExceptionClient.getRecordList(aprlr);
	}

	@Override
	public CertificateAuthorityResponse updateUserCAMQ(String userId) {

		return certificateAuthorityExceptionClient.updateUserCAMQ(Integer.valueOf(userId));
	}

	/**
	 * 获取CA认证异常的列表
	 * @param request
	 * @return
	 */
	@Override
	public CertificateAuthorityResponse getExceptionRecordList(CertificateAuthorityExceptionRequest request) {
		return certificateAuthorityExceptionClient.getExceptionRecordList(request);
	}

	

}
