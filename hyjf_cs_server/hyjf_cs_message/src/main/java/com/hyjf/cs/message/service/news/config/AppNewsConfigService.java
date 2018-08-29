/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.news.config;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.vo.user.UserVO;

/**
 * @author fq
 * @version AppNewsConfigService, v0.1 2018/7/25 15:49
 */
public interface AppNewsConfigService {
    /**
     * 开关闭推送服务
     * @param users
     * @return
     */
    IntegerResponse updateAppNewsConfig(UserVO users);
}
