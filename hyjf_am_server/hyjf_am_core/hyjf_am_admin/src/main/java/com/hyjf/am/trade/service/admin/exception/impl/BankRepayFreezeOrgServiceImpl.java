package com.hyjf.am.trade.service.admin.exception.impl;

import com.hyjf.am.resquest.admin.RepayFreezeOrgRequest;
import com.hyjf.am.trade.dao.model.customize.BankRepayFreezeOrgCustomize;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hesy
 * @version BankRepayFreezeOrgService, v0.1 2018/10/19 11:17
 */
@Service
public class BankRepayFreezeOrgServiceImpl extends BaseServiceImpl implements com.hyjf.am.trade.service.admin.exception.BankRepayFreezeOrgService {
    @Override
    public Integer selectCount(RepayFreezeOrgRequest requestBean) {
        Map<String,Object> paraMap = new HashMap<>();
        if(StringUtils.isNotBlank(requestBean.getBorrowNid())){
            paraMap.put("borrowNid", requestBean.getBorrowNid());
        }
        if(StringUtils.isNotBlank(requestBean.getInstCode())){
            paraMap.put("instCode", requestBean.getInstCode());
        }
        if(StringUtils.isNotBlank(requestBean.getPlanNid())){
            paraMap.put("planNid", requestBean.getPlanNid());
        }
        if(StringUtils.isNotBlank(requestBean.getBorrowUserName())){
            paraMap.put("borrowUserName", requestBean.getBorrowUserName());
        }
        if(StringUtils.isNotBlank(requestBean.getRepayUserName())){
            paraMap.put("repayUserName", requestBean.getRepayUserName());
        }
        if(StringUtils.isNotBlank(requestBean.getOrderId())){
            paraMap.put("orderId", requestBean.getOrderId());
        }
        if(StringUtils.isNotBlank(requestBean.getSubmitTimeStartSrch())){
            paraMap.put("submitTimeStartSrch", requestBean.getSubmitTimeStartSrch());
        }
        if(StringUtils.isNotBlank(requestBean.getSubmitTimeEndSrch())){
            paraMap.put("submitTimeEndSrch", requestBean.getSubmitTimeEndSrch());
        }
        Integer result = bankRepayFreezeOrgCustomizeMapper.selectCount(paraMap);
        if(result == null){
            result = 0;
        }
        return result;
    }

    @Override
    public List<BankRepayFreezeOrgCustomize> selectList(RepayFreezeOrgRequest requestBean) {
        Map<String,Object> paraMap = new HashMap<>();
        if(StringUtils.isNotBlank(requestBean.getBorrowNid())){
            paraMap.put("borrowNid", requestBean.getBorrowNid());
        }
        if(StringUtils.isNotBlank(requestBean.getInstCode())){
            paraMap.put("instCode", requestBean.getInstCode());
        }
        if(StringUtils.isNotBlank(requestBean.getPlanNid())){
            paraMap.put("planNid", requestBean.getPlanNid());
        }
        if(StringUtils.isNotBlank(requestBean.getBorrowUserName())){
            paraMap.put("borrowUserName", requestBean.getBorrowUserName());
        }
        if(StringUtils.isNotBlank(requestBean.getRepayUserName())){
            paraMap.put("repayUserName", requestBean.getRepayUserName());
        }
        if(StringUtils.isNotBlank(requestBean.getOrderId())){
            paraMap.put("orderId", requestBean.getOrderId());
        }
        if(StringUtils.isNotBlank(requestBean.getSubmitTimeStartSrch())){
            paraMap.put("submitTimeStartSrch", requestBean.getSubmitTimeStartSrch());
        }
        if(StringUtils.isNotBlank(requestBean.getSubmitTimeEndSrch())){
            paraMap.put("submitTimeEndSrch", requestBean.getSubmitTimeEndSrch());
        }
        if(requestBean.getLimitStart() != null && requestBean.getLimitStart() >= 0){
            paraMap.put("limitStart", requestBean.getLimitStart());
        }
        if(requestBean.getLimitEnd() != null && requestBean.getLimitEnd() >= 0){
            paraMap.put("limitEnd", requestBean.getLimitEnd());
        }
        return bankRepayFreezeOrgCustomizeMapper.selectList(paraMap);
    }
}
