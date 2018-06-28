/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

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
     *
     * @param request
     * @return
     */
    List<LoanCoverUserVO> selectUserMemberList(LoanCoverUserRequest request);
    /**
     * 保存记录
     */
    int insertLoanCoverUser(LoanCoverUserRequest request);
    /**
     * 保存记录
     */
    LoanCoverUserVO selectIsExistsRecordByIdNo(String strIdNo);
    /**
     * 更新记录
     */
    int updateLoanCoverUserRecord(LoanCoverUserRequest request);
    /**
     * 根据id查找记录
     */
    LoanCoverUserVO selectIsExistsRecordById(String strId);

}
