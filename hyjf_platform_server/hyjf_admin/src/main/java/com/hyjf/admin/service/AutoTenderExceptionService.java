/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.AutoTenderExceptionResponse;
import com.hyjf.am.resquest.admin.AutoTenderExceptionRequest;
import com.hyjf.am.resquest.admin.TenderExceptionSolveRequest;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanBorrowTmpVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author nixiaoling
 * @version AutoTenderExceptionService, v0.1 2018/7/12 10:30
 */
public interface AutoTenderExceptionService {
    /**
     * 检索汇计划加入明细列表
     * @param request
     * @return
     */
    AutoTenderExceptionResponse selectAccedeRecordList(AutoTenderExceptionRequest request);

    /**
     * 查找HjhAccedeVO实体
     * @param tenderExceptionSolveRequest
     * @return
     */
   HjhAccedeVO getHjhAccedeVO(TenderExceptionSolveRequest tenderExceptionSolveRequest);

    /**
     * 查找HjhPlanBorrowTmpVO实体
     * @param tenderExceptionSolveRequest
     * @return
     */
    HjhPlanBorrowTmpVO getHjhPlanBorrowTmpVO(TenderExceptionSolveRequest tenderExceptionSolveRequest);
    /**
     * 查询相应的债权的状态
     *
     * @param userId
     * @param accountId
     * @param orderId
     * @return
     */
   BankCallBean debtStatusQuery(int userId, String accountId, String orderId);
    /**
     * 查询相应的债权的状态
     *
     * @param userId
     * @param accountId
     * @param orderId
     * @return
     */
    BankCallBean creditStatusQuery(int userId, String accountId, String orderId);

    HjhPlanVO getFirstHjhPlanVO(String planNid);

    /**
     * 根据编号获取borrow
     *
     * @param borrowNid
     * @return
     */
    BorrowVO selectBorrowByNid(String borrowNid);
    /**
     * 更新投资数据
     *
     * @return
     * @author nxl
     */
    boolean updateBorrowForAutoTender(BorrowVO borrow, HjhAccedeVO hjhAccede, BankCallBean bean);
    /**
     * 更新
     * @param status
     * @param accedeId
     * @return
     */
    boolean updateTenderByParam(int status,int accedeId);
    /**
     * 计算实际金额 保存creditTenderLog表
     *
     * @return
     * @author nxl
     */
    Map<String, Object> saveCreditTenderLogNoSave(HjhDebtCreditVO credit, HjhAccedeVO hjhAccede, String orderId, String orderDate, BigDecimal yujiAmoust, boolean isLast);
    /**
     * 汇计划自动承接成功后数据库更新操作
     *
     * @return
     * @author nxl
     */
    boolean updateCreditForAutoTender(HjhDebtCreditVO credit, HjhAccedeVO hjhAccede, HjhPlanVO hjhPlan, BankCallBean bean,String tenderUsrcustid, String sellerUsrcustid, Map<String, Object> resultMap);
}