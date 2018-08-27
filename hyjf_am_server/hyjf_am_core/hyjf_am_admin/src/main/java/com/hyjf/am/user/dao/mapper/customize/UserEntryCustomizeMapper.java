/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyjf.am.user.dao.model.auto.UserInfo;
/**
 * @author wangjun
 * @version UserEntryCustomizeMapper, v0.1 2018/6/12 16:06
 */
public interface UserEntryCustomizeMapper {
    /**
     * 查询符合条件的入职员工 列表
     * @return
     */
    List<UserInfo> queryEmployeeList();

    /**
     * 客户变员工后，其所推荐客户变为‘有主单’
     * @param referrer
     * @return
     */
    int updateSpreadAttribute(@Param("referrer") Integer referrer);

    /**
     * 查询符合条件的入职员工
     * @param userId
     * @return
     */
    List<UserInfo> queryEmployeeById(@Param("userId") Integer userId);
}
