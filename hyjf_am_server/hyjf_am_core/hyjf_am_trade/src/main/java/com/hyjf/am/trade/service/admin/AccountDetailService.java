/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin;

import com.hyjf.am.trade.dao.model.customize.admin.AdminAccountDetailCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version AccountDetailService, v0.1 2018/6/29 13:56
 */
public interface AccountDetailService {

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
    int countAccountDetail(Map<String,Object> mapParam);


}
