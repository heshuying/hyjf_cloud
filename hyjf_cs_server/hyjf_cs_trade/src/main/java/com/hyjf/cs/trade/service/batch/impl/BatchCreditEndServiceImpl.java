/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.batch.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.trade.BankCreditEndVO;
import com.hyjf.am.vo.trade.BatchCreditEndSubVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.batch.BatchCreditEndService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 批次结束债权
 * @author liubin
 * @version BatchCreditEndServiceImpl, v0.1 2018/7/10 19:25
 */
@Service
public class BatchCreditEndServiceImpl extends BaseTradeServiceImpl implements BatchCreditEndService {
    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private AmTradeClient amTradeClient;

    /**
     * 批次结束债权
     * @return
     */
    @Override
    public Boolean batchCreditEnd() {
        // 筛选出一个批次更新（0-1）
        BankCreditEndVO bankCreditEnd = new BankCreditEndVO();
        bankCreditEnd.setBatchNo(GetOrderIdUtils.getBatchNo());
        bankCreditEnd.setTxDate(GetOrderIdUtils.getTxDate());
        bankCreditEnd.setTxTime(GetOrderIdUtils.getTxTime());
        bankCreditEnd.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bankCreditEnd.setStatus(1); // 待处理
        bankCreditEnd.setUpdateTime(GetDate.getDate());
        // 更新结束债权任务表为待处理
        int count = this.amTradeClient.updateCreditEndForBatch(bankCreditEnd);
        if (count == 0){
            logger.info(bankCreditEnd.getBatchNo()+"--该批次无需要结束的债权--");
            return true;
        }
        bankCreditEnd.setTxCounts(count);
        logger.info("--该批次债权数量为"+count+"--[批次号：" + bankCreditEnd.getBatchNo() + "],"
                + "[日期：" + bankCreditEnd.getTxDate() + "]");
        // 调用批次结束债权接口（1-2）
        return this.creditEndApi(bankCreditEnd);
    }

    /**
     * 合法性检查后，更新批次结束债权任务
     * @param bean
     * @return
     */
    @Override
    public int updateBatchCreditEndCheck(BankCallBean bean) {
        BankCallBeanVO bankCallBeanVO = new BankCallBeanVO();
        BeanUtils.copyProperties(bean, bankCallBeanVO);
        return this.amTradeClient.updateBatchCreditEndCheck(bankCallBeanVO);
    }

    /**
     * 银行完成后，更新批次结束债权任务
     * @param bean
     * @return
     */
    @Override
    public int updateBatchCreditEndFinish(BankCallBean bean) {
        BankCallBeanVO bankCallBeanVO = new BankCallBeanVO();
        BeanUtils.copyProperties(bean, bankCallBeanVO);
        return this.amTradeClient.updateBatchCreditEndFinish(bankCallBeanVO);
    }

    /**
     * 批次结束债权处理
     * @param bankCreditEnd
     * @return
     */
    private boolean creditEndApi(BankCreditEndVO bankCreditEnd) {
        // 取得该批次的债权信息生成Json
        String subPacks = getSubPacks(bankCreditEnd);

        try {
            // 生成订单
            String orderId = GetOrderIdUtils.getOrderId2(Integer.valueOf(bankCreditEnd.getBatchNo()));
            // 银行接口用bean
            BankCallBean bean = new BankCallBean(orderId, Integer.valueOf(bankCreditEnd.getBatchNo()), BankCallConstant.TXCODE_BATCH_CREDIT_END, "批次结束债权(BatchNo)", 0);

            // 调用放款接口
            bean.setBatchNo(bankCreditEnd.getBatchNo());
            bean.setTxDate(bankCreditEnd.getTxDate());
            bean.setTxTime(bankCreditEnd.getTxTime());
            bean.setSeqNo(bankCreditEnd.getSeqNo());
            bean.setTxCounts(String.valueOf(bankCreditEnd.getTxCounts()));
            bean.setNotifyURL(systemConfig.getNotifyUrl());
            bean.setRetNotifyURL(systemConfig.getRetNotifyUrl());
            bean.setSubPacks(subPacks);

            BankCallBean result = BankCallUtils.callApiBg(bean);
            if (Validator.isNull(result)) {
                throw new RuntimeException("银行没有返回批次结束债权请求结果。数据回滚[批次号：" + bankCreditEnd.getBatchNo() + "],"
                        + "[日期：" + bankCreditEnd.getTxDate() + "]");
            }

            String received = StringUtils.isNotBlank(result.getReceived()) ? result.getReceived() : "";
            if (!BankCallConstant.RECEIVED_SUCCESS.equals(received)) {
                throw new RuntimeException("银行接受批次结束债权请求失败。数据回滚[批次号：" + bankCreditEnd.getBatchNo() + "],"
                        + "[日期：" + bankCreditEnd.getTxDate() + "]");
            }
            // 更新状态 请求成功
            boolean apicronResultFlag = this.amTradeClient.updateCreditEndForStatus(bankCreditEnd, 2) > 0 ? true : false;
            if (!apicronResultFlag) {
                logger.error("银行接受结束债权请求请求成功，更新状态为（请求成功）失败。[批次号：" + bankCreditEnd.getBatchNo() + "],"
                        + "[日期：" + bankCreditEnd.getTxDate() + "]");
            }

            logger.info("更新状态为（结束债权请求）成功。[批次号：" + bankCreditEnd.getBatchNo() + "],"
                    + "[日期：" + bankCreditEnd.getTxDate() + "]");
            return true;
        } catch ( Exception e) {
            throw new RuntimeException("批次结束债权请求异常！[批次号：" + bankCreditEnd.getBatchNo() + "],"
                    + "[日期：" + bankCreditEnd.getTxDate() + "]", e);
        }
    }

    /**
     * 取得该批次的债权信息生成Json
     * @param bankCreditEnd
     * @return
     */
    private String getSubPacks(BankCreditEndVO bankCreditEnd) {
        String subPacks = null;
        List<BatchCreditEndSubVO> jsonList= new ArrayList<BatchCreditEndSubVO>();
        List<BankCreditEndVO> list = this.selectList(bankCreditEnd);
        if (list == null || list.size() == 0) {
            logger.info("未获取到批次号为{}、交易日期为{}的债权信息，1秒后重新获取！", bankCreditEnd.getBatchNo(), bankCreditEnd.getTxDate());
            try {
                Thread.sleep(1000);
                list = this.selectList(bankCreditEnd);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
        if (list != null) {
            for (BankCreditEndVO fromBean : list) {
                BatchCreditEndSubVO jsonBean = new BatchCreditEndSubVO();
                jsonBean.setAccountId(fromBean.getAccountId());
                jsonBean.setAuthCode(fromBean.getAuthCode());
                jsonBean.setForAccountId(fromBean.getTenderAccountId());
                jsonBean.setOrderId(fromBean.getOrderId());
                jsonBean.setProductId(fromBean.getBorrowNid());
                jsonList.add(jsonBean);
            }
            subPacks = JSON.toJSONString(jsonList);
        }
        return subPacks;
    }

    /**
     * 取得该批次数据
     * @param bankCreditEndVO
     * @return
     */
    private List<BankCreditEndVO> selectList(BankCreditEndVO bankCreditEndVO) {
        return this.amTradeClient.getBankCreditEndListByBatchnoTxdate(bankCreditEndVO);
    }
}
