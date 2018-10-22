package com.hyjf.am.trade.service.admin.productcenter.applyagreement.impl;

import com.hyjf.am.resquest.admin.ApplyAgreementRequest;
import com.hyjf.am.resquest.admin.BorrowRepayAgreementAmRequest;
import com.hyjf.am.trade.dao.model.auto.ApplyAgreement;
import com.hyjf.am.trade.dao.model.auto.ApplyAgreementExample;
import com.hyjf.am.trade.dao.model.auto.TenderAgreement;
import com.hyjf.am.trade.dao.model.auto.TenderAgreementExample;
import com.hyjf.am.trade.service.admin.productcenter.applyagreement.ApplyAgreementService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.BorrowRepayAgreementCustomizeVO;
import com.hyjf.am.vo.trade.borrow.ApplyAgreementVO;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther:Zhadaojian
 * @Date:2018/7/7
 * @Description:
 */
@Service
public class ApplyAgreementServiceImpl extends BaseServiceImpl implements ApplyAgreementService {


    /**
     * 垫付协议申请明细列表页--分期列表总数量
     *
     * @param request
     * @return
     */
    @Override
    public int countBorrowRepayPlan(BorrowRepayAgreementAmRequest request) {
        return this.borrowRepayAgreementCustomizeMapper.countBorrowRepayPlan(request);
    }

    /**
     * 垫付协议申请明细列表页--分期列表
     *
     * @param request
     * @return
     */
    @Override
    public List<BorrowRepayAgreementCustomizeVO> selectBorrowRepayPlan(BorrowRepayAgreementAmRequest request, int limitStart, int limitEnd) {
        request.setLimitStart(limitStart);
        request.setLimitEnd(limitEnd);
        return this.borrowRepayAgreementCustomizeMapper.selectBorrowRepayPlan(request);
    }

    /**
     * 垫付协议申请明细列表页--列表总数量
     *
     * @param request
     * @return
     */
    @Override
    public int countBorrowRepay(BorrowRepayAgreementAmRequest request) {
        return this.borrowRepayAgreementCustomizeMapper.countBorrowRepay(request);
    }

    /**
     * 垫付协议申请明细列表页--列表
     *
     * @param request
     * @return
     */
    @Override
    public List<BorrowRepayAgreementCustomizeVO> selectBorrowRepay(BorrowRepayAgreementAmRequest request, int limitStart, int limitEnd) {
        request.setLimitStart(limitStart);
        request.setLimitEnd(limitEnd);
        return this.borrowRepayAgreementCustomizeMapper.selectBorrowRepay(request);
    }

    /**
     * 垫付协议申请  数目
     *
     * @return
     */
    @Override
    public Integer countApplyAgreement(ApplyAgreementRequest request ) {
        ApplyAgreementExample applyAgreement = new ApplyAgreementExample();
        ApplyAgreementExample.Criteria criteria = applyAgreement.createCriteria();
        if(StringUtils.isNotEmpty(request.getBorrowNid())){
            criteria.andBorrowNidEqualTo(request.getBorrowNid());
        }
        if(StringUtils.isNotEmpty(request.getBorrowPeriod())){
            criteria.andRepayPeriodEqualTo(Integer.valueOf(request.getBorrowPeriod()));
        }
        if(StringUtils.isNotEmpty(request.getTimeStart()) &&
                StringUtils.isNotEmpty(request.getTimeEnd())){
            criteria.andCreateTimeBetween(GetDate.str2Timestamp(request.getTimeStart()), GetDate.str2Timestamp(request.getTimeEnd()));
        }
        Integer count = this.applyAgreementMapper.countByExample(applyAgreement);
        return count;
    }

    /**
     * 垫付协议申请  数目
     *
     * @return
     */
    @Override
    public List<ApplyAgreementVO> selectApplyAgreement(ApplyAgreementRequest request) {
        ApplyAgreementExample applyAgreementExample = new ApplyAgreementExample();
        ApplyAgreementExample.Criteria criteriaA = applyAgreementExample.createCriteria();
        if(StringUtils.isNotEmpty(request.getBorrowNid())){
            criteriaA.andBorrowNidEqualTo(request.getBorrowNid());
        }
        if(StringUtils.isNotEmpty(request.getBorrowPeriod())){
            criteriaA.andRepayPeriodEqualTo(Integer.valueOf(request.getBorrowPeriod()));
        }
        if(StringUtils.isNotEmpty(request.getTimeStart()) &&
                StringUtils.isNotEmpty(request.getTimeEnd())){
            criteriaA.andCreateTimeBetween(GetDate.str2Timestamp(request.getTimeStart()), GetDate.str2Timestamp(request.getTimeEnd()));
        }
        Integer count = this.applyAgreementMapper.countByExample(applyAgreementExample);
        List<ApplyAgreementVO> listVo = new ArrayList<ApplyAgreementVO>();
        if (count != null && count > 0) {
            applyAgreementExample.setOrderByClause("create_time Desc");
            List<ApplyAgreement> list = this.applyAgreementMapper.selectByExample(applyAgreementExample);

            if (list != null && list.size() > 0) {
                for (ApplyAgreement applyAgreement : list) {
                    String tenderNid = "DF-" + applyAgreement.getRepayPeriod() + "-";
                    TenderAgreementExample example = new TenderAgreementExample();
                    TenderAgreementExample.Criteria criteria = example.createCriteria();
                    //criteria.andStatusEqualTo(3);
                    criteria.andTenderNidLike(tenderNid + "%");
                    criteria.andBorrowNidEqualTo(applyAgreement.getBorrowNid());
                    List<TenderAgreement> tenderAgreements = this.tenderAgreementMapper.selectByExample(example);
                    //申请状态 0 全部；1申请失败(hyjf_tender_agreement没有记录)：2申请中；3申请成功
                    if (tenderAgreements != null) {
                        int tenderAgreementCout = 0;//hyjf_tender_agreement状态为3的数量和hyjf_apply_agreement协议份数相同表示都生成成功
                        for (TenderAgreement tenderAgreement : tenderAgreements) {
                            if (tenderAgreement.getStatus() == 1 || tenderAgreement.getStatus() == 2) {
                                applyAgreement.setStatus(2);
                                break;
                            }
                            tenderAgreementCout++;
                        }
                        if (tenderAgreementCout == applyAgreement.getAgreementNumber()) {
                            applyAgreement.setStatus(3);
                        }
                    } else {
                        applyAgreement.setStatus(1);
                    }
                    ApplyAgreementVO applyAgreementVO  =  new ApplyAgreementVO();
                    BeanUtils.copyProperties(applyAgreement,applyAgreementVO);
                    listVo.add(applyAgreementVO);
                }
            }
        }
        return  listVo;
    }
}
