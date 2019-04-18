package com.hyjf.wbs.trade.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.http.HttpClientUtils;
import com.hyjf.wbs.WbsConstants;
import com.hyjf.wbs.configs.WbsConfig;
import com.hyjf.wbs.exceptions.WbsException;
import com.hyjf.wbs.qvo.ProductInfoQO;
import com.hyjf.wbs.qvo.WbsCommonQO;
import com.hyjf.wbs.trade.service.SynProductInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void sync(ProductInfoQO productInfoQO) {
        WbsCommonQO wbsCommonQO = new WbsCommonQO();
//		wbsCommonQO.setApp_key(wbsConfig.getAppKey());
        wbsCommonQO.setName(WbsConstants.INTERFACE_NAME_SYNC_PRODUCT_INFO);
//		wbsCommonQO.setSign(WbsSignUtil.encrypt(customerSyncQO, wbsConfig.getAppSecret()));
        wbsCommonQO.setData(JSONObject.toJSONString(productInfoQO));

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
