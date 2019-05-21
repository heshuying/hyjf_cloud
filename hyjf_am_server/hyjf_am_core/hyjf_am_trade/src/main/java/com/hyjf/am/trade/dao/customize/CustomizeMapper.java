package com.hyjf.am.trade.dao.customize;

import com.hyjf.am.trade.dao.auto.AutoMapper;
import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.mapper.customize.*;
import com.hyjf.am.trade.dao.mapper.customize.admin.SmsCodeCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.batch.BatchAccountCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.batch.BorrowRepayLateMapper;
import com.hyjf.am.trade.dao.mapper.customize.batch.BorrowUserStatisticsMapper;
import com.hyjf.am.trade.dao.mapper.customize.hgreportdata.bifa.*;
import com.hyjf.am.trade.dao.mapper.customize.hgreportdata.cert.CertMapper;
import com.hyjf.am.trade.dao.mapper.customize.hgreportdata.nifa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;

@Service
public class CustomizeMapper extends AutoMapper {

	@Autowired
	protected BorrowCustomizeMapper borrowCustomizeMapper;

	@Autowired
	protected BatchBorrowRecoverCustomizeMapper batchBorrowRecoverCustomizeMapper;

	@Autowired
	protected BifaBorrowCustomizeMapper bifaBorrowCustomizeMapper;

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
	protected BifaBorrowTenderInfoCustomizeMapper bifaBorrowTenderInfoCustomizeMapper;

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
	protected ApiRepayListCustomizeMapper apiRepayListCustomizeMapper;

	@Autowired
	protected WebUserRepayListCustomizeMapper webUserRepayListCustomizeMapper;

	@Autowired
	protected BorrowRecoverPlanMapper borrowRecoverPlanMapper;

	@Autowired
	protected ApiBailConfigInfoCustomizeMapper apiBailConfigInfoCustomizeMapper;

	@Autowired
	protected HjhBailConfigCustomizeMapper hjhBailConfigCustomizeMapper;
	@Autowired
	protected HjhPlanRepayCustomizeMapper hjhPlanRepayCustomizeMapper;

	@Autowired
	protected AemsBorrowRepayPlanCustomizeMapper aemsBorrowRepayPlanCustomizeMapper;

	@Autowired
	protected HjhAssetRiskInfoMapper hjhAssetRiskInfoMapper;

	@Autowired
	protected EvaluationConfigMapper evaluationConfigMapper;

	@Autowired
	protected SmsCodeCustomizeMapper smsCodeCustomizeMapper;

	@Autowired
	protected NifaStatisticalCustomizeMapper nifaStatisticalCustomizeMapper;

	@Autowired
	protected CertMapper certMapper;

	@Autowired
	protected BifaBorrowRecoverCustomizeMapper bifaBorrowRecoverCustomizeMapper;

	@Autowired
	protected BifaCreditTenderCustomizeMapper bifaCreditTenderCustomizeMapper;

	@Autowired
	protected BifaHjhDebtCreditTenderCustomizeMapper bifaHjhDebtCreditTenderCustomizeMapper;

	@Autowired
	protected OperationReportJobCustomizeMapper operationReportJobCustomizeMapper;

	@Autowired
	protected BifaOperationReportJobCustomizeMapper bifaOperationReportJobCustomizeMapper;

	@Autowired
	protected BorrowUserStatisticsMapper borrowUserStatisticsMapper;
	@Autowired
	protected UserLargeScreenCustomizeMapper userLargeScreenCustomizeMapper;

	@Autowired
	protected BorrowRepayLateMapper borrowRepayLateMapper;

	@Autowired
	protected WjtProjectListCustomizeMapper wjtProjectListCustomizeMapper;

	@Autowired
	protected CertClaimMapper certClaimMapper;
}
