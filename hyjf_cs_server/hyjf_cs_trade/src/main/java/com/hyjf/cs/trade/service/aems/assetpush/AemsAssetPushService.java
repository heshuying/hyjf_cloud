/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.aems.assetpush;

import com.hyjf.cs.trade.bean.AemsPushRequestBean;
import com.hyjf.cs.trade.bean.AemsPushResultBean;
import com.hyjf.cs.trade.service.BaseTradeService;

/**
 * @author Zha Daojian
 * @date 2018/12/19 16:42
 * @param 
 * @return 
 **/
public interface AemsAssetPushService extends BaseTradeService {


    /**
     * AEMS个人资产推送
     * @param pushRequestBean
     */
    AemsPushResultBean assetPush(AemsPushRequestBean pushRequestBean);


    /**
     * AEMS企业资产推送
     * @param pushRequestBean
     */
    AemsPushResultBean companyAssetPush(AemsPushRequestBean pushRequestBean);
}
