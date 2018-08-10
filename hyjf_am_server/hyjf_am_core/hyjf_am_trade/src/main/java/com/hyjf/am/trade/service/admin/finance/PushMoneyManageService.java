/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance;

import com.hyjf.am.resquest.admin.PushMoneyRequest;
import com.hyjf.am.trade.dao.model.customize.PushMoneyCustomize;

import java.util.List;

/**
 * @author zdj
 * @version PushMoneyManageService, v0.1 2018/7/4 15:34
 */
public interface PushMoneyManageService {
    /**
     * 提成管理 （列表）
     *
     * @return
     */
    List<PushMoneyCustomize> selectPushMoneyList(PushMoneyRequest request);

    /**
     * 提成管理 （列表条数）
     *
     * @return
     */
    int countPushMoney(PushMoneyRequest request);
}
