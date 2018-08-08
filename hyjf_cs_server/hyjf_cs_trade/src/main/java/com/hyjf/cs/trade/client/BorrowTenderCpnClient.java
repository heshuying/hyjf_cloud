/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;

import java.util.List;

/**
 * @author yaoy
 * @version BorrowTenderCpnClient, v0.1 2018/6/25 15:45
 */
public interface BorrowTenderCpnClient {


    /**
     * @Author walter.limeng
     * @Description  更新borrowTenderCpn表
     * @Date 10:57 2018/7/18
     * @Param borrowTenderCpn
     * @return
     */
    int updateBorrowTenderCpn(BorrowTenderCpnVO borrowTenderCpn);

    /**
     * @Author walter.limeng
     * @Description  根据borrowNid获取优惠券放款数据
     * @Date 18:15 2018/7/18
     * @Param borrowNid
     * @return
     */
    List<BorrowTenderCpnVO> getBorrowTenderCpnList(String borrowNid);

}
