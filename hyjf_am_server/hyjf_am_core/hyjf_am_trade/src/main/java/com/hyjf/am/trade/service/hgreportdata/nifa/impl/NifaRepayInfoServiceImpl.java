/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.hgreportdata.nifa.impl;

import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.hgreportdata.nifa.NifaRepayInfoService;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version NifaRepayInfoServiceImpl, v0.1 2018/9/11 17:20
 */
@Service
public class NifaRepayInfoServiceImpl extends BaseServiceImpl implements NifaRepayInfoService {

    private String thisMessName = "【生成还款记录、合同状态、出借人回款信息】";

    /**
     * 还款完成
     */
    private static final String TYPE_YES = "wait_yes";

    /**
     * 获取18位社会信用代码
     */
    @Value("${hyjf.com.social.credit.code}")
    private String COM_SOCIAL_CREDIT_CODE;

    /**
     * 借款人还款表
     *
     * @param borrowNid
     * @param repayPeriod
     * @return
     */
    @Override
    public boolean insertNifaRepayInfo(String borrowNid, Integer repayPeriod) {

        // 查询借款详情
        Borrow borrow = this.getBorrowByNid(borrowNid);
        if (null == borrow) {
            logger.error(thisMessName + "未查询到相应的借款详情数据！！borrowNid:" + borrowNid + " repayPeriod:" + repayPeriod);
            return false;
        }

        // 查询还款计划总表
        BorrowRepay borrowRepay = this.selectBorrowRepay(borrowNid);
        if (null == borrowRepay) {
            logger.error(thisMessName + "未查询到相应的还款信息！！borrowNid:" + borrowNid + " repayPeriod:" + repayPeriod);
            return false;
        }

        NifaRepayInfo nifaRepayInfo = new NifaRepayInfo();
        // 统一社会信用代码
        nifaRepayInfo.setPlatformNo(COM_SOCIAL_CREDIT_CODE);
        // 项目编号
        nifaRepayInfo.setProjectNo(borrowNid);
        // 还款期数
        nifaRepayInfo.setPaymentNum(repayPeriod);
        // 还款日期
        try {
            if (null == borrowRepay.getRepayActionTime()){
                logger.error(thisMessName + "还款日为空、请确认还款是否成功，borrowNid:" + borrowRepay.getBorrowNid());
                return false;
            }
            nifaRepayInfo.setPaymentDate(GetDate.getDateMyTimeInMillis(borrowRepay.getRepayActionTime()));
        } catch (NumberFormatException e) {
            logger.error(thisMessName + "还款日格式化失败，borrowNid:" + borrowRepay.getBorrowNid());
            logger.error(e.getMessage());
            return false;
        }
        // 还款来源 1、借款人还款，2、机构垫付，3、保证金垫付
        if (borrowRepay.getRepayMoneySource() == 1) {
            nifaRepayInfo.setPaymentSource(0);
        } else {
            nifaRepayInfo.setPaymentSource(1);
        }
        // 还款情况
        nifaRepayInfo.setPaymentSituation(0);
        // 剩余待还本金
        BigDecimal principalRest = BigDecimal.ZERO;
        BigDecimal interestRest = BigDecimal.ZERO;
        // 计算还款剩余本金和利息
        boolean isMonth = "principal".equals(borrow.getBorrowStyle()) || "month".equals(borrow.getBorrowStyle()) || "endmonth".equals(borrow.getBorrowStyle());
        if (isMonth) {
            // 获取还款记录
            List<BorrowRepayPlan> borrowRepayPlanList = this.selectBorrowRepayPlanList(borrowNid, repayPeriod);
            if (null == borrowRepayPlanList) {
                logger.error(thisMessName + "未获取到放款记录详情，borrowNid:" + borrowNid + " repayPeriod:" + repayPeriod);
                return false;
            }
            // 判断是否提前、逾期还款1：提前3：逾期
            if (borrowRepayPlanList.get(0).getAdvanceStatus() == 1) {
                nifaRepayInfo.setPaymentSituation(2);
            } else if (borrowRepayPlanList.get(0).getAdvanceStatus() == 3) {
                nifaRepayInfo.setPaymentSituation(1);
            }
            // 当期还款本金
            nifaRepayInfo.setPaymentPrincipal(borrowRepayPlanList.get(0).getRepayCapitalYes().toString());
            // 当期还款利息
            nifaRepayInfo.setPaymentInterest(borrowRepayPlanList.get(0).getRepayInterestYes().toString());
            // 计算剩余n期待还本息
            BorrowRepayPlanExample borrowRepayPlanExample = new BorrowRepayPlanExample();
            borrowRepayPlanExample.createCriteria().andBorrowNidEqualTo(borrowNid).andRepayPeriodGreaterThan(repayPeriod);
            List<BorrowRepayPlan> borrowRepayPlanList1 = this.borrowRepayPlanMapper.selectByExample(borrowRepayPlanExample);
            if (null != borrowRepayPlanList1 && borrowRepayPlanList1.size() > 0) {
                for (BorrowRepayPlan borrowRepayPlan : borrowRepayPlanList1) {
                    principalRest = principalRest.add(borrowRepayPlan.getRepayCapital());
                    interestRest = interestRest.add(borrowRepayPlan.getRepayInterest());
                }
            }
        } else {
            // 判断是否提前、逾期还款
            if (borrowRepay.getAdvanceStatus() == 1) {
                nifaRepayInfo.setPaymentSituation(2);
            } else if (borrowRepay.getAdvanceStatus() == 3) {
                nifaRepayInfo.setPaymentSituation(1);
            }
            // 当期还款本金insertNifaRepayInfo
            nifaRepayInfo.setPaymentPrincipal(borrowRepay.getRepayCapitalYes().toString());
            // 当期还款利息
            nifaRepayInfo.setPaymentInterest(borrowRepay.getRepayInterestYes().toString());
        }
        // 剩余待还本金不分期的还款后剩余为0
        nifaRepayInfo.setPaymentPrincipalRest(principalRest.toString());
        // 剩余待还利息
        nifaRepayInfo.setPaymentInterestRest(interestRest.toString());

        // 查询是否已存在该标的信息：存在更新、不存在插入
        boolean result;
        NifaRepayInfoExample example = new NifaRepayInfoExample();
        example.createCriteria().andProjectNoEqualTo(nifaRepayInfo.getProjectNo()).andPaymentNumEqualTo(nifaRepayInfo.getPaymentNum());
        List<NifaRepayInfo> nifaRepayInfoList = nifaRepayInfoMapper.selectByExample(example);
        if (null == nifaRepayInfoList || nifaRepayInfoList.size() <= 0) {
            result = this.nifaRepayInfoMapper.insertSelective(nifaRepayInfo) > 0 ? true : false;
        } else {
            result = this.nifaRepayInfoMapper.updateByExampleSelective(nifaRepayInfo, example) > 0 ? true : false;
        }
        return result;
    }

    /**
     * 合同状态变更数据生成
     *
     * @param borrowNid
     * @param repayPeriod
     * @return
     */
    @Override
    public boolean insertNifaContractStatus(String borrowNid, Integer repayPeriod) {

        // 获取标的详情
        Borrow borrow = this.getBorrowByNid(borrowNid);
        if (null == borrow) {
            logger.error(thisMessName + "未获取到用户标的详情，borrowNid:" + borrowNid);
            return false;
        }

        // 获取用户出借信息
        List<BorrowTender> borrowTenderList = this.selectBorrowTenderListByBorrowNid(borrowNid);
        if (null == borrowTenderList || borrowTenderList.size() <= 0) {
            logger.error(thisMessName + "未获取到用户出借信息，borrowNid:" + borrowNid);
            return false;
        }

        // 获取标的还款计划数据
        BorrowRepay borrowRepay = this.selectBorrowRepay(borrowNid);
        if (null == borrowRepay) {
            logger.error(thisMessName + "未获取到标的还款计划，borrowNid:" + borrowNid);
            return false;
        }

        //  判断是否提前或逾期还款 1：提前 3:逾期
        Integer advanceStatus = 0;
        boolean isMonth = "principal".equals(borrow.getBorrowStyle()) || "month".equals(borrow.getBorrowStyle()) || "endmonth".equals(borrow.getBorrowStyle());
        if (isMonth) {
            List<BorrowRepayPlan> borrowRepayPlanList = this.selectBorrowRepayPlanList(borrowNid, repayPeriod);
            if (null != borrowRepayPlanList && borrowRepayPlanList.size() > 0) {
                advanceStatus = borrowRepayPlanList.get(0).getAdvanceStatus();
            }
        } else {
            advanceStatus = borrowRepay.getAdvanceStatus();
        }

        for (BorrowTender borrowTender : borrowTenderList) {
            // 获取用户放款信息
            BorrowRecover borrowRecover = this.selectBorrowRecoverByNid(borrowTender.getNid());
            if (null == borrowRecover) {
                logger.error(thisMessName + "放款数据获取失败，borrowNid:" + borrowRepay.getBorrowNid() + "订单号：" + borrowTender.getNid());
                continue;
            }

            Integer contractStatus = 0;
            // 判定最新合同状态
            // 提前还款天数不为0、还款状态已完成
            if (TYPE_YES.equals(borrowRepay.getRepayType())) {
                // 完全还完
                contractStatus = 4;
                if (advanceStatus == 1) {
                    // 已经完全还款
                    contractStatus = 3;
                }
                // 逾期的天数
                if (advanceStatus == 3) {
                    contractStatus = 5;
                }
                // 非借款人本人还款已结清
                if (borrowRepay.getRepayMoneySource() != 1) {
                    contractStatus = 6;
                }
            } else {
                // 未完全还完
                contractStatus = 1;
            }
            // 完全债转的情况
            BigDecimal capitalYes = borrowRecover.getRecoverCapital().subtract(borrowRecover.getCreditAmount());
            if (borrowRecover.getCreditStatus() == 2 || capitalYes.compareTo(BigDecimal.ZERO) == 0) {
                contractStatus = 6;
            }

            // 互金合同状态表获取合同状态信息
            NifaContractStatus nifaContractStatusOld = this.selectNifaContractStatusByNid(borrowTender.getNid());
            if (null == nifaContractStatusOld) {
                // 插入数据
                if (!insertNifaContractStatusRecorde(thisMessName, COM_SOCIAL_CREDIT_CODE, borrowNid, contractStatus, borrowTender, borrowRepay)) {
                    logger.error(thisMessName + "合同状态变更数据生成失败，borrowNid:" + borrowRepay.getBorrowNid());
                }
            } else {
                // 合同状态有变更重新上报
                if (!contractStatus.equals(nifaContractStatusOld.getContractStatus())) {
                    if (!updateNifaContractStatusRecorde(thisMessName, contractStatus, borrowRepay, nifaContractStatusOld)) {
                        logger.error(thisMessName + "合同状态变更数据更新失败，borrowNid:" + borrowRepay.getBorrowNid());
                    }
                } else {
                    logger.info(thisMessName + "合同状态未变更，borrowNid:" + borrowRepay.getBorrowNid());
                }
            }
        }
        return true;
    }

    /**
     * 出借人回款记录生成
     *
     * @param borrowNid
     * @param repayPeriod
     * @return
     */
    @Override
    public boolean insertNifaReceivedPayments(String borrowNid, Integer repayPeriod) {

        // 获取标的详情
        Borrow borrow = this.getBorrowByNid(borrowNid);
        if (null == borrow) {
            logger.error(thisMessName + "未获取到借款详细信息，borrowNid:" + borrowNid + " repayPeriod:" + repayPeriod);
            return false;
        }

        // 获取标的还款计划数据
        BorrowRepay borrowRepay = this.selectBorrowRepay(borrowNid);
        if (null == borrowRepay) {
            logger.error(thisMessName + "未获取到标的还款计划，borrowNid:" + borrowNid + " repayPeriod:" + repayPeriod);
            return false;
        }

        // 是否月标(true:月标, false:天标)
        boolean isMonth = "principal".equals(borrow.getBorrowStyle()) || "month".equals(borrow.getBorrowStyle()) || "endmonth".equals(borrow.getBorrowStyle());
        if (isMonth) {
            // 获取放款记录详情
            List<BorrowRecoverPlan> borrowRecoverPlanlist = this.selectBorrowRecoverPlanList(borrowNid, repayPeriod);
            if (null == borrowRecoverPlanlist) {
                logger.error(thisMessName + "未获取到放款记录详情，borrowNid:" + borrowNid + " repayPeriod:" + repayPeriod);
                return false;
            }
            for (BorrowRecoverPlan borrowRecoverPlan : borrowRecoverPlanlist) {
                BorrowRecoverExample example = new BorrowRecoverExample();
                example.createCriteria().andBorrowNidEqualTo(borrowNid).andNidEqualTo(borrowRecoverPlan.getNid());
                List<BorrowRecover> borrowRecoverList = this.borrowRecoverMapper.selectByExample(example);
                if (null == borrowRecoverList || borrowRecoverList.size() <= 0) {
                    logger.error(thisMessName + "未获取到用户放款信息，borrowNid:" + borrowNid + " repayPeriod:" + repayPeriod);
                    return false;
                }
                BorrowRecover borrowRecover = borrowRecoverList.get(0);

                BigDecimal creditTatol = borrowRecoverPlan.getCreditAmount().add(borrowRecoverPlan.getCreditInterestAmount());
                // 全部承接债转的数据不再报送(全部承接有些承接状态不为2、分期还款的按每期的承接金额判断是否全部承接)
                if (borrowRecover.getCreditStatus() == 2 || borrowRecoverPlan.getCreditStatus() == 2 || (creditTatol.compareTo(BigDecimal.ZERO) > 0 && borrowRecoverPlan.getRecoverAccount().compareTo(creditTatol) == 0)) {
                    continue;
                }

                NifaReceivedPayments nifaReceivedPayments = new NifaReceivedPayments();
                // 统一社会信用代码
                nifaReceivedPayments.setPlatformNo(COM_SOCIAL_CREDIT_CODE);
                // 项目编号
                nifaReceivedPayments.setProjectNo(borrowNid);
                // 合同编号
                nifaReceivedPayments.setContractNo(borrowRecoverPlan.getNid());
                // 回款期数
                nifaReceivedPayments.setReturnNum(repayPeriod);
                // 回款日期
                if (StringUtils.isBlank(borrowRecoverPlan.getRecoverYestime())){
                    logger.error(thisMessName + "放款日期为空、请确认放款是否成功，borrowNid:" + borrowRepay.getBorrowNid());
                    return false;
                }
                if (Validator.isNumber(borrowRecoverPlan.getRecoverYestime())) {
                    try {
                        nifaReceivedPayments.setReturnDate(GetDate.getDateMyTimeInMillis(Integer.parseInt(borrowRecoverPlan.getRecoverYestime())));
                    } catch (NumberFormatException e) {
                        logger.error(thisMessName + "还款日格式化失败，borrowNid:" + borrowNid + " repayPeriod:" + repayPeriod);
                        logger.error(e.getMessage());
                        return false;
                    }
                } else {
                    logger.error(thisMessName + "还款日格式化失败，borrowNid:" + borrowNid + " repayPeriod:" + repayPeriod);
                    return false;
                }
                // 回款本息计算
                BigDecimal capitalYes = borrowRecoverPlan.getRecoverCapitalYes();
                BigDecimal interestYes = borrowRecoverPlan.getRecoverInterestYes();
                // 判断散标还是进计划
                if (StringUtils.isBlank(borrow.getPlanNid())) {
                    // 散标：判断是否部分承接
                    CreditRepayExample creditRepayExample = new CreditRepayExample();
                    creditRepayExample.createCriteria().andCreditTenderNidEqualTo(borrowRecover.getNid()).andRecoverPeriodEqualTo(repayPeriod);
                    List<CreditRepay> creditRepayList = this.creditRepayMapper.selectByExample(creditRepayExample);
                    if (null != creditRepayList && creditRepayList.size() > 0) {
                        // 部分承接
                        // huiyingdai_credit_repay的AssignRepayInterest的集合是承接人收到的利息
                        for (CreditRepay creditRepay : creditRepayList) {
                            // 总回款本金减去每个承接人的回款本金
                            capitalYes = capitalYes.subtract(creditRepay.getAssignRepayCapital());
                            // 总回款利息减去每个承接人的回款利息
                            interestYes = interestYes.subtract(creditRepay.getAssignRepayInterest());
                        }
                    }
                } else {
                    // 计划：判断是否部分承接
                    HjhDebtCreditRepayExample hjhDebtCreditRepayExample = new HjhDebtCreditRepayExample();
                    // del_flag ：是否删除 0未删除 1已删除
                    hjhDebtCreditRepayExample.createCriteria().andInvestOrderIdEqualTo(borrowRecover.getNid()).andRepayPeriodEqualTo(repayPeriod).andDelFlagEqualTo(0);
                    List<HjhDebtCreditRepay> hjhDebtCreditRepayList = this.hjhDebtCreditRepayMapper.selectByExample(hjhDebtCreditRepayExample);
                    if (null != hjhDebtCreditRepayList && hjhDebtCreditRepayList.size() > 0) {
                        // 部分承接
                        // hyjf_hjh_debt_credit_repay的repay_advance_interest+repay_late_interest+repay_delay_interest的集合是承接人收到的利息
                        for (HjhDebtCreditRepay hjhDebtCreditRepay : hjhDebtCreditRepayList) {
                            // 总回款本金减去每个承接人的回款本金
                            capitalYes = capitalYes.subtract(hjhDebtCreditRepay.getRepayCapital());
                            // 总回款利息减去每个承接人的回款利息
                            interestYes = interestYes.subtract(hjhDebtCreditRepay.getRepayInterest()).subtract(hjhDebtCreditRepay.getRepayLateInterest()).subtract(hjhDebtCreditRepay.getRepayAdvanceInterest().subtract(hjhDebtCreditRepay.getRepayDelayInterest()));
                        }
                    }
                }
                // 回款本金
                nifaReceivedPayments.setReturnPrincipal(capitalYes.toString());
                // 回款利息
                nifaReceivedPayments.setReturnInterest(interestYes.toString());
                // 回款来源
                // 还款来源 1、借款人还款，2、机构垫付，3、保证金垫付
                if (borrowRepay.getRepayMoneySource() == 1) {
                    nifaReceivedPayments.setReturnSource(0);
                } else {
                    nifaReceivedPayments.setReturnSource(1);
                }
                // 回款情况
                nifaReceivedPayments.setReturnSituation(0);
                // 判断是否提前或逾期还款 1：提前 3:逾期
                // 提前还款
                if (borrowRecoverPlan.getAdvanceStatus() == 1) {
                    nifaReceivedPayments.setReturnSituation(2);
                } else if (borrowRecoverPlan.getAdvanceStatus() == 3) {
                    nifaReceivedPayments.setReturnSituation(1);
                }
                BigDecimal returnPrincipalRest = BigDecimal.ZERO;
                BigDecimal returnInterestRest = BigDecimal.ZERO;
                // 带还本息计算 获取剩余n期的应还本金和利息最后一期设0
                BorrowRecoverPlanExample borrowRecoverPlanExample = new BorrowRecoverPlanExample();
                borrowRecoverPlanExample.createCriteria().andBorrowNidEqualTo(borrowNid).andNidEqualTo(borrowRecover.getNid()).andRecoverPeriodGreaterThan(repayPeriod);
                List<BorrowRecoverPlan> borrowRecoverPlanLists = this.borrowRecoverPlanMapper.selectByExample(borrowRecoverPlanExample);
                // 查不到数据就是最后一期
                if (null != borrowRecoverPlanLists && borrowRecoverPlanLists.size() > 0) {
                    for (BorrowRecoverPlan borrowRecoverPlan1 : borrowRecoverPlanLists) {
                        returnPrincipalRest = returnPrincipalRest.add(borrowRecoverPlan1.getRecoverCapital());
                        returnInterestRest = returnInterestRest.add(borrowRecoverPlan1.getRecoverInterest());
                    }
                    // 判断散标还是进计划
                    if (StringUtils.isBlank(borrow.getPlanNid())) {
                        // 散标：判断是否部分承接
                        CreditRepayExample creditRepayExample = new CreditRepayExample();
                        creditRepayExample.createCriteria().andCreditTenderNidEqualTo(borrowRecover.getNid()).andRecoverPeriodGreaterThan(repayPeriod);
                        List<CreditRepay> creditRepayList = this.creditRepayMapper.selectByExample(creditRepayExample);
                        if (null != creditRepayList && creditRepayList.size() > 0) {
                            // 部分承接
                            // huiyingdai_credit_repay的AssignRepayInterest的集合是承接人收到的利息
                            for (CreditRepay creditRepay : creditRepayList) {
                                // 总回款本金减去每个承接人的回款本金
                                returnPrincipalRest = returnPrincipalRest.subtract(creditRepay.getAssignCapital());
                                // 总回款利息减去每个承接人的回款利息
                                returnInterestRest = returnInterestRest.subtract(creditRepay.getAssignInterest());
                            }
                        }
                    } else {
                        // 计划：判断是否部分承接
                        HjhDebtCreditRepayExample hjhDebtCreditRepayExample = new HjhDebtCreditRepayExample();
                        // del_flag ：是否删除 0未删除 1已删除
                        hjhDebtCreditRepayExample.createCriteria().andInvestOrderIdEqualTo(borrowRecover.getNid()).andRepayPeriodGreaterThan(repayPeriod).andDelFlagEqualTo(0);
                        List<HjhDebtCreditRepay> hjhDebtCreditRepayList = this.hjhDebtCreditRepayMapper.selectByExample(hjhDebtCreditRepayExample);
                        if (null != hjhDebtCreditRepayList && hjhDebtCreditRepayList.size() > 0) {
                            // 部分承接
                            // hyjf_hjh_debt_credit_repay的repay_advance_interest+repay_late_interest+repay_delay_interest的集合是承接人收到的利息
                            for (HjhDebtCreditRepay hjhDebtCreditRepay : hjhDebtCreditRepayList) {
                                // 总回款本金减去每个承接人的回款本金
                                returnPrincipalRest = returnPrincipalRest.subtract(hjhDebtCreditRepay.getRepayCapital());
                                // 总回款利息减去每个承接人的回款利息
                                returnInterestRest = returnInterestRest.subtract(hjhDebtCreditRepay.getRepayInterest());
                            }
                        }
                    }
                }
                // 剩余待回本金(总共未回的)
                nifaReceivedPayments.setReturnPrincipalRest(returnPrincipalRest.toString());
                // 剩余待回利息
                nifaReceivedPayments.setReturnInterestRest(returnInterestRest.toString());
                // 防重验证
                boolean result;
                NifaReceivedPaymentsExample nifaReceivedPaymentsExample = new NifaReceivedPaymentsExample();
                nifaReceivedPaymentsExample.createCriteria().andProjectNoEqualTo(nifaReceivedPayments.getProjectNo()).andContractNoEqualTo(nifaReceivedPayments.getContractNo()).andReturnNumEqualTo(nifaReceivedPayments.getReturnNum());
                List<NifaReceivedPayments> nifaReceivedPaymentsList = this.nifaReceivedPaymentsMapper.selectByExample(nifaReceivedPaymentsExample);
                if (null == nifaReceivedPaymentsList || nifaReceivedPaymentsList.size() <= 0) {
                    result = this.nifaReceivedPaymentsMapper.insert(nifaReceivedPayments) > 0 ? true : false;
                } else {
                    result = this.nifaReceivedPaymentsMapper.updateByExampleSelective(nifaReceivedPayments, nifaReceivedPaymentsExample) > 0 ? true : false;
                }
                if (!result) {
                    logger.error(thisMessName + "出借人回款记录生成失败，borrowNid:" + borrowRepay.getBorrowNid() + " repayPeriod:" + repayPeriod);
                }
            }
        } else {
            // 获取放款记录
            List<BorrowRecover> borrowRecoverList = this.selectBorrowRecoverList(borrowNid, repayPeriod);
            if (null == borrowRecoverList || borrowRecoverList.size() <= 0) {
                logger.error(thisMessName + "未获取到用户放款信息，borrowNid:" + borrowNid + " repayPeriod:" + repayPeriod);
                return false;
            }
            for (BorrowRecover borrowRecover : borrowRecoverList) {
                // 全部承接债转的数据不再报送
                if (borrowRecover.getCreditStatus() == 2 || borrowRecover.getRecoverCapital().compareTo(borrowRecover.getCreditAmount()) == 0) {
                    continue;
                }
                NifaReceivedPayments nifaReceivedPayments = new NifaReceivedPayments();
                // 统一社会信用代码
                nifaReceivedPayments.setPlatformNo(COM_SOCIAL_CREDIT_CODE);
                // 项目编号
                nifaReceivedPayments.setProjectNo(borrowNid);
                // 合同编号
                nifaReceivedPayments.setContractNo(borrowRecover.getNid());
                // 回款期数
                nifaReceivedPayments.setReturnNum(repayPeriod);
                // 回款日期
                try {
                    if (null == borrowRepay.getRepayActionTime()){
                        logger.error(thisMessName + "还款日为空、请确认还款是否成功，borrowNid:" + borrowNid + " repayPeriod:" + repayPeriod);
                        return false;
                    }
                    nifaReceivedPayments.setReturnDate(GetDate.getDateMyTimeInMillis(borrowRepay.getRepayActionTime()));
                } catch (NumberFormatException e) {
                    logger.error(thisMessName + "还款日格式化失败，borrowNid:" + borrowNid + " repayPeriod:" + repayPeriod);
                    logger.error(e.getMessage());
                    return false;
                }
                // 回款本金计算
                BigDecimal capitalYes = borrowRecover.getRecoverCapitalYes();
                BigDecimal interestYes = borrowRecover.getRecoverInterestYes();
                // 判断散标还是进计划
                if (StringUtils.isBlank(borrow.getPlanNid())) {
                    // 散标：判断是否部分承接
                    CreditRepayExample creditRepayExample = new CreditRepayExample();
                    creditRepayExample.createCriteria().andCreditTenderNidEqualTo(borrowRecover.getNid()).andRecoverPeriodEqualTo(repayPeriod);
                    List<CreditRepay> creditRepayList = this.creditRepayMapper.selectByExample(creditRepayExample);
                    if (null != creditRepayList && creditRepayList.size() > 0) {
                        // 部分承接
                        // huiyingdai_credit_repay的AssignRepayInterest的集合是承接人收到的利息
                        for (CreditRepay creditRepay : creditRepayList) {
                            // 总回款本金减去每个承接人的回款本金
                            capitalYes = capitalYes.subtract(creditRepay.getAssignRepayCapital());
                            // 总回款利息减去每个承接人的回款利息
                            interestYes = interestYes.subtract(creditRepay.getAssignRepayInterest());
                        }
                    }
                } else {
                    // 计划：判断是否部分承接
                    HjhDebtCreditRepayExample hjhDebtCreditRepayExample = new HjhDebtCreditRepayExample();
                    // del_flag ：是否删除 0未删除 1已删除
                    hjhDebtCreditRepayExample.createCriteria().andInvestOrderIdEqualTo(borrowRecover.getNid()).andRepayPeriodEqualTo(repayPeriod).andDelFlagEqualTo(0);
                    List<HjhDebtCreditRepay> hjhDebtCreditRepayList = this.hjhDebtCreditRepayMapper.selectByExample(hjhDebtCreditRepayExample);
                    if (null != hjhDebtCreditRepayList && hjhDebtCreditRepayList.size() > 0) {
                        // 部分承接
                        // hyjf_hjh_debt_credit_repay的repay_advance_interest+repay_late_interest+repay_delay_interest的集合是承接人收到的利息
                        for (HjhDebtCreditRepay hjhDebtCreditRepay : hjhDebtCreditRepayList) {
                            // 总回款本金减去每个承接人的回款本金
                            capitalYes = capitalYes.subtract(hjhDebtCreditRepay.getRepayCapital());
                            // 总回款利息减去每个承接人的回款利息
                            interestYes = interestYes.subtract(hjhDebtCreditRepay.getRepayInterest()).subtract(hjhDebtCreditRepay.getRepayLateInterest()).subtract(hjhDebtCreditRepay.getRepayAdvanceInterest().subtract(hjhDebtCreditRepay.getRepayDelayInterest()));
                        }
                    }
                }
                // 回款本金
                nifaReceivedPayments.setReturnPrincipal(capitalYes.toString());
                // 回款利息
                nifaReceivedPayments.setReturnInterest(interestYes.toString());
                // 回款来源
                // 还款来源 1、借款人还款，2、机构垫付，3、保证金垫付
                if (borrowRepay.getRepayMoneySource() == 1) {
                    nifaReceivedPayments.setReturnSource(0);
                } else {
                    nifaReceivedPayments.setReturnSource(1);
                }
                // 回款情况
                nifaReceivedPayments.setReturnSituation(0);
                // 判断是否提前或逾期还款 1：提前 3:逾期
                // 提前还款
                if (borrowRepay.getAdvanceStatus() == 1) {
                    nifaReceivedPayments.setReturnSituation(2);
                } else if (borrowRepay.getAdvanceStatus() == 3) {
                    nifaReceivedPayments.setReturnSituation(1);
                }
                // 带还本息计算 到期还款的一次性还清没有待还
                BigDecimal returnPrincipalRest = BigDecimal.ZERO;
                BigDecimal returnInterestRest = BigDecimal.ZERO;
                // 剩余待回本金
                nifaReceivedPayments.setReturnPrincipalRest(returnPrincipalRest.toString());
                // 剩余待回利息
                nifaReceivedPayments.setReturnInterestRest(returnInterestRest.toString());
                // 防重验证
                boolean result;
                NifaReceivedPaymentsExample nifaReceivedPaymentsExample = new NifaReceivedPaymentsExample();
                nifaReceivedPaymentsExample.createCriteria().andProjectNoEqualTo(nifaReceivedPayments.getProjectNo()).andContractNoEqualTo(nifaReceivedPayments.getContractNo()).andReturnNumEqualTo(nifaReceivedPayments.getReturnNum());
                List<NifaReceivedPayments> nifaReceivedPaymentsList = this.nifaReceivedPaymentsMapper.selectByExample(nifaReceivedPaymentsExample);
                if (null == nifaReceivedPaymentsList || nifaReceivedPaymentsList.size() <= 0) {
                    result = this.nifaReceivedPaymentsMapper.insert(nifaReceivedPayments) > 0 ? true : false;
                } else {
                    result = this.nifaReceivedPaymentsMapper.updateByExampleSelective(nifaReceivedPayments, nifaReceivedPaymentsExample) > 0 ? true : false;
                }
                if (!result) {
                    logger.error(thisMessName + "出借人回款记录生成失败，borrowNid:" + borrowRepay.getBorrowNid() + " repayPeriod:" + repayPeriod);
                }
            }
        }
        return true;
    }


    /**
     * 根据借款编号和还款期数获取还款信息详情
     *
     * @param borrowNid
     * @param repayPeriod
     * @return
     */
    private List<BorrowRepayPlan> selectBorrowRepayPlanList(String borrowNid, Integer repayPeriod) {
        BorrowRepayPlanExample example = new BorrowRepayPlanExample();
        example.createCriteria().andBorrowNidEqualTo(borrowNid).andRepayPeriodEqualTo(repayPeriod);
        List<BorrowRepayPlan> borrowRepayPlanList = this.borrowRepayPlanMapper.selectByExample(example);
        if (null != borrowRepayPlanList && borrowRepayPlanList.size() > 0) {
            return borrowRepayPlanList;
        }
        return null;
    }

    /**
     * @param thisMessName
     * @param comSocialCreditCode
     * @param borrowNid
     * @param contractStatus
     * @param borrowTender
     * @param borrowRepay
     * @return
     */
    private boolean insertNifaContractStatusRecorde(String thisMessName, String comSocialCreditCode, String borrowNid, Integer contractStatus, BorrowTender borrowTender, BorrowRepay borrowRepay) {

        NifaContractStatus nifaContractStatus = new NifaContractStatus();
        // 统一社会信用代码
        nifaContractStatus.setPlatformNo(comSocialCreditCode);
        // 项目编号
        nifaContractStatus.setProjectNo(borrowNid);
        // 合同编号
        nifaContractStatus.setContractNo(borrowTender.getNid());
        // 合同状态
        nifaContractStatus.setContractStatus(contractStatus);
        // 创建时间为当前时间
        nifaContractStatus.setCreateTime(new Date());
        // 更新时间为当前时间
        nifaContractStatus.setUpdateTime(new Date());

        // 更新日期 YYYY-MM-DD HH:MM:SS
        if (null != borrowRepay.getRepayActionTime()) {
            try {
                nifaContractStatus.setChangeDate(GetDate.getDateTimeMyTime(borrowRepay.getRepayActionTime()));
            } catch (NumberFormatException e) {
                logger.error(thisMessName + "还款日格式化失败，borrowNid:" + borrowRepay.getBorrowNid());
                logger.error(e.getMessage());
                return false;
            }
        } else {
            // 没有还款时间的情况进到此处属于逾期还款的场景
            nifaContractStatus.setChangeDate(GetDate.getDateTimeMyTime(GetDate.getNowTime10()));
        }
        return this.nifaContractStatusMapper.insert(nifaContractStatus) > 0 ? true : false;
    }

    /**
     * 互金合同状态更新
     *
     * @param contractStatus
     * @param borrowRepay
     * @param nifaContractStatusOld
     * @return
     */
    public boolean updateNifaContractStatusRecorde(String thisMessName, Integer contractStatus, BorrowRepay borrowRepay, NifaContractStatus nifaContractStatusOld) {
        nifaContractStatusOld.setContractStatus(contractStatus);
        nifaContractStatusOld.setUpdateTime(new Date());
        // 更新日期 YYYY-MM-DD HH:MM:SS
        if (null != borrowRepay.getRepayActionTime()) {
            try {
                nifaContractStatusOld.setChangeDate(GetDate.getDateTimeMyTime(borrowRepay.getRepayActionTime()));
            } catch (NumberFormatException e) {
                logger.error(thisMessName + "还款日格式化失败，borrowNid:" + borrowRepay.getBorrowNid());
                logger.error(e.getMessage());
                return false;
            }
        } else {
            // 没有还款时间的情况进到此处属于逾期还款的场景
            nifaContractStatusOld.setChangeDate(GetDate.getDateTimeMyTime(GetDate.getNowTime10()));
        }
        return this.nifaContractStatusMapper.updateByPrimaryKeySelective(nifaContractStatusOld) > 0 ? true : false;
    }
}
