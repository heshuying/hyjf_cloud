/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer.hgdatareport.nifa.registry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.admin.NifaContractTemplateVO;
import com.hyjf.am.vo.admin.NifaFieldDefinitionVO;
import com.hyjf.am.vo.config.SiteSettingsVO;
import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;
import com.hyjf.am.vo.trade.FddTempletVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.hgreportdata.nifa.NifaContractEssenceVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.constants.FddGenerateContractConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.service.consumer.hgdatareport.nifa.NifaContractEssenceMessageService;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LIUSHOUYI
 * @version NifaContractEssenceMessageConsumer, v0.1 2018/6/25 17:52
 */
@Service
@RocketMQMessageListener(topic = MQConstant.HYJF_TOPIC, selectorExpression = MQConstant.LOAN_SUCCESS_TAG, consumerGroup = "NIFA_CONTRACT_ESSENCE")
public class NifaContractEssenceMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(NifaContractEssenceMessageConsumer.class);

    /**
     * 获取18位社会信用代码
     */
    @Value("${hyjf.com.social.credit.code}")
    private String COM_SOCIAL_CREDIT_CODE;

    @Autowired
    NifaContractEssenceMessageService nifaContractEssenceMessageService;

    private String thisMessName = "【生成合同要素信息】";

    @Override
    public void onMessage(MessageExt msg)  {
        // --> 消息检查
        if (msg == null || msg.getBody() == null) {
            logger.error(thisMessName + "接收到的消息为null");
            return;
        }

        // --> 消息转换
        String msgBody = new String(msg.getBody());
        logger.info(thisMessName + "接收到的消息：" + msgBody);

        JSONObject json = null;
        try {
            json = JSONObject.parseObject(msgBody);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error(thisMessName + "解析消息体失败...");
            return;
        }
        String borrowNid = json.getString("borrowNid");
        if (StringUtils.isBlank(borrowNid)) {
            logger.error(thisMessName + "接收到的borrowNid为null");
            return;
        }

        // --> 消息处理

        try {
            // 通过网站设置获取公司信息
            String company = null;
            SiteSettingsVO siteSettingsVO = nifaContractEssenceMessageService.selectSiteSetting();
            if (siteSettingsVO != null && StringUtils.isNotBlank(siteSettingsVO.getCompany())) {
                company = siteSettingsVO.getCompany();
            } else {
                logger.error(thisMessName + "未从数据库获取到公司名称、数据生成使用默认公司名称。");
                company = "惠众商务顾问（北京）有限公司";
            }

            // 获取借款详情
            BorrowAndInfoVO borrow = nifaContractEssenceMessageService.selectBorrowByBorrowNid(borrowNid);
            BorrowInfoVO borrowInfo = nifaContractEssenceMessageService.selectBorrowInfoByBorrowNid(borrowNid);

            // 根据放款编号获取该标的的投资信息
            List<BorrowTenderVO> borrowTenderList = nifaContractEssenceMessageService.selectTenderListByBorrowNid(borrowNid);
            if (null != borrowTenderList && borrowTenderList.size() > 0) {
                for (BorrowTenderVO borrowTender : borrowTenderList) {
                    String nid = borrowTender.getNid();

                    // 防重校验应对部分放款成功的数据
                    List<NifaContractEssenceVO> nifaContractEssenceList = nifaContractEssenceMessageService.selectNifaContractEssenceByContractNo(nid);
                    // 已经生成数据的订单不再重复生成
                    if (null != nifaContractEssenceList && nifaContractEssenceList.size() > 0) {
                        logger.info(thisMessName + "相同合同编号数据已导入合同要素信息表，借款编号:" + borrowNid + "订单号：" + nid);
                        continue;
                    }

                    // 出借人信息
                    Integer userId = borrowTender.getUserId();
                    UserInfoVO lenderInfo = nifaContractEssenceMessageService.getUsersInfoByUserId(userId);

                    // 获取最新合同模板
                    FddTempletVO fddTemplet = nifaContractEssenceMessageService.selectFddTemplet();
                    if (null == fddTemplet) {
                        logger.error(thisMessName + "无法匹配模板----借款编号：" + borrowNid);
                        continue;
                    }

                    // 根据合同编号获取合同模版约定条款
                    NifaContractTemplateVO nifaContractTemplate = nifaContractEssenceMessageService.selectNifaContractTemplateByTemplateNid(fddTemplet.getTempletId());
                    if (null == nifaContractTemplate) {
                        logger.error(thisMessName + "未获取到投资订单相关合同模板信息，借款编号:" + borrowNid + "订单号：" + nid);
                        continue;
                    }

                    // 获取最新互金字段定义
                    NifaFieldDefinitionVO nifaFieldDefinition = nifaContractEssenceMessageService.selectNifaFieldDefinition();
                    if (null == nifaFieldDefinition) {
                        logger.error(thisMessName + "未获取到互金字段定义信息，借款编号:" + borrowNid + "订单号：" + nid);
                        continue;
                    }

                    // 获取还款计算公式
                    BorrowStyleVO borrowStyleVO = nifaContractEssenceMessageService.selectBorrowStyleWithBLOBs(borrow.getBorrowStyle());
                    if (null == borrowStyleVO) {
                        logger.error(thisMessName + "未获取到该还款方式对应的还款公式，借款编号:" + borrowNid + "订单号：" + nid);
                        continue;
                    }

                    // 合同要素信息处理
                    NifaContractEssenceVO nifaContractEssence = new NifaContractEssenceVO();
                    // 统一社会信用代码
                    nifaContractEssence.setPlatformNo(COM_SOCIAL_CREDIT_CODE);
                    // 从业机构名称
                    nifaContractEssence.setPlatformName(company);
                    // 项目编号
                    nifaContractEssence.setProjectNo(borrow.getBorrowNid());
                    // 合同名称
                    nifaContractEssence.setContractName(FddGenerateContractConstant.CONTRACT_DOC_TITLE);
                    // 合同编号
                    nifaContractEssence.setContractNo(nid);
                    // 合同签署日
                    nifaContractEssence.setContractTime(GetDate.timestamptoStrYYYYMMDD(GetDate.getNowTime10()));
                    // 出借人类型 01 个人
                    nifaContractEssence.setInvestorType(1);
                    // 出借人证件类型
                    nifaContractEssence.setInvestorCertType("0");
                    // 出借人证件号码
                    nifaContractEssence.setInvestorCertNo(lenderInfo.getIdcard());
                    // 出借人姓名
                    nifaContractEssence.setInvestorName(lenderInfo.getTruename());

                    // 出借人暂无机构
                    // 出借人统一社会信用代码
                    nifaContractEssence.setInvestorNacaoNo("");
                    // 出借人组织机构代码
                    nifaContractEssence.setInvestorOrgcodeNo("");
                    // 出借人名称
                    nifaContractEssence.setInvestorCompany("");

                    // 借款人信息1：公司 2：个人
                    if (null != borrowInfo.getCompanyOrPersonal() && 1 == borrowInfo.getCompanyOrPersonal()) {
                        // 根据借款编号获取借款人公司信息
                        BorrowUserVO borrowUsers = nifaContractEssenceMessageService.selectBorrowUsersByBorrowNid(borrow.getBorrowNid());
                        if (null == borrowUsers) {
                            logger.error(thisMessName + "未获取到借款人公司信息，订单号：" + borrowTender.getNid());
                            continue;
                        }
                        // 借款人类型
                        nifaContractEssence.setBorrowerType(2);
                        // 合同签署方
                        nifaContractEssence.setContractSigner("甲方：" + lenderInfo.getTruename() + "，乙方：" + borrowUsers.getUsername());
                        // 借款人统一社会信用代码
                        if (null != borrowUsers.getSocialCreditCode()) {
                            nifaContractEssence.setBorrowerNacaoNo(borrowUsers.getSocialCreditCode());
                        } else {
                            // 借款人组织机构代码
                            nifaContractEssence.setBorrowerOrgcodeNo(null != borrowUsers.getCorporateCode() ? borrowUsers.getCorporateCode() : "");
                        }
                        // 借款人名称
                        nifaContractEssence.setBorrowerCompany(borrowUsers.getUsername());
                        // 通知与送达
                        nifaContractEssence.setNoticeAddress(borrowUsers.getRegistrationAddress());
                    } else if (null != borrowInfo.getCompanyOrPersonal() && 2 == borrowInfo.getCompanyOrPersonal()) {
                        // 根据借款编号获取借款人信息
                        BorrowManinfoVO borrowManinfo = nifaContractEssenceMessageService.selectBorrowMainfo(borrow.getBorrowNid());
                        if (null == borrowManinfo) {
                            logger.error(thisMessName + "未获取到借款人信息，订单号：" + borrowTender.getNid());
                            continue;
                        }
                        // 借款人证件类型 0:居民身份证
                        nifaContractEssence.setBorrowerCertType("0");
                        // 借款人类型
                        nifaContractEssence.setBorrowerType(1);
                        // 合同签署方
                        nifaContractEssence.setContractSigner("甲方：" + lenderInfo.getTruename() + "，乙方：" + borrowManinfo.getName());
                        // 借款人证件号码
                        nifaContractEssence.setBorrowerCertNo(borrowManinfo.getCardNo());
                        // 借款人姓名
                        nifaContractEssence.setBorrowerName(borrowManinfo.getName());
                        // 借款人地址
                        nifaContractEssence.setBorrowerAddress(borrowManinfo.getAddress());
                        // 通知与送达
                        nifaContractEssence.setNoticeAddress(borrowManinfo.getAddress());
                    } else {
                        logger.error(thisMessName + "未正确获取到借款人属性！" + borrowTender.getNid());
                        continue;
                    }

                    // 借款金额
                    nifaContractEssence.setInvestAmount(borrowTender.getAccount().toString());
                    // 年化利率（居间服务协议统一取标的的年华利率）
                    nifaContractEssence.setBorrowRate(borrow.getBorrowApr().divide(new BigDecimal("100")).toString());
                    // 借款用途
                    nifaContractEssence.setBorrowUse(borrowInfo.getFinancePurpose());
                    // 借款用途限制
                    nifaContractEssence.setBorrowUseLimit(nifaFieldDefinition.getBorrowingRestrictions());
                    // 借款放款日 最后一笔放款时间
                    nifaContractEssence.setLoanDate(GetDate.timestamptoStrYYYYMMDD(GetDate.getNowTime10()));
                    // 借款放款日判断依据
                    nifaContractEssence.setLoanDateBasis(nifaFieldDefinition.getJudgmentsBased());
                    // 起息日 放款日开始计息
                    nifaContractEssence.setStartDate(GetDate.timestamptoStrYYYYMMDD(GetDate.getNowTime10()));
                    // 是否按月还款
                    boolean isMonth = false;
                    // 到期日
                    Integer lasterDay;
                    // 还款方式 去还款表取数据
                    if ("month".equals(borrow.getBorrowStyle())) {
                        isMonth = true;
                        nifaContractEssence.setRepayType(10);
                    } else if ("end".equals(borrow.getBorrowStyle())) {
                        isMonth = false;
                        nifaContractEssence.setRepayType(25);
                    } else if ("endmonth".equals(borrow.getBorrowStyle())) {
                        isMonth = true;
                        nifaContractEssence.setRepayType(9);
                    } else if ("endday".equals(borrow.getBorrowStyle())) {
                        isMonth = false;
                        nifaContractEssence.setRepayType(25);
                    } else if ("principal".equals(borrow.getBorrowStyle())) {
                        isMonth = true;
                        nifaContractEssence.setRepayType(11);
                    }

                    // 还款计划 json串 查询还款计划表
                    JSONArray jsonArray = new JSONArray();
                    if (isMonth) {
                        // 还款期数
                        nifaContractEssence.setRepayNum(borrow.getBorrowPeriod());
                        // 获取用户投资订单还款详情
                        List<BorrowRecoverPlanVO> borrowRecoverPlanList = nifaContractEssenceMessageService.selectBorrowRecoverPlanList(nid);
                        if (null == borrowRecoverPlanList) {
                            logger.error(thisMessName + "未获取到借款人分期回款信息，订单号：" + borrowTender.getNid() + "借款编号：" + borrow.getBorrowNid() + "借款人id：" + userId);
                            continue;
                        }
                        // 最后一期还款日
                        lasterDay = borrowRecoverPlanList.get(borrowRecoverPlanList.size() - 1).getRecoverTime();
                        // json数据生成
                        for (BorrowRecoverPlanVO borrowRecoverPlan : borrowRecoverPlanList) {
                            Map<String, String> map = new HashMap<String, String>();
                            try {
                                map.put("date", GetDate.timestamptoStrYYYYMMDD(lasterDay));
                            } catch (Exception e) {
                                logger.error(thisMessName + "还款日格式化失败，borrowNid:" + borrowNid + " 还款日期：" + borrowRecoverPlan.getRecoverTime());
                                logger.error(e.getMessage());
                                continue;
                            }
                            map.put("principal", borrowRecoverPlan.getRecoverCapital().toString());
                            map.put("interest", borrowRecoverPlan.getRecoverInterest().toString());
                            jsonArray.add(map);
                        }
                    } else {
                        // 还款期数（到期还本还息的只有1期）
                        nifaContractEssence.setRepayNum(1);
                        // 根据借款编号获取还款计划
                        BorrowRecoverVO borrowRecover = nifaContractEssenceMessageService.selectBorrowRecover(nid);
                        if (null == borrowRecover) {
                            logger.error(thisMessName + "未获取到回款计划信息，借款编号:" + borrowNid + "订单号：" + nid + "借款编号：" + borrow.getBorrowNid() + "借款人id：" + userId);
                            continue;
                        }
                        // 最后还款日
                        lasterDay = borrowRecover.getRecoverTime();
                        // json数据生成
                        Map<String, String> map = new HashMap<String, String>();
                        try {
                            map.put("date", GetDate.timestamptoStrYYYYMMDD(lasterDay));
                        } catch (Exception e) {
                            logger.error(thisMessName + "还款日格式化失败，borrowNid:" + borrowNid + " 还款日期：" + borrowRecover.getRecoverTime());
                            logger.error(e.getMessage());
                            continue;
                        }
                        map.put("principal", borrowRecover.getRecoverCapital().toString());
                        map.put("interest", borrowRecover.getRecoverInterest().toString());
                        jsonArray.add(map);
                    }

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("plan", jsonArray);
                    nifaContractEssence.setRepayPlan(jsonObject.toString());

                    // 到期日设定
                    try {
                        nifaContractEssence.setExpiryDate(GetDate.timestamptoStrYYYYMMDD(lasterDay));
                    } catch (Exception e) {
                        logger.error(thisMessName + "最后一期还款日格式化失败，borrowNid:" + borrowNid);
                        logger.error(e.getMessage());
                        continue;
                    }
                    // 还款方式含义及计算公式
                    nifaContractEssence.setRepayFormula(borrowStyleVO.getContents());

                    // 还款日判断依据
                    nifaContractEssence.setRepayDateRule(nifaFieldDefinition.getRepayDateRule());
                    // 逾期还款定义
                    nifaContractEssence.setOverdueRepayDef(nifaFieldDefinition.getOverdueDefinition());
                    // 逾期还款责任
                    nifaContractEssence.setOverdueRepayResp(nifaFieldDefinition.getOverdueResponsibility());
                    // 逾期还款流程
                    nifaContractEssence.setOverdueRepayProc(nifaFieldDefinition.getOverdueProcess());
                    // 合同生效日
                    nifaContractEssence.setContractEffectiveDate(GetDate.timestamptoStrYYYYMMDD(GetDate.getNowTime10()));
                    // 合同模板编号
                    nifaContractEssence.setContractTemplateNo(COM_SOCIAL_CREDIT_CODE + fddTemplet.getTempletId());
                    // 数据创建更新人id
                    nifaContractEssence.setCreateUserId(0);
                    nifaContractEssence.setUpdateUserId(0);
                    // 插入数据库
                    boolean result = nifaContractEssenceMessageService.insertNifaContractEssence(nifaContractEssence) > 0 ? true : false;
                    if (result) {
                        logger.error(thisMessName + "互金合同要素信息插入失败，合同订单号：" + nid);
                    }
                }
            } else {
                logger.error(thisMessName + "未查询到投资信息,借款编号:" + borrowNid);
            }
        } catch (Exception e) {
            logger.error(thisMessName + "生成合同要素处理失败,借款编号:" + borrowNid, e);
        } finally {
            logger.info(thisMessName + "生成合同要素处理结束,借款编号:" + borrowNid);
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====NifaContractEssence consumer=====");
    }
}
