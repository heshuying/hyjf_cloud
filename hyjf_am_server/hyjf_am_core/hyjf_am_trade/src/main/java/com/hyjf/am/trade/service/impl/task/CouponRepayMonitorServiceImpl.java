/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl.task;

import com.hyjf.am.trade.dao.mapper.auto.CouponRepayMonitorMapper;
import com.hyjf.am.trade.dao.model.auto.CouponRepayMonitor;
import com.hyjf.am.trade.dao.model.auto.CouponRepayMonitorExample;
import com.hyjf.am.trade.service.task.CouponRepayMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yaoy
 * @version CouponRepayMonitorServiceImpl, v0.1 2018/6/22 9:41
 */
@Service
public class CouponRepayMonitorServiceImpl implements CouponRepayMonitorService {

    @Autowired
    private CouponRepayMonitorMapper couponRepayMonitorMapper;

    @Override
    public List<CouponRepayMonitor> selectCouponRepayMonitor(String nowDay) {
        CouponRepayMonitorExample example = new CouponRepayMonitorExample();
        CouponRepayMonitorExample.Criteria criteria = example.createCriteria();
        criteria.andDayEqualTo(nowDay);
        List<CouponRepayMonitor> couponRepayMonitorList = couponRepayMonitorMapper.selectByExample(example);
        return couponRepayMonitorList;
    }

    @Override
    public int insertCouponRepayMonitor(CouponRepayMonitor couponRepayMonitor) {
        int result = couponRepayMonitorMapper.insertSelective(couponRepayMonitor);
        return result;
    }

    @Override
    public int updateCouponRepayMonitor(CouponRepayMonitor couponRepayMonitor) {
        int result = couponRepayMonitorMapper.updateByPrimaryKeySelective(couponRepayMonitor);
        return result;
    }
}
