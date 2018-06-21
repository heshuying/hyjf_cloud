/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.UserCenterClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.response.user.UserManagerResponse;
import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.UserManagerVO;
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
     *查找用户信息
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
     * @param instCode
     * @return
     */
    @Override
    public List<HjhInstConfigVO> selectHjhInstConfigListByInstCode(String instCode) {
        HjhInstConfigResponse response = restTemplate.
                getForEntity("http://AM-TRADE/am-trade/trade/selectInstConfigListByInstCode/" + instCode, HjhInstConfigResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据筛选条件查找用户总数
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
}
