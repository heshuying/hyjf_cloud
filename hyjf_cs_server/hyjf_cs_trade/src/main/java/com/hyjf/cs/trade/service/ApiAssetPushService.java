/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service;

import com.hyjf.am.vo.trade.borrow.BorrowWithBLOBsVO;
import com.hyjf.am.vo.trade.hjh.HjhLabelVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
import com.hyjf.cs.trade.bean.assetpush.PushRequestBean;
import com.hyjf.cs.trade.bean.assetpush.PushResultBean;

/**
 * @author fuqiang
 * @version ApiAssetPushService, v0.1 2018/6/11 18:06
 */
public interface ApiAssetPushService extends BaseTradeService{

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

    /**
     * 查询标签
     *
     * @param borrowVO
     * @param hjhPlanAssetVO
     * @return
     */
    HjhLabelVO getLabelId(BorrowWithBLOBsVO borrowVO, HjhPlanAssetVO hjhPlanAssetVO);

    /**
     * 发送消息自动备案
     *
     * @param hjhPlanAssetVO
     * @param mqGroup
     */
    void sendToMQ(HjhPlanAssetVO hjhPlanAssetVO, String mqGroup);
}
