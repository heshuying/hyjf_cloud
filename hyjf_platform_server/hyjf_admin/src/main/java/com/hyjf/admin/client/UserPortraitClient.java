/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.response.user.UserPortraitResponse;
import com.hyjf.am.resquest.user.LoanCoverUserRequest;
import com.hyjf.am.resquest.user.UserPortraitRequest;
import com.hyjf.am.vo.user.LoanCoverUserVO;
import com.hyjf.am.vo.user.UserPortraitVO;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version LoanCoverClient, v0.1 2018/6/26 17:13
 */
public interface UserPortraitClient {
    /**
     * 根据参数查询用户画像信息
     * @param request
     * @return
     * @author nxl
     */
    UserPortraitResponse selectRecordList(UserPortraitRequest request);

    /**
     * 根据用户id查找用户画像
     * @param userId
     * @author nxl
     * @return
     */
    UserPortraitVO selectUsersPortraitByUserId(Integer userId);

    /**
     * 修改用户画像
     * @author nxl
     */
    int updateUserPortrait(UserPortraitRequest request);

}
