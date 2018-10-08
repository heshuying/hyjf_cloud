package com.hyjf.am.trade.service.task.issuerecover;

import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.HjhAssetBorrowtype;
import com.hyjf.am.trade.dao.model.auto.HjhPlanAsset;

/**
 * 汇计划自动发标修复
 * @author walter.limeng
 * @version AutoIssueRecoverJob, v0.1 2018/7/11 10:30
 */
public interface AutoRecordService {
    /**
     * @Author walter.limeng
     * @Description 根据BorrowNid获取borrow
     * @Date 17:48 2018/7/11
     * @Param borrowNid
     * @return Borrow
     */
    Borrow getBorrowByBorrowNid(String borrowNid);

    /**
     * @Author walter.limeng
     * @Description  判断该资产是否可以自动备案，是否关联计划
     * @Date 17:50 2018/7/11
     * @Param borrowInfo
     * @return HjhAssetBorrowtype
     */
    HjhAssetBorrowtype selectAssetBorrowType(BorrowInfo borrowInfo);

    /**
     * @Author walter.limeng
     * @Description  根据ID获取对象
     * @Date 17:52 2018/7/11
     * @Param borrowNid
     * @return
     */
    BorrowInfo getBorrowInfoById(String borrowNid);

    /**
     * @Author walter.limeng
     * @Description  标的自动备案
     * @Date 17:55 2018/7/11
     * @Param borrow
     * @Param borrowInfo
     * @return boolean
     */
    boolean updateRecordBorrow(Borrow borrow,BorrowInfo borrowInfo);

    /**
     * @Author walter.limeng
     * @Description  计划标自动备案
     * @Date 11:26 2018/9/27
     * @Param mqHjhPlanAsset
     * @Param hjhAssetBorrowType
     * @return
     */
    boolean updateRecordBorrow(HjhPlanAsset mqHjhPlanAsset, HjhAssetBorrowtype hjhAssetBorrowType);
}
