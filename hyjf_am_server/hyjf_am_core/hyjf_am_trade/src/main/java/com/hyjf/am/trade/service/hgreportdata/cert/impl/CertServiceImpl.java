/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.hgreportdata.cert.impl;

import com.hyjf.am.resquest.hgreportdata.cert.CertRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.CertAccountListCustomize;
import com.hyjf.am.trade.dao.model.customize.CertAccountListIdCustomize;
import com.hyjf.am.trade.service.hgreportdata.cert.CertService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.GetDate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */
@Service
public class CertServiceImpl extends BaseServiceImpl implements CertService {


    @Override
    public List<CertAccountListCustomize> queryCertAccountList(CertRequest certTransactRequest) {
        Map<String, Object> map =new HashMap<String, Object>();
        map.put("minId", certTransactRequest.getMinId());
        map.put("maxId", certTransactRequest.getMaxId());
        List<CertAccountListCustomize> accountLists=certMapper.queryCertAccountList(map);
        return accountLists;
    }

    @Override
    public List<AccountList> getAccountListVOListByRequest(CertRequest certTransactRequest) {
        List<String> tradeList=certTransactRequest.getTradeList();
        AccountListExample accountListExample=new AccountListExample();
        accountListExample.createCriteria().andTradeIn(tradeList).andRemarkEqualTo(certTransactRequest.getBorrowNid());
        List<AccountList> tenderList=accountListMapper.selectByExample(accountListExample);
        return tenderList;
    }

    @Override
    public List<BorrowRepay> getBorrowRepayListByRequest(CertRequest certRequest) {
        BorrowRepayExample borrowRepayExample = new BorrowRepayExample();
        borrowRepayExample.createCriteria().andNidEqualTo(certRequest.getNid()).andBorrowNidEqualTo(certRequest.getBorrowNid());
        List<BorrowRepay> borrowRepays = borrowRepayMapper.selectByExample(borrowRepayExample);
        return borrowRepays;
    }

    @Override
    public List<BorrowRepayPlan> getBorrowRepayPlanListByRequest(CertRequest certRequest) {
        Integer timestamp=GetDate.getTime10(certRequest.getRepayYestime());
        BorrowRepayPlanExample borrowRepayPlanExample=new BorrowRepayPlanExample();
        borrowRepayPlanExample.createCriteria().andRepayYestimeBetween(timestamp-60, timestamp).
                andBorrowNidEqualTo(certRequest.getBorrowNid());
        List<BorrowRepayPlan> borrowRepayPlans=borrowRepayPlanMapper.selectByExample(borrowRepayPlanExample);
        return borrowRepayPlans;
    }

    @Override
    public List<CouponRecover> getCouponRecoverListByCertRequest(CertRequest certRequest) {
        CouponRecoverExample couponRecoverExample=new CouponRecoverExample();
        couponRecoverExample.createCriteria().andTransferIdEqualTo(certRequest.getTransferId());
        List<CouponRecover> couponRecovers=couponRecoverMapper.selectByExample(couponRecoverExample);
        return couponRecovers;
    }

    @Override
    public List<BorrowTenderCpn> getBorrowTenderCpnListByCertRequest(CertRequest certRequest) {
        BorrowTenderCpnExample borrowTenderCpnExample=new BorrowTenderCpnExample();
        borrowTenderCpnExample.createCriteria().andNidEqualTo(certRequest.getCouponTenderId());
        List<BorrowTenderCpn> borrowTenderCpnList=borrowTenderCpnMapper.selectByExample(borrowTenderCpnExample);
        return borrowTenderCpnList;
    }

    @Override
    public List<CouponRealTender> getCouponRealTenderListByCertRequest(CertRequest certRequest) {
        CouponRealTenderExample couponRealTenderExample=new CouponRealTenderExample();
        couponRealTenderExample.createCriteria().andCouponTenderIdEqualTo(certRequest.getCouponTenderId());
        List<CouponRealTender> couponRealTenders=couponRealTenderMapper.selectByExample(couponRealTenderExample);
        return couponRealTenders;
    }

    @Override
    public List<BorrowRecover> selectBorrowRecoverListByRequest(CertRequest certRequest) {
        BorrowRecoverExample borrowRecoverExample=new BorrowRecoverExample();
        borrowRecoverExample.createCriteria().andRepayOrdidEqualTo(certRequest.getRepayOrdid());
        List<BorrowRecover> borrowRecovers=borrowRecoverMapper.selectByExample(borrowRecoverExample);
        return borrowRecovers;
    }

    @Override
    public List<HjhDebtCreditRepay> getHjhDebtCreditRepayListByRequest(CertRequest certRequest) {
        HjhDebtCreditRepayExample example=new HjhDebtCreditRepayExample();
        example.createCriteria().
                andInvestOrderIdEqualTo(certRequest.getInvestOrderId()).
                andBorrowNidEqualTo(certRequest.getBorrowNid()).
                andAssignRepayPeriodEqualTo(certRequest.getPeriod());
        List<HjhDebtCreditRepay> hjhDebtCreditRepays=hjhDebtCreditRepayMapper.selectByExample(example);
        return hjhDebtCreditRepays;
    }

    @Override
    public List<CreditRepay> getCreditRepayListByRequest(CertRequest certRequest) {
        CreditRepayExample example=new CreditRepayExample();
        example.createCriteria().
                andCreditTenderNidEqualTo(certRequest.getInvestOrderId()).
                andBidNidEqualTo(certRequest.getBorrowNid()).
                andRecoverPeriodEqualTo(certRequest.getPeriod());
        List<CreditRepay> creditRepays=creditRepayMapper.selectByExample(example);
        return creditRepays;
    }

    @Override
    public List<BorrowRecoverPlan> selectBorrowRecoverPlanListByRequest(CertRequest certRequest) {
        BorrowRecoverPlanExample borrowRecoverPlanExample=new BorrowRecoverPlanExample();
        borrowRecoverPlanExample.createCriteria().andRepayOrderIdEqualTo(certRequest.getRepayOrdid());
        List<BorrowRecoverPlan> borrowRecoverPlans = borrowRecoverPlanMapper.selectByExample(borrowRecoverPlanExample);
        return borrowRecoverPlans;
    }

    @Override
    public List<HjhDebtCreditRepay> getHjhDebtCreditRepayListByRepayOrdId(CertRequest certRequest) {
        HjhDebtCreditRepayExample example=new HjhDebtCreditRepayExample();
        example.createCriteria().andCreditRepayOrderIdEqualTo(certRequest.getRepayOrdid());
        List<HjhDebtCreditRepay> hjhDebtCreditRepays=hjhDebtCreditRepayMapper.selectByExample(example);
        return hjhDebtCreditRepays;
    }

    @Override
    public List<CreditRepay> getCreditRepayListByRepayOrdId(CertRequest certRequest) {
        CreditRepayExample example=new CreditRepayExample();
        example.createCriteria().andCreditRepayOrderIdEqualTo(certRequest.getRepayOrdid());
        List<CreditRepay> creditRepays=creditRepayMapper.selectByExample(example);
        return creditRepays;
    }

    @Override
    public CertAccountListIdCustomize queryCertAccountListId(CertRequest certRequest) {
        Map<String, Object> param =  new HashMap<String, Object>();
        param.put("minId", certRequest.getMinId());
        param.put("limitStart", certRequest.getLimitStart());
        param.put("limitEnd", certRequest.getLimitEnd());
        CertAccountListIdCustomize customize=certMapper.queryCertAccountListId(param);
        return customize;
    }
}