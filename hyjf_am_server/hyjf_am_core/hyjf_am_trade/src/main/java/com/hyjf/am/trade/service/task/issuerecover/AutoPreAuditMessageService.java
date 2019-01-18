package com.hyjf.am.trade.service.task.issuerecover;

import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.HjhPlanAsset;
import com.hyjf.am.trade.service.BaseService;

/**
 * @Author walter.limeng
 * @Description  自动初审消息
 * @Date 9:23 2018/7/12
 */
public interface AutoPreAuditMessageService extends BaseService {
    
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
     * 资产自动初审
     * @param hjhPlanAsset
     * @return
     */
    boolean updateRecordBorrow(HjhPlanAsset hjhPlanAsset);
}
