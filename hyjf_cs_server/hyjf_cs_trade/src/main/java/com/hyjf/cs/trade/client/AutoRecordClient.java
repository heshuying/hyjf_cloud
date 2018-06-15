/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;

/**
 * @author fuqiang
 * @version AutoRecordClient, v0.1 2018/6/14 11:02
 */
public interface AutoRecordClient {
    /**
     * 查询标的
     * @param borrowNid
     * @return
     */
    BorrowVO selectBorrowByNid(String borrowNid);

    /**
     * 更新相应的标的状态为备案中
     * @param request
     * @return
     */
    boolean updateBorrowRegist(BorrowRegistRequest request);

    /**
     * 获取受托支付电子账户列表
     * @param entrustedUserId
     * @param instCode
     * @return
     */
    STZHWhiteListVO selectSTZHWhiteList(Integer entrustedUserId, String instCode);
}
