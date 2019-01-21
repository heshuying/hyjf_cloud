/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.trade;

import com.hyjf.am.resquest.trade.ChinaPnrWithdrawRequest;
import com.hyjf.am.trade.dao.model.auto.ChinapnrLog;
import com.hyjf.am.trade.service.BaseService;

import java.util.List;

/**
 * 汇付
 * @author zhangqingqing
 * @version ChinapnrService, v0.1 2018/9/8 10:11
 */
public interface ChinapnrService extends BaseService {

    /**
     * 更新提现表
     * @param ordId
     * @param reason
     * @return
     */
    int updateAccountWithdrawByOrdId(String ordId, String reason);

    /**
     * 汇付体现后处理
     * @param chinaPnrWithdrawRequest
     * @return
     */
    boolean updateAfterCash(ChinaPnrWithdrawRequest chinaPnrWithdrawRequest);
}
