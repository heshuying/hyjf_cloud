package com.hyjf.am.trade.service.front.borrow.impl;

import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.front.borrow.BorrowTenderService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.coupon.CouponRecoverCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BorrowTenderServiceImpl extends BaseServiceImpl implements BorrowTenderService {


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
    public Integer getUtmTenderNum(List<Integer> list, String type) {
        String dayStart = GetDate.getDayStart(GetDate.date2Str(GetDate.date_sdf));
        String dayEnd = GetDate.getDayEnd(GetDate.date2Str(GetDate.date_sdf));
        return borrowTenderCustomizeMapper.getUtmTenderNum(list, dayStart, dayEnd, type);
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

    @Override
    public BorrowTenderCpn getCouponTenderInfo(String couponTenderNid) {
        BorrowTenderCpnExample example = new BorrowTenderCpnExample();
        example.createCriteria().andNidEqualTo(couponTenderNid);
        List<BorrowTenderCpn> btList = this.borrowTenderCpnMapper.selectByExample(example);
        if (btList != null && btList.size() > 0) {
            return btList.get(0);
        }
        return null;
    }

    @Override
    public CouponRecoverCustomizeVO getCurrentCouponRecover(String couponTenderNid, Integer periodNow) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("tenderNid", couponTenderNid);
        paramMap.put("periodNow", periodNow);
        return borrowTenderCustomizeMapper.getCurrentCouponRecover(paramMap);
    }

    @Override
    public Integer updateBorrowTenderCpn(BorrowTenderCpnVO borrowTenderCpn) {
        return borrowTenderCpnMapper.updateByPrimaryKeySelective(CommonUtils.convertBean(borrowTenderCpn,BorrowTenderCpn.class));
    }

    @Override
    public String countMoneyByBorrowId(Map<String, Object> params) {
        return borrowTenderInfoCustomizeMapper.countMoneyByBorrowId(params);
    }

    /**
     * 查询固定时间间隔的用户投资列表
     * @param repairStartDate
     * @param repairEndDate
     * @return
     */
    @Override
    public List<BorrowTender> selectBorrowTenderList(String repairStartDate, String repairEndDate) {
        SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BorrowTenderExample example = new BorrowTenderExample();
        BorrowTenderExample.Criteria crt = example.createCriteria();
        if (StringUtils.isNotBlank(repairStartDate) && StringUtils.isNotBlank(repairEndDate)) {
            String strStart = repairStartDate + " 00:00:00";
            String strEnd = repairEndDate + " 23:59:59";
            try {
                Date start = smp.parse(strStart);
                Date end = smp.parse(strEnd);
                crt.andCreateTimeGreaterThanOrEqualTo(start);
                crt.andCreateTimeLessThanOrEqualTo(end);
                List<BorrowTender> borrowTenderList = this.borrowTenderMapper.selectByExample(example);
                return borrowTenderList;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
       return null;
    }

    /**
     * 更新标的投资详情表
     * @param borrowTender
     * @return
     */
    @Override
    public Boolean updateBorrowTender(BorrowTender borrowTender){
        int updFlg = borrowTenderMapper.updateByPrimaryKey(borrowTender);
        if(updFlg>0){
            logger.info("=========borrowTender表更新成功==============");
            return true;
        }else{
            throw new RuntimeException("============borrowTender表更新成功失败!========");
        }
    }
}
