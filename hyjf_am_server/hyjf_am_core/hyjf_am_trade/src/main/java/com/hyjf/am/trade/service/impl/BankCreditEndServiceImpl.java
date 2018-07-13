/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.resquest.trade.BankCreditEndListRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.BankCreditEndService;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author liubin
 * @version BankCreditEndServiceImpl, v0.1 2018/7/6 18:37
 */
@Service
public class BankCreditEndServiceImpl extends BaseServiceImpl implements BankCreditEndService {


    @Override
    public int insertBankCreditEndForCreditEnd(HjhDebtCredit hjhDebtCredit, String tenderAccountId, String tenderAuthCode) {

        String borrowNid = hjhDebtCredit.getBorrowNid();
        Integer tenderUserId = hjhDebtCredit.getUserId();

        Borrow borrow = this.getBorrow(borrowNid);
        if (borrow == null) {
            throw new RuntimeException("结束债券接口：标的"+borrowNid+"不存在");
        }
        Account account = this.getAccount(borrow.getUserId());
        if (account == null) {
            throw new RuntimeException("结束债券接口：借款人"+borrow.getUserId()+"银行未开户");
        }

        String orderId = GetOrderIdUtils.getOrderId2(tenderUserId);
        logger.info(borrowNid+" 直投还款结束债权  借款人: "+borrow.getUserId()+"-"+account.getAccountId()+" 投资人: "+tenderUserId+"-"+tenderAccountId+" 授权码: "+tenderAuthCode+" 原始订单号: "+hjhDebtCredit.getSellOrderId());

        BankCreditEnd record = new BankCreditEnd();
        record.setUserId(borrow.getUserId());
        record.setTenderUserId(tenderUserId);
        record.setAccountId(account.getAccountId());
        record.setTenderAccountId(tenderAccountId);
        record.setOrderId(orderId);
        record.setBorrowNid(borrowNid);
        record.setAuthCode(tenderAuthCode);
        record.setCreditEndType(2); // 结束债权类型（1:还款，2:散标债转，3:计划债转）
        record.setStatus(0);
        record.setOrgOrderId(hjhDebtCredit.getSellOrderId());

        Date nowDate = GetDate.getDate();
        record.setCreateUser(tenderUserId);
        record.setCreateTime(nowDate);
        record.setUpdateUser(tenderUserId);
        record.setUpdateTime(nowDate);

        return this.bankCreditEndMapper.insertSelective(record);
    }

    /**
     * 债权结束异常列表
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public List<BankCreditEnd> getCreditEndList(BankCreditEndListRequest requestBean){
        BankCreditEndExample example = new BankCreditEndExample();
        BankCreditEndExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(requestBean.getUserIdSrch())){
            criteria.andUserIdEqualTo(Integer.parseInt(requestBean.getUserIdSrch()));
        }
        if(StringUtils.isNotBlank(requestBean.getTenderUserIdSrch())){
            criteria.andTenderUserIdEqualTo(Integer.parseInt(requestBean.getTenderUserIdSrch()));
        }
        if(StringUtils.isNotBlank(requestBean.getBatchNoSrch())){
            criteria.andBatchNoEqualTo(requestBean.getBatchNoSrch());
        }
        if(StringUtils.isNotBlank(requestBean.getOrderIdSrch())){
            criteria.andOrderIdEqualTo(requestBean.getOrderIdSrch());
        }
        if(StringUtils.isNotBlank(requestBean.getStatusSrch())){
            criteria.andStateEqualTo(Integer.parseInt(requestBean.getStatusSrch()));
        }
        if(requestBean.getLimitStart() != null && requestBean.getLimitStart() >=0){
            example.setLimitStart(requestBean.getLimitStart());
        }
        if(requestBean.getLimitEnd() != null && requestBean.getLimitEnd() >=0){
            example.setLimitEnd(requestBean.getLimitEnd());
        }

        return bankCreditEndMapper.selectByExample(example);
    }

    /**
     * 债权结束异常总记录数
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public Integer getCreditEndCount(BankCreditEndListRequest requestBean){
        BankCreditEndExample example = new BankCreditEndExample();
        BankCreditEndExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(requestBean.getUserIdSrch())){
            criteria.andUserIdEqualTo(Integer.parseInt(requestBean.getUserIdSrch()));
        }
        if(StringUtils.isNotBlank(requestBean.getTenderUserIdSrch())){
            criteria.andTenderUserIdEqualTo(Integer.parseInt(requestBean.getTenderUserIdSrch()));
        }
        if(StringUtils.isNotBlank(requestBean.getBatchNoSrch())){
            criteria.andBatchNoEqualTo(requestBean.getBatchNoSrch());
        }
        if(StringUtils.isNotBlank(requestBean.getOrderIdSrch())){
            criteria.andOrderIdEqualTo(requestBean.getOrderIdSrch());
        }
        if(StringUtils.isNotBlank(requestBean.getStatusSrch())){
            criteria.andStateEqualTo(Integer.parseInt(requestBean.getStatusSrch()));
        }

        return bankCreditEndMapper.countByExample(example);
    }

    /**
     * 更新
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public int updateBankCreditEnd(BankCreditEnd bankCreditEnd) {
        BankCreditEndExample example = new BankCreditEndExample();
        example.createCriteria().andOrderIdEqualTo(bankCreditEnd.getOrderId());
        bankCreditEnd.setUpdateTime(GetDate.getNowTime());
        return this.bankCreditEndMapper.updateByExampleSelective(bankCreditEnd, example);
    }

    /**
     * 通过orderId检索
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public BankCreditEnd selectByOrderId(String orderId) {
        BankCreditEndExample example = new BankCreditEndExample();
        example.createCriteria().andOrderIdEqualTo(orderId);//订单号
        List<BankCreditEnd> list = this.bankCreditEndMapper.selectByExample(example);
        if (list != null && list.size() == 1){
            return list.get(0);
        }
        return null;
    }

    /**
     * 批次恢复为初始状态
     * @auther: hesy
     * @date: 2018/7/12
     */
    @Override
    public int updateCreditEndForInitial(BankCreditEnd bankCreditEnd) {
        BankCreditEndExample example = new BankCreditEndExample();
        example.createCriteria().andOrderIdEqualTo(bankCreditEnd.getOrderId());//订单号
        example.createCriteria().andStatusGreaterThanOrEqualTo(10);//订单状态为失败
        bankCreditEnd.setStatus(0);//批次状态
        bankCreditEnd.setOrderId(GetOrderIdUtils.getOrderId2(Integer.valueOf(bankCreditEnd.getTenderUserId())));//批次状态
        bankCreditEnd.setUpdateTime(GetDate.getNowTime());
        return this.bankCreditEndMapper.updateByExampleSelective(bankCreditEnd, example);
    }

}
