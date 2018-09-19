package com.hyjf.am.trade.service.task.issuerecover;

import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.HjhAssetBorrowtype;

/**
 * @Auther: Walter
 * @Date: 2018/7/11 19:03
 * @Description:自动审核保证金
 */
public interface AutoBailMessageService {
    /**
     * @Author walter.limeng
     * @Description  根据borrowNid查询borrow
     * @Date 19:05 2018/7/11
     * @Param borrowNid
     * @return Borrow
     */
    Borrow getBorrowByBorrowNidrowNid(String borrowNid);

    /**
     * @Author walter.limeng
     * @Description  根据ID查询对象
     * @Date 19:08 2018/7/11
     * @Param borrowNid
     * @return
     */
    BorrowInfo getByBorrowNid(String borrowNid);

    /**
     * @Author walter.limeng
     * @Description  判断该资产是否可以自动审核保证金
     * @Date 19:13 2018/7/11
     * @Param borrowInfo
     * @Param borrow
     * @Param hjhAssetBorrowType
     * @return boolean
     */
    boolean updateRecordBorrow(BorrowInfo borrowInfo, Borrow borrow, HjhAssetBorrowtype hjhAssetBorrowType);
}
