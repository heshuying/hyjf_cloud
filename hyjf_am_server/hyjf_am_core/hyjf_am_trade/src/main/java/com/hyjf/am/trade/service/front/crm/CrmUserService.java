package com.hyjf.am.trade.service.front.crm;

import com.hyjf.am.trade.dao.model.auto.ROaUsers;
import com.hyjf.am.trade.dao.model.auto.ROaUsersExample;

/**
 * @Description crm user表操作
 * @Author sunss
 * @Date 2018/7/26 11:50
 */
public interface CrmUserService {
    /**
     * 修改
     * @param users
     * @return
     */
    Integer update(ROaUsers users);

    /**
     * 新增
     * @param users
     * @return
     */
    Integer insert(ROaUsers users);

    /**
     * 删除
     * @param users
     * @return
     */
    Integer delete(Integer users);

    /**
     * 修改用户
     * @param user
     * @param example
     */
    void updateByExample(ROaUsers user, ROaUsersExample example);
}
