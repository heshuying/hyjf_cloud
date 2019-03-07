/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.coupon;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.front.coupon.CheckCouponService;
import com.hyjf.common.security.util.MD5;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;


/**
 * @author yaoyong
 * @version CheckCouponController, v0.1 2018/7/6 16:15
 */
@RestController
@ApiIgnore
@RequestMapping("/am-trade/checkCoupon")
public class CheckCouponController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(CheckCouponController.class);
    @Autowired
    CheckCouponService checkCouponService;
    @Value("${release.coupon.accesskey}")
    public String SOA_COUPON_KEY;

    /**
     * 批量上传发券接口
     */
    @PostMapping("/getBatchCoupons")
    public JSONObject getBatchCoupons(Map<String, Object> map) {
        logger.info("优惠券批量发放开始");
        JSONObject result = new JSONObject();
        String userId = map.get("userId").toString();
        String timestamp = String.valueOf(GetDate.getNowTime10());
        String chkValue = StringUtils.lowerCase(MD5.toMD5Code(SOA_COUPON_KEY + userId + timestamp + SOA_COUPON_KEY));
        map.put("sign",chkValue);
        map.put("timestamp",timestamp);
//        BatchCouponsRequest batchCouponsRequest = new BatchCouponsRequest();
//        // 用户id
//        batchCouponsRequest.setUserId(userId);
//        // 时间戳
//        batchCouponsRequest.setTimestamp(timestamp);
//        // 签名
//        batchCouponsRequest.setChkValue(chkValue);
//        // 数据
//        batchCouponsRequest.setUsercoupons(map.get("usercoupons").toString());

//        try {
//            if(checkSign(map)){
        try {
            result = checkCouponService.batchInsertUserCoupon(map);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
//            }else{
//                logger.info("用户："+ userId +",验签失败！");
//            }
//        } catch (Exception e) {
//            logger.info("用户："+ userId +",发放优惠券---失败");
//            result.put("status", 1);
//        }
        return result;
    }

    /**
     * 验证签名
     *
     * @param map
     * @return
     */
//    private boolean checkSign(Map<String,Object> map) {
//
//        String userId = map.get("userId").toString();
//        String timestamp = map.get("timestamp").toString();
//
//        String accessKey = SOA_COUPON_KEY;
//        String sign = StringUtils.lowerCase(MD5.toMD5Code(accessKey + userId + timestamp + accessKey));
//        return StringUtils.equals(sign, map.get("sign").toString()) ? true : false;
//    }
}
