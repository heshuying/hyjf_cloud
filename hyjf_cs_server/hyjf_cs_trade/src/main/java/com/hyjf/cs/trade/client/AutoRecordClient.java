/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.assetpush.STZHWhiteListVO;
import com.hyjf.am.vo.borrow.BorrowVO;

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
     * @param borrowVO
     * @param i
     * @param i1
     * @return
     */
    boolean updateBorrowRegist(BorrowVO borrowVO, int i, int i1);

    STZHWhiteListVO selectSTZHWhiteList(Integer entrustedUserId, String instCode);
}
