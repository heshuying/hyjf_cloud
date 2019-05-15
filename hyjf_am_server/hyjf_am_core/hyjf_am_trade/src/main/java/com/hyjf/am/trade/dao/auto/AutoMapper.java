package com.hyjf.am.trade.dao.auto;


import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.mapper.customize.BorrowFullCustomizeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AutoMapper {

    @Resource
    protected AccountMapper accountMapper;

    @Resource
    protected AccountBorrowMapper accountBorrowMapper;

    @Resource
    protected AccountDirectionalTransferMapper accountDirectionalTransferMapper;

    @Resource
    protected AccountListMapper accountListMapper;

    @Resource
    protected AccountRechargeMapper accountRechargeMapper;

    @Resource
    protected AccountTradeMapper accountTradeMapper;

    @Resource
    protected AccountWithdrawMapper accountWithdrawMapper;

    @Resource
    protected AleveErrorLogMapper aleveErrorLogMapper;

    @Resource
    protected AleveLogMapper aleveLogMapper;

    @Resource
    protected BankCreditEndMapper bankCreditEndMapper;

    @Resource
    protected BankMerchantAccountMapper bankMerchantAccountMapper;

    @Resource
    protected BankMerchantAccountInfoMapper bankMerchantAccountInfoMapper;

    @Resource
    protected BankMerchantAccountListMapper bankMerchantAccountListMapper;

    @Resource
    protected BankRepayFreezeLogMapper bankRepayFreezeLogMapper;

    @Resource
    protected BankRepayOrgFreezeLogMapper bankRepayOrgFreezeLogMapper;

    @Resource
    protected BorrowMapper borrowMapper;

    @Resource
    protected BorrowApicronMapper borrowApicronMapper;

    @Resource
    protected BorrowApicronLogMapper borrowApicronLogMapper;

    @Resource
    protected BorrowBailMapper borrowBailMapper;

    @Resource
    protected BorrowCarinfoMapper borrowCarinfoMapper;

    @Resource
    protected BorrowCompanyAuthenMapper borrowCompanyAuthenMapper;

    @Resource
    protected BorrowConfigMapper borrowConfigMapper;

    @Resource
    protected BorrowConsumeMapper borrowConsumeMapper;

    @Resource
    protected BorrowCreditMapper borrowCreditMapper;

    @Resource
    protected BorrowFinhxfmanChargeMapper borrowFinhxfmanChargeMapper;

    @Resource
    protected BorrowFinmanNewChargeMapper borrowFinmanNewChargeMapper;

    @Resource
    protected BorrowFinserChargeMapper borrowFinserChargeMapper;

    @Resource
    protected BorrowHousesMapper borrowHousesMapper;

    @Resource
    protected BorrowInfoMapper borrowInfoMapper;

    @Resource
    protected BorrowLogMapper borrowLogMapper;

    @Resource
    protected BorrowManinfoMapper borrowManinfoMapper;

    @Resource
    protected BorrowProjectRepayMapper borrowProjectRepayMapper;

    @Resource
    protected BorrowProjectTypeMapper borrowProjectTypeMapper;

    @Resource
    protected BorrowRecoverMapper borrowRecoverMapper;

    @Resource
    protected BorrowRecoverPlanMapper borrowRecoverPlanMapper;

    @Resource
    protected BorrowRepayMapper borrowRepayMapper;

    @Resource
    protected BorrowRepayPlanMapper borrowRepayPlanMapper;

    @Resource
    protected BorrowSendTypeMapper borrowSendTypeMapper;

    @Resource
    protected BorrowStyleMapper borrowStyleMapper;

    @Resource
    protected BorrowTenderMapper borrowTenderMapper;

    @Resource
    protected BorrowTenderCpnMapper borrowTenderCpnMapper;

    @Resource
    protected BorrowTenderTmpMapper borrowTenderTmpMapper;

    @Resource
    protected BorrowTenderTmpinfoMapper borrowTenderTmpinfoMapper;

    @Resource
    protected BorrowUserMapper borrowUserMapper;

    @Resource
    protected ConsumeMapper consumeMapper;

    @Resource
    protected ConsumeListMapper consumeListMapper;

    @Resource
    protected CouponConfigMapper couponConfigMapper;

    @Resource
    protected CouponOperationHistoryMapper couponOperationHistoryMapper;

    @Resource
    protected CouponRealTenderMapper couponRealTenderMapper;

    @Resource
    protected CouponRecoverMapper couponRecoverMapper;

    @Resource
    protected CouponRepayMonitorMapper couponRepayMonitorMapper;

    @Resource
    protected CouponTenderMapper couponTenderMapper;

    @Resource
    protected CouponUserMapper couponUserMapper;

    @Resource
    protected CreditRepayMapper creditRepayMapper;

    @Resource
    protected CreditRepayLogMapper creditRepayLogMapper;

    @Resource
    protected CreditTenderMapper creditTenderMapper;

    @Resource
    protected CreditTenderLogMapper creditTenderLogMapper;

    @Resource
    protected DebtAccedeCommissionMapper debtAccedeCommissionMapper;

    @Resource
    protected DebtAccountListMapper debtAccountListMapper;

    @Resource
    protected DebtApicronMapper debtApicronMapper;

    @Resource
    protected DebtBailMapper debtBailMapper;

    @Resource
    protected DebtBorrowMapper debtBorrowMapper;

    @Resource
    protected DebtCarInfoMapper debtCarInfoMapper;

    @Resource
    protected DebtCommissionConfigMapper debtCommissionConfigMapper;

    @Resource
    protected DebtCompanyAuthenMapper debtCompanyAuthenMapper;

    @Resource
    protected DebtCompanyInfoMapper debtCompanyInfoMapper;

    @Resource
    protected DebtCreditMapper debtCreditMapper;

    @Resource
    protected DebtCreditRepayMapper debtCreditRepayMapper;

    @Resource
    protected DebtCreditTenderMapper debtCreditTenderMapper;

    @Resource
    protected DebtCreditTenderLogMapper debtCreditTenderLogMapper;

    @Resource
    protected DebtDeleteLogMapper debtDeleteLogMapper;

    @Resource
    protected DebtDetailMapper debtDetailMapper;

    @Resource
    protected DebtFreezeMapper debtFreezeMapper;

    @Resource
    protected DebtFreezeLogMapper debtFreezeLogMapper;

    @Resource
    protected DebtHouseInfoMapper debtHouseInfoMapper;

    @Resource
    protected DebtInvestMapper debtInvestMapper;

    @Resource
    protected DebtInvestLogMapper debtInvestLogMapper;

    @Resource
    protected DebtLoanMapper debtLoanMapper;

    @Resource
    protected DebtLoanDetailMapper debtLoanDetailMapper;

    @Resource
    protected DebtLoanLogMapper debtLoanLogMapper;

    @Resource
    protected DebtPlanMapper debtPlanMapper;

    @Resource
    protected DebtPlanAccedeMapper debtPlanAccedeMapper;

    @Resource
    protected DebtPlanBorrowMapper debtPlanBorrowMapper;

    @Resource
    protected DebtPlanConfigMapper debtPlanConfigMapper;

    @Resource
    protected DebtPlanInfoStaticMapper debtPlanInfoStaticMapper;

    @Resource
    protected DebtPlanInfoStaticCountMapper debtPlanInfoStaticCountMapper;

    @Resource
    protected DebtRepayMapper debtRepayMapper;

    @Resource
    protected DebtRepayDetailMapper debtRepayDetailMapper;

    @Resource
    protected DebtUserInfoMapper debtUserInfoMapper;

    @Resource
    protected EveLogMapper eveLogMapper;

    @Resource
    protected FddTempletMapper fddTempletMapper;

    @Resource
    protected FreezeHistoryMapper freezeHistoryMapper;

    @Resource
    protected FreezeListMapper freezeListMapper;

    @Resource
    protected HjhAccedeMapper hjhAccedeMapper;

    @Resource
    protected HjhAllocationEngineMapper hjhAllocationEngineMapper;

    @Resource
    protected HjhAssetBorrowtypeMapper hjhAssetBorrowtypeMapper;

    @Resource
    protected HjhAssetTypeMapper hjhAssetTypeMapper;

    @Resource
    protected HjhDebtCreditMapper hjhDebtCreditMapper;

    @Resource
    protected HjhDebtCreditRepayMapper hjhDebtCreditRepayMapper;

    @Resource
    protected HjhDebtCreditTenderMapper hjhDebtCreditTenderMapper;

    @Resource
    protected HjhDebtCreditTenderLogMapper hjhDebtCreditTenderLogMapper;

    @Resource
    protected HjhDebtDetailMapper hjhDebtDetailMapper;

    @Resource
    protected HjhInstConfigMapper hjhInstConfigMapper;

    @Resource
    protected HjhLabelMapper hjhLabelMapper;

    @Resource
    protected HjhPlanMapper hjhPlanMapper;

    @Resource
    protected HjhPlanAssetMapper hjhPlanAssetMapper;

    @Resource
    protected HjhPlanBorrowTmpMapper hjhPlanBorrowTmpMapper;

    @Resource
    protected HjhRegionMapper hjhRegionMapper;

    @Resource
    protected HjhRepayMapper hjhRepayMapper;

    @Resource
    protected IncreaseInterestInvestMapper increaseInterestInvestMapper;

    @Resource
    protected IncreaseInterestLoanMapper increaseInterestLoanMapper;

    @Resource
    protected IncreaseInterestLoanDetailMapper increaseInterestLoanDetailMapper;

    @Resource
    protected IncreaseInterestRepayMapper increaseInterestRepayMapper;

    @Resource
    protected IncreaseInterestRepayDetailMapper increaseInterestRepayDetailMapper;

    @Resource
    protected ManualReverseMapper manualReverseMapper;

    @Resource
    protected MerchantAccountMapper merchantAccountMapper;

    @Resource
    protected MerchantTransferMapper merchantTransferMapper;

    @Resource
    protected PoundageMapper poundageMapper;

    @Resource
    protected PoundageDetailMapper poundageDetailMapper;

    @Resource
    protected PoundageExceptionMapper poundageExceptionMapper;

    @Resource
    protected PoundageLedgerMapper poundageLedgerMapper;

    @Resource
    protected ProductMapper productMapper;

    @Resource
    protected ProductErrorLogMapper productErrorLogMapper;

    @Resource
    protected ProductInfoMapper productInfoMapper;

    @Resource
    protected ProductInterestMapper productInterestMapper;

    @Resource
    protected ProductListMapper productListMapper;

    @Resource
    protected ProductListLogMapper productListLogMapper;

    @Resource
    protected ProductRedeemMapper productRedeemMapper;

    @Resource
    protected ProductRedeemDayMapper productRedeemDayMapper;

    @Resource
    protected ProductRedeemFailMapper productRedeemFailMapper;

    @Resource
    protected ProductRedeemListMapper productRedeemListMapper;

    @Resource
    protected ProductRewardListMapper productRewardListMapper;

    @Resource
    protected ProductUserMapper productUserMapper;

    @Resource
    protected PushMoneyMapper pushMoneyMapper;

    @Resource
    protected ROaDepartmentMapper rOaDepartmentMapper;

    @Resource
    protected ROaUsersMapper rOaUsersMapper;

    @Resource
    protected RUserMapper rUserMapper;

    @Resource
    protected StzhWhiteListMapper stzhWhiteListMapper;

    @Resource
    protected SubCommissionMapper subCommissionMapper;

    @Resource
    protected SubCommissionListConfigMapper subCommissionListConfigMapper;

    @Resource
    protected TenderAgreementMapper tenderAgreementMapper;

    @Resource
    protected TenderBackHistoryMapper tenderBackHistoryMapper;

    @Resource
    protected TenderCommissionMapper tenderCommissionMapper;

    @Resource
    protected TransferExceptionLogMapper transferExceptionLogMapper;

    @Resource
    protected UserTransferMapper userTransferMapper;

    @Resource
    protected WkcdBorrowMapper wkcdBorrowMapper;

    @Resource
    protected HjhAssetBorrowtypeMapper hjhAssetBorrowTypeMapper;

    @Resource
    protected StzhWhiteListMapper sTZHWhiteListMapper;

    @Resource
    protected BorrowFullCustomizeMapper borrowFullCustomizeMapper;
    
    @Resource
    protected CommissionLogMapper commissionLogMapper;

    @Resource
    protected ApplyAgreementMapper applyAgreementMapper;

    @Resource
    protected ApplyAgreementInfoMapper applyAgreementInfoMapper;

    @Resource
    protected UnderLineRechargeMapper underLineRechargeMapper;

    @Resource
    protected NifaReportLogMapper nifaReportLogMapper;

    @Resource
    protected NifaContractStatusMapper nifaContractStatusMapper;

    @Resource
    protected NifaContractEssenceMapper nifaContractEssenceMapper;

    @Resource
    protected NifaContractTemplateMapper nifaContractTemplateMapper;

    @Resource
    protected NifaFieldDefinitionMapper nifaFieldDefinitionMapper;

    @Resource
    protected NifaRepayInfoMapper nifaRepayInfoMapper;

    @Resource
    protected NifaReceivedPaymentsMapper nifaReceivedPaymentsMapper;

    @Resource
    protected HjhBailConfigMapper hjhBailConfigMapper;

    @Resource
    protected HjhBailConfigInfoMapper hjhBailConfigInfoMapper;

    @Resource
    protected AppPushManageMapper appPushManageMapper;
}

