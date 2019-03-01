package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.ApplyAgreementRequestBean;
import com.hyjf.admin.beans.request.BorrowRepayAgreementRequestBean;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.client.BaseClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.admin.service.AccedeListService;
import com.hyjf.admin.service.ApplyAgreementService;
import com.hyjf.admin.service.AutoTenderExceptionService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.bean.fdd.FddGenerateContractBean;
import com.hyjf.am.response.admin.ApplyAgreementInfoResponse;
import com.hyjf.am.response.trade.ApplyAgreementResponse;
import com.hyjf.am.response.trade.BorrowRepayAgreementResponse;
import com.hyjf.am.resquest.admin.ApplyAgreementRequest;
import com.hyjf.am.resquest.admin.BorrowRepayAgreementAmRequest;
import com.hyjf.am.resquest.admin.BorrowRepayAgreementRequest;
import com.hyjf.am.resquest.admin.DownloadAgreementRequest;
import com.hyjf.am.vo.admin.BorrowRepayAgreementCustomizeVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.CreditRepayVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditRepayVO;
import com.hyjf.am.vo.user.ApplyAgreementInfoVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.FddGenerateContractConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.file.FavFTPUtil;
import com.hyjf.common.file.FileUtil;
import com.hyjf.common.file.SFTPParameter;
import com.hyjf.common.file.ZIPGenerator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @version ApplyAgreementServiceImpl, v0.1 2018/8/9 16:51
 * @Author: Zha Daojian
 */
@Service
public class ApplyAgreementServiceImpl implements ApplyAgreementService {
    protected Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    CommonProducer commonProducer;
    @Autowired
    protected AutoTenderExceptionService autoTenderExceptionService;

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AccedeListService accedeListService;

    public static final String BASE_URL = "http://AM-ADMIN/am-trade/applyAgreement";

    /**垫付协议申请列表*/
    public static final String AGREEMENT_LIST_URL = BASE_URL + "/getApplyAgreementList";

    /**垫付协议申请列表页count*/
    public static final String AGREEMENT_COUNT_URL = BASE_URL + "/getApplyAgreementCount";

    /**垫付协议申请明细列表-分期*/
    public static final String ADD_AGREEMENT_LIST_URL_PLAN = BASE_URL + "/getAddApplyAgreementPlanList";

    /**垫付协议申请明细列表页-分期count*/
    public static final String ADD_AGREEMENT_COUNT_URL_PLAN = BASE_URL + "/getAddApplyAgreementPlanCount";

    /**垫付协议申请明细列表*/
    public static final String ADD_AGREEMENT_LIST_URL= BASE_URL + "/getAddApplyAgreementList";

    /**垫付协议申请明细列表页count*/
    public static final String ADD_AGREEMENT_COUNT_URL = BASE_URL + "/getAddApplyAgreementCount";

    /**垫付协议生成标识*/
    private String DF = "DF";

    /**
     * 查询垫付协议申请列表
     * @author Zha Daojian
     * @date 2018/7/11 14:34
     */
    @Override
    public AdminResult getApplyAgreementList(ApplyAgreementRequest request){
        AdminResult result = new AdminResult();
        ApplyAgreementRequestBean bean = new ApplyAgreementRequestBean();
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
        if(StringUtils.isEmpty(request.getBorrowNidSrch())){
            return new AdminResult(BaseResult.FAIL, "项目编号不能为空");
        }
        BorrowRepayAgreementRequestBean bean = new BorrowRepayAgreementRequestBean();
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        BorrowRepayAgreementAmRequest req = CommonUtils.convertBean(request, BorrowRepayAgreementAmRequest.class);
        req.setLimitStart(page.getOffset());
        req.setLimitEnd(page.getLimit());
        // 根据标的编号查询标的详情
        BorrowAndInfoVO borrowVO = amTradeClient.searchBorrowByBorrowNid(request.getBorrowNidSrch());
        if (borrowVO == null) {
            return new AdminResult(BaseResult.FAIL, "未找到对应的编号的标");
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
                List<BorrowRepayAgreementCustomizeVO> list = response.getResultList();
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
    public AdminResult generateContract(BorrowRepayAgreementRequest request,AdminSystemVO currUser) {

        //获取项目编号和期数组合
        List<String> recordList = request.getIds();
        if (recordList==null) {
            logger.error("-------------------------担保机构协议管理， 项目编号和期数组合不能为空："+ request.getIds());
            return new AdminResult(BaseResult.FAIL, "项目编号和期数组合不能为空");
        }

        //批量生成协议总条数
        int agreementsSUM = 0;
        for (String string : recordList) {
            String[] paemStrings = string.split("_");
            if (paemStrings != null && paemStrings.length > 0) {
                //当前生成协议条数
                int agreements = 0;
                String borrow_nid = paemStrings[0];//项目编号
                int repay_period = Integer.valueOf(paemStrings[1]);//期数
                // 获取标的信息
                BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(borrow_nid);
                BorrowInfoVO borrowInfo = amTradeClient.selectBorrowInfoByNid(borrow_nid);
                if (borrow == null || borrowInfo == null) {
                    logger.error("-------------------------担保机构协议管理， 标的不存在borrow_nid："+borrow_nid);
                    return new AdminResult(BaseResult.FAIL, "标的不存在");
                }
                //还款方式
                String borrowStyle = borrow.getBorrowStyle();
                // 是否分期(true:分期, false:不分期)
                boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)
                        || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                        || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
                String planNid = borrow.getPlanNid();//为空时，为直投，否则为计划
                /**
                 * huiyingdai_borrow_recover(标的放款记录（出借人） 总表)
                 * huiyingdai_borrow_recover_plan(标的放款记录分期（出借人）)-borrow_nid,repay_period
                 */
                List<BorrowRecoverVO> borrowRecoverPlist = amTradeClient.selectBorrowRecoverList(borrow_nid);
                if (borrowRecoverPlist == null || borrowRecoverPlist.size()==0) {
                    logger.error("-------------------------担保机构协议管理， 标的不存在borrow_nid："+borrow_nid);
                    return new AdminResult(BaseResult.FAIL, "标的不存在");
                }
                boolean isNotPlan = StringUtils.isEmpty(planNid);//是否是直投标
                for (BorrowRecoverVO borrowRecoverP : borrowRecoverPlist) {
                    /**
                     * 1：已债转金额(creditAmountp)不为0并且等于应还本金（recoverCapitalp）--全部承接债转
                     * 2：已债转金额(creditAmountp)不为0并且小于应还本金（recoverCapitalp）--部分承接债转
                     * 3：已债转金额(creditAmountp)为0--非承接债转
                     */
                    if(isMonth) {//分期
                        //logger.info("-------------------------担保机构协议管理， 处理分期:"+borrowRecoverP.getNid());
                        List<BorrowRecoverPlanVO> borrowRecoverList = amTradeClient.selectBorrowRecoverPlanList(borrowRecoverP.getNid(),repay_period);
                        if (borrowRecoverList == null || borrowRecoverList.size()==0) {
                            logger.error("-------------------------担保机构协议管理， 标的放款记录列表获取失败borrowRecoverP.getNid():"+borrowRecoverP.getNid());
                            logger.error("-------------------------担保机构协议管理， 标的放款记录列表获取失败repay_period:"+repay_period);
                            break;
                        }
                        for (BorrowRecoverPlanVO borrowRecover : borrowRecoverList) {
                            if(isNotPlan){//直投
                                agreements =  convertAndSendPlan(borrow,borrowInfo,borrowRecoverP,borrowRecover);
                                agreementsSUM = agreementsSUM+agreements;
                            }else{//计划
                                agreements =  hjhConvertAndSendPlan(borrow,borrowInfo,borrowRecoverP,borrowRecover);
                                agreementsSUM = agreementsSUM+agreements;
                            }
                        }
                    }else{//不分期
                        if(isNotPlan) {//直投
                            agreements = convertAndSend(borrow, borrowInfo, borrowRecoverP);
                            agreementsSUM = agreementsSUM+agreements;
                        }else {//计划
                            agreements = hjhConvertAndSend(borrow, borrowInfo, borrowRecoverP);
                            agreementsSUM = agreementsSUM+agreements;
                        }
                    }
                }
                ApplyAgreementVO applyAgreement = new ApplyAgreementVO();
                applyAgreement.setBorrowNid(borrow_nid);
                applyAgreement.setRepayPeriod(repay_period);
                applyAgreement.setApplyUserId(Integer.valueOf(currUser.getId()));
                applyAgreement.setApplyUserName(currUser.getUsername());
                applyAgreement.setAgreementNumber(agreementsSUM);
                applyAgreement.setStatus(1);
                applyAgreement.setCreateTime(new Date());
                applyAgreement.setDelFlag(0);
                amTradeClient.saveApplyAgreement(applyAgreement);

            }
        }
        return new AdminResult(BaseResult.SUCCESS, "申请成功，大约需要10分钟生成协议");
    }

    /**
     * 处理分期债转
     * @param borrow
     * @param borrowInfo
     * @param borrowRecoverP
     * @param borrowRecover
     */
    private int convertAndSendPlan(BorrowAndInfoVO borrow,BorrowInfoVO borrowInfo,BorrowRecoverVO borrowRecoverP, BorrowRecoverPlanVO borrowRecover){
        int agreements = 0;
        String borrow_nid = borrow.getBorrowNid();
        //承接人都是担保机构
        int repayOrgUserId = borrowInfo.getRepayOrgUserId();//担保机构用户ID
        //已承接债转本金
        BigDecimal creditAmountp = borrowRecoverP.getCreditAmount();
        //应还本金
        BigDecimal recoverCapitalp = borrowRecoverP.getRecoverCapital();
        //债转投标单号
        String nid = borrowRecoverP.getNid();
        //已承接债转本金-当期
        BigDecimal creditAmount = borrowRecover.getCreditAmount();
        //应还本金-当期
        BigDecimal recoverCapital = borrowRecover.getRecoverCapital();
        //当前期数
        int repay_period = borrowRecover.getRecoverPeriod();
        int recoverUserId= borrowRecover.getUserId();
        List<CreditRepayVO> creditRepayListPlan = this.selectCreditRepay(nid,repay_period);
        if(creditRepayListPlan!=null && creditRepayListPlan.size()>0){//债转
            boolean creditRepayAll = (creditAmountp.compareTo(new BigDecimal("0.00"))==1) && (creditAmountp.compareTo(recoverCapitalp)==0);//是否是全部债转
            BigDecimal assignPay  = new BigDecimal("0.00");//所有债转已还利息总和（结算剩余部分用）
            //填充所有债转信息
            for (CreditRepayVO creditRepay : creditRepayListPlan) {
                if(!creditRepayAll) {//部分债转
                    assignPay = assignPay.add(creditRepay.getAssignRepayInterest());
                }
                FddGenerateContractBean bean = getFddGenerateContractBean(borrow_nid,repay_period,repayOrgUserId,creditRepay.getAssignNid()+"-"+repay_period,creditRepay.getCreditUserId(),5,2);
                JSONObject paramter = getAllcreditParamter(creditRepay,bean,borrow);
                bean.setParamter(paramter);
                bean.setTeString(DF);
                // 法大大生成合同
                try {
                    // 获取用户出借协议记录
                    TenderAgreementVO tenderAgreement = amTradeClient.selectTenderAgreement("DF-"+repay_period+"-"+creditRepay.getAssignNid()+"-"+repay_period);
                    // 签署成功(status = 2)
                    if (tenderAgreement != null && tenderAgreement.getStatus() == 2) {
                        accedeListService.updateSaveSignInfo(tenderAgreement, borrow_nid, FddGenerateContractConstant.PROTOCOL_TYPE_TENDER, borrow.getInstCode());
                    } else {
                        commonProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC, MQConstant.FDD_GENERATE_CONTRACT_TAG, UUID.randomUUID().toString(), bean));
                    }
                } catch (MQException e) {
                    logger.error("法大大合同生成MQ发送失败！");
                }
                //this.rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_GENERATE_CONTRACT, JSONObject.toJSONString(bean));
                agreements++;
            }
            BigDecimal assignCapital = recoverCapital.subtract(creditAmount);
            assignPay = borrowRecover.getRecoverInterestYes().subtract(assignPay);
            if((!assignCapital.equals(new BigDecimal("0.00"))) && (!assignPay.equals(new BigDecimal("0.00")))){
                /***************计算剩余部分********/
                //垫付协议申请-协议生成详情
                FddGenerateContractBean bean = getFddGenerateContractBean(borrow_nid,repay_period,repayOrgUserId,nid+"-"+repay_period,recoverUserId,5,2);
                //recoverCapital.subtract(creditAmount);
                assignPay = borrowRecover.getRecoverInterestYes().subtract(assignPay);
                borrowRecover.setRecoverCapital(assignCapital);//剩余部分已还本金
                borrowRecover.setRecoverInterestYes(assignPay);//剩余部分已还利息
                JSONObject paramter = getNocreditParamterPlan(borrowRecover,bean,borrow);
                bean.setParamter(paramter); bean.setTeString(DF);
                // 法大大生成合同
                try {
                    // 获取用户出借协议记录
                    TenderAgreementVO tenderAgreement = amTradeClient.selectTenderAgreement("DF-"+repay_period+"-"+nid+"-"+repay_period);
                    // 签署成功(status = 2)
                    if (tenderAgreement != null && tenderAgreement.getStatus() == 2) {
                        accedeListService.updateSaveSignInfo(tenderAgreement, borrow_nid, FddGenerateContractConstant.PROTOCOL_TYPE_TENDER, borrow.getInstCode());
                        commonProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC, MQConstant.FDD_GENERATE_CONTRACT_TAG, UUID.randomUUID().toString(), bean));
                    }
                } catch (MQException e) {
                    logger.error("法大大合同生成MQ发送失败！");
                }
                //this.rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_GENERATE_CONTRACT, JSONObject.toJSONString(bean));
                agreements++;

            }
        }else {//非债转
            //垫付协议申请-协议生成详情
            FddGenerateContractBean bean = getFddGenerateContractBean(borrow_nid,repay_period,repayOrgUserId,nid+"-"+repay_period,recoverUserId,5,2);
            JSONObject paramter = getNocreditParamterPlan(borrowRecover,bean,borrow);
            bean.setParamter(paramter); bean.setTeString(DF);
            // 法大大生成合同
            try {
                // 获取用户出借协议记录
                TenderAgreementVO tenderAgreement = amTradeClient.selectTenderAgreement("DF-"+repay_period+"-"+nid+"-"+repay_period);
                // 签署成功(status = 2)
                if (tenderAgreement != null && tenderAgreement.getStatus() == 2) {
                    accedeListService.updateSaveSignInfo(tenderAgreement, borrow_nid, FddGenerateContractConstant.PROTOCOL_TYPE_TENDER, borrow.getInstCode());
                } else {
                    commonProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC, MQConstant.FDD_GENERATE_CONTRACT_TAG, UUID.randomUUID().toString(), bean));
                }
            } catch (MQException e) {
                logger.error("法大大合同生成MQ发送失败！");
            }
            //this.rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_GENERATE_CONTRACT, JSONObject.toJSONString(bean));
            agreements++;
        }
        return  agreements;
    }
    /**
     * 处理分期债转-汇计划
     * @param borrow
     * @param borrowInfo
     * @param borrowRecoverP
     * @param borrowRecover
     */
    private int hjhConvertAndSendPlan(BorrowAndInfoVO borrow,BorrowInfoVO borrowInfo,BorrowRecoverVO borrowRecoverP, BorrowRecoverPlanVO borrowRecover){
        int agreements= 0;
        String borrow_nid = borrow.getBorrowNid();
        //承接人都是担保机构
        int repayOrgUserId = borrowInfo.getRepayOrgUserId();//担保机构用户ID
        //已承接债转本金
        BigDecimal creditAmountp = borrowRecoverP.getCreditAmount();
        //应还本金
        BigDecimal recoverCapitalp = borrowRecoverP.getRecoverCapital();
        //债转投标单号
        String nid = borrowRecoverP.getNid();
        //已承接债转本金-当期
        BigDecimal creditAmount = borrowRecover.getCreditAmount();
        //应还本金-当期
        BigDecimal recoverCapital = borrowRecover.getRecoverCapital();
        //当前期数
        int repay_period = borrowRecover.getRecoverPeriod();
        int recoverUserId= borrowRecover.getUserId();
        List<HjhDebtCreditRepayVO> creditRepayListPlan = this.selectHjhCreditRepay(nid,repay_period);
        if(creditRepayListPlan!=null && creditRepayListPlan.size()>0){//债转

            boolean creditRepayAll = (creditAmountp.compareTo(new BigDecimal("0.00"))==1) && (creditAmountp.compareTo(recoverCapitalp)==0);//是否是全部债转
            BigDecimal assignPay  = new BigDecimal("0.00");//所有债转已还利息总和（结算剩余部分用）
            //填充所有债转信息
            for (HjhDebtCreditRepayVO hjhDebtCreditRepayVO : creditRepayListPlan) {
                if(!creditRepayAll) {//部分债转
                    assignPay = assignPay.add(hjhDebtCreditRepayVO.getRepayInterest()).add(hjhDebtCreditRepayVO.getRepayAdvanceInterest()).add(hjhDebtCreditRepayVO.getRepayLateInterest()).add(hjhDebtCreditRepayVO.getRepayDelayInterest());
                }
                FddGenerateContractBean bean = getFddGenerateContractBean(borrow_nid,repay_period,repayOrgUserId,hjhDebtCreditRepayVO.getUniqueNid()+"-"+repay_period,hjhDebtCreditRepayVO.getCreditUserId(),6,2);
                JSONObject paramter = getAllcreditParamterHjh(hjhDebtCreditRepayVO,bean,borrow);
                bean.setParamter(paramter);
                bean.setTeString(DF);
                // 法大大生成合同
                try {
                    // 获取用户出借协议记录
                    TenderAgreementVO tenderAgreement = amTradeClient.selectTenderAgreement("DF-"+repay_period+"-"+hjhDebtCreditRepayVO.getUniqueNid()+"-"+repay_period);
                    // 签署成功(status = 2)
                    if (tenderAgreement != null && tenderAgreement.getStatus() == 2) {
                        accedeListService.updateSaveSignInfo(tenderAgreement, borrow_nid, FddGenerateContractConstant.PROTOCOL_TYPE_TENDER, borrow.getInstCode());
                    } else {
                        commonProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC, MQConstant.FDD_GENERATE_CONTRACT_TAG, UUID.randomUUID().toString(), bean));
                    }
                } catch (MQException e) {
                    logger.error("法大大合同生成MQ发送失败！");
                }
                //this.rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_GENERATE_CONTRACT, JSONObject.toJSONString(bean));
                agreements++;
            }
            BigDecimal assignCapital = recoverCapital.subtract(creditAmount);
            assignPay = borrowRecover.getRecoverInterestYes().subtract(assignPay);
            if((!assignCapital.equals(new BigDecimal("0.00"))) && (!assignPay.equals(new BigDecimal("0.00")))){
                /***************计算剩余部分********/
                //垫付协议申请-协议生成详情
                FddGenerateContractBean bean = getFddGenerateContractBean(borrow_nid,repay_period,repayOrgUserId,nid+"-"+repay_period,recoverUserId,6,2);
                //recoverCapital.subtract(creditAmount);
                assignPay = borrowRecover.getRecoverInterestYes().subtract(assignPay);
                borrowRecover.setRecoverCapital(assignCapital);//剩余部分已还本金
                borrowRecover.setRecoverInterestYes(assignPay);//剩余部分已还利息
                JSONObject paramter = getNocreditParamterPlan(borrowRecover,bean,borrow);
                bean.setParamter(paramter); bean.setTeString(DF);
                // 法大大生成合同
                try {
                    // 获取用户出借协议记录
                    TenderAgreementVO tenderAgreement = amTradeClient.selectTenderAgreement("DF-"+repay_period+"-"+nid+"-"+repay_period);
                    // 签署成功(status = 2)
                    if (tenderAgreement != null && tenderAgreement.getStatus() == 2) {
                        accedeListService.updateSaveSignInfo(tenderAgreement, borrow_nid, FddGenerateContractConstant.PROTOCOL_TYPE_TENDER, borrow.getInstCode());
                    } else {
                        commonProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC, MQConstant.FDD_GENERATE_CONTRACT_TAG, UUID.randomUUID().toString(), bean));
                    }
                } catch (MQException e) {
                    logger.error("法大大合同生成MQ发送失败！");
                }
                //this.rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_GENERATE_CONTRACT, JSONObject.toJSONString(bean));
                agreements++;

            }
        }else {//非债转
            //垫付协议申请-协议生成详情
            FddGenerateContractBean bean = getFddGenerateContractBean(borrow_nid,repay_period,repayOrgUserId,nid+"-"+repay_period,recoverUserId,6,2);
            JSONObject paramter = getNocreditParamterPlan(borrowRecover,bean,borrow);
            bean.setParamter(paramter); bean.setTeString(DF);
            // 法大大生成合同
            try {
                // 获取用户出借协议记录
                TenderAgreementVO tenderAgreement = amTradeClient.selectTenderAgreement("DF-"+repay_period+"-"+nid+"-"+repay_period);
                // 签署成功(status = 2)
                if (tenderAgreement != null && tenderAgreement.getStatus() == 2) {
                    accedeListService.updateSaveSignInfo(tenderAgreement, borrow_nid, FddGenerateContractConstant.PROTOCOL_TYPE_TENDER, borrow.getInstCode());
                } else {
                    commonProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC, MQConstant.FDD_GENERATE_CONTRACT_TAG, UUID.randomUUID().toString(), bean));
                }
            } catch (MQException e) {
                logger.error("法大大合同生成MQ发送失败！");
            }
            //this.rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_GENERATE_CONTRACT, JSONObject.toJSONString(bean));
            agreements++;
        }
        return  agreements;
    }

    /**
     * 处理不分期债转
     * @param borrow
     * @param borrowInfo
     * @param borrowRecover
     */
    private int convertAndSend(BorrowAndInfoVO borrow,BorrowInfoVO borrowInfo,BorrowRecoverVO borrowRecover){
        int agreements = 0;
        String borrow_nid = borrow.getBorrowNid();
        //承接人都是担保机构
        int repayOrgUserId = borrowInfo.getRepayOrgUserId();//担保机构用户ID
        //已承接债转本金
        BigDecimal creditAmount = borrowRecover.getCreditAmount();
        //应还本金
        BigDecimal recoverCapital = borrowRecover.getRecoverCapital();
        //当前期数
        int repay_period = borrowRecover.getRecoverPeriod();
        //债转投标单号
        String nid = borrowRecover.getNid();
        List<CreditRepayVO> creditRepayList = this.selectCreditRepay(nid,repay_period);
        if(creditRepayList!=null && creditRepayList.size()>0){//债转
            boolean creditRepayAll = (creditAmount.compareTo(new BigDecimal("0.00"))==1) && (creditAmount.compareTo(recoverCapital)==0);//是否是全部债转
            BigDecimal assignPay  = new BigDecimal("0.00");//所有债转已还利息总和（结算剩余部分用）
            //填充所有债转信息
            for (CreditRepayVO creditRepay : creditRepayList) {
                if(!creditRepayAll) {//部分债转
                    assignPay = assignPay.add(creditRepay.getAssignRepayInterest());
                }
                FddGenerateContractBean bean = getFddGenerateContractBean(borrow_nid,repay_period,repayOrgUserId,creditRepay.getAssignNid()+"-"+repay_period,creditRepay.getCreditUserId(),5,2);
                JSONObject paramter = getAllcreditParamter(creditRepay,bean,borrow);
                bean.setParamter(paramter);
                bean.setTeString(DF);
                // 法大大生成合同
                try {
                    // 获取用户出借协议记录
                    TenderAgreementVO tenderAgreement = amTradeClient.selectTenderAgreement("DF-"+repay_period+"-"+creditRepay.getAssignNid()+"-"+repay_period);
                    // 签署成功(status = 2)
                    if (tenderAgreement != null && tenderAgreement.getStatus() == 2) {
                        accedeListService.updateSaveSignInfo(tenderAgreement, borrow_nid, FddGenerateContractConstant.PROTOCOL_TYPE_TENDER, borrow.getInstCode());
                    } else {
                        commonProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC, MQConstant.FDD_GENERATE_CONTRACT_TAG, UUID.randomUUID().toString(), bean));
                    }
                } catch (MQException e) {
                    logger.error("法大大合同生成MQ发送失败！");
                }
                //this.rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_GENERATE_CONTRACT, JSONObject.toJSONString(bean));
                agreements++;
            }
            BigDecimal assignCapital = recoverCapital.subtract(creditAmount);
            assignPay = borrowRecover.getRecoverInterestYes().subtract(assignPay);
            if((!assignCapital.equals(new BigDecimal("0.00"))) && (!assignPay.equals(new BigDecimal("0.00")))){
                /***************计算剩余部分********/
                //垫付协议申请-协议生成详情
                FddGenerateContractBean bean = getFddGenerateContractBean(borrow_nid,repay_period,repayOrgUserId,nid+"-"+repay_period,repayOrgUserId,5,2);
               // recoverCapital.subtract(creditAmount);
                assignPay = borrowRecover.getRecoverInterestYes().subtract(assignPay);
                borrowRecover.setRecoverCapital(assignCapital);//剩余部分已还本金
                borrowRecover.setRecoverInterestYes(assignPay);//剩余部分已还利息
                JSONObject paramter = getNocreditParamter(borrowRecover,bean,borrow);
                bean.setParamter(paramter); bean.setTeString(DF);
                // 法大大生成合同
                try {
                    // 获取用户出借协议记录
                    TenderAgreementVO tenderAgreement = amTradeClient.selectTenderAgreement("DF-"+repay_period+"-"+nid+"-"+repay_period);
                    // 签署成功(status = 2)
                    if (tenderAgreement != null && tenderAgreement.getStatus() == 2) {
                        accedeListService.updateSaveSignInfo(tenderAgreement, borrow_nid, FddGenerateContractConstant.PROTOCOL_TYPE_TENDER, borrow.getInstCode());
                    } else {
                        commonProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC, MQConstant.FDD_GENERATE_CONTRACT_TAG, UUID.randomUUID().toString(), bean));
                    }
                } catch (MQException e) {
                    logger.error("法大大合同生成MQ发送失败！");
                }
                //this.rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_GENERATE_CONTRACT, JSONObject.toJSONString(bean));
                agreements++;

            }
        }else {//非债转
            //垫付协议申请-协议生成详情
            FddGenerateContractBean bean = getFddGenerateContractBean(borrow_nid,repay_period,repayOrgUserId,nid+"-"+repay_period,repayOrgUserId,5,2);
            JSONObject paramter = getNocreditParamter(borrowRecover,bean,borrow);
            bean.setParamter(paramter); bean.setTeString(DF);
            // 法大大生成合同
            try {
                // 获取用户出借协议记录
                TenderAgreementVO tenderAgreement = amTradeClient.selectTenderAgreement("DF-"+repay_period+"-"+nid+"-"+repay_period);
                // 签署成功(status = 2)
                if (tenderAgreement != null && tenderAgreement.getStatus() == 2) {
                    accedeListService.updateSaveSignInfo(tenderAgreement, borrow_nid, FddGenerateContractConstant.PROTOCOL_TYPE_TENDER, borrow.getInstCode());
                } else {
                    commonProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC, MQConstant.FDD_GENERATE_CONTRACT_TAG, UUID.randomUUID().toString(), bean));
                }
            } catch (MQException e) {
                logger.error("法大大合同生成MQ发送失败！");
            }
            //this.rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_GENERATE_CONTRACT, JSONObject.toJSONString(bean));
            agreements++;
        }
        return agreements;
    }

    /**
     * 处理不分期债转-汇计划
     * @param borrow
     * @param borrowInfo
     * @param borrowRecover
     */
    private int hjhConvertAndSend(BorrowAndInfoVO borrow,BorrowInfoVO borrowInfo,BorrowRecoverVO borrowRecover){
        int agreements = 0;
        String borrow_nid = borrow.getBorrowNid();
        //承接人都是担保机构
        int repayOrgUserId = borrowInfo.getRepayOrgUserId();//担保机构用户ID
        //已承接债转本金
        BigDecimal creditAmount = borrowRecover.getCreditAmount();
        //应还本金
        BigDecimal recoverCapital = borrowRecover.getRecoverCapital();
        //当前期数
        int repay_period = borrowRecover.getRecoverPeriod();
        //债转投标单号
        String nid = borrowRecover.getNid();
        List<HjhDebtCreditRepayVO> hjhDebtCreditRepayList = this.selectHjhCreditRepay(nid,repay_period);
        if(hjhDebtCreditRepayList!=null && hjhDebtCreditRepayList.size()>0){//债转
            boolean creditRepayAll = (creditAmount.compareTo(new BigDecimal("0.00"))==1) && (creditAmount.compareTo(recoverCapital)==0);//是否是全部债转
            BigDecimal assignPay  = new BigDecimal("0.00");//所有债转已还利息总和（结算剩余部分用）
            //填充所有债转信息
            for (HjhDebtCreditRepayVO hjhDebtCreditRepayVO : hjhDebtCreditRepayList) {
                if(!creditRepayAll) {//部分债转
                    assignPay = assignPay.add(hjhDebtCreditRepayVO.getRepayInterest()).add(hjhDebtCreditRepayVO.getRepayAdvanceInterest()).add(hjhDebtCreditRepayVO.getRepayLateInterest()).add(hjhDebtCreditRepayVO.getRepayDelayInterest());
                }
                FddGenerateContractBean bean = getFddGenerateContractBean(borrow_nid,repay_period,repayOrgUserId,hjhDebtCreditRepayVO.getUniqueNid()+"-"+repay_period,hjhDebtCreditRepayVO.getCreditUserId(),6,2);
                JSONObject paramter = getAllcreditParamterHjh(hjhDebtCreditRepayVO,bean,borrow);
                bean.setParamter(paramter);
                bean.setTeString(DF);
                // 法大大生成合同
                try {
                    // 获取用户出借协议记录
                    TenderAgreementVO tenderAgreement = amTradeClient.selectTenderAgreement("DF-"+repay_period+"-"+hjhDebtCreditRepayVO.getUniqueNid()+"-"+repay_period);
                    // 签署成功(status = 2)
                    if (tenderAgreement != null && tenderAgreement.getStatus() == 2) {
                        accedeListService.updateSaveSignInfo(tenderAgreement, borrow_nid, FddGenerateContractConstant.PROTOCOL_TYPE_TENDER, borrow.getInstCode());
                    } else {
                        commonProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC, MQConstant.FDD_GENERATE_CONTRACT_TAG, UUID.randomUUID().toString(), bean));
                    }
                } catch (MQException e) {
                    logger.error("法大大合同生成MQ发送失败！");
                }
                //this.rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_GENERATE_CONTRACT, JSONObject.toJSONString(bean));
                agreements++;
            }
            BigDecimal assignCapital = recoverCapital.subtract(creditAmount);
            assignPay = borrowRecover.getRecoverInterestYes().subtract(assignPay);
            if((!assignCapital.equals(new BigDecimal("0.00"))) && (!assignPay.equals(new BigDecimal("0.00")))){
                /***************计算剩余部分********/
                //垫付协议申请-协议生成详情
                FddGenerateContractBean bean = getFddGenerateContractBean(borrow_nid,repay_period,repayOrgUserId,nid+"-"+repay_period,repayOrgUserId,6,2);
               // recoverCapital.subtract(creditAmount);
                assignPay = borrowRecover.getRecoverInterestYes().subtract(assignPay);
                borrowRecover.setRecoverCapital(assignCapital);//剩余部分已还本金
                borrowRecover.setRecoverInterestYes(assignPay);//剩余部分已还利息
                JSONObject paramter = getNocreditParamter(borrowRecover,bean,borrow);
                bean.setParamter(paramter); bean.setTeString(DF);
                // 法大大生成合同
                try {
                    // 获取用户出借协议记录
                    TenderAgreementVO tenderAgreement = amTradeClient.selectTenderAgreement("DF-"+repay_period+"-"+nid+"-"+repay_period);
                    // 签署成功(status = 2)
                    if (tenderAgreement != null && tenderAgreement.getStatus() == 2) {
                        accedeListService.updateSaveSignInfo(tenderAgreement, borrow_nid, FddGenerateContractConstant.PROTOCOL_TYPE_TENDER, borrow.getInstCode());
                    } else {
                        commonProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC, MQConstant.FDD_GENERATE_CONTRACT_TAG, UUID.randomUUID().toString(), bean));
                    }
                } catch (MQException e) {
                    logger.error("法大大合同生成MQ发送失败！");
                }
                //this.rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_GENERATE_CONTRACT, JSONObject.toJSONString(bean));
                agreements++;

            }
        }else {//非债转
            //垫付协议申请-协议生成详情
            FddGenerateContractBean bean = getFddGenerateContractBean(borrow_nid,repay_period,repayOrgUserId,nid+"-"+repay_period,repayOrgUserId,6,2);
            JSONObject paramter = getNocreditParamter(borrowRecover,bean,borrow);
            bean.setParamter(paramter); bean.setTeString(DF);
            // 法大大生成合同
            try {
                // 获取用户出借协议记录
                TenderAgreementVO tenderAgreement = amTradeClient.selectTenderAgreement("DF-"+repay_period+"-"+nid+"-"+repay_period);
                // 签署成功(status = 2)
                if (tenderAgreement != null && tenderAgreement.getStatus() == 2) {
                    //logger.info("-------------------------处理不分期债转-汇计划，非债转签署成功tenderAgreement："+JSONObject.toJSON(tenderAgreement));
                    accedeListService.updateSaveSignInfo(tenderAgreement, borrow_nid, FddGenerateContractConstant.PROTOCOL_TYPE_TENDER, borrow.getInstCode());
                } else {
                    //logger.info("-------------------------处理不分期债转-汇计划，非债转新生成tenderAgreement："+JSONObject.toJSON(tenderAgreement));
                    commonProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC, MQConstant.FDD_GENERATE_CONTRACT_TAG, UUID.randomUUID().toString(), bean));
                }
            } catch (MQException e) {
                logger.error("法大大合同生成MQ发送失败！");
            }
            //this.rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGES_NAME, RabbitMQConstants.ROUTINGKEY_GENERATE_CONTRACT, JSONObject.toJSONString(bean));
            agreements++;
        }
        return  agreements;
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

    /**
     * 查询相应的债转还款记录-汇计划
     * @param nid
     * @param periodNow
     * @return
     */
    private List<HjhDebtCreditRepayVO> selectHjhCreditRepay(String nid, Integer periodNow) {
        return amTradeClient.selectHjhDebtCreditRepayList(nid,periodNow);
    }

    /**
     * 填充所有债转信息
     * @author Zha Daojian
     * @date 2018/8/23 15:04
     * @param borrow_nid, repay_period, repayOrgUserId, contractId, creditUserId, transType, tenderType
     * @return com.hyjf.am.bean.fdd.FddGenerateContractBean
     **/
    private FddGenerateContractBean getFddGenerateContractBean(String borrow_nid, Integer repay_period,Integer repayOrgUserId,String contractId,Integer creditUserId,int transType,int tenderType) {
        FddGenerateContractBean bean = new FddGenerateContractBean();
        //垫付协议申请-协议生成详情
        ApplyAgreementInfoVO applyAgreementInfo = new ApplyAgreementInfoVO();
        applyAgreementInfo.setBorrowNid(borrow_nid);//项目编号
        applyAgreementInfo.setRepayPeriod(repay_period);//期数
        applyAgreementInfo.setContractId("DF-"+repay_period+"-"+contractId);//合同编号
        applyAgreementInfo.setUserId(repayOrgUserId);//出借人(出让人)
        applyAgreementInfo.setCreditUserId(creditUserId+"");//承接人-担保机构
        applyAgreementInfo.setStatus(transType);
        //logger.info("--------------------填充所有债转信息getFddGenerateContractBean:applyAgreementInfo:"+JSONObject.toJSON(applyAgreementInfo));
        ApplyAgreementInfoResponse response = amTradeClient.saveApplyAgreementInfo(applyAgreementInfo);
        //logger.info("--------------------填充所有债转信息getFddGenerateContractBean:ApplyAgreementInfoResponse:"+JSONObject.toJSON(response));
        bean.setBorrowNid(borrow_nid);//标的编号
        bean.setRepayPeriod(repay_period);//期数
        bean.setTransType(transType);//交易类型
        bean.setTenderType(tenderType);//出借类型 0：原始 1：债转 2 :计划
        bean.setTenderUserId(repayOrgUserId);//出借人-承接人（担保机构）
        bean.setCreditUserID(creditUserId);//出讓人
        return bean;
    }

    /**非转让债转参数集合-分期*/
    private JSONObject getNocreditParamterPlan(BorrowRecoverPlanVO borrowRecover,FddGenerateContractBean bean,BorrowAndInfoVO borrow) {
        JSONObject paramter = new JSONObject();

        /** 标的基本数据 */
        String borrowStyle = borrow.getBorrowStyle();// 还款方式
        Integer borrowPeriod = borrow.getBorrowPeriod();
        String borrowDate = "";
        Object creditTerm = paramter.get("creditTerm");
        String recoverYestime = borrowRecover.getRecoverYestime();
        if(StringUtils.isEmpty(recoverYestime)){
            recoverYestime = "0";
        }
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
        }else{//不分期
            recoverTime = borrowRecover.getRecoverTime()+"";//（需要确认数据迁移之后类型）
        }
        creditTerm = creditTerm + "天";
        if(CustomConstants.BORROW_STYLE_END.equals(borrowStyle)
                ||CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)){//到期还本付息的
            /**转让本金为借款本金，转让价款为本息之和。剩余期限为0*/
            // 转让债权本金
            paramter.put("assignCapital", borrowRecover.getRecoverCapital()+"");//已承接债转本金recover_capital
            //转让价款
            paramter.put("assignPay",  borrowRecover.getRecoverCapital().add(borrowRecover.getRecoverInterestYes())+"");//已承接垫付利息recover_interest
            // 债转期限
            paramter.put("creditTerm", 0+"天");

        }else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {//先息后本时

            /**还某一期（非最后一期）转让本金是0，转让价款是该期利息金额。剩余转让期间是债权的剩余期限（应还时间-还款时间）。
             还最后一期转让本金是借款本金，转让价款是借款本金+最后一期利息金额。剩余转让期间为0*/
            if(borrowRecover.getRecoverPeriod()!=borrow.getBorrowPeriod()){//非最后一期
                // 转让债权本金
                paramter.put("assignCapital", 0+"");//已承接债转本金
                //转让价款
                paramter.put("assignPay",  borrowRecover.getRecoverInterestYes()+"");//已承接垫付利息recover_interest
                // 债转期限
                paramter.put("creditTerm", DateUtils.differentDaysByString(recoverTime, borrowRecover.getRecoverYestime())+"天");
            }else{//最后一期
                // 转让债权本金
                paramter.put("assignCapital", borrowRecover.getRecoverCapital()+"");//已承接债转本金
                //转让价款
                paramter.put("assignPay",  borrowRecover.getRecoverCapital().add(borrowRecover.getRecoverInterestYes())+"");//已承接垫付利息recover_interest
                // 债转期限
                paramter.put("creditTerm", 0+"天");
            }

        }else if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)) {//等额本息时
            /**转让期限是原债权的剩余期限，转让本金为本期借款本金，转让价款为本期应还本息之和*/
            // 转让债权本金
            paramter.put("assignCapital", borrowRecover.getRecoverCapital()+"");//已承接债转本金recover_capital
            //转让价款
            paramter.put("assignPay",  borrowRecover.getRecoverCapital().add(borrowRecover.getRecoverInterestYes())+"");//已承接垫付利息recover_interest
            // 债转期限
            //logger.info("---------------------非转让债转参数集合-分期:recoverTime:"+recoverTime);
            //logger.info("---------------------非转让债转参数集合-分期:getRecoverYestime:"+borrowRecover.getRecoverYestime());
            paramter.put("creditTerm", DateUtils.differentDaysByString(recoverTime,borrowRecover.getRecoverYestime()+"")+"天");

        }else{
            // 转让债权本金
            paramter.put("assignCapital", borrowRecover.getRecoverCapital()+"");//已承接债转本金recover_capital
            //转让价款
            paramter.put("assignPay", borrowRecover.getRecoverInterestYes()+"");//已承接垫付利息recover_interest
            // 债转期限
            paramter.put("creditTerm", DateUtils.differentDaysByString(recoverTime,borrowRecover.getRecoverYestime()+"")+"天");
        }
        // 签署时间
        paramter.put("addTime", borrowRecover.getRecoverYestime());
        // 签署时间
        paramter.put("addTime", GetDate.getDateMyTimeInMillisYYYYMMDD(Integer.valueOf(recoverYestime)));
        //转让日期
        paramter.put("creditTime", GetDate.getDateMyTimeInMillisYYYYMMDD(Integer.valueOf(recoverYestime)));

        // 标的编号
        paramter.put("borrowNid", borrow.getBorrowNid());
        //编号
        paramter.put("NID", borrowRecover.getNid());//原始标的出借订单号
        //借款本金总额
        paramter.put("borrowAccount", borrow.getAccount().toString());
        // 借款利率
        paramter.put("borrowApr", borrow.getBorrowApr() + "%");
        // 还款方式
        paramter.put("borrowStyle", this.getBorrowStyle(borrow.getBorrowStyle()));
        // 借款期限
        paramter.put("borrowPeriod", borrowDate);


        // 出让人相关 start-repay_org_user_id
        UserInfoVO creditUsersInfo = amUserClient.findUsersInfoById(borrowRecover.getUserId());

        paramter.put("CreditTruename", creditUsersInfo.getTruename());
        // 出让人身份证号
        paramter.put("CreditIdcard", creditUsersInfo.getIdcard());
        int tenderUserId = bean.getTenderUserId();
        // 承接人用户 start
        UserInfoVO tenderUserInfo = amUserClient.findUsersInfoById(tenderUserId);
        // 获取承接人平台信息
        UserVO tenderUser = amUserClient.getUserByUserId(tenderUserId);
        String tenderTrueName = tenderUserInfo.getTruename();
        String tenderIdCard = tenderUserInfo.getIdcard();
        if(tenderUser.getUserType() == 1){
            CorpOpenAccountRecordVO info = amUserClient.selectCorpOpenAccountRecordByUserId(bean.getTenderUserId()+"");
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
        UserVO borrowUsers = amUserClient.getUserByUserId(borrow.getUserId());
        // 借款人用户名

        paramter.put("BorrowUsername",borrowUsers.getUsername());
        bean.setTenderUserId(tenderUserId);
        bean.setTenderUserName(tenderUser.getUsername());
        bean.setOrdid(borrowRecover.getNid());//承接订单号
        bean.setAssignOrderId(borrowRecover.getNid());
        bean.setCreditNid(borrowRecover.getNid());//债转编号
        bean.setCreditTenderNid(borrowRecover.getNid());//债转原始订单号
        return paramter;
    }
    /**非转让债转参数集合-不分期*/
    public JSONObject getNocreditParamter(BorrowRecoverVO borrowRecover,FddGenerateContractBean bean,BorrowAndInfoVO borrow) {
        JSONObject paramter = new JSONObject();
        /** 标的基本数据 */
        String borrowStyle = borrow.getBorrowStyle();// 还款方式
        Integer borrowPeriod = borrow.getBorrowPeriod();
        String borrowDate = "";
        // 是否分期(true:分期, false:不分期)
        boolean isPlan = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
        String  recoverTime = "0";//还款时间
        if(isPlan){//分期repay_last_time-huiyingdai_borrow
            recoverTime = borrow.getRepayLastTime()+"";
        }else{//不分期
            recoverTime = borrowRecover.getRecoverTime()+"";
        }
        if(CustomConstants.BORROW_STYLE_END.equals(borrowStyle)
                ||CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)){//到期还本付息的
            /**转让本金为借款本金，转让价款为本息之和。剩余期限为0*/
            // 转让债权本金
            paramter.put("assignCapital", borrowRecover.getRecoverCapital()+"");//已承接债转本金recover_capital
            //转让价款
            paramter.put("assignPay",  borrowRecover.getRecoverCapital().add(borrowRecover.getRecoverInterestYes())+"");//已承接垫付利息recover_interest
            // 债转期限
            paramter.put("creditTerm", 0+"天");

        }else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {//先息后本时
            /**还某一期（非最后一期）转让本金是0，转让价款是该期利息金额。剩余转让期间是债权的剩余期限（应还时间-还款时间）。
             还最后一期转让本金是借款本金，转让价款是借款本金+最后一期利息金额。剩余转让期间为0*/
            if(borrowRecover.getRecoverPeriod()!=borrow.getBorrowPeriod()){//非最后一期
                // 转让债权本金
                paramter.put("assignCapital", 0+"");//已承接债转本金
                //转让价款
                paramter.put("assignPay",  borrowRecover.getRecoverInterestYes()+"");//已承接垫付利息recover_interest
                // 债转期限
                paramter.put("creditTerm", DateUtils.differentDaysByString(recoverTime, borrowRecover.getRecoverYestime()+"")+"天");
            }else{//最后一期
                // 转让债权本金
                paramter.put("assignCapital", borrowRecover.getRecoverCapital()+"");//已承接债转本金
                //转让价款
                paramter.put("assignPay",  borrowRecover.getRecoverCapital().add(borrowRecover.getRecoverInterestYes())+"");//已承接垫付利息recover_interest
                // 债转期限
                paramter.put("creditTerm", 0+"天");
            }

        }else if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)) {//等额本息时
            /**转让期限是原债权的剩余期限，转让本金为本期借款本金，转让价款为本期应还本息之和*/
            // 转让债权本金
            paramter.put("assignCapital", borrowRecover.getRecoverCapital()+"");//已承接债转本金recover_capital
            //转让价款
            paramter.put("assignPay",  borrowRecover.getRecoverCapital().add(borrowRecover.getRecoverInterestYes())+"");//已承接垫付利息recover_interest
            // 债转期限
            paramter.put("creditTerm", DateUtils.differentDaysByString(recoverTime,borrowRecover.getRecoverYestime()+"")+"天");

        }else{
            // 转让债权本金
            paramter.put("assignCapital", borrowRecover.getRecoverCapital()+"");//已承接债转本金recover_capital
            //转让价款
            paramter.put("assignPay", borrowRecover.getRecoverInterestYes()+"");//已承接垫付利息recover_interest
            // 债转期限
            //logger.info("--------------------非转让债转参数集合-不分期:recoverTime:"+recoverTime);
            //logger.info("--------------------非转让债转参数集合-不分期:getRecoverYestime:"+borrowRecover.getRecoverYestime());
            paramter.put("creditTerm", DateUtils.differentDaysByString(recoverTime,borrowRecover.getRecoverYestime()+"")+"天");
        }
        String recoverYestime = borrowRecover.getRecoverYestime()+"";
        if(StringUtils.isEmpty(recoverYestime)){
            recoverYestime = "0";
        }
        // 签署时间
        paramter.put("addTime", GetDate.getDateMyTimeInMillisYYYYMMDD(Integer.valueOf(recoverYestime)));
        //转让日期
        paramter.put("creditTime", GetDate.getDateMyTimeInMillisYYYYMMDD(Integer.valueOf(recoverYestime)));

        // 是否月标(true:月标, false:天标)
        boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_END.equals(borrowStyle);
        if(isMonth){//月表
            borrowDate = borrowPeriod + "个月";
        }else{
            borrowDate = borrowPeriod + "天";
        }

        // 标的编号
        paramter.put("borrowNid", borrow.getBorrowNid());
        //编号
        paramter.put("NID", borrowRecover.getNid());//原始标的出借订单号
        //借款本金总额
        paramter.put("borrowAccount", borrow.getAccount().toString());
        // 借款利率
        paramter.put("borrowApr", borrow.getBorrowApr() + "%");
        // 还款方式
        paramter.put("borrowStyle", this.getBorrowStyle(borrow.getBorrowStyle()));
        // 借款期限
        paramter.put("borrowPeriod", borrowDate);

        // 出让人相关 start-repay_org_user_id
        UserInfoVO creditUsersInfo = amUserClient.findUsersInfoById(borrowRecover.getUserId());
        paramter.put("CreditTruename", creditUsersInfo.getTruename());
        // 出让人身份证号
        paramter.put("CreditIdcard", creditUsersInfo.getIdcard());
        int tenderUserId = bean.getTenderUserId();
        // 承接人用户 start
        UserInfoVO tenderUserInfo = amUserClient.findUsersInfoById(tenderUserId);
        // 获取承接人平台信息
        UserVO tenderUser = amUserClient.getUserByUserId(tenderUserId);
        String tenderTrueName = tenderUserInfo.getTruename();
        String tenderIdCard = tenderUserInfo.getIdcard();
        if(tenderUser!=null && tenderUser.getUserType() == 1){
            CorpOpenAccountRecordVO info = amUserClient.selectCorpOpenAccountRecordByUserId(bean.getTenderUserId()+"");
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
        UserVO borrowUsers = amUserClient.getUserByUserId(borrow.getUserId());
        // 借款人用户名

        paramter.put("BorrowUsername",borrowUsers.getUsername());
        bean.setTenderUserId(tenderUserId);
        bean.setTenderUserName(tenderUser.getUsername());
        bean.setOrdid(borrowRecover.getNid());//承接订单号
        bean.setAssignOrderId(borrowRecover.getNid());
        bean.setCreditNid(borrowRecover.getNid());//债转编号
        bean.setCreditTenderNid(borrowRecover.getNid());//债转原始订单号
        return paramter;
    }

    /**
     * 汇计划-转让债转参数集合-不分期
     */
    public JSONObject getAllcreditParamterHjh(HjhDebtCreditRepayVO hjhDebtCreditRepay,FddGenerateContractBean bean,BorrowAndInfoVO borrow) {
        JSONObject paramter = new JSONObject();
        /** 标的基本数据 */
        String borrowStyle = borrow.getBorrowStyle();// 还款方式
        Integer borrowPeriod = borrow.getBorrowPeriod();
        String borrowDate = "";
        // 是否分期(true:分期, false:不分期)
        boolean isPlan = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
        String  recoverTime = "0";//还款时间
        if(isPlan){//分期repay_last_time-huiyingdai_borrow
            //logger.info("---------------------汇计划-转让债转参数集合-不分期等额本息时:分期repay_last_time-huiyingdai_borrow:"+recoverTime);
            recoverTime = borrow.getRepayLastTime()+"";
        }else{//
            //logger.info("---------------------汇计划-转让债转参数集合-不分期等额本息时:不分期:"+recoverTime);
            recoverTime =hjhDebtCreditRepay.getAssignRepayEndTime()+"";
        }
        if(CustomConstants.BORROW_STYLE_END.equals(borrowStyle)
                ||CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)){//到期还本付息的
            /**转让本金为借款本金，转让价款为本息之和。剩余期限为0*/
            // 转让债权本金
            paramter.put("assignCapital", hjhDebtCreditRepay.getRepayCapital()+"");//已承接债转本金recover_capital
            //转让价款
            paramter.put("assignPay",  hjhDebtCreditRepay.getRepayCapital().add(hjhDebtCreditRepay.getRepayInterest()).add(hjhDebtCreditRepay.getRepayAdvanceInterest()).add(hjhDebtCreditRepay.getRepayLateInterest()).add(hjhDebtCreditRepay.getRepayDelayInterest())+"");//已承接垫付利息recover_interest
            // 债转期限
            paramter.put("creditTerm", 0+"天");

        }else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {//先息后本时
            /**还某一期（非最后一期）转让本金是0，转让价款是该期利息金额。剩余转让期间是债权的剩余期限（应还时间-还款时间）。
             还最后一期转让本金是借款本金，转让价款是借款本金+最后一期利息金额。剩余转让期间为0*/
            if(!hjhDebtCreditRepay.getRepayPeriod().equals(borrow.getBorrowPeriod())){//非最后一期
                // 转让债权本金
                paramter.put("assignCapital", 0+"");//已承接债转本金
                //转让价款
                paramter.put("assignPay",  hjhDebtCreditRepay.getRepayInterest().add(hjhDebtCreditRepay.getRepayAdvanceInterest()).add(hjhDebtCreditRepay.getRepayLateInterest()).add(hjhDebtCreditRepay.getRepayDelayInterest())+"");//已承接垫付利息recover_interest
                // 债转期限
                paramter.put("creditTerm", DateUtils.differentDaysByString(recoverTime, hjhDebtCreditRepay.getAssignRepayYesTime()+"")+"天");
            }else{//最后一期
                // 转让债权本金
                paramter.put("assignCapital", hjhDebtCreditRepay.getRepayCapital()+"");//已承接债转本金
                //转让价款
                paramter.put("assignPay",  hjhDebtCreditRepay.getRepayCapital().add(hjhDebtCreditRepay.getRepayInterest()).add(hjhDebtCreditRepay.getRepayAdvanceInterest()).add(hjhDebtCreditRepay.getRepayLateInterest()).add(hjhDebtCreditRepay.getRepayDelayInterest())+"");//已承接垫付利息recover_interest
                // 债转期限
                paramter.put("creditTerm", 0+"天");
            }

        }else if (CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)) {//等额本息时
            /**转让期限是原债权的剩余期限，转让本金为本期借款本金，转让价款为本期应还本息之和*/
            // 转让债权本金
            paramter.put("assignCapital", hjhDebtCreditRepay.getRepayCapital()+"");//已承接债转本金recover_capital
            //转让价款
            paramter.put("assignPay",  hjhDebtCreditRepay.getRepayCapital().add(hjhDebtCreditRepay.getRepayInterest()).add(hjhDebtCreditRepay.getRepayAdvanceInterest()).add(hjhDebtCreditRepay.getRepayLateInterest()).add(hjhDebtCreditRepay.getRepayDelayInterest())+"");//已承接垫付利息recover_interest
            // 债转期限
            //logger.info("---------------------汇计划-转让债转参数集合-不分期等额本息时:recoverTime:"+recoverTime);
            //logger.info("---------------------汇计划-转让债转参数集合-不分期等额本息时:getAssignRepayYesTime:"+hjhDebtCreditRepay.getAssignRepayYesTime());
            paramter.put("creditTerm", DateUtils.differentDaysByString(recoverTime,hjhDebtCreditRepay.getAssignRepayYesTime()+"")+"天");

        }else{
            // 转让债权本金
            paramter.put("assignCapital", hjhDebtCreditRepay.getRepayCapital()+"");//已承接债转本金recover_capital
            //转让价款
            paramter.put("assignPay", hjhDebtCreditRepay.getRepayInterest().add(hjhDebtCreditRepay.getRepayAdvanceInterest()).add(hjhDebtCreditRepay.getRepayLateInterest()).add(hjhDebtCreditRepay.getRepayDelayInterest())+"");//已承接垫付利息recover_interest
            // 债转期限
            paramter.put("creditTerm", DateUtils.differentDaysByString(recoverTime,hjhDebtCreditRepay.getAssignRepayYesTime()+"")+"天");
        }
        // 签署时间
        paramter.put("addTime", GetDate.getDateMyTimeInMillisYYYYMMDD(hjhDebtCreditRepay.getAssignRepayYesTime()));
        //转让日期
        paramter.put("creditTime", GetDate.getDateMyTimeInMillisYYYYMMDD(hjhDebtCreditRepay.getAssignRepayYesTime()));

        // 是否月标(true:月标, false:天标)
        boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_END.equals(borrowStyle);
        if(isMonth){//月表
            borrowDate = borrowPeriod + "个月";
        }else{
            borrowDate = borrowPeriod + "天";
        }

        // 标的编号
        paramter.put("borrowNid", borrow.getBorrowNid());
        //编号
        paramter.put("NID", hjhDebtCreditRepay.getUniqueNid());//原始标的出借订单号
        //借款本金总额
        paramter.put("borrowAccount", borrow.getAccount().toString());
        // 借款利率
        paramter.put("borrowApr", borrow.getBorrowApr() + "%");
        // 还款方式
        paramter.put("borrowStyle", this.getBorrowStyle(borrow.getBorrowStyle()));
        // 借款期限
        paramter.put("borrowPeriod", borrowDate);


        // 出让人相关 start-repay_org_user_id


        UserInfoVO creditUsersInfo = amUserClient.findUsersInfoById(hjhDebtCreditRepay.getUserId());
        paramter.put("CreditTruename", creditUsersInfo.getTruename());
        // 出让人身份证号
        paramter.put("CreditIdcard", creditUsersInfo.getIdcard());
        int tenderUserId = bean.getTenderUserId();
        // 承接人用户 start
        UserInfoVO tenderUserInfo = amUserClient.findUsersInfoById(tenderUserId);
        // 获取承接人平台信息
        UserVO tenderUser = amUserClient.getUserByUserId(tenderUserId);
        String tenderTrueName = tenderUserInfo.getTruename();
        String tenderIdCard = tenderUserInfo.getIdcard();
        if(tenderUser.getUserType() == 1){
            CorpOpenAccountRecordVO info = amUserClient.selectCorpOpenAccountRecordByUserId(bean.getTenderUserId()+"");
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
        UserVO borrowUsers = amUserClient.getUserByUserId(borrow.getUserId());
        // 借款人用户名

        paramter.put("BorrowUsername",borrowUsers.getUsername());
        bean.setTenderUserId(tenderUserId);
        bean.setTenderUserName(tenderUser.getUsername());
        bean.setOrdid(hjhDebtCreditRepay.getUniqueNid());//承接订单号
        bean.setAssignOrderId(hjhDebtCreditRepay.getUniqueNid());
        bean.setCreditNid(hjhDebtCreditRepay.getCreditNid());//债转编号
        bean.setCreditTenderNid(hjhDebtCreditRepay.getSellOrderId());//债转原始订单号
        return paramter;
    }

    /**转让债转参数集合*/
    private JSONObject getAllcreditParamter(CreditRepayVO creditRepay,FddGenerateContractBean bean,BorrowAndInfoVO borrow) {
        JSONObject paramter = new JSONObject();
        /** 标的基本数据 */
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
            /*转让本金为借款本金，转让价款为本息之和。剩余期限为0*/
            // 转让债权本金
            paramter.put("assignCapital", creditRepay.getAssignRepayCapital()+"");//已承接债转本金recover_capital
            //转让价款
            paramter.put("assignPay",  creditRepay.getAssignRepayCapital().add(creditRepay.getAssignRepayInterest())+"");//已承接垫付利息recover_interest
            // 债转期限
            paramter.put("creditTerm", 0+"天");

        }else if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle)) {//先息后本时
            /**还某一期（非最后一期）转让本金是0，转让价款是该期利息金额。剩余转让期间是债权的剩余期限（应还时间-还款时间）。
             还最后一期转让本金是借款本金，转让价款是借款本金+最后一期利息金额。剩余转让期间为0 */
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
            //logger.info("---------------------转让债转参数集合等额本息时:recoverTime:"+recoverTime);
            //logger.info("---------------------转让债转参数集合等额本息时:getAssignRepayYesTime:"+creditRepay.getAssignRepayYesTime());
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
        paramter.put("NID", creditRepay.getAssignNid());
        //借款本金总额
        paramter.put("borrowAccount", borrow.getAccount().toString());
        // 借款利率
        paramter.put("borrowApr", borrow.getBorrowApr() + "%");
        // 还款方式
        paramter.put("borrowStyle", this.getBorrowStyle(borrow.getBorrowStyle()));
        // 借款期限
        paramter.put("borrowPeriod", borrowDate);
        // 出让人相关 start-repay_org_user_id
        UserInfoVO creditUsersInfo = amUserClient.findUsersInfoById(creditRepay.getUserId());
        paramter.put("CreditTruename", creditUsersInfo.getTruename());
        // 出让人身份证号
        paramter.put("CreditIdcard", creditUsersInfo.getIdcard());
        int tenderUserId = bean.getTenderUserId();
        // 承接人用户 start
        UserInfoVO tenderUserInfo = amUserClient.findUsersInfoById(tenderUserId);
        // 获取承接人平台信息
        UserVO tenderUser = amUserClient.getUserByUserId(tenderUserId);
        String tenderTrueName = tenderUserInfo.getTruename();
        String tenderIdCard = tenderUserInfo.getIdcard();
        if(tenderUser.getUserType() == 1){
            CorpOpenAccountRecordVO info = amUserClient.selectCorpOpenAccountRecordByUserId(bean.getTenderUserId()+"");
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
        UserVO borrowUsers = amUserClient.getUserByUserId(borrow.getUserId());
        // 借款人用户名

        paramter.put("BorrowUsername",borrowUsers.getUsername());
        bean.setTenderUserId(tenderUserId);
        bean.setTenderUserName(tenderUser.getUsername());
        bean.setOrdid(creditRepay.getAssignNid());//承接订单号
        bean.setAssignOrderId(creditRepay.getAssignNid());
        bean.setCreditNid(creditRepay.getCreditNid());//债转编号
        bean.setCreditTenderNid(creditRepay.getCreditTenderNid());//原始出借订单号
        //logger.info("-------------------------处理不分期债转，填充所有债转信息bean2："+JSONObject.toJSON(bean));
        return paramter;
    }


    /**
     * 获取还款方式
     * @param borrowStyle
     * @return
     */
    private  String  getBorrowStyle(String borrowStyle){
        List<BorrowStyleVO> BorrowStyleList =  amTradeClient.findBorrowStyleList();
        for (BorrowStyleVO borrowStyleVO:BorrowStyleList) {
            if(borrowStyle.equals(borrowStyleVO.getNid())){
                return borrowStyleVO.getName();
            }
        }
        return "";
    }

    /**
     * 下载文件签署
     * @param response
     * @return
     */
    public void downloadAction(DownloadAgreementRequest requestBean,HttpServletResponse response) {
        //logger.info("--------------------下载文件签署request:"+JSONObject.toJSON(requestBean));
        String status = requestBean.getStatus();//1:脱敏，0：原始
        String repayPeriod = "DF-"+requestBean.getRepayPeriod()+"-";
        requestBean.setRepayPeriod(repayPeriod);
        List<TenderAgreementVO> tenderAgreementsAss= amTradeClient.selectLikeByExample(requestBean);//债转协议
        //logger.info("--------------------下载文件签署request:"+JSONObject.toJSON(requestBean));
        //logger.info("下载文件签署。。。。tenderAgreementsAss:"+JSONObject.toJSON(tenderAgreementsAss));
        //输出文件集合
        List<File> files = new ArrayList<File>();
        if (CollectionUtils.isNotEmpty(tenderAgreementsAss)){
            for (TenderAgreementVO tenderAgreement : tenderAgreementsAss) {
                if(tenderAgreementsAss!=null && tenderAgreementsAss.size()>0){
                    if("1".equals(status)){
                        //logger.info("--------------------下载文件签署,脱敏");
                        File file = createFaddPDFImgFile(tenderAgreement);
                        //logger.info("--------------------下载文件签署,脱敏file:"+file);
                        if(file!=null){
                            files.add(file);
                        }
                    }else {
                        //logger.info("--------------------下载文件签署，原始");
                        if(StringUtils.isNotBlank(tenderAgreement.getDownloadUrl())){
                            try {
                                File filePdf = FileUtil.getFile(tenderAgreement.getDownloadUrl(),tenderAgreement.getTenderNid()+".pdf");
                                //logger.info("--------------------下载文件签署，原始filePdf:"+filePdf);
                                if(filePdf!=null){
                                    files.add(filePdf);
                                }
                            } catch (IOException e) {
                                //logger.info("--------------------下载文件签署，原始filePdf失败");
                            }//债转协议
                        }
                    }
                }
            }
        }else {
            logger.error(this.getClass().getName(), "searchTenderToCreditDetail", "下载失败，请稍后重试。。。。");
        }

        if(files!=null && files.size()>0){
            //logger.info("--------------------下载文件签署，打压缩包files："+JSONObject.toJSON(files));
           ZIPGenerator.generateZip(response, files, repayPeriod);
            //logger.info("searchTenderToCreditDetail下载成功");
        }else{
            logger.error("searchTenderToCreditDetail下载失败，请稍后重试。。。。");

        }
    }
    /**
     * 下载法大大协议 __垫付
     *
     * @param tenderAgreement
     * 返回 0:下载成功；1:下载失败；2:没有生成法大大合同记录
     */
    public File createFaddPDFImgFile(TenderAgreementVO tenderAgreement) {
        SFTPParameter para = new SFTPParameter() ;

        String ftpIP = systemConfig.getFtpIp();
        String port = systemConfig.getFtpPort();
        String basePath = systemConfig.getFtpBasePath();
        String password = systemConfig.getFtpPassword();
        String username = systemConfig.getFtpUsername();
        String domain = systemConfig.getFtpDomain();
        String basePathImage = systemConfig.getHyjfFtpBasepathImg();
        String basePathPdf = systemConfig.getHyjfFtpBasepathPdf();

        para.hostName = ftpIP;//ftp服务器地址
        para.userName = username;//ftp服务器用户名
        para.passWord = password;//ftp服务器密码
        para.port = Integer.valueOf(port);//ftp服务器端口
        para.fileName=tenderAgreement.getTenderNid();
//        para.downloadPath =basePathImage;//ftp服务器文件目录creditUserId
        para.savePath = "/pdf_tem/pdf/" + tenderAgreement.getTenderNid();
        String imgUrl = tenderAgreement.getImgUrl();
        String pdfUrl = tenderAgreement.getPdfUrl();
        //logger.info("下载文件签署。。。。imgUrl:"+imgUrl);
        //logger.info("下载文件签署。。。。pdfUrl:"+pdfUrl);
        if(org.apache.commons.lang.StringUtils.isNotBlank(pdfUrl)){
            //获取文件目录
            int index = pdfUrl.lastIndexOf("/");
            String pdfPath = pdfUrl.substring(0,index);
            //文件名称
            String pdfName = pdfUrl.substring(index+1);
            para.downloadPath = basePathPdf + "/" + pdfPath;
            para.sftpKeyFile = pdfName;
            //logger.info("下载文件签署。。pdfUrl。。 para.downloadPath:"+ para.downloadPath);
            //logger.info("下载文件签署。。pdfUrl。。para.sftpKeyFile:"+para.sftpKeyFile);
        }else if(org.apache.commons.lang.StringUtils.isNotBlank(imgUrl)){
            int index = imgUrl.lastIndexOf("/");
            String imgPath = imgUrl.substring(0,index);
            //文件名称
            String imgName = imgUrl.substring(index+1);
            para.downloadPath = "/" + basePathImage + "/" + imgPath;
            para.sftpKeyFile = imgName;
            //logger.info("下载文件签署。。imgUrl。。 para.downloadPath:"+ para.downloadPath);
            //logger.info("下载文件签署。。imgUrl。。para.sftpKeyFile:"+para.sftpKeyFile);
        }else{
            //logger.info( "下载文件签署。。imgUrl。。para.sftpKeyFile:null");
            return null;
        }
        File file =  FavFTPUtil.downloadDirectory(para);
        return file;
    }

}
