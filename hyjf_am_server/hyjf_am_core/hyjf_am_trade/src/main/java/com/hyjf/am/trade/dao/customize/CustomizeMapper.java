package com.hyjf.am.trade.dao.customize;

import com.hyjf.am.trade.dao.mapper.customize.*;
import com.hyjf.am.trade.dao.mapper.customize.admin.*;
import com.hyjf.am.trade.dao.mapper.customize.app.AppProjectListCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.app.AppTenderCreditCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.coupon.CouponUserCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.*;
import com.hyjf.am.trade.dao.mapper.customize.wdzj.BorrowTenderInfoCustomizeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.trade.dao.auto.AutoMapper;
import com.hyjf.am.trade.dao.mapper.customize.batch.BatchHjhAccedeCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.batch.OntimeTenderCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.batch.TzjCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.repay.BorrowAuthCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.repay.RepayManageCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.wdzj.WDZJCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.web.AssetManageCustomizeMapper;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;

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

	@Autowired
	protected HjhReInvestDetailCustomizeMapper hjhReInvestDetailCustomizeMapper;

	@Autowired
	protected HjhReInvestDebtCustomizeMapper hjhReInvestDebtCustomizeMapper;

	@Autowired
	protected HjhDayCreditDetailCustomizeMapper hjhDayCreditDetailCustomizeMapper;

	@Autowired
	protected WebPandectCustomizeMapper webPandectCustomizeMapper;

	@Autowired
	protected AppUserInvestCustomizeMapper appUserInvestCustomizeMapper;

	@Autowired
	protected PlatformTransactionManager transactionManager;

	@Autowired
	protected TransactionDefinition transactionDefinition;

	@Autowired
	protected WebUserInvestListCustomizeMapper webUserInvestListCustomizeMapper;

	@Autowired
	protected CouponUserCustomizeMapper couponUserCustomizeMapper;

	@Autowired
	protected BatchBorrowTenderExceptionCustomizeMapper batchBorrowTenderExceptionCustomizeMapper;

	@Autowired
	protected AppAccountTradeCustomizeMapper appAccountTradeCustomizeMapper;

	@Autowired
	protected AssetListServiceCustomizeMapper assetListServiceCustomizeMapper;

	@Autowired
	protected AdminHjhLabelCustomizeMapper adminHjhLabelCustomizeMapper;

    @Autowired
    protected AdminAccountDetailCustomizeMapper adminAccountDetailCustomizeMapper;

    @Autowired
	protected BorrowFirstCustomizeMapper borrowFirstCustomizeMapper;

	@Autowired
	protected WebProjectListCustomizeMapper webProjectListCustomizeMapper;

	@Autowired
	protected AppProjectListCustomizeMapper appProjectListCustomizeMapper;

	@Autowired
	protected BorrowTenderCustomizeMapper borrowTenderCustomizeMapper;

	@Autowired
	protected BorrowTenderInfoCustomizeMapper borrowTenderInfoCustomizeMapper;

	@Autowired
	protected PushMoneyCustomizeMapper pushMoneyCustomizeMapper;

	@Autowired
	protected EveLogCustomizeMapper eveLogCustomizeMapper;

	@Autowired
	protected AleveLogCustomizeMapper aleveLogCustomizeMapper;

	@Autowired
	protected PoundageDetailCustomizeMapper poundageDetailCustomizeMapper;

	@Autowired
	protected AppTenderCreditCustomizeMapper appTenderCreditCustomizeMapper;

	@Autowired
	protected BorrowRegistCustomizeMapper borrowRegistCustomizeMapper;

	@Autowired
	protected BorrowInvestCustomizeMapper borrowInvestCustomizeMapper;

	@Autowired
	protected AleveCustomizeMapper aleveCustomizeMapper;

	@Autowired
	protected HjhAccountBalanceCustomizeMapper hjhAccountBalanceCustomizeMapper;
}
