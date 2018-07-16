package com.hyjf.am.trade.dao.customize;


import com.hyjf.am.trade.dao.auto.AutoMapper;
import com.hyjf.am.trade.dao.mapper.customize.BatchHjhBorrowRepayCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BatchUserPortraitQueryCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.*;
import com.hyjf.am.trade.dao.mapper.customize.batch.BatchHjhAccedeCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.batch.OntimeTenderCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.batch.TzjCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.repay.BorrowAuthCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.repay.RepayManageCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.*;
import com.hyjf.am.trade.dao.mapper.customize.web.AssetManageCustomizeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CustomizeMapper extends AutoMapper {

    @Resource
    protected BorrowCustomizeMapper borrowCustomizeMapper;

    @Resource
    protected HjhDebtDetailCustomizeMapper hjhDebtDetailCustomizeMapper;

    @Resource
    protected RepayManageCustomizeMapper repayManageCustomizeMapper;

    @Resource
    protected BatchUserPortraitQueryCustomizeMapper batchUserPortraitQueryCustomizeMapper;

    @Resource
    protected HjhPlanCustomizeMapper hjhPlanCustomizeMapper;

    @Resource
    protected BatchHjhAccedeCustomizeMapper batchHjhAccedeCustomizeMapper;

    @Resource
    protected AdminAccountCustomizeMapper adminAccountCustomizeMapper;

    @Resource
    protected EmployeeCustomizeMapper employeeCustomizeMapper;

    @Resource
    protected RUserCustomizeMapper rUserCustomizeMapper;

    @Resource
    protected AssetManageCustomizeMapper assetManageCustomizeMapper;

    @Resource
    protected UserTradeDetailCustomizeMapper userTradeDetailCustomizeMapper;

    @Resource
    protected BatchHjhBorrowRepayCustomizeMapper batchHjhBorrowRepayCustomizeMapper;

    @Autowired
    protected AdminBorrowRecoverCustomizeMapper borrowRecoverCustomizeMapper;
    @Autowired
    protected AdminBorrowLogCustomizeMapper borrowLogCustomizeMapper;
    @Autowired
    protected AdminBorrowRepaymentCustomizeMapper borrowRepaymentCustomizeMapper;

    @Autowired
    protected AdminBorrowRepaymentInfoCustomizeMapper borrowRepaymentInfoCustomizeMapper;

    @Autowired
    protected AdminBorrowRepaymentInfoListCustomizeMapper borrowRepaymentInfoListCustomizeMapper;
    @Resource
    protected AdminHjhDebtCreditCustomizeMapper adminHjhDebtCreditCustomizeMapper;

    @Resource
    protected BorrowAuthCustomizeMapper borrowAuthCustomizeMapper;

    @Resource
    protected BatchCenterCustomizeMapper batchCenterCustomizeMapper;

    @Resource
    protected TzjCustomizeMapper tzjCustomizeMapper;

    @Autowired
    protected AdminTransferExceptionLogCustomizeMapper transferExceptionLogCustomizeMapper;

    @Autowired
    protected CouponRecoverCustomizeMapper couponRecoverCustomizeMapper;

    @Autowired
    protected OntimeTenderCustomizeMapper ontimeTenderCustomizeMapper;

    @Autowired
    protected WithdrawCustomizeMapper withdrawCustomizeMapper;

    @Resource
    protected ManualReverseCustomizeMapper manualReverseCustomizeMapper;

}


