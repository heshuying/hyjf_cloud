/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.news.config.impl;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.message.client.AmConfigClient;
import com.hyjf.cs.message.service.news.config.AppNewsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fq
 * @version AppNewsConfigServiceImpl, v0.1 2018/7/25 15:49
 */
@Service
public class AppNewsConfigServiceImpl implements AppNewsConfigService {
    @Autowired
    private AmConfigClient amConfigClient;

    @Override
    public int updateAppNewsConfig(UserVO users) {
        return amConfigClient.updateAppNewsConfig(users);
    }
}
