/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.exception;

import com.hyjf.am.resquest.admin.TenderCancelExceptionRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.dao.model.auto.BorrowTenderTmp;
import com.hyjf.am.vo.admin.FreezeHistoryVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: TenderCancelRepairService, v0.1 2018/7/11 10:42
 */
public interface TenderCancelRepairService {
    /**
     * 根据筛选条件查询银行出借撤销异常的数据count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    Integer getTenderCancelExceptionCount(TenderCancelExceptionRequest request);

    /**
     * 查询银行出借撤销异常列表
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    List<BorrowTenderTmp> searchTenderCancelExceptionList(TenderCancelExceptionRequest request);

    /**
     * 根据orderId查询BorrowTender
     * @auth sunpeikai
     * @param orderId 订单号
     * @return
     */
    List<BorrowTender> searchBorrowTenderByOrderId(String orderId);

    /**
     * 根据borrowNid查询BorrowTender
     * @auth zdj
     * @param borrowNid 订单号
     * @return List<BorrowTender> searchBorrowTenderByBorrowNid(String borrowNid);
     */
    List<BorrowTender> searchBorrowTenderByBorrowNid(String borrowNid);

    /**
     * 根据orderId查询BorrowTenderTmp
     * @auth sunpeikai
     * @param orderId 订单号
     * @return
     */
    List<BorrowTenderTmp> searchBorrowTenderTmpByOrderId(String orderId);

    /**
     * 根据id删除BorrowTenderTmp
     * @auth sunpeikai
     * @param id 主键
     * @return
     */
    Integer deleteBorrowTenderTmpById(Integer id);

    /**
     * 插入数据
     * @auth sunpeikai
     * @param freezeHistoryVO 冻结历史
     * @return
     */
    Integer insertFreezeHistory(FreezeHistoryVO freezeHistoryVO);
}
