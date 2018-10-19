package com.hyjf.cs.trade.controller.wechat.coupon;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.AppCouponRequest;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
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
import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/9/28 15:18
 * @Description: WechatCouponController
 */
@Api(value = "wechat端-我的优惠券列表", tags = "wechat端-我的优惠券列表")
@RestController
@RequestMapping("/hyjf-wechat/wechatcoupon")
public class WechatCouponController {
    private static final Logger logger = LoggerFactory.getLogger(WechatCouponController.class);
    @Autowired
    MyCouponListService myCouponListService;
    @Autowired
    private AppCouponService appCouponService;

    /**
     * @Author walter.limeng
     * @Description  的优惠券列表
     * @Date 15:41 2018/9/28
     * @Param userId
     * @return
     */
    @ApiOperation(value = "微信端获取我的优惠券列表", notes = "微信端我的优惠券列表")
    @PostMapping(value = "/mycouponlist", produces = "application/json; charset=utf-8")
    public WebResult<List<MyCouponListCustomizeVO>> selectMyCouponList(@RequestHeader(value = "userId") Integer userId, HttpServletRequest request){
        WebResult<List<MyCouponListCustomizeVO>> result = new WebResult<List<MyCouponListCustomizeVO>>();
        logger.info("微信端获取我的优惠券列表数据开始，userId:{}", userId);

        try {
            List<MyCouponListCustomizeVO> listUnUsed = myCouponListService.selectWechatCouponList(String.valueOf(userId), 0);
            result.setData(listUnUsed);
        } catch (Exception e) {
            result.setStatus(WebResult.ERROR);
            result.setStatusDesc(WebResult.ERROR_DESC);
            logger.error("微信端查询优惠券列表异常", e);
        }

        return result;
    }

    @ApiOperation(value = "wechat根据borrowNid和用户id获取用户可用优惠券和不可用优惠券列表", notes = "根据borrowNid和用户id获取用户可用优惠券和不可用优惠券列表")
    @PostMapping("/getborrowcoupon")
    public JSONObject getProjectAvailableUserCoupon(@RequestHeader(value = "userId") Integer userId, @RequestBody AppCouponRequest appCouponRequest) throws Exception {
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
        return ret;
    }
}
