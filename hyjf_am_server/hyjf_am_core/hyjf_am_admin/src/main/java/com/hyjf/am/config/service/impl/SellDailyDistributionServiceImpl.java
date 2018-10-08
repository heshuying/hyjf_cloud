package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.service.SellDailyDistributionService;
import com.hyjf.am.resquest.admin.EmailRecipientRequest;
import com.hyjf.am.vo.admin.SellDailyDistributionVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lisheng
 * @version SellDailyDistributionServiceImpl, v0.1 2018/10/8 11:37
 */
@Service
public class SellDailyDistributionServiceImpl implements SellDailyDistributionService {

    @Override
    public Integer queryTotal(EmailRecipientRequest form) {
        return null;
    }

    @Override
    public List<SellDailyDistributionVO> queryRecordList(EmailRecipientRequest form, int limitStart, int limitEnd) {
        return null;
    }

    @Override
    public SellDailyDistributionVO queryRecordById(Integer id) {
        return null;
    }
}
