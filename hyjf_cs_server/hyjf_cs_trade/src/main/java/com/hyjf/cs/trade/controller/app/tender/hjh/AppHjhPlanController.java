/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.controller.app.tender.hjh;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.common.annotation.RequestLimit;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.TenderInfoResult;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.hjh.HjhTenderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * @Description APP端加入计划
 * @Author sss
 * @Version v0.1
 * @Date 2018/6/19 9:32
 */
@Api(value = "app端-加入计划",tags = "app端-加入计划")
@RestController
@RequestMapping("/hyjf-app/tender/hjh")
public class AppHjhPlanController extends BaseTradeController {
    private static final Logger logger = LoggerFactory.getLogger(AppHjhPlanController.class);

    @Autowired
    private HjhTenderService hjhTenderService;

    @ApiOperation(value = "app端-加入计划", notes = "app端-加入计划")
    @PostMapping(value = "/joinPlan", produces = "application/json; charset=utf-8")
    @RequestLimit(seconds=3)
    public WebResult<Map<String, Object>> joinPlan(@RequestHeader(value = "userId") Integer userId, TenderRequest tender, HttpServletRequest request) {
        String ip = CustomUtil.getIpAddr(request);
        tender.setIp(ip);
        tender.setUserId(userId);
        // 神策数据统计 add by liuyang 20180726 start
        // 神策数据统计事件的预置属性
        String presetProps = request.getParameter("presetProps");
        // 神策数据统计 add by liuyang 20180726 end
        WebResult<Map<String, Object>> result = null;
        try {
            result = hjhTenderService.joinPlan(tender);
            Map<String, Object> resultMap = result.getData();
            if(resultMap!=null&&resultMap.containsKey("appEarnings")){
                // 如果是代金券 并且是app
                resultMap.remove("earnings");
                resultMap.put("earnings",resultMap.get("appEarnings"));
                result.setData(resultMap);
            }

        } catch (CheckException e) {
            throw e;
        } finally {
            RedisUtils.del(RedisConstants.HJH_TENDER_REPEAT + tender.getUser().getUserId());
        }

        // 神策数据统计 add by liuyang 20180726 start
        logger.info("神策预置属性presetProps:[" + presetProps + "]");
        if (StringUtils.isNotBlank(presetProps)){
            try {
                presetProps = URLDecoder.decode(presetProps,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage());
            }
            logger.info("APP端智投服务,神策预置属性presetProps2:[" + presetProps + "]");
            SensorsDataBean sensorsDataBean = new SensorsDataBean();
            // 将json串转换成Bean
            try {
                Map<String, Object> sensorsDataMap = JSONObject.parseObject(presetProps, new TypeReference<Map<String, Object>>() {
                });
                sensorsDataBean.setPresetProps(sensorsDataMap);
                sensorsDataBean.setUserId(userId);
                sensorsDataBean.setEventCode("submit_intelligent_invest");
                sensorsDataBean.setOrderId(String.valueOf(result.getData().get("accedeOrderId")));
                // 发送神策数据统计MQ
                this.hjhTenderService.sendSensorsDataMQ(sensorsDataBean);

            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        // 神策数据统计 add by liuyang 20180726 end
        return result;
    }

    @ApiOperation(value = "APP端获取计划出借信息", notes = "APP端获取计划出借信息")
    @PostMapping(value = "/investInfo", produces = "application/json; charset=utf-8")
    public WebResult<TenderInfoResult> getInvestInfo(@RequestHeader(value = "userId", required = true) Integer userId, @RequestBody @Valid TenderRequest tender) {
        tender.setUserId(userId);
        return  hjhTenderService.getInvestInfo(tender);
    }

}
