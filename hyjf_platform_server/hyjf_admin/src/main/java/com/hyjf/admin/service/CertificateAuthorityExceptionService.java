package com.hyjf.admin.service;

import com.hyjf.am.response.user.CertificateAuthorityResponse;
import com.hyjf.am.resquest.user.CertificateAuthorityExceptionRequest;

public interface CertificateAuthorityExceptionService {

	CertificateAuthorityResponse getRecordList(CertificateAuthorityExceptionRequest aprlr);

	CertificateAuthorityResponse updateUserCAMQ(String userId);

}
