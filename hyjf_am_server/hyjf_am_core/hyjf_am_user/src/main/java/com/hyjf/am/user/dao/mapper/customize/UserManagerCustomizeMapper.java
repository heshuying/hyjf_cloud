/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.resquest.user.UserManagerRequest;
import com.hyjf.am.user.dao.model.customize.UserManagerCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version UserManagerCustomizeMapper, v0.1 2018/6/20 10:01
 */
public interface UserManagerCustomizeMapper {

    /**
     *  根据筛选条件查找会员列表
     * @param userRequest 筛选条件
     * @return
     */
    List<UserManagerCustomize> selectUserMemberList(Map<String,Object> userRequest);
    /**
     * 根据条件获取用户列表总数
     * @param userRequest
     * @return
     */
    Integer countUserRecord(UserManagerRequest userRequest);
}
