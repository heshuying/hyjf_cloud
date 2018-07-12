/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.service.SubCommissionService;
import com.hyjf.am.resquest.admin.SubCommissionRequest;
import com.hyjf.am.vo.admin.SubCommissionListConfigVO;
import com.hyjf.am.vo.admin.SubCommissionVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.EmployeeCustomizeVO;
import com.hyjf.am.vo.user.SpreadsUserVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: SubCommissionServiceImpl, v0.1 2018/7/10 9:46
 */
@Service
public class SubCommissionServiceImpl extends BaseAdminServiceImpl implements SubCommissionService {

    @Value("${hyjf.bank.mers.account}")
    private String HYJF_BANK_MERS_ACCOUNT;

    @Value("${hyjf.sub.commission.password}")
    private String SUB_COMMISSION_PASSWORD;

    /**
     * 发起账户分佣所需的detail信息
     * @auth sunpeikai
     * @return
     */
    @Override
    public JSONObject searchDetails(Integer loginUserId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountId",HYJF_BANK_MERS_ACCOUNT);
        List<SubCommissionListConfigVO> subCommissionListConfigVOList = amTradeClient.searchSubCommissionListConfig();
        jsonObject.put("userNameList",subCommissionListConfigVOList);
        BigDecimal balance = getBankBalance(loginUserId,HYJF_BANK_MERS_ACCOUNT);
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
    @Transactional(rollbackFor = Exception.class)
    public JSONObject subCommission(Integer loginUserId, SubCommissionRequest request) {
        JSONObject jsonObject = new JSONObject();
        //当前登录用户信息
        AdminSystemVO adminSystemVO = amConfigClient.getUserInfoById(loginUserId);
        // 余额
        BigDecimal balance = getBankBalance(loginUserId,HYJF_BANK_MERS_ACCOUNT);
        request.setBalance(balance.toString());
        //根据id获取用户信息
        UserVO userVO = amUserClient.searchUserByUserId(request.getReceiveUserId());
        if(userVO != null){
            request.setReceiveUserName(userVO.getUsername());
        }
        //校验参数
        this.checkParam(request);
        // 调用江西银行接口分佣
        try {
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
                if (resultBean == null) {
                    logger.info("调用银行接口失败,银行返回空.订单号:[" + bean.getLogOrderId() + "].");
                    this.updateSubCommission(bean,adminSystemVO);
                    CheckUtil.check(false,MsgEnum.ERR_BANK_CALL);
                    jsonObject.put("status","1");
                    jsonObject.put("result","调用银行接口失败");
                    return jsonObject;
                }
                // 银行返回响应代码
                String retCode = resultBean == null ? "" : resultBean.getRetCode();
                if("CA51".equals(retCode)){
                    // 更新订单状态:失败
                    this.updateSubCommission(resultBean,adminSystemVO);
                    // 转账成功，更新状态失败
                    CheckUtil.check(false,MsgEnum.ERR_AMT_NO_MONEY);
                    jsonObject.put("status","1");
                    jsonObject.put("result","账户余额不足");
                    return jsonObject;
                }
                // 调用银行接口失败
                if (!BankCallStatusConstant.RESPCODE_SUCCESS.equals(retCode)) {
                    // 更新订单状态:失败
                    this.updateSubCommission(resultBean,adminSystemVO);
                    // 转账成功，更新状态失败
                    CheckUtil.check(false,MsgEnum.ERR_BANK_CALL);
                    jsonObject.put("status","1");
                    jsonObject.put("result","调用银行接口失败");
                    return jsonObject;
                }
                // 银行返回成功
                // 更新订单,用户账户等信息
                boolean updateFlag = this.updateSubCommissionSuccess(resultBean, request,adminSystemVO);
                if (!updateFlag) {
                    logger.info("调用银行成功后,更新数据失败");
                    // 转账成功，更新状态失败
                    CheckUtil.check(false,MsgEnum.ERR_BANK_UPDATE_AFTER_CALL);
                    return jsonObject;
                }
            }
        } catch (Exception e) {
            logger.info("转账发生异常:异常信息:[" + e.getMessage() + "].");
            CheckUtil.check(false,MsgEnum.ERR_AMT_TRANSFER);
            return jsonObject;
        }
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
    @Transactional(rollbackFor = Exception.class)
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
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(Integer.valueOf(userId)));
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
            e.printStackTrace();
        }

        return balance;
    }
    /**
     * 校验参数
     * @auth sunpeikai
     * @param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void checkParam(SubCommissionRequest request){
        CheckUtil.check(request.getAccountId()!=null, MsgEnum.ERR_OBJECT_REQUIRED,"电子账户号");
        CheckUtil.check(request.getReceiveUserId()!=null, MsgEnum.ERR_OBJECT_REQUIRED,"转入用户ID");
        CheckUtil.check(StringUtils.isNotEmpty(request.getReceiveUserName()), MsgEnum.ERR_OBJECT_REQUIRED,"转入用户名");
        CheckUtil.check(StringUtils.isNotEmpty(request.getPassword()), MsgEnum.ERR_OBJECT_REQUIRED,"交易密码");
        CheckUtil.check(StringUtils.isNotEmpty(request.getTxAmount()), MsgEnum.ERR_OBJECT_REQUIRED,"分佣金额");
        //验证余额是否充足
        CheckUtil.check(new BigDecimal(request.getBalance()).compareTo(new BigDecimal(request.getTxAmount())) > 0, MsgEnum.ERR_AMT_NO_MONEY);
        // 验证交易密码正确性
        CheckUtil.check(SUB_COMMISSION_PASSWORD.equals(MD5Utils.MD5(request.getPassword())),MsgEnum.ERR_TRADE_PASSWORD);
        //验证remark长度
        CheckUtil.check(request.getRemark().length()<50,MsgEnum.ERR_OBJECT_EXCEED_LIMIT,"说明");
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean insertSubCommissionLog(BankCallBean bean, SubCommissionRequest form, AdminSystemVO adminSystemVO) {
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

    /**
     * 更新失败订单状态
     *
     * @param bean
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateSubCommission(BankCallBean bean,AdminSystemVO adminSystemVO) {
        Date nowTime = GetDate.getNowTime();
        SubCommissionVO subCommission = amTradeClient.searchSubCommissionByOrderId(bean.getLogOrderId());
        if (subCommission != null) {
            // 银行返回错误信息
            String errorMsg = amConfigClient.getBankRetMsg(bean.getRetCode() == null ? "" : bean.getRetCode());
            subCommission.setTradeStatus(2);// 失败
            subCommission.setErrorMsg(errorMsg);
            subCommission.setUpdateTime(nowTime);
            subCommission.setUpdateUserId(Integer.parseInt(adminSystemVO.getId()));
            subCommission.setUpdateUserName(adminSystemVO.getUsername());
            amTradeClient.updateSubCommission(subCommission);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateSubCommissionSuccess(BankCallBean resultBean, SubCommissionRequest request,AdminSystemVO adminSystemVO) {
        Integer nowTime = GetDate.getNowTime10();
        Date date = new Date();
        // 转账订单号
        String orderId = resultBean.getLogOrderId();
        // 转入用户ID
        Integer receiveUserId = request.getReceiveUserId();
        // 交易金额
        String txAmount = resultBean.getTxAmount();
        // 交易日期
        String txDate = resultBean.getTxDate();
        // 交易时间
        String txTime = resultBean.getTxTime();
        // 交易流水号
        String seqNo = resultBean.getSeqNo();
        // 更新订单状态
        SubCommissionVO subCommission = amTradeClient.searchSubCommissionByOrderId(orderId);
        subCommission.setTradeStatus(1);// 成功
        subCommission.setUpdateTime(date);
        subCommission.setUpdateUserId(Integer.parseInt(adminSystemVO.getId()));
        subCommission.setUpdateUserName(adminSystemVO.getUsername());

        if (request.getReceiveAccountId()!=null) {
            subCommission.setReceiveAccountId(request.getReceiveAccountId());
        }

        boolean updateFlag = amTradeClient.updateSubCommission(subCommission)>0;
        if (!updateFlag) {
            logger.info("更新分账记录表失败,订单号:[" + resultBean.getLogOrderId() + "].");
            throw new RuntimeException("更新分账记录表失败,订单号:[" + resultBean.getLogOrderId() + "].");
        }
        // 查询交易记录
        boolean isExistFlag = amTradeClient.accountWebListByOrderId(orderId)>0;
        if (isExistFlag) {
            logger.info("重复转账:转账订单号:[" + orderId + "].");
            throw new RuntimeException("重复转账:转账订单号:[" + orderId + "].");
        }

        // 更新转入用户账户信息
        AccountVO receiveUserAccount = new AccountVO();
        receiveUserAccount.setUserId(receiveUserId);
        receiveUserAccount.setBankTotal(new BigDecimal(txAmount));
        receiveUserAccount.setBankBalance(new BigDecimal(txAmount));
        boolean isUpdateFlag = amTradeClient.updateAccount(receiveUserAccount) > 0;
        if (!isUpdateFlag) {
            logger.info("更新转入用户的账户信息失败,用户ID:[" + receiveUserId + "].订单号:[" + orderId + "].");
            throw new RuntimeException("更新转入用户的账户信息失败,用户ID:[" + receiveUserId + "].订单号:[" + orderId + "].");
        }
        // 插入交易明细
        List<AccountVO> accountVOList = amTradeClient.searchAccountByUserId(receiveUserId);
        if(null != accountVOList && accountVOList.size() == 1){
            receiveUserAccount = accountVOList.get(0);
        }


        AccountListVO receiveUserList = new AccountListVO();
        receiveUserList.setNid(orderId); // 订单号
        receiveUserList.setUserId(receiveUserId); // 转入人用户ID
        receiveUserList.setAmount(new BigDecimal(txAmount)); // 操作金额
        /** 银行相关 */
        receiveUserList.setAccountId(resultBean.getForAccountId());
        receiveUserList.setBankAwait(receiveUserAccount.getBankAwait());
        receiveUserList.setBankAwaitCapital(receiveUserAccount.getBankAwaitCapital());
        receiveUserList.setBankAwaitInterest(receiveUserAccount.getBankAwaitInterest());
        receiveUserList.setBankBalance(receiveUserAccount.getBankBalance());
        receiveUserList.setBankFrost(receiveUserAccount.getBankFrost());
        receiveUserList.setBankInterestSum(receiveUserAccount.getBankInterestSum());
        receiveUserList.setBankInvestSum(receiveUserAccount.getBankInvestSum());
        receiveUserList.setBankTotal(receiveUserAccount.getBankTotal());
        receiveUserList.setBankWaitCapital(receiveUserAccount.getBankWaitCapital());
        receiveUserList.setBankWaitInterest(receiveUserAccount.getBankWaitInterest());
        receiveUserList.setBankWaitRepay(receiveUserAccount.getBankWaitRepay());
        receiveUserList.setCheckStatus(0);
        receiveUserList.setTradeStatus(1);// 交易状态 0:失败 1:成功
        receiveUserList.setIsBank(1);
        receiveUserList.setTxDate(Integer.parseInt(txDate));
        receiveUserList.setTxTime(Integer.parseInt(txTime));
        receiveUserList.setSeqNo(seqNo);
        receiveUserList.setBankSeqNo(txDate + txTime + seqNo);
        /** 非银行相关 */
        receiveUserList.setType(1); // 1收入
        receiveUserList.setTrade("fee_share_in"); // 手续费分账转入
        receiveUserList.setTradeCode("balance"); // 余额操作
        receiveUserList.setTotal(receiveUserAccount.getTotal()); // 投资人资金总额
        receiveUserList.setBalance(receiveUserAccount.getBalance()); // 投资人可用金额
        receiveUserList.setPlanFrost(receiveUserAccount.getPlanFrost());// 汇添金冻结金额
        receiveUserList.setPlanBalance(receiveUserAccount.getPlanBalance());// 汇添金可用金额
        receiveUserList.setFrost(receiveUserAccount.getFrost()); // 投资人冻结金额
        receiveUserList.setAwait(receiveUserAccount.getAwait()); // 投资人待收金额
        receiveUserList.setCreateTime(nowTime); // 创建时间
        receiveUserList.setBaseUpdate(nowTime); // 更新时间
        receiveUserList.setOperator(CustomConstants.OPERATOR_AUTO_REPAY); // 操作者
        receiveUserList.setRemark("账户分佣");
        receiveUserList.setIp(""); // 操作IP
        receiveUserList.setIsUpdate(0);
        receiveUserList.setBaseUpdate(0);
        receiveUserList.setInterest(BigDecimal.ZERO); // 利息
        receiveUserList.setWeb(0); // PC
        boolean receiveUserListFlag = amTradeClient.insertAccountList(receiveUserList) > 0;
        if (!receiveUserListFlag) {
            logger.info("插入转入用户交易记录失败,用户ID:[" + receiveUserId + "],订单号:[" + orderId + "].");
            throw new RuntimeException("插入转出用户交易记录失败,用户ID:[" + receiveUserId + "],订单号:[" + orderId + "].");
        }
        // 插入网站收支明细记录
        AccountWebListVO accountWebList = new AccountWebListVO();
        accountWebList.setOrdid(orderId);// 订单号
        accountWebList.setBorrowNid(""); // 投资编号
        accountWebList.setUserId(receiveUserId); //
        accountWebList.setAmount(new BigDecimal(txAmount)); // 管理费
        accountWebList.setType(CustomConstants.TYPE_OUT); // 类型1收入,2支出
        accountWebList.setTrade("fee_share_out"); // 管理费
        accountWebList.setTradeType("手续费分佣"); // 账户管理费
        accountWebList.setRemark(request.getRemark()); // 备注
        accountWebList.setCreateTime(nowTime);
        accountWebList.setOperator(adminSystemVO.getUsername());
        int webListCount = amTradeClient.accountWebListByOrderId(accountWebList.getOrdid());
        if (webListCount == 0) {
            UserInfoVO userInfo = amUserClient.findUsersInfoById(receiveUserId);
            if (userInfo != null) {
                Integer attribute = userInfo.getAttribute();
                if (attribute != null) {
                    // 查找用户的的推荐人
                    SpreadsUserVO spreadsUserVO = amUserClient.searchSpreadsUserByUserId(receiveUserId);
                    UserVO userVO = amUserClient.findUserById(receiveUserId);
                    Integer refUserId = spreadsUserVO.getSpreadsUserId();

                    // 如果是线上员工或线下员工，推荐人的userId和username不插
                    if (userVO != null && (attribute == 2 || attribute == 3)) {
                        // 查找用户信息
                        EmployeeCustomizeVO employeeCustomizeVO = amUserClient.searchEmployeeBuUserId(receiveUserId);
                        if (employeeCustomizeVO != null) {
                            accountWebList.setRegionName(employeeCustomizeVO.getRegionName());
                            accountWebList.setBranchName(employeeCustomizeVO.getBranchName());
                            accountWebList.setDepartmentName(employeeCustomizeVO.getDepartmentName());
                        }
                    }
                    // 如果是无主单，全插
                    else if (userVO != null && (attribute == 1)) {
                        // 查找用户推荐人
                        EmployeeCustomizeVO employeeCustomizeVO = amUserClient.searchEmployeeBuUserId(receiveUserId);
                        if (employeeCustomizeVO != null) {
                            accountWebList.setRegionName(employeeCustomizeVO.getRegionName());
                            accountWebList.setBranchName(employeeCustomizeVO.getBranchName());
                            accountWebList.setDepartmentName(employeeCustomizeVO.getDepartmentName());
                        }
                    }
                    // 如果是有主单
                    else if (userVO != null && (attribute == 0)) {
                        // 查找用户推荐人
                        EmployeeCustomizeVO employeeCustomizeVO = amUserClient.searchEmployeeBuUserId(receiveUserId);
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
            boolean accountWebListFlag = amTradeClient.insertAccountWebList(accountWebList) > 0;
            if (!accountWebListFlag) {
                throw new RuntimeException("网站收支记录(huiyingdai_account_web_list)更新失败！" + "[订单号：" + orderId + "]");
            }
        } else {
            throw new RuntimeException("网站收支记录(huiyingdai_account_web_list)已存在!" + "[投资订单号：" + orderId + "]");
        }
        return true;
    }
}
