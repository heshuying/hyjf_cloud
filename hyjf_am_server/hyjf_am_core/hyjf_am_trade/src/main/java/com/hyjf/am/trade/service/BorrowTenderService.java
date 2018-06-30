package com.hyjf.am.trade.service;

import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.dao.model.auto.FddTemplet;
import com.hyjf.am.trade.dao.model.auto.TenderAgreement;
import com.hyjf.am.vo.trade.TenderAgreementVO;

import java.util.List;

public interface BorrowTenderService {

    Integer getCountBorrowTenderService(Integer userId, String borrowNid);

    BorrowTender selectBorrowTender(BorrowTenderRequest request);

    List<FddTemplet> getFddTempletList(Integer protocolType);

    int saveTenderAgreement(TenderAgreement tenderAgreement);

    int updateTenderAgreement(TenderAgreement tenderAgreement);

    List<BorrowTender> getBorrowTenderListByNid(String nid);
}
