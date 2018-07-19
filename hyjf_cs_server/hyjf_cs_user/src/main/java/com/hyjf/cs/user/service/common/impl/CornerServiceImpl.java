/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.common.impl;

import com.hyjf.am.vo.config.VersionVO;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.service.BaseUserServiceImpl;
import com.hyjf.cs.user.service.common.CornerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangqingqing
 * @version CornerServiceImpl, v0.1 2018/7/18 11:28
 */
@Service
public class CornerServiceImpl extends BaseUserServiceImpl implements CornerService {

    @Autowired
    AmConfigClient amConfigClient;

    @Override
    public VersionVO getNewVersionByType(Integer type) {
        return amConfigClient.getNewVersionByType(type);
    }

    @Override
    public VersionVO getVersionByType(Integer type, Integer isupdate, String versionStr) {
        return amConfigClient.getUpdateversion(type,isupdate,versionStr);
    }
}
