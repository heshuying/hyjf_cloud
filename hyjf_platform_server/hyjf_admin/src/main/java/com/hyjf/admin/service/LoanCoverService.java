/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.LoanCoverUserRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.am.response.user.LoanCoverUserResponse;
import com.hyjf.am.resquest.user.LoanCoverUserRequest;
import com.hyjf.am.vo.user.CertificateAuthorityVO;
import com.hyjf.am.vo.user.LoanCoverUserVO;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version LoanCoverService, v0.1 2018/6/26 17:10
 */
public interface LoanCoverService {
    /**
     * 查找借款盖章用户信息
     *
     * @param request
     * @return
     */
    LoanCoverUserResponse selectUserMemberList(LoanCoverUserRequest request);
    /**
     * 保存记录
     */
    int insertLoanCoverUser(LoanCoverUserRequest request);
    /**
     * 根据id查找记录是否存在
     */
    boolean selectIsExistsRecordByIdNo(String strIdNo);
    /**
     * 根据证件号码查找记录
     */
    LoanCoverUserVO selectRecordByIdNo(String strIdNo);
    /**
     * 更新记录
     */
    AdminResult updateLoanCoverUser(LoanCoverUserRequestBean loanCoverUserRequestBean);

    /**
     * 根据id查找借款主体CA认证记录表
     * @param id
     * @return
     */
    LoanCoverUserResponse getLoanCoverUserById(String id);

    /**
     * 更新借款主体CA认证记录表
     * @param loanCoverUserRequest
     * @return
     */
    boolean updateLoanCoverUserRecord(LoanCoverUserRequest loanCoverUserRequest);
    /**
     * 根据证件号码和姓名查找用户CA认证记录表
     * @param strIdNo
     * @param tureName
     * @return
     */
    CertificateAuthorityVO selectCertificateAuthorityByIdNoName(String strIdNo, String tureName);
}
