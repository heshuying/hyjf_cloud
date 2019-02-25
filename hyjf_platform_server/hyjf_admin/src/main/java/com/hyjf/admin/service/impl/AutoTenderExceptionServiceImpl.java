/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.TenderExceptionSolveRequestBean;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.AutoTenderExceptionService;
import com.hyjf.am.response.admin.AutoTenderExceptionResponse;
import com.hyjf.am.response.trade.HjhAccedeResponse;
import com.hyjf.am.response.trade.HjhPlanBorrowTmpResponse;
import com.hyjf.am.resquest.admin.AutoTenderExceptionRequest;
import com.hyjf.am.resquest.admin.TenderExceptionSolveRequest;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.hjh.*;
import com.hyjf.am.vo.trade.hjh.calculate.HjhCreditCalcResultVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.bean.RedisBorrow;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author nxl
 * @version AutoTenderExceptionServiceImpl, v0.1 2018/7/12 10:30
 */
@Service
public class AutoTenderExceptionServiceImpl extends BaseServiceImpl implements AutoTenderExceptionService {
    private String logHeader = "【智投自动出借异常处理】";
    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private CommonProducer commonProducer;

    /**
     * 检索汇计划加入明细列表
     * @param request
     * @return
     */
    @Override
    public AutoTenderExceptionResponse selectAccedeRecordList(AutoTenderExceptionRequest request){
        return amTradeClient.selectAccedeRecordList(request);
    }

    /**
     * 查找HjhAccedeVO实体
     * @param tenderExceptionSolveRequest
     * @return
     */
    @Override
    public HjhAccedeVO doGetHjhAccedeVO(TenderExceptionSolveRequest tenderExceptionSolveRequest){
        HjhAccedeResponse hjhAccedeResponse =amTradeClient.doSelectHjhAccedeByParam(tenderExceptionSolveRequest);
        HjhAccedeVO hjhAccedeVO = null;
        if(null!=hjhAccedeResponse){
            hjhAccedeVO =  hjhAccedeResponse.getResult();
        }
        return hjhAccedeVO;
    }

    /**
     * 查找HjhPlanBorrowTmpVO实体
     * @param tenderExceptionSolveRequest
     * @return
     */
    @Override
    public HjhPlanBorrowTmpVO getHjhPlanBorrowTmpVO(TenderExceptionSolveRequest tenderExceptionSolveRequest){
        HjhPlanBorrowTmpResponse hjhAccedeResponse =amTradeClient.selectBorrowJoinList(tenderExceptionSolveRequest);
        HjhPlanBorrowTmpVO  hjhAccedeVO = null;
        if(null!=hjhAccedeResponse){
            hjhAccedeVO =  hjhAccedeResponse.getResult();
        }
        return hjhAccedeVO;
    }
    /**
     * 查询相应的债权的状态
     *
     * @param userId
     * @param accountId
     * @param orderId
     * @return
     */
    @Override
    public BankCallBean debtStatusQuery(int userId, String accountId, String orderId) {
        // 获取共同参数
        BankCallBean bean = new BankCallBean();
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallConstant.TXCODE_BID_APPLY_QUERY);// 消息类型
        bean.setLogUserId(String.valueOf(userId));
        /*bean.setInstCode(PropUtils.getSystem(BankCallConstant.BANK_INSTCODE));// 机构代码
        //银行代码
        bean.setBankCode(PropUtils.getSystem(BankCallConstant.BANK_BANKCODE));*/
        //暂定
        bean.setBankCode(systemConfig.getBANK_BANKCODE());
        bean.setInstCode(systemConfig.getBANK_INSTCODE());// 机构代码
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        bean.setAccountId(accountId);// 电子账号
        bean.setOrgOrderId(orderId);
        bean.setLogOrderId(GetOrderIdUtils.getUsrId(userId));

        BankCallBean statusResult = BankCallUtils.callApiBg(bean);

        return statusResult;
    }
    /**
     * 查询相应的债权的状态
     *
     * @param userId
     * @param accountId
     * @param orderId
     * @return
     */
    @Override
    public BankCallBean creditStatusQuery(int userId, String accountId, String orderId) {
        // 获取共同参数
        BankCallBean bean = new BankCallBean();
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallConstant.TXCODE_CREDIT_INVEST_QUERY);// 消息类型
        bean.setLogUserId(String.valueOf(userId));
        /*
        // 机构代码
        bean.setInstCode(PropUtils.getSystem(BankCallConstant.BANK_INSTCODE));
        //银行代码
        bean.setBankCode(PropUtils.getSystem(BankCallConstant.BANK_BANKCODE));*/
        //暂定
        bean.setBankCode("30050000");
        bean.setInstCode("00810001");
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        bean.setAccountId(accountId);// 电子账号
        bean.setOrgOrderId(orderId);
        bean.setLogOrderId(GetOrderIdUtils.getUsrId(userId));

        BankCallBean statusResult = BankCallUtils.callApiBg(bean);

        return statusResult;
    }

    @Override
    public HjhPlanVO getFirstHjhPlanVO(String planNid){
        List<HjhPlanVO> hjhPlanVOList= amTradeClient.getHjhPlanByPlanNid(planNid);
        if(null!=hjhPlanVOList&&hjhPlanVOList.size()>0){
            return hjhPlanVOList.get(0);
        }
        return null;
    }
    /**
     * 根据编号获取borrow
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowAndInfoVO selectBorrowByNid(String borrowNid){
        return amTradeClient.selectBorrowByNid(borrowNid);
    }
    /**
     * 根据编号获取borrow
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowAndInfoVO doSelectBorrowByNid(String borrowNid){
        return amTradeClient.doSelectBorrowByNid(borrowNid);
    }
    /**
     * 更新出借数据
     *
     * @return
     * @author nxl
     */
    @Override
    public boolean updateBorrowForAutoTender(BorrowAndInfoVO borrow, HjhAccedeVO hjhAccede, BankCallBean bean){
        return amTradeClient.updateBorrowForAutoTender(borrow.getBorrowNid(), hjhAccede.getAccedeOrderId(),bean);
    }
    /**
     * 更新
     * @param status
     * @param accedeId
     * @return
     */
    @Override
    public boolean updateTenderByParam(int status,int accedeId){
        boolean ret = amTradeClient.updateTenderByParam(status,accedeId);
        logger.info("【智投自动出借异常处理】Order_Status => " + status);
        return ret;
    }
    /**
     * 计算实际金额 保存creditTenderLog表
     *
     * @return
     * @author nxl
     */
    @Override
    public HjhCreditCalcResultVO saveCreditTenderLogNoSave(HjhDebtCreditVO credit, HjhAccedeVO hjhAccede, String orderId, String orderDate, BigDecimal yujiAmoust, boolean isLast) {
        return amTradeClient.saveCreditTenderLogNoSave(credit,hjhAccede,orderId,orderDate,yujiAmoust,isLast);
    }
    /**
     * 汇计划自动承接成功后数据库更新操作
     *
     * @return
     * @author nxl
     */
    @Override
    public boolean updateCreditForAutoTender(HjhDebtCreditVO credit, HjhAccedeVO hjhAccede, HjhPlanVO hjhPlan, BankCallBean bean,String tenderUsrcustid, String sellerUsrcustid, HjhCreditCalcResultVO resultVO){
        return amTradeClient.updateCreditForAutoTender(credit.getCreditNid(), hjhAccede.getAccedeOrderId(), hjhPlan.getPlanNid(),bean,tenderUsrcustid,sellerUsrcustid,resultVO);
    }

    /**
     * 异常处理
     * @param tenderExceptionSolveRequestBean
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String tenderExceptionAction(TenderExceptionSolveRequestBean tenderExceptionSolveRequestBean){

        String userId = tenderExceptionSolveRequestBean.getUserId();//加入用户ID
        String accedeOrderId = tenderExceptionSolveRequestBean.getPlanOrderId();//加入订单号
        String planNid = tenderExceptionSolveRequestBean.getDebtPlanNid();//计划编号
        String logMsgHeader = logHeader + "智投订单号[" + accedeOrderId + "]";
        logger.info(logMsgHeader + "=============开始=============");

        TenderExceptionSolveRequest tenderExceptionSolveRequest = new TenderExceptionSolveRequest();
        BeanUtils.copyProperties(tenderExceptionSolveRequestBean, tenderExceptionSolveRequest);

        int userIdint = Integer.parseInt(userId);

        HjhAccedeVO hjhAccede = doGetHjhAccedeVO(tenderExceptionSolveRequest);
        if(hjhAccede == null){
            logger.warn(logMsgHeader + " 没有加入明细");
            return accedeOrderId+" 没有加入明细";
        }
        if(hjhAccede.getOrderStatus() == null ||
                hjhAccede.getOrderStatus().compareTo(90) >= 0){
            logger.warn(logMsgHeader + " >90 失败(联系管理员) 手动处理");
            return accedeOrderId+" >90 失败(联系管理员) 手动处理";
        }
        // 原始标，异常先查异常表，访问银行接口，如果订单成功，查看表是否OK, 修复后，更新状态。没修复不更新
        // 债转也是一样。如失败则直接更新
        HjhPlanBorrowTmpVO hjhPlanBorrowTmp =getHjhPlanBorrowTmpVO(tenderExceptionSolveRequest);
        if(hjhPlanBorrowTmp == null){
            logger.error(logMsgHeader + " 没有tmp出借异常明细");
            return accedeOrderId+" 没有tmp出借异常明细";
        }

        logger.info(logMsgHeader + "标的编号：" + hjhPlanBorrowTmp.getBorrowNid() + "，出借人："+hjhPlanBorrowTmp.getUserId()+", 交易编号：" + hjhPlanBorrowTmp.getOrderId() + "，异常返回码：" + hjhPlanBorrowTmp.getRespCode());

        if(StringUtils.isBlank(hjhPlanBorrowTmp.getOrderId())){
            logger.error(logMsgHeader + " 没有tmp出借异常订单");
            return accedeOrderId+" 没有tmp出借异常订单";

        }
        BankOpenAccountVO borrowUserAccount = amUserClient.queryBankOpenAccountByUserId(userIdint);
        if (borrowUserAccount == null || StringUtils.isBlank(borrowUserAccount.getAccount())) {
            logger.error(logMsgHeader + "出借人未开户:" + userId);
            return "出借人未开户:" + userId;

        }
        // 借款人账户
        String borrowUserAccountId = borrowUserAccount.getAccount();
        // 为了判断
        // 修复完成的orderStatus
        int orderStatus = hjhAccede.getOrderStatus()%10;
        hjhAccede.setOrderStatus(orderStatus);

        //异常处理初始订单状态设定
        final Integer ORDER_STATUS_INIT = orderStatus + 100;
        //异常处理异常订单状态设定
        final Integer ORDER_STATUS_ERR = orderStatus + 110;

        try {
            // 原始标出借
            if(hjhPlanBorrowTmp.getBorrowType() == 0){
                BorrowAndInfoVO borrow = doSelectBorrowByNid(hjhPlanBorrowTmp.getBorrowNid());
                if (borrow == null) {
                    logger.error(logMsgHeader + "[" + hjhPlanBorrowTmp.getBorrowNid() + "]" + "标的号不存在 ");
                    return accedeOrderId+" 标的号不存在 "+hjhPlanBorrowTmp.getBorrowNid();

                }

                // 目前处理 510000 银行系统返回异常
                // CA101141	投标记录不存在
                BankCallBean debtQuery = debtStatusQuery(userIdint, borrowUserAccountId,hjhPlanBorrowTmp.getOrderId());
                if(debtQuery == null || StringUtils.isBlank(debtQuery.getRetCode())){
                    logger.error(logMsgHeader + userIdint + "  " + borrowUserAccountId + "  " + hjhPlanBorrowTmp.getOrderId() + " 请求银行查询无响应");
                    return accedeOrderId+" 请求银行查询无响应";
                }
                String queryRetCode = debtQuery.getRetCode();
                logger.info(logMsgHeader + "查询出借请求返回：" + queryRetCode);
                boolean bankQueryisOK = false;
                if (BankCallConstant.RESPCODE_SUCCESS.equals(queryRetCode)) {
                    String state = StringUtils.isNotBlank(debtQuery.getState()) ? debtQuery.getState() : "";
                    logger.info(logMsgHeader + "查询出借请求成功:" + hjhPlanBorrowTmp.getOrderId() + " 状态 " + state + "(1：投标中； 2：计息中；4：本息已返还；9：已撤销)");
                    // 1：投标中； 2：计息中；4：本息已返还；9：已撤销；
                    if (StringUtils.isNotBlank(state) && "1".equals(state)) {
                        bankQueryisOK = true;
                    }else{
                        logger.error(logMsgHeader + "查询出借请求成功，但状态非投标中:" + hjhPlanBorrowTmp.getOrderId() + " 状态 " + state + "(1：投标中； 2：计息中；4：本息已返还；9：已撤销)");
                        return accedeOrderId + "查询出借请求成功，但状态非投标中:" + hjhPlanBorrowTmp.getOrderId() + " 状态 " + state + "(1：投标中； 2：计息中；4：本息已返还；9：已撤销)";
                    }
                }
                if(bankQueryisOK){
                    logger.info(logMsgHeader + "0********查询到银行自动出借成功，开始做BD和redis更新。（2）");
                    logger.info(logMsgHeader + "投前的可投金额：" + hjhAccede.getAvailableInvestAccount() + "，" + "投前的本次金额：" + debtQuery.getTxAmount() + "，标的"
                            + borrow.getBorrowNid() + "可投余额：" + borrow.getBorrowAccountWait());

                    // 校验
                    // 标的待投金额DB和redis是否一致
                    // 防止爆标校验
                    if (borrow.getBorrowAccountWait().compareTo(BigDecimal.ZERO) == 0){
                        logger.error(logMsgHeader + "该标的：" + borrow.getBorrowNid() + "的待投金额为0。 银行成功需要撤标");
                        return accedeOrderId + "该标的：" + borrow.getBorrowNid() + "的待投金额为0。银行成功需要撤标";
                    }

                    if (borrow.getBorrowAccountWait().compareTo(new BigDecimal(debtQuery.getTxAmount())) < 0){
                        logger.error(logMsgHeader + "该标的：" + borrow.getBorrowNid() + "的投资金额" + debtQuery.getTxAmount() + ">待投金额" + borrow.getBorrowAccountWait() + "。银行成功需要撤标");
                        return accedeOrderId + "该标的：" + borrow.getBorrowNid() + "的投资金额>待投金额。银行成功需要撤标";
                    }

                    debtQuery.setOrderId(hjhPlanBorrowTmp.getOrderId());

                    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓核心异常处理开始↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
                    updateTenderByParam(ORDER_STATUS_INIT,hjhAccede.getId());
                    RedisBorrow redisBorrow = new RedisBorrow();
                    try{
                        // BD更新出借数据
                        boolean isOK = updateBorrowForAutoTender(borrow,hjhAccede,debtQuery);
                        if(isOK){
                            logger.info(logMsgHeader + "1********BD更新出借数据成功");
                            // 更改加入明细状态和出借临时表状态
                            // 更改加入明细状态
                            updateTenderByParam(orderStatus,hjhAccede.getId());

                            // 如果标的可投金额非0，推回队列的头部
                            String queueName = RedisConstants.HJH_PLAN_LIST + RedisConstants.HJH_BORROW_INVEST + hjhAccede.getPlanNid();
                            redisBorrow.setBorrowAccountWait(borrow.getBorrowAccountWait().subtract(hjhPlanBorrowTmp.getAccount()));
                            redisBorrow.setBorrowNid(borrow.getBorrowNid());

                            // 待投=0时也推回队列，埋点等相关处理放到batch中处理
//                            if (redisBorrow.getBorrowAccountWait().compareTo(BigDecimal.ZERO) != 0) {
                            String redisStr = JSON.toJSONString(redisBorrow);
                            RedisUtils.rightpush(queueName,redisStr);
                            logger.info(logMsgHeader + "2********Redis:" + queueName + "(r)<<<<<<<<<<<<<<<<<<<<<<<<<<" + redisStr);
//                            }else{
//                                logger.info(logMsgHeader + "2********标的" + borrow.getBorrowNid() + "待投：" + redisBorrow.getBorrowAccountWait() + "，已经满标，不再推回队列。");
//                                // add by liushouyi nifa2 20181204 start
//                                // 满标发送满标状态埋点
//                                // 发送发标成功的消息队列到互金上报数据
//                                JSONObject params = new JSONObject();
//                                params.put("borrowNid", borrow.getBorrowNid());
//                                commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.ISSUE_INVESTED_TAG, UUID.randomUUID().toString(), params),
//                                        MQConstant.HG_REPORT_DELAY_LEVEL);
//                                // add by liushouyi nifa2 20181204 end
//                            }
                        }
                    } catch (Exception e) {
                        updateTenderByParam(ORDER_STATUS_ERR,hjhAccede.getId());
                        logger.error(logMsgHeader + "核心异常处理（出借银行成功）发生未知异常！！！", e);
                        return accedeOrderId+" 核心异常处理（出借银行成功）发生未知异常！！！";
                    }
                    //  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑核心异常处理结束↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
                    HjhAccedeVO hjhAccedeEnd = doGetHjhAccedeVO(tenderExceptionSolveRequest);
                    logger.info(logMsgHeader + "投后的可投金额：" + hjhAccedeEnd.getAvailableInvestAccount() + "，"
                            + redisBorrow.getBorrowNid() + "可投余额：" + redisBorrow.getBorrowAccountWait());
                    // 投标记录不存在才会继续，不然属于未知情况
                }else if ("CA101141".equals(queryRetCode)){
                    // 自动出借时银行调用失败
                    if(!BankCallConstant.RESPCODE_SUCCESS.equals(hjhPlanBorrowTmp.getRespCode())) {
                        // 更改加入明细状态和出借临时表状态
                        logger.info(logMsgHeader + "0********查询到银行自动出借失败，恢复DB智投订单和Redis队列的标的信息，待batch重新自动出借。（3）");
                        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓核心异常处理开始↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
                        updateTenderByParam(ORDER_STATUS_INIT,hjhAccede.getId());
                        try{
                            updateTenderByParam(orderStatus,hjhAccede.getId());
                            logger.info(logMsgHeader + "1********DB智投订单信息已经恢复。");

                            // 投标成功后减掉redis 钱
                            String queueName = RedisConstants.HJH_PLAN_LIST + RedisConstants.HJH_BORROW_INVEST + hjhAccede.getPlanNid();
                            RedisBorrow redisBorrow = new RedisBorrow();
                            redisBorrow.setBorrowAccountWait(borrow.getBorrowAccountWait());
                            redisBorrow.setBorrowNid(borrow.getBorrowNid());

                            // 银行成功后，如果标的可投金额非0，推回队列的头部
                            // 待投=0时也推回队列，埋点等相关处理放到batch中处理
//                            if (redisBorrow.getBorrowAccountWait().compareTo(BigDecimal.ZERO) != 0) {
                            String redisStr = JSON.toJSONString(redisBorrow);
                            RedisUtils.rightpush(queueName, redisStr);
                            logger.info(logMsgHeader + "2********Redis:" + queueName + "(r)<<<<<<<<<<<<<<<<<<<<<<<<<<" + redisStr);
//                            }else{
//                                logger.warn(logMsgHeader + "2********标的" + borrow.getBorrowNid() + "待投：" + redisBorrow.getBorrowAccountWait() + "，已经满标，不再推回队列。(可能存在满标状态未更新的情况)");
//                            }
                            // 删除临时表
                            amTradeClient.deleteBorrowTmp(borrow.getBorrowNid(), hjhAccede.getAccedeOrderId());
                            logger.info(logMsgHeader + "3********BorrowTmp删除成功。");
                        } catch (Exception e) {
                            updateTenderByParam(ORDER_STATUS_ERR,hjhAccede.getId());
                            logger.error(logMsgHeader + "核心异常处理（出借银行失败）发生未知异常！！！", e);
                            return accedeOrderId+" 核心异常处理（出借银行失败）发生未知异常！！！";
                        }
                        //  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑核心异常处理结束↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
                    }else{
                        logger.error(logMsgHeader + "查询银行投标记录不存在（" + queryRetCode + "），但出借调用银行时接口成功（" + hjhPlanBorrowTmp.getRespCode() + "）。 表更新失败");
                        return (accedeOrderId + "查询银行投标记录不存在，但出借调用银行时接口成功，表更新失败。暂时没有处理该异常，需要协调开发处理  "+ queryRetCode);
                    }

                }else{
                    logger.error(logMsgHeader + "********查询到银行自动出借失败，查询银行结果："+queryRetCode+"，不是CA101141，需调查原因");
                    return accedeOrderId+" 暂时没有处理该异常，需要协调开发处理  "+ queryRetCode;
                }
                // 债转标出借
            }else if(hjhPlanBorrowTmp.getBorrowType() == 1){
                HjhDebtCreditVO credit =amTradeClient.doSelectHjhDebtCreditByCreditNid(hjhPlanBorrowTmp.getBorrowNid());
                if (credit == null) {
                    logger.error(logMsgHeader + "债转号不存在 "+hjhPlanBorrowTmp.getBorrowNid());
                    return accedeOrderId+" 债转号不存在 "+hjhPlanBorrowTmp.getBorrowNid();
                }
                // 标的待承接金额
                BigDecimal await = credit.getLiquidationFairValue().subtract(credit.getCreditPrice());

                // 为了即信同步银行的结果，先调用下“出借人投标申请查询”接口，返回的txAmount不带承接利息
                BankCallBean bean = debtStatusQuery(userIdint, borrowUserAccountId,hjhPlanBorrowTmp.getOrderId());
                // 调用下“出借人购买债权查询”接口，返回的txAmount带承接利息
                bean = creditStatusQuery(userIdint, borrowUserAccountId, hjhPlanBorrowTmp.getOrderId());
                String queryRetCode = StringUtils.isNotBlank(bean.getRetCode()) ? bean.getRetCode() : "";
                if(BankCallConstant.RESPCODE_SUCCESS.equals(queryRetCode)){
                    logger.info(logMsgHeader + "0********查询到银行自动承接成功，开始做BD和redis更新。（2）");
                    logger.info(logMsgHeader + "承前的可承金额：" + hjhAccede.getAvailableInvestAccount() + "，本次承接金额：" + bean.getTxAmount() + "，标的"
                            + credit.getCreditNid() + "待承投余额：" + await);

                    //BD更新承接数据
                    HjhPlanVO hjhPlan = getFirstHjhPlanVO(hjhAccede.getPlanNid());
                    BankOpenAccountVO sellerBankOpenAccount = amUserClient.queryBankOpenAccountByUserId(credit.getUserId());
                    if (sellerBankOpenAccount == null) {
                        logger.error(logMsgHeader + "转出用户没开户 "+credit.getUserId());
                        return  accedeOrderId+" 债转号不存在 "+hjhPlanBorrowTmp.getBorrowNid();
                    }
                    String sellerUsrcustid = sellerBankOpenAccount.getAccount();//出让用户的江西银行电子账号
                    // 生成承接日志
                    // 债权承接订单日期
                    String orderDate = GetDate.getServerDateTime(1, new Date());
                    Boolean isLast = false;
                    //是否标的的最后一笔出借/承接(0:非最后一笔；1:最后一笔)
                    if(hjhPlanBorrowTmp.getIsLast()==1){
                        isLast = true;
                    }
                    // 计算实际金额 保存creditTenderLog表
                    HjhCreditCalcResultVO resultVO = saveCreditTenderLogNoSave(credit, hjhAccede,
                            hjhPlanBorrowTmp.getOrderId(), orderDate, hjhPlanBorrowTmp.getAccount(),isLast);
                    if (Validator.isNull(resultVO)) {
                        logger.error(logMsgHeader + "保存creditTenderLog表失败。");
                        return  "保存creditTenderLog表失败，计划订单号：" + hjhAccede.getAccedeOrderId();
                    }
                    //汇计划自动出借(收债转服务费)
                    logger.info(logMsgHeader + "承接用计算完成\n"
                            + resultVO.toLog());
                    // add 汇计划三期 汇计划自动出借(收债转服务费) liubin 20180515 end
                    bean.setOrderId(hjhPlanBorrowTmp.getOrderId());


                    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓核心异常处理开始↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
                    updateTenderByParam(ORDER_STATUS_INIT,hjhAccede.getId());
                    RedisBorrow redisBorrow = new RedisBorrow();
                    try{
                        //BD更新承接数据
                        boolean isOK = updateCreditForAutoTender(credit, hjhAccede, hjhPlan, bean, borrowUserAccountId, sellerUsrcustid, resultVO);
                        if(isOK){
                            logger.info(logMsgHeader + "1********BD更新出借数据成功");

                            // 更改加入明细状态和出借临时表状态
                            updateTenderByParam(orderStatus,hjhAccede.getId());

                            // add 合规数据上报 埋点 liubin 20181122 start
                            // 推送数据到MQ 承接（每笔）
                            JSONObject params = new JSONObject();
                            params.put("assignOrderId", bean.getOrderId());
                            params.put("flag", "2"); //1（散）2（智投）
                            params.put("status", "1"); //1承接（每笔）
                            commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.UNDERTAKE_SINGLE_SUCCESS_TAG, UUID.randomUUID().toString(), params),
                                    MQConstant.HG_REPORT_DELAY_LEVEL);
                            // add 合规数据上报 埋点 liubin 20181122 end

                            // 更新后重新查
                            credit =amTradeClient.doSelectHjhDebtCreditByCreditNid(hjhPlanBorrowTmp.getBorrowNid());
                            if (credit == null) {
                                logger.error(logMsgHeader + "债转号不存在 "+hjhPlanBorrowTmp.getBorrowNid());
                                return accedeOrderId+" 债转号不存在 结束债权 "+hjhPlanBorrowTmp.getBorrowNid();
                            }
                            // 剩余待投金额退回队列
                            // 投标成功后减掉redis 钱
                            String queueName =RedisConstants.HJH_PLAN_LIST + RedisConstants.HJH_BORROW_CREDIT + hjhAccede.getPlanNid();
                            await = credit.getLiquidationFairValue().subtract(credit.getCreditPrice());
                            redisBorrow.setBorrowAccountWait(await);
                            redisBorrow.setBorrowNid(credit.getCreditNid());
                            // 银行成功后，如果标的可投金额非0，推回队列的头部，标的可投金额为0，不再推回队列
//                            if (await.compareTo(BigDecimal.ZERO) >= 0) {
                            String redisStr = JSON.toJSONString(redisBorrow);
                            RedisUtils.rightpush(queueName,redisStr);
                            logger.info(logMsgHeader + "2********Redis:" + queueName + "(r)<<<<<<<<<<<<<<<<<<<<<<<<<<" + redisStr);
                            //完全承接时，结束债券放回到batch处理
//                            }
//                            /** 4.7. 完全承接时，结束债券  */
//                            if (credit.getCreditAccountWait().compareTo(BigDecimal.ZERO) == 0) {
//                                logger.info(logMsgHeader + "标的" + credit.getCreditNid() + "待承接金额：" + redisBorrow.getBorrowAccountWait() + "，已经完全承接，不再推回队列。");
//                                requestHjhCreditEnd(accedeOrderId, redisBorrow.getBorrowNid(), sellerUsrcustid);
//                            }
                        }
                    } catch (Exception e) {
                        updateTenderByParam(ORDER_STATUS_ERR,hjhAccede.getId());
                        logger.error(logMsgHeader + "核心异常处理（承接银行成功）发生未知异常！！！", e);
                        return accedeOrderId+" 核心异常处理（承接银行成功）发生未知异常！！！";
                    }
                    //  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑核心异常处理结束↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
                    HjhAccedeVO hjhAccedeEnd = doGetHjhAccedeVO(tenderExceptionSolveRequest);
                    logger.info(logMsgHeader + "承后的承后金额：" + hjhAccedeEnd.getAvailableInvestAccount() + "，"
                            + redisBorrow.getBorrowNid() + "承后余额：" + await);
                    //查询银行返回投标记录不存在
                }else if ("CA110112".equals(queryRetCode)){

                    // batch调用银行自动承接接口失败时
                    if(!BankCallConstant.RESPCODE_SUCCESS.equals(hjhPlanBorrowTmp.getRespCode())){
                        // 更改加入明细状态和出借临时表状态
                        logger.info(logMsgHeader + "0********查询到银行自动承接失败，恢复DB智投订单和Redis队列的标的信息，待batch重新自动承接。（3）");
                        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓核心异常处理开始↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
                        updateTenderByParam(ORDER_STATUS_INIT,hjhAccede.getId());
                        try{
                            updateTenderByParam(orderStatus,hjhAccede.getId());
                            logger.info(logMsgHeader + "1********DB智投订单信息已经恢复。");

                            // 投标成功后减掉redis 钱
                            String queueName = RedisConstants.HJH_PLAN_LIST + RedisConstants.HJH_BORROW_CREDIT + hjhAccede.getPlanNid();
                            RedisBorrow redisBorrow = new RedisBorrow();
                            redisBorrow.setBorrowAccountWait(await);
                            redisBorrow.setBorrowNid(credit.getCreditNid());
                            // 银行成功后，如果标的可投金额非0，推回队列的头部
//                            if (await.compareTo(BigDecimal.ZERO) >= 0) {
                            String redisStr = JSON.toJSONString(redisBorrow);
                            RedisUtils.rightpush(queueName,redisStr);
                            logger.info(logMsgHeader + "2********Redis:" + queueName + "(r)<<<<<<<<<<<<<<<<<<<<<<<<<<" + redisStr);
//                            }
                            // 删除临时表
                            amTradeClient.deleteBorrowTmp(credit.getCreditNid(), hjhAccede.getAccedeOrderId());
                            logger.info(logMsgHeader + "3********BorrowTmp删除成功。");
                        } catch (Exception e) {
                            updateTenderByParam(ORDER_STATUS_ERR,hjhAccede.getId());
                            logger.error(logMsgHeader + "核心异常处理（承接银行失败）发生未知异常！！！", e);
                            return accedeOrderId+" 核心异常处理（承接银行失败）发生未知异常！！！";
                        }
                        //  ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑核心异常处理结束↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
                    }else {
                        // batch调用银行自动承接接口成功时
                        logger.error(logMsgHeader + "查询银行投标记录不存在（" + queryRetCode + "），但承接调用银行时接口成功（" + hjhPlanBorrowTmp.getRespCode() + "）。 表更新失败");
                        return (accedeOrderId + "查询银行投标记录不存在，但承接调用银行时接口成功，表更新失败。暂时没有处理该异常，需要协调开发处理  "+ queryRetCode);
                    }
                }else{
                    logger.error(logMsgHeader + " 暂时没有处理该异常，需要协调开发处理  "+ queryRetCode);
                    return accedeOrderId+" 暂时没有处理该异常，需要协调开发处理  "+ queryRetCode;
                }
            }
            logger.info(logMsgHeader + "=============结束=============");
            return null;
        } catch (Exception e) {
            logger.error(logMsgHeader + "非核心异常处理发生未知异常！！！", e);
            return accedeOrderId+" 非核心异常处理发生未知异常，需要协调开发处理";
        }
    }

    public String getSellerAuthCode(String tenderOrderId, Integer SourceType) {
        String authCode = null;
        if (SourceType.compareTo(1) == 0) {
            // 1原始债权
            BorrowTenderVO borrowtender = amTradeClient.getBorrowTenderByNid(tenderOrderId);
            if (borrowtender == null || borrowtender.getAuthCode() == null) {
                logger.error("未从BorrowTender获取出让人"+tenderOrderId+"的投标成功的授权号。  ");
                return null;
            }
            authCode = borrowtender.getAuthCode();
        }else {
            // 0非原始债权
            HjhDebtCreditTenderVO hjhDebtCreditTender = amTradeClient.getByAssignOrderId(tenderOrderId);
            if (hjhDebtCreditTender == null || hjhDebtCreditTender.getAuthCode() == null) {
                logger.error("未从HjhDebtCreditTender获取出让人"+tenderOrderId+"的债转成功的授权号。  ");
                return null;
            }
            authCode = hjhDebtCreditTender.getAuthCode();
        }
        return authCode;
    }
    public boolean requestDebtEnd(HjhDebtCreditVO credit, String tenderAccountId, String tenderAuthCode) throws Exception {
        return amTradeClient.requestDebtEnd(credit, tenderAccountId, tenderAuthCode) > 0 ? true : false;
    }

    /**
     * 请求结束债权（插入结束债权任务）
     * @param accedeOrderId
     * @param creditNid
     * @param sellerUsrcustid
     * @throws Exception
     */
    private void requestHjhCreditEnd(String accedeOrderId, String creditNid, String sellerUsrcustid) throws Exception {
        String logMsgHeader = "【请求结束债权(智投完全承接)】";
        logger.info(logMsgHeader + "----------开始--------- 债转号：" + creditNid);

        // 1.获取债转详情	 */
        HjhDebtCreditVO credit = this.amTradeClient.doSelectHjhDebtCreditByCreditNid(creditNid); // 从主库
        if (credit == null) {
            throw new RuntimeException(logMsgHeader + "债转号不存在 "+creditNid);
        }
        if (credit.getCreditAccountWait().compareTo(BigDecimal.ZERO) != 0){ // 待承接金额不为0时
            throw new RuntimeException(logMsgHeader + "债转号" + creditNid + "未被完全承接，不能结束债权。未被承接金额： " + credit.getCreditAccountWait());
        }

        // 2.获取出让人投标成功的授权号
        String sellerAuthCode = this.getSellerAuthCode(credit.getSellOrderId(), credit.getSourceType());
        if (sellerAuthCode == null) {
            throw new RuntimeException(logMsgHeader + "未取得出让人" + credit.getUserId() + "的债权类型" +
                    credit.getSourceType() + "(0非原始 1原始)的授权码，结束债权失败。");
        }

        // add 合规数据上报 埋点 liubin 20181122 start
        // 推送数据到MQ 承接（完全）
        JSONObject params = new JSONObject();
        params.put("creditNid", credit.getCreditNid());
        params.put("flag", "2"); //1（散）2（智投）
        params.put("status", "2"); //2承接（完全）
        commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.UNDERTAKE_ALL_SUCCESS_TAG, UUID.randomUUID().toString(), params),
                MQConstant.HG_REPORT_DELAY_LEVEL);
        // add 合规数据上报 埋点 liubin 20181122 end

        // 3.插入结束债权任务
        boolean ret = this.amTradeClient.requestDebtEnd(credit, sellerUsrcustid, sellerAuthCode) > 0 ? true : false;
        if (!ret) {
            logger.info(logMsgHeader + "被承接标的" + credit.getCreditNid() + "被完全承接，银行结束债权失败。");
        }

        // 4.更新债权表为完全承接
        ret = this.amTradeClient.updateHjhDebtCreditForEnd(credit) > 0 ? true : false;
        if (!ret) {
            logger.info(logMsgHeader + "银行结束债权后，更新债权表为完全承接失败。");
        }
        logger.info(logMsgHeader + "被承接标的" + credit.getCreditNid() + "被完全承接，银行结束债权成功。");
    }
}
