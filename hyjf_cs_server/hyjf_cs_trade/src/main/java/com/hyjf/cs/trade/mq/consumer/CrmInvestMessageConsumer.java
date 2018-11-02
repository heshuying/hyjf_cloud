package com.hyjf.cs.trade.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;
import com.hyjf.am.response.user.HjhPlanResponse;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.borrow.RightBorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.service.BaseClient;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.base.Consumer;
import com.hyjf.cs.trade.util.CheckSignUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.hyjf.common.util.GetDate.getDate;

@Component
public class CrmInvestMessageConsumer extends Consumer {

    private static final Logger logger = LoggerFactory.getLogger(CrmInvestMessageConsumer.class);

    private static final String CONSUMER_NAME = "<<CRM投资信息同步>>:";

    private static final String CONSUMER_QUIT = "消费退出";

    private static final String SUCCESS = "success";
    private static final String FAILUE = "failue";
    private static final String TRY = "try";


    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private BaseClient baseClient;


    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setConsumerGroup(MQConstant.BORROW_GROUP);
        defaultMQPushConsumer.subscribe(MQConstant.CRM_TENDER_INFO_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        // 设置并发数
        defaultMQPushConsumer.setConsumeThreadMin(1);
        defaultMQPushConsumer.setConsumeThreadMax(1);
        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(1);
        defaultMQPushConsumer.setConsumeTimeout(30);
        defaultMQPushConsumer.registerMessageListener(new MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("====" + CONSUMER_NAME + "监听初始化完成, 启动完毕=====");
    }

    public class MessageListener implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            logger.info("======" + CONSUMER_NAME + "消费开始 ========");
            try {
                logger.info("========" + CONSUMER_NAME + "监听器收到消息:{}========", JSON.toJSONString(msgs,SerializerFeature.IgnoreNonFieldGetter));
                MessageExt msg = msgs.get(0);
                String msgBody = new String(msg.getBody());
                logger.info("======="+ CONSUMER_NAME +" msgBody [{}] ",msgBody);
                JSONObject json = JSON.parseObject(msgBody);
                if (json == null) {
                    logger.info("=====" + CONSUMER_NAME + "json解析为空," + CONSUMER_QUIT + "=====");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                String accId = json.getString("planNid");
                Object obj = null;
                if (StringUtils.isNotBlank(accId)) {
                    logger.info("------计划投资------");
                    obj = JSONObject.parseObject(msgBody, HjhAccedeVO.class);
                } else {
                    logger.info("-----散标投资------");
                    obj = JSONObject.parseObject(msgBody, BorrowTenderVO.class);
                }
                if (obj == null) {
                    logger.info("=====" + CONSUMER_NAME + "实体转换异常," + CONSUMER_QUIT + "=====");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                String result = "" ;
                try {
                    String postUrl = systemConfig.getCrmTenderUrl();
                    logger.info("======="+ CONSUMER_NAME  +"消息转换实体信息 [{}]",JSON.toJSONString(obj));
                    String postData = buildData(obj).toJSONString();
                    logger.info("=====" + CONSUMER_NAME + "postUrl:[{}] =====", postUrl);
                    logger.info("=====" + CONSUMER_NAME + "postParam: [{}] ====",postData);
                    result =postJson(postUrl, postData);
                    logger.info("=====" + CONSUMER_NAME + "投递CRM结果 :" +JSON.toJSONString(result));
                } catch (Exception e) {
                    logger.error("=====" + CONSUMER_NAME + ",发生异常,重新投递=====",e);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

                if (TRY.equals(result)) {
                    logger.info("=====" + CONSUMER_NAME + "网络或者链接异常，重新投递======");
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                } else if (SUCCESS.equals(result)){
                    logger.info("=====" + CONSUMER_NAME + "数据同步成功=====");
                    logger.info("=====" + CONSUMER_NAME + "消费结束 =====");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }else {
                    logger.info("=====" + CONSUMER_NAME + "CRM返回未正常处理,不在重复投递 =====");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            } catch (Exception e) {
                logger.error("====="+CONSUMER_NAME+"消费过程发生异常,重新投递 =====",e);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
    }


    /* post数据构造 所取数据为必要数据 */
    private JSONObject buildData(Object obj) {
        JSONObject ret = new JSONObject();
        Map<String, Object> map = Maps.newHashMap();
        // 根据数据类型判断投资类型。直投类
        if (obj instanceof BorrowTenderVO) {
            BorrowTenderVO bt = (BorrowTenderVO) obj;
            UserInfoVO userInfo = amUserClient.findUsersInfoById(bt.getUserId());
            RightBorrowVO borrowVO = amTradeClient.getRightBorrowByNid(bt.getBorrowNid());
            BorrowInfoVO borrowInfo = amTradeClient.getBorrowInfoByNid(bt.getBorrowNid());
            String borrowStyle = borrowVO.getBorrowStyle();

            map.put("idNum", userInfo.getIdcard());
            map.put("referrerIdCard", "");
            map.put("status", 1);
            map.put("borrowType", borrowInfo.getName());
            map.put("borrowNid", bt.getBorrowNid());
            map.put("investmentNid", bt.getNid());
            map.put("unit",
                    CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)
                            || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                            || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)
                            || CustomConstants.BORROW_STYLE_END.equals(borrowStyle) ? 2 : 1);
            map.put("term", borrowVO.getBorrowPeriod());
            map.put("account", bt.getAccount());
            map.put("addTime", GetDate.getTime10(bt.getCreateTime()));
            map.put("loanTime", getDate(bt.getLoanOrderDate()));
            if (StringUtils.isNotBlank(borrowVO.getPlanNid())) {
                map.put("productNo", 1007);
            } else {
                map.put("productNo", 1001);
            }
            map.put("referrerDepartmentId", bt.getInviteDepartmentId());
        }
        // 计划类投资
        else if (obj instanceof HjhAccedeVO) {
            HjhAccedeVO hj = (HjhAccedeVO) obj;
            UserInfoVO userInfo = amUserClient.findUsersInfoById(hj.getUserId());
            HjhPlanResponse response = baseClient.getExe("http://AM-TRADE/am-trade/hjhPlan/getHjhPlanByPlanNid/" + hj.getPlanNid(), HjhPlanResponse.class);
            HjhPlanVO hjhPlan = response.getResult();

            map.put("idNum", userInfo.getIdcard());
            map.put("referrerIdCard", "");
            map.put("status", 1);
            map.put("borrowType", hjhPlan.getPlanName());
            map.put("borrowNid", hj.getPlanNid());
            map.put("investmentNid", hj.getAccedeOrderId());
            map.put("unit", hjhPlan.getIsMonth() == 0 ? 1 : 2);
            map.put("term", hjhPlan.getLockPeriod());
            map.put("account", hj.getAccedeAccount());
            map.put("addTime", GetDate.getTime10(hj.getCreateTime()));
            map.put("loanTime", GetDate.getTime10(hj.getCreateTime()));
            map.put("productNo", 1002);

        }
        map.put("instCode", "10000001");

        String sign = new CheckSignUtil().encryptByRSA(map, "10000001");
        ret.put("instCode", "10000001");
        ret.put("object", map);
        ret.put("sign", sign);
        return ret;
    }


    /**
     * 处理post请求.
     *
     * @param url 参数
     * @return json
     */
    private String postJson(String url, String jsonStr) {

        // 实例化httpClient
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 实例化post方法
        HttpPost httpPost = new HttpPost(url);

        // 结果
        CloseableHttpResponse response = null;
        try {
            // 提交的参数
            StringEntity uefEntity = new StringEntity(jsonStr, "utf-8");
            uefEntity.setContentEncoding("UTF-8");
            uefEntity.setContentType("application/json");
            // 将参数给post方法
            httpPost.setEntity(uefEntity);
            // 执行post方法
            response = httpclient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String content = EntityUtils.toString(response.getEntity());
                logger.info("====="+ CONSUMER_NAME +"crm 投递返回值：[{}]=====",content);
                if (StringUtils.isNotBlank(content)){
                    JSONObject  jasonObject = JSONObject.parseObject(content);
                    Map map = (Map)jasonObject;
                    if (map!=null && "000".equals(String.valueOf(map.get("status")))){
                        return SUCCESS;
                    }
                }
                // 网络状态200， 但是业务处理失败， 标记处理失败  不重试
                return FAILUE;
            }
            // 网络异常 重试
            return TRY;
        } catch (Exception e) {
            e.printStackTrace();
            return TRY;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
