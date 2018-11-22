/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.customize.UserPayAuthCustomize;

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
    List<UserPayAuthCustomize> selectUserPayAuthList(Map<String, Object> authUser);
    /**
     * 获取缴费授权的记录数目
     * @param userAuth
     * @return
     */
    int countRecordTotalPay(Map<String, Object> userAuth);
    /**
     * 获取还款记录数
     * @param userAuth
     * @return
     */
    int countRecordTotalRePay(Map<String, Object> userAuth);

    /**
     * 查找还款记录
     * @param authUser
     * @return
     */
    List<UserPayAuthCustomize> selectUserRePayAuthList(Map<String, Object> authUser);



}
