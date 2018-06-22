/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service;

import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.user.dao.model.auto.BindUser;
import com.hyjf.am.user.dao.model.auto.CertificateAuthority;
import com.hyjf.am.user.dao.model.auto.CorpOpenAccountRecord;
import com.hyjf.am.user.dao.model.customize.UserBankOpenAccountCustomize;
import com.hyjf.am.user.dao.model.customize.UserManagerCustomize;
import com.hyjf.am.user.dao.model.customize.UserManagerDetailCustomize;

import java.util.List;

/**
 * @author nxl
 * @version UserManagerService, v0.1 2018/6/20 9:47
 *          后台管理系统：会员中心->会员管理
 */
public interface UserManagerService {

    /**
     * 根据筛选条件查找会员列表
     *
     * @param userRequest
     * @return
     */
    List<UserManagerCustomize> selectUserMemberList(UserManagerRequest userRequest);

    /**
     * 根据条件查询用户管理总数
     *
     * @return
     */
    int countUserRecord(UserManagerRequest userRequest);

    /**
     * 根据用户id获取用户详情
     *
     * @param userId
     * @return
     */
    UserManagerDetailCustomize selectUserDetailById(int userId);

    /**
     * 根据用户id获取开户信息
     *
     * @param userId
     * @return
     */
    UserBankOpenAccountCustomize selectBankOpenAccountByUserId(int userId);

    /**
     * 根据用户id获取企业用户开户信息
     *
     * @param userId
     * @return
     */
    CorpOpenAccountRecord selectCorpOpenAccountRecordByUserId(int userId);

    /**
     * 根据用户id获取第三方平台绑定信息
     *
     * @param userId
     * @return
     */
    BindUser selectBindUserByUserId(int userId);

    /**
     * 根据用户id获取用户CA认证记录表
     *
     * @param userId
     * @return
     */
    CertificateAuthority selectCertificateAuthorityByUserId(int userId);

}
