/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.hjh.impl;

import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.front.hjh.HjhAutoEndCreditService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.GetDate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 汇计划自动结束前一天未完全承接的债转
 *
 * @author liuyang
 * @version HjhAutoEndCreditServiceImpl, v0.1 2018/6/28 10:52
 */
@Service
public class HjhAutoEndCreditServiceImpl extends BaseServiceImpl implements HjhAutoEndCreditService {

    /**
     * 检索当天的未完全承接完成的债转
     *
     * @return
     */
    @Override
    public List<HjhDebtCredit> selectHjhDebtCreditList() {
        HjhDebtCreditExample example = new HjhDebtCreditExample();
        HjhDebtCreditExample.Criteria cra = example.createCriteria();
        List<Integer> statusList = new ArrayList<Integer>();
        statusList.add(0);
        statusList.add(1);
        cra.andCreditStatusIn(statusList);
//        cra.andPlanOrderIdEqualTo("25286963496823616880");
//        List<String> ordidList = new ArrayList<>();
//        ordidList.add("25288687760674416938");
//        cra.andPlanOrderIdIn(ordidList);
        cra.andCreateTimeLessThanOrEqualTo(GetDate.stringToDate(GetDate.getDayEnd(GetDate.getDate())));
        List<HjhDebtCredit> hjhDebtCreditList = this.hjhDebtCreditMapper.selectByExample(example);
        return hjhDebtCreditList;
    }


    /**
     * 更新未完全承接的债转的状态
     *
     * @param hjhDebtCredit
     */
    @Override
    public void updateHjhDebtCreditStatus(HjhDebtCredit hjhDebtCredit) throws Exception {
        // 根据债转的计划编号查询计划
        String planNid = hjhDebtCredit.getPlanNidNew();
        Integer now = GetDate.getNowTime10();
        // 如果新的计划编号为空
        if (StringUtils.isBlank(planNid)) {
            logger.info("清算出的债转未绑定新的计划:债转编号:[" + hjhDebtCredit.getCreditNid() + "]");
            return;
        }
        // 如果计划编号不为空
        // 根据计划编号查询计划
        HjhPlan hjhPlan = this.selectHjhPlanByPlanNid(planNid);
        // 计划不存在
        if (hjhPlan == null) {
            throw new RuntimeException("根据计划编号查询计划不存在,计划编号:[" + planNid + "].");
        }

        // 原始加入订单号
        String accedeOrderId = hjhDebtCredit.getPlanOrderId();
        // 根据订单号查询查询加入订单
        HjhAccede hjhAccede = this.selectHjhPlanAccedeByOrderId(accedeOrderId);

        // 如果计划订单为空
        if (hjhAccede == null) {
            throw new RuntimeException("根据加入订单号查询加入订单失败,加入计划订单号:[" + accedeOrderId + "].");
        }
        // 如果计划加入订单的状态不为退出中,暂不结束
        if (hjhAccede.getOrderStatus() != 5) {
            logger.info("计划加入订单不为退出中,计划清算的债转暂不结束,计划加入订单号:[" + accedeOrderId + "]");
            return;
        }
        // 债转编号
        String creditNid = hjhDebtCredit.getCreditNid();


        // 根据债转编号查询出借异常记录表里是否有数据
        HjhPlanBorrowTmpExample hjhPlanBorrowTmpExample = new HjhPlanBorrowTmpExample();
        HjhPlanBorrowTmpExample.Criteria hjhPlanBorrowTmpCra = hjhPlanBorrowTmpExample.createCriteria();
        hjhPlanBorrowTmpCra.andBorrowNidEqualTo(creditNid);
        List<HjhPlanBorrowTmp> hjhPlanBorrowTmpList = this.hjhPlanBorrowTmpMapper.selectByExample(hjhPlanBorrowTmpExample);
        if (hjhPlanBorrowTmpList != null && hjhPlanBorrowTmpList.size() > 0) {
            logger.info("转让记录出借存在异常,不予结束转让,转让编号:[" + creditNid + "].");
            return;
        }

        // 计划的开放额度减扣
        // 债权的公允价值
        BigDecimal liquidationFairValue = BigDecimal.ZERO;
        if (hjhDebtCredit.getCreditStatus() == 0) {
            // 如果清算出债权一笔也没承接
            liquidationFairValue = hjhDebtCredit.getLiquidationFairValue();
        } else {
            // 清算时的债权价值减去已承接的购买价格
            // 已承接债权的购买价格
            BigDecimal assignPrice = this.selectSumAssignPriceByCreditNid(creditNid);
            // 此时,剩余的债权价值 = 清算时的债权价值 - 已承接部分的购买价格
            liquidationFairValue = hjhDebtCredit.getLiquidationFairValue().subtract(assignPrice);
        }
        // 1.更新债转状态未停止
        hjhDebtCredit.setCreditStatus(3);
        hjhDebtCredit.setEndTime(now);
        hjhDebtCredit.setUpdateTime(new Date());
        boolean isHjhDebtCreditStatusupdateFlag = this.hjhDebtCreditMapper.updateByPrimaryKey(hjhDebtCredit) > 0 ? true : false;
        if (!isHjhDebtCreditStatusupdateFlag) {
            throw new RuntimeException("更新未结束债转状态失败,债转编号:[" + hjhDebtCredit.getCreditNid() + "].");
        }
        // 2.更新计划可投金额
        HjhPlan updateHjhPlan = new HjhPlan();
        updateHjhPlan.setPlanNid(planNid);
        updateHjhPlan.setAvailableInvestAccount(liquidationFairValue);
        boolean updateFlag = this.hjhPlanCustomizeMapper.updateRepayPlanAccount(updateHjhPlan) > 0 ? true : false;
        if (!updateFlag) {
            throw new RuntimeException("更新计划开放额度失败,计划编号:[" + planNid + "].");
        }
        String oldOpenAmount = RedisUtils.get(RedisConstants.HJH_PLAN + planNid);
        logger.info("债权停止后，更新计划开放额度，计划编号:[" + planNid + "],Redis原开放额度:" + oldOpenAmount + "，应减额度:" + liquidationFairValue.toString());
        // 3.减扣redis相应计划可投金额
        RedisUtils.add(RedisConstants.HJH_PLAN + hjhAccede.getPlanNid(), liquidationFairValue.negate().toString());
        // 4.更新原始加入订单的清算状态
        // 更新计划订单的状态
        // 只有是退出中状态,并且清算标志位是已完成,计划订单的清算标志位才能更新
        if (hjhAccede.getOrderStatus() == 5 && hjhAccede.getCreditCompleteFlag() == 1) {
            // 计划订单的清算状态置为2:需要重新清算
            hjhAccede.setCreditCompleteFlag(2);
            // 更新计划订单的清算标志位
            boolean isHjhAccedeUpdateFlag = this.hjhAccedeMapper.updateByPrimaryKeySelective(hjhAccede) > 0 ? true : false;
            if (!isHjhAccedeUpdateFlag) {
                throw new RuntimeException("更新计划订单的清算标志位失败,计划加入订单:[" + accedeOrderId + "].");
            }
        }
    }


    /**
     * 根据计划编号查询计划
     *
     * @param planNid
     * @return
     */
    private HjhPlan selectHjhPlanByPlanNid(String planNid) {
        HjhPlanExample example = new HjhPlanExample();
        HjhPlanExample.Criteria cra = example.createCriteria();
        cra.andPlanNidEqualTo(planNid);
        List<HjhPlan> hjhPlanList = hjhPlanMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(hjhPlanList)) {
            return hjhPlanList.get(0);
        }
        return null;
    }


    /**
     * 根据加入订单号查询计划加入订单
     *
     * @param accedeOrderId
     * @return
     */
    private HjhAccede selectHjhPlanAccedeByOrderId(String accedeOrderId) {
        HjhAccedeExample example = new HjhAccedeExample();
        HjhAccedeExample.Criteria cra = example.createCriteria();
        cra.andAccedeOrderIdEqualTo(accedeOrderId);
        List<HjhAccede> hjhAccedeList = this.hjhAccedeMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(hjhAccedeList)) {
            return hjhAccedeList.get(0);
        }
        return null;
    }

    /**
     * 根据债转编号查询已承接债转的认购价格
     *
     * @param creditNid
     * @return
     */
    private BigDecimal selectSumAssignPriceByCreditNid(String creditNid) {
        // 已承接部分的购买价格总额
        BigDecimal sumAssignPrice = BigDecimal.ZERO;
        HjhDebtCreditTenderExample example = new HjhDebtCreditTenderExample();
        HjhDebtCreditTenderExample.Criteria cra = example.createCriteria();
        cra.andCreditNidEqualTo(creditNid);
        List<HjhDebtCreditTender> hjhDebtCreditTenderList = this.hjhDebtCreditTenderMapper.selectByExample(example);
        if (hjhDebtCreditTenderList != null && hjhDebtCreditTenderList.size() > 0) {
            for (int i = 0; i < hjhDebtCreditTenderList.size(); i++) {
                // 承接记录
                HjhDebtCreditTender hjhDebtCreditTender = hjhDebtCreditTenderList.get(i);
                sumAssignPrice = sumAssignPrice.add(hjhDebtCreditTender.getAssignPrice());
            }
        }
        return sumAssignPrice;
    }
}
