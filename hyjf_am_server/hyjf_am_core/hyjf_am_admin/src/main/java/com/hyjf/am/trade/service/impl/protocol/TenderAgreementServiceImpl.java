package com.hyjf.am.trade.service.impl.protocol;

import com.hyjf.am.trade.dao.mapper.auto.TenderAgreementMapper;
import com.hyjf.am.trade.dao.model.auto.TenderAgreement;
import com.hyjf.am.trade.dao.model.auto.TenderAgreementExample;
import com.hyjf.am.trade.service.front.protocol.TenderAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pangchengchao
 * @version TenderAgreementServiceImpl, v0.1 2018/6/27 9:43
 */
@Service
public class TenderAgreementServiceImpl implements TenderAgreementService {

    @Autowired
    protected TenderAgreementMapper tenderAgreementMapper;
    @Override
    public List<TenderAgreement> selectTenderAgreementByNid(String nid) {
        TenderAgreementExample example = new TenderAgreementExample();
        example.createCriteria().andTenderNidEqualTo(nid);
        List<TenderAgreement> tenderAgreements= this.tenderAgreementMapper.selectByExample(example);

        if(tenderAgreements != null && tenderAgreements.size()>0){
            return tenderAgreements;
        }
        return null;
    }

    @Override
    public TenderAgreement getTenderAgreementInfo(String tenderAgreementID) {
        TenderAgreement tenderAgreement = this.tenderAgreementMapper.selectByPrimaryKey(Integer.valueOf(tenderAgreementID));
        return tenderAgreement;
    }

    @Override
    public List<TenderAgreement> getTenderAgreementByBorrowNid(String borrowNid) {
        TenderAgreementExample example = new TenderAgreementExample();
        example.createCriteria().andBorrowNidEqualTo(borrowNid);
        return this.tenderAgreementMapper.selectByExample(example);
    }


    @Override
    public List<TenderAgreement> getTenderAgreementListByTenderNidAndStatusNot2(String tenderNid) {
        TenderAgreementExample example = new TenderAgreementExample();
        example.createCriteria().andTenderNidEqualTo(tenderNid).andStatusNotEqualTo(2);
        return this.tenderAgreementMapper.selectByExample(example);
    }
}
