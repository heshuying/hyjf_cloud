package com.hyjf.am.trade.service.front.config.impl;

import com.hyjf.am.trade.dao.mapper.auto.BorrowRecoverMapper;
import com.hyjf.am.trade.dao.mapper.auto.TenderAgreementMapper;
import com.hyjf.am.trade.dao.model.auto.BorrowRecover;
import com.hyjf.am.trade.dao.model.auto.BorrowRecoverExample;
import com.hyjf.am.trade.dao.model.auto.TenderAgreement;
import com.hyjf.am.trade.dao.model.auto.TenderAgreementExample;
import com.hyjf.am.trade.service.front.config.TenderAgreementService;
import org.apache.commons.collections.CollectionUtils;
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

    @Autowired
    protected BorrowRecoverMapper borrowRecoverMapper;

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
    public List<TenderAgreement> getTenderAgreementListByTenderNidAndStatusNot2(String tenderNid) {
        TenderAgreementExample example = new TenderAgreementExample();
        example.createCriteria().andTenderNidEqualTo(tenderNid).andStatusNotEqualTo(2);
        return this.tenderAgreementMapper.selectByExample(example);
    }

    /**
     * 根据订单号获取用户放款信息
     *
     * @param nid
     * @return
     */
    @Override
    public BorrowRecover selectBorrowRecoverByNid(String nid) {
        // 获取用户放款信息
        BorrowRecoverExample example = new BorrowRecoverExample();
        example.createCriteria().andNidEqualTo(nid);
        List<BorrowRecover> list = this.borrowRecoverMapper.selectByExample(example);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }
}
