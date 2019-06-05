package com.hyjf.am.trade.service.hgreportdata.cert;

import com.hyjf.am.resquest.hgreportdata.cert.CertRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.CertAccountListCustomize;
import com.hyjf.am.trade.dao.model.customize.CertAccountListIdCustomize;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.trade.cert.CertClaimUpdateVO;
import com.hyjf.am.vo.trade.cert.CertProductUpdateVO;

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

    List<CertAccountListCustomize> getCertAccountListCustomizeVO(CertRequest certRequest);
    /**
     * 根据标示，查找国家互联网应急中心（产品配置历史数据上报）
     * @return
     */
    List<CertClaim> insertCertBorrowConfig();
    /**
     * 批量更新
     * @param update
     * @return
     */
    int updateCertBorrowStatusBatch (CertClaimUpdateVO update);

    /**
     * 产品信息未上报的
     * @return
     */
    List<CertProduct> insertCertProductList();

    /**
     * 批量更新产品信息
     * @param update
     * @return
     */
    int updateCertProductBatch (CertProductUpdateVO update);

    List<String> getBorrowNidList();
}
