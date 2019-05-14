package com.hyjf.wbs.trade.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.spring.SpringUtils;
import com.hyjf.wbs.configs.WbsConfig;
import com.hyjf.wbs.qvo.WbsCommonExQO;
import com.hyjf.wbs.qvo.WbsCommonQO;
import com.hyjf.wbs.qvo.WbsCommonVO;
import com.hyjf.wbs.sign.WbsSignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: wxd
 * @Date: 2019-05-10 16:18
 * @Description:
 */
@RestController
@RequestMapping("/hyjf-wbs/trade/tools")
public class ToolsController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 调用接口名称
    private String interfaceName;

    // 请求参数
    private Object parameter;

    private  static  WbsConfig wbsConfig;
    @ResponseBody
    @RequestMapping(value = "/createsign", method = RequestMethod.GET)
    public Object repayAction(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("entId", "8001");
        jsonMap.put("currentPage", request.getParameter("page"));


        this.interfaceName = interfaceName;
        this.parameter = jsonMap;
        this.wbsConfig = SpringUtils.getBean(WbsConfig.class);

        WbsCommonQO wbsCommonQO = new WbsCommonQO();
        wbsCommonQO.setApp_key(wbsConfig.getAppKey());
        wbsCommonQO.setName(interfaceName);

        String dataJson = JSON.toJSONString(parameter);
        logger.info("【{}】原始数据【{}】",interfaceName,dataJson);
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

        String jsonRequest = JSONObject.toJSONString(commonExQO);

        return jsonRequest;


    }

    @ResponseBody
    @RequestMapping(value = "/createorder", method = RequestMethod.GET)
    public Object orderAction(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("entId", "8001");
        jsonMap.put("startTime", request.getParameter("start"));
        jsonMap.put("endTime", request.getParameter("end"));

        this.interfaceName = interfaceName;
        this.parameter = jsonMap;
        this.wbsConfig = SpringUtils.getBean(WbsConfig.class);

        WbsCommonQO wbsCommonQO = new WbsCommonQO();
        wbsCommonQO.setApp_key(wbsConfig.getAppKey());
        wbsCommonQO.setName(interfaceName);

        String dataJson = JSON.toJSONString(parameter);
        logger.info("【{}】原始数据【{}】",interfaceName,dataJson);
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

        String jsonRequest = JSONObject.toJSONString(commonExQO);

        return jsonRequest;


    }


}
