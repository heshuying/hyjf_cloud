/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.LoanCoverUserRequest;
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
    List<LoanCoverUserVO> selectUserMemberList(LoanCoverUserRequest request);
    /**
     * 保存记录
     */
    int insertLoanCoverUser(LoanCoverUserRequest request);
    /**
     * 保存记录
     */
    boolean selectIsExistsRecordByIdNo(String strIdNo);
    /**
     * 根据id查找记录
     */
    LoanCoverUserVO selectRecordByIdNo(String strIdNo);
    /**
     * 更新记录
     */
    JSONObject updateLoanCoverUser(Map<String,Object> mapParam);
}
