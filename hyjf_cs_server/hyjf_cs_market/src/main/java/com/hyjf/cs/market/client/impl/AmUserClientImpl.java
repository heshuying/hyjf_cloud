package com.hyjf.cs.market.client.impl;

import com.hyjf.am.vo.datacollect.TzjDayReportVO;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.cs.market.client.AmUserClient;

import java.util.Set;

/**
 * @author xiasq
 * @version AmUserClientImpl, v0.1 2018/7/6 11:04
 */
@Cilent
public class AmUserClientImpl implements AmUserClient {

    /**
     * 查询投之家当天注册人数、开户人数、绑卡人数
     * @return
     */
    @Override
    public TzjDayReportVO queryUserDataOnToday() {
        return null;
    }

    /**
     * 查询投之家当天注册用户
     * @return
     */
    @Override
    public Set<Integer> queryRegisterUsersOnToday() {
        return null;
    }

    /**
     * 查询投之家所有注册用户
     * @return
     */
    @Override
    public Set<Integer> queryAllTzjUsers() {
        return null;
    }
}
