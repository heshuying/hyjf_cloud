package com.hyjf.admin.service.exception;

import com.hyjf.am.vo.trade.repay.BankRepayFreezeLogVO;

import java.util.List;

/**
 * @author hesy
 * @version BankRepayFreezeService, v0.1 2018/7/11 15:16
 */
public interface BankRepayFreezeService {
    Integer getFreezeLogCount();

    List<BankRepayFreezeLogVO> getFreezeLogList(Integer limitStart, Integer limitEnd);
    /**
     * 根据id获取冻结记录
     * @param orderId
     * @return
     */
    BankRepayFreezeLogVO getFreezeLogByOrderId(String orderId);
    /**
     * 冻结撤销更新数据
     * @param freezeLogVO
     * @return
     */
    boolean updateBankRepayFreeze(BankRepayFreezeLogVO freezeLogVO);
    /**
     * 申请冻结撤销
     * @auther: hesy
     * @date: 2018/7/11
     */
    boolean repayUnfreeze(BankRepayFreezeLogVO repayFreezeFlog);
}
