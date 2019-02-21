/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.service.PlatformTransferService;
import com.hyjf.am.resquest.admin.PlatformTransferListRequest;
import com.hyjf.am.resquest.admin.PlatformTransferRequest;
import com.hyjf.am.vo.admin.AccountRechargeVO;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: PlatformTransferServiceImpl, v0.1 2018/7/9 10:29
 */
@Service
public class PlatformTransferServiceImpl extends BaseAdminServiceImpl implements PlatformTransferService {

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
        if(CollectionUtils.isEmpty(accountRechargeVOList)){
            return new ArrayList<>();
        }
        String userIds = "";
        for(AccountRechargeVO accountRechargeVO:accountRechargeVOList){
            if(null != accountRechargeVO.getUserId()){
                userIds += String.valueOf(accountRechargeVO.getUserId()) + ",";
            }
        }
        userIds = userIds.substring(0,userIds.lastIndexOf(","));
        logger.info("平台转账--> userIds=====[{}]",userIds);
        List<UserVO> userVOList = amUserClient.findUserListByUserIds(userIds);
        for(AccountRechargeVO accountRechargeVO:accountRechargeVOList){
            //txTime时间格式化
            if(null != accountRechargeVO.getTxDate()){
                accountRechargeVO.setTxDateStr(timeFormat(accountRechargeVO.getTxDate(),"-"));
            }
            if(null != accountRechargeVO.getTxTime()){
                accountRechargeVO.setTxTimeStr(timeFormat(accountRechargeVO.getTxTime(),":"));
            }
            for(UserVO userVO:userVOList){
                if(null != userVO && null != userVO.getUserId() && accountRechargeVO.getUserId().equals(userVO.getUserId())){
                    accountRechargeVO.setMobile(userVO.getMobile());
                    userVOList.remove(userVO);
                    break;
                }
            }
        }
        logger.debug("accountRechargeVOList:[{}]",JSON.toJSONString(accountRechargeVOList));
        return accountRechargeVOList;
    }

    private static String timeFormat(int time,String format){
        int s = time % 100;
        time = time / 100;
        int m = time % 100;
        int h = time / 100;
        String hStr = "" + h;
        String mStr = "" + m;
        String sStr = "" + s;
        if(m<10){
            mStr = "0" + m;
        }
        if(s<10){
            sStr = "0" + s;
        }
        if(h<10){
            hStr = "0" + h;
        }
        return hStr + format + mStr + format + sStr;
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
                result.put("status","99");
                result.put("info", "用户未开户，无法转账!");
            }
        } else {
            result.put("status","99");
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
    @Transactional(rollbackFor = Exception.class)
    public JSONObject handRecharge(Integer loginUserId,HttpServletRequest request, PlatformTransferRequest platformTransferRequest) {
        JSONObject result = new JSONObject();
        AdminSystemVO adminSystemVO = amConfigClient.getUserInfoById(loginUserId);
        String password = platformTransferRequest.getPassword();
        if (password != null && password.equals(systemConfig.HYJF_HANDRECHARGE_PASSWORD)) {

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
            String merrpAccount = systemConfig.getBANK_MERRP_ACCOUNT();
            BigDecimal bankBalance = this.getBankBalance(loginUserId, merrpAccount);
            logger.info("账户余额::::::[{}],转账金额::::::::[{}]",bankBalance,platformTransferRequest.getMoney());
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
                logger.debug("调用银行出错,e:[{}]",e.getMessage());
                result.put("status", "error");
                result.put("result", "平台转账发生错误,请重新操作!");
                return result;
            }

            if (resultBean == null || !BankCallConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
                logger.info("调用银行未返回或者返回失败，银行返回码:[{}]",resultBean.getRetCode());
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
                result.put("status", "0");
                result.put("result", "平台转账操作成功!");
            } else {
                logger.info("调用银行成功后，插入数据表失败");
                result.put("status", "error");
                result.put("result", "平台转账发生错误,请重新操作!");
            }
        } else {
            result.put("status", "error");
            result.put("result", "密码错误,请重新操作!");
        }

        return result;
    }

    @Override
    public BigDecimal getAccountBalance(Integer userId) {
        BigDecimal balance = getBankBalance(userId,systemConfig.getBANK_MERRP_ACCOUNT());
        return balance;
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
            logger.debug("银行返回的报文:[{}]",JSON.toJSONString(resultBean));
            if (resultBean != null && BankCallStatusConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
                logger.debug("银行返回的code:[{}]",resultBean.getRetCode());
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
    private int updateHandRechargeRecord(PlatformTransferRequest platformTransferRequest, BankCallBean bankBean, UserInfoCustomizeVO userInfo, String accountId,String loginUserName) {
        platformTransferRequest.setBankCallBeanVO(CommonUtils.convertBean(bankBean,BankCallBeanVO.class));
        platformTransferRequest.setLoginUserName(loginUserName);
        platformTransferRequest.setUserInfo(userInfo);
        platformTransferRequest.setAccountId(accountId);
        UserInfoCustomizeVO userInfoCustomizeVO = amUserClient.getUserInfoCustomizeByUserId(platformTransferRequest.getUserId());
        platformTransferRequest.setUserInfoCustomizeVO(userInfoCustomizeVO);
        return amAdminClient.updateHandRechargeRecord(platformTransferRequest);
    }
}
