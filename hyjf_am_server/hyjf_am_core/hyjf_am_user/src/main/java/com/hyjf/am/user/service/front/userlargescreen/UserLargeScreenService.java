/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.userlargescreen;

import com.hyjf.am.response.user.UserCustomerTaskConfigResponse;
import com.hyjf.am.response.user.UserScreenConfigResponse;
import com.hyjf.am.resquest.admin.UserLargeScreenRequest;

import java.util.List;

/**
 * @author tanyy
 * @version UserLargeScreenService, v0.1 2019/3/18 14:28
 */
public interface UserLargeScreenService {
    /**
     * 大屏幕运营部配置获取
     * @return
     */
    UserScreenConfigResponse getScreenConfig(UserLargeScreenRequest request);
    /**
     * 坐席月任务配置
     * @return
     */
    UserCustomerTaskConfigResponse getCustomerTaskConfig(UserLargeScreenRequest request);

    /**
     * 运营部所有用户id
     * @return
     */
    List<Integer> getOperUserIds();
}
