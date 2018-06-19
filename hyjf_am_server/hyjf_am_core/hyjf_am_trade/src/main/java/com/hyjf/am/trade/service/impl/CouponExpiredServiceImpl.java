/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.CouponUserMapper;
import com.hyjf.am.trade.dao.model.auto.CouponUser;
import com.hyjf.am.trade.dao.model.auto.CouponUserExample;
import com.hyjf.am.trade.service.CouponExpiredService;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

/**
 * @author yaoy
 * @version CouponExpiredServiceImpl, v0.1 2018/6/19 10:43
 */
@Service
public class CouponExpiredServiceImpl implements CouponExpiredService {
    private static final Logger logger = LoggerFactory.getLogger(CouponExpiredServiceImpl.class);

    @Autowired
    private CouponUserMapper couponUserMapper;

    @Override
    public void updateCouponExpired() {
        String methodName = "updateCouponExpired";
        logger.info("优惠券过期未使用更新 开始。methodName:{} ", methodName);
        SimpleDateFormat sdf = GetDate.datetimeFormat;
        int nowTime = GetDate.getNowTime10();
        //yyyy-MM-dd 的时间戳
        int nowDate = GetDate.strYYYYMMDD2Timestamp2(GetDate.getDataString(GetDate.date_sdf));
        // 取得体验金投资（无真实投资）的还款列表
        CouponUserExample example = new CouponUserExample();
        CouponUserExample.Criteria criteria = example.createCriteria();
        criteria.andDelFlagEqualTo(0);
        // 未使用
        criteria.andUsedFlagEqualTo(0);
        // 截止日小于当前时间
        criteria.andEndTimeLessThan(nowDate);
        CouponUser couponUser = new CouponUser();
        // 已失效
        couponUser.setUsedFlag(4);
        couponUser.setUpdateTime(GetDate.str2Date(GetDate.timestamptoStrYYYYMMDDHHMMSS(nowTime),sdf) );
        couponUser.setUpdateUser(CustomConstants.OPERATOR_AUTO_REPAY);
        int count = couponUserMapper.updateByExampleSelective(couponUser, example);
        if (count > 0) {
            logger.info("优惠券过期失效更新成功，共有{}张优惠券过期", count);
        }
        logger.info("优惠券过期未使用更新 结束。");
    }
}
