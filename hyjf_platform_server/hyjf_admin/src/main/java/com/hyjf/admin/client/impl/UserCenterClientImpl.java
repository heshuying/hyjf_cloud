/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.UserCenterClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.CorpOpenAccountRecordResponse;
import com.hyjf.am.response.user.*;
import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author nixiaoling
 * @version UserCenterClientImpl, v0.1 2018/6/20 15:38
 */
@Service
public class UserCenterClientImpl implements UserCenterClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查找用户信息
     *
     * @param request
     * @return
     */
    @Override
    public List<UserManagerVO> selectUserMemberList(UserManagerRequest request) {
        UserManagerResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/userManager/userslist", request, UserManagerResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据机构编号获取机构列表
     *
     * @return
     */
    @Override
    public List<HjhInstConfigVO> selectInstConfigAll() {
        HjhInstConfigResponse response = restTemplate.
                getForEntity("http://AM-TRADE/am-trade/hjhInstConfig/selectInstConfigAll", HjhInstConfigResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据筛选条件查找用户总数
     *
     * @param request
     * @return
     */
    @Override
    public int countRecordTotal(UserManagerRequest request) {
        UserManagerResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/userManager/countUserList", request, UserManagerResponse.class)
                .getBody();
        return response.getCount();
    }

    /**
     * 根据用户id获取用户详情
     *
     * @param userId
     * @return
     */
    @Override
    public UserManagerDetailVO selectUserDetailById(String userId) {
        UserManagerDetailResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/userManager/selectUserDetail/{userId}" + userId, UserManagerDetailResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据用户id获取测评信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserEvalationResultVO getUserEvalationResult(String userId) {
        UserEvalationResultResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/user/selectUserEvalationResultByUserId/{userId}" + userId, UserEvalationResultResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据用户id获取开户信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserBankOpenAccountVO selectBankOpenAccountByUserId(String userId) {
        UserBankOpenAccountResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/userManager/selectBankOpenAccountByUserId/{userId}" + userId, UserBankOpenAccountResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据用户id获取企业用户开户信息
     *
     * @param userId
     * @return
     */
    @Override
    public CorpOpenAccountRecordVO selectCorpOpenAccountRecordByUserId(String userId) {
        CorpOpenAccountRecordResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/userManager/selectCorpOpenAccountRecordByUserId/{userId}" + userId, CorpOpenAccountRecordResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据用户id获取第三方平台绑定信息
     *
     * @param userId
     * @return
     */
    @Override
    public BindUserVo selectBindeUserByUserId(String userId) {
        BindUserResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/userManager/selectBindUserByUserId/{userId}" + userId, BindUserResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据用户id获取用户CA认证记录表
     *
     * @param userId
     * @return
     */
    @Override
    public CertificateAuthorityVO selectCertificateAuthorityByUserId(String userId) {
        CertificateAuthorityResponse response = restTemplate.
                getForEntity("http://AM-USER/am-user/userManager//selectCertificateAuthorityByUserId/{userId}" + userId, CertificateAuthorityResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }
}
