/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.promotion.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.admin.PlatformCountRequest;
import com.hyjf.am.user.dao.mapper.customize.PlatformUserCountCustomizeMapper;
import com.hyjf.am.user.dao.model.customize.PlatformUserCountCustomize;
import com.hyjf.am.user.service.admin.promotion.PlatformCountService;

/**
 * @author fq
 * @version PlatformCountServiceImpl, v0.1 2018/8/13 9:54
 */
@Service("amUserPlatformCountServiceImpl")
public class PlatformCountServiceImpl implements PlatformCountService {
    @Autowired
    private PlatformUserCountCustomizeMapper customizeMapper;

    @Override
    public List<PlatformUserCountCustomize> getUserInfo(PlatformCountRequest request) {
        if (request.getTimeStartSrch() != null && request.getTimeEndSrch() != null) {
            return customizeMapper.getUserInfo(request.getTimeStartSrch(), request.getTimeEndSrch());
        }
        return null;
    }
}
