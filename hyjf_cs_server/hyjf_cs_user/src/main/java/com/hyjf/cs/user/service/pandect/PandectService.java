/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.pandect;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.service.BaseUserService;

/**
 * @author zhangqingqing
 * @version PandectService, v0.1 2018/6/21 14:37
 */
public interface PandectService extends BaseUserService {

    /**
     * 获取账户总览信息
     * @param user
     * @return
     */
    JSONObject pandect(Integer userId);
}
