package com.hyjf.cs.trade.service.repay.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.resquest.trade.*;
import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;
import com.hyjf.am.vo.trade.CreditRepayVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditRepayVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.repay.BankRepayFreezeLogVO;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.*;
import com.hyjf.common.util.calculate.AccountManagementFeeUtils;
import com.hyjf.common.util.calculate.UnnormalRepayUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.repay.*;
import com.hyjf.cs.trade.client.*;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.repay.RepayManageService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 还款管理列表
 *
 * @author hesy
 * @version RepayManageServiceImpl, v0.1 2018/6/23 17:16
 */
@Service
public class RepayManageServiceImpl extends BaseTradeServiceImpl implements RepayManageService {
    @Autowired
    AmBorrowClient amBorrowClient;
    @Autowired
    BorrowApicronClient borrowApicronClient;
    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    AmBorrowRepayClient borrowRepayClient;
    @Autowired
    CreditClient creditClient;
    @Autowired
    AmBorrowRepayPlanClient borrowRepayPlanClient;
    @Autowired
    HjhDebtCreditClient hjhDebtCreditClient;

    /**
     * 普通用户管理费总待还
     * @param userId
     * @return
     */
    @Override
    public BigDecimal getUserRepayFeeWaitTotal(Integer userId) {
        return amTradeClient.getUserRepayFeeWaitTotal(userId);
    }

    /**
     * 担保机构管理费总待还
     * @param userId
     * @return
     */
    @Override
    public BigDecimal getOrgRepayFeeWaitTotal(Integer userId) {
        return amTradeClient.getUserRepayFeeWaitTotal(userId);
    }

    /**
     * 担保机构待还
     * @param userId
     * @return
     */
    @Override
    public BigDecimal getOrgRepayWaitTotal(Integer userId) {
        return amTradeClient.getOrgRepayWaitTotal(userId);
    }

    /**
     * 请求参数校验
     *
     * @param requestBean
     */
    @Override
    public void checkForRepayList(RepayListRequest requestBean) {
        if (requestBean.getCurrPage() <= 0) {
            requestBean.setCurrPage(1);
        }

        if (requestBean.getPageSize() <= 0) {
            requestBean.setPageSize(10);
        }
    }

    /**
     * 用户已还款/待还款列表
     *
     * @param requestBean
     * @return
     */
    @Override
    public List<RepayListCustomizeVO> selectRepayList(RepayListRequest requestBean) {
        List<RepayListCustomizeVO> resultList =  amTradeClient.repayList(requestBean);
        if(resultList == null){
            return new ArrayList<RepayListCustomizeVO>();
        }
        return resultList;
    }

    /**
     * 垫付机构待还款列表
     *
     * @param requestBean
     * @return
     */
    @Override
    public List<RepayListCustomizeVO> selectOrgRepayList(RepayListRequest requestBean) {
        List<RepayListCustomizeVO> resultList = amTradeClient.orgRepayList(requestBean);
        if(resultList == null){
            return new ArrayList<RepayListCustomizeVO>();
        }
        return resultList;
    }

    /**
     * 垫付机构已还款列表
     *
     * @param requestBean
     * @return
     */
    @Override
    public List<RepayListCustomizeVO> selectOrgRepayedList(RepayListRequest requestBean) {
        List<RepayListCustomizeVO> resultList = amTradeClient.orgRepayedList(requestBean);
        if(resultList == null){
            return new ArrayList<RepayListCustomizeVO>();
        }
        return resultList;
    }

    /**
     * 用户待还款/已还款总记录数
     *
     * @param requestBean
     * @return
     */
    @Override
    public Integer selectRepayCount(RepayListRequest requestBean) {
        return amTradeClient.repayCount(requestBean);
    }

    /**
     * 垫付机构待还款总记录数
     *
     * @param requestBean
     * @return
     */
    @Override
    public Integer selectOrgRepayCount(RepayListRequest requestBean) {
        return amTradeClient.orgRepayCount(requestBean);
    }

    /**
     * 垫付机构已还款总记录数
     *
     * @param requestBean
     * @return
     */
    @Override
    public Integer selectOrgRepayedCount(RepayListRequest requestBean) {
        return amTradeClient.orgRepayedCount(requestBean);
    }

    @Override
    public boolean checkPassword(Integer userId, String password) {
        UserVO user = this.getUserByUserId(userId);
        String codeSalt = user.getSalt();
        String passwordDb = user.getPassword();
        // 验证用的password
        password = MD5Utils.MD5(MD5Utils.MD5(password) + codeSalt);
        // 密码正确时
        if (password.equals(passwordDb)) {
            return true;
        } else {
            return false;
        }
    }

    private BorrowApicronVO getApiCron(List<BorrowApicronVO> borrowApicrons, Integer periodNow){
        if(borrowApicrons == null || borrowApicrons.isEmpty()){
            return null;
        }

        for(BorrowApicronVO apicron : borrowApicrons){
            if(apicron.getApiType() == 1 && apicron.getPeriodNow().equals(periodNow)){
                return  apicron;
            }
        }
        return null;
    }

    /**
     * 还款详情数据获取
     * @auther: hesy
     * @date: 2018/7/9
     */
    @Override
    public ProjectBean searchRepayProjectDetail(ProjectBean form) throws NumberFormatException, ParseException {

        String userId = StringUtils.isNotEmpty(form.getUserId()) ? form.getUserId() : null;
        String borrowNid = StringUtils.isNotEmpty(form.getBorrowNid()) ? form.getBorrowNid() : null;

        BorrowVO borrow = amBorrowClient.getBorrowByNid(borrowNid);
        if (borrow == null) {
            return null;
        }
        // userId 改成借款人的userid！！！
        userId = borrow.getUserId().toString();
        form.settType("0");// 设置为非汇添金专属项目
        // 设置相应的项目名称
        // 之前取borrow表的Name，现在取borrow表的projectName
        // form.setBorrowName(borrow.getName());
        form.setBorrowName(borrow.getProjectName());

        // 获取相应的项目还款方式
        String borrowStyle = StringUtils.isNotEmpty(borrow.getBorrowStyle()) ? borrow.getBorrowStyle() : null;
        form.setBorrowStyle(borrowStyle);
        // 还款总期数
        int periodTotal = borrow.getBorrowPeriod();
        if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle) || CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
            RepayBean repay = this.calculateRepay(borrow);
            form.setRepayPeriod("0");
            form.setManageFee(repay.getRepayFee().toString());
            form.setRepayTotal(repay.getRepayAccountAll().toString()); // 计算的是还款总额
            form.setRepayAccount(repay.getRepayAccount().add(repay.getChargeInterest()).add(repay.getDelayInterest()).add(repay.getLateInterest()).toString());
            form.setRepayCapital(repay.getRepayCapital().toString());
            form.setRepayInterest(repay.getRepayInterest().add(repay.getChargeInterest()).add(repay.getDelayInterest()).add(repay.getLateInterest()).toString());
            form.setShouldInterest(repay.getRepayInterest().toString());
            // 判断当前期是否在还款
            BorrowApicronVO apicron = this.getApiCron(borrowApicronClient.selectBorrowApicronListByBorrowNid(borrowNid), 1);
            if (apicron != null) {
                if (apicron.getStatus() != 6) {
                    // 用户还款当前期
                    form.setRepayStatus("1");
                } else {// 用户未还款当前期
                    form.setRepayStatus("0");
                }
            } else {// 用户未还款当前期
                form.setRepayStatus("0");
            }
            form.setAdvanceStatus(String.valueOf(repay.getAdvanceStatus()));
            form.setChargeDays(repay.getChargeDays().toString());
            form.setChargeInterest(repay.getChargeInterest().toString());
            form.setDelayDays(repay.getDelayDays().toString());
            form.setDelayInterest(repay.getDelayInterest().toString());
            form.setLateDays(repay.getLateDays().toString());
            form.setLateInterest(repay.getLateInterest().toString());
            List<ProjectRepayBean> userRepayList = new ArrayList<ProjectRepayBean>();
            ProjectRepayBean userRepayBean = new ProjectRepayBean();
            // 此处是本息和
            userRepayBean.setRepayTotal(repay.getRepayAccountAll().toString());
            userRepayBean.setRepayAccount(repay.getRepayAccount().add(repay.getChargeInterest()).add(repay.getDelayInterest()).add(repay.getLateInterest()).toString());
            userRepayBean.setRepayCapital(repay.getRepayCapital().toString());
            userRepayBean.setRepayInterest(repay.getRepayInterest().toString());
            userRepayBean.setChargeDays(repay.getChargeDays().toString());
            userRepayBean.setChargeInterest(repay.getChargeInterest().multiply(new BigDecimal("-1")).toString());
            userRepayBean.setDelayDays(repay.getDelayDays().toString());
            userRepayBean.setDelayInterest(repay.getDelayInterest().toString());
            userRepayBean.setManageFee(repay.getRepayFee().toString());
            userRepayBean.setLateDays(repay.getLateDays().toString());
            userRepayBean.setLateInterest(repay.getLateInterest().toString());
            userRepayBean.setRepayTime(GetDate.getDateMyTimeInMillis(repay.getRepayTime()));
            userRepayBean.setStatus(repay.getRepayStatus().toString());
            userRepayBean.setUserId(repay.getUserId().toString());
            userRepayBean.setRepayPeriod("1");
            userRepayBean.setAdvanceStatus(repay.getAdvanceStatus().toString());
            List<RepayRecoverBean> userRecovers = repay.getRecoverList();
            if (userRecovers != null && userRecovers.size() > 0) {
                List<ProjectRepayDetailBean> userRepayDetails = new ArrayList<ProjectRepayDetailBean>();
                for (int i = 0; i < userRecovers.size(); i++) {
                    RepayRecoverBean userRecover = userRecovers.get(i);
                    // 如果发生债转
                    List<RepayCreditRepayBean> creditRepays = userRecover.getCreditRepayList();
                    if (creditRepays != null && creditRepays.size() > 0) {
                        // 循环遍历添加记录
                        for (int j = 0; j < creditRepays.size(); j++) {
                            RepayCreditRepayBean creditRepay = creditRepays.get(j);
                            ProjectRepayDetailBean userRepayDetail = new ProjectRepayDetailBean();
                            userRepayDetail.setRepayAccount(creditRepay.getAssignAccount().toString());
                            userRepayDetail.setRepayCapital(creditRepay.getAssignCapital().toString());
                            userRepayDetail.setRepayInterest(creditRepay.getAssignInterest().toString());
                            userRepayDetail.setChargeDays(userRecover.getChargeDays().toString());
                            userRepayDetail.setChargeInterest(creditRepay.getChargeInterest().multiply(new BigDecimal("-1")).toString());
                            userRepayDetail.setDelayDays(userRecover.getDelayDays().toString());
                            userRepayDetail.setDelayInterest(creditRepay.getDelayInterest().toString());
                            userRepayDetail.setManageFee(creditRepay.getManageFee().toString());
                            userRepayDetail.setLateDays(userRecover.getLateDays().toString());
                            userRepayDetail.setLateInterest(creditRepay.getLateInterest().toString());
                            userRepayDetail.setAdvanceStatus(userRecover.getAdvanceStatus().toString());
                            userRepayDetail.setRepayTime(GetDate.getDateMyTimeInMillis(userRecover.getRecoverTime()));
                            BigDecimal total = new BigDecimal("0");
                            if (creditRepay.getStatus() == 1) {
                                total = creditRepay.getAssignRepayAccount().add(creditRepay.getManageFee());
                            } else {
                                total = creditRepay.getAssignTotal();
                            }
                            userRepayDetail.setRepayTotal(total.toString());
                            userRepayDetail.setStatus(creditRepay.getStatus().toString());
                            userRepayDetail.setUserId(creditRepay.getUserId().toString());
                            String userName = this.searchUserNameById(creditRepay.getUserId());
                            String userNameStr = userName.substring(0, 1).concat("**");
                            userRepayDetail.setUserName(userNameStr);
                            userRepayDetails.add(userRepayDetail);
                        }
                    }
                    //计划债转列表
                    List<HjhDebtCreditRepayBean> hjhCreditRepayList = userRecover.getHjhCreditRepayList();

                    if (hjhCreditRepayList != null && hjhCreditRepayList.size() > 0) {
                        for (int k = 0; k < hjhCreditRepayList.size(); k++) {
                            HjhDebtCreditRepayBean creditRepay = hjhCreditRepayList.get(k);
                            ProjectRepayDetailBean userRepayDetail = new ProjectRepayDetailBean();
                            userRepayDetail.setRepayAccount(creditRepay.getRepayAccount().toString());
                            userRepayDetail.setRepayCapital(creditRepay.getRepayCapital().toString());
                            userRepayDetail.setRepayInterest(creditRepay.getRepayInterest().toString());
                            userRepayDetail.setChargeDays(userRecover.getChargeDays().toString());
                            userRepayDetail.setChargeInterest(creditRepay.getRepayAdvanceInterest().multiply(new BigDecimal("-1")).toString());
                            userRepayDetail.setDelayDays(userRecover.getDelayDays().toString());
                            userRepayDetail.setDelayInterest(creditRepay.getRepayDelayInterest().toString());
                            userRepayDetail.setManageFee(creditRepay.getManageFee().toString());
                            userRepayDetail.setLateDays(userRecover.getLateDays().toString());
                            userRepayDetail.setLateInterest(creditRepay.getRepayLateInterest().toString());
                            userRepayDetail.setAdvanceStatus(userRecover.getAdvanceStatus().toString());
                            userRepayDetail.setRepayTime(GetDate.getDateMyTimeInMillis(userRecover.getRecoverTime()));
                            BigDecimal total = new BigDecimal("0");
                            if (creditRepay.getRepayStatus() == 1) {
                                total = creditRepay.getRepayAccount().add(creditRepay.getManageFee());
                            } else {
                                total = creditRepay.getAssignTotal();
                            }
                            userRepayDetail.setRepayTotal(total.toString());
                            userRepayDetail.setStatus(creditRepay.getRepayStatus().toString());
                            userRepayDetail.setUserId(creditRepay.getUserId().toString());
                            String userName = this.searchUserNameById(creditRepay.getUserId());
                            String userNameStr = userName.substring(0, 1).concat("**");
                            userRepayDetail.setUserName(userNameStr);
                            userRepayDetails.add(userRepayDetail);
                        }
                    }
                    BorrowRecoverVO paramRecover = amTradeClient.selectBorrowRecoverById(userRecover.getId());
                    boolean overFlag = isOverUndertake(paramRecover, null, null, false, 0);
                    if (overFlag) {
                        ProjectRepayDetailBean userRepayDetail = new ProjectRepayDetailBean();
                        userRepayDetail.setRepayAccount(userRecover.getRecoverAccount().toString());
                        userRepayDetail.setRepayCapital(userRecover.getRecoverCapital().toString());
                        userRepayDetail.setRepayInterest(userRecover.getRecoverInterest().toString());
                        userRepayDetail.setChargeDays(userRecover.getChargeDays().toString());
                        userRepayDetail.setChargeInterest(userRecover.getChargeInterest().multiply(new BigDecimal("-1")).toString());
                        userRepayDetail.setDelayDays(userRecover.getDelayDays().toString());
                        userRepayDetail.setDelayInterest(userRecover.getDelayInterest().toString());
                        userRepayDetail.setManageFee(userRecover.getRecoverFee().toString());
                        userRepayDetail.setLateDays(userRecover.getLateDays().toString());
                        userRepayDetail.setLateInterest(userRecover.getLateInterest().toString());
                        userRepayDetail.setAdvanceStatus(userRecover.getAdvanceStatus().toString());
                        userRepayDetail.setRepayTime(GetDate.getDateMyTimeInMillis(userRecover.getRecoverTime()));
                        BigDecimal total = new BigDecimal("0");
                        if (userRecover.getRecoverStatus() == 1) {
                            total = userRecover.getRecoverAccountYes().add(userRecover.getRecoverFee());
                        } else {
                            // recover中account未更新
                            total = userRecover.getRecoverTotal();
                        }
                        userRepayDetail.setRepayTotal(total.toString());
                        userRepayDetail.setStatus(userRecover.getRecoverStatus().toString());
                        userRepayDetail.setUserId(userRecover.getUserId().toString());
                        String userName = this.searchUserNameById(userRecover.getUserId());
                        String userNameStr = userName.substring(0, 1).concat("**");
                        userRepayDetail.setUserName(userNameStr);
                        userRepayDetails.add(userRepayDetail);
                    }
                }
                userRepayBean.setUserRepayDetailList(userRepayDetails);
                userRepayList.add(userRepayBean);
            }
            form.setUserRepayList(userRepayList);
        } else {
            // 计算分期的项目还款信息
            RepayBean repayByTerm = this.calculateRepayByTerm(borrow);
            // 计算当前还款期数
            int repayPeriod = periodTotal - repayByTerm.getRepayPeriod() + 1;
            // 如果用户不是还款最后一期
            if (repayPeriod <= periodTotal) {
                BorrowApicronVO apicron = this.getApiCron(borrowApicronClient.selectBorrowApicronListByBorrowNid(borrowNid), repayPeriod);
                // 正在还款当前期
                if (apicron != null) {
                    if (apicron.getStatus() != 6) {
                        // 用户还款当前期
                        form.setRepayStatus("1");
                    } else {// 用户当前期正在还款
                        form.setRepayStatus("0");
                    }
                } else {// 用户未还款当前期
                    form.setRepayStatus("0");
                }
            } else {// 用户正在还款最后一期
                form.setRepayStatus("1");
            }
            // 设置当前的还款期数
            form.setRepayPeriod(String.valueOf(repayPeriod));
            // 获取统计的用户还款计划列表
            List<RepayDetailBean> userRepayPlans = repayByTerm.getRepayPlanList();
            if (userRepayPlans != null && userRepayPlans.size() > 0) {
                List<ProjectRepayBean> recoverList = new ArrayList<ProjectRepayBean>();
                // 遍历计划还款信息，拼接数据
                for (int i = 0; i < userRepayPlans.size(); i++) {
                    // 获取用户的还款信息
                    RepayDetailBean userRepayPlan = userRepayPlans.get(i);
                    // 声明需拼接数据的实体
                    ProjectRepayBean userRepayBean = new ProjectRepayBean();
                    // 如果本期已经还款完成
                    if (userRepayPlan.getRepayStatus() == 1) {
                        // 获取本期的用户已还款总额
                        userRepayBean.setRepayTotal(userRepayPlan.getRepayAccountYes().add(userRepayPlan.getRepayFee()).toString());
                    }
                    // 用户未还款本息
                    else {
                        // 此处分期计算的是本息+管理费
                        userRepayBean.setRepayTotal(userRepayPlan.getRepayAccountAll().toString());
                    }
                    userRepayBean.setRepayAccount(userRepayPlan.getRepayAccount().add(userRepayPlan.getChargeInterest()).add(userRepayPlan.getDelayInterest()).add(userRepayPlan.getLateInterest())
                            .toString());// 设置本期的用户本息和
                    userRepayBean.setRepayCapital(userRepayPlan.getRepayCapital().toString());// 用户未还款本息
                    userRepayBean.setRepayInterest(userRepayPlan.getRepayInterest().toString()); // 设置本期的用户利息
                    userRepayBean.setUserId(userRepayPlan.getUserId().toString());
                    userRepayBean.setRepayPeriod(userRepayPlan.getRepayPeriod().toString());
                    userRepayBean.setAdvanceStatus(userRepayPlan.getAdvanceStatus().toString());
                    userRepayBean.setStatus(userRepayPlan.getRepayStatus().toString());
                    userRepayBean.setRepayTime(GetDate.getDateMyTimeInMillis(userRepayPlan.getRepayTime()));
                    userRepayBean.setChargeDays(userRepayPlan.getChargeDays().toString());
                    userRepayBean.setChargeInterest(userRepayPlan.getChargeInterest().multiply(new BigDecimal("-1")).toString());
                    userRepayBean.setDelayDays(userRepayPlan.getDelayDays().toString());
                    userRepayBean.setDelayInterest(userRepayPlan.getDelayInterest().toString());
                    userRepayBean.setManageFee(userRepayPlan.getRepayFee().toString());
                    userRepayBean.setLateDays(userRepayPlan.getLateDays().toString());
                    userRepayBean.setLateInterest(userRepayPlan.getLateInterest().toString());
                    if (repayPeriod == userRepayPlan.getRepayPeriod()) {
                        form.setManageFee(userRepayPlan.getRepayFee().toString());
                        form.setRepayTotal(userRepayPlan.getRepayAccountAll().toString());// 此处计算的是还款总额包含管理费
                        form.setRepayAccount(userRepayPlan.getRepayAccount().add(userRepayPlan.getChargeInterest()).add(userRepayPlan.getDelayInterest()).add(userRepayPlan.getLateInterest())
                                .toString());
                        form.setRepayCapital(userRepayPlan.getRepayCapital().toString());
                        form.setRepayInterest(userRepayPlan.getRepayInterest().add(userRepayPlan.getChargeInterest()).add(userRepayPlan.getDelayInterest()).add(userRepayPlan.getLateInterest()).toString());
                        form.setShouldInterest(userRepayPlan.getRepayInterest().toString());
                        form.setAdvanceStatus(userRepayPlan.getAdvanceStatus().toString());
                        form.setChargeDays(userRepayPlan.getChargeDays().toString());
                        form.setChargeInterest(userRepayPlan.getChargeInterest().multiply(new BigDecimal("-1")).toString());
                        form.setDelayDays(userRepayPlan.getDelayDays().toString());
                        form.setDelayInterest(userRepayPlan.getDelayInterest().toString());
                        form.setLateDays(userRepayPlan.getLateDays().toString());
                        form.setLateInterest(userRepayPlan.getLateInterest().toString());
                    }
                    List<RepayRecoverPlanBean> userRecoversDetails = userRepayPlan.getRecoverPlanList();
                    List<ProjectRepayDetailBean> userRepayDetails = new ArrayList<ProjectRepayDetailBean>();
                    for (int j = 0; j < userRecoversDetails.size(); j++) {
                        RepayRecoverPlanBean userRecoverPlan = userRecoversDetails.get(j);
                        Integer id = userRecoverPlan.getId();
                        BorrowRecoverPlanVO planInfo = amTradeClient.selectRecoverPlanById(id);
                        BigDecimal recoverAccount = planInfo.getRecoverAccount();
                        // 如果发生债转
                        int hjhFlag = 0;//是否计划债转
                        List<RepayCreditRepayBean> creditRepays = userRecoverPlan.getCreditRepayList();
                        if (creditRepays != null && creditRepays.size() > 0) {
                            // 循环遍历添加记录
                            for (int k = 0; k < creditRepays.size(); k++) {
                                RepayCreditRepayBean creditRepay = creditRepays.get(k);
                                ProjectRepayDetailBean userRepayDetail = new ProjectRepayDetailBean();
                                userRepayDetail.setRepayAccount(creditRepay.getAssignAccount().toString());
                                userRepayDetail.setRepayCapital(creditRepay.getAssignCapital().toString());
                                userRepayDetail.setRepayInterest(creditRepay.getAssignInterest().toString());
                                userRepayDetail.setChargeDays(userRecoverPlan.getChargeDays().toString());
                                userRepayDetail.setChargeInterest(creditRepay.getChargeInterest().multiply(new BigDecimal("-1")).toString());
                                userRepayDetail.setDelayDays(userRecoverPlan.getDelayDays().toString());
                                userRepayDetail.setDelayInterest(creditRepay.getDelayInterest().toString());
                                userRepayDetail.setManageFee(creditRepay.getManageFee().toString());
                                userRepayDetail.setLateDays(userRecoverPlan.getLateDays().toString());
                                userRepayDetail.setLateInterest(creditRepay.getLateInterest().toString());
                                userRepayDetail.setAdvanceStatus(userRecoverPlan.getAdvanceStatus().toString());
                                userRepayDetail.setRepayTime(GetDate.getDateMyTimeInMillis(userRecoverPlan.getRecoverTime()));
                                BigDecimal total = new BigDecimal("0");
                                if (creditRepay.getStatus() == 1) {
                                    total = creditRepay.getAssignRepayAccount().add(creditRepay.getManageFee());
                                } else {
                                    total = creditRepay.getAssignTotal();
                                }
                                userRepayDetail.setRepayTotal(total.toString());
                                userRepayDetail.setStatus(creditRepay.getStatus().toString());
                                userRepayDetail.setUserId(creditRepay.getUserId().toString());
                                String userName = this.searchUserNameById(creditRepay.getUserId());
                                String userNameStr = userName.substring(0, 1).concat("**");
                                userRepayDetail.setUserName(userNameStr);
                                userRepayDetails.add(userRepayDetail);
                            }
                        }
                        //计划债转列表
                        List<HjhDebtCreditRepayBean> hjhCreditRepayList = userRecoverPlan.getHjhCreditRepayList();
                        BigDecimal sumAccount = BigDecimal.ZERO;
                        if (hjhCreditRepayList != null && hjhCreditRepayList.size() > 0) {
                            hjhFlag = 1;
                            for (int k = 0; k < hjhCreditRepayList.size(); k++) {
                                HjhDebtCreditRepayBean creditRepay = hjhCreditRepayList.get(k);
                                ProjectRepayDetailBean userRepayDetail = new ProjectRepayDetailBean();
                                sumAccount = sumAccount.add(creditRepay.getRepayAccount());
                                userRepayDetail.setRepayAccount(creditRepay.getRepayAccount().toString());
                                userRepayDetail.setRepayCapital(creditRepay.getRepayCapital().toString());
                                userRepayDetail.setRepayInterest(creditRepay.getRepayInterest().toString());
                                userRepayDetail.setChargeDays(userRecoverPlan.getChargeDays().toString());
                                userRepayDetail.setChargeInterest(creditRepay.getRepayAdvanceInterest().multiply(new BigDecimal("-1")).toString());
                                userRepayDetail.setDelayDays(userRecoverPlan.getDelayDays().toString());
                                userRepayDetail.setDelayInterest(creditRepay.getRepayDelayInterest().toString());
                                userRepayDetail.setManageFee(creditRepay.getManageFee().toString());
                                userRepayDetail.setLateDays(userRecoverPlan.getLateDays().toString());
                                userRepayDetail.setLateInterest(creditRepay.getRepayLateInterest().toString());
                                userRepayDetail.setAdvanceStatus(userRecoverPlan.getAdvanceStatus().toString());
                                userRepayDetail.setRepayTime(GetDate.getDateMyTimeInMillis(userRecoverPlan.getRecoverTime()));
                                BigDecimal total = new BigDecimal("0");
                                if (creditRepay.getRepayStatus() == 1) {
                                    total = creditRepay.getReceiveAccountYes().add(creditRepay.getManageFee());
                                } else {
                                    total = creditRepay.getAssignTotal();
                                }
                                userRepayDetail.setRepayTotal(total.toString());
                                userRepayDetail.setStatus(creditRepay.getRepayStatus().toString());
                                userRepayDetail.setUserId(creditRepay.getUserId().toString());
                                String userName = this.searchUserNameById(creditRepay.getUserId());
                                String userNameStr = userName.substring(0, 1).concat("**");
                                userRepayDetail.setUserName(userNameStr);
                                userRepayDetails.add(userRepayDetail);
                            }
                        }
                        BorrowRecoverVO borrowRecover = amTradeClient.selectBorrowRecoverByNid(userRecoverPlan.getNid());
                        boolean overFlag = isOverUndertake(borrowRecover, recoverAccount, sumAccount, true, hjhFlag);
                        Integer recoverStatus = userRecoverPlan.getRecoverStatus();
                        if (overFlag) {
                            ProjectRepayDetailBean userRepayDetail = new ProjectRepayDetailBean();
                            userRepayDetail.setRepayAccount(userRecoverPlan.getRecoverAccount().toString());
                            userRepayDetail.setRepayCapital(userRecoverPlan.getRecoverCapital().toString());
                            userRepayDetail.setRepayInterest(userRecoverPlan.getRecoverInterest().toString());
                            userRepayDetail.setChargeDays(userRecoverPlan.getChargeDays().toString());
                            if (recoverStatus == 1) {//已还款
                                userRepayDetail.setChargeInterest(planInfo.getChargeInterest().multiply(new BigDecimal("-1")).toString());
                            } else {
                                userRepayDetail.setChargeInterest(userRecoverPlan.getChargeInterest().multiply(new BigDecimal("-1")).toString());
                            }
                            userRepayDetail.setDelayDays(userRecoverPlan.getDelayDays().toString());
                            if (recoverStatus == 1) {
                                userRepayDetail.setDelayInterest(planInfo.getDelayInterest().toString());
                            } else {
                                userRepayDetail.setDelayInterest(userRecoverPlan.getDelayInterest().toString());
                            }
                            userRepayDetail.setManageFee(userRecoverPlan.getRecoverFee().toString());
                            userRepayDetail.setLateDays(userRecoverPlan.getLateDays().toString());
                            if (recoverStatus == 1) {
                                userRepayDetail.setLateInterest(planInfo.getLateInterest().toString());
                            } else {
                                userRepayDetail.setLateInterest(userRecoverPlan.getLateInterest().toString());
                            }
                            userRepayDetail.setAdvanceStatus(userRecoverPlan.getAdvanceStatus().toString());
                            userRepayDetail.setRepayTime(GetDate.getDateMyTimeInMillis(userRecoverPlan.getRecoverTime()));
                            BigDecimal total = new BigDecimal("0");
                            if (recoverStatus == 1) {
                                total = planInfo.getRecoverAccountYes().subtract(sumAccount).add(userRecoverPlan.getRecoverFee());
                            } else {
                                // recover中account未更新
                                total = userRecoverPlan.getRecoverTotal();
                            }
                            userRepayDetail.setRepayTotal(total.toString());
                            userRepayDetail.setStatus(recoverStatus.toString());
                            userRepayDetail.setUserId(userRecoverPlan.getUserId().toString());
                            String userName = this.searchUserNameById(userRecoverPlan.getUserId());
                            String userNameStr = userName.substring(0, 1).concat("**");
                            userRepayDetail.setUserName(userNameStr);
                            userRepayDetails.add(userRepayDetail);
                        }
                    }
                    userRepayBean.setUserRepayDetailList(userRepayDetails);
                    recoverList.add(userRepayBean);
                }
                form.setUserRepayList(recoverList);
            }
        }
        return form;

    }

    public RepayBean calculateRepay(BorrowVO borrow) throws ParseException {

        RepayBean repay = new RepayBean();
        // 获取还款总表数据
        BorrowRepayVO borrowRepay = borrowRepayClient.getBorrowRepay(borrow.getBorrowNid());
        // 判断是否存在还款数据
        if (borrowRepay != null) {
            // 获取相应的还款信息
            BeanUtils.copyProperties(borrowRepay, repay);
            // 计划还款时间
            String repayTimeStr = String.valueOf(borrowRepay.getRepayTime());
            // 获取用户申请的延期天数
            int delayDays = borrowRepay.getDelayDays().intValue();
            repay.setBorrowPeriod(String.valueOf(borrow.getBorrowPeriod()));
            // 未分期默认传分期为0
            this.calculateRecover(repay, borrow, repayTimeStr, delayDays);
        }
        return repay;
    }

    /**
     * 计算单期的用户的还款信息
     *
     * @param repay
     * @param borrow
     * @param repayTimeStr
     * @param delayDays
     * @throws ParseException
     */
    private void calculateRecover(RepayBean repay, BorrowVO borrow, String repayTimeStr, int delayDays) throws ParseException {
        int time = GetDate.getNowTime10();
        // 用户计划还款时间
        String repayTime = GetDate.getDateTimeMyTimeInMillis(Integer.parseInt(repayTimeStr));
        // 用户实际还款时间
        String factRepayTime = GetDate.getDateTimeMyTimeInMillis(time);
        int distanceDays = GetDate.daysBetween(factRepayTime, repayTime);
        String borrowStyle = borrow.getBorrowStyle();
        if (distanceDays < 0) {// 用户延期或者逾期了
            int lateDays = delayDays + distanceDays;
            if (lateDays >= 0) {// 用户延期还款
                delayDays = -distanceDays;
                this.calculateRecoverTotalDelay(repay, borrow, delayDays);
            } else {// 用户逾期还款
                lateDays = -lateDays;
                if (StringUtils.isNotBlank(borrow.getPlanNid())) {//汇计划计算逾期免息金额
                    Integer lateFreeDays = borrow.getLateFreeDays();
                    if (lateFreeDays >= lateDays) {//在免息期以内,正常还款
                        this.calculateRecoverTotal(repay, borrow, distanceDays);
                    } else {//过了免息期,罚免息期外的利息
                        lateDays = lateDays - lateFreeDays;
                        this.calculateRecoverTotalLate(repay, borrow, delayDays, lateDays);
                    }
                } else {
                    this.calculateRecoverTotalLate(repay, borrow, delayDays, lateDays);
                }
            }
        } else {// 用户正常或者提前还款
            // 获取提前还款的阀值
            BorrowConfigVO borrowConfigVO = amTradeClient.getConfigByCode("REPAY_ADVANCE_TIME");
            String repayAdvanceDay = borrowConfigVO.getConfigValue();
            int advanceDays = distanceDays;
            // 用户提前还款
            //如果是融通宝项目,不判断提前还款的阀值 add by cwyang 2017-6-14
            int projectType = borrow.getProjectType();
            if (13 == projectType || CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                repayAdvanceDay = "0";
            }
            if (Integer.parseInt(repayAdvanceDay) < advanceDays) {
                // 计算用户提前还款总额
                this.calculateRecoverTotalAdvance(repay, borrow, advanceDays);
            } else {// 用户正常还款
                // 计算用户实际还款总额
                this.calculateRecoverTotal(repay, borrow, advanceDays);
            }
        }
    }

    /**
     * 统计单期还款用户正常还款的总标
     *
     * @param repay
     * @param interestDay
     * @throws ParseException
     */
    private void calculateRecoverTotal(RepayBean repay, BorrowVO borrow, int interestDay) throws ParseException {
        // 正常还款
        String borrowNid = borrow.getBorrowNid();// 项目编号
        String borrowStyle = borrow.getBorrowStyle(); // 还款方式
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());// 管理费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());// 差异费率
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : Integer.parseInt(borrow.getVerifyTime());// 初审时间
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();// 项目总期数
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        List<BorrowRecoverVO> borrowRecovers = amTradeClient.selectBorrowRecoverByBorrowNid(borrowNid);
        if (borrowRecovers != null && borrowRecovers.size() > 0) {
            List<RepayRecoverBean> repayRecoverList = new ArrayList<RepayRecoverBean>();
            BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
            BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户還款管理費
            for (int i = 0; i < borrowRecovers.size(); i++) {
                BorrowRecoverVO borrowRecover = borrowRecovers.get(i);
                RepayRecoverBean repayRecoverBean = new RepayRecoverBean();
                String tenderOrderId = borrowRecover.getNid();// 投资订单号
                userAccount = borrowRecover.getRecoverAccount();// 计算用户实际获得的本息和
                userCapital = borrowRecover.getRecoverCapital();
                userInterest = borrowRecover.getRecoverInterest();
                userManageFee = borrowRecover.getRecoverFee();// 计算用户還款管理費
                if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                    if (Validator.isNull(borrowRecover.getAccedeOrderId())) {
                        // 投资项目还款
                        List<CreditRepayVO> creditRepayList = creditClient.selectCreditRepayList(borrowNid, tenderOrderId, 1, 0);
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                CreditRepayVO creditRepay = creditRepayList.get(j);
                                RepayCreditRepayBean creditRepayBean = new RepayCreditRepayBean();
                                assignAccount = creditRepay.getAssignAccount();// 计算用户实际获得的本息和
                                assignCapital = creditRepay.getAssignCapital();// 用户实际还款本本金
                                assignInterest = creditRepay.getAssignInterest();
                                if (borrowRecover.getCreditStatus() == 2 && j == creditRepayList.size() - 1) {
                                    assignManageFee = userManageFee;
                                } else {
                                    // 按月计息，到期还本还息end
                                    if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByMonth(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 按天计息到期还本还息
                                    else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByDay(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                }
                                BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                creditRepayBean.setAssignTotal(assignAccount.add(assignManageFee));
                                creditRepayBean.setAssignInterest(assignInterest);
                                creditRepayBean.setManageFee(assignManageFee);
                                creditRepayBean.setAdvanceStatus(0);
                                creditRepayBean.setChargeInterest(BigDecimal.ZERO);
                                creditRepayBean.setChargeDays(interestDay);
                                creditRepayBeanList.add(creditRepayBean);
                                // 统计出让人还款金额
                                userAccount = userAccount.subtract(assignAccount);
                                userCapital = userCapital.subtract(assignCapital);
                                userInterest = userInterest.subtract(assignInterest);
                                userManageFee = userManageFee.subtract(assignManageFee);
                                // 统计总额
                                repayTotal = repayTotal.add(assignAccount).add(assignManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                repayCapital = repayCapital.add(assignCapital);
                                repayInterest = repayInterest.add(assignInterest);
                                repayManageFee = repayManageFee.add(assignManageFee);// 統計管理費
                            }
                            repayRecoverBean.setCreditRepayList(creditRepayBeanList);
                        }
                        if (borrowRecover.getCreditStatus() != 2) {
                            // 统计总额
                            repayTotal = repayTotal.add(userAccount).add(userManageFee);// 统计总和本息+管理费
                            repayAccount = repayAccount.add(userAccount);// 统计总和本息
                            repayCapital = repayCapital.add(userCapital);
                            repayInterest = repayInterest.add(userInterest);
                            repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                        }
                    } else {
                        // 计划类还款
                        List<HjhDebtCreditRepayVO> creditRepayList = amTradeClient.selectHjhDebtCreditRepay(borrowNid, tenderOrderId, 1, borrowRecover.getRecoverStatus());
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                            //判断当前期是否全部承接
                            boolean overFlag = isOverUndertake(borrowRecover, null, null, false, 0);
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                HjhDebtCreditRepayVO creditRepay = creditRepayList.get(j);
                                HjhDebtCreditRepayBean creditRepayBean = new HjhDebtCreditRepayBean();
                                assignAccount = creditRepay.getRepayAccount();// 计算用户实际获得的本息和
                                assignCapital = creditRepay.getRepayCapital();// 用户实际还款本本金
                                assignInterest = creditRepay.getRepayInterest();
                                if (!overFlag && j == creditRepayList.size() - 1) {
                                    assignManageFee = userManageFee;
                                } else {
                                    // 按月计息，到期还本还息end
                                    if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByMonth(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 按天计息到期还本还息
                                    else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByDay(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                }
                                BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                creditRepayBean.setAssignTotal(assignAccount.add(assignManageFee));
                                creditRepayBean.setRepayInterest(assignInterest);
                                creditRepayBean.setManageFee(assignManageFee);
                                creditRepayBean.setAdvanceStatus(0);
                                creditRepayBean.setRepayAdvanceInterest(BigDecimal.ZERO);
                                creditRepayBean.setAdvanceDays(interestDay);
                                creditRepayBeanList.add(creditRepayBean);
                                // 统计出让人还款金额
                                userAccount = userAccount.subtract(assignAccount);
                                userCapital = userCapital.subtract(assignCapital);
                                userInterest = userInterest.subtract(assignInterest);
                                userManageFee = userManageFee.subtract(assignManageFee);
                                // 统计总额
                                repayTotal = repayTotal.add(assignAccount).add(assignManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                repayCapital = repayCapital.add(assignCapital);
                                repayInterest = repayInterest.add(assignInterest);
                                repayManageFee = repayManageFee.add(assignManageFee);// 統計管理費
                            }
                            repayRecoverBean.setHjhCreditRepayList(creditRepayBeanList);
                        }
                        if (borrowRecover.getCreditStatus() != 2) {
                            // 统计总额
                            repayTotal = repayTotal.add(userAccount).add(userManageFee);// 统计总和本息+管理费
                            repayAccount = repayAccount.add(userAccount);// 统计总和本息
                            repayCapital = repayCapital.add(userCapital);
                            repayInterest = repayInterest.add(userInterest);
                            repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                        }
                    }
                } else {
                    repayTotal = repayTotal.add(userAccount).add(userManageFee);// 统计总和本息+管理费
                    repayAccount = repayAccount.add(userAccount); // 统计本息总和
                    repayCapital = repayCapital.add(userCapital);
                    repayInterest = repayInterest.add(userInterest);
                    repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                }
                BeanUtils.copyProperties(borrowRecover, repayRecoverBean);
                repayRecoverBean.setRecoverTotal(userAccount.add(userManageFee));
                repayRecoverBean.setRecoverAccount(userAccount);
                repayRecoverBean.setRecoverCapital(userCapital);
                repayRecoverBean.setRecoverInterest(userInterest);
                repayRecoverBean.setRecoverFee(userManageFee);
                repayRecoverBean.setChargeDays(interestDay);
                repayRecoverBean.setAdvanceStatus(0);
                repayRecoverList.add(repayRecoverBean);
            }
            repay.setRecoverList(repayRecoverList);
        }
        repay.setRepayAccountAll(repayTotal);
        repay.setRepayAccount(repayAccount);
        repay.setRepayCapital(repayCapital);
        repay.setRepayInterest(repayInterest);
        repay.setRepayFee(repayManageFee);
        repay.setChargeDays(interestDay);
        repay.setAdvanceStatus(0);
    }

    /**
     * 统计单期还款用户提前还款的总标
     *
     * @param repay
     * @param interestDay
     * @throws ParseException
     */
    private void calculateRecoverTotalAdvance(RepayBean repay, BorrowVO borrow, int interestDay) throws ParseException {

        // 用户提前还款
        String borrowNid = borrow.getBorrowNid();// 项目编号
        String borrowStyle = borrow.getBorrowStyle();// 还款方式
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());// 管理费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());// 差异费率
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : Integer.parseInt(borrow.getVerifyTime());// 初审时间
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();// 项目总期数
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayChargeInterest = BigDecimal.ZERO;// 提前还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        List<BorrowRecoverVO> borrowRecovers = amTradeClient.selectBorrowRecoverByBorrowNid(borrow.getBorrowNid());
        if (borrowRecovers != null && borrowRecovers.size() > 0) {
            List<RepayRecoverBean> repayRecoverList = new ArrayList<RepayRecoverBean>();
            BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
            BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
            BigDecimal userManageFee = BigDecimal.ZERO;// 获取应还款管理费
            for (int i = 0; i < borrowRecovers.size(); i++) {
                BorrowRecoverVO borrowRecover = borrowRecovers.get(i);
                RepayRecoverBean repayRecoverBean = new RepayRecoverBean();
                String tenderOrderId = borrowRecover.getNid();// 投资订单号
                String recoverTime = GetDate.getDateTimeMyTimeInMillis(borrowRecover.getRecoverTime());
                String createTime = GetDate.getDateTimeMyTimeInMillis(borrowRecover.getAddTime());
                int totalDays = GetDate.daysBetween(createTime, recoverTime);// 获取这两个时间之间有多少天
                // 计算投资用户实际获得的本息和
                userAccount = borrowRecover.getRecoverAccount();
                userCapital = borrowRecover.getRecoverCapital();
                userInterest = borrowRecover.getRecoverInterest();
                // 如果项目类型为融通宝，调用新的提前还款利息计算公司
                if (borrow.getProjectType() == 13 || CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                    // 提前还款不应该大于本次计息时间
                    if (totalDays < interestDay) {
                        // 用户提前还款减少的利息
                        userChargeInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(userCapital, borrow.getBorrowApr(), totalDays);
                    } else {
                        // 用户提前还款减少的利息
                        userChargeInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(userCapital, borrow.getBorrowApr(), interestDay);
                    }
                } else {
                    boolean isStyle = CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
                    if (isStyle) {
                        if (interestDay >= 30) {
                            userChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(userInterest, totalDays);
                        } else {
                            userChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(userInterest, interestDay);
                        }
                    } else {
                        // 提前还款不应该大于本次计息时间
                        if (totalDays < interestDay) {

                            // 用户提前还款减少的利息
                            userChargeInterest = UnnormalRepayUtils.aheadRepayChargeInterest(userCapital, borrow.getBorrowApr(), totalDays);

                        } else {

                            // 用户提前还款减少的利息
                            userChargeInterest = UnnormalRepayUtils.aheadRepayChargeInterest(userCapital, borrow.getBorrowApr(), interestDay);

                        }
                    }
                }
                // 项目提前还款时，提前还款利息不得大于应还款利息，需求变更
                if (userChargeInterest.compareTo(userInterest) > 0) {
                    userChargeInterest = userInterest;
                }
                userManageFee = borrowRecover.getRecoverFee();// 获取应还款管理费
                if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                    if (Validator.isNull(borrowRecover.getAccedeOrderId())) {
                        // 直投项目债转还款
                        // 债转还款数据
                        List<CreditRepayVO> creditRepayList = creditClient.selectCreditRepayList(borrowNid, tenderOrderId, 1, 0);
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                CreditRepayVO creditRepay = creditRepayList.get(j);
                                RepayCreditRepayBean creditRepayBean = new RepayCreditRepayBean();
                                // 计算投资用户实际获得的本息和
                                assignAccount = creditRepay.getAssignAccount();
                                assignCapital = creditRepay.getAssignCapital();// 承接本金
                                assignInterest = creditRepay.getAssignInterest();
                                //最后一笔兜底
                                if (borrowRecover.getCreditStatus() == 2 && j == creditRepayList.size() - 1) {
                                    assignManageFee = userManageFee;
                                    assignChargeInterest = userChargeInterest;
                                } else {
                                    // 按月计息，到期还本还息end
                                    if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByMonth(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 按天计息到期还本还息
                                    else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByDay(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 如果项目类型为融通宝，调用新的提前还款利息计算公司
                                    if (borrow.getProjectType() == 13) {
                                        // 提前还款不应该大于本次计息时间
                                        if (totalDays < interestDay) {
                                            // 用户提前还款减少的利息
                                            assignChargeInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(assignCapital, borrow.getBorrowApr(), totalDays);
                                        } else {
                                            // 用户提前还款减少的利息
                                            assignChargeInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(assignCapital, borrow.getBorrowApr(), interestDay);
                                        }
                                    } else {
                                        boolean isStyle = CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
                                        if (isStyle) {
                                            if (interestDay >= 30) {
                                                assignChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(assignInterest, totalDays);
                                            } else {
                                                assignChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(assignInterest, interestDay);
                                            }
                                        } else {
                                            // 提前还款不应该大于本次计息时间
                                            if (totalDays < interestDay) {
                                                // 用户提前还款减少的利息
                                                assignChargeInterest = UnnormalRepayUtils.aheadRepayChargeInterest(assignCapital, borrow.getBorrowApr(), totalDays);
                                            } else {
                                                // 用户提前还款减少的利息
                                                assignChargeInterest = UnnormalRepayUtils.aheadRepayChargeInterest(assignCapital, borrow.getBorrowApr(), interestDay);

                                            }
                                        }
                                    }
                                }
                                // 项目提前还款时，提前还款利息不得大于应还款利息，需求变更
                                if (assignChargeInterest.compareTo(assignInterest) > 0) {
                                    assignChargeInterest = assignInterest;
                                }
                                BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                creditRepayBean.setAssignTotal(assignAccount.subtract(assignChargeInterest).add(assignManageFee));
                                creditRepayBean.setManageFee(assignManageFee);
                                creditRepayBean.setAdvanceStatus(1);
                                creditRepayBean.setChargeInterest(assignChargeInterest.multiply(new BigDecimal(-1)));
                                creditRepayBean.setChargeDays(interestDay);
                                creditRepayBeanList.add(creditRepayBean);
                                // 统计出让人还款金额
                                userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                userCapital = userCapital.subtract(assignCapital);
                                userInterest = userInterest.subtract(assignInterest);
                                userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
                                userChargeInterest = userChargeInterest.subtract(assignChargeInterest);// 提前还款利息
                                // 统计总额
                                repayTotal = repayTotal.add(assignAccount).subtract(assignChargeInterest).add(assignManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                repayCapital = repayCapital.add(assignCapital);
                                repayInterest = repayInterest.add(assignInterest);
                                repayManageFee = repayManageFee.add(assignManageFee);// 統計管理費
                                repayChargeInterest = repayChargeInterest.add(assignChargeInterest);// 统计提前还款减少的利息
                            }
                            repayRecoverBean.setCreditRepayList(creditRepayBeanList);
                        }
                        if (borrowRecover.getCreditStatus() != 2) {
                            // 统计总额
                            repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userManageFee);// 统计总和本息+管理费
                            repayAccount = repayAccount.add(userAccount);// 统计总和本息
                            repayCapital = repayCapital.add(userCapital);
                            repayInterest = repayInterest.add(userInterest);
                            repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                            repayChargeInterest = repayChargeInterest.add(userChargeInterest);// 统计提前还款减少的利息
                        }
                    } else {
                        boolean overFlag = false;
                        // 计划类还款
                        // 债转还款数据
                        List<HjhDebtCreditRepayVO> creditRepayList = amTradeClient.selectHjhDebtCreditRepay(borrowNid, tenderOrderId, 1, borrowRecover.getRecoverStatus());
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                            //判断当前期是否全部承接
                            overFlag = isOverUndertake(borrowRecover, null, null, false, 0);
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                HjhDebtCreditRepayVO creditRepay = creditRepayList.get(j);
                                HjhDebtCreditRepayBean creditRepayBean = new HjhDebtCreditRepayBean();
                                // 计算投资用户实际获得的本息和
                                assignAccount = creditRepay.getRepayAccount();
                                assignCapital = creditRepay.getRepayCapital();// 承接本金
                                assignInterest = creditRepay.getRepayInterest();
                                //最后一笔兜底
                                if (!overFlag && j == creditRepayList.size() - 1) {
                                    assignManageFee = userManageFee;
                                    assignChargeInterest = userChargeInterest;
                                } else {
                                    // 按月计息，到期还本还息end
                                    if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByMonth(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 按天计息到期还本还息
                                    else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByDay(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 如果项目类型为融通宝，调用新的提前还款利息计算公司
                                    if (borrow.getProjectType() == 13) {
                                        // 提前还款不应该大于本次计息时间
                                        if (totalDays < interestDay) {
                                            // 用户提前还款减少的利息
                                            assignChargeInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(assignCapital, borrow.getBorrowApr(), totalDays);
                                        } else {
                                            // 用户提前还款减少的利息
                                            assignChargeInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(assignCapital, borrow.getBorrowApr(), interestDay);
                                        }
                                    } else {
                                        boolean isStyle = CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
                                        if (isStyle) {
                                            if (interestDay >= 30) {
                                                assignChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(assignInterest, totalDays);
                                            } else {
                                                assignChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(assignInterest, interestDay);
                                            }
                                        } else {
                                            // 提前还款不应该大于本次计息时间
                                            if (totalDays < interestDay) {
                                                // 用户提前还款减少的利息
                                                assignChargeInterest = UnnormalRepayUtils.aheadRepayChargeInterest(assignCapital, borrow.getBorrowApr(), totalDays);
                                            } else {
                                                // 用户提前还款减少的利息
                                                assignChargeInterest = UnnormalRepayUtils.aheadRepayChargeInterest(assignCapital, borrow.getBorrowApr(), interestDay);
                                            }
                                        }
                                    }
                                }
                                // 项目提前还款时，提前还款利息不得大于应还款利息，需求变更
                                if (assignChargeInterest.compareTo(assignInterest) > 0) {
                                    assignChargeInterest = assignInterest;
                                }
                                BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                creditRepayBean.setAssignTotal(assignAccount.subtract(assignChargeInterest).add(assignManageFee));
                                creditRepayBean.setManageFee(assignManageFee);
                                creditRepayBean.setAdvanceStatus(1);
                                creditRepayBean.setRepayAdvanceInterest(assignChargeInterest.multiply(new BigDecimal(-1)));
                                creditRepayBean.setAdvanceDays(interestDay);
                                creditRepayBeanList.add(creditRepayBean);
                                // 统计出让人还款金额
                                userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                userCapital = userCapital.subtract(assignCapital);
                                userInterest = userInterest.subtract(assignInterest);
                                userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
                                userChargeInterest = userChargeInterest.subtract(assignChargeInterest);// 提前还款利息
                                // 统计总额
                                repayTotal = repayTotal.add(assignAccount).subtract(assignChargeInterest).add(assignManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                repayCapital = repayCapital.add(assignCapital);
                                repayInterest = repayInterest.add(assignInterest);
                                repayManageFee = repayManageFee.add(assignManageFee);// 統計管理費
                                repayChargeInterest = repayChargeInterest.add(assignChargeInterest);// 统计提前还款减少的利息
                            }
                            repayRecoverBean.setHjhCreditRepayList(creditRepayBeanList);
                        }
                        if (overFlag) {
                            // 统计总额
                            repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userManageFee);// 统计总和本息+管理费
                            repayAccount = repayAccount.add(userAccount);// 统计总和本息
                            repayCapital = repayCapital.add(userCapital);
                            repayInterest = repayInterest.add(userInterest);
                            repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                            repayChargeInterest = repayChargeInterest.add(userChargeInterest);// 统计提前还款减少的利息
                        }
                    }
                } else {
                    // 统计总额
                    repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userManageFee);// 统计总和本息+管理费
                    repayAccount = repayAccount.add(userAccount); // 统计本息总和
                    repayCapital = repayCapital.add(userCapital);
                    repayInterest = repayInterest.add(userInterest);
                    repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                    repayChargeInterest = repayChargeInterest.add(userChargeInterest);// 统计提前还款减少的利息
                }
                BeanUtils.copyProperties(borrowRecover, repayRecoverBean);
                repayRecoverBean.setRecoverTotal(userAccount.subtract(userChargeInterest).add(userManageFee));
                repayRecoverBean.setRecoverAccount(userAccount);
                repayRecoverBean.setRecoverCapital(userCapital);
                repayRecoverBean.setRecoverInterest(userInterest);
                repayRecoverBean.setRecoverFee(userManageFee);
                repayRecoverBean.setChargeInterest(userChargeInterest.multiply(new BigDecimal(-1)));
                repayRecoverBean.setAdvanceStatus(1);
                repayRecoverBean.setChargeDays(interestDay);
                repayRecoverList.add(repayRecoverBean);
            }
            repay.setRecoverList(repayRecoverList);
        }
        repay.setRepayAccountAll(repayTotal);
        repay.setRepayAccount(repayAccount);
        repay.setRepayCapital(repayCapital);
        repay.setRepayInterest(repayInterest);
        repay.setRepayFee(repayManageFee);
        repay.setChargeDays(interestDay);
        repay.setChargeInterest(repayChargeInterest.multiply(new BigDecimal(-1)));
        repay.setAdvanceStatus(1);

    }

    /**
     * 统计单期还款用户延期还款的总标
     *
     * @param repay
     * @param delayDays
     * @throws ParseException
     */
    private void calculateRecoverTotalDelay(RepayBean repay, BorrowVO borrow, int delayDays) throws ParseException {

        // 用户延期
        String borrowNid = borrow.getBorrowNid();// 项目编号
        String borrowStyle = borrow.getBorrowStyle(); // 还款方式
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate()); // 管理费率
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : Integer.parseInt(borrow.getVerifyTime()); // 初审时间
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();// 项目总期数
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        BigDecimal repayDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
        // 查询相应的不分期的还款信息
        List<BorrowRecoverVO> borrowRecovers = amTradeClient.selectBorrowRecoverByBorrowNid(borrowNid);
        if (borrowRecovers != null && borrowRecovers.size() > 0) {
            List<RepayRecoverBean> repayRecoverList = new ArrayList<RepayRecoverBean>();
            BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
            BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
            BigDecimal userManageFee = BigDecimal.ZERO;// 获取应还款管理费
            for (int i = 0; i < borrowRecovers.size(); i++) {
                BorrowRecoverVO borrowRecover = borrowRecovers.get(i);
                RepayRecoverBean repayRecoverBean = new RepayRecoverBean();
                String tenderOrderId = borrowRecover.getNid();// 投资订单号
                userAccount = borrowRecover.getRecoverAccount();
                userCapital = borrowRecover.getRecoverCapital();
                userInterest = borrowRecover.getRecoverInterest();
                // 计算用户延期利息
                userDelayInterest = UnnormalRepayUtils.delayRepayInterest(userCapital, borrow.getBorrowApr(), delayDays);
                // 用户管理费
                userManageFee = borrowRecover.getRecoverFee();
                // 如果已经发生债转此笔不考虑提前，延期，逾期还款
                if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                    // 如果是直投还款
                    if (Validator.isNull(borrowRecover.getAccedeOrderId())) {
                        List<CreditRepayVO> creditRepayList = creditClient.selectCreditRepayList(borrowNid, tenderOrderId, 1, 0);
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                            BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                CreditRepayVO creditRepay = creditRepayList.get(j);
                                RepayCreditRepayBean creditRepayBean = new RepayCreditRepayBean();
                                assignCapital = creditRepay.getAssignCapital();// 承接本金
                                assignInterest = creditRepay.getAssignInterest();
                                // 计算用户实际获得的本息和
                                assignAccount = creditRepay.getAssignAccount();
                                //最后一笔兜底
                                if (borrowRecover.getCreditStatus() == 2 && j == creditRepayList.size() - 1) {
                                    assignManageFee = userManageFee;
                                    assignDelayInterest = userDelayInterest;
                                } else {
                                    // 按月计息，到期还本还息end
                                    if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByMonth(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 按天计息到期还本还息
                                    else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByDay(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 计算用户延期利息
                                    assignDelayInterest = UnnormalRepayUtils.delayRepayInterest(assignCapital, borrow.getBorrowApr(), delayDays);
                                }
                                BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                creditRepayBean.setAssignTotal(assignAccount.add(assignDelayInterest).add(assignManageFee));
                                creditRepayBean.setManageFee(assignManageFee);
                                creditRepayBean.setAdvanceStatus(2);
                                creditRepayBean.setDelayDays(delayDays);
                                creditRepayBean.setDelayInterest(assignDelayInterest);
                                creditRepayBeanList.add(creditRepayBean);
                                // 统计出让人还款金额
                                userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                userCapital = userCapital.subtract(assignCapital);
                                userInterest = userInterest.subtract(assignInterest);
                                userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
                                userDelayInterest = userDelayInterest.subtract(assignDelayInterest);// 提前还款利息
                                // 统计总额
                                repayTotal = repayTotal.add(assignAccount).add(assignDelayInterest).add(assignManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                repayCapital = repayCapital.add(assignCapital);
                                repayInterest = repayInterest.add(assignInterest);
                                repayManageFee = repayManageFee.add(assignManageFee);// 統計管理費
                                repayDelayInterest = repayDelayInterest.add(assignDelayInterest);
                            }
                            repayRecoverBean.setCreditRepayList(creditRepayBeanList);
                        }
                        if (borrowRecover.getCreditStatus() != 2) {
                            // 统计总额
                            repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userManageFee);// 统计总和本息+管理费
                            repayAccount = repayAccount.add(userAccount);// 统计总和本息
                            repayCapital = repayCapital.add(userCapital);
                            repayInterest = repayInterest.add(userInterest);
                            repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                            repayDelayInterest = repayDelayInterest.add(userDelayInterest);
                        }
                    } else {
                        // 计划还款
                        List<HjhDebtCreditRepayVO> creditRepayList = amTradeClient.selectHjhDebtCreditRepay(borrowNid, tenderOrderId, 1, borrowRecover.getRecoverStatus());
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                            BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                            //判断当前期是否全部承接
                            boolean overFlag = isOverUndertake(borrowRecover, null, null, false, 0);
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                HjhDebtCreditRepayVO creditRepay = creditRepayList.get(j);
                                HjhDebtCreditRepayBean creditRepayBean = new HjhDebtCreditRepayBean();
                                assignCapital = creditRepay.getRepayCapital();// 承接本金
                                assignInterest = creditRepay.getRepayInterest();
                                // 计算用户实际获得的本息和
                                assignAccount = creditRepay.getRepayAccount();
                                //最后一笔兜底
                                if (!overFlag && j == creditRepayList.size() - 1) {
                                    assignManageFee = userManageFee;
                                    assignDelayInterest = userDelayInterest;
                                } else {
                                    // 按月计息，到期还本还息end
                                    if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByMonth(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 按天计息到期还本还息
                                    else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByDay(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 计算用户延期利息
                                    assignDelayInterest = UnnormalRepayUtils.delayRepayInterest(assignCapital, borrow.getBorrowApr(), delayDays);
                                }
                                BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                creditRepayBean.setAssignTotal(assignAccount.add(assignDelayInterest).add(assignManageFee));
                                creditRepayBean.setManageFee(assignManageFee);
                                creditRepayBean.setAdvanceStatus(2);
                                creditRepayBean.setDelayDays(delayDays);
                                creditRepayBean.setRepayDelayInterest(assignDelayInterest);
                                creditRepayBeanList.add(creditRepayBean);
                                // 统计出让人还款金额
                                userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                userCapital = userCapital.subtract(assignCapital);
                                userInterest = userInterest.subtract(assignInterest);
                                userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
//                                userDelayInterest = userDelayInterest.subtract(assignDelayInterest);// 提前还款利息
                                // 统计总额
                                repayTotal = repayTotal.add(assignAccount).add(assignDelayInterest).add(assignManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                repayCapital = repayCapital.add(assignCapital);
                                repayInterest = repayInterest.add(assignInterest);
                                repayManageFee = repayManageFee.add(assignManageFee);// 統計管理費
                                repayDelayInterest = repayDelayInterest.add(assignDelayInterest);
                            }
                            repayRecoverBean.setHjhCreditRepayList(creditRepayBeanList);
                        }
                        if (borrowRecover.getCreditStatus() != 2) {
                            // 统计总额
                            repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userManageFee);// 统计总和本息+管理费
                            repayAccount = repayAccount.add(userAccount);// 统计总和本息
                            repayCapital = repayCapital.add(userCapital);
                            repayInterest = repayInterest.add(userInterest);
                            repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                            repayDelayInterest = repayDelayInterest.add(userDelayInterest);
                        }
                    }

                } else {
                    // 统计总额
                    repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userManageFee);// 统计总和本息+管理费
                    repayAccount = repayAccount.add(userAccount); // 统计本息总和
                    repayCapital = repayCapital.add(userCapital);
                    repayInterest = repayInterest.add(userInterest);
                    repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                    repayDelayInterest = repayDelayInterest.add(userDelayInterest);
                }
                // 用户延期还款
                BeanUtils.copyProperties(borrowRecover, repayRecoverBean);
                repayRecoverBean.setRecoverTotal(userAccount.add(userDelayInterest).add(userManageFee));
                repayRecoverBean.setRecoverAccount(userAccount);
                repayRecoverBean.setRecoverCapital(userCapital);
                repayRecoverBean.setRecoverInterest(userInterest);
                repayRecoverBean.setRecoverFee(userManageFee);
                repayRecoverBean.setAdvanceStatus(2);
                repayRecoverBean.setDelayInterest(userDelayInterest); // 延期利息
                repayRecoverBean.setDelayDays(delayDays);
                repayRecoverList.add(repayRecoverBean);
            }
            repay.setRecoverList(repayRecoverList);
        }
        repay.setRepayAccountAll(repayTotal);
        repay.setRepayAccount(repayAccount);
        repay.setRepayCapital(repayCapital);
        repay.setRepayInterest(repayInterest);
        repay.setRepayFee(repayManageFee);
        repay.setAdvanceStatus(2);
        repay.setDelayDays(delayDays);
        repay.setDelayInterest(repayDelayInterest);
    }

    /**
     * 统计单期还款用户逾期还款的总标
     *
     * @param repay
     * @param delayDays
     * @param lateDays
     * @throws ParseException
     */
    private void calculateRecoverTotalLate(RepayBean repay, BorrowVO borrow, int delayDays, int lateDays) throws ParseException {

        // 项目编号
        String borrowNid = borrow.getBorrowNid();
        // 还款方式
        String borrowStyle = borrow.getBorrowStyle();
        // 管理费率
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        // 初审时间
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : Integer.parseInt(borrow.getVerifyTime());
        // 项目总期数
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        BigDecimal repayDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
        BigDecimal repayOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
        List<BorrowRecoverVO> borrowRecovers = amTradeClient.selectBorrowRecoverByBorrowNid(borrowNid);
        if (borrowRecovers != null && borrowRecovers.size() > 0) {
            List<RepayRecoverBean> repayRecoverList = new ArrayList<RepayRecoverBean>();
            BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
            BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户還款管理費
            BigDecimal userDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
            BigDecimal userOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
            for (int i = 0; i < borrowRecovers.size(); i++) {
                BorrowRecoverVO borrowRecover = borrowRecovers.get(i);
                RepayRecoverBean repayRecoverBean = new RepayRecoverBean();
                String tenderOrderId = borrowRecover.getNid();// 投资订单号
                userAccount = borrowRecover.getRecoverAccount();// 获取未还款前用户能够获取的本息和
                userCapital = borrowRecover.getRecoverCapital();
                userInterest = borrowRecover.getRecoverInterest();
                // 计算用户逾期利息
                userOverdueInterest = UnnormalRepayUtils.overdueRepayOverdueInterest(userAccount, lateDays);
                if (StringUtils.isNotBlank(borrow.getPlanNid())) {//计划相关
                    BigDecimal planRate = new BigDecimal(borrow.getLateInterestRate());
                    userOverdueInterest = UnnormalRepayUtils.overduePlanRepayOverdueInterest(userAccount, lateDays, planRate);
                }
                // 计算用户延期利息
                userDelayInterest = UnnormalRepayUtils.overdueRepayDelayInterest(userCapital, borrow.getBorrowApr(), delayDays);
                // 获取应还款管理费
                userManageFee = borrowRecover.getRecoverFee();
                if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                    if (Validator.isNull(borrowRecover.getAccedeOrderId())) {
                        // 直投类项目还款
                        List<CreditRepayVO> creditRepayList = creditClient.selectCreditRepayList(borrowNid, tenderOrderId, 1, 0);
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                            BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                            BigDecimal assignOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                CreditRepayVO creditRepay = creditRepayList.get(j);
                                RepayCreditRepayBean creditRepayBean = new RepayCreditRepayBean();
                                assignAccount = creditRepay.getAssignAccount();// 承接本息
                                assignCapital = creditRepay.getAssignCapital();// 承接本金
                                assignInterest = creditRepay.getAssignInterest();
                                //最后一笔兜底
                                if (borrowRecover.getCreditStatus() == 2 && j == creditRepayList.size() - 1) {
                                    assignManageFee = userManageFee;
                                    // 计算用户逾期利息
                                    assignOverdueInterest = userOverdueInterest;
                                    // 计算用户延期利息
                                    assignDelayInterest = userDelayInterest;
                                } else {
                                    // 按月计息，到期还本还息end
                                    if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByMonth(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 按天计息到期还本还息
                                    else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByDay(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 计算用户逾期利息
                                    assignOverdueInterest = UnnormalRepayUtils.overdueRepayOverdueInterest(assignAccount, lateDays);
                                    if (StringUtils.isNotBlank(borrow.getPlanNid())) {//计划相关
                                        BigDecimal planRate = new BigDecimal(borrow.getLateInterestRate());
                                        userOverdueInterest = UnnormalRepayUtils.overduePlanRepayOverdueInterest(assignAccount, lateDays, planRate);
                                    }
                                    // 计算用户延期利息
                                    assignDelayInterest = UnnormalRepayUtils.overdueRepayDelayInterest(assignCapital, borrow.getBorrowApr(), delayDays);
                                }
                                BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                creditRepayBean.setAssignTotal(assignAccount.add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee));
                                creditRepayBean.setManageFee(assignManageFee);
                                creditRepayBean.setAdvanceStatus(3);
                                creditRepayBean.setDelayDays(delayDays);
                                creditRepayBean.setDelayInterest(assignDelayInterest);
                                creditRepayBean.setLateDays(lateDays);
                                creditRepayBean.setLateInterest(assignOverdueInterest);
                                creditRepayBeanList.add(creditRepayBean);
                                // 统计出让人还款金额
                                userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                userCapital = userCapital.subtract(assignCapital);
                                userInterest = userInterest.subtract(assignInterest);
                                userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
                                userDelayInterest = userDelayInterest.subtract(assignDelayInterest);// 提前还款利息
                                userOverdueInterest = userOverdueInterest.subtract(assignOverdueInterest);// 逾期利息
                                // 统计总额
                                repayTotal = repayTotal.add(assignAccount).add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                repayCapital = repayCapital.add(assignCapital);
                                repayInterest = repayInterest.add(assignInterest);
                                repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                repayDelayInterest = repayDelayInterest.add(assignDelayInterest);// 统计借款用户总延期利息
                                repayOverdueInterest = repayOverdueInterest.add(assignOverdueInterest);// 统计借款用户总逾期利息
                            }
                            repayRecoverBean.setCreditRepayList(creditRepayBeanList);
                        }
                        if (borrowRecover.getCreditStatus() != 2) {
                            // 统计总额
                            repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                            repayAccount = repayAccount.add(userAccount);// 统计总和本息
                            repayCapital = repayCapital.add(userCapital);
                            repayInterest = repayInterest.add(userInterest);
                            repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                            repayDelayInterest = repayDelayInterest.add(userDelayInterest);// 统计借款用户总延期利息
                            repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);// 统计借款用户总逾期利息
                        }
                    } else {
                        // 计划类债转还款
                        List<HjhDebtCreditRepayVO> creditRepayList = amTradeClient.selectHjhDebtCreditRepay(borrowNid, tenderOrderId, 1, borrowRecover.getRecoverStatus());
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                            BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                            BigDecimal assignOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
                            //判断当前期是否全部承接
                            boolean overFlag = isOverUndertake(borrowRecover, null, null, false, 0);
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                HjhDebtCreditRepayVO creditRepay = creditRepayList.get(j);
                                HjhDebtCreditRepayBean creditRepayBean = new HjhDebtCreditRepayBean();
                                assignAccount = creditRepay.getRepayAccount();// 承接本息
                                assignCapital = creditRepay.getRepayCapital();// 承接本金
                                assignInterest = creditRepay.getRepayInterest();
                                //最后一笔兜底
                                if (!overFlag && j == creditRepayList.size() - 1) {
                                    assignManageFee = userManageFee;
                                    // 计算用户逾期利息
                                    assignOverdueInterest = userOverdueInterest;
                                    // 计算用户延期利息
                                    assignDelayInterest = userDelayInterest;
                                } else {
                                    // 按月计息，到期还本还息end
                                    if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByMonth(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 按天计息到期还本还息
                                    else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                        assignManageFee = AccountManagementFeeUtils.getDueAccountManagementFeeByDay(assignCapital, feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                                    }
                                    // 计算用户逾期利息
                                    assignOverdueInterest = UnnormalRepayUtils.overdueRepayOverdueInterest(assignAccount, lateDays);
                                    if (StringUtils.isNotBlank(borrow.getPlanNid())) {//计划相关
                                        BigDecimal planRate = new BigDecimal(borrow.getLateInterestRate());
                                        userOverdueInterest = UnnormalRepayUtils.overduePlanRepayOverdueInterest(assignAccount, lateDays, planRate);
                                    }
                                    // 计算用户延期利息
                                    assignDelayInterest = UnnormalRepayUtils.overdueRepayDelayInterest(assignCapital, borrow.getBorrowApr(), delayDays);
                                }
                                BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                creditRepayBean.setAssignTotal(assignAccount.add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee));
                                creditRepayBean.setManageFee(assignManageFee);
                                creditRepayBean.setAdvanceStatus(3);
                                creditRepayBean.setDelayDays(delayDays);
                                creditRepayBean.setRepayDelayInterest(assignDelayInterest);
                                creditRepayBean.setLateDays(lateDays);
                                creditRepayBean.setRepayLateInterest(assignOverdueInterest);
                                creditRepayBeanList.add(creditRepayBean);
                                // 统计出让人还款金额
                                userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                userCapital = userCapital.subtract(assignCapital);
                                userInterest = userInterest.subtract(assignInterest);
                                userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
//                                userDelayInterest = userDelayInterest.subtract(assignDelayInterest);// 提前还款利息
//                                userOverdueInterest = userOverdueInterest.subtract(assignOverdueInterest);// 逾期利息
                                // 统计总额
                                repayTotal = repayTotal.add(assignAccount).add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                repayCapital = repayCapital.add(assignCapital);
                                repayInterest = repayInterest.add(assignInterest);
                                repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                repayDelayInterest = repayDelayInterest.add(assignDelayInterest);// 统计借款用户总延期利息
                                repayOverdueInterest = repayOverdueInterest.add(assignOverdueInterest);// 统计借款用户总逾期利息
                            }
                            repayRecoverBean.setHjhCreditRepayList(creditRepayBeanList);
                        }
                        if (borrowRecover.getCreditStatus() != 2) {
                            // 统计总额
                            repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                            repayAccount = repayAccount.add(userAccount);// 统计总和本息
                            repayCapital = repayCapital.add(userCapital);
                            repayInterest = repayInterest.add(userInterest);
                            repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                            repayDelayInterest = repayDelayInterest.add(userDelayInterest);// 统计借款用户总延期利息
                            repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);// 统计借款用户总逾期利息
                        }
                    }
                } else {
                    // 统计总额
                    repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                    repayAccount = repayAccount.add(userAccount);// 统计总和本息
                    repayCapital = repayCapital.add(userCapital);
                    repayInterest = repayInterest.add(userInterest);
                    repayManageFee = repayManageFee.add(userManageFee);// 管理费
                    repayDelayInterest = repayDelayInterest.add(userDelayInterest);// 统计借款用户总延期利息
                    repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);// 统计借款用户总逾期利息
                }
                // 延期还款利息
                BeanUtils.copyProperties(borrowRecover, repayRecoverBean);
                // 用户延期还款
                repayRecoverBean.setRecoverTotal(userAccount.add(userDelayInterest).add(userOverdueInterest).add(userManageFee));
                repayRecoverBean.setRecoverAccount(userAccount);
                repayRecoverBean.setRecoverCapital(userCapital);
                repayRecoverBean.setRecoverInterest(userInterest);
                repayRecoverBean.setRecoverFee(userManageFee);
                repayRecoverBean.setDelayInterest(userDelayInterest);
                repayRecoverBean.setLateInterest(userOverdueInterest);
                repayRecoverBean.setDelayDays(delayDays);
                repayRecoverBean.setLateDays(lateDays);
                repayRecoverBean.setAdvanceStatus(3);
                repayRecoverList.add(repayRecoverBean);
            }
            repay.setRecoverList(repayRecoverList);
        }
        repay.setRepayAccountAll(repayTotal);
        repay.setRepayAccount(repayAccount);
        repay.setRepayCapital(repayCapital);
        repay.setRepayInterest(repayInterest);
        repay.setRepayFee(repayManageFee);
        repay.setDelayDays(delayDays);
        repay.setDelayInterest(repayDelayInterest);
        repay.setLateDays(lateDays);
        repay.setLateInterest(repayOverdueInterest);
        repay.setAdvanceStatus(3);
    }

    /**
     * 计算多期的总的还款信息
     *
     * @throws ParseException
     */
    public RepayBean calculateRepayByTerm(BorrowVO borrow) throws ParseException {

        RepayBean repay = new RepayBean();
        // 获取还款总表数据
        BorrowRepayVO borrowRepay = borrowRepayClient.getBorrowRepay(borrow.getBorrowNid());
        // 判断用户的余额是否足够还款
        if (borrowRepay != null) {
            // 获取相应的还款信息
            BeanUtils.copyProperties(borrowRepay, repay);
            repay.setBorrowPeriod(String.valueOf(borrow.getBorrowPeriod()));
            int period = borrow.getBorrowPeriod() - repay.getRepayPeriod() + 1;
            this.calculateRepayPlan(repay, borrow, period);
        }
        return repay;
    }

    /***
     * 计算用户分期还款本期应还金额
     *
     * @param repay
     * @throws ParseException
     */
    private BigDecimal calculateRepayPlan(RepayBean repay, BorrowVO borrow, int period) throws ParseException {

        List<RepayDetailBean> borrowRepayPlanDeails = new ArrayList<RepayDetailBean>();
        List<BorrowRepayPlanVO> borrowRepayPlans = borrowRepayPlanClient.getBorrowRepayPlansByBorrowNid(borrow.getBorrowNid());
        BigDecimal repayAccountAll = new BigDecimal("0");
        if (borrowRepayPlans != null && borrowRepayPlans.size() > 0) {
            // 用户实际还款额
            for (int i = 0; i < borrowRepayPlans.size(); i++) {
                RepayDetailBean repayPlanDetail = new RepayDetailBean();
                BorrowRepayPlanVO borrowRepayPlan = borrowRepayPlans.get(i);
                if (period == borrowRepayPlan.getRepayPeriod()) {
                    String repayTimeStart = null;
                    if (i == 0) {
                        repayTimeStart = String.valueOf(borrowRepayPlan.getAddTime());
                    } else {
                        repayTimeStart = String.valueOf(borrowRepayPlans.get(i - 1).getRepayTime());
                    }
                    // 计算还款期的数据
                    BeanUtils.copyProperties(borrowRepayPlan, repayPlanDetail);
                    this.calculateRecoverPlan(repayPlanDetail, borrow, period, repayTimeStart);
                    borrowRepayPlanDeails.add(repayPlanDetail);
                    repay.setRepayAccountAll(repayPlanDetail.getRepayAccountAll());
                    repay.setRepayAccount(repayPlanDetail.getRepayAccount());
                    repay.setRepayCapital(repayPlanDetail.getRepayCapital());
                    repay.setRepayInterest(repayPlanDetail.getRepayInterest());
                    repay.setRepayFee(repayPlanDetail.getRepayFee());
                    repay.setChargeDays(repayPlanDetail.getChargeDays());
                    repay.setChargeInterest(repayPlanDetail.getChargeInterest());
                    repay.setDelayDays(repayPlanDetail.getDelayDays());
                    repay.setDelayInterest(repayPlanDetail.getDelayInterest());
                    repay.setLateDays(repayPlanDetail.getLateDays());
                    repay.setLateInterest(repayPlanDetail.getLateInterest());
                    repayAccountAll = repayPlanDetail.getRepayAccountAll();
                } else {
                    BeanUtils.copyProperties(borrowRepayPlan, repayPlanDetail);
                    this.calculateRecoverPlan(repayPlanDetail, borrow);
                    borrowRepayPlanDeails.add(repayPlanDetail);
                }
            }
            repay.setRepayPlanList(borrowRepayPlanDeails);
        }
        return repayAccountAll;
    }

    /**
     * 统计分期还款用户还款信息
     *
     * @throws ParseException
     */
    private void calculateRecoverPlan(RepayDetailBean borrowRepayPlan, BorrowVO borrow) throws ParseException {

        // 项目编号
        String borrowNid = borrow.getBorrowNid();
        // 还款方式
        String borrowStyle = borrow.getBorrowStyle();
        // 管理费率
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        // 初审时间
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : Integer.parseInt(borrow.getVerifyTime());
        // 项目总期数
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
        // 还款期数
        int repayPeriod = borrowRepayPlan.getRepayPeriod();
        List<BorrowRecoverVO> borrowRecoverList = amTradeClient.selectBorrowRecoverByBorrowNid(borrow.getBorrowNid());
        List<BorrowRecoverPlanVO> borrowRecoverPlans = amTradeClient.selectRecoverPlan(borrow.getBorrowNid(),borrowRepayPlan.getRepayPeriod());
        List<RepayRecoverPlanBean> repayRecoverPlanList = new ArrayList<RepayRecoverPlanBean>();
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayChargeInterest = BigDecimal.ZERO;// 提前还款利息
        BigDecimal repayDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
        BigDecimal repayOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        if (borrowRecoverList != null && borrowRecoverList.size() > 0) {
            for (int i = 0; i < borrowRecoverList.size(); i++) {
                BorrowRecoverVO borrowRecover = borrowRecoverList.get(i);
                String recoverNid = borrowRecover.getNid();// 投资订单号
                int recoverUserId = borrowRecover.getUserId();// 投资用户userId
                if (borrowRecoverPlans != null && borrowRecoverPlans.size() > 0) {
                    BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                    BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userChargeInterest = BigDecimal.ZERO;// 计算用户提前还款利息
                    BigDecimal userDelayInterest = BigDecimal.ZERO;// 计算用户延期还款利息
                    BigDecimal userOverdueInterest = BigDecimal.ZERO;// 计算用户逾期还款利息
                    BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                    for (int j = 0; j < borrowRecoverPlans.size(); j++) {
                        BorrowRecoverPlanVO borrowRecoverPlan = borrowRecoverPlans.get(j);
                        String recoverPlanNid = borrowRecoverPlan.getNid();// 投资订单号
                        int recoverPlanUserId = borrowRecoverPlan.getUserId();// 投资用户userId
                        if (recoverPlanNid.equals(recoverNid) && recoverUserId == recoverPlanUserId) {
                            RepayRecoverPlanBean repayRecoverPlanBean = new RepayRecoverPlanBean();
                            userAccount = borrowRecoverPlan.getRecoverAccount();// 获取应还款本息
                            userCapital = borrowRecoverPlan.getRecoverCapital();
                            userInterest = borrowRecoverPlan.getRecoverInterest();
                            userChargeInterest = borrowRecoverPlan.getChargeInterest();
                            userManageFee = borrowRecoverPlan.getRecoverFee();// 获取应还款管理费
                            userOverdueInterest = borrowRecoverPlan.getLateInterest();
                            userDelayInterest = borrowRecoverPlan.getDelayInterest();
                            // 如果发生债转
                            if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                                if(Validator.isNull(borrowRecover.getAccedeOrderId())){
                                    // 直投类项目债转还款
                                    List<CreditRepayVO> creditRepayList = creditClient.selectCreditRepayList(borrowNid, recoverNid, repayPeriod, -1);
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
                                        BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                                        BigDecimal assignOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            CreditRepayVO creditRepay = creditRepayList.get(k);
                                            RepayCreditRepayBean creditRepayBean = new RepayCreditRepayBean();
                                            String assignNid = creditRepay.getAssignNid();// 承接订单号
                                            CreditTenderVO creditTender = amTradeClient.selectCreditTender(assignNid).get(0);// 查询相应的承接记录
                                            assignAccount = creditRepay.getAssignAccount();// 承接本息
                                            assignCapital = creditRepay.getAssignCapital();// 用户实际还款本本金
                                            assignInterest = creditRepay.getAssignInterest();
                                            assignChargeInterest = creditRepay.getChargeInterest();
                                            assignOverdueInterest = creditRepay.getLateInterest(); // 计算用户逾期利息
                                            assignDelayInterest = creditRepay.getDelayInterest();// 计算用户延期利息
                                            if (borrowRecoverPlan.getCreditStatus() == 2 && k == creditRepayList.size() - 1) {
                                                assignManageFee = userManageFee;
                                            } else {
                                                if (creditRepay.getStatus() == 1) {
                                                    assignManageFee = creditRepay.getManageFee();
                                                } else {
                                                    // 等额本息month、等额本金principal
                                                    if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
                                                        if (repayPeriod == borrowPeriod.intValue()) {
                                                            assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 1,
                                                                    borrow.getAccount(), borrowPeriod, borrowVerifyTime);
                                                        } else {
                                                            assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 0,
                                                                    borrow.getAccount(), borrowPeriod, borrowVerifyTime);
                                                        }
                                                    }
                                                    // 先息后本endmonth
                                                    else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
                                                        if (repayPeriod == borrowPeriod.intValue()) {
                                                            assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                    borrowPeriod, borrowPeriod, differentialRate, 1, borrowVerifyTime);
                                                        } else {
                                                            assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                    borrowPeriod, borrowPeriod, differentialRate, 0, borrowVerifyTime);
                                                        }
                                                    }
                                                }
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.add(assignChargeInterest).add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee));
                                            creditRepayBean.setManageFee(assignManageFee);
                                            creditRepayBeanList.add(creditRepayBean);
                                            // 统计出让人还款金额
                                            userAccount = userAccount.subtract(assignAccount);
                                            userCapital = userCapital.subtract(assignCapital);
                                            userInterest = userInterest.subtract(assignInterest);
                                            userChargeInterest = userChargeInterest.subtract(assignChargeInterest);// 提前还款利息
                                            userDelayInterest = userDelayInterest.subtract(assignDelayInterest);// 提前还款利息
                                            userOverdueInterest = userOverdueInterest.subtract(assignOverdueInterest);// 逾期利息
                                            userManageFee = userManageFee.subtract(assignManageFee);
                                            // 统计总额
                                            repayTotal = repayTotal.add(assignAccount).add(assignChargeInterest).add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee);// 统计总和本息+管理费
                                            repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                            repayCapital = repayCapital.add(assignCapital);
                                            repayInterest = repayInterest.add(assignInterest);
                                            repayChargeInterest = repayChargeInterest.add(assignChargeInterest);// 统计提前还款减少的利息
                                            repayDelayInterest = repayDelayInterest.add(assignDelayInterest);// 统计借款用户总延期利息
                                            repayOverdueInterest = repayOverdueInterest.add(assignOverdueInterest);// 统计借款用户总逾期利息
                                            repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                        }
                                        repayRecoverPlanBean.setCreditRepayList(creditRepayBeanList);
                                    }
                                    if (borrowRecoverPlan.getCreditStatus() != 2) {
                                        // 统计总额
                                        repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userDelayInterest).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayChargeInterest = repayChargeInterest.add(userChargeInterest);// 统计提前还款减少的利息
                                        repayDelayInterest = repayDelayInterest.add(userDelayInterest);// 统计借款用户总延期利息
                                        repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);// 统计借款用户总逾期利息
                                        repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                    }
                                }else{
                                    // 计划类项目债转还款
                                    List<HjhDebtCreditRepayVO> creditRepayList = amTradeClient.selectHjhDebtCreditRepay(borrowNid, recoverNid, repayPeriod, borrowRecoverPlan.getRecoverStatus());
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
                                        BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                                        BigDecimal assignOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                        BigDecimal sumCreditAccount = BigDecimal.ZERO;//总承接金额
                                        int hjhFlag = 0;
                                        for (HjhDebtCreditRepayVO hjhDebtCreditRepayBean : creditRepayList) {
                                            hjhFlag++;
                                            sumCreditAccount = sumCreditAccount.add(hjhDebtCreditRepayBean.getRepayAccount());
                                        }
                                        //判断当前期是否全部承接
                                        boolean overFlag = isOverUndertake(borrowRecover,borrowRecoverPlan.getRecoverAccount(),sumCreditAccount,true,hjhFlag);
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            HjhDebtCreditRepayVO creditRepay = creditRepayList.get(k);
                                            HjhDebtCreditRepayBean creditRepayBean = new HjhDebtCreditRepayBean();
                                            String assignNid = creditRepay.getAssignOrderId();// 承接订单号
                                            HjhDebtCreditTenderVO creditTender = hjhDebtCreditClient.selectHjhCreditTenderListByAssignOrderId(assignNid).get(0);// 查询相应的承接记录
                                            assignAccount = creditRepay.getRepayAccount();// 承接本息
                                            assignCapital = creditRepay.getRepayCapital();// 用户实际还款本本金
                                            assignInterest = creditRepay.getRepayInterest();
                                            assignChargeInterest = creditRepay.getRepayAdvanceInterest();
                                            assignOverdueInterest = creditRepay.getRepayLateInterest(); // 计算用户逾期利息
                                            assignDelayInterest = creditRepay.getRepayDelayInterest();// 计算用户延期利息
                                            if (!overFlag && k == creditRepayList.size() - 1) {
                                                assignManageFee = userManageFee;
                                            } else {
                                                if (creditRepay.getRepayStatus() == 1) {
                                                    assignManageFee = creditRepay.getManageFee();
                                                } else {
                                                    // 等额本息month、等额本金principal
                                                    if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
                                                        if (repayPeriod == borrowPeriod.intValue()) {
                                                            assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 1,
                                                                    borrowRecover.getRecoverCapital(), borrowPeriod, borrowVerifyTime);
                                                        } else {
                                                            assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 0,
                                                                    borrowRecover.getRecoverCapital(), borrowPeriod, borrowVerifyTime);
                                                        }
                                                    }
                                                    // 先息后本endmonth
                                                    else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
                                                        if (repayPeriod == borrowPeriod.intValue()) {
                                                            assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                    borrowPeriod, borrowPeriod, differentialRate, 1, borrowVerifyTime);
                                                        } else {
                                                            assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                    borrowPeriod, borrowPeriod, differentialRate, 0, borrowVerifyTime);
                                                        }
                                                    }
                                                }
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.add(assignChargeInterest).add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee));
                                            creditRepayBean.setManageFee(assignManageFee);
                                            creditRepayBeanList.add(creditRepayBean);
                                            // 统计出让人还款金额
                                            userAccount = userAccount.subtract(assignAccount);
                                            userCapital = userCapital.subtract(assignCapital);
                                            userInterest = userInterest.subtract(assignInterest);
//                                            userChargeInterest = userChargeInterest.subtract(assignChargeInterest);// 提前还款利息
//                                            userDelayInterest = userDelayInterest.subtract(assignDelayInterest);// 提前还款利息
//                                            userOverdueInterest = userOverdueInterest.subtract(assignOverdueInterest);// 逾期利息
                                            userManageFee = userManageFee.subtract(assignManageFee);
                                            // 统计总额
                                            repayTotal = repayTotal.add(assignAccount).add(assignChargeInterest).add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee);// 统计总和本息+管理费
                                            repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                            repayCapital = repayCapital.add(assignCapital);
                                            repayInterest = repayInterest.add(assignInterest);
                                            repayChargeInterest = repayChargeInterest.add(assignChargeInterest);// 统计提前还款减少的利息
                                            repayDelayInterest = repayDelayInterest.add(assignDelayInterest);// 统计借款用户总延期利息
                                            repayOverdueInterest = repayOverdueInterest.add(assignOverdueInterest);// 统计借款用户总逾期利息
                                            repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                        }
                                        repayRecoverPlanBean.setHjhCreditRepayList(creditRepayBeanList);
                                    }
                                    if (borrowRecoverPlan.getCreditStatus() != 2) {
                                        // 统计总额
                                        repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userDelayInterest).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayChargeInterest = repayChargeInterest.add(userChargeInterest);// 统计提前还款减少的利息
                                        repayDelayInterest = repayDelayInterest.add(userDelayInterest);// 统计借款用户总延期利息
                                        repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);// 统计借款用户总逾期利息
                                        repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                    }
                                }
                            } else {
                                // 统计总和
                                repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userDelayInterest).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(userAccount); // 统计本息总和
                                repayCapital = repayCapital.add(userCapital);
                                repayInterest = repayInterest.add(userInterest);
                                repayChargeInterest = repayChargeInterest.add(userChargeInterest);// 统计提前还款减少的利息
                                repayDelayInterest = repayDelayInterest.add(userDelayInterest);// 统计借款用户总延期利息
                                repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);// 统计借款用户总逾期利息
                                repayManageFee = repayManageFee.add(userManageFee);// 管理费
                            }
                            BeanUtils.copyProperties(borrowRecoverPlan, repayRecoverPlanBean);
                            repayRecoverPlanBean.setRecoverTotal(userAccount.add(userManageFee));
                            repayRecoverPlanBean.setRecoverAccount(userAccount);
                            repayRecoverPlanBean.setRecoverCapital(userCapital);
                            repayRecoverPlanBean.setRecoverInterest(userInterest);
                            repayRecoverPlanBean.setChargeInterest(userChargeInterest);
                            repayRecoverPlanBean.setDelayInterest(userDelayInterest);
                            repayRecoverPlanBean.setLateInterest(userOverdueInterest);
                            repayRecoverPlanBean.setRecoverFee(userManageFee);
                            repayRecoverPlanList.add(repayRecoverPlanBean);
                        }
                    }
                }
            }
            borrowRepayPlan.setRecoverPlanList(repayRecoverPlanList);
        }
        borrowRepayPlan.setRepayAccountAll(repayTotal);
        borrowRepayPlan.setRepayAccount(repayAccount);
        borrowRepayPlan.setRepayCapital(repayCapital);
        borrowRepayPlan.setRepayInterest(repayInterest);
        borrowRepayPlan.setChargeInterest(repayChargeInterest);
        borrowRepayPlan.setDelayInterest(repayDelayInterest);
        borrowRepayPlan.setLateInterest(repayOverdueInterest);
        borrowRepayPlan.setRepayFee(repayManageFee);
    }

    /***
     *计算用户分期还款本期应还金额
     * @throws ParseException
     */
    private void calculateRecoverPlan(RepayDetailBean borrowRepayPlan, BorrowVO borrow, int period, String repayTimeStart) throws ParseException {

        int delayDays = borrowRepayPlan.getDelayDays().intValue();
        String repayTimeStr = String.valueOf(borrowRepayPlan.getRepayTime());
        int time = GetDate.getNowTime10();
        // 用户计划还款时间
        String repayTime = GetDate.getDateTimeMyTimeInMillis(Integer.parseInt(repayTimeStr));
        // 用户实际还款时间
        String RepayTime = GetDate.getDateTimeMyTimeInMillis(time);
        // 获取实际还款同计划还款时间的时间差
        int distanceDays = GetDate.daysBetween(RepayTime, repayTime);
        if (distanceDays < 0) {// 用户延期或者逾期了
            int lateDays = delayDays + distanceDays;
            if (lateDays >= 0) {// 用户延期还款
                delayDays = -distanceDays;
                this.calculateRecoverPlanDelay(borrowRepayPlan, borrow, delayDays);
            } else {// 用户逾期还款
                lateDays = -lateDays;
                Integer lateFreeDays = borrow.getLateFreeDays();
                if (lateFreeDays >= lateDays) {//在免息期以内,正常还款
                    this.calculateRecoverPlan(borrowRepayPlan, borrow, delayDays);
                } else {//过了免息期,罚免息期外的利息
                    lateDays = lateDays - lateFreeDays;
                    this.calculateRecoverPlanLate(borrowRepayPlan, borrow, delayDays, lateDays);
                }
            }
        } else {// 用户正常或者提前还款
            // 获取提前还款的阀值
            String repayAdvanceDay = amTradeClient.getConfigByCode("REPAY_ADVANCE_TIME").getConfigValue();
            int advanceDays = distanceDays;
            //如果是融通宝项目,不判断提前还款的阙值 add by cwyang 2017-6-14
            int projectType = borrow.getProjectType();
            if (13 == projectType) {
                repayAdvanceDay = "0";
            }
            if (Integer.parseInt(repayAdvanceDay) < advanceDays) {// 用户提前还款
                // 计算用户实际还款总额
                this.calculateRecoverPlanAdvance(borrowRepayPlan, borrow, advanceDays, repayTimeStart);
            } else {// 用户正常还款
                // 计算用户实际还款总额
                this.calculateRecoverPlan(borrowRepayPlan, borrow, advanceDays);
            }
        }
    }

    /**
     * 统计分期还款用户提前还款的总标
     *
     * @return
     * @throws ParseException
     */
    private void calculateRecoverPlanAdvance(RepayDetailBean borrowRepayPlan, BorrowVO borrow, int advanceDays, String repayTimeStart) throws ParseException {

        String borrowNid = borrow.getBorrowNid();// 项目编号
        String borrowStyle = borrow.getBorrowStyle(); // 还款方式
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());// 管理费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());// 差异费率
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : Integer.parseInt(borrow.getVerifyTime());// 初审时间
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod(); // 项目总期数
        int repayPeriod = borrowRepayPlan.getRepayPeriod();// 还款期数
        List<BorrowRecoverVO> borrowRecoverList = amTradeClient.selectBorrowRecoverByBorrowNid(borrow.getBorrowNid());
        List<BorrowRecoverPlanVO> borrowRecoverPlans = amTradeClient.selectRecoverPlan(borrow.getBorrowNid(), repayPeriod);
        List<RepayRecoverPlanBean> repayRecoverPlanList = new ArrayList<RepayRecoverPlanBean>();
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        BigDecimal repayChargeInterest = BigDecimal.ZERO;// 提前还款利息
        if (borrowRecoverList != null && borrowRecoverList.size() > 0) {
            for (int i = 0; i < borrowRecoverList.size(); i++) {
                BorrowRecoverVO borrowRecover = borrowRecoverList.get(i);
                String recoverNid = borrowRecover.getNid();// 投资订单号
                int recoverUserId = borrowRecover.getUserId();// 投资用户userId
                if (borrowRecoverPlans != null && borrowRecoverPlans.size() > 0) {
                    BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                    BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                    BigDecimal userChargeInterest = BigDecimal.ZERO;// 计算用户提前还款利息
                    for (int j = 0; j < borrowRecoverPlans.size(); j++) {
                        BorrowRecoverPlanVO borrowRecoverPlan = borrowRecoverPlans.get(j);
                        String recoverPlanNid = borrowRecoverPlan.getNid();// 投资订单号
                        int recoverPlanUserId = borrowRecoverPlan.getUserId();// 投资用户userId
                        if (recoverPlanNid.equals(recoverNid) && recoverUserId == recoverPlanUserId) {
                            RepayRecoverPlanBean repayRecoverPlanBean = new RepayRecoverPlanBean();
                            String recoverTime = GetDate.getDateTimeMyTimeInMillis(borrowRecoverPlan.getRecoverTime());
                            String repayStartTime = GetDate.getDateTimeMyTimeInMillis(Integer.parseInt(repayTimeStart));
                            int totalDays = GetDate.daysBetween(repayStartTime, recoverTime);// 获取这两个时间之间有多少天
                            // 获取未还款前用户能够获取的本息和
                            userAccount = borrowRecoverPlan.getRecoverAccount();
                            // 获取用户投资项目分期后的投资本金
                            userCapital = borrowRecoverPlan.getRecoverCapital();
                            userInterest = borrowRecoverPlan.getRecoverInterest();
                            // 如果项目类型为融通宝，调用新的提前还款利息计算公司
                            if (borrow.getProjectType() == 13) {
                                // 提前还款不应该大于本次计息时间
                                if (totalDays < advanceDays) {
                                    // 用户提前还款减少的利息
                                    userChargeInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(userCapital, borrow.getBorrowApr(), totalDays);
                                } else {
                                    // 用户提前还款减少的利息
                                    userChargeInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(userCapital, borrow.getBorrowApr(), advanceDays);
                                }
                            } else {
                                boolean isStyle = CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
                                if (isStyle) {
                                    if (advanceDays >= 30) {
                                        userChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(userInterest, totalDays);
                                    } else {
                                        userChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(userInterest, advanceDays);
                                    }
                                } else {
                                    // 提前还款不应该大于本次计息时间
                                    if (totalDays < advanceDays) {
                                        // 用户提前还款减少的利息
                                        userChargeInterest = UnnormalRepayUtils.aheadRepayChargeInterest(userCapital, borrow.getBorrowApr(), totalDays);
                                    } else {
                                        // 用户提前还款减少的利息
                                        userChargeInterest = UnnormalRepayUtils.aheadRepayChargeInterest(userCapital, borrow.getBorrowApr(), advanceDays);
                                    }
                                }

                            }
                            // 项目提前还款时，提前还款利息不得大于应还款利息，需求变更
                            if (userChargeInterest.compareTo(userInterest) > 0) {
                                userChargeInterest = userInterest;
                            }
                            userManageFee = borrowRecoverPlan.getRecoverFee();// 获取应还款管理费
                            if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                                if (Validator.isNull(borrowRecover.getAccedeOrderId())) {
                                    List<CreditRepayVO> creditRepayList = creditClient.selectCreditRepayList(borrowNid, recoverNid, repayPeriod, 0);
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            CreditRepayVO creditRepay = creditRepayList.get(k);
                                            RepayCreditRepayBean creditRepayBean = new RepayCreditRepayBean();
                                            String assignNid = creditRepay.getAssignNid();// 承接订单号
                                            CreditTenderVO creditTender = amTradeClient.selectCreditTender(assignNid).get(0);// 查询相应的承接记录
                                            assignAccount = creditRepay.getAssignAccount();
                                            assignCapital = creditRepay.getAssignCapital();// 用户实际还款本本金
                                            assignInterest = creditRepay.getAssignInterest();
                                            //最后一笔兜底
                                            if (borrowRecoverPlan.getCreditStatus() == 2 && k == creditRepayList.size() - 1) {
                                                assignManageFee = userManageFee;
                                                assignChargeInterest = userChargeInterest;
                                            } else {
                                                // 等额本息month、等额本金principal
                                                if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 1,
                                                                borrow.getAccount(), borrowPeriod, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 0,
                                                                borrow.getAccount(), borrowPeriod, borrowVerifyTime);
                                                    }
                                                }
                                                // 先息后本endmonth
                                                else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 1, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 0, borrowVerifyTime);
                                                    }
                                                }
                                                // 如果项目类型为融通宝，调用新的提前还款利息计算公司
                                                if (borrow.getProjectType() == 13) {
                                                    // 提前还款不应该大于本次计息时间
                                                    if (totalDays < advanceDays) {
                                                        // 用户提前还款减少的利息
                                                        assignChargeInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(assignCapital, borrow.getBorrowApr(), totalDays);
                                                    } else {
                                                        // 用户提前还款减少的利息
                                                        assignChargeInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(assignCapital, borrow.getBorrowApr(), advanceDays);
                                                    }
                                                } else {
                                                    boolean isStyle = CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
                                                    if (isStyle) {
                                                        if (advanceDays >= 30) {
                                                            assignChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(assignInterest, totalDays);
                                                        } else {
                                                            assignChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(assignInterest, advanceDays);
                                                        }
                                                    } else {
                                                        // 提前还款不应该大于本次计息时间
                                                        if (totalDays < advanceDays) {
                                                            // 用户提前还款减少的利息
                                                            assignChargeInterest = UnnormalRepayUtils.aheadRepayChargeInterest(assignCapital, borrow.getBorrowApr(), totalDays);
                                                        } else {
                                                            // 用户提前还款减少的利息
                                                            assignChargeInterest = UnnormalRepayUtils.aheadRepayChargeInterest(assignCapital, borrow.getBorrowApr(), advanceDays);
                                                        }
                                                    }
                                                }
                                            }
                                            // 项目提前还款时，提前还款利息不得大于应还款利息，需求变更
                                            if (assignChargeInterest.compareTo(assignInterest) > 0) {
                                                assignChargeInterest = assignInterest;
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.subtract(assignChargeInterest).add(assignManageFee));
                                            creditRepayBean.setManageFee(assignManageFee);
                                            creditRepayBean.setAdvanceStatus(1);
                                            creditRepayBean.setChargeInterest(assignChargeInterest.multiply(new BigDecimal(-1)));
                                            creditRepayBean.setChargeDays(advanceDays);
                                            creditRepayBeanList.add(creditRepayBean);
                                            // 统计出让人还款金额
                                            userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                            userCapital = userCapital.subtract(assignCapital);
                                            userInterest = userInterest.subtract(assignInterest);
                                            userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
                                            userChargeInterest = userChargeInterest.subtract(assignChargeInterest);// 提前还款利息
                                            // 统计总额
                                            repayTotal = repayTotal.add(assignAccount).subtract(assignChargeInterest).add(assignManageFee);// 统计总和本息+管理费
                                            repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                            repayCapital = repayCapital.add(assignCapital);
                                            repayInterest = repayInterest.add(assignInterest);
                                            repayManageFee = repayManageFee.add(assignManageFee);// 統計管理費
                                            repayChargeInterest = repayChargeInterest.add(assignChargeInterest);// 统计提前还款减少的利息
                                        }
                                        repayRecoverPlanBean.setCreditRepayList(creditRepayBeanList);
                                    }
                                    if (borrowRecoverPlan.getCreditStatus() != 2) {
                                        // 重新计算债转后出让人剩余金额的提前减息金额
                                        boolean isStyle = CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
                                        if (isStyle) {
                                            if (advanceDays >= 30) {
                                                userChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(userInterest, totalDays);
                                            } else {
                                                userChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(userInterest, advanceDays);
                                            }
                                        }
                                        // 统计总额
                                        repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                        repayChargeInterest = repayChargeInterest.add(userChargeInterest);
                                    }
                                } else {
                                    // 计划类项目还款
                                    List<HjhDebtCreditRepayVO> creditRepayList = amTradeClient.selectHjhDebtCreditRepay(borrowNid, recoverNid, repayPeriod, borrowRecoverPlan.getRecoverStatus());
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                        BigDecimal sumCreditAccount = BigDecimal.ZERO;//总承接金额
                                        int hjhFlag = 0;
                                        for (HjhDebtCreditRepayVO hjhDebtCreditRepayBean : creditRepayList) {
                                            hjhFlag++;
                                            sumCreditAccount = sumCreditAccount.add(hjhDebtCreditRepayBean.getRepayAccount());
                                        }
                                        //判断当前期是否全部承接
                                        boolean overFlag = isOverUndertake(borrowRecover, borrowRecoverPlan.getRecoverAccount(), sumCreditAccount, true, hjhFlag);
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            HjhDebtCreditRepayVO creditRepay = creditRepayList.get(k);
                                            HjhDebtCreditRepayBean creditRepayBean = new HjhDebtCreditRepayBean();
                                            // 承接订单号
                                            String assignNid = creditRepay.getAssignOrderId();
                                            // 查询相应的承接记录
                                            HjhDebtCreditTenderVO creditTender = hjhDebtCreditClient.selectHjhCreditTenderListByAssignOrderId(assignNid).get(0);
                                            assignAccount = creditRepay.getRepayAccount();
                                            assignCapital = creditRepay.getRepayCapital();// 用户实际还款本本金
                                            assignInterest = creditRepay.getRepayInterest();
                                            //最后一笔兜底
                                            if (!overFlag && k == creditRepayList.size() - 1) {
                                                assignManageFee = userManageFee;
                                                assignChargeInterest = userChargeInterest;
                                            } else {
                                                // 等额本息month、等额本金principal
                                                if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 1,
                                                                borrowRecover.getRecoverCapital(), borrowPeriod, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 0,
                                                                borrowRecover.getRecoverCapital(), borrowPeriod, borrowVerifyTime);
                                                    }
                                                }
                                                // 先息后本endmonth
                                                else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 1, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 0, borrowVerifyTime);
                                                    }
                                                }
                                                // 如果项目类型为融通宝，调用新的提前还款利息计算公司
                                                if (borrow.getProjectType() == 13) {
                                                    // 提前还款不应该大于本次计息时间
                                                    if (totalDays < advanceDays) {
                                                        // 用户提前还款减少的利息
                                                        assignChargeInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(assignCapital, borrow.getBorrowApr(), totalDays);
                                                    } else {
                                                        // 用户提前还款减少的利息
                                                        assignChargeInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(assignCapital, borrow.getBorrowApr(), advanceDays);
                                                    }
                                                } else {
                                                    boolean isStyle = CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
                                                    if (isStyle) {
                                                        if (advanceDays >= 30) {
                                                            assignChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(assignInterest, totalDays);
                                                        } else {
                                                            assignChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(assignInterest, advanceDays);
                                                        }
                                                    } else {
                                                        // 提前还款不应该大于本次计息时间
                                                        if (totalDays < advanceDays) {
                                                            // 用户提前还款减少的利息
                                                            assignChargeInterest = UnnormalRepayUtils.aheadRepayChargeInterest(assignCapital, borrow.getBorrowApr(), totalDays);
                                                        } else {
                                                            // 用户提前还款减少的利息
                                                            assignChargeInterest = UnnormalRepayUtils.aheadRepayChargeInterest(assignCapital, borrow.getBorrowApr(), advanceDays);
                                                        }
                                                    }
                                                }
                                            }
                                            // 项目提前还款时，提前还款利息不得大于应还款利息，需求变更
                                            if (assignChargeInterest.compareTo(assignInterest) > 0) {
                                                assignChargeInterest = assignInterest;
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.subtract(assignChargeInterest).add(assignManageFee));
                                            creditRepayBean.setManageFee(assignManageFee);
                                            creditRepayBean.setAdvanceStatus(1);
                                            creditRepayBean.setRepayAdvanceInterest(assignChargeInterest.multiply(new BigDecimal(-1)));
                                            creditRepayBean.setAdvanceDays(advanceDays);
                                            creditRepayBeanList.add(creditRepayBean);
                                            // 统计出让人还款金额
                                            userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                            userCapital = userCapital.subtract(assignCapital);
                                            userInterest = userInterest.subtract(assignInterest);
                                            userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
                                            userChargeInterest = userChargeInterest.subtract(assignChargeInterest);// 提前还款利息
                                            // 统计总额
                                            repayTotal = repayTotal.add(assignAccount).subtract(assignChargeInterest).add(assignManageFee);// 统计总和本息+管理费
                                            repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                            repayCapital = repayCapital.add(assignCapital);
                                            repayInterest = repayInterest.add(assignInterest);
                                            repayManageFee = repayManageFee.add(assignManageFee);// 統計管理費
                                            repayChargeInterest = repayChargeInterest.add(assignChargeInterest);// 统计提前还款减少的利息
                                        }
                                        repayRecoverPlanBean.setHjhCreditRepayList(creditRepayBeanList);
                                    }
                                    if (borrowRecoverPlan.getCreditStatus() != 2) {
                                        // 重新计算债转后出让人剩余金额的提前减息金额
                                        boolean isStyle = CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
                                        if (isStyle) {
                                            if (advanceDays >= 30) {
                                                userChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(userInterest, totalDays);
                                            } else {
                                                userChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(userInterest, advanceDays);
                                            }
                                        }
                                        // 统计总额
                                        repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                        repayChargeInterest = repayChargeInterest.add(userChargeInterest);
                                    }
                                }
                            } else {
                                // 统计总和
                                repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(userAccount); // 统计本息总和
                                repayCapital = repayCapital.add(userCapital);
                                repayInterest = repayInterest.add(userInterest);
                                repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                repayChargeInterest = repayChargeInterest.add(userChargeInterest);
                            }
                            // 计算还款期的数据
                            BeanUtils.copyProperties(borrowRecoverPlan, repayRecoverPlanBean);
                            repayRecoverPlanBean.setRecoverTotal(userAccount.subtract(userChargeInterest).add(userManageFee));
                            repayRecoverPlanBean.setRecoverAccount(userAccount);
                            repayRecoverPlanBean.setRecoverCapital(userCapital);
                            repayRecoverPlanBean.setRecoverInterest(userInterest);
                            repayRecoverPlanBean.setRecoverFee(userManageFee);
                            repayRecoverPlanBean.setChargeInterest(userChargeInterest.multiply(new BigDecimal(-1)));
                            repayRecoverPlanBean.setAdvanceStatus(1);
                            repayRecoverPlanBean.setChargeDays(advanceDays);
                            repayRecoverPlanList.add(repayRecoverPlanBean);
                        }
                    }
                }
            }
            borrowRepayPlan.setRecoverPlanList(repayRecoverPlanList);
        }
        borrowRepayPlan.setRepayAccountAll(repayTotal);
        borrowRepayPlan.setRepayAccount(repayAccount);
        borrowRepayPlan.setRepayCapital(repayCapital);
        borrowRepayPlan.setRepayInterest(repayInterest);
        borrowRepayPlan.setRepayFee(repayManageFee);
        borrowRepayPlan.setAdvanceStatus(1);
        borrowRepayPlan.setChargeDays(advanceDays);
        borrowRepayPlan.setChargeInterest(repayChargeInterest.multiply(new BigDecimal(-1)));
    }

    /**
     * 统计分期还款用户延期还款的总标
     *
     * @param delayDays
     * @throws ParseException
     */
    private void calculateRecoverPlanDelay(RepayDetailBean borrowRepayPlan, BorrowVO borrow, int delayDays) throws ParseException {

        // 项目编号
        String borrowNid = borrow.getBorrowNid();
        // 还款方式
        String borrowStyle = borrow.getBorrowStyle();
        // 管理费率
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        // 初审时间
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : Integer.parseInt(borrow.getVerifyTime());
        // 项目总期数
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
        // 还款期数
        int repayPeriod = borrowRepayPlan.getRepayPeriod();
        List<BorrowRecoverVO> borrowRecoverList = amTradeClient.selectBorrowRecoverByBorrowNid(borrow.getBorrowNid());
        List<BorrowRecoverPlanVO> borrowRecoverPlans = amTradeClient.selectRecoverPlan(borrow.getBorrowNid(), borrowRepayPlan.getRepayPeriod());
        List<RepayRecoverPlanBean> repayRecoverPlanList = new ArrayList<RepayRecoverPlanBean>();
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        BigDecimal repayDelayInterest = new BigDecimal(0); // 统计借款用户总延期利息
        if (borrowRecoverList != null && borrowRecoverList.size() > 0) {
            for (int i = 0; i < borrowRecoverList.size(); i++) {
                BorrowRecoverVO borrowRecover = borrowRecoverList.get(i);
                String recoverNid = borrowRecover.getNid();// 投资订单号
                int recoverUserId = borrowRecover.getUserId();// 投资用户userId
                if (borrowRecoverPlans != null && borrowRecoverPlans.size() > 0) {
                    BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                    BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                    BigDecimal userDelayInterest = BigDecimal.ZERO;// 计算用户提前还款利息
                    for (int j = 0; j < borrowRecoverPlans.size(); j++) {
                        BorrowRecoverPlanVO borrowRecoverPlan = borrowRecoverPlans.get(j);
                        String recoverPlanNid = borrowRecoverPlan.getNid();// 投资订单号
                        int recoverPlanUserId = borrowRecoverPlan.getUserId();// 投资用户userId
                        if (recoverPlanNid.equals(recoverNid) && recoverUserId == recoverPlanUserId) {
                            RepayRecoverPlanBean repayRecoverPlanBean = new RepayRecoverPlanBean();
                            userAccount = borrowRecoverPlan.getRecoverAccount();
                            userCapital = borrowRecoverPlan.getRecoverCapital();
                            userInterest = borrowRecoverPlan.getRecoverInterest();
                            // 计算用户延期利息
                            userDelayInterest = UnnormalRepayUtils.delayRepayInterest(userCapital, borrow.getBorrowApr(), delayDays);
                            // 获取应还款管理费
                            userManageFee = borrowRecoverPlan.getRecoverFee();
                            if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                                if (Validator.isNull(borrowRecover.getAccedeOrderId())) {
                                    // 直投项目还款
                                    List<CreditRepayVO> creditRepayList = creditClient.selectCreditRepayList(borrowNid, recoverNid, repayPeriod, 0);
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                                        CreditRepayVO creditRepay = null;
                                        RepayCreditRepayBean creditRepayBean = null;
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                        BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            creditRepay = creditRepayList.get(k);
                                            creditRepayBean = new RepayCreditRepayBean();
                                            String assignNid = creditRepay.getAssignNid();// 承接订单号
                                            CreditTenderVO creditTender = amTradeClient.selectCreditTender(assignNid).get(0);// 查询相应的承接记录
                                            assignAccount = creditRepay.getAssignAccount();// 承接本息
                                            assignCapital = creditRepay.getAssignCapital();// 用户实际还款本本金
                                            assignInterest = creditRepay.getAssignInterest();
                                            //用户延期利息
                                            if (borrowRecoverPlan.getCreditStatus() == 2 && k == creditRepayList.size() - 1) {
                                                assignManageFee = userManageFee;
                                                // 计算用户延期利息
                                                assignDelayInterest = userDelayInterest;
                                            } else {
                                                // 等额本息month、等额本金principal
                                                if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 1,
                                                                borrow.getAccount(), borrowPeriod, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 0,
                                                                borrow.getAccount(), borrowPeriod, borrowVerifyTime);
                                                    }
                                                }
                                                // 先息后本endmonth
                                                else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 1, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 0, borrowVerifyTime);
                                                    }
                                                }
                                                // 计算用户延期利息
                                                assignDelayInterest = UnnormalRepayUtils.delayRepayInterest(assignCapital, borrow.getBorrowApr(), delayDays);
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.add(assignDelayInterest).add(assignManageFee));
                                            creditRepayBean.setManageFee(assignManageFee);
                                            creditRepayBean.setAdvanceStatus(2);
                                            creditRepayBean.setDelayDays(delayDays);
                                            creditRepayBean.setDelayInterest(assignDelayInterest);
                                            creditRepayBeanList.add(creditRepayBean);
                                            // 统计出让人还款金额
                                            userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                            userCapital = userCapital.subtract(assignCapital);
                                            userInterest = userInterest.subtract(assignInterest);
                                            userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
                                            userDelayInterest = userDelayInterest.subtract(assignDelayInterest);// 提前还款利息
                                            // 统计总额
                                            repayTotal = repayTotal.add(assignAccount).add(assignDelayInterest).add(assignManageFee);// 统计总和本息+管理费
                                            repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                            repayCapital = repayCapital.add(assignCapital);
                                            repayInterest = repayInterest.add(assignInterest);
                                            repayManageFee = repayManageFee.add(assignManageFee);// 統計管理費
                                            repayDelayInterest = repayDelayInterest.add(assignDelayInterest);
                                        }
                                        repayRecoverPlanBean.setCreditRepayList(creditRepayBeanList);
                                    }
                                    if (borrowRecoverPlan.getCreditStatus() != 2) {
                                        // 统计总额
                                        repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                        repayDelayInterest = repayDelayInterest.add(userDelayInterest);
                                    }
                                } else {
                                    // 计划类债转还款
                                    List<HjhDebtCreditRepayVO> creditRepayList = amTradeClient.selectHjhDebtCreditRepay(borrowNid, recoverNid, repayPeriod, borrowRecoverPlan.getRecoverStatus());
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                                        HjhDebtCreditRepayVO creditRepay = null;
                                        HjhDebtCreditRepayBean creditRepayBean = null;
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                        BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                                        BigDecimal sumCreditAccount = BigDecimal.ZERO;//总承接金额
                                        int hjhFlag = 0;
                                        for (HjhDebtCreditRepayVO hjhDebtCreditRepayBean : creditRepayList) {
                                            hjhFlag++;
                                            sumCreditAccount = sumCreditAccount.add(hjhDebtCreditRepayBean.getRepayAccount());
                                        }
                                        //判断当前期是否全部承接
                                        boolean overFlag = isOverUndertake(borrowRecover, borrowRecoverPlan.getRecoverAccount(), sumCreditAccount, true, hjhFlag);
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            creditRepay = creditRepayList.get(k);
                                            creditRepayBean = new HjhDebtCreditRepayBean();
                                            // 承接订单号
                                            String assignNid = creditRepay.getAssignOrderId();
                                            // 查询相应的承接记录
                                            HjhDebtCreditTenderVO creditTender = hjhDebtCreditClient.selectHjhCreditTenderListByAssignOrderId(assignNid).get(0);
                                            assignAccount = creditRepay.getRepayAccount();// 承接本息
                                            assignCapital = creditRepay.getRepayCapital();// 用户实际还款本本金
                                            assignInterest = creditRepay.getRepayInterest();
                                            //用户延期利息
                                            if (!overFlag && k == creditRepayList.size() - 1) {
                                                assignManageFee = userManageFee;
                                                // 计算用户延期利息
                                                assignDelayInterest = userDelayInterest;
                                            } else {
                                                // 等额本息month、等额本金principal
                                                if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 1,
                                                                borrowRecover.getRecoverCapital(), borrowPeriod, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 0,
                                                                borrowRecover.getRecoverCapital(), borrowPeriod, borrowVerifyTime);
                                                    }
                                                }
                                                // 先息后本endmonth
                                                else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 1, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 0, borrowVerifyTime);
                                                    }
                                                }
                                                // 计算用户延期利息
                                                assignDelayInterest = UnnormalRepayUtils.delayRepayInterest(assignCapital, borrow.getBorrowApr(), delayDays);
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.add(assignDelayInterest).add(assignManageFee));
                                            creditRepayBean.setManageFee(userManageFee);
                                            creditRepayBean.setAdvanceStatus(2);
                                            creditRepayBean.setDelayDays(delayDays);
                                            creditRepayBean.setRepayDelayInterest(assignDelayInterest);
                                            creditRepayBeanList.add(creditRepayBean);
                                            // 统计出让人还款金额
                                            userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                            userCapital = userCapital.subtract(assignCapital);
                                            userInterest = userInterest.subtract(assignInterest);
                                            userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
//                                            userDelayInterest = userDelayInterest.subtract(assignDelayInterest);// 提前还款利息
                                            // 统计总额
                                            repayTotal = repayTotal.add(assignAccount).add(assignDelayInterest).add(assignManageFee);// 统计总和本息+管理费
                                            repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                            repayCapital = repayCapital.add(assignCapital);
                                            repayInterest = repayInterest.add(assignInterest);
                                            repayManageFee = repayManageFee.add(assignManageFee);// 統計管理費
                                            repayDelayInterest = repayDelayInterest.add(assignDelayInterest);
                                        }
                                        repayRecoverPlanBean.setHjhCreditRepayList(creditRepayBeanList);
                                    }
                                    if (borrowRecoverPlan.getCreditStatus() != 2) {
                                        // 统计总额
                                        repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                        repayDelayInterest = repayDelayInterest.add(userDelayInterest);
                                    }
                                }
                            } else {
                                // 统计总额
                                repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                repayCapital = repayCapital.add(userCapital);
                                repayInterest = repayInterest.add(userInterest);
                                repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                repayDelayInterest = repayDelayInterest.add(userDelayInterest);
                            }
                            // 计算还款期的数据
                            BeanUtils.copyProperties(borrowRecoverPlan, repayRecoverPlanBean);
                            repayRecoverPlanBean.setRecoverTotal(userAccount.add(userDelayInterest).add(userManageFee));
                            repayRecoverPlanBean.setRecoverAccount(userAccount);
                            repayRecoverPlanBean.setRecoverCapital(userCapital);
                            repayRecoverPlanBean.setRecoverInterest(userInterest);
                            repayRecoverPlanBean.setRecoverFee(userManageFee);
                            repayRecoverPlanBean.setDelayInterest(userDelayInterest);
                            repayRecoverPlanBean.setDelayDays(delayDays);
                            repayRecoverPlanBean.setAdvanceStatus(2);
                            repayRecoverPlanList.add(repayRecoverPlanBean);
                        }
                    }
                }
            }
            borrowRepayPlan.setRecoverPlanList(repayRecoverPlanList);
        }
        borrowRepayPlan.setRepayAccountAll(repayTotal);
        borrowRepayPlan.setRepayAccount(repayAccount);
        borrowRepayPlan.setRepayCapital(repayCapital);
        borrowRepayPlan.setRepayInterest(repayInterest);
        borrowRepayPlan.setRepayFee(repayManageFee);
        borrowRepayPlan.setDelayInterest(repayDelayInterest);
        borrowRepayPlan.setAdvanceStatus(2);
        borrowRepayPlan.setDelayDays(delayDays);
    }

    /**
     * 统计分期还款用户逾期还款的总标
     *
     * @param delayDays
     * @param lateDays
     * @throws ParseException
     */
    private void calculateRecoverPlanLate(RepayDetailBean borrowRepayPlan, BorrowVO borrow, int delayDays, int lateDays) throws ParseException {

        // 项目编号
        String borrowNid = borrow.getBorrowNid();
        // 还款方式
        String borrowStyle = borrow.getBorrowStyle();
        // 管理费率
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        // 初审时间
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : Integer.parseInt(borrow.getVerifyTime());
        // 项目总期数
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
        // 还款期数
        int repayPeriod = borrowRepayPlan.getRepayPeriod();
        List<BorrowRecoverVO> borrowRecoverList = amTradeClient.selectBorrowRecoverByBorrowNid(borrow.getBorrowNid());
        List<BorrowRecoverPlanVO> borrowRecoverPlans = amTradeClient.selectRecoverPlan(borrow.getBorrowNid(), borrowRepayPlan.getRepayPeriod());
        List<RepayRecoverPlanBean> repayRecoverPlanList = new ArrayList<RepayRecoverPlanBean>();
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        BigDecimal repayDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
        BigDecimal repayOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
        if (borrowRecoverList != null && borrowRecoverList.size() > 0) {
            for (int i = 0; i < borrowRecoverList.size(); i++) {
                BorrowRecoverVO borrowRecover = borrowRecoverList.get(i);
                String recoverNid = borrowRecover.getNid();// 投资订单号
                int recoverUserId = borrowRecover.getUserId();// 投资用户userId
                if (borrowRecoverPlans != null && borrowRecoverPlans.size() > 0) {
                    BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                    BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                    BigDecimal userDelayInterest = BigDecimal.ZERO;// 计算用户延期还款利息
                    BigDecimal userOverdueInterest = BigDecimal.ZERO;// 计算用户逾期还款利息
                    for (int j = 0; j < borrowRecoverPlans.size(); j++) {
                        BorrowRecoverPlanVO borrowRecoverPlan = borrowRecoverPlans.get(j);
                        String recoverPlanNid = borrowRecoverPlan.getNid();// 投资订单号
                        int recoverPlanUserId = borrowRecoverPlan.getUserId();// 投资用户userId
                        userAccount = borrowRecoverPlan.getRecoverAccount();
                        userCapital = borrowRecoverPlan.getRecoverCapital();
                        userInterest = borrowRecoverPlan.getRecoverInterest();
                        // 计算用户逾期利息
                        userOverdueInterest = UnnormalRepayUtils.overdueRepayOverdueInterest(userAccount, lateDays);
                        if (StringUtils.isNotBlank(borrow.getPlanNid())) {//计划相关
                            BigDecimal planRate = new BigDecimal(borrow.getLateInterestRate());
                            userOverdueInterest = UnnormalRepayUtils.overduePlanRepayOverdueInterest(userAccount, lateDays, planRate);
                        }
                        // 计算用户延期利息
                        userDelayInterest = UnnormalRepayUtils.overdueRepayDelayInterest(userCapital, borrow.getBorrowApr(), delayDays);
                        // 获取应还款管理费
                        userManageFee = borrowRecoverPlan.getRecoverFee();
                        if (recoverPlanNid.equals(recoverNid) && recoverUserId == recoverPlanUserId) {
                            RepayRecoverPlanBean repayRecoverPlanBean = new RepayRecoverPlanBean();
                            if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                                if (Validator.isNull(borrowRecover.getAccedeOrderId())) {
                                    // 直投类项目债转还款
                                    List<CreditRepayVO> creditRepayList = creditClient.selectCreditRepayList(borrowNid, recoverNid, repayPeriod, 0);
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                                        CreditRepayVO creditRepay = null;
                                        RepayCreditRepayBean creditRepayBean = null;
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                        BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                                        BigDecimal assignOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            creditRepay = creditRepayList.get(k);
                                            creditRepayBean = new RepayCreditRepayBean();
                                            String assignNid = creditRepay.getAssignNid();// 承接订单号
                                            CreditTenderVO creditTender = amTradeClient.selectCreditTender(assignNid).get(0);// 查询相应的承接记录
                                            assignAccount = creditRepay.getAssignAccount();// 承接本息
                                            assignCapital = creditRepay.getAssignCapital();// 用户实际还款本本金
                                            assignInterest = creditRepay.getAssignInterest();
                                            // 计算用户实际获得的本息和
                                            assignAccount = UnnormalRepayUtils.overdueRepayPrincipalInterest(assignAccount, assignCapital, borrow.getBorrowApr(), delayDays, lateDays);
                                            //最后一笔兜底
                                            if (borrowRecoverPlan.getCreditStatus() == 2 && k == creditRepayList.size() - 1) {
                                                assignManageFee = userManageFee;
                                                // 计算用户逾期利息
                                                assignOverdueInterest = userOverdueInterest;
                                                // 计算用户延期利息
                                                assignDelayInterest = userDelayInterest;
                                            } else {
                                                // 等额本息month、等额本金principal
                                                if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 1,
                                                                borrow.getAccount(), borrowPeriod, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 0,
                                                                borrow.getAccount(), borrowPeriod, borrowVerifyTime);
                                                    }
                                                }
                                                // 先息后本endmonth
                                                else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 1, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 0, borrowVerifyTime);
                                                    }
                                                }
                                                // 计算用户逾期利息
                                                assignOverdueInterest = UnnormalRepayUtils.overdueRepayOverdueInterest(assignAccount, lateDays);
                                                if (StringUtils.isNotBlank(borrow.getPlanNid())) {//计划相关
                                                    BigDecimal planRate = new BigDecimal(borrow.getLateInterestRate());
                                                    userOverdueInterest = UnnormalRepayUtils.overduePlanRepayOverdueInterest(assignAccount, lateDays, planRate);
                                                }
                                                // 计算用户延期利息
                                                assignDelayInterest = UnnormalRepayUtils.overdueRepayDelayInterest(assignCapital, borrow.getBorrowApr(), delayDays);
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee));
                                            creditRepayBean.setManageFee(assignManageFee);
                                            creditRepayBean.setAdvanceStatus(3);
                                            creditRepayBean.setDelayDays(delayDays);
                                            creditRepayBean.setDelayInterest(assignDelayInterest);
                                            creditRepayBean.setLateDays(lateDays);
                                            creditRepayBean.setLateInterest(assignOverdueInterest);
                                            creditRepayBeanList.add(creditRepayBean);
                                            // 统计出让人还款金额
                                            userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                            userCapital = userCapital.subtract(assignCapital);
                                            userInterest = userInterest.subtract(assignInterest);
                                            userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
                                            userDelayInterest = userDelayInterest.subtract(assignDelayInterest);// 提前还款利息
                                            userOverdueInterest = userOverdueInterest.subtract(assignOverdueInterest);// 逾期利息
                                            // 统计总额
                                            repayTotal = repayTotal.add(assignAccount).add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee);// 统计总和本息+管理费
                                            repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                            repayCapital = repayCapital.add(assignCapital);
                                            repayInterest = repayInterest.add(assignInterest);
                                            repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                            repayDelayInterest = repayDelayInterest.add(assignDelayInterest);// 统计借款用户总延期利息
                                            repayOverdueInterest = repayOverdueInterest.add(assignOverdueInterest);// 统计借款用户总逾期利息
                                        }
                                        repayRecoverPlanBean.setCreditRepayList(creditRepayBeanList);
                                    }
                                    if (borrowRecoverPlan.getCreditStatus() != 2) {
                                        // 统计总和
                                        repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                        repayDelayInterest = repayDelayInterest.add(userDelayInterest);
                                        repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);
                                    }
                                } else {
                                    // 计划类项目债转还款
                                    List<HjhDebtCreditRepayVO> creditRepayList = amTradeClient.selectHjhDebtCreditRepay(borrowNid, recoverNid, repayPeriod, borrowRecoverPlan.getRecoverStatus());
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                                        HjhDebtCreditRepayVO creditRepay = null;
                                        HjhDebtCreditRepayBean creditRepayBean = null;
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                        BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                                        BigDecimal assignOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
                                        BigDecimal oldAssignAccount = BigDecimal.ZERO;// 原始承接本金
                                        BigDecimal sumCreditAccount = BigDecimal.ZERO;//总承接金额
                                        int hjhFlag = 0;
                                        for (HjhDebtCreditRepayVO hjhDebtCreditRepayBean : creditRepayList) {
                                            hjhFlag++;
                                            sumCreditAccount = sumCreditAccount.add(hjhDebtCreditRepayBean.getRepayAccount());
                                        }
                                        //判断当前期是否全部承接
                                        boolean overFlag = isOverUndertake(borrowRecover, borrowRecoverPlan.getRecoverAccount(), sumCreditAccount, true, hjhFlag);
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            creditRepay = creditRepayList.get(k);
                                            creditRepayBean = new HjhDebtCreditRepayBean();
                                            // 承接订单号
                                            String assignNid = creditRepay.getAssignOrderId();
                                            // 查询相应的承接记录
                                            HjhDebtCreditTenderVO creditTender = hjhDebtCreditClient.selectHjhCreditTenderListByAssignOrderId(assignNid).get(0);
                                            assignAccount = creditRepay.getRepayAccount();// 承接本息
                                            oldAssignAccount = assignAccount;
                                            assignCapital = creditRepay.getRepayCapital();// 用户实际还款本本金
                                            assignInterest = creditRepay.getRepayInterest();
                                            // 计算用户实际获得的本息和
//                                            assignAccount = UnnormalRepayUtils.overdueRepayPrincipalInterest(assignAccount, assignCapital, borrow.getBorrowApr(), delayDays, lateDays);
                                            //最后一笔兜底
                                            if (!overFlag && k == creditRepayList.size() - 1) {
                                                assignManageFee = userManageFee;
                                                // 计算用户逾期利息
                                                assignOverdueInterest = userOverdueInterest;
                                                // 计算用户延期利息
                                                assignDelayInterest = userDelayInterest;
                                            } else {
                                                // 等额本息month、等额本金principal
                                                if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 1,
                                                                borrowRecover.getRecoverCapital(), borrowPeriod, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 0,
                                                                borrowRecover.getRecoverCapital(), borrowPeriod, borrowVerifyTime);
                                                    }
                                                }
                                                // 先息后本endmonth
                                                else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 1, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 0, borrowVerifyTime);
                                                    }
                                                }
                                                // 计算用户逾期利息
                                                assignOverdueInterest = UnnormalRepayUtils.overdueRepayOverdueInterest(oldAssignAccount, lateDays);
                                                if (StringUtils.isNotBlank(borrow.getPlanNid())) {//计划相关
                                                    BigDecimal planRate = new BigDecimal(borrow.getLateInterestRate());
                                                    assignOverdueInterest = UnnormalRepayUtils.overduePlanRepayOverdueInterest(oldAssignAccount, lateDays, planRate);
                                                }
                                                // 计算用户延期利息
                                                assignDelayInterest = UnnormalRepayUtils.overdueRepayDelayInterest(assignCapital, borrow.getBorrowApr(), delayDays);
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee));
                                            creditRepayBean.setManageFee(assignManageFee);
                                            creditRepayBean.setAdvanceStatus(3);
                                            creditRepayBean.setDelayDays(delayDays);
                                            creditRepayBean.setRepayDelayInterest(assignDelayInterest);
                                            creditRepayBean.setLateDays(lateDays);
                                            creditRepayBean.setRepayLateInterest(assignOverdueInterest);
                                            creditRepayBeanList.add(creditRepayBean);
                                            // 统计出让人还款金额
                                            userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                            userCapital = userCapital.subtract(assignCapital);
                                            userInterest = userInterest.subtract(assignInterest);
                                            userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
                                            userDelayInterest = userDelayInterest.subtract(assignDelayInterest);// 提前还款利息
                                            userOverdueInterest = userOverdueInterest.subtract(assignOverdueInterest);// 逾期利息
                                            // 统计总额
                                            repayTotal = repayTotal.add(assignAccount).add(assignDelayInterest).add(assignOverdueInterest).add(assignManageFee);// 统计总和本息+管理费
                                            repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                            repayCapital = repayCapital.add(assignCapital);
                                            repayInterest = repayInterest.add(assignInterest);
                                            repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                            repayDelayInterest = repayDelayInterest.add(assignDelayInterest);// 统计借款用户总延期利息
                                            repayOverdueInterest = repayOverdueInterest.add(assignOverdueInterest);// 统计借款用户总逾期利息
                                        }
                                        repayRecoverPlanBean.setHjhCreditRepayList(creditRepayBeanList);
                                    }
                                    if (borrowRecoverPlan.getCreditStatus() != 2) {
                                        // 统计总和
                                        repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                        repayDelayInterest = repayDelayInterest.add(userDelayInterest);
                                        repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);
                                    }
                                }
                            } else {
                                // 统计总和
                                repayTotal = repayTotal.add(userAccount).add(userDelayInterest).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                repayCapital = repayCapital.add(userCapital);
                                repayInterest = repayInterest.add(userInterest);
                                repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                repayDelayInterest = repayDelayInterest.add(userDelayInterest);
                                repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);
                            }
                            // 计算还款期的数据
                            BeanUtils.copyProperties(borrowRecoverPlan, repayRecoverPlanBean);
                            repayRecoverPlanBean.setRecoverTotal(userAccount.add(userDelayInterest).add(userOverdueInterest).add(userManageFee));
                            repayRecoverPlanBean.setRecoverAccount(userAccount);
                            repayRecoverPlanBean.setRecoverCapital(userCapital);
                            repayRecoverPlanBean.setRecoverInterest(userInterest);
                            repayRecoverPlanBean.setRecoverFee(userManageFee);
                            repayRecoverPlanBean.setDelayInterest(userDelayInterest);
                            repayRecoverPlanBean.setLateInterest(userOverdueInterest);
                            repayRecoverPlanBean.setDelayDays(delayDays);
                            repayRecoverPlanBean.setLateDays(lateDays);
                            repayRecoverPlanBean.setAdvanceStatus(3);
                            repayRecoverPlanList.add(repayRecoverPlanBean);
                        }
                    }
                }
            }
            borrowRepayPlan.setRecoverPlanList(repayRecoverPlanList);
        }
        borrowRepayPlan.setRepayAccountAll(repayTotal);
        borrowRepayPlan.setRepayAccount(repayAccount);
        borrowRepayPlan.setRepayCapital(repayCapital);
        borrowRepayPlan.setRepayInterest(repayInterest);
        borrowRepayPlan.setRepayFee(repayManageFee);
        borrowRepayPlan.setDelayDays(delayDays);
        borrowRepayPlan.setDelayInterest(repayDelayInterest);
        borrowRepayPlan.setLateDays(lateDays);
        borrowRepayPlan.setLateInterest(repayOverdueInterest);
        borrowRepayPlan.setAdvanceStatus(3);
    }

    /**
     * 统计分期还款用户正常还款的总标
     *
     * @param interestDay
     * @throws ParseException
     */
    private void calculateRecoverPlan(RepayDetailBean borrowRepayPlan, BorrowVO borrow, int interestDay) throws ParseException {

        // 项目编号
        String borrowNid = borrow.getBorrowNid();
        // 还款方式
        String borrowStyle = borrow.getBorrowStyle();
        // 管理费率
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        // 初审时间
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : Integer.parseInt(borrow.getVerifyTime());
        // 项目总期数
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
        // 还款期数
        int repayPeriod = borrowRepayPlan.getRepayPeriod();
        List<BorrowRecoverVO> borrowRecoverList = amTradeClient.selectBorrowRecoverByBorrowNid(borrow.getBorrowNid());
        List<BorrowRecoverPlanVO> borrowRecoverPlans = amTradeClient.selectRecoverPlan(borrow.getBorrowNid(), borrowRepayPlan.getRepayPeriod());
        List<RepayRecoverPlanBean> repayRecoverPlanList = new ArrayList<RepayRecoverPlanBean>();
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        if (borrowRecoverList != null && borrowRecoverList.size() > 0) {
            for (int i = 0; i < borrowRecoverList.size(); i++) {
                BorrowRecoverVO borrowRecover = borrowRecoverList.get(i);
                String recoverNid = borrowRecover.getNid();// 投资订单号
                int recoverUserId = borrowRecover.getUserId();// 投资用户userId
                if (borrowRecoverPlans != null && borrowRecoverPlans.size() > 0) {
                    BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                    BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                    for (int j = 0; j < borrowRecoverPlans.size(); j++) {
                        BorrowRecoverPlanVO borrowRecoverPlan = borrowRecoverPlans.get(j);
                        String recoverPlanNid = borrowRecoverPlan.getNid();// 投资订单号
                        int recoverPlanUserId = borrowRecoverPlan.getUserId();// 投资用户userId
                        if (recoverPlanNid.equals(recoverNid) && recoverUserId == recoverPlanUserId) {
                            RepayRecoverPlanBean repayRecoverPlanBean = new RepayRecoverPlanBean();
                            userAccount = borrowRecoverPlan.getRecoverAccount();// 获取应还款本息
                            userCapital = borrowRecoverPlan.getRecoverCapital();
                            userInterest = borrowRecoverPlan.getRecoverInterest();
                            userManageFee = borrowRecoverPlan.getRecoverFee();// 获取应还款管理费
                            // 如果发生债转
                            if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                                if (Validator.isNull(borrowRecover.getAccedeOrderId())){
                                    List<CreditRepayVO> creditRepayList = creditClient.selectCreditRepayList(borrowNid, recoverNid, repayPeriod, 0);

                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            CreditRepayVO creditRepay = creditRepayList.get(k);
                                            RepayCreditRepayBean creditRepayBean = new RepayCreditRepayBean();
                                            String assignNid = creditRepay.getAssignNid();// 承接订单号
                                            CreditTenderVO creditTender = amTradeClient.selectCreditTender(assignNid).get(0);// 查询相应的承接记录
                                            assignAccount = creditRepay.getAssignAccount();// 承接本息
                                            assignCapital = creditRepay.getAssignCapital();// 用户实际还款本本金
                                            assignInterest = creditRepay.getAssignInterest();
                                            if (borrowRecoverPlan.getCreditStatus() == 2 && k == creditRepayList.size() - 1) {
                                                assignManageFee = userManageFee;
                                            } else {
                                                // 等额本息month、等额本金principal
                                                if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 1,
                                                                borrow.getAccount(), borrowPeriod, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 0,
                                                                borrow.getAccount(), borrowPeriod, borrowVerifyTime);
                                                    }
                                                }
                                                // 先息后本endmonth
                                                else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 1, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 0, borrowVerifyTime);
                                                    }
                                                }
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.add(assignManageFee));
                                            creditRepayBean.setManageFee(assignManageFee);
                                            creditRepayBean.setAdvanceStatus(0);
                                            creditRepayBean.setChargeInterest(BigDecimal.ZERO);
                                            creditRepayBean.setChargeDays(interestDay);
                                            creditRepayBeanList.add(creditRepayBean);
                                            // 统计出让人还款金额
                                            userAccount = userAccount.subtract(assignAccount);
                                            userCapital = userCapital.subtract(assignCapital);
                                            userInterest = userInterest.subtract(assignInterest);
                                            userManageFee = userManageFee.subtract(assignManageFee);
                                            // 统计总额
                                            repayTotal = repayTotal.add(assignAccount).add(assignManageFee);// 统计总和本息+管理费
                                            repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                            repayCapital = repayCapital.add(assignCapital);
                                            repayInterest = repayInterest.add(assignInterest);
                                            repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                        }
                                        repayRecoverPlanBean.setCreditRepayList(creditRepayBeanList);
                                    }
                                    if (borrowRecoverPlan.getCreditStatus() != 2) {
                                        // 统计总额
                                        repayTotal = repayTotal.add(userAccount).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                    }
                                }else{//计划还款
                                    List<HjhDebtCreditRepayVO> creditRepayList = amTradeClient.selectHjhDebtCreditRepay(borrowNid, recoverNid, repayPeriod, borrowRecoverPlan.getRecoverStatus());
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                        BigDecimal sumCreditAccount = BigDecimal.ZERO;//总承接金额
                                        int hjhFlag = 0;
                                        for (HjhDebtCreditRepayVO hjhDebtCreditRepayBean : creditRepayList) {
                                            hjhFlag++;
                                            sumCreditAccount = sumCreditAccount.add(hjhDebtCreditRepayBean.getRepayAccount());
                                        }
                                        //判断当前期是否全部承接
                                        boolean overFlag = isOverUndertake(borrowRecover,borrowRecoverPlan.getRecoverAccount(),sumCreditAccount,true,hjhFlag);
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            HjhDebtCreditRepayVO creditRepay = creditRepayList.get(k);
                                            HjhDebtCreditRepayBean creditRepayBean = new HjhDebtCreditRepayBean();
                                            // 承接订单号
                                            String assignNid = creditRepay.getAssignOrderId();
                                            // 查询相应的承接记录
                                            HjhDebtCreditTenderVO creditTender = hjhDebtCreditClient.selectHjhCreditTenderListByAssignOrderId(assignNid).get(0);
                                            assignAccount = creditRepay.getRepayAccount();// 承接本息
                                            assignCapital = creditRepay.getRepayCapital();// 用户实际还款本本金
                                            assignInterest = creditRepay.getRepayInterest();
                                            if (!overFlag && k == creditRepayList.size() - 1) {
                                                assignManageFee = userManageFee;
                                            } else {
                                                // 等额本息month、等额本金principal
                                                if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 1,
                                                                borrowRecover.getRecoverCapital(), borrowPeriod, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 0,
                                                                borrowRecover.getRecoverCapital(), borrowPeriod, borrowVerifyTime);
                                                    }
                                                }
                                                // 先息后本endmonth
                                                else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
                                                    if (repayPeriod == borrowPeriod.intValue()) {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 1, borrowVerifyTime);
                                                    } else {
                                                        assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(creditTender.getAssignCapital(), feeRate,
                                                                borrowPeriod, borrowPeriod, differentialRate, 0, borrowVerifyTime);
                                                    }
                                                }
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.add(assignManageFee));
                                            creditRepayBean.setManageFee(assignManageFee);
                                            creditRepayBean.setAdvanceStatus(0);
                                            creditRepayBean.setRepayAdvanceInterest(BigDecimal.ZERO);
                                            creditRepayBean.setAdvanceDays(interestDay);
                                            creditRepayBeanList.add(creditRepayBean);
                                            // 统计出让人还款金额
                                            userAccount = userAccount.subtract(assignAccount);
                                            userCapital = userCapital.subtract(assignCapital);
                                            userInterest = userInterest.subtract(assignInterest);
                                            userManageFee = userManageFee.subtract(assignManageFee);
                                            // 统计总额
                                            repayTotal = repayTotal.add(assignAccount).add(assignManageFee);// 统计总和本息+管理费
                                            repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                            repayCapital = repayCapital.add(assignCapital);
                                            repayInterest = repayInterest.add(assignInterest);
                                            repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                        }
                                        repayRecoverPlanBean.setHjhCreditRepayList(creditRepayBeanList);
                                    }
                                    if (borrowRecoverPlan.getCreditStatus() != 2) {
                                        // 统计总额
                                        repayTotal = repayTotal.add(userAccount).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayManageFee = repayManageFee.add(userManageFee);// 統計管理費
                                    }
                                }
                            } else {
                                // 统计总和
                                repayTotal = repayTotal.add(userAccount).add(userManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(userAccount); // 统计本息总和
                                repayCapital = repayCapital.add(userCapital);
                                repayInterest = repayInterest.add(userInterest);
                                repayManageFee = repayManageFee.add(userManageFee);// 管理费
                            }
                            BeanUtils.copyProperties(borrowRecoverPlan, repayRecoverPlanBean);
                            repayRecoverPlanBean.setRecoverTotal(userAccount.add(userManageFee));
                            repayRecoverPlanBean.setRecoverAccount(userAccount);
                            repayRecoverPlanBean.setRecoverCapital(userCapital);
                            repayRecoverPlanBean.setRecoverInterest(userInterest);
                            repayRecoverPlanBean.setRecoverFee(userManageFee);
                            repayRecoverPlanBean.setChargeDays(interestDay);
                            repayRecoverPlanBean.setAdvanceStatus(0);
                            repayRecoverPlanList.add(repayRecoverPlanBean);
                        }
                    }
                }
            }
            borrowRepayPlan.setRecoverPlanList(repayRecoverPlanList);
        }
        borrowRepayPlan.setRepayAccountAll(repayTotal);
        borrowRepayPlan.setRepayAccount(repayAccount);
        borrowRepayPlan.setRepayCapital(repayCapital);
        borrowRepayPlan.setRepayInterest(repayInterest);
        borrowRepayPlan.setRepayFee(repayManageFee);
        borrowRepayPlan.setChargeDays(interestDay);
        borrowRepayPlan.setAdvanceStatus(0);
    }

   /**
    * 还款申请校验
    * @auther: hesy
    * @date: 2018/7/10
    */
    @Override
    public RepayBean checkForRepayRequest(RepayRequest requestBean, WebViewUserVO user, int flag){
        RepayBean repayByTerm = null;
        if(StringUtils.isBlank(requestBean.getBorrowNid()) || StringUtils.isBlank(requestBean.getPassword())){
            throw new ReturnMessageException(MsgEnum.ERR_PARAM_NUM);
        }
        // 平台密码校验
        UserVO userVO = getUserByUserId(user.getUserId());
        String mdPassword = MD5.toMD5Code(requestBean.getPassword() + userVO.getSalt());
        if (!mdPassword.equals(userVO.getPassword())) {
            throw  new ReturnMessageException(MsgEnum.ERR_PASSWORD_INVALID);
        }

        // 开户校验
        if(!user.isOpenAccount()){
            throw  new ReturnMessageException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        BorrowVO borrow = amBorrowClient.getBorrowByNid(requestBean.getBorrowNid());
        if(borrow == null){
            throw  new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_BORROW_NOT_EXIST);
        }

        boolean tranactionSetFlag = RedisUtils.tranactionSet(RedisConstants.HJH_DEBT_SWAPING + borrow.getBorrowNid(),300);
        if (!tranactionSetFlag) {//设置失败
            repayByTerm = new RepayBean();
            repayByTerm.setFlag(1);//校验失败
            repayByTerm.setMessage("系统繁忙,请5分钟后重试.......");
            return repayByTerm;
        }

        AccountVO accountVO = getAccountByUserId(user.getUserId());
        try {
            // 一次性还款余额校验
            if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrow.getBorrowStyle()) || CustomConstants.BORROW_STYLE_END.equals(borrow.getBorrowStyle())) {
                BigDecimal repayTotal = this.searchRepayTotal(user.getUserId(), borrow);
                if (repayTotal.compareTo(accountVO.getBalance()) == 0 || repayTotal.compareTo(accountVO.getBalance()) == -1) {
                    if(flag == 1){
                        repayByTerm = this.calculateRepay(borrow);
                        repayByTerm.setRepayUserId(user.getUserId());
                    }else{
                        // 查询用户在银行电子账户的余额
                        BigDecimal userBankBalance = getBankBalancePay(user.getUserId(),user.getBankAccount());
                        if (repayTotal.compareTo(userBankBalance) == 0 || repayTotal.compareTo(userBankBalance) == -1) {
                            // ** 用户符合还款条件，可以还款 *//*
                            repayByTerm = this.calculateRepay(borrow);
                            repayByTerm.setRepayUserId(user.getUserId());
                        } else {
                            throw new ReturnMessageException(MsgEnum.STATUS_CE000016);
                        }
                    }
                } else {
                    throw new ReturnMessageException(MsgEnum.STATUS_CE000015);
                }
            } // 分期还款余额校验
            else {
                BigDecimal repayTotal = this.searchRepayByTermTotal(borrow, borrow.getBorrowPeriod());
                if (repayTotal.compareTo(accountVO.getBalance()) == 0 || repayTotal.compareTo(accountVO.getBalance()) == -1) {
                    if(flag == 1){
                        repayByTerm = this.calculateRepayByTerm(borrow);
                        repayByTerm.setRepayUserId(user.getUserId());
                    }else{
                        // 查询用户在银行电子账户的可用余额
                        BigDecimal userBankBalance = getBankBalancePay(user.getUserId(),user.getBankAccount());
                        if (repayTotal.compareTo(userBankBalance) == 0 || repayTotal.compareTo(userBankBalance) == -1) {
                            // ** 用户符合还款条件，可以还款 *//*
                            repayByTerm = this.calculateRepayByTerm(borrow);
                            repayByTerm.setRepayUserId(user.getUserId());
                        } else {
                            throw new ReturnMessageException(MsgEnum.STATUS_CE000016);
                        }

                    }
                } else {
                    throw new ReturnMessageException(MsgEnum.STATUS_CE000015);
                }
            }
        } catch (Exception e) {
            logger.error("还款申请可用余额校验异常", e);
            throw new ReturnMessageException(MsgEnum.STATUS_CE999999);
        }

        return repayByTerm;
    }

    /**
     * 还款申请回调数据更新
     * @auther: hesy
     * @date: 2018/7/10
     */
    @Override
    public Boolean updateForRepayRequest(RepayBean repayBean, BankCallBean bankCallBean){
        RepayRequestUpdateRequest requestBean = new RepayRequestUpdateRequest();
        requestBean.setRepayBeanData(JSON.toJSONString(requestBean));
        requestBean.setBankCallBeanData(JSON.toJSONString(bankCallBean));

        return amTradeClient.repayRequestUpdate(requestBean);
    }

    /**
     * 统计待还款总额（不分期）
     * @auther: hesy
     * @date: 2018/7/10
     */
    public BigDecimal searchRepayTotal(int userId, BorrowVO borrow) throws ParseException {
        RepayBean RepayBean = this.calculateRepay(borrow);
        return RepayBean.getRepayAccountAll();
    }

    /**
     * 统计待还款总额（分期）
     * @auther: hesy
     * @date: 2018/7/10
     */
    public BigDecimal searchRepayByTermTotal(BorrowVO borrow, int periodTotal) throws ParseException {
        BorrowRepayVO borrowRepay = borrowRepayClient.getBorrowRepay(borrow.getBorrowNid());
        BigDecimal repayPlanTotal = new BigDecimal(0);
        // 判断用户的余额是否足够还款
        if (borrowRepay != null) {
            RepayBean repayByTerm = new RepayBean();
            // 获取相应的还款信息
            BeanUtils.copyProperties(borrowRepay, repayByTerm);
            // 计算当前还款期数
            int period = periodTotal - borrowRepay.getRepayPeriod() + 1;
            repayPlanTotal = calculateRepayPlan(repayByTerm, borrow, period);
        }
        return repayPlanTotal;
    }

    /**
     * 根据用户id获取用户名
     *
     * @param userId
     * @return
     */
    private String searchUserNameById(Integer userId) {
        UserVO user = amUserClient.findUserById(userId);
        if (user != null) {
            return user.getUsername();
        }
        return null;
    }

    /**
     * 判断是否完全承接  true:未完全承接
     * @param borrowRecover
     * @param isMonth
     * @return
     */
    private boolean isOverUndertake(BorrowRecoverVO borrowRecover, BigDecimal recoverPlanCapital, BigDecimal creditSumCapital, boolean isMonth, int hjhFlag) {
        if (isMonth) {//分期标的并且是计划债转
            if (hjhFlag > 0) {
                //分期待还本金 大于 承接本金
                if (recoverPlanCapital.compareTo(creditSumCapital) > 0) {
                    return true;
                }
            }else{
                return true;
            }
        }else{
            if (borrowRecover.getRecoverCapital().compareTo(borrowRecover.getCreditAmount()) > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 校验重复还款
     * @param userId
     * @param borrowNid
     * @return
     */
    @Override
    public boolean checkRepayInfo(Integer userId, String borrowNid) {
        BankRepayFreezeLogVO log = amTradeClient.getFreezeLogValid(userId, borrowNid);
        if(log == null){
            return true;
        }
        return false;
    }

    /**
     * 添加冻结日志
     * @auther: hesy
     * @date: 2018/7/10
     */
    @Override
    public Integer addFreezeLog(Integer userId, String orderId, String account, String borrowNid, BigDecimal repayTotal,
                                String userName){
        BankRepayFreezeLogRequest requestBean = new BankRepayFreezeLogRequest();
        requestBean.setBorrowNid(borrowNid);
        requestBean.setAccount(account);
        requestBean.setAmount(repayTotal);
        requestBean.setOrderId(orderId);
        requestBean.setUserId(userId);
        requestBean.setUserName(userName);
        return amTradeClient.addFreezeLog(requestBean);
    }

    /**
     * 删除冻结日志
     * @auther: hesy
     * @date: 2018/7/10
     */
    @Override
    public Integer deleteFreezeLogByOrderId(String orderId){
        if(StringUtils.isBlank(orderId)){
            return 0;
        }
        return amTradeClient.deleteFreezeLogByOrderId(orderId);
    }

    /**
     * 更新借款API任务表
     * @return
     */
    @Override
    public Boolean updateBorrowApicron(BorrowApicronVO apicronVO, Integer status) {
        ApiCronUpdateRequest requestBean = new ApiCronUpdateRequest();
        requestBean.setApicronVO(apicronVO);
        requestBean.setStatus(status);

        return amTradeClient.updateBorrowApicron(requestBean);
    }

    /**
     * 根据bankSeqNo检索
     * @param bankSeqNO
     * @return
     */
    @Override
    public BorrowApicronVO selectBorrowApicron(String bankSeqNO) {
        return amTradeClient.selectBorrowApicron(bankSeqNO);
    }
    /**
     * 获取用户还款列表
     * @param projectBean
     * @return
     */
    @Override
    public List<RepayListCustomizeVO> selectUserRepayedList(RepayListRequest projectBean){
        return amTradeClient.selectUserRepayedList(projectBean);
    }
    /**
     * 批次状态查询
     * @param apicron
     * @return
     */
    @Override
    public BankCallBean batchQuery(BorrowApicronVO apicron) {
        // 获取共同参数
        String batchNo = apicron.getBatchNo();// 还款请求批次号
        String batchTxDate = String.valueOf(apicron.getTxDate());// 还款请求日期
        int userId = apicron.getUserId();
        String channel = BankCallConstant.CHANNEL_PC;
        for (int i = 0; i < 3; i++) {
            String logOrderId = GetOrderIdUtils.getOrderId2(userId);
            String orderDate = GetOrderIdUtils.getOrderDate();
            // 调用还款接口
            BankCallBean loanBean = new BankCallBean();
            loanBean.setTxCode(BankCallConstant.TXCODE_BATCH_QUERY);// 消息类型(批量还款)
            loanBean.setBatchNo(batchNo);
            loanBean.setBatchTxDate(batchTxDate);
            loanBean.setLogUserId(String.valueOf(apicron.getUserId()));
            loanBean.setLogOrderId(logOrderId);
            loanBean.setLogOrderDate(orderDate);
            loanBean.setLogRemark("批次状态查询");
            loanBean.setLogClient(0);
            BankCallBean queryResult = BankCallUtils.callApiBg(loanBean);
            if (Validator.isNotNull(queryResult)) {
                String retCode = StringUtils.isNotBlank(queryResult.getRetCode()) ? queryResult.getRetCode() : "";
                if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
                    return queryResult;
                } else {
                    continue;
                }
            } else {
                continue;
            }
        }
        return null;
    }
}
