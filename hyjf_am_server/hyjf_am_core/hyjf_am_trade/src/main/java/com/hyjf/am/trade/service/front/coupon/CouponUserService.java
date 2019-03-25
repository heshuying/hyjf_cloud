/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.coupon;

import com.hyjf.am.resquest.trade.CouponUserSearchRequest;
import com.hyjf.am.trade.dao.model.auto.CouponUser;
import com.hyjf.am.trade.dao.model.customize.CouponUserListCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version CouponUserService, v0.1 2018/6/19 19:07
 */
public interface CouponUserService {

    List<CouponUser> selectCouponUser(int nowBeginDate, int nowEndDate,Integer type);

    Integer countCouponValid(Integer userId);

    List<CouponUserListCustomize> selectCouponUserList(Map<String,Object> mapParameter);

    /**
     * 查询用户的优惠券数量
     * @author zhangyk
     * @date 2018/7/4 15:38
     */
    Integer getUserCouponCount(Integer userId , String useFlag);

    /**
     * 根据优惠券编号查询
     * @param couponCode
     * @return
     */
    Integer getIssueNumber(String couponCode);

    /**
     * 插入优惠券用户表
     * @param couponUser
     * @return
     */
    int insertCouponUser(CouponUser couponUser);

    /**
     * @Author walter.limeng
     * @Description  查询用户是否已经发送优惠券
     * @Date 18:00 2018/7/16
     * @Param couponUserRequest
     * @return
     */
    boolean getSendRepeat(CouponUserSearchRequest couponUserSearchRequest);


}
