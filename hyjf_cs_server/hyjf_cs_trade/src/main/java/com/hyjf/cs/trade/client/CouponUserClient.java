/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;

import java.util.List;

/**
 * @author yaoy
 * @version CouponUserClient, v0.1 2018/6/19 18:28
 */
public interface CouponUserClient {
    List<CouponUserVO> selectCouponUser(int nowBeginDate, int nowEndDate);

    /**
     * 查询用户有效的优惠券数目
     * @author zhangyk
     * @date 2018/7/4 15:31
     */
    Integer getUserCouponCount(Integer userId, String usedFlag);

    /**
     * 根据用户名获取用户
     * @param userName
     * @return
     */
    UserVO getUser(String userName);

    /**
     * 根据用户id获取用户信息详情
     * @param userId
     * @return
     */
    UserInfoVO getUserInfo(Integer userId);

    /**
     * 根据用户id获取注册时渠道名称
     * @param userId
     * @return
     */
    String selectChannelNameByUserId(Integer userId);

    /**
     * 插入优惠券用户表
     * @param couponUser
     */
    Integer insertCouponUser(CouponUserVO couponUser);
}
