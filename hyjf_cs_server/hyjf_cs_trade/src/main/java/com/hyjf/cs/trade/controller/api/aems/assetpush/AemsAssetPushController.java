/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.api.aems.assetpush;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.util.ApiSignUtil;
import com.hyjf.cs.trade.bean.AemsPushBean;
import com.hyjf.cs.trade.bean.AemsPushRequestBean;
import com.hyjf.cs.trade.bean.AemsPushResultBean;
import com.hyjf.cs.trade.bean.assetpush.PushResultBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.aems.assetpush.AemsAssetPushService;
import com.hyjf.cs.trade.util.ErrorCodeConstant;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * AEMS资产推送接口
 * @author Zha Daojian
 * @date 2018/12/19 15:27
 * @param
 * @return
 **/
@Api(tags = "api端-AEMS资产推送接口")
@RestController
@RequestMapping("/hyjf-api/aems/assetpush")
public class AemsAssetPushController extends BaseTradeController {

    private static final Logger logger = LoggerFactory.getLogger(AemsAssetPushController.class);

    public static final String AEMS_ASSETPUSH = "/hyjf-api/aems/assetpush";
    public static final String PERSON = "/push";
    public static final String COMPANY = "/pushcompany";

    @Autowired
    private AemsAssetPushService pushService;

    @PostMapping(PERSON)
    @ApiParam(required = true, name = "pushRequestBean", value = "个人资产信息")
    @ApiOperation(value = "AEMS-个人资产推送", httpMethod = "POST", notes = "AEMS-个人资产推送")
    @HystrixCommand(commandKey = "API-AEMS个人资产推送", fallbackMethod = "fallBackAssetPush", ignoreExceptions = CheckException.class, commandProperties = {
            //设置断路器生效
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            //一个统计窗口内熔断触发的最小个数3/10s
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
            @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "50"),
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
            //熔断5秒后去尝试请求
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            //失败率达到30百分比后熔断
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30")})
    public JSONObject push(@RequestBody AemsPushRequestBean pushRequestBean) {
        logger.info("API端-AEMS个人资产推送[开始]，请求参数["+ pushRequestBean.toString() +"]，接口路径+["+ AEMS_ASSETPUSH+PERSON +"]");

        JSONObject result = aemsAssetPushParamCheck(pushRequestBean, PERSON);
        if (result != null){
            return result;
        }

        //AEMS个人资产推送
        AemsPushResultBean resultBean = pushService.assetPush(pushRequestBean);

        logger.info("API端-AEMS个人资产推送[结束]");
        result = new JSONObject();
        result.put("data", resultBean.getData());
        result.put("status", resultBean.getStatus());
        result.put("chkValue",resultBean.getChkValue());
        result.put("statusDesc", resultBean.getStatusDesc());
        return result;
    }


    @PostMapping(COMPANY)
    @ApiParam(required = true, name = "pushRequestBean", value = "企业资产信息")
    @ApiOperation(value = "AEMS-企业资产推送", httpMethod = "POST", notes = "AEMS-企业资产推送")
    @HystrixCommand(commandKey = "API-AEMS个人资产推送", fallbackMethod = "fallBackAssetPush", ignoreExceptions = CheckException.class, commandProperties = {
            //设置断路器生效
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            //一个统计窗口内熔断触发的最小个数3/10s
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
            @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "50"),
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
            //熔断5秒后去尝试请求
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            //失败率达到30百分比后熔断
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30")})
    public JSONObject pushcompany(@RequestBody AemsPushRequestBean pushRequestBean) {
        logger.info("API端-AEMS企业资产推送[开始]，请求参数["+ pushRequestBean.toString() +"]，接口路径+["+ AEMS_ASSETPUSH+COMPANY +"]");

        JSONObject result = aemsAssetPushParamCheck(pushRequestBean, COMPANY);
        if (result != null){
            return result;
        }

        //AEMS企业资产推送
        AemsPushResultBean resultBean = pushService.companyAssetPush(pushRequestBean);

        logger.info("API端-AEMS企业资产推送[结束]");
        result = new JSONObject();
        result.put("data", resultBean.getData());
        result.put("status", resultBean.getStatus());
        result.put("chkValue",resultBean.getChkValue());
        result.put("statusDesc", resultBean.getStatusDesc());
        return result;
    }

    /**
     * A-EMS资产推送参数校验
     * @return
     */
    private JSONObject aemsAssetPushParamCheck(AemsPushRequestBean pushRequestBean, String flag){
        JSONObject result = new JSONObject();
        // 验证请求参数
        List<AemsPushBean> reqData = pushRequestBean.getReqData();
        if (Validator.isNull(reqData) ||
                Validator.isNull(pushRequestBean.getInstCode()) ||
                Validator.isNull(pushRequestBean.getChkValue()) ||
                Validator.isNull(pushRequestBean.getAssetType())
                ) {
            logger.info("------请求参数非法-------" + pushRequestBean);
            result.put("status", ErrorCodeConstant.STATUS_ZT000100);
            result.put("statusDesc", "请求参数非法");
            result.put("chkValue", ApiSignUtil.encryptByRSA(ErrorCodeConstant.STATUS_ZT000100));
            return result;
        }

        //验签
        /*if (!SignUtil.AEMSVerifyRequestSign(pushRequestBean, AEMS_ASSETPUSH+flag)) {
            logger.info("------------------验签失败！---------------------");
            result.put("status", ErrorCodeConstant.STATUS_CE000002);
            result.put("statusDesc", "验签失败！");
            result.put("chkValue", ApiSignUtil.encryptByRSA(ErrorCodeConstant.STATUS_CE000002));
            return result;
        }*/

        logger.info("instCode：["+ pushRequestBean.getInstCode() +"]开始推送资产");

        if (CustomConstants.INST_CODE_HYJF.equals(pushRequestBean.getInstCode())) {
            logger.info("instCode：["+ pushRequestBean.getInstCode() +"]，assetType：["+ pushRequestBean.getAssetType() +"]  -->不能推送本平台资产！");
            result.put("status", ErrorCodeConstant.STATUS_ZT000010);
            result.put("statusDesc", "不能推送本平台资产！");
            result.put("chkValue", ApiSignUtil.encryptByRSA(ErrorCodeConstant.STATUS_ZT000010));
            return result;
        }

        return null;
    }

    /**
     * 熔断方法默认返回
     * @param pushRequestBean
     * @return
     */
    public JSONObject fallBackAssetPush(AemsPushRequestBean pushRequestBean) {
        logger.warn("已进入 AEMS个人/企业资产推送(api) fallBackAssetPush 熔断方法， pushRequestBean is: {}", pushRequestBean);
        JSONObject result = new JSONObject();
        result.put("status", ErrorCodeConstant.STATUS_CE999999);
        result.put("statusDesc", "系统维护，请稍后重试!");
        result.put("data", new PushResultBean());
        return result;
    }
}
