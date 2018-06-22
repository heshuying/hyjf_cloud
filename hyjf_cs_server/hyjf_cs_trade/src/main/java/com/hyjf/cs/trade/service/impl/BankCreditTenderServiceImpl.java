package com.hyjf.cs.trade.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.vo.trade.CreditTenderLogVO;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.BankCreditTenderClient;
import com.hyjf.cs.trade.mq.SmsProducer;
import com.hyjf.cs.trade.service.BankCreditTenderService;

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
