package com.hyjf.admin.service.exception;

import com.hyjf.admin.common.service.BaseService;
import com.hyjf.am.response.admin.HjhDebtCreditReponse;
import com.hyjf.am.resquest.admin.HjhDebtCreditListRequest;
import com.hyjf.am.vo.admin.HjhDebtCreditVo;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;

import java.util.List;

/**
 * @author hesy
 * @version HjhCreditEndExceptionService, v0.1 2018/7/12 20:01
 */
public interface HjhCreditEndExceptionService extends BaseService {
    HjhDebtCreditVO selectHjhDebtCreditByCreditNid(String creditNid);

    boolean requestDebtEnd(HjhDebtCreditVO credit, String tenderAccountId, String tenderAuthCode) throws Exception;

    boolean updateCreditForEnd(HjhDebtCreditVO hjhDebtCreditVO);

    String getSellerAuthCode(String tenderOrderId, Integer SourceType);

    HjhDebtCreditReponse queryHjhDebtCreditList(HjhDebtCreditListRequest request);

    void queryHjhDebtCreditListStatusName(List<HjhDebtCreditVo> hjhDebtCreditVoList);
}
