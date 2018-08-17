/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.common.impl;

import com.hyjf.am.vo.config.UserCornerVO;
import com.hyjf.am.vo.config.VersionVO;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
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
    /**
     * 根据设备唯一标识获取用户角标
     * @auth sunpeikai
     * @param sign 设备唯一标识
     * @return
     */
    @Override
    public UserCornerVO getUserCornerBySign(String sign) {
        return amConfigClient.getUserCornerBySign(sign);
    }

    /**
     * 更新用户角标数据
     * @auth sunpeikai
     * @param userCornerVO 用户角标数据
     * @return
     */
    @Override
    public Integer updateUserCorner(UserCornerVO userCornerVO) {
        return amConfigClient.updateUserCorner(userCornerVO);
    }

    /**
     * 插入一条新的用户角标数据
     * @auth sunpeikai
     * @param userCornerVO 用户角标数据
     * @return
     */
    @Override
    public Integer insertUserCorner(UserCornerVO userCornerVO) {
        return amConfigClient.insertUserCorner(userCornerVO);
    }
}
