package com.hyjf.cs.trade.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Maps;
import com.hyjf.am.bean.crmtender.CrmInvestMsgBean;
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
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RocketMQMessageListener(topic = MQConstant.CRM_TENDER_INFO_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.BORROW_GROUP)
public class CrmInvestMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(CrmInvestMessageConsumer.class);

    private static final String CONSUMER_NAME = "<<CRM出借信息同步>>:";

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
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        // 设置并发数
        defaultMQPushConsumer.setConsumeThreadMin(1);
        defaultMQPushConsumer.setConsumeThreadMax(1);
        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(1);
        defaultMQPushConsumer.setConsumeTimeout(30);
        logger.info("====" + CONSUMER_NAME + "监听初始化完成, 启动完毕=====");
    }

    @Override
    public void onMessage(MessageExt msg) {
        logger.info("======" + CONSUMER_NAME + "消费开始 ========");
        try {
            logger.info("========" + CONSUMER_NAME + "监听器收到消息:{}========", JSON.toJSONString(msg, SerializerFeature.IgnoreNonFieldGetter));
            String msgBody = new String(msg.getBody());
            CrmInvestMsgBean crmInvestMsgBean = null;
            crmInvestMsgBean = JSONObject.parseObject(msgBody, CrmInvestMsgBean.class);
            if (crmInvestMsgBean == null) {
                logger.info("=====" + CONSUMER_NAME + "json解析为空," + CONSUMER_QUIT + "=====");
                return;
            }
            // 投资或加入订单号
            String orderId = crmInvestMsgBean.getOrderId();
            if (StringUtils.isEmpty(orderId)) {
                logger.info("=====" + CONSUMER_NAME + "投资或加入订单号为空," + CONSUMER_QUIT + "=====");
                return;
            }
            // 投资类型
            Integer investType = crmInvestMsgBean.getInvestType();
            String result = "";
            try {
                JSONObject postData = buildData(orderId, investType);
                if (postData == null){
                    logger.error("构造数据出错");
                    return;
                }
                String postDataStr = postData.toJSONString();
                String postUrl = systemConfig.getCrmTenderUrl();
                logger.info("=====" + CONSUMER_NAME + "postUrl:[{}] =====", postUrl);
                logger.info("=====" + CONSUMER_NAME + "postParam: [{}] ====", postDataStr);
                result = postJson(postUrl, postDataStr);
                logger.info("=====" + CONSUMER_NAME + "投递CRM结果 :" + JSON.toJSONString(result));
            } catch (Exception e) {
                logger.error("=====" + CONSUMER_NAME + ",发生异常,重新投递=====", e);
                return;
            }
            if (TRY.equals(result)) {
                logger.info("=====" + CONSUMER_NAME + "网络或者链接异常，重新投递======");
                return;
            } else if (SUCCESS.equals(result)) {
                logger.info("=====" + CONSUMER_NAME + "数据同步成功=====");
                logger.info("=====" + CONSUMER_NAME + "消费结束 =====");
                return;
            } else {
                logger.info("=====" + CONSUMER_NAME + "CRM返回未正常处理,不在重复投递 =====");
                return;
            }
        } catch (Exception e) {
            logger.error("=====" + CONSUMER_NAME + "消费过程发生异常,重新投递 =====", e);
            return;
        }
    }

    /**
     * post数据构造,所取数据为必要数据
     *
     * @param orderId
     * @param investType
     * @return
     */
    private JSONObject buildData(String orderId, Integer investType) {
        JSONObject ret = new JSONObject();
        Map<String, Object> map = Maps.newHashMap();
        // 根据数据类型判断出借类型。直投类
        if (investType == 0) {
            BorrowTenderVO bt = amTradeClient.selectBorrowTenderByOrderId(orderId);
            if (bt == null) {
                logger.error("根据订单号查询出借记录不存在,出借订单号:[" + orderId + "]");
                return null;
            }
            // 获取出借人用户信息
            UserInfoVO userInfo = amUserClient.findUsersInfoById(bt.getUserId());
            if (userInfo == null) {
                logger.error("根据出借人ID查询出借人信息失败,出借人用户ID:[" + bt.getUserId() + "].");
                return null;
            }
            RightBorrowVO borrowVO = amTradeClient.getRightBorrowByNid(bt.getBorrowNid());
            if (borrowVO == null) {
                logger.error("根据出借标的编号查询标的信息失败,出借标的编号:[" + bt.getBorrowNid() + "].");
                return null;
            }
            BorrowInfoVO borrowInfo = amTradeClient.getBorrowInfoByNid(bt.getBorrowNid());
            if (borrowInfo == null){
                logger.error("根据出借标的编号查询标的详情信息失败,出借标的编号:["+ bt.getBorrowNid()+"].");
                return null;
            }
            String borrowStyle = borrowVO.getBorrowStyle();
            // 出借人身份证号
            map.put("idNum", userInfo.getIdcard());
            map.put("status", 1);
            map.put("borrowType", borrowInfo.getName());
            // 标的编号
            map.put("borrowNid", bt.getBorrowNid());
            // 出借订单号
            map.put("investmentNid", bt.getNid());
            map.put("unit", CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle) ? 1 : 2);
            map.put("term", borrowVO.getBorrowPeriod());
            map.put("account", bt.getAccount());
            map.put("addTime", GetDate.getTime10(bt.getCreateTime()));
            map.put("loanTime", GetDate.getTime10(bt.getCreateTime()));
            map.put("productNo", 1001);
            map.put("referrerDepartmentId", bt.getInviteDepartmentId());
        }
        // 计划类出借
        else if (investType == 1) {
            HjhAccedeVO hj = amTradeClient.getHjhAccede(orderId);
            if (hj == null){
                logger.error("根据加入订单号查询计划加入订单失败,加入订单号:[" + orderId + "].");
                return null;
            }
            UserInfoVO userInfo = amUserClient.findUsersInfoById(hj.getUserId());
            if (userInfo == null) {
                logger.error("根据出借人ID查询出借人信息失败,出借人用户ID:[" + hj.getUserId() + "].");
                return null;
            }
            HjhPlanVO hjhPlanVO = amTradeClient.getHjhPlan(hj.getPlanNid());
            if (hjhPlanVO == null){
                logger.error("根据智投编号查询智投信息失败,智投编号:["+hj.getPlanNid()+"].");
                return null;
            }
            map.put("idNum", userInfo.getIdcard());
            map.put("status", 1);
            map.put("borrowType", hjhPlanVO.getPlanName());
            map.put("borrowNid", hj.getPlanNid());
            map.put("investmentNid", hj.getAccedeOrderId());
            map.put("unit", hjhPlanVO.getIsMonth() == 0 ? 1 : 2);
            map.put("term", hjhPlanVO.getLockPeriod());
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

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String content = EntityUtils.toString(response.getEntity());
                logger.info("=====" + CONSUMER_NAME + "crm 投递返回值：[{}]=====", content);
                if (StringUtils.isNotBlank(content)) {
                    JSONObject jasonObject = JSONObject.parseObject(content);
                    Map map = (Map) jasonObject;
                    if (map != null && "000".equals(String.valueOf(map.get("status")))) {
                        return SUCCESS;
                    }
                }
                // 网络状态200， 但是业务处理失败， 标记处理失败  不重试
                return FAILUE;
            }
            // 网络异常 重试
            return TRY;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return TRY;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }
}
