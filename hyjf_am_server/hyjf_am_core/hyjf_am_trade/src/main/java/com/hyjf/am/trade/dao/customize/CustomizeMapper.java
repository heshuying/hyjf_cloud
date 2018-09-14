package com.hyjf.am.trade.dao.customize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;

import com.hyjf.am.trade.dao.auto.AutoMapper;
import com.hyjf.am.trade.dao.mapper.customize.AccountCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminAccountCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminAccountDetailCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminBankAccountCheckCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminHjhCommissionMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminHjhDebtCreditCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminHjhLabelCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AdminTransferExceptionLogCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AleveCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.ApiBorrowRepaymentInfoCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.ApiTransactionDetailsCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AppAccountTradeCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AppProjectListCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AppTenderCreditCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AppUserInvestCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AssetListServiceCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AssetManageCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.AutoReqRepayBorrowCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BankAccountManageCustomizeMapper;
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
import com.hyjf.am.trade.dao.mapper.customize.BorrowRepayAgreementCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BorrowTenderCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BorrowTenderInfoCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.CouponRecoverCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.CouponUserCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.EmployeeCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.EveLogCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.FundChangeStatisticsCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.HjhAccountBalanceCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.HjhDayCreditDetailCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.HjhDebtDetailCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.HjhPlanCapitalCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.HjhPlanCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.HjhPlanRepayCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.HjhReInvestDebtCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.HjhReInvestDetailCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.ManualReverseCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.OntimeTenderCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.PoundageDetailCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.PushMoneyCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.RUserCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.RepayManageCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.TenderCreditCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.TzjCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.UserTradeDetailCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.WDZJCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.WebCalculateInvestInterestCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.WebPandectCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.WebProjectListCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.WebUserInvestListCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.WithdrawCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.batch.BatchAccountCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.nifa.NifaContractEssenceCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.nifa.NifaContractStatusCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.nifa.NifaContractTemplateCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.nifa.NifaReceivedPaymentsCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.nifa.NifaRepayInfoCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.nifa.NifaReportLogCustomizeMapper;

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

	@Autowired
	protected FundChangeStatisticsCustomizeMapper fundChangeStatisticsCustomizeMapper;

	@Autowired
	protected BorrowRepayAgreementCustomizeMapper borrowRepayAgreementCustomizeMapper;

	@Autowired
	protected ApiBorrowRepaymentInfoCustomizeMapper apiBorrowRepaymentInfoCustomizeMapper;

	@Autowired
	protected BankAccountManageCustomizeMapper bankAccountManageCustomizeMapper;

	@Autowired
	protected AdminBankAccountCheckCustomizeMapper adminBankAccountCheckCustomizeMapper;
	@Autowired
	protected ApiProjectListCustomizeMapper apiProjectListCustomizeMapper;

	@Autowired
	protected HjhPlanRepayCustomizeMapper hjhPlanRepayCustomizeMapper;

	@Autowired
	protected NifaContractEssenceCustomizeMapper nifaContractEssenceCustomizeMapper;

	@Autowired
	protected NifaContractTemplateCustomizeMapper nifaContractTemplateCustomizeMapper;

	@Autowired
	protected NifaRepayInfoCustomizeMapper nifaRepayInfoCustomizeMapper;

	@Autowired
	protected NifaContractStatusCustomizeMapper nifaContractStatusCustomizeMapper;

	@Autowired
	protected NifaReceivedPaymentsCustomizeMapper nifaReceivedPaymentsCustomizeMapper;

	@Autowired
	protected NifaReportLogCustomizeMapper nifaReportLogCustomizeMapper;

	@Autowired
	protected AccountCustomizeMapper accountCustomizeMapper;

	@Autowired
	protected ApiTransactionDetailsCustomizeMapper apiTransactionDetailsCustomizeMapper;

	@Autowired
	protected BatchAccountCustomizeMapper batchAccountCustomizeMapper;

	@Autowired
	protected WebUserRepayListCustomizeMapper webUserRepayListCustomizeMapper;
}
