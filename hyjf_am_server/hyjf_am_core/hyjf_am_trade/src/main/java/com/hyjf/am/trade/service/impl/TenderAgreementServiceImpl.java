package com.hyjf.am.trade.service.impl;

import com.hyjf.am.resquest.trade.TenderAgreementRequest;
import com.hyjf.am.trade.dao.mapper.auto.TenderAgreementMapper;
import com.hyjf.am.trade.dao.model.auto.TenderAgreement;
import com.hyjf.am.trade.dao.model.auto.TenderAgreementExample;
import com.hyjf.am.trade.service.TenderAgreementService;
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
    public boolean updateTenderAgreement(TenderAgreementRequest request) {
        TenderAgreement tenderAgreement = this.tenderAgreementMapper.selectByPrimaryKey(Integer.valueOf(request.getTenderAgreementID()));
        tenderAgreement.setImgUrl(request.getImgUrl());
        tenderAgreement.setPdfUrl(request.getPdfUrl());
        tenderAgreement.setStatus(3);//下载成功
        int count=this.tenderAgreementMapper.updateByPrimaryKey(tenderAgreement);
        if (count>0){
            return true;
        }else{
            return false;
        }
    }
}
