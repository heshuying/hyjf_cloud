package com.hyjf.signatrues.client;


import com.hyjf.am.resquest.user.CertificateAuthorityRequest;
import com.hyjf.am.resquest.user.LoanSubjectCertificateAuthorityRequest;

import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.*;

import java.util.List;

/**
 * @Description 
 * @Author pangchengchao
 * @Version v0.1
 * @Date  
 */
public interface AmUserClient {

	UserVO findUserById(int userId);

	UserInfoVO findUsersInfoById(int userId);

	CorpOpenAccountRecordVO selectCorpOpenAccountRecordByUserId(Integer userId);

	/**
	 * 根据用户id获取用户CA认证记录表
	 * @return
	 */
	CertificateAuthorityVO selectCertificateAuthorityByUserId(String userId);

	/**
	 * 通过userID获得CA认证的客户ID
	 * @param userId
	 * @param code
	 * @return
	 */
	String getCustomerIDByUserID(Integer userId, String code);

	/**
	 * 用户CA认证记录表
	 * @param request
	 * @return
	 */
	List<CertificateAuthorityVO> getCertificateAuthorityList(CertificateAuthorityRequest request);

	/**
	 * 借款主体CA认证记录表
	 * @param request1
	 * @return
	 */
	List<LoanSubjectCertificateAuthorityVO> getLoanSubjectCertificateAuthorityList(
			LoanSubjectCertificateAuthorityRequest request1);

}
