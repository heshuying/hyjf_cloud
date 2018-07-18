package com.hyjf.am.trade.service.impl;

import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.mapper.customize.trade.BorrowTenderCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.BorrowTenderService;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class BorrowTenderServiceImpl implements BorrowTenderService {

    @Autowired
    private BorrowTenderMapper borrowTenderMapper;
    @Autowired
    private FddTempletMapper fddTempletMapper;
    @Autowired
    private TenderAgreementMapper tenderAgreementMapper;
    @Autowired
    private CreditTenderMapper creditTenderMapper;

    @Autowired
    private CreditTenderLogMapper creditTenderLogMapper;
    @Autowired
    private BorrowTenderCustomizeMapper borrowTenderCustomizeMapper;

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

    /**
     * 根据投资订单号查询已承接金额
     *
     * @param tenderNid
     * @return
     */
    @Override
    public BigDecimal getAssignCapitalByTenderNid(String tenderNid) {
        BigDecimal assignCapital = BigDecimal.ZERO;
        CreditTenderExample example = new CreditTenderExample();
        CreditTenderExample.Criteria cra = example.createCriteria();
        cra.andCreditTenderNidEqualTo(tenderNid);
        Date date = GetDate.getDate(1499011200);
        cra.andCreateTimeLessThanOrEqualTo(date);
        List<CreditTender> list = this.creditTenderMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            for (CreditTender creditTender : list) {
                assignCapital = assignCapital.add(creditTender.getAssignCapital());
            }
        }
        return assignCapital;
    }

    /**
     * 保存债转信息
     *
     * @param bean
     * @return
     */
    @Override
    public Integer saveCreditTenderAssignLog(CreditTenderLog bean) {
        return creditTenderLogMapper.insertSelective(bean);
    }

    @Override
    public Integer getUtmTenderNum(List<Integer> list) {
        String dayStart = GetDate.getDayStart(GetDate.date2Str(GetDate.date_sdf));
        String dayEnd = GetDate.getDayEnd(GetDate.date2Str(GetDate.date_sdf));
        return borrowTenderCustomizeMapper.getUtmTenderNum(list, dayStart, dayEnd);
    }

    @Override
    public BigDecimal getHztTenderPrice(List<Integer> list) {
        String dayStart = GetDate.getDayStart(GetDate.date2Str(GetDate.date_sdf));
        String dayEnd = GetDate.getDayEnd(GetDate.date2Str(GetDate.date_sdf));
        return borrowTenderCustomizeMapper.getHztTenderPrice(list, dayStart, dayEnd);
    }

    @Override
    public BigDecimal getHxfTenderPrice(List<Integer> list) {
        String dayStart = GetDate.getDayStart(GetDate.date2Str(GetDate.date_sdf));
        String dayEnd = GetDate.getDayEnd(GetDate.date2Str(GetDate.date_sdf));
        return borrowTenderCustomizeMapper.getHxfTenderPrice(list, dayStart, dayEnd);
    }

    @Override
    public BigDecimal getRtbTenderPrice(List<Integer> list) {
        String dayStart = GetDate.getDayStart(GetDate.date2Str(GetDate.date_sdf));
        String dayEnd = GetDate.getDayEnd(GetDate.date2Str(GetDate.date_sdf));
        return borrowTenderCustomizeMapper.getRtbTenderPrice(list, dayStart, dayEnd);
    }
}
