/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.trade.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.producer.AutoPreAuditProducer;
import com.hyjf.am.trade.service.front.trade.TrusteePayService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author wangjun
 * @version TrusteePayServiceImpl, v0.1 2018/8/27 15:49
 */
@Service
public class TrusteePayServiceImpl extends BaseServiceImpl implements TrusteePayService {
    @Autowired
    AutoPreAuditProducer autoPreAuditProducer;

    /**
     * 借款人受托支付申请异步回调更新
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BooleanResponse update(String borrowNid){
        BooleanResponse response = new BooleanResponse();
        //更新标的信息(ht_borrow)
        Borrow borrow = new Borrow();
        borrow.setBorrowNid(borrowNid);
        borrow.setStatus(1);
        BorrowExample example = new BorrowExample();
        example.createCriteria().andBorrowNidEqualTo(borrowNid).andStatusEqualTo(7);
        int updateBorrow = this.borrowMapper.updateByExampleSelective(borrow, example);

        //更新标的信息(ht_borrow_info)
        BorrowInfoWithBLOBs borrowInfo = new BorrowInfoWithBLOBs();
        borrowInfo.setBorrowNid(borrowNid);
        borrowInfo.setTrusteePayTime(GetDate.getNowTime10());// 受托支付完成时间
        BorrowInfoExample borrowInfoExample = new BorrowInfoExample();
        borrowInfoExample.createCriteria().andBorrowNidEqualTo(borrowNid);
        int updateBorrowInfo = this.borrowInfoMapper.updateByExampleSelective(borrowInfo, borrowInfoExample);
        Boolean flag = (updateBorrow + updateBorrowInfo) == 2 ? true : false;

        if (flag) {
            HjhPlanAsset hp = new HjhPlanAsset();
            hp.setStatus(5);
            hp.setBorrowNid(borrowNid);
            HjhPlanAssetExample hpexp = new HjhPlanAssetExample();
            hpexp.createCriteria().andBorrowNidEqualTo(borrowNid);
            flag = this.hjhPlanAssetMapper.updateByExampleSelective(hp, hpexp) > 0 ? true : false;

            if (flag) {
                logger.info("受托支付开始推送消息到MQ,标的编号:【" + borrowNid + "】");
                // 查询资产表
                HjhPlanAsset hjhHp = getHjhPlanAsset(borrowNid);
                // 推送消息到mq
                try {
                    if (hjhHp != null) {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("mqMsgId", GetCode.getRandomCode(10));
                        params.put("assetId", hjhHp.getAssetId());
                        params.put("instCode", hjhHp.getInstCode());
                        autoPreAuditProducer.messageSend(new MessageContent(MQConstant.ROCKETMQ_BORROW_PREAUDIT_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(params)));
                    }
                } catch (Exception e) {
                    logger.error("发送自动初审MQ失败(AM),标的编号:【" + borrowNid + "】错误原因：", e);
                }
                logger.info("受托支付推送消息到MQ结束,标的编号:【" + borrowNid + "】");
            }
        }
        response.setResultBoolean(flag);
        return response;
    }

    /**
     * 汇计划资产
     * @param nid
     * @return
     */
    private HjhPlanAsset getHjhPlanAsset(String nid) {
        HjhPlanAssetExample example = new HjhPlanAssetExample();
        example.createCriteria().andBorrowNidEqualTo(nid);
        List<HjhPlanAsset> lists = this.hjhPlanAssetMapper.selectByExample(example);
        if (lists != null && lists.size() > 0) {
            return lists.get(0);
        }
        return null;
    }

    /**
     * 查询受托白名单
     *
     * @param instCode
     * @param receiptAccountId
     * @return
     */
    @Override
    public StzhWhiteList getSTZHWhiteList(String instCode, String receiptAccountId){
        StzhWhiteListExample example = new StzhWhiteListExample();
        example.createCriteria().andStAccountidEqualTo(receiptAccountId).andInstcodeEqualTo(instCode);
        List<StzhWhiteList> lists = this.sTZHWhiteListMapper.selectByExample(example);
        if (lists != null && lists.size() > 0) {
            return lists.get(0);
        }
        return null;
    }
}
