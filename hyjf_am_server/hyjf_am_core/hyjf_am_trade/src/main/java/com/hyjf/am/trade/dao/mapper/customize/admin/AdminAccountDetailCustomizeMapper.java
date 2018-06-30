/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize.admin;

import com.hyjf.am.trade.dao.model.customize.admin.AdminAccountDetailCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
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
}
