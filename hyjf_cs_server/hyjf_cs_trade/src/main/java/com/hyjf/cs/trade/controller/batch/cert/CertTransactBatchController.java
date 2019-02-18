/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch.cert;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.vo.hgreportdata.cert.CertAccountListIdCustomizeVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.transact.CertTransactService;
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
 * @Description 应急中心异常处理
 * @Author pcc
 * @Date 2019/1/30 15:09
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/batch/certTransactMessage")
public class CertTransactBatchController {

    @Autowired
    private CommonProducer commonProducer;

    private String thisMessName = "国家互联网应急中心交易流水上报";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";
    Logger logger = LoggerFactory.getLogger(CertTransactBatchController.class);

    @Autowired
    private CertTransactService certTransactService;

    @GetMapping("/certTransact")
    public StringResponse certTransact() {
        logger.info(logHeader + "CertTransactBatchController execute start...");
        Integer page=1;
        Integer size=1000;
        CertAccountListIdCustomizeVO customize=new CertAccountListIdCustomizeVO();
        do {
            String certTransactMaxId = RedisUtils.get("certTransactOtherMaxId");
            Map<String, Object> param =  new HashMap<String, Object>();
            param.put("minId", certTransactMaxId==null?"0":certTransactMaxId);
            param.put("limitStart", (page-1)*size);
            param.put("limitEnd", size);
            logger.info("queryCertAccountListId:" + JSONObject.toJSONString(param));
            customize=certTransactService.queryCertAccountListId(param);
            if(certTransactMaxId==null||"".equals(certTransactMaxId)){
                RedisUtils.set("certTransactOtherMaxId", customize.getMaxId()+"");
                logger.info(logHeader + "CertTransactBatchController execute end...");
                return new StringResponse("success");
            }
            String minId=customize.getLimitMinId()+"";
            String maxId=customize.getLimitMaxId()+"";
            String sumCount=customize.getSumCount()+"";
            if("0".equals(sumCount)){
                logger.info(logHeader + "CertTransactBatchController execute end...");
                return new StringResponse("success");
            }
            // 加入到消息队列
            Map<String, String> params = new HashMap<String, String>();
            params.put("mqMsgId", GetCode.getRandomCode(10));
            params.put("minId", minId);
            params.put("maxId", maxId);
            try{
                commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.CERT_TRANSACT_TAG, UUID.randomUUID().toString(), params),
                        MQConstant.HG_REPORT_DELAY_LEVEL);


            }catch (Exception e){}
            RedisUtils.set("certTransactOtherMaxId", maxId);
            page=page+1;
        } while (customize.getMaxId()>customize.getLimitMaxId());
        logger.info(logHeader + "CertTransactBatchController execute end...");
        return new StringResponse("success");
    }
}
