/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.customize.AdminUserPayAuthCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version UserManagerCustomizeMapper, v0.1 2018/6/20 10:01
 */
public interface UserPayAuthCustomizeMapper {

    /**
     * 获取到缴费授权的列表
     * @param authUser
     * @return
     */
    List<AdminUserPayAuthCustomize> selectUserPayAuthList(Map<String, Object> authUser);
    /**
     * 获取缴费授权的记录数目
     * @param userAuth
     * @return
     */
    int countRecordTotalPay(Map<String, Object> userAuth);
}
