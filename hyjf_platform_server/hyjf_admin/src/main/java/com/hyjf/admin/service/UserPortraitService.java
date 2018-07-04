/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.user.UserPortraitResponse;
import com.hyjf.am.resquest.user.LoanCoverUserRequest;
import com.hyjf.am.resquest.user.UserPortraitRequest;
import com.hyjf.am.vo.user.LoanCoverUserVO;
import com.hyjf.am.vo.user.UserPortraitVO;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version LoanCoverService, v0.1 2018/6/26 17:10
 */
public interface UserPortraitService {
    /**
     * 根据参数查询用户画像信息
     *
     * @param mapParam
     * @return
     */
    UserPortraitResponse selectRecordList(UserPortraitRequest userPortraitRequest);

    /**
     * 根据用户id查找用户画像
     *
     * @param userId
     * @return
     */
    UserPortraitVO selectUsersPortraitByUserId(Integer userId);

    /**
     * 修改用户画像
     */
    int updateUserPortrait(Map<String,Object>mapParam);

}
