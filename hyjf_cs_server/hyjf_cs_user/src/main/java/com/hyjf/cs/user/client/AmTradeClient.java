/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.client;

import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.coupon.CouponUserListCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.RecentPaymentListCustomizeVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AmTradeClient, v0.1 2018/6/20 12:46
 */
public interface AmTradeClient {

    HjhInstConfigVO selectInstConfigByInstCode(String instCode);

    AccountVO getAccount(Integer userId);

    List<RecentPaymentListCustomizeVO> selectRecentPaymentList(Integer userId);
    /**
     * 根据userId获得填充userPortrait的info --用户画像定时任务用
     * */
    List<BatchUserPortraitQueryVO> searchInfoForUserPortrait(String userIds);

    Integer countCouponValid(Integer userId);

    List<CouponUserListCustomizeVO> selectCouponUserList(Map<String,Object> mapParameter);

    /**
     * 获取账户信息通过userId范围
     * @param ids
     * @return
     */
    List<AccountVO> getAccountByUserIds(List<Integer> ids);

    /**
     * 获取投资人本金信息
     * @param productSearchForPage
     * @return
     */
    ProductSearchForPageVO selectUserPrincipal(ProductSearchForPageVO productSearchForPage);

    /**
     * 已回收本金和 已回收利息
     * @param userId
     * @return
     */
    WebPandectRecoverMoneyCustomizeVO queryRecoverMoney(Integer userId);

    /**
     * 融通宝累计收益计算
     * @param userId
     * @return
     */
    WebPandectRecoverMoneyCustomizeVO queryRecoverMoneyForRtb(Integer userId);

    /**
     * 待收本金和 待收利息
     * @param userId
     * @return
     */
    WebPandectWaitMoneyCustomizeVO queryWaitMoney(Integer userId);

    /**
     * 插入预约授权记录表
     * @param userId
     * @return
     */
    WebPandectWaitMoneyCustomizeVO queryWaitMoneyForRtb(Integer userId);

    /**
     * 获取汇天利 购买明细表可赎回金额总额
     * @param userId
     * @return
     */
    BigDecimal queryHtlSumRestAmount(Integer userId);

    /**
     * 债转统计
     * @param userId
     * @return
     */
    WebPandectCreditTenderCustomizeVO queryCreditInfo(Integer userId);

    /**
     * 债转信息
     * 去掉已债转（r.recover_status=1）; 去掉待收已债转（r.recover_status=0）
     * @param userId
     * @param recoverStatus
     * @return
     */
    WebPandectBorrowRecoverCustomizeVO queryRecoverInfo(Integer userId, int recoverStatus);

    /**
     * 获取汇天利 总收益
     * @param userId
     * @return
     */
    BigDecimal queryHtlSumInterest(Integer userId);

    /**
     * 获取某用户优惠券待收收益总和
     * @param userId
     * @return
     */
    String selectCouponInterestTotal(Integer userId);

    /**
     *获取某用户优惠券累计收益总和
     * @param userId
     * @return
     */
    String selectCouponReceivedInterestTotal(Integer userId);


    /**
     * 查询用户投资次数 包含直投类、债转、汇添金
     * @param userId
     * @return
     */
    int selectUserTenderCount(Integer userId);

    /**
     * 查询有效未读的优惠券列表
     * @param userId
     * @return
     */
    List<CouponUserCustomizeVO> selectLatestCouponValidUNReadList(Integer userId);
}
