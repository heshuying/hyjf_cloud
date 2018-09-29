/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.UserPortraitService;
import com.hyjf.am.response.user.UserPortraitResponse;
import com.hyjf.am.resquest.user.UserPortraitRequest;
import com.hyjf.am.vo.user.UserPortraitVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nxl
 * @version LoanCoverServiceImpl, v0.1 2018/6/26 17:11
 */
@Service
public class UserPortraitServiceImpl implements UserPortraitService {
    @Autowired
    private AmUserClient userPortraitClient;
    /**
     * 根据参数查询用户画像信息
     * @return
     */
    @Override
    public UserPortraitResponse selectRecordList(UserPortraitRequest userPortraitRequest){
        UserPortraitResponse response =  userPortraitClient.selectRecordList(userPortraitRequest);
        return response;
    }

    /**
     * 根据用户id查找用户画像
     * @param userId
     * @return
     */
    @Override
    public UserPortraitVO selectUsersPortraitByUserId(Integer userId){
        UserPortraitVO userPortraitVO = userPortraitClient.selectUsersPortraitByUserId(userId);
        return userPortraitVO;
    }

    /**
     * 修改用户画像
     */
    @Override
    public int updateUserPortrait(UserPortraitRequest userPortraitRequest){
        int updFlg = userPortraitClient.updateUserPortrait(userPortraitRequest);
        return updFlg;
    }

}
