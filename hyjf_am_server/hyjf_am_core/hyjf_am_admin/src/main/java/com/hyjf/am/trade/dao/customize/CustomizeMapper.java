package com.hyjf.am.trade.dao.customize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import com.hyjf.am.trade.dao.auto.AutoMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminAccountCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminAccountDetailCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminBorrowLogCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminBorrowRecoverCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminBorrowRepaymentCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminBorrowRepaymentInfoCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminBorrowRepaymentInfoListCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminHjhCommissionMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminHjhDebtCreditCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminHjhLabelCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminTransferExceptionLogCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AleveCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AppAccountTradeCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AppProjectListCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AppTenderCreditCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AppUserInvestCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AssetListServiceCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AssetManageCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AutoReqRepayBorrowCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BatchBorrowTenderExceptionCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BatchCenterCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BatchHjhAccedeCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BatchHjhBorrowRepayCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BatchUserPortraitQueryCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BorrowAuthCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BorrowCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BorrowFirstCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BorrowInvestCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BorrowRegistCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BorrowTenderCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BorrowTenderInfoCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.CouponRecoverCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.CouponUserCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.EveLogCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.HjhAccountBalanceCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.HjhDayCreditDetailCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.HjhDebtDetailCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.HjhPlanCapitalCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.HjhPlanCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.HjhReInvestDebtCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.HjhReInvestDetailCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.ManualReverseCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.OntimeTenderCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.PoundageDetailCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.PushMoneyCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.RUserCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.RepayManageCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.TenderCreditCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.UserTradeDetailCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.WDZJCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.WebCalculateInvestInterestCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.WebPandectCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.WebProjectListCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.WebUserInvestListCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.WithdrawCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.EmployeeCustomizeMapper;
import com.hyjf.am.user.dao.mapper.customize.TzjCustomizeMapper;

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
	protected HjhPlanCapitalCustomizeMapper hjhPlanCapitalCustomizeMapper;

	@Autowired
	protected HjhAccountBalanceCustomizeMapper hjhAccountBalanceCustomizeMapper;

	@Autowired
	protected PushMoneyCustomizeMapper pushMoneyCustomizeMapper;

	@Autowired
	protected EveLogCustomizeMapper eveLogCustomizeMapper;

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
	protected AdminHjhCommissionMapper adminHjhCommissionMapper;

}
