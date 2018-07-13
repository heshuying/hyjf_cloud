/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.BankCreditEnd;
import com.hyjf.am.resquest.trade.BankCreditEndListRequest;
import com.hyjf.am.trade.dao.model.auto.BankCreditEnd;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCredit;
import com.hyjf.am.vo.bank.BankCallBeanVO;

import java.util.List;

import java.util.List;

/**
 * @author liubin
 * @version BankCreditEndService, v0.1 2018/6/28 21:15
 */
public interface BankCreditEndService extends BaseService{

    /**
     * 结束债券
     * @param hjhDebtCredit
     * @param tenderAccountId
     * @param tenderAuthCode
     * @return
     */
    int insertBankCreditEndForCreditEnd(HjhDebtCredit hjhDebtCredit, String tenderAccountId, String tenderAuthCode);

    List<BankCreditEnd> getCreditEndList(BankCreditEndListRequest requestBean);

    Integer getCreditEndCount(BankCreditEndListRequest requestBean);

    int updateBankCreditEnd(BankCreditEnd bankCreditEnd);

    BankCreditEnd selectByOrderId(String orderId);

    int updateCreditEndForInitial(BankCreditEnd bankCreditEnd);

    /**
     * 批次结束债权用更新 结束债权任务表
     * @param bankCreditEnd
     * @return
     */
    int updateBankCreditEndForBatch(BankCreditEnd bankCreditEnd);

    /**
     * 据批次号和日期，取得结束债权任务列表
     * @param batchNo
     * @param txDate
     * @return
     */
    List<BankCreditEnd> getBankCreditEndListByBatchnoTxdate(String batchNo, String txDate);

    /**
     * 根据批次号和日期，更新结束债权任务状态
     * @param batchNo
     * @param txDate
     * @param status
     * @return
     */
    int updateBankCreditEndForStatus(String batchNo, String txDate, int status);

    /**
     * 合法性检查后，更新批次结束债权任务
     * @param bean
     * @return
     */
    int updateBatchCreditEndCheck(BankCallBeanVO bean);

    /**
     * 银行完成后，更新批次结束债权任务
     * @param bean
     * @return
     */
    int updateBatchCreditEndFinish(BankCallBeanVO bean);
}
