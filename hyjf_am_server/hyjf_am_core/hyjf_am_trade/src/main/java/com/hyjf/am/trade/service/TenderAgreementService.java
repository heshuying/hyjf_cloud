package com.hyjf.am.trade.service;

import com.hyjf.am.resquest.trade.TenderAgreementRequest;
import com.hyjf.am.trade.dao.model.auto.TenderAgreement;

import java.util.List;

/**
 * @author pangchengchao
 * @version TenderAgreementService, v0.1 2018/6/27 9:43
 */
public interface TenderAgreementService {

    List<TenderAgreement> selectTenderAgreementByNid(String nid);

    TenderAgreement getTenderAgreementInfo(String tenderAgreementID);

    boolean updateTenderAgreement(TenderAgreementRequest request);
}
