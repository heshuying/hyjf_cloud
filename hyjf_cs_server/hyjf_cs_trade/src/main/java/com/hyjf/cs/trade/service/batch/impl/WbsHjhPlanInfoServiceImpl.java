/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.batch.WbsHjhPlanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * WBS系统智投服务推送Service实现类
 *
 * @author liuyang
 * @version WbsHjhPlanInfoServiceImpl, v0.1 2019/4/16 9:00
 */
@Service
public class WbsHjhPlanInfoServiceImpl extends BaseServiceImpl implements WbsHjhPlanInfoService {

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private CommonProducer commonProducer;

    /**
     * WBS系统获取智投列表
     *
     * @return
     */
    @Override
    public List<HjhPlanVO> selectWbsSendHjhPlanList() {
        return amTradeClient.selectWbsSendHjhPlanList();
    }

    /**
     * WBS系统发送智投信息
     *
     * @param planNid
     * @param productStatus
     * @param productType
     */
    @Override
    public void sendWbsPlanInfoMQ(String planNid, String productStatus, Integer productType) throws MQException {
        JSONObject params = new JSONObject();
        // 产品编号
        params.put("productNo", planNid);
        // 产品状态
        params.put("productStatus", productStatus);
        // 产品类型 0 散标类, 1 计划类
        params.put("productType", productType);
        commonProducer.messageSend(new MessageContent(MQConstant.WBS_BORROW_INFO_TOPIC, MQConstant.WBS_BORROW_INFO_TAG, UUID.randomUUID().toString(), params));
    }
}
