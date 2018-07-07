package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.BorrowAuthRequest;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.coupon.CouponRecoverCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponTenderCustomizeVO;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.am.vo.trade.repay.BorrowAuthCustomizeVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xiasq
 * @version AmTradeClient, v0.1 2018/6/19 15:44
 */
public interface AmTradeClient {

    /**
     * 统计加息券每日待收收益
     * @param timeStart
     * @param timeEnd
     * @return
     */
    List<CouponRecoverCustomizeVO> selectCouponInterestWaitToday(long timeStart, long timeEnd);

    /**
     * 统计加息券每日已收收益
     * @param timeStart
     * @return timeEnd
     */
    BigDecimal selectCouponInterestReceivedToday(long timeStart, long timeEnd);


    List<MyCouponListCustomizeVO> selectMyCouponList(MyCouponListRequest requestBean);

    Integer selectMyCouponCount(MyCouponListRequest requestBean);

    List<MyRewardRecordCustomizeVO> selectMyRewardList(MyInviteListRequest requestBean);

    /**
     * 根据borrowNid查询优惠券还款列表
     * @param borrowNid
     * @param repayTimeConfig
     * @return
     */
    List<CouponTenderCustomizeVO> selectCouponRecoverAll(String borrowNid, int repayTimeConfig);

    /**
     * 取得分期付款信息
     * @param couponTenderNid
     * @param periodNow
     * @return
     */
    CouponRecoverCustomizeVO getCurrentCouponRecover(String couponTenderNid, int periodNow);


    int selectMyRewardCount(MyInviteListRequest requestBean);

    BigDecimal selectMyRewardTotal(MyInviteListRequest requestBean);

    List<BorrowAuthCustomizeVO> selectBorrowAuthList(BorrowAuthRequest requestBean);

    int selectBorrowAuthCount(BorrowAuthRequest requestBean);

    List<BorrowAuthCustomizeVO> selectBorrowAuthedList(BorrowAuthRequest requestBean);

    int selectBorrowAuthedCount(BorrowAuthRequest requestBean);

    Integer updateTrusteePaySuccess(String borrowNid);

    STZHWhiteListVO getCurrentCouponRecover(Integer userId, Integer stzUserId);
}
