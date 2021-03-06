package com.hyjf.am.trade.service.admin.borrow.impl;

import com.hyjf.am.resquest.admin.ApplyBorrowAgreementRequest;
import com.hyjf.am.resquest.admin.ApplyBorrowAgreementSaveRequest;
import com.hyjf.am.trade.dao.model.auto.ApplyBorrowAgreement;
import com.hyjf.am.trade.dao.model.auto.ApplyBorrowAgreementExample;
import com.hyjf.am.trade.service.admin.borrow.ApplyBorrowAgreementService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.borrow.ApplyBorrowAgreementVO;
import com.hyjf.am.vo.trade.borrow.ApplyBorrowInfoVO;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther:Zhadaojian
 * @Date:2018/7/7
 * @Description:
 */
@Service
public class ApplyBorrowAgreementServiceImpl extends BaseServiceImpl implements ApplyBorrowAgreementService {

    /**
     * 协议申请  数目
     *
     * @return
     */
    @Override
    public Integer countApplyBorrowAgreement(ApplyBorrowAgreementRequest request ) {
        ApplyBorrowAgreementExample agreement = new ApplyBorrowAgreementExample();
        ApplyBorrowAgreementExample.Criteria criteria = agreement.createCriteria();
        if(StringUtils.isNotEmpty(request.getBorrowNid())){
            criteria.andBorrowNidEqualTo(request.getBorrowNid());
        }
        if(StringUtils.isNotEmpty(request.getStatus())){
            criteria.andStatusEqualTo(Integer.valueOf(request.getStatus()));
        }
        if(StringUtils.isNotEmpty(request.getApplyUserName())){
            criteria.andApplyUserNameEqualTo(request.getApplyUserName());
        }
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(request.getTimeStart())){
            criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.str2Date(GetDate.getDayStart(request.getTimeStart()),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
        }
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(request.getTimeEnd())){
            criteria.andCreateTimeLessThanOrEqualTo(GetDate.str2Date(GetDate.getDayEnd(request.getTimeEnd()),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
        }
        Integer count = this.applyBorrowAgreementMapper.countByExample(agreement);
        return count;
    }

    /**
     * 垫付协议申请
     *
     * @return
     */
    @Override
    public List<ApplyBorrowAgreementVO> selectApplyBorrowAgreement(ApplyBorrowAgreementRequest request) {
        ApplyBorrowAgreementExample agreementExample = new ApplyBorrowAgreementExample();
        ApplyBorrowAgreementExample.Criteria criteria = agreementExample.createCriteria();
        if(StringUtils.isNotEmpty(request.getBorrowNid())){
            criteria.andBorrowNidEqualTo(request.getBorrowNid());
        }
        if(StringUtils.isNotEmpty(request.getStatus())){
            criteria.andStatusEqualTo(Integer.valueOf(request.getStatus()));
        }
        if(StringUtils.isNotEmpty(request.getApplyUserName())){
            criteria.andApplyUserNameEqualTo(request.getApplyUserName());
        }
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(request.getTimeStart())){
            criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.str2Date(GetDate.getDayStart(request.getTimeStart()),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
        }
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(request.getTimeEnd())){
            criteria.andCreateTimeLessThanOrEqualTo(GetDate.str2Date(GetDate.getDayEnd(request.getTimeEnd()),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
        }
        if (request.getLimitStart()!=null && request.getLimitStart() != -1) {
            agreementExample.setLimitEnd(request.getLimitEnd());
            agreementExample.setLimitStart(request.getLimitStart());
        }
        agreementExample.setOrderByClause("update_time Desc");
        List<ApplyBorrowAgreement>  applyBorrowAgreementList = this.applyBorrowAgreementMapper.selectByExample(agreementExample);
        List<ApplyBorrowAgreementVO> listVo = new ArrayList<ApplyBorrowAgreementVO>();
        if (applyBorrowAgreementList != null && applyBorrowAgreementList.size() > 0) {
            for (ApplyBorrowAgreement applyBorrowAgreement : applyBorrowAgreementList) {
                ApplyBorrowAgreementVO applyAgreementVO  =  new ApplyBorrowAgreementVO();
                BeanUtils.copyProperties(applyBorrowAgreement,applyAgreementVO);
                listVo.add(applyAgreementVO);
            }
        }
        return  listVo;
    }

    /***
     * 协议申请标的信息明细
    * @author Zha Daojian
    * @date 2019/5/6 15:43
    * @param borrowNid
    * @return com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO
    **/
    @Override
    public ApplyBorrowInfoVO getApplyBorrowInfoDetail(String borrowNid){
        return this.borrowCustomizeMapper.getApplyBorrowInfoDetail(borrowNid);
    }

    /**
     * 订单数量：最新的标的订单数量=原始标投资+承接总数
     * @author Zha Daojian
     * @date 2019/7/9 10:33
     * @param borrowNid
     * @return java.lang.Integer
     **/
    @Override
    public Integer getTotalTenderCountByBorrowNid(String borrowNid){
        return this.borrowCustomizeMapper.getTotalTenderCountByBorrowNid(borrowNid);
    }

    /**
     * 协议申请标
     * @author Zha Daojian
     * @date 2019/5/9 10:14
     * @param request
     * @return int
     **/
    @Override
    public int saveApplyBorrowAgreement(ApplyBorrowAgreementSaveRequest request) {
        ApplyBorrowAgreement applyBorrowAgreement = new ApplyBorrowAgreement();
        BeanUtils.copyProperties(request,applyBorrowAgreement);
        ApplyBorrowAgreementExample example = new ApplyBorrowAgreementExample();
        example.createCriteria().andBorrowNidEqualTo(applyBorrowAgreement.getBorrowNid());
        List<ApplyBorrowAgreement> applyBorrowAgreements = this.applyBorrowAgreementMapper.selectByExample(example);
        if(applyBorrowAgreements != null && applyBorrowAgreements.size() > 0){
            applyBorrowAgreement.setId(applyBorrowAgreements.get(0).getId());
            applyBorrowAgreement.setUpdateTime(new Date());
            applyBorrowAgreement.setCreateTime(applyBorrowAgreements.get(0).getCreateTime());
            return this.applyBorrowAgreementMapper.updateByPrimaryKey(applyBorrowAgreement);

        }else {
            applyBorrowAgreement.setCreateTime(new Date());
            return this.applyBorrowAgreementMapper.insert(applyBorrowAgreement);
        }
    }

}
