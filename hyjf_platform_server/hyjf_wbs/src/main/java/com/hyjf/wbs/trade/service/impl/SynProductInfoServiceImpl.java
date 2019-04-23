package com.hyjf.wbs.trade.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.http.HttpClientUtils;
import com.hyjf.common.util.ConvertUtils;
import com.hyjf.wbs.WbsConstants;
import com.hyjf.wbs.configs.WbsConfig;
import com.hyjf.wbs.exceptions.WbsException;
import com.hyjf.wbs.qvo.ProductInfoQO;
import com.hyjf.wbs.qvo.WbsCommonExQO;
import com.hyjf.wbs.qvo.WbsCommonQO;
import com.hyjf.wbs.qvo.WbsSignQO;
import com.hyjf.wbs.sign.WbsSignUtil;
import com.hyjf.wbs.trade.service.SynProductInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: wxd
 * @Date: 2019-04-18 10:17
 * @Description:
 */
@Service
public class SynProductInfoServiceImpl extends BaseServiceImpl implements SynProductInfoService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WbsConfig wbsConfig;

    @Override
    public void sync(Map<String,String> mapdata) throws IOException {

        String data = JSONObject.toJSONString(mapdata);
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        WbsCommonExQO wbsCommonQO = new WbsCommonExQO();
		wbsCommonQO.setApp_key(wbsConfig.getAppKey());
        wbsCommonQO.setName(WbsConstants.INTERFACE_NAME_SYNC_PRODUCT_INFO);
        wbsCommonQO.setData(URLEncoder.encode(data,"utf-8"));
        wbsCommonQO.setVersion("");
        wbsCommonQO.setTimestamp(timestamp);
        wbsCommonQO.setAccess_token("");
        /**
         * 组装加签需要的特定参数
         */
        WbsSignQO wbsSignQO = new WbsSignQO();
        wbsSignQO.setApp_key(wbsConfig.getAppKey());
        wbsSignQO.setName(WbsConstants.INTERFACE_NAME_SYNC_PRODUCT_INFO);
        wbsSignQO.setData(URLEncoder.encode(data,"utf-8"));
        wbsSignQO.setVersion("");
        wbsSignQO.setTimestamp(timestamp);


        wbsCommonQO.setSign(WbsSignUtil.encrypt(wbsSignQO, wbsConfig.getAppSecret()));

        String jsonRequest = JSONObject.toJSONString(wbsCommonQO);

        String postUrl = wbsConfig.getSyncProductInfoUrl();

        String content = HttpClientUtils.postJson(postUrl, jsonRequest);

        JSONObject jasonObject = JSONObject.parseObject(content);
        Map map = jasonObject;
        if (map != null && WbsConstants.WBS_RESPONSE_STATUS_SUCCESS
                .equals(String.valueOf(map.get(WbsConstants.WBS_RESPONSE_STATUS_KEY)))) {
            return;
        } else {
            logger.error("标的信息回调接口返回失败！详细信息【{}】", map.get(WbsConstants.WBS_RESPONSE_ERROR_MSG_KEY));
            throw new WbsException("标的信息回调接口返回失败！");
        }
    }
}
