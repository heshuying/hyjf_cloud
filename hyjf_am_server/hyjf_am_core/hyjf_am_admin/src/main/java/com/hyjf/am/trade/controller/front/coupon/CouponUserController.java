/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.coupon;

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
import com.hyjf.am.trade.service.front.coupon.CouponUserService;
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
 * @version CouponUserController, v0.1 2018/9/27 14:34
 */
@RestController
@RequestMapping("/am-trade/couponUser")
public class CouponUserController extends BaseController {

    @Autowired
    private CouponUserService couponUserService;

    /**
     * 根据优惠券编号查询用户优惠券
     *
     * @param couponCode
     * @return
     */
    @RequestMapping("/getIssueNumber/{couponCode}")
    public CouponUserResponse getIssueNumber(@PathVariable String couponCode) {
        CouponUserResponse response = new CouponUserResponse();
        int count = couponUserService.getIssueNumber(couponCode);
        response.setCount(count);
        return response;
    }

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
        Integer recordCount = couponUserService.countCouponUser(request);
        Paginator paginator = new Paginator(request.getCurrPage(), recordCount, request.getPageSize());
        if (request.getPageSize() == 0) {
            paginator = new Paginator(request.getCurrPage(), recordCount);
        }
        if (recordCount > 0) {
            List<CouponUserCustomize> couponUserCustomizes = couponUserService.getRecordList(request, paginator.getOffset(), paginator.getLimit());
            if (!CollectionUtils.isEmpty(couponUserCustomizes)) {
                List<CouponUserCustomizeVO> couponUserCustomizeVOS = CommonUtils.convertBeanList(couponUserCustomizes, CouponUserCustomizeVO.class);
                response.setResultList(couponUserCustomizeVOS);
            }
        }
        response.setCount(recordCount);
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
            int count = couponUserService.deleteCouponUserById(couponUserBeanRequest);
            if (count > 0) {
                response.setRtn(Response.SUCCESS);
                response.setCount(count);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response;
    }

    /**
     * 发放一条优惠券
     *
     * @param request
     * @return
     */
    @PostMapping("/insertcouponUser")
    public CouponUserResponse insertCouponUser(@RequestBody CouponUserRequest request) {
        CouponUserResponse response = new CouponUserResponse();
        try {
            int count = couponUserService.insertCouponUser(request);
            if (count > 0) {
                response.setRtn("0");
                response.setCount(count);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            response.setRtn("1");
            response.setMessage("发放失败");
        }
        return response;
    }

    /**
     * 根据优惠券编码查询用户优惠券
     *
     * @param couponCode
     * @return
     */
    @RequestMapping("/getCouponUserByCouponCode/{couponCode}")
    public CouponUserResponse getCouponUserByCouponCode(@PathVariable String couponCode) {
        CouponUserResponse response = new CouponUserResponse();
        List<CouponUser> couponUserList = couponUserService.getCouponUserByCouponCode(couponCode);
        if (!CollectionUtils.isEmpty(couponUserList)) {
            List<CouponUserVO> couponUserVOS = CommonUtils.convertBeanList(couponUserList, CouponUserVO.class);
            response.setResultList(couponUserVOS);
        }
        return response;
    }

    /**
     * 根据id查询用户优惠券
     *
     * @param couponUserId
     * @return
     */
    @RequestMapping("/selectCouponUserById/{couponUserId}")
    public CouponUserCustomizeResponse selectCouponUserById(@PathVariable Integer couponUserId) {
        CouponUserCustomizeResponse response = new CouponUserCustomizeResponse();
        CouponUser couponUser = couponUserService.selectCouponUserById(couponUserId);
        if (couponUser != null) {
            CouponUserVO couponUserVO = new CouponUserVO();
            BeanUtils.copyProperties(couponUser, couponUserVO);
            couponUserVO.setUpdateTime(GetDate.getTime10(couponUser.getUpdateTime()));
            couponUserVO.setAddTime(GetDate.getTime10(couponUser.getCreateTime()));
            response.setCouponUser(couponUserVO);
        }
        return response;
    }

    /**
     * 用户优惠券审批
     *
     * @param couponUserRequestBean
     * @return
     */
    @PostMapping("/auditRecord")
    public CouponUserCustomizeResponse auditRecord(@RequestBody AdminCouponUserRequestBean couponUserRequestBean) {
        CouponUserCustomizeResponse response = new CouponUserCustomizeResponse();
        Integer count = couponUserService.auditRecord(couponUserRequestBean);
        if (count > 0) {
            response.setCount(count);
        } else {
            response.setRtn(Response.FAIL);
            response.setMessage(Response.FAIL_MSG);
        }
        return response;
    }

}
