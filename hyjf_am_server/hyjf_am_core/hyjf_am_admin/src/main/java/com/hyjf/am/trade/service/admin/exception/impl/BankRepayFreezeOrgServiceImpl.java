package com.hyjf.am.trade.service.admin.exception.impl;

import com.hyjf.am.resquest.admin.RepayFreezeOrgRequest;
import com.hyjf.am.trade.dao.model.auto.BankRepayOrgFreezeLog;
import com.hyjf.am.trade.dao.model.auto.BankRepayOrgFreezeLogExample;
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

    /**
     * 删除垫付机构临时日志,外部调用
     */
    @Override
    public Integer deleteOrgFreezeTempLogs(String orderId, String borrowNid) {
        BankRepayOrgFreezeLogExample example = new BankRepayOrgFreezeLogExample();
        BankRepayOrgFreezeLogExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        if(StringUtils.isNotBlank(borrowNid)){
            criteria.andBorrowNidEqualTo(borrowNid);
        }
        List<BankRepayOrgFreezeLog> log = this.bankRepayOrgFreezeLogMapper.selectByExample(example);
        int result = 0;
        if (log != null && log.size() > 0) {
            for (int i = 0; i < log.size(); i++) {
                BankRepayOrgFreezeLog record = log.get(i);
                record.setDelFlag(1);// 0 有效 1无效
                int flag = this.bankRepayOrgFreezeLogMapper.updateByPrimaryKey(record);
                result += flag;
                if (flag > 0) {
                    logger.info("=============还款冻结成功,删除垫付机构还款冻结临时日志成功=========");
                } else {
                    logger.info("==============删除垫付机构还款冻结临时日志失败============");
                }
            }
        }
        return result;
    }

    /**
     * 查询垫付机构冻结列表
     */
    @Override
    public List<BankRepayOrgFreezeLog> getBankRepayOrgFreezeLogList(String orderId, String borrowNid){
        BankRepayOrgFreezeLogExample orgExample = new BankRepayOrgFreezeLogExample();
        BankRepayOrgFreezeLogExample.Criteria criteria = orgExample.createCriteria();
        if(StringUtils.isNotBlank(orderId)){
            criteria.andOrderIdEqualTo(orderId);
        }
        if(StringUtils.isNotBlank(borrowNid)) {
            criteria.andBorrowNidEqualTo(borrowNid).andDelFlagEqualTo(0);
        }
        criteria.andDelFlagEqualTo(0);
        return bankRepayOrgFreezeLogMapper.selectByExample(orgExample);
    }
}
