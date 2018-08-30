package com.hyjf.am.trade.service.admin.productcenter.applyagreement.impl;

import com.hyjf.am.resquest.admin.ApplyAgreementInfoRequest;
import com.hyjf.am.resquest.admin.ApplyAgreementRequest;
import com.hyjf.am.resquest.admin.BorrowRepayAgreementRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.BorrowRepayAgreementCustomize;
import com.hyjf.am.trade.service.admin.productcenter.applyagreement.ApplyAgreementService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 垫付协议管理
 * @version ApplyAgreementServiceImpl, v0.1 2018/8/14 14:26
 * @Author: Zha Daojian
 */
@Service
public class ApplyAgreementServiceImpl extends BaseServiceImpl implements ApplyAgreementService {

    /**
     * 获取列表数量
     *
     * @param request
     * @return
     */
    @Override
    public Integer getListTotal(ApplyAgreementRequest request) {
        ApplyAgreementExample applyAgreementExample =  convertExample(request);
        Integer count = this.applyAgreementMapper.countByExample(applyAgreementExample);
        return count;
    }

    /**
     * 获取列表
     *
     * @param request
     * @return
     */
    @Override
    public List<ApplyAgreement> getList(ApplyAgreementRequest request) {
        ApplyAgreementExample applyAgreementExample =  convertExample(request);
        applyAgreementExample.setOrderByClause("create_time Desc");
        List<ApplyAgreement> list = this.applyAgreementMapper.selectByExample(applyAgreementExample);
        if(list!=null && list.size()>0){
            for (ApplyAgreement applyAgreement : list) {
                String tenderNid = "DF"+"-"+applyAgreement.getRepayPeriod()+"-";
                TenderAgreementExample example = new TenderAgreementExample();
                TenderAgreementExample.Criteria criteria= example.createCriteria();
                criteria.andTenderNidLike(tenderNid+"%");
                criteria.andBorrowNidEqualTo(applyAgreement.getBorrowNid());
                List<TenderAgreement> tenderAgreements= this.tenderAgreementMapper.selectByExample(example);
                //申请状态 0 全部；1申请失败(hyjf_tender_agreement没有记录)：2申请中；3申请成功
                if(tenderAgreements != null){
                    int tenderAgreementCout = 0;//hyjf_tender_agreement状态为3的数量和hyjf_apply_agreement协议份数相同表示都生成成功
                    for (TenderAgreement tenderAgreement : tenderAgreements) {
                        if(tenderAgreement.getStatus()==1 || tenderAgreement.getStatus()==2 ){
                            applyAgreement.setStatus(2);
                            break;
                        }
                        tenderAgreementCout++;
                    }
                    if(tenderAgreementCout==applyAgreement.getAgreementNumber()){
                        applyAgreement.setStatus(3);
                    }
                }else{
                    applyAgreement.setStatus(1);
                }
            }
        }
        return list;
    }

    /**
     * 垫付协议申请明细列表页--分期列表总数量
     *
     * @param map
     * @return
     */
    @Override
    public Integer getListTotalPlan(Map map) {
        return this.borrowRepayAgreementCustomizeMapper.countBorrowRepayPlan(map);
    }

    /**
     * 垫付协议申请明细列表页--分期
     *
     * @param map
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<BorrowRepayAgreementCustomize> getListPlay(Map map, int limitStart, int limitEnd) {
        map.put("limitStart",limitStart);
        map.put("limitEnd",limitEnd);
        List<BorrowRepayAgreementCustomize> list = this.borrowRepayAgreementCustomizeMapper.selectBorrowRepayPlan(map);
        return list;
    }

    /**
     * 垫付协议申请明细列表页--不分期列表总数量
     *
     * @param map
     * @return
     */
    @Override
    public Integer getListTotal(Map map) {
        return this.borrowRepayAgreementCustomizeMapper.countBorrowRepay(map);
    }

    /**
     * 垫付协议申请明细列表页--不分期
     *
     * @param map
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<BorrowRepayAgreementCustomize> getList(Map map, int limitStart, int limitEnd) {
        map.put("limitStart",limitStart);
        map.put("limitEnd",limitEnd);
        List<BorrowRepayAgreementCustomize> list = this.borrowRepayAgreementCustomizeMapper.selectBorrowRepay(map);
        return list;
    }

    /**
     * 批量生成垫付债转协议
     *
     * @param request
     * @author Zha Daojian
     * @date 2018/7/12 10:52
     */
    @Override
    public Integer generateContract(BorrowRepayAgreementRequest request) {
        return null;
    }

    /**
     * 封装查询条件
     * @param request
     * @return ApplyAgreementExample
     */
    private ApplyAgreementExample convertExample(ApplyAgreementRequest request){
        ApplyAgreementExample applyAgreementExample = new ApplyAgreementExample();
        ApplyAgreementExample.Criteria agreementcriteria = applyAgreementExample.createCriteria();
        if(StringUtils.isNotEmpty(request.getBorrowNid())){
            agreementcriteria.andBorrowNidEqualTo(request.getBorrowNid());
        }
        if(StringUtils.isNotEmpty(request.getBorrowPeriod())){
            agreementcriteria.andRepayPeriodEqualTo(Integer.valueOf(request.getBorrowPeriod()));
        }
        if(StringUtils.isNotEmpty(request.getTimeStart()) &&
                StringUtils.isNotEmpty(request.getTimeEnd())){
            Date endDate = GetDate.stringToDate(request.getTimeEnd());
            Date startDate = GetDate.stringToDate(request.getTimeStart());
            agreementcriteria.andCreateTimeBetween(startDate, endDate);
        }
        return applyAgreementExample;
    }

    /**
     * 获取用户投资协议
     *
     * @param borrowNid
     * @return
     */
    @Override
    public List<BorrowRecover> selectBorrowRecoverList(String borrowNid) {
        return this.getBorrowRecover(borrowNid);
    }

    /**
     * 获取用户投资协议分期
     *
     * @param borrowNid
     * @return
     */
    @Override
    public List<BorrowRecoverPlan> selectBorrowRecoverPlanList(String borrowNid, int period) {
        return this.getBorrowRecoverPlan(borrowNid,period);
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
     * 获取用户投资协议分期
     *
     * @param borrowNid
     * @return
     */
    @Override
    public List<HjhDebtCreditRepay> selectHjhDebtCreditRepayList(String borrowNid, int period) {
            HjhDebtCreditRepayExample example = new HjhDebtCreditRepayExample();
            HjhDebtCreditRepayExample.Criteria crt = example.createCriteria();
            crt.andBorrowNidEqualTo(borrowNid);
            crt.andRepayPeriodEqualTo(period);
            crt.andRepayStatusEqualTo(1);
            crt.andDelFlagEqualTo(0);
            crt.andRepayAccountGreaterThan(BigDecimal.ZERO);
            example.setOrderByClause("id ASC");
            List<HjhDebtCreditRepay> creditRepayList = this.hjhDebtCreditRepayMapper.selectByExample(example);
            return creditRepayList;
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
        CommonUtils.convertBean(request, ApplyAgreementInfo.class);
        ApplyAgreementInfoExample example = new ApplyAgreementInfoExample();
        example.createCriteria().andContractIdEqualTo(applyAgreementInfo.getContractId());
        List<ApplyAgreementInfo> openAccountRecords = this.applyAgreementInfoMapper.selectByExample(example);
        if(openAccountRecords != null && openAccountRecords.size() > 0){
            applyAgreementInfo.setId(openAccountRecords.get(0).getId());
            applyAgreementInfo.setUpdateTime(new Date());
            applyAgreementInfo.setUpdateTime(openAccountRecords.get(0).getCreateTime());
            return this.applyAgreementInfoMapper.updateByPrimaryKey(applyAgreementInfo);

        }else {
            applyAgreementInfo.setCreateTime(new Date());
            return this.applyAgreementInfoMapper.insert(applyAgreementInfo);
        }
    }
}
