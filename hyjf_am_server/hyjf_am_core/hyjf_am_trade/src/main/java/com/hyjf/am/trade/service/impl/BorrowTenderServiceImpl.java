package com.hyjf.am.trade.service.impl;

import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.trade.dao.mapper.auto.BorrowTenderMapper;
import com.hyjf.am.trade.dao.mapper.auto.FddTempletMapper;
import com.hyjf.am.trade.dao.mapper.auto.TenderAgreementMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.BorrowTenderService;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.common.constants.FddGenerateContractConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowTenderServiceImpl implements BorrowTenderService {

    @Autowired
    private BorrowTenderMapper borrowTenderMapper;
    @Autowired
    private FddTempletMapper fddTempletMapper;
    @Autowired
    private TenderAgreementMapper tenderAgreementMapper;

    @Override
    public Integer getCountBorrowTenderService(Integer userId, String borrowNid) {
        BorrowTenderExample example = new BorrowTenderExample();
        BorrowTenderExample.Criteria cri = example.createCriteria();
        cri.andNidEqualTo(borrowNid);
        cri.andUserIdEqualTo(userId);
        int count = borrowTenderMapper.countByExample(example);
        return count;
    }

    @Override
    public BorrowTender selectBorrowTender(BorrowTenderRequest request) {
        BorrowTenderExample example = new BorrowTenderExample();
        BorrowTenderExample.Criteria cra = example.createCriteria();
        cra.andNidEqualTo(request.getTenderNid());
        cra.andBorrowNidEqualTo(request.getBorrowNid());
        cra.andUserIdEqualTo(request.getTenderUserId());
        List<BorrowTender> tenderList = this.borrowTenderMapper.selectByExample(example);
        if(tenderList != null && tenderList.size() > 0){
            return tenderList.get(0);
        }
        return null;
    }

    @Override
    public List<FddTemplet> getFddTempletList(Integer protocolType) {
        FddTempletExample example = new FddTempletExample();
        FddTempletExample.Criteria criteria = example.createCriteria();
        criteria.andProtocolTypeEqualTo(protocolType);
        criteria.andIsActiveEqualTo(1);
        criteria.andCaFlagEqualTo(1);
        return this.fddTempletMapper.selectByExample(example);
    }

    @Override
    public int saveTenderAgreement(TenderAgreement tenderAgreement) {
        return this.tenderAgreementMapper.insertSelective(tenderAgreement);
    }

    @Override
    public int updateTenderAgreement(TenderAgreement tenderAgreement) {
        return this.tenderAgreementMapper.updateByPrimaryKey(tenderAgreement);
    }

    @Override
    public List<BorrowTender> getBorrowTenderListByNid(String nid) {
        BorrowTenderExample example = new BorrowTenderExample();
        example.createCriteria().andNidEqualTo(nid);
        List<BorrowTender> tenderList = this.borrowTenderMapper.selectByExample(example);
        return tenderList;
    }
}
