package com.hyjf.admin.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.client.CertificateAuthorityExceptionClient;
import com.hyjf.am.response.user.CertificateAuthorityResponse;
import com.hyjf.am.resquest.user.CertificateAuthorityRequest;

@Service
public class CertificateAuthorityExceptionClientImpl implements CertificateAuthorityExceptionClient {
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public CertificateAuthorityResponse getRecordList(CertificateAuthorityRequest aprlr) {
		String url = "http://AM-TRADE/am-user/certificate/search";
		CertificateAuthorityResponse response = restTemplate
				.postForEntity(url, aprlr, CertificateAuthorityResponse.class).getBody();
		if (response != null) {
			return response;
		}
		return null;
	}

	@Override
	public CertificateAuthorityResponse updateUserCAMQ(int userId) {
		String url = "http://AM-TRADE/am-user/certificate/modifyAction/";
		CertificateAuthorityResponse response = restTemplate
				.postForEntity(url, userId, CertificateAuthorityResponse.class).getBody();
		if (response != null) {
			return response;
		}
		return null;
	}

}
