/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.trade;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.trade.dao.model.auto.StzhWhiteList;
import com.hyjf.am.trade.service.BaseService;

/**
 * @author wangjun
 * @version TrusteePayService, v0.1 2018/8/27 15:48
 */
public interface TrusteePayService extends BaseService {
    /**
     * 借款人受托支付申请异步回调更新
     *
     * @param borrowNid
     * @return
     */
    BooleanResponse update(String borrowNid);

    /**
     * 查询受托白名单
     *
     * @param instCode
     * @param receiptAccountId
     * @return
     */
    StzhWhiteList getSTZHWhiteList(String instCode, String receiptAccountId);
}
