/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.admin.coupon;



import com.hyjf.am.resquest.trade.DataCenterCouponRequest;
import com.hyjf.am.trade.dao.model.customize.admin.DataCenterCouponCustomize;
import com.hyjf.am.trade.service.admin.coupon.DataCenterCouponService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fq
 * @version DataCenterCouponServiceImpl, v0.1 2018/7/19 14:35
 */
@Service
public class DataCenterCouponServiceImpl extends BaseServiceImpl implements DataCenterCouponService {

    @Override
    public List<DataCenterCouponCustomize> getDataCenterCouponList(DataCenterCouponRequest request) {
        return null;//todo
    }
}
