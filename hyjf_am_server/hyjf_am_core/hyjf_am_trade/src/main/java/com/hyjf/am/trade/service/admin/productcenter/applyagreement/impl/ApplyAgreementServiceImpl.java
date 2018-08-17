package com.hyjf.am.trade.service.admin.productcenter.applyagreement.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.fdd.FddGenerateContractBean;
import com.hyjf.am.resquest.admin.ApplyAgreementRequest;
import com.hyjf.am.resquest.admin.BorrowRepayAgreementRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.BorrowRepayAgreementCustomize;
import com.hyjf.am.trade.service.admin.productcenter.applyagreement.ApplyAgreementService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 垫付协议管理
 * @version ApplyAgreementServiceImpl, v0.1 2018/8/14 14:26
 * @Author: Zha Daojian
 */
@Service
public class ApplyAgreementServiceImpl extends BaseServiceImpl implements ApplyAgreementService {

    /**
     * 获取列表数量
     *
     * @param request
     * @return
     */
    @Override
    public Integer getListTotal(ApplyAgreementRequest request) {
        ApplyAgreementExample applyAgreementExample =  convertExample(request);
        Integer count = this.applyAgreementMapper.countByExample(applyAgreementExample);
        return count;
    }

    /**
     * 获取列表
     *
     * @param request
     * @return
     */
    @Override
    public List<ApplyAgreement> getList(ApplyAgreementRequest request) {
        ApplyAgreementExample applyAgreementExample =  convertExample(request);
        applyAgreementExample.setOrderByClause("create_time Desc");
        List<ApplyAgreement> list = this.applyAgreementMapper.selectByExample(applyAgreementExample);
        if(list!=null && list.size()>0){
            for (ApplyAgreement applyAgreement : list) {
                String tenderNid = "DF"+"-"+applyAgreement.getRepayPeriod()+"-";
                TenderAgreementExample example = new TenderAgreementExample();
                TenderAgreementExample.Criteria criteria= example.createCriteria();
                criteria.andTenderNidLike(tenderNid+"%");
                criteria.andBorrowNidEqualTo(applyAgreement.getBorrowNid());
                List<TenderAgreement> tenderAgreements= this.tenderAgreementMapper.selectByExample(example);
                //申请状态 0 全部；1申请失败(hyjf_tender_agreement没有记录)：2申请中；3申请成功
                if(tenderAgreements != null){
                    int tenderAgreementCout = 0;//hyjf_tender_agreement状态为3的数量和hyjf_apply_agreement协议份数相同表示都生成成功
                    for (TenderAgreement tenderAgreement : tenderAgreements) {
                        if(tenderAgreement.getStatus()==1 || tenderAgreement.getStatus()==2 ){
                            applyAgreement.setStatus(2);
                            break;
                        }
                        tenderAgreementCout++;
                    }
                    if(tenderAgreementCout==applyAgreement.getAgreementNumber()){
                        applyAgreement.setStatus(3);
                    }
                }else{
                    applyAgreement.setStatus(1);
                }
            }
        }
        return list;
    }

    /**
     * 垫付协议申请明细列表页--分期列表总数量
     *
     * @param map
     * @return
     */
    @Override
    public Integer getListTotalPlan(Map map) {
        return this.borrowRepayAgreementCustomizeMapper.countBorrowRepayPlan(map);
    }

    /**
     * 垫付协议申请明细列表页--分期
     *
     * @param map
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<BorrowRepayAgreementCustomize> getListPlay(Map map, int limitStart, int limitEnd) {
        map.put("limitStart",limitStart);
        map.put("limitEnd",limitEnd);
        List<BorrowRepayAgreementCustomize> list = this.borrowRepayAgreementCustomizeMapper.selectBorrowRepayPlan(map);
        return list;
    }

    /**
     * 垫付协议申请明细列表页--不分期列表总数量
     *
     * @param map
     * @return
     */
    @Override
    public Integer getListTotal(Map map) {
        return this.borrowRepayAgreementCustomizeMapper.countBorrowRepay(map);
    }

    /**
     * 垫付协议申请明细列表页--不分期
     *
     * @param map
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<BorrowRepayAgreementCustomize> getList(Map map, int limitStart, int limitEnd) {
        map.put("limitStart",limitStart);
        map.put("limitEnd",limitEnd);
        List<BorrowRepayAgreementCustomize> list = this.borrowRepayAgreementCustomizeMapper.selectBorrowRepay(map);
        return list;
    }

    /**
     * 封装查询条件
     * @param request
     * @return ApplyAgreementExample
     */
    private ApplyAgreementExample convertExample(ApplyAgreementRequest request){
        ApplyAgreementExample applyAgreementExample = new ApplyAgreementExample();
        ApplyAgreementExample.Criteria agreementcriteria = applyAgreementExample.createCriteria();
        if(StringUtils.isNotEmpty(request.getBorrowNid())){
            agreementcriteria.andBorrowNidEqualTo(request.getBorrowNid());
        }
        if(StringUtils.isNotEmpty(request.getBorrowPeriod())){
            agreementcriteria.andRepayPeriodEqualTo(Integer.valueOf(request.getBorrowPeriod()));
        }
        if(StringUtils.isNotEmpty(request.getTimeStart()) &&
                StringUtils.isNotEmpty(request.getTimeEnd())){
            Date endDate = GetDate.stringToDate(request.getTimeEnd());
            Date startDate = GetDate.stringToDate(request.getTimeStart());
            agreementcriteria.andCreateTimeBetween(startDate, endDate);
        }
        return applyAgreementExample;
    }

    /**
     * 批量生成垫付债转协议
     *
     * @param request
     * @author Zha Daojian
     * @date 2018/7/12 10:52
     */
    @Override
    public Integer generateContract(BorrowRepayAgreementRequest request) {

        //获取借款编号和期数组合
        String ids = request.getIds();
        if (StringUtils.isEmpty(ids)) {
            return 0;
        }
        //借款编号和期数组合
        List<String> recordList = null;
        try {
            recordList = JSONArray.parseArray(ids, String.class);
        } catch (Exception e) {
            return 0;
        }
        //批量生成协议总条数
        int agreementsSUM = 0;
        for (String string : recordList) {
            String[] paemStrings = string.split("_");
            if (paemStrings != null && paemStrings.length > 0) {
                //当前生成协议条数
                int agreements = 0;
                String borrow_nid = paemStrings[0];//借款编号
                int repay_period = Integer.valueOf(paemStrings[1]);//期数
                // 获取标的信息
                Borrow borrow = this.getBorrow(borrow_nid);
                BorrowInfo borrowInfo = this.getBorrowInfoByNid(borrow_nid);
                if (borrow == null) {
                    return 0;
                }
                //还款方式
                String borrowStyle = borrow.getBorrowStyle();
                // 是否分期(true:分期, false:不分期)
                boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)
                        || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                        || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
                String planNid = borrow.getPlanNid();//为空时，为直投，否则为计划
                //承接人都是垫付机构
                int repayOrgUserId = borrowInfo.getRepayOrgUserId();//垫付机构用户ID
                if (StringUtils.isEmpty(planNid)) {//直投
                    if(isMonth) {//分期
                        /**
                         * huiyingdai_borrow_recover(标的放款记录（投资人） 总表)
                         * huiyingdai_borrow_recover_plan(标的放款记录分期（投资人）)-borrow_nid,repay_period
                         */
                        List<BorrowRecover> borrowRecoverPlist = this.getBorrowRecover(borrow_nid);
                        if (borrowRecoverPlist == null || borrowRecoverPlist.size()==0) {
                            return 0;
                        }
                        for (BorrowRecover borrowRecoverP : borrowRecoverPlist) {
                            //已承接债转本金
                            BigDecimal  creditAmountp = borrowRecoverP.getCreditAmount();
                            //应还本金
                            BigDecimal recoverCapitalp = borrowRecoverP.getRecoverCapital();
                            /**
                             * 1：已债转金额(creditAmountp)不为0并且等于应还本金（recoverCapitalp）--全部承接债转
                             * 2：已债转金额(creditAmountp)不为0并且小于应还本金（recoverCapitalp）--部分承接债转
                             * 3：已债转金额(creditAmountp)为0--非承接债转
                             */
                            List<BorrowRecoverPlan> borrowRecoverList = this.getBorrowRecoverPlan(borrowRecoverP.getNid(),repay_period);
                            if (borrowRecoverList == null || borrowRecoverList.size()==0) {
                                break;
                            }
                            for (BorrowRecoverPlan borrowRecover : borrowRecoverList) {
                                //债转投标单号
                                String nid = borrowRecover.getNid();
                                //已承接债转本金-当期
                                BigDecimal creditAmount = borrowRecover.getCreditAmount();
                                //应还本金-当期
                                BigDecimal recoverCapital = borrowRecover.getRecoverCapital();
                                List<CreditRepay> creditRepayList = this.selectCreditRepay(nid,repay_period);
                                if(creditRepayList!=null && creditRepayList.size()>0){//债转
                                    if ((creditAmountp.compareTo(new BigDecimal("0.00"))==1) && (creditAmountp.compareTo(recoverCapitalp)==0)) {//全部债转
                                            //填充所有债转信息
                                            for (CreditRepay creditRepay : creditRepayList) {
                                                FddGenerateContractBean bean = getFddGenerateContractBean(borrow_nid,repay_period,repayOrgUserId,creditRepay.getAssignNid()+"_"+repay_period,creditRepay.getCreditUserId());
                                                JSONObject paramter = getAllcreditParamter(creditRepay,bean,borrow);
                                                //出让人-承接人huiyingdai_credit_repay（nid-credit_tender_nid）-user_id(承接人的id)_List
                                                bean.setParamter(paramter);
                                            }
                                        }
                                }else {//非债转
                                }
                            }
                        }
                    }else{//不分期

                    }

                } else {//计划
                }

            }
        }
        return null;
    }

    /**
     * 查询相应的债转还款记录
     * @param borrowNid
     * @param periodNow
     * @return
     */
    private List<CreditRepay> selectCreditRepay(String borrowNid, Integer periodNow) {
        CreditRepayExample example = new CreditRepayExample();
        CreditRepayExample.Criteria crt = example.createCriteria();
        crt.andBidNidEqualTo(borrowNid);
        crt.andRecoverPeriodEqualTo(periodNow);
        crt.andStatusEqualTo(1);
        example.setOrderByClause("id ASC");
        List<CreditRepay> creditRepayList = this.creditRepayMapper.selectByExample(example);
        return creditRepayList;
    }

    private FddGenerateContractBean getFddGenerateContractBean(String borrow_nid, Integer repay_period,Integer repayOrgUserId,String contractId,Integer creditUserId) {
        FddGenerateContractBean bean = new FddGenerateContractBean();
        //垫付协议申请-协议生成详情
        ApplyAgreementInfo applyAgreementInfo = new ApplyAgreementInfo();
        applyAgreementInfo.setBorrowNid(borrow_nid);//借款编号
        applyAgreementInfo.setRepayPeriod(repay_period);//期数
        applyAgreementInfo.setContractId(repay_period+"-"+contractId);//合同编号
        applyAgreementInfo.setUserId(repayOrgUserId);//投资人(出让人)
        applyAgreementInfo.setCreditUserId(creditUserId+"");//承接人-垫付机构
        applyAgreementInfo.setStatus(5);
        saveApplyAgreementInfo(applyAgreementInfo);
        bean.setBorrowNid(borrow_nid);//标的编号
        bean.setRepayPeriod(repay_period);//期数
        bean.setTransType(5);//交易类型
        bean.setTenderType(2);//投资类型 0：原始 1：债转 2 :计划
        bean.setTenderUserId(repayOrgUserId);//投资人-承接人（垫付机构）
        bean.setCreditUserID(creditUserId);//出讓人
        return bean;
    }

    /**
     * 保存垫付协议申请-协议生成详情
     * @param applyAgreementInfo
     * @return
     */
    private int saveApplyAgreementInfo(ApplyAgreementInfo applyAgreementInfo) {

        ApplyAgreementInfoExample example = new ApplyAgreementInfoExample();
        example.createCriteria().andContractIdEqualTo(applyAgreementInfo.getContractId());
        List<ApplyAgreementInfo> openAccountRecords = this.applyAgreementInfoMapper.selectByExample(example);
        if(openAccountRecords != null && openAccountRecords.size() > 0){
            applyAgreementInfo.setId(openAccountRecords.get(0).getId());
            applyAgreementInfo.setUpdateTime(GetDate.getDate());
            applyAgreementInfo.setUpdateTime(openAccountRecords.get(0).getCreateTime());
            return this.applyAgreementInfoMapper.updateByPrimaryKey(applyAgreementInfo);

        }else {
            applyAgreementInfo.setCreateTime(GetDate.getDate());
            return this.applyAgreementInfoMapper.insert(applyAgreementInfo);
        }
    }

    /**转让债转参数集合*/
    private JSONObject getAllcreditParamter(CreditRepay creditRepay,FddGenerateContractBean bean,Borrow borrow) {
        JSONObject paramter = new JSONObject();
        /** 标的基本数据 */
        /*String borrowStyle = borrow.getBorrowStyle();// 还款方式
        Integer borrowPeriod = borrow.getBorrowPeriod();
        String borrowDate = "";

        // 是否月标(true:月标, false:天标)
        boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_END.equals(borrowStyle);
        if(isMonth){//月表
            borrowDate = borrowPeriod + "个月";
        }else{
            borrowDate = borrowPeriod + "天";
        }
        // 是否分期(true:分期, false:不分期)
        boolean isPlan = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
        String  recoverTime = "0";//还款时间
        if(isPlan){//分期repay_last_time-huiyingdai_borrow
            recoverTime = borrow.getRepayLastTime();
        }else{//不分期assign_repay_end_time
            recoverTime = creditRepay.getAssignRepayEndTime()+"";
        }
        if(CustomConstants.BORROW_STYLE_END.equals(borrowStyle)
                ||CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)){//到期还本付息的
            *//**转让本金为借款本金，转让价款为本息之和。剩余期限为0*//*
            // 转让债权本金
            paramter.put("assignCapital", creditRepay.getAssignRepayCapital()+"");//已承接债转本金recover_capital
            //转让价款
            paramter.put("assignPay",  creditRepay.getAssignRepayCapital().add(creditRepay.getAssignRepayInterest())+"");//已承接垫付利息recover_interest
            // 债转期限
            paramter.put("creditTerm", 0+"天");

        }else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {//先息后本时
            *//**还某一期（非最后一期）转让本金是0，转让价款是该期利息金额。剩余转让期间是债权的剩余期限（应还时间-还款时间）。
             还最后一期转让本金是借款本金，转让价款是借款本金+最后一期利息金额。剩余转让期间为0*//*
            if(creditRepay.getRecoverPeriod()!=borrow.getBorrowPeriod()){//非最后一期
                // 转让债权本金
                paramter.put("assignCapital", 0+"");//已承接债转本金
                //转让价款
                paramter.put("assignPay",  creditRepay.getAssignRepayInterest()+"");//已承接垫付利息recover_interest
                // 债转期限assign_repay_yes_time
                paramter.put("creditTerm", DateUtils.differentDaysByString(recoverTime, creditRepay.getAssignRepayYesTime()+"")+"天");
            }else{//最后一期
                // 转让债权本金
                paramter.put("assignCapital", creditRepay.getAssignRepayCapital()+"");//已承接债转本金
                //转让价款
                paramter.put("assignPay",  creditRepay.getAssignRepayCapital().add(creditRepay.getAssignRepayInterest())+"");//已承接垫付利息recover_interest
                // 债转期限
                paramter.put("creditTerm", 0+"天");
            }

        }else if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)) {//等额本息时
            *//**转让期限是原债权的剩余期限，转让本金为本期借款本金，转让价款为本期应还本息之和*//*
            // 转让债权本金
            paramter.put("assignCapital", creditRepay.getAssignRepayCapital()+"");//已承接债转本金recover_capital
            //转让价款
            paramter.put("assignPay",  creditRepay.getAssignRepayCapital().add(creditRepay.getAssignRepayInterest())+"");//已承接垫付利息recover_interest
            // 债转期限
            paramter.put("creditTerm", DateUtils.differentDaysByString(recoverTime,creditRepay.getAssignRepayYesTime()+"天"));

        }else{
            // 转让债权本金
            paramter.put("assignCapital", creditRepay.getAssignRepayCapital()+"");//已承接债转本金recover_capital
            //转让价款
            paramter.put("assignPay", creditRepay.getAssignRepayInterest()+"");//已承接垫付利息recover_interest
            // 债转期限
            paramter.put("creditTerm", DateUtils.differentDaysByString(recoverTime,creditRepay.getAssignRepayYesTime()+"天"));
        }
        // 签署时间
        paramter.put("addTime", GetDate.getDateMyTimeInMillisYYYYMMDD(creditRepay.getAssignRepayYesTime()));
        //转让日期
        paramter.put("creditTime", GetDate.getDateMyTimeInMillisYYYYMMDD(creditRepay.getAssignRepayYesTime()));

        // 标的编号
        paramter.put("borrowNid", borrow.getBorrowNid());
        //编号
        paramter.put("NID", creditRepay.getUniqueNid());
        //借款本金总额
        paramter.put("borrowAccount", borrow.getAccount().toString());
        // 借款利率
        paramter.put("borrowApr", borrow.getBorrowApr() + "%");
        // 还款方式
        paramter.put("borrowStyle", this.getBorrowStyle(borrow.getBorrowStyle()));
        // 借款期限
        paramter.put("borrowPeriod", borrowDate);


        // 出让人相关 start-repay_org_user_id
        UsersInfo creditUsersInfo = this.getUsersInfoByUserId(creditRepay.getUserId());

        paramter.put("CreditTruename", creditUsersInfo.getTruename());
        // 出让人身份证号
        paramter.put("CreditIdcard", creditUsersInfo.getIdcard());
        int tenderUserId = bean.getTenderUserId();
        // 承接人用户 start
        UsersInfo tenderUserInfo = this.getUsersInfoByUserId(tenderUserId);
        // 获取承接人平台信息
        Users tenderUser = this.getUsersByUserId(tenderUserId);
        String tenderTrueName = null;
        String tenderIdCard = null;
        tenderTrueName = tenderUserInfo.getTruename();
        tenderIdCard = tenderUserInfo.getIdcard();
        if(tenderUser.getUserType() == 1){
            CorpOpenAccountRecord info = getCorpOpenAccountInfoByUserID(bean.getTenderUserId());
            tenderTrueName = info.getBusiName();
            tenderIdCard = info.getBusiCode();
        }

        // 承接人用户 start
        // 承接人真实姓名
        paramter.put("truename", tenderTrueName);
        // 承接人身份证号
        paramter.put("idcard", tenderIdCard);
        // 承接人用户 end

        // 获取融资方平台信息
        Users borrowUsers = this.getUsers(borrow.getUserId());
        // 借款人用户名

        paramter.put("BorrowUsername",borrowUsers.getUsername());
        bean.setTenderUserId(tenderUserId);
        bean.setTenderUserName(tenderUser.getUsername());
        bean.setOrdid(creditRepay.getUniqueNid());//承接订单号
        bean.setAssignOrderId(creditRepay.getUniqueNid());
        bean.setCreditNid(creditRepay.getCreditNid());//债转编号
        bean.setCreditTenderNid(creditRepay.getCreditTenderNid());//原始投资订单号*/
        return paramter;
    }

}
