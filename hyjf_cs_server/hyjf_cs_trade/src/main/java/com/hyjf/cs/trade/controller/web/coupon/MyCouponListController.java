package com.hyjf.cs.trade.controller.web.coupon;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.AppCouponRequest;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.service.coupon.AppCouponService;
import com.hyjf.cs.trade.service.coupon.MyCouponListService;
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
 * 我的优惠券列表
 * @author hesy
 * @version MyCouponListController, v0.1 2018/6/23 14:09
 */
@Api(value = "web端-我的优惠券列表", tags = "web端-我的优惠券列表")
@RestController
@RequestMapping("/hyjf-web/coupon")
public class MyCouponListController {
    private static final Logger logger = LoggerFactory.getLogger(MyCouponListController.class);

    @Autowired
    MyCouponListService myCouponListService;
    @Autowired
    private AppCouponService appCouponService;

    /**
     * 我的优惠券列表
     * @auther: hesy
     * @date: 2018/6/23
     */
    @ApiOperation(value = "获取我的优惠券列表", notes = "我的优惠券列表")
    @PostMapping(value = "/myCouponList", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> selectMyCouponList(@RequestHeader(value = "userId") Integer userId, HttpServletRequest request){
        WebResult<Map<String,Object>> result = new WebResult<>();
        WebViewUserVO userVO = myCouponListService.getUserFromCache(userId);
        logger.info("获取我的优惠券列表数据开始，userId:{}", userVO.getUserId());

        try {
            List<MyCouponListCustomizeVO> listUnUsed = myCouponListService.selectMyCouponListUnUsed(String.valueOf(userVO.getUserId()));
            List<MyCouponListCustomizeVO> listUsed = myCouponListService.selectMyCouponListUsed(String.valueOf(userVO.getUserId()));
            List<MyCouponListCustomizeVO> listInValid = myCouponListService.selectMyCouponListInValid(String.valueOf(userVO.getUserId()));

            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("wsyList", listUnUsed);
            resultMap.put("ysyList", listUsed);
            resultMap.put("ysxList", listInValid);
            result.setData(resultMap);
        } catch (Exception e) {
            result.setStatus(WebResult.ERROR);
            result.setStatusDesc(WebResult.ERROR_DESC);
            logger.error("查询优惠券列表异常", e);
        }

        return result;
    }

    @ApiOperation(value = "web根据borrowNid和用户id获取用户可用优惠券和不可用优惠券列表", notes = "根据borrowNid和用户id获取用户可用优惠券和不可用优惠券列表")
    @PostMapping("/getborrowcoupon")
    public JSONObject getProjectAvailableUserCoupon(@RequestHeader(value = "userId") Integer userId,@RequestBody AppCouponRequest appCouponRequest) throws Exception {
        JSONObject ret = new JSONObject();
        // 检查参数正确性
        String borrowNid = appCouponRequest.getBorrowNid();
        String investType = appCouponRequest.getBorrowType();
        String money = appCouponRequest.getMoney();
        String platform = appCouponRequest.getPlatform();
        if ( Validator.isNull(borrowNid)||  Validator.isNull(platform)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        if(money==null||"".equals(money)||money.length()==0){
            money="0";
        }
        logger.info("investType is :{}", investType);
        JSONObject json = new JSONObject();
        if(investType != null){

            if(investType==null||!"HJH".equals(investType)){
                // 如果为空  就执行以前的逻辑
                json = appCouponService.getBorrowCoupon(userId,borrowNid,money,platform);
            }else{
                // HJH的接口
                json = appCouponService.getPlanCoupon(userId,borrowNid,money,platform);
            }
        }else {
            if(borrowNid.contains("HJH")){
                json = appCouponService.getPlanCoupon(userId,borrowNid,money,platform);
            }else{
                // 如果为空  就执行以前的逻辑
                json = appCouponService.getBorrowCoupon(userId,borrowNid,money,platform);
            }
        }
        ret.put("status","000");
        ret.put("statusDesc","成功");
        ret.put("data",json);
        logger.info("ret is :{}", JSONObject.toJSON(ret));
        return ret;
    }
}
