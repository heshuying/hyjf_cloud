package com.hyjf.am.trade.dao.mapper.customize.coupon;

import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * @author hesy
 * @version MyCouponListCustomizeMapper, v0.1 2018/6/22 16:36
 */
public interface MyCouponListCustomizeMapper {
    List<MyCouponListCustomizeVO> selectMyCouponList(Map<String,Object> paraMap);
    Integer countMyCouponList(Map<String,Object> paraMap);
}
