/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.*;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserCenterService, v0.1 2018/6/20 15:34
 */
public interface UserCenterService {
    /**
     *查找用户信息
     * @param request
     * @return
     */
    Map<String,Object> selectUserMemberList(UserManagerRequest request);

    /**
     * 根据机构编号获取机构列表
     * @return
     */
    List<HjhInstConfigVO> selectInstConfigAll();

    /**
     * 根据用户id获取用户详情
     * @param userId
     * @return
     */
    UserManagerDetailVO selectUserDetail(String userId);

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
    BindUserVo selectBindeUserByUserI(String userId);

    /**
     * 根据用户id获取用户CA认证记录表
     * @param userId
     * @return
     */
    CertificateAuthorityVO selectCertificateAuthorityByUserId(String userId);

}
