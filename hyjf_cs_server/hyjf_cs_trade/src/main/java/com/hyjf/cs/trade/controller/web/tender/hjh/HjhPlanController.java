/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.controller.web.tender.hjh;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomConstants;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * @Description web端加入计划
 * @Author sss
 * @Version v0.1
 * @Date 2018/6/19 9:32
 */
@Api(tags = "web端-加入计划")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/hyjf-web/tender/hjh")
public class HjhPlanController extends BaseTradeController {
    private static final Logger logger = LoggerFactory.getLogger(HjhPlanController.class);

    @Autowired
    private HjhTenderService hjhTenderService;

    @ApiOperation(value = "web端加入计划", notes = "web端加入计划")
    @PostMapping(value = "/joinPlan", produces = "application/json; charset=utf-8")
    @RequestLimit(seconds=3)
    public WebResult<Map<String, Object>> joinPlan(@RequestHeader(value = "userId", required = false) Integer userId, @RequestBody TenderRequest tender, HttpServletRequest request) {
        String ip = CustomUtil.getIpAddr(request);
        // 神策数据统计 add by liuyang 20180726 start
        // 神策数据统计事件的预置属性
        String presetProps = tender.getPresetProps();
        // 神策数据统计 add by liuyang 20180726 end
        tender.setIp(ip);
        tender.setUserId(userId);
        tender.setPlatform(String.valueOf(ClientConstants.WEB_CLIENT));
        WebResult<Map<String, Object>> result = null;
        try {
            result = hjhTenderService.joinPlan(tender);
        } catch (CheckException e) {
            logger.error("-----",e);
           throw e;
        } finally {
            RedisUtils.del(RedisConstants.HJH_TENDER_REPEAT + tender.getUser().getUserId());
        }

        // 神策数据统计 add by liuyang 20180726 start
        logger.info("PC端智投服务,神策预置属性presetProps:[" + presetProps + "]");
        if (StringUtils.isNotBlank(presetProps)){
            try {
                presetProps = URLDecoder.decode(presetProps,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage());
            }
            logger.info("神策预置属性presetProps2:[" + presetProps + "]");
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

    @ApiOperation(value = "web获取计划出借信息", notes = "web获取计划出借信息")
    @PostMapping(value = "/investInfo", produces = "application/json; charset=utf-8")
    public WebResult<TenderInfoResult> getInvestInfo(@RequestHeader(value = "userId", required = false) Integer userId, @RequestBody TenderRequest tender) {
        tender.setUserId(userId);
        tender.setPlatform(String.valueOf(ClientConstants.WEB_CLIENT));
        return  hjhTenderService.getInvestInfo(tender);
    }

    @ApiOperation(value = "web计划出借校验", notes = "web计划出借校验")
    @PostMapping(value = "/planCheck", produces = "application/json; charset=utf-8")
    public WebResult<TenderInfoResult> planCheck(@RequestHeader(value = "userId", required = false) Integer userId, @RequestBody TenderRequest tender) {
        tender.setUserId(userId);
        tender.setPlatform(String.valueOf(ClientConstants.WEB_CLIENT));
        Map<String, Object> resultMap = hjhTenderService.checkPlan(tender);
        WebResult<TenderInfoResult> resultWebResult = new WebResult();
        //校验用户测评
        if(resultMap != null){
            logger.info("校验用户测评==============:" + resultMap + "========开始===");
            TenderInfoResult tenderInfo = new TenderInfoResult();
            tenderInfo.setStatus(false);
            tenderInfo.setEvalType((String) resultMap.get("evalType"));
            tenderInfo.setInvestLevel((String) resultMap.get("investLevel"));
            tenderInfo.setEvalFlagType((String) resultMap.get("evalFlagType"));
            tenderInfo.setRevaluationMoney((String) resultMap.get("revaluationMoney"));
            tenderInfo.setRiskTested((String) resultMap.get("riskTested"));
            tenderInfo.setMessage((String) resultMap.get("message"));
            if(resultMap.get("riskTested") != null && resultMap.get("riskTested") != ""){
                String riskTested = (String) resultMap.get("riskTested");
                if(CustomConstants.BANK_TENDER_RETURN_ANSWER_FAIL.equals(riskTested)){
                    //未测评需要重新评测
                    resultWebResult.setStatus(MsgEnum.ERR_AMT_TENDER_NEED_RISK_ASSESSMENT.getCode());
                    //tenderInfo.setStatus(MsgEnum.ERR_AMT_TENDER_NEED_RISK_ASSESSMENT.getCode());
                }else if(CustomConstants.BANK_TENDER_RETURN_ANSWER_EXPIRED.equals(riskTested)){
                    //已过期需要重新评测
                    resultWebResult.setStatus(MsgEnum.STATUS_EV000004.getCode());
                    //tenderInfo.setStatus(MsgEnum.STATUS_EV000004.getCode());
                }else if(CustomConstants.BANK_TENDER_RETURN_CUSTOMER_STANDARD_FAIL.equals(riskTested)){
                    //计划类判断用户类型为稳健型以上才可以出借
                    resultWebResult.setStatus(MsgEnum.STATUS_EV000007.getCode());
                    //tenderInfo.setStatus(MsgEnum.STATUS_EV000007.getCode());
                }else if(CustomConstants.BANK_TENDER_RETURN_LIMIT_EXCESS.equals(riskTested)){
                    //金额对比判断（校验金额 大于 设置测评金额）
                    resultWebResult.setStatus(MsgEnum.STATUS_EV000005.getCode());
                    //tenderInfo.setStatus(MsgEnum.STATUS_EV000005.getCode());
                }else if(CustomConstants.BANK_TENDER_RETURN_LIMIT_EXCESS_PRINCIPAL.equals(riskTested)){
                    //金额对比判断（校验金额 大于 设置测评金额）代收本金
                    resultWebResult.setStatus(MsgEnum.STATUS_EV000008.getCode());
                    //tenderInfo.setStatus(MsgEnum.STATUS_EV000008.getCode());
                }
            }
            resultWebResult.setData(tenderInfo);
            logger.info("校验用户测评==============:" + resultWebResult + "========结束===");
        }
        return resultWebResult;
    }

}
