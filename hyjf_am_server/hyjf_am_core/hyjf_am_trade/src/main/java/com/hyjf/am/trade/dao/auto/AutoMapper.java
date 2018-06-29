package com.hyjf.am.trade.dao.auto;


import com.hyjf.am.trade.dao.mapper.auto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutoMapper {

    @Autowired
    protected AccountMapper accountMapper;

    @Autowired
    protected AccountBorrowMapper accountBorrowMapper;

    @Autowired
    protected AccountDirectionalTransferMapper accountDirectionalTransferMapper;

    @Autowired
    protected AccountListMapper accountListMapper;

    @Autowired
    protected AccountRechargeMapper accountRechargeMapper;

    @Autowired
    protected AccountTradeMapper accountTradeMapper;

    @Autowired
    protected AccountWithdrawMapper accountWithdrawMapper;

    @Autowired
    protected AleveErrorLogMapper aleveErrorLogMapper;

    @Autowired
    protected AleveLogMapper aleveLogMapper;

    @Autowired
    protected BankCreditEndMapper bankCreditEndMapper;

    @Autowired
    protected BankMerchantAccountMapper bankMerchantAccountMapper;

    @Autowired
    protected BankMerchantAccountInfoMapper bankMerchantAccountInfoMapper;

    @Autowired
    protected BankMerchantAccountListMapper bankMerchantAccountListMapper;

    @Autowired
    protected BankRepayFreezeLogMapper bankRepayFreezeLogMapper;

    @Autowired
    protected BorrowMapper borrowMapper;

    @Autowired
    protected BorrowApicronMapper borrowApicronMapper;

    @Autowired
    protected BorrowBailMapper borrowBailMapper;

    @Autowired
    protected BorrowCarinfoMapper borrowCarinfoMapper;

    @Autowired
    protected BorrowCompanyAuthenMapper borrowCompanyAuthenMapper;

    @Autowired
    protected BorrowConfigMapper borrowConfigMapper;

    @Autowired
    protected BorrowConsumeMapper borrowConsumeMapper;

    @Autowired
    protected BorrowCreditMapper borrowCreditMapper;

    @Autowired
    protected BorrowFinhxfmanChargeMapper borrowFinhxfmanChargeMapper;

    @Autowired
    protected BorrowFinmanNewChargeMapper borrowFinmanNewChargeMapper;

    @Autowired
    protected BorrowFinserChargeMapper borrowFinserChargeMapper;

    @Autowired
    protected BorrowHousesMapper borrowHousesMapper;

    @Autowired
    protected BorrowInfoMapper borrowInfoMapper;

    @Autowired
    protected BorrowLogMapper borrowLogMapper;

    @Autowired
    protected BorrowManinfoMapper borrowManinfoMapper;

    @Autowired
    protected BorrowProjectRepayMapper borrowProjectRepayMapper;

    @Autowired
    protected BorrowProjectTypeMapper borrowProjectTypeMapper;

    @Autowired
    protected BorrowRecoverMapper borrowRecoverMapper;

    @Autowired
    protected BorrowRecoverPlanMapper borrowRecoverPlanMapper;

    @Autowired
    protected BorrowRepayMapper borrowRepayMapper;

    @Autowired
    protected BorrowRepayPlanMapper borrowRepayPlanMapper;

    @Autowired
    protected BorrowSendTypeMapper borrowSendTypeMapper;

    @Autowired
    protected BorrowStyleMapper borrowStyleMapper;

    @Autowired
    protected BorrowTenderMapper borrowTenderMapper;

    @Autowired
    protected BorrowTenderCpnMapper borrowTenderCpnMapper;

    @Autowired
    protected BorrowTenderTmpMapper borrowTenderTmpMapper;

    @Autowired
    protected BorrowTenderTmpinfoMapper borrowTenderTmpinfoMapper;

    @Autowired
    protected BorrowTypeMapper borrowTypeMapper;

    @Autowired
    protected BorrowUserMapper borrowUserMapper;

    @Autowired
    protected CalculateInvestInterestMapper calculateInvestInterestMapper;

    @Autowired
    protected ChinapnrExclusiveLogMapper chinapnrExclusiveLogMapper;

    @Autowired
    protected ChinapnrLogMapper chinapnrLogMapper;

    @Autowired
    protected ChinapnrSendLogMapper chinapnrSendLogMapper;

    @Autowired
    protected ConsumeMapper consumeMapper;

    @Autowired
    protected ConsumeListMapper consumeListMapper;

    @Autowired
    protected CouponConfigMapper couponConfigMapper;

    @Autowired
    protected CouponOperationHistoryMapper couponOperationHistoryMapper;

    @Autowired
    protected CouponRealTenderMapper couponRealTenderMapper;

    @Autowired
    protected CouponRecoverMapper couponRecoverMapper;

    @Autowired
    protected CouponRepayMonitorMapper couponRepayMonitorMapper;

    @Autowired
    protected CouponTenderMapper couponTenderMapper;

    @Autowired
    protected CouponUserMapper couponUserMapper;

    @Autowired
    protected CreditRepayMapper creditRepayMapper;

    @Autowired
    protected CreditRepayLogMapper creditRepayLogMapper;

    @Autowired
    protected CreditTenderMapper creditTenderMapper;

    @Autowired
    protected CreditTenderLogMapper creditTenderLogMapper;

    @Autowired
    protected DebtAccedeCommissionMapper debtAccedeCommissionMapper;

    @Autowired
    protected DebtAccountListMapper debtAccountListMapper;

    @Autowired
    protected DebtApicronMapper debtApicronMapper;

    @Autowired
    protected DebtBailMapper debtBailMapper;

    @Autowired
    protected DebtBorrowMapper debtBorrowMapper;

    @Autowired
    protected DebtCarInfoMapper debtCarInfoMapper;

    @Autowired
    protected DebtCommissionConfigMapper debtCommissionConfigMapper;

    @Autowired
    protected DebtCompanyAuthenMapper debtCompanyAuthenMapper;

    @Autowired
    protected DebtCompanyInfoMapper debtCompanyInfoMapper;

    @Autowired
    protected DebtCreditMapper debtCreditMapper;

    @Autowired
    protected DebtCreditRepayMapper debtCreditRepayMapper;

    @Autowired
    protected DebtCreditTenderMapper debtCreditTenderMapper;

    @Autowired
    protected DebtCreditTenderLogMapper debtCreditTenderLogMapper;

    @Autowired
    protected DebtDeleteLogMapper debtDeleteLogMapper;

    @Autowired
    protected DebtDetailMapper debtDetailMapper;

    @Autowired
    protected DebtFreezeMapper debtFreezeMapper;

    @Autowired
    protected DebtFreezeLogMapper debtFreezeLogMapper;

    @Autowired
    protected DebtHouseInfoMapper debtHouseInfoMapper;

    @Autowired
    protected DebtInvestMapper debtInvestMapper;

    @Autowired
    protected DebtInvestLogMapper debtInvestLogMapper;

    @Autowired
    protected DebtLoanMapper debtLoanMapper;

    @Autowired
    protected DebtLoanDetailMapper debtLoanDetailMapper;

    @Autowired
    protected DebtLoanLogMapper debtLoanLogMapper;

    @Autowired
    protected DebtPlanMapper debtPlanMapper;

    @Autowired
    protected DebtPlanAccedeMapper debtPlanAccedeMapper;

    @Autowired
    protected DebtPlanBorrowMapper debtPlanBorrowMapper;

    @Autowired
    protected DebtPlanConfigMapper debtPlanConfigMapper;

    @Autowired
    protected DebtPlanInfoStaticMapper debtPlanInfoStaticMapper;

    @Autowired
    protected DebtPlanInfoStaticCountMapper debtPlanInfoStaticCountMapper;

    @Autowired
    protected DebtRepayMapper debtRepayMapper;

    @Autowired
    protected DebtRepayDetailMapper debtRepayDetailMapper;

    @Autowired
    protected DebtUserInfoMapper debtUserInfoMapper;

    @Autowired
    protected EveLogMapper eveLogMapper;

    @Autowired
    protected FddTempletMapper fddTempletMapper;

    @Autowired
    protected FreezeHistoryMapper freezeHistoryMapper;

    @Autowired
    protected FreezeListMapper freezeListMapper;

    @Autowired
    protected HjhAccedeMapper hjhAccedeMapper;

    @Autowired
    protected HjhAllocationEngineMapper hjhAllocationEngineMapper;

    @Autowired
    protected HjhAssetBorrowtypeMapper hjhAssetBorrowtypeMapper;

    @Autowired
    protected HjhAssetTypeMapper hjhAssetTypeMapper;

    @Autowired
    protected HjhDebtCreditMapper hjhDebtCreditMapper;

    @Autowired
    protected HjhDebtCreditRepayMapper hjhDebtCreditRepayMapper;

    @Autowired
    protected HjhDebtCreditTenderMapper hjhDebtCreditTenderMapper;

    @Autowired
    protected HjhDebtCreditTenderLogMapper hjhDebtCreditTenderLogMapper;

    @Autowired
    protected HjhDebtDetailMapper hjhDebtDetailMapper;

    @Autowired
    protected HjhInstConfigMapper hjhInstConfigMapper;

    @Autowired
    protected HjhLabelMapper hjhLabelMapper;

    @Autowired
    protected HjhPlanMapper hjhPlanMapper;

    @Autowired
    protected HjhPlanAssetMapper hjhPlanAssetMapper;

    @Autowired
    protected HjhPlanBorrowTmpMapper hjhPlanBorrowTmpMapper;

    @Autowired
    protected HjhRegionMapper hjhRegionMapper;

    @Autowired
    protected HjhRepayMapper hjhRepayMapper;

    @Autowired
    protected IncreaseInterestInvestMapper increaseInterestInvestMapper;

    @Autowired
    protected IncreaseInterestLoanMapper increaseInterestLoanMapper;

    @Autowired
    protected IncreaseInterestLoanDetailMapper increaseInterestLoanDetailMapper;

    @Autowired
    protected IncreaseInterestRepayMapper increaseInterestRepayMapper;

    @Autowired
    protected IncreaseInterestRepayDetailMapper increaseInterestRepayDetailMapper;

    @Autowired
    protected ManualReverseMapper manualReverseMapper;

    @Autowired
    protected MerchantAccountMapper merchantAccountMapper;

    @Autowired
    protected MerchantTransferMapper merchantTransferMapper;

    @Autowired
    protected PoundageMapper poundageMapper;

    @Autowired
    protected PoundageDetailMapper poundageDetailMapper;

    @Autowired
    protected PoundageExceptionMapper poundageExceptionMapper;

    @Autowired
    protected PoundageLedgerMapper poundageLedgerMapper;

    @Autowired
    protected ProducerTransactionMessageMapper producerTransactionMessageMapper;

    @Autowired
    protected ProductMapper productMapper;

    @Autowired
    protected ProductErrorLogMapper productErrorLogMapper;

    @Autowired
    protected ProductInfoMapper productInfoMapper;

    @Autowired
    protected ProductInterestMapper productInterestMapper;

    @Autowired
    protected ProductListMapper productListMapper;

    @Autowired
    protected ProductListLogMapper productListLogMapper;

    @Autowired
    protected ProductRedeemMapper productRedeemMapper;

    @Autowired
    protected ProductRedeemDayMapper productRedeemDayMapper;

    @Autowired
    protected ProductRedeemFailMapper productRedeemFailMapper;

    @Autowired
    protected ProductRedeemListMapper productRedeemListMapper;

    @Autowired
    protected ProductRewardListMapper productRewardListMapper;

    @Autowired
    protected ProductUserMapper productUserMapper;

    @Autowired
    protected PushMoneyMapper pushMoneyMapper;

    @Autowired
    protected ROaDepartmentMapper rOaDepartmentMapper;

    @Autowired
    protected ROaUsersMapper rOaUsersMapper;

    @Autowired
    protected RUserMapper rUserMapper;

    @Autowired
    protected StzhWhiteListMapper stzhWhiteListMapper;

    @Autowired
    protected SubCommissionMapper subCommissionMapper;

    @Autowired
    protected SubCommissionListConfigMapper subCommissionListConfigMapper;

    @Autowired
    protected TenderAgreementMapper tenderAgreementMapper;

    @Autowired
    protected TenderBackHistoryMapper tenderBackHistoryMapper;

    @Autowired
    protected TenderCommissionMapper tenderCommissionMapper;

    @Autowired
    protected TransferExceptionLogMapper transferExceptionLogMapper;

    @Autowired
    protected UserTransferMapper userTransferMapper;

    @Autowired
    protected WkcdBorrowMapper wkcdBorrowMapper;
}

