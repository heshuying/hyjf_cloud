/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.PlatformTransferService;
import com.hyjf.am.resquest.admin.PlatformTransferListRequest;
import com.hyjf.am.resquest.admin.PlatformTransferRequest;
import com.hyjf.am.vo.admin.AccountRechargeVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;
import com.hyjf.am.vo.trade.account.BankMerchantAccountVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: PlatformTransferServiceImpl, v0.1 2018/7/9 10:29
 */
@Service
public class PlatformTransferServiceImpl extends BaseServiceImpl implements PlatformTransferService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    private AmConfigClient amConfigClient;

    @Value("${hyjf.handrecharge.password}")
    private String HYJF_HANDRECHARGE_PASSWORD;

    @Value("${hyjf.bank.merrp.account}")
    private String BANK_MERRP_ACCOUNT;

    // 充值状态:充值中
    private static final int RECHARGE_STATUS_WAIT = 1;
    // 充值状态:成功
    private static final int RECHARGE_STATUS_SUCCESS = 2;
    // 充值状态:失败
    private static final int RECHARGE_STATUS_FAIL = 3;



    /**
     * 根据筛选条件查询数据count
     * @auth sunpeikai
     * @param request
     * @return
     */
    @Override
    public Integer getPlatformTransferCount(PlatformTransferListRequest request) {
        return amTradeClient.getPlatformTransferCount(request);
    }

    /**
     * 根据筛选条件查询平台转账list
     * @auth sunpeikai
     * @param request
     * @return
     */
    @Override
    public List<AccountRechargeVO> searchPlatformTransferList(PlatformTransferListRequest request) {
        List<AccountRechargeVO> accountRechargeVOList = amTradeClient.searchPlatformTransferList(request);
        String userIds = "";
        for(AccountRechargeVO accountRechargeVO:accountRechargeVOList){
            if(null != accountRechargeVO.getUserId()){
                userIds += String.valueOf(accountRechargeVO.getUserId()) + ",";
            }
        }
        userIds = userIds.substring(0,userIds.lastIndexOf(","));
        logger.info("userIds=====[{}]",userIds);
        List<UserVO> userVOList = amUserClient.findUserListByUserIds(userIds);
        for(AccountRechargeVO accountRechargeVO:accountRechargeVOList){
            for(UserVO userVO:userVOList){
                if(null != userVO && null != userVO.getUserId() && accountRechargeVO.getUserId().equals(userVO.getUserId())){
                    accountRechargeVO.setMobile(userVO.getMobile());
                    userVOList.remove(userVO);
                    break;
                }
            }
        }
        return accountRechargeVOList;
    }

    /**
     * 根据userName检查是否可以平台转账
     * @auth sunpeikai
     * @param userName 用户名
     * @return
     */
    @Override
    public JSONObject checkTransfer(String userName) {
        JSONObject result = new JSONObject();
        List<UserVO> userVOList = amUserClient.searchUserByUsername(userName);
        if (userVOList != null && userVOList.size() == 1) {
            UserVO userVO = userVOList.get(0);
            BankOpenAccountVO bankOpenAccountVO = amUserClient.searchBankOpenAccount(userVO.getUserId());
            if (bankOpenAccountVO != null && !Validator.isNull(bankOpenAccountVO.getAccount())) {
                result.put("status","0");
            } else {
                result.put("info", "用户未开户，无法转账!");
            }
        } else {
            result.put("info", "未查询到正确的用户信息!");
        }
        return result;
    }

    /**
     * 执行平台转账
     * @auth sunpeikai
     * @param request 传参
     * @return
     */
    @Override
    public JSONObject handRecharge(Integer loginUserId,HttpServletRequest request, PlatformTransferRequest platformTransferRequest) {
        JSONObject result = new JSONObject();
        AdminSystemVO adminSystemVO = amConfigClient.getUserInfoById(loginUserId);
        String password = platformTransferRequest.getPassword();
        if (password != null && password.equals(HYJF_HANDRECHARGE_PASSWORD)) {

            UserInfoCustomizeVO userInfo = amUserClient.getUserInfoCustomizeByUserName(platformTransferRequest.getUserName());
            Integer userId = userInfo == null ? 0 : userInfo.getUserId();
            if (userId == 0) {
                result.put("status", "error");
                result.put("result", "该用户不存在");
                return result;
            }
            platformTransferRequest.setUserId(userId);

            BankOpenAccountVO bankOpenAccountVO = amUserClient.searchBankOpenAccount(userId);
            // 用户未开户时,返回错误信息
            if (bankOpenAccountVO == null || Validator.isNull(bankOpenAccountVO.getAccount())) {
                result.put("status", "error");
                result.put("result", "该用户未开户");
                return result;
            }

            // 查询商户子账户余额
            String merrpAccount = BANK_MERRP_ACCOUNT;
            BigDecimal bankBalance = this.getBankBalance(loginUserId, merrpAccount);

            if (bankBalance.compareTo(platformTransferRequest.getMoney()) <= 0) {
                result.put("status", "error");
                result.put("result", "红包账户余额不足,请先充值或向该子账户转账!");
                return result;
            }


            // IP地址
            String ip = CustomUtil.getIpAddr(request);
            String orderId = GetOrderIdUtils.getOrderId2(userId);

            BankCallBean bean = new BankCallBean();
            // 版本号
            bean.setVersion(BankCallConstant.VERSION_10);
            // 交易代码
            bean.setTxCode(BankCallMethodConstant.TXCODE_VOUCHER_PAY);
            // 交易日期
            bean.setTxDate(GetOrderIdUtils.getTxDate());
            // 交易时间
            bean.setTxTime(GetOrderIdUtils.getTxTime());
            // 交易流水号
            bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
            // 交易渠道
            bean.setChannel(BankCallConstant.CHANNEL_PC);
            // 电子账号
            bean.setAccountId(merrpAccount);
            bean.setTxAmount(platformTransferRequest.getMoney().toString());
            bean.setForAccountId(bankOpenAccountVO.getAccount());
            bean.setDesLineFlag("1");
            // 订单号
            bean.setLogOrderId(orderId);
            // 订单时间(必须)格式为yyyyMMdd，例如：20130307
            bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
            bean.setLogUserId(String.valueOf(userId));
            // 平台
            bean.setLogClient(0);
            bean.setLogIp(ip);

            BankCallBean resultBean;
            try {
                resultBean = BankCallUtils.callApiBg(bean);
            } catch (Exception e) {
                e.printStackTrace();
                result.put("status", "error");
                result.put("result", "平台转账发生错误,请重新操作!");
                return result;
            }

            if (resultBean == null || !BankCallConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
                result.put("status", "error");
                result.put("result", "平台转账发生错误,请重新操作!");
                return result;
            }

            int cnt = 0;
            try {
                // 平台转账处理
                cnt = this.updateHandRechargeRecord(platformTransferRequest, resultBean, userInfo, bankOpenAccountVO.getAccount(),adminSystemVO.getUsername());
            } catch (Exception e) {
                logger.error(e.toString());
            }

            // 返现成功
            if (cnt > 0) {
                result.put("status", "success");
                result.put("success", "success");
                result.put("result", "平台转账操作成功!");
            } else {
                result.put("status", "error");
                result.put("result", "平台转账发生错误,请重新操作!");
            }
        } else {
            result.put("status", "error");
            result.put("result", "密码错误,请重新操作!");
        }

        return result;
    }

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
        try{
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
     * 手动充值处理
     * @param platformTransferRequest 请求参数
     * @param userInfo 自定义用户信息
     * @param accountId 银行账户
     * @return
     */
    public int updateHandRechargeRecord(PlatformTransferRequest platformTransferRequest, BankCallBean bankBean, UserInfoCustomizeVO userInfo, String accountId,String loginUserName) {
        int ret = 0;

        // 增加时间
        Date now = new Date();
        Integer time = GetDate.getMyTimeInMillis();
        // 商户userID
        // Integer merUserId = Integer.valueOf(ShiroUtil.getLoginUserId());
        // 客户userID
        Integer cusUserId = platformTransferRequest.getUserId();

        // 更新账户信息
        List<AccountVO> accountVOList = amTradeClient.searchAccountByUserId(cusUserId);
        AccountVO accountVO = new AccountVO();
        if(!CollectionUtils.isEmpty(accountVOList)){
            accountVO = accountVOList.get(0);
        }

        BigDecimal bankBalanceCash = accountVO.getBankBalanceCash() == null ? BigDecimal.ZERO : accountVO.getBankBalanceCash();
        // 充值金额
        BigDecimal money = platformTransferRequest.getMoney();
        accountVO.setBankBalance(accountVO.getBankBalance().add(money));
        // 累加到账户总资产
        accountVO.setBankTotal(accountVO.getBankTotal().add(money));
        accountVO.setBankBalanceCash(bankBalanceCash.add(money));
        ret += amTradeClient.updateAccount(accountVO);

        // 写入充值表
        AccountRechargeVO accountRechargeVO = new AccountRechargeVO();
        accountRechargeVO.setNid(bankBean.getLogOrderId());
        accountRechargeVO.setUserId(cusUserId);
        accountRechargeVO.setStatus(RECHARGE_STATUS_SUCCESS);
        accountRechargeVO.setMoney(money);
        accountRechargeVO.setBalance(accountVO.getBankBalance());
        accountRechargeVO.setFee(new BigDecimal(0));
        accountRechargeVO.setGateType("ADMIN");
        // 线下充值
        accountRechargeVO.setType(0);
        accountRechargeVO.setRemark(platformTransferRequest.getRemark());
        accountRechargeVO.setCreateTime(now);
        accountRechargeVO.setOperator(loginUserName);
        //accountRechargeVO.setAddtime(time.toString());
        accountRechargeVO.setAddIp(bankBean.getLogIp());
        accountRechargeVO.setUpdateTime(now);
        //accountRechargeVO.setNok(0);
        //accountRechargeVO.setDianfuFee(new BigDecimal(0));
        //accountRechargeVO.setIsok(0);
        // 0pc 1app
        accountRechargeVO.setClient(0);
        //accountRechargeVO.setIsok11(0);
        //accountRechargeVO.setFlag(0);
        //accountRechargeVO.setActivityFlag(0);
        accountRechargeVO.setUsername(userInfo.getUserName());
        // 资金托管平台 0:汇付,1:江西银行
        accountRechargeVO.setIsBank(1);
        // 交易日期
        accountRechargeVO.setTxDate(Integer.parseInt(bankBean.getTxDate()));
        // 交易时间
        accountRechargeVO.setTxTime(Integer.parseInt(bankBean.getTxTime()));
        // 交易流水号
        accountRechargeVO.setSeqNo(Integer.parseInt(bankBean.getSeqNo()));
        // 交易日期+交易时间+交易流水号
        accountRechargeVO.setBankSeqNo(bankBean.getTxDate() + bankBean.getTxTime() + bankBean.getSeqNo());
        // 电子账号
        accountRechargeVO.setAccountId(accountId);
        ret += amTradeClient.insertAccountRecharge(accountRechargeVO);


        // 写入收支明细
        AccountListVO accountListVO = new AccountListVO();
        accountListVO.setNid(bankBean.getLogOrderId());
        accountListVO.setSeqNo(bankBean.getSeqNo());
        accountListVO.setTxDate(Integer.parseInt(bankBean.getTxDate()));
        accountListVO.setTxTime(Integer.parseInt(bankBean.getTxTime()));
        accountListVO.setBankSeqNo(bankBean.getTxDate() + bankBean.getTxTime() + bankBean.getSeqNo());
        accountListVO.setCheckStatus(0);
        accountListVO.setTradeStatus(1);
        accountListVO.setUserId(platformTransferRequest.getUserId());
        accountListVO.setAccountId(accountId);
        accountListVO.setAmount(money);
        // 1收入2支出3冻结
        accountListVO.setType(1);
        accountListVO.setTrade("platform_transfer");
        accountListVO.setTradeCode("balance");
        // 银行总资产
        accountListVO.setBankTotal(accountVO.getBankTotal());
        // 银行可用余额
        accountListVO.setBankBalance(accountVO.getBankBalance());
        // 银行冻结金额
        accountListVO.setBankFrost(accountVO.getBankFrost());
        // 银行待还本金
        accountListVO.setBankWaitCapital(accountVO.getBankWaitCapital());
        // 银行待还利息
        accountListVO.setBankWaitInterest(accountVO.getBankWaitInterest());
        // 银行待收本金
        accountListVO.setBankAwaitCapital(accountVO.getBankAwaitCapital());
        // 银行待收利息
        accountListVO.setBankAwaitInterest(accountVO.getBankAwaitInterest());
        // 银行待收总额
        accountListVO.setBankAwait(accountVO.getBankAwait());
        // 银行累计收益
        accountListVO.setBankInterestSum(accountVO.getBankInterestSum());
        // 银行累计投资
        accountListVO.setBankInvestSum(accountVO.getBankInvestSum());
        // 银行待还金额
        accountListVO.setBankWaitRepay(accountVO.getBankWaitRepay());
        //汇计划账户可用余额
        accountListVO.setPlanBalance(accountVO.getPlanBalance());
        accountListVO.setPlanFrost(accountVO.getPlanFrost());
        accountListVO.setTotal(accountVO.getTotal());
        accountListVO.setBalance(accountVO.getBalance());
        accountListVO.setFrost(accountVO.getFrost());
        accountListVO.setAwait(accountVO.getAwait());
        accountListVO.setRepay(accountVO.getRepay());
        accountListVO.setRemark("平台转账");
        accountListVO.setCreateTime(time);
        accountListVO.setOperator(loginUserName);
        accountListVO.setIp(bankBean.getLogIp());
        accountListVO.setIsUpdate(0);
        accountListVO.setBaseUpdate(0);
        accountListVO.setInterest(null);
        accountListVO.setWeb(2);
        accountListVO.setIsBank(1);
        ret += amTradeClient.insertAccountList(accountListVO);

        // 写入网站收支
        AccountWebListVO accountWebListVO = new AccountWebListVO();
        accountWebListVO.setOrdid(bankBean.getLogOrderId());
        accountWebListVO.setAmount(money);
        // 1收入2支出
        accountWebListVO.setType(2);
        accountWebListVO.setTrade("platform_transfer");
        accountWebListVO.setTradeType("平台转账");
        accountWebListVO.setUserId(platformTransferRequest.getUserId());
        accountWebListVO.setUsrcustid(accountId);
        accountWebListVO.setTruename(userInfo.getTrueName());
        accountWebListVO.setRegionName(userInfo.getRegionName());
        accountWebListVO.setBranchName(userInfo.getBranchName());
        accountWebListVO.setDepartmentName(userInfo.getDepartmentName());
        accountWebListVO.setRemark(platformTransferRequest.getRemark());
        accountWebListVO.setCreateTime(time);
        accountWebListVO.setOperator(loginUserName);
        accountWebListVO.setFlag(1);
        ret += amTradeClient.insertAccountWebList(accountWebListVO);

        // 添加红包账户明细
        BankMerchantAccountVO bankMerchantAccountVO = amTradeClient.searchBankMerchantAccountByAccountId(Integer.valueOf(bankBean.getAccountId()));
        bankMerchantAccountVO.setAvailableBalance(bankMerchantAccountVO.getAvailableBalance().subtract(money));
        bankMerchantAccountVO.setAccountBalance(bankMerchantAccountVO.getAccountBalance().subtract(money));
        bankMerchantAccountVO.setUpdateTime(GetDate.getNowTime());

        // 更新红包账户信息
        int updateCount = amTradeClient.updateBankMerchantAccount(bankMerchantAccountVO);


        if(updateCount > 0){
            UserInfoCustomizeVO userInfoCustomize = amUserClient.getUserInfoCustomizeByUserId(platformTransferRequest.getUserId());

            // 添加红包明细
            BankMerchantAccountListVO bankMerchantAccountListVO = new BankMerchantAccountListVO();
            bankMerchantAccountListVO.setOrderId(bankBean.getLogOrderId());
            bankMerchantAccountListVO.setUserId(platformTransferRequest.getUserId());
            bankMerchantAccountListVO.setAccountId(accountId);
            bankMerchantAccountListVO.setAmount(money);
            bankMerchantAccountListVO.setBankAccountCode(bankBean.getAccountId());
            bankMerchantAccountListVO.setBankAccountBalance(bankMerchantAccountVO.getAccountBalance());
            bankMerchantAccountListVO.setBankAccountFrost(bankMerchantAccountVO.getFrost());
            bankMerchantAccountListVO.setTransType(CustomConstants.BANK_MER_TRANS_TYPE_AUTOMATIC);
            bankMerchantAccountListVO.setType(CustomConstants.BANK_MER_TYPE_EXPENDITURE);
            bankMerchantAccountListVO.setStatus(CustomConstants.BANK_MER_TRANS_STATUS_SUCCESS);
            bankMerchantAccountListVO.setTxDate(Integer.parseInt(bankBean.getTxDate()));
            bankMerchantAccountListVO.setTxTime(Integer.parseInt(bankBean.getTxTime()));
            bankMerchantAccountListVO.setSeqNo(bankBean.getSeqNo());
            bankMerchantAccountListVO.setCreateTime(new Date());
            bankMerchantAccountListVO.setUpdateTime(new Date());
            bankMerchantAccountListVO.setCreateUserId(platformTransferRequest.getUserId());
            bankMerchantAccountListVO.setUpdateUserId(platformTransferRequest.getUserId());
            bankMerchantAccountListVO.setRegionName(userInfoCustomize.getRegionName());
            bankMerchantAccountListVO.setBranchName(userInfoCustomize.getBranchName());
            bankMerchantAccountListVO.setDepartmentName(userInfoCustomize.getDepartmentName());
            bankMerchantAccountListVO.setCreateUserName(userInfoCustomize.getUserName());
            bankMerchantAccountListVO.setUpdateUserName(userInfoCustomize.getUserName());
            bankMerchantAccountListVO.setRemark("平台转账");

            ret += amTradeClient.insertBankMerchantAccountList(bankMerchantAccountListVO);
        }
        return ret;
    }
}
