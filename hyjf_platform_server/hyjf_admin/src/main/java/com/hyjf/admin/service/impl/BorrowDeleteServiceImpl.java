/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.service.AssetExceptionService;
import com.hyjf.admin.service.BorrowDeleteService;
import com.hyjf.am.resquest.admin.BorrowRegistUpdateRequest;
import com.hyjf.am.vo.admin.BorrowDeleteConfirmCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 标的删除
 * @author hesy
 */
@Service
public class BorrowDeleteServiceImpl implements BorrowDeleteService {

    Logger logger = LoggerFactory.getLogger(BorrowDeleteServiceImpl.class);

    @Autowired
    AmTradeClient amTradeClient;
    @Autowired
    AmUserClient amUserClient;

    @Autowired
    AssetExceptionService assetExceptionService;

    /**
     * 标的删除确认页面数据
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowDeleteConfirmCustomizeVO selectDeleteConfirm(String borrowNid) {
        return amTradeClient.selectDeleteConfirm(borrowNid);
    }

    /**
     * 标的删除
     * @param borrowNid
     * @return
     */
    @Override
    public AdminResult borrowDelete(String borrowNid, String currUserId, String currUserName) {
        BorrowRegistUpdateRequest request = new BorrowRegistUpdateRequest();
        // 获取标的并校验
        BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(borrowNid);
        BorrowInfoVO borrowInfo = amTradeClient.selectBorrowInfoByNid(borrowNid);
        if(borrow == null || borrowInfo == null){
            return new AdminResult(BaseResult.FAIL,"未查询到标的信息！");
        }
        // 获取开户信息并校验
        int userId = borrow.getUserId();
        BankOpenAccountVO bankOpenAccount = amUserClient.getBankOpenAccount(userId);
        if(bankOpenAccount == null){
            return new AdminResult(BaseResult.FAIL,"未查询到借款人开户信息！");
        }

        // 已备案/受托支付 先撤销备案再删除
        if(borrow.getStatus() == 1 || borrow.getStatus() == 7){
            // 调用银行接口订单号，订单日期
            String orderId = GetOrderIdUtils.getOrderId2(userId);
            String orderDate = GetOrderIdUtils.getOrderDate();

            BankCallBean bankCallBean = new BankCallBean();
            // 调用类型：标的撤销
            bankCallBean.setTxCode(BankCallConstant.TXCODE_DEBT_REGISTER_CANCEL);
            // 标的号
            bankCallBean.setProductId(borrowNid);
            // 电子账户
            bankCallBean.setAccountId(bankOpenAccount.getAccount());
            // 募集日
            bankCallBean.setRaiseDate(borrowInfo.getBankRaiseStartDate());

            // 日志用字段
            bankCallBean.setLogOrderId(orderId);
            bankCallBean.setLogOrderDate(orderDate);
            bankCallBean.setLogUserId(String.valueOf(userId));
            bankCallBean.setLogRemark("借款人标的撤销");
            bankCallBean.setLogClient(0);

            // 调用银行接口撤销标的
            BankCallBean borrowCancelResult = BankCallUtils.callApiBg(bankCallBean);
            if(borrowCancelResult == null){
                logger.error("标的删除失败，标的号：{}，撤销备案接口银行返回null", borrowNid);
                return new AdminResult(BaseResult.FAIL,"请求撤销备案接口失败，返回null！");
            }

            String retCode = StringUtils.isNotBlank(borrowCancelResult.getRetCode()) ? borrowCancelResult.getRetCode() : "";
            // state为空的时赋一个负数
            String state = StringUtils.isNotBlank(borrowCancelResult.getState()) ? borrowCancelResult.getState() : "-1";
            // 失败描述
            String bankRetmsg = StringUtils.isNotBlank(borrowCancelResult.getRetMsg()) ? borrowCancelResult.getRetMsg() : "";
            // 成功撤销或者标的已经撤销，则删除标的数据
            if(BankCallConstant.RESPCODE_SUCCESS.equals(retCode) || 9 == Integer.valueOf(state)){
                // 请求实体赋值
                request.setBorrowNid(borrowNid);
                request.setBorrowVO(borrow);
                request.setCurrUserId(currUserId);
                request.setCurrUserName(currUserName);
                AdminResult result = amTradeClient.deleteBorrow(request);
                return result;
            } else {
                logger.error("标的删除失败，标的号：{}，银行返回码：{}", borrowNid, retCode);
                return new AdminResult(BaseResult.FAIL, "调用银行撤销接口失败, retCode:" + retCode + " retMsg:" + bankRetmsg);
            }
        }
        // 未备案直接删除
        else if(borrow.getStatus() == 0){
            // 请求实体赋值
            request.setBorrowNid(borrowNid);
            request.setBorrowVO(borrow);
            request.setCurrUserId(currUserId);
            request.setCurrUserName(currUserName);
            AdminResult result = amTradeClient.deleteBorrow(request);
            return result;
        }else{
            logger.error("标的删除失败，原因：标的处于不可删除状态");
            return new AdminResult(BaseResult.FAIL, "标的删除失败，原因：标的处于不可删除状态");
        }
    }
}
