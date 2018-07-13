package com.hyjf.admin.service.exception;

import com.hyjf.admin.common.service.BaseService;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;

/**
 * @author hesy
 * @version HjhCreditEndExceptionService, v0.1 2018/7/12 20:01
 */
public interface HjhCreditEndExceptionService extends BaseService {
    HjhDebtCreditVO selectHjhDebtCreditByCreditNid(String creditNid);

    boolean requestDebtEnd(HjhDebtCreditVO credit, String tenderAccountId, String tenderAuthCode) throws Exception;

    boolean updateCreditForEnd(HjhDebtCreditVO hjhDebtCreditVO);
}
