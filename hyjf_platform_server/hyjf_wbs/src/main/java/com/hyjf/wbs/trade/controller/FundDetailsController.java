/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.trade.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.hyjf.common.exception.CheckException;
import com.hyjf.wbs.qvo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.wbs.configs.WbsConfig;
import com.hyjf.wbs.exceptions.WbsFundDetailsException;
import com.hyjf.wbs.sign.WbsSignUtil;
import com.hyjf.wbs.trade.service.FundDetailsService;

import io.swagger.annotations.Api;

/**
 * 资金明细接口
 * @author cui
 * @version FundDetailsController, v0.1 2019/6/28 17:12
 */
@Api(value = "资金明细",tags = "资金明细")
@RestController
@RequestMapping(value = "/hyjf-wbs/trade/funddetail")
public class FundDetailsController {
    private Logger logger=LoggerFactory.getLogger(getClass());

    @Autowired
    private FundDetailsService fundDetailsService;

    @Autowired
    private WbsConfig wbsConfig;

    @RequestMapping("/batchsync")
    @PostMapping
    public WbsCommonVO batchSync(@RequestBody WbsCommonExQO qo){
        WbsCommonVO response=new WbsCommonVO();

        WbsCommonQO wbsCommonQO=new WbsCommonQO();
        BeanUtils.copyProperties(qo,wbsCommonQO);

        boolean signVerifyResult=WbsSignUtil.verify(wbsCommonQO,qo.getSign(),wbsConfig.getAppSecret());
        if(!signVerifyResult){
            throw new WbsFundDetailsException("验签失败！");
        }

        try {
            String data=URLDecoder.decode(wbsCommonQO.getData(),"utf-8");

            logger.info("资金明细接口接收到参数【{}】",data);

            FundDetailsQO fundDetailsQO=JSONObject.parseObject(data,FundDetailsQO.class);

            List<FundDetailsVO> lstFundDetails=fundDetailsService.queryFundDetails(fundDetailsQO);

            response.setData(lstFundDetails);

        } catch (UnsupportedEncodingException e) {
            throw new WbsFundDetailsException("解码utf-8出错，请检查请求参数！");
        }
        return response;
    }

    @RequestMapping(value = "/generateReqeust")
    public WbsCommonExQO generateRequest(@RequestParam Map<String,Object> parameterMap){
        WbsCommonQO wbsCommonQO = new WbsCommonExQO();
        wbsCommonQO.setApp_key(wbsConfig.getAppKey());

        String dataJson=JSON.toJSONString(parameterMap);
        try {
            wbsCommonQO.setData(URLEncoder.encode(dataJson, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("为数据【{}】UTF-8编码出错", dataJson);
            throw new CheckException("999", "编码出错！" + e.getMessage());
        }
        wbsCommonQO.setAccess_token("");
        wbsCommonQO.setVersion("");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(new Date());
        wbsCommonQO.setTimestamp(nowTime);

        WbsCommonExQO commonExQO = new WbsCommonExQO();
        BeanUtils.copyProperties(wbsCommonQO, commonExQO);
        commonExQO.setSign(WbsSignUtil.encrypt(wbsCommonQO, wbsConfig.getAppSecret()));

        return commonExQO;
    }
}
