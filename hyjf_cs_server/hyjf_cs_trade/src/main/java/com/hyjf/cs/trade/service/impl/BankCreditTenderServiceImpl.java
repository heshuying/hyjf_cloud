package com.hyjf.cs.trade.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowWithBLOBsVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.fdd.fddgeneratecontract.FddGenerateContractBean;
import com.hyjf.cs.trade.mq.Producer;
import com.hyjf.cs.trade.mq.SmsProducer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.vo.trade.CreditTenderLogVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.BankCreditTenderClient;
import com.hyjf.cs.trade.service.BankCreditTenderService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;

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
    private SmsProducer smsProducer;
    /**
     * 处理债转投资异常
     */
    @Override
    public void handle() {
        List<CreditTenderLogVO> creditTenderLogs = bankCreditTenderClient.selectCreditTenderLogs();
        if (CollectionUtils.isNotEmpty(creditTenderLogs)){

        }
    }






}
