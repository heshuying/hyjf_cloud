/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch;

/**
 * @author yaoyong
 * @version LateAndCreditService, v0.1 2018/12/18 17:01
 */
public interface LateAndCreditService {
    /**
     * 互金拉取逾期和完全债转数据更新合同状态
     */
    void updateRepayInfo();
}
