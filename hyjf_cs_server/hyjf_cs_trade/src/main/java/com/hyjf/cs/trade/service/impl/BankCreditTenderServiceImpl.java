package com.hyjf.cs.trade.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.user.EmployeeCustomizeResponse;
import com.hyjf.am.resquest.trade.CreditTenderRequest;
import com.hyjf.am.vo.statistics.AppChannelStatisticsDetailVO;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.CreditTenderLogVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.AmMongoClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.client.BankCreditTenderClient;
import com.hyjf.cs.trade.mq.AppChannelStatisticsProducer;
import com.hyjf.cs.trade.mq.FddProducer;
import com.hyjf.cs.trade.mq.Producer;
import com.hyjf.cs.trade.service.BankCreditTenderService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 债转投资异常Service实现类
 *
 * @author jun
 * @since 20180619
 */
@Service
public class BankCreditTenderServiceImpl extends BaseServiceImpl implements BankCreditTenderService {

    private static final Logger logger = LoggerFactory.getLogger(BankCreditTenderServiceImpl.class);

    @Autowired
    private BankCreditTenderClient bankCreditTenderClient;
    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    private FddProducer fddProducer;
    @Autowired
    private AmMongoClient amMongoClient;
    @Autowired
    private AppChannelStatisticsProducer appChannelStatisticsProducer;

    /**
     * 处理债转投资异常
     */
    @Override
    public void handle() {
        logger.info("债转投资掉单异常处理开始...");
        //查询债转承接掉单的数据
        List<CreditTenderLogVO> creditTenderLogs=bankCreditTenderClient.selectCreditTenderLogs();
        if (CollectionUtils.isNotEmpty(creditTenderLogs)){
            logger.info("待处理数据:size:[" + creditTenderLogs.size() + "].");
            for (CreditTenderLogVO creditTenderLog : creditTenderLogs) {
                // 承接订单号
                String assignNid = creditTenderLog.getAssignNid();
                Integer userId = creditTenderLog.getUserId();
                String logOrderId = creditTenderLog.getLogOrderId();
                // 根据承接订单号查询债转投资表
                List<CreditTenderVO> creditTenderList = this.bankCreditTenderClient.selectCreditTender(assignNid);
                if (CollectionUtils.isNotEmpty(creditTenderList)) {
                    continue;
                }
                BankCallBean tenderQueryBean = this.creditInvestQuery(logOrderId, userId);
                if (tenderQueryBean!=null){
                    // bean实体转化
                    tenderQueryBean.convert();
                    // 获取债转查询返回码
                    String retCode = StringUtils.isNotBlank(tenderQueryBean.getRetCode()) ? tenderQueryBean.getRetCode() : "";
                    // 承接成功
                    if (!BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {

                        // 直接返回查询银行债转状态查询失败
                        // mod by nxl 20180412 更新log表中的状态(有几个固定的状态，待确认)需将状态设置为9 start
                        if("CA110112".equals(retCode)) {
                            //投标记录不存在
                            creditTenderLog.setStatus((byte) 9);
                            boolean tenderLogsFlag = this.bankCreditTenderClient.updateCreditTenderLog(creditTenderLog);
                            if(tenderLogsFlag) {
                                logger.info("债转投资记录日志表creditTenderLog表更新成功，承接订单号编号：" + assignNid+"，应答码："+retCode);
                            }
                        }
                        // 更新log表中的状态(有几个固定的状态，待确认)需将状态设置为9 end
                        continue;
                    }

                    // 查询相应的债转承接记录
                    CreditTenderLogVO creditenderLog = this.bankCreditTenderClient.selectCreditTenderLogByOrderId(logOrderId);
                    if (creditenderLog!=null){
                        try {
                            // 此次查询的授权码
                            String authCode = tenderQueryBean.getAuthCode();
                            if (StringUtils.isNotBlank(authCode)) {
                                int sellerUserId = creditTenderLog.getCreditUserId();
                                // 取得债权出让人的用户在汇付天下的客户号
                                BankOpenAccountVO sellerBankAccount = this.bankCreditTenderClient.getBankOpenAccount(sellerUserId);
                                // 取得承接债转的用户在汇付天下的客户号
                                BankOpenAccountVO assignBankAccount = this.bankCreditTenderClient.getBankOpenAccount(userId);
                                //查询债转承接掉单的数据
                                List<CreditTenderLogVO> creditTenderLogVOs=this.bankCreditTenderClient.getCreditTenderLogs(logOrderId, userId);

                                UserVO webUser = null;
                                UserInfoVO userInfo = null;
                                List<BorrowCreditVO> borrowCreditList = null;
                                if(CollectionUtils.isNotEmpty(creditTenderLogVOs) && creditTenderLogVOs.size() == 1) {
                                	CreditTenderLogVO creditTenderLogVO = creditTenderLogVOs.get(0);
                                	// 债转编号
                        			String creditNid = creditTenderLogVO.getCreditNid();
                        			// 原始投资订单号
                        			String tenderOrderId = creditTenderLog.getCreditTenderNid();
                        			// 获取会转让标的列表
                                    borrowCreditList = this.bankCreditTenderClient.getBorrowCreditList(creditNid,sellerUserId,tenderOrderId);
                        			if(CollectionUtils.isNotEmpty(borrowCreditList) && borrowCreditList.size()==1){
                                        BorrowCreditVO borrowCreditVO=borrowCreditList.get(0);
                                        webUser=this.amUserClient.findUserById(borrowCreditVO.getCreditUserId());
                                        userInfo=this.amUserClient.findUsersInfoById(borrowCreditVO.getCreditUserId());
                                    }

                        			
                                }


                                UserInfoCustomizeVO userInfoCustomize = this.amUserClient.queryUserInfoCustomizeByUserId(userId);

                                SpreadsUserVO spreadsUsers = this.amUserClient.querySpreadsUsersByUserId(userId);

                                //承接人
                                UserInfoCustomizeVO userInfoCustomizeRefCj = null;

                                //添加承接人承接时推荐人信息
                                if(spreadsUsers!=null){
                                    int refUserId = spreadsUsers.getSpreadsUserId();
                                    userInfoCustomizeRefCj = this.amUserClient.queryUserInfoCustomizeByUserId(refUserId);
                                }

                                UserInfoCustomizeVO userInfoCustomizeSeller = this.amUserClient.queryUserInfoCustomizeByUserId(sellerUserId);;
                                SpreadsUserVO spreadsUsersSeller = this.amUserClient.querySpreadsUsersByUserId(sellerUserId);

                                //出让人
                                UserInfoCustomizeVO userInfoCustomizeRefCr;
                                //添加出让人承接时推荐人信息
                                if (spreadsUsersSeller != null) {
                                    int refUserId = spreadsUsersSeller.getSpreadsUserId();
                                    userInfoCustomizeRefCr = this.amUserClient.queryUserInfoCustomizeByUserId(refUserId);

                                }
                                //更新网站收支明细记录
                                EmployeeCustomizeResponse employeeCustomizeResponse=this.getEmployeeCustomize(userId,spreadsUsers);

                                UserVO investUser = this.amUserClient.findUserById(userId);

                                CreditTenderRequest request = new CreditTenderRequest();
                                request.setAssignNid(logOrderId);
                                request.setUserId(userId);
                                request.setAuthCode(authCode);
                                request.setSellerBankAccount(sellerBankAccount);
                                request.setAssignBankAccount(assignBankAccount);
                                request.setWebUser(webUser);
                                request.setUserInfo(userInfo);
                                request.setSpreadsUsers(spreadsUsers);
                                request.setUserInfoCustomizeRefCj(userInfoCustomizeRefCj);
                                request.setUserInfoCustomize(userInfoCustomize);
                                request.setSpreadsUsersSeller(spreadsUsersSeller);
                                request.setUserInfoCustomizeSeller(userInfoCustomizeSeller);
                                request.setEmployeeCustomizeResponse(employeeCustomizeResponse);
                                request.setNowTime(GetDate.getNowTime10());
                                request.setCreditTenderLogs(creditTenderLogVOs);
                                request.setBorrowCreditList(borrowCreditList);


                                boolean tenderFlag = this.bankCreditTenderClient.updateTenderCreditInfo(request);

                                if (tenderFlag){

                                    AppChannelStatisticsDetailVO channelDetail
                                            = this.amMongoClient.getAppChannelStatisticsDetailByUserId(request.getUserId());
                                    if (Validator.isNotNull(channelDetail)){
                                        Map<String, Object> params = new HashMap<String, Object>();
                                        // 认购本金
                                        params.put("accountDecimal", creditTenderLog.getAssignCapital());
                                        // 投资时间
                                        params.put("investTime", request.getNowTime());
                                        // 项目类型
                                        params.put("projectType", "汇转让");
                                        // 首次投标项目期限
                                        String investProjectPeriod = request.getBorrowCreditList().get(0).getCreditTerm() + "天";
                                        params.put("investProjectPeriod", investProjectPeriod);
                                        params.put("investFlag",investUser.getInvestflag());
                                        params.put("isFirst",0);
                                        params.put("fromBusiness","credit");
                                        //推送mq
                                        this.appChannelStatisticsProducer.messageSend(new Producer.MassageContent(MQConstant.APP_CHANNEL_STATISTICS_DETAIL_TOPIC,JSON.toJSONBytes(params)));
                                    }else{
                                        UtmRegVO utmRegVO=this.amUserClient.findUtmRegByUserId(userId);
                                        if (Validator.isNotNull(utmRegVO)){
                                            Map<String, Object> params = new HashMap<String, Object>();
                                            params.put("id", utmRegVO.getId());
                                            params.put("accountDecimal", creditTenderLog.getAssignCapital());
                                            // 投资时间
                                            params.put("investTime", request.getNowTime());
                                            // 项目类型
                                            params.put("projectType", "汇转让");
                                            // 首次投标项目期限
                                            String investProjectPeriod = request.getBorrowCreditList().get(0).getCreditTerm() + "天";
                                            // 首次投标项目期限
                                            params.put("investProjectPeriod", investProjectPeriod);
                                            //首次投标标志位
                                            params.put("isFirst",1);
                                            params.put("fromBusiness","credit");
                                            if (investUser.getInvestflag() == 0){
                                                this.appChannelStatisticsProducer.messageSend(new Producer.MassageContent(MQConstant.APP_CHANNEL_STATISTICS_DETAIL_TOPIC,JSON.toJSONBytes(params)));
                                            }
                                        }
                                    }

                                    // 更新新手标志位
                                    investUser.setInvestflag(1);
                                    boolean userFlag=this.amUserClient.updateByPrimaryKeySelective(investUser);
                                    if (!userFlag) {
                                        logger.info("更新相应的用户新手标志位失败!" + "[用户userId：" + userId + "]");
                                    }

                                    // 查询相应的承接记录，如果相应的承接记录存在，则承接成功
                                    CreditTenderVO creditTender = this.bankCreditTenderClient.selectByAssignNidAndUserId(logOrderId, userId);
                                    // 发送法大大PDF处理MQ start
                                    this.sendFDDToMQ(userId,creditTender);
                                    // 发送法大大PDF处理MQ end
                                    logger.info("债转投资异常修复成功:承接订单号=" + creditTender.getAssignNid());
                                }else{
                                    continue;
                                }
                            }else{
                                continue;
                            }
                        }catch (Exception e){
                            continue;
                        }
                    }else {
                        continue;
                    }

                }
            }
        }

    }


    /**
     * 网站收支明细记录
     * @param userId
     * @return
     */
    private EmployeeCustomizeResponse getEmployeeCustomize(Integer userId,SpreadsUserVO spreadsUsers){
        EmployeeCustomizeResponse response = new EmployeeCustomizeResponse();
        UserInfoVO usersInfo = this.amUserClient.findUsersInfoById(userId);
        if(usersInfo!=null){
            Integer attribute = usersInfo.getAttribute();
            if (attribute != null) {
                UserVO users =this.amUserClient.findUserById(userId);
                Integer refUserId = spreadsUsers.getSpreadsUserId();
                if (users != null && (attribute == 2 || attribute == 3)) {
                    EmployeeCustomizeVO employeeCustomize =this.amUserClient.selectEmployeeByUserId(userId);
                    response.setResult(employeeCustomize);
                }else if (users != null && (attribute == 1 || attribute == 0)){
                    EmployeeCustomizeVO employeeCustomize = this.amUserClient.selectEmployeeByUserId(refUserId);
                    response.setResult(employeeCustomize);
                }
            }

            response.setTruename(usersInfo.getTruename());
        }
        return response;
    }


    /**
     * 发送法大大PDF处理MQ
     */
    private void sendFDDToMQ(Integer userId,CreditTenderVO creditTender){
        JSONObject params = new JSONObject();
        params.put("userId", userId);
        params.put("bidNid", creditTender.getBidNid());
        params.put("assignNid", creditTender.getAssignNid());
        params.put("creditNid",creditTender.getCreditNid());
        params.put("creditTenderNid",creditTender.getCreditTenderNid());
        try {
            fddProducer.messageSend(new Producer.MassageContent(MQConstant.FDD_CONTRACT_TOPIC, params));
        } catch (MQException e) {
            e.printStackTrace();
            logger.error("法大大发送消息失败...", e);
        }
    }


    /**
     * 调用江西银行购买债券查询接口
     * @param assignOrderId
     * @param userId
     * @return
     */
        private BankCallBean creditInvestQuery(String assignOrderId, Integer userId) {
        // 承接人用户Id
        BankOpenAccountVO tenderOpenAccount = this.bankCreditTenderClient.getBankOpenAccount(userId);
        BankCallBean bean = new BankCallBean();
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallMethodConstant.TXCODE_CREDIT_INVEST_QUERY);
        bean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
        bean.setTxTime(GetOrderIdUtils.getTxTime());// 交易时间
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号6位
        bean.setChannel(BankCallConstant.CHANNEL_PC);// 交易渠道
        bean.setAccountId(tenderOpenAccount.getAccount());// 存管平台分配的账号
        bean.setOrgOrderId(assignOrderId);// 原购买债权订单号
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        bean.setLogUserId(String.valueOf(userId));
        return BankCallUtils.callApiBg(bean);
    }



}
