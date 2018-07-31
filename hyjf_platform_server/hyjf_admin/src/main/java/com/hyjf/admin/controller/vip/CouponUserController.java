/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.vip;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.coupon.CouponUserService;
import com.hyjf.admin.utils.AdminValidatorFieldCheckUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminCouponUserCustomizeResponse;
import com.hyjf.am.response.admin.CouponConfigCustomizeResponse;
import com.hyjf.am.response.admin.CouponUserCustomizeResponse;
import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.response.trade.CouponConfigResponse;
import com.hyjf.am.response.trade.CouponUserResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.admin.CouponConfigRequest;
import com.hyjf.am.resquest.admin.CouponUserBeanRequest;
import com.hyjf.am.resquest.admin.CouponUserRequest;
import com.hyjf.am.vo.admin.CouponConfigCustomizeVO;
import com.hyjf.am.vo.admin.coupon.AdminCouponUserCustomizeVO;
import com.hyjf.am.vo.admin.coupon.CouponUserCustomizeVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.ValidatorCheckUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author yaoyong
 * @version CouponUserController, v0.1 2018/7/23 15:23
 * 优惠券用户列表
 */
@Api(value = "优惠券用户列表", description = "优惠券用户列表")
@RestController
@RequestMapping("/hyjf-admin/couponUser")
public class CouponUserController extends BaseController {

    @Autowired
    private CouponUserService couponUserService;

    @ApiOperation(value = "优惠券用户列表", notes = "优惠券用户列表页面初始化")
    @PostMapping("/couponUserList")
    public AdminResult<ListResult<CouponUserCustomizeVO>> searchCouponUser(CouponUserBeanRequest couponUserBeanRequest) {
        CouponUserCustomizeResponse response = couponUserService.searchList(couponUserBeanRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<ListResult<CouponUserCustomizeVO>>(ListResult.build(response.getResultList(), response.getCount()));
    }

    @ApiOperation(value = "优惠券用户列表", notes = "删除一条优惠券")
    @PostMapping("/deleteAction")
    public AdminResult<CouponUserCustomizeVO> deleteCouponUser(HttpServletRequest request, CouponUserBeanRequest couponUserBeanRequest) {
        AdminSystemVO user = getUser(request);
        String userId = user.getId();
        int id = couponUserBeanRequest.getId();
        String remark = couponUserBeanRequest.getContent();
        CouponUserCustomizeResponse response = couponUserService.deleteById(id, remark, userId);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<CouponUserCustomizeVO>(response.getResult());
    }

    @ApiOperation(value = "优惠券用户列表",notes = "手动发放页面信息")
    @PostMapping("/distributeViewAction")
    public AdminResult<AdminCouponUserCustomizeVO> distributeViewAction(CouponConfigRequest request) {
        request.setStatus(CustomConstants.COUPON_STATUS_PUBLISHED);
        AdminCouponUserCustomizeResponse response = couponUserService.getRecordList(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<AdminCouponUserCustomizeVO>(response.getResult());
    }


    @ApiOperation(value = "优惠券用户列表",notes = "发放一条优惠券")
    @PostMapping("/distributeAction")
    public AdminResult distributeAction(CouponUserBeanRequest couponUserBeanRequest,HttpServletRequest request) {
        AdminSystemVO user = getUser(request);
        String loginUserId = user.getId();
        //校验请求参数
        JSONObject json = new JSONObject();
        json = this.validatorFieldCheck(json, couponUserBeanRequest);
        if (AdminValidatorFieldCheckUtil.hasValidateError(json)) {
            return new AdminResult<>(FAIL, "校验失败");
        }
        //校验数量
        if(couponUserBeanRequest.getAmount() == null || couponUserBeanRequest.getAmount() < 0){
            couponUserBeanRequest.setAmount(1);
        }
        CouponUserCustomizeResponse response = new CouponUserCustomizeResponse();
        //根据用户名获取用户id
        String userName = couponUserBeanRequest.getUserName();
        UserResponse userResponse = couponUserService.getUser(userName);
        UserVO userVO = userResponse.getResult();
        Integer userId = userVO.getUserId();

        //根据用户id获取用户详情信息
        UserInfoResponse userInfoResponse = couponUserService.getUserInfo(userId);
        UserInfoVO userInfoVO = userInfoResponse.getResult();

        //根据用户id获取注册时渠道名
        UtmResponse utmResponse = couponUserService.getChannelName(userId);
        String channelName = utmResponse.getChannelName();

        //根据优惠券编码查询优惠券
        CouponConfigResponse configResponse = couponUserService.getCouponConfig(couponUserBeanRequest.getCouponCode());
        CouponConfigVO configVO = configResponse.getResult();

        CouponUserRequest couponUserRequest = new CouponUserRequest();
        //截止日
        if(configVO.getExpirationType() == 1){
            couponUserRequest.setEndTime(configVO.getExpirationDate());
        }else if(configVO.getExpirationType() == 2){
            Date endDate = GetDate.countDate(GetDate.getDate(), 2, configVO.getExpirationLength());
            couponUserRequest.setEndTime((int)(endDate.getTime()/1000));
        }else if(configVO.getExpirationType() == 3){
            Date endDate = GetDate.countDate(GetDate.getDate(), 5, configVO.getExpirationLengthDay());
            couponUserRequest.setEndTime((int)(endDate.getTime()/1000));
        }
        couponUserRequest.setUserId(userId);
        couponUserRequest.setCouponUserCode(GetCode.getCouponUserCode(configVO.getCouponType()));
        couponUserRequest.setCreateUserId(Integer.parseInt(loginUserId));
        couponUserRequest.setCreateTime(GetDate.getDate());
        couponUserRequest.setUpdateUserId(Integer.parseInt(loginUserId));
        couponUserRequest.setUpdateTime(GetDate.getDate());
        couponUserRequest.setDelFlag(CustomConstants.FALG_NOR);
        couponUserRequest.setUsedFlag(CustomConstants.USER_COUPON_STATUS_WAITING_PUBLISH);
        couponUserRequest.setReadFlag(CustomConstants.USER_COUPON_READ_FLAG_NO);
        if(couponUserRequest.getActivityId() == null){
            couponUserRequest.setCouponSource(CustomConstants.USER_COUPON_SOURCE_MANUAL);
        }else{
            couponUserRequest.setCouponSource(CustomConstants.USER_COUPON_SOURCE_ACTIVE);
        }
        couponUserRequest.setAttribute(userInfoVO.getAttribute());
        couponUserRequest.setChannel(channelName);
        for(int i=0; i< couponUserBeanRequest.getAmount(); i++){
            CouponUserResponse couponUserResponse = couponUserService.insertCouponUser(couponUserRequest);
//            this.operationLog(couponUser, CustomConstants.OPERATION_CODE_INSERT);
        }
        return new AdminResult<>();
    }





    private JSONObject validatorFieldCheck(JSONObject jsonObject, CouponUserBeanRequest request) {
        //优惠券编码
        AdminValidatorFieldCheckUtil.validateRequired(jsonObject,"couponCode",request.getCouponCode());
        //用户名
        AdminValidatorFieldCheckUtil.validateRequired(jsonObject,"userName",request.getUserName());
        return jsonObject;
    }
}
