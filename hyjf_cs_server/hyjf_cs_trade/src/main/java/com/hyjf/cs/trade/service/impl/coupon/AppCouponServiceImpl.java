package com.hyjf.cs.trade.service.impl.coupon;

import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.vo.trade.coupon.CouponUserForAppCustomizeVO;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.coupon.AppCouponService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 我的优惠券列表
 * @author walter.li
 * @version CouponServiceImpl, v0.1 2018/7/9 14:08
 */
@Service
public class AppCouponServiceImpl extends BaseTradeServiceImpl implements AppCouponService {
    @Override
    public List<CouponUserForAppCustomizeVO> getMyCoupon(Integer userId, Integer page, Integer pageSize, String couponStatus) {
        MyCouponListRequest requestBean = new MyCouponListRequest();
        requestBean.setUserId(userId+"");
        requestBean.setUsedFlag(couponStatus);
        requestBean.setLimitStart((page-1) * pageSize);
        requestBean.setLimitEnd(page * pageSize);
        return amTradeClient.getMyCoupon(requestBean);
    }

    @Override
    public Integer countMyCoupon(Integer userId, String couponStatus) {
        MyCouponListRequest requestBean = new MyCouponListRequest();
        requestBean.setUserId(userId+"");
        requestBean.setUsedFlag(couponStatus);
        return amTradeClient.selectMyCouponCount(requestBean);
    }

    @Override
    public Map<String, Object> getBorrowCoupon(Integer userId, String borrowNid, String money, String platform) {
        return null;
    }
}
