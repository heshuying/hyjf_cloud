package com.hyjf.am.trade.service.admin.productcenter.applyagreement.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.ApplyAgreementInfoRequest;
import com.hyjf.am.resquest.admin.ApplyAgreementRequest;
import com.hyjf.am.resquest.admin.BorrowRepayAgreementAmRequest;
import com.hyjf.am.resquest.admin.DownloadAgreementRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.admin.productcenter.applyagreement.ApplyAgreementService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.BorrowRepayAgreementCustomizeVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.ApplyAgreementVO;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
        List<BorrowRepayAgreementCustomizeVO> list = this.borrowRepayAgreementCustomizeMapper.selectBorrowRepayPlan(request);
        if (list != null && list.size() > 0) {
            for (BorrowRepayAgreementCustomizeVO customize : list) {//判断是否申请过垫付协议
                ApplyAgreementExample applyAgreement = new ApplyAgreementExample();
                ApplyAgreementExample.Criteria criteria = applyAgreement.createCriteria();
                criteria.andBorrowNidEqualTo(customize.getBorrowNid());
                criteria.andRepayPeriodEqualTo(customize.getRepayPeriod());
                Integer count = this.applyAgreementMapper.countByExample(applyAgreement);
                logger.info("--------------------垫付协议申请明细列表页--分期列表applyAgreement:"+JSONObject.toJSON(applyAgreement));
                logger.info("--------------------垫付协议申请明细列表页--分期列表count:"+count);
                if (count > 0) {
                    customize.setApplyagreements(1);
                } else {
                    customize.setApplyagreements(0);
                }
            }
        }
        logger.info("--------------------垫付协议申请明细列表页--分期列表list:"+JSONObject.toJSON(list));
        return  list;
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
        List<BorrowRepayAgreementCustomizeVO> list =  this.borrowRepayAgreementCustomizeMapper.selectBorrowRepay(request);
        for (BorrowRepayAgreementCustomizeVO customize : list) {//判断是否申请过垫付协议
            ApplyAgreementExample applyAgreement = new ApplyAgreementExample();
            ApplyAgreementExample.Criteria criteria = applyAgreement.createCriteria();
            criteria.andBorrowNidEqualTo(customize.getBorrowNid());
            criteria.andRepayPeriodEqualTo(customize.getRepayPeriod());
            Integer count = this.applyAgreementMapper.countByExample(applyAgreement);
            logger.info("--------------------垫付协议申请明细列表页--列表applyAgreement:"+JSONObject.toJSON(applyAgreement));
            logger.info("--------------------垫付协议申请明细列表页--列表count:"+count);
            if (count > 0) {
                customize.setApplyagreements(1);
            } else {
                customize.setApplyagreements(0);
            }
        }
        return list;
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
            criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.str2Timestamp(request.getTimeStart()));
            criteria.andCreateTimeLessThanOrEqualTo(GetDate.str2Timestamp(request.getTimeEnd()));
        }
        Integer count = this.applyAgreementMapper.countByExample(applyAgreement);
        return count;
    }

    /**
     * 垫付协议申请
     *
     * @return
     */
    @Override
    public List<TenderAgreementVO> selectLikeByExample(DownloadAgreementRequest request) {
        String tenderNid = request.getRepayPeriod()+"%";
        String borrowNid = request.getBorrowNid();
        TenderAgreementExample example = new TenderAgreementExample();
        TenderAgreementExample.Criteria cra = example.createCriteria();
        cra.andTenderNidLike(tenderNid);
        cra.andBorrowNidEqualTo(borrowNid);
        List<TenderAgreement> tenderAgreements= this.tenderAgreementMapper.selectByExample(example);
        List<TenderAgreementVO> tenderAgreementvos = new ArrayList<>();
        if(tenderAgreements != null && tenderAgreements.size()>0){
            for (TenderAgreement tenderAgreement : tenderAgreements) {
                TenderAgreementVO tenderAgreementVO =  new TenderAgreementVO();
                BeanUtils.copyProperties(tenderAgreement,tenderAgreementVO);
                tenderAgreementvos.add(tenderAgreementVO);
            }
            return tenderAgreementvos;
        }
        return null;

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
            //criteriaA.andCreateTimeBetween(GetDate.str2Timestamp(request.getTimeStart()), GetDate.str2Timestamp(request.getTimeEnd()));
            criteriaA.andCreateTimeGreaterThanOrEqualTo(GetDate.str2Timestamp(request.getTimeStart()));
            criteriaA.andCreateTimeLessThanOrEqualTo(GetDate.str2Timestamp(request.getTimeEnd()));
        }
        if (request.getLimitStart()!=null && request.getLimitStart() != -1) {
            applyAgreementExample.setLimitEnd(request.getLimitEnd());
            applyAgreementExample.setLimitStart(request.getLimitStart());
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
                    if (tenderAgreements != null && tenderAgreements.size()>0) {
                        int tenderAgreementCout = 0;//hyjf_tender_agreement状态为3的数量和hyjf_apply_agreement协议份数相同表示都生成成功
                        for (TenderAgreement tenderAgreement : tenderAgreements) {
                            if (tenderAgreement.getStatus() == 1 || tenderAgreement.getStatus() == 2) {
                                applyAgreement.setStatus(2);
                                break;
                            }
                            tenderAgreementCout++;
                        }
                        if (tenderAgreementCout == applyAgreement.getAgreementNumber() && applyAgreement.getAgreementNumber()>0) {
                            applyAgreement.setStatus(3);
                        }else{
                            applyAgreement.setStatus(1);
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

    /**
     * 获取用户出借协议
     *
     * @param borrowNid
     * @return
     */
    @Override
    public List<BorrowRecover> selectBorrowRecoverList(String borrowNid) {
        return this.getBorrowRecover(borrowNid);
    }

    /**
     * 获取用户出借协议分期
     *
     * @param nid
     * @return
     */
    @Override
    public List<BorrowRecoverPlan> selectBorrowRecoverPlanList(String nid, int period) {
        return this.getBorrowRecoverPlan(nid,period);
    }

    /**
     * 获取用户汇计划债转还款表
     * @author Zha Daojian
     * @date 2018/8/17 16:38
     * @param nid, repay_period
     * @return java.util.List<com.hyjf.am.trade.dao.model.auto.CreditRepay>
     **/
    @Override
    public List<CreditRepay> selectCreditRepayList(String nid,int repay_period) {
        CreditRepayExample creditRepayExample = new CreditRepayExample();
        CreditRepayExample.Criteria creditRepayCra = creditRepayExample.createCriteria();
        creditRepayCra.andCreditTenderNidEqualTo(nid);
        creditRepayCra.andRecoverPeriodEqualTo(repay_period);
        creditRepayCra.andStatusEqualTo(1);
        List<CreditRepay> list = this.creditRepayMapper.selectByExample(creditRepayExample);
        return list;
    }
    /**
     * 获取用户出借协议分期
     *
     * @param nid
     * @return
     */
    @Override
    public List<HjhDebtCreditRepay> selectHjhDebtCreditRepayList(String nid, int period) {
        HjhDebtCreditRepayExample example = new HjhDebtCreditRepayExample();
        HjhDebtCreditRepayExample.Criteria crt = example.createCriteria();
        crt.andInvestOrderIdEqualTo(nid);
        crt.andRepayPeriodEqualTo(period);
        crt.andDelFlagEqualTo(0);
        List<HjhDebtCreditRepay> hjhDebtCreditRepays = this.hjhDebtCreditRepayMapper.selectByExample(example);
        return hjhDebtCreditRepays;
    }

    /**
     * 根据contract_id查询垫付协议生成详情
     *
     * @param contractId
     * @return java.util.List<com.hyjf.am.trade.dao.model.auto.BorrowRecoverPlan>
     * @author Zha Daojian
     * @date 2018/8/23 16:37
     **/
    @Override
    public List<ApplyAgreementInfo> selectApplyAgreementInfoByContractId(String contractId) {
        ApplyAgreementInfoExample applyAgreementInfoExample = new ApplyAgreementInfoExample();
        ApplyAgreementInfoExample.Criteria cra = applyAgreementInfoExample.createCriteria();
        cra.andContractIdEqualTo(contractId);
        List<ApplyAgreementInfo> applyAgreementInfoList = this.applyAgreementInfoMapper.selectByExample(applyAgreementInfoExample);
        return applyAgreementInfoList;
    }

    /**
     * 保存垫付协议申请-协议生成详情
     *
     * @param request
     * @return java.util.List<com.hyjf.am.trade.dao.model.auto.BorrowRecoverPlan>
     * @author Zha Daojian
     * @date 2018/8/23 16:37
     **/
    @Override
    public int saveApplyAgreementInfo(ApplyAgreementInfoRequest request) {
        ApplyAgreementInfo applyAgreementInfo = new ApplyAgreementInfo();
        BeanUtils.copyProperties(request,applyAgreementInfo);
        ApplyAgreementInfoExample example = new ApplyAgreementInfoExample();
        example.createCriteria().andContractIdEqualTo(applyAgreementInfo.getContractId());
        List<ApplyAgreementInfo> openAccountRecords = this.applyAgreementInfoMapper.selectByExample(example);
        if(openAccountRecords != null && openAccountRecords.size() > 0){
            applyAgreementInfo.setId(openAccountRecords.get(0).getId());
            applyAgreementInfo.setUpdateTime(new Date());
            applyAgreementInfo.setCreateTime(openAccountRecords.get(0).getCreateTime());
            return this.applyAgreementInfoMapper.updateByPrimaryKey(applyAgreementInfo);

        }else {
            applyAgreementInfo.setCreateTime(new Date());
            return this.applyAgreementInfoMapper.insert(applyAgreementInfo);
        }
    }

    /**
     * 保存垫付协议申请
     *
     * @param vo
     * @return java.util.List<com.hyjf.am.trade.dao.model.auto.BorrowRecoverPlan>
     * @author Zha Daojian
     * @date 2018/8/23 16:37
     **/
    @Override
    public int saveApplyAgreement(ApplyAgreementVO vo) {
        ApplyAgreement applyAgreement = new ApplyAgreement();
        BeanUtils.copyProperties(vo,applyAgreement);
        applyAgreement.setStatus(1);
        applyAgreement.setDelFlag(0);
        ApplyAgreementExample example = new ApplyAgreementExample();//垫付协议申请记录
        ApplyAgreementExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowNidEqualTo(applyAgreement.getBorrowNid());
        criteria.andRepayPeriodEqualTo(applyAgreement.getRepayPeriod());
        List<ApplyAgreement> applyAgreements = this.applyAgreementMapper.selectByExample(example);
        if(applyAgreements != null && applyAgreements.size() > 0){
            applyAgreement.setId(applyAgreements.get(0).getId());
            applyAgreement.setUpdateTime(new Date());
            applyAgreement.setCreateTime(applyAgreements.get(0).getCreateTime());
            this.applyAgreementMapper.updateByPrimaryKey(applyAgreement);
        }else {
            applyAgreement.setCreateTime(new Date());
            return this.applyAgreementMapper.insert(applyAgreement);
        }
        return 0;
    }
}
