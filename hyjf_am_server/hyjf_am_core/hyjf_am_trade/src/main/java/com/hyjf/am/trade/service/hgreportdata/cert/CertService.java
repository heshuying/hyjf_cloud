package com.hyjf.am.trade.service.hgreportdata.cert;

import com.hyjf.am.resquest.hgreportdata.cert.CertRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.CertAccountListCustomize;
import com.hyjf.am.trade.dao.model.customize.CertAccountListIdCustomize;
import com.hyjf.am.trade.service.BaseService;

import java.util.List;

/**
 * @author pangchengchao
 * @version CertTransactService, v0.1 2019/1/24 17:08
 */
public interface CertService extends BaseService {
    List<CertAccountListCustomize> queryCertAccountList(CertRequest certRequest);

    List<AccountList> getAccountListVOListByRequest(CertRequest certRequest);

    List<BorrowRepay> getBorrowRepayListByRequest(CertRequest certRequest);

    List<BorrowRepayPlan> getBorrowRepayPlanListByRequest(CertRequest certRequest);

    List<CouponRecover> getCouponRecoverListByCertRequest(CertRequest certRequest);

    List<BorrowTenderCpn> getBorrowTenderCpnListByCertRequest(CertRequest certRequest);

    List<CouponRealTender> getCouponRealTenderListByCertRequest(CertRequest certRequest);

    List<BorrowRecover> selectBorrowRecoverListByRequest(CertRequest certRequest);

    List<HjhDebtCreditRepay> getHjhDebtCreditRepayListByRequest(CertRequest certRequest);

    List<CreditRepay> getCreditRepayListByRequest(CertRequest certRequest);

    List<BorrowRecoverPlan> selectBorrowRecoverPlanListByRequest(CertRequest certRequest);

    List<HjhDebtCreditRepay> getHjhDebtCreditRepayListByRepayOrdId(CertRequest certRequest);

    List<CreditRepay> getCreditRepayListByRepayOrdId(CertRequest certRequest);

    CertAccountListIdCustomize queryCertAccountListId(CertRequest certRequest);
}
