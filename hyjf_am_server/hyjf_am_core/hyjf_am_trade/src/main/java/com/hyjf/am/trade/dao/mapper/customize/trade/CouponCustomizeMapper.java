package com.hyjf.am.trade.dao.mapper.customize.trade;

import com.hyjf.am.trade.dao.model.auto.CouponUser;
import com.hyjf.am.trade.dao.model.customize.trade.CouponCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.WebProjectListCustomize;

import java.util.List;
import java.util.Map;
/**
 * @Description 优惠券相关
 * @Author sunss
 * @Version v0.1
 * @Date 2018/6/19 17:09
 */
public interface CouponCustomizeMapper {

    /**
     * @Description 根据userid和优惠券ID查询优惠券信息
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 17:12
     */
    List<CouponCustomize> getCouponUser(Map<String,Object> paraMap);
}
