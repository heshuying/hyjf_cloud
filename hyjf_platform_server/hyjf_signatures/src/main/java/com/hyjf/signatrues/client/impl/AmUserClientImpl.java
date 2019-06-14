package com.hyjf.signatrues.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.CorpOpenAccountRecordResponse;
import com.hyjf.am.response.user.*;
import com.hyjf.am.resquest.user.*;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.signatrues.client.AmUserClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 
 * @Author pangchengchao
 * @Version v0.1
 * @Date  
 */

@Service
public class AmUserClientImpl implements AmUserClient {
	public static final String urlBase = "http://AM-USER/am-user/";

	@Resource
	private RestTemplate restTemplate;
	@Value("${am.user.service.name}")
	private String userService;
	@Override
	public UserVO findUserById(int userId) {
		UserResponse response = restTemplate
				.getForEntity(urlBase + "user/findById/" + userId, UserResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public UserInfoVO findUsersInfoById(int userId) {
		UserInfoResponse response = restTemplate
				.getForEntity(urlBase + "userInfo/findById/" + userId, UserInfoResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public CorpOpenAccountRecordVO selectCorpOpenAccountRecordByUserId(Integer userId) {
		CorpOpenAccountRecordResponse response = restTemplate
				.getForEntity("http://AM-USER/am-user/userManager/selectCorpOpenAccountRecordByUserId/" + userId, CorpOpenAccountRecordResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}



	@Override
	public CertificateAuthorityVO selectCertificateAuthorityByUserId(String userId) {
		  CertificateAuthorityResponse response = restTemplate.
	                getForEntity("http://AM-USER/am-user/userManager/selectCertificateAuthorityByUserId/" + userId, CertificateAuthorityResponse.class).
	                getBody();
	        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
	            return response.getResult();
	        }
	        return null;
	}

	@Override
	public List<CertificateAuthorityVO> getCertificateAuthorityList(CertificateAuthorityRequest request) {
		String url ="http://AM-USER/am-user/user/getCertificateAuthorityList";
		CertificateAuthorityResponse response = restTemplate.postForEntity(url,request,CertificateAuthorityResponse.class).getBody();
		if(Validator.isNotNull(response)) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public List<LoanSubjectCertificateAuthorityVO> getLoanSubjectCertificateAuthorityList(
			LoanSubjectCertificateAuthorityRequest request1) {
		String url = "http://AM-USER/am-user/user/getLoanSubjectCertificateAuthorityList";
		LoanSubjectCertificateAuthorityResponse response = restTemplate.postForEntity(url,request1,LoanSubjectCertificateAuthorityResponse.class).getBody();
		if(Validator.isNotNull(response)) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 通过userID获得CA认证的客户ID
	 */
	@Override
	public String getCustomerIDByUserID(Integer userId, String code) {
		String url ="http://AM-USER/am-user/user/getCustomerIDByUserID/"+userId+"/"+code;
		return restTemplate.getForEntity(url, String.class).getBody();
	}



}
