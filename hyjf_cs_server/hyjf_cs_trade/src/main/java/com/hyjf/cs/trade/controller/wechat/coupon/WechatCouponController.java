package com.hyjf.cs.trade.controller.wechat.coupon;

import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.controller.web.coupon.MyCouponListController;
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

    /**
     * @Author walter.limeng
     * @Description  的优惠券列表
     * @Date 15:41 2018/9/28
     * @Param userId
     * @return
     */
    @ApiOperation(value = "微信端获取我的优惠券列表", notes = "微信端我的优惠券列表")
    @PostMapping(value = "/mycouponlist", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> selectMyCouponList(@RequestHeader(value = "userId") Integer userId, HttpServletRequest request){
        WebResult<Map<String,Object>> result = new WebResult<Map<String,Object>>();
        logger.info("微信端获取我的优惠券列表数据开始，userId:{}", userId);

        try {
            List<MyCouponListCustomizeVO> listUnUsed = myCouponListService.selectWechatCouponList(String.valueOf(userId), 0);
            Map<String,Object> resultMap = new HashMap<String,Object>();
            resultMap.put("object", listUnUsed);
            result.setData(resultMap);
        } catch (Exception e) {
            result.setStatus(WebResult.ERROR);
            result.setStatusDesc(WebResult.ERROR_DESC);
            logger.error("微信端查询优惠券列表异常", e);
        }

        return result;
    }
}
