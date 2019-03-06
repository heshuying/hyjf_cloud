/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.SubCommissionService;
import com.hyjf.am.resquest.admin.SubCommissionRequest;
import com.hyjf.am.vo.admin.SubCommissionListConfigVO;
import com.hyjf.am.vo.admin.SubCommissionVO;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.user.EmployeeCustomizeVO;
import com.hyjf.am.vo.user.SpreadsUserVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author: sunpeikai
 * @version: SubCommissionServiceImpl, v0.1 2018/7/10 9:46
 */
@Service
public class SubCommissionServiceImpl extends BaseAdminServiceImpl implements SubCommissionService {

    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private CommonProducer commonProducer;

    /**
     * 发起账户分佣所需的detail信息
     * @auth sunpeikai
     * @return
     */
    @Override
    public JSONObject searchDetails(Integer loginUserId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountId",systemConfig.BANK_MERS_ACCOUNT);
        List<SubCommissionListConfigVO> subCommissionListConfigVOList = amTradeClient.searchSubCommissionListConfig();
        jsonObject.put("userNameList",subCommissionListConfigVOList);
        BigDecimal balance = getBankBalance(loginUserId,systemConfig.BANK_MERS_ACCOUNT);
        jsonObject.put("balance",balance);
        return jsonObject;
    }

    /**
     * 平台账户分佣
     * @auth sunpeikai
     * @param loginUserId 当前登录用户id
     * @param request 插入数据参数
     * @return
     */
    @Override
    public JSONObject subCommission(Integer loginUserId, SubCommissionRequest request) {
        JSONObject jsonObject = new JSONObject();
        //当前登录用户信息
        AdminSystemVO adminSystemVO = amConfigClient.getUserInfoById(loginUserId);
        // 余额
        //BigDecimal balance = getBankBalance(loginUserId,HYJF_BANK_MERS_ACCOUNT);
        BigDecimal balance = getBankBalance(loginUserId,request.getAccountId());
        request.setBalance(balance.toString());
        //根据id获取用户信息
        UserVO userVO = amUserClient.searchUserByUserId(request.getReceiveUserId());
        if(userVO != null){
            request.setReceiveUserName(userVO.getUsername());
        }

        //校验参数
        this.checkParam(request);
        // 调用江西银行接口分佣
        /*        try {*/
        BankCallBean bean = new BankCallBean();
        String channel = BankCallConstant.CHANNEL_PC;
        String orderDate = GetOrderIdUtils.getOrderDate();
        String txDate = GetOrderIdUtils.getTxDate();
        String txTime = GetOrderIdUtils.getTxTime();
        String seqNo = GetOrderIdUtils.getSeqNo(6);
        // 版本号
        bean.setVersion(BankCallConstant.VERSION_10);
        // 交易代码
        bean.setTxCode(BankCallConstant.TXCODE_FEE_SHARE);
        bean.setTxDate(txDate);
        bean.setTxTime(txTime);
        bean.setSeqNo(seqNo);
        bean.setChannel(channel);
        // 电子账号
        bean.setAccountId(request.getAccountId());
        // 币种
        bean.setCurrency(BankCallConstant.CURRENCY_TYPE_RMB);
        // 分账金额
        bean.setTxAmount(CustomUtil.formatAmount(request.getTxAmount()));
        // 对手电子账号
        bean.setForAccountId(request.getReceiveAccountId());
        bean.setDescription("手续费分账");
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(loginUserId));
        bean.setLogUserId(String.valueOf(loginUserId));
        bean.setLogOrderDate(orderDate);
        bean.setLogRemark("手续费分账");
        // 请求前插入记录表
        boolean insertFlag = this.insertSubCommissionLog(bean, request, adminSystemVO);
        if (insertFlag) {
            // 调用银行接口
            BankCallBean resultBean = BankCallUtils.callApiBg(bean);

            String retCode = resultBean.getRetCode() == null ? "" : resultBean.getRetCode();
            logger.info("处理前retCode:[{}],处理后retCode:[{}]",resultBean.getRetCode(),retCode);
            // 银行返回错误信息
            if(StringUtils.isNotBlank(retCode) && !"null".equals(retCode)){
                String errorMsg = amConfigClient.getBankRetMsg(resultBean.getRetCode() == null ? "" : resultBean.getRetCode());
                request.setErrorMsg(errorMsg);
            }
            request.setResultBean(CommonUtils.convertBean(resultBean,BankCallBeanVO.class));
            request.setAdminSystemVO(adminSystemVO);

            if (resultBean == null) {
                logger.info("调用银行接口失败,银行返回空.订单号:[" + bean.getLogOrderId() + "].");
                request.setCallBankSuccess(false);
                jsonObject = amTradeClient.subCommission(request);
                CheckUtil.check(false,MsgEnum.ERR_BANK_CALL);
            }
            // 银行返回响应代码
            if("CA51".equals(retCode)){
                // 更新订单状态:失败
                request.setCallBankSuccess(false);
                jsonObject = amTradeClient.subCommission(request);
                // 转账成功，更新状态失败
                CheckUtil.check(false,MsgEnum.ERR_AMT_NO_MONEY);
            }
            // 调用银行接口失败
            if (!BankCallStatusConstant.RESPCODE_SUCCESS.equals(retCode)) {
                // 更新订单状态:失败
                request.setCallBankSuccess(false);
                jsonObject = amTradeClient.subCommission(request);
                // 转账成功，更新状态失败
                CheckUtil.check(false,MsgEnum.ERR_BANK_CALL);
            }
            // 银行返回成功
            // 更新订单,用户账户等信息

            Integer nowTime = GetDate.getNowTime10();
            // 转账订单号
            String orderId = resultBean.getLogOrderId();
            // 转入用户ID
            Integer receiveUserId = request.getReceiveUserId();
            // 交易金额
            String txAmount = resultBean.getTxAmount();

            // 查询交易记录
            AccountWebListVO accountWebListVO = new AccountWebListVO();
            accountWebListVO.setOrdid(orderId);
            boolean isExistFlag = csMessageClient.queryAccountWebList(accountWebListVO).getResultList().size()>0;
            if (isExistFlag) {
                logger.info("重复转账:转账订单号:[" + orderId + "].");
                throw new RuntimeException("重复转账:转账订单号:[" + orderId + "].");
            }

            // 插入网站收支明细记录
            AccountWebListVO accountWebList = new AccountWebListVO();
            // 订单号
            accountWebList.setOrdid(orderId);
            // 出借编号
            accountWebList.setBorrowNid("");
            accountWebList.setUserId(receiveUserId);
            // 管理费
            accountWebList.setAmount(Double.valueOf(txAmount));
            // 类型1收入,2支出
            accountWebList.setType(CustomConstants.TYPE_OUT);
            // 管理费
            accountWebList.setTrade("fee_share_out");
            // 还款服务费
            accountWebList.setTradeType("手续费分佣");
            // 备注
            accountWebList.setRemark(request.getRemark());
            accountWebList.setCreateTime(nowTime);
            accountWebList.setOperator(adminSystemVO.getUsername());
            int webListCount = csMessageClient.queryAccountWebList(accountWebList).getResultList().size();
            if (webListCount == 0) {
                UserInfoVO userInfo = amUserClient.findUsersInfoById(receiveUserId);
                if (userInfo != null) {
                    Integer attribute = userInfo.getAttribute();
                    if (attribute != null) {
                        // 查找用户的的推荐人
                        SpreadsUserVO spreadsUserVO = amUserClient.searchSpreadsUserByUserId(receiveUserId);
                        Integer refUserId = (spreadsUserVO == null || spreadsUserVO.getSpreadsUserId() == null)?null:spreadsUserVO.getSpreadsUserId();
                        UserVO user = amUserClient.findUserById(receiveUserId);


                        // 如果是线上员工或线下员工，推荐人的userId和username不插
                        if (user != null && (attribute == 2 || attribute == 3)) {
                            // 查找用户信息
                            EmployeeCustomizeVO employeeCustomizeVO = amUserClient.searchEmployeeBuUserId(receiveUserId);
                            if (employeeCustomizeVO != null) {
                                accountWebList.setRegionName(employeeCustomizeVO.getRegionName());
                                accountWebList.setBranchName(employeeCustomizeVO.getBranchName());
                                accountWebList.setDepartmentName(employeeCustomizeVO.getDepartmentName());
                            }
                        }
                        // 如果是无主单，全插
                        else if (user != null && (attribute == 1)) {
                            // 查找用户推荐人
                            EmployeeCustomizeVO employeeCustomizeVO = amUserClient.searchEmployeeBuUserId(refUserId);
                            if (employeeCustomizeVO != null) {
                                accountWebList.setRegionName(employeeCustomizeVO.getRegionName());
                                accountWebList.setBranchName(employeeCustomizeVO.getBranchName());
                                accountWebList.setDepartmentName(employeeCustomizeVO.getDepartmentName());
                            }
                        }
                        // 如果是有主单
                        else if (user != null && (attribute == 0)) {
                            // 查找用户推荐人
                            EmployeeCustomizeVO employeeCustomizeVO = amUserClient.searchEmployeeBuUserId(refUserId);
                            if (employeeCustomizeVO != null) {
                                accountWebList.setRegionName(employeeCustomizeVO.getRegionName());
                                accountWebList.setBranchName(employeeCustomizeVO.getBranchName());
                                accountWebList.setDepartmentName(employeeCustomizeVO.getDepartmentName());
                            }
                        }
                    }
                    accountWebList.setTruename(userInfo.getTruename());
                    accountWebList.setFlag(1);
                }
                //boolean accountWebListFlag = csMessageClient.insertAccountWebList(accountWebList) > 0;
                try {
                    // 发mq 插入accountWebList
                    commonProducer.messageSend(new MessageContent(MQConstant.ACCOUNT_WEB_LIST_TOPIC, UUID.randomUUID().toString(), accountWebListVO));
                } catch (MQException e) {
                    logger.error(e.getMessage());
                    throw new RuntimeException("发送MQ(ht_account_web_list)失败！" + "[订单号：" + orderId + "]");
                }
            } else {
                throw new RuntimeException("网站收支记录(ht_account_web_list)已存在!" + "[出借订单号：" + orderId + "]");
            }

            request.setCallBankSuccess(true);
            jsonObject = amTradeClient.subCommission(request);

            boolean updateFlag = jsonObject.getBoolean("isUpdate");
            if (!updateFlag) {
                logger.info("调用银行成功后,更新数据失败");
                // 转账成功，更新状态失败
                CheckUtil.check(false,MsgEnum.ERR_BANK_UPDATE_AFTER_CALL);
                return jsonObject;
            }
        }
/*        } catch (Exception e) {
            logger.info("转账发生异常:异常信息:[" + e.getMessage() + "].");
            logger.info("转账发生异常:" + e);
            CheckUtil.check(false,MsgEnum.ERR_AMT_TRANSFER);
            return jsonObject;
        }*/
        return jsonObject;
    }

    /**
     * 根据筛选条件查询分佣数据count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public Integer getSubCommissionCount(SubCommissionRequest request) {
        return amTradeClient.getSubCommissionCount(request);
    }

    /**
     * 根据筛选条件查询分佣数据list
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @Override
    public List<SubCommissionVO> searchSubCommissionList(SubCommissionRequest request) {
        return amTradeClient.searchSubCommissionList(request);
    }

    /**
     * 根据nameClass获取数据字典表的下拉列表
     *
     * @param
     * @return
     * @auth sunpeikai
     */
    @Override
    public List<ParamNameVO> searchParamNameList(String nameClass) {
        return amConfigClient.getParamNameList(nameClass);
    }
    /**
     * 查询账户可用余额
     * @auth sunpeikai
     * @param
     * @return
     */
    public BigDecimal getBankBalance(Integer userId, String accountId) {
        // 账户可用余额
        BigDecimal balance = BigDecimal.ZERO;
        BankCallBean bean = new BankCallBean();
        // 版本号
        bean.setVersion(BankCallConstant.VERSION_10);
        // 交易代码
        bean.setTxCode(BankCallMethodConstant.TXCODE_BALANCE_QUERY);
        // 交易日期
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        // 交易时间
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        // 交易流水号
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        // 交易渠道
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        // 电子账号
        bean.setAccountId(accountId);
        // 订单号
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        // 订单时间(必须)格式为yyyyMMdd，例如：20130307
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        bean.setLogUserId(String.valueOf(userId));
        // 平台
        bean.setLogClient(0);
        try {
            BankCallBean resultBean = BankCallUtils.callApiBg(bean);
            if (resultBean != null && BankCallStatusConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
                balance = new BigDecimal(resultBean.getAvailBal().replace(",", ""));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return balance;
    }
    /**
     * 校验参数
     * @auth sunpeikai
     * @param
     * @return
     */
    private void checkParam(SubCommissionRequest request){
        CheckUtil.check(request.getAccountId()!=null, MsgEnum.ERR_OBJECT_REQUIRED,"电子账户号");
        CheckUtil.check(request.getReceiveUserId()!=null, MsgEnum.ERR_OBJECT_REQUIRED,"转入用户ID");
        CheckUtil.check(StringUtils.isNotEmpty(request.getReceiveUserName()), MsgEnum.ERR_OBJECT_REQUIRED,"转入用户名");
        CheckUtil.check(StringUtils.isNotEmpty(request.getPassword()), MsgEnum.ERR_OBJECT_REQUIRED,"交易密码");
        CheckUtil.check(StringUtils.isNotEmpty(request.getTxAmount()), MsgEnum.ERR_OBJECT_REQUIRED,"分佣金额");
        //验证余额是否充足
        CheckUtil.check(new BigDecimal(request.getBalance()).compareTo(new BigDecimal(request.getTxAmount())) > 0, MsgEnum.ERR_AMT_NO_MONEY);
        // 验证交易密码正确性
        logger.info("交易密码 => 配置文件中:[{}],参数MD5加密:[{}],参数不加密:[{}]",systemConfig.SUB_COMMISSION_PASSWORD,MD5Utils.MD5(request.getPassword()),request.getPassword());
        CheckUtil.check(systemConfig.SUB_COMMISSION_PASSWORD.equals(MD5Utils.MD5(request.getPassword())),MsgEnum.ERR_TRADE_PASSWORD);
        //验证remark长度
        CheckUtil.check(request.getRemark().length()<50,MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"说明");
    }

    private boolean insertSubCommissionLog(BankCallBean bean, SubCommissionRequest form, AdminSystemVO adminSystemVO) {
        // 当前时间
        Date nowTime = GetDate.getNowTime();
        SubCommissionVO subCommission = new SubCommissionVO();
        subCommission.setOrderId(bean.getLogOrderId());
        subCommission.setAccountId(bean.getAccountId());
        subCommission.setReceiveUserId(form.getReceiveUserId());
        subCommission.setReceiveUserName(form.getReceiveUserName());
        //新增转入姓名
        subCommission.setTruename(form.getTruename());
        subCommission.setReceiveAccountId(bean.getForAccountId());
        subCommission.setAccount(new BigDecimal(bean.getTxAmount()));
        subCommission.setTxDate(Integer.parseInt(bean.getTxDate()));
        subCommission.setTxTime(Integer.parseInt(bean.getTxTime()));
        subCommission.setSeqNo(bean.getSeqNo());
        subCommission.setTradeStatus(0);
        subCommission.setRemark(form.getRemark());
        subCommission.setCreateUserId(Integer.valueOf(adminSystemVO.getId()));
        subCommission.setCreateUserName(adminSystemVO.getUsername());
        subCommission.setCreateTime(nowTime);
        return amTradeClient.insertSubCommission(subCommission);
    }

}
