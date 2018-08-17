/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.auto.RUser;

/**
 * @author liubin
 * @version RUserCustomizeMapper, v0.1 2018/6/30 18:56
 */
public interface RUserCustomizeMapper {
    /**
     * 通过用户ID获取推荐人信息
     * @param userId
     * @return
     */
    RUser selectRefUserInfoByUserId(Integer userId);

    /**
     * 通过用户名称获取推荐人信息
     * @param userName
     * @return
     */
    RUser selectRefUserInfoByUserName(String userName);
}
