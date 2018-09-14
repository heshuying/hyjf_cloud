/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.borrow.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.hyjf.am.vo.task.issuerecover.BorrowWithBLOBs;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.hjh.BorrowBailVO;
import com.hyjf.am.vo.trade.hjh.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AutoSendClient;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetCode;
import com.hyjf.cs.trade.bean.MQBorrow;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.mq.producer.AutoPreAuditProducer;
import com.hyjf.cs.trade.service.borrow.AutoPreAuditService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;

/**
 * @author fuqiang
 * @version AutoPreAuditServiceImpl, v0.1 2018/6/14 16:35
 */
@Service
public class AutoPreAuditServiceImpl extends BaseTradeServiceImpl implements AutoPreAuditService {

    @Autowired
    private AutoPreAuditProducer autoPreAuditProducer;

    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public void sendToMQ(MQBorrow mqBorrow, String hyjfBorrowIssueGroup) {
        // 加入到消息队列
        JSONObject params = new JSONObject();
        params.put("mqMsgId", GetCode.getRandomCode(10));
        if (mqBorrow.getBorrowNid() != null) {
            params.put("borrowNid", mqBorrow.getBorrowNid());
        }else if (mqBorrow.getCreditNid() != null) {
            params.put("creditNid", mqBorrow.getCreditNid());
        }
        try {
            autoPreAuditProducer.messageSend(new MessageContent(MQConstant.ROCKETMQ_BORROW_ISSUE_TOPIC, UUID.randomUUID().toString(),JSONObject.toJSONBytes(params)));
        } catch (MQException e) {
            logger.error("发送自动关联计划消息失败...");
        }
    }

    /**
     * 资产自动备案-自动初审 add by liushouyi
     *
     * @param hjhPlanAssetVO
     * @param hjhAssetBorrowType
     * @return
     */
    @Override
    public boolean updateRecordBorrow(HjhPlanAssetVO hjhPlanAssetVO, HjhAssetBorrowTypeVO hjhAssetBorrowType) {

        // 验证资产风险保证金是否足够（redis）,关联汇计划才验证
        if (!checkAssetCanSend(hjhPlanAssetVO)) {
            logger.info("资产编号："+hjhPlanAssetVO.getAssetId()+" 保证金不足");
            //add by pcc 20180531 增加待补缴状态
            HjhPlanAssetVO planAssetVO = new HjhPlanAssetVO();
            planAssetVO.setId(hjhPlanAssetVO.getId());
            //待补缴保证金
            planAssetVO.setStatus(1);
            this.amTradeClient.updateHjhPlanAssetnew(planAssetVO);
            //end
            return false;
        }

        // 风险保证金，初审
        if(hjhAssetBorrowType.getAutoBail() != null && hjhAssetBorrowType.getAutoBail() == 1){
            saveBailRecord(hjhPlanAssetVO.getBorrowNid());
        }

        //修改发标状态 更新资产表，更新borrow
        if(hjhAssetBorrowType.getAutoAudit() != null && hjhAssetBorrowType.getAutoAudit() == 1){

            updateOntimeRecord(hjhPlanAssetVO,hjhAssetBorrowType);

            HjhPlanAssetVO hjhPlanAssetnew = new HjhPlanAssetVO();
            hjhPlanAssetnew.setId(hjhPlanAssetVO.getId());
            //投资中
            hjhPlanAssetnew.setStatus(7);
            //获取当前时间
            int nowTime = GetDate.getNowTime10();
            hjhPlanAssetnew.setUpdateTime(nowTime);
            hjhPlanAssetnew.setUpdateUserId(1);
            boolean borrowFlag = amTradeClient.updateHjhPlanAssetnew(hjhPlanAssetnew)>0?true:false;
            if(borrowFlag){
                return true;
            }
        }
        return false;
    }

    /**
     * 手动录标自动备案-自动初审  add by liushouyi
     *
     * @param borrows
     * @return
     */
    @Override
    public boolean updateRecordBorrow(BorrowAndInfoVO borrows) {

        //检查是否交过保证金
        String borrowNid = borrows.getBorrowNid();

        BorrowBailVO borrowBail = amTradeClient.selectBorrowBail(borrowNid);
        // 该借款编号没有交过保证金
        if (borrowBail == null) {
            logger.info("该借款编号没有交过保证金 "+borrowNid);
            return false;
        }

        // 插入时间
        int systemNowDateLong = GetDate.getNowTime10();
        Date systemNowDate = GetDate.getDate(systemNowDateLong);
        BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(borrowNid);
        if (borrow != null) {
            // 剩余的金额
            borrow.setBorrowAccountWait(borrow.getAccount());
            int time = systemNowDateLong;

            // 是否可以进行借款
            borrow.setBorrowStatus(1);
            // 初审时间
            borrow.setVerifyTime(String.valueOf(GetDate.getNowTime10()));
            // 发标的状态
            borrow.setVerifyStatus(Integer.valueOf(4));
            // 状态
            borrow.setStatus(2);
            // 借款到期时间
            borrow.setBorrowEndTime(String.valueOf(time + borrow.getBorrowValidTime() * 86400));
            // 更新时间
            borrow.setUpdatetime(systemNowDate);
            boolean result = amTradeClient.updateBorrowByBorrowNid(borrow);
            return result;

        }

        return false;
    }

    /**
     * 交保证金（默认已交风险保证金）  add by liushouyi
     *
     * @param borrowPreNid
     */
    private boolean saveBailRecord(String borrowPreNid) {
        // 借款编号存在
        if (StringUtils.isNotEmpty(borrowPreNid)) {

            BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(borrowPreNid);
            if (borrow != null) {
                // 该借款编号没有交过保证金
                BorrowBailVO borrowBailVO = amTradeClient.selectBorrowBail(borrow.getBorrowNid());
                if (borrowBailVO == null) {
                    BorrowBailVO borrowBail = new BorrowBailVO();
                    // 借款人的ID
                    borrowBail.setBorrowUid(borrow.getUserId());
                    // 操作人的ID
                    borrowBail.setOperaterUid(1);
                    // 借款编号
                    borrowBail.setBorrowNid(borrow.getBorrowNid());
                    // 保证金数值
                    // 计算公式：保证金金额=借款金额×3％
                    String bailPercentStr = amTradeClient.getBorrowConfig(CustomConstants.BORROW_BAIL_RATE);
                    if (StringUtils.isBlank(bailPercentStr)){
                        logger.error("配置表borrowConfig中未查询到保证金费率相关配置：" + CustomConstants.BORROW_BAIL_RATE);
                        return false;
                    }
                    BigDecimal bailPercent = new BigDecimal(bailPercentStr);
                    BigDecimal accountBail = (borrow.getAccount()).multiply(bailPercent).setScale(2, BigDecimal.ROUND_DOWN);
                    borrowBail.setBailNum(accountBail);
                    // 10位系统时间（到秒）
                    borrowBail.setUpdateTime(GetDate.getNowTime());
                    boolean bailFlag = amTradeClient.insertBorrowBail(borrowBail) > 0;
                    if (bailFlag) {
                        borrow.setVerifyStatus(1);
                        return amTradeClient.updateBorrowByBorrowNid(borrow);
                    }
                }
            }
        }
        return false;
    }

    /**
     * 发标，更新状态 add by liushouyi
     *
     * @param hjhPlanAsset
     * @param hjhAssetBorrowType
     * @return
     */
    private boolean updateOntimeRecord(HjhPlanAssetVO hjhPlanAsset,HjhAssetBorrowTypeVO hjhAssetBorrowType) {

        //检查是否交过保证金
        String borrowNid = hjhPlanAsset.getBorrowNid();


        BorrowBailVO borrowBailVO = amTradeClient.selectBorrowBail(borrowNid);
        // 该借款编号没有交过保证金
        if (borrowBailVO == null) {
            logger.info("该借款编号没有交过保证金 "+borrowNid);
            return false;
        }

        // 插入时间
        int systemNowDateLong = GetDate.getNowTime10();
        Date systemNowDate = GetDate.getDate(systemNowDateLong);
        BorrowAndInfoVO borrowVO = amTradeClient.selectBorrowByNid(borrowNid);
        if (borrowVO != null) {
            // 剩余的金额
            borrowVO.setBorrowAccountWait(borrowVO.getAccount());
            int time = systemNowDateLong;

            // 是否可以进行借款
            borrowVO.setBorrowStatus(1);
            // 初审时间
            borrowVO.setVerifyTime(String.valueOf(GetDate.getNowTime10()));
            // 发标的状态
            borrowVO.setVerifyStatus(Integer.valueOf(4));
            // 状态
            borrowVO.setStatus(2);
            // 借款到期时间
            borrowVO.setBorrowEndTime(String.valueOf(time + borrowVO.getBorrowValidTime() * 86400));
            // 更新时间
            borrowVO.setUpdatetime(systemNowDate);
            return amTradeClient.updateBorrowByBorrowNid(borrowVO);
        }

        return false;
    }

    /**
     * 验证资产风险保证金是否足够（redis） add by liushouyi
     * @param hjhPlanAsset
     * @return
     */
    private boolean checkAssetCanSend(HjhPlanAssetVO hjhPlanAsset) {
        String instCode = hjhPlanAsset.getInstCode();

        String capitalToplimit = RedisUtils.get(RedisConstants.CAPITAL_TOPLIMIT_+instCode);
        BigDecimal lcapitalToplimit = new BigDecimal(capitalToplimit);
        BigDecimal assetAcount = new BigDecimal(hjhPlanAsset.getAccount());

        if (BigDecimal.ZERO.compareTo(lcapitalToplimit) >= 0) {
            logger.info("资产编号："+hjhPlanAsset.getAssetId()+" 风险保证金小于等于零 "+capitalToplimit);
            // 风险保证金小于等于0不能发标
            return false;
        }

        if(assetAcount.compareTo(lcapitalToplimit) > 0){
            logger.info("资产编号："+hjhPlanAsset.getAssetId()+" 金额： "+assetAcount+" 风险保证金小于等于零 "+capitalToplimit);
            // 风险保证金不够不能发标
            return false;
        }

        return true;
    }
}
