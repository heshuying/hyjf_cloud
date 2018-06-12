/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.borrow.service;

import com.hyjf.am.vo.borrow.HjhPlanAssetVO;
import com.hyjf.cs.borrow.bean.assetpush.PushRequestBean;
import com.hyjf.cs.borrow.bean.assetpush.PushResultBean;

/**
 * @author fuqiang
 * @version ApiAssetPushService, v0.1 2018/6/11 18:06
 */
public interface ApiAssetPushService {

    /**
     * 发送消息
     *
     * @param record
     */
    void sendToMQ(HjhPlanAssetVO record);

    /**
     * 资产推送
     *
     * @param pushRequestBean
     */
    PushResultBean assetPush(PushRequestBean pushRequestBean);
}
