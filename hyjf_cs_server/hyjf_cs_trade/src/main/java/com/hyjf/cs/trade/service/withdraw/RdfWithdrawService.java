/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.withdraw;

import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.cs.trade.service.BaseTradeService;

import java.util.List;

/**
 * @author nxl
 * @version RdfWithdrawService, v0.1 2018/7/19 14:06
 */
public interface RdfWithdrawService extends BaseTradeService {
    /**
     * 获取银行卡信息
     * @param userId
     * @param status
     * @return
     */
    List<BankCardVO> selectBankCardByUserIdAndStatus(Integer userId, Integer status);

    /**
     * 根据银行名获取银行图标
     * @param bankName
     * @return
     */
    String getBankLogByBankName(String bankName);
}
