package com.hyjf.cs.user.service.myprofile;

import com.hyjf.am.vo.trade.coupon.CouponUserListCustomizeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.common.service.BaseService;
import com.hyjf.cs.user.vo.MyProfileVO;

import java.util.List;

/**
 * Created by cuigq on 2018/2/1.
 */
public interface MyProfileService extends BaseService {

    String getUserTrueName(Integer userId);

    void buildUserAccountInfo(Integer userId, MyProfileVO.UserAccountInfo userAccountInfo);

    void buildOutInfo(Integer userId, MyProfileVO myProfileVO);

    List<CouponUserListCustomizeVO> queryUserCouponList(Integer userId);

	String getUserCouponsData(String couponStatus, Integer page, Integer pageSize, Integer userId, String host);

    UserVO getUsers(Integer userId);
}
