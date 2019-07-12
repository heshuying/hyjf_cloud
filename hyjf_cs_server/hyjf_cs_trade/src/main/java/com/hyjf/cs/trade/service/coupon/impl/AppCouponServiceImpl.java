package com.hyjf.cs.trade.service.coupon.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.trade.coupon.CouponResponse;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.vo.trade.coupon.CouponUserForAppCustomizeVO;
import com.hyjf.cs.trade.service.coupon.AppCouponService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 我的优惠券列表
 * @author walter.li
 * @version CouponServiceImpl, v0.1 2018/7/9 14:08
 */
@Service
public class AppCouponServiceImpl extends BaseTradeServiceImpl implements AppCouponService {
    @Override
    public List<CouponUserForAppCustomizeVO> getMyCoupon(MyCouponListRequest requestBean) {
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
    public JSONObject getBorrowCoupon(Integer userId, String borrowNid, String money, String platform) {
        JSONObject jsonObject = new JSONObject();
        MyCouponListRequest requestBean = new MyCouponListRequest();
        requestBean.setUserId(userId+"");
        requestBean.setBorrowNid(borrowNid);
        requestBean.setMoney(money);
        requestBean.setPlatform(platform);
        CouponResponse couponResponse = amTradeClient.getBorrowCoupon(requestBean);
        if (null != couponResponse){
            String json = JSONObject.toJSONString(couponResponse.getResult());
            jsonObject = JSONObject.parseObject(json);
        }
        return jsonObject;
    }

    @Override
    public JSONObject getPlanCoupon(Integer userId, String planNid, String money, String platform) {
        JSONObject jsonObject = new JSONObject();
        MyCouponListRequest requestBean = new MyCouponListRequest();
        requestBean.setUserId(userId+"");
        requestBean.setBorrowNid(planNid);
        requestBean.setMoney(money);
        requestBean.setPlatform(platform);
        CouponResponse couponResponse = amTradeClient.getPlanCoupon(requestBean);
        if (null != couponResponse){
            String json = JSONObject.toJSONString(couponResponse.getResult());
            jsonObject = JSONObject.parseObject(json);
        }
        return jsonObject;
    }
}
