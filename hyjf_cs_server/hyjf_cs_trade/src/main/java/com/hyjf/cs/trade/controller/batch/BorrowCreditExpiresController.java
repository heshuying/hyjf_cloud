/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.batch.BorrowCreditService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author PC-LIUSHOUYI
 * @version BorrowCreditExpiresController, v0.1 2018/6/23 17:03
 */
@ApiIgnore
@RestController
@RequestMapping(value = "/batch/borrowCredit")
public class BorrowCreditExpiresController extends BaseTradeController {

    Logger logger = LoggerFactory.getLogger(BorrowCreditExpiresController.class);

    @Autowired
    private BorrowCreditService borrowCreditService;
    @Autowired
    private CommonProducer commonProducer;

    /**
     * 债转有效定时任务处理
     *
     * @return
     */
    @ApiOperation(value = "债转有效定时任务处理", notes = "债转有效定时任务处理")
    @RequestMapping(value = "/expires")
    public void handle() {
        logger.info("债转有效定时任务处理start...");
//      债转有效定时任务
//      查询债转表债转状态为0的数据
        List<BorrowCreditVO> borrowCreditVOS = this.borrowCreditService.selectBorrowCreditList();
        if (borrowCreditVOS != null && borrowCreditVOS.size() > 0) {
            for (BorrowCreditVO borrowCredit : borrowCreditVOS) {
                Integer nowTime = GetDate.getNowTime10();
                Integer creditAddTime = GetDate.getTime10(borrowCredit.getCreateTime());
                Integer timeDifference = (nowTime - creditAddTime) / 3600;
                if (timeDifference >= 72) {
                    borrowCredit.setCreditStatus(1);
                    //更新债转表状态0的数据的债转状态为1
                    Integer result = this.borrowCreditService.updateBorrowCredit(borrowCredit);
                    if (result == 0) {
                        //更新出错处理下一条
                        logger.error("债转有效定时任务处理更新债转状态失败!!! userId:" + borrowCredit.getCreditUserId() + " creditId:" + borrowCredit.getCreditId());
                        continue;
                    }
                    //发送短信
                    sendMessage(borrowCredit);
                }
            }
        }
        logger.info("债转有效定时任务处理end...");
    }

    void sendMessage(BorrowCreditVO borrowCreditVO) {
        //      获取相应用户信息
        UserInfoVO userInfo = this.borrowCreditService.findUsersInfoById(borrowCreditVO.getCreditUserId());
        if (userInfo != null) {
            Map<String, String> param = new HashMap<String, String>();
            if (userInfo.getTruename() != null && userInfo.getTruename().length() > 1) {
                param.put("val_name", userInfo.getTruename().substring(0, 1));
            } else {
                param.put("val_name", userInfo.getTruename());
            }
            if (userInfo.getSex() == 1) {
                param.put("val_sex", "先生");
            } else if (userInfo.getSex() == 2) {
                param.put("val_sex", "女士");
            } else {
                param.put("val_sex", "");
            }
            param.put("val_amount", borrowCreditVO.getCreditCapitalAssigned() + "");
            param.put("val_profit", borrowCreditVO.getCreditInterestAdvance() + "");
            if (borrowCreditVO.getCreditCapitalAssigned() != null && borrowCreditVO.getCreditCapitalAssigned().longValue() > 0) {
                // add 合规数据上报 埋点 liubin 20181122 start
                // 推送数据到MQ 承接（完全）散
                JSONObject params = new JSONObject();
                params.put("creditNid", borrowCreditVO.getCreditNid()+"");
                params.put("flag", "1"); //1（散）2（智投）
                params.put("status", "2"); //2承接（成功）
                commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.UNDERTAKE_ALL_SUCCESS_TAG, UUID.randomUUID().toString(), params),
                        MQConstant.HG_REPORT_DELAY_LEVEL);
                // add 合规数据上报 埋点 liubin 20181122 end

                // 发送短信验证码
                SmsMessage smsMessage =
                        new SmsMessage(borrowCreditVO.getCreditUserId(), param, null, null, MessageConstant.SMS_SEND_FOR_USER, null,
                                CustomConstants.PARAM_TPL_ZZBFZRCG, CustomConstants.CHANNEL_TYPE_NORMAL);
                try {
                    commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));
                } catch (MQException e) {
                    logger.error("债转部分转让发送短信失败：userId:" + borrowCreditVO.getCreditUserId() + ",trueName:" + userInfo.getTruename(), e);
                }
            } else {
                // add 合规数据上报 埋点 liubin 20181122 start
                //停止债转并且没有被承接过
                JSONObject params = new JSONObject();
                params.put("creditNid", borrowCreditVO.getCreditNid()+"");
                params.put("flag", "1");//1（散）2（智投）
                params.put("status", "3"); //3承接（失败）
                // 推送数据到MQ 承接（失败）散
                commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.UNDERTAKE_ALL_FAIL_TAG, UUID.randomUUID().toString(), params),
                        MQConstant.HG_REPORT_DELAY_LEVEL);
                // add 合规数据上报 埋点 liubin 20181122 end

                param.put("val_amount", "0");
                param.put("val_profit", "0");
                // 发送短信验证码
                SmsMessage smsMessage =
                        new SmsMessage(borrowCreditVO.getCreditUserId(), param, null, null, MessageConstant.SMS_SEND_FOR_USER, null,
                                CustomConstants.PARAM_TPL_ZZDQ, CustomConstants.CHANNEL_TYPE_NORMAL);
                try {
                    commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));
                } catch (MQException e) {
                    logger.error("债转0转让发送短信失败：userId:" + borrowCreditVO.getCreditUserId() + ",trueName:" + userInfo.getTruename(), e);
                }
            }
            AppMsMessage appMsMessage = new AppMsMessage(borrowCreditVO.getCreditUserId(), param, null, MessageConstant.APP_MS_SEND_FOR_USER, CustomConstants.JYTZ_TPL_ZHUANRANGJIESHU);
            try {
                commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, UUID.randomUUID().toString(), appMsMessage));
            } catch (MQException e) {
                logger.error("债转转让结束发送消息失败：userId:" + borrowCreditVO.getCreditUserId() + ",trueName:" + userInfo.getTruename(), e);
            }
        }
    }
}
