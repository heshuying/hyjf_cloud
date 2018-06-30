/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin;

import com.hyjf.am.trade.dao.mapper.customize.admin.AdminAccountDetailCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.admin.AdminAccountDetailCustomize;
import com.hyjf.am.trade.service.admin.AccountDetailService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version AccountDetailServiceImpl, v0.1 2018/6/29 13:58
 */
public class AccountDetailServiceImpl implements AccountDetailService {
    @Autowired
    private AdminAccountDetailCustomizeMapper adminAccountDetailCustomizeMapper;

    /**
     * 资金明细 （列表）
     *
     * @param mapParam
     * @return
     */
    @Override
    public List<AdminAccountDetailCustomize> queryAccountDetails(Map<String,Object> mapParam){
        List<AdminAccountDetailCustomize> listAccountDetail = adminAccountDetailCustomizeMapper.queryAccountDetails(mapParam);
        return listAccountDetail;
    }
}
