/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.*;

import java.util.List;

/**
 * @author nixiaoling
 * @version UserCenterClient, v0.1 2018/6/20 15:37
 */
public interface UserCenterClient {
    /**
     *查找用户信息
     * @param request
     * @return
     */
    List<UserManagerVO> selectUserMemberList(UserManagerRequest request);
    /**
     * 根据机构编号获取机构列表
     * @return
     */
    List<HjhInstConfigVO> selectInstConfigAll();

    /**
     * 根据筛选条件查找用户总数
     * @param request
     * @return
     */
    int  countRecordTotal(UserManagerRequest request);

    /**
     * 根据用户id获取用户详情
     * @param userId
     * @return
     */
    UserManagerDetailVO selectUserDetailById(String userId);

    /**
     * 根据用户id获取测评信息
     * @param userId
     * @return
     */
    UserEvalationResultVO getUserEvalationResult(String userId);
    /**
     * 根据用户id获取开户信息
     * @param userId
     * @return
     */
    UserBankOpenAccountVO selectBankOpenAccountByUserId(String userId);

    /**
     * 根据用户id获取企业用户开户信息
     * @param userId
     * @return
     */
    CorpOpenAccountRecordVO selectCorpOpenAccountRecordByUserId(String userId);

    /**
     * 根据用户id获取第三方平台绑定信息
     * @param userId
     * @return
     */
    BindUserVo selectBindeUserByUserId(String userId);

    /**
     * 根据用户id获取用户CA认证记录表
     * @param userId
     * @return
     */
    CertificateAuthorityVO selectCertificateAuthorityByUserId(String userId);

}