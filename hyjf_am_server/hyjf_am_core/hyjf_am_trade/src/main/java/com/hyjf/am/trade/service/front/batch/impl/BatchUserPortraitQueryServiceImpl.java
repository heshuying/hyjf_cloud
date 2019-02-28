/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.batch.impl;

import com.hyjf.am.resquest.user.BatchUserPortraitRequest;
import com.hyjf.am.trade.dao.mapper.auto.AccountRechargeMapper;
import com.hyjf.am.trade.dao.mapper.auto.AccountWithdrawMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.front.batch.BatchUserPortraitQueryService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.BatchUserPortraitQueryVO;
import com.hyjf.am.vo.user.UserAndSpreadsUserVO;
import com.hyjf.common.util.GetDate;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: BatchUserPortraitQueryServiceImpl, v0.1 2018/6/28 11:14
 * 查询用户画像所需要的出借相关参数
 */
@Service
public class BatchUserPortraitQueryServiceImpl extends BaseServiceImpl implements BatchUserPortraitQueryService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountRechargeMapper accountRechargeMapper;

    @Autowired
    private AccountWithdrawMapper accountWithdrawMapper;
    /**
     * 查询用户画像所需要的出借相关参数
     * @param batchUserPortraitRequest 需要查询的userId的list
     * @return 结构与UserPortrait相同的封装对象list
     * */
    @Override
    public List<BatchUserPortraitQueryVO> selectInfoForUserPortrait(BatchUserPortraitRequest batchUserPortraitRequest) {
        List<BatchUserPortraitQueryVO> resultList = new ArrayList<>();
        List<UserAndSpreadsUserVO> userAndSpreadsUserVOList = batchUserPortraitRequest.getUserAndSpreadsUserVOList();

        for(UserAndSpreadsUserVO userAndSpreadsUserVO:userAndSpreadsUserVOList){
            Integer userId = userAndSpreadsUserVO.getUserId();
            List<Integer> spreadsUserIdList = userAndSpreadsUserVO.getSpreadsUserId();
            if(userId != null){ // userId有值
                BatchUserPortraitQueryVO batchUserPortraitQueryVO = new BatchUserPortraitQueryVO();
                // 累计收益
                BigDecimal interestSum =  batchUserPortraitQueryCustomizeMapper.getInterestSum(userId);
                if(interestSum == null){
                    interestSum = BigDecimal.valueOf(0.00);
                }

                //散标累计年化出借金额
                BigDecimal investSum = batchUserPortraitQueryCustomizeMapper.getInvestSum(userId);
                if (investSum == null) {
                    investSum = BigDecimal.valueOf(0.00);
                }
                //计划累计年化出借金额
                BigDecimal planSum = batchUserPortraitQueryCustomizeMapper.getPlanSum(userId);
                if (planSum == null) {
                    planSum = new BigDecimal("0.00");
                }
                //累计充值金额
                BigDecimal rechargeSum = batchUserPortraitQueryCustomizeMapper.getRechargeSum(userId);
                if (rechargeSum == null) {
                    rechargeSum = new BigDecimal("0.00");
                }
                //累计提现金额
                BigDecimal withdrawSum = batchUserPortraitQueryCustomizeMapper.getWithdrawSum(userId);
                if (withdrawSum == null) {
                    batchUserPortraitQueryVO.setWithdrawSum(BigDecimal.valueOf(0.00));
                }else{
                    batchUserPortraitQueryVO.setWithdrawSum(withdrawSum);
                }

                //交易笔数
                int tradeNumber = batchUserPortraitQueryCustomizeMapper.getTradeNumber(userId);

                //出借进程
                int tenderRecord = borrowCustomizeMapper.countInvest(userId);
                AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
                AccountRechargeExample.Criteria criteria1 = accountRechargeExample.createCriteria();
                criteria1.andUserIdEqualTo(userId).andStatusEqualTo(2);
                int count = accountRechargeMapper.countByExample(accountRechargeExample);

                if (tenderRecord > 0) {
                    batchUserPortraitQueryVO.setInvestProcess("出借");
                } else if (count > 0) {
                    batchUserPortraitQueryVO.setInvestProcess("充值");
                } else {
                    batchUserPortraitQueryVO.setInvestProcess(null);
                }

                // 最后提现时间
                AccountWithdrawExample accountWithdrawExample = new AccountWithdrawExample();
                accountWithdrawExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(2);
                accountWithdrawExample.setOrderByClause("create_time desc");
                List<AccountWithdraw> accountWithdraws = accountWithdrawMapper.selectByExample(accountWithdrawExample);
                if (CollectionUtils.isNotEmpty(accountWithdraws)) {
                    // 数据库取出来的Date时间戳转Str
                    String createTime = GetDate.date2Str(accountWithdraws.get(0).getCreateTime(),GetDate.datetimeFormat);
                    // Str转unix时间戳
                    Integer addTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(createTime);
                    batchUserPortraitQueryVO.setLastWithdrawTime(addTime);
                }
                // 最后充值时间
                AccountRechargeExample accountRechargeExample1 = new AccountRechargeExample();
                accountRechargeExample1.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(2);
                accountRechargeExample1.setOrderByClause("create_time desc");
                List<AccountRecharge> accountRecharges = accountRechargeMapper.selectByExample(accountRechargeExample1);
                if (CollectionUtils.isNotEmpty(accountRecharges)) {
                    // 数据库取出来的Date时间戳转Str
                    String createTime = GetDate.date2Str(accountRecharges.get(0).getCreateTime(),GetDate.datetimeFormat);
                    // Str转unix时间戳
                    Integer addTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(createTime);
                    batchUserPortraitQueryVO.setLastRechargeTime(addTime);
                }

                // 最后一笔回款时间
                Integer recoverTime = batchUserPortraitQueryCustomizeMapper.getLastRepayTime(userId);
                log.info("用户 ：" + userId + "最后一笔回款时间：{}", recoverTime );
                if (recoverTime != null && recoverTime != 0) {
                    batchUserPortraitQueryVO.setLastRepayTime(recoverTime);
                }

                //账户余额
                AccountExample accountExample = new AccountExample();
                accountExample.createCriteria().andUserIdEqualTo(userId);
                List<Account> accounts = accountMapper.selectByExample(accountExample);
                if(!CollectionUtils.isEmpty(accounts)){
                    Account account = accounts.get(0);
                    BigDecimal bankTotal = account.getBankTotal();
                    bankTotal = (bankTotal==null)?BigDecimal.ZERO:bankTotal;
                    BigDecimal bankInterestSum = account.getBankInterestSum();
                    bankInterestSum = (bankInterestSum==null)?BigDecimal.ZERO:bankInterestSum;
                    batchUserPortraitQueryVO.setBankTotal(bankTotal);

                    //账户可用余额
                    BigDecimal bankBalance = account.getBankBalance();
                    batchUserPortraitQueryVO.setBankBalance(bankBalance);

                    //账户待还金额
                    BigDecimal bankAwait = account.getBankAwait();
                    bankAwait = (bankAwait==null)?BigDecimal.ZERO:bankAwait;
                    BigDecimal planAccountWait = account.getPlanAccountWait();
                    planAccountWait = (planAccountWait==null)?BigDecimal.ZERO:planAccountWait;
                    batchUserPortraitQueryVO.setAccountAwait(bankAwait.add(planAccountWait));

                    //账户冻结金额
                    BigDecimal bankFrost = account.getBankFrost();
                    batchUserPortraitQueryVO.setBankFrost(bankFrost);

                    //资金存留比
                    BigDecimal balance = new BigDecimal(0);
                    AccountRechargeExample rechargeExample = new AccountRechargeExample();
                    AccountRechargeExample.Criteria criteria4 = rechargeExample.createCriteria();
                    criteria4.andUserIdEqualTo(userId).andStatusEqualTo(2);
                    List<AccountRecharge> recharges = accountRechargeMapper.selectByExample(rechargeExample);
                    if (!CollectionUtils.isEmpty(recharges)) {
                        for (AccountRecharge accountRecharge : recharges) {
                            balance = balance.add(accountRecharge.getBalance());
                        }
                    }
                    if (bankInterestSum != null && balance != null && bankInterestSum.add(balance).compareTo(new BigDecimal(0)) > 0 ) {
                        BigDecimal fundRetention = (account.getBankBalance().divide(bankInterestSum.add(balance), 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100)));
                        batchUserPortraitQueryVO.setFundRetention(fundRetention);
                    } else {
                        batchUserPortraitQueryVO.setFundRetention(new BigDecimal(0));
                    }

                }

                //邀约客户注册数
                batchUserPortraitQueryVO.setInviteRegist(spreadsUserIdList.size());

                //邀约充值客户数
                int rechargeCount = 0;
                int tenderCount = 0;
                for (Integer spreadsUserId : spreadsUserIdList) {
                    AccountRechargeExample accountRechargeExample2 = new AccountRechargeExample();
                    accountRechargeExample2.createCriteria().andUserIdEqualTo(spreadsUserId).andStatusEqualTo(2);

                    BorrowTenderExample borrowTenderExample = new BorrowTenderExample();
                    borrowTenderExample.createCriteria().andUserIdEqualTo(spreadsUserId);
                    int count4 = borrowTenderMapper.countByExample(borrowTenderExample);

                    int count3 = accountRechargeMapper.countByExample(accountRechargeExample2);
                    if (count3 > 0) {
                        rechargeCount++;
                    }
                    if (count4 > 0) {
                        tenderCount++;
                    }
                }
                batchUserPortraitQueryVO.setInviteRecharge(rechargeCount);
                batchUserPortraitQueryVO.setInviteTender(tenderCount);


                //客均收益率
                BigDecimal tenderSum = new BigDecimal(0);
                BorrowTenderExample borrowTenderExample1 = new BorrowTenderExample();
                borrowTenderExample1.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(1);
                List<BorrowTender> borrowTenders = borrowTenderMapper.selectByExample(borrowTenderExample1);
                for (BorrowTender borrowTender : borrowTenders) {
                    tenderSum = tenderSum.add(borrowTender.getAccount());
                }
                if (tenderSum.compareTo(new BigDecimal(0)) > 0) {
                    BigDecimal yield = interestSum.divide(tenderSum, 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
                    batchUserPortraitQueryVO.setYield(yield);
                } else {
                    batchUserPortraitQueryVO.setYield(new BigDecimal(0));
                }

                // 组装参数
                batchUserPortraitQueryVO.setUserId(userId);
                batchUserPortraitQueryVO.setInterestSum(interestSum);
                batchUserPortraitQueryVO.setInvestSum(investSum.add(planSum));
                batchUserPortraitQueryVO.setRechargeSum(rechargeSum);
                batchUserPortraitQueryVO.setTradeNumber(tradeNumber);
                resultList.add(batchUserPortraitQueryVO);
            }

        }
        return resultList;
    }
}
