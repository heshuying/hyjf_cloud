/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.customize.CustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.BaseService;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

/**
 * 资金服务:BaseService实现类
 *
 * @author liuyang
 * @version BaseServiceImpl, v0.1 2018/6/27 9:33
 */
public class BaseServiceImpl extends CustomizeMapper implements BaseService {
    protected Logger logger = LoggerFactory.getLogger(getClass());
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
     * 获取用户的账户信息
     *
     * @param userId
     * @return 获取用户的账户信息
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
     * 取得本库冗余的用户信息
     * @param userId
     * @return
     */
    @Override
    public RUser getRUser(Integer userId) {
        RUserExample example = new RUserExample();
        RUserExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        List<RUser> list=this.rUserMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 取得本库冗余的用户信息
     * @param userName
     * @return
     */
    @Override
    public RUser getRUser(String userName) {
        RUserExample example = new RUserExample();
        RUserExample.Criteria cra = example.createCriteria();
        cra.andUsernameEqualTo(userName);
        List<RUser> list=this.rUserMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 取得本库冗余的推荐人信息
     * @param userId
     * @return
     */
    @Override
    public RUser getRefUser(Integer userId) {
        return rUserCustomizeMapper.selectRefUserInfoByUserId(userId);
    }

    /**
     * 汇计划全部流程用更新用户的账户表
     * @param hjhProcessFlg
     * @param userId
     * @param amount
     * @param interest
     * @return
     */
    @Override
    public Boolean updateAccountForHjh(String hjhProcessFlg, Integer userId, BigDecimal amount, BigDecimal interest) {
        //更新用户的账户表
        Account accountBean = new Account();
        accountBean.setUserId(userId);
        switch (hjhProcessFlg) {
            case CustomConstants.HJH_PROCESS_B:
                //计划订单-自动投标
            case CustomConstants.HJH_PROCESS_BF:
                //计划订单-自动投标/复投
                //amount=自动投标金额=b
                accountBean.setPlanBalance(amount.negate()); // 汇计划可用余额  -b
                accountBean.setPlanFrost(amount); // 汇计划冻结金额 +b
                break;

            case CustomConstants.HJH_PROCESS_D:
                //计划订单-自动承接(投资)
            case CustomConstants.HJH_PROCESS_DF:
                //计划订单-自动承接(复投)
                //amount=自动投标金额=d
                accountBean.setPlanBalance(amount.negate()); // 汇计划可用余额  -d
                break;

            case CustomConstants.HJH_PROCESS_F:
                //计划订单锁定期-债权回款（承接和还款，要复投）
                //amount=回款总额=f
                accountBean.setPlanBalance(amount); // 汇计划可用余额  +f
                break;
            case CustomConstants.HJH_PROCESS_H:
                //汇计划清算-债权回款（承接和还款，不复投）
                //amount=回款总额=h
                accountBean.setPlanFrost(amount); // 汇计划冻结金额 +h
                break;
            default:
                break;
        }

        Boolean accountFlag = this.adminAccountCustomizeMapper.updateAccountForHjhProcess(accountBean) > 0 ? true : false;
        if (!accountFlag) {
            throw new RuntimeException("用户账户信息表更新失败");
        }

        return accountFlag;
    }

    /**
     * 汇计划重算更新汇计划加入明细表
     * @param hjhProcessFlg
     * @param id
     * @param amount
     * @param interest
     * @return
     */
    @Override
    public Boolean updateHjhAccedeForHjh(String hjhProcessFlg, Integer id, BigDecimal amount, BigDecimal interest, BigDecimal serviceFee) {
        //更新用户的账户表
        HjhAccede hjhAccede = new HjhAccede();

        //获取当前时间
        hjhAccede.setUpdateTime(GetDate.getDate());
        hjhAccede.setUpdateUser(1);
        hjhAccede.setId(id);
        switch (hjhProcessFlg) {
            case CustomConstants.HJH_PROCESS_B:
                //计划订单-自动投标
                //amount=自动投标金额=b
                hjhAccede.setAlreadyInvest(amount);// 计划订单已投资金额 +b
            case CustomConstants.HJH_PROCESS_BF:
                //计划订单-自动复投
                //amount=自动投标金额=b
                hjhAccede.setAvailableInvestAccount(amount.negate()); // 计划订单可用余额  -b
                hjhAccede.setFrostAccount(amount); // 计划订单冻结金额 +b
                // add 汇计划三期 计划订单投资笔数累加 liubin 20180515 start
                hjhAccede.setInvestCounts(1);// 投资笔数 +1
                // add 汇计划三期 计划订单投资笔数累加 liubin 20180515 end
                break;

            case CustomConstants.HJH_PROCESS_D:
                //计划订单-自动承接(投资)
                //amount=自动投标金额=d
                hjhAccede.setAlreadyInvest(amount);// 计划订单已投资金额 +d
            case CustomConstants.HJH_PROCESS_DF:
                //计划订单-自动承接(投资/复投)
                //amount=自动投标金额=d
                hjhAccede.setAvailableInvestAccount(amount.negate()); // 计划订单可用余额  -d
                // add 汇计划三期 计划订单投资笔数累加 liubin 20180515 start
                hjhAccede.setInvestCounts(1); // 投资笔数 +1
                // add 汇计划三期 计划订单投资笔数累加 liubin 20180515 end
                break;
            case CustomConstants.HJH_PROCESS_F:
                //计划订单锁定期-债权回款（承接和还款，要复投）
                //amount=回款总额=f
                hjhAccede.setAvailableInvestAccount(amount); // 计划订单可用余额  +f
                // add 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 start
                hjhAccede.setLqdServiceFee(serviceFee); // 债转服务费累计
                // add 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 end
                break;
            case CustomConstants.HJH_PROCESS_H:
                //汇计划清算-债权回款（承接和还款，不复投）
                //amount=回款总额=h
                hjhAccede.setFrostAccount(amount); // 计划订单冻结金额 +h
                // add 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 start
                hjhAccede.setLqdServiceFee(serviceFee); // 债转服务费累计
                // add 汇计划三期 汇计划自动投资(收债转服务费) liubin 20180515 end
                break;
            default:
                break;
        }

        Boolean accountFlag = this.hjhPlanCustomizeMapper.updateHjhAccedeForHjhProcess(hjhAccede) > 0 ? true : false;
        if (!accountFlag) {
            throw new RuntimeException("用户账户信息表更新失败");
        }

        return accountFlag;
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


}
