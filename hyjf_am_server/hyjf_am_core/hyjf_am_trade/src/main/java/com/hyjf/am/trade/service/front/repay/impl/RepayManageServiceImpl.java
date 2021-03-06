package com.hyjf.am.trade.service.front.repay.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.resquest.user.WebUserRepayTransferRequest;
import com.hyjf.am.trade.bean.repay.*;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.EmployeeCustomize;
import com.hyjf.am.trade.dao.model.customize.WebUserRepayTransferCustomize;
import com.hyjf.am.trade.dao.model.customize.WebUserTransferBorrowInfoCustomize;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.front.repay.RepayManageService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.am.vo.trade.repay.RepayPlanListVO;
import com.hyjf.am.vo.trade.repay.SponsorLogCustomizeVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.FormatRateUtil;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.AccountManagementFeeUtils;
import com.hyjf.common.util.calculate.UnnormalRepayUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * 还款管理
 * @author hesy
 * @version RepayManageServiceImpl, v0.1 2018/6/26 18:15
 */
@Service
public class RepayManageServiceImpl extends BaseServiceImpl implements RepayManageService {

    @Autowired
    private CommonProducer commonProducer;

    //初始化放款/承接时间(大于2018年3月28号法大大上线时间)
    private static final int ADD_TIME = 1922195200;

    //放款/承接时间(2018-3-28法大大上线时间）
    private static final int ADD_TIME328 = 1522195200;

    /**
     * 普通借款人管理费总待还
     * @param userId
     * @return
     */
    @Override
    public BigDecimal selectUserRepayFeeWaitTotal(Integer userId){
        BigDecimal waitManageFee = webUserRepayListCustomizeMapper.getWaitRepayManageFee(userId);
        BigDecimal waitPlanManageFee = webUserRepayListCustomizeMapper.getWaitRepayPlanManageFee(userId);
        return waitManageFee.add(waitPlanManageFee);
    }

    /**
     * 普通借款人逾期利息总待还
     * @param userId
     * @return
     */
    @Override
    public BigDecimal selectUserLateInterestWaitTotal(Integer userId){
        BigDecimal lateInterest = webUserRepayListCustomizeMapper.getWaitRepayLateInterest(userId);
        BigDecimal latePlanInterest = webUserRepayListCustomizeMapper.getWaitRepayPlanLateInterest(userId);
        return lateInterest.add(latePlanInterest);
    }

    /**
     * 普通借款人总借款金额
     * @param userId
     * @return
     */
    @Override
    public BigDecimal selectUserBorrowAccountTotal(Integer userId){
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("userId", userId);
        BigDecimal accountTotal = repayManageCustomizeMapper.selectUserBorrowAccountTotal(paraMap);
        return accountTotal;
    }

    /**
     * 担保机构管理费总待还
     * @param userId
     * @return
     */
    @Override
    public BigDecimal selectOrgRepayFeeWaitTotal(Integer userId){
        BigDecimal waitManageFee = webUserRepayListCustomizeMapper.getOrgWaitRepayManageFee(userId);
        BigDecimal waitPlanManageFee = webUserRepayListCustomizeMapper.getOrgWaitRepayPlanManageFee(userId);
        return waitManageFee.add(waitPlanManageFee);
    }

    /**
     * 担保机构逾期利息总待还
     * @param userId
     * @return
     */
    @Override
    public BigDecimal selectOrgLateInterestWaitTotal(Integer userId){
        BigDecimal lateInterest = webUserRepayListCustomizeMapper.getOrgWaitRepayLateInterest(userId);
        BigDecimal latePlanInterest = webUserRepayListCustomizeMapper.getOrgWaitRepayPlanLateInterest(userId);
        return lateInterest.add(latePlanInterest);
    }

    /**
     * 担保机构总待还
     * @param userId
     * @return
     */
    @Override
    public Map<String, BigDecimal> selectRepayOrgRepaywait(Integer userId){
        return repayManageCustomizeMapper.selectRepayOrgRepaywait(userId);
    }

    /**
     * 还款计划列表
     * @param borrowNid
     * @return
     */
    @Override
    public List<RepayPlanListVO> selectRepayPlanList(String borrowNid){
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("borrowNid", borrowNid);
        Borrow borrow = getBorrowByNid(borrowNid);

        List<RepayPlanListVO> resultList = null;
        if("end".equals(borrow.getBorrowStyle()) || "endday".equals(borrow.getBorrowStyle())){
            resultList = repayManageCustomizeMapper.selectRepayNotPlanList(paraMap);
        }else{
            resultList = repayManageCustomizeMapper.selectRepayPlanList(paraMap);
        }

        if(resultList == null){
            return new ArrayList<>();
        }
        return resultList;
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
                RepayListCustomizeVO record = list.get(i);
                // 查询承接条数(标的发生过债转,并且已有人承接)
                int listCount = 0;
                if (org.apache.commons.lang3.StringUtils.isNotEmpty(record.getPlanNid())){
                    listCount =  webUserRepayListCustomizeMapper.selectUserRepayTransferListTotalByHjhCreditTender(record.getBorrowNid());
                }else {
                    listCount = webUserRepayListCustomizeMapper.selectUserRepayTransferListTotalByCreditTender(record.getBorrowNid());
                }

                // 承接条数
                record.setListCount(listCount);

                BigDecimal accountFee = BigDecimal.ZERO;
                BigDecimal borrowTotal = BigDecimal.ZERO;
                BigDecimal realAccountTotal = BigDecimal.ZERO;
                BigDecimal allAccountFee = BigDecimal.ZERO;
                BigDecimal serviceFee = BigDecimal.ZERO;
                if (StringUtils.isNotBlank(record.getRepayFee())) {
                    accountFee = new BigDecimal(record.getRepayFee());
                }
                if (StringUtils.isNotBlank(record.getBorrowTotal())) {
                    borrowTotal = new BigDecimal(record.getBorrowTotal());
                }
                if (StringUtils.isNotBlank(record.getRealAccountYes())) {
                    realAccountTotal = new BigDecimal(record.getRealAccountYes());
                }
                if (StringUtils.isNotBlank(record.getAllRepayFee())) {
                    allAccountFee = new BigDecimal(record.getAllRepayFee());
                }
                if (StringUtils.isNotBlank(record.getServiceFee())) {
                    serviceFee = new BigDecimal(record.getServiceFee());
                }
                BigDecimal oldYesAccount = new BigDecimal(record.getYesAccount());
                BigDecimal yesAccount = oldYesAccount.subtract(serviceFee);
                record.setYesAccount(yesAccount.toString());
                record.setBorrowTotal(borrowTotal.add(allAccountFee).toString());
                record.setRealAccountYes(realAccountTotal.add(accountFee).toString());

                //当前还款期数页面展示
                if("end".equals(record.getBorrowStyle()) || "endday".equals(record.getBorrowStyle())){ //不分期
                    record.setCurrentPeriodView("1期");
                }else{ //分期
                    record.setCurrentPeriodView(record.getCurrentPeriod() + "/" + record.getBorrowPeriodInt() + "期");
                }
            }
        }

        for(RepayListCustomizeVO record : list){
            List<BorrowTender> tenderList = this.getBorrowTender(record.getBorrowNid());
            for(BorrowTender tender : tenderList){
                int addTime = ADD_TIME;
                List<TenderAgreement> agreementList = this.getTenderAgreement(tender.getNid());
                if(agreementList !=null && !agreementList.isEmpty()){
                    TenderAgreement tenderAgreement = agreementList.get(0);
                    Integer fddStatus = tenderAgreement.getStatus();
                    //法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功
                    if(fddStatus.equals(3)){
                        record.setFddStatus(1);
                    }else {
                        //隐藏下载按钮
                        record.setFddStatus(2);
                    }
                }else {
                    /**
                     * 1.2018年3月28号以后出借（放款时间/承接时间为准）生成的协议(法大大签章协议）如果协议状态不是"下载成功"时 点击下载按钮提示“协议生成中”。
                     * 2.2018年3月28号以前出借（放款时间/承接时间为准）生成的协议(CFCA协议）点击下载CFCA协议。
                     * 3.智投中承接债转，如果债转协议中有2018-3-28之前的，2018-3-28之前承接的下载CFCA债转协议，2018-3-28之后承接的下载法大大债转协议。
                     */
                    //根据订单号获取用户放款信息
                    BorrowRecover borrowRecover =  selectBorrowRecoverByNid(tender.getNid());
                    if(borrowRecover != null){
                        //放款记录创建时间（放款时间）
                        addTime = (borrowRecover.getCreateTime() == null? 0 : GetDate.getTime10(borrowRecover.getCreateTime()));
                    }
                    if (addTime<ADD_TIME328) {
                        //下载老版本协议
                        record.setFddStatus(1);
                    } else {
                        //隐藏下载按钮
                        record.setFddStatus(0);
                    }
                }
            }
        }

        return list;
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
     * 获取出借协议
     */
    private List<TenderAgreement> getTenderAgreement(String tenderNid) {
        TenderAgreementExample example = new TenderAgreementExample();
        example.createCriteria().andTenderNidEqualTo(tenderNid);
        List<TenderAgreement> tenderAgreements= this.tenderAgreementMapper.selectByExample(example);

        if(tenderAgreements != null && tenderAgreements.size()>0){
            return tenderAgreements;
        }
        return null;
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
            	//格式化利率
            	 list.get(i).setBorrowInterest( FormatRateUtil.formatBorrowApr(list.get(i).getBorrowInterest()));
                RepayListCustomizeVO info = list.get(i);
                //获得标的类型
                String borrowStyle = info.getBorrowStyle();

                BigDecimal accountFee = BigDecimal.ZERO;
                BigDecimal borrowTotal = BigDecimal.ZERO;
                BigDecimal realAccountTotal = BigDecimal.ZERO;
                BigDecimal allAccountFee = BigDecimal.ZERO;
//                BigDecimal serviceFee = BigDecimal.ZERO;
                if (StringUtils.isNotBlank(info.getRepayFee())) {
                    accountFee = new BigDecimal(info.getRepayFee());
                }
                if (StringUtils.isNotBlank(info.getBorrowTotal())) {
                    borrowTotal = new BigDecimal(info.getBorrowTotal());
                }
                if (StringUtils.isNotBlank(info.getRealAccountYes())) {
                    realAccountTotal = new BigDecimal(info.getRealAccountYes());
                }
                if (StringUtils.isNotBlank(info.getAllRepayFee())) {
                    allAccountFee = new BigDecimal(info.getAllRepayFee());
                }
//                if (StringUtils.isNotBlank(info.getServiceFee())) {
//                    serviceFee = new BigDecimal(info.getServiceFee());
//                }
//                BigDecimal oldYesAccount = new BigDecimal(info.getYesAccount()==null?"0":info.getYesAccount());
//                BigDecimal yesAccount = oldYesAccount.subtract(serviceFee);
//                info.setYesAccount(yesAccount.toString());
                info.setBorrowTotal(borrowTotal.add(allAccountFee).toString());
                info.setRealAccountYes(realAccountTotal.add(accountFee).toString());

                if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)||CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {//日标
                    info.setOrgBorrowPeriod("1");
                }else{//月标 获取当前应还期数
                    if (org.apache.commons.lang3.StringUtils.isBlank(info.getBorrowAllPeriod())) {
                        info.setBorrowAllPeriod("0");
                    }
                    if (org.apache.commons.lang3.StringUtils.isBlank(info.getRepayPeriod())) {
                        info.setRepayPeriod("0");
                    }
                    int borrowPeriod = Integer.parseInt(info.getBorrowAllPeriod());
                    int repayPeriod = Integer.parseInt(info.getRepayPeriod());
                    int orgBorrowPeriod = borrowPeriod - repayPeriod + 1;
                    info.setOrgBorrowPeriod(orgBorrowPeriod+"");
                }

                //当前还款期数页面展示
                if("end".equals(info.getBorrowStyle()) || "endday".equals(info.getBorrowStyle())){ //不分期
                    info.setCurrentPeriodView("1期");
                }else{ //分期
                    info.setCurrentPeriodView(info.getCurrentPeriod() + "/" + info.getBorrowPeriodInt() + "期");
                }
            }
        }

        for(RepayListCustomizeVO record : list){
            List<BorrowTender> tenderList = this.getBorrowTender(record.getBorrowNid());
            for(BorrowTender tender : tenderList){
                //放款时间
                int addTime = ADD_TIME;
                List<TenderAgreement> agreementList = this.getTenderAgreement(tender.getNid());
                if(agreementList !=null && !agreementList.isEmpty()){
                    TenderAgreement tenderAgreement = agreementList.get(0);
                    Integer fddStatus = tenderAgreement.getStatus();
                    //法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功
                    if(fddStatus.equals(3)){
                        record.setFddStatus(1);
                    }else {
                        //隐藏下载按钮
                        record.setFddStatus(2);
                    }
                }else {
                    /**
                     * 1.2018年3月28号以后出借（放款时间/承接时间为准）生成的协议(法大大签章协议）如果协议状态不是"下载成功"时 点击下载按钮提示“协议生成中”。
                     * 2.2018年3月28号以前出借（放款时间/承接时间为准）生成的协议(CFCA协议）点击下载CFCA协议。
                     * 3.智投中承接债转，如果债转协议中有2018-3-28之前的，2018-3-28之前承接的下载CFCA债转协议，2018-3-28之后承接的下载法大大债转协议。
                     */
                    //根据订单号获取用户放款信息
                    BorrowRecover borrowRecover =  selectBorrowRecoverByNid(tender.getNid());
                    if(borrowRecover != null){
                        //放款记录创建时间（放款时间）
                        addTime = (borrowRecover.getCreateTime() == null? 0 : GetDate.getTime10(borrowRecover.getCreateTime()));
                    }
                    if (addTime<ADD_TIME328) {
                        //下载老版本协议
                        record.setFddStatus(1);
                    } else {
                        //隐藏下载按钮
                        record.setFddStatus(0);
                    }
                }
            }
        }

        return list;
    }

    /**
     * 垫付机构本期应还总额
     * @param requestBean
     * @return
     */
    @Override
    public BigDecimal selectOrgRepayWaitCurrent(RepayListRequest requestBean){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", requestBean.getUserId());
        param.put("status", requestBean.getStatus());
        param.put("repayStatus", requestBean.getRepayStatus());
        param.put("startDate", requestBean.getStartDate());
        param.put("endDate", requestBean.getEndDate());
        param.put("repayTimeOrder", requestBean.getRepayTimeOrder());
        param.put("checkTimeOrder", requestBean.getCheckTimeOrder());
        param.put("borrowNid", requestBean.getBorrowNid());

        BigDecimal waitTotal =  repayManageCustomizeMapper.selectOrgRepayWaitTotal(param);

        return waitTotal;
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

        List<RepayListCustomizeVO> list = repayManageCustomizeMapper.searchOrgRepayedList(param);

        for(RepayListCustomizeVO record : list){
            //当前还款期数页面展示
            if("end".equals(record.getBorrowStyle()) || "endday".equals(record.getBorrowStyle())){ //不分期
                record.setCurrentPeriodView("1期");
            }else{ //分期
                record.setCurrentPeriodView(record.getCurrentPeriod() + "/" + record.getBorrowPeriodInt() + "期");
            }

            List<BorrowTender> tenderList = this.getBorrowTender(record.getBorrowNid());
            for(BorrowTender tender : tenderList){
                //放款时间
                int addTime = ADD_TIME;
                List<TenderAgreement> agreementList = this.getTenderAgreement(tender.getNid());
                if(agreementList !=null && !agreementList.isEmpty()){
                    TenderAgreement tenderAgreement = agreementList.get(0);
                    Integer fddStatus = tenderAgreement.getStatus();
                    //法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功
                    if(fddStatus.equals(3)){
                        record.setFddStatus(1);
                    }else {
                        //隐藏下载按钮
                        record.setFddStatus(2);
                    }
                }else {
                    /**
                     * 1.2018年3月28号以后出借（放款时间/承接时间为准）生成的协议(法大大签章协议）如果协议状态不是"下载成功"时 点击下载按钮提示“协议生成中”。
                     * 2.2018年3月28号以前出借（放款时间/承接时间为准）生成的协议(CFCA协议）点击下载CFCA协议。
                     * 3.智投中承接债转，如果债转协议中有2018-3-28之前的，2018-3-28之前承接的下载CFCA债转协议，2018-3-28之后承接的下载法大大债转协议。
                     */
                    //根据订单号获取用户放款信息
                    BorrowRecover borrowRecover =  selectBorrowRecoverByNid(tender.getNid());
                    if(borrowRecover != null){
                        //放款记录创建时间（放款时间）
                        addTime = (borrowRecover.getCreateTime() == null? 0 : GetDate.getTime10(borrowRecover.getCreateTime()));
                    }
                    if (addTime<ADD_TIME328) {
                        //下载老版本协议
                        record.setFddStatus(1);
                    } else {
                        //隐藏下载按钮
                        record.setFddStatus(0);
                    }
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

        Integer count = repayManageCustomizeMapper.selectOrgRepayedCount(param);

        return count;
    }

    /**
     *  查询用户还款详情
     * @param isAllRepay            是否一次性还款
     * @param latePeriod       用户选择的逾期数
     * isAllRepay = 1  一次性还款
     * 添加逾期可以一次性还款规则
     * isAllRepay = 0 & latePeriod = 0 当前期还款(包含之前的逾期)
     * 1、修改当前期判断规则：有逾期时，当前期需要根据日期判断，之前是根据repay表判断
     * 2、添加逾期可以当前期还款规则
     * isAllRepay = 0 & latePeriod != 0 逾期还款(第latePeriod期及之前期)
     * 1、判断第latePeriod期是否逾期 非逾期且不是当前期 报错；非逾期是当前期走非逾期当前期还款规则
     * 2、逾期情况，累加第latePeriod期和之前未还款逾期的金额
     * @return
     */
    @Override
    public ProjectBean searchRepayProjectDetail(ProjectBean form, boolean isAllRepay, int latePeriod) throws Exception {
        if (form == null || StringUtils.isBlank(form.getBorrowNid()) || StringUtils.isBlank(form.getUserId())) {
            logger.error("【还款详情】借款编号或用户ID为空！");
            return null;
        }
        List<Integer> lateArray = new ArrayList<>();
        String borrowNid = form.getBorrowNid();
        String userId = form.getUserId();
        Borrow borrow = this.getBorrowByNid(borrowNid);
        BorrowInfo borrowInfo = this.getBorrowInfoByNid(borrowNid);
        if(borrow == null || borrowInfo == null){
            logger.error("【还款详情】借款编号：{}，未查询到数据！", borrowNid);
            return null;
        }
        if (!checkBorrowUser(form.getRoleId(), userId, borrow, borrowInfo)) {
            logger.error("【还款详情】借款编号：{}，当前用户与标的不匹配，不能进行还款！用户ID：{}", borrowNid, userId);
            return null;
        }
        // userId 改成借款人的userid！！！
        userId = borrow.getUserId().toString();
        form.settType("0");// 设置为非汇添金专属项目
        // 设置相应的项目名称
        form.setBorrowName(borrowInfo.getProjectName());
        // 获取相应的项目还款方式
        String borrowStyle = StringUtils.isNotBlank(borrow.getBorrowStyle()) ? borrow.getBorrowStyle() : null;
        form.setBorrowStyle(borrowStyle);
        form.setBorrowPeriod(borrow.getBorrowPeriod().toString());// 标的总还款期数
        form.setAllRepay("0");// 是否一次性还款
        form.setRepayStatus("0");// 是否正在还款
        form.setOnlyAllRepay("0");// 是否只能一次性还款
        //form.setLatePeriod(0);// 提交的逾期期数
        int lastPeriod = 0;// 最后一期还款
        StringBuilder allPeriodStr = new StringBuilder("第");// 当前还款期数
        BorrowApicronExample exampleBorrowApicron = new BorrowApicronExample();
        BorrowApicronExample.Criteria crtBorrowApicron = exampleBorrowApicron.createCriteria();
        crtBorrowApicron.andBorrowNidEqualTo(borrowNid);
        crtBorrowApicron.andApiTypeEqualTo(1);
        crtBorrowApicron.andStatusNotEqualTo(6);// 不是已经还款的，正在还款的
        exampleBorrowApicron.setOrderByClause("period_now");
        List<BorrowApicron> borrowApicrons = borrowApicronMapper.selectByExample(exampleBorrowApicron);
        // 有正在还款，查看是否是一次结清，适用分期和一次性还款方式，   下面逻辑中的分期最后一期继续适用
        if (borrowApicrons != null && borrowApicrons.size() > 0) {
            for (int i = 0; i < borrowApicrons.size(); i++) {
                BorrowApicron borrowApicron = borrowApicrons.get(i);
                if (i == borrowApicrons.size() - 1) {
                    lastPeriod = borrowApicron.getPeriodNow();
                }
                boolean isLate = borrowApicron.getIsLate() != null && borrowApicron.getIsLate().intValue() == 1 ? true : false;
                if(isLate){// 添加逾期期数
                    latePeriod = borrowApicron.getPeriodNow();// 提交的逾期期数
                } else {
                    latePeriod = 0;// 有提交的未逾期还款，说明时当期还款或者一次性还款，返回0
                }
                if(!isAllRepay && borrowApicron.getIsAllrepay() != null && borrowApicron.getIsAllrepay().intValue() == 1){
                    form.setAllRepay("1");// 属于一次性还款
                    isAllRepay = true;
                    latePeriod = -1;
                }
            }
            // 能查到，无论如何都是正在还款
            form.setRepayStatus("1");
        }
        if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle) || CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
            // 不分期还款
            RepayBean repay = this.calculateRepay(Integer.parseInt(userId), borrow);
            setRecoverDetail(form, userId, borrow, repay);
        } else {
            RepayBean repayByTerm = new RepayBean();
            BorrowRepay borrowRepay = this.searchRepay(Integer.parseInt(userId), borrow.getBorrowNid());
            // 获取相应的还款信息
            BeanUtils.copyProperties(borrowRepay, repayByTerm);
            repayByTerm.setBorrowPeriod(String.valueOf(borrow.getBorrowPeriod()));
            // 计算当前最早需要还款的还款期数
            int period = borrow.getBorrowPeriod() - borrowRepay.getRepayPeriod() + 1;
            if(latePeriod > 0 && period > latePeriod){
                logger.error("【还款详情】传入的第{}期逾期还款已还款！最新还款期数：第{}期。", latePeriod, period);
                throw new Exception("传入的还款数据有误！");
            }
            if (period > 1) {// 至少还了一期
                // 有逾期的情况一定不会出现超前还款，所以直接判断前一期的还款时间
                BorrowRepayPlan borrowRepayPlan = this.searchRepayPlan(Integer.parseInt(userId), borrowNid, period - 1);
                Integer repayTimeStart = borrowRepayPlan.getRepayTime();
                int curPlanStart = GetDate.getIntYYMMDD(repayTimeStart);
                int nowDate = GetDate.getIntYYMMDD(new Date());
                // 超前还款的情况，只能一次性还款
                if (nowDate <= curPlanStart) {
                    form.setOnlyAllRepay("1");
                    isAllRepay = true;
                }
            }
            allPeriodStr.append(period);
            // 计算分期的项目还款信息
            if (isAllRepay || latePeriod == -1) {
                // 全部结清的
                // 分期 当前期 计算，如果当前期没有还款，则先算当前期，后算所有剩下的期数
                this.calculateRepayPlanAll(repayByTerm, borrow, period);
                setRecoverPlanAllDetail(form, borrow, repayByTerm);
                if (lastPeriod == 0) {
                    lastPeriod = borrow.getBorrowPeriod();
                }
            } else if (latePeriod > 0) {
                // 逾期部分还款
                this.calculateRepayPlanPart(repayByTerm, borrow, period, latePeriod);
                setRecoverPlanPartDetail(form, isAllRepay, userId, borrow, repayByTerm, latePeriod);
                if (lastPeriod == 0) {
                    lastPeriod = latePeriod;
                }
            } else {
                // 当期还款
                this.calculateRepayPlan(repayByTerm, borrow, period);
                setRecoverPlanDetail(form, isAllRepay, userId, borrow, repayByTerm);
                if (lastPeriod == 0) {
                    lastPeriod = Integer.parseInt(form.getRepayPeriod());
                }
            }
            if(period != lastPeriod){
                allPeriodStr.append("至").append(lastPeriod);
            }
            allPeriodStr.append("期");
            if(latePeriod == -1 || lastPeriod == borrow.getBorrowPeriod()) {
                allPeriodStr.append("，全部结清");
            }
            form.setAllPeriodStr(allPeriodStr.toString());
        }

        return form;
    }

    private boolean checkBorrowUser(String roleId, String userId, Borrow borrow, BorrowInfo borrowInfo) {
        if (StringUtils.isNotEmpty(roleId) && "3".equals(roleId)) {
            // 垫付机构
            if (!borrowInfo.getRepayOrgUserId().equals(Integer.parseInt(userId))) {
                return false;
            }
        } else {
            // 普通借款人
            if (!borrow.getUserId().equals(Integer.parseInt(userId))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 计算单期的总的还款信息
     *
     * @param userId
     * @param borrow
     * @return
     * @throws Exception
     */
    @Override
    public RepayBean calculateRepay(int userId, Borrow borrow) throws Exception {

        RepayBean repay = new RepayBean();
        // 获取还款总表数据
        BorrowRepay borrowRepay = this.searchRepay(userId, borrow.getBorrowNid());
        // 判断是否存在还款数据
        if (borrowRepay != null) {
            // 获取相应的还款信息
            BeanUtils.copyProperties(borrowRepay, repay);
            // 计划还款时间
            Integer repayTimeInt = borrowRepay.getRepayTime();
            repay.setBorrowPeriod(String.valueOf(borrow.getBorrowPeriod()));
            // 未分期默认传分期为0
            this.calculateRecover(repay, borrow, repayTimeInt);
        }
        return repay;
    }

    /**
     * 计算单期的用户的还款信息
     *
     * @param repay
     * @param borrow
     * @param repayTimeInt
     * @throws ParseException
     */
    private void calculateRecover(RepayBean repay, Borrow borrow, Integer repayTimeInt) throws Exception {
        int time = GetDate.getNowTime10();
        // 用户计划还款时间
        String repayTime = GetDate.getDateTimeMyTimeInMillis(repayTimeInt);
        // 用户实际还款时间
        String factRepayTime = GetDate.getDateTimeMyTimeInMillis(time);
        int distanceDays = GetDate.daysBetween(factRepayTime, repayTime);
        String borrowStyle = borrow.getBorrowStyle();
        if (distanceDays < 0) {// 用户逾期了
            this.calculateRecoverTotalLate(repay, borrow, -distanceDays);
        } else {// 用户正常或者提前还款
            // 获取提前还款的阀值
            String repayAdvanceDay = this.getBorrowConfig("REPAY_ADVANCE_TIME");
            int advanceDays = distanceDays;
            // 用户提前还款
            //如果是融通宝项目,不判断提前还款的阀值 add by cwyang 2017-6-14
            int projectType = borrow.getProjectType();
            if (13 == projectType || CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                repayAdvanceDay = "0";

                if (Integer.parseInt(repayAdvanceDay) < advanceDays) {
                    // 计算用户提前还款总额
                    this.calculateRecoverTotalAdvance(repay, borrow, advanceDays);
                } else {// 用户正常还款
                    // 计算用户实际还款总额
                    this.calculateRecoverTotal(repay, borrow, advanceDays);
                }
            } else if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                if (Integer.parseInt(repayAdvanceDay) <= advanceDays) {
                    // 计算用户提前还款总额
                    this.calculateRecoverTotalAdvance(repay, borrow, advanceDays);
                } else {// 用户正常还款
                    // 计算用户实际还款总额
                    this.calculateRecoverTotal(repay, borrow, advanceDays);
                }

            }
        }
    }

    /**
     * 统计单期还款用户提前还款的总标
     */
    private void calculateRecoverTotalAdvance(RepayBean repay, Borrow borrow, int interestDay) throws ParseException {

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
        BigDecimal repayChargeInterest = BigDecimal.ZERO;// 提前还款实际减息
        BigDecimal repayChargePenaltyInterest = BigDecimal.ZERO;// 提前还款罚息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
//        int time = GetDate.getNowTime10(); // 现在时间
        String factRepayTime = GetDate.getDayStart(new Date());
        //获取标的总的提前减息利息

//        BigDecimal totalChargeInterest = new BigDecimal(0);
//        // 实际持有天数
//        int totalAcctualDays = GetDate.daysBetween(GetDate.getDayStart(GetDate.getDate(repay.getCreateTime())),factRepayTime);
//        // 用户提前还款减少的利息
//        BigDecimal tatalAcctualInterest = UnnormalRepayUtils.aheadEndRepayInterest(repay.getRepayCapital(), borrow.getBorrowApr(), totalAcctualDays);
//        totalChargeInterest = repay.getRepayInterest().subtract(tatalAcctualInterest);
//        if(tatalAcctualInterest.compareTo(repay.getRepayInterest()) >= 0){
//            totalChargeInterest = BigDecimal.ZERO;
//        }

        List<BorrowRecover> borrowRecovers = this.getBorrowRecover(borrow.getBorrowNid());
        if (borrowRecovers != null && borrowRecovers.size() > 0) {
            List<RepayRecoverBean> repayRecoverList = new ArrayList<RepayRecoverBean>();
            BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
            BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
            BigDecimal userChargePenaltyInterest = BigDecimal.ZERO;// 计算用户提前还款的罚息
            BigDecimal userManageFee = BigDecimal.ZERO;// 获取应还款管理费
            for (int i = 0; i < borrowRecovers.size(); i++) {
                BorrowRecover borrowRecover = borrowRecovers.get(i);
                RepayRecoverBean repayRecoverBean = new RepayRecoverBean();
                String tenderOrderId = borrowRecover.getNid();// 出借订单号
                String recoverTime = GetDate.getDateTimeMyTimeInMillis(borrowRecover.getRecoverTime());
                String createTime = GetDate.getDateTimeMyTimeInMillis(borrowRecover.getCreateTime());
                int totalDays = GetDate.daysBetween(createTime, recoverTime);// 获取这两个时间之间有多少天
                // 计算出借用户实际获得的本息和
                userAccount = borrowRecover.getRecoverAccount();
                userCapital = borrowRecover.getRecoverCapital();
                userInterest = borrowRecover.getRecoverInterest();

                repayRecoverBean.setRecoverCapitalOld(userCapital);
                repayRecoverBean.setCreditAmountOld(borrowRecover.getCreditAmount());
                // 如果项目类型为融通宝，调用新的提前还款利息计算公司
                if (borrow.getProjectType() == 13) {
                    // 提前还款不应该大于本次计息时间
                    if (totalDays < interestDay) {
                        // 用户提前还款减少的利息
                        userChargeInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(userCapital, borrow.getBorrowApr(), totalDays);
                    } else {
                        // 用户提前还款减少的利息
                        userChargeInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(userCapital, borrow.getBorrowApr(), interestDay);
                    }
                } else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                    int carryDays = totalDays - Math.min(totalDays,interestDay) + 1;// 按天计息标的实际持有天数
                    // 产品确认用应还利息-持有天数利息计算减息 update by wgx & mjb 2019/04/17
                    BigDecimal acctualInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(userCapital, borrow.getBorrowApr(), carryDays);
                    if (acctualInterest.compareTo(userInterest) >= 0) {
                        userChargeInterest = BigDecimal.ZERO;
                    } else {
                        userChargeInterest = userInterest.subtract(acctualInterest);
                    }
                } else {
                    // 实际持有天数
                    int acctualDays = GetDate.daysBetween(createTime, factRepayTime);
                    // 用户提前还款减少的利息
                    BigDecimal acctualInterest = UnnormalRepayUtils.aheadEndRepayInterest(userCapital, borrow.getBorrowApr(), acctualDays);
                    if (acctualInterest.compareTo(userInterest) >= 0) {
                        userChargeInterest = BigDecimal.ZERO;
                    } else {
                        userChargeInterest = userInterest.subtract(acctualInterest);
                    }
                    userChargePenaltyInterest = UnnormalRepayUtils.aheadEndRepayInterest(userCapital, borrow.getBorrowApr());
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
                        List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, tenderOrderId, 1, 0);
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
                            BigDecimal assignChargePenaltyInterest = BigDecimal.ZERO;// 计算用户提前还款罚息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户还款管理费
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                CreditRepay creditRepay = creditRepayList.get(j);
                                RepayCreditRepayBean creditRepayBean = new RepayCreditRepayBean();
                                // 计算出借用户实际获得的本息和
                                assignAccount = creditRepay.getAssignAccount();
                                assignCapital = creditRepay.getAssignCapital();// 承接本金
                                assignInterest = creditRepay.getAssignInterest();
                                //最后一笔兜底
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
                                } else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                    int carryDays = totalDays - Math.min(totalDays,interestDay) + 1;// 按天计息标的实际持有天数
                                    // 产品确认用应还利息-持有天数利息计算减息 update by wgx & mjb 2019/04/17
                                    BigDecimal acctualInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(assignCapital, borrow.getBorrowApr(), carryDays);
                                    if (acctualInterest.compareTo(assignInterest) >= 0) {
                                        assignChargeInterest = BigDecimal.ZERO;
                                    } else {
                                        assignChargeInterest = assignInterest.subtract(acctualInterest);
                                    }
                                } else {
                                    // 实际持有天数
                                    int acctualDays = GetDate.daysBetween(createTime, factRepayTime);

                                    // 用户提前还款应还的利息  实际用款天数+3   这个不变
                                    BigDecimal acctualInterest = UnnormalRepayUtils.aheadEndRepayInterest(assignCapital, borrow.getBorrowApr(), acctualDays);
                                    if (acctualInterest.compareTo(assignInterest) >= 0) {
                                        assignChargeInterest = BigDecimal.ZERO;
                                    } else {
                                        assignChargeInterest = assignInterest.subtract(acctualInterest);// 用户提前还款实际减息 应还利息-实际应还利息
                                    }
                                    assignChargePenaltyInterest = UnnormalRepayUtils.aheadEndRepayInterest(assignCapital, borrow.getBorrowApr());// 用户提前还款罚息
                                }

                                BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                creditRepayBean.setAssignTotal(assignAccount.subtract(assignChargeInterest).add(assignManageFee));
                                creditRepayBean.setManageFee(assignManageFee);
                                creditRepayBean.setAdvanceStatus(1);
                                creditRepayBean.setChargeInterest(assignChargeInterest.multiply(new BigDecimal(-1)));
                                creditRepayBean.setChargePenaltyInterest(assignChargePenaltyInterest);
                                creditRepayBean.setChargeDays(interestDay);
                                creditRepayBeanList.add(creditRepayBean);
                                // 统计出让人还款金额
                                userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                userCapital = userCapital.subtract(assignCapital);
                                userInterest = userInterest.subtract(assignInterest);
                                userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
                                userChargeInterest = userChargeInterest.subtract(assignChargeInterest);// 提前还款利息
                                userChargePenaltyInterest = userChargePenaltyInterest.subtract(assignChargePenaltyInterest);// 提前还款利息
                                // 统计总额
                                repayTotal = repayTotal.add(assignAccount).subtract(assignChargeInterest).add(assignManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                repayCapital = repayCapital.add(assignCapital);
                                repayInterest = repayInterest.add(assignInterest);
                                repayManageFee = repayManageFee.add(assignManageFee);// 统计管理费
                                repayChargeInterest = repayChargeInterest.add(assignChargeInterest);// 统计提前还款减少的利息
                                repayChargePenaltyInterest = repayChargePenaltyInterest.add(assignChargePenaltyInterest);// 统计提前还款罚息
                            }
                            repayRecoverBean.setCreditRepayList(creditRepayBeanList);
                        }
                        if (borrowRecover.getCreditStatus() != 2) {
                            // 实际持有天数
                            int acctualDays = GetDate.daysBetween(createTime, factRepayTime);

                            if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                                // 用户提前还款减少的利息
                                BigDecimal acctualInterest = UnnormalRepayUtils.aheadEndRepayInterest(userCapital, borrow.getBorrowApr(), acctualDays);
                                if (acctualInterest.compareTo(userInterest) >= 0) {
                                    userChargeInterest = BigDecimal.ZERO;
                                } else {
                                    userChargeInterest = userInterest.subtract(acctualInterest);
                                }
                                userChargePenaltyInterest = UnnormalRepayUtils.aheadEndRepayInterest(userCapital, borrow.getBorrowApr());// 用户提前还款罚息
                            } else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                int carryDays = totalDays - Math.min(totalDays,interestDay) + 1;// 按天计息标的实际持有天数
                                // 产品确认用应还利息-持有天数利息计算减息 update by wgx & mjb 2019/04/17
                                BigDecimal acctualInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(userCapital, borrow.getBorrowApr(), carryDays);
                                if (acctualInterest.compareTo(userInterest) >= 0) {
                                    userChargeInterest = BigDecimal.ZERO;
                                } else {
                                    userChargeInterest = userInterest.subtract(acctualInterest);
                                }
                            }
                            // 统计总额
                            repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userManageFee);// 统计总和本息+管理费
                            repayAccount = repayAccount.add(userAccount);// 统计总和本息
                            repayCapital = repayCapital.add(userCapital);
                            repayInterest = repayInterest.add(userInterest);
                            repayManageFee = repayManageFee.add(userManageFee);// 统计管理费
                            repayChargeInterest = repayChargeInterest.add(userChargeInterest);// 统计提前还款减少的利息
                            repayChargePenaltyInterest = repayChargePenaltyInterest.add(userChargePenaltyInterest);// 统计提前还款罚息
                        }
                    } else {
                        // 计划类还款
                        boolean overFlag = false;
                        // 债转还款数据
                        List<HjhDebtCreditRepay> creditRepayList = this.selectHjhDebtCreditRepay(borrowNid, tenderOrderId, 1, borrowRecover.getRecoverStatus());
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
                            BigDecimal assignChargePenaltyInterest = BigDecimal.ZERO;// 计算用户提前还款罚息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户还款管理费
                            //判断当前期是否全部承接
                            overFlag = isOverUndertake(borrowRecover, null, null, false, 0);
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                HjhDebtCreditRepay creditRepay = creditRepayList.get(j);
                                HjhDebtCreditRepayBean creditRepayBean = new HjhDebtCreditRepayBean();
                                // 计算出借用户实际获得的本息和
                                assignAccount = creditRepay.getRepayAccount();
                                assignCapital = creditRepay.getRepayCapital();// 承接本金
                                assignInterest = creditRepay.getRepayInterest();
                                //最后一笔兜底
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
                                } else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                    int carryDays = totalDays - Math.min(totalDays,interestDay) + 1;// 按天计息标的实际持有天数
                                    // 产品确认用应还利息-持有天数利息计算减息 update by wgx & mjb 2019/04/17
                                    BigDecimal acctualInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(assignCapital, borrow.getBorrowApr(), carryDays);
                                    if (acctualInterest.compareTo(assignInterest) >= 0) {
                                        assignChargeInterest = BigDecimal.ZERO;
                                    } else {
                                        assignChargeInterest = assignInterest.subtract(acctualInterest);
                                    }
                                } else {
                                    // 实际持有天数
                                    int acctualDays = GetDate.daysBetween(createTime, factRepayTime);

                                    // 用户提前还款减少的利息
                                    BigDecimal acctualInterest = UnnormalRepayUtils.aheadEndRepayInterest(assignCapital, borrow.getBorrowApr(), acctualDays);
                                    if (acctualInterest.compareTo(assignInterest) >= 0) {
                                        assignChargeInterest = BigDecimal.ZERO;
                                    } else {
                                        assignChargeInterest = assignInterest.subtract(acctualInterest);
                                    }
                                    assignChargePenaltyInterest = UnnormalRepayUtils.aheadEndRepayInterest(assignCapital, borrow.getBorrowApr());
                                }

                                BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                creditRepayBean.setAssignTotal(assignAccount.subtract(assignChargeInterest).add(assignManageFee));
                                creditRepayBean.setManageFee(assignManageFee);
                                creditRepayBean.setAdvanceStatus(1);
                                creditRepayBean.setRepayAdvanceInterest(assignChargeInterest.multiply(new BigDecimal(-1)));
                                creditRepayBean.setRepayAdvancePenaltyInterest(assignChargePenaltyInterest);
                                creditRepayBean.setAdvanceDays(interestDay);
                                creditRepayBeanList.add(creditRepayBean);
                                // 统计出让人还款金额
                                userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                userCapital = userCapital.subtract(assignCapital);
                                userInterest = userInterest.subtract(assignInterest);
                                userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
                                userChargeInterest = userChargeInterest.subtract(assignChargeInterest);// 提前还款利息
                                userChargePenaltyInterest = userChargePenaltyInterest.subtract(assignChargePenaltyInterest);// 提前还款罚息
                                // 统计总额
                                repayTotal = repayTotal.add(assignAccount).subtract(assignChargeInterest).add(assignManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                repayCapital = repayCapital.add(assignCapital);
                                repayInterest = repayInterest.add(assignInterest);
                                repayManageFee = repayManageFee.add(assignManageFee);// 统计管理费
                                repayChargeInterest = repayChargeInterest.add(assignChargeInterest);// 统计提前还款减少的利息
                                repayChargePenaltyInterest = repayChargePenaltyInterest.add(assignChargePenaltyInterest);// 统计提前还款罚息
                            }
                            repayRecoverBean.setHjhCreditRepayList(creditRepayBeanList);
                        }
                        if (overFlag) {
                            // 重新计算债转后出让人剩余金额的提前减息金额
                            // 实际持有天数
                            int acctualDays = GetDate.daysBetween(createTime, factRepayTime);
                            if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                                // 用户提前还款减少的利息
                                BigDecimal acctualInterest = UnnormalRepayUtils.aheadEndRepayInterest(userCapital, borrow.getBorrowApr(), acctualDays);
                                if (acctualInterest.compareTo(userInterest) >= 0) {
                                    userChargeInterest = BigDecimal.ZERO;
                                } else {
                                    userChargeInterest = userInterest.subtract(acctualInterest);
                                }
                                userChargePenaltyInterest = UnnormalRepayUtils.aheadEndRepayInterest(userCapital, borrow.getBorrowApr());// 用户提前还款罚息
                            } else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                int carryDays = totalDays - Math.min(totalDays,interestDay) + 1;// 按天计息标的实际持有天数
                                // 产品确认用应还利息-持有天数利息计算减息 update by wgx & mjb 2019/04/17
                                BigDecimal acctualInterest = UnnormalRepayUtils.aheadRTBRepayChargeInterest(userCapital, borrow.getBorrowApr(), carryDays);
                                if (acctualInterest.compareTo(userInterest) >= 0) {
                                    userChargeInterest = BigDecimal.ZERO;
                                } else {
                                    userChargeInterest = userInterest.subtract(acctualInterest);
                                }
                            }
                            // 统计总额
                            repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userManageFee);// 统计总和本息+管理费
                            repayAccount = repayAccount.add(userAccount);// 统计总和本息
                            repayCapital = repayCapital.add(userCapital);
                            repayInterest = repayInterest.add(userInterest);
                            repayManageFee = repayManageFee.add(userManageFee);// 统计管理费
                            repayChargeInterest = repayChargeInterest.add(userChargeInterest);// 统计提前还款减少的利息
                            repayChargePenaltyInterest = repayChargePenaltyInterest.add(userChargePenaltyInterest);// 统计提前还款减少的利息
                        }
                    }
                } else {
                    // 统计总额
                    repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userManageFee);// 统计总和本息+管理费
                    repayAccount = repayAccount.add(userAccount); // 统计本息总和
                    repayCapital = repayCapital.add(userCapital);
                    repayInterest = repayInterest.add(userInterest);
                    repayManageFee = repayManageFee.add(userManageFee);// 统计管理费
                    repayChargeInterest = repayChargeInterest.add(userChargeInterest);// 统计提前还款减少的利息
                    repayChargePenaltyInterest = repayChargePenaltyInterest.add(userChargePenaltyInterest);// 统计提前还款罚息
                }
                BeanUtils.copyProperties(borrowRecover, repayRecoverBean);
                repayRecoverBean.setRecoverTotal(userAccount.subtract(userChargeInterest).add(userManageFee));
                repayRecoverBean.setRecoverAccount(userAccount);
                repayRecoverBean.setRecoverCapital(userCapital);
                repayRecoverBean.setRecoverInterest(userInterest);
                repayRecoverBean.setRecoverFee(userManageFee);
                repayRecoverBean.setChargeInterest(userChargeInterest.multiply(new BigDecimal(-1)));
                repayRecoverBean.setChargePenaltyInterest(userChargePenaltyInterest);
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
        repay.setChargePenaltyInterest(repayChargePenaltyInterest);
        repay.setAdvanceStatus(1);

    }

    /***
     * 查询相应的债转还款记录
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
     * 查询相应的债转还款记录
     *
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
     */
    private boolean isOverUndertake(RepayRecoverBean borrowRecover, BigDecimal recoverPlanCapital, BigDecimal creditSumCapital, boolean isMonth, int hjhFlag) {
        if (isMonth) {//分期标的并且是计划债转
            if (hjhFlag > 0) {
                //分期待还本金 大于 承接本金
                if (recoverPlanCapital.compareTo(creditSumCapital) > 0) {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            if (borrowRecover.getRecoverCapitalOld().compareTo(borrowRecover.getCreditAmountOld()) > 0) {
                return true;
            }
        }
        return false;
    }



    /**
     * 判断是否完全承接  true:未完全承接
     */
    private boolean isOverUndertake(BorrowRecover borrowRecover, BigDecimal recoverPlanCapital, BigDecimal creditSumCapital, boolean isMonth, int hjhFlag) {
        if (isMonth) {//分期标的并且是计划债转
            if (hjhFlag > 0) {
                //分期待还本金 大于 承接本金
                if (recoverPlanCapital.compareTo(creditSumCapital) > 0) {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            if (borrowRecover.getRecoverCapital().compareTo(borrowRecover.getCreditAmount()) > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否完全承接  true:未完全承接
     */
    private boolean isOverUndertake(RepayRecoverPlanBean userRecoverPlan, BigDecimal recoverPlanCapital, BigDecimal creditSumCapital, boolean isMonth, int hjhFlag) {
        if (isMonth) {//分期标的并且是计划债转
            if (hjhFlag > 0) {
                //分期待还本金 大于 承接本金
                if (recoverPlanCapital.compareTo(creditSumCapital) > 0) {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            if (userRecoverPlan.getRecoverCapitalOld().compareTo(userRecoverPlan.getCreditAmountOld()) > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 统计单期还款用户逾期还款的总标
     */
    private void calculateRecoverTotalLate(RepayBean repay, Borrow borrow, int lateDays) throws Exception {

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
        BigDecimal repayOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
        List<BorrowRecover> borrowRecovers = this.getBorrowRecover(borrowNid);
        int repayMoneySource = repay.getRepayMoneySource() == null ? 0 : repay.getRepayMoneySource();// 还款来源，提交还款会更新，用于判断是否已提交还款
        if (borrowRecovers != null && borrowRecovers.size() > 0) {
            List<RepayRecoverBean> repayRecoverList = new ArrayList<RepayRecoverBean>();
            BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
            BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户还款管理费
            BigDecimal userOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
            for (int i = 0; i < borrowRecovers.size(); i++) {
                BorrowRecover borrowRecover = borrowRecovers.get(i);
                RepayRecoverBean repayRecoverBean = new RepayRecoverBean();
                String tenderOrderId = borrowRecover.getNid();// 出借订单号
                userAccount = borrowRecover.getRecoverAccount();// 获取未还款前用户能够获取的本息和
                userCapital = borrowRecover.getRecoverCapital();
                userInterest = borrowRecover.getRecoverInterest();
                userOverdueInterest = borrowRecover.getLateInterest();// batch计算的逾期利息
                int userAdvanceStatus = borrowRecover.getAdvanceStatus();// batch计算的还款状态
                int userLateDays = borrowRecover.getLateDays();// batch计算的逾期天数
                // 已经提交还款的不校验
                if (repayMoneySource == 0 && (userAdvanceStatus != 3 || userLateDays != lateDays)) {
                    logger.error("【还款明细】计算的逾期信息有误！计算逾期状态：{}，计算逾期天数：{}，实际逾期天数：{}",
                            userAdvanceStatus == 3 ? "已逾期" : "未逾期", userLateDays, lateDays);
                    throw new Exception("计算的逾期信息有误！");
                }
                // 获取应还款管理费
                userManageFee = borrowRecover.getRecoverFee();
                repayRecoverBean.setRecoverCapitalOld(userCapital);
                repayRecoverBean.setCreditAmountOld(borrowRecover.getCreditAmount());
                if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                    if (Validator.isNull(borrowRecover.getAccedeOrderId())) {
                        // 直投类项目还款
                        List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, tenderOrderId, 1, 0);
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户还款管理费
                            BigDecimal assignOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                CreditRepay creditRepay = creditRepayList.get(j);
                                RepayCreditRepayBean creditRepayBean = new RepayCreditRepayBean();
                                assignAccount = creditRepay.getAssignAccount();// 承接本息
                                assignCapital = creditRepay.getAssignCapital();// 承接本金
                                assignInterest = creditRepay.getAssignInterest();
                                assignOverdueInterest = creditRepay.getLateInterest();// batch计算的逾期利息
                                int assignAdvanceStatus = creditRepay.getAdvanceStatus();// batch计算的还款状态
                                int assignLateDays = creditRepay.getLateDays();// batch计算的逾期天数
                                if (repayMoneySource == 0 && (assignAdvanceStatus != 3 || assignLateDays != lateDays)) {
                                    logger.error("【还款明细】计算的债转逾期信息有误！计算逾期状态：{}，计算逾期天数：{}，实际逾期天数：{}",
                                            assignAdvanceStatus == 3 ? "已逾期" : "未逾期", assignLateDays, lateDays);
                                    throw new Exception("计算的逾期信息有误！");
                                }
                                //最后一笔兜底
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
                                creditRepayBean.setAssignTotal(assignAccount.add(assignOverdueInterest).add(assignManageFee));
                                creditRepayBean.setManageFee(assignManageFee);
                                creditRepayBeanList.add(creditRepayBean);
                                // 统计出让人还款金额
                                userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                userCapital = userCapital.subtract(assignCapital);
                                userInterest = userInterest.subtract(assignInterest);
                                userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
                                userOverdueInterest = userOverdueInterest.subtract(assignOverdueInterest);// 逾期利息
                                // 统计总额
                                repayTotal = repayTotal.add(assignAccount).add(assignOverdueInterest).add(assignManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                repayCapital = repayCapital.add(assignCapital);
                                repayInterest = repayInterest.add(assignInterest);
                                repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                repayOverdueInterest = repayOverdueInterest.add(assignOverdueInterest);// 统计借款用户总逾期利息
                            }
                            repayRecoverBean.setCreditRepayList(creditRepayBeanList);
                        }
                        if (borrowRecover.getCreditStatus() != 2) {
                            // 统计总额
                            repayTotal = repayTotal.add(userAccount).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                            repayAccount = repayAccount.add(userAccount);// 统计总和本息
                            repayCapital = repayCapital.add(userCapital);
                            repayInterest = repayInterest.add(userInterest);
                            repayManageFee = repayManageFee.add(userManageFee);// 统计管理费
                            repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);// 统计借款用户总逾期利息
                        }
                    } else {
                        // 计划类债转还款
                        List<HjhDebtCreditRepay> creditRepayList = this.selectHjhDebtCreditRepay(borrowNid, tenderOrderId, 1, borrowRecover.getRecoverStatus());
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户还款管理费
                            BigDecimal assignOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
                            //判断当前期是否全部承接
                            boolean overFlag = isOverUndertake(borrowRecover, null, null, false, 0);
                            for (int j = 0; j < creditRepayList.size(); j++) {
                                HjhDebtCreditRepay creditRepay = creditRepayList.get(j);
                                HjhDebtCreditRepayBean creditRepayBean = new HjhDebtCreditRepayBean();
                                assignAccount = creditRepay.getRepayAccount();// 承接本息
                                assignCapital = creditRepay.getRepayCapital();// 承接本金
                                assignInterest = creditRepay.getRepayInterest();
                                assignOverdueInterest = creditRepay.getRepayLateInterest();// batch计算的逾期利息
                                int assignAdvanceStatus = creditRepay.getAdvanceStatus();// batch计算的还款状态
                                int assignLateDays = creditRepay.getLateDays();// batch计算的逾期天数
                                if (repayMoneySource == 0 && (assignAdvanceStatus != 3 || assignLateDays != lateDays)) {
                                    logger.error("【还款明细】计算的债转逾期信息有误！计算逾期状态：{}，计算逾期天数：{}，实际逾期天数：{}",
                                            assignAdvanceStatus == 3 ? "已逾期" : "未逾期", assignLateDays, lateDays);
                                    throw new Exception("计算的逾期信息有误！");
                                }
                                //最后一笔兜底
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
                                creditRepayBean.setAssignTotal(assignAccount.add(assignOverdueInterest).add(assignManageFee));
                                creditRepayBean.setManageFee(assignManageFee);
                                creditRepayBeanList.add(creditRepayBean);
                                // 统计出让人还款金额
                                userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                userCapital = userCapital.subtract(assignCapital);
                                userInterest = userInterest.subtract(assignInterest);
                                userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
                                userOverdueInterest = userOverdueInterest.subtract(assignOverdueInterest);// 逾期利息
                                // 统计总额
                                repayTotal = repayTotal.add(assignAccount).add(assignOverdueInterest).add(assignManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                repayCapital = repayCapital.add(assignCapital);
                                repayInterest = repayInterest.add(assignInterest);
                                repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                repayOverdueInterest = repayOverdueInterest.add(assignOverdueInterest);// 统计借款用户总逾期利息
                            }
                            repayRecoverBean.setHjhCreditRepayList(creditRepayBeanList);
                        }
                        if (borrowRecover.getCreditStatus() != 2) {
                            // 统计总额
                            repayTotal = repayTotal.add(userAccount).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                            repayAccount = repayAccount.add(userAccount);// 统计总和本息
                            repayCapital = repayCapital.add(userCapital);
                            repayInterest = repayInterest.add(userInterest);
                            repayManageFee = repayManageFee.add(userManageFee);// 统计管理费
                            repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);// 统计借款用户总逾期利息
                        }
                    }
                } else {
                    // 统计总额
                    repayTotal = repayTotal.add(userAccount).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                    repayAccount = repayAccount.add(userAccount);// 统计总和本息
                    repayCapital = repayCapital.add(userCapital);
                    repayInterest = repayInterest.add(userInterest);
                    repayManageFee = repayManageFee.add(userManageFee);// 管理费
                    repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);// 统计借款用户总逾期利息
                }
                // 延期还款利息
                BeanUtils.copyProperties(borrowRecover, repayRecoverBean);
                // 用户延期还款
                repayRecoverBean.setRecoverTotal(userAccount.add(userOverdueInterest).add(userManageFee));
                repayRecoverBean.setRecoverAccount(userAccount);
                repayRecoverBean.setRecoverCapital(userCapital);
                repayRecoverBean.setRecoverInterest(userInterest);
                repayRecoverBean.setRecoverFee(userManageFee);
                repayRecoverBean.setLateInterest(userOverdueInterest);
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
        repay.setLateDays(lateDays);
        repay.setLateInterest(repayOverdueInterest);
        repay.setLateStatus("1");
    }

    /**
     * 统计单期还款用户正常还款的总标
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
        List<BorrowRecover> borrowRecovers = this.getBorrowRecover(borrowNid);
        if (borrowRecovers != null && borrowRecovers.size() > 0) {
            List<RepayRecoverBean> repayRecoverList = new ArrayList<RepayRecoverBean>();
            BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
            BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户还款管理费
            for (int i = 0; i < borrowRecovers.size(); i++) {
                BorrowRecover borrowRecover = borrowRecovers.get(i);
                RepayRecoverBean repayRecoverBean = new RepayRecoverBean();
                String tenderOrderId = borrowRecover.getNid();// 出借订单号
                userAccount = borrowRecover.getRecoverAccount();// 计算用户实际获得的本息和
                userCapital = borrowRecover.getRecoverCapital();
                userInterest = borrowRecover.getRecoverInterest();
                userManageFee = borrowRecover.getRecoverFee();// 计算用户还款管理费

                repayRecoverBean.setRecoverCapitalOld(userCapital);
                repayRecoverBean.setCreditAmountOld(borrowRecover.getCreditAmount());

                if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                    if (Validator.isNull(borrowRecover.getAccedeOrderId())) {
                        // 出借项目还款
                        List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, tenderOrderId, 1, 0);
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户还款管理费
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
                                creditRepayBean.setChargePenaltyInterest(BigDecimal.ZERO);
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
                                repayManageFee = repayManageFee.add(assignManageFee);// 统计管理费
                            }
                            repayRecoverBean.setCreditRepayList(creditRepayBeanList);
                        }
                        if (borrowRecover.getCreditStatus() != 2) {
                            // 统计总额
                            repayTotal = repayTotal.add(userAccount).add(userManageFee);// 统计总和本息+管理费
                            repayAccount = repayAccount.add(userAccount);// 统计总和本息
                            repayCapital = repayCapital.add(userCapital);
                            repayInterest = repayInterest.add(userInterest);
                            repayManageFee = repayManageFee.add(userManageFee);// 统计管理费
                        }
                    } else {
                        // 计划类还款
                        List<HjhDebtCreditRepay> creditRepayList = this.selectHjhDebtCreditRepay(borrowNid, tenderOrderId, 1, borrowRecover.getRecoverStatus());
                        if (creditRepayList != null && creditRepayList.size() > 0) {
                            List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                            BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                            BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                            BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                            BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户还款管理费
                            //判断当前期是否全部承接
                            boolean overFlag = isOverUndertake(borrowRecover, null, null, false, 0);
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
                                repayManageFee = repayManageFee.add(assignManageFee);// 统计管理费
                            }
                            repayRecoverBean.setHjhCreditRepayList(creditRepayBeanList);
                        }
                        if (borrowRecover.getCreditStatus() != 2) {
                            // 统计总额
                            repayTotal = repayTotal.add(userAccount).add(userManageFee);// 统计总和本息+管理费
                            repayAccount = repayAccount.add(userAccount);// 统计总和本息
                            repayCapital = repayCapital.add(userCapital);
                            repayInterest = repayInterest.add(userInterest);
                            repayManageFee = repayManageFee.add(userManageFee);// 统计管理费
                        }
                    }
                } else {
                    repayTotal = repayTotal.add(userAccount).add(userManageFee);// 统计总和本息+管理费
                    repayAccount = repayAccount.add(userAccount); // 统计本息总和
                    repayCapital = repayCapital.add(userCapital);
                    repayInterest = repayInterest.add(userInterest);
                    repayManageFee = repayManageFee.add(userManageFee);// 统计管理费
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
     * 设置不分期还款方式数据
     */
    private void setRecoverDetail(ProjectBean form, String userId, Borrow borrow, RepayBean repay)
            throws ParseException {
        form.setRepayPeriod("0");
        form.setManageFee(repay.getRepayFee().toString());
        form.setRepayTotal(repay.getRepayAccountAll().toString()); // 计算的是还款总额
        form.setRepayAccount(repay.getRepayAccount().add(repay.getChargeInterest()).add(repay.getLateInterest()).toString());
        form.setRepayCapital(repay.getRepayCapital().toString());
        form.setRepayInterest(repay.getRepayInterest().add(repay.getChargeInterest()).add(repay.getLateInterest()).toString());
        form.setShouldInterest(repay.getRepayInterest().toString());
        form.setLateStatus(repay.getLateStatus());
        form.setAdvanceStatus(String.valueOf(repay.getAdvanceStatus()));
        form.setChargeDays(repay.getChargeDays().toString());
        //form.setChargeInterest(repay.getChargeInterest().toString());
        form.setChargeOriginalInterest(repay.getChargePenaltyInterest().subtract(repay.getChargeInterest()).toString());
        form.setChargePenaltyInterest(repay.getChargePenaltyInterest().toString());
        if ("0".equals(form.getChargeOriginalInterest())) {
            form.setChargeOriginalInterest("0.00");
        }
        if ("0".equals(form.getChargePenaltyInterest())) {
            form.setChargePenaltyInterest("0.00");
        }
        form.setLateDays(repay.getLateDays().toString());
        form.setLateInterest(repay.getLateInterest().toString());
        List<ProjectRepayBean> userRepayList = new ArrayList<ProjectRepayBean>();
        ProjectRepayBean userRepayBean = new ProjectRepayBean();
        // 此处是本息和
        userRepayBean.setRepayTotal(repay.getRepayAccountAll().toString());
        userRepayBean.setRepayAccount(repay.getRepayAccount().add(repay.getChargeInterest()).add(repay.getLateInterest()).toString());
        userRepayBean.setRepayCapital(repay.getRepayCapital().toString());
        userRepayBean.setRepayInterest(repay.getRepayInterest().toString());
        userRepayBean.setChargeDays(repay.getChargeDays().toString());
        // 提前还款利息拆分 add by wgx 2019/03/22
        userRepayBean.setChargeOriginalInterest(repay.getChargePenaltyInterest().subtract(repay.getChargeInterest()).toString());
        userRepayBean.setChargePenaltyInterest(repay.getChargePenaltyInterest().toString());
        if ("0".equals(userRepayBean.getChargeOriginalInterest())) {
            userRepayBean.setChargeOriginalInterest("0.00");
        }
        if ("0".equals(userRepayBean.getChargePenaltyInterest())) {
            userRepayBean.setChargePenaltyInterest("0.00");
        }
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
                        // 提前还款利息拆分 add by wgx 2019/03/22
                        userRepayDetail.setChargeOriginalInterest(creditRepay.getChargePenaltyInterest().subtract(creditRepay.getChargeInterest()).toString());
                        userRepayDetail.setChargePenaltyInterest(creditRepay.getChargePenaltyInterest().toString());
                        if ("0".equals(userRepayDetail.getChargeOriginalInterest())) {
                            userRepayDetail.setChargeOriginalInterest("0.00");
                        }
                        if ("0".equals(userRepayDetail.getChargePenaltyInterest())) {
                            userRepayDetail.setChargePenaltyInterest("0.00");
                        }
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
                        // userName直接取表中数据 update by wgx 2019/02/15
                        String userNameStr = creditRepay.getUserName().substring(0, 1).concat("**");
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
                        userRepayDetail.setChargeOriginalInterest(creditRepay.getRepayAdvancePenaltyInterest().subtract(creditRepay.getRepayAdvanceInterest()).toString());
                        userRepayDetail.setChargePenaltyInterest(creditRepay.getRepayAdvancePenaltyInterest().toString());
                        if ("0".equals(userRepayDetail.getChargeOriginalInterest())) {
                            userRepayDetail.setChargeOriginalInterest("0.00");
                        }
                        if ("0".equals(userRepayDetail.getChargePenaltyInterest())) {
                            userRepayDetail.setChargePenaltyInterest("0.00");
                        }
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
                        // userName直接取表中数据 update by wgx 2019/02/15
                        String userNameStr = creditRepay.getUserName().substring(0, 1).concat("**");
                        userRepayDetail.setUserName(userNameStr);
                        userRepayDetails.add(userRepayDetail);
                    }
                }

                boolean overFlag = isOverUndertake(userRecover, null, null, false, 0);
                if (overFlag) {
                    ProjectRepayDetailBean userRepayDetail = new ProjectRepayDetailBean();
                    userRepayDetail.setRepayAccount(userRecover.getRecoverAccount().toString());
                    userRepayDetail.setRepayCapital(userRecover.getRecoverCapital().toString());
                    userRepayDetail.setRepayInterest(userRecover.getRecoverInterest().toString());
                    userRepayDetail.setChargeDays(userRecover.getChargeDays().toString());
                    // 提前还款利息拆分 add by wgx 2019/03/22
                    userRepayDetail.setChargeOriginalInterest(userRecover.getChargePenaltyInterest().subtract(userRecover.getChargeInterest()).toString());
                    userRepayDetail.setChargePenaltyInterest(userRecover.getChargePenaltyInterest().toString());
                    if ("0".equals(userRepayDetail.getChargeOriginalInterest())) {
                        userRepayDetail.setChargeOriginalInterest("0.00");
                    }
                    if ("0".equals(userRepayDetail.getChargePenaltyInterest())) {
                        userRepayDetail.setChargePenaltyInterest("0.00");
                    }
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
                    // userName直接取表中数据 update by wgx 2019/02/15
                    String userNameStr = userRecover.getUserName().substring(0, 1).concat("**");
                    userRepayDetail.setUserName(userNameStr);
                    userRepayDetails.add(userRepayDetail);
                }
            }
            userRepayBean.setUserRepayDetailList(userRepayDetails);
            userRepayList.add(userRepayBean);
        }
        form.setUserRepayList(userRepayList);
    }

    /**
     * 计算用户一次性还款应还金额
     */
    private BigDecimal calculateRepayPlanAll(RepayBean repay, Borrow borrow, int period) throws Exception {
        List<RepayDetailBean> borrowRepayPlanDeails = new ArrayList<RepayDetailBean>();
        List<BorrowRepayPlan> borrowRepayPlans = searchRepayPlanAll(repay.getUserId(), borrow.getBorrowNid());// 还款计划按照期数排序
        BigDecimal repayAccountAll = BigDecimal.ZERO;
        // 一下值先清0，因为是从数据库repay 表复制过来的
        repay.setBorrowPeriod(borrow.getBorrowPeriod().toString());
        repay.setRepayAccountAll(BigDecimal.ZERO);
        repay.setRepayAccount(BigDecimal.ZERO);
        repay.setRepayCapital(BigDecimal.ZERO);
        repay.setRepayInterest(BigDecimal.ZERO);
        repay.setRepayFee(BigDecimal.ZERO);
        repay.setChargeInterest(BigDecimal.ZERO);
        repay.setChargePenaltyInterest(BigDecimal.ZERO);
        repay.setLateInterest(BigDecimal.ZERO);
        //repay.setAdvanceStatus(1);// 默认属于提前还款
        int totalPeriod = borrow.getBorrowPeriod() - period;
        int nowPeriod = period;
        if (borrowRepayPlans != null && borrowRepayPlans.size() > 0) {
            // 将calculateRecoverPlanAll的borrowRecoverList移到方法外查询 update by wgx 2019/02/15
            List<BorrowRecover> borrowRecoverList = this.getBorrowRecover(borrow.getBorrowNid());
            // 用户实际还款额
            for (int i = 0; i < borrowRepayPlans.size(); i++) {
                RepayDetailBean repayPlanDetail = new RepayDetailBean();
                BorrowRepayPlan borrowRepayPlan = borrowRepayPlans.get(i);
                int repayPeriod = borrowRepayPlan.getRepayPeriod();
                int repayTimeInt = borrowRepayPlan.getRepayTime();
                int repayStatus = borrowRepayPlan.getRepayStatus();
                int nowDate = GetDate.getIntYYMMDD(new Date());
                if(GetDate.getIntYYMMDD(repayTimeInt) < nowDate && repayPeriod >= nowPeriod && repayStatus != 1){// 已逾期期数
                    // 计算还款期的数据
                    BeanUtils.copyProperties(borrowRepayPlan, repayPlanDetail);
                    this.calculateRecoverPlan(repayPlanDetail, borrow, nowPeriod, null, borrowRecoverList);
                    borrowRepayPlanDeails.add(repayPlanDetail);
                    //  累加以下值
                    repay.setRepayAccountAll(repay.getRepayAccountAll().add(repayPlanDetail.getRepayAccountAll()));
                    repay.setRepayAccount(repay.getRepayAccount().add(repayPlanDetail.getRepayAccount()));
                    repay.setRepayCapital(repay.getRepayCapital().add(repayPlanDetail.getRepayCapital()));
                    repay.setRepayInterest(repay.getRepayInterest().add(repayPlanDetail.getRepayInterest()));
                    repay.setRepayFee(repay.getRepayFee().add(repayPlanDetail.getRepayFee()));
                    repay.setLateDays(repayPlanDetail.getLateDays());
                    // 逾期期数只累加逾期利息
                    repay.setLateInterest(repay.getLateInterest().add(repayPlanDetail.getLateInterest()));
                    repayAccountAll = repayPlanDetail.getRepayAccountAll();
                    nowPeriod ++;// 逾期了 当前期+1
                    repay.setLateStatus("1");
                } else if (repayPeriod == nowPeriod) {
                    // 当前期已经还款
                    if (repayStatus == 1) {
                        BeanUtils.copyProperties(borrowRepayPlan, repayPlanDetail);
                        this.calculateRecoverPlan(repayPlanDetail, borrow);
                        borrowRepayPlanDeails.add(repayPlanDetail);
                    } else {
                        // 查看当前还款时间 是否 在当前期里头,如果超前则不算当期还款
                        Integer repayTimeStart = null;
                        if (i == 0) {
                            repayTimeStart = GetDate.getTime10(borrowRepayPlan.getCreateTime());
                        } else {
                            repayTimeStart = borrowRepayPlans.get(i - 1).getRepayTime();
                        }
                        int curPlanStart = GetDate.getIntYYMMDD(repayTimeStart);
                        // 超期还
                        if (i != 0 && nowDate <= curPlanStart) {
                            // 当前期也算的话，需要加上当前期
                            totalPeriod = borrow.getBorrowPeriod() - nowPeriod + 1;
                            // 计算还款期的数据
                            BeanUtils.copyProperties(borrowRepayPlan, repayPlanDetail);
                            this.calculateRecoverPlanAll(repayPlanDetail, borrow, totalPeriod, borrowRecoverList);
                            borrowRepayPlanDeails.add(repayPlanDetail);
                            repay.setRepayAccountAll(repay.getRepayAccountAll().add(repayPlanDetail.getRepayAccountAll()));
                            repay.setRepayAccount(repay.getRepayAccount().add(repayPlanDetail.getRepayAccount()));
                            repay.setRepayCapital(repay.getRepayCapital().add(repayPlanDetail.getRepayCapital()));
                            repay.setRepayInterest(repay.getRepayInterest().add(repayPlanDetail.getRepayInterest()));
                            repay.setRepayFee(repay.getRepayFee().add(repayPlanDetail.getRepayFee()));
                            repay.setAdvanceStatus(repayPlanDetail.getAdvanceStatus());
                            repay.setChargeInterest(repay.getChargeInterest().add(repayPlanDetail.getChargeInterest()));
                            repay.setChargePenaltyInterest(repay.getChargePenaltyInterest().add(repayPlanDetail.getChargePenaltyInterest()));
                            repayAccountAll = repayPlanDetail.getRepayAccountAll();

                        } else {
                            // 计算还款期的数据
                            BeanUtils.copyProperties(borrowRepayPlan, repayPlanDetail);
                            this.calculateRecoverPlan(repayPlanDetail, borrow, nowPeriod, null, borrowRecoverList);
                            borrowRepayPlanDeails.add(repayPlanDetail);
                            if (repayPlanDetail.getAdvanceStatus() == 3) {
                                throw new Exception("当期逾期，不能全部结清");
                            }
                            repay.setRepayAccountAll(repay.getRepayAccountAll().add(repayPlanDetail.getRepayAccountAll()));
                            repay.setRepayAccount(repay.getRepayAccount().add(repayPlanDetail.getRepayAccount()));
                            repay.setRepayCapital(repay.getRepayCapital().add(repayPlanDetail.getRepayCapital()));
                            repay.setRepayInterest(repay.getRepayInterest().add(repayPlanDetail.getRepayInterest()));
                            repay.setRepayFee(repay.getRepayFee().add(repayPlanDetail.getRepayFee()));
                            repay.setAdvanceStatus(repayPlanDetail.getAdvanceStatus());
                            repay.setChargeDays(repayPlanDetail.getChargeDays());
                            repay.setChargeInterest(repay.getChargeInterest().add(repayPlanDetail.getChargeInterest()));
                            repay.setChargePenaltyInterest(repay.getChargePenaltyInterest().add(repayPlanDetail.getChargePenaltyInterest()));
                            repayAccountAll = repayPlanDetail.getRepayAccountAll();
                        }
                    }
                } else if (repayPeriod > nowPeriod) {// 当前期之后的数据
                    // 当前期也算的话，需要加上当前期
                    totalPeriod = borrow.getBorrowPeriod() - nowPeriod;
                    // 计算还款期的数据
                    BeanUtils.copyProperties(borrowRepayPlan, repayPlanDetail);
                    this.calculateRecoverPlanAll(repayPlanDetail, borrow, totalPeriod, borrowRecoverList);
                    borrowRepayPlanDeails.add(repayPlanDetail);
                    //  累加以下值
                    repay.setRepayAccountAll(repay.getRepayAccountAll().add(repayPlanDetail.getRepayAccountAll()));
                    repay.setRepayAccount(repay.getRepayAccount().add(repayPlanDetail.getRepayAccount()));
                    repay.setRepayCapital(repay.getRepayCapital().add(repayPlanDetail.getRepayCapital()));
                    repay.setRepayInterest(repay.getRepayInterest().add(repayPlanDetail.getRepayInterest()));
                    repay.setRepayFee(repay.getRepayFee().add(repayPlanDetail.getRepayFee()));
                    repay.setAdvanceStatus(repayPlanDetail.getAdvanceStatus());
                    repay.setChargeDays(repayPlanDetail.getChargeDays());
                    repay.setChargeInterest(repay.getChargeInterest().add(repayPlanDetail.getChargeInterest()));
                    repay.setChargePenaltyInterest(repay.getChargePenaltyInterest().add(repayPlanDetail.getChargePenaltyInterest()));
                    repayAccountAll = repayPlanDetail.getRepayAccountAll();
                } else {// 已还款信息
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
     * 统计分期还款用户正常还款的总标
     */
    private void calculateRecoverPlanAll(RepayDetailBean borrowRepayPlan, Borrow borrow, int totalPeriod, List<BorrowRecover> borrowRecoverList) throws Exception {

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
        // ====> 是否分期最后一期
        boolean isLastPeriod = (borrowPeriod == repayPeriod ? true : false);
        // 将calculateRecoverPlanAll的borrowRecoverList移到方法外查询 update by wgx 2019/02/15
        // List<BorrowRecover> borrowRecoverList = this.getBorrowRecover(borrow.getBorrowNid());
        List<BorrowRecoverPlan> borrowRecoverPlans = this.getBorrowRecoverPlan(borrow.getBorrowNid(), borrowRepayPlan.getRepayPeriod());
        List<RepayRecoverPlanBean> repayRecoverPlanList = new ArrayList<RepayRecoverPlanBean>();
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        BigDecimal repayChargeInterest = BigDecimal.ZERO;// 提前还款利息
        BigDecimal repayChargePenaltyInterest = BigDecimal.ZERO;// 提前还款罚息

        if (borrowRecoverList == null || borrowRecoverList.size() <= 0) {
            logger.error("【还款计算】借款编号：{}，没有放款数据！", borrow.getBorrowNid());
            return;
        }
        if (borrowRecoverPlans == null || borrowRecoverPlans.size() <= 0) {
            logger.error("【还款计算】借款编号：{}，没有第{}期放款分期数据！", borrow.getBorrowNid(), borrowRepayPlan.getRepayPeriod());
            return;
        }
        // 用户计划还款时间
        String repayTime = GetDate.getDateTimeMyTimeInMillis(borrowRepayPlan.getRepayTime());
        // 用户实际还款时间
        String nowTime = GetDate.getDateTimeMyTimeInMillis(GetDate.getNowTime10());
        // 获取实际还款同计划还款时间的时间差
        int distanceDays = GetDate.daysBetween(nowTime, repayTime);
        for (int i = 0; i < borrowRecoverList.size(); i++) {
            BorrowRecover borrowRecover = borrowRecoverList.get(i);
            String recoverNid = borrowRecover.getNid();// 出借订单号
            int recoverUserId = borrowRecover.getUserId();// 出借用户userId
            BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
            BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
            BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户还款管理费
            BigDecimal userChargeInterest = BigDecimal.ZERO;// 计算用户提前还款利息
            BigDecimal userChargePenaltyInterest = BigDecimal.ZERO;// 计算用户提前还款罚息
            for (int j = 0; j < borrowRecoverPlans.size(); j++) {
                BorrowRecoverPlan borrowRecoverPlan = borrowRecoverPlans.get(j);
                String recoverPlanNid = borrowRecoverPlan.getNid();// 出借订单号
                int recoverPlanUserId = borrowRecoverPlan.getUserId();// 出借用户userId
                if (recoverPlanNid.equals(recoverNid) && recoverUserId == recoverPlanUserId) {
                    RepayRecoverPlanBean repayRecoverPlanBean = new RepayRecoverPlanBean();
                    userAccount = borrowRecoverPlan.getRecoverAccount();// 获取应还款本息
                    userCapital = borrowRecoverPlan.getRecoverCapital();
                    userInterest = borrowRecoverPlan.getRecoverInterest();
                    userManageFee = borrowRecoverPlan.getRecoverFee();// 获取应还款管理费
                    BigDecimal recoverUserCapital = borrowRecover.getRecoverCapital().subtract(borrowRecover.getRecoverCapitalYes()); // 原始出借本金
                    // 给页面展示，就不计算了
                    repayRecoverPlanBean.setRecoverAccountOld(userAccount);
                    repayRecoverPlanBean.setChargeInterestOld(borrowRecoverPlan.getChargeInterest());
                    repayRecoverPlanBean.setChargePenaltyInterestOld(borrowRecoverPlan.getChargePenaltyInterest());
                    repayRecoverPlanBean.setLateInterestOld(borrowRecoverPlan.getLateInterest());
                    repayRecoverPlanBean.setRecoverAccountYesOld(borrowRecoverPlan.getRecoverAccountYes());
                    repayRecoverPlanBean.setRecoverCapitalOld(borrowRecover.getRecoverCapital());
                    repayRecoverPlanBean.setCreditAmountOld(borrowRecover.getCreditAmount());
                    // ** 计算三天罚息
                    BigDecimal acctualInterest = UnnormalRepayUtils.aheadEndRepayInterest(recoverUserCapital, borrow.getBorrowApr(), 0);
                    if (isLastPeriod) {
                        acctualInterest = UnnormalRepayUtils.aheadLastRepayInterest(recoverUserCapital, borrow.getBorrowApr(), totalPeriod);
                    }
                    if (acctualInterest.compareTo(userInterest) >= 0) {
                        userChargeInterest = BigDecimal.ZERO;
                    } else {
                        userChargeInterest = userInterest.subtract(acctualInterest);
                    }
                    // 项目提前还款时，提前还款利息不得大于应还款利息
                    if (userChargeInterest.compareTo(userInterest) > 0) {
                        userChargeInterest = userInterest;
                    }
                    userChargePenaltyInterest = acctualInterest;
                    // 如果发生债转
                    if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                        if (Validator.isNull(borrowRecover.getAccedeOrderId())) {
                            List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, recoverNid, repayPeriod, 0);
                            if (creditRepayList != null && creditRepayList.size() > 0) {
                                List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                                BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户还款管理费
                                BigDecimal assignChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
                                for (int k = 0; k < creditRepayList.size(); k++) {
                                    CreditRepay creditRepay = creditRepayList.get(k);
                                    RepayCreditRepayBean creditRepayBean = new RepayCreditRepayBean();
                                    String assignNid = creditRepay.getAssignNid();// 承接订单号
                                    CreditTender creditTender = this.getCreditTender(assignNid);// 查询相应的承接记录
                                    assignAccount = creditRepay.getAssignAccount();// 承接本息
                                    assignCapital = creditRepay.getAssignCapital();// 用户应还本金
                                    assignInterest = creditRepay.getAssignInterest();// 用户应还利息
                                    BigDecimal assignUserCapital = BigDecimal.ZERO;// 剩余承接本金
                                    if (borrowRecoverPlan.getCreditStatus() == 2 && k == creditRepayList.size() - 1) {
                                        assignManageFee = userManageFee;
                                    } else {
                                        assignManageFee = getManageFee(borrowStyle, feeRate, differentialRate, borrowVerifyTime, borrowPeriod, repayPeriod, assignCapital, assignManageFee, borrow.getAccount(), creditTender.getAssignCapital());
                                    }
                                    assignUserCapital = creditTender.getAssignCapital().subtract(creditTender.getAssignRepayCapital());
                                    BigDecimal acctualAsignInterest = UnnormalRepayUtils.aheadEndRepayInterest(assignUserCapital, borrow.getBorrowApr(), 0);
                                    if (isLastPeriod) {
                                        acctualAsignInterest = UnnormalRepayUtils.aheadLastRepayInterest(assignUserCapital, borrow.getBorrowApr(), totalPeriod);
                                    }
                                    if (acctualAsignInterest.compareTo(assignInterest) >= 0) {
                                        assignChargeInterest = BigDecimal.ZERO;
                                    } else {
                                        assignChargeInterest = assignInterest.subtract(acctualAsignInterest);
                                    }
                                    // 项目提前还款时，提前还款利息不得大于应还款利息
                                    if (assignChargeInterest.compareTo(assignInterest) > 0) {
                                        assignChargeInterest = assignInterest;
                                    }
                                    BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                    creditRepayBean.setAssignTotal(assignAccount.subtract(assignChargeInterest).add(assignManageFee));
                                    creditRepayBean.setManageFee(assignManageFee);
                                    creditRepayBean.setAdvanceStatus(1);
                                    creditRepayBean.setChargeInterest(assignChargeInterest.multiply(new BigDecimal(-1)));
                                    creditRepayBean.setChargePenaltyInterest(acctualAsignInterest);// 提前还款罚息
                                    creditRepayBean.setChargeDays(distanceDays);// 默认是3天
                                    creditRepayBeanList.add(creditRepayBean);
                                    // 统计出让人还款金额
                                    userAccount = userAccount.subtract(assignAccount);
                                    userCapital = userCapital.subtract(assignCapital);
                                    recoverUserCapital = recoverUserCapital.subtract(assignUserCapital);
                                    userInterest = userInterest.subtract(assignInterest);
                                    userManageFee = userManageFee.subtract(assignManageFee);
                                    // 统计总额
                                    repayTotal = repayTotal.add(assignAccount).add(assignManageFee).subtract(assignChargeInterest);// 统计总和本息+管理费
                                    repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                    repayCapital = repayCapital.add(assignCapital);
                                    repayInterest = repayInterest.add(assignInterest);
                                    repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                    repayChargeInterest = repayChargeInterest.add(assignChargeInterest);// 统计提前还款减少的利息
                                    repayChargePenaltyInterest = repayChargePenaltyInterest.add(acctualAsignInterest);// 统计提前还款罚息
                                }
                                repayRecoverPlanBean.setCreditRepayList(creditRepayBeanList);
                            }
                            if (borrowRecoverPlan.getCreditStatus() != 2) {
                                //出让人剩余部分不再通过兜底进行计算，通过剩余本金进行计算
                                BigDecimal acctualUserInterest = UnnormalRepayUtils.aheadEndRepayInterest(recoverUserCapital, borrow.getBorrowApr(), 0);
                                if (isLastPeriod) {
                                    acctualUserInterest = UnnormalRepayUtils.aheadLastRepayInterest(recoverUserCapital, borrow.getBorrowApr(), totalPeriod);
                                }
                                if (acctualUserInterest.compareTo(userInterest) >= 0) {
                                    userChargeInterest = BigDecimal.ZERO;
                                } else {
                                    userChargeInterest = userInterest.subtract(acctualUserInterest);
                                }
                                userChargePenaltyInterest = acctualUserInterest;// 提前还款罚息
                                // 统计总额
                                repayTotal = repayTotal.add(userAccount).add(userManageFee).subtract(userChargeInterest);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                repayCapital = repayCapital.add(userCapital);
                                repayInterest = repayInterest.add(userInterest);
                                repayManageFee = repayManageFee.add(userManageFee);// 统计管理费
                                repayChargeInterest = repayChargeInterest.add(userChargeInterest);
                                repayChargePenaltyInterest = repayChargePenaltyInterest.add(acctualUserInterest);

                            }
                        } else {//计划还款
                            boolean overFlag = false;
                            List<HjhDebtCreditRepay> creditRepayList = this.selectHjhDebtCreditRepay(borrowNid, recoverNid, repayPeriod, borrowRecoverPlan.getRecoverStatus());
                            if (creditRepayList != null && creditRepayList.size() > 0) {
                                List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                                BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户还款管理费
                                BigDecimal sumCreditAccount = BigDecimal.ZERO;//总承接金额
                                BigDecimal assignChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
                                int hjhFlag = 0;
                                for (HjhDebtCreditRepay hjhDebtCreditRepayBean : creditRepayList) {
                                    hjhFlag++;
                                    sumCreditAccount = sumCreditAccount.add(hjhDebtCreditRepayBean.getRepayAccount());
                                }
                                //判断当前期是否全部承接
                                overFlag = isOverUndertake(borrowRecover, borrowRecoverPlan.getRecoverAccount(), sumCreditAccount, true, hjhFlag);
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
                                        assignManageFee = getManageFee(borrowStyle, feeRate, differentialRate, borrowVerifyTime, borrowPeriod, repayPeriod, assignCapital, assignManageFee, borrowRecover.getRecoverCapital(), creditTender.getAssignCapital());
                                    }

                                    // modify by cwyang 2018-5-23 计算金额取自剩余承接本金
                                    BigDecimal assignUserCapital = getAssignSurplusCapital(assignNid, recoverNid);
                                    BigDecimal acctualAsignInterest = UnnormalRepayUtils.aheadEndRepayInterest(assignUserCapital, borrow.getBorrowApr(), 0);
                                    if (isLastPeriod) {
                                        acctualAsignInterest = UnnormalRepayUtils.aheadLastRepayInterest(assignUserCapital, borrow.getBorrowApr(), totalPeriod);
                                    }
                                    if (acctualAsignInterest.compareTo(assignInterest) >= 0) {
                                        assignChargeInterest = BigDecimal.ZERO;
                                    } else {
                                        assignChargeInterest = assignInterest.subtract(acctualAsignInterest);
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
                                    creditRepayBean.setRepayAdvancePenaltyInterest(acctualAsignInterest);// 提前还款罚息
                                    creditRepayBean.setAdvanceDays(distanceDays);
                                    creditRepayBeanList.add(creditRepayBean);
                                    // 统计出让人还款金额
                                    userAccount = userAccount.subtract(assignAccount);
                                    userCapital = userCapital.subtract(assignCapital);
                                    recoverUserCapital = recoverUserCapital.subtract(assignUserCapital);
                                    userInterest = userInterest.subtract(assignInterest);
                                    userManageFee = userManageFee.subtract(assignManageFee);
                                    // 统计总额
                                    repayTotal = repayTotal.add(assignAccount).subtract(assignChargeInterest).add(assignManageFee);// 统计总和本息+管理费
                                    repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                    repayCapital = repayCapital.add(assignCapital);
                                    repayInterest = repayInterest.add(assignInterest);
                                    repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                    repayChargeInterest = repayChargeInterest.add(assignChargeInterest);// 统计提前还款减少的利息
                                    repayChargePenaltyInterest = repayChargePenaltyInterest.add(acctualAsignInterest);// 统计提前还款罚息
                                }
                                repayRecoverPlanBean.setHjhCreditRepayList(creditRepayBeanList);
                            }
                            if (overFlag) {
                                //出让人剩余部分不再通过兜底进行计算，通过剩余本金进行计算
                                BigDecimal acctualUserInterest = UnnormalRepayUtils.aheadEndRepayInterest(recoverUserCapital, borrow.getBorrowApr(), 0);
                                if (isLastPeriod) {
                                    acctualUserInterest = UnnormalRepayUtils.aheadLastRepayInterest(recoverUserCapital, borrow.getBorrowApr(), totalPeriod);
                                }
                                if (acctualUserInterest.compareTo(userInterest) >= 0) {
                                    userChargeInterest = BigDecimal.ZERO;
                                } else {
                                    userChargeInterest = userInterest.subtract(acctualUserInterest);
                                }
                                userChargePenaltyInterest = acctualUserInterest;// 提前还款罚息
                                // 统计借款人还款总额
                                repayTotal = repayTotal.add(userAccount).add(userManageFee).subtract(userChargeInterest);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                repayCapital = repayCapital.add(userCapital);
                                repayInterest = repayInterest.add(userInterest);
                                repayManageFee = repayManageFee.add(userManageFee);// 统计管理费
                                repayChargeInterest = repayChargeInterest.add(userChargeInterest);
                                repayChargePenaltyInterest = repayChargePenaltyInterest.add(acctualUserInterest);
                            }
                        }

                    } else {
                        // 无承接的原始出借人债权
                        repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userManageFee);// 统计总和本息+管理费
                        repayAccount = repayAccount.add(userAccount); // 统计本息总和
                        repayCapital = repayCapital.add(userCapital);
                        repayInterest = repayInterest.add(userInterest);
                        repayManageFee = repayManageFee.add(userManageFee);// 管理费
                        repayChargeInterest = repayChargeInterest.add(userChargeInterest);
                        repayChargePenaltyInterest = repayChargePenaltyInterest.add(userChargePenaltyInterest);
                    }
                    BeanUtils.copyProperties(borrowRecoverPlan, repayRecoverPlanBean);
                    // 承接后的原始出借人债权
                    repayRecoverPlanBean.setRecoverTotal(userAccount.subtract(userChargeInterest).add(userManageFee));
                    repayRecoverPlanBean.setRecoverAccount(userAccount);
                    repayRecoverPlanBean.setRecoverCapital(userCapital);
                    repayRecoverPlanBean.setRecoverInterest(userInterest);
                    repayRecoverPlanBean.setRecoverFee(userManageFee);
                    repayRecoverPlanBean.setChargeDays(distanceDays);
                    repayRecoverPlanBean.setChargeInterest(userChargeInterest.multiply(new BigDecimal(-1)));// 原始出借人剩余债权提前还款实际减息
                    repayRecoverPlanBean.setChargePenaltyInterest(userChargePenaltyInterest);// 原始出借人剩余债权提前还款罚息
                    repayRecoverPlanBean.setAdvanceStatus(1);
                    repayRecoverPlanList.add(repayRecoverPlanBean);
                }
            }

        }
        borrowRepayPlan.setRecoverPlanList(repayRecoverPlanList);
        // 借款人还款总额
        borrowRepayPlan.setRepayAccount(repayAccount);
        borrowRepayPlan.setRepayAccountAll(repayTotal);
        borrowRepayPlan.setRepayCapital(repayCapital);
        borrowRepayPlan.setRepayInterest(repayInterest);
        borrowRepayPlan.setRepayFee(repayManageFee);
        borrowRepayPlan.setAdvanceStatus(1);
        borrowRepayPlan.setChargeDays(distanceDays);
        borrowRepayPlan.setChargeInterest(repayChargeInterest.multiply(new BigDecimal(-1)));
        borrowRepayPlan.setChargePenaltyInterest(repayChargePenaltyInterest);
    }

    /**
     * 获得剩余本金
     */
    private BigDecimal getAssignSurplusCapital(String assignNid, String recoverNid) {
        HjhDebtCreditRepayExample example = new HjhDebtCreditRepayExample();
        HjhDebtCreditRepayExample.Criteria criteria = example.createCriteria();
        criteria.andAssignOrderIdEqualTo(assignNid);
        criteria.andInvestOrderIdEqualTo(recoverNid);
        criteria.andRepayStatusEqualTo(0);
        criteria.andDelFlagEqualTo(0);
        List<HjhDebtCreditRepay> repayList = this.hjhDebtCreditRepayMapper.selectByExample(example);
        if (repayList != null && repayList.size() > 0) {
            BigDecimal sumCapital = BigDecimal.ZERO;
            for (HjhDebtCreditRepay info : repayList) {
                sumCapital = sumCapital.add(info.getRepayCapital());
            }
            return sumCapital;
        }
        return BigDecimal.ZERO;
    }

    /***
     * 计算用户分期还款本期应还金额
     */
    private void calculateRecoverPlan(RepayDetailBean borrowRepayPlan, Borrow borrow, int period, Integer repayTimeStart, List<BorrowRecover> borrowRecoverList) throws Exception {

        Integer repayTimeInt = borrowRepayPlan.getRepayTime();
        int time = GetDate.getNowTime10();
        // 用户计划还款时间
        String repayTime = GetDate.getDateTimeMyTimeInMillis(repayTimeInt);
        // 用户实际还款时间
        String nowTime = GetDate.getDateTimeMyTimeInMillis(time);
        // 获取实际还款同计划还款时间的时间差
        int distanceDays = GetDate.daysBetween(nowTime, repayTime);
        if (distanceDays < 0) {// 用户逾期了
            this.calculateRecoverPlanLate(borrowRepayPlan, borrow, -distanceDays, borrowRecoverList);
        } else {// 用户正常或者提前还款
            // 获取提前还款的阀值
            String repayAdvanceDay = this.getBorrowConfig("REPAY_ADVANCE_TIME");
            int advanceDays = distanceDays;
            //如果是融通宝项目,不判断提前还款的阙值 add by cwyang 2017-6-14
            int projectType = borrow.getProjectType();
            if (13 == projectType) {
                repayAdvanceDay = "0";
            }
            if (Integer.parseInt(repayAdvanceDay) < advanceDays) {// 用户提前还款
                // 提前还款方便页面判断，实际数据不更新
                borrowRepayPlan.setAdvanceStatus(1);
            } else {// 用户正常还款
                borrowRepayPlan.setAdvanceStatus(0);
            }

            // 计算用户实际还款总额 提前还款当期不减息
            this.calculateRecoverPlan(borrowRepayPlan, borrow, advanceDays);

            borrowRepayPlan.setChargeDays(advanceDays);
        }
    }

    /**
     * 统计分期还款用户逾期还款的总标
     */
    private void calculateRecoverPlanLate(RepayDetailBean borrowRepayPlan, Borrow borrow, int lateDays, List<BorrowRecover> borrowRecoverList) throws Exception {

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
        // 将calculateRecoverPlanAll的borrowRecoverList移到方法外查询 update by wgx 2019/03/21
        //List<BorrowRecover> borrowRecoverList = this.getBorrowRecover(borrow.getBorrowNid());
        List<BorrowRecoverPlan> borrowRecoverPlans = getBorrowRecoverPlan(borrow.getBorrowNid(), borrowRepayPlan.getRepayPeriod());
        List<RepayRecoverPlanBean> repayRecoverPlanList = new ArrayList<RepayRecoverPlanBean>();
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        BigDecimal repayOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
        int repayMoneySource = borrowRepayPlan.getRepayMoneySource() == null ? 0 : borrowRepayPlan.getRepayMoneySource();// 还款来源，提交还款会更新，用于判断是否已提交还款
        if (borrowRecoverList != null && borrowRecoverList.size() > 0) {
            for (int i = 0; i < borrowRecoverList.size(); i++) {
                BorrowRecover borrowRecover = borrowRecoverList.get(i);
                String recoverNid = borrowRecover.getNid();// 出借订单号
                int recoverUserId = borrowRecover.getUserId();// 出借用户userId
                if (borrowRecoverPlans != null && borrowRecoverPlans.size() > 0) {
                    BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                    BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户还款管理费
                    BigDecimal userOverdueInterest = BigDecimal.ZERO;// 计算原始投资用户逾期还款利息
                    for (int j = 0; j < borrowRecoverPlans.size(); j++) {
                        BorrowRecoverPlan borrowRecoverPlan = borrowRecoverPlans.get(j);
                        String recoverPlanNid = borrowRecoverPlan.getNid();// 出借订单号
                        int recoverPlanUserId = borrowRecoverPlan.getUserId();// 出借用户userId
                        userAccount = borrowRecoverPlan.getRecoverAccount();
                        userCapital = borrowRecoverPlan.getRecoverCapital();
                        userInterest = borrowRecoverPlan.getRecoverInterest();
                        userOverdueInterest = borrowRecoverPlan.getLateInterest();// batch计算的逾期利息
                        int userAdvanceStatus = borrowRecoverPlan.getAdvanceStatus();// batch计算的还款状态
                        int userLateDays = borrowRecoverPlan.getLateDays();// batch计算的逾期天数
                        if (repayMoneySource == 0 && (userAdvanceStatus != 3 || userLateDays != lateDays)) {
                            logger.error("【还款明细】计算的逾期信息有误！计算逾期状态：{}，计算逾期天数：{}，实际逾期天数：{}",
                                    userAdvanceStatus == 3 ? "已逾期" : "未逾期", userLateDays, lateDays);
                            throw new Exception("计算的逾期信息有误！");
                        }
                        // 获取应还款管理费
                        userManageFee = borrowRecoverPlan.getRecoverFee();
                        if (recoverPlanNid.equals(recoverNid) && recoverUserId == recoverPlanUserId) {
                            RepayRecoverPlanBean repayRecoverPlanBean = new RepayRecoverPlanBean();
                            // 给页面展示，就不计算了
                            repayRecoverPlanBean.setRecoverAccountOld(userAccount);
                            repayRecoverPlanBean.setChargeInterestOld(borrowRecoverPlan.getChargeInterest());
                            repayRecoverPlanBean.setChargePenaltyInterestOld(borrowRecoverPlan.getChargePenaltyInterest());
                            repayRecoverPlanBean.setLateInterestOld(borrowRecoverPlan.getLateInterest());
                            repayRecoverPlanBean.setRecoverAccountYesOld(borrowRecoverPlan.getRecoverAccountYes());

                            repayRecoverPlanBean.setRecoverCapitalOld(borrowRecover.getRecoverCapital());
                            repayRecoverPlanBean.setCreditAmountOld(borrowRecover.getCreditAmount());

                            if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                                if (Validator.isNull(borrowRecover.getAccedeOrderId())) {
                                    // 直投类项目债转还款
                                    List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, recoverNid, repayPeriod, 0);
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                                        CreditRepay creditRepay = null;
                                        RepayCreditRepayBean creditRepayBean = null;
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户还款管理费
                                        BigDecimal assignOverdueInterest = BigDecimal.ZERO;// 债转用户逾期利息
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            creditRepay = creditRepayList.get(k);
                                            creditRepayBean = new RepayCreditRepayBean();
                                            String assignNid = creditRepay.getAssignNid();// 承接订单号
                                            CreditTender creditTender = this.getCreditTender(assignNid);// 查询相应的承接记录
                                            assignAccount = creditRepay.getAssignAccount();// 承接本息
                                            assignCapital = creditRepay.getAssignCapital();// 用户实际还款本本金
                                            assignInterest = creditRepay.getAssignInterest();
                                            assignOverdueInterest = creditRepay.getLateInterest();// batch计算的逾期利息
                                            int assignAdvanceStatus = creditRepay.getAdvanceStatus();// batch计算的还款状态
                                            int assignLateDays = creditRepay.getLateDays();// batch计算的逾期天数
                                            if (repayMoneySource == 0 && (assignAdvanceStatus != 3 || assignLateDays != lateDays)) {
                                                logger.error("【还款明细】计算的债转逾期信息有误！计算逾期状态：{}，计算逾期天数：{}，实际逾期天数：{}",
                                                        assignAdvanceStatus == 3 ? "已逾期" : "未逾期", assignLateDays, lateDays);
                                                throw new Exception("计算的逾期信息有误！");
                                            }
                                            // 计算用户实际获得的本息和
                                            //assignAccount = assignAccount.add(assignOverdueInterest);

                                            //最后一笔兜底
                                            if (borrowRecoverPlan.getCreditStatus() == 2 && k == creditRepayList.size() - 1) {
                                                assignManageFee = userManageFee;
                                            } else {
                                                assignManageFee = getManageFee(borrowStyle, feeRate, differentialRate, borrowVerifyTime, borrowPeriod, repayPeriod, assignCapital, assignManageFee, borrow.getAccount(), creditTender.getAssignCapital());
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.add(assignOverdueInterest).add(assignManageFee));
                                            creditRepayBean.setManageFee(assignManageFee);
                                            creditRepayBeanList.add(creditRepayBean);
                                            // 统计出让人还款金额
                                            userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                            userCapital = userCapital.subtract(assignCapital);
                                            userInterest = userInterest.subtract(assignInterest);
                                            userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
                                            userOverdueInterest = userOverdueInterest.subtract(assignOverdueInterest);// 逾期利息
                                            // 统计总额
                                            repayTotal = repayTotal.add(assignAccount).add(assignOverdueInterest).add(assignManageFee);// 统计总和本息+管理费
                                            repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                            repayCapital = repayCapital.add(assignCapital);
                                            repayInterest = repayInterest.add(assignInterest);
                                            repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                            repayOverdueInterest = repayOverdueInterest.add(assignOverdueInterest);// 统计借款用户总逾期利息
                                        }
                                        repayRecoverPlanBean.setCreditRepayList(creditRepayBeanList);
                                    }
                                    if (borrowRecoverPlan.getCreditStatus() != 2) {
                                        // 统计总和
                                        repayTotal = repayTotal.add(userAccount).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayManageFee = repayManageFee.add(userManageFee);// 统计管理费
                                        repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);// 统计借款用户总逾期利息
                                    }
                                } else {
                                    // 计划类项目债转还款
                                    List<HjhDebtCreditRepay> creditRepayList = this.selectHjhDebtCreditRepay(borrowNid, recoverNid, repayPeriod, borrowRecoverPlan.getRecoverStatus());
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                                        HjhDebtCreditRepay creditRepay = null;
                                        HjhDebtCreditRepayBean creditRepayBean = null;
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户还款管理费
                                        BigDecimal assignOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
                                        BigDecimal sumCreditAccount = BigDecimal.ZERO;//总承接金额
                                        int hjhFlag = 0;
                                        for (HjhDebtCreditRepay hjhDebtCreditRepayBean : creditRepayList) {
                                            hjhFlag++;
                                            sumCreditAccount = sumCreditAccount.add(hjhDebtCreditRepayBean.getRepayAccount());
                                        }
                                        //判断当前期是否全部承接
                                        boolean overFlag = isOverUndertake(borrowRecover, borrowRecoverPlan.getRecoverAccount(), sumCreditAccount, true, hjhFlag);
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            creditRepay = creditRepayList.get(k);
                                            creditRepayBean = new HjhDebtCreditRepayBean();
                                            String assignNid = creditRepay.getAssignOrderId();// 承接订单号
                                            HjhDebtCreditTender creditTender = this.getHjhDebtCreditTender(assignNid);// 查询相应的承接记录
                                            assignAccount = creditRepay.getRepayAccount();// 承接本息
                                            assignCapital = creditRepay.getRepayCapital();// 用户实际还款本本金
                                            assignInterest = creditRepay.getRepayInterest();
                                            assignOverdueInterest = creditRepay.getRepayLateInterest();// batch计算的逾期利息
                                            int assignAdvanceStatus = creditRepay.getAdvanceStatus();// batch计算的还款状态
                                            int assignLateDays = creditRepay.getLateDays();// batch计算的逾期天数
                                            if (repayMoneySource == 0 && (assignAdvanceStatus != 3 || assignLateDays != lateDays)) {
                                                logger.error("【还款明细】计算的债转逾期信息有误！计算逾期状态：{}，计算逾期天数：{}，实际逾期天数：{}",
                                                        assignAdvanceStatus == 3 ? "已逾期" : "未逾期", assignLateDays, lateDays);
                                                throw new Exception("计算的逾期信息有误！");
                                            }
                                            //最后一笔兜底
                                            if (!overFlag && k == creditRepayList.size() - 1) {
                                                assignManageFee = userManageFee;
                                            } else {
                                                assignManageFee = getManageFee(borrowStyle, feeRate, differentialRate, borrowVerifyTime, borrowPeriod, repayPeriod, assignCapital, assignManageFee, borrowRecover.getRecoverCapital(), creditTender.getAssignCapital());
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.add(assignOverdueInterest).add(assignManageFee));
                                            creditRepayBean.setManageFee(assignManageFee);
                                            creditRepayBeanList.add(creditRepayBean);
                                            // 统计出让人还款金额
                                            userAccount = userAccount.subtract(assignAccount);// 获取应还款本息
                                            userCapital = userCapital.subtract(assignCapital);
                                            userInterest = userInterest.subtract(assignInterest);
                                            userManageFee = userManageFee.subtract(assignManageFee);// 获取应还款管理费
                                            userOverdueInterest = userOverdueInterest.subtract(assignOverdueInterest);// 逾期利息
                                            // 统计总额
                                            repayTotal = repayTotal.add(assignAccount).add(assignOverdueInterest).add(assignManageFee);// 统计总和本息+管理费
                                            repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                            repayCapital = repayCapital.add(assignCapital);
                                            repayInterest = repayInterest.add(assignInterest);
                                            repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                            repayOverdueInterest = repayOverdueInterest.add(assignOverdueInterest);// 统计借款用户总逾期利息
                                        }
                                        repayRecoverPlanBean.setHjhCreditRepayList(creditRepayBeanList);
                                    }
                                    if (borrowRecoverPlan.getCreditStatus() != 2) {
                                        // 统计总和
                                        repayTotal = repayTotal.add(userAccount).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayManageFee = repayManageFee.add(userManageFee);// 统计管理费
                                        repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);// 统计借款用户总逾期利息
                                    }
                                }
                            } else {
                                // 统计总和
                                repayTotal = repayTotal.add(userAccount).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                repayCapital = repayCapital.add(userCapital);
                                repayInterest = repayInterest.add(userInterest);
                                repayManageFee = repayManageFee.add(userManageFee);// 统计管理费
                                repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);// 累加借款用户总逾期利息
                            }
                            // 计算还款期的数据
                            BeanUtils.copyProperties(borrowRecoverPlan, repayRecoverPlanBean);
                            repayRecoverPlanBean.setRecoverTotal(userAccount.add(userOverdueInterest).add(userManageFee));
                            repayRecoverPlanBean.setRecoverAccount(userAccount);
                            repayRecoverPlanBean.setRecoverCapital(userCapital);
                            repayRecoverPlanBean.setRecoverInterest(userInterest);
                            repayRecoverPlanBean.setRecoverFee(userManageFee);
                            repayRecoverPlanBean.setLateInterest(userOverdueInterest);
                            repayRecoverPlanBean.setLateDays(lateDays);
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
        borrowRepayPlan.setLateDays(lateDays);
        borrowRepayPlan.setLateInterest(repayOverdueInterest);
        borrowRepayPlan.setAdvanceStatus(3);
    }

    private BigDecimal getManageFee(String borrowStyle, BigDecimal feeRate, BigDecimal differentialRate, int borrowVerifyTime, Integer borrowPeriod, int repayPeriod, BigDecimal assignCapital, BigDecimal assignManageFee, BigDecimal account, BigDecimal assignCapital2) {
        // 等额本息month、等额本金principal
        if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
            if (repayPeriod == borrowPeriod.intValue()) {
                assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 1,
                        account, borrowPeriod, borrowVerifyTime);
            } else {
                assignManageFee = AccountManagementFeeUtils.getMonthAccountManagementFee(assignCapital, feeRate, repayPeriod, differentialRate, 0,
                        account, borrowPeriod, borrowVerifyTime);
            }
        }
        // 先息后本endmonth
        else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {
            if (repayPeriod == borrowPeriod.intValue()) {
                assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(assignCapital2, feeRate,
                        borrowPeriod, borrowPeriod, differentialRate, 1, borrowVerifyTime);
            } else {
                assignManageFee = AccountManagementFeeUtils.getBeforeInterestAfterPrincipalAccountManagementFee(assignCapital2, feeRate,
                        borrowPeriod, borrowPeriod, differentialRate, 0, borrowVerifyTime);
            }
        }
        return assignManageFee;
    }

    /**
     * 统计分期还款用户正常还款的总标
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
        List<BorrowRecover> borrowRecoverList = this.getBorrowRecover(borrow.getBorrowNid());
        List<BorrowRecoverPlan> borrowRecoverPlans = getBorrowRecoverPlan(borrow.getBorrowNid(), borrowRepayPlan.getRepayPeriod());
        List<RepayRecoverPlanBean> repayRecoverPlanList = new ArrayList<RepayRecoverPlanBean>();
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        if (borrowRecoverList != null && borrowRecoverList.size() > 0) {
            for (int i = 0; i < borrowRecoverList.size(); i++) {
                BorrowRecover borrowRecover = borrowRecoverList.get(i);
                String recoverNid = borrowRecover.getNid();// 出借订单号
                int recoverUserId = borrowRecover.getUserId();// 出借用户userId
                if (borrowRecoverPlans != null && borrowRecoverPlans.size() > 0) {
                    BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                    BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户还款管理费
                    for (int j = 0; j < borrowRecoverPlans.size(); j++) {
                        BorrowRecoverPlan borrowRecoverPlan = borrowRecoverPlans.get(j);
                        String recoverPlanNid = borrowRecoverPlan.getNid();// 出借订单号
                        int recoverPlanUserId = borrowRecoverPlan.getUserId();// 出借用户userId
                        if (recoverPlanNid.equals(recoverNid) && recoverUserId == recoverPlanUserId) {
                            RepayRecoverPlanBean repayRecoverPlanBean = new RepayRecoverPlanBean();
                            userAccount = borrowRecoverPlan.getRecoverAccount();// 获取应还款本息
                            userCapital = borrowRecoverPlan.getRecoverCapital();
                            userInterest = borrowRecoverPlan.getRecoverInterest();
                            userManageFee = borrowRecoverPlan.getRecoverFee();// 获取应还款管理费

                            // 给页面展示，就不计算了
                            repayRecoverPlanBean.setRecoverAccountOld(userAccount);
                            repayRecoverPlanBean.setChargeInterestOld(borrowRecoverPlan.getChargeInterest());
                            repayRecoverPlanBean.setChargePenaltyInterestOld(borrowRecoverPlan.getChargePenaltyInterest());
                            repayRecoverPlanBean.setLateInterestOld(borrowRecoverPlan.getLateInterest());
                            repayRecoverPlanBean.setRecoverAccountYesOld(borrowRecoverPlan.getRecoverAccountYes());

                            repayRecoverPlanBean.setRecoverCapitalOld(borrowRecover.getRecoverCapital());
                            repayRecoverPlanBean.setCreditAmountOld(borrowRecover.getCreditAmount());
                            // 如果发生债转
                            if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                                if (Validator.isNull(borrowRecover.getAccedeOrderId())) {
                                    List<CreditRepay> creditRepayList = this.selectCreditRepay(borrowNid, recoverNid, repayPeriod, 0);

                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户还款管理费
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
                                                assignManageFee = getManageFee(borrowStyle, feeRate, differentialRate, borrowVerifyTime, borrowPeriod, repayPeriod, assignCapital, assignManageFee, borrow.getAccount(), creditTender.getAssignCapital());
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.add(assignManageFee));
                                            creditRepayBean.setManageFee(assignManageFee);
                                            creditRepayBean.setAdvanceStatus(0);
                                            creditRepayBean.setChargeInterest(BigDecimal.ZERO);
                                            creditRepayBean.setChargePenaltyInterest(BigDecimal.ZERO);
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
                                        repayManageFee = repayManageFee.add(userManageFee);// 统计管理费
                                    }
                                } else {//计划还款
                                    boolean overFlag = false;
                                    List<HjhDebtCreditRepay> creditRepayList = this.selectHjhDebtCreditRepay(borrowNid, recoverNid, repayPeriod, borrowRecoverPlan.getRecoverStatus());
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户还款管理费
                                        BigDecimal sumCreditAccount = BigDecimal.ZERO;//总承接金额
                                        int hjhFlag = 0;
                                        for (HjhDebtCreditRepay hjhDebtCreditRepayBean : creditRepayList) {
                                            hjhFlag++;
                                            sumCreditAccount = sumCreditAccount.add(hjhDebtCreditRepayBean.getRepayAccount());
                                        }
                                        //判断当前期是否全部承接
                                        overFlag = isOverUndertake(borrowRecover, borrowRecoverPlan.getRecoverAccount(), sumCreditAccount, true, hjhFlag);
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
                                                assignManageFee = getManageFee(borrowStyle, feeRate, differentialRate, borrowVerifyTime, borrowPeriod, repayPeriod, assignCapital, assignManageFee, borrowRecover.getRecoverCapital(), creditTender.getAssignCapital());
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
                                    if (overFlag) {
                                        // 统计总额
                                        repayTotal = repayTotal.add(userAccount).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayManageFee = repayManageFee.add(userManageFee);// 统计管理费
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
     * 统计分期还款用户还款信息
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
        List<BorrowRecover> borrowRecoverList = this.getBorrowRecover(borrow.getBorrowNid());
        List<BorrowRecoverPlan> borrowRecoverPlans = getBorrowRecoverPlan(borrow.getBorrowNid(), borrowRepayPlan.getRepayPeriod());
        List<RepayRecoverPlanBean> repayRecoverPlanList = new ArrayList<RepayRecoverPlanBean>();
        BigDecimal repayTotal = BigDecimal.ZERO; // 用户实际还款本息+管理费
        BigDecimal repayAccount = BigDecimal.ZERO; // 用户实际还款本息
        BigDecimal repayCapital = BigDecimal.ZERO; // 用户实际还款本金
        BigDecimal repayInterest = BigDecimal.ZERO;// 用户实际还款利息
        BigDecimal repayChargeInterest = BigDecimal.ZERO;// 提前还款利息
        BigDecimal repayChargePenaltyInterest = BigDecimal.ZERO;// 提前还款罚息
        BigDecimal repayOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
        BigDecimal repayManageFee = BigDecimal.ZERO;// 提前还款管理费
        if (borrowRecoverList != null && borrowRecoverList.size() > 0) {
            for (int i = 0; i < borrowRecoverList.size(); i++) {
                BorrowRecover borrowRecover = borrowRecoverList.get(i);
                String recoverNid = borrowRecover.getNid();// 出借订单号
                int recoverUserId = borrowRecover.getUserId();// 出借用户userId
                if (borrowRecoverPlans != null && borrowRecoverPlans.size() > 0) {
                    BigDecimal userAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                    BigDecimal userCapital = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userInterest = BigDecimal.ZERO; // 用户实际还款本本金
                    BigDecimal userChargeInterest = BigDecimal.ZERO;// 计算用户提前还款利息
                    BigDecimal userChargePenaltyInterest = BigDecimal.ZERO;// 计算用户提前还款罚息
                    BigDecimal userOverdueInterest = BigDecimal.ZERO;// 计算用户逾期还款利息
                    BigDecimal userManageFee = BigDecimal.ZERO;// 计算用户还款管理费
                    for (int j = 0; j < borrowRecoverPlans.size(); j++) {
                        BorrowRecoverPlan borrowRecoverPlan = borrowRecoverPlans.get(j);
                        String recoverPlanNid = borrowRecoverPlan.getNid();// 出借订单号
                        int recoverPlanUserId = borrowRecoverPlan.getUserId();// 出借用户userId
                        if (recoverPlanNid.equals(recoverNid) && recoverUserId == recoverPlanUserId) {
                            RepayRecoverPlanBean repayRecoverPlanBean = new RepayRecoverPlanBean();
                            userAccount = borrowRecoverPlan.getRecoverAccount();// 获取应还款本息
                            userCapital = borrowRecoverPlan.getRecoverCapital();
                            userInterest = borrowRecoverPlan.getRecoverInterest();
                            userChargeInterest = borrowRecoverPlan.getChargeInterest();
                            userChargePenaltyInterest = borrowRecoverPlan.getChargePenaltyInterest();
                            userManageFee = borrowRecoverPlan.getRecoverFee();// 获取应还款管理费
                            userOverdueInterest = borrowRecoverPlan.getLateInterest();

                            // 给页面展示，就不计算了
                            repayRecoverPlanBean.setRecoverAccountOld(userAccount);
                            repayRecoverPlanBean.setChargeInterestOld(borrowRecoverPlan.getChargeInterest());
                            repayRecoverPlanBean.setChargePenaltyInterestOld(borrowRecoverPlan.getChargePenaltyInterest());
                            repayRecoverPlanBean.setLateInterestOld(borrowRecoverPlan.getLateInterest());
                            repayRecoverPlanBean.setRecoverAccountYesOld(borrowRecoverPlan.getRecoverAccountYes());

                            repayRecoverPlanBean.setRecoverCapitalOld(borrowRecover.getRecoverCapital());
                            repayRecoverPlanBean.setCreditAmountOld(borrowRecover.getCreditAmount());
                            // 如果发生债转
                            if (borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                                if (Validator.isNull(borrowRecover.getAccedeOrderId())) {
                                    // 直投类项目债转还款
                                    List<CreditRepay> creditRepayList = this.selectCreditRepayList(borrowNid, recoverNid, repayPeriod);
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<RepayCreditRepayBean> creditRepayBeanList = new ArrayList<RepayCreditRepayBean>();
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
                                        BigDecimal assignChargePenaltyInterest = BigDecimal.ZERO;// 计算用户提前还款罚息
                                        BigDecimal assignOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户还款管理费
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            CreditRepay creditRepay = creditRepayList.get(k);
                                            RepayCreditRepayBean creditRepayBean = new RepayCreditRepayBean();
                                            String assignNid = creditRepay.getAssignNid();// 承接订单号
                                            CreditTender creditTender = this.getCreditTender(assignNid);// 查询相应的承接记录
                                            assignAccount = creditRepay.getAssignAccount();// 承接本息
                                            assignCapital = creditRepay.getAssignCapital();// 用户实际还款本本金
                                            assignInterest = creditRepay.getAssignInterest();
                                            assignChargeInterest = creditRepay.getChargeInterest();
                                            assignChargePenaltyInterest = creditRepay.getChargePenaltyInterest();
                                            assignOverdueInterest = creditRepay.getLateInterest(); // 计算用户逾期利息
                                            if (borrowRecoverPlan.getCreditStatus() == 2 && k == creditRepayList.size() - 1) {
                                                assignManageFee = userManageFee;
                                            } else {
                                                if (creditRepay.getStatus() == 1) {
                                                    assignManageFee = creditRepay.getManageFee();
                                                } else {
                                                    assignManageFee = getManageFee(borrowStyle, feeRate, differentialRate, borrowVerifyTime, borrowPeriod, repayPeriod, assignCapital, assignManageFee, borrow.getAccount(), creditTender.getAssignCapital());
                                                }
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.add(assignChargeInterest).add(assignOverdueInterest).add(assignManageFee));
                                            creditRepayBean.setManageFee(assignManageFee);
                                            creditRepayBeanList.add(creditRepayBean);
                                            // 统计出让人还款金额
                                            userAccount = userAccount.subtract(assignAccount);
                                            userCapital = userCapital.subtract(assignCapital);
                                            userInterest = userInterest.subtract(assignInterest);
                                            userChargeInterest = userChargeInterest.subtract(assignChargeInterest);// 提前还款利息
                                            userChargePenaltyInterest = userChargePenaltyInterest.subtract(assignChargePenaltyInterest);// 提前还款罚息
                                            userOverdueInterest = userOverdueInterest.subtract(assignOverdueInterest);// 逾期利息
                                            userManageFee = userManageFee.subtract(assignManageFee);
                                            // 统计总额
                                            repayTotal = repayTotal.add(assignAccount).add(assignChargeInterest).add(assignOverdueInterest).add(assignManageFee);// 统计总和本息+管理费
                                            repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                            repayCapital = repayCapital.add(assignCapital);
                                            repayInterest = repayInterest.add(assignInterest);
                                            repayChargeInterest = repayChargeInterest.add(assignChargeInterest);// 统计提前还款减少的利息
                                            repayChargePenaltyInterest = repayChargePenaltyInterest.add(assignChargePenaltyInterest);// 统计提前还款罚息
                                            repayOverdueInterest = repayOverdueInterest.add(assignOverdueInterest);// 统计借款用户总逾期利息
                                            repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                        }
                                        repayRecoverPlanBean.setCreditRepayList(creditRepayBeanList);
                                    }
                                    if (borrowRecoverPlan.getCreditStatus() != 2) {
                                        // 统计总额
                                        repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayChargeInterest = repayChargeInterest.add(userChargeInterest);// 统计提前还款减少的利息
                                        repayChargePenaltyInterest = repayChargePenaltyInterest.add(userChargePenaltyInterest);// 统计提前还款罚息
                                        repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);// 统计借款用户总逾期利息
                                        repayManageFee = repayManageFee.add(userManageFee);// 统计管理费
                                    }
                                } else {
                                    // 计划类项目债转还款
                                    List<HjhDebtCreditRepay> creditRepayList = this.selectHjhDebtCreditRepay(borrowNid, recoverNid, repayPeriod, borrowRecoverPlan.getRecoverStatus());
                                    if (creditRepayList != null && creditRepayList.size() > 0) {
                                        List<HjhDebtCreditRepayBean> creditRepayBeanList = new ArrayList<HjhDebtCreditRepayBean>();
                                        BigDecimal assignAccount = BigDecimal.ZERO;// 计算用户实际获得的本息和
                                        BigDecimal assignCapital = BigDecimal.ZERO; // 用户实际还款本本金
                                        BigDecimal assignInterest = BigDecimal.ZERO; // 用户实际还款利息
                                        BigDecimal assignChargeInterest = BigDecimal.ZERO;// 计算用户提前还款减少的的利息
                                        BigDecimal assignChargePenaltyInterest = BigDecimal.ZERO;// 计算用户提前还款罚息
                                        BigDecimal assignOverdueInterest = BigDecimal.ZERO;// 统计借款用户总逾期利息
                                        BigDecimal assignManageFee = BigDecimal.ZERO;// 计算用户还款管理费
                                        BigDecimal sumCreditAccount = BigDecimal.ZERO;//总承接金额
                                        int hjhFlag = 0;
                                        for (HjhDebtCreditRepay hjhDebtCreditRepayBean : creditRepayList) {
                                            hjhFlag++;
                                            sumCreditAccount = sumCreditAccount.add(hjhDebtCreditRepayBean.getRepayAccount());
                                        }
                                        //判断当前期是否全部承接
                                        boolean overFlag = isOverUndertake(borrowRecover, borrowRecoverPlan.getRecoverAccount(), sumCreditAccount, true, hjhFlag);
                                        for (int k = 0; k < creditRepayList.size(); k++) {
                                            HjhDebtCreditRepay creditRepay = creditRepayList.get(k);
                                            HjhDebtCreditRepayBean creditRepayBean = new HjhDebtCreditRepayBean();
                                            String assignNid = creditRepay.getAssignOrderId();// 承接订单号
                                            HjhDebtCreditTender creditTender = this.getHjhDebtCreditTender(assignNid);// 查询相应的承接记录
                                            assignAccount = creditRepay.getRepayAccount();// 承接本息
                                            assignCapital = creditRepay.getRepayCapital();// 用户实际还款本本金
                                            assignInterest = creditRepay.getRepayInterest();
                                            assignChargeInterest = creditRepay.getRepayAdvanceInterest();
                                            assignChargePenaltyInterest = creditRepay.getRepayAdvancePenaltyInterest();
                                            assignOverdueInterest = creditRepay.getRepayLateInterest(); // 计算用户逾期利息
                                            if (!overFlag && k == creditRepayList.size() - 1) {
                                                assignManageFee = userManageFee;
                                            } else {
                                                if (creditRepay.getRepayStatus() == 1) {
                                                    assignManageFee = creditRepay.getManageFee();
                                                } else {
                                                    assignManageFee = getManageFee(borrowStyle, feeRate, differentialRate, borrowVerifyTime, borrowPeriod, repayPeriod, assignCapital, assignManageFee, borrowRecover.getRecoverCapital(), creditTender.getAssignCapital());
                                                }
                                            }
                                            BeanUtils.copyProperties(creditRepay, creditRepayBean);
                                            creditRepayBean.setAssignTotal(assignAccount.add(assignChargeInterest).add(assignOverdueInterest).add(assignManageFee));
                                            creditRepayBean.setManageFee(assignManageFee);
                                            creditRepayBeanList.add(creditRepayBean);
                                            // 统计出让人还款金额
                                            userAccount = userAccount.subtract(assignAccount);
                                            userCapital = userCapital.subtract(assignCapital);
                                            userInterest = userInterest.subtract(assignInterest);
                                            userManageFee = userManageFee.subtract(assignManageFee);
                                            // 统计总额
                                            repayTotal = repayTotal.add(assignAccount).add(assignChargeInterest).add(assignOverdueInterest).add(assignManageFee);// 统计总和本息+管理费
                                            repayAccount = repayAccount.add(assignAccount);// 统计总和本息
                                            repayCapital = repayCapital.add(assignCapital);
                                            repayInterest = repayInterest.add(assignInterest);
                                            repayChargeInterest = repayChargeInterest.add(assignChargeInterest);// 统计提前还款减少的利息
                                            repayChargePenaltyInterest = repayChargePenaltyInterest.add(assignChargePenaltyInterest);// 统计提前还款减少的利息
                                            repayOverdueInterest = repayOverdueInterest.add(assignOverdueInterest);// 统计借款用户总逾期利息
                                            repayManageFee = repayManageFee.add(assignManageFee);// 管理费
                                        }
                                        repayRecoverPlanBean.setHjhCreditRepayList(creditRepayBeanList);
                                    }
                                    if (borrowRecoverPlan.getCreditStatus() != 2) {
                                        // 统计总额
                                        repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                                        repayAccount = repayAccount.add(userAccount);// 统计总和本息
                                        repayCapital = repayCapital.add(userCapital);
                                        repayInterest = repayInterest.add(userInterest);
                                        repayChargeInterest = repayChargeInterest.add(userChargeInterest);// 统计提前还款减少的利息
                                        repayChargePenaltyInterest = repayChargePenaltyInterest.add(userChargePenaltyInterest);// 统计提前还款减少的利息
                                        repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);// 统计借款用户总逾期利息
                                        repayManageFee = repayManageFee.add(userManageFee);// 统计管理费
                                    }
                                }
                            } else {
                                // 统计总和
                                repayTotal = repayTotal.add(userAccount).subtract(userChargeInterest).add(userOverdueInterest).add(userManageFee);// 统计总和本息+管理费
                                repayAccount = repayAccount.add(userAccount); // 统计本息总和
                                repayCapital = repayCapital.add(userCapital);
                                repayInterest = repayInterest.add(userInterest);
                                repayChargeInterest = repayChargeInterest.add(userChargeInterest);// 统计提前还款减少的利息
                                repayChargePenaltyInterest = repayChargePenaltyInterest.add(userChargePenaltyInterest);// 统计提前还款减少的利息
                                repayOverdueInterest = repayOverdueInterest.add(userOverdueInterest);// 统计借款用户总逾期利息
                                repayManageFee = repayManageFee.add(userManageFee);// 管理费
                            }
                            BeanUtils.copyProperties(borrowRecoverPlan, repayRecoverPlanBean);
                            repayRecoverPlanBean.setRecoverTotal(userAccount.add(userManageFee));
                            repayRecoverPlanBean.setRecoverAccount(userAccount);
                            repayRecoverPlanBean.setRecoverCapital(userCapital);
                            repayRecoverPlanBean.setRecoverInterest(userInterest);
                            repayRecoverPlanBean.setChargeInterest(userChargeInterest);
                            repayRecoverPlanBean.setChargePenaltyInterest(userChargePenaltyInterest);
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
        borrowRepayPlan.setChargePenaltyInterest(repayChargePenaltyInterest);
        borrowRepayPlan.setLateInterest(repayOverdueInterest);
        borrowRepayPlan.setRepayFee(repayManageFee);
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
     * 分期还款 全部结清 计算各期数据
     *
     * @param form
     * @param borrow
     * @param repayByTerm
     * @throws ParseException
     */
    private void setRecoverPlanAllDetail(ProjectBean form, Borrow borrow, RepayBean repayByTerm) throws ParseException {

        String borrowNid = borrow.getBorrowNid();
        // 还款总期数
        int periodTotal = borrow.getBorrowPeriod();

        // 计算当前还款期数
        int repayPeriod = periodTotal - repayByTerm.getRepayPeriod() + 1;
        // 如果用户不是还款最后一期
        if (repayPeriod > periodTotal) {
            form.setRepayStatus("1");
        }
        int nowPeriod = repayPeriod;
        List<Integer> lateArray = new ArrayList<>();// 逾期期数列表
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
                } else {// 用户未还款本息
                    if(userRepayPlan.getAdvanceStatus() == 3 || userRepayPlan.getAdvanceStatus() == 30){// 逾期未还款/还款中
                        lateArray.add(userRepayPlan.getRepayPeriod());
                        form.setLateStatus("1");
                        if (nowPeriod < periodTotal) {
                            nowPeriod++;
                        } else {
                            form.setOnlyAllRepay("2");// 全部逾期
                        }
                    }
                    // 此处分期计算的是本息+管理费
                    userRepayBean.setRepayTotal(userRepayPlan.getRepayAccountAll().toString());
                }
                userRepayBean.setRepayAccount(userRepayPlan.getRepayAccount().add(userRepayPlan.getChargeInterest()).add(userRepayPlan.getLateInterest())
                        .toString());// 设置本期的用户本息和
                userRepayBean.setRepayCapital(userRepayPlan.getRepayCapital().toString());// 用户未还款本息
                userRepayBean.setRepayInterest(userRepayPlan.getRepayInterest().toString()); // 设置本期的用户利息
                userRepayBean.setUserId(userRepayPlan.getUserId().toString());
                userRepayBean.setRepayPeriod(userRepayPlan.getRepayPeriod().toString());
                userRepayBean.setAdvanceStatus(userRepayPlan.getAdvanceStatus().toString());
                userRepayBean.setStatus(userRepayPlan.getRepayStatus().toString());
                userRepayBean.setRepayTime(GetDate.getDateMyTimeInMillis(userRepayPlan.getRepayTime()));
                userRepayBean.setChargeDays(userRepayPlan.getChargeDays().toString());
                // 提前还款利息拆分 add by wgx 2019/03/22
                userRepayBean.setChargeOriginalInterest(userRepayPlan.getChargePenaltyInterest().subtract(userRepayPlan.getChargeInterest()).toString());
                userRepayBean.setChargePenaltyInterest(userRepayPlan.getChargePenaltyInterest().toString());
                if ("0".equals(userRepayBean.getChargeOriginalInterest())) {
                    userRepayBean.setChargeOriginalInterest("0.00");
                }
                if ("0".equals(userRepayBean.getChargePenaltyInterest())) {
                    userRepayBean.setChargePenaltyInterest("0.00");
                }
                userRepayBean.setManageFee(userRepayPlan.getRepayFee().toString());
                userRepayBean.setLateDays(userRepayPlan.getLateDays().toString());
                userRepayBean.setLateInterest(userRepayPlan.getLateInterest().toString());
                List<RepayRecoverPlanBean> userRecoversDetails = userRepayPlan.getRecoverPlanList();
                List<ProjectRepayDetailBean> userRepayDetails = new ArrayList<ProjectRepayDetailBean>();
                for (int j = 0; j < userRecoversDetails.size(); j++) {
                    RepayRecoverPlanBean userRecoverPlan = userRecoversDetails.get(j);

//		            BorrowRecoverPlan planInfo = this.borrowRecoverPlanMapper.selectByPrimaryKey(id);

                    BigDecimal recoverAccount = userRecoverPlan.getRecoverAccountOld();
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
                            // 提前还款利息拆分 add by wgx 2019/03/22
                            userRepayDetail.setChargeOriginalInterest(creditRepay.getChargePenaltyInterest().subtract(creditRepay.getChargeInterest()).toString());
                            userRepayDetail.setChargePenaltyInterest(creditRepay.getChargePenaltyInterest().toString());
                            if ("0".equals(userRepayDetail.getChargeOriginalInterest())) {
                                userRepayDetail.setChargeOriginalInterest("0.00");
                            }
                            if ("0".equals(userRepayDetail.getChargePenaltyInterest())) {
                                userRepayDetail.setChargePenaltyInterest("0.00");
                            }
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
                            // userName直接取表中数据 update by wgx 2019/02/15
                            String userNameStr = creditRepay.getUserName().substring(0, 1).concat("**");
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
                            // 提前还款利息拆分 add by wgx 2019/03/22
                            userRepayDetail.setChargeOriginalInterest(creditRepay.getRepayAdvancePenaltyInterest().subtract(creditRepay.getRepayAdvanceInterest()).toString());
                            userRepayDetail.setChargePenaltyInterest(creditRepay.getRepayAdvancePenaltyInterest().toString());
                            if ("0".equals(userRepayDetail.getChargeOriginalInterest())) {
                                userRepayDetail.setChargeOriginalInterest("0.00");
                            }
                            if ("0".equals(userRepayDetail.getChargePenaltyInterest())) {
                                userRepayDetail.setChargePenaltyInterest("0.00");
                            }
                            userRepayDetail.setManageFee(creditRepay.getManageFee().toString());
                            userRepayDetail.setLateDays(userRecoverPlan.getLateDays().toString());
                            userRepayDetail.setLateInterest(creditRepay.getRepayLateInterest().toString());
                            userRepayDetail.setAdvanceStatus(userRecoverPlan.getAdvanceStatus().toString());
                            userRepayDetail.setRepayTime(GetDate.getDateMyTimeInMillis(userRecoverPlan.getRecoverTime()));
                            BigDecimal total = BigDecimal.ZERO;
                            if (creditRepay.getRepayStatus() == 1) {
                                total = creditRepay.getReceiveAccountYes().add(creditRepay.getManageFee());
                            } else {
                                total = creditRepay.getAssignTotal();
                            }
                            userRepayDetail.setRepayTotal(total.toString());
                            userRepayDetail.setStatus(creditRepay.getRepayStatus().toString());
                            userRepayDetail.setUserId(creditRepay.getUserId().toString());
                            // userName直接取表中数据 update by wgx 2019/02/15
                            String userNameStr = creditRepay.getUserName().substring(0, 1).concat("**");
                            userRepayDetail.setUserName(userNameStr);
                            userRepayDetails.add(userRepayDetail);
                        }
                    }
//		            BorrowRecover borrowRecover = getBorrowRecoverByPlanInfo(userRecoverPlan);
                    boolean overFlag = isOverUndertake(userRecoverPlan, recoverAccount, sumAccount, true, hjhFlag);
                    Integer recoverStatus = userRecoverPlan.getRecoverStatus();
                    if (hjhFlag == 0) {
                        if (userRecoverPlan.getCreditStatus() == 2) {
                            overFlag = false;
                        }
                    }
                    if (overFlag) {
                        ProjectRepayDetailBean userRepayDetail = new ProjectRepayDetailBean();
                        userRepayDetail.setRepayAccount(userRecoverPlan.getRecoverAccount().toString());
                        userRepayDetail.setRepayCapital(userRecoverPlan.getRecoverCapital().toString());
                        userRepayDetail.setRepayInterest(userRecoverPlan.getRecoverInterest().toString());
                        userRepayDetail.setChargeDays(userRecoverPlan.getChargeDays().toString());
                        // 提前还款利息拆分 add by wgx 2019/03/22
                        if (recoverStatus == 1) {//已还款
                            //userRepayDetail.setChargeInterest(userRecoverPlan.getChargeInterestOld().multiply(new BigDecimal("-1")).toString());
                            userRepayDetail.setChargeOriginalInterest(userRecoverPlan.getChargePenaltyInterestOld().subtract(userRecoverPlan.getChargeInterestOld()).toString());
                            userRepayDetail.setChargePenaltyInterest(userRecoverPlan.getChargePenaltyInterestOld().toString());
                        } else {
                            //userRepayDetail.setChargeInterest(userRecoverPlan.getChargeInterest().multiply(new BigDecimal("-1")).toString());
                            userRepayDetail.setChargeOriginalInterest(userRecoverPlan.getChargePenaltyInterest().subtract(userRecoverPlan.getChargeInterest()).toString());
                            userRepayDetail.setChargePenaltyInterest(userRecoverPlan.getChargePenaltyInterest().toString());
                        }
                        if ("0".equals(userRepayDetail.getChargeOriginalInterest())) {
                            userRepayDetail.setChargeOriginalInterest("0.00");
                        }
                        if ("0".equals(userRepayDetail.getChargePenaltyInterest())) {
                            userRepayDetail.setChargePenaltyInterest("0.00");
                        }
                        userRepayDetail.setManageFee(userRecoverPlan.getRecoverFee().toString());
                        userRepayDetail.setLateDays(userRecoverPlan.getLateDays().toString());
                        if (recoverStatus == 1) {
                            userRepayDetail.setLateInterest(userRecoverPlan.getLateInterestOld().toString());
                        } else {
                            userRepayDetail.setLateInterest(userRecoverPlan.getLateInterest().toString());
                        }
                        userRepayDetail.setAdvanceStatus(userRecoverPlan.getAdvanceStatus().toString());
                        userRepayDetail.setRepayTime(GetDate.getDateMyTimeInMillis(userRecoverPlan.getRecoverTime()));
                        BigDecimal total = BigDecimal.ZERO;
                        if (recoverStatus == 1) {
                            total = userRecoverPlan.getRecoverAccountYesOld().subtract(sumAccount).add(userRecoverPlan.getRecoverFee());
                        } else {
                            // recover中account未更新
                            total = userRecoverPlan.getRecoverTotal();
                        }
                        userRepayDetail.setRepayTotal(total.toString());
                        userRepayDetail.setStatus(recoverStatus.toString());
                        userRepayDetail.setUserId(userRecoverPlan.getUserId().toString());
                        // userName直接取表中数据 update by wgx 2019/02/15
                        String userNameStr = userRecoverPlan.getUserName().substring(0, 1).concat("**");
                        userRepayDetail.setUserName(userNameStr);
                        userRepayDetails.add(userRepayDetail);
                    }
                }
                userRepayBean.setUserRepayDetailList(userRepayDetails);
                recoverList.add(userRepayBean);
            }
            form.setUserRepayList(recoverList);

            form.setManageFee(repayByTerm.getRepayFee().toString());
            form.setRepayTotal(repayByTerm.getRepayAccountAll().toString());// 此处计算的是还款总额包含管理费
            form.setRepayAccount(repayByTerm.getRepayAccount().add(repayByTerm.getChargeInterest()).add(repayByTerm.getLateInterest())
                    .toString());
            form.setRepayCapital(repayByTerm.getRepayCapital().toString());
            form.setRepayInterest(repayByTerm.getRepayInterest().add(repayByTerm.getChargeInterest()).add(repayByTerm.getLateInterest()).toString());
            form.setShouldInterest(repayByTerm.getRepayInterest().toString());
            form.setLateStatus(repayByTerm.getLateStatus());
            form.setAdvanceStatus(repayByTerm.getAdvanceStatus().toString());
            form.setChargeDays(repayByTerm.getChargeDays().toString());
            form.setChargeOriginalInterest(repayByTerm.getChargePenaltyInterest().subtract(repayByTerm.getChargeInterest()).toString());
            form.setChargePenaltyInterest(repayByTerm.getChargePenaltyInterest().toString());
            form.setLateDays(repayByTerm.getLateDays().toString());
            form.setLateInterest(repayByTerm.getLateInterest().toString());
        }
        if("2".equals(form.getOnlyAllRepay())) {
            form.setLatePeriod(nowPeriod);// 全部逾期时设为最后一期还款
            form.setAllRepay("0");// 不属于一次性还款
        }else if(nowPeriod == periodTotal){
            form.setLatePeriod(0);// 只有最后一期未逾期设为当期还款
            form.setAllRepay("0");// 不属于一次性还款
        }else{
            form.setLatePeriod(-1);
            form.setAllRepay("1");// 属于一次性还款
        }
        form.setRepayPeriod(String.valueOf(nowPeriod));// 设置当期的还款期数
        form.setLateArray(lateArray);// 设置未还款逾期列表
    }

    /***
     * 计算用户分期还款本期应还金额
     */
    private BigDecimal calculateRepayPlan(RepayBean repay, Borrow borrow, int period) throws Exception {

        List<RepayDetailBean> borrowRepayPlanDeails = new ArrayList<RepayDetailBean>();
        List<BorrowRepayPlan> borrowRepayPlans = searchRepayPlan(repay.getUserId(), borrow.getBorrowNid());
        BigDecimal repayAccountAll = new BigDecimal("0");
        repay.setRepayAccountAll(BigDecimal.ZERO);
        repay.setRepayAccount(BigDecimal.ZERO);
        repay.setRepayCapital(BigDecimal.ZERO);
        repay.setRepayInterest(BigDecimal.ZERO);
        repay.setRepayFee(BigDecimal.ZERO);
        repay.setChargeInterest(BigDecimal.ZERO);
        repay.setChargePenaltyInterest(BigDecimal.ZERO);
        repay.setLateInterest(BigDecimal.ZERO);
        int nowPeriod = period;
        if (borrowRepayPlans != null && borrowRepayPlans.size() > 0) {
            // 将calculateRecoverPlan的borrowRecoverList移到方法外查询 update by wgx 2019/02/15
            List<BorrowRecover> borrowRecoverList = this.getBorrowRecover(borrow.getBorrowNid());
            // 用户实际还款额
            for (int i = 0; i < borrowRepayPlans.size(); i++) {
                RepayDetailBean repayPlanDetail = new RepayDetailBean();
                BorrowRepayPlan borrowRepayPlan = borrowRepayPlans.get(i);
                int repayPeriod = borrowRepayPlan.getRepayPeriod();
                int repayTimeInt = borrowRepayPlan.getRepayTime();
                int repayStatus = borrowRepayPlan.getRepayStatus();
                int nowDate = GetDate.getIntYYMMDD(new Date());
                if(GetDate.getIntYYMMDD(repayTimeInt) < nowDate && repayPeriod >= nowPeriod && repayStatus != 1){// 已逾期期数
                    BeanUtils.copyProperties(borrowRepayPlan, repayPlanDetail);
                    this.calculateRecoverPlan(repayPlanDetail, borrow, nowPeriod, null, borrowRecoverList);
                    borrowRepayPlanDeails.add(repayPlanDetail);
                    //  累加以下值
                    repay.setRepayAccountAll(repay.getRepayAccountAll().add(repayPlanDetail.getRepayAccountAll()));
                    repay.setRepayAccount(repay.getRepayAccount().add(repayPlanDetail.getRepayAccount()));
                    repay.setRepayCapital(repay.getRepayCapital().add(repayPlanDetail.getRepayCapital()));
                    repay.setRepayInterest(repay.getRepayInterest().add(repayPlanDetail.getRepayInterest()));
                    repay.setRepayFee(repay.getRepayFee().add(repayPlanDetail.getRepayFee()));
                    repay.setLateDays(repayPlanDetail.getLateDays());
                    // 逾期期数只累加逾期利息
                    repay.setLateInterest(repay.getLateInterest().add(repayPlanDetail.getLateInterest()));
                    repayAccountAll = repayPlanDetail.getRepayAccountAll();
                    if (nowPeriod < borrow.getBorrowPeriod()) {
                        nowPeriod++;// 逾期了 当前期+1
                    }
                    repay.setLateStatus("1");
                } else if (repayPeriod == nowPeriod) {
                    Integer repayTimeStart = null;
                    if (i == 0) {
                        repayTimeStart = GetDate.getTime10(borrowRepayPlan.getCreateTime());
                    } else {
                        repayTimeStart = borrowRepayPlans.get(i - 1).getRepayTime();
                    }

                    // 当期是下一期的话，不能超前还的检查
                    Integer repayTimeEnd = borrowRepayPlan.getRepayTime();
                    // 用户计划还款时间
                    Date repayEndDate = GetDate.getDate(repayTimeEnd);
                    Date repayStartDate = DateUtils.addMonths(repayEndDate, -1);

                    int curPlanStart = GetDate.getIntYYMMDD(repayStartDate);
                    if (nowDate < curPlanStart) {
                        logger.info("repayTimeEnd:" + repayTimeEnd + " repayEndDate:" + repayEndDate + " repayStartDate:" + repayStartDate);
                        logger.info("nowDate:" + nowDate + " curPlanStart:" + curPlanStart);
                        throw new Exception("不能超前还，只能全部结清");
                    }
                    // 计算还款期的数据
                    BeanUtils.copyProperties(borrowRepayPlan, repayPlanDetail);
                    this.calculateRecoverPlan(repayPlanDetail, borrow, period, repayTimeStart, borrowRecoverList);
                    borrowRepayPlanDeails.add(repayPlanDetail);
                    repay.setRepayAccountAll(repay.getRepayAccountAll().add(repayPlanDetail.getRepayAccountAll()));
                    repay.setRepayAccount(repay.getRepayAccount().add(repayPlanDetail.getRepayAccount()));
                    repay.setRepayCapital(repay.getRepayCapital().add(repayPlanDetail.getRepayCapital()));
                    repay.setRepayInterest(repay.getRepayInterest().add(repayPlanDetail.getRepayInterest()));
                    repay.setRepayFee(repay.getRepayFee().add(repayPlanDetail.getRepayFee()));
                    repay.setChargeDays(repayPlanDetail.getChargeDays());
                    repay.setChargeInterest(repayPlanDetail.getChargeInterest());
                    repay.setChargePenaltyInterest(repayPlanDetail.getChargePenaltyInterest());
                    repayAccountAll = repayPlanDetail.getRepayAccountAll();
                } else {
                    BeanUtils.copyProperties(borrowRepayPlan, repayPlanDetail);
                    this.calculateRecoverPlan(repayPlanDetail, borrow);
                    borrowRepayPlanDeails.add(repayPlanDetail);
                }
            }
            repay.setRepayPlanList(borrowRepayPlanDeails);
        }
        if(nowPeriod > period) {
            repay.setLastPeriod(nowPeriod);
        }
        return repayAccountAll;
    }

    /**
     * 分期还款 计算各期数据
     *
     * @param form
     * @param isAllRepay
     * @param userId
     * @param borrow
     * @param repayByTerm
     * @throws ParseException
     */
    private void setRecoverPlanDetail(ProjectBean form, boolean isAllRepay, String userId, Borrow borrow, RepayBean repayByTerm) throws ParseException {

        String borrowNid = borrow.getBorrowNid();
        // 还款总期数
        int periodTotal = borrow.getBorrowPeriod();

        // 计算当前还款期数
        int repayPeriod = periodTotal - repayByTerm.getRepayPeriod() + 1;
        // 如果用户不是还款最后一期
        if (repayPeriod <= periodTotal) {
//		    BorrowApicronExample exampleBorrowApicron = new BorrowApicronExample();
//		    BorrowApicronExample.Criteria crtBorrowApicron = exampleBorrowApicron.createCriteria();
//		    crtBorrowApicron.andBorrowNidEqualTo(borrowNid);
//		    crtBorrowApicron.andPeriodNowEqualTo(repayPeriod);
//		    crtBorrowApicron.andApiTypeEqualTo(1);
//		    List<BorrowApicron> borrowApicrons = borrowApicronMapper.selectByExample(exampleBorrowApicron);
//		    // 正在还款当前期
//		    if (borrowApicrons != null && borrowApicrons.size() > 0) {
//		        BorrowApicron borrowApicron = borrowApicrons.get(0);
//		        if (borrowApicron.getStatus() != 6) {
//		            // 用户还款当前期
//		            form.setRepayStatus("1");
//		        } else {// 用户当前期正在还款
//		            form.setRepayStatus("0");
//		        }
//		    } else {// 用户未还款当前期
//		        form.setRepayStatus("0");
//		    }
        } else {// 用户正在还款最后一期
            form.setRepayStatus("1");
        }
        int nowPeriod = repayPeriod;
        List<Integer> lateArray = new ArrayList<>();// 逾期期数列表
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
                } else {// 用户未还款本息
                    if(userRepayPlan.getAdvanceStatus() == 3 || userRepayPlan.getAdvanceStatus() == 30){// 逾期未还款/还款中
                        lateArray.add(userRepayPlan.getRepayPeriod());
                        form.setLateStatus("1");
                        if (nowPeriod < periodTotal) {
                            nowPeriod++;
                        } else {
                            form.setOnlyAllRepay("2");// 全部逾期
                        }
                    }
                    // 此处分期计算的是本息+管理费
                    userRepayBean.setRepayTotal(userRepayPlan.getRepayAccountAll().toString());
                }
                userRepayBean.setRepayAccount(userRepayPlan.getRepayAccount().add(userRepayPlan.getChargeInterest()).add(userRepayPlan.getLateInterest())
                        .toString());// 设置本期的用户本息和
                userRepayBean.setRepayCapital(userRepayPlan.getRepayCapital().toString());// 用户未还款本息
                userRepayBean.setRepayInterest(userRepayPlan.getRepayInterest().toString()); // 设置本期的用户利息
                userRepayBean.setUserId(userRepayPlan.getUserId().toString());
                userRepayBean.setRepayPeriod(userRepayPlan.getRepayPeriod().toString());
                userRepayBean.setAdvanceStatus(userRepayPlan.getAdvanceStatus().toString());
                userRepayBean.setStatus(userRepayPlan.getRepayStatus().toString());
                userRepayBean.setRepayTime(GetDate.getDateMyTimeInMillis(userRepayPlan.getRepayTime()));
                userRepayBean.setChargeDays(userRepayPlan.getChargeDays().toString());
                userRepayBean.setChargeOriginalInterest(userRepayPlan.getChargePenaltyInterest().subtract(userRepayPlan.getChargeInterest()).toString());
                userRepayBean.setChargePenaltyInterest(userRepayPlan.getChargePenaltyInterest().toString());
                userRepayBean.setManageFee(userRepayPlan.getRepayFee().toString());
                userRepayBean.setLateDays(userRepayPlan.getLateDays().toString());
                userRepayBean.setLateInterest(userRepayPlan.getLateInterest().toString());
                if (nowPeriod == repayPeriod && nowPeriod == userRepayPlan.getRepayPeriod()) {//当期还款只有一期
                    form.setManageFee(userRepayPlan.getRepayFee().toString());
                    form.setRepayTotal(userRepayPlan.getRepayAccountAll().toString());// 此处计算的是还款总额包含管理费
                    form.setRepayAccount(userRepayPlan.getRepayAccount().add(userRepayPlan.getChargeInterest()).add(userRepayPlan.getLateInterest())
                            .toString());
                    form.setRepayCapital(userRepayPlan.getRepayCapital().toString());
                    form.setRepayInterest(userRepayPlan.getRepayInterest().add(userRepayPlan.getChargeInterest()).add(userRepayPlan.getLateInterest()).toString());
                    form.setShouldInterest(userRepayPlan.getRepayInterest().toString());
                    form.setAdvanceStatus(userRepayPlan.getAdvanceStatus().toString());
                    form.setChargeDays(userRepayPlan.getChargeDays().toString());
                    form.setChargeOriginalInterest(userRepayPlan.getChargePenaltyInterest().subtract(userRepayPlan.getChargeInterest()).toString());
                    form.setChargePenaltyInterest(userRepayPlan.getChargePenaltyInterest().toString());
                    form.setLateDays(userRepayPlan.getLateDays().toString());
                    form.setLateInterest(userRepayPlan.getLateInterest().toString());
                }
                List<RepayRecoverPlanBean> userRecoversDetails = userRepayPlan.getRecoverPlanList();
                List<ProjectRepayDetailBean> userRepayDetails = new ArrayList<ProjectRepayDetailBean>();
                for (int j = 0; j < userRecoversDetails.size(); j++) {
                    RepayRecoverPlanBean userRecoverPlan = userRecoversDetails.get(j);
                    BigDecimal recoverAccount = userRecoverPlan.getRecoverAccountOld();
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
                            //userRepayDetail.setChargeInterest(creditRepay.getChargeInterest().multiply(new BigDecimal("-1")).toString());
                            // 提前还款利息拆分 add by wgx 2019/03/22
                            userRepayDetail.setChargeOriginalInterest(creditRepay.getChargePenaltyInterest().subtract(creditRepay.getChargeInterest()).toString());
                            userRepayDetail.setChargePenaltyInterest(creditRepay.getChargePenaltyInterest().toString());
                            if ("0".equals(userRepayDetail.getChargeOriginalInterest())) {
                                userRepayDetail.setChargeOriginalInterest("0.00");
                            }
                            if ("0".equals(userRepayDetail.getChargePenaltyInterest())) {
                                userRepayDetail.setChargePenaltyInterest("0.00");
                            }
                            userRepayDetail.setManageFee(creditRepay.getManageFee().toString());
                            userRepayDetail.setLateDays(userRecoverPlan.getLateDays().toString());
                            userRepayDetail.setLateInterest(creditRepay.getLateInterest().toString());
                            userRepayDetail.setAdvanceStatus(userRecoverPlan.getAdvanceStatus().toString());
                            userRepayDetail.setRepayTime(GetDate.getDateMyTimeInMillis(userRecoverPlan.getRecoverTime()));
                            BigDecimal total = BigDecimal.ZERO;
                            if (creditRepay.getStatus() == 1) {
                                total = creditRepay.getAssignRepayAccount().add(creditRepay.getManageFee());
                            } else {
                                total = creditRepay.getAssignTotal();
                            }
                            userRepayDetail.setRepayTotal(total.toString());
                            userRepayDetail.setStatus(creditRepay.getStatus().toString());
                            userRepayDetail.setUserId(creditRepay.getUserId().toString());
                            // userName直接取表中数据 update by wgx 2019/02/15
                            String userNameStr = creditRepay.getUserName().substring(0, 1).concat("**");
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
                            // 提前还款利息拆分 add by wgx 2019/03/22
                            userRepayDetail.setChargeOriginalInterest(creditRepay.getRepayAdvancePenaltyInterest().subtract(creditRepay.getRepayAdvanceInterest()).toString());
                            userRepayDetail.setChargePenaltyInterest(creditRepay.getRepayAdvancePenaltyInterest().toString());
                            if ("0".equals(userRepayDetail.getChargeOriginalInterest())) {
                                userRepayDetail.setChargeOriginalInterest("0.00");
                            }
                            if ("0".equals(userRepayDetail.getChargePenaltyInterest())) {
                                userRepayDetail.setChargePenaltyInterest("0.00");
                            }
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
                            // userName直接取表中数据 update by wgx 2019/02/15
                            String userNameStr = creditRepay.getUserName().substring(0, 1).concat("**");
                            userRepayDetail.setUserName(userNameStr);
                            userRepayDetails.add(userRepayDetail);
                        }
                    }
                    boolean overFlag = isOverUndertake(userRecoverPlan, recoverAccount, sumAccount, true, hjhFlag);
                    Integer recoverStatus = userRecoverPlan.getRecoverStatus();
                    if (hjhFlag == 0) {
                        if (userRecoverPlan.getCreditStatus() == 2) {
                            overFlag = false;
                        }
                    }
                    if (overFlag) {
                        ProjectRepayDetailBean userRepayDetail = new ProjectRepayDetailBean();
                        userRepayDetail.setRepayAccount(userRecoverPlan.getRecoverAccount().toString());
                        userRepayDetail.setRepayCapital(userRecoverPlan.getRecoverCapital().toString());
                        userRepayDetail.setRepayInterest(userRecoverPlan.getRecoverInterest().toString());
                        userRepayDetail.setChargeDays(userRecoverPlan.getChargeDays().toString());
                        if (recoverStatus == 1) {//已还款
                            //userRepayDetail.setChargeInterest(userRecoverPlan.getChargeInterestOld().multiply(new BigDecimal("-1")).toString());
                            userRepayDetail.setChargeOriginalInterest(userRecoverPlan.getChargePenaltyInterestOld().subtract(userRecoverPlan.getChargeInterestOld()).toString());
                            userRepayDetail.setChargePenaltyInterest(userRecoverPlan.getChargePenaltyInterestOld().toString());
                        } else {
                            //userRepayDetail.setChargeInterest(userRecoverPlan.getChargeInterest().multiply(new BigDecimal("-1")).toString());
                            userRepayDetail.setChargeOriginalInterest(userRecoverPlan.getChargePenaltyInterest().subtract(userRecoverPlan.getChargeInterest()).toString());
                            userRepayDetail.setChargePenaltyInterest(userRecoverPlan.getChargePenaltyInterest().toString());
                        }
                        if ("0".equals(userRepayDetail.getChargeOriginalInterest())) {
                            userRepayDetail.setChargeOriginalInterest("0.00");
                        }
                        if ("0".equals(userRepayDetail.getChargePenaltyInterest())) {
                            userRepayDetail.setChargePenaltyInterest("0.00");
                        }
                        userRepayDetail.setManageFee(userRecoverPlan.getRecoverFee().toString());
                        userRepayDetail.setLateDays(userRecoverPlan.getLateDays().toString());
                        if (recoverStatus == 1) {
                            userRepayDetail.setLateInterest(userRecoverPlan.getLateInterestOld().toString());
                        } else {
                            userRepayDetail.setLateInterest(userRecoverPlan.getLateInterest().toString());
                        }
                        userRepayDetail.setAdvanceStatus(userRecoverPlan.getAdvanceStatus().toString());
                        userRepayDetail.setRepayTime(GetDate.getDateMyTimeInMillis(userRecoverPlan.getRecoverTime()));
                        BigDecimal total = new BigDecimal("0");
                        if (recoverStatus == 1) {
                            total = userRecoverPlan.getRecoverAccountYesOld().subtract(sumAccount).add(userRecoverPlan.getRecoverFee());
                        } else {
                            // recover中account未更新
                            total = userRecoverPlan.getRecoverTotal();
                        }
                        userRepayDetail.setRepayTotal(total.toString());
                        userRepayDetail.setStatus(recoverStatus.toString());
                        userRepayDetail.setUserId(userRecoverPlan.getUserId().toString());
                        // userName直接取表中数据 update by wgx 2019/02/15
                        String userNameStr = userRecoverPlan.getUserName().substring(0, 1).concat("**");
                        userRepayDetail.setUserName(userNameStr);
                        userRepayDetails.add(userRepayDetail);
                    }
                }
                userRepayBean.setUserRepayDetailList(userRepayDetails);
                recoverList.add(userRepayBean);
            }
            form.setUserRepayList(recoverList);
            if (nowPeriod != repayPeriod) {// 当期还款存在逾期期数
                form.setManageFee(repayByTerm.getRepayFee().toString());
                form.setRepayTotal(repayByTerm.getRepayAccountAll().toString());// 此处计算的是还款总额包含管理费
                form.setRepayAccount(repayByTerm.getRepayAccount().add(repayByTerm.getChargeInterest()).add(repayByTerm.getLateInterest())
                        .toString());
                form.setRepayCapital(repayByTerm.getRepayCapital().toString());
                form.setRepayInterest(repayByTerm.getRepayInterest().add(repayByTerm.getChargeInterest()).add(repayByTerm.getLateInterest()).toString());
                form.setShouldInterest(repayByTerm.getRepayInterest().toString());
                form.setLateStatus("1");
                form.setAdvanceStatus(repayByTerm.getAdvanceStatus().toString());
                form.setChargeDays(repayByTerm.getChargeDays().toString());
                form.setChargeOriginalInterest(repayByTerm.getChargePenaltyInterest().subtract(repayByTerm.getChargeInterest()).toString());
                form.setChargePenaltyInterest(repayByTerm.getChargePenaltyInterest().toString());
                form.setLateDays(repayByTerm.getLateDays().toString());
                form.setLateInterest(repayByTerm.getLateInterest().toString());
            }
        }
        if("2".equals(form.getOnlyAllRepay())) {
            form.setLatePeriod(nowPeriod);// 全部逾期时设为最后一期还款
        }else{
            form.setLatePeriod(0);
        }
        form.setRepayPeriod(String.valueOf(nowPeriod));// 设置当期的还款期数
        form.setLateArray(lateArray);// 设置未还款逾期列表
    }

    public BorrowRepay searchRepay(int userId, String borrowNid) {
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

    public List<BorrowRepayPlan> searchRepayPlan(int userId, String borrowNid) {
        BorrowRepayPlanExample borrowRepayPlanExample = new BorrowRepayPlanExample();
        BorrowRepayPlanExample.Criteria borrowRepayPlanCrt = borrowRepayPlanExample.createCriteria();
        borrowRepayPlanCrt.andUserIdEqualTo(userId);
        borrowRepayPlanCrt.andBorrowNidEqualTo(borrowNid);
        List<BorrowRepayPlan> borrowRepayPlans = borrowRepayPlanMapper.selectByExample(borrowRepayPlanExample);
        return borrowRepayPlans;
    }

    public List<BorrowRepayPlan> searchRepayPlanAll(int userId, String borrowNid) {
        BorrowRepayPlanExample borrowRepayPlanExample = new BorrowRepayPlanExample();
        BorrowRepayPlanExample.Criteria borrowRepayPlanCrt = borrowRepayPlanExample.createCriteria();
        borrowRepayPlanCrt.andUserIdEqualTo(userId);
        borrowRepayPlanCrt.andBorrowNidEqualTo(borrowNid);
        borrowRepayPlanExample.setOrderByClause("repay_period");
        List<BorrowRepayPlan> borrowRepayPlans = borrowRepayPlanMapper.selectByExample(borrowRepayPlanExample);
        return borrowRepayPlans;
    }


    public BorrowRepayPlan searchRepayPlan(int userId, String borrowNid, int period) {
        BorrowRepayPlanExample borrowRepayPlanExample = new BorrowRepayPlanExample();
        BorrowRepayPlanExample.Criteria borrowRepayPlanCrt = borrowRepayPlanExample.createCriteria();
//        borrowRepayPlanCrt.andUserIdEqualTo(userId);
        borrowRepayPlanCrt.andBorrowNidEqualTo(borrowNid);
        borrowRepayPlanCrt.andRepayPeriodEqualTo(period);
        List<BorrowRepayPlan> borrowRepayPlans = borrowRepayPlanMapper.selectByExample(borrowRepayPlanExample);
        if (borrowRepayPlans != null && borrowRepayPlans.size() == 1) {
            return borrowRepayPlans.get(0);
        } else {
            return null;
        }
    }



    /**
     * 用户还款
     *
     * @throws Exception
     */
    @Override
    public boolean updateRepayMoney(RepayBean repay, BankCallBean bean, boolean isAllRepay, int latePeriod) throws Exception {

        String borrowNid = repay.getBorrowNid();
        String periodTotal = repay.getBorrowPeriod();
        int remainRepayPeriod = repay.getRepayPeriod();
        int period = Integer.parseInt(periodTotal) - remainRepayPeriod + 1;
        int userId = repay.getUserId();// 借款人id
        Integer repayUserId = repay.getRepayUserId();
        logger.info("borrowNid:" + borrowNid + " repayUserId:" + repayUserId);
        RUser repayUser = this.getRUser(repayUserId);
        logger.info("repayUser:" + repayUser);
        String userName = repayUser.getUsername();
        Integer roleId = repayUser.getRoleId();
        BigDecimal repayTotal = repay.getRepayAccountAll();// 用户还款总额
        String nid = "";
        Boolean repayFlag = false;
        int errorCount = 0;
        Borrow borrow = this.getBorrowByNid(borrowNid);
        BorrowInfo borrowInfo = getBorrowInfoByNid(borrowNid);
        /** 标的基本数据 */
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();// 借款期数
        String borrowStyle = borrow.getBorrowStyle();// 项目还款方式
        Integer projectType = borrow.getProjectType(); // 项目类型
        boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
        if (!isMonth) {
            borrowPeriod = 1;
        }
        // 不分期还款
        List<RepayRecoverBean> recoverList = repay.getRecoverList();
        if (recoverList != null && recoverList.size() > 0) {
            // 获取用户本次应还的金额
            BorrowRepay borrowRepay = this.searchRepay(userId, borrowNid);
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
                    Integer tenderUserId = borrowRecoverOld.getUserId(); // 出借人信息
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
                                oldCreditRepay.setRepayAdvancePenaltyInterest(hjhDebtCreditRepayBean.getRepayAdvancePenaltyInterest());
                                oldCreditRepay.setManageFee(hjhDebtCreditRepayBean.getManageFee());
                                if(hjhDebtCreditRepayBean.getAdvanceStatus() == 3){
                                    oldCreditRepay.setAdvanceStatus(30);// 不分期逾期标的，置成逾期还款中30
                                } else {// 提前还款更新状态
                                    oldCreditRepay.setAdvanceStatus(hjhDebtCreditRepayBean.getAdvanceStatus());
                                }
                                int hjhCreditRepayFlag = this.hjhDebtCreditRepayMapper.updateByPrimaryKey(oldCreditRepay);
                                if (hjhCreditRepayFlag > 0) {
                                    manageFee = manageFee.add(hjhDebtCreditRepayBean.getManageFee());
                                    borrowRecoverOld.setChargeDays(hjhDebtCreditRepayBean.getAdvanceDays());
                                    borrowRecoverOld.setChargeInterest(borrowRecoverOld.getChargeInterest().add(hjhDebtCreditRepayBean.getRepayAdvanceInterest()));
                                    borrowRecoverOld.setChargePenaltyInterest(borrowRecoverOld.getChargePenaltyInterest().add(hjhDebtCreditRepayBean.getRepayAdvancePenaltyInterest()));
                                    boolean recoverFlag = this.borrowRecoverMapper.updateByPrimaryKeySelective(borrowRecoverOld) > 0 ? true : false;
                                    if (!recoverFlag) {
                                        errorCount = errorCount + 1;
                                    }
                                    borrowRecoverFlag = borrowRecoverFlag && recoverFlag;
                                } else {
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
                                creditRepayOld.setChargePenaltyInterest(creditRepay.getChargePenaltyInterest());
                                creditRepayOld.setManageFee(creditRepay.getManageFee());
                                if(creditRepay.getAdvanceStatus() == 3){
                                    creditRepayOld.setAdvanceStatus(30);// 不分期逾期标的，置成逾期还款中30
                                } else {// 提前还款更新状态
                                    creditRepayOld.setAdvanceStatus(creditRepay.getAdvanceStatus());
                                }
                                boolean creditRepayFlag = this.creditRepayMapper.updateByPrimaryKeySelective(creditRepayOld) > 0 ? true : false;
                                if (creditRepayFlag) {
                                    manageFee = manageFee.add(creditRepay.getManageFee());
                                    borrowRecoverOld.setChargeDays(creditRepay.getChargeDays());
                                    borrowRecoverOld.setChargeInterest(borrowRecoverOld.getChargeInterest().add(creditRepay.getChargeInterest()));
                                    borrowRecoverOld.setChargePenaltyInterest(borrowRecoverOld.getChargePenaltyInterest().add(creditRepay.getChargePenaltyInterest()));
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
                        RUser users = this.getRefUser(tenderUserId);
                        if (users != null) {
                            // 获取出借人属性
                            // 用户属性 0=>无主单 1=>有主单 2=>线下员工 3=>线上员工
                            Integer attribute = null;
                            // 获取出借用户的用户属性
                            attribute = users.getAttribute();
                            if (attribute != null) {
                                // 出借人用户属性
                                borrowRecoverOld.setTenderUserAttribute(attribute);
                                // 如果是线上员工或线下员工，推荐人的userId和username不插
                                if (attribute == 2 || attribute == 3) {
                                    EmployeeCustomize employeeCustomize = employeeCustomizeMapper.selectEmployeeByUserId(tenderUserId);
                                    if (employeeCustomize != null) {
                                        borrowRecoverOld.setInviteRegionId(employeeCustomize.getRegionId());
                                        borrowRecoverOld.setInviteRegionName(employeeCustomize.getRegionName());
                                        borrowRecoverOld.setInviteBranchId(employeeCustomize.getBranchId());
                                        borrowRecoverOld.setInviteBranchName(employeeCustomize.getBranchName());
                                        borrowRecoverOld.setInviteDepartmentId(employeeCustomize.getDepartmentId());
                                        borrowRecoverOld.setInviteDepartmentName(employeeCustomize.getDepartmentName());
                                    }
                                } else if (attribute == 1) {
                                    Integer refUserId = users.getSpreadsUserId();
                                    // 查找用户推荐人
                                    if(refUserId != null && refUserId != 0){
                                        RUser userss = this.getRUser(refUserId);
                                        if (userss != null) {
                                            borrowRecoverOld.setInviteUserId(userss.getUserId());
                                            borrowRecoverOld.setInviteUserName(userss.getUsername());
                                            borrowRecoverOld.setInviteUserAttribute(userss.getAttribute());
                                        }
                                        // 查找用户推荐人部门
                                        EmployeeCustomize employeeCustomize = employeeCustomizeMapper.selectEmployeeByUserId(refUserId);
                                        if (employeeCustomize != null) {
                                            borrowRecoverOld.setInviteRegionId(employeeCustomize.getRegionId());
                                            borrowRecoverOld.setInviteRegionName(employeeCustomize.getRegionName());
                                            borrowRecoverOld.setInviteBranchId(employeeCustomize.getBranchId());
                                            borrowRecoverOld.setInviteBranchName(employeeCustomize.getBranchName());
                                            borrowRecoverOld.setInviteDepartmentId(employeeCustomize.getDepartmentId());
                                            borrowRecoverOld.setInviteDepartmentName(employeeCustomize.getDepartmentName());
                                        }
                                    }
                                } else if (attribute == 0) {
                                    Integer refUserId = users.getSpreadsUserId();
                                    // 查找推荐人
                                    if(refUserId != null && refUserId != 0){
                                        RUser userss = getRUser(refUserId);
                                        if (userss != null) {
                                            borrowRecoverOld.setInviteUserId(userss.getUserId());
                                            borrowRecoverOld.setInviteUserName(userss.getUsername());
                                            borrowRecoverOld.setInviteUserAttribute(userss.getAttribute());
                                        }
                                    }
                                }
                            }
                        }
                        manageFee = manageFee.add(repayRecover.getRecoverFee());
                        if(repayRecover.getAdvanceStatus() == 3){
                            borrowRecoverOld.setAdvanceStatus(30);// 不分期逾期标的，置成逾期还款中30
                        } else {// 提前还款更新状态
                            borrowRecoverOld.setAdvanceStatus(repayRecover.getAdvanceStatus());
                        }
                        borrowRecoverOld.setChargeDays(repayRecover.getChargeDays());
                        BigDecimal chargeInterest = repayRecover.getChargeInterest() != null ? repayRecover.getChargeInterest() : BigDecimal.ZERO;
                        BigDecimal chargePenaltyInterest = repayRecover.getChargePenaltyInterest() != null ? repayRecover.getChargePenaltyInterest() : BigDecimal.ZERO;
                        borrowRecoverOld.setChargeInterest(borrowRecoverOld.getChargeInterest().add(chargeInterest));
                        borrowRecoverOld.setChargePenaltyInterest(borrowRecoverOld.getChargePenaltyInterest().add(chargePenaltyInterest));
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
                    if(borrowRepay.getAdvanceStatus() == 3){
                        borrowRepay.setAdvanceStatus(30);// 不分期逾期标的，置成逾期还款中30
                    }
                    boolean borrowRepayFlag = borrowRepayMapper.updateByPrimaryKeySelective(borrowRepay) > 0 ? true : false;
                    if (borrowRepayFlag) {
                        int borrowApicronCount = this.borrowApicronMapper.countByExample(example);
                        // 还款任务>0件
                        if (borrowApicronCount > 0) {
                            throw new Exception("还款失败！" + "重复还款，【" + errorCount + "】" + "项目编号：" + borrowNid);
                        }
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
                        borrowApicron.setSubmitter(userName);
                        borrowApicron.setIsLate(borrow.getStatus() == 8 ? 1 : 0);
                        borrowApicron.setLastPeriod(0);
                        boolean increase = Validator.isIncrease(borrow.getIncreaseInterestFlag(), borrowInfo.getBorrowExtraYield());
                        if (increase) {
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
                            sendRepayMessage(borrowApicron);// 用户还款后直接发起还款消息
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
            logger.info("isAllRepay: " + isAllRepay);
            logger.info("repayPlanList size: " + repayPLanList.size() + " json:" + JSON.toJSONString(repayPLanList));
            int nowPeriod = period;
            for (int i = 0; i < repayPLanList.size(); i++) {
                RepayDetailBean repayDetail = repayPLanList.get(i);
                logger.debug("【还款】借款编号：{}，第{}期正在进行还款！", borrowNid, repayDetail.getRepayPeriod());
                if (latePeriod == 0 && repayDetail.getRepayPeriod() == nowPeriod && !isAllRepay) {
                    Map mapResult = updaterepayData(userId, borrowNid, nowPeriod, repayDetail, errorCount, nid, roleId, userName, repay, repayUserId, borrow,
                            borrowPeriod, projectType, repayFlag, isAllRepay, latePeriod);
                    if (mapResult.get("result") != null) {
                        boolean result = (boolean) mapResult.get("result");
                        if (result) {
                            return true;
                        }
                    }
                    if(repayDetail.getAdvanceStatus() == 3){
                        logger.info("【还款】借款编号：{}，第{}期已逾期，正在进行当期还款！", borrowNid, nowPeriod);
                        nowPeriod ++;// 逾期了，当前期+1
                    }
                    if (mapResult.get("repayFlag") != null) {
                        repayFlag = (Boolean) mapResult.get("repayFlag");
                        if (!repayFlag && nowPeriod != period) {// 当期带别期逾期还款出现失败
                            throw new RuntimeException("标的当期还款出现异常，还款标的：" + borrowNid + ",还款期数：" + repayDetail.getRepayPeriod());
                        }
                    }
                } else if (latePeriod > 0 && !isAllRepay && repayDetail.getRepayPeriod() >= period && repayDetail.getRepayPeriod() <= latePeriod && repayDetail.getRepayStatus() == 0) {
                    // 逾期还款选择一期或多期的情况
                    Map mapResult = updaterepayData(userId, borrowNid, repayDetail.getRepayPeriod(), repayDetail, errorCount, nid, roleId, userName, repay, repayUserId, borrow,
                            borrowPeriod, projectType, repayFlag, isAllRepay, latePeriod);
                    logger.info("【还款】借款编号：{}，第{}期正在进行逾期还款！", borrowNid, repayDetail.getRepayPeriod());
                    if (mapResult.get("result") != null) {
                        boolean result = (boolean) mapResult.get("result");
                        if (result) {
                            return true;
                        }
                    }
                    if (mapResult.get("repayFlag") != null) {
                        repayFlag = (Boolean) mapResult.get("repayFlag");
                        if (!repayFlag && latePeriod != period) {// 多期逾期还款出现失败
                            throw new RuntimeException("标的逾期还款出现异常，还款标的：" + borrowNid + ",还款期数：" + repayDetail.getRepayPeriod());
                        }
                    }
                } else if (isAllRepay && repayDetail.getRepayPeriod() >= period && repayDetail.getRepayStatus() == 0) {

                    Map mapResult = updaterepayData(userId, borrowNid, repayDetail.getRepayPeriod(), repayDetail, errorCount, nid, roleId, userName, repay, repayUserId, borrow,
                            borrowPeriod, projectType, repayFlag, isAllRepay, latePeriod);

                    if (mapResult.get("result") != null) {
                        boolean result = (boolean) mapResult.get("result");
                        if (result) {
                            return true;
                        }
                    }
                    if (mapResult.get("repayFlag") != null) {
                        repayFlag = (Boolean) mapResult.get("repayFlag");
                        if (!repayFlag) {
                            throw new RuntimeException("标的全部还款出现异常，还款标的：" + borrowNid + ",还款期数：" + repayDetail.getRepayPeriod());
                        }
                    }
                }
            }
        }
        if (!repayFlag) {
            throw new RuntimeException("还款失败！" + "失败数量【" + errorCount + "】");
        }
        if (countRepayAccountListByNid(nid) > 0) {
            throw new RuntimeException("此笔还款的交易明细已存在,请勿重复还款");
        }
        // 获取用户的账户信息
        Account repayAccount = this.getAccount(repayUserId);
        if (StringUtils.isBlank(repayAccount.getAccountId())) {
            throw new RuntimeException("用户开户信息不存在!");
        }
        BigDecimal borrowBalance = repayAccount.getBankBalance();
        String repayAccountId = repayAccount.getAccountId();
        if (borrowBalance.compareTo(repayTotal) < 0) {
            throw new RuntimeException("用户汇付账户余额不足!");
        }
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
        if (!accountFlag) {
            throw new RuntimeException("还款失败！" + "更新借款人账户余额表Account失败！");
        }
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
        if (!accountListFlag) {
            throw new RuntimeException("还款失败！" + "插入借款人交易明细表AccountList失败！");
        }
        updateOverdueStatus(borrow, latePeriod);
        deleteFreezeTempLogs(bean.getOrderId(), borrowNid);
        return true;
    }

    private void updateOverdueStatus(Borrow borrow, int latePeriod) throws Exception {
        if (borrow.getStatus() != 8) {// 标的当前不是逾期状态
            return;
        }
        if (latePeriod > 0) {// 查看逾期还款是否已经提交全部逾期期数
            BorrowRecoverPlanExample LateRecoverPlanExample = new BorrowRecoverPlanExample();
            LateRecoverPlanExample.createCriteria().andBorrowNidEqualTo(borrow.getBorrowNid())
                    .andRecoverPeriodGreaterThan(latePeriod).andAdvanceStatusEqualTo(3);
            // 原始投资的逾期笔数
            Integer lateCount = this.borrowRecoverPlanMapper.countByExample(LateRecoverPlanExample);
            if (lateCount != null && lateCount > 0) {
                logger.info("【还款】借款编号：{}，仍有逾期未提交！", borrow.getBorrowNid());
                return;
            }
        }
        // 逾期全部提交
        Borrow newBorrow = new Borrow();
        newBorrow.setId(borrow.getId());
        newBorrow.setStatus(4);// 逾期中修改为还款中
        boolean borrowFlag = this.borrowMapper.updateByPrimaryKeySelective(newBorrow) > 0 ? true : false;
        if (!borrowFlag) {
            throw new Exception("全部逾期还款提交成功后，标的表(ht_borrow)更新失败！[借款编号：" + borrow.getBorrowNid() + "]");
        }
    }

    /**
     * 判断该收支明细是否存在
     */
    private int countRepayAccountListByNid(String nid) {
        AccountListExample accountListExample = new AccountListExample();
        accountListExample.createCriteria().andNidEqualTo(nid).andTradeEqualTo("repay_success");
        return this.accountListMapper.countByExample(accountListExample);
    }

    /**
     * 处理还款数据
     * @param userId
     * @param borrowNid
     * @param period
     * @param repayDetail
     * @param errorCount
     * @param nid
     * @param roleId
     * @param userName
     * @param repay
     * @param repayUserId
     * @param borrow
     * @param borrowPeriod
     * @param projectType
     * @param repayFlag
     * @param isAllRepay
     */
    private Map updaterepayData(int userId, String borrowNid, int period, RepayDetailBean repayDetail, int errorCount,
                                String nid, Integer roleId, String userName, RepayBean repay, Integer repayUserId,
                                Borrow borrow, Integer borrowPeriod, Integer projectType, Boolean repayFlag,
                                boolean isAllRepay, int latePeriod) throws Exception {

        Map map = new HashMap();
        BorrowInfo borrowInfo = getBorrowInfoByNid(borrow.getBorrowNid());
        BorrowRepayPlan borrowRepayPlan = this.searchRepayPlan(userId, borrowNid, period);
        BorrowApicronExample example = new BorrowApicronExample();
        BorrowApicronExample.Criteria crt = example.createCriteria();
        crt.andBorrowNidEqualTo(borrowNid);
        crt.andApiTypeEqualTo(1);
        crt.andPeriodNowEqualTo(repayDetail.getRepayPeriod());
        List<BorrowApicron> borrowApicrons = borrowApicronMapper.selectByExample(example);
        if (borrowApicrons == null || (borrowApicrons != null && borrowApicrons.size() == 0)) {
            boolean borrowRecoverPlanFlag = true;
            boolean creditFlag = true;
            List<RepayRecoverPlanBean> repayRecoverPlans = repayDetail.getRecoverPlanList();
            if (repayRecoverPlans != null && repayRecoverPlans.size() > 0) {
                for (int j = 0; j < repayRecoverPlans.size(); j++) {
                    RepayRecoverPlanBean repayRecoverPlan = repayRecoverPlans.get(j);
                    BorrowRecoverPlan borrowRecoverPlanOld = borrowRecoverPlanMapper.selectByPrimaryKey(repayRecoverPlan.getId());
                    Integer tenderUserId = borrowRecoverPlanOld.getUserId();// 出借人信息
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
                                creditRepayOld.setChargePenaltyInterest(creditRepay.getChargePenaltyInterest());
                                creditRepayOld.setManageFee(creditRepay.getManageFee());
                                if(creditRepay.getAdvanceStatus() == 3){
                                    creditRepayOld.setAdvanceStatus(30);// 逾期标的，置成逾期还款中30
                                } else {// 提前还款更新状态
                                    creditRepayOld.setAdvanceStatus(creditRepay.getAdvanceStatus());
                                }
                                boolean creditRepayFlag = this.creditRepayMapper.updateByPrimaryKeySelective(creditRepayOld) > 0 ? true : false;
                                if (creditRepayFlag) {
                                    manageFee = manageFee.add(creditRepay.getManageFee());
                                    borrowRecoverPlanOld.setChargeDays(creditRepay.getChargeDays());
                                    borrowRecoverPlanOld.setChargeInterest(borrowRecoverPlanOld.getChargeInterest().add(creditRepay.getChargeInterest()));
                                    borrowRecoverPlanOld.setChargePenaltyInterest(borrowRecoverPlanOld.getChargePenaltyInterest().add(creditRepay.getChargePenaltyInterest()));
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
                                oldCreditRepay.setRepayAdvancePenaltyInterest(hjhDebtCreditRepayBean.getRepayAdvancePenaltyInterest());
                                oldCreditRepay.setManageFee(hjhDebtCreditRepayBean.getManageFee());
                                if(hjhDebtCreditRepayBean.getAdvanceStatus() == 3){
                                    oldCreditRepay.setAdvanceStatus(30);// 逾期标的，置成逾期还款中30
                                } else {// 提前还款更新状态
                                    oldCreditRepay.setAdvanceStatus(hjhDebtCreditRepayBean.getAdvanceStatus());
                                }
                                int hjhCreditRepayFlag = this.hjhDebtCreditRepayMapper.updateByPrimaryKey(oldCreditRepay);
                                if (hjhCreditRepayFlag > 0) {
                                    manageFee = manageFee.add(hjhDebtCreditRepayBean.getManageFee());
                                    borrowRecoverPlanOld.setChargeDays(hjhDebtCreditRepayBean.getAdvanceDays());
                                    borrowRecoverPlanOld.setChargeInterest(borrowRecoverPlanOld.getChargeInterest().add(hjhDebtCreditRepayBean.getRepayAdvanceInterest()));
                                    borrowRecoverPlanOld.setChargePenaltyInterest(borrowRecoverPlanOld.getChargePenaltyInterest().add(hjhDebtCreditRepayBean.getRepayAdvancePenaltyInterest()));
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
                        RUser users = this.getRUser(tenderUserId);
                        if (users != null) {
                            // 用户属性 0=>无主单 1=>有主单 2=>线下员工 3=>线上员工
                            Integer attribute = users.getAttribute();
                            if (attribute != null) {
                                // 出借人用户属性
                                borrowRecoverPlanOld.setTenderUserAttribute(attribute);
                                // 如果是线上员工或线下员工，推荐人的userId和username不插
                                if (attribute == 2 || attribute == 3) {
                                    EmployeeCustomize employeeCustomize = employeeCustomizeMapper.selectEmployeeByUserId(tenderUserId);
                                    if (employeeCustomize != null) {
                                        borrowRecoverPlanOld.setInviteRegionId(employeeCustomize.getRegionId());
                                        borrowRecoverPlanOld.setInviteRegionName(employeeCustomize.getRegionName());
                                        borrowRecoverPlanOld.setInviteBranchId(employeeCustomize.getBranchId());
                                        borrowRecoverPlanOld.setInviteBranchName(employeeCustomize.getBranchName());
                                        borrowRecoverPlanOld.setInviteDepartmentId(employeeCustomize.getDepartmentId());
                                        borrowRecoverPlanOld.setInviteDepartmentName(employeeCustomize.getDepartmentName());
                                    }
                                } else if (attribute == 1) {
                                    Integer refUserId = users.getSpreadsUserId();
                                    // 查找用户推荐人
                                    if(refUserId != null && refUserId != 0){
                                        RUser userss = this.getRUser(refUserId);
                                        if (userss != null) {
                                            borrowRecoverPlanOld.setInviteUserId(userss.getUserId());
                                            borrowRecoverPlanOld.setInviteUserName(userss.getUsername());
                                            borrowRecoverPlanOld.setInviteUserAttribute(userss.getAttribute());
                                        }
                                        // 查找用户推荐人部门
                                        EmployeeCustomize employeeCustomize = employeeCustomizeMapper.selectEmployeeByUserId(refUserId);
                                        if (employeeCustomize != null) {
                                            borrowRecoverPlanOld.setInviteRegionId(employeeCustomize.getRegionId());
                                            borrowRecoverPlanOld.setInviteRegionName(employeeCustomize.getRegionName());
                                            borrowRecoverPlanOld.setInviteBranchId(employeeCustomize.getBranchId());
                                            borrowRecoverPlanOld.setInviteBranchName(employeeCustomize.getBranchName());
                                            borrowRecoverPlanOld.setInviteDepartmentId(employeeCustomize.getDepartmentId());
                                            borrowRecoverPlanOld.setInviteDepartmentName(employeeCustomize.getDepartmentName());
                                        }
                                    }
                                } else if (attribute == 0) {
                                    Integer refUserId = users.getSpreadsUserId();
                                    // 查找推荐人
                                    if(refUserId !=null && refUserId != 0){
                                        RUser userss = this.getRUser(refUserId);
                                        if (userss != null) {
                                            borrowRecoverPlanOld.setInviteUserId(userss.getUserId());
                                            borrowRecoverPlanOld.setInviteUserName(userss.getUsername());
                                            borrowRecoverPlanOld.setInviteUserAttribute(userss.getAttribute());
                                        }
                                    }
                                }
                            }
                        }
                        manageFee = manageFee.add(repayRecoverPlan.getRecoverFee());
                        if(repayRecoverPlan.getAdvanceStatus() == 3){
                            borrowRecoverPlanOld.setAdvanceStatus(30);// 逾期标的，置成逾期还款中30
                        } else {// 提前还款更新状态
                            borrowRecoverPlanOld.setAdvanceStatus(repayRecoverPlan.getAdvanceStatus());
                        }
                        borrowRecoverPlanOld.setChargeDays(repayRecoverPlan.getChargeDays());
                        BigDecimal chargeInterest = repayRecoverPlan.getChargeInterest() != null ? repayRecoverPlan.getChargeInterest() : BigDecimal.ZERO;
                        BigDecimal chargePenaltyInterest = repayRecoverPlan.getChargePenaltyInterest() != null ? repayRecoverPlan.getChargePenaltyInterest() : BigDecimal.ZERO;
                        borrowRecoverPlanOld.setChargeInterest(borrowRecoverPlanOld.getChargeInterest().add(chargeInterest));
                        borrowRecoverPlanOld.setChargePenaltyInterest(borrowRecoverPlanOld.getChargePenaltyInterest().add(chargePenaltyInterest));
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
                if(borrowRepayPlan.getAdvanceStatus() == 3){
                    borrowRepayPlan.setAdvanceStatus(30);// 逾期标的，置成逾期还款中30
                }
                boolean borrowRepayPlanFlag = borrowRepayPlanMapper.updateByPrimaryKeySelective(borrowRepayPlan) > 0 ? true : false;
                if (borrowRepayPlanFlag) {
                    int borrowApicronCount = this.borrowApicronMapper.countByExample(example);
                    // 还款任务>0件
                    if (borrowApicronCount > 0) {
                        throw new Exception("重复还款");
                    }
                    String nid2 = repay.getBorrowNid() + "_" + repay.getUserId() + "_" + period;
                    BorrowApicron borrowApicron = new BorrowApicron();
                    borrowApicron.setNid(nid2);
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
                    borrowApicron.setSubmitter(userName);
                    borrowApicron.setIsLate(borrowRepayPlan.getAdvanceStatus() == 30 ? 1 : 0);
                    borrowApicron.setLastPeriod(repay.getLastPeriod() == null ? 0 : repay.getLastPeriod());
                    if(isAllRepay){
                        borrowApicron.setIsAllrepay(1);
                    }
                    boolean increase = Validator.isIncrease(borrow.getIncreaseInterestFlag(), borrowInfo.getBorrowExtraYield());
                    if (increase) {
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
                        sendRepayMessage(borrowApicron);// 用户还款后直接发起还款消息
                    } else {
                        throw new RuntimeException("重复还款");
                    }
                } else {
                    throw new RuntimeException("还款失败！" + "失败数量【" + errorCount + "】");
                }
            } else {
                throw new RuntimeException("还款失败！" + "失败数量【" + errorCount + "】");
            }
        } else if (borrowApicrons.size() == 1) {
            map.put("result",true);
            return map;
        } else {
            throw new RuntimeException("还款失败！" + "重复还款，【" + errorCount + "】" + "项目编号：" + borrowNid + ",期数；" + period);
        }
        map.put("repayFlag",repayFlag);
        return map;
    }

    /**
     * 发送发起还款消息
     * 由于是在事务内提交 会发生MQ消费时事务还没提交的情况 所以改成延时队列
     * @author wgx
     * @date 2018/10/17
     */
    public void sendRepayMessage(BorrowApicron apiCron) {
        try {
            commonProducer.messageSendDelay(new MessageContent(MQConstant.BORROW_REPAY_REQUEST_TOPIC,
                    apiCron.getBorrowNid(), apiCron),2);
            logger.info("【还款】发起还款消息:还款项目编号:[" + apiCron.getBorrowNid() + ",还款期数:[第" + apiCron.getPeriodNow() +
                    "],计划编号:[" + (org.apache.commons.lang3.StringUtils.isEmpty(apiCron.getPlanNid()) ? "" : apiCron.getPlanNid()));
        } catch (MQException e) {
            logger.error("【还款】发起还款消息发生异常 ", e);
        }
    }

    /**
     * 根据项目编号查询正在债转的项目
     */
    @Override
    public boolean updateBorrowCreditStautus(String borrowNid) {
        Borrow borrow = this.getBorrowByNid(borrowNid);
        String planNid = borrow.getPlanNid();
        // BigDecimal rollBackAccount = BigDecimal.ZERO;
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
                    // rollBackAccount = rollBackAccount.add(resultAmount);
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
                    //回滚开放额度 add by cwyang 2017-12-25
                    // 回滚开放额度按未承接债权逐条回滚 update by wgx 2019/03/01
                    if (StringUtils.isNotBlank(rollBackPlanNid)) {
                        rollBackAccedeAccount(rollBackPlanNid, resultAmount);
                    }
                    // add 合规数据上报 埋点 liubin 20181122 start
                    //停止债转并且没有被承接过
                    if (hjhDebtCredit.getCreditStatus().compareTo(3) == 0) {
                        if (hjhDebtCredit.getCreditCapitalAssigned().compareTo(BigDecimal.ZERO) == 0) {
                            JSONObject params = new JSONObject();
                            params.put("creditNid", hjhDebtCredit.getCreditNid());
                            params.put("flag", "2");//1（散）2（智投）
                            params.put("status", "3"); //3承接（失败）
                            // 推送数据到MQ 承接（失败）智
                            commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.UNDERTAKE_ALL_FAIL_TAG, UUID.randomUUID().toString(), params),
                                    MQConstant.HG_REPORT_DELAY_LEVEL);
                        } else {
                            // add 合规数据上报 埋点 liubin 20181122 start
                            // 推送数据到MQ 承接（完全）散
                            JSONObject params = new JSONObject();
                            params.put("creditNid", hjhDebtCredit.getCreditNid());
                            params.put("flag", "2");//1（散）2（智投）
                            params.put("status", "2"); //2承接（成功）
                            commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.UNDERTAKE_ALL_SUCCESS_TAG, UUID.randomUUID().toString(), params),
                                    MQConstant.HG_REPORT_DELAY_LEVEL);
                            // add 合规数据上报 埋点 liubin 20181122 end
                        }
                    }
                    // add 合规数据上报 埋点 liubin 20181122 end
                }
            }
        } else {//直投标的
            BorrowCreditExample example = new BorrowCreditExample();
            BorrowCreditExample.Criteria cra = example.createCriteria();
            cra.andBidNidEqualTo(borrowNid);
            cra.andCreditStatusEqualTo(0);
            List<BorrowCredit> borrowCreditList = this.borrowCreditMapper.selectByExample(example);
            if (borrowCreditList != null && borrowCreditList.size() > 0) {
                for (BorrowCredit borrowCredit : borrowCreditList) {
                    // 老系统也是3，可能不对， 经过刘阳和杨昌卫确认，此处改成1(承接停止)，  2018年10月24日10:21:39
                    borrowCredit.setCreditStatus(1);
                    this.borrowCreditMapper.updateByPrimaryKeySelective(borrowCredit);

                    //停止债转并且没有被承接过
                    if (borrowCredit.getCreditCapitalAssigned().compareTo(BigDecimal.ZERO) == 0) {
                        JSONObject params = new JSONObject();
                        params.put("creditNid", borrowCredit.getCreditNid()+"");
                        params.put("flag", "1");//1（散）2（智投）
                        params.put("status", "3"); //3承接（失败）
                        // 推送数据到MQ 承接（失败）散
                        commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.UNDERTAKE_ALL_FAIL_TAG, UUID.randomUUID().toString(), params),
                                MQConstant.HG_REPORT_DELAY_LEVEL);
                    }else{
                        // 推送数据到MQ 承接（完全）散
                        JSONObject params = new JSONObject();
                        params.put("creditNid", borrowCredit.getCreditNid()+"");
                        params.put("flag", "1"); //1（散）2（智投）
                        params.put("status", "2"); //2承接（成功）
                        commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.UNDERTAKE_ALL_SUCCESS_TAG, UUID.randomUUID().toString(), params),
                                MQConstant.HG_REPORT_DELAY_LEVEL);
                    }
                    // add 合规数据上报 埋点 liubin 20181122 end
                }
            }
        }

        return true;
    }

    /**
     * 回滚开放额度
     *
     * @param rollBackAccount
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
        JedisPool poolNew = RedisUtils.getPool();
        Jedis jedis = poolNew.getResource();
        try{
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
                    if (ret != null && ret.equals("OK")) {
                        // 成功后
                        break;
                    } else {
                        jedis.unwatch();
                    }
                }
            }
        }catch(Exception e){
            logger.info("抛出异常:[{}]",e);
        }finally {
            RedisUtils.returnResource(poolNew,jedis);
        }

    }

    @Override
    public RepayBean searchRepayTotalV2(int userId, Borrow borrow) throws Exception {
        RepayBean RepayBean = this.calculateRepay(userId, borrow);
        return RepayBean;
    }

    @Override
    public RepayBean searchRepayPlanTotal(int userId, Borrow borrow) throws Exception {
        RepayBean repayByTerm = new RepayBean();

        BorrowRepay borrowRepay = this.searchRepay(userId, borrow.getBorrowNid());

        if (borrowRepay != null) {
            // 获取相应的还款信息
            BeanUtils.copyProperties(borrowRepay, repayByTerm);
            repayByTerm.setBorrowPeriod(String.valueOf(borrow.getBorrowPeriod()));
            // 计算当前还款期数
            int period = borrow.getBorrowPeriod() - borrowRepay.getRepayPeriod() + 1;

            // 分期 当前期 计算，如果当前期没有还款，则先算当前期，后算所有剩下的期数
            // 如果当前期已经还款，直接算所有剩下期数
            calculateRepayPlanAll(repayByTerm, borrow, period);
        }
        return repayByTerm;
    }

    @Override
    public RepayBean searchRepayByTermTotalV2(int userId, Borrow borrow, BigDecimal borrowApr, String borrowStyle, int periodTotal) throws Exception {

        RepayBean repayByTerm = new RepayBean();
        BorrowRepay borrowRepay = this.searchRepay(userId, borrow.getBorrowNid());

        if (borrowRepay != null) {
            // 获取相应的还款信息
            BeanUtils.copyProperties(borrowRepay, repayByTerm);
            repayByTerm.setBorrowPeriod(String.valueOf(borrow.getBorrowPeriod()));
            // 计算当前还款期数
            int period = periodTotal - borrowRepay.getRepayPeriod() + 1;
            calculateRepayPlan(repayByTerm, borrow, period);
        }
        return repayByTerm;
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
        BorrowApicron newBorrowApicron = new BorrowApicron();
        newBorrowApicron.setStatus(status);
        if(CustomConstants.BANK_BATCH_STATUS_VERIFY_FAIL == status || CustomConstants.BANK_BATCH_STATUS_FAIL == status) {
            newBorrowApicron.setFailTimes(apicron.getFailTimes());
            newBorrowApicron.setData(apicron.getData());
        }
        borrowApicronMapper.updateByExampleSelective(newBorrowApicron, example);

        //更新borrow表状态
        Borrow borrow = this.getBorrowByNid(borrowNid);
        Borrow newBorrow = new Borrow();
        newBorrow.setId(borrow.getId());
        newBorrow.setRepayStatus(status);
        this.borrowMapper.updateByPrimaryKeySelective(newBorrow);
        try {
            updateBorrowApicronLog(apicron, status);
        } catch (Exception e) {
            logger.error("同步还款任务日志表发生异常！", e);
        }
        return true;
    }

    /**
     * 获取垫付机构批量垫付页面还款数据
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public ProjectBean getOrgBatchRepayData(String userId, String startDate, String endDate){
        RepayListRequest requestBean = new RepayListRequest();
        requestBean.setUserId(userId);
        requestBean.setRoleId("3");
        requestBean.setStartDate(startDate);
        requestBean.setEndDate(endDate);
        requestBean.setStatus("0");
        requestBean.setRepayStatus("0");

        List<RepayListCustomizeVO> resultList = this.selectOrgRepayList(requestBean);
        if (resultList == null) {
            resultList = new ArrayList<RepayListCustomizeVO>();
        }

        ProjectBean form = new ProjectBean();
        form.setUserId(userId);
        form.setRoleId("3");
        //垫付机构总的还款信息
        ProjectBean repayProjectInfo = new ProjectBean();
        BigDecimal repayAccount = new BigDecimal(0);
        BigDecimal repayCapital = new BigDecimal(0);
        BigDecimal repayInterest = new BigDecimal(0);
        BigDecimal repayMangee = new BigDecimal(0);
        BigDecimal chargeOriginalInterest = new BigDecimal(0);//原始提前减息
        BigDecimal chargePenaltyInterest = new BigDecimal(0);//提前罚息
        BigDecimal lateInterest = new BigDecimal(0);//逾期利息
        try {
            for (int i = 0; i < resultList.size(); i++) {
                form.setBorrowNid(resultList.get(i).getBorrowNid());
                ProjectBean repayProject =  this.searchRepayProjectDetail(form,false, 0);
                repayAccount = repayAccount.add(new BigDecimal(repayProject.getRepayAccount())).add(new BigDecimal(repayProject.getManageFee()));
                repayCapital = repayCapital.add(new BigDecimal(repayProject.getRepayCapital()));
                repayInterest = repayInterest.add(new BigDecimal(repayProject.getShouldInterest()));
                repayMangee = repayMangee.add(new BigDecimal(repayProject.getManageFee()));
                chargeOriginalInterest = chargeOriginalInterest.add(new BigDecimal(repayProject.getChargeOriginalInterest() == null ? "0" : repayProject.getChargeOriginalInterest()));
                chargePenaltyInterest = chargePenaltyInterest.add(new BigDecimal(repayProject.getChargePenaltyInterest() == null ? "0" : repayProject.getChargePenaltyInterest()));
                lateInterest = lateInterest.add(new BigDecimal(repayProject.getLateInterest() == null ? "0" : repayProject.getLateInterest()));
            }
            repayProjectInfo.setRepayAccount(repayAccount.toString());
            repayProjectInfo.setRepayCapital(repayCapital.toString());
            repayProjectInfo.setRepayInterest(repayInterest.toString());
            repayProjectInfo.setManageFee(repayMangee.toString());
            repayProjectInfo.setChargeOriginalInterest(chargeOriginalInterest.toString());
            repayProjectInfo.setChargePenaltyInterest(chargePenaltyInterest.toString());
            repayProjectInfo.setLateInterest(lateInterest.toString());
            //返回应收笔数
            repayProjectInfo.setRepayNum(String.valueOf(resultList.size()));
            return repayProjectInfo;

        } catch (Exception e) {
            logger.error("【还款详情】查询还款详情页面时发生异常！",e);
        }

        return repayProjectInfo;
    }

    @Override
    public void insertRepayOrgFreezeLog(Integer userId, String orderId, String account, String borrowNid, RepayBean repay, String userName, boolean isAllRepay, int latePeriod) {
        Borrow borrow = getBorrowByNid(borrowNid);
        BorrowInfo borrowInfo = getBorrowInfoByNid(borrowNid);
        BankRepayOrgFreezeLog log = new BankRepayOrgFreezeLog();
        log.setRepayUserId(userId);// 还款人用户userId
        log.setRepayUserName(userName);// 还款人用户名
        log.setBorrowUserId(borrow.getUserId());// 借款人userId
        log.setBorrowUserName(borrow.getBorrowUserName());// 借款人用户名
        log.setAccount(account);// 电子账号
        log.setOrderId(orderId);// 订单号:txDate+txTime+seqNo
        log.setBorrowNid(borrow.getBorrowNid());// 借款编号
        log.setPlanNid(borrow.getPlanNid());// 计划编号
        log.setInstCode(borrowInfo.getInstCode());// 资产来源
        log.setAmount(borrow.getAccount());// 借款金额
        log.setAmountFreeze(repay.getRepayAccountAll());// 冻结金额
        log.setRepayAccount(repay.getRepayAccount());// 应还本息
        log.setRepayFee(repay.getRepayFee());// 还款服务费
        log.setLowerInterest(BigDecimal.ZERO.subtract(repay.getChargeInterest()));// 减息金额
        log.setPenaltyAmount(BigDecimal.ZERO);// 违约金
        log.setDefaultInterest(repay.getLateInterest());// 逾期罚息?
        Integer period = borrow.getBorrowPeriod();
        String borrowStyle = borrow.getBorrowStyle();
        String borrowPeriod = period + (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle) ? "天" : "个月");
        String periodTotal = CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle) || CustomConstants.BORROW_STYLE_END.equals(borrowStyle)
                ? "1" : repay.getBorrowPeriod();
        int remainRepayPeriod = CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle) || CustomConstants.BORROW_STYLE_END.equals(borrowStyle)
                ? 1 : repay.getRepayPeriod();
        int repayPeriod = Integer.parseInt(periodTotal) - remainRepayPeriod + 1;
        log.setBorrowPeriod(borrowPeriod);// 借款期限
        log.setTotalPeriod(Integer.parseInt(periodTotal));// 总期数
        log.setCurrentPeriod(repayPeriod);// 当前期数
        log.setAllRepayFlag(isAllRepay ? 1 : 0);// 是否全部还款 0 否 1 是
        log.setDelFlag(0);// 0 有效 1 无效
        log.setLatePeriod(latePeriod);// 提交的逾期期数
        log.setCreateUserId(userId);
        log.setCreateUserName(userName);
        bankRepayOrgFreezeLogMapper.insertSelective(log);
        logger.info("【批量还款垫付】借款编号：{}，插入垫付机构冻结表日志。", borrowNid);
    }

    @Override
    public boolean checkRepayInfo(Integer userId, String borrowNid) {
        BankRepayFreezeLogExample example = new BankRepayFreezeLogExample();
        BankRepayFreezeLogExample.Criteria criteria = example.createCriteria();
        if(userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        criteria.andBorrowNidEqualTo(borrowNid);
        criteria.andDelFlagEqualTo(0);
        List<BankRepayFreezeLog> list = bankRepayFreezeLogMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return false;
        }
        BankRepayOrgFreezeLogExample orgExample = new BankRepayOrgFreezeLogExample();
        orgExample.createCriteria().andBorrowNidEqualTo(borrowNid).andDelFlagEqualTo(0);
        List<BankRepayOrgFreezeLog> orgList = bankRepayOrgFreezeLogMapper.selectByExample(orgExample);
        if (orgList != null && orgList.size() > 0) {
            return false;
        }
        return true;
    }

    @Override
    public void insertRepayFreezeLog(Integer userId, String orderId, String account, String borrowNid,
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

    @Override
    public void deleteFreezeTempLogs(String orderId, String borrowNid) {
        BankRepayFreezeLogExample example = new BankRepayFreezeLogExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<BankRepayFreezeLog> log = this.bankRepayFreezeLogMapper.selectByExample(example);
        if (log != null && log.size() > 0) {
            for (int i = 0; i < log.size(); i++) {
                BankRepayFreezeLog record = log.get(i);
                BankRepayFreezeLog newRecord = new BankRepayFreezeLog();
                newRecord.setId(record.getId());
                newRecord.setDelFlag(1);// 0 有效 1无效
                int flag = this.bankRepayFreezeLogMapper.updateByPrimaryKeySelective(newRecord);
                logger.info("【还款冻结处理】借款编号：{}，删除冻结临时日志{}。", record.getBorrowNid(), flag > 0 ? "成功" : "失败");
            }
        }
        BankRepayOrgFreezeLogExample orgExample = new BankRepayOrgFreezeLogExample();
        BankRepayOrgFreezeLogExample.Criteria criteria = orgExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        if(StringUtils.isNotBlank(borrowNid)){
            criteria.andBorrowNidEqualTo(borrowNid);// 批量垫付需要根据标的号逐个删除
        }
        List<BankRepayOrgFreezeLog> orgLog = this.bankRepayOrgFreezeLogMapper.selectByExample(orgExample);
        if (orgLog != null && orgLog.size() > 0) {
            for (int i = 0; i < orgLog.size(); i++) {
                BankRepayOrgFreezeLog record = orgLog.get(i);
                BankRepayOrgFreezeLog newRecord = new BankRepayOrgFreezeLog();
                newRecord.setId(record.getId());
                newRecord.setDelFlag(1);// 0 有效 1无效
                int flag = this.bankRepayOrgFreezeLogMapper.updateByPrimaryKeySelective(newRecord);
                logger.info("【代偿冻结处理】借款编号：{}，删除担保机构冻结临时日志{}。", record.getBorrowNid(), flag > 0 ? "成功" : "失败");
            }
        }
    }

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
            return this.getBorrowByNid(borrowInfoList.get(0).getBorrowNid());
        } else {
            return null;
        }
    }

    @Override
    public BigDecimal searchRepayTotal(int userId, Borrow borrow) throws Exception {
        RepayBean RepayBean = this.calculateRepay(userId, borrow);
        return RepayBean.getRepayAccountAll();
    }

    @Override
    public BigDecimal searchRepayByTermTotal(int userId, Borrow borrow, BigDecimal borrowApr, String borrowStyle, int periodTotal) throws Exception {
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

    @Override
    public RepayBean calculateRepayByTerm(int userId, Borrow borrow) throws Exception {

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
     *
     * @param borrowNid
     * @return
     * @Author : huanghui
     */
    @Override
    public WebUserTransferBorrowInfoCustomize getUserTransferBorrowInfo(String borrowNid) {
        return webUserRepayListCustomizeMapper.getBorrowInfo(borrowNid);
    }

    @Override
    public int selectUserRepayTransferDetailListTotal(String borrowNid, String verificationFlag) {
        if (StringUtils.isNotEmpty(verificationFlag)){
            return webUserRepayListCustomizeMapper.selectUserRepayTransferListTotalByHjhCreditTender(borrowNid);
        }else {
            return webUserRepayListCustomizeMapper.selectUserRepayTransferListTotalByCreditTender(borrowNid);
        }
    }

    /**
     * 用户待还详情
     * @param repayTransferRequest
     * @return
     */
    @Override
    public List<WebUserRepayTransferCustomize> selectUserRepayTransferDetailList(WebUserRepayTransferRequest repayTransferRequest) {

        Integer limitStart = repayTransferRequest.getLimitStart();
        Integer limitEnd = repayTransferRequest.getLimitEnd();
        /**
         * verificationFlag 为空时,为计划类的标的 查询 hyjf_hjh_debt_credit_tender 表
         * verificationFlag 不为空时, 为非计划的标的,查询 huiyingdai_credit_tender 表
         */
        List<WebUserRepayTransferCustomize> list = null;
        Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(repayTransferRequest.getVerificationFlag())){
            params.put("borrowNid", repayTransferRequest.getBorrowNid());
            if (limitStart == 0 || limitStart > 0) {
                params.put("limitStart", limitStart);
            }
            if (limitEnd > 0) {
                params.put("limitEnd", limitEnd);
            }
            list = webUserRepayListCustomizeMapper.selectUserRepayTransferListByHjhCreditTender(params);
        }else {
            params.put("borrowNid", repayTransferRequest.getBorrowNid());
            if (limitStart == 0 || limitStart > 0) {
                params.put("limitStart", limitStart);
            }
            if (limitEnd > 0) {
                params.put("limitEnd", limitEnd);
            }
            list = webUserRepayListCustomizeMapper.selectUserRepayTransferListByCreditTender(params);
        }

        return list;
    }

    /**
     *
     * 以hyjf开头:
     *      hyjf123456 的加密第5-8位
     *      hyjf13125253333 的加密第8-11位
     * 其他 :
     *      a**
     *          或
     *      张**
     * @param userName
     * @return
     * @Author : huanghui
     */
    @Override
    public String usernameEncryption(String userName){
        if (StringUtils.isNotBlank(userName)){
            /**
             * 2,不为汉字时,截取前四位判断是否以hyjf开头.
             * 3,以hyjf开头判断字符串长度,确定需要加密的位置.
             */
            String firstString = userName.substring(0, 1);

            String str = "****";
            if (userName.startsWith("hyjf")){
                StringBuilder stringBuilder = new StringBuilder(userName);
                if (userName.length() >= 15){
                    return stringBuilder.replace(7, 11, str).toString();
                }else {
                    return stringBuilder.replace(4, 8, str).toString();
                }
            }else {
                return firstString + "**";
            }
        }
        return null;
    }

    @Override
    public boolean getFailCredit(String borrowNid) {
        Integer failCreditCount = webUserRepayListCustomizeMapper.getFailCredit(borrowNid);
        return failCreditCount != null && failCreditCount > 0;
    }

    @Override
    public RepayBean searchRepayPlanPart(int userId, Borrow borrow, int latePeriod) throws Exception {
        RepayBean repayByTerm = new RepayBean();

        BorrowRepay borrowRepay = this.searchRepay(userId, borrow.getBorrowNid());

        if (borrowRepay != null) {
            // 获取相应的还款信息
            BeanUtils.copyProperties(borrowRepay, repayByTerm);
            repayByTerm.setBorrowPeriod(String.valueOf(borrow.getBorrowPeriod()));
            // 计算当前还款期数
            int period = borrow.getBorrowPeriod() - borrowRepay.getRepayPeriod() + 1;
            this.calculateRepayPlanPart(repayByTerm, borrow, period, latePeriod);
        }
        return repayByTerm;
    }

    /***
     * 计算用户分期还款部分期数应还金额(逾期使用)
     */
    private BigDecimal calculateRepayPlanPart(RepayBean repay, Borrow borrow, int period, int latePeriod) throws Exception {

        List<RepayDetailBean> borrowRepayPlanDeails = new ArrayList<RepayDetailBean>();
        List<BorrowRepayPlan> borrowRepayPlans = searchRepayPlan(repay.getUserId(), borrow.getBorrowNid());
        BigDecimal repayAccountAll = new BigDecimal("0");
        repay.setRepayAccountAll(BigDecimal.ZERO);
        repay.setRepayAccount(BigDecimal.ZERO);
        repay.setRepayCapital(BigDecimal.ZERO);
        repay.setRepayInterest(BigDecimal.ZERO);
        repay.setRepayFee(BigDecimal.ZERO);
        repay.setAdvanceStatus(3);
        repay.setChargeInterest(BigDecimal.ZERO);
        repay.setChargePenaltyInterest(BigDecimal.ZERO);
        repay.setLateInterest(BigDecimal.ZERO);
        if (borrowRepayPlans != null && borrowRepayPlans.size() > 0) {
            // 将calculateRecoverPlan的borrowRecoverList移到方法外查询 update by wgx 2019/02/15
            List<BorrowRecover> borrowRecoverList = this.getBorrowRecover(borrow.getBorrowNid());
            // 用户实际还款额
            for (int i = 0; i < borrowRepayPlans.size(); i++) {
                RepayDetailBean repayPlanDetail = new RepayDetailBean();
                BorrowRepayPlan borrowRepayPlan = borrowRepayPlans.get(i);
                int repayPeriod = borrowRepayPlan.getRepayPeriod();
                int repayStatus = borrowRepayPlan.getRepayStatus();
                if (repayPeriod <= latePeriod && repayStatus != 1) {// 未还款
                    // 计算还款期的数据
                    BeanUtils.copyProperties(borrowRepayPlan, repayPlanDetail);
                    this.calculateRecoverPlan(repayPlanDetail, borrow, repayPeriod, null, borrowRecoverList);
                    borrowRepayPlanDeails.add(repayPlanDetail);
                    //  累加以下值
                    repay.setRepayAccountAll(repay.getRepayAccountAll().add(repayPlanDetail.getRepayAccountAll()));
                    repay.setRepayAccount(repay.getRepayAccount().add(repayPlanDetail.getRepayAccount()));
                    repay.setRepayCapital(repay.getRepayCapital().add(repayPlanDetail.getRepayCapital()));
                    repay.setRepayInterest(repay.getRepayInterest().add(repayPlanDetail.getRepayInterest()));
                    repay.setRepayFee(repay.getRepayFee().add(repayPlanDetail.getRepayFee()));
                    repay.setLateDays(repayPlanDetail.getLateDays());
                    repay.setLateInterest(repay.getLateInterest().add(repayPlanDetail.getLateInterest()));
                    repayAccountAll = repayPlanDetail.getRepayAccountAll();
                } else {
                    BeanUtils.copyProperties(borrowRepayPlan, repayPlanDetail);
                    this.calculateRecoverPlan(repayPlanDetail, borrow);
                    borrowRepayPlanDeails.add(repayPlanDetail);
                }
            }
            repay.setRepayPlanList(borrowRepayPlanDeails);
        }
        if(latePeriod > period) {
            repay.setLastPeriod(latePeriod);
        }
        return repayAccountAll;
    }

    /**
     * 分期还款 计算各期数据(逾期使用)
     *
     * @param form
     * @param isAllRepay
     * @param userId
     * @param borrow
     * @param repayByTerm
     * @throws ParseException
     */
    private void setRecoverPlanPartDetail(ProjectBean form, boolean isAllRepay, String userId, Borrow borrow, RepayBean repayByTerm, int latePeriod) throws Exception {

        // 还款总期数
        int periodTotal = borrow.getBorrowPeriod();
        // 计算当前还款期数
        int repayPeriod = periodTotal - repayByTerm.getRepayPeriod() + 1;
        // 如果用户最后一期不是已还款
        if (repayPeriod <= periodTotal) {
            ;
        } else {// 用户最后一期已还款
            form.setRepayStatus("1");
        }
        int nowPeriod = repayPeriod;
        List<Integer> lateArray = new ArrayList<>();// 逾期期数列表
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
                } else {// 用户未还款本息
                    if(userRepayPlan.getAdvanceStatus() == 3 || userRepayPlan.getAdvanceStatus() == 30){// 逾期未还款/还款中
                        lateArray.add(userRepayPlan.getRepayPeriod());
                        if (nowPeriod < periodTotal) {
                            nowPeriod++;
                        }
                        if(userRepayPlan.getRepayPeriod() == periodTotal){
                            form.setOnlyAllRepay("2");// 全部逾期
                        }
                    } else if (latePeriod >= userRepayPlan.getRepayPeriod()) {// 未还款且未逾期不处理
                        logger.error("【还款明细】传入的的逾期期数有误！第{}期未逾期，传入逾期期数：{}", userRepayPlan.getRepayPeriod(), latePeriod);
                        throw new Exception("传入的的逾期期数有误！");
                    }
                    // 此处分期计算的是本息+管理费
                    userRepayBean.setRepayTotal(userRepayPlan.getRepayAccountAll().toString());
                }
                userRepayBean.setRepayAccount(userRepayPlan.getRepayAccount().add(userRepayPlan.getChargeInterest()).add(userRepayPlan.getLateInterest())
                        .toString());// 设置本期的用户本息和
                userRepayBean.setRepayCapital(userRepayPlan.getRepayCapital().toString());// 用户未还款本息
                userRepayBean.setRepayInterest(userRepayPlan.getRepayInterest().toString()); // 设置本期的用户利息
                userRepayBean.setUserId(userRepayPlan.getUserId().toString());
                userRepayBean.setRepayPeriod(userRepayPlan.getRepayPeriod().toString());
                userRepayBean.setAdvanceStatus(userRepayPlan.getAdvanceStatus().toString());
                userRepayBean.setStatus(userRepayPlan.getRepayStatus().toString());
                userRepayBean.setRepayTime(GetDate.getDateMyTimeInMillis(userRepayPlan.getRepayTime()));
                userRepayBean.setChargeDays(userRepayPlan.getChargeDays().toString());
                userRepayBean.setChargeOriginalInterest(userRepayPlan.getChargePenaltyInterest().subtract(userRepayPlan.getChargeInterest()).toString());
                userRepayBean.setChargePenaltyInterest(userRepayPlan.getChargePenaltyInterest().toString());
                userRepayBean.setManageFee(userRepayPlan.getRepayFee().toString());
                userRepayBean.setLateDays(userRepayPlan.getLateDays().toString());
                userRepayBean.setLateInterest(userRepayPlan.getLateInterest().toString());
                List<RepayRecoverPlanBean> userRecoversDetails = userRepayPlan.getRecoverPlanList();
                List<ProjectRepayDetailBean> userRepayDetails = new ArrayList<ProjectRepayDetailBean>();
                for (int j = 0; j < userRecoversDetails.size(); j++) {
                    RepayRecoverPlanBean userRecoverPlan = userRecoversDetails.get(j);
                    BigDecimal recoverAccount = userRecoverPlan.getRecoverAccountOld();
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
                            // 提前还款利息拆分 add by wgx 2019/03/22
                            userRepayDetail.setChargeOriginalInterest(creditRepay.getChargePenaltyInterest().subtract(creditRepay.getChargeInterest()).toString());
                            userRepayDetail.setChargePenaltyInterest(creditRepay.getChargePenaltyInterest().toString());
                            userRepayDetail.setManageFee(creditRepay.getManageFee().toString());
                            userRepayDetail.setLateDays(userRecoverPlan.getLateDays().toString());
                            userRepayDetail.setLateInterest(creditRepay.getLateInterest().toString());
                            userRepayDetail.setAdvanceStatus(userRecoverPlan.getAdvanceStatus().toString());
                            userRepayDetail.setRepayTime(GetDate.getDateMyTimeInMillis(userRecoverPlan.getRecoverTime()));
                            BigDecimal total = BigDecimal.ZERO;
                            if (creditRepay.getStatus() == 1) {
                                total = creditRepay.getAssignRepayAccount().add(creditRepay.getManageFee());
                            } else {
                                total = creditRepay.getAssignTotal();
                            }
                            userRepayDetail.setRepayTotal(total.toString());
                            userRepayDetail.setStatus(creditRepay.getStatus().toString());
                            userRepayDetail.setUserId(creditRepay.getUserId().toString());
                            // userName直接取表中数据 update by wgx 2019/02/15
                            String userNameStr = creditRepay.getUserName().substring(0, 1).concat("**");
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
                            // 提前还款利息拆分 add by wgx 2019/03/22
                            userRepayDetail.setChargeOriginalInterest(creditRepay.getRepayAdvancePenaltyInterest().subtract(creditRepay.getRepayAdvanceInterest()).toString());
                            userRepayDetail.setChargePenaltyInterest(creditRepay.getRepayAdvancePenaltyInterest().toString());
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
                            // userName直接取表中数据 update by wgx 2019/02/15
                            String userNameStr = creditRepay.getUserName().substring(0, 1).concat("**");
                            userRepayDetail.setUserName(userNameStr);
                            userRepayDetails.add(userRepayDetail);
                        }
                    }
                    boolean overFlag = isOverUndertake(userRecoverPlan, recoverAccount, sumAccount, true, hjhFlag);
                    Integer recoverStatus = userRecoverPlan.getRecoverStatus();
                    if (hjhFlag == 0) {
                        if (userRecoverPlan.getCreditStatus() == 2) {
                            overFlag = false;
                        }
                    }
                    if (overFlag) {
                        ProjectRepayDetailBean userRepayDetail = new ProjectRepayDetailBean();
                        userRepayDetail.setRepayAccount(userRecoverPlan.getRecoverAccount().toString());
                        userRepayDetail.setRepayCapital(userRecoverPlan.getRecoverCapital().toString());
                        userRepayDetail.setRepayInterest(userRecoverPlan.getRecoverInterest().toString());
                        userRepayDetail.setChargeDays(userRecoverPlan.getChargeDays().toString());
                        if (recoverStatus == 1) {//已还款
                            //userRepayDetail.setChargeInterest(userRecoverPlan.getChargeInterestOld().multiply(new BigDecimal("-1")).toString());
                            userRepayDetail.setChargeOriginalInterest(userRecoverPlan.getChargePenaltyInterestOld().subtract(userRecoverPlan.getChargeInterestOld()).toString());
                            userRepayDetail.setChargePenaltyInterest(userRecoverPlan.getChargePenaltyInterestOld().toString());
                        } else {
                            //userRepayDetail.setChargeInterest(userRecoverPlan.getChargeInterest().multiply(new BigDecimal("-1")).toString());
                            userRepayDetail.setChargeOriginalInterest(userRecoverPlan.getChargePenaltyInterest().subtract(userRecoverPlan.getChargeInterest()).toString());
                            userRepayDetail.setChargePenaltyInterest(userRecoverPlan.getChargePenaltyInterest().toString());
                        }
                        userRepayDetail.setManageFee(userRecoverPlan.getRecoverFee().toString());
                        userRepayDetail.setLateDays(userRecoverPlan.getLateDays().toString());
                        if (recoverStatus == 1) {
                            userRepayDetail.setLateInterest(userRecoverPlan.getLateInterestOld().toString());
                        } else {
                            userRepayDetail.setLateInterest(userRecoverPlan.getLateInterest().toString());
                        }
                        userRepayDetail.setAdvanceStatus(userRecoverPlan.getAdvanceStatus().toString());
                        userRepayDetail.setRepayTime(GetDate.getDateMyTimeInMillis(userRecoverPlan.getRecoverTime()));
                        BigDecimal total = new BigDecimal("0");
                        if (recoverStatus == 1) {
                            total = userRecoverPlan.getRecoverAccountYesOld().subtract(sumAccount).add(userRecoverPlan.getRecoverFee());
                        } else {
                            // recover中account未更新
                            total = userRecoverPlan.getRecoverTotal();
                        }
                        userRepayDetail.setRepayTotal(total.toString());
                        userRepayDetail.setStatus(recoverStatus.toString());
                        userRepayDetail.setUserId(userRecoverPlan.getUserId().toString());
                        // userName直接取表中数据 update by wgx 2019/02/15
                        String userNameStr = userRecoverPlan.getUserName().substring(0, 1).concat("**");
                        userRepayDetail.setUserName(userNameStr);
                        userRepayDetails.add(userRepayDetail);
                    }
                }
                userRepayBean.setUserRepayDetailList(userRepayDetails);
                recoverList.add(userRepayBean);
            }
            form.setUserRepayList(recoverList);
            form.setUserRepayList(recoverList);
            form.setManageFee(repayByTerm.getRepayFee().toString());
            form.setRepayTotal(repayByTerm.getRepayAccountAll().toString());// 此处计算的是还款总额包含管理费
            form.setRepayAccount(repayByTerm.getRepayAccount().add(repayByTerm.getChargeInterest()).add(repayByTerm.getLateInterest())
                    .toString());
            form.setRepayCapital(repayByTerm.getRepayCapital().toString());
            form.setRepayInterest(repayByTerm.getRepayInterest().add(repayByTerm.getChargeInterest()).add(repayByTerm.getLateInterest()).toString());
            form.setShouldInterest(repayByTerm.getRepayInterest().toString());
            form.setLateStatus("1");
            form.setAdvanceStatus(repayByTerm.getAdvanceStatus().toString());
            form.setChargeDays(repayByTerm.getChargeDays().toString());
            form.setChargeOriginalInterest(repayByTerm.getChargePenaltyInterest().subtract(repayByTerm.getChargeInterest()).toString());
            form.setChargePenaltyInterest(repayByTerm.getChargePenaltyInterest().toString());
            form.setLateDays(repayByTerm.getLateDays().toString());
            form.setLateInterest(repayByTerm.getLateInterest().toString());
        }
        if (latePeriod == periodTotal) {
            form.setOnlyAllRepay("2");// 全部逾期
        }
        form.setLatePeriod(latePeriod);// 提交的逾期期数
        form.setRepayPeriod(String.valueOf(nowPeriod));// 设置当期的还款期数
        form.setLateArray(lateArray);// 设置未还款逾期列表
    }

    @Override
    public Integer selectSponsorLogCount(RepayListRequest requestBean){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userName", requestBean.getUserId());
        Integer count = repayManageCustomizeMapper.selectSponsorLogCount(param);

        return count;
    }
    @Override
    public  List<SponsorLogCustomizeVO> selectSponsorLog(RepayListRequest requestBean){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userName", requestBean.getUserId());

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

        List<SponsorLogCustomizeVO> list = repayManageCustomizeMapper.selectSponsorLog(param);

        return list;

    }

    @Override
    public int updateSponsorLog(@Valid RepayListRequest requestBean) {


        SponsorLog sl = sponsorLogMapper.selectByPrimaryKey(Integer.valueOf(requestBean.getId()));
        RUserExample example2=new RUserExample();
        example2.or().andUsernameEqualTo(sl.getNewSponsor());
        RUser user = rUserMapper.selectByExample(example2).get(0);
        sl.setStatus(Integer.valueOf(requestBean.getStatus()));
        sl.setUpdateTime(new Date());
        //sl.setUpdateUserName(user.getUsername());
        sl.setNewSponsorId(user.getUserId());
        sponsorLogMapper.updateByPrimaryKeySelective(sl);
        if(requestBean.getStatus().equals("1")) {

            BorrowInfoExample example=new BorrowInfoExample();
            example.or().andBorrowNidEqualTo(sl.getBorrowNid());
            BorrowInfoWithBLOBs borrowInfo = borrowInfoMapper.selectByExampleWithBLOBs(example).get(0);
            borrowInfo.setRepayOrgName(sl.getNewSponsor());
            borrowInfo.setIsRepayOrgFlag(1);
            borrowInfo.setRepayOrgUserId(Integer.valueOf(user.getUserId()));
            borrowInfoMapper.updateByPrimaryKeyWithBLOBs(borrowInfo);

//			BorrowInfo record2=new BorrowInfo();
//			BorrowInfoExample example2=new BorrowInfoExample();
//			example2.or().andBorrowNidEqualTo(requestBean.getBorrowNid());
//			record2.setRepayOrgName(user.getUsername());
//			record2.setIsRepayOrgFlag(1);
//			borrowInfoMapper.updateByExample(record2, example2);
        }
        return 0;
    }
}
