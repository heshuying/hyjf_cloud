/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.landingpage;

import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.cs.user.vo.LandingPageResulltVO;

/**
 * @author wangjun
 * @version LandingPageService, v0.1 2018/7/30 16:39
 */
public interface LandingPageService extends BaseUserService {
    LandingPageResulltVO getUserData();
}
