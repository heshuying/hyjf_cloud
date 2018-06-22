/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.pandect;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.service.BaseUserService;

import java.util.Map;

/**
 * @author zhangqingqing
 * @version PandectService, v0.1 2018/6/21 14:37
 */
public interface PandectService extends BaseUserService {

    Map<String,Object> pandect(UserVO user);
}
