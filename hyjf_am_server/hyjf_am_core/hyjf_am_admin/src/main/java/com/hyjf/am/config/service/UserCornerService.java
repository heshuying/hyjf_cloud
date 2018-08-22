/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.UserCorner;
import com.hyjf.am.vo.config.UserCornerVO;

/**
 * @author: sunpeikai
 * @version: UserCornerService, v0.1 2018/7/26 11:38
 */
public interface UserCornerService {
    /**
     * 根据设备唯一标识获取角标数据
     * @auth sunpeikai
     * @param sign 设备唯一标识
     * @return
     */
    UserCorner getUserCornerBySign(String sign);

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
