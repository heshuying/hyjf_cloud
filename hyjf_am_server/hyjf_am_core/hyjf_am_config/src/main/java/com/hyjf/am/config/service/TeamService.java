/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.Team;

/**
 * @author fuqiang
 * @version TeamService, v0.1 2018/7/9 16:46
 */
public interface TeamService {
    /**
     * 获取创始人信息 （最新数据）
     *
     * @return
     */
    Team getFounder();
}
