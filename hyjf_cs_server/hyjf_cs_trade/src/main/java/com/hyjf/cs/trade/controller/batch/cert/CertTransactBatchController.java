/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch.cert;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.hgreportdata.cert.CertAccountListIdCustomizeVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.GetCode;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.transact.CertTransactService;
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
 * @Author pcc
 * @Date 2019/1/30 15:09
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/certTransactMessage")
public class CertTransactBatchController {

    @Autowired
    private CommonProducer producer;


    private CertTransactService certTransactService;

    @GetMapping("/certTransact")
    public String autoIssueRecover() {
        Integer page=1;
        Integer size=1000;
        CertAccountListIdCustomizeVO customize=new CertAccountListIdCustomizeVO();
        do {
            String certTransactMaxId = RedisUtils.get("certTransactOtherMaxId");
            Map<String, Object> param =  new HashMap<String, Object>();
            param.put("minId", certTransactMaxId==null?"0":certTransactMaxId);
            param.put("limitStart", (page-1)*size);
            param.put("limitEnd", size);
            customize=certTransactService.queryCertAccountListId(param);
            if(certTransactMaxId==null||"".equals(certTransactMaxId)){
                RedisUtils.set("certTransactOtherMaxId", customize.getMaxId()+"");
                return "Success";
            }
            String minId=customize.getLimitMinId()+"";
            String maxId=customize.getLimitMaxId()+"";
            String sumCount=customize.getSumCount()+"";
            if("0".equals(sumCount)){
                return "Success";
            }
            // 加入到消息队列
            Map<String, String> params = new HashMap<String, String>();
            params.put("mqMsgId", GetCode.getRandomCode(10));
            params.put("minId", minId);
            params.put("maxId", maxId);
            try{
                producer.messageSend(new MessageContent(MQConstant.CERT_CERT_TRANSACT_TOPIC, UUID.randomUUID().toString(), JSONObject.toJSONString(params)));
            }catch (Exception e){}
            RedisUtils.set("certTransactOtherMaxId", maxId);
            page=page+1;
        } while (customize.getMaxId()>customize.getLimitMaxId());
        return "Success";
    }
}
