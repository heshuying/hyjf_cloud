/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch.cert;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.GetCode;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Description 应急中心异常处理
 * @Author sunss
 * @Date 2019/1/30 15:09
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/batch/certException")
public class CertExceptionBatchController {

    @Autowired
    private CommonProducer producer;

    @GetMapping("/doException")
    public String autoIssueRecover() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mqMsgId", GetCode.getRandomCode(10));
        try{
            producer.messageSend(new MessageContent(MQConstant.CERT_EXCEPTION_TOPIC, UUID.randomUUID().toString(), JSONObject.toJSONString(params)));
        }catch (Exception e){}
        return "Success";
    }
}
