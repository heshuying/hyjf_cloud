package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.vo.trade.CouponRecoverCustomizeVO;
import com.hyjf.am.vo.trade.CouponTenderCustomizeVO;
import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.MyInviteListCustomizeVO;

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

    /**
     *
     * @param currentRecover
     * @param userId
     * @param count
     */
    void repayDataRecover(CouponRecoverCustomizeVO currentRecover, BankOpenAccountVO bankOpenAccountInfo, String userId, String couponUserCode, String ip, int count);

    int selectMyRewardCount(MyInviteListRequest requestBean);

    BigDecimal selectMyRewardTotal(MyInviteListRequest requestBean);

    void updateRepayDataRecover(CouponRecoverCustomizeVO currentRecover,BorrowTenderCpnVO borrowTenderCpn, String borrowNid, String couponUserCode,String userId,String borrowStyle,int periodNow);
}
