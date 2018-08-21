/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.message;

import cn.emay.sdk.client.api.Client;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.SmsCodeRequestBean;
import com.hyjf.admin.beans.request.SmsLogRequestBean;
import com.hyjf.admin.common.util.SmsUtil;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.mq.SmsProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.SmsCodeService;
import com.hyjf.am.vo.admin.SmsCodeCustomizeVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.util.GetSessionOrRequestUtils;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * @author fq
 * @version SmsCodeController, v0.1 2018/8/14 19:49
 */
@Api(tags = "消息中心-发送短信")
@RestController
@RequestMapping("/hyjf-admin/message/message")
public class SmsCodeController extends BaseController {
    @Autowired
    private SmsCodeService smsCodeService;
    @Autowired
    private SmsProducer smsProducer;

    @ApiOperation(value = "筛选用户", notes = "筛选用户")
    @PostMapping("/query_user")
    public JSONObject queryUser(@RequestBody SmsCodeRequestBean requestBean) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(STATUS, SUCCESS);
        jsonObject.put("statusDesc", SUCCESS_DESC);
        // 在筛选条件下查询出用户
        List<SmsCodeCustomizeVO> msgs = smsCodeService.queryUser(requestBean);
        jsonObject.put("user_number", 0);
		if (!CollectionUtils.isEmpty(msgs)) {
			jsonObject.put("user_number", msgs.size());
		}
        jsonObject.put("smsCode", requestBean);
        BigDecimal remain_money = BigDecimal.ZERO;
        int remain_number = 0;
        try {
            if(RedisUtils.exists(RedisConstants.REMAIN_NUMBER) && RedisUtils.exists(RedisConstants.REMAIN_MONEY)){
                String remain_numberT = RedisUtils.get(RedisConstants.REMAIN_NUMBER);
                String remain_moneyT = RedisUtils.get(RedisConstants.REMAIN_MONEY);
                remain_number = Integer.valueOf(remain_numberT);
                remain_money = new BigDecimal(remain_moneyT);
            }else{
                Client c = SmsUtil.getClient();
                remain_number = (int) c.getBalance() * 10;
                remain_money = BigDecimal.valueOf(remain_number).multiply(BigDecimal.valueOf(0.06));
                RedisUtils.set(RedisConstants.REMAIN_NUMBER, remain_number + "", 5 * 60);
                RedisUtils.set(RedisConstants.REMAIN_MONEY, remain_money.toString(), 5 * 60);
            }
        } catch (Exception e1) {
            logger.error("在筛选条件下查询出用户出错...", e1);
        }
        // 短信余额
        jsonObject.put("remain_money", remain_money.toPlainString());

        // 剩余短信条数
        jsonObject.put("remain_number", remain_number);

        // 上个月发的短信数量
        SmsLogRequestBean smlogCustomize = new SmsLogRequestBean();
        // 获取上个月第一天00:00:00
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        int begin = (int) (cal.getTimeInMillis() / 1000L);
        // 获取上个月最后一天23:59:59
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        int end = (int) (cal.getTimeInMillis() / 1000L);
        smlogCustomize.setPosttime(begin);
        smlogCustomize.setPost_time_end(end);
        smlogCustomize.setStatus(0);
        Integer lastMonth_number = this.smsCodeService.queryLogCount(smlogCustomize);
        jsonObject.put("lastMonth_number", lastMonth_number);
        return jsonObject;
    }

    @ApiOperation(value = "发送短信", notes = "发送短信")
    @PostMapping("/send_message_action")
    public JSONObject send(HttpServletRequest request, @RequestBody SmsCodeRequestBean form) throws ParseException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", false);
        jsonObject.put("status", FAIL);
        jsonObject.put("statusDesc", FAIL_DESC);
        logger.info("后台发送短信开始...");
        // 获取用户输入的手机号码
        String mobile = form.getUser_phones();
        if (form.getMessage() == null) {
            jsonObject.put("msg", "发送消息不能为空");
            return jsonObject;
        }
        if (request.getHeader("Referer").contains("timeinit")) {
            if (mobile.contains(",")) {
                jsonObject.put("msg", "单发只能发送一条");
                return jsonObject;
            }
            boolean flag = smsCodeService.getUserByMobile(mobile);
            if (!flag) {
                jsonObject.put("msg", "单发不能发送平台外的用户手机号");
                return jsonObject;
            }
        }
        String send_message = form.getMessage();
        String channelType = form.getChannelType();
        String sendType = form.getSendType();
        form.setIp(GetCilentIP.getIpAddr(GetSessionOrRequestUtils.getRequest()));
        if (sendType == null) {
            jsonObject.put("msg", "请选择发送类型");
            return jsonObject;
        } else if (sendType.equals("ontime")) {
            if (form.getOn_time() == null || form.getOn_time().equals("")) {
                jsonObject.put("msg", "请选择发送时间");
                return jsonObject;
            }
            if (StringUtils.isEmpty(mobile)) {
                if (form.getOpen_account() == null) {
                    jsonObject.put("msg", "请选择发送条件或者填写手机号");
                    return jsonObject;
                }
            }
            boolean flag = smsCodeService.sendSmsOntime(form);
            if (flag) {
                jsonObject.put("success", true);
                jsonObject.put("msg", "定时发送任务创建成功");
                return jsonObject;
            } else {
                jsonObject.put("msg", "定时发送任务创建失败");
                return jsonObject;
            }

        } else {
            if (StringUtils.isEmpty(mobile)) {
                if (form.getOpen_account() == null) {
                    jsonObject.put("msg", "请选择发送条件或者填写手机号");
                    return jsonObject;
                }
                // 在筛选条件下查询出用户
                List<SmsCodeCustomizeVO> msgs = smsCodeService.queryUser(form);
                // 用户数
                jsonObject.put("user_number", msgs.size());
                // 用户未手写手机号码
                int number = 200;// 分组每组数
                if (msgs != null && msgs.size() != 0) {
                    int i = msgs.size() / number;
                    for (int j = 0; j <= i; j++) {
                        int tosize = (j + 1) * number;
                        List<SmsCodeCustomizeVO> smslist;
                        if (tosize > msgs.size()) {
                            smslist = msgs.subList(j * number, msgs.size());
                        } else {
                            smslist = msgs.subList(j * number, tosize);
                        }
                        String phones = "";
                        for (int z = 0; z < smslist.size(); z++) {
                            if (StringUtils.isNotEmpty(smslist.get(z).getUser_phones())
                                    && Validator.isPhoneNumber(smslist.get(z).getUser_phones())) {
                                if (z < smslist.size() - 1) {
                                    phones += smslist.get(z).getUser_phones() + ",";
                                } else {
                                    phones += smslist.get(z).getUser_phones();
                                }
                            }
                        }
                        try {
                            SmsMessage smsMessage = new SmsMessage(null, null, phones, send_message,
                                    MessageConstant.SMS_SEND_FOR_USERS_NO_TPL, null, null, channelType);
                            smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(),
                                    JSON.toJSONBytes(smsMessage)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {

                // 发送短信
                try {
                    String[] mobiles = mobile.split(",");
                    for (int i = 0; i < (mobiles.length / 200) + 1; i++) {
                        String mbl = "";
                        for (int j = i * 200; j < ((i + 1) * 200) && j < mobiles.length; j++) {
                            mbl += mobiles[j] + ",";
                        }
                        if (mbl.endsWith(",")) {
                            mbl = mbl.substring(0, mbl.length() - 1);
                        }
                        SmsMessage smsMessage = new SmsMessage(null, null, mbl, send_message,
                                MessageConstant.SMS_SEND_FOR_USERS_NO_TPL, null, null, channelType);
                        smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(),
                                JSON.toJSONBytes(smsMessage)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 筛选条件
            if (form.getOpen_account() == null) {
                form.setOpen_account(3);
            }
            {
                // 更新消息条数

                BigDecimal remain_money = BigDecimal.ZERO;
                int remain_number = 0;
                try {
                    if(RedisUtils.exists(RedisConstants.REMAIN_NUMBER) && RedisUtils.exists(RedisConstants.REMAIN_MONEY)){
                        String remain_numberT = RedisUtils.get(RedisConstants.REMAIN_NUMBER);
                        String remain_moneyT = RedisUtils.get(RedisConstants.REMAIN_MONEY);
                        remain_number = Integer.valueOf(remain_numberT);
                        remain_money = new BigDecimal(remain_moneyT);
                    }else{
                        Client c = SmsUtil.getClient();
                        remain_number = (int) c.getBalance() * 10;
                        remain_money = BigDecimal.valueOf(remain_number).multiply(BigDecimal.valueOf(0.06));
                        RedisUtils.set(RedisConstants.REMAIN_NUMBER, remain_number + "", 5 * 60);
                        RedisUtils.set(RedisConstants.REMAIN_MONEY, remain_money.toString(), 5 * 60);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                // 短信余额
                jsonObject.put("remain_money", remain_money.toPlainString());
                // 剩余短信条数
                jsonObject.put("remain_number", remain_number);
            }
            jsonObject.put("success", true);
            jsonObject.put("msg", "发送成功");
        }
        jsonObject.put("status", SUCCESS);
        jsonObject.put("statusDesc", SUCCESS_DESC);
        return jsonObject;
    }
}
