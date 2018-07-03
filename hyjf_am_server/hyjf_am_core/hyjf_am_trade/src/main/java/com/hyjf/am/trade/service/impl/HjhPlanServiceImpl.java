/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.mapper.customize.trade.HjhPlanCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.trade.UserHjhInvistDetailCustomize;
import com.hyjf.am.trade.service.HjhPlanService;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fuqiang
 * @version HjhPlanServiceImpl, v0.1 2018/6/13 17:23
 */
@Service
public class HjhPlanServiceImpl implements HjhPlanService {

    @Autowired
    private HjhInstConfigMapper hjhInstConfigMapper;

    @Autowired
    private HjhLabelMapper hjhLabelMapper;

    @Autowired
    private HjhPlanMapper hjhPlanMapper;

    @Autowired
    private HjhAccedeMapper hjhAccedeMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountListMapper accountListMapper;

    @Autowired
    private HjhPlanCustomizeMapper hjhPlanCustomizeMapper;

    @Override
    public List<HjhInstConfig> selectHjhInstConfigByInstCode(String instCode) {
        HjhInstConfigExample example = new HjhInstConfigExample();
        HjhInstConfigExample.Criteria cra = example.createCriteria();
        if (StringUtils.isNotEmpty(instCode)&&!"-1".equals(instCode)) {
            cra.andInstCodeEqualTo(instCode);
        }
        cra.andDelFlagEqualTo(0);
        List<HjhInstConfig> list = this.hjhInstConfigMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<HjhLabel> seleHjhLabelByBorrowStyle(String borrowStyle) {
        HjhLabelExample example = new HjhLabelExample();
        HjhLabelExample.Criteria cra = example.createCriteria();

        cra.andDelFlagEqualTo(0);
        cra.andLabelStateEqualTo(1);
        cra.andBorrowStyleEqualTo(borrowStyle);
        cra.andIsCreditEqualTo(0); // 原始标
        cra.andIsLateEqualTo(0); // 是否逾期
        example.setOrderByClause(" update_time desc ");

        List<HjhLabel> list = this.hjhLabelMapper.selectByExample(example);
        return list;
    }

    /**
     * @param planNid
     * @Description 根据计划编号查询计划
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 14:08
     */
    @Override
    public HjhPlan getHjhPlanByNid(String planNid) {
        HjhPlanExample example = new HjhPlanExample();
        HjhPlanExample.Criteria cra = example.createCriteria();
        ;
        cra.andPlanNidEqualTo(planNid);
        List<HjhPlan> list = this.hjhPlanMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 插入计划明细表
     *
     * @param accedeVO
     * @param userAccount
     * @return
     */
    @Override
    public int insertHJHPlanAccede(HjhAccedeVO accedeVO, Account userAccount) {
        TenderRequest request = accedeVO.getRequest();
        HjhAccede planAccede = new HjhAccede();
        BeanUtils.copyProperties(accedeVO, planAccede);
        // 插入投资记录
        int planAccedeFlag = hjhAccedeMapper.insertSelective(planAccede);
        if(planAccedeFlag==1){
            // 插入资金明细表
            this.insertHJHPlanAccoutList(request,userAccount);
        }
        return 1;
    }

    /**
     * 获取汇计划投资详情
     * @param params
     * @return
     */
    @Override
    public UserHjhInvistDetailCustomize selectUserHjhInvistDetail(Map<String, Object> params) {
        return hjhPlanCustomizeMapper.selectUserHjhInvistDetail(params);
    }

    /**
     * 插入资金明细表
     * @param request
     * @param accedeAccount
     */
    private void insertHJHPlanAccoutList(TenderRequest request, Account accedeAccount) {
        BigDecimal accountDecimal = new BigDecimal(request.getAccount());//用户投资金额
        AccountVO tenderAccount = request.getTenderAccount();
        // 冻结金额(查询当前用户)
        BigDecimal frost = tenderAccount.getBankFrost();
        // 总金额(查询当前用户)
        BigDecimal total = tenderAccount.getBankTotal();

        Account account = new Account();
        account.setUserId(request.getUser().getUserId());
        // 计划加入总金额
        account.setPlanAccedeTotal(accountDecimal);
        // 计划可用余额
        account.setPlanBalance(accountDecimal);

        // 计划累计待收总额
        account.setPlanInterestWait(request.getEarnings());
        // 计划累计待收本金
        account.setPlanCapitalWait(accountDecimal);
        // 计划待收利息
        account.setPlanAccountWait(accountDecimal.add(request.getEarnings()));

        // 更新用户计划账户
        boolean accountFlag = hjhPlanCustomizeMapper.updateOfPlanJoin(account)> 0 ? true : false;

        // 组装accountList
        AccountList accountList = new AccountList();
        accountList.setIsBank(1);
        accountList.setIsShow(0);
        accountList.setNid(request.getPlanOrderId());//插入生成的计划订单号
        accountList.setAccedeOrderId(request.getPlanOrderId());//也插入生成的计划订单号
        accountList.setUserId(request.getUser().getUserId());
        accountList.setAmount(accountDecimal);//插入用户加入金额
        accountList.setType(2);// 收支类型1收入2支出3冻结
        accountList.setTrade("hjh_invest");// 汇计划投资
        accountList.setTradeCode("balance");
        accountList.setIp(request.getIp());
        accountList.setOperator(request.getUser().getUserId() + "");
        accountList.setRemark(request.getBorrowNid());
        accountList.setBankBalance(accedeAccount.getBankBalance());//江西银行账户余额
        accountList.setPlanBalance(accedeAccount.getPlanBalance());//汇计划账户可用余额
        accountList.setBalance(accedeAccount.getBalance().subtract(accountDecimal));
        //accountList.setInterest(new BigDecimal(0));
        accountList.setAwait(accedeAccount.getAwait());
        accountList.setPlanFrost(accedeAccount.getPlanFrost());
        accountList.setBankFrost(frost);
        accountList.setRepay(new BigDecimal(0));
        accountList.setBankTotal(total);
        accountList.setWeb(0);
        accountList.setAccountId(request.getBankOpenAccount().getAccount());
        // 插入accountList
        accountListMapper.insertSelective(accountList);
        Map<String, Object> planUpdate = new HashMap<String, Object>();
        planUpdate.put("planId", request.getBorrowNid());
        planUpdate.put("accountDecimal", accountDecimal);//用户加入金额
        planUpdate.put("earnings", request.getEarnings());
        // 更新计划表
        boolean updateBorrowAccountFlag = hjhPlanCustomizeMapper.updateByDebtPlanId(planUpdate) > 0 ? true : false;
        // TODO: 2018/6/22  更新  平台累积投资   开始
        /*(5)更新  平台累积投资   开始*/
       /* List<CalculateInvestInterest> calculates = this.calculateInvestInterestMapper.selectByExample(new CalculateInvestInterestExample());
        if (calculates != null && calculates.size() > 0) {
            CalculateInvestInterest calculateNew = new CalculateInvestInterest();
            calculateNew.setTenderSum(accountDecimal);
            calculateNew.setId(calculates.get(0).getId());
            calculateNew.setCreateTime(GetDate.getDate(nowTime));
            this.webCalculateInvestInterestCustomizeMapper.updateCalculateInvestByPrimaryKey(calculateNew);
        }*/
    }
}
