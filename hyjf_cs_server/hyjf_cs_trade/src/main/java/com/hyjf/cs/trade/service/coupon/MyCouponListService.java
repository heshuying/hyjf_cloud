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
}
