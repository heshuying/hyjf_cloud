/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.customize.CustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.BaseService;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * 资金服务:BaseService实现类
 *
 * @author liuyang
 * @version BaseServiceImpl, v0.1 2018/6/27 9:33
 */
public class BaseServiceImpl extends CustomizeMapper implements BaseService {

    /**
     * 根据标的编号检索标的信息
     *
     * @param borrowNid
     * @return
     */
    @Override
    public Borrow getBorrow(String borrowNid) {
        BorrowExample example = new BorrowExample();
        BorrowExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowNidEqualTo(borrowNid);
        List<Borrow> list = this.borrowMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据标的编号检索标的借款详情
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowInfo getBorrowInfoByNid(String borrowNid) {
        BorrowInfoExample example = new BorrowInfoExample();
        BorrowInfoExample.Criteria cra = example.createCriteria();
        cra.andBorrowNidEqualTo(borrowNid);
        List<BorrowInfo> list=this.borrowInfoMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 获取借款人总的还款表数据
     * @auther: hesy
     * @date: 2018/7/9
     */
    @Override
    public BorrowRepay getBorrowRepay(String borrowNid) {
        BorrowRepayExample borrowRepayExample = new BorrowRepayExample();
        BorrowRepayExample.Criteria borrowRepayCrt = borrowRepayExample.createCriteria();
        borrowRepayCrt.andBorrowNidEqualTo(borrowNid);
        List<BorrowRepay> borrowRepays = borrowRepayMapper.selectByExample(borrowRepayExample);
        if (borrowRepays != null && borrowRepays.size() == 1) {
            return borrowRepays.get(0);
        } else {
            return null;
        }
    }

    /**
     * 获取某一期的还款计划
     * @auther: hesy
     * @date: 2018/7/9
     */
    @Override
    public BorrowRepayPlan getRepayPlan(String borrowNid, int period) {
        BorrowRepayPlanExample borrowRepayPlanExample = new BorrowRepayPlanExample();
        BorrowRepayPlanExample.Criteria borrowRepayPlanCrt = borrowRepayPlanExample.createCriteria();
        borrowRepayPlanCrt.andBorrowNidEqualTo(borrowNid);
        borrowRepayPlanCrt.andRepayPeriodEqualTo(period);
        List<BorrowRepayPlan> borrowRepayPlans = borrowRepayPlanMapper.selectByExample(borrowRepayPlanExample);
        if (borrowRepayPlans != null && borrowRepayPlans.size() == 1) {
            return borrowRepayPlans.get(0);
        } else {
            return null;
        }
    }

    /**
     * 获取用户的账户信息
     * @auther: hesy
     * @date: 2018/7/9
     */
    @Override
    public Account getAccount(Integer userId) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<Account> listAccount = this.accountMapper.selectByExample(example);
        if (listAccount != null && listAccount.size() > 0) {
            return listAccount.get(0);
        }
        return null;
    }

    /**
     * 获取用户信息
     * @auther: hesy
     * @date: 2018/7/9
     */
    @Override
    public RUser getRUser(Integer userId){
        return rUserMapper.selectByPrimaryKey(userId);
    }

}
