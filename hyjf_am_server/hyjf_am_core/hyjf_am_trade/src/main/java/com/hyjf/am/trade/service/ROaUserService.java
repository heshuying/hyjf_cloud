package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.AccountTrade;
import com.hyjf.am.trade.dao.model.auto.ROaUsers;

import java.util.List;

/**
 * @Description crm user表操作
 * @Author sunss
 * @Date 2018/7/26 11:50
 */
public interface ROaUserService {
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
    Integer delete(ROaUsers users);
}
