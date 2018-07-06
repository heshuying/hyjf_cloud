/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance;

import com.hyjf.am.resquest.admin.MerchantAccountListRequest;
import com.hyjf.am.trade.dao.model.auto.MerchantAccount;
import com.hyjf.am.trade.service.BaseService;

import java.util.List;

/**
 * @author zhangqingqing
 * @version MerchantAccountService, v0.1 2018/7/5 13:43
 */
public interface MerchantAccountService extends BaseService {

    /**
     * 获取商户子账户列表
     * @return
     * @param request
     * @param offset
     * @param limit
     */
    List<MerchantAccount> selectRecordList(MerchantAccountListRequest request, int offset, int limit);

    Integer updateByPrimaryKeySelective(MerchantAccount merchantAccount);

    int countByExample();
}
