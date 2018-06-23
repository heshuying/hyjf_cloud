package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.vo.trade.CouponRecoverCustomizeVO;
import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.am.vo.user.MyInviteListCustomizeVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xiasq
 * @version AmTradeClient, v0.1 2018/6/19 15:44
 */
public interface AmTradeClient {
    /**
     * 根据借款编号,还款期数,还款方式取得融通宝待还款金额
     * @param borrowNid
     * @param borrowStyle
     * @param periodNow
     * @return
     */
    BigDecimal selectRtbRepayAmount(String borrowNid, String borrowStyle, Integer periodNow);

    /**
     * 融通宝还款加息
     * @param borrowApicronVO
     * @param account
     * @param companyAccount
     */
    void rtbIncreaseReapy(BorrowApicronVO borrowApicronVO, String account, String companyAccount);

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

    List<MyRewardRecordCustomizeVO> selectMyRewardList(MyInviteListRequest requestBean);
}
