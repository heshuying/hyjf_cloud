package com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.userinfo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.hgreportdata.cert.CertReportEntityVO;
import com.hyjf.am.vo.hgreportdata.cert.CertSendUserVO;
import com.hyjf.am.vo.hgreportdata.cert.CertUserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.userinfo.CertUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 合规数据上报 CERT 用户数据推送上报（延时队列）
 * @Author sunss
 * @Date 2019/1/21 10:23
 */
@Service
@RocketMQMessageListener(topic = MQConstant.HYJF_TOPIC, selectorExpression = MQConstant.CERT_USER_INFO_TAG, consumerGroup = MQConstant.CERT_USER_INFO_GROUP)
public class CertUserInfoMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(CertUserInfoMessageConsumer.class);

    private String thisMessName = "用户数据推送";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";

    @Autowired
    private CertUserInfoService certUserInfoService;

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info(logHeader+"====start=====");
    }

    @Override
    public void onMessage(MessageExt message) {
        logger.info(logHeader+" 收到消息，开始处理....");

        // --> 消息内容校验
        if (message == null || message.getBody() == null) {
            logger.error(logHeader + "接收到的消息为null！！！");
            return;
        }
        String msgBody = new String(message.getBody());
        logger.info(logHeader + "接收到的消息：" + msgBody);
        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.parseObject(msgBody);
        } catch (Exception e) {
            logger.error(logHeader + "解析消息体失败！！！", e);
            return;
        }

        String borrowNid = jsonObject.getString("borrowNid");
        String tradeDate = jsonObject.getString("tradeDate");
        String userId = jsonObject.getString("userId");
        if (StringUtils.isBlank(userId)) {
            logger.error(logHeader + "通知参数不全！！！");
            return;
        }
        // 检查redis的值是否允许运行 允许返回true  不允许返回false
        boolean canRun = certUserInfoService.checkCanRun();
        if(!canRun){
            logger.info(logHeader + "redis不允许上报！");
            return;
        }
        // --> 消息处理
        try {
            CertSendUserVO users = certUserInfoService.getCertSendUserByUserId(Integer.parseInt(userId));
            if (users == null){
                // 如果未开户  则不上报
                logger.info(logHeader+"未查找到相关用户，userId:"+userId);
                return;
            }
            if (users.getBankOpenAccount().equals(0)){
                // 如果未开户  则不上报
                logger.info(logHeader+"用户未开户，userId:"+userId);
                return;
            }
            // 查询已经上报过的用户
            // 用来判断是新增还是修改
            List<CertUserVO> certUser = new ArrayList<>();
            if(StringUtils.isBlank(borrowNid)){
                // 投资人  或者借款人解绑卡操作
                certUser = certUserInfoService.getCertUsersByUserId(Integer.parseInt(userId));
            }else{
                // 借款人
                CertUserVO oneUser = certUserInfoService.getCertUserByUserIdBorrowNid(Integer.parseInt(userId),borrowNid);
                if(oneUser!=null){
                    certUser.add(oneUser);
                }
            }
            // 垫付机构不用上报
            if (users.getUserAttr().equals(3)){
                logger.info(logHeader+"垫付机构不用上报.");
                return;
            }
            // 如果是借款人   并且是解绑卡操作
            if (users.getUserAttr().equals(2) && (StringUtils.isBlank(borrowNid))) {
                // 修改手机号风险测评时候用
                if(certUser==null){
                    logger.info(logHeader+"借款人未上报过，userId:"+userId);
                    return;
                }
            }

            // --> 调用service组装数据
            if(certUser==null){
                certUser = new ArrayList<>();
            }
            JSONArray data = certUserInfoService.getSendData(users,borrowNid,certUser);

            logger.info(logHeader+"需要操作的用户数量："+(certUser.size())+"  组装数据为："+data.toString());

            // 上送数据
            CertReportEntityVO entity = new CertReportEntityVO(thisMessName, CertCallConstant.CERT_INF_TYPE_USER_INFO, borrowNid, data);
            entity.setUserId(userId);
            try {
                // 掉单用
                if(tradeDate!=null&&!"".equals(tradeDate)){
                    entity.setTradeDate(tradeDate);
                }
                entity = certUserInfoService.insertAndSendPost(entity);
            } catch (Exception e) {
                logger.error(logHeader + "上报用户信息错误！userId:"+userId,e);
            }
            // 上报成功  保存到数据库 不管成功失败都插入
            for (CertUserVO item:certUser) {
                if(item!=null && item.getId()==null){
                    // 新增
                    item.setUserName(users.getUsername());
                    item.setLogOrdId(entity.getLogOrdId());
                    certUserInfoService.insertCertUser(item);
                }
            }
            return;
        } catch (Exception e) {
            logger.error(logHeader + " 处理失败！！" + msgBody, e);
            return;
        }finally {
            logger.info(logHeader + " 结束。");
        }
    }
}
