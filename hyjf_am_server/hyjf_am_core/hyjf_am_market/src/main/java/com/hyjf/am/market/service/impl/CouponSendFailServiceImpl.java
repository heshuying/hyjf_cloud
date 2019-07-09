package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.dao.mapper.auto.CouponSendFailMapper;
import com.hyjf.am.market.dao.model.auto.CouponSendFail;
import com.hyjf.am.market.service.CouponSendFailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiasq
 * @version CouponSendFailServiceImpl, v0.1 2019/5/20 11:43
 */
@Service
public class CouponSendFailServiceImpl implements CouponSendFailService {

    @Autowired
    private CouponSendFailMapper mapper;

    @Override
    public int save(Integer userId, Integer activityId, String couponCode, Integer sendFlg, String remark) {

        CouponSendFail record = new CouponSendFail();
        record.setUserId(userId);
        record.setActivityId(activityId);
        record.setCouponCode(couponCode);
        record.setSendFlag(sendFlg == null ? "" : String.valueOf(sendFlg));
        record.setFailDesc(remark);
        return mapper.insertSelective(record);
    }
}
