/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.TenderAgreementRequest;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;

import java.util.List;

/**
 * @author jijun
 * @date 20180629
 */
public interface TenderAgreementClient {


    TenderAgreementVO getTenderAgreementInfo(String tenderAgreementID);

    void updateTenderAgreement(TenderAgreementRequest request);

    List<TenderAgreementVO> selectTenderAgreementByNid(String orderId);

    List<TenderAgreementVO> getTenderAgreementListByTenderNidAndStatusNot2(String contract_id);
}
