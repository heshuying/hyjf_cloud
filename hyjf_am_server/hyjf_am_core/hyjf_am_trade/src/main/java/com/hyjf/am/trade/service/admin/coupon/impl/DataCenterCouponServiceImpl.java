/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.coupon.impl;



import com.hyjf.am.resquest.trade.DataCenterCouponRequest;
import com.hyjf.am.trade.dao.mapper.customize.DataCenterCouponCustomizeMapper;
import com.hyjf.am.trade.dao.model.customize.DataCenterCouponCustomize;
import com.hyjf.am.trade.service.admin.coupon.DataCenterCouponService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author fq
 * @version DataCenterCouponServiceImpl, v0.1 2018/7/19 14:35
 */
@Service
public class DataCenterCouponServiceImpl extends BaseServiceImpl implements DataCenterCouponService {
    @Autowired
    private DataCenterCouponCustomizeMapper dataCenterCouponMapper;

    @Override
    public List<DataCenterCouponCustomize> getDataCenterCouponList(DataCenterCouponRequest request) {
        if (request != null) {
            String type = request.getType();
            Map<String, Object> params = new HashMap<>();
            if (Objects.equals(type, "jx")) {
                params.put("type", 2);
            } else if (Objects.equals(type, "dj")) {
                params.put("type", 3);
            }
            params.put("offset", request.getLimitStart());
            params.put("limit", request.getLimitEnd());
            return dataCenterCouponMapper.getDataCenterCouponList(params);
        }
        return null;
    }
}
