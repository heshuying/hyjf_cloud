package com.hyjf.admin.service;

import com.hyjf.am.response.user.CertificateAuthorityResponse;
import com.hyjf.am.resquest.user.CertificateAuthorityRequest;

public interface CertificateAuthorityExceptionService {

	CertificateAuthorityResponse getRecordList(CertificateAuthorityRequest aprlr);

	CertificateAuthorityResponse updateUserCAMQ(String userId);

}
