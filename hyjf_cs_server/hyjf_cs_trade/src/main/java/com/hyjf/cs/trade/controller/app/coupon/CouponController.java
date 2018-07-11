package com.hyjf.cs.trade.controller.app.coupon;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.coupon.CouponUserForAppCustomizeVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.WebViewUser;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.coupon.AppCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 优惠券Controller
 *
 * @author zhangqingqing
 *
 */
@Api(value = "app端用户优惠券接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/app/coupon")
public class CouponController extends BaseTradeController {
    Logger logger = LoggerFactory.getLogger(CouponController.class);

    @Autowired
    private AppCouponService appCouponService;

    /**
     * APP获取我的优惠券列表
     * @param request
     * @return
     */
    @ApiOperation(value = "获取我的优惠券列表", notes = "获取我的优惠券列表")
    @PostMapping("/getUserCoupons")
    public WebResult<Map<String,Object>> getUserCoupons(@RequestHeader(value = "token") String token, HttpServletRequest request) throws Exception {
        WebResult<Map<String,Object>> result = new WebResult<Map<String,Object>>();
        logger.info("获取我的优惠券列表");
        String couponStatus = "0";
        Integer page = 1;
        Integer pageSize = 100;
        WebViewUser user = RedisUtils.getObj(token, WebViewUser.class);
        if(null != user && null != user.getUserId()){
            Map<String,Object> resultMap = new HashMap<String,Object>();
            Integer count =  appCouponService.countMyCoupon(user.getUserId(),couponStatus);
            List<CouponUserForAppCustomizeVO> couponList = appCouponService.getMyCoupon(user.getUserId(),page,pageSize,couponStatus);
            resultMap.put("couponTotal",count);
            resultMap.put("couponStatus",couponStatus);
            resultMap.put("couponList",couponList);
            resultMap.put("request","/app/coupon/getUserCoupons");
            result.setData(resultMap);
        }else{
            result.setStatus(WebResult.FAIL);
            result.setStatusDesc("用户未登录");
            logger.info("用户未登录！");
        }
        return result;
    }

    /**
     * APP,PC散标投资获取我的优惠券列表
     * @param request
     * @return
     */
    @ApiOperation(value = "APP散标投资获取我的优惠券列表", notes = "APP散标投资获取我的优惠券列表")
    @PostMapping("/getborrowcoupon")
    public WebResult<Map<String,Object>> getBorrowCoupon(@RequestHeader(value = "token") String token, HttpServletRequest request,String borrowNid,String money,String platform) throws Exception {
        WebResult<Map<String,Object>> result = new WebResult<Map<String,Object>>();
        WebViewUser user = RedisUtils.getObj(token, WebViewUser.class);
        if(null != user && null != user.getUserId()){
            JSONObject resultMap = new JSONObject();
            resultMap = appCouponService.getBorrowCoupon(user.getUserId(),borrowNid,money,platform);
            result.setData(resultMap);
        }else{
            result.setStatus(WebResult.FAIL);
            result.setStatusDesc("用户未登录");
            logger.info("用户未登录！");
        }
        return result;
    }

    /**
     * APP,PC加入计划获取我的优惠券列表
     * @param request
     * @return
     */
    @ApiOperation(value = "APP加入计划获取我的优惠券列表", notes = "APP加入计划获取我的优惠券列表")
    @PostMapping("/getplancoupon")
    public WebResult<Map<String,Object>> getPlanCoupon(@RequestHeader(value = "token") String token, HttpServletRequest request,String planNid,String money,String platform) throws Exception {
        WebResult<Map<String,Object>> result = new WebResult<Map<String,Object>>();
        WebViewUser user = RedisUtils.getObj(token, WebViewUser.class);
        if(null != user && null != user.getUserId()){
            JSONObject resultMap = new JSONObject();
            resultMap = appCouponService.getPlanCoupon(user.getUserId(),planNid,money,platform);
            result.setData(resultMap);
        }else{
            result.setStatus(WebResult.FAIL);
            result.setStatusDesc("用户未登录");
            logger.info("用户未登录！");
        }
        return result;
    }

}
