package com.hyjf.cs.user.service.wrb.impl;

import com.hyjf.am.resquest.api.WrbRegisterRequest;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.client.CsMessageClient;
import com.hyjf.cs.user.service.wrb.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lisheng
 * @version UserRegisterServiceImpl, v0.1 2018/9/19 15:44
 */
@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    AmUserClient amUserClient;

    @Autowired
    CsMessageClient csMessageClient;

    @Override
    public HjhInstConfigVO selectHjhInstConfig(String instcode) {
        return amTradeClient.selectHjhInstConfig(instcode);
    }

    @Override
    public UtmPlatVO selectUtmPlatByUtmId(String utmId) {
        return amUserClient.selectUtmPlatByUtmId(utmId);
    }

    @Override
    public UserVO findUserByMobile(String mobile) {
        return amUserClient.findUserByMobile(mobile);
    }

    @Override
    public Integer selectByUserId(Integer userId, String instCode) {
        return amUserClient.getUserIdByBind(userId,Integer.valueOf(instCode));
    }

    @Override
    public UserVO checkUserByUserId(Integer userId) {
        return amUserClient.findUserById(userId);
    }

    @Override
    public UserInfoVO getUserInfoByUserId(Integer userId) {
        return amUserClient.findUserInfoById(userId);
    }

    @Override
    public boolean bindThirdUser(Integer userId, int bindUniqueId, Integer pid) {
        return amUserClient.bindThirdUser(userId,bindUniqueId,pid);
    }

    @Override
    public Integer updateUserInfoByUserInfo(UserInfoVO userInfoVO) {
        return amUserClient.updateUserInfoByUserInfo(userInfoVO);
    }

    @Override
    public Integer insertUserAction(WrbRegisterRequest wrbRegisterRequest) {
        return amUserClient.insertUserAction(wrbRegisterRequest);
    }

    @Override
    public boolean insertAppChannelStatisticsDetail(WrbRegisterRequest wrbRegisterRequest) {
        return csMessageClient.insertAppChannelStatisticsDetail(wrbRegisterRequest);
    }


}
