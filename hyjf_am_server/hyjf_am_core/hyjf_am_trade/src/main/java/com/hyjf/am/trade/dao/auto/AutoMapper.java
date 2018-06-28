package com.hyjf.am.trade.dao.auto;


import com.hyjf.am.trade.dao.mapper.auto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutoMapper {

    @Autowired
    protected BorrowMapper borrowMapper;

    @Autowired
    protected BorrowInfoMapper borrowInfoMapper;

    @Autowired
    protected BorrowFinmanNewChargeMapper borrowFinmanNewChargeMapper;

    @Autowired
    protected BorrowConfigMapper borrowConfigMapper;

    @Autowired
    protected BorrowManinfoMapper borrowManinfoMapper;

    @Autowired
    protected BorrowStyleMapper borrowStyleMapper;

    @Autowired
    protected BorrowTenderTmpMapper borrowTenderTmpMapper;

    @Autowired
    protected BorrowTenderTmpinfoMapper borrowTenderTmpinfoMapper;

    @Autowired
    protected HjhDebtCreditMapper hjhDebtCreditMapper;

    @Autowired
    protected BorrowRecoverMapper borrowRecoverMapper;

    @Autowired
    protected BorrowRecoverPlanMapper borrowRecoverPlanMapper;

    @Autowired
    protected HjhDebtDetailMapper hjhDebtDetailMapper;

    @Autowired
    protected HjhRepayMapper hjhRepayMapper;

    @Autowired
    protected HjhDebtCreditTenderMapper hjhDebtCreditTenderMapper;

    @Autowired
    protected BorrowTenderMapper borrowTenderMapper;

    @Autowired
    protected TenderAgreementMapper tenderAgreementMapper;
}

