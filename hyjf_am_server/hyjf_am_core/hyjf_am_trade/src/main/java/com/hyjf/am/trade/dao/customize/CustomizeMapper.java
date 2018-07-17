package com.hyjf.am.trade.dao.customize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.trade.dao.auto.AutoMapper;
import com.hyjf.am.trade.dao.mapper.customize.BatchHjhBorrowRepayCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BatchUserPortraitQueryCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.AdminAccountCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.AdminBorrowLogCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.AdminBorrowRecoverCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.AdminBorrowRepaymentCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.AdminBorrowRepaymentInfoCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.AdminBorrowRepaymentInfoListCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.AdminHjhDebtCreditCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.AdminTransferExceptionLogCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.BatchCenterCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.admin.WithdrawCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.batch.BatchHjhAccedeCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.batch.OntimeTenderCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.batch.TzjCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.repay.BorrowAuthCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.repay.RepayManageCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.AutoReqRepayBorrowCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.BorrowCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.CouponRecoverCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.EmployeeCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.HjhDebtDetailCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.HjhPlanCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.ManualReverseCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.RUserCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.TenderCreditCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.UserTradeDetailCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.WebCalculateInvestInterestCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.wdzj.WDZJCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.web.AssetManageCustomizeMapper;

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

	@Autowired
	protected EmployeeCustomizeMapper employeeCustomizeMapper;

	@Autowired
	protected RUserCustomizeMapper rUserCustomizeMapper;

	@Autowired
	protected AssetManageCustomizeMapper assetManageCustomizeMapper;

	@Autowired
	protected UserTradeDetailCustomizeMapper userTradeDetailCustomizeMapper;

	@Autowired
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
	
	@Autowired
	protected AdminHjhDebtCreditCustomizeMapper adminHjhDebtCreditCustomizeMapper;

	@Autowired
	protected BorrowAuthCustomizeMapper borrowAuthCustomizeMapper;

	@Autowired
	protected BatchCenterCustomizeMapper batchCenterCustomizeMapper;

	@Autowired
	protected TzjCustomizeMapper tzjCustomizeMapper;

	@Autowired
	protected AdminTransferExceptionLogCustomizeMapper transferExceptionLogCustomizeMapper;

	@Autowired
	protected CouponRecoverCustomizeMapper couponRecoverCustomizeMapper;

	@Autowired
	protected OntimeTenderCustomizeMapper ontimeTenderCustomizeMapper;

	@Autowired
	protected WithdrawCustomizeMapper withdrawCustomizeMapper;

	@Autowired
	protected AutoReqRepayBorrowCustomizeMapper autoReqRepayBorrowCustomizeMapper;

	@Autowired
	protected ManualReverseCustomizeMapper manualReverseCustomizeMapper;
	
	@Autowired
	protected WDZJCustomizeMapper wdzjCustomizeMapper;

	@Autowired
	protected TenderCreditCustomizeMapper tenderCreditCustomizeMapper;

	@Autowired
	protected WebCalculateInvestInterestCustomizeMapper webCalculateInvestInterestCustomizeMapper;

}
