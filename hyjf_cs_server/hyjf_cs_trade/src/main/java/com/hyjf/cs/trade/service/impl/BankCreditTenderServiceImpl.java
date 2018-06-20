package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.vo.trade.CreditTenderLogVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.BankCreditTenderClient;
import com.hyjf.cs.trade.service.BankCreditTenderService;
import org.apache.commons.collections.CollectionUtils;
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
            }
        }

    }

}
