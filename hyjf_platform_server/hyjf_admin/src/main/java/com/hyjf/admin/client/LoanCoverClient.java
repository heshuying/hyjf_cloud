/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.response.user.LoanCoverUserResponse;
import com.hyjf.am.resquest.user.LoanCoverUserRequest;
import com.hyjf.am.vo.user.LoanCoverUserVO;

import java.util.List;

/**
 * @author Administrator
 * @version LoanCoverClient, v0.1 2018/6/26 17:13
 */
public interface LoanCoverClient {

    /**
     * 查找借款盖章用户信息
     * @author nxl
     * @param request
     * @return
     */
    LoanCoverUserResponse selectUserMemberList(LoanCoverUserRequest request);
    /**
     * 保存借款盖章用户信息
     * @author nxl
     * @author nxl
     */
    int insertLoanCoverUser(LoanCoverUserRequest request);
    /**
     * 根据证件号码查找借款主体CA认证记录表
     * @param strIdNo
     * @author nxl
     * @return
     */
     LoanCoverUserVO selectIsExistsRecordByIdNo(String strIdNo);
    /**
     * 更新记录
     */
    int updateLoanCoverUserRecord(LoanCoverUserRequest request);
    /**
     * 根据id查找借款主体CA认证记录表
     * @param strId
     * @author nxl
     * @return
     */
    LoanCoverUserResponse selectIsExistsRecordById(String strId);

}
