package com.hyjf.cs.market.client;

import com.hyjf.am.vo.datacollect.TzjDayReportVO;

import java.util.Set;

/**
 * @author xiasq
 * @version AmUserClient, v0.1 2018/7/6 11:04
 */
public interface AmUserClient {
    /**
     * 查询投之家当天注册人数、开户人数、绑卡人数
     * @return
     */
    TzjDayReportVO queryUserDataOnToday();

    /**
     * 查询投之家当天注册用户
     * @return
     */
    Set<Integer> queryRegisterUsersOnToday();
    /**
     * 查询投之家所有注册用户
     * @return
     */
    Set<Integer> queryAllTzjUsers();
}
