/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl.coupon;

import com.hyjf.admin.beans.request.DadaCenterCouponRequestBean;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.DataCenterCouponService;
import com.hyjf.am.response.admin.DataCenterCouponResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fq
 * @version DataCenterCouponServiceImpl, v0.1 2018/7/19 9:53
 */
@Service
public class DataCenterCouponServiceImpl implements DataCenterCouponService {
    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AmUserClient amUserClient;

    @Override
    public DataCenterCouponResponse searchAction(DadaCenterCouponRequestBean requestBean, String type) {
        return amTradeClient.getDataCenterCouponList(requestBean, type);
    }
}
