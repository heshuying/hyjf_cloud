package com.hyjf.cs.trade.service.coupon;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.vo.trade.coupon.CouponUserForAppCustomizeVO;
import com.hyjf.cs.trade.service.BaseTradeService;

import java.util.List;

/**
 * @author walter.limeng
 * @version CouponService, v0.1 2018/7/9 14:08
 */
public interface AppCouponService extends BaseTradeService {
    /**
     * 获取我的优惠券分页数据
     * @param userId 用户ID
     * @param page 页数
     * @param pageSize 大小
     * @param couponStatus 优惠券状态
     * @return List
     */
    List<CouponUserForAppCustomizeVO> getMyCoupon(MyCouponListRequest requestBean);

    /**
     *  获取我的优惠券总数
     * @param userId 用户ID
     * @param couponStatus 优惠券状态
     * @return Integer
     */
    Integer countMyCoupon(Integer userId,String couponStatus);

    /**
     * APP,PC,wechat散标出借根据标的编号，出借金额等查询优惠券
     * @param userId 用户ID
     * @param borrowNid 标的编号
     * @param money 出借金额
     * @param platform 出借平台
     * @return Map<String,Object>
     */
    JSONObject getBorrowCoupon(Integer userId, String borrowNid, String money, String platform);

    /**
     * APP,PC,wechat加入计划获取我的优惠券列表
     * @param userId 用户ID
     * @param planNid 计划编号
     * @param money 出借金额
     * @param platform 出借平台
     * @return Map<String,Object>
     */
    JSONObject getPlanCoupon(Integer userId, String planNid, String money, String platform);
}
