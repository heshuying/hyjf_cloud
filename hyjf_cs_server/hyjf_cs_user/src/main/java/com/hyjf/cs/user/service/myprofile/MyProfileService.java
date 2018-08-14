package com.hyjf.cs.user.service.myprofile;

import com.hyjf.am.vo.trade.coupon.CouponUserForAppCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponUserListCustomizeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.common.service.BaseService;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.cs.user.vo.MyProfileVO;
import com.hyjf.cs.user.vo.UserAccountInfoVO;

import java.util.List;

/**
 * 账户总览接口service
 * Created by jijun on 2018/7/5.
 */
public interface MyProfileService extends BaseUserService {
    /**
     * 通过userId获取用户真实姓名
     * @param userId
     * @return
     */
    String getUserCallName(Integer userId);

    /**
     * 装填用户账户信息
     * @param userId
     * @param userAccountInfo
     */
    void buildUserAccountInfo(Integer userId, UserAccountInfoVO userAccountInfo);

    /**
     * 装填用户账户数据
     * @param userId
     * @param myProfileVO
     */
    void buildOutInfo(Integer userId, MyProfileVO myProfileVO);

    /**
     * 获取用户优惠券列表
     * @param userId
     * @return
     */
    List<CouponUserListCustomizeVO> queryUserCouponList(Integer userId);

    /**
     * 获取用户优惠券数据
     * @param couponStatus
     * @param page
     * @param pageSize
     * @param userId
     * @param host
     * @return
     */
    List<CouponUserForAppCustomizeVO> getUserCouponsData(String couponStatus, Integer page, Integer pageSize, Integer userId, String host);

}
