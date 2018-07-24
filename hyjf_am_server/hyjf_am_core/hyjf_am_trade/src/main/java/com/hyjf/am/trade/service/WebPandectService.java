/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.customize.WebPandectBorrowRecoverCustomize;
import com.hyjf.am.trade.dao.model.customize.WebPandectCreditTenderCustomize;
import com.hyjf.am.trade.dao.model.customize.WebPandectRecoverMoneyCustomize;
import com.hyjf.am.trade.dao.model.customize.WebPandectWaitMoneyCustomize;

import java.math.BigDecimal;

/**
 * @author zhangqingqing
 * @version WebPandectService, v0.1 2018/7/23 11:30
 */
public interface WebPandectService extends BaseService {

    /**
     * 已回收本金和 已回收利息
     * @param userId
     * @return
     */
    WebPandectRecoverMoneyCustomize queryRecoverMoney(Integer userId);

    /**
     * 融通宝累计收益计算
     * @param userId
     * @return
     */
    WebPandectRecoverMoneyCustomize queryRecoverMoneyForRtb(Integer userId);

    /**
     * 待收本金和 待收利息
     * @param userId
     * @return
     */
    WebPandectWaitMoneyCustomize queryWaitMoney(Integer userId);

    /**
     * 插入预约授权记录表
     * @param userId
     * @return
     */
    WebPandectWaitMoneyCustomize queryWaitMoneyForRtb(Integer userId);

    /**
     * 获取汇天利 购买明细表可赎回金额总额
     * @param userId
     * @return
     */
    BigDecimal queryHtlSumRestAmount(Integer userId);

    /**
     * 债转统计
     * @param userId
     * @return
     */
    WebPandectCreditTenderCustomize queryCreditInfo(Integer userId);

    /**
     * 债转信息
     * 去掉已债转（r.recover_status=1）; 去掉待收已债转（r.recover_status=0）
     * @param userId
     * @param recoverStatus
     * @return
     */
    WebPandectBorrowRecoverCustomize queryRecoverInfo(Integer userId, Integer recoverStatus);

    /**
     * 获取汇天利 总收益
     * @param userId
     * @return
     */
    BigDecimal queryHtlSumInterest(Integer userId);

    int selectUserTenderCount(Integer userId);
}
