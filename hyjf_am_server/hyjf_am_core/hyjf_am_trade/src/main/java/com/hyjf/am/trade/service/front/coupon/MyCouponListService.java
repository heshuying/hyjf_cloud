package com.hyjf.am.trade.service.front.coupon;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
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

    int updateCouponReadFlag(Integer userId, Integer readFlag);

    /**
     * APP,PC,wechat散标出借查询优惠券列表
     * @param requestBean userId:用户ID，money：出借金额，borrowNid:出借标的，platform：出借平台
     * @return JSONObject
     */
    JSONObject getBorrowCoupon(MyCouponListRequest requestBean);

    /**
     * APP,PC,wechat加入计划查询优惠券列表
     * @param requestBean userId:用户ID，money：出借金额，planNid:出借标的，platform：出借平台
     * @return JSONObject
     */
    JSONObject getPlanCouponoupon(MyCouponListRequest requestBean);

    /**
     * @Author walter.limeng
     * @Description  微信获取我的优惠券列表
     * @Date 15:29 2018/9/28
     * @Param userId
     * @Param usedFlag 0:未使用  1：已使用  4：已失效
     * @Param limitStart
     * @Param limitEnd
     * @return
     */
    List<MyCouponListCustomizeVO> wechatCouponList(String userId, String usedFlag, Integer limitStart, Integer limitEnd);
}
