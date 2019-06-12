/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch;

import com.hyjf.am.vo.trade.BankCreditEndVO;
import com.hyjf.cs.trade.service.BaseTradeService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.util.List;

/**
 * @author liubin
 * @version BatchCreditEndService, v0.1 2018/7/10 19:23
 */
public interface BatchCreditEndService extends BaseTradeService {
    /**
     * 批次结束债权
     * @return
     */
    Boolean batchCreditEnd();

    /**
     * 合法性检查后，更新批次结束债权任务
     * @param bean
     * @return
     */
    int updateBatchCreditEndCheck(BankCallBean bean);

    /**
     * 银行完成后，更新批次结束债权任务
     * @param bean
     * @return
     */
    int updateBatchCreditEndFinish(BankCallBean bean);

    int updateForCallBackFail(BankCallBean bean);

    List<BankCreditEndVO> getCreditEndListForCallBackFail();

    BankCallBean batchQuery(BankCreditEndVO creditEndVO);
}
