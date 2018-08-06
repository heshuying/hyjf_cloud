/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.batch.impl;

import com.hyjf.am.trade.dao.mapper.auto.AccountRechargeMapper;
import com.hyjf.am.trade.dao.mapper.auto.AccountWithdrawMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.front.batch.BatchUserPortraitQueryService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.BatchUserPortraitQueryVO;
import com.hyjf.common.util.GetDate;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: BatchUserPortraitQueryServiceImpl, v0.1 2018/6/28 11:14
 * 查询用户画像所需要的投资相关参数
 */
@Service
public class BatchUserPortraitQueryServiceImpl extends BaseServiceImpl implements BatchUserPortraitQueryService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountRechargeMapper accountRechargeMapper;

    @Autowired
    private AccountWithdrawMapper accountWithdrawMapper;
    /**
     * 查询用户画像所需要的投资相关参数
     * @param userIds 需要查询的userId的list
     * @return 结构与UserPortrait相同的封装对象list
     * */
    @Override
    public List<BatchUserPortraitQueryVO> selectInfoForUserPortrait(List<Integer> userIds) {
        log.info("BatchUserPortraitQueryServiceImpl............userIds=========={}",userIds);
        List<BatchUserPortraitQueryVO> resultList = new ArrayList<>();

        for(Integer userId:userIds){
            if(userId != null){ // userId有值
                BatchUserPortraitQueryVO batchUserPortraitQueryVO = new BatchUserPortraitQueryVO();
                // 累计收益
                BigDecimal interestSum =  batchUserPortraitQueryCustomizeMapper.getInterestSum(userId);

                //散标累计年化投资金额
                BigDecimal investSum = batchUserPortraitQueryCustomizeMapper.getInvestSum(userId);
                if (investSum == null) {
                    investSum = new BigDecimal("0.00");
                }
                //计划累计年化投资金额
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
                    withdrawSum = new BigDecimal("0.00");
                }
                //交易笔数
                int tradeNumber = batchUserPortraitQueryCustomizeMapper.getTradeNumber(userId);

                //投资进程
                int tenderRecord = borrowCustomizeMapper.countInvest(userId);
                AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
                AccountRechargeExample.Criteria criteria1 = accountRechargeExample.createCriteria();
                criteria1.andUserIdEqualTo(userId).andStatusEqualTo(2);
                int count = accountRechargeMapper.countByExample(accountRechargeExample);

                if (tenderRecord > 0) {
                    batchUserPortraitQueryVO.setInvestProcess("投资");
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
                if (!CollectionUtils.isEmpty(accountWithdraws)) {
                    // 数据库取出来的Date时间戳转Str
                    String createTime = GetDate.date2Str(accountWithdraws.get(0).getCreateTime(),GetDate.datetimeFormat);
                    // Str转unix时间戳
                    int addTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(createTime);
                    batchUserPortraitQueryVO.setLastWithdrawTime(addTime);
                }
                // 最后充值时间
                AccountRechargeExample accountRechargeExample1 = new AccountRechargeExample();
                accountRechargeExample1.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(2);
                accountRechargeExample1.setOrderByClause("create_time desc");
                List<AccountRecharge> accountRecharges = accountRechargeMapper.selectByExample(accountRechargeExample1);
                if (!CollectionUtils.isEmpty(accountRecharges)) {
                    // 数据库取出来的Date时间戳转Str
                    String createTime = GetDate.date2Str(accountRecharges.get(0).getCreateTime(),GetDate.datetimeFormat);
                    // Str转unix时间戳
                    int addTime = GetDate.strYYYYMMDDHHMMSS2Timestamp2(createTime);
                    batchUserPortraitQueryVO.setLastRechargeTime(addTime);
                }
                // 组装参数
                batchUserPortraitQueryVO.setUserId(userId);
                batchUserPortraitQueryVO.setInterestSum(interestSum);
                batchUserPortraitQueryVO.setInvestSum(investSum.add(planSum));
                batchUserPortraitQueryVO.setRechargeSum(rechargeSum);
                batchUserPortraitQueryVO.setWithdrawSum(withdrawSum);
                batchUserPortraitQueryVO.setTradeNumber(tradeNumber);
                resultList.add(batchUserPortraitQueryVO);
            }

        }
        return resultList;
    }
}
