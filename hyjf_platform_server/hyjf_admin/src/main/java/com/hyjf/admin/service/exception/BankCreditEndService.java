package com.hyjf.admin.service.exception;

import com.hyjf.am.resquest.trade.BankCreditEndListRequest;
import com.hyjf.am.resquest.trade.BankCreditEndUpdateRequest;
import com.hyjf.am.vo.trade.BankCreditEndVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.util.List;

/**
 * @author hesy
 * @version BankCreditEndService, v0.1 2018/7/12 16:51
 */
public interface BankCreditEndService {
    List<BankCreditEndVO> getCreditEndList(BankCreditEndListRequest requestBean);

    int getCreditEndCount(BankCreditEndListRequest requestBean);

    BankCreditEndVO getCreditEndByOrderId(String orderId);

    int updateBankCreditEnd(BankCreditEndVO requestBean);

    boolean updateCreditEndForInitial(BankCreditEndVO requestBean);

    boolean checkForUpdate(BankCreditEndUpdateRequest requestBean);

    boolean checkForUpdateInitial(BankCreditEndUpdateRequest requestBean);

    List<BankCallBean> queryBatchDetails(BankCreditEndUpdateRequest requestBean);

    boolean updateBankCreditEndFromBankQuery(List<BankCallBean> resultBeans);
}
