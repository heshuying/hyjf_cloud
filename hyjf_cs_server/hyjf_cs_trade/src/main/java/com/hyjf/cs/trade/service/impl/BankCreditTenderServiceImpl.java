package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.vo.trade.BankCallBeanVO;
import com.hyjf.am.vo.trade.CreditTenderLogVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.BankCreditTenderClient;
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

import java.util.List;

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

    /**
     * 处理债转投资异常
     */
    @Override
    public void handle() {
        List<CreditTenderLogVO> creditTenderLogs = bankCreditTenderClient.selectCreditTenderLogs();
        if (CollectionUtils.isNotEmpty(creditTenderLogs)) {
            logger.info("待处理数据:size:[" + creditTenderLogs.size() + "].");
            for (CreditTenderLogVO creditTenderLog : creditTenderLogs) {
                // 承接订单号
                String assignNid = creditTenderLog.getAssignNid();
                // 根据承接订单号查询债转投资表
                List<CreditTenderVO> creditTenderList = this.bankCreditTenderClient.selectCreditTender(assignNid);
                if (creditTenderList != null && creditTenderList.size() > 0) {
                    continue;
                }

                Integer userId = creditTenderLog.getUserId();
                String logOrderId = creditTenderLog.getLogOrderId();
                BankCallBean tenderQueryBean = this.queryCreditInvest(logOrderId, userId);
                if (tenderQueryBean!=null){
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
                    }
                }

            }
        }

    }






    /**
     * 调用江西银行购买债券查询接口
     * @param assignOrderId
     * @param userId
     * @return
     */
    public BankCallBean queryCreditInvest(String assignOrderId, Integer userId) {
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
