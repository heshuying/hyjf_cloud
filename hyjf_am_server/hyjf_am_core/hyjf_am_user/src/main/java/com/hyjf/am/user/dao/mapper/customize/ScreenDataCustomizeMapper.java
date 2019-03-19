package com.hyjf.am.user.dao.mapper.customize;

import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author lisheng
 * @version QianleUserCustomizeMapper, v0.1 2018/8/21 18:06
 */

public interface ScreenDataCustomizeMapper {

    /**
     * 查询用户是否为无主单非千乐渠道
     * @return
     */
    HashMap<String,String> findUserGroupNotQianLe(@Param(value = "userId") Integer userId);

    /**
     * 查询用户为有主单
     * @return
     */
    HashMap<String,String> findUserGroup(@Param(value = "userId") Integer userId);

}
