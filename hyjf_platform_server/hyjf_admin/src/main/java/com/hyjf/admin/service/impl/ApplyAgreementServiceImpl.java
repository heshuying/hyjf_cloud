package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.ApplyAgreementRequestBean;
import com.hyjf.admin.beans.request.BorrowRepayAgreementRequestBean;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.service.ApplyAgreementService;
import com.hyjf.admin.service.AutoTenderExceptionService;
import com.hyjf.admin.service.TenderCancelExceptionService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.bean.fdd.FddGenerateContractBean;
import com.hyjf.am.response.trade.ApplyAgreementResponse;
import com.hyjf.am.response.trade.BorrowRepayAgreementResponse;
import com.hyjf.am.resquest.admin.ApplyAgreementAmRequest;
import com.hyjf.am.resquest.admin.ApplyAgreementRequest;
import com.hyjf.am.resquest.admin.BorrowRepayAgreementAmRequest;
import com.hyjf.am.resquest.admin.BorrowRepayAgreementRequest;
import com.hyjf.am.vo.admin.BorrowRecoverCustomizeVO;
import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;
import com.hyjf.am.vo.trade.BorrowRepayAgreementVO;
import com.hyjf.am.vo.trade.CreditRepayVO;
import com.hyjf.am.vo.trade.borrow.ApplyAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.DateUtils;
import com.hyjf.cs.common.service.BaseClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @version ApplyAgreementServiceImpl, v0.1 2018/8/9 16:51
 * @Author: Zha Daojian
 */
@Service
public class ApplyAgreementServiceImpl implements ApplyAgreementService {

    @Autowired
    protected AutoTenderExceptionService autoTenderExceptionService;

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private AmTradeClient amTradeClient;

    public static final String BASE_URL = "http://AM-TRADE/am-trade/applyAgreement";

    /*垫付协议申请列表*/
    public static final String AGREEMENT_LIST_URL = BASE_URL + "/getApplyAgreementList";

    /*垫付协议申请明细列表页count*/
    public static final String AGREEMENT_COUNT_URL = BASE_URL + "/getApplyAgreementCount";

    /*垫付协议申请列表-分期*/
    public static final String ADD_AGREEMENT_LIST_URL_PLAN = BASE_URL + "/getAddApplyAgreementPlanList";

    /*垫付协议申请明细列表页-不分期count*/
    public static final String ADD_AGREEMENT_COUNT_URL_PLAN = BASE_URL + "/getAddApplyAgreementPlanCount";

    /*垫付协议申请列表*/
    public static final String ADD_AGREEMENT_LIST_URL= BASE_URL + "/getAddApplyAgreementList";

    /*垫付协议申请明细列表页count*/
    public static final String ADD_AGREEMENT_COUNT_URL = BASE_URL + "/getAddApplyAgreementCount";

    /**
     * 查询垫付协议申请列表
     * @author Zha Daojian
     * @date 2018/7/11 14:34
     */
    @Override
    public AdminResult getApplyAgreementList(ApplyAgreementRequest request){
        AdminResult result = new AdminResult();
        ApplyAgreementRequestBean bean = new ApplyAgreementRequestBean();
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        ApplyAgreementAmRequest req = CommonUtils.convertBean(request, ApplyAgreementAmRequest.class);
        req.setLimitStart(page.getOffset());
        req.setLimitEnd(page.getLimit());
        ApplyAgreementResponse response = baseClient.postExe(AGREEMENT_COUNT_URL, request, ApplyAgreementResponse.class);
        Integer count = response.getCount();
        if (count > 0) {
            response = baseClient.postExe(AGREEMENT_LIST_URL, request, ApplyAgreementResponse.class);
            List<ApplyAgreementVO> list = response.getResultList();
            bean.setRecordList(list);
        }
        bean.setTotal(count);
        result.setData(bean);
        return result;
    }


    /**
     * 垫付协议申请明细列表页
     * @author Zha Daojian
     * @date 2018/7/12 10:52
     */
    @Override
    public AdminResult getAddApplyAgreementListDetail(BorrowRepayAgreementRequest request){
        AdminResult result = new AdminResult();
        BorrowRepayAgreementRequestBean bean = new BorrowRepayAgreementRequestBean();
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        BorrowRepayAgreementAmRequest req = CommonUtils.convertBean(request, BorrowRepayAgreementAmRequest.class);
        req.setLimitStart(page.getOffset());
        req.setLimitEnd(page.getLimit());
        // 根据标的编号查询标的详情
        BorrowVO borrowVO = amTradeClient.searchBorrowByBorrowNid(request.getBorrowNidSrch());
        if (borrowVO == null) {
            throw new RuntimeException("根据标的编号查询标的详情失败,标的编号:[" + request.getBorrowNidSrch() + "].");
        }else{
            // 还款方式
            String borrowStyle = borrowVO.getBorrowStyle();
            // 是否分期(true:分期, false:不分期)
            boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                    || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
            String countUrl = "";//查询列表数量url
            String ListUrl = "";//查询列表url
            if(isMonth) {//分期
                countUrl =ADD_AGREEMENT_COUNT_URL_PLAN;
                ListUrl =ADD_AGREEMENT_LIST_URL_PLAN;
            }else{//不分期
                countUrl =ADD_AGREEMENT_COUNT_URL;
                ListUrl =ADD_AGREEMENT_LIST_URL;
            }
            BorrowRepayAgreementResponse response = baseClient.postExe(countUrl, req, BorrowRepayAgreementResponse.class);
            Integer count = response.getCount();
            if (count > 0) {
                response = baseClient.postExe(ListUrl, req, BorrowRepayAgreementResponse.class);
                List<BorrowRepayAgreementVO> list = response.getResultList();
                bean.setRecordList(list);
            }
            bean.setTotal(count);
            result.setData(bean);
        }
        return result;
    }

    /**
     * 批量生成垫付债转协议
     *
     * @param request
     * @author Zha Daojian
     * @date 2018/7/12 10:52
     */
    @Override
    public AdminResult generateContract(BorrowRepayAgreementRequest request) {

        AdminResult result = new AdminResult();
        //获取借款编号和期数组合
        String ids = request.getIds();
        if (StringUtils.isEmpty(ids)) {
            return result;
        }
        //借款编号和期数组合
        List<String> recordList = null;
        try {
            recordList = JSONArray.parseArray(ids, String.class);
        } catch (Exception e) {
            return result;
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
                // 获取标的信息
                BorrowVO borrow = amTradeClient.selectBorrowByNid(borrow_nid);
                BorrowInfoVO borrowInfo = amTradeClient.selectBorrowInfoByNid(borrow_nid);
                if (borrow == null || borrowInfo == null) {
                    return new AdminResult(BaseResult.FAIL, "标的不存在");
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
                        List<BorrowRecoverVO> borrowRecoverPlist = amTradeClient.selectBorrowRecoverList(borrow_nid);
                        if (borrowRecoverPlist == null || borrowRecoverPlist.size()==0) {
                            return result;
                        }
                        for (BorrowRecoverVO borrowRecoverP : borrowRecoverPlist) {
                            //已承接债转本金
                            BigDecimal creditAmountp = borrowRecoverP.getCreditAmount();
                            //应还本金
                            BigDecimal recoverCapitalp = borrowRecoverP.getRecoverCapital();
                            /**
                             * 1：已债转金额(creditAmountp)不为0并且等于应还本金（recoverCapitalp）--全部承接债转
                             * 2：已债转金额(creditAmountp)不为0并且小于应还本金（recoverCapitalp）--部分承接债转
                             * 3：已债转金额(creditAmountp)为0--非承接债转
                             */
                            List<BorrowRecoverPlanVO> borrowRecoverList = amTradeClient.selectBorrowRecoverPlanList(borrowRecoverP.getNid(),repay_period);
                            if (borrowRecoverList == null || borrowRecoverList.size()==0) {
                                break;
                            }
                            for (BorrowRecoverPlanVO borrowRecover : borrowRecoverList) {
                                //债转投标单号
                                String nid = borrowRecover.getNid();
                                //已承接债转本金-当期
                                BigDecimal creditAmount = borrowRecover.getCreditAmount();
                                //应还本金-当期
                                BigDecimal recoverCapital = borrowRecover.getRecoverCapital();
                                List<CreditRepayVO> creditRepayList = this.selectCreditRepay(nid,repay_period);
                                if(creditRepayList!=null && creditRepayList.size()>0){//债转
                                    if ((creditAmountp.compareTo(new BigDecimal("0.00"))==1) && (creditAmountp.compareTo(recoverCapitalp)==0)) {//全部债转
                                        //填充所有债转信息
                                        for (CreditRepayVO creditRepay : creditRepayList) {
                                           /* FddGenerateContractBean bean = getFddGenerateContractBean(borrow_nid,repay_period,repayOrgUserId,creditRepay.getAssignNid()+"_"+repay_period,creditRepay.getCreditUserId());
                                            JSONObject paramter = getAllcreditParamter(creditRepay,bean,borrow);
                                            //出让人-承接人huiyingdai_credit_repay（nid-credit_tender_nid）-user_id(承接人的id)_List
                                            bean.setParamter(paramter);*/
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
     * @param nid
     * @param periodNow
     * @return
     */
    private List<CreditRepayVO> selectCreditRepay(String nid, Integer periodNow) {
        return amTradeClient.selectCreditRepayList(nid,periodNow);
    }

/*    private FddGenerateContractBean getFddGenerateContractBean(String borrow_nid, Integer repay_period,Integer repayOrgUserId,String contractId,Integer creditUserId) {
        FddGenerateContractBean bean = new FddGenerateContractBean();
        //垫付协议申请-协议生成详情
        ApplyAgreementInfoVO applyAgreementInfo = new ApplyAgreementInfo();
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

    *//**
     * 保存垫付协议申请-协议生成详情
     * @param applyAgreementInfo
     * @return
     *//*
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

    *//**转让债转参数集合*//*
    private JSONObject getAllcreditParamter(CreditRepay creditRepay,FddGenerateContractBean bean,Borrow borrow) {
        JSONObject paramter = new JSONObject();
        *//** 标的基本数据 *//*
        String borrowStyle = borrow.getBorrowStyle();// 还款方式
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
            recoverTime = borrow.getRepayLastTime()+"";
        }else{//不分期assign_repay_end_time
            recoverTime = creditRepay.getAssignRepayEndTime()+"";
        }
        if(CustomConstants.BORROW_STYLE_END.equals(borrowStyle)
                ||CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)){//到期还本付息的
            *//*转让本金为借款本金，转让价款为本息之和。剩余期限为0*//*
            // 转让债权本金
            paramter.put("assignCapital", creditRepay.getAssignRepayCapital()+"");//已承接债转本金recover_capital
            //转让价款
            paramter.put("assignPay",  creditRepay.getAssignRepayCapital().add(creditRepay.getAssignRepayInterest())+"");//已承接垫付利息recover_interest
            // 债转期限
            paramter.put("creditTerm", 0+"天");

        }else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {//先息后本时
            *//**还某一期（非最后一期）转让本金是0，转让价款是该期利息金额。剩余转让期间是债权的剩余期限（应还时间-还款时间）。
             还最后一期转让本金是借款本金，转让价款是借款本金+最后一期利息金额。剩余转让期间为0 *//*
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
            //转让期限是原债权的剩余期限，转让本金为本期借款本金，转让价款为本期应还本息之和
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
        paramter.put("NID", creditRepay.getAssignNid()+"_"+creditRepay.getRecoverPeriod());
        //借款本金总额
        paramter.put("borrowAccount", borrow.getAccount().toString());
        // 借款利率
        paramter.put("borrowApr", borrow.getBorrowApr() + "%");
        // 还款方式
        paramter.put("borrowStyle", this.getBorrowStyle(borrow.getBorrowStyle()));
        // 借款期限
        paramter.put("borrowPeriod", borrowDate);
        // 出让人相关 start-repay_org_user_id
        UsersInfoVO creditUsersInfo = this.getUsersInfoByUserId(creditRepay.getUserId());
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
        bean.setCreditTenderNid(creditRepay.getCreditTenderNid());//原始投资订单号
        return paramter;
    }

    *//**
     * 获取还款方式
     * @param borrowStyle
     * @return
     *//*
    private  String  getBorrowStyle(String borrowStyle){
        BorrowStyleExample example = new BorrowStyleExample();
        example.createCriteria().andNidEqualTo(borrowStyle);
        List<BorrowStyle> borrowStyles = this.borrowStyleMapper.selectByExample(example);

        if(borrowStyles != null && borrowStyles.size()>0){
            return borrowStyles.get(0).getName();
        }
        return "";
    }
    *//**
     * 获取企业用户信息
     * @param tenderUserId
     * @return
     *//*
    private CorpOpenAccountRecord getCorpOpenAccountInfoByUserID(Integer tenderUserId) {

        CorpOpenAccountRecordExample example = new CorpOpenAccountRecordExample();
        example.createCriteria().andUserIdEqualTo(tenderUserId);
        List<CorpOpenAccountRecord> openAccountRecords = this.corpOpenAccountRecordMapper.selectByExample(example);
        if(openAccountRecords != null && openAccountRecords.size() > 0){
            return openAccountRecords.get(0);
        }
        return null;
    }*/

}
