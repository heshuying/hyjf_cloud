/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.coupon;

import com.hyjf.am.response.trade.CouponUserListCustomizeResponse;
import com.hyjf.am.response.trade.CouponUserResponse;
import com.hyjf.am.resquest.trade.CouponUserSearchRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.CouponUser;
import com.hyjf.am.trade.dao.model.customize.CouponUserListCustomize;
import com.hyjf.am.trade.service.front.coupon.CouponUserService;
import com.hyjf.am.vo.trade.coupon.CouponUserListCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version CouponUserController, v0.1 2018/6/19 18:48
 */
@RestController
@RequestMapping("/am-trade/couponUser")
public class CouponUserController extends BaseController {

    @Autowired
    private CouponUserService couponUserService;

    @RequestMapping("/selectCouponUser/{nowBeginDate}/{nowEndDate}/{type}")
    public CouponUserResponse selectCouponUser(@PathVariable int nowBeginDate, @PathVariable int nowEndDate, @PathVariable int type) {
        CouponUserResponse response = new CouponUserResponse();
        List<CouponUser> couponUserList = couponUserService.selectCouponUser(nowBeginDate, nowEndDate,type);
        if (!CollectionUtils.isEmpty(couponUserList)) {
            List<CouponUserVO> couponUserVOList = CommonUtils.convertBeanList(couponUserList, CouponUserVO.class);
            response.setResultList(couponUserVOList);
        }
        return response;
    }


    @GetMapping("/countCouponValid/{userId}")
    public Integer countCouponValid(@PathVariable Integer userId) {
        return couponUserService.countCouponValid(userId);
    }


    @PostMapping("/selectCouponUserList")
    public CouponUserListCustomizeResponse selectCouponUserList(@RequestBody Map<String, Object> mapParameter) {
        CouponUserListCustomizeResponse response = new CouponUserListCustomizeResponse();
        List<CouponUserListCustomize> lstResult = couponUserService.selectCouponUserList(mapParameter);
        if (CollectionUtils.isNotEmpty(lstResult)) {
            response.setResultList(CommonUtils.convertBeanList(lstResult, CouponUserListCustomizeVO.class));
        }
        return response;
    }


//    @RequestMapping("/getIssueNumber/{couponCode}")
//    public CouponUserResponse getIssueNumber(@PathVariable String couponCode) {
//        CouponUserResponse response = new CouponUserResponse();
//        int count = couponUserService.getIssueNumber(couponCode);
//        response.setCount(count);
//        return response;
//    }


    /**
     * 查询用户的优惠券数目(useFlag[0:未使用，1：已使用])
     *
     * @author zhangyk
     * @date 2018/7/4 15:34
     */
    @RequestMapping("/user_coupon_count/{userId}/{useFlag}")
    public CouponUserResponse getUserCouponCount(@PathVariable Integer userId, @PathVariable String useFlag) {
        CouponUserResponse response = new CouponUserResponse();
        Integer count = couponUserService.getUserCouponCount(userId, useFlag);
        response.setCount(count);
        return response;
    }


    @PostMapping("/insertCouponUser")
    public CouponUserResponse insertCouponUser(@RequestBody @Valid CouponUserVO couponUserVO) {
        CouponUserResponse response = new CouponUserResponse();
        CouponUser couponUser = new CouponUser();
        BeanUtils.copyProperties(couponUserVO, couponUser);
        int count = couponUserService.insertCouponUser(couponUser);
        response.setCount(count);
        return response;
    }

    /**
     * @return
     * @Author walter.limeng
     * @Description 查询用户优惠券
     * @Date 18:06 2018/7/16
     * @Param couponUserRequest
     */
    @PostMapping("/getsendrepeat")
    public CouponUserResponse getSendRepeat(@RequestBody @Valid CouponUserSearchRequest couponUserSearchRequest) {
        CouponUserResponse response = new CouponUserResponse();
        boolean isSend = couponUserService.getSendRepeat(couponUserSearchRequest);
        response.setSend(isSend);
        return response;
    }


}
