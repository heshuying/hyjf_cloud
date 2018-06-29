/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.UserPortraitClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.UserPortraitResponse;
import com.hyjf.am.resquest.user.UserPortraitRequest;
import com.hyjf.am.vo.user.UserPortraitVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nxl
 * @version LoanCoverClientImpl, v0.1 2018/6/26 17:13
 */
@Service
public class UserPortraitClientImpl implements UserPortraitClient {
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 根据参数查询用户画像信息
     * @param request
     * @return
     */
    @Override
    public UserPortraitResponse selectRecordList(UserPortraitRequest request){
        UserPortraitResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/userPortraitManage/findUserPortraitRecord", request, UserPortraitResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
           return response;
        }
        return null;
    }

    /**
     * 根据用户id查找用户画像
     * @param userId
     * @return
     */
    @Override
    public UserPortraitVO selectUsersPortraitByUserId(Integer userId){
        UserPortraitResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/userPortraitManage/selectUserPortraitByUserId/"+ userId, UserPortraitResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 修改用户画像
     */
    @Override
    public int updateUserPortrait(UserPortraitRequest request){
        int response = restTemplate
                .postForEntity("http://AM-USER/am-user/userPortraitManage/updateUserPortraitRecord",request,Integer.class)
                .getBody();
        return response;
    }

}
