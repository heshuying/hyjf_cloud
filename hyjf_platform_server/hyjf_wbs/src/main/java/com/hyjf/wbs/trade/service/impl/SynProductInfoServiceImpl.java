package com.hyjf.wbs.trade.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.wbs.WbsConstants;
import com.hyjf.wbs.common.WbsNewBankerSender;
import com.hyjf.wbs.configs.WbsConfig;
import com.hyjf.wbs.exceptions.WbsException;
import com.hyjf.wbs.qvo.ProductInfoQO;
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

    @Override
    public void sync(ProductInfoQO productInfoQO){
        WbsNewBankerSender sender=new WbsNewBankerSender(WbsConstants.INTERFACE_NAME_SYNC_PRODUCT_INFO,productInfoQO);

        String content=sender.send();
        JSONObject jasonObject = JSONObject.parseObject(content);
        Map map = jasonObject;
        if (map != null && WbsConstants.WBS_RESPONSE_STATUS_SUCCESS
                .equals(String.valueOf(map.get(WbsConstants.WBS_RESPONSE_STATUS_KEY)))) {
            logger.info("====<<产品信息及状态同步到WBS财富管理系统>>:推送成功[{}],推送结果=====", jasonObject);
            return;
        } else {
            logger.error("标的信息回调接口返回失败！详细信息【{}】", jasonObject);
            throw new WbsException("标的信息回调接口返回失败！");
        }
    }
}
