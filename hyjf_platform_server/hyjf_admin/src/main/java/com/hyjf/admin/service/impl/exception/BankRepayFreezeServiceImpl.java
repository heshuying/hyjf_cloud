package com.hyjf.admin.service.impl.exception;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.am.vo.trade.repay.BankRepayFreezeLogVO;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 银行还款冻结异常撤销
 * @author hesy
 * @version BankRepayFreezeServiceImpl, v0.1 2018/7/11 14:56
 */
@Service
public class BankRepayFreezeServiceImpl extends BaseServiceImpl implements com.hyjf.admin.service.exception.BankRepayFreezeService {
    @Autowired
    AmTradeClient amTradeClient;

    @Override
    public Integer getFreezeLogCount(){
        return amTradeClient.getFreezeLogValidAllCount();
    }

    @Override
    public List<BankRepayFreezeLogVO> getFreezeLogList(Integer limitStart, Integer limitEnd){
        return amTradeClient.getFreezeLogValidAll(limitStart,limitEnd);
    }

    /**
     * 根据id获取冻结记录
     * @param orderId
     * @return
     */
    @Override
    public BankRepayFreezeLogVO getFreezeLogByOrderId(String orderId){
        return amTradeClient.getBankFreezeLogByOrderId(orderId);
    }

    /**
     * 冻结撤销更新数据
     * @param freezeLogVO
     * @return
     */
    @Override
    public boolean updateBankRepayFreeze(BankRepayFreezeLogVO freezeLogVO){
        return amTradeClient.deleteFreezeLogById(freezeLogVO.getId()) > 0 ? true : false;
    }

    /**
     * 申请冻结撤销
     * @auther: hesy
     * @date: 2018/7/11
     */
    @Override
    public boolean repayUnfreeze(BankRepayFreezeLogVO repayFreezeFlog) {
        BankCallBean bean = new BankCallBean();
        bean.setTxCode(BankCallConstant.TXCODE_BALANCE_UNFREEZE);
        bean.setAccountId(repayFreezeFlog.getAccount());// 电子账号
        bean.setOrderId(GetOrderIdUtils.getUsrId(repayFreezeFlog.getUserId()));// 订单号
        bean.setOrgOrderId(repayFreezeFlog.getOrderId());// 原订单号
        bean.setProductId(repayFreezeFlog.getBorrowNid());
        bean.setTxAmount(String.valueOf(repayFreezeFlog.getAmount()));// 冻结资金
        bean.setLogUserId(String.valueOf(repayFreezeFlog.getUserId())); // 操作者ID
        bean.setLogOrderId(GetOrderIdUtils.getUsrId(repayFreezeFlog.getUserId()));
        bean.setLogClient(0);
        // 调用接口
        BankCallBean callApiBg = BankCallUtils.callApiBg(bean);
        if (Validator.isNotNull(callApiBg)) {
            logger.info("记录状态: {}", callApiBg.getState());

            String retCode = callApiBg.getRetCode();
            if (StringUtils.isBlank(retCode)) {
                logger.info("=============返回码为空===========");
                return false;
            }
            if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
                logger.info("===========冻结订单号为: {}", repayFreezeFlog.getOrderId());
                return true;
            } else if (retCode.equals(BankCallConstant.RETCODE_UNFREEZE_NOT_EXIST)
                    || retCode.equals(BankCallConstant.RETCODE_FREEZE_FAIL)
                    || retCode.equals(BankCallConstant.RETCODE_UNFREEZE_ALREADY)) {
                logger.info("===============冻结记录不存在,不予处理========");
                return true;
            }
        }
        return false;
    }

}
