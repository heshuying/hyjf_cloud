package com.hyjf.admin.service.impl.exception;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.service.BaseService;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.exception.HjhCreditEndExceptionService;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hesy
 * @version HjhCreditEndExceptionServiceImpl, v0.1 2018/7/12 18:47
 */
@Service
public class HjhCreditEndExceptionServiceImpl extends BaseServiceImpl implements HjhCreditEndExceptionService {
    @Autowired
    AmTradeClient amTradeClient;

    /**
     * 根据creditNid查询债转信息
     * @param creditNid
     * @return
     */
    @Override
    public HjhDebtCreditVO selectHjhDebtCreditByCreditNid(String creditNid){
        return amTradeClient.selectHjhDebtCreditByCreditNid(creditNid);
    }

    /**
     * 请求结束债权（追加结束债权任务记录）
     * @param credit
     * @param tenderAccountId
     * @param tenderAuthCode
     * @return
     * @throws Exception
     */
    @Override
    public boolean requestDebtEnd(HjhDebtCreditVO credit, String tenderAccountId, String tenderAuthCode) throws Exception {
        return amTradeClient.requestDebtEnd(credit, tenderAccountId, tenderAuthCode) > 0 ? true : false;
    }

    /**
     * 银行结束债权后，更新债权表为完全承接
     * @param hjhDebtCreditVO
     * @return
     */
    @Override
    public boolean updateCreditForEnd(HjhDebtCreditVO hjhDebtCreditVO){
        return amTradeClient.updateHjhDebtCreditForEnd(hjhDebtCreditVO) > 0 ? true : false;
    }
}
