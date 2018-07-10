package com.hyjf.am.trade.service.impl.repay;

import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.resquest.trade.RepayRequestUpdateRequest;
import com.hyjf.am.trade.bean.repay.*;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.trade.EmployeeCustomize;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.trade.service.repay.BankRepayFreezeLogService;
import com.hyjf.am.trade.service.repay.RepayManageService;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Autowired
    BankRepayFreezeLogService bankRepayFreezeLogService;

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

}
