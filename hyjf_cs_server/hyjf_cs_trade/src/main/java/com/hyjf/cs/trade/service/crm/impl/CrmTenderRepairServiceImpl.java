/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.crm.impl;

import com.hyjf.am.bean.crmtender.CrmInvestMsgBean;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.crm.CrmTenderRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * CRM投资修复
 *
 * @author liuyang
 * @version CrmTenderRepairServiceImpl, v0.1 2019/3/12 9:38
 */
@Service
public class CrmTenderRepairServiceImpl extends BaseServiceImpl implements CrmTenderRepairService {

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private CommonProducer commonProducer;

    @Override
    public List<BorrowTenderVO> selectCrmBorrowTenderList() {
        List<BorrowTenderVO> list = this.amTradeClient.selectCrmBorrowTenderList();
        return list;
    }

    @Override
    public void sendCrmTenderInfoMQ(BorrowTenderVO borrowTenderVO) {
        try {
            logger.info("出借成功后,发送CRM投资统计MQ:散标投资订单号:[" + borrowTenderVO.getNid() + "].");
            CrmInvestMsgBean crmInvestMsgBean = new CrmInvestMsgBean();
            crmInvestMsgBean.setInvestType(0);
            crmInvestMsgBean.setOrderId(borrowTenderVO.getNid());
            String uuid = UUID.randomUUID().toString();
            logger.info("消息ID:" + uuid);
            commonProducer.messageSendDelay(new MessageContent(MQConstant.CRM_TENDER_INFO_TOPIC, uuid, crmInvestMsgBean), 2);
        } catch (Exception e) {
            logger.error("发送CRM消息失败:" + e.getMessage());
        }
    }


    @Override
    public List<HjhAccedeVO> selectCrmHjhAccedeList() {
        List<HjhAccedeVO> list = this.amTradeClient.selectCrmHjhAccedeList();
        return list;
    }


    @Override
    public void sendCrmHjhInfoMQ(HjhAccedeVO hjhAccedeVO) {
        try {
            logger.info("出借成功后,发送CRM投资统计MQ:散标投资订单号:[" + hjhAccedeVO.getAccedeOrderId() + "].");
            CrmInvestMsgBean crmInvestMsgBean = new CrmInvestMsgBean();
            crmInvestMsgBean.setInvestType(1);
            crmInvestMsgBean.setOrderId(hjhAccedeVO.getAccedeOrderId());
            String uuid = UUID.randomUUID().toString();
            logger.info("消息ID:" + uuid);
            commonProducer.messageSendDelay(new MessageContent(MQConstant.CRM_TENDER_INFO_TOPIC, uuid, crmInvestMsgBean), 2);
        } catch (Exception e) {
            logger.error("发送CRM消息失败:" + e.getMessage());
        }
    }
}
