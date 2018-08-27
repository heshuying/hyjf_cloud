/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.repayauth;

import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

/**
 * @author nxl
 * 还款授权
 */
public interface RepayAuthPageService extends BaseUserService {
    /**
     * 插入日志
     * @param userId
     * @param bean
     * @param client
     */
    void insertUserAuthLog(int userId, BankCallBean bean, Integer client);

}
