/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.controller.wechat.tender.hjh;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.admin.UserOperationLogEntityVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.UserOperationLogConstant;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.cs.common.annotation.RequestLimit;
import com.hyjf.cs.common.bean.result.WeChatResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.TenderInfoResult;
import com.hyjf.cs.trade.bean.app.AppInvestInfoResultVO;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.hjh.HjhTenderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @Description wechat端-加入计划
 * @Author sss
 * @Version v0.1
 * @Date 2018/6/19 9:32
 */
@Api(tags = "weChat端-加入计划")
@RestController
@RequestMapping("/hyjf-wechat/wx/plantender")
public class WechatjhPlanController extends BaseTradeController {

    @Autowired
    private HjhTenderService hjhTenderService;
    @Autowired
    private CommonProducer commonProducer;

    @ApiOperation(value = "加入计划", notes = "加入计划")
    @PostMapping(value = "/joinPlan", produces = "application/json; charset=utf-8")
    @RequestLimit(seconds=3)
    public WeChatResult<Map<String, Object>> joinPlan(@RequestHeader(value = "userId") Integer userId, @RequestBody @Valid TenderRequest tender, HttpServletRequest request) {
        String ip = CustomUtil.getIpAddr(request);
        tender.setIp(ip);
        tender.setUserId(userId);
        tender.setPlatform(String.valueOf(ClientConstants.WECHAT_CLIENT));
        // 神策数据统计 add by liuyang 20180726 start
        // 从payload里面获取预置属性
        String presetProps = tender.getPresetProps();
        // 神策数据统计 add by liuyang 20180726 end
        WebResult<Map<String, Object>> result = new WebResult();
        WeChatResult weChatResult = new WeChatResult();
        try {
            result = hjhTenderService.joinPlan(tender);
            Map<String, Object> resultMap = result.getData();
            if(resultMap!=null&&resultMap.containsKey("appEarnings")){
                // 如果是代金券 并且是app
                resultMap.remove("earnings");
                resultMap.put("earnings",resultMap.get("appEarnings"));
                result.setData(resultMap);
            }
            HjhPlanVO plan =  hjhTenderService.getPlanByNid(tender.getBorrowNid());
            String lockPeriod = plan.getLockPeriod().toString();
            String dayOrMonth="";
            if (plan.getIsMonth().intValue()!=0) {
                dayOrMonth = lockPeriod + "个月智投";
            } else {
                dayOrMonth = lockPeriod + "天智投";
            }
            UserVO userVO = hjhTenderService.getUsers(userId);
            UserInfoVO usersInfo = hjhTenderService.getUsersInfoByUserId(userId);
            UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
            userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE4);
            userOperationLogEntity.setIp(GetCilentIP.getIpAddr(request));
            userOperationLogEntity.setPlatform(1);
            userOperationLogEntity.setRemark(dayOrMonth);
            userOperationLogEntity.setOperationTime(new Date());
            userOperationLogEntity.setUserName(userVO.getUsername());
            userOperationLogEntity.setUserRole(String.valueOf(usersInfo.getRoleId()));
            try {
                commonProducer.messageSend(new MessageContent(MQConstant.USER_OPERATION_LOG_TOPIC, UUID.randomUUID().toString(), userOperationLogEntity));
            } catch (MQException e) {
                logger.error("保存用户日志失败", e);
            }
            //用户测评校验状态转换
            if(result.getStatus()!=null && result.getStatus()!="" && !"1".equals(result.getStatus())){
                weChatResult.setStatus(result.getStatus());
            }
            weChatResult.setObject(result.getData());
        } catch (CheckException e) {
            throw e;
        } finally {
            RedisUtils.del(RedisConstants.HJH_TENDER_REPEAT + tender.getUser().getUserId());
        }
        // 神策数据统计 add by liuyang 20180726 start
        logger.info("微信端智投服务,神策预置属性presetProps:[" + presetProps + "]");
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
                sensorsDataBean.setOrderId(String.valueOf(((Map<String,Object>)weChatResult.getObject()).get("accedeOrderId")));
                // 发送神策数据统计MQ
                this.hjhTenderService.sendSensorsDataMQ(sensorsDataBean);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        // 神策数据统计 add by liuyang 20180726 end
        return weChatResult;
    }

    @ApiOperation(value = "获取计划出借信息", notes = "获取计划出借信息")
    @GetMapping(value = "/getInvestInfo", produces = "application/json; charset=utf-8")
    public WeChatResult<TenderInfoResult> getInvestInfo(@RequestHeader(value = "userId") Integer userId,TenderRequest tender) {
        tender.setUserId(userId);
        tender.setPlatform(String.valueOf(ClientConstants.WECHAT_CLIENT));
        AppInvestInfoResultVO resultBean = hjhTenderService.getInvestInfoApp(tender);
        WeChatResult result = new WeChatResult();
        result.setObject(resultBean);
        return  result;
    }

}
