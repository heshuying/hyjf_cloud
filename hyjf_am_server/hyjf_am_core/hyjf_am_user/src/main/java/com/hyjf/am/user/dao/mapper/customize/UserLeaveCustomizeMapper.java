/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.customize.AdminEmployeeLeaveCustomize;
import com.hyjf.am.user.dao.model.customize.UserUpdateParamCustomize;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangjun
 * @version UserLeaveCustomizeMapper, v0.1 2018/6/12 16:06
 */
public interface UserLeaveCustomizeMapper {

    /**
     * 查询符合条件的离职员工 列表
     * @return
     */
    List<User> queryEmployeeList();

    /**
     * 员工离职后，其所推荐客户变为‘无主单’
     * @param referrer
     * @return
     */
    int updateSpreadAttribute(@Param("referrer") Integer referrer);

    /**
     * 根据关联关系查询OA表的内容,得到部门的线上线下属性
     * @return
     */
    List<UserUpdateParamCustomize> queryUserAndDepartment(@Param("userId") Integer userId);

    /**
     * 查询符合条件的离职员工
     * @param userId
     * @return
     */
    List<User> queryEmployeeById(@Param("userId") Integer userId);

    /**
     * 根据用户id查找离职信息
     * @param userId
     * @return
     */
    List<AdminEmployeeLeaveCustomize> selectUserLeaveByUserId(@Param("userId") Integer userId);
}
