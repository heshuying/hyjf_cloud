package com.hyjf.cs.trade.service.repay.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.*;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.repay.BankRepayFreezeLogVO;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.MD5;
import com.hyjf.common.util.MD5Utils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.repay.ProjectBean;
import com.hyjf.cs.trade.bean.repay.RepayBean;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.repay.RepayManageService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 还款管理列表
 *
 * @author hesy
 * @version RepayManageServiceImpl, v0.1 2018/6/23 17:16
 */
@Service
public class RepayManageServiceImpl extends BaseTradeServiceImpl implements RepayManageService {

    /**
     * 普通用户管理费总待还
     * @param userId
     * @return
     */
    @Override
    public BigDecimal getUserRepayFeeWaitTotal(Integer userId) {
        return amTradeClient.getUserRepayFeeWaitTotal(userId);
    }

    /**
     * 担保机构管理费总待还
     * @param userId
     * @return
     */
    @Override
    public BigDecimal getOrgRepayFeeWaitTotal(Integer userId) {
        return amTradeClient.getUserRepayFeeWaitTotal(userId);
    }

    /**
     * 担保机构待还
     * @param userId
     * @return
     */
    @Override
    public BigDecimal getOrgRepayWaitTotal(Integer userId) {
        return amTradeClient.getOrgRepayWaitTotal(userId);
    }

    /**
     * 请求参数校验
     *
     * @param requestBean
     */
    @Override
    public void checkForRepayList(RepayListRequest requestBean) {
        if (requestBean.getCurrPage() <= 0) {
            requestBean.setCurrPage(1);
        }

        if (requestBean.getPageSize() <= 0) {
            requestBean.setPageSize(10);
        }
    }

    /**
     * 用户已还款/待还款列表
     *
     * @param requestBean
     * @return
     */
    @Override
    public List<RepayListCustomizeVO> selectRepayList(RepayListRequest requestBean) {
        List<RepayListCustomizeVO> resultList =  amTradeClient.repayList(requestBean);
        if(resultList == null){
            return new ArrayList<RepayListCustomizeVO>();
        }
        return resultList;
    }

    /**
     * 垫付机构待还款列表
     *
     * @param requestBean
     * @return
     */
    @Override
    public List<RepayListCustomizeVO> selectOrgRepayList(RepayListRequest requestBean) {
        List<RepayListCustomizeVO> resultList = amTradeClient.orgRepayList(requestBean);
        if(resultList == null){
            return new ArrayList<RepayListCustomizeVO>();
        }
        return resultList;
    }

    /**
     * 垫付机构已还款列表
     *
     * @param requestBean
     * @return
     */
    @Override
    public List<RepayListCustomizeVO> selectOrgRepayedList(RepayListRequest requestBean) {
        List<RepayListCustomizeVO> resultList = amTradeClient.orgRepayedList(requestBean);
        if(resultList == null){
            return new ArrayList<RepayListCustomizeVO>();
        }
        return resultList;
    }

    /**
     * 用户待还款/已还款总记录数
     *
     * @param requestBean
     * @return
     */
    @Override
    public Integer selectRepayCount(RepayListRequest requestBean) {
        return amTradeClient.repayCount(requestBean);
    }

    /**
     * 垫付机构待还款总记录数
     *
     * @param requestBean
     * @return
     */
    @Override
    public Integer selectOrgRepayCount(RepayListRequest requestBean) {
        return amTradeClient.orgRepayCount(requestBean);
    }

    /**
     * 垫付机构已还款总记录数
     *
     * @param requestBean
     * @return
     */
    @Override
    public Integer selectOrgRepayedCount(RepayListRequest requestBean) {
        return amTradeClient.orgRepayedCount(requestBean);
    }

    @Override
    public boolean checkPassword(Integer userId, String password) {
        UserVO user = this.getUserByUserId(userId);
        String codeSalt = user.getSalt();
        String passwordDb = user.getPassword();
        // 验证用的password
        password = MD5Utils.MD5(password + codeSalt);
        // 密码正确时
        if (password.equals(passwordDb)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public JSONObject getRepayDetailData(RepayRequestDetailRequest requestBean) {
        return amTradeClient.getRepayDetailData(requestBean);
    }

   /**
    * 还款申请校验
    * @auther: hesy
    * @date: 2018/7/10
    */
    @Override
    public void checkForRepayRequest(String borrowNid, String password, WebViewUserVO user, RepayBean repayBean){
        if(StringUtils.isBlank(borrowNid) || StringUtils.isBlank(password)){
            throw new CheckException(MsgEnum.ERR_PARAM_NUM);
        }
        // 平台密码校验
        UserVO userVO = getUserByUserId(user.getUserId());
        String mdPassword = MD5Utils.MD5(password + userVO.getSalt());
        if (!mdPassword.equals(userVO.getPassword())) {
            throw  new CheckException(MsgEnum.ERR_PASSWORD_INVALID);
        }

        // 开户校验
        if(!user.isBankOpenAccount()){
            throw  new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        BorrowAndInfoVO borrow = amTradeClient.getBorrowByNid(borrowNid);
        if(borrow == null){
            throw  new CheckException(MsgEnum.ERR_AMT_TENDER_BORROW_NOT_EXIST);
        }

        boolean tranactionSetFlag = RedisUtils.tranactionSet(RedisConstants.HJH_DEBT_SWAPING + borrow.getBorrowNid(),300);
        if (!tranactionSetFlag) {//设置失败
            throw  new CheckException(MsgEnum.ERR_SYSTEM_BUSY);
        }

        AccountVO accountVO = getAccountByUserId(user.getUserId());
        try {
            if (repayBean.getRepayAccountAll().compareTo(accountVO.getBankBalance()) == 0 || repayBean.getRepayAccountAll().compareTo(accountVO.getBankBalance()) == -1) {
                // ** 用户符合还款条件，可以还款 *//*
                // 查询用户在银行电子账户的余额
                BigDecimal userBankBalance = getBankBalancePay(user.getUserId(),user.getBankAccount());
                if (repayBean.getRepayAccountAll().compareTo(userBankBalance) == 0 || repayBean.getRepayAccountAll().compareTo(userBankBalance) == -1) {
                    // ** 用户符合还款条件，可以还款 *//*
                } else {
                    // 银行账户余额不足
                    throw  new CheckException(MsgEnum.ERR_AMT_NO_MONEY);
                }
            } else {
                // 平台账户余额不足
                throw  new CheckException(MsgEnum.ERR_AMT_NO_MONEY);
            }

        } catch (Exception e) {
            logger.error("还款申请可用余额校验异常", e);
            throw new ReturnMessageException(MsgEnum.STATUS_CE999999);
        }

    }


    @Override
    public void checkForRepayRequestOrg(String borrowNid, String password, WebViewUserVO user, RepayBean repayBean, int flag){
        if(StringUtils.isBlank(borrowNid) || StringUtils.isBlank(password)){
            throw new CheckException(MsgEnum.ERR_PARAM_NUM);
        }
        // 平台密码校验
        UserVO userVO = getUserByUserId(user.getUserId());
        String mdPassword = MD5.toMD5Code(password + userVO.getSalt());
        if (!mdPassword.equals(userVO.getPassword())) {
            throw  new CheckException(MsgEnum.ERR_PASSWORD_INVALID);
        }

        // 开户校验
        if(!user.isBankOpenAccount()){
            throw  new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        BorrowAndInfoVO borrow = amTradeClient.getBorrowByNid(borrowNid);
        if(borrow == null){
            throw  new CheckException(MsgEnum.ERR_AMT_TENDER_BORROW_NOT_EXIST);
        }

        boolean tranactionSetFlag = RedisUtils.tranactionSet(RedisConstants.HJH_DEBT_SWAPING + borrow.getBorrowNid(),300);
        if (!tranactionSetFlag) {//设置失败
            throw  new CheckException(MsgEnum.ERR_SYSTEM_BUSY);
        }

        AccountVO accountVO = getAccountByUserId(user.getUserId());
        // 一次性还款
        if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrow.getBorrowStyle()) || CustomConstants.BORROW_STYLE_END.equals(borrow.getBorrowStyle())) {
            if (repayBean.getRepayAccountAll().compareTo(accountVO.getBankBalance()) == 0 || repayBean.getRepayAccountAll().compareTo(accountVO.getBankBalance()) == -1) {
                // ** 垫付机构符合还款条件，可以还款 *//*
                // 查询用户在银行的电子账户
                BigDecimal userBankBalance = getBankBalancePay(user.getUserId(),user.getBankAccount());
                // 获取用户在银行的电子账户余额
                if (flag == 1) {//垫付机构批量还款
                    repayBean.setRepayUserId(user.getUserId());// 垫付机构id
                }else{
                    if (repayBean.getRepayAccountAll().compareTo(userBankBalance) == 0 || repayBean.getRepayAccountAll().compareTo(userBankBalance) == -1) {
                        // ** 垫付机构符合还款条件，可以还款 *//*
                        repayBean.setRepayUserId(user.getUserId());// 垫付机构id
                    } else {
                        // 银行账户余额不足
                        throw  new CheckException(MsgEnum.ERR_AMT_NO_MONEY);
                    }
                }

            } else {
                // 用户平台账户余额不足
                throw  new CheckException(MsgEnum.ERR_AMT_NO_MONEY);
            }
        } // 分期还款
        else {
            repayBean.setRepayUserId(user.getUserId());// 垫付机构id

            if (flag ==1) {
                //垫付机构批量还款 ，不验证总额
                ;
            }else {

                if (repayBean.getRepayAccountAll().compareTo(accountVO.getBankBalance()) == 0 || repayBean.getRepayAccountAll().compareTo(accountVO.getBankBalance()) == -1) {
                    // ** 垫付机构符合还款条件，可以还款 *//*
                    // 查询用户在银行的电子账户
                    BigDecimal userBankBalance = getBankBalancePay(user.getUserId(),user.getBankAccount());
                    if (repayBean.getRepayAccountAll().compareTo(userBankBalance) == 0 || repayBean.getRepayAccountAll().compareTo(userBankBalance) == -1) {
                        // ** 用户符合还款条件，可以还款 *//*
                        ;
                    } else {
                        // 银行账户余额不足
                        throw  new CheckException(MsgEnum.ERR_AMT_NO_MONEY);
                    }
                } else {
                    // 用户平台账户余额不足
                    throw  new CheckException(MsgEnum.ERR_AMT_NO_MONEY);
                }

            }


        }
        // 如果有正在出让的债权,先去把出让状态停止
        this.updateBorrowCreditStautus(borrow.getBorrowNid());

    }

    @Override
    public RepayBean getRepayBean(Integer userId, String roleId, String borrowNid, boolean isAllRepay) {
        Map<String,String> paraMap = new HashMap<>();
        paraMap.put("userId", String.valueOf(userId));
        paraMap.put("roleId", roleId);
        paraMap.put("borrowNid", borrowNid);
        paraMap.put("isAllRepay", String.valueOf(isAllRepay));

        return amTradeClient.getRepayBean(paraMap);
    }

    /**
     * 还款申请回调数据更新
     * @auther: hesy
     * @date: 2018/7/10
     */
    @Override
    public Boolean updateForRepayRequest(RepayBean repayBean, BankCallBean bankCallBean){
        RepayRequestUpdateRequest requestBean = new RepayRequestUpdateRequest();
        requestBean.setRepayBeanData(JSON.toJSONString(repayBean));
        requestBean.setBankCallBeanData(JSON.toJSONString(bankCallBean));

        return amTradeClient.repayRequestUpdate(requestBean);
    }

    /**
     * 如果有正在出让的债权,先去把出让状态停止
     * @param borrowNid
     * @return
     */
    @Override
    public Boolean updateBorrowCreditStautus(String borrowNid) {
        return amTradeClient.updateBorrowCreditStautus(borrowNid);
    }

    /**
     * 校验重复还款
     * @param userId
     * @param borrowNid
     * @return
     */
    @Override
    public boolean checkRepayInfo(Integer userId, String borrowNid) {
        BankRepayFreezeLogVO log = amTradeClient.getFreezeLogValid(userId, borrowNid);
        if(log == null){
            return true;
        }
        return false;
    }

    /**
     * 添加冻结日志
     * @auther: hesy
     * @date: 2018/7/10
     */
    @Override
    public Integer addFreezeLog(Integer userId, String orderId, String account, String borrowNid, BigDecimal repayTotal,
                                String userName){
        BankRepayFreezeLogRequest requestBean = new BankRepayFreezeLogRequest();
        requestBean.setBorrowNid(borrowNid);
        requestBean.setAccount(account);
        requestBean.setAmount(repayTotal);
        requestBean.setOrderId(orderId);
        requestBean.setUserId(userId);
        requestBean.setUserName(userName);
        return amTradeClient.addFreezeLog(requestBean);
    }

    /**
     * 删除冻结日志
     * @auther: hesy
     * @date: 2018/7/10
     */
    @Override
    public Integer deleteFreezeLogByOrderId(String orderId){
        if(StringUtils.isBlank(orderId)){
            return 0;
        }
        return amTradeClient.deleteFreezeLogByOrderId(orderId);
    }

    /**
     * 更新借款API任务表
     * @return
     */
    @Override
    public Boolean updateBorrowApicron(BorrowApicronVO apicronVO, Integer status) {
        ApiCronUpdateRequest requestBean = new ApiCronUpdateRequest();
        requestBean.setApicronVO(apicronVO);
        requestBean.setStatus(status);

        return amTradeClient.updateBorrowApicron(requestBean);
    }

    /**
     * 根据bankSeqNo检索
     * @param bankSeqNO
     * @return
     */
    @Override
    public BorrowApicronVO selectBorrowApicron(String bankSeqNO) {
        return amTradeClient.selectBorrowApicron(bankSeqNO);
    }

    /**
     * 批次状态查询
     * @param apicron
     * @return
     */
    @Override
    public BankCallBean batchQuery(BorrowApicronVO apicron) {
        // 获取共同参数
        String batchNo = apicron.getBatchNo();// 还款请求批次号
        String batchTxDate = String.valueOf(apicron.getTxDate());// 还款请求日期
        int userId = apicron.getUserId();
        for (int i = 0; i < 3; i++) {
            String logOrderId = GetOrderIdUtils.getOrderId2(userId);
            String orderDate = GetOrderIdUtils.getOrderDate();
            // 调用还款接口
            BankCallBean loanBean = new BankCallBean();
            loanBean.setTxCode(BankCallConstant.TXCODE_BATCH_QUERY);// 消息类型(批量还款)
            loanBean.setBatchNo(batchNo);
            loanBean.setBatchTxDate(batchTxDate);
            loanBean.setLogUserId(String.valueOf(apicron.getUserId()));
            loanBean.setLogOrderId(logOrderId);
            loanBean.setLogOrderDate(orderDate);
            loanBean.setLogRemark("批次状态查询");
            loanBean.setLogClient(0);
            BankCallBean queryResult = BankCallUtils.callApiBg(loanBean);
            if (Validator.isNotNull(queryResult)) {
                String retCode = StringUtils.isNotBlank(queryResult.getRetCode()) ? queryResult.getRetCode() : "";
                if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
                    return queryResult;
                } else {
                    continue;
                }
            } else {
                continue;
            }
        }
        return null;
    }

    /**
     * 获取批量还款页面数据
     */
    @Override
    public ProjectBean getOrgBatchRepayData(String userId, String startDate, String endDate) {
        BatchRepayDataRequest requestBean = new BatchRepayDataRequest();
        requestBean.setUserId(userId);
        requestBean.setStartDate(startDate);
        requestBean.setEndDate(endDate);
        return amTradeClient.getOrgBatchRepayData(requestBean);
    }
}
