/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.trade.impl;

import com.hyjf.am.trade.dao.model.customize.WebPandectBorrowRecoverCustomize;
import com.hyjf.am.trade.dao.model.customize.WebPandectCreditTenderCustomize;
import com.hyjf.am.trade.dao.model.customize.WebPandectRecoverMoneyCustomize;
import com.hyjf.am.trade.dao.model.customize.WebPandectWaitMoneyCustomize;
import com.hyjf.am.trade.service.front.trade.WebPandectService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version WebPandectServiceImpl, v0.1 2018/7/23 11:30
 */
@Service
public class WebPandectServiceImpl extends BaseServiceImpl implements WebPandectService {

    @Override
    public WebPandectRecoverMoneyCustomize queryRecoverMoney(Integer userId){
        WebPandectRecoverMoneyCustomize result = webPandectCustomizeMapper.queryRecoverMoney(userId);
        return result;
    }

    @Override
    public WebPandectRecoverMoneyCustomize queryRecoverMoneyForRtb(Integer userId) {
        WebPandectRecoverMoneyCustomize result = webPandectCustomizeMapper.queryRecoverMoneyForRtb(userId);
        return result;
    }

    @Override
    public WebPandectWaitMoneyCustomize queryWaitMoney(Integer userId) {
        WebPandectWaitMoneyCustomize result = webPandectCustomizeMapper.queryWaitMoney(userId);
        return result;
    }

    @Override
    public WebPandectWaitMoneyCustomize queryWaitMoneyForRtb(Integer userId) {
        WebPandectWaitMoneyCustomize result = webPandectCustomizeMapper.queryWaitMoneyForRtb(userId);
        return result;
    }

    @Override
    public BigDecimal queryHtlSumRestAmount(Integer userId) {
        return webPandectCustomizeMapper.queryHtlSumRestAmount(userId);
    }

    @Override
    public WebPandectCreditTenderCustomize queryCreditInfo(Integer userId) {
        return webPandectCustomizeMapper.queryCreditInfo(userId);
    }

    @Override
    public WebPandectBorrowRecoverCustomize queryRecoverInfo(Integer userId, Integer recoverStatus) {
        return webPandectCustomizeMapper.queryRecoverInfo(userId,recoverStatus);
    }

    @Override
    public BigDecimal queryHtlSumInterest(Integer userId) {
        return webPandectCustomizeMapper.queryHtlSumInterest(userId);
    }

    @Override
    public int selectUserTenderCount(Integer userId){
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("userId", userId);
        int tenderCount = appUserInvestCustomizeMapper.selectUserTenderCount(paraMap);
        return tenderCount;
    }


}
