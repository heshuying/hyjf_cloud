/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.customize.UserCrmInfoCustomize;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 用户CRM信息查询
 * @Date 2018/6/21 17:34
 */
public interface UserCrmInfoCustomizeMapper {

    List<UserCrmInfoCustomize> findUserCrmInfoByUserId(@Param("userId")Integer userId);
}
