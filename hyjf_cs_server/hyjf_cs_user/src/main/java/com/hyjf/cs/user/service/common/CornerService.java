/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.common;

import com.hyjf.am.vo.config.UserCornerVO;
import com.hyjf.am.vo.config.VersionVO;
import com.hyjf.cs.user.service.BaseUserService;

/**
 * @author zhangqingqing
 * @version CornerService, v0.1 2018/7/18 11:27
 */
public interface CornerService extends BaseUserService{
    /**
     * 获取版本信息
     * @param type
     * @return
     */
    VersionVO getNewVersionByType(Integer type);

    /**
     * 强制更新版本
     * @param type
     * @param isUpdate
     * @param version
     * @return
     */
    VersionVO getVersionByType(Integer type, Integer isUpdate, String version);

    /**
     * 根据设备唯一标识获取用户角标
     * @auth sunpeikai
     * @param sign 设备唯一标识
     * @return
     */
    UserCornerVO getUserCornerBySign(String sign);

    /**
     * 更新用户角标数据
     * @auth sunpeikai
     * @param userCornerVO 用户角标数据
     * @return
     */
    Integer updateUserCorner(UserCornerVO userCornerVO);

    /**
     * 插入一条新的用户角标数据
     * @auth sunpeikai
     * @param userCornerVO 用户角标数据
     * @return
     */
    Integer insertUserCorner(UserCornerVO userCornerVO);
}
