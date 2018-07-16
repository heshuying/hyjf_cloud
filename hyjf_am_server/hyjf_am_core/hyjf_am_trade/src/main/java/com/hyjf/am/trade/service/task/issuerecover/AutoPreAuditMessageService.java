package com.hyjf.am.trade.service.task.issuerecover;

import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.HjhAssetBorrowtype;
import com.hyjf.am.trade.dao.model.auto.HjhPlanAsset;

/**
 * @Author walter.limeng
 * @Description  自动初审消息
 * @Date 9:23 2018/7/12
 */
public interface AutoPreAuditMessageService {
    
    /**
     * @Author walter.limeng
     * @Description  手动录标自动备案-自动初审
     * @Date 9:26 2018/7/12
     * @Param borrow
     * @Param borrowInfo
     * @return boolean
     */
    boolean updateRecordBorrow(Borrow borrow, BorrowInfo borrowInfo);

    /**
     * @Author walter.limeng
     * @Description  根据资产编号和机构编号查询对象
     * @Date 10:15 2018/7/12
     * @Param assetId 资产编号
     * @Param instCode 机构编号
     * @return HjhPlanAsset
     */
    HjhPlanAsset selectPlanAsset(String assetId, String instCode);

    /**
     * @Author walter.limeng
     * @Description 获取资产项目类型
     * @Date 10:21 2018/7/12
     * @Param hjhPlanAsset
     * @return HjhAssetBorrowtype
     */
    HjhAssetBorrowtype selectAssetBorrowType(HjhPlanAsset hjhPlanAsset);

    /**
     * @Author walter.limeng
     * @Description  资产自动初审
     * @Date 10:40 2018/7/12
     * @Param hjhPlanAsset
     * @Param hjhAssetBorrowType
     * @return boolean
     */
    boolean updateRecordBorrow(HjhPlanAsset hjhPlanAsset, HjhAssetBorrowtype hjhAssetBorrowType);
}
