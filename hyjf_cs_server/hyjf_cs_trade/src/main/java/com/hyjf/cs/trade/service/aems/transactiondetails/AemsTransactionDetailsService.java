package com.hyjf.cs.trade.service.aems.transactiondetails;

import com.hyjf.am.vo.trade.ApiTransactionDetailsCustomizeVO;
import com.hyjf.cs.trade.bean.AemsTransactionDetailsResultBean;
import com.hyjf.cs.trade.bean.TransactionDetailsResultBean;

import java.util.List;

/**
 * Aems交易明细查询 Service
 * @Author : ZhaDaojian
 */
public interface AemsTransactionDetailsService {

    List<ApiTransactionDetailsCustomizeVO> searchTradeList(AemsTransactionDetailsResultBean trade);
    /**
     * 验证手机号码是否存在
     * @param mobile
     * @return
     */
    boolean existPhone(String mobile);

    /**
     * 验证手机号码与accountId是否一致
     * @param mobile
     * @param accountId
     * @return
     */
    boolean existAccountId(String mobile, String accountId);
}
