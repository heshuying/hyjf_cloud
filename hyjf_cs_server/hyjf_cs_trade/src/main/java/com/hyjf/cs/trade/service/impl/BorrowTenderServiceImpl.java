/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.trade.CouponUserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.client.*;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.BorrowTenderService;
import com.hyjf.cs.trade.service.CouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description 投资接口
 * @Author sunss
 * @Date 2018/6/24 14:30
 */
@Service
public class BorrowTenderServiceImpl extends BaseTradeServiceImpl implements BorrowTenderService {
    private static final Logger logger = LoggerFactory.getLogger(BorrowTenderServiceImpl.class);

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmBorrowClient amBorrowClient;

    @Autowired
    private CouponClient couponClient;

    @Autowired
    private RechargeClient rechargeClient;

    @Autowired
    private AmMongoClient amMongoClient;

    @Autowired
    private CouponService couponService;

    /**
     * @param request
     * @Description 散标投资
     * @Author sunss
     * @Date 2018/6/24 14:35
     */
    @Override
    public WebResult<Map<String, Object>> borrowTender(TenderRequest request) {
        WebViewUserVO loginUser = RedisUtils.getObj(request.getToken(), WebViewUserVO.class);
        Integer userId = loginUser.getUserId();
        request.setUser(loginUser);
        // 查询选择的优惠券
        CouponUserVO cuc = null;
        if (request.getCouponGrantId() != null && request.getCouponGrantId() > 0) {
            cuc = couponClient.getCouponUser(request.getCouponGrantId(), userId);
        }





        return null;
    }
}
