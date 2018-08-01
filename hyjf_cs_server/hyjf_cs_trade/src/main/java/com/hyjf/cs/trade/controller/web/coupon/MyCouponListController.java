package com.hyjf.cs.trade.controller.web.coupon;

import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.service.coupon.MyCouponListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 我的优惠券列表
     * @auther: hesy
     * @date: 2018/6/23
     */
    @ApiOperation(value = "获取我的优惠券列表", notes = "我的优惠券列表")
    @PostMapping(value = "/myCouponList", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> selectMyCouponList(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request){
        WebResult<Map<String,Object>> result = new WebResult<Map<String,Object>>();
        WebViewUserVO userVO = myCouponListService.getUsersByToken(token);
        logger.info("获取我的优惠券列表数据开始，userId:{}", userVO.getUserId());

        try {
            List<MyCouponListCustomizeVO> listUnUsed = myCouponListService.selectMyCouponListUnUsed(String.valueOf(userVO.getUserId()));
            List<MyCouponListCustomizeVO> listUsed = myCouponListService.selectMyCouponListUsed(String.valueOf(userVO.getUserId()));
            List<MyCouponListCustomizeVO> listInValid = myCouponListService.selectMyCouponListInValid(String.valueOf(userVO.getUserId()));
            Map<String,String> pageData = myCouponListService.selectInvitePageData(String.valueOf(userVO.getUserId()));
            Map<String,Object> resultMap = new HashMap<String,Object>();
            resultMap.put("wsyList", listUnUsed);
            resultMap.put("ysyList", listUsed);
            resultMap.put("ysxList", listInValid);
            resultMap.putAll(pageData);
            result.setData(resultMap);
        } catch (Exception e) {
            result.setStatus(WebResult.ERROR);
            result.setStatusDesc(WebResult.ERROR_DESC);
            logger.error("查询优惠券列表异常", e);
        }

        return result;
    }
}
