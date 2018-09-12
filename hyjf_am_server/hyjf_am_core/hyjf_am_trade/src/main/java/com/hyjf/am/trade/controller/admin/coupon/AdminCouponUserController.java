/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.coupon;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CouponUserCustomizeResponse;
import com.hyjf.am.response.trade.CouponUserResponse;
import com.hyjf.am.resquest.admin.AdminCouponUserRequestBean;
import com.hyjf.am.resquest.admin.CouponUserBeanRequest;
import com.hyjf.am.resquest.admin.CouponUserRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.CouponUser;
import com.hyjf.am.trade.dao.model.customize.CouponUserCustomize;
import com.hyjf.am.trade.service.admin.coupon.AdminCouponUserService;
import com.hyjf.am.vo.admin.coupon.CouponUserCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author yaoyong
 * @version AdminCouponUserController, v0.1 2018/7/23 16:54
 * 后台优惠券用户
 */
@RestController
@RequestMapping("/am-trade/adminCouponUser")
public class AdminCouponUserController extends BaseController {

    @Autowired
    private AdminCouponUserService adminCouponUserService;

    /**
     * 优惠券用户列表
     *
     * @param request
     * @return
     */
    @PostMapping("/getCouponUserList")
    public CouponUserCustomizeResponse getCouponUserList(@RequestBody @Valid CouponUserBeanRequest request) {
        logger.info("后台优惠券用户列表" + JSONObject.toJSON(request));
        CouponUserCustomizeResponse response = new CouponUserCustomizeResponse();
        String returnCode = Response.FAIL;
        Integer recordCount = adminCouponUserService.countCouponUser(request);
        Paginator paginator = new Paginator(request.getCurrPage(), recordCount, request.getPageSize());
        if (request.getPageSize() == 0) {
            paginator = new Paginator(request.getCurrPage(), recordCount);
        }
        List<CouponUserCustomize> couponUserCustomizes = adminCouponUserService.getRecordList(request, paginator.getOffset(), paginator.getLimit());
        if (recordCount > 0) {
            if (!CollectionUtils.isEmpty(couponUserCustomizes)) {
                List<CouponUserCustomizeVO> couponUserCustomizeVOS = CommonUtils.convertBeanList(couponUserCustomizes, CouponUserCustomizeVO.class);
                response.setResultList(couponUserCustomizeVOS);
                response.setCount(recordCount);
                returnCode = Response.SUCCESS;
            }
        }
        response.setRtn(returnCode);
        return response;
    }


    /**
     * 根据id删除一条优惠券
     *
     * @param couponUserBeanRequest
     * @return
     */
    @RequestMapping("/deleteCouponUser")
    public CouponUserCustomizeResponse deleteCouponUser(@RequestBody CouponUserBeanRequest couponUserBeanRequest) {
        CouponUserCustomizeResponse response = new CouponUserCustomizeResponse();
        try {
            int count = adminCouponUserService.deleteCouponUserById(couponUserBeanRequest);
            if (count > 0) {
                response.setRtn(Response.SUCCESS);
                response.setCount(count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 发放一条优惠券
     *
     * @param request
     * @return
     */
    @PostMapping("/insertCouponUser")
    public CouponUserResponse insertCouponUser(@RequestBody CouponUserRequest request) {
        CouponUserResponse response = new CouponUserResponse();
        try {
            int count = adminCouponUserService.insertCouponUser(request);
            if (count > 0) {
                response.setRtn("0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setRtn("1");
            response.setMessage("发放失败");
        }
        return response;
    }

    /**
     * 根据优惠券编码查询用户优惠券
     * @param couponCode
     * @return
     */
    @RequestMapping("/getCouponUserByCouponCode/{couponCode}")
    public CouponUserResponse getCouponUserByCouponCode(@PathVariable String couponCode) {
        CouponUserResponse response = new CouponUserResponse();
        List<CouponUser> couponUserList = adminCouponUserService.getCouponUserByCouponCode(couponCode);
        if (!CollectionUtils.isEmpty(couponUserList)) {
            List<CouponUserVO> couponUserVOS = CommonUtils.convertBeanList(couponUserList,CouponUserVO.class);
            response.setResultList(couponUserVOS);
        }
        return response;
    }

    /**
     * 根据id查询用户优惠券
     * @param couponUserId
     * @return
     */
    @RequestMapping("/selectCouponUserById/{couponUserId}")
    public CouponUserCustomizeResponse selectCouponUserById(@PathVariable Integer couponUserId) {
        CouponUserCustomizeResponse response = new CouponUserCustomizeResponse();
        CouponUser couponUser = adminCouponUserService.selectCouponUserById(couponUserId);
        if (couponUser != null) {
            CouponUserVO couponUserVO = new CouponUserVO();
            BeanUtils.copyProperties(couponUser,couponUserVO);
            couponUserVO.setUpdateTime(GetDate.getTime10(couponUser.getUpdateTime()));
            couponUserVO.setAddTime(GetDate.getTime10(couponUser.getCreateTime()));
            response.setCouponUser(couponUserVO);
        }
        return response;
    }

    /**
     * 用户优惠券审批
     * @param couponUserRequestBean
     * @return
     */
    @PostMapping("/auditRecord")
    public CouponUserCustomizeResponse auditRecord(@RequestBody AdminCouponUserRequestBean couponUserRequestBean) {
        CouponUserCustomizeResponse response = new CouponUserCustomizeResponse();
        Integer count = adminCouponUserService.auditRecord(couponUserRequestBean);
        if (count > 0) {
            response.setCount(count);
        }else {
            response.setRtn(Response.FAIL);
            response.setMessage(Response.FAIL_MSG);
        }
        return response;
    }

}
