package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.CommisionComputeService;
import com.hyjf.am.vo.trade.HjhLockVo;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.http.HtmlUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Auther:yangchangwei
 * @Date:2018/8/8
 * @Description: 提成计算服务
 */
@Service
public class CommisionComputeServiceImpl extends BaseServiceImpl implements CommisionComputeService{

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;

    /**
     *
     * 检索代计算提成的加入记录
     * @author hsy
     * @return
     */
    @Override
    public List<HjhAccede> selectHasCommisionAccedeList() {
        Integer[] orderStatus = {3,5,7};

        HjhAccedeExample example = new HjhAccedeExample();
        example.createCriteria().andOrderStatusIn(Arrays.asList(orderStatus)).andCommissionStatusEqualTo(0);
        List<HjhAccede> accedeList = hjhAccedeMapper.selectByExample(example);

        return accedeList;
    }

    /**
     *
     * 计算提成
     * @author hsy
     * @param record
     * @return
     */
    @Override
    public void commisionCompute(HjhAccede record, HjhLockVo hjhLockVo) {
        Integer commisionUserId = null;

        PushMoney pushMoneyOnline = getCommisionConfig(2, "线上员工");
        PushMoney pushMoney51 = getCommisionConfig(2, "51老用户");

        // 计算提成用户id
        UserInfoVO userInfoCommision = hjhLockVo.getCommissioUserInfoVO();
        commisionUserId = userInfoCommision.getUserId();
        if(commisionUserId == null){
            statusUpdate(record, 1);

            return;
        }

        if(userInfoCommision.getAttribute() != 3 && userInfoCommision.getIs51() ==1 && pushMoney51.getRewardSend() == 0){
            statusUpdate(record, 1);

            return;
        }
        BankOpenAccountVO bankOpenAccountInfo = hjhLockVo.getBankOpenAccountVO();
        // 判断提成人是否开户  (提成人未开户的不计算提成)
        if (bankOpenAccountInfo == null || Validator.isNull(bankOpenAccountInfo.getAccount())) {
            logger.info("计算提成失败，因为用户没有开户");
            statusUpdate(record, 2);
            return;
        }

        HjhPlan plan = getPlan(record.getPlanNid());
        if(plan == null){
            logger.info("计算提成失败，根据计划编号："  + record.getPlanNid() + " 没有查到计划");
            statusUpdate(record, 2);
            return;
        }

        // 提成利率(天标)
        BigDecimal rateDay = BigDecimal.ZERO;
        // 提成利率(月标)
        BigDecimal rateMonth = BigDecimal.ZERO;
        // 提成金额
        BigDecimal commission = BigDecimal.ZERO;
        // 投资金额
        BigDecimal accountTender = record.getAccedeAccount();

        if(userInfoCommision.getAttribute() == 3){
            rateDay = new BigDecimal(pushMoneyOnline.getDayTender());
            rateMonth = new BigDecimal(pushMoneyOnline.getMonthTender());
        }else{
            rateDay = new BigDecimal(pushMoney51.getDayTender());
            rateMonth = new BigDecimal(pushMoney51.getMonthTender());
        }

        commission = compute(record, userInfoCommision, plan, rateDay, rateMonth, accountTender);

        if(commission.compareTo(BigDecimal.ZERO) <= 0){
            statusUpdate(record, 1);

            return;
        }

        TenderCommission tenderCommission = new TenderCommission();
        tenderCommission.setCommission(commission);
        // 投资人
        tenderCommission.setTenderUserId(record.getUserId());
        // 投资金额
        tenderCommission.setAccountTender(record.getAccedeAccount());
        // 项目编号
        tenderCommission.setBorrowNid(plan.getPlanNid());
        // 投资ID
        tenderCommission.setTenderId(record.getId());
        // 投资时间
        tenderCommission.setTenderTime(GetDate.getTime10(record.getCreateTime()));
        // 状态 0：未发放；1：已发放
        tenderCommission.setStatus(0);
        // 备注
        tenderCommission.setRemark("");
        // 计算时间
        tenderCommission.setComputeTime(GetDate.getNowTime10());
        // 订单号
        tenderCommission.setOrdid(record.getAccedeOrderId());
        // 类型 2：汇计划
        tenderCommission.setTenderType(2);
        // 提成用户id
        tenderCommission.setUserId(commisionUserId);
        // 电子账号
        tenderCommission.setAccountId(bankOpenAccountInfo.getAccount());

        // 根据用户ID查询部门信息
        List<UserInfoCustomizeVO> userInfoCustomizes = hjhLockVo.getUserInfoCustomizeVOS();
        UserInfoCustomizeVO userInfoCustomize;
        if (userInfoCustomizes != null && userInfoCustomizes.size() > 0) {
            userInfoCustomize = userInfoCustomizes.get(0);

            tenderCommission.setRegionId(userInfoCustomize.getRegionId());
            tenderCommission.setRegionName(userInfoCustomize.getRegionName());
            tenderCommission.setBranchId(userInfoCustomize.getBranchId());
            tenderCommission.setBranchName(userInfoCustomize.getBranchName());
            tenderCommission.setDepartmentId(userInfoCustomize.getDepartmentId());
            tenderCommission.setDepartmentName(HtmlUtil.unescape(userInfoCustomize.getDepartmentName()));
        }

        // 开启事物
        TransactionStatus txStatus = null;
        try {
            // 开启事务
            txStatus = this.transactionManager.getTransaction(transactionDefinition);
            // 插入提成表
            TenderCommissionExample tenderCommissionExample = new TenderCommissionExample();
            tenderCommissionExample.createCriteria().andTenderTypeEqualTo(2)
                    .andTenderIdEqualTo(record.getId());
            if (this.tenderCommissionMapper.countByExample(tenderCommissionExample) == 0) {
                // 执行插入
                int result = this.tenderCommissionMapper.insertSelective(tenderCommission);
                if(result > 0){
                    statusUpdate(record, 1);
                }
            }

            transactionManager.commit(txStatus);
        } catch (Exception e) {
            logger.error("发生异常，提成计算失败", e);
            transactionManager.rollback(txStatus);
            statusUpdate(record, 2);
        }
    }

    @Override
    public int statusUpdate(HjhAccede record, Integer status) {
        // 更新计算状态为已计算
        record.setCommissionStatus(status);
        record.setCommissionCountTime(GetDate.getNowTime10());
        record.setUpdateTime(new Date());
        return hjhAccedeMapper.updateByPrimaryKeySelective(record);
    }


    /**
     *
     * 计算提成金额
     * @author hsy
     * @param record
     * @param userInfoCommision
     * @param plan
     * @param rateDay
     * @param rateMonth
     * @param accountTender
     * @return
     */
    private BigDecimal compute(HjhAccede record, UserInfoVO userInfoCommision, HjhPlan plan, BigDecimal rateDay,
                               BigDecimal rateMonth, BigDecimal accountTender) {
        BigDecimal commission;
        // 天标
        if (CustomConstants.BORROW_STYLE_ENDDAY.equals(plan.getBorrowStyle())) {
            // 线上员工
            if (userInfoCommision.getAttribute() == 3) {
                // 每笔提成= 投资金额*提成比例（天-线上员工）*融资天数
                commission = accountTender.multiply(rateDay).multiply(new BigDecimal(record.getLockPeriod()));
            }
            //51老用户（非员工）
            else {
                if (record.getLockPeriod() >= 50) {
                    // 融资期限≥50天时，每笔提成=投资金额*提成比例（月-51老用户）
                    commission = accountTender.multiply(rateMonth);
                } else {
                    // 融资期限＜50天时，每笔提成=投资金额*提成比例（天-51老用户）*天数
                    commission = accountTender.multiply(rateDay).multiply(new BigDecimal(record.getLockPeriod()));
                }
            }
        }
        // 月标
        else {
            // 线上员工
            if (userInfoCommision.getAttribute() == 3) {
                // 每笔提成=投资金额*提成比例（月-线上员工）*融资月数
                commission = accountTender.multiply(rateMonth).multiply(new BigDecimal(record.getLockPeriod()));
            }
            //51老用户（非员工）
            else {
                // 每笔提成= 投资金额*提成比例（月-51老用户）
                commission = accountTender.multiply(rateMonth);
            }
        }
        return commission;
    }

    /**
     *
     * 查询提成配置信息
     * @author hsy
     * @param projectType
     * @param userType
     * @return
     */
    @Override
    public PushMoney getCommisionConfig(Integer projectType, String userType){
        PushMoneyExample example = new PushMoneyExample();
        example.createCriteria().andProjectTypeEqualTo(projectType).andTypeEqualTo(userType);
        List<PushMoney> list = this.pushMoneyMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     *
     * 获取计划
     * @author hsy
     * @param planNid
     * @return
     */
    private HjhPlan getPlan(String planNid){
        if(StringUtils.isEmpty(planNid)){
            return null;
        }

        HjhPlanExample example = new HjhPlanExample();
        example.createCriteria().andPlanNidEqualTo(planNid);
        List<HjhPlan> planList = hjhPlanMapper.selectByExample(example);
        if(planList == null || planList.isEmpty()){
            return null;
        }

        return planList.get(0);
    }
}
