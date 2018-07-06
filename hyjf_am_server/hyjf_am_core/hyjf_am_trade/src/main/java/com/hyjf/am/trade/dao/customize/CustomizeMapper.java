package com.hyjf.am.trade.dao.customize;


import com.hyjf.am.trade.dao.auto.AutoMapper;
import com.hyjf.am.trade.dao.mapper.customize.BatchHjhBorrowRepayCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BatchUserPortraitQueryCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.AdminAccountCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.AdminBorrowRecoverCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.AdminBorrowRepaymentCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.batch.BatchHjhAccedeCustomizeMapper;
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
    protected AdminBorrowRepaymentCustomizeMapper borrowRepaymentCustomizeMapper;

    @Resource
    protected BorrowAuthCustomizeMapper borrowAuthCustomizeMapper;
}


