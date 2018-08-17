/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.bank;

/**
 * @author zhangqingqing
 * @version BankReturnConfig, v0.1 2018/8/3 14:21
 */
public interface BankReturnConfig {

    /**
     * 查询检证日志
     * @param logOrdId
     * @return
     */
    String getRetCode(String logOrdId);
}
