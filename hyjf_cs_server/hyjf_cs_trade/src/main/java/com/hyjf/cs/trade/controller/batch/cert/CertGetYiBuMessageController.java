/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch.cert;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.StringResponse;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.GetCode;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.getyibumessage.CertGetYiBuMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Description 合规数据上报 CERT 查询批次数据入库消息 （延时队列）
 * @Author nxl
 * @Date 2018/12/25 17:57
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/batch/getCertYiBuMessage")
public class CertGetYiBuMessageController {

    Logger logger = LoggerFactory.getLogger(CertGetYiBuMessageController.class);

    @Autowired
    private CommonProducer producer;
    @Autowired
    private CertGetYiBuMessageService certGetYiBuMessageService;

    @GetMapping("/certYiBuMessage")
    public StringResponse certYiBuMessage() {
        Map<String, String> params = new HashMap<String, String>();
        /*int logSize = certGetYiBuMessageService.selectCertLogLength();
        if (logSize > 0) {

        }*/
        params.put("mqMsgId", GetCode.getRandomCode(10));
        logger.info("查询批次数据入库消息 执行MQ,参数为:" + params.toString());
        try {
            producer.messageSend(new MessageContent(MQConstant.CERT_GETYIBU_MESSAGE_TOPIC, UUID.randomUUID().toString(), JSONObject.toJSONString(params)));
        } catch (Exception e) {
        }
        return new StringResponse("success");
    }
}
