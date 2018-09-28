package com.hyjf.cs.trade.service.coupon;

import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.cs.trade.service.BaseTradeService;

import java.util.List;
import java.util.Map;

/**
 * @author hesy
 * @version MyCouponListService, v0.1 2018/6/23 16:08
 */
public interface MyCouponListService extends BaseTradeService {

    List<MyCouponListCustomizeVO> selectMyCouponListUsed(String userId);

    List<MyCouponListCustomizeVO> selectMyCouponListUnUsed(String userId);

    List<MyCouponListCustomizeVO> selectMyCouponListInValid(String userId);

    Map<String,String> selectInvitePageData(String userId);

    /**
     * @Author walter.limeng
     * @Description  微信端获取我的优惠券列表
     * @Date 15:24 2018/9/28
     * @Param userId
     * @Param useFlag 0:未使用  1：已使用  4：已失效
     * @return
     */
    List<MyCouponListCustomizeVO> selectWechatCouponList(String userId,Integer useFlag);
}
