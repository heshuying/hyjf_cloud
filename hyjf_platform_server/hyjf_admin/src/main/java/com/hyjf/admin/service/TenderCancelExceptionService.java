/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.TenderCancelExceptionRequest;
import com.hyjf.am.vo.trade.borrow.BorrowTenderTmpVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: TenderCancelExceptionService, v0.1 2018/7/11 10:00
 */
public interface TenderCancelExceptionService {

    /**
     * 根据筛选条件查询银行投资撤销异常的数据count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    Integer getTenderCancelExceptionCount(TenderCancelExceptionRequest request);

    /**
     * 根据筛选条件查询银行投资撤销异常list
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    List<BorrowTenderTmpVO> searchTenderCancelExceptionList(TenderCancelExceptionRequest request);

    /**
     * 根据borrowNid查询borrow
     * @auth sunpeikai
     * @param borrowNid 项目编号
     * @return
     */
    BorrowVO getBorrowByBorrowNid(String borrowNid);

    /**
     * 投资撤销异常处理
     * @auth sunpeikai
     * @param request 主要包含orderId
     * @return
     */
    JSONObject handleTenderCancelException(TenderCancelExceptionRequest request,Integer loginUserId);
}
