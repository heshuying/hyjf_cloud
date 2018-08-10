/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.auto.AccountTrade;
import com.hyjf.am.trade.dao.model.customize.AdminAccountDetailCustomize;
import com.hyjf.am.trade.dao.model.customize.AdminAccountDetailDataRepairCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version AdminAccountDetailCustomizeMapper, v0.1 2018/6/29 13:52
 */
public interface AdminAccountDetailCustomizeMapper {

    /**
     * 资金明细 （列表）
     *
     * @param mapParam
     * @return
     */
    List<AdminAccountDetailCustomize> queryAccountDetails(Map<String,Object> mapParam);

    /**
     * 查询总数
     * @param mapParam
     * @return
     */
    int queryAccountDetailCount(Map<String,Object> mapParam);
    /**
     * 查询出20170120还款后,交易明细有问题的用户ID
     */
    List<AdminAccountDetailDataRepairCustomize> queryAccountDetailErrorUserList();

    /**
     * 查询交易明细最小的id
     * @param userId
     * @return
     */
    List<AdminAccountDetailDataRepairCustomize> queryAccountDetailIdByUserId(int userId);

    /**
     * 根据查询用交易类型查询用户操作金额
     * @param tradValue
     * @return
     */
    List<AccountTrade> selectAccountTrade(String tradValue);

}
