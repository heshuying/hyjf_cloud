package com.hyjf.cs.trade.controller.app.coupon;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.vo.trade.coupon.CouponUserForAppCustomizeVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.coupon.AppCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 优惠券Controller
 *
 * @author zhangqingqing
 *
 */
@Api(tags = "app端-用户优惠券接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/hyjf-app/coupon")
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
    public JSONObject getUserCoupons(@RequestHeader(value = "userId") Integer userId, HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();
        logger.info("获取我的优惠券列表");
        String couponStatus = "0";
        Integer page = 1;
        Integer pageSize = 100;
        // 取得用户ID
        if(null != userId){
            Map<String,Object> resultMap = new HashMap<String,Object>();
            Integer count =  appCouponService.countMyCoupon(userId,couponStatus);
            MyCouponListRequest requestBean = new MyCouponListRequest();
            requestBean.setUserId(userId+"");
            requestBean.setUsedFlag(couponStatus);
            requestBean.setLimitStart((page-1) * pageSize);
            requestBean.setLimitEnd(page * pageSize);
            requestBean.setPlatform(request.getParameter("platform"));
            List<CouponUserForAppCustomizeVO> couponList = appCouponService.getMyCoupon(requestBean);
            if(!CollectionUtils.isEmpty(couponList)){
                couponList.sort(Comparator.comparing(CouponUserForAppCustomizeVO::getEndTimeStamp));
            }
            result.put("couponTotal",count);
            result.put("couponStatus",couponStatus);
            result.put("couponList",couponList);
            result.put("request","/hyjf-app/coupon/getUserCoupons");
            result.put("status","0");
            result.put("statusDesc","用户未登录");
        }else{
            result.put("status",WebResult.FAIL);
            result.put("statusDesc","用户未登录");
            logger.info("用户未登录！");
        }
        return result;
    }

    @ApiOperation(value = "APP根据borrowNid和用户id获取用户可用优惠券和不可用优惠券列表", notes = "根据borrowNid和用户id获取用户可用优惠券和不可用优惠券列表")
    @PostMapping("/getProjectAvailableUserCoupon")
    public JSONObject getProjectAvailableUserCoupon(@RequestHeader(value = "userId") Integer userId,@RequestParam String borrowNid,
                                                    @RequestParam String money,@RequestParam String borrowType,
                                                    @RequestParam String platform) throws Exception {
        JSONObject ret = new JSONObject();
        // 检查参数正确性
        if ( Validator.isNull(borrowNid)||  Validator.isNull(platform)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        if(money==null||"".equals(money)||money.length()==0){
            money="0";
        }
        String investType = borrowType;
        logger.info("investType is :{}", investType);
        if(investType != null){

            if(investType==null||!"HJH".equals(investType)){
                // 如果为空  就执行以前的逻辑
                ret = appCouponService.getBorrowCoupon(userId,borrowNid,money,platform);
            }else{
                // HJH的接口
                ret = appCouponService.getPlanCoupon(userId,borrowNid,money,platform);
            }
        }else {
            if(borrowNid.contains("HJH")){
                ret = appCouponService.getPlanCoupon(userId,borrowNid,money,platform);
            }else{
                // 如果为空  就执行以前的逻辑
                ret = appCouponService.getBorrowCoupon(userId,borrowNid,money,platform);
            }
        }
        ret.put("status","0");
        ret.put("statusDesc","成功");
        return ret;
    }

    /**
     * @Author walter.limeng
     * @Description  APP,PC加入计划获取我的优惠券列表
     * @Date 17:15 2018/8/13
     * @Param
     * @return
     */
    @ApiOperation(value = "APP加入计划获取我的优惠券列表", notes = "APP加入计划获取我的优惠券列表")
    @PostMapping("/getplancoupon")
    public WebResult<Map<String,Object>> getPlanCoupon(@RequestHeader(value = "userId") Integer userId,
                                                       @RequestParam String planNid,
                                                       @RequestParam String money,
                                                       @RequestParam String platform) throws Exception {
        WebResult<Map<String,Object>> result = new WebResult<Map<String,Object>>();
        WebViewUserVO user = RedisUtils.getObj(RedisConstants.USERID_KEY + userId, WebViewUserVO.class);
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
