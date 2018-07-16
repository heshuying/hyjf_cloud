package com.hyjf.am.trade.service.coupon;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.vo.coupon.CouponBeanVo;
import com.hyjf.am.vo.trade.coupon.BestCouponListVO;
import com.hyjf.am.vo.trade.coupon.CouponUserForAppCustomizeVO;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;

import java.util.List;

/**
 * @author hesy
 * @version MyCouponListService, v0.1 2018/6/22 20:04
 */
public interface MyCouponListService {
    List<MyCouponListCustomizeVO> selectUserCouponList(String userId, String usedFlag, Integer limitStart, Integer limitEnd);

    Integer countUserCouponList(String userId, String usedFlag);

    BestCouponListVO selectBestCouponList(MyCouponListRequest request);

    Integer countAvaliableCoupon(MyCouponListRequest request);

    /**
     * 查询汇计划最优优惠券
     * @param requestBean
     * @return
     */
    BestCouponListVO selectHJHBestCoupon(MyCouponListRequest requestBean);

    /**
     * 查询hjh可用优惠券数量
     * @param requestBean
     * @return
     */
    Integer getHJHUserCouponAvailableCount(MyCouponListRequest requestBean);

    /**
     * APP获取我的优惠券分页数据
     * @param requestBean 参数
     * @return list
     */
    List<CouponUserForAppCustomizeVO> getMyCouponByPage(MyCouponListRequest requestBean);

    /**
     * APP,PC,wechat散标投资查询优惠券列表
     * @param requestBean userId:用户ID，money：投资金额，borrowNid:投资标的，platform：投资平台
     * @return JSONObject
     */
    JSONObject getBorrowCoupon(MyCouponListRequest requestBean);

    /**
     * APP,PC,wechat加入计划查询优惠券列表
     * @param requestBean userId:用户ID，money：投资金额，planNid:投资标的，platform：投资平台
     * @return JSONObject
     */
    JSONObject getPlanCouponoupon(MyCouponListRequest requestBean);
}
