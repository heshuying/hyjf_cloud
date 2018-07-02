package com.hyjf.am.trade.dao.customize;


import com.hyjf.am.trade.dao.auto.AutoMapper;
import com.hyjf.am.trade.dao.mapper.customize.BatchHjhBorrowRepayCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BatchUserPortraitQueryCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.AdminAccountCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.batch.BatchHjhAccedeCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.repay.RepayManageCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.*;
import com.hyjf.am.trade.dao.mapper.customize.web.AssetManageCustomizeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomizeMapper extends AutoMapper {

    @Autowired
    protected BorrowCustomizeMapper borrowCustomizeMapper;

    @Autowired
    protected HjhDebtDetailCustomizeMapper hjhDebtDetailCustomizeMapper;

    @Autowired
    protected RepayManageCustomizeMapper repayManageCustomizeMapper;

    @Autowired
    protected BatchUserPortraitQueryCustomizeMapper batchUserPortraitQueryCustomizeMapper;

    @Autowired
    protected HjhPlanCustomizeMapper hjhPlanCustomizeMapper;

    @Autowired
    protected BatchHjhAccedeCustomizeMapper batchHjhAccedeCustomizeMapper;

    @Autowired
    protected AdminAccountCustomizeMapper adminAccountCustomizeMapper;

/*    @Autowired
    protected EmployeeCustomizeMapper employeeCustomizeMapper;

    @Autowired
    protected RUserCustomizeMapper rUserCustomizeMapper;*/


    @Autowired
    protected AssetManageCustomizeMapper assetManageCustomizeMapper;

    @Autowired
    protected UserTradeDetailCustomizeMapper userTradeDetailCustomizeMapper;

    @Autowired
    protected BatchHjhBorrowRepayCustomizeMapper batchHjhBorrowRepayCustomizeMapper;
}


