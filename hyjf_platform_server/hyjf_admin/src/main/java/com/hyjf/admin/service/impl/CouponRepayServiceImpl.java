/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.CouponRepayService;
import com.hyjf.am.response.admin.AdminCouponRepayMonitorCustomizeResponse;
import com.hyjf.am.resquest.admin.CouponRepayRequest;
import com.hyjf.am.vo.admin.AdminCouponRepayMonitorCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangqingqing
 * @version CouponRepayServiceImpl, v0.1 2018/7/9 11:13
 */
@Service
public class CouponRepayServiceImpl implements CouponRepayService {

    @Autowired
    AmTradeClient amTradeClient;

    @Override
    public List<AdminCouponRepayMonitorCustomizeVO> selectRecordList(CouponRepayRequest form) {
        List<AdminCouponRepayMonitorCustomizeVO>  couponRepayMonitorVOS = amTradeClient.selectRecordList(form);
        return couponRepayMonitorVOS;
    }

    @Override
    public AdminCouponRepayMonitorCustomizeResponse couponRepayMonitorCreatePage(CouponRepayRequest form) {
       return amTradeClient.couponRepayMonitorCreatePage(form);
    }

    @Override
    public List<AdminCouponRepayMonitorCustomizeVO> selectInterestSum(CouponRepayRequest form) {
        return amTradeClient.selectInterestSum(form);
    }
}
