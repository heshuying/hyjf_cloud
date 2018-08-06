package com.hyjf.am.trade.service.impl.repay;

import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.trade.bean.repay.*;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.trade.EmployeeCustomize;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.repay.BankRepayFreezeLogService;
import com.hyjf.am.trade.service.repay.RepayManageService;
import com.hyjf.am.vo.task.issuerecover.BorrowWithBLOBs;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.AccountManagementFeeUtils;
import com.hyjf.common.util.calculate.UnnormalRepayUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 还款管理
 * @author hesy
 * @version RepayManageServiceImpl, v0.1 2018/6/26 18:15
 */
@Service
public class RepayManageServiceImpl extends BaseServiceImpl implements RepayManageService {
    private static final Logger logger = LoggerFactory.getLogger(RepayManageServiceImpl.class);

    public static JedisPool pool = RedisUtils.getPool();

    @Autowired
    BankRepayFreezeLogService bankRepayFreezeLogService;

    /**
     * 普通借款人管理费总待还
     * @param userId
     * @return
     */
    @Override
    public BigDecimal selectUserRepayFeeWaitTotal(Integer userId){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        return repayManageCustomizeMapper.selectUserRepayFeeWaitTotal(params);
    }

    /**
     * 担保机构管理费总待还
     * @param userId
     * @return
     */
    @Override
    public BigDecimal selectOrgRepayFeeWaitTotal(Integer userId){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        return repayManageCustomizeMapper.selectOrgRepayFeeWaitTotal(params);
    }

    /**
     * 担保机构总待还
     * @param userId
     * @return
     */
    @Override
    public BigDecimal selectRepayOrgRepaywait(Integer userId){
        return repayManageCustomizeMapper.selectRepayOrgRepaywait(userId);
    }

    /**
     * 检索还款列表
     * @param requestBean
     * @return
     */
    @Override
    public List<RepayListCustomizeVO> selectRepayList(RepayListRequest requestBean){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", requestBean.getUserId());
        param.put("status", requestBean.getStatus());
        param.put("repayStatus", requestBean.getRepayStatus());
        param.put("startDate", requestBean.getStartDate());
        param.put("endDate", requestBean.getEndDate());
        param.put("repayTimeOrder", requestBean.getRepayTimeOrder());
        param.put("checkTimeOrder", requestBean.getCheckTimeOrder());
        param.put("borrowNid", requestBean.getBorrowNid());

        if (requestBean.getLimitStart() != null) {
            param.put("limitStart", requestBean.getLimitStart());
        }else {
            param.put("limitStart", -1);
        }
        if (requestBean.getLimitEnd() != null) {
            param.put("limitEnd", requestBean.getLimitEnd());
        }else {
            param.put("limitEnd", -1);
        }

        List<RepayListCustomizeVO> list = repayManageCustomizeMapper.selectRepayList(param);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                BigDecimal accountFee = BigDecimal.ZERO;
                BigDecimal borrowTotal = BigDecimal.ZERO;
                BigDecimal realAccountTotal = BigDecimal.ZERO;
                BigDecimal allAccountFee = BigDecimal.ZERO;
                BigDecimal serviceFee = BigDecimal.ZERO;
                if (StringUtils.isNotBlank(list.get(i).getRepayFee())) {
                    accountFee = new BigDecimal(list.get(i).getRepayFee());
                }
                if (StringUtils.isNotBlank(list.get(i).getBorrowTotal())) {
                    borrowTotal = new BigDecimal(list.get(i).getBorrowTotal());
                }
                if (StringUtils.isNotBlank(list.get(i).getRealAccountYes())) {
                    realAccountTotal = new BigDecimal(list.get(i).getRealAccountYes());
                }
                if (StringUtils.isNotBlank(list.get(i).getAllRepayFee())) {
                    allAccountFee = new BigDecimal(list.get(i).getAllRepayFee());
                }
                if (StringUtils.isNotBlank(list.get(i).getServiceFee())) {
                    serviceFee = new BigDecimal(list.get(i).getServiceFee());
                }
                BigDecimal oldYesAccount = new BigDecimal(list.get(i).getYesAccount());
                BigDecimal yesAccount = oldYesAccount.subtract(serviceFee);
                list.get(i).setYesAccount(yesAccount.toString());
                list.get(i).setBorrowTotal(borrowTotal.add(allAccountFee).toString());
                list.get(i).setRealAccountYes(realAccountTotal.add(accountFee).toString());
            }
        }

        for(RepayListCustomizeVO record : list){
            List<BorrowTender> tenderList = this.getBorrowTender(record.getBorrowNid());
            for(BorrowTender tender : tenderList){
                List<TenderAgreement> agreementList = this.getTenderAgreement(tender.getNid());
                if(agreementList !=null && !agreementList.isEmpty()){
                    TenderAgreement tenderAgreement = agreementList.get(0);
                    Integer fddStatus = tenderAgreement.getStatus();
                    //法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功
                    if(fddStatus.equals(3)){
                        record.setFddStatus(1);
                    }else {
                        //隐藏下载按钮
                        record.setFddStatus(0);
                    }
                }else {
                    //下载老版本协议
                    record.setFddStatus(1);
                }
            }
        }

        return list;
    }

    /**
     * 统计还款列表总记录数
     * @param requestBean
     * @return
     */
    @Override
    public Integer selectRepayCount(RepayListRequest requestBean){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", requestBean.getUserId());
        param.put("status", requestBean.getStatus());
        param.put("repayStatus", requestBean.getRepayStatus());
        param.put("startDate", requestBean.getStartDate());
        param.put("endDate", requestBean.getEndDate());
        param.put("repayTimeOrder", requestBean.getRepayTimeOrder());
        param.put("checkTimeOrder", requestBean.getCheckTimeOrder());
        param.put("borrowNid", requestBean.getBorrowNid());

        if (requestBean.getLimitStart() != null) {
            param.put("limitStart", requestBean.getLimitStart());
        }else {
            param.put("limitStart", -1);
        }
        if (requestBean.getLimitEnd() != null) {
            param.put("limitEnd", requestBean.getLimitEnd());
        }else {
            param.put("limitEnd", -1);
        }

        Integer count = repayManageCustomizeMapper.selectRepayCount(param);

        return count;
    }

    /**
     * 检索垫付机构待垫付列表
     * @param requestBean
     * @return
     */
    @Override
    public List<RepayListCustomizeVO> selectOrgRepayList(RepayListRequest requestBean){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", requestBean.getUserId());
        param.put("status", requestBean.getStatus());
        param.put("repayStatus", requestBean.getRepayStatus());
        param.put("startDate", requestBean.getStartDate());
        param.put("endDate", requestBean.getEndDate());
        param.put("repayTimeOrder", requestBean.getRepayTimeOrder());
        param.put("checkTimeOrder", requestBean.getCheckTimeOrder());
        param.put("borrowNid", requestBean.getBorrowNid());

        if (requestBean.getLimitStart() != null) {
            param.put("limitStart", requestBean.getLimitStart());
        }else {
            param.put("limitStart", -1);
        }
        if (requestBean.getLimitEnd() != null) {
            param.put("limitEnd", requestBean.getLimitEnd());
        }else {
            param.put("limitEnd", -1);
        }

        List<RepayListCustomizeVO> list = repayManageCustomizeMapper.selectOrgRepayList(param);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                BigDecimal accountFee = BigDecimal.ZERO;
                BigDecimal borrowTotal = BigDecimal.ZERO;
                BigDecimal realAccountTotal = BigDecimal.ZERO;
                BigDecimal allAccountFee = BigDecimal.ZERO;
                BigDecimal serviceFee = BigDecimal.ZERO;
                if (StringUtils.isNotBlank(list.get(i).getRepayFee())) {
                    accountFee = new BigDecimal(list.get(i).getRepayFee());
                }
                if (StringUtils.isNotBlank(list.get(i).getBorrowTotal())) {
                    borrowTotal = new BigDecimal(list.get(i).getBorrowTotal());
                }
                if (StringUtils.isNotBlank(list.get(i).getRealAccountYes())) {
                    realAccountTotal = new BigDecimal(list.get(i).getRealAccountYes());
                }
                if (StringUtils.isNotBlank(list.get(i).getAllRepayFee())) {
                    allAccountFee = new BigDecimal(list.get(i).getAllRepayFee());
                }
                if (StringUtils.isNotBlank(list.get(i).getServiceFee())) {
                    serviceFee = new BigDecimal(list.get(i).getServiceFee());
                }
                BigDecimal oldYesAccount = new BigDecimal(list.get(i).getYesAccount());
                BigDecimal yesAccount = oldYesAccount.subtract(serviceFee);
                list.get(i).setYesAccount(yesAccount.toString());
                list.get(i).setBorrowTotal(borrowTotal.add(allAccountFee).toString());
                list.get(i).setRealAccountYes(realAccountTotal.add(accountFee).toString());
            }
        }

        for(RepayListCustomizeVO record : list){
            List<BorrowTender> tenderList = this.getBorrowTender(record.getBorrowNid());
            for(BorrowTender tender : tenderList){
                List<TenderAgreement> agreementList = this.getTenderAgreement(tender.getNid());
                if(agreementList !=null && !agreementList.isEmpty()){
                    TenderAgreement tenderAgreement = agreementList.get(0);
                    Integer fddStatus = tenderAgreement.getStatus();
                    //法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功
                    if(fddStatus.equals(3)){
                        record.setFddStatus(1);
                    }else {
                        //隐藏下载按钮
                        record.setFddStatus(0);
                    }
                }else {
                    //下载老版本协议
                    record.setFddStatus(1);
                }
            }
        }

        return list;
    }

    /**
     * 统计垫付机构待垫付总记录数
     * @param requestBean
     * @return
     */
    @Override
    public Integer selectOrgRepayCount(RepayListRequest requestBean){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", requestBean.getUserId());
        param.put("status", requestBean.getStatus());
        param.put("repayStatus", requestBean.getRepayStatus());
        param.put("startDate", requestBean.getStartDate());
        param.put("endDate", requestBean.getEndDate());
        param.put("repayTimeOrder", requestBean.getRepayTimeOrder());
        param.put("checkTimeOrder", requestBean.getCheckTimeOrder());
        param.put("borrowNid", requestBean.getBorrowNid());

        if (requestBean.getLimitStart() != null) {
            param.put("limitStart", requestBean.getLimitStart());
        }else {
            param.put("limitStart", -1);
        }
        if (requestBean.getLimitEnd() != null) {
            param.put("limitEnd", requestBean.getLimitEnd());
        }else {
            param.put("limitEnd", -1);
        }

        Integer count = repayManageCustomizeMapper.selectOrgRepayCount(param);

        return count;
    }

    /**
     * 检索垫付机构已垫付列表
     * @param requestBean
     * @return
     */
    @Override
    public List<RepayListCustomizeVO> selectOrgRepayedList(RepayListRequest requestBean){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", requestBean.getUserId());
        param.put("status", requestBean.getStatus());
        param.put("repayStatus", requestBean.getRepayStatus());
        param.put("startDate", requestBean.getStartDate());
        param.put("endDate", requestBean.getEndDate());
        param.put("repayTimeOrder", requestBean.getRepayTimeOrder());
        param.put("checkTimeOrder", requestBean.getCheckTimeOrder());
        param.put("borrowNid", requestBean.getBorrowNid());

        if (requestBean.getLimitStart() != null) {
            param.put("limitStart", requestBean.getLimitStart());
        }else {
            param.put("limitStart", -1);
        }
        if (requestBean.getLimitEnd() != null) {
            param.put("limitEnd", requestBean.getLimitEnd());
        }else {
            param.put("limitEnd", -1);
        }

        List<RepayListCustomizeVO> list = repayManageCustomizeMapper.selectOrgRepayList(param);

        for(RepayListCustomizeVO record : list){
            List<BorrowTender> tenderList = this.getBorrowTender(record.getBorrowNid());
            for(BorrowTender tender : tenderList){
                List<TenderAgreement> agreementList = this.getTenderAgreement(tender.getNid());
                if(agreementList !=null && !agreementList.isEmpty()){
                    TenderAgreement tenderAgreement = agreementList.get(0);
                    Integer fddStatus = tenderAgreement.getStatus();
                    //法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功
                    if(fddStatus.equals(3)){
                        record.setFddStatus(1);
                    }else {
                        //隐藏下载按钮
                        record.setFddStatus(0);
                    }
                }else {
                    //下载老版本协议
                    record.setFddStatus(1);
                }
            }
        }

        return list;
    }

    /**
     * 统计垫付机构总的已垫付记录数
     * @param requestBean
     * @return
     */
    @Override
    public Integer selectOrgRepayedCount(RepayListRequest requestBean){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", requestBean.getUserId());
        param.put("status", requestBean.getStatus());
        param.put("repayStatus", requestBean.getRepayStatus());
        param.put("startDate", requestBean.getStartDate());
        param.put("endDate", requestBean.getEndDate());
        param.put("repayTimeOrder", requestBean.getRepayTimeOrder());
        param.put("checkTimeOrder", requestBean.getCheckTimeOrder());
        param.put("borrowNid", requestBean.getBorrowNid());

        if (requestBean.getLimitStart() != null) {
            param.put("limitStart", requestBean.getLimitStart());
        }else {
            param.put("limitStart", -1);
        }
        if (requestBean.getLimitEnd() != null) {
            param.put("limitEnd", requestBean.getLimitEnd());
        }else {
            param.put("limitEnd", -1);
        }

        Integer count = repayManageCustomizeMapper.selectOrgRepayCount(param);

        return count;
    }

    /**
     * 获取borrowTender列表
     */
    private List<BorrowTender> getBorrowTender(String borrowNid){
        BorrowTenderExample example = new BorrowTenderExample();
        example.createCriteria().andBorrowNidEqualTo(borrowNid);
        List<BorrowTender> resultList = borrowTenderMapper.selectByExample(example);

        return resultList;
    }

    /**
     * 获取投资协议
     */
    public List<TenderAgreement> getTenderAgreement(String tenderNid) {
        TenderAgreementExample example = new TenderAgreementExample();
        example.createCriteria().andTenderNidEqualTo(tenderNid);
        List<TenderAgreement> tenderAgreements= this.tenderAgreementMapper.selectByExample(example);

        if(tenderAgreements != null && tenderAgreements.size()>0){
            return tenderAgreements;
        }
        return null;
    }

    /**
     * 还款申请更新
     * @auther: hesy
     * @date: 2018/7/10
     */
    @Override
    public boolean updateRepayMoney(RepayBean repay, BankCallBean bean) throws Exception {

        String borrowNid = repay.getBorrowNid();
        String periodTotal = repay.getBorrowPeriod();
        int remainRepayPeriod = repay.getRepayPeriod();
        int period = Integer.parseInt(periodTotal) - remainRepayPeriod + 1;
        BigDecimal repayTotal = repay.getRepayAccountAll();// 用户还款总额
        String nid = "";
        Boolean repayFlag = false;
        int errorCount = 0;
        // 获取还款人用户信息
        RUser repayUser = getRUser(repay.getRepayUserId());
        Integer roleId = repayUser.getRoleId();
        Integer repayUserId = repayUser.getUserId();
        String userName = repayUser.getUsername();

        Borrow borrow = this.getBorrow(borrowNid);
        BorrowInfo borrowInfo = this.getBorrowInfoByNid(borrowNid);
        /** 标的基本数据 */
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();// 借款期数
        String borrowStyle = borrow.getBorrowStyle();// 项目还款方式
        Integer projectType = borrowInfo.getProjectType(); // 项目类型
        boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
        if (!isMonth) {
            borrowPeriod = 1;
        }
        // 不分期还款
        List<RepayRecoverBean> recoverList = repay.getRecoverList();
        if (recoverList != null && recoverList.size() > 0) {
            BankRepayFreezeLogExample freezeLogexample = new BankRepayFreezeLogExample();
            freezeLogexample.createCriteria().andOrderIdEqualTo(bean.getOrderId());
            List<BankRepayFreezeLog> log = this.bankRepayFreezeLogMapper.selectByExample(freezeLogexample);
            if (log != null && log.size() > 0) {
                for (int i = 0; i < log.size(); i++) {
                    BankRepayFreezeLog record = log.get(i);
                    record.setDelFlag(1);// 0 有效 1无效
                    boolean repayFreezeLogFlag = this.bankRepayFreezeLogMapper.updateByPrimaryKey(record) > 0 ? true : false;
                    if (!repayFreezeLogFlag) {
                        throw new Exception("还款失败！" + "更新还款冻结日志失败" + "冻结订单号：" + bean.getOrderId());
                    }
                }
            }
            // 获取用户本次应还的金额
            BorrowRepay borrowRepay = this.getBorrowRepay(borrowNid);
            BorrowApicronExample example = new BorrowApicronExample();
            BorrowApicronExample.Criteria crt = example.createCriteria();
            crt.andBorrowNidEqualTo(borrowNid);
            crt.andApiTypeEqualTo(1); // 放还款状态 0放款1还款
            List<BorrowApicron> borrowApicrons = borrowApicronMapper.selectByExample(example);
            // 如果未还款
            if (borrowApicrons == null || (borrowApicrons != null && borrowApicrons.size() == 0)) {
                boolean borrowRecoverFlag = true;
                boolean creditFlag = true;
                for (int i = 0; i < recoverList.size(); i++) {
                    RepayRecoverBean repayRecover = recoverList.get(i);
                    BorrowRecover borrowRecoverOld = borrowRecoverMapper.selectByPrimaryKey(repayRecover.getId());
                    Integer tenderUserId = borrowRecoverOld.getUserId(); // 投资人信息
                    BigDecimal manageFee = BigDecimal.ZERO;
                    List<RepayCreditRepayBean> creditRepayList = repayRecover.getCreditRepayList();
                    List<HjhDebtCreditRepayBean> hjhCreditRepayList = repayRecover.getHjhCreditRepayList();//汇计划债转还款列表的
                    if (hjhCreditRepayList != null && hjhCreditRepayList.size() > 0) {
                        for (int j = 0; j < hjhCreditRepayList.size(); j++) {
                            HjhDebtCreditRepayBean hjhDebtCreditRepayBean = hjhCreditRepayList.get(j);
                            String investOrderId = hjhDebtCreditRepayBean.getInvestOrderId();
                            if (investOrderId.equals(repayRecover.getNid())) {
                                HjhDebtCreditRepay oldCreditRepay = this.hjhDebtCreditRepayMapper.selectByPrimaryKey(hjhDebtCreditRepayBean.getId());
                                oldCreditRepay.setAdvanceDays(hjhDebtCreditRepayBean.getAdvanceDays());
                                oldCreditRepay.setRepayAdvanceInterest(hjhDebtCreditRepayBean.getRepayAdvanceInterest());
                                oldCreditRepay.setDelayDays(hjhDebtCreditRepayBean.getDelayDays());
                                oldCreditRepay.setRepayDelayInterest(hjhDebtCreditRepayBean.getRepayDelayInterest());
                                oldCreditRepay.setLateDays(hjhDebtCreditRepayBean.getLateDays());
                                oldCreditRepay.setRepayLateInterest(hjhDebtCreditRepayBean.getRepayLateInterest());
                                oldCreditRepay.setManageFee(hjhDebtCreditRepayBean.getManageFee());
                                oldCreditRepay.setAdvanceStatus(hjhDebtCreditRepayBean.getAdvanceStatus());
                                int hjhCreditRepayFlag = this.hjhDebtCreditRepayMapper.updateByPrimaryKey(oldCreditRepay);
                                if (hjhCreditRepayFlag > 0) {
                                    manageFee = manageFee.add(hjhDebtCreditRepayBean.getManageFee());
                                    borrowRecoverOld.setChargeDays(hjhDebtCreditRepayBean.getAdvanceDays());
                                    borrowRecoverOld.setChargeInterest(borrowRecoverOld.getChargeInterest().add(hjhDebtCreditRepayBean.getRepayAdvanceInterest()));
                                    borrowRecoverOld.setDelayDays(hjhDebtCreditRepayBean.getDelayDays());
                                    borrowRecoverOld.setDelayInterest(borrowRecoverOld.getDelayInterest().add(hjhDebtCreditRepayBean.getRepayDelayInterest()));
                                    borrowRecoverOld.setLateDays(hjhDebtCreditRepayBean.getLateDays());
                                    borrowRecoverOld.setLateInterest(borrowRecoverOld.getLateInterest().add(hjhDebtCreditRepayBean.getRepayLateInterest()));
                                    boolean recoverFlag = this.borrowRecoverMapper.updateByPrimaryKeySelective(borrowRecoverOld) > 0 ? true : false;
                                    if (!recoverFlag) {
                                        errorCount = errorCount + 1;
                                    }
                                    borrowRecoverFlag = borrowRecoverFlag && recoverFlag;
                                }else {
                                    errorCount = errorCount + 1;
                                }
                                creditFlag = creditFlag && hjhCreditRepayFlag > 0;
                            }
                        }
                    }
                    if (creditRepayList != null && creditRepayList.size() > 0) {
                        for (int j = 0; j < creditRepayList.size(); j++) {
                            RepayCreditRepayBean creditRepay = creditRepayList.get(j);
                            String tenderOrderId = creditRepay.getCreditTenderNid();
                            if (tenderOrderId.equals(repayRecover.getNid())) {
                                CreditRepay creditRepayOld = creditRepayMapper.selectByPrimaryKey(creditRepay.getId());
                                creditRepayOld.setChargeDays(creditRepay.getChargeDays());
                                creditRepayOld.setChargeInterest(creditRepay.getChargeInterest());
                                creditRepayOld.setDelayDays(creditRepay.getDelayDays());
                                creditRepayOld.setDelayInterest(creditRepay.getDelayInterest());
                                creditRepayOld.setLateDays(creditRepay.getLateDays());
                                creditRepayOld.setLateInterest(creditRepay.getLateInterest());
                                creditRepayOld.setManageFee(creditRepay.getManageFee());
                                creditRepayOld.setAdvanceStatus(creditRepay.getAdvanceStatus());
                                boolean creditRepayFlag = this.creditRepayMapper.updateByPrimaryKeySelective(creditRepayOld) > 0 ? true : false;
                                if (creditRepayFlag) {
                                    manageFee = manageFee.add(creditRepay.getManageFee());
                                    borrowRecoverOld.setChargeDays(creditRepay.getChargeDays());
                                    borrowRecoverOld.setChargeInterest(borrowRecoverOld.getChargeInterest().add(creditRepay.getChargeInterest()));
                                    borrowRecoverOld.setDelayDays(creditRepay.getDelayDays());
                                    borrowRecoverOld.setDelayInterest(borrowRecoverOld.getDelayInterest().add(creditRepay.getDelayInterest()));
                                    borrowRecoverOld.setLateDays(creditRepay.getLateDays());
                                    borrowRecoverOld.setLateInterest(borrowRecoverOld.getLateInterest().add(creditRepay.getLateInterest()));
                                    boolean recoverFlag = this.borrowRecoverMapper.updateByPrimaryKeySelective(borrowRecoverOld) > 0 ? true : false;
                                    if (!recoverFlag) {
                                        errorCount = errorCount + 1;
                                    }
                                    borrowRecoverFlag = borrowRecoverFlag && recoverFlag;
                                } else {
                                    errorCount = errorCount + 1;
                                }
                                creditFlag = creditFlag && creditRepayFlag;
                            }
                        }
                    }
                    if (borrowRecoverFlag && creditFlag) {
                        // 设置推荐人信息
                        setRecommendInfo(borrowRecoverOld, tenderUserId);

                        manageFee = manageFee.add(repayRecover.getRecoverFee());
                        borrowRecoverOld.setAdvanceStatus(repayRecover.getAdvanceStatus());
                        borrowRecoverOld.setChargeDays(repayRecover.getChargeDays());
                        borrowRecoverOld.setChargeInterest(borrowRecoverOld.getChargeInterest().add(repayRecover.getChargeInterest()));
                        borrowRecoverOld.setDelayDays(repayRecover.getDelayDays());
                        borrowRecoverOld.setDelayInterest(borrowRecoverOld.getDelayInterest().add(repayRecover.getDelayInterest()));
                        borrowRecoverOld.setLateDays(repayRecover.getLateDays());
                        borrowRecoverOld.setLateInterest(borrowRecoverOld.getLateInterest().add(repayRecover.getLateInterest()));
                        borrowRecoverOld.setRecoverFee(manageFee);
                        boolean flag = borrowRecoverMapper.updateByPrimaryKey(borrowRecoverOld) > 0 ? true : false;
                        if (!flag) {
                            errorCount = errorCount + 1;
                        }
                        borrowRecoverFlag = borrowRecoverFlag && flag;
                    }
                }
                if (borrowRecoverFlag && creditFlag) {
                    // 添加借款表repay还款来源、实际还款人
                    if (roleId == 3) { // repayUserId不为空，表示垫付机构还款
                        borrowRepay.setRepayMoneySource(2);
                        borrowRepay.setRepayUsername(userName);
                    } else {
                        borrowRepay.setRepayMoneySource(1);
                        borrowRepay.setRepayUsername(userName);
                    }
                    boolean borrowRepayFlag = borrowRepayMapper.updateByPrimaryKeySelective(borrowRepay) > 0 ? true : false;
                    if (borrowRepayFlag) {
                        int borrowApicronCount = this.borrowApicronMapper.countByExample(example);
                        // 还款任务>0件
                        if (borrowApicronCount > 0) {
                            throw new Exception("还款失败！" + "重复还款，【" + errorCount + "】" + "项目编号：" + borrowNid);
                        }
                        int nowTime = GetDate.getNowTime10();
                        nid = repay.getBorrowNid() + "_" + repay.getUserId() + "_1";
                        BorrowApicron borrowApicron = new BorrowApicron();
                        borrowApicron.setNid(nid);
                        borrowApicron.setUserId(repayUserId);
                        borrowApicron.setUserName(userName);
                        borrowApicron.setBorrowNid(borrowNid);
                        borrowApicron.setBorrowAccount(borrow.getAccount());
                        borrowApicron.setBorrowPeriod(borrowPeriod);
                        if (roleId == 3) {
                            borrowApicron.setIsRepayOrgFlag(1);
                        } else {
                            borrowApicron.setIsRepayOrgFlag(0);
                        }
                        borrowApicron.setApiType(1);
                        borrowApicron.setPeriodNow(1);
                        borrowApicron.setRepayStatus(0);
                        borrowApicron.setStatus(1);
                        borrowApicron.setFailTimes(0);
                        borrowApicron.setCreditRepayStatus(0);
                        borrowApicron.setCreateTime(GetDate.getDate());
                        borrowApicron.setUpdateTime(GetDate.getDate());
                        if (projectType == 13) {
                            borrowApicron.setExtraYieldStatus(0);// 融通宝加息相关的放款状态
                            borrowApicron.setExtraYieldRepayStatus(0);// 融通宝相关的加息还款状态
                        } else {
                            borrowApicron.setExtraYieldStatus(1);// 融通宝加息相关的放款状态
                            borrowApicron.setExtraYieldRepayStatus(1);// 融通宝相关的加息还款状态
                        }
                        borrowApicron.setPlanNid(borrow.getPlanNid());// 汇计划计划编号
                        boolean apiCronFlag = borrowApicronMapper.insertSelective(borrowApicron) > 0 ? true : false;
                        if (apiCronFlag) {
                            repayFlag = true;
                        } else {
                            throw new Exception("还款失败，项目编号：" + borrowNid);
                        }
                    } else {
                        throw new Exception("还款失败！" + "失败数量【" + errorCount + "】");
                    }
                } else {
                    throw new Exception("还款失败！！" + "失败数量【" + errorCount + "】");
                }
            } else if (borrowApicrons.size() == 1) {
                repayFlag = true;
            } else {
                throw new Exception("还款失败！" + "重复还款，【" + errorCount + "】" + "项目编号：" + borrowNid);
            }
        }
        List<RepayDetailBean> repayPLanList = repay.getRepayPlanList();
        // 分期还款
        if (repayPLanList != null && repayPLanList.size() > 0) {
            for (int i = 0; i < repayPLanList.size(); i++) {
                RepayDetailBean repayDetail = repayPLanList.get(i);
                if (repayDetail.getRepayPeriod() == period) {
                    BorrowRepayPlan borrowRepayPlan = this.getRepayPlan(borrowNid,period);
                    BorrowApicronExample example = new BorrowApicronExample();
                    BorrowApicronExample.Criteria crt = example.createCriteria();
                    crt.andBorrowNidEqualTo(borrowNid);
                    crt.andApiTypeEqualTo(1);
                    crt.andPeriodNowEqualTo(period);
                    List<BorrowApicron> borrowApicrons = borrowApicronMapper.selectByExample(example);
                    if (borrowApicrons == null || (borrowApicrons != null && borrowApicrons.size() == 0)) {
                        boolean borrowRecoverPlanFlag = true;
                        boolean creditFlag = true;
                        List<RepayRecoverPlanBean> repayRecoverPlans = repayDetail.getRecoverPlanList();
                        if (repayRecoverPlans != null && repayRecoverPlans.size() > 0) {
                            for (int j = 0; j < repayRecoverPlans.size(); j++) {
                                RepayRecoverPlanBean repayRecoverPlan = repayRecoverPlans.get(j);
                                BorrowRecoverPlan borrowRecoverPlanOld = borrowRecoverPlanMapper.selectByPrimaryKey(repayRecoverPlan.getId());
                                Integer tenderUserId = borrowRecoverPlanOld.getUserId();// 投资人信息
                                BigDecimal manageFee = BigDecimal.ZERO;
                                List<RepayCreditRepayBean> creditRepayList = repayRecoverPlan.getCreditRepayList();
                                if (creditRepayList != null && creditRepayList.size() > 0) {
                                    for (int k = 0; k < creditRepayList.size(); k++) {
                                        RepayCreditRepayBean creditRepay = creditRepayList.get(k);
                                        String tenderOrderId = creditRepay.getCreditTenderNid();
                                        if (tenderOrderId.equals(repayRecoverPlan.getNid())) {
                                            CreditRepay creditRepayOld = creditRepayMapper.selectByPrimaryKey(creditRepay.getId());
                                            creditRepayOld.setChargeDays(creditRepay.getChargeDays());
                                            creditRepayOld.setChargeInterest(creditRepay.getChargeInterest());
                                            creditRepayOld.setDelayDays(creditRepay.getDelayDays());
                                            creditRepayOld.setDelayInterest(creditRepay.getDelayInterest());
                                            creditRepayOld.setLateDays(creditRepay.getLateDays());
                                            creditRepayOld.setLateInterest(creditRepay.getLateInterest());
                                            creditRepayOld.setManageFee(creditRepay.getManageFee());
                                            creditRepayOld.setAdvanceStatus(creditRepay.getAdvanceStatus());
                                            boolean creditRepayFlag = this.creditRepayMapper.updateByPrimaryKeySelective(creditRepayOld) > 0 ? true : false;
                                            if (creditRepayFlag) {
                                                manageFee = manageFee.add(creditRepay.getManageFee());
                                                borrowRecoverPlanOld.setChargeDays(creditRepay.getChargeDays());
                                                borrowRecoverPlanOld.setChargeInterest(borrowRecoverPlanOld.getChargeInterest().add(creditRepay.getChargeInterest()));
                                                borrowRecoverPlanOld.setDelayDays(creditRepay.getDelayDays());
                                                borrowRecoverPlanOld.setDelayInterest(borrowRecoverPlanOld.getDelayInterest().add(creditRepay.getDelayInterest()));
                                                borrowRecoverPlanOld.setLateDays(creditRepay.getLateDays());
                                                borrowRecoverPlanOld.setLateInterest(borrowRecoverPlanOld.getLateInterest().add(creditRepay.getLateInterest()));
                                                boolean recoverPlanFlag = this.borrowRecoverPlanMapper.updateByPrimaryKeySelective(borrowRecoverPlanOld) > 0 ? true : false;
                                                if (!recoverPlanFlag) {
                                                    errorCount = errorCount + 1;
                                                }
                                                borrowRecoverPlanFlag = borrowRecoverPlanFlag && recoverPlanFlag;
                                            } else {
                                                errorCount = errorCount + 1;
                                            }
                                            creditFlag = creditFlag && creditRepayFlag;
                                        }
                                    }
                                }
                                List<HjhDebtCreditRepayBean> hjhCreditRepayList = repayRecoverPlan.getHjhCreditRepayList();//汇计划债转还款列表的
                                if (hjhCreditRepayList != null && hjhCreditRepayList.size() > 0) {
                                    for (int k = 0; k < hjhCreditRepayList.size(); k++) {
                                        HjhDebtCreditRepayBean hjhDebtCreditRepayBean = hjhCreditRepayList.get(k);
                                        String investOrderId = hjhDebtCreditRepayBean.getInvestOrderId();
                                        if (investOrderId.equals(repayRecoverPlan.getNid())) {
                                            HjhDebtCreditRepay oldCreditRepay = this.hjhDebtCreditRepayMapper.selectByPrimaryKey(hjhDebtCreditRepayBean.getId());
                                            oldCreditRepay.setAdvanceDays(hjhDebtCreditRepayBean.getAdvanceDays());
                                            oldCreditRepay.setRepayAdvanceInterest(hjhDebtCreditRepayBean.getRepayAdvanceInterest());
                                            oldCreditRepay.setDelayDays(hjhDebtCreditRepayBean.getDelayDays());
                                            oldCreditRepay.setRepayDelayInterest(hjhDebtCreditRepayBean.getRepayDelayInterest());
                                            oldCreditRepay.setLateDays(hjhDebtCreditRepayBean.getLateDays());
                                            oldCreditRepay.setRepayLateInterest(hjhDebtCreditRepayBean.getRepayLateInterest());
                                            oldCreditRepay.setManageFee(hjhDebtCreditRepayBean.getManageFee());
                                            oldCreditRepay.setAdvanceStatus(hjhDebtCreditRepayBean.getAdvanceStatus());
                                            int hjhCreditRepayFlag = this.hjhDebtCreditRepayMapper.updateByPrimaryKey(oldCreditRepay);
                                            if (hjhCreditRepayFlag > 0) {
                                                manageFee = manageFee.add(hjhDebtCreditRepayBean.getManageFee());
                                                borrowRecoverPlanOld.setChargeDays(hjhDebtCreditRepayBean.getAdvanceDays());
                                                borrowRecoverPlanOld.setChargeInterest(borrowRecoverPlanOld.getChargeInterest().add(hjhDebtCreditRepayBean.getRepayAdvanceInterest()));
                                                borrowRecoverPlanOld.setDelayDays(hjhDebtCreditRepayBean.getDelayDays());
                                                borrowRecoverPlanOld.setDelayInterest(borrowRecoverPlanOld.getDelayInterest().add(hjhDebtCreditRepayBean.getRepayDelayInterest()));
                                                borrowRecoverPlanOld.setLateDays(hjhDebtCreditRepayBean.getLateDays());
                                                borrowRecoverPlanOld.setLateInterest(borrowRecoverPlanOld.getLateInterest().add(hjhDebtCreditRepayBean.getRepayLateInterest()));
                                                boolean recoverPlanFlag = this.borrowRecoverPlanMapper.updateByPrimaryKeySelective(borrowRecoverPlanOld) > 0 ? true : false;
                                                if (!recoverPlanFlag) {
                                                    errorCount = errorCount + 1;
                                                }
                                                borrowRecoverPlanFlag = borrowRecoverPlanFlag && recoverPlanFlag;
                                            }else {
                                                errorCount = errorCount + 1;
                                            }
                                            creditFlag = creditFlag && hjhCreditRepayFlag > 0;
                                        }
                                    }
                                }
                                if (borrowRecoverPlanFlag && creditFlag) {
                                    // 设置推荐人信息
                                    setRecommendInfo(borrowRecoverPlanOld, tenderUserId);

                                    manageFee = manageFee.add(repayRecoverPlan.getRecoverFee());
                                    borrowRecoverPlanOld.setAdvanceStatus(repayRecoverPlan.getAdvanceStatus());
                                    borrowRecoverPlanOld.setChargeDays(repayRecoverPlan.getChargeDays());
                                    borrowRecoverPlanOld.setChargeInterest(borrowRecoverPlanOld.getChargeInterest().add(repayRecoverPlan.getChargeInterest()));
                                    borrowRecoverPlanOld.setDelayDays(repayRecoverPlan.getDelayDays());
                                    borrowRecoverPlanOld.setDelayInterest(borrowRecoverPlanOld.getDelayInterest().add(repayRecoverPlan.getDelayInterest()));
                                    borrowRecoverPlanOld.setLateDays(repayRecoverPlan.getLateDays());
                                    borrowRecoverPlanOld.setLateInterest(borrowRecoverPlanOld.getLateInterest().add(repayRecoverPlan.getLateInterest()));
                                    borrowRecoverPlanOld.setRecoverFee(manageFee);
                                    boolean flag = borrowRecoverPlanMapper.updateByPrimaryKey(borrowRecoverPlanOld) > 0 ? true : false;
                                    if (!flag) {
                                        errorCount = errorCount + 1;
                                    }
                                    borrowRecoverPlanFlag = borrowRecoverPlanFlag && flag;
                                }
                            }
                        }
                        if (borrowRecoverPlanFlag && creditFlag) {
                            // 添加借款表repay还款来源、实际还款人
                            if (roleId == 3) { // repayUserId不为空，表示垫付机构还款
                                borrowRepayPlan.setRepayMoneySource(2);
                                borrowRepayPlan.setRepayUsername(userName);
                            } else {
                                borrowRepayPlan.setRepayMoneySource(1);
                                borrowRepayPlan.setRepayUsername(userName);
                            }
                            boolean borrowRepayPlanFlag = borrowRepayPlanMapper.updateByPrimaryKeySelective(borrowRepayPlan) > 0 ? true : false;
                            if (borrowRepayPlanFlag) {
                                int borrowApicronCount = this.borrowApicronMapper.countByExample(example);
                                // 还款任务>0件
                                if (borrowApicronCount > 0) {
                                    throw new Exception("重复还款");
                                }
                                int nowTime = GetDate.getNowTime10();
                                nid = repay.getBorrowNid() + "_" + repay.getUserId() + "_" + period;
                                BorrowApicron borrowApicron = new BorrowApicron();
                                borrowApicron.setNid(nid);
                                borrowApicron.setUserId(repayUserId);
                                borrowApicron.setUserName(userName);
                                borrowApicron.setBorrowNid(borrowNid);
                                borrowApicron.setBorrowAccount(borrow.getAccount());
                                borrowApicron.setBorrowPeriod(borrowPeriod);
                                if (roleId == 3) {
                                    borrowApicron.setIsRepayOrgFlag(1);
                                } else {
                                    borrowApicron.setIsRepayOrgFlag(0);
                                }
                                borrowApicron.setApiType(1);
                                borrowApicron.setPeriodNow(period);
                                borrowApicron.setRepayStatus(0);
                                borrowApicron.setStatus(1);
                                borrowApicron.setFailTimes(0);
                                borrowApicron.setCreditRepayStatus(0);
                                borrowApicron.setCreateTime(GetDate.getDate());
                                borrowApicron.setUpdateTime(GetDate.getDate());
                                if (projectType == 13) {
                                    borrowApicron.setExtraYieldStatus(0);// 融通宝加息相关的放款状态
                                    borrowApicron.setExtraYieldRepayStatus(0);// 融通宝相关的加息还款状态
                                } else {
                                    borrowApicron.setExtraYieldStatus(1);// 融通宝加息相关的放款状态
                                    borrowApicron.setExtraYieldRepayStatus(1);// 融通宝相关的加息还款状态
                                }
                                borrowApicron.setPlanNid(borrow.getPlanNid());// 汇计划项目编号
                                boolean apiCronFlag = borrowApicronMapper.insertSelective(borrowApicron) > 0 ? true : false;
                                if (apiCronFlag) {
                                    repayFlag = true;
                                } else {
                                    throw new Exception("重复还款");
                                }
                            } else {
                                throw new Exception("还款失败！" + "失败数量【" + errorCount + "】");
                            }
                        } else {
                            throw new Exception("还款失败！" + "失败数量【" + errorCount + "】");
                        }
                    } else if (borrowApicrons.size() == 1) {
                        return true;
                    } else {
                        throw new Exception("还款失败！" + "重复还款，【" + errorCount + "】" + "项目编号：" + borrowNid + ",期数；" + period);
                    }
                }
            }
        }
        if (repayFlag) {
            if (countRepayAccountListByNid(nid) == 0) {
                // 获取用户的账户信息
                Account repayAccount = this.getAccount(repayUserId);
                BigDecimal borrowBalance = repayAccount.getBankBalance();
                String repayAccountId = bean.getAccountId();
                if (borrowBalance.compareTo(repayTotal) >= 0) {
                    // ** 用户符合还款条件，可以还款 *//*
                    BigDecimal bankBalance = repayTotal;// 可用金额
                    BigDecimal bankFrost = repayTotal;// 冻结金额
                    BigDecimal bankBalanceCash = repayTotal;// 江西银行账户余额
                    BigDecimal bankFrostCash = repayTotal;// 江西银行账户冻结金额
                    repayAccount.setBankBalance(bankBalance);
                    repayAccount.setBankFrost(bankFrost);
                    repayAccount.setBankFrostCash(bankBalanceCash);
                    repayAccount.setBankBalanceCash(bankFrostCash);
                    boolean accountFlag = this.adminAccountCustomizeMapper.updateOfRepayBorrowFreeze(repayAccount) > 0 ? true : false;
                    if (accountFlag) {
                        repayAccount = this.getAccount(repayUserId);
                        // 插入huiyingdai_account_list表
                        AccountList accountList = new AccountList();
                        accountList.setNid(borrowNid + "_" + repay.getUserId() + "_" + period); // 生成规则BorrowNid_userid_期数
                        accountList.setUserId(repayUserId);// 借款人/垫付机构 id
                        accountList.setAmount(repayTotal);// 操作金额
                        /** 银行存管相关字段设置 */
                        accountList.setAccountId(repayAccountId);
                        accountList.setBankAwait(repayAccount.getBankAwait());
                        accountList.setBankAwaitCapital(repayAccount.getBankAwaitCapital());
                        accountList.setBankAwaitInterest(repayAccount.getBankAwaitInterest());
                        accountList.setBankBalance(repayAccount.getBankBalance());
                        accountList.setBankFrost(repayAccount.getBankFrost());
                        accountList.setBankInterestSum(repayAccount.getBankInterestSum());
                        accountList.setBankInvestSum(repayAccount.getBankInvestSum());
                        accountList.setBankTotal(repayAccount.getBankTotal());
                        accountList.setBankWaitCapital(repayAccount.getBankWaitCapital());
                        accountList.setBankWaitInterest(repayAccount.getBankWaitInterest());
                        accountList.setBankWaitRepay(repayAccount.getBankWaitRepay());
                        accountList.setPlanBalance(repayAccount.getPlanBalance());//汇计划账户可用余额
                        accountList.setPlanFrost(repayAccount.getPlanFrost());
                        accountList.setCheckStatus(0);
                        accountList.setTradeStatus(1);// 交易状态 0:失败 1:成功
                        accountList.setIsBank(1);
                        accountList.setTxDate(Integer.parseInt(bean.getTxDate()));
                        accountList.setTxTime(Integer.parseInt(bean.getTxTime()));
                        accountList.setSeqNo(bean.getSeqNo());
                        accountList.setBankSeqNo(bean.getTxDate() + bean.getTxTime() + bean.getSeqNo());
                        // 非银行相关
                        accountList.setType(3);// 收支类型1收入2支出3冻结
                        accountList.setTrade("repay_freeze");// 交易类型
                        accountList.setTradeCode("balance");// 操作识别码
                        accountList.setTotal(repayAccount.getTotal());// 资金总额
                        accountList.setBalance(repayAccount.getBalance());
                        accountList.setFrost(repayAccount.getFrost());// 冻结金额
                        accountList.setAwait(repayAccount.getAwait());// 待收金额
                        accountList.setRepay(repayAccount.getRepay());// 待还金额
                        accountList.setCreateTime(GetDate.getDate());// 创建时间
                        accountList.setOperator(CustomConstants.OPERATOR_AUTO_REPAY);// 操作员
                        accountList.setRemark(borrowNid);
                        accountList.setIp(repay.getIp());// 操作IP
                        accountList.setWeb(0);
                        boolean accountListFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;
                        if (accountListFlag) {
                            bankRepayFreezeLogService.deleteFreezeLogsByOrderId(bean.getOrderId());
                            return true;
                        } else {
                            throw new Exception("还款失败！" + "插入借款人交易明细表AccountList失败！");
                        }
                    } else {
                        throw new Exception("还款失败！" + "更新借款人账户余额表Account失败！");
                    }
                } else {
                    throw new Exception("用户汇付账户余额不足!");
                }
            } else {
                throw new Exception("此笔还款的交易明细已存在,请勿重复还款");
            }
        } else {
            throw new Exception("还款失败！" + "失败数量【" + errorCount + "】");
        }
    }

    /**
     * 设置还款表推荐人信息
     * @param recover
     * @param tenderUserId
     */
    private void setRecommendInfo(BorrowRecover recover, Integer tenderUserId) {
        RUser rUser = getRUser(tenderUserId);
        // 用户属性 0=>无主单 1=>有主单 2=>线下员工 3=>线上员工
        Integer attribute = rUser.getAttribute();
        // 投资人用户属性
        recover.setTenderUserAttribute(attribute);
        // 如果是线上员工或线下员工，推荐人的userId和username不插
        if (attribute == 2 || attribute == 3) {
            EmployeeCustomize employeeCustomize = employeeCustomizeMapper.selectEmployeeByUserId(tenderUserId);
            if (employeeCustomize != null) {
                recover.setInviteRegionId(employeeCustomize.getRegionId());
                recover.setInviteRegionName(employeeCustomize.getRegionName());
                recover.setInviteBranchId(employeeCustomize.getBranchId());
                recover.setInviteBranchName(employeeCustomize.getBranchName());
                recover.setInviteDepartmentId(employeeCustomize.getDepartmentId());
                recover.setInviteDepartmentName(employeeCustomize.getDepartmentName());
            }
        } else if (attribute == 1) {
            int refUserId = rUser.getSpreadsUserId();
            // 查找用户推荐人
            RUser ruserRef = getRUser(refUserId);
            if (ruserRef != null) {
                recover.setInviteUserId(ruserRef.getUserId());
                recover.setInviteUserName(ruserRef.getUsername());
            }
            recover.setInviteUserAttribute(ruserRef.getAttribute());
            // 查找用户推荐人部门
            EmployeeCustomize employeeCustomize = employeeCustomizeMapper.selectEmployeeByUserId(refUserId);
            if (employeeCustomize != null) {
                recover.setInviteRegionId(employeeCustomize.getRegionId());
                recover.setInviteRegionName(employeeCustomize.getRegionName());
                recover.setInviteBranchId(employeeCustomize.getBranchId());
                recover.setInviteBranchName(employeeCustomize.getBranchName());
                recover.setInviteDepartmentId(employeeCustomize.getDepartmentId());
                recover.setInviteDepartmentName(employeeCustomize.getDepartmentName());
            }
        } else if (attribute == 0) {
            int refUserId = rUser.getSpreadsUserId();
            // 查找推荐人
            RUser ruserRef = getRUser(refUserId);
            if (ruserRef != null) {
                recover.setInviteUserId(ruserRef.getUserId());
                recover.setInviteUserName(ruserRef.getUsername());
            }
            recover.setInviteUserAttribute(ruserRef.getAttribute());
        }
    }

    /**
     * 设置还款计划表推荐人信息
     * @param recoverPlan
     * @param tenderUserId
     */
    private void setRecommendInfo(BorrowRecoverPlan recoverPlan, Integer tenderUserId) {
        RUser rUser = getRUser(tenderUserId);
        // 用户属性 0=>无主单 1=>有主单 2=>线下员工 3=>线上员工
        Integer attribute = rUser.getAttribute();
        // 投资人用户属性
        recoverPlan.setTenderUserAttribute(attribute);
        // 如果是线上员工或线下员工，推荐人的userId和username不插
        if (attribute == 2 || attribute == 3) {
            EmployeeCustomize employeeCustomize = employeeCustomizeMapper.selectEmployeeByUserId(tenderUserId);
            if (employeeCustomize != null) {
                recoverPlan.setInviteRegionId(employeeCustomize.getRegionId());
                recoverPlan.setInviteRegionName(employeeCustomize.getRegionName());
                recoverPlan.setInviteBranchId(employeeCustomize.getBranchId());
                recoverPlan.setInviteBranchName(employeeCustomize.getBranchName());
                recoverPlan.setInviteDepartmentId(employeeCustomize.getDepartmentId());
                recoverPlan.setInviteDepartmentName(employeeCustomize.getDepartmentName());
            }
        } else if (attribute == 1) {
            int refUserId = rUser.getSpreadsUserId();
            // 查找用户推荐人
            RUser ruserRef = getRUser(refUserId);
            if (ruserRef != null) {
                recoverPlan.setInviteUserId(ruserRef.getUserId());
                recoverPlan.setInviteUserName(ruserRef.getUsername());
            }
            recoverPlan.setInviteUserAttribute(ruserRef.getAttribute());
            // 查找用户推荐人部门
            EmployeeCustomize employeeCustomize = employeeCustomizeMapper.selectEmployeeByUserId(refUserId);
            if (employeeCustomize != null) {
                recoverPlan.setInviteRegionId(employeeCustomize.getRegionId());
                recoverPlan.setInviteRegionName(employeeCustomize.getRegionName());
                recoverPlan.setInviteBranchId(employeeCustomize.getBranchId());
                recoverPlan.setInviteBranchName(employeeCustomize.getBranchName());
                recoverPlan.setInviteDepartmentId(employeeCustomize.getDepartmentId());
                recoverPlan.setInviteDepartmentName(employeeCustomize.getDepartmentName());
            }
        } else if (attribute == 0) {
            int refUserId = rUser.getSpreadsUserId();
            // 查找推荐人
            RUser ruserRef = getRUser(refUserId);
            if (ruserRef != null) {
                recoverPlan.setInviteUserId(ruserRef.getUserId());
                recoverPlan.setInviteUserName(ruserRef.getUsername());
            }
            recoverPlan.setInviteUserAttribute(ruserRef.getAttribute());
        }
    }

    private int countRepayAccountListByNid(String nid) {
        AccountListExample accountListExample = new AccountListExample();
        accountListExample.createCriteria().andNidEqualTo(nid).andTradeEqualTo("repay_success");
        return this.accountListMapper.countByExample(accountListExample);
    }

    @Override
    public boolean updateBorrowCreditStautus(String borrowNid) {
        Borrow borrow = getBorrow(borrowNid);
        String planNid = borrow.getPlanNid();
        BigDecimal rollBackAccount = BigDecimal.ZERO;
        if (StringUtils.isNotBlank(planNid)) {//计划标的
            HjhDebtCreditExample example = new HjhDebtCreditExample();
            List<Integer> list = new ArrayList<>();
            list.add(0);
            list.add(1);
            example.createCriteria().andBorrowNidEqualTo(borrowNid).andCreditStatusIn(list);
            List<HjhDebtCredit> hjhDebtCreditList = hjhDebtCreditMapper.selectByExample(example);
            String rollBackPlanNid = null;
            if (hjhDebtCreditList != null && hjhDebtCreditList.size() > 0) {
                for (HjhDebtCredit hjhDebtCredit : hjhDebtCreditList) {
                    logger.info("===================标的：" + borrowNid + ",存在未承接债权，需要终止债权，再次清算，债权编号：" + hjhDebtCredit.getCreditNid() + "===================");
                    BigDecimal creditPrice = hjhDebtCredit.getCreditPrice();//已承接金额
                    BigDecimal liquidationFairValue = hjhDebtCredit.getLiquidationFairValue();//清算时债权价值
                    BigDecimal resultAmount = liquidationFairValue.subtract(creditPrice);//回滚金额
                    rollBackAccount = rollBackAccount.add(resultAmount);
                    hjhDebtCredit.setCreditStatus(3);
                    //更新债权结束时间 add by cwyang 2018-4-2
                    hjhDebtCredit.setEndTime(GetDate.getNowTime10());
                    rollBackPlanNid = hjhDebtCredit.getPlanNidNew();
                    this.hjhDebtCreditMapper.updateByPrimaryKey(hjhDebtCredit);
                    String planOrderId = hjhDebtCredit.getPlanOrderId();//获得订单号
                    HjhAccedeExample accedeExample = new HjhAccedeExample();
                    accedeExample.createCriteria().andAccedeOrderIdEqualTo(planOrderId);
                    List<HjhAccede> accedeList = this.hjhAccedeMapper.selectByExample(accedeExample);
                    HjhAccede hjhAccede = accedeList.get(0);
                    hjhAccede.setCreditCompleteFlag(2);//将清算状态置为2,以便2次清算
                    this.hjhAccedeMapper.updateByPrimaryKey(hjhAccede);
                    logger.info("===================标的：" + borrowNid + ",存在未承接债权，需要终止债权，再次清算，债权编号：" + hjhDebtCredit.getCreditNid() + "，订单号：" + planOrderId + "===================");
                }
            }
            //回滚开放额度 add by cwyang 2017-12-25
            if (StringUtils.isNotBlank(rollBackPlanNid)) {
                rollBackAccedeAccount(rollBackPlanNid, rollBackAccount);
            }
        } else {//直投标的
            BorrowCreditExample example = new BorrowCreditExample();
            BorrowCreditExample.Criteria cra = example.createCriteria();
            cra.andBidNidEqualTo(borrowNid);
            cra.andCreditStatusEqualTo(0);
            List<BorrowCredit> borrowCreditList = this.borrowCreditMapper.selectByExample(example);
            if (borrowCreditList != null && borrowCreditList.size() > 0) {
                for (BorrowCredit borrowCredit : borrowCreditList) {
                    borrowCredit.setCreditStatus(3);
                    this.borrowCreditMapper.updateByPrimaryKeySelective(borrowCredit);
                }
            }
        }

        return true;
    }

    /**
     * 回滚开放额度
     */
    private void rollBackAccedeAccount(String planNid, BigDecimal rollBackAccount) {
        HjhPlanExample example = new HjhPlanExample();
        example.createCriteria().andPlanNidEqualTo(planNid);
        List<HjhPlan> planList = this.hjhPlanMapper.selectByExample(example);
        if (planList != null && planList.size() > 0) {
            for (HjhPlan hjhPlan : planList) {
                HjhPlan hjhPlanParam = new HjhPlan();
                hjhPlanParam.setPlanNid(hjhPlan.getPlanNid());
                hjhPlanParam.setAvailableInvestAccount(rollBackAccount);
                this.hjhPlanCustomizeMapper.updateRepayPlanAccount(hjhPlanParam);
                redisSub(RedisConstants.HJH_PLAN + hjhPlan.getPlanNid(), rollBackAccount.toString());//增加redis相应计划可投金额
            }
        }

    }

    /**
     * 并发情况下保证设置一个值
     *
     * @param key
     * @param value
     */
    private void redisSub(String key, String value) {

        Jedis jedis = pool.getResource();

        while ("OK".equals(jedis.watch(key))) {
            List<Object> results = null;

            String balance = jedis.get(key);
            BigDecimal bal = new BigDecimal(0);
            if (balance != null) {
                bal = new BigDecimal(balance);
            }
            BigDecimal val = new BigDecimal(value);

            Transaction tx = jedis.multi();
            String valbeset = bal.subtract(val).toString();
            tx.set(key, valbeset);
            results = tx.exec();
            if (results == null || results.isEmpty()) {
                jedis.unwatch();
            } else {
                String ret = (String) results.get(0);
                if (ret != null && "OK".equals(ret)) {
                    // 成功后
                    break;
                } else {
                    jedis.unwatch();
                }
            }
        }
    }

    /**
     * 根据还款人id，项目编号查询相应的项目
     *
     * @param userId
     * @param userName
     * @param roleId
     * @param borrowNid
     * @return
     */
    @Override
    public Borrow searchRepayProject(int userId, String userName, String roleId, String borrowNid) {
        // 获取当前的用户还款的项目
        BorrowInfoExample example = new BorrowInfoExample();
        BorrowInfoExample.Criteria borrowCrt = example.createCriteria();
        borrowCrt.andBorrowNidEqualTo(borrowNid);
        if (StringUtils.isNotEmpty(roleId) && "3".equals(roleId)) {// 如果是垫付机构
            borrowCrt.andRepayOrgUserIdEqualTo(userId);
        } else {// 普通借款人
            borrowCrt.andUserIdEqualTo(userId);
        }
        List<BorrowInfo> borrowInfoList = this.borrowInfoMapper.selectByExample(example);
        if (borrowInfoList != null && borrowInfoList.size() == 1) {
            return this.getBorrow(borrowInfoList.get(0).getBorrowNid());
        } else {
            return null;
        }
    }

    @Override
    public boolean checkRepayInfo(Integer userId, String borrowNid) {
        BankRepayFreezeLogExample example = new BankRepayFreezeLogExample();
        example.createCriteria().andUserIdEqualTo(userId).andBorrowNidEqualTo(borrowNid).andDelFlagEqualTo(0);
        List<BankRepayFreezeLog> list = bankRepayFreezeLogMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return false;
        }
        return true;
    }


    @Override
    public void insertRepayFreezeLof(Integer userId, String orderId, String account, String borrowNid,
                                     BigDecimal repayTotal, String userName) {
        BankRepayFreezeLog log = new BankRepayFreezeLog();
        log.setBorrowNid(borrowNid);
        log.setAccount(account);
        log.setAmount(repayTotal);
        log.setDelFlag(0);// 0 有效 1 无效
        log.setOrderId(orderId);
        log.setUserId(userId);
        log.setUserName(userName);
        log.setCreateTime(GetDate.getDate());
        log.setCreateUserId(userId);
        log.setCreateUserName(userName);
        int flag = this.bankRepayFreezeLogMapper.insertSelective(log);
        if (flag > 0) {
            logger.info("====================插入冻结表日志成功!============");
        } else {
            logger.info("====================插入冻结表日志失败!============");
        }
    }

    /**
     * 删除临时日志,外部调用
     *
     * @param orderId
     */
    @Override
    public void deleteFreezeTempLogs(String orderId) {
        BankRepayFreezeLogExample example = new BankRepayFreezeLogExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<BankRepayFreezeLog> log = this.bankRepayFreezeLogMapper.selectByExample(example);
        if (log != null && log.size() > 0) {
            for (int i = 0; i < log.size(); i++) {
                BankRepayFreezeLog record = log.get(i);
                record.setDelFlag(1);// 0 有效 1无效
                int flag = this.bankRepayFreezeLogMapper.updateByPrimaryKey(record);
                if (flag > 0) {
                    logger.info("=============还款冻结成功,删除还款冻结临时日志成功=========");
                } else {
                    logger.info("==============删除还款冻结临时日志失败============");
                }
            }
        }
    }

    @Override
    public BigDecimal searchRepayTotal(int userId, Borrow borrow) throws ParseException {
        RepayBean RepayBean = this.calculateRepay(userId, borrow);
        return RepayBean.getRepayAccountAll();
    }

    /**
     * 计算单期的总的还款信息
     * @param userId
     * @param borrow
     * @return
     * @throws ParseException
     */
    @Override
    public RepayBean calculateRepay(int userId, Borrow borrow) throws ParseException {

        RepayBean repay = new RepayBean();
        // 获取还款总表数据
        BorrowRepay borrowRepay = this.searchRepay(userId, borrow.getBorrowNid());
        // 判断是否存在还款数据
        if (borrowRepay != null) {
            // 获取相应的还款信息
            BeanUtils.copyProperties(borrowRepay, repay);
            // 计划还款时间
            Integer repayTime = borrowRepay.getRepayTime();
            // 获取用户申请的延期天数
            int delayDays = borrowRepay.getDelayDays().intValue();
            repay.setBorrowPeriod(String.valueOf(borrow.getBorrowPeriod()));
            // 未分期默认传分期为0
            this.calculateRecover(repay, borrow, repayTime, delayDays);
        }
        return repay;
    }

    private BorrowRepay searchRepay(int userId, String borrowNid) {
        // 获取还款总表数据
        BorrowRepayExample borrowRepayExample = new BorrowRepayExample();
        BorrowRepayExample.Criteria borrowRepayCrt = borrowRepayExample.createCriteria();
        borrowRepayCrt.andUserIdEqualTo(userId);
        borrowRepayCrt.andBorrowNidEqualTo(borrowNid);
        List<BorrowRepay> borrowRepays = borrowRepayMapper.selectByExample(borrowRepayExample);
        if (borrowRepays != null && borrowRepays.size() == 1) {
            return borrowRepays.get(0);
        } else {
            return null;
        }
    }

    /**
     * 计算单期的用户的还款信息
     * @param repay
     * @param borrow
     * @param repayTime
     * @param delayDays
     * @throws ParseException
     */
    private void calculateRecover(RepayBean repay, Borrow borrow, Integer repayTime, int delayDays) throws ParseException {
        int time = GetDate.getNowTime10();
        // 用户计划还款时间
        String repayTimeStr = GetDate.getDateTimeMyTimeInMillis(repayTime);
        // 用户实际还款时间
        String factRepayTime = GetDate.getDateTimeMyTimeInMillis(time);
        int distanceDays = GetDate.daysBetween(factRepayTime, repayTimeStr);
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
            String repayAdvanceDay = this.getBorrowConfig("REPAY_ADVANCE_TIME");
            int advanceDays = distanceDays;
            // 用户提前还款
            //如果是融通宝项目,不判断提前还款的阀值 add by cwyang 2017-6-14
            int projectType = this.getBorrowInfoByNid(borrow.getBorrowNid()).getProjectType();
            if (13 == projectType || CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                repayAdvanceDay = "0";
            }
            if (Integer.parseInt(repayAdvanceDay) < advanceDays) {
                // 计算用户提前还款总额
                this.calculateRecoverTotalAdvance(repay, borrow, advanceDays, projectType);
            } else {// 用户正常还款
                // 计算用户实际还款总额
                this.calculateRecoverTotal(repay, borrow, advanceDays);
            }
        }
    }

    /**
     * 统计单期还款用户延期还款的总标
     * @param repay
     * @param borrow
     * @param delayDays
     * @throws ParseException
     */
    private void calculateRecoverTotalDelay(RepayBean repay, Borrow borrow, int delayDays) throws ParseException {

        // 用户延期
        String borrowNid = borrow.getBorrowNid();// 项目编号
        String borrowStyle = borrow.getBorrowStyle(); // 还款方式
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate()); // 管理费率
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : borrow.getVerifyTime(); // 初审时间
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();// 项目总期数
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        BigDecimal repayDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
        // 查询相应的不分期的还款信息
        List<BorrowRecover> borrowRecovers = searchBorrowRecover(borrowNid);
        if (borrowRecovers != null && borrowRecovers.size() > 0) {
            List<RepayRecoverBean> repayRecoverList = new ArrayList<RepayRecoverBean>();
            BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
            BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
            BigDecimal userManageFee = BigDecimal.ZERO;// 获取应还款管理费
            for (int i = 0; i < borrowRecovers.size(); i++) {
                BorrowRecover borrowRecover = borrowRecovers.get(i);
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
                        List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, tenderOrderId, 1, 0);
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                            BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                CreditRepay creditRepay = creditRepayList.get(j);
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
                        List<HjhDebtCreditRepay> creditRepayList = this.selectHjhDebtCreditRepay(borrowNid, tenderOrderId, 1, borrowRecover.getRecoverStatus());
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                            BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                            //判断当前期是否全部承接
                            boolean overFlag = isOverUndertake(borrowRecover,null,null,false,0);
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                HjhDebtCreditRepay creditRepay = creditRepayList.get(j);
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
     * 根据项目id查询相应的用户的待还款信息
     *
     * @param borrowNid
     * @return
     */
    @Override
    public List<BorrowRecover> searchBorrowRecover(String borrowNid) {
        BorrowRecoverExample example = new BorrowRecoverExample();
        BorrowRecoverExample.Criteria crt = example.createCriteria();
        crt.andBorrowNidEqualTo(borrowNid);
        List<BorrowRecover> borrowRecovers = borrowRecoverMapper.selectByExample(example);
        return borrowRecovers;
    }

    /**
     * 统计单期还款用户正常还款的总标
     * @param repay
     * @param borrow
     * @param interestDay
     * @throws ParseException
     */
    private void calculateRecoverTotal(RepayBean repay, Borrow borrow, int interestDay) throws ParseException {

        // 正常还款
        String borrowNid = borrow.getBorrowNid();// 项目编号
        String borrowStyle = borrow.getBorrowStyle(); // 还款方式
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());// 管理费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());// 差异费率
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : borrow.getVerifyTime();// 初审时间
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();// 项目总期数
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        List<BorrowRecover> borrowRecovers = searchBorrowRecover(borrowNid);
        if (borrowRecovers != null && borrowRecovers.size() > 0) {
            List<RepayRecoverBean> repayRecoverList = new ArrayList<RepayRecoverBean>();
            BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
            BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户還款管理費
            for (int i = 0; i < borrowRecovers.size(); i++) {
                BorrowRecover borrowRecover = borrowRecovers.get(i);
                RepayRecoverBean repayRecoverBean = new RepayRecoverBean();
                String tenderOrderId = borrowRecover.getNid();// 投资订单号
                userAccount = borrowRecover.getRecoverAccount();// 计算用户实际获得的本息和
                userCapital = borrowRecover.getRecoverCapital();
                userInterest = borrowRecover.getRecoverInterest();
                userManageFee = borrowRecover.getRecoverFee();// 计算用户還款管理費
                if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                    if(Validator.isNull(borrowRecover.getAccedeOrderId())){
                        // 投资项目还款
                        List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, tenderOrderId, 1, 0);
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                CreditRepay creditRepay = creditRepayList.get(j);
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
                    }else{
                        // 计划类还款
                        List<HjhDebtCreditRepay> creditRepayList = this.selectHjhDebtCreditRepay(borrowNid, tenderOrderId, 1, borrowRecover.getRecoverStatus());
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                            //判断当前期是否全部承接
                            boolean overFlag = isOverUndertake(borrowRecover,null,null,false,0);
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                HjhDebtCreditRepay creditRepay = creditRepayList.get(j);
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
     * 查询相应的债转还款记录
     * @param borrowNid
     * @param tenderOrderId
     * @param periodNow
     * @param status
     * @return
     */
    private List<CreditRepay> selectCreditRepay(String borrowNid, String tenderOrderId, Integer periodNow, int status) {
        CreditRepayExample example = new CreditRepayExample();
        CreditRepayExample.Criteria crt = example.createCriteria();
        crt.andBidNidEqualTo(borrowNid);
        crt.andCreditTenderNidEqualTo(tenderOrderId);
        crt.andRecoverPeriodEqualTo(periodNow);
        crt.andStatusEqualTo(status);
        example.setOrderByClause("id ASC");
        List<CreditRepay> creditRepayList = this.creditRepayMapper.selectByExample(example);
        return creditRepayList;
    }

    /**
     *  查询相应的债转还款记录
     * @param borrowNid
     * @param tenderOrderId
     * @param periodNow
     * @param status
     * @return
     */
    private List<HjhDebtCreditRepay> selectHjhDebtCreditRepay(String borrowNid, String tenderOrderId, int periodNow, int status) {
        HjhDebtCreditRepayExample example = new HjhDebtCreditRepayExample();
        HjhDebtCreditRepayExample.Criteria crt = example.createCriteria();
        crt.andBorrowNidEqualTo(borrowNid);
        crt.andInvestOrderIdEqualTo(tenderOrderId);
        crt.andRepayPeriodEqualTo(periodNow);
        crt.andRepayStatusEqualTo(status);
        crt.andDelFlagEqualTo(0);
        crt.andRepayAccountGreaterThan(BigDecimal.ZERO);
        example.setOrderByClause("id ASC");
        List<HjhDebtCreditRepay> creditRepayList = this.hjhDebtCreditRepayMapper.selectByExample(example);
        return creditRepayList;
    }

    /**
     * 判断是否完全承接  true:未完全承接
     * @param borrowRecover
     * @param recoverPlanCapital
     * @param creditSumCapital
     * @param isMonth
     * @param hjhFlag
     * @return
     */
    private boolean isOverUndertake(BorrowRecover borrowRecover, BigDecimal recoverPlanCapital, BigDecimal creditSumCapital, boolean isMonth, int hjhFlag) {
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
     * 统计单期还款用户逾期还款的总标
     * @param repay
     * @param borrow
     * @param delayDays
     * @param lateDays
     * @throws ParseException
     */
    private void calculateRecoverTotalLate(RepayBean repay, Borrow borrow, int delayDays, int lateDays) throws ParseException {

        // 项目编号
        String borrowNid = borrow.getBorrowNid();
        // 还款方式
        String borrowStyle = borrow.getBorrowStyle();
        // 管理费率
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        // 初审时间
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : borrow.getVerifyTime();
        // 项目总期数
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        BigDecimal repayDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
        BigDecimal repayOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
        List<BorrowRecover> borrowRecovers = searchBorrowRecover(borrowNid);
        if (borrowRecovers != null && borrowRecovers.size() > 0) {
            List<RepayRecoverBean> repayRecoverList = new ArrayList<RepayRecoverBean>();
            BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
            BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户還款管理費
            BigDecimal userDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
            BigDecimal userOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
            for (int i = 0; i < borrowRecovers.size(); i++) {
                BorrowRecover borrowRecover = borrowRecovers.get(i);
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
                    if(Validator.isNull(borrowRecover.getAccedeOrderId())){
                        // 直投类项目还款
                        List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, tenderOrderId, 1, 0);
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                            BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                            BigDecimal assignOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                CreditRepay creditRepay = creditRepayList.get(j);
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
                    }else{
                        // 计划类债转还款
                        List<HjhDebtCreditRepay> creditRepayList = this.selectHjhDebtCreditRepay(borrowNid, tenderOrderId, 1, borrowRecover.getRecoverStatus());
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                            BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                            BigDecimal assignOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
                            //判断当前期是否全部承接
                            boolean overFlag = isOverUndertake(borrowRecover,null,null,false,0);
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                HjhDebtCreditRepay creditRepay = creditRepayList.get(j);
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
     * 统计单期还款用户提前还款的总标
     * @param repay
     * @param borrow
     * @param interestDay
     * @param projectType
     * @throws ParseException
     */
    private void calculateRecoverTotalAdvance(RepayBean repay, Borrow borrow, int interestDay, int projectType) throws ParseException {

        // 用户提前还款
        String borrowNid = borrow.getBorrowNid();// 项目编号
        String borrowStyle = borrow.getBorrowStyle();// 还款方式
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());// 管理费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());// 差异费率
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : borrow.getVerifyTime();// 初审时间
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();// 项目总期数
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayChargeInterest = BigDecimal.ZERO;// 提前还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        List<BorrowRecover> borrowRecovers = searchBorrowRecover(borrow.getBorrowNid());
        if (borrowRecovers != null && borrowRecovers.size() > 0) {
            List<RepayRecoverBean> repayRecoverList = new ArrayList<RepayRecoverBean>();
            BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
            BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
            BigDecimal userManageFee = BigDecimal.ZERO;// 获取应还款管理费
            for (int i = 0; i < borrowRecovers.size(); i++) {
                BorrowRecover borrowRecover = borrowRecovers.get(i);
                RepayRecoverBean repayRecoverBean = new RepayRecoverBean();
                String tenderOrderId = borrowRecover.getNid();// 投资订单号
                String recoverTime = GetDate.getDateTimeMyTimeInMillis(borrowRecover.getRecoverTime());
                String createTime = GetDate.datetimeFormat.format(borrowRecover.getCreateTime());
                int totalDays = GetDate.daysBetween(createTime, recoverTime);// 获取这两个时间之间有多少天
                // 计算投资用户实际获得的本息和
                userAccount = borrowRecover.getRecoverAccount();
                userCapital = borrowRecover.getRecoverCapital();
                userInterest = borrowRecover.getRecoverInterest();
                // 如果项目类型为融通宝，调用新的提前还款利息计算公司
                if (projectType == 13 || CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
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
                    if(isStyle){
                        if(interestDay >= 30){
                            userChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(userInterest,totalDays);
                        }else{
                            userChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(userInterest,interestDay);
                        }
                    }else {
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
                    if(Validator.isNull(borrowRecover.getAccedeOrderId())){
                        // 直投项目债转还款
                        // 债转还款数据
                        List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, tenderOrderId, 1, 0);
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                CreditRepay creditRepay = creditRepayList.get(j);
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
                                    if (projectType == 13) {
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
                                        if(isStyle){
                                            if(interestDay >= 30){
                                                assignChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(assignInterest,totalDays);
                                            }else{
                                                assignChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(assignInterest,interestDay);
                                            }
                                        }else {
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
                    }else{
                        boolean overFlag = false;
                        // 计划类还款
                        // 债转还款数据
                        List<HjhDebtCreditRepay> creditRepayList = this.selectHjhDebtCreditRepay(borrowNid, tenderOrderId, 1, borrowRecover.getRecoverStatus());
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                            //判断当前期是否全部承接
                            overFlag = isOverUndertake(borrowRecover,null,null,false,0);
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                HjhDebtCreditRepay creditRepay = creditRepayList.get(j);
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
                                    if (projectType == 13) {
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
                                        if(isStyle){
                                            if(interestDay >= 30){
                                                assignChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(assignInterest,totalDays);
                                            }else{
                                                assignChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(assignInterest,interestDay);
                                            }
                                        }else {
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

    @Override
    public BigDecimal searchRepayByTermTotal(int userId, Borrow borrow, BigDecimal borrowApr, String borrowStyle, int periodTotal) throws ParseException {
        BorrowRepay borrowRepay = this.searchRepay(userId, borrow.getBorrowNid());
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
     * 计算多期的总的还款信息
     *
     * @param userId
     * @param borrow
     * @return
     * @throws ParseException
     */
    @Override
    public RepayBean calculateRepayByTerm(int userId, Borrow borrow) throws ParseException {

        RepayBean repay = new RepayBean();
        // 获取还款总表数据
        BorrowRepay borrowRepay = this.searchRepay(userId, borrow.getBorrowNid());
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

    /**
     * 计算用户分期还款本期应还金额
     * @param repay
     * @param borrow
     * @param period
     * @return
     * @throws ParseException
     */
    private BigDecimal calculateRepayPlan(RepayBean repay, Borrow borrow, int period) throws ParseException {

        List<RepayDetailBean> borrowRepayPlanDeails = new ArrayList<RepayDetailBean>();
        List<BorrowRepayPlan> borrowRepayPlans = searchRepayPlan(repay.getUserId(), borrow.getBorrowNid());
        BigDecimal repayAccountAll = new BigDecimal("0");
        if (borrowRepayPlans != null && borrowRepayPlans.size() > 0) {
            // 用户实际还款额
            for (int i = 0; i < borrowRepayPlans.size(); i++) {
                RepayDetailBean repayPlanDetail = new RepayDetailBean();
                BorrowRepayPlan borrowRepayPlan = borrowRepayPlans.get(i);
                if (period == borrowRepayPlan.getRepayPeriod()) {
                    String repayTimeStart = null;
                    if (i == 0) {
                        repayTimeStart = borrowRepayPlan.getCreateTime().toString();
                    } else {
                        repayTimeStart = borrowRepayPlans.get(i - 1).getRepayTime().toString();
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

    private List<BorrowRepayPlan> searchRepayPlan(int userId, String borrowNid) {
        BorrowRepayPlanExample borrowRepayPlanExample = new BorrowRepayPlanExample();
        BorrowRepayPlanExample.Criteria borrowRepayPlanCrt = borrowRepayPlanExample.createCriteria();
        borrowRepayPlanCrt.andUserIdEqualTo(userId);
        borrowRepayPlanCrt.andBorrowNidEqualTo(borrowNid);
        List<BorrowRepayPlan> borrowRepayPlans = borrowRepayPlanMapper.selectByExample(borrowRepayPlanExample);
        return borrowRepayPlans;
    }

    /**
     * 计算用户分期还款本期应还金额
     * @param borrowRepayPlan
     * @param borrow
     * @param period
     * @param repayTimeStart
     * @throws ParseException
     */
    private void calculateRecoverPlan(RepayDetailBean borrowRepayPlan, Borrow borrow, int period, String repayTimeStart) throws ParseException {

        int delayDays = borrowRepayPlan.getDelayDays().intValue();
        int time = GetDate.getNowTime10();
        // 用户计划还款时间
        String repayTime = GetDate.getDateTimeMyTimeInMillis(borrowRepayPlan.getRepayTime());
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
            String repayAdvanceDay = this.getBorrowConfig("REPAY_ADVANCE_TIME");
            int advanceDays = distanceDays;
            //如果是融通宝项目,不判断提前还款的阙值 add by cwyang 2017-6-14
            int projectType = this.getBorrowInfoByNid(borrow.getBorrowNid()).getProjectType();
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
     * 统计分期还款用户延期还款的总标
     * @param borrowRepayPlan
     * @param borrow
     * @param delayDays
     * @throws ParseException
     */
    private void calculateRecoverPlanDelay(RepayDetailBean borrowRepayPlan, Borrow borrow, int delayDays) throws ParseException {

        // 项目编号
        String borrowNid = borrow.getBorrowNid();
        // 还款方式
        String borrowStyle = borrow.getBorrowStyle();
        // 管理费率
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        // 初审时间
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : borrow.getVerifyTime();
        // 项目总期数
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
        // 还款期数
        int repayPeriod = borrowRepayPlan.getRepayPeriod();
        List<BorrowRecover> borrowRecoverList = this.selectBorrowRecoverList(borrow.getBorrowNid());
        List<BorrowRecoverPlan> borrowRecoverPlans = searchBorrowRecoverPlan(borrow.getBorrowNid(), borrowRepayPlan.getRepayPeriod());
        List<RepayRecoverPlanBean> repayRecoverPlanList = new ArrayList<RepayRecoverPlanBean>();
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        BigDecimal repayDelayInterest = new BigDecimal(0); // 统计借款用户总延期利息
        if (borrowRecoverList != null && borrowRecoverList.size() > 0) {
            for (int i = 0; i < borrowRecoverList.size(); i++) {
                BorrowRecover borrowRecover = borrowRecoverList.get(i);
                String recoverNid = borrowRecover.getNid();// 投资订单号
                int recoverUserId = borrowRecover.getUserId();// 投资用户userId
                if (borrowRecoverPlans != null && borrowRecoverPlans.size() > 0) {
                    BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                    BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                    BigDecimal userDelayInterest = BigDecimal.ZERO;// 计算用户提前还款利息
                    for (int j = 0; j < borrowRecoverPlans.size(); j++) {
                        BorrowRecoverPlan borrowRecoverPlan = borrowRecoverPlans.get(j);
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
                                if (Validator.isNull(borrowRecover.getAccedeOrderId())){
                                    // 直投项目还款
                                    List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, recoverNid, repayPeriod, 0);
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                                        CreditRepay creditRepay = null;
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
                                            CreditTender creditTender = this.getCreditTender(assignNid);// 查询相应的承接记录
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
                                }else{
                                    // 计划类债转还款
                                    List<HjhDebtCreditRepay> creditRepayList = this.selectHjhDebtCreditRepay(borrowNid, recoverNid, repayPeriod, borrowRecoverPlan.getRecoverStatus());
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                                        HjhDebtCreditRepay creditRepay = null;
                                        HjhDebtCreditRepayBean creditRepayBean = null;
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                        BigDecimal assignDelayInterest = BigDecimal.ZERO;// 统计借款用户总延期利息
                                        BigDecimal sumCreditAccount = BigDecimal.ZERO;//总承接金额
                                        int hjhFlag = 0;
                                        for (HjhDebtCreditRepay hjhDebtCreditRepayBean : creditRepayList) {
											hjhFlag++;
											sumCreditAccount = sumCreditAccount.add(hjhDebtCreditRepayBean.getRepayAccount());
										}
                                        //判断当前期是否全部承接
                                        boolean overFlag = isOverUndertake(borrowRecover,borrowRecoverPlan.getRecoverAccount(),sumCreditAccount,true,hjhFlag);
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            creditRepay = creditRepayList.get(k);
                                            creditRepayBean = new HjhDebtCreditRepayBean();
                                            String assignNid = creditRepay.getAssignOrderId();// 承接订单号
                                            HjhDebtCreditTender creditTender = this.getHjhDebtCreditTender(assignNid);// 查询相应的承接记录
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
     * @param borrowRepayPlan
     * @param borrow
     * @param delayDays
     * @param lateDays
     * @throws ParseException
     */
    private void calculateRecoverPlanLate(RepayDetailBean borrowRepayPlan, Borrow borrow, int delayDays, int lateDays) throws ParseException {

        // 项目编号
        String borrowNid = borrow.getBorrowNid();
        // 还款方式
        String borrowStyle = borrow.getBorrowStyle();
        // 管理费率
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        // 初审时间
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : borrow.getVerifyTime();
        // 项目总期数
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
        // 还款期数
        int repayPeriod = borrowRepayPlan.getRepayPeriod();
        List<BorrowRecover> borrowRecoverList = this.selectBorrowRecoverList(borrow.getBorrowNid());
        List<BorrowRecoverPlan> borrowRecoverPlans = searchBorrowRecoverPlan(borrow.getBorrowNid(), borrowRepayPlan.getRepayPeriod());
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
                BorrowRecover borrowRecover = borrowRecoverList.get(i);
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
                        BorrowRecoverPlan borrowRecoverPlan = borrowRecoverPlans.get(j);
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
                                if (Validator.isNull(borrowRecover.getAccedeOrderId())){
                                    // 直投类项目债转还款
                                    List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, recoverNid, repayPeriod, 0);
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                                        CreditRepay creditRepay = null;
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
                                            CreditTender creditTender = this.getCreditTender(assignNid);// 查询相应的承接记录
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
                                }else{
                                    // 计划类项目债转还款
                                    List<HjhDebtCreditRepay> creditRepayList = this.selectHjhDebtCreditRepay(borrowNid, recoverNid, repayPeriod, borrowRecoverPlan.getRecoverStatus());
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                                        HjhDebtCreditRepay creditRepay = null;
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
                                        for (HjhDebtCreditRepay hjhDebtCreditRepayBean : creditRepayList) {
											hjhFlag++;
											sumCreditAccount = sumCreditAccount.add(hjhDebtCreditRepayBean.getRepayAccount());
										}
                                        //判断当前期是否全部承接
                                        boolean overFlag = isOverUndertake(borrowRecover,borrowRecoverPlan.getRecoverAccount(),sumCreditAccount,true,hjhFlag);
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            creditRepay = creditRepayList.get(k);
                                            creditRepayBean = new HjhDebtCreditRepayBean();
                                            String assignNid = creditRepay.getAssignOrderId();// 承接订单号
                                            HjhDebtCreditTender creditTender = this.getHjhDebtCreditTender(assignNid);// 查询相应的承接记录
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
     * 统计分期还款用户还款信息
     * @param borrowRepayPlan
     * @param borrow
     * @throws ParseException
     */
    private void calculateRecoverPlan(RepayDetailBean borrowRepayPlan, Borrow borrow) throws ParseException {

        // 项目编号
        String borrowNid = borrow.getBorrowNid();
        // 还款方式
        String borrowStyle = borrow.getBorrowStyle();
        // 管理费率
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        // 初审时间
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : borrow.getVerifyTime();
        // 项目总期数
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
        // 还款期数
        int repayPeriod = borrowRepayPlan.getRepayPeriod();
        List<BorrowRecover> borrowRecoverList = this.selectBorrowRecoverList(borrow.getBorrowNid());
        List<BorrowRecoverPlan> borrowRecoverPlans = searchBorrowRecoverPlan(borrow.getBorrowNid(), borrowRepayPlan.getRepayPeriod());
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
                BorrowRecover borrowRecover = borrowRecoverList.get(i);
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
                        BorrowRecoverPlan borrowRecoverPlan = borrowRecoverPlans.get(j);
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
                                    List<CreditRepay> creditRepayList = this.selectCreditRepayList(borrowNid, recoverNid, repayPeriod);
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
                                            CreditRepay creditRepay = creditRepayList.get(k);
                                            RepayCreditRepayBean creditRepayBean = new RepayCreditRepayBean();
                                            String assignNid = creditRepay.getAssignNid();// 承接订单号
                                            CreditTender creditTender = this.getCreditTender(assignNid);// 查询相应的承接记录
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
                                    List<HjhDebtCreditRepay> creditRepayList = this.selectHjhDebtCreditRepay(borrowNid, recoverNid, repayPeriod, borrowRecoverPlan.getRecoverStatus());
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
                                        for (HjhDebtCreditRepay hjhDebtCreditRepayBean : creditRepayList) {
                                            hjhFlag++;
                                            sumCreditAccount = sumCreditAccount.add(hjhDebtCreditRepayBean.getRepayAccount());
                                        }
                                        //判断当前期是否全部承接
                                        boolean overFlag = isOverUndertake(borrowRecover,borrowRecoverPlan.getRecoverAccount(),sumCreditAccount,true,hjhFlag);
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            HjhDebtCreditRepay creditRepay = creditRepayList.get(k);
                                            HjhDebtCreditRepayBean creditRepayBean = new HjhDebtCreditRepayBean();
                                            String assignNid = creditRepay.getAssignOrderId();// 承接订单号
                                            HjhDebtCreditTender creditTender = this.getHjhDebtCreditTender(assignNid);// 查询相应的承接记录
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

    /**
     * 根据项目编号，投资用户，订单号获取用户的放款总记录
     * @param borrowNid
     * @return
     */
    private List<BorrowRecover> selectBorrowRecoverList(String borrowNid) {
        BorrowRecoverExample example = new BorrowRecoverExample();
        BorrowRecoverExample.Criteria crt = example.createCriteria();
        crt.andBorrowNidEqualTo(borrowNid);
        List<BorrowRecover> borrowRecoverList = this.borrowRecoverMapper.selectByExample(example);
        return borrowRecoverList;
    }

    /**
     * 统计分期还款用户正常还款的总标
     * @param borrowRepayPlan
     * @param borrow
     * @param interestDay
     * @throws ParseException
     */
    private void calculateRecoverPlan(RepayDetailBean borrowRepayPlan, Borrow borrow, int interestDay) throws ParseException {

        // 项目编号
        String borrowNid = borrow.getBorrowNid();
        // 还款方式
        String borrowStyle = borrow.getBorrowStyle();
        // 管理费率
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        // 初审时间
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : borrow.getVerifyTime();
        // 项目总期数
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
        // 还款期数
        int repayPeriod = borrowRepayPlan.getRepayPeriod();
        List<BorrowRecover> borrowRecoverList = this.selectBorrowRecoverList(borrow.getBorrowNid());
        List<BorrowRecoverPlan> borrowRecoverPlans = searchBorrowRecoverPlan(borrow.getBorrowNid(), borrowRepayPlan.getRepayPeriod());
        List<RepayRecoverPlanBean> repayRecoverPlanList = new ArrayList<RepayRecoverPlanBean>();
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        if (borrowRecoverList != null && borrowRecoverList.size() > 0) {
            for (int i = 0; i < borrowRecoverList.size(); i++) {
                BorrowRecover borrowRecover = borrowRecoverList.get(i);
                String recoverNid = borrowRecover.getNid();// 投资订单号
                int recoverUserId = borrowRecover.getUserId();// 投资用户userId
                if (borrowRecoverPlans != null && borrowRecoverPlans.size() > 0) {
                    BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                    BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                    for (int j = 0; j < borrowRecoverPlans.size(); j++) {
                        BorrowRecoverPlan borrowRecoverPlan = borrowRecoverPlans.get(j);
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
                            		 List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, recoverNid, repayPeriod, 0);

                                     if (creditRepayList != null && creditRepayList.size() > 0) {
                                         List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                                         BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                         BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                         BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                         BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                         for (int k = 0; k < creditRepayList.size(); k++) {
                                             CreditRepay creditRepay = creditRepayList.get(k);
                                             RepayCreditRepayBean creditRepayBean = new RepayCreditRepayBean();
                                             String assignNid = creditRepay.getAssignNid();// 承接订单号
                                             CreditTender creditTender = this.getCreditTender(assignNid);// 查询相应的承接记录
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
                            		 List<HjhDebtCreditRepay> creditRepayList =this.selectHjhDebtCreditRepay(borrowNid, recoverNid, repayPeriod, borrowRecoverPlan.getRecoverStatus());
                                     if (creditRepayList != null && creditRepayList.size() > 0) {
                                         List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                                         BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                         BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                         BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                         BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                         BigDecimal sumCreditAccount = BigDecimal.ZERO;//总承接金额
                                         int hjhFlag = 0;
                                         for (HjhDebtCreditRepay hjhDebtCreditRepayBean : creditRepayList) {
 											hjhFlag++;
 											sumCreditAccount = sumCreditAccount.add(hjhDebtCreditRepayBean.getRepayAccount());
 										}
                                         //判断当前期是否全部承接
                                         boolean overFlag = isOverUndertake(borrowRecover,borrowRecoverPlan.getRecoverAccount(),sumCreditAccount,true,hjhFlag);
                                         for (int k = 0; k < creditRepayList.size(); k++) {
                                             HjhDebtCreditRepay creditRepay = creditRepayList.get(k);
                                             HjhDebtCreditRepayBean creditRepayBean = new HjhDebtCreditRepayBean();
                                             String assignNid = creditRepay.getAssignOrderId();// 承接订单号
                                             HjhDebtCreditTender creditTender = this.getHjhDebtCreditTender(assignNid);// 查询相应的承接记录
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

    private CreditTender getCreditTender(String assignNid) {
        CreditTenderExample example = new CreditTenderExample();
        CreditTenderExample.Criteria crt = example.createCriteria();
        crt.andAssignNidEqualTo(assignNid);
        List<CreditTender> creditTenderList = this.creditTenderMapper.selectByExample(example);
        if (creditTenderList != null && creditTenderList.size() == 1) {
            return creditTenderList.get(0);
        }
        return null;
    }

    /**
     * 统计分期还款用户提前还款的总标
     * @param borrowRepayPlan
     * @param borrow
     * @param advanceDays
     * @param repayTimeStart
     * @throws ParseException
     */
    private void calculateRecoverPlanAdvance(RepayDetailBean borrowRepayPlan, Borrow borrow, int advanceDays, String repayTimeStart) throws ParseException {
        int projectType = this.getBorrowInfoByNid(borrow.getBorrowNid()).getProjectType();
        String borrowNid = borrow.getBorrowNid();// 项目编号
        String borrowStyle = borrow.getBorrowStyle(); // 还款方式
        BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());// 管理费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());// 差异费率
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : borrow.getVerifyTime();// 初审时间
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod(); // 项目总期数
        int repayPeriod = borrowRepayPlan.getRepayPeriod();// 还款期数
        List<BorrowRecover> borrowRecoverList = this.selectBorrowRecoverList(borrow.getBorrowNid());
        List<BorrowRecoverPlan> borrowRecoverPlans = searchBorrowRecoverPlan(borrow.getBorrowNid(), repayPeriod);
        List<RepayRecoverPlanBean> repayRecoverPlanList = new ArrayList<RepayRecoverPlanBean>();
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        BigDecimal repayChargeInterest = BigDecimal.ZERO;// 提前还款利息
        if (borrowRecoverList != null && borrowRecoverList.size() > 0) {
            for (int i = 0; i < borrowRecoverList.size(); i++) {
                BorrowRecover borrowRecover = borrowRecoverList.get(i);
                String recoverNid = borrowRecover.getNid();// 投资订单号
                int recoverUserId = borrowRecover.getUserId();// 投资用户userId
                if (borrowRecoverPlans != null && borrowRecoverPlans.size() > 0) {
                    BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                    BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                    BigDecimal userChargeInterest = BigDecimal.ZERO;// 计算用户提前还款利息
                    for (int j = 0; j < borrowRecoverPlans.size(); j++) {
                        BorrowRecoverPlan borrowRecoverPlan = borrowRecoverPlans.get(j);
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
                            if (projectType == 13) {
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
                                if(isStyle){
                                    if(advanceDays >= 30){
                                        userChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(userInterest,totalDays);
                                    }else{
                                        userChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(userInterest,advanceDays);
                                    }
                                }else{
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
                                if(Validator.isNull(borrowRecover.getAccedeOrderId())){
                                    List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, recoverNid, repayPeriod, 0);
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            CreditRepay creditRepay = creditRepayList.get(k);
                                            RepayCreditRepayBean creditRepayBean = new RepayCreditRepayBean();
                                            String assignNid = creditRepay.getAssignNid();// 承接订单号
                                            CreditTender creditTender = this.getCreditTender(assignNid);// 查询相应的承接记录
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
                                                if (projectType == 13) {
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
                                                    if(isStyle){
                                                        if(advanceDays >= 30){
                                                            assignChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(assignInterest,totalDays);
                                                        }else{
                                                            assignChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(assignInterest,advanceDays);
                                                        }
                                                    }else {
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
                                        if(isStyle){
                                            if(advanceDays >= 30){
                                                userChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(userInterest,totalDays);
                                            }else{
                                                userChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(userInterest,advanceDays);
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
                                }else{
                                    // 计划类项目还款
                                    List<HjhDebtCreditRepay> creditRepayList = this.selectHjhDebtCreditRepay(borrowNid, recoverNid, repayPeriod, borrowRecoverPlan.getRecoverStatus());
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户還款管理費
                                        BigDecimal sumCreditAccount = BigDecimal.ZERO;//总承接金额
                                        int hjhFlag = 0;
                                        for (HjhDebtCreditRepay hjhDebtCreditRepayBean : creditRepayList) {
											hjhFlag++;
											sumCreditAccount = sumCreditAccount.add(hjhDebtCreditRepayBean.getRepayAccount());
										}
                                        //判断当前期是否全部承接
                                        boolean overFlag = isOverUndertake(borrowRecover,borrowRecoverPlan.getRecoverAccount(),sumCreditAccount,true,hjhFlag);
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            HjhDebtCreditRepay creditRepay = creditRepayList.get(k);
                                            HjhDebtCreditRepayBean creditRepayBean = new HjhDebtCreditRepayBean();
                                            String assignNid = creditRepay.getAssignOrderId();// 承接订单号
                                            HjhDebtCreditTender creditTender = this.getHjhDebtCreditTender(assignNid);// 查询相应的承接记录
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
                                                if (projectType == 13) {
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
                                                    if(isStyle){
                                                        if(advanceDays >= 30){
                                                            assignChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(assignInterest,totalDays);
                                                        }else{
                                                            assignChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(assignInterest,advanceDays);
                                                        }
                                                    }else{
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
                                        if(isStyle){
                                            if(advanceDays >= 30){
                                                userChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(userInterest,totalDays);
                                            }else{
                                                userChargeInterest = UnnormalRepayUtils.aheadEndMonthRepayChargeInterest(userInterest,advanceDays);
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
     * 查询投资用户分期的详情
     *
     * @param borrowNid
     * @param period
     * @return
     */
    private List<BorrowRecoverPlan> searchBorrowRecoverPlan(String borrowNid, int period) {
        BorrowRecoverPlanExample example = new BorrowRecoverPlanExample();
        BorrowRecoverPlanExample.Criteria crt = example.createCriteria();
        crt.andBorrowNidEqualTo(borrowNid);
        crt.andRecoverPeriodEqualTo(period);
        List<BorrowRecoverPlan> borrowRecovers = borrowRecoverPlanMapper.selectByExample(example);
        return borrowRecovers;
    }

    /**
     * 计划类债转
     * 根据承接订单号获取承接记录
     * @param assignNid
     * @return
     */
    private HjhDebtCreditTender getHjhDebtCreditTender(String assignNid) {
        HjhDebtCreditTenderExample example = new HjhDebtCreditTenderExample();
        HjhDebtCreditTenderExample.Criteria crt = example.createCriteria();
        crt.andAssignOrderIdEqualTo(assignNid);
        List<HjhDebtCreditTender> creditTenderList = this.hjhDebtCreditTenderMapper.selectByExample(example);
        if (creditTenderList != null && creditTenderList.size() == 1) {
            return creditTenderList.get(0);
        }
        return null;
    }

    /**
     * 查询相应的债转还款记录
     *
     * @param borrowNid
     * @param tenderOrderId
     * @param periodNow
     * @return
     */
    private List<CreditRepay> selectCreditRepayList(String borrowNid, String tenderOrderId, Integer periodNow) {
        CreditRepayExample example = new CreditRepayExample();
        CreditRepayExample.Criteria crt = example.createCriteria();
        crt.andBidNidEqualTo(borrowNid);
        crt.andCreditTenderNidEqualTo(tenderOrderId);
        crt.andRecoverPeriodEqualTo(periodNow);
        example.setOrderByClause("id ASC");
        List<CreditRepay> creditRepayList = this.creditRepayMapper.selectByExample(example);
        return creditRepayList;
    }

    /**
     * 更新借款API任务表
     * @auther: hesy
     * @date: 2018/7/17
     */
    @Override
    public boolean updateBorrowApicron(BorrowApicron apicron, int status){
        //更新api表状态
        String borrowNid = apicron.getBorrowNid();
        BorrowApicronExample example = new BorrowApicronExample();
        example.createCriteria().andIdEqualTo(apicron.getId()).andStatusEqualTo(apicron.getStatus());
        apicron.setStatus(status);
        apicron.setUpdateTime(GetDate.getNowTime());
        borrowApicronMapper.updateByExampleSelective(apicron, example);

        //更新borrow表状态
        Borrow borrow = this.getBorrow(borrowNid);
        borrow.setRepayStatus(status);
        this.borrowMapper.updateByPrimaryKey(borrow);

        return true;
    }
}
