/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.ActivityUserGuessResponse;
import com.hyjf.am.resquest.admin.ActivityUserGuessRequest; /**
 * @author yaoyong
 * @version ActivityUserGuessService, v0.1 2019/4/18 15:28
 */
public interface ActivityUserGuessService {
    /**
     * 获取竞猜列表
     * @param request
     * @return
     */
    ActivityUserGuessResponse getGuessList(ActivityUserGuessRequest request);
}
