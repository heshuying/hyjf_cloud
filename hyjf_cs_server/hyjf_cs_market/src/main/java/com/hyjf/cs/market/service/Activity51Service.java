package com.hyjf.cs.market.service;

import java.math.BigDecimal;

/**
 * @author xiasq
 * @version Activity51Service, v0.1 2019-04-17 9:53
 */
public interface Activity51Service {

    /**
     * 判断是否是活动时间
     * @return
     */
    boolean isActivityTime();

    /**
     * 查询活动期间累计投资年化金额
     * @return
     */
    BigDecimal getSumAmount();

    /**
     * 判断是否满足发放优惠券标准（投资年化金额>=1w）
     * @param userId
     * @return
     */
    boolean canSendCoupon(int userId);

    /**
     * 发放优惠券
     * @param userId
     * @param grade 档位奖励
     * @return
     */
    boolean sendCoupon(int userId, int grade);

    /**
     * 竞猜
     * @param userId
     * @param grade
     * @return
     */
    boolean guess(int userId, int grade);

    /**
     * 判断是否已领取奖励
     * @param userId
     * @param grade
     * @return
     */
    boolean isRepeatReceive(int userId, int grade);

    /**
     * 判断是否重复竞猜
     * @param userId
     * @return
     */
    boolean isRepeatGuess(int userId);
}
