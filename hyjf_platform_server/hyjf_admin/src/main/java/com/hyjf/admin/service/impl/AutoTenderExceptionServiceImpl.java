/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.admin.beans.request.TenderExceptionSolveRequestBean;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.service.AutoTenderExceptionService;
import com.hyjf.am.response.admin.AutoTenderExceptionResponse;
import com.hyjf.am.response.trade.HjhAccedeResponse;
import com.hyjf.am.response.trade.HjhPlanBorrowTmpResponse;
import com.hyjf.am.resquest.admin.AutoTenderExceptionRequest;
import com.hyjf.am.resquest.admin.TenderExceptionSolveRequest;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.hjh.*;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.bean.RedisBorrow;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
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
import java.util.Map;

/**
 * @author nxl
 * @version AutoTenderExceptionServiceImpl, v0.1 2018/7/12 10:30
 */
@Service
public class AutoTenderExceptionServiceImpl extends BaseServiceImpl implements AutoTenderExceptionService {
    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    private SystemConfig systemConfig;

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
    public HjhAccedeVO getHjhAccedeVO(TenderExceptionSolveRequest tenderExceptionSolveRequest){
        HjhAccedeResponse hjhAccedeResponse =amTradeClient.selectHjhAccedeByParam(tenderExceptionSolveRequest);
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
     * 更新投资数据
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
        return amTradeClient.updateTenderByParam(status,accedeId);
    }
    /**
     * 计算实际金额 保存creditTenderLog表
     *
     * @return
     * @author nxl
     */
    @Override
    public Map<String, Object> saveCreditTenderLogNoSave(HjhDebtCreditVO credit, HjhAccedeVO hjhAccede, String orderId, String orderDate, BigDecimal yujiAmoust, boolean isLast) {
        return amTradeClient.saveCreditTenderLogNoSave(credit,hjhAccede,orderId,orderDate,yujiAmoust,isLast);
    }
    /**
     * 汇计划自动承接成功后数据库更新操作
     *
     * @return
     * @author nxl
     */
    @Override
    public boolean updateCreditForAutoTender(HjhDebtCreditVO credit, HjhAccedeVO hjhAccede, HjhPlanVO hjhPlan, BankCallBean bean,String tenderUsrcustid, String sellerUsrcustid, Map<String, Object> resultMap){
        return amTradeClient.updateCreditForAutoTender(credit.getCreditNid(), hjhAccede.getAccedeOrderId(), hjhPlan.getPlanNid(),bean,tenderUsrcustid,sellerUsrcustid,resultMap);
    }

    /**
     * 异常处理
     * @param tenderExceptionSolveRequestBean
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String tenderExceptionAction(TenderExceptionSolveRequestBean tenderExceptionSolveRequestBean){
        String userId = tenderExceptionSolveRequestBean.getUserId();
        String planOrderId = tenderExceptionSolveRequestBean.getPlanOrderId();
        String debtPlanNid = tenderExceptionSolveRequestBean.getDebtPlanNid();
        logger.info("===自动投资异常处理开始。。。 userId:"+userId+",planOrderId:"+planOrderId+",debtPlanNid:"+debtPlanNid);
        TenderExceptionSolveRequest tenderExceptionSolveRequest = new TenderExceptionSolveRequest();
        BeanUtils.copyProperties(tenderExceptionSolveRequestBean, tenderExceptionSolveRequest);
        String retMessage = "系统异常";
        try {
            int userIdint = Integer.parseInt(userId);

            HjhAccedeVO hjhAccede = getHjhAccedeVO(tenderExceptionSolveRequest);
            if(hjhAccede == null){
                return planOrderId+" 没有加入明细";
            }
            if(hjhAccede.getOrderStatus() == null ||
                    hjhAccede.getOrderStatus().intValue() >= 90){
                return planOrderId+" >90 失败(联系管理员) 手动处理";

            }
            // 原始标，异常先查异常表，访问银行接口，如果订单成功，查看表是否OK, 修复后，更新状态。没修复不更新
            // 债转也是一样。如失败则直接更新
            HjhPlanBorrowTmpVO hjhPlanBorrowTmp =getHjhPlanBorrowTmpVO(tenderExceptionSolveRequest);
            if(hjhPlanBorrowTmp == null){
                logger.info(planOrderId+" 没有投资异常明细");
                return planOrderId+" 没有投资异常明细";
            }

            logger.info(planOrderId+"， "+hjhPlanBorrowTmp.getBorrowNid()+",  异常返回码："+hjhPlanBorrowTmp.getRespCode());

            if(StringUtils.isBlank(hjhPlanBorrowTmp.getOrderId())){
                return planOrderId+" 没有投资异常订单";

            }
            BankOpenAccountVO borrowUserAccount = amUserClient.queryBankOpenAccountByUserId(userIdint);
            if (borrowUserAccount == null || StringUtils.isBlank(borrowUserAccount.getAccount())) {
                logger.info("投资人未开户:" + userId);
                return "投资人未开户:" + userId;

            }
            // 借款人账户
            String borrowUserAccountId = borrowUserAccount.getAccount();
            // 为了判断
            int orderStatus = hjhAccede.getOrderStatus()%10;
            hjhAccede.setOrderStatus(orderStatus);
            // 原始标投资
            if(hjhPlanBorrowTmp.getBorrowType() == 0){
                BorrowAndInfoVO borrow = selectBorrowByNid(hjhPlanBorrowTmp.getBorrowNid());
                if (borrow == null) {
                    logger.error("[" + hjhPlanBorrowTmp.getBorrowNid() + "]" + "标的号不存在 ");
                    return planOrderId+" 标的号不存在 "+hjhPlanBorrowTmp.getBorrowNid();

                }
                // 目前处理 510000 银行系统返回异常
                // CA101141	投标记录不存在
                BankCallBean debtQuery = debtStatusQuery(userIdint, borrowUserAccountId,hjhPlanBorrowTmp.getOrderId());
                if(debtQuery == null || StringUtils.isBlank(debtQuery.getRetCode())){
                    logger.info(userIdint+"  "+borrowUserAccountId+"  "+hjhPlanBorrowTmp.getOrderId()+" 请求银行查询无响应");
                    return planOrderId+" 请求银行查询无响应";
                }
                String queryRetCode = debtQuery.getRetCode();
                boolean bankQueryisOK = false;
                if (BankCallConstant.RESPCODE_SUCCESS.equals(queryRetCode)) {
                    String state = StringUtils.isNotBlank(debtQuery.getState()) ? debtQuery.getState() : "";
                    logger.info(("查询投资请求成功:" + hjhPlanBorrowTmp.getOrderId())+ " 状态 "+state);
                    // 1：投标中； 2：计息中；4：本息已返还；9：已撤销；
                    if (StringUtils.isNotBlank(state)) {
                        if ("1".equals(state)) {
                            bankQueryisOK = true;
                        } else if ("2".equals(state)) {
                        }
                    }
                }
                if(bankQueryisOK){
                    logger.info("查询银行记录成功:" + userId);
                    debtQuery.setOrderId(hjhPlanBorrowTmp.getOrderId());
                    // 如果有tmp表，说明第一接口调用了，调用成功，表更新失败，无如都是需要调用下面的方法
                    boolean isOK = updateBorrowForAutoTender(borrow,hjhAccede,debtQuery);
                    if(isOK){
                        // 更改加入明细状态和投资临时表状态
                        updateTenderByParam(orderStatus,hjhAccede.getId());
                        // 只有不是接口成功表失败的情况才会退回队列
                        // 此处 if判断为汇计划三期优化改动
//                        if(!BankCallConstant.RESPCODE_SUCCESS.equals(hjhPlanBorrowTmp.getRespCode())){
                            // 投资成功后减掉redis 钱
                            String queueName = RedisConstants.HJH_PLAN_LIST + RedisConstants.HJH_BORROW_INVEST + RedisConstants.HJH_SLASH + hjhAccede.getPlanNid();
                            RedisBorrow redisBorrow = new RedisBorrow();
                            redisBorrow.setBorrowAccountWait(borrow.getBorrowAccountWait().subtract(hjhPlanBorrowTmp.getAccount()));
                            redisBorrow.setBorrowNid(borrow.getBorrowNid());

                            // 银行成功后，如果标的可投金额非0，推回队列的头部，标的可投金额为0，不再推回队列
                            if (redisBorrow.getBorrowAccountWait().compareTo(BigDecimal.ZERO) != 0) {
                                String redisStr = JSON.toJSONString(redisBorrow);
                                logger.info("退回队列 " + queueName + redisStr);
                                RedisUtils.rightpush(queueName,redisStr);
                            }
//                        }
                    }
                    // 投标记录不存在才会继续，不然属于未知情况
                }else if ("CA101141".equals(queryRetCode)){
                    // 更改加入明细状态和投资临时表状态
                    logger.info(hjhPlanBorrowTmp.getRespCode()+" 查询银行记录成功，但是投资失败:" + userId);
                    updateTenderByParam(hjhAccede.getId(),orderStatus);

                    // 只有不是接口成功表失败的情况才会退回队列
                    // 此处 if判断为汇计划三期优化改动
//                    if(!BankCallConstant.RESPCODE_SUCCESS.equals(hjhPlanBorrowTmp.getRespCode())){
                        // 投资成功后减掉redis 钱
                        String queueName = RedisConstants.HJH_PLAN_LIST + RedisConstants.HJH_BORROW_INVEST + RedisConstants.HJH_SLASH + hjhAccede.getPlanNid();
                        RedisBorrow redisBorrow = new RedisBorrow();
                        redisBorrow.setBorrowAccountWait(borrow.getBorrowAccountWait());
                        redisBorrow.setBorrowNid(borrow.getBorrowNid());

                        // 银行成功后，如果标的可投金额非0，推回队列的头部，标的可投金额为0，不再推回队列
                        if (redisBorrow.getBorrowAccountWait().compareTo(BigDecimal.ZERO) != 0) {
                            String redisStr = JSON.toJSONString(redisBorrow);
                            logger.info("退回队列 " + queueName + redisStr);
                            RedisUtils.rightpush(queueName,redisStr);
                        }
//                    }
                }else{
                    return planOrderId+" 暂时没有处理该异常，需要协调开发处理  "+ queryRetCode;
                }
                // 债转标投资
            }else if(hjhPlanBorrowTmp.getBorrowType() == 1){
                HjhDebtCreditVO credit =amTradeClient.selectHjhDebtCreditByCreditNid(hjhPlanBorrowTmp.getBorrowNid());
                if (credit == null) {
                    logger.error("[" + hjhPlanBorrowTmp.getAccedeOrderId() + "]" + "债转号不存在 "+hjhPlanBorrowTmp.getBorrowNid());
                    return planOrderId+" 债转号不存在 "+hjhPlanBorrowTmp.getBorrowNid();
                }
                //BankCallBean bean = creditStatusQuery(userIdint, borrowUserAccountId, hjhPlanBorrowTmp.getOrderId());
                // 为了即信同步银行的结果，先调用下“投资人投标申请查询”接口，返回的txAmount不带承接利息
                BankCallBean bean = debtStatusQuery(userIdint, borrowUserAccountId,hjhPlanBorrowTmp.getOrderId());
                // 调用下“投资人购买债权查询”接口，返回的txAmount带承接利息
                bean = creditStatusQuery(userIdint, borrowUserAccountId, hjhPlanBorrowTmp.getOrderId());
                String queryRetCode = StringUtils.isNotBlank(bean.getRetCode()) ? bean.getRetCode() : "";
                if(BankCallConstant.RESPCODE_SUCCESS.equals(queryRetCode)){
                    logger.info("查询银行记录成功:" + userId);

                    //更新表
                    HjhPlanVO hjhPlan = getFirstHjhPlanVO(hjhAccede.getPlanNid());
                    BankOpenAccountVO sellerBankOpenAccount = amUserClient.queryBankOpenAccountByUserId(credit.getUserId());
                    if (sellerBankOpenAccount == null) {
                        logger.info("[" + hjhPlanBorrowTmp.getAccedeOrderId() + "]" + "转出用户没开户 "+credit.getUserId());
                        return  planOrderId+" 债转号不存在 "+hjhPlanBorrowTmp.getBorrowNid();
                    }
                    String sellerUsrcustid = sellerBankOpenAccount.getAccount();//出让用户的江西银行电子账号
                    // 生成承接日志
                    // 债权承接订单日期
                    String orderDate = GetDate.getServerDateTime(1, new Date());
                    Boolean isLast = false;
                    //是否标的的最后一笔投资/承接(0:非最后一笔；1:最后一笔)
                    if(hjhPlanBorrowTmp.getIsLast()==1){
                        isLast = true;
                    }
                    // 计算实际金额 保存creditTenderLog表
                    Map<String, Object> resultMap = saveCreditTenderLogNoSave(credit, hjhAccede,
                            hjhPlanBorrowTmp.getOrderId(), orderDate, hjhPlanBorrowTmp.getAccount(),isLast);
                    if (Validator.isNull(resultMap)) {
                        logger.info("保存creditTenderLog表失败，计划订单号：" + hjhAccede.getAccedeOrderId());
                        return  "保存creditTenderLog表失败，计划订单号：" + hjhAccede.getAccedeOrderId();
                    }
                    //汇计划自动投资(收债转服务费)
                    logger.info("[" + hjhAccede.getAccedeOrderId() + "]" + "承接用计算完成"
                            + "\n,分期数据结果:" + resultMap.get("assignResult")
                            + "\n,承接总额:" + resultMap.get("assignAccount")
                            + "\n,承接本金:" + resultMap.get("assignCapital")
                            + "\n,承接利息:" + resultMap.get("assignInterest")
                            + "\n,承接支付金额:" + resultMap.get("assignPay")
                            + "\n,承接垫付利息:" + resultMap.get("assignAdvanceMentInterest")
                            + "\n,承接延期利息:" + resultMap.get("assignRepayDelayInterest")
                            + "\n,承接逾期利息:" + resultMap.get("assignRepayLateInterest")
                            + "\n,分期本金:" + resultMap.get("assignPeriodCapital")
                            + "\n,分期利息:" + resultMap.get("assignPeriodInterest")
                            + "\n,分期垫付利息:" + resultMap.get("assignPeriodAdvanceMentInterest")
                            + "\n,分期承接延期利息:" + resultMap.get("assignPeriodRepayDelayInterest")
                            + "\n,分期承接延期利息:" + resultMap.get("assignPeriodRepayLateInterest")
                            + "\n,承接服务率:" + resultMap.get("serviceApr")
                            + "\n,承接服务费:" + resultMap.get("serviceFee"));
                    // add 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 end
                    bean.setOrderId(hjhPlanBorrowTmp.getOrderId());
                    boolean isOK = updateCreditForAutoTender(credit, hjhAccede, hjhPlan, bean, borrowUserAccountId, sellerUsrcustid, resultMap);
                    if(isOK){
                        // 更改加入明细状态和投资临时表状态
                        updateTenderByParam(orderStatus,hjhAccede.getId());
                        // 更新后重新查
                        credit =amTradeClient.selectHjhDebtCreditByCreditNid(hjhPlanBorrowTmp.getBorrowNid());
                        if (credit == null) {
                            logger.error("[" + hjhPlanBorrowTmp.getAccedeOrderId() + "]" + "债转号不存在 "+hjhPlanBorrowTmp.getBorrowNid());
                            return planOrderId+" 债转号不存在 结束债权 "+hjhPlanBorrowTmp.getBorrowNid();
                        }
                        // 只有不是接口成功表失败的情况才会退回队列
                        if(!BankCallConstant.RESPCODE_SUCCESS.equals(hjhPlanBorrowTmp.getRespCode())){
                            // 投资成功后减掉redis 钱
                            String queueName = RedisConstants.HJH_PLAN_LIST + RedisConstants.HJH_BORROW_CREDIT + RedisConstants.HJH_SLASH + hjhAccede.getPlanNid();
                            RedisBorrow redisBorrow = new RedisBorrow();
                            BigDecimal await = credit.getLiquidationFairValue().subtract(credit.getCreditPrice());
                            redisBorrow.setBorrowAccountWait(await);
                            redisBorrow.setBorrowNid(credit.getCreditNid());
                            logger.info(credit.getCreditNid()+" 更新表退回队列  "+await);
                            // 银行成功后，如果标的可投金额非0，推回队列的头部，标的可投金额为0，不再推回队列
                            if (await.compareTo(BigDecimal.ZERO) >= 0) {
                                String redisStr = JSON.toJSONString(redisBorrow);
                                logger.info("退回队列 " + queueName + redisStr);
                                RedisUtils.rightpush(queueName,redisStr);
                            }
                        }
                        /** 4.7. 完全承接时，结束债券  */
                        if (credit.getCreditAccountWait().compareTo(BigDecimal.ZERO) == 0) {
                            //获取出让人投标成功的授权号
                            String sellerAuthCode =getSellerAuthCode(credit.getSellOrderId(), credit.getSourceType());
                            if (sellerAuthCode == null) {
                                logger.info("[" + hjhPlanBorrowTmp.getAccedeOrderId() + "]未取得出让人" + credit.getUserId() + "的债权类型" +
                                        credit.getSourceType() + "(1原始0原始)的授权码，结束债权失败。");
                                return planOrderId+"(1原始0原始)的授权码，结束债权失败。";
                            }

                            //调用银行结束债权接口
                            boolean ret = requestDebtEnd(credit, sellerUsrcustid, sellerAuthCode);
                            if (!ret) {
                                logger.info("[" + hjhPlanBorrowTmp.getAccedeOrderId() + "]被承接标的" + hjhPlanBorrowTmp.getBorrowNid() +"被完全承接，银行结束债权失败。");
                            }
                            logger.info("[" + hjhPlanBorrowTmp.getAccedeOrderId() + "]被承接标的" + hjhPlanBorrowTmp.getBorrowNid() +"被完全承接，银行结束债权成功。");
                            //银行结束债权后，更新债权表为完全承接
                            ret = requestDebtEnd(credit, sellerUsrcustid, sellerAuthCode);
                            if (!ret) {
                                logger.info("[" + hjhPlanBorrowTmp.getAccedeOrderId() + "]银行结束债权后，更新债权表为完全承接失败。");
                            }
                        }
                    }
                }else if ("CA110112".equals(queryRetCode)){
                    // 更改加入明细状态和投资临时表状态
                    updateTenderByParam(orderStatus,hjhAccede.getId());
                    // 只有不是接口成功表失败的情况才会退回队列
                    if(!BankCallConstant.RESPCODE_SUCCESS.equals(hjhPlanBorrowTmp.getRespCode())){
                        // 投资成功后减掉redis 钱
                        String queueName = RedisConstants.HJH_PLAN_LIST + RedisConstants.HJH_BORROW_CREDIT + RedisConstants.HJH_SLASH + hjhAccede.getPlanNid();
                        RedisBorrow redisBorrow = new RedisBorrow();
                        BigDecimal await = credit.getLiquidationFairValue().subtract(credit.getCreditPrice());
                        redisBorrow.setBorrowAccountWait(await);
                        redisBorrow.setBorrowNid(credit.getCreditNid());
                        logger.info(credit.getCreditNid()+" 更新表退回队列  "+await);
                        // 银行成功后，如果标的可投金额非0，推回队列的头部，标的可投金额为0，不再推回队列
                        if (await.compareTo(BigDecimal.ZERO) >= 0) {
                            String redisStr = JSON.toJSONString(redisBorrow);
                            logger.info("退回队列 " + queueName + redisStr);
                            RedisUtils.rightpush(queueName,redisStr);
                        }
                    }
                }else{
                    return planOrderId+" 暂时没有处理该异常，需要协调开发处理  "+ queryRetCode;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
}
