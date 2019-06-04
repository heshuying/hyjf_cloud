package com.hyjf.cs.trade.service.projectlist.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.BorrowCreditRequest;
import com.hyjf.am.resquest.trade.CreditTenderRequest;
import com.hyjf.am.resquest.trade.HjhDebtCreditRequest;
import com.hyjf.am.resquest.trade.HjhDebtCreditTenderRequest;
import com.hyjf.am.vo.admin.BorrowCustomizeVO;
import com.hyjf.am.vo.admin.WebProjectRepayListCustomizeVO;
import com.hyjf.am.vo.admin.WebUserInvestListCustomizeVO;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.borrow.DebtBorrowCustomizeVO;
import com.hyjf.am.vo.trade.borrow.RightBorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.file.FavFTPUtil;
import com.hyjf.common.file.FileUtil;
import com.hyjf.common.file.SFTPParameter;
import com.hyjf.common.file.ZIPGenerator;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.DateUtils;
import com.hyjf.common.util.calculate.DuePrincipalAndInterestUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.bean.CreditAssignedBean;
import com.hyjf.cs.trade.bean.ProtocolRequest;
import com.hyjf.cs.trade.bean.repay.ProjectRepayListBean;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.controller.web.agreement.CreateAgreementController;
import com.hyjf.cs.trade.service.projectlist.WebProtocolService;
import com.hyjf.cs.trade.util.PdfGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class WebProtocolServiceImpl implements WebProtocolService {

    private static Logger logger = LoggerFactory.getLogger(WebProtocolServiceImpl.class);

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private AmUserClient amUserClient;

    //初始化放款/承接时间(大于2018年3月28号法大大上线时间)
    private static final int ADD_TIME = 1922195200;

    //放款/承接时间(2018-3-28法大大上线时间）
    private static final int ADD_TIME328 = 1522195200;

    //放款/承接时间(2018-3-28法大大上线时间）
    private static final String ADD_TIME328_STRING = "2018-03-28";


    /**
     * 下载脱敏后居间服务借款协议（原始标的）_计划出借人
     * @author zhangyk
     * @date 2018/10/18 11:34
     */
    @Override
    public File creditPaymentPlan(ProtocolRequest form, Integer userId, HttpServletRequest request, HttpServletResponse response) {
        logger.info(">>>>>>>>>>>>creditPaymentPlan协议下载开始<<<<<<<<<<<");
        CheckUtil.check(StringUtils.isNotBlank(form.getBorrowNid()),MsgEnum.ERR_OBJECT_REQUIRED, "借款编号");
        Map<String,Object> param = new HashMap<>();
        String borrowNid = form.getBorrowNid();
        String random = form.getRandom();
        if (StringUtils.isNotBlank(random) && userId == null){
            userId = Integer.valueOf(random);
        }
        if (userId == null){
            logger.info(">>>>>>> random(userId) is null  exit <<<<<<<<");
            return null;
        }

        String nid = form.getNid();
        param.put("borrowNidSrch",borrowNid);
        List<BorrowListVO> recordList1 = amTradeClient.searchBorrowList4Protocol(param);
        List<File> files = new ArrayList<File>();
        TenderAgreementVO tenderAgreement = new TenderAgreementVO();
        List<TenderAgreementVO> tenderAgreementsNid = amTradeClient.selectTenderAgreementByNid(nid);//居间协议
        /*******************************下载法大大合同************************************/
        //下载法大大协议--居间
        if(tenderAgreementsNid!=null && tenderAgreementsNid.size()>0){

            tenderAgreement = tenderAgreementsNid.get(0);
            if(tenderAgreement!=null){
                logger.info("*******居间协议部分****");
                if (recordList1.size() >0){
                    files = createFaddPDFImgFile(files,tenderAgreement);//下载脱敏
                }
                if(files!=null && files.size()>0){
                    ZIPGenerator.generateZip(response, files, nid);
                }
                return null;
            }
        }
        if("13".equals(form.getProjectType())){
            logger.info("form.getProjectType() = 13");
            // 融通宝静态打印 暂时注释掉
        }else{
            // 判断是否是汇添金专属标的导出
            if (recordList1.size() != 1){
                Map<String,Object> contents = new HashMap<>();

                List<DebtBorrowCustomizeVO> recordList = amTradeClient.searchDebtBorrowList4Protocol(param);
                if (recordList.size() != 1) {
                    logger.error("标的信息异常[recordList.size = {}]（0条或者大于1条信息）,下载汇盈金服互联网金融服务平台居间服务协议PDF失败。",recordList.size());
                    return null;
                }
                /**zdj
                 * 1.2018年3月28号以后出借（放款时间/承接时间为准）生成的协议(法大大签章协议）如果协议状态不是"下载成功"时 点击下载按钮提示“协议生成中”。
                 * 2.2018年3月28号以前出借（放款时间/承接时间为准）生成的协议(CFCA协议）点击下载CFCA协议。
                 * 3.智投中承接债转，如果债转协议中有2018-3-28之前的，2018-3-28之前承接的下载CFCA债转协议，2018-3-28之后承接的下载法大大债转协议。
                 */
                //根据订单号获取用户放款信息
                //放款记录创建时间（放款时间）
                String addTime = recordList.get(0).getRecoverLastTime();
                if (DateUtils.compareDateToString(ADD_TIME328_STRING,addTime)) {
                    logger.error("createAgreementPDF 导出PDF文件（汇盈金服互联网金融服务平台居间服务协议）,协议未生成");
                    return  null;
                }
                contents.put("borrowNid", borrowNid);
                contents.put("nid", nid);
                // 借款人用户名
                int userIds = recordList.get(0).getUserId();
                UserInfoVO userInfo = amUserClient.findUserInfoById(userIds);
                contents.put("borrowUsername", userInfo.getTruename());
                contents.put("idCard", userInfo.getIdcard());
                System.out.println("333------------------------idCard:"+userInfo.getIdcard());
                contents.put("recoverLastDay", recordList.get(0).getRecoverLastDay());// 最后一笔的放款完成时间
                if (org.apache.commons.lang.StringUtils.isNotBlank(recordList.get(0).getRecoverLastTime())) {
                    // 最后一笔的放款完成时间 (协议签订日期)
                    contents.put("recoverTime", recordList.get(0).getRecoverLastTime());
                } else {
                    // 设置为满标时间
                    contents.put("recoverTime", recordList.get(0).getReverifyTime());
                }
                form.setUserId(userId.toString());
                // 用户出借列表
                List<WebUserInvestListCustomizeVO> tzList = planInfoSelectUserInvestList(form,0,100);
                if (tzList != null && tzList.size() > 0) {
                    contents.put("userInvest", tzList.get(0));
                }

                // 如果是分期还款，查询分期信息
                String borrowStyle = recordList.get(0).getBorrowStyle();// 还款模式
                if (borrowStyle != null) {
                    if ("month".equals(borrowStyle) || "principal".equals(borrowStyle)
                            || "endmonth".equals(borrowStyle)) {
                        Map<String,Object> bean = new HashMap<>();
                        bean.put("userId",userId);
                        bean.put("borrowNid",borrowNid);
                        bean.put("nid",nid);
                        int recordTotal = planInfoCountProjectRepayPlanRecordTotal(bean);
                        Page page = Page.initPage(1,10);
                        if (recordTotal > 0) {
                            List<WebProjectRepayListCustomizeVO> fqList = selectProjectRepayPlanList(
                                    form,page.getOffset(), page.getLimit());
                            contents.put("paginator", page);
                            contents.put("repayList", fqList);
                        } else {
                            contents.put("paginator", page);
                            contents.put("repayList", "");
                        }
                    }
                }

                // 导出PDF文件
                try {

                    String flag = form.getFlag();
                    if(flag!=null && flag=="1"){
                        File file  =  PdfGenerator.generatePdfFile(request, response, form.getBorrowNid() + "_" + form.getNid() + ".pdf",
                                CustomConstants.TENDER_CONTRACT, contents);
                        return file;
                    }else {
                        PdfGenerator.generatePdf(request, response, form.getBorrowNid() + "_" + form.getNid() + ".pdf",
                                CustomConstants.TENDER_CONTRACT, contents);
                    }

                } catch (Exception e) {
                    logger.error("createAgreementPDF 导出PDF文件（汇盈金服互联网金融服务平台居间服务协议）", e);
                }
            }else{

                List<BorrowListVO> recordList = recordList1;
                Map<String, Object> contents = new HashMap<String, Object>();
                contents.put("record", recordList.get(0));
                contents.put("borrowNid", borrowNid);
                contents.put("nid", nid);

                contents.put("recoverLastDay", recordList.get(0).getRecoverLastDay());// 最后一笔的放款完成时间
                if (org.apache.commons.lang.StringUtils.isNotBlank(recordList.get(0).getRecoverLastTime())) {
                    // 最后一笔的放款完成时间 (协议签订日期)
                    contents.put("recoverTime", recordList.get(0).getRecoverLastTime());
                } else {
                    // 设置为满标时间
                    contents.put("recoverTime", recordList.get(0).getReverifyTime());
                }


                //承接和非承接做了判断
                String flag = form.getFlag();
                // 借款人用户名
                int userIds = recordList.get(0).getUserId();
                UserInfoVO userInfo = amUserClient.findUserInfoById(userIds);
                String borrowUsername = userInfo.getTruename();
                System.out.println("2221------------------------idCard:"+userInfo.getIdcard());
                if(flag!=null && flag=="1"){
                    form.setUserId(form.getCreditUserId());
                    List<WebUserInvestListCustomizeVO> tzList = myTenderSelectUserInvestList(form, 0, 100);
                    if (tzList != null && tzList.size() > 0) {
                        WebUserInvestListCustomizeVO userInvest = tzList.get(0);
                        UserInfoVO creditUserInfo = amUserClient.findUsersInfoById(Integer.valueOf(userInvest.getUserId()));
                        if(userInfo!=null&&!((userInfo.getUserId()+"").equals(userInvest.getUserId()))){
                            if (creditUserInfo != null){
                                userInvest.setRealName(creditUserInfo.getTruename().substring(0,1)+"**");
                                userInvest.setIdCard(creditUserInfo.getIdcard().substring(0,4)+"**************");
                            }
                            userInvest.setUsername(userInvest.getUsername().substring(0,1)+"*****");
                        }
                        contents.put("userInvest", userInvest);
                    }else {
                        logger.info("List<WebUserInvestListCustomizeVO> tzList = 0     ----11111");
                        return null;
                    }
                } else{
                    form.setUserId(userId.toString());
                    List<WebUserInvestListCustomizeVO> tzList = myTenderSelectUserInvestList(form, 0, 100);
                    if (tzList != null && tzList.size() > 0) {
                        WebUserInvestListCustomizeVO userInvest = tzList.get(0);
                        UserInfoVO userInfoVO = amUserClient.findUserInfoById(userId);
                        if(userInfo!=null&&!((userInfo.getUserId()+"").equals(userInvest.getUserId()))){
                            userInvest.setRealName(userInfo.getTruename().substring(0,1)+"**");
                            userInvest.setUsername(userInvest.getUsername().substring(0,1)+"*****");
                            userInvest.setIdCard(userInfo.getIdcard().substring(0,4)+"**************");
                        }
                        contents.put("userInvest", userInvest);
                    }else {
                        logger.info(" List<WebUserInvestListCustomizeVO> tzList.size() = 0     ----22222");
                        return null;
                    }
                }

                if(!(userInfo.getUserId()+"").equals(userInfo.getUserId()+"") ){
                    borrowUsername = borrowUsername.substring(0,1)+"**";
                }
                contents.put("borrowUsername", borrowUsername);
                // 如果是分期还款，查询分期信息
                String borrowStyle = recordList.get(0).getBorrowStyle();// 还款模式
                if (borrowStyle != null) {
                    //计算历史回报
                    BigDecimal earnings = new BigDecimal("0");
                    // 收益率

                    String borrowAprString = org.apache.commons.lang.StringUtils.isEmpty(recordList.get(0).getBorrowApr())?"0.00":recordList.get(0).getBorrowApr().replace("%", "");
                    BigDecimal borrowApr = new BigDecimal(borrowAprString);
                    //出借金额
                    String accountString = org.apache.commons.lang.StringUtils.isEmpty(recordList.get(0).getAccount())?"0.00":recordList.get(0).getAccount().replace(",", "");
                    BigDecimal account = new BigDecimal(accountString);
                    // 周期
                    String borrowPeriodString = org.apache.commons.lang.StringUtils.isEmpty(recordList.get(0).getBorrowPeriod())?"0":recordList.get(0).getBorrowPeriod();
                    String regEx="[^0-9]";
                    Pattern p = Pattern.compile(regEx);
                    Matcher m = p.matcher(borrowPeriodString);
                    borrowPeriodString = m.replaceAll("").trim();
                    Integer borrowPeriod = Integer.valueOf(borrowPeriodString);
                    if (org.apache.commons.lang.StringUtils.equals("endday", borrowStyle)){
                        // 还款方式为”按天计息，到期还本还息“：历史回报=出借金额*年化收益÷365*锁定期；
                        earnings = DuePrincipalAndInterestUtils.getDayInterest(account, borrowApr.divide(new BigDecimal("100")), borrowPeriod).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);
                    } else {
                        // 还款方式为”按月计息，到期还本还息“：历史回报=出借金额*年化收益÷12*月数；
                        earnings = DuePrincipalAndInterestUtils.getMonthInterest(account, borrowApr.divide(new BigDecimal("100")), borrowPeriod).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);

                    }
                    contents.put("earnings", earnings);
                    if ("month".equals(borrowStyle) || "principal".equals(borrowStyle)
                            || "endmonth".equals(borrowStyle)) {
                        ProjectRepayListBean bean = new ProjectRepayListBean();
                        Map<String,Object> param2 = new HashMap<>();
                        param2.put("userId",userId);
                        param2.put("borrowNid",borrowNid);
                        param2.put("nid",nid);
                        int recordTotal = myTenderCountProjectRepayPlanRecordTotal(param2);
                        Page page = Page.initPage(1,10);
                        if (recordTotal > 0) {
                            List<WebProjectRepayListCustomizeVO> fqList = selectProjectRepayPlanList(
                                    form,page.getOffset(), page.getLimit());
                            contents.put("paginator", page);
                            contents.put("repayList", fqList);
                        } else {
                            contents.put("paginator", page);
                            contents.put("repayList", "");
                        }
                    }
                }

                // 导出PDF文件
                try {
                    String flag1 = form.getFlag();
                    if(flag1!=null && flag1=="1"){
                        File file  =  PdfGenerator.generatePdfFile(request, response, form.getBorrowNid() + "_" + form.getNid() + ".pdf",
                                CustomConstants.TENDER_CONTRACT, contents);
                        return file;
                    }else {
                        PdfGenerator.generatePdf(request, response, form.getBorrowNid() + "_" + form.getNid() + ".pdf",
                                CustomConstants.TENDER_CONTRACT, contents);
                    }

                } catch (Exception e) {
                    logger.error("createAgreementPDF 导出PDF文件（汇盈金服互联网金融服务平台居间服务协议）", e);
                    logger.error(e.getMessage());
                }

            }

        }
        return null;
    }

    @Override
    public void downloadIntermediaryPdf(ProtocolRequest form, Integer userId, HttpServletRequest request, HttpServletResponse response) {
        String random = form.getRandom();
        String nid = form.getNid();
        String assignNid = form.getAssignNid();
        String borrowNid = form.getBorrowNid();
        if (StringUtils.isAnyBlank(random,nid,assignNid,borrowNid)){
            logger.info(">>>>>> 参数缺少,协议下载退出 <<<<<<<<");
            return;
        }

        userId = Integer.valueOf(random);
        UserVO userVO =  amUserClient.findUserById(userId);
        if (userVO == null){
            return ;
        }

        List<File> files = new ArrayList<File>();
        RightBorrowVO borrowVO = amTradeClient.getRightBorrowByNid(borrowNid);
        if( borrowVO != null ){
            if(StringUtils.isNotEmpty(borrowVO.getPlanNid())){
                TenderAgreementVO tenderAgreement = new TenderAgreementVO();
                List<TenderAgreementVO> tenderAgreementsNid= amTradeClient.selectTenderAgreementByNid(assignNid);//居间协议
                //if(tenderAgreementsNid!=null && tenderAgreementsNid.size()>0){


                    //计划中的标的---法大大
                    List<TenderAgreementVO>  tenderAgreementsAss;
                    while (true) {
                        List<HjhDebtCreditTenderVO> hjhCreditTenderList = amTradeClient.selectHjhCreditTenderListByAssignOrderId(nid);//hyjf_hjh_debt_credit_tender
                        if(hjhCreditTenderList!=null && hjhCreditTenderList.size()>0){
                            HjhDebtCreditTenderVO hjhCreditTender = hjhCreditTenderList.get(0);
                            logger.info("调用下载计划债转协议的方法 ---------------------:"+assignNid);
                            tenderAgreementsAss= amTradeClient.selectTenderAgreementByNid(hjhCreditTender.getAssignOrderId());//债转协议
                            //下载法大大协议--债转
                            if(tenderAgreementsAss!=null && tenderAgreementsAss.size()>0){
                                tenderAgreement = tenderAgreementsAss.get(0);
                                files = this.createFaddPDFImgFile(files,tenderAgreement);//下载脱敏
                            }
                            nid = hjhCreditTender.getSellOrderId();
                            if(nid.equals(hjhCreditTender.getInvestOrderId())){
                                //下载法大大协议--居间
                                tenderAgreementsNid= amTradeClient.selectTenderAgreementByNid(nid);//居间
                                if(tenderAgreementsNid!=null && tenderAgreementsNid.size()>0){
                                    tenderAgreement = tenderAgreementsNid.get(0);
                                    if(tenderAgreement!=null ){
                                        files = this.createFaddPDFImgFile(files,tenderAgreement);//下载脱敏
                                    }
                                }
                                break;
                            }
                        }else {
                            break;
                        }
                    }
                }else{
                    //计划中的标的
                    while (true) {
                        List<HjhDebtCreditTenderVO> hjhCreditTenderList = amTradeClient.selectHjhCreditTenderListByAssignOrderId(assignNid);//hyjf_hjh_debt_credit_tender
                        if(hjhCreditTenderList!=null && hjhCreditTenderList.size()>0){
                            HjhDebtCreditTenderVO hjhCreditTender = hjhCreditTenderList.get(0);
                            //调用下载计划债转协议的方法
                            /**
                             * 1.2018年3月28号以后出借（放款时间/承接时间为准）生成的协议(法大大签章协议）如果协议状态不是"下载成功"时 点击下载按钮提示“协议生成中”。
                             * 2.2018年3月28号以前出借（放款时间/承接时间为准）生成的协议(CFCA协议）点击下载CFCA协议。
                             * 3.智投中承接债转，如果债转协议中有2018-3-28之前的，2018-3-28之前承接的下载CFCA债转协议，2018-3-28之后承接的下载法大大债转协议。
                             */
                            //根据订单号获取用户放款信息
                            //放款记录创建时间（放款时间）
                           int addTime = (hjhCreditTender.getCreateTime() == null? 0 : GetDate.getTime10(hjhCreditTender.getCreateTime()));
                            if (addTime > ADD_TIME328) {
                                logger.error("createAgreementPDF 导出PDF文件（计划债转协议）,协议未生成");
                                break;
                            }
                            CreditAssignedBean tenderCreditAssignedBean  = new CreditAssignedBean();
                            Map<String, Object> creditContract = null;
                            tenderCreditAssignedBean.setBidNid(hjhCreditTender.getBorrowNid());// 标号
                            tenderCreditAssignedBean.setCreditNid(hjhCreditTender.getCreditNid());// 债转编号
                            tenderCreditAssignedBean.setCreditTenderNid(hjhCreditTender.getInvestOrderId());//原始出借订单号
                            tenderCreditAssignedBean.setAssignNid(hjhCreditTender.getAssignOrderId());//债转后的新的"出借"订单号
                            if(userId != null){
                                tenderCreditAssignedBean.setCurrentUserId(userId);
                            }
                            // 模板参数对象(查新表)
                            creditContract = this.selectHJHUserCreditContract(tenderCreditAssignedBean);
                            if(creditContract!=null){
                                try {
                                    File filetender = PdfGenerator.generatePdfFile(request, response, ((HjhDebtCreditTenderVO) creditContract.get("creditTender")).getAssignOrderId() + ".pdf", CustomConstants.HJH_CREDIT_CONTRACT, creditContract);
                                    if(filetender!=null){
                                        files.add(filetender);
                                    }
                                } catch (Exception e) {
                                    logger.error(e.getMessage());
                                }

                            }
                            nid = hjhCreditTender.getSellOrderId();
                            if(nid.equals(hjhCreditTender.getInvestOrderId())){
                                //原始标的居间协议
                                //放款记录创建时间（放款时间）
                                addTime = ADD_TIME;
                                /**
                                 * 1.2018年3月28号以后出借（放款时间/承接时间为准）生成的协议(法大大签章协议）如果协议状态不是"下载成功"时 点击下载按钮提示“协议生成中”。
                                 * 2.2018年3月28号以前出借（放款时间/承接时间为准）生成的协议(CFCA协议）点击下载CFCA协议。
                                 * 3.智投中承接债转，如果债转协议中有2018-3-28之前的，2018-3-28之前承接的下载CFCA债转协议，2018-3-28之后承接的下载法大大债转协议。
                                 */
                                //根据订单号获取用户放款信息
                                BorrowRecoverVO borrowRecoverVO = amTradeClient.selectBorrowRecoverByNid(nid);
                                if(borrowRecoverVO != null){
                                    addTime = (borrowRecoverVO.getCreateTime() == null? 0 : GetDate.getTime10(borrowRecoverVO.getCreateTime()));
                                }
                                if (addTime > ADD_TIME328) {
                                    logger.error("createAgreementPDF 导出PDF文件（汇盈金服互联网金融服务平台居间服务协议）,协议未生成");
                                    break;
                                }
                                ProtocolRequest req;
                                req = new ProtocolRequest();
                                req.setBorrowNid(borrowNid);
                                req.setNid(nid);//huiyingdai_borrow_tender--nid(取自银行)字段
                                req.setFlag("1");
                                File file;
                                //居间服务于借款协议时展示标的维度的借款方与出借方的关系的，出借方来自于 huiyingdai_borrow_tender
                                //原居间协议(注掉) file = createAgreementPDFFile(request, response, form, tmp.getUserId());
                                //(1)调用新作的居间借款协议
                                file = creditPaymentPlan(req,userId,request, response);
                                if (file != null) {
                                    files.add(file);
                                }
                                break;
                            }
                        }else {
                            break;
                        }
                    }
                }
            }
        if(files!=null && files.size()>0){
            ZIPGenerator.generateZip(response, files, borrowNid);
        }
    }






    private List<WebUserInvestListCustomizeVO> myTenderSelectUserInvestList(ProtocolRequest form , int limitStart, int limitEnd){
        String borrowNid = org.apache.commons.lang.StringUtils.isNotEmpty(form.getBorrowNid()) ? form.getBorrowNid() : null;
        String userId = org.apache.commons.lang.StringUtils.isNotEmpty(form.getUserId()) ? form.getUserId() : null;
        String nid = org.apache.commons.lang.StringUtils.isNotEmpty(form.getNid()) ? form.getNid() : null;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("borrowNid", borrowNid);
        params.put("userId", userId);
        params.put("nid", nid);
        if (limitStart == 0 || limitStart > 0) {
            params.put("limitStart", limitStart);
        }
        if (limitEnd > 0) {
            params.put("limitEnd", limitEnd);
        }
        List<WebUserInvestListCustomizeVO> list = amTradeClient.selectUserInvestList(params);
        return list;
    }


    private  List<WebUserInvestListCustomizeVO> planInfoSelectUserInvestList(ProtocolRequest form,int limitStart, int limitEnd){
        String borrowNid =StringUtils.isNotEmpty(form.getBorrowNid())?form.getBorrowNid():null;
        String userId = StringUtils.isNotEmpty(form.getUserId())?form.getUserId():null;
        String nid =StringUtils.isNotEmpty(form.getNid())?form.getNid():null;
        Map<String ,Object> params=new HashMap<String ,Object>();
        params.put("borrowNid", borrowNid);
        params.put("userId", userId);
        params.put("nid", nid);
        if(limitStart==0||limitStart>0){
            params.put("limitStart", limitStart);
        }
        if(limitEnd>0){
            params.put("limitEnd", limitEnd);
        }
        List<WebUserInvestListCustomizeVO> list=amTradeClient.selectUserDebtInvestList(params);
        return list;
    }

    private int planInfoCountProjectRepayPlanRecordTotal(Map<String,Object> param){
        Map<String ,Object> params=new HashMap<String ,Object>();
        params.put("userId", param.get("userId"));
        params.put("borrowNid", param.get("borrowNid"));
        params.put("nid", param.get("nid"));
        int total = amTradeClient.planInfoCountProjectRepayPlanRecordTotal(params);
        return total;
    }

    private int myTenderCountProjectRepayPlanRecordTotal(Map<String,Object> param){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", param.get("userId"));
        params.put("borrowNid", param.get("borrowNid"));
        params.put("nid", param.get("nid"));
        int total = amTradeClient.myTenderCountProjectRepayPlanRecordTotal(params);
        return total;
    }

    private List<WebProjectRepayListCustomizeVO> selectProjectRepayPlanList(ProtocolRequest form, int limitStart, int limitEnd){
        Map<String ,Object> params=new HashMap<String ,Object>();
        String userId = StringUtils.isNotEmpty(form.getUserId())?form.getUserId():null;
        String borrowNid =StringUtils.isNotEmpty(form.getBorrowNid())?form.getBorrowNid():null;
        params.put("userId", userId);
        params.put("borrowNid", borrowNid);
        params.put("nid", form.getNid());
        return amTradeClient.selectProjectLoanDetailList(params);
    }


    private List<File> createFaddPDFImgFile(List<File> files,TenderAgreementVO tenderAgreement) {
        SFTPParameter para = new SFTPParameter() ;
        String ftpIP = systemConfig.getHyjfFtpIp();
        String port = systemConfig.getHyjfFtpPort();
        String basePathImage = systemConfig.getHyjfFtpBasepathImg();
        String basePathPdf = systemConfig.getHyjfFtpBasepathPdf();
        String password = systemConfig.getHyjfFtpPassword();
        String username = systemConfig.getHyjfFtpUsername();
        para.hostName = ftpIP;//ftp服务器地址
        para.userName = username;//ftp服务器用户名
        para.passWord = password;//ftp服务器密码
        para.port = Integer.valueOf(port);//ftp服务器端口
        para.fileName=tenderAgreement.getTenderNid();
//        para.downloadPath =basePathImage;//ftp服务器文件目录creditUserId
        para.savePath = "/pdf_tem/pdf/" + tenderAgreement.getTenderNid();
        String imgUrl = tenderAgreement.getImgUrl();
        String pdfUrl = tenderAgreement.getPdfUrl();
        if(org.apache.commons.lang.StringUtils.isNotBlank(pdfUrl)){
            //获取文件目录
            int index = pdfUrl.lastIndexOf("/");
            String pdfPath = pdfUrl.substring(0,index);
            //文件名称
            String pdfName = pdfUrl.substring(index+1);
            para.downloadPath = basePathPdf + "/" + pdfPath;
            para.sftpKeyFile = pdfName;
        }else if(org.apache.commons.lang.StringUtils.isNotBlank(imgUrl)){
            int index = imgUrl.lastIndexOf("/");
            String imgPath = imgUrl.substring(0,index);
            //文件名称
            String imgName = imgUrl.substring(index+1);
            para.downloadPath = "/" + basePathImage + "/" + imgPath;
            para.sftpKeyFile = imgName;
        }else{
            return null;
        }
        File file =  FavFTPUtil.downloadDirectory(para);
        files.add(file);

        return files;
    }

    /**
     * 资产管理-散标-转让记录-查看详情-下载协议
     * @param tenderCreditAssignedBean
     * @param currentUserId
     * @param request
     * @param response
     * @return
     */
    @Override
    public void creditTransferAgreement(CreditAssignedBean tenderCreditAssignedBean, Integer currentUserId, HttpServletRequest request, HttpServletResponse response){
        String borrowNid = tenderCreditAssignedBean.getBidNid();
        String nid = tenderCreditAssignedBean.getCreditTenderNid();//原始标编号(取居间协议)
        String assignNid = tenderCreditAssignedBean.getAssignNid();//承接订单号(取债转协议)
        logger.info("散标-转让记录-下载协议开始，用户ID:{}，标的编号:{}，出让人原始出借单号:{}，承接单号:{}", currentUserId, borrowNid, nid, assignNid);
        String flag = "1";
        // 出让人userid
        String creditUserId = tenderCreditAssignedBean.getCreditUserId();
        //输出文件集合
        List<File> files = new ArrayList<File>();
        TenderAgreementVO tenderAgreement = new TenderAgreementVO();
        List<TenderAgreementVO> tenderAgreementsNid= amTradeClient.selectTenderAgreementByNid(nid);//居间协议
        List<TenderAgreementVO> tenderAgreementsAss= amTradeClient.selectTenderAgreementByNid(assignNid);//债转协议
        /** 由于前台页面判断协议下载规则是居间协议和债转协议生成其中有一个成功之后打包下载，一般居间服务协议生成之后才会有债转协议，所以
         * 根据协议判断是否下载不准确，由Zha Daojian2019-06-06修改为据居间协议生成状态判断
         */
        if(tenderAgreementsNid!=null && tenderAgreementsNid.size()>0){
            tenderAgreement = tenderAgreementsAss.get(0);
            //下载法大大协议--债转
            if(tenderAgreement!=null && tenderAgreement.getStatus()==3){
                /** 脱敏规则三期
                 *  出借债转：可以看到脱敏后的债权转让协议，出让人和承接人信息（姓名、证件号、盖章）均为脱敏后的信息*/
                logger.info("调用下载法大大协议--债转 ---------------------:"+tenderAgreement);
                files = createFaddPDFImgFile(files,tenderAgreement);//下载脱敏
            }
            //下载法大大协议--居间
            tenderAgreement = tenderAgreementsNid.get(0);
            if(tenderAgreement!=null && tenderAgreement.getStatus()==3){
                /** 脱敏规则三期
                 *  出借债转：可以看到脱敏后的债权转让协议，出让人和承接人信息（姓名、证件号、盖章）均为脱敏后的信息*/
                logger.info("调用下载法大大协议--居间 ---------------------:"+tenderAgreement);
                files = createFaddPDFImgFile(files,tenderAgreement);//下载脱敏
            }
        }else{
            // 下载平台老协议 单个文件 居间借款协议
            File file1;
            if (currentUserId != null && currentUserId.intValue() != 0) {
                if (org.apache.commons.lang.StringUtils.isNotEmpty(borrowNid) && org.apache.commons.lang.StringUtils.isNotEmpty(nid)) {
                    /**
                     * 1.2018年3月28号以后出借（放款时间/承接时间为准）生成的协议(法大大签章协议）如果协议状态不是"下载成功"时 点击下载按钮提示“协议生成中”。
                     * 2.2018年3月28号以前出借（放款时间/承接时间为准）生成的协议(CFCA协议）点击下载CFCA协议。
                     * 3.智投中承接债转，如果债转协议中有2018-3-28之前的，2018-3-28之前承接的下载CFCA债转协议，2018-3-28之后承接的下载法大大债转协议。
                     */
                    //根据订单号获取用户放款信息
                    int addTime = ADD_TIME;
                    BorrowRecoverVO borrowRecoverVO = amTradeClient.selectBorrowRecoverByNid(nid);
                    if(borrowRecoverVO != null){
                        addTime = (borrowRecoverVO.getCreateTime() == null? 0 : GetDate.getTime10(borrowRecoverVO.getCreateTime()));
                    }
                    if (addTime < ADD_TIME328) {
                        ProtocolRequest userInvestListBean = new ProtocolRequest();
                        userInvestListBean.setBorrowNid(borrowNid);
                        userInvestListBean.setNid(nid);
                        userInvestListBean.setFlag(flag);
                        userInvestListBean.setCreditUserId(creditUserId);
                        file1 = creditPaymentPlan(userInvestListBean, currentUserId, request, response);
                        if (file1 != null) {
                            files.add(file1);
                        }
                    }

                }
            }

            //(2)债转协议
            //这段代码没用了
//            ServletContext sc = request.getSession().getServletContext();
//            String webInfoPath = sc.getRealPath("/WEB-INF"); // 值为D:\apache-tomcat-6.0.26\webapps\webmonitor
//            // 把路径中的反斜杠转成正斜杠
//            webInfoPath = webInfoPath.replaceAll("\\\\", "/"); // 值为D:/apache-tomcat-6.0.26/webapps/webmonitor

            try {
                //以下参数为下载居间借款协议使用
                //borrow表的borrowNid ：tenderCreditAssignedBean.getBidNid()
                //huiyingdai_borrow_tender 的 nid : tenderCreditAssignedBean.getCreditTenderNid()
                if (currentUserId != null && currentUserId.intValue() != 0) {
                    if (org.apache.commons.lang.StringUtils.isEmpty(tenderCreditAssignedBean.getBidNid()) || org.apache.commons.lang.StringUtils.isEmpty(tenderCreditAssignedBean.getCreditNid())
                            || org.apache.commons.lang.StringUtils.isEmpty(tenderCreditAssignedBean.getCreditTenderNid()) || org.apache.commons.lang.StringUtils.isEmpty(tenderCreditAssignedBean.getAssignNid())) {
                        logger.info("下载协议参数不全。");
                        return;
                    }
                    /**
                     * 1.2018年3月28号以后出借（放款时间/承接时间为准）生成的协议(法大大签章协议）如果协议状态不是"下载成功"时 点击下载按钮提示“协议生成中”。
                     * 2.2018年3月28号以前出借（放款时间/承接时间为准）生成的协议(CFCA协议）点击下载CFCA协议。
                     * 3.智投中承接债转，如果债转协议中有2018-3-28之前的，2018-3-28之前承接的下载CFCA债转协议，2018-3-28之后承接的下载法大大债转协议。
                     */
                    //根据订单号获取用户放款信息
                    int addTime = ADD_TIME;
                    CreditTenderVO creditTenderVO = amTradeClient.selectCreditTenderByAssignOrderId(assignNid);
                    if(creditTenderVO != null){
                        addTime = (creditTenderVO.getCreateTime() == null? 0 : GetDate.getTime10(creditTenderVO.getCreateTime()));
                    }
                    if(addTime < ADD_TIME328){
                        //将当前登陆者的ID传送
                        tenderCreditAssignedBean.setCurrentUserId(currentUserId);
                        // 模板参数对象
                        Map<String, Object> creditContract = this.selectUserCreditContract(tenderCreditAssignedBean);
                        // 临时文件夹生成PDF文件
                        //generatePdfFile
                        //原：PdfGenerator.generatePdf(request, response, ((CreditTender) creditContract.get("creditTender")).getAssignNid() + ".pdf", CustomConstants.CREDIT_CONTRACT, creditContract);
                        File file2 = PdfGenerator.generatePdfFile(request, response, ((CreditTenderVO) creditContract.get("creditTender")).getAssignNid() + ".pdf", CustomConstants.CREDIT_CONTRACT, creditContract);
                        if(file2!=null){
                            files.add(file2);
                        }
                    }

                } else {
                    logger.info("转让用户未登录！");
                    return;
                }
            } catch (Exception e) {
                logger.info("下载协议系统异常", e);
                return ;
            }
        }
        if(files!=null && files.size()>0){
            logger.info("下载协议Download................"+JSONObject.toJSONString(files));
            ZIPGenerator.generateZip(response, files, tenderCreditAssignedBean.getBidNid());
        }else{
            logger.info("下载失败！");
            return ;
        }
        logger.info("下载协议结束。");
        return ;
    }

    public Map<String, Object> selectUserCreditContract(CreditAssignedBean tenderCreditAssignedBean) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 当前登陆者ID
        Integer currentUserId = tenderCreditAssignedBean.getCurrentUserId();
        // 获取债转出借信
        //原代码
//        CreditTenderExample creditTenderExample = new CreditTenderExample();
//        CreditTenderExample.Criteria creditTenderCra = creditTenderExample.createCriteria();
//        creditTenderCra.andAssignNidEqualTo(tenderCreditAssignedBean.getAssignNid()).andBidNidEqualTo(tenderCreditAssignedBean.getBidNid()).andCreditNidEqualTo(tenderCreditAssignedBean.getCreditNid())
//                .andCreditTenderNidEqualTo(tenderCreditAssignedBean.getCreditTenderNid());
//        List<CreditTender> creditTenderList = this.creditTenderMapper.selectByExample(creditTenderExample);

        CreditTenderRequest creditTenderRequest = new CreditTenderRequest();
        creditTenderRequest.setAssignNid(tenderCreditAssignedBean.getAssignNid());
        creditTenderRequest.setBidNid(tenderCreditAssignedBean.getBidNid());
        creditTenderRequest.setCreditNid(tenderCreditAssignedBean.getCreditNid());
        creditTenderRequest.setCreditTenderNid(tenderCreditAssignedBean.getCreditTenderNid());
        List<CreditTenderVO> creditTenderList = amTradeClient.getCreditTenderList(creditTenderRequest);
        if (creditTenderList != null && creditTenderList.size() > 0) {
            CreditTenderVO creditTender = creditTenderList.get(0);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("creditNid", creditTender.getCreditNid());
            params.put("assignNid", creditTender.getAssignNid());
            List<TenderToCreditDetailCustomizeVO> tenderToCreditDetailList = amTradeClient.selectWebCreditTenderDetailForContract(params);
            if (tenderToCreditDetailList != null && tenderToCreditDetailList.size() > 0) {
                if (tenderToCreditDetailList.get(0).getCreditRepayEndTime() != null) {
                    tenderToCreditDetailList.get(0).setCreditRepayEndTime(GetDate.getDateMyTimeInMillis(Integer.parseInt(tenderToCreditDetailList.get(0).getCreditRepayEndTime())));
                }
                if (tenderToCreditDetailList.get(0).getCreditTime() != null) {
                    try {
                        tenderToCreditDetailList.get(0).setCreditTime(GetDate.formatDate(GetDate.parseDate(tenderToCreditDetailList.get(0).getCreditTime(), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd"));
                    } catch (ParseException e) {
                        logger.error(e.getMessage());
                    }
                }
                resultMap.put("tenderToCreditDetail", tenderToCreditDetailList.get(0));
            }
            // 获取借款标的信息
            BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(creditTender.getBidNid());

            // 获取债转信息
            BorrowCreditRequest borrowCreditRequest = new BorrowCreditRequest();
            borrowCreditRequest.setCreditNid(tenderCreditAssignedBean.getCreditNid());
            borrowCreditRequest.setBidNid(tenderCreditAssignedBean.getBidNid());
            borrowCreditRequest.setTenderNid(tenderCreditAssignedBean.getCreditTenderNid());
            List<BorrowCreditVO> borrowCredit = amTradeClient.getBorrowCreditList(borrowCreditRequest);

            // 获取承接人身份信息
            UserInfoVO usersInfo = amUserClient.findUsersInfoById(creditTender.getUserId());

            // 获取承接人平台信息
            UserVO users = amUserClient.findUserById(creditTender.getUserId());

            // 获取融资方平台信息
            UserVO usersBorrow = amUserClient.findUserById(borrow.getUserId());

            // 获取债转人身份信息
            UserInfoVO usersInfoCredit = amUserClient.findUsersInfoById(creditTender.getCreditUserId());

            // 获取债转人平台信息
            UserVO usersCredit = amUserClient.selectUsersById(creditTender.getCreditUserId());

            // 将int类型时间转成字符串
            //老代码
//            creditTender.setAddTime(GetDate.times10toStrYYYYMMDD(Integer.valueOf(creditTender.getAddTime())));
//            creditTender.setAddip(GetDate.getDateMyTimeInMillis(creditTender.getAssignRepayEndTime()));// 借用ip字段存储最后还款时间

//            creditTender.setAddTime(GetDate.times10toStrYYYYMMDD(Integer.valueOf(creditTender.getAddTime())));
            creditTender.setAddIp(GetDate.getDateMyTimeInMillis(creditTender.getAssignRepayEndTime()));// 借用ip字段存储最后还款时间
            resultMap.put("creditTender", creditTender);
            if (borrow != null) {
                if (borrow.getReverifyTime() != null) {
                    borrow.setReverifyTimeStr(GetDate.getDateMyTimeInMillis(borrow.getReverifyTime()));
                }
                if (borrow.getRepayLastTime() != null) {
                    borrow.setRepayLastTimeStr(GetDate.getDateMyTimeInMillis(borrow.getRepayLastTime()));
                }
                resultMap.put("borrow", borrow);
                // 获取借款人信息
                UserInfoVO usersInfoBorrow = amUserClient.findUsersInfoById(borrow.getUserId());
                if (usersInfoBorrow != null) {
                    if (usersInfoBorrow.getTruename().length() > 1) {
                        if (usersInfoBorrow.getUserId().equals(currentUserId)) {
                            usersInfoBorrow.setTruename(usersInfoBorrow.getTruename());
                        } else {
                            usersInfoBorrow.setTruename(usersInfoBorrow.getTruename().substring(0, 1) + "**");
                        }
                    }
                    if (usersInfoBorrow.getIdcard().length() > 8) {
                        if (usersInfoBorrow.getUserId().equals(currentUserId)) {
                            usersInfoBorrow.setIdcard(usersInfoBorrow.getIdcard());
                        } else {
                            usersInfoBorrow.setIdcard(usersInfoBorrow.getIdcard().substring(0, 4) + "**************");
                        }
                    }
                    resultMap.put("usersInfoBorrow", usersInfoBorrow);
                }
            }
            if (borrowCredit != null && borrowCredit.size() > 0) {
                resultMap.put("borrowCredit", borrowCredit.get(0));
            }

            // 因为使用userid查询，所以只能有一条记录，取get(0)
            if (usersInfo != null) {
                if (usersInfo.getTruename().length() > 1) {
                    if (usersInfo.getUserId().equals(currentUserId)) {
                        // 本人信息不加密
                        usersInfo.setTruename(usersInfo.getTruename());
                    } else {

                        usersInfo.setTruename(usersInfo.getTruename().substring(0, 1) + "**");
                    }
                }
                if (usersInfo.getIdcard().length() > 8) {

                    if (usersInfo.getUserId().equals(currentUserId)) {
                        // 本人信息不加密
                        usersInfo.setIdcard(usersInfo.getIdcard());
                    } else {
                        // 非本人加密
                        usersInfo.setIdcard(usersInfo.getIdcard().substring(0, 4) + "**************");
                    }
                }
                resultMap.put("usersInfo", usersInfo);
            }

            if (usersBorrow != null) {
                if (usersBorrow.getUsername().length() > 1) {
                    if (usersBorrow.getUserId().equals(currentUserId)) {
                        usersBorrow.setUsername(usersBorrow.getUsername());
                    } else {
                        usersBorrow.setUsername(usersBorrow.getUsername().substring(0, 1) + "*****");
                    }
                }
                resultMap.put("usersBorrow", usersBorrow);
            }

            if (users != null) {
                if (users.getUsername().length() > 1) {

                    if (users.getUserId().equals(currentUserId)) {
                        users.setUsername(users.getUsername());
                    } else {
                        users.setUsername(users.getUsername().substring(0, 1) + "*****");
                    }
                }
                resultMap.put("users", users);
            }

            if (usersCredit != null) {
                if (usersCredit.getUsername().length() > 1) {
                    if (usersCredit.getUserId().equals(currentUserId)) {
                        usersCredit.setUsername(usersCredit.getUsername());
                    } else {
                        usersCredit.setUsername(usersCredit.getUsername().substring(0, 1) + "*****");
                    }
                }
                resultMap.put("usersCredit", usersCredit);
            }


            //甲方(原债权人)
            if (usersInfoCredit != null) {
                if (usersInfoCredit.getTruename().length() > 1) {
                    if (usersInfoCredit.getUserId().equals(currentUserId)) {
                        usersInfoCredit.setTruename(usersInfoCredit.getTruename());
                    } else {
                        usersInfoCredit.setTruename(usersInfoCredit.getTruename().substring(0, 1) + "**");
                    }
                }

                if (usersInfoCredit.getIdcard().length() > 8) {
                    if (usersInfoCredit.getUserId().equals(currentUserId)) {
                        usersInfoCredit.setIdcard(usersInfoCredit.getIdcard());
                    } else {
                        usersInfoCredit.setIdcard(usersInfoCredit.getIdcard().substring(0, 4) + "**************");
                    }
                }
                resultMap.put("usersInfoCredit", usersInfoCredit);
            }

            //之前的PHP服务器 已经停用很久了。。
//            String phpWebHost = PropUtils.getSystem("hyjf.web.host.php");
//            if (org.apache.commons.lang.StringUtils.isNotEmpty(phpWebHost)) {
//                resultMap.put("phpWebHost", phpWebHost);
//            } else {
//                resultMap.put("phpWebHost", "http://site.hyjf.com");
//            }
        }
        return resultMap;
    }

    /**
     * 查询汇计划债转出借表
     * @param tenderCreditAssignedBean
     * @return
     */
    private Map<String,Object> selectHJHUserCreditContract(CreditAssignedBean tenderCreditAssignedBean) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 获取债转出借信息
        //查询 hyjf_hjh_debt_credit_tender 表
        HjhDebtCreditTenderRequest request = new HjhDebtCreditTenderRequest();
        request.setBorrowNid(tenderCreditAssignedBean.getBidNid());
        request.setCreditNid(tenderCreditAssignedBean.getCreditNid());
        request.setInvestOrderId(tenderCreditAssignedBean.getCreditTenderNid());
        request.setAssignOrderId(tenderCreditAssignedBean.getAssignNid());
        List<HjhDebtCreditTenderVO> creditTenderList =this.amTradeClient.getHjhDebtCreditTenderList(request);
        // 当前用户的id
        Integer currentUserId = tenderCreditAssignedBean.getCurrentUserId();

        if (creditTenderList != null && creditTenderList.size() > 0) {
            HjhDebtCreditTenderVO creditTender = creditTenderList.get(0);
            Map<String, Object> params = new HashMap<String, Object>();

            params.put("creditNid", creditTender.getCreditNid());//取得 hyjf_hjh_debt_credit_tender 表的债转编号
            params.put("assignOrderId", creditTender.getAssignOrderId());//取得 hyjf_hjh_debt_credit_tender 表的债转编号

            //查看债转详情
            List<TenderToCreditDetailCustomizeVO> tenderToCreditDetailList=this.amTradeClient.selectHJHWebCreditTenderDetail(params);

            if (tenderToCreditDetailList != null && tenderToCreditDetailList.size() > 0) {
                if (tenderToCreditDetailList.get(0).getCreditRepayEndTime() != null) {
                    tenderToCreditDetailList.get(0).setCreditRepayEndTime(GetDate.getDateMyTimeInMillis(Integer.parseInt(tenderToCreditDetailList.get(0).getCreditRepayEndTime())));
                }
                if (tenderToCreditDetailList.get(0).getCreditTime() != null) {
                    try {
                        tenderToCreditDetailList.get(0).setCreditTime(GetDate.formatDate(GetDate.parseDate(tenderToCreditDetailList.get(0).getCreditTime(), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd"));
                    } catch (ParseException e) {
                        logger.error(e.getMessage());
                    }
                }
                resultMap.put("tenderToCreditDetail", tenderToCreditDetailList.get(0));
            }


            // 获取借款标的信息
            BorrowAndInfoVO borrow=this.amTradeClient.getBorrowByNid(creditTender.getBorrowNid());

            // 获取债转信息(新表)
            HjhDebtCreditRequest request1 = new HjhDebtCreditRequest();
            request1.setBorrowNid(tenderCreditAssignedBean.getBidNid());
            request1.setCreditNid(tenderCreditAssignedBean.getCreditNid());
            request1.setInvestOrderId(tenderCreditAssignedBean.getCreditTenderNid());
            List<HjhDebtCreditVO> borrowCredit=this.amTradeClient.getHjhDebtCreditList(request1);

            // 获取承接人身份信息
            UserInfoVO usersInfo=this.amUserClient.findUsersInfoById(creditTender.getUserId());
            // 获取承接人平台信息
            UserVO users=this.amUserClient.findUserById(creditTender.getUserId());

            // 获取融资方平台信息
            UserVO usersBorrow=this.amUserClient.findUserById(borrow.getUserId());

            // 获取债转人身份信息
            UserInfoVO usersInfoCredit=this.amUserClient.findUsersInfoById(creditTender.getCreditUserId());

            // 获取债转人平台信息
            UserVO usersCredit=this.amUserClient.findUserById(creditTender.getCreditUserId());

            // 将int类型时间转成字符串
            String createTime = GetDate.dateToString2(creditTender.getCreateTime(),"yyyyMMdd");
            String addip = GetDate.getDateMyTimeInMillis(creditTender.getAssignRepayEndTime());// 借用ip字段存储最后还款时
            resultMap.put("createTime", createTime);//记得去JSP上替换 creditResult.data.creditTender.addip 字段，要新建一个JSP
            resultMap.put("addip", addip);//记得去JSP上替换 creditResult.data.creditTender.addip 字段，要新建一个JSP
            resultMap.put("creditTender", creditTender);

            if (borrow != null) {
                if (borrow.getReverifyTime() != null) {
                    borrow.setReverifyTime(borrow.getReverifyTime());
                }
                if (borrow.getRepayLastTime() != null) {
                    borrow.setRepayLastTime(borrow.getRepayLastTime());
                }
                resultMap.put("borrow", borrow);
                // 获取借款人信息
                UserInfoVO usersInfoBorrow=this.amUserClient.findUsersInfoById(borrow.getUserId());


                if (usersInfoBorrow != null) {
                    resultMap.put("usersInfoBorrow", usersInfoBorrow);
                }
            }

            if (borrowCredit != null && borrowCredit.size() > 0) {
                resultMap.put("borrowCredit", borrowCredit);
            }


            if (usersInfo != null) {
                resultMap.put("usersInfo", usersInfo);
            }


            if (usersBorrow != null) {

                resultMap.put("usersBorrow", usersBorrow);
            }

            if (users != null) {
                resultMap.put("users", users);
            }

            if (usersCredit != null) {
                resultMap.put("usersCredit", usersCredit);
            }

            if (usersInfoCredit != null) {

                resultMap.put("usersInfoCredit", usersInfoCredit);
            }

            String phpWebHost = "";
            //PropUtils.getSystem("hyjf.web.host.php");
            //hyjf.web.host.php 不再使用
            if (org.apache.commons.lang.StringUtils.isNotEmpty(phpWebHost)) {
                resultMap.put("phpWebHost", phpWebHost);
            } else {
                resultMap.put("phpWebHost", "http://site.hyjf.com");
            }
        }
        return resultMap;
    }


    @Override
    public void newHjhInvestPDF(ProtocolRequest form, HttpServletRequest request, HttpServletResponse response) {
        logger.info(">>>>>>>>newHjhInvestPDF 协议下载开始<<<<<<<<<");
        String random = form.getRandom();
        String planNid = form.getPlanNid();
        String accedeOrderNid = form.getAccedeOrderId();
        if (StringUtils.isAnyBlank(random,planNid,accedeOrderNid)){
            logger.info(">>>>>> 参数缺少,协议下载退出 <<<<<<<<");
            return;
        }

        Integer userId = Integer.valueOf(random);
        UserVO userVO =  amUserClient.findUserById(userId);
        if (userVO == null){
            logger.info(">>>>>>用户不存在<<<<<<<");
            return ;
        }

        String nid  = accedeOrderNid;
        //输出文件集合
        List<File> files = new ArrayList<File>();
        TenderAgreementVO tenderAgreement = new TenderAgreementVO();
        List<TenderAgreementVO> tenderAgreementsNid = amTradeClient.selectTenderAgreementByNid(nid);//汇盈金服互联网金融服务平台汇计划智投服务协议
        /*******************************下载法大大合同************************************/

        //下载法大大协议--债转
        if(tenderAgreementsNid!=null && tenderAgreementsNid.size()>0){
            tenderAgreement = tenderAgreementsNid.get(0);
            try {
                if(org.apache.commons.lang.StringUtils.isNotBlank(tenderAgreement.getDownloadUrl())){
                    File filePdf= FileUtil.getFile(tenderAgreement.getDownloadUrl(),nid+".pdf");//汇盈金服互联网金融服务平台汇计划智投服务协议
                    if(filePdf!=null){
                        files.add(filePdf);
                    }
                }
                if(files!=null && files.size()>0){
                    ZIPGenerator.generateZip(response, files, nid);

                }
            } catch (IOException e) {
                logger.info("newHjhInvestPDF", "下载失败，请稍后重试。。。。");
                return;
            }
        }else{//下载原来的
            /**
             * 1.2018年3月28号以后出借（放款时间/承接时间为准）生成的协议(法大大签章协议）如果协议状态不是"下载成功"时 点击下载按钮提示“协议生成中”。
             * 2.2018年3月28号以前出借（放款时间/承接时间为准）生成的协议(CFCA协议）点击下载CFCA协议。
             * 3.智投中承接债转，如果债转协议中有2018-3-28之前的，2018-3-28之前承接的下载CFCA债转协议，2018-3-28之后承接的下载法大大债转协议。
             */
            //根据订单号获取用户放款信息
            //放款记录创建时间（放款时间）
            int addTime = ADD_TIME;
            BorrowRecoverVO borrowRecoverVO =  amTradeClient.selectBorrowRecoverByNid(nid);
            if(borrowRecoverVO != null){
                //放款记录创建时间（放款时间）
                addTime = (borrowRecoverVO.getCreateTime() == null? 0 : GetDate.getTime10(borrowRecoverVO.getCreateTime()));
                if (addTime < ADD_TIME328) {
                    Map<String, Object> contents = new HashMap<String, Object>();
                    //1基本信息
                    Map<String ,Object> params=new HashMap<String ,Object>();
                    params.put("accedeOrderId", accedeOrderNid);
                    params.put("userId", userId);
                    UserInfoVO userInfo=amUserClient.findUserInfoById(userId);
                    contents.put("userInfo", userInfo);
                    contents.put("username", userVO.getUsername());
                    UserHjhInvistDetailCustomizeVO userHjhInvistDetailCustomize = amTradeClient.selectUserHjhInvistDetail(params);
                    contents.put("userHjhInvistDetail", userHjhInvistDetailCustomize);
                    // 导出PDF文件
                    try {
                        PdfGenerator.generatePdf(request, response, planNid + "_" + accedeOrderNid + ".pdf",
                                CustomConstants.NEW_HJH_INVEST_CONTRACT, contents);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }
            }

        }
    }

    @Override
    public void intermediaryAgreementPDF(ProtocolRequest form, HttpServletRequest request, HttpServletResponse response) {
        Integer userId=Integer.parseInt(form.getRandom());
        form.setUserId(form.getRandom());
        logger.info(CreateAgreementController.class.toString(), "createAgreementPDF 导出PDF文件（汇盈金服互联网金融服务平台居间服务协议）");
        if (form.getBorrowNid() == null || "".equals(form.getBorrowNid().trim())) {
            logger.info(CreateAgreementController.class.toString(), "标的信息不存在,下载汇盈金服互联网金融服务平台居间服务协议PDF失败。");
            return;
        }
        // 查询借款人用户名
        BorrowCommonCustomizeVO borrowCommonCustomize = new BorrowCommonCustomizeVO();
        // 借款编码
        borrowCommonCustomize.setBorrowNidSrch(form.getBorrowNid());
        List<BorrowCustomizeVO> recordList1 = amTradeClient.searchBorrowCustomizeList(borrowCommonCustomize);
        //输出文件集合
        List<File> files = new ArrayList<File>();
        String nid=form.getNid();
        TenderAgreementVO tenderAgreement = new TenderAgreementVO();
        List<TenderAgreementVO> tenderAgreementsNid= amTradeClient.selectTenderAgreementByNid(nid);//居间协议
        /*******************************下载法大大合同************************************/
        //下载法大大协议--居间
        if(tenderAgreementsNid!=null && tenderAgreementsNid.size()>0){
            tenderAgreement = tenderAgreementsNid.get(0);
            if(tenderAgreement!=null){
                logger.info("----------------------------下载法大大脱敏pdf文件:"+JSONObject.toJSONString(tenderAgreement));
                files = createFaddPDFImgFile(files,tenderAgreement);//下载脱敏
                logger.info("----------------------------获取法大大居间协议:"+JSONObject.toJSONString(files));
                if(files!=null && files.size()>0){
                    logger.info("----------------------------压缩法大大居间协议:"+JSONObject.toJSONString(files));
                    ZIPGenerator.generateZip(response, files, nid);
                }
                return;
            }

        }
        /**
         * 1.2018年3月28号以后出借（放款时间/承接时间为准）生成的协议(法大大签章协议）如果协议状态不是"下载成功"时 点击下载按钮提示“协议生成中”。
         * 2.2018年3月28号以前出借（放款时间/承接时间为准）生成的协议(CFCA协议）点击下载CFCA协议。
         * 3.智投中承接债转，如果债转协议中有2018-3-28之前的，2018-3-28之前承接的下载CFCA债转协议，2018-3-28之后承接的下载法大大债转协议。
         */
        //根据订单号获取用户放款信息
        //放款记录创建时间（放款时间）
        int addTime = ADD_TIME;
        BorrowRecoverVO borrowRecoverVO =  amTradeClient.selectBorrowRecoverByNid(nid);
        if(borrowRecoverVO != null){
            //放款记录创建时间（放款时间）
            addTime = (borrowRecoverVO.getCreateTime() == null? 0 : GetDate.getTime10(borrowRecoverVO.getCreateTime()));
            if (addTime > ADD_TIME328) {
                logger.error("createAgreementPDF 导出PDF文件（汇盈金服互联网金融服务平台居间服务协议）,协议未生成");
                return;
            }
        }
        //下载平台老协议
        // 融通宝静态打印
        if (StringUtils.isNotEmpty(form.getProjectType()) && form.getProjectType().equals("13")) {
            Map<String, Object> contents = new HashMap<String, Object>();
            // 用户ID
            UserInfoVO userinfo = amUserClient.findUsersInfoById(userId);
            form.setUserId(userId + "");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("borrowNid", form.getBorrowNid());
            params.put("userId", form.getUserId());
            params.put("nid", form.getNid());
            List<WebUserInvestListCustomizeVO> invest = amTradeClient.selectUserInvestList(params);
            if (invest != null && invest.size() > 0) {
                contents.put("investDeatil", invest.get(0));
            }
            ProjectCustomeDetailVO borrow = this.amTradeClient.selectProjectDetail(form.getBorrowNid());
            contents.put("projectDeatil", borrow);
            contents.put("truename", userinfo.getTruename());
            contents.put("idcard", userinfo.getIdcard());
            contents.put("borrowNid", form.getBorrowNid());// 标的号
            contents.put("nid", form.getNid());// 标的号
            contents.put("assetNumber", form.getAssetNumber());// 资产编号
            contents.put("projectType", form.getProjectType());// 项目类型
            String moban = CustomConstants.RTB_TENDER_CONTRACT;
            if (borrow != null && borrow.getBorrowPublisher() != null && borrow.getBorrowPublisher().equals("中商储")) {
                moban = CustomConstants.RTB_TENDER_CONTRACT_ZSC;
            }
            // 导出PDF文件
            try {
                PdfGenerator.generatePdf(request, response, form.getBorrowNid() + ".pdf", moban, contents);
            } catch (Exception e) {
                logger.info(CreateAgreementController.class.toString(), "createAgreementPDF 导出PDF文件（汇盈金服互联网金融服务平台居间服务协议）", e);
                logger.error(e.getMessage());
            }
        } else {
            // 判断是否是汇添金专属标的导出
            if (recordList1.size() != 1) {
                Map<String, Object> contents = new HashMap<String, Object>();
                // 查询借款人用户名
                DebtBorrowCommonCustomizeVO debtBorrowCommonCustomize = new DebtBorrowCommonCustomizeVO();
                // 借款编码
                debtBorrowCommonCustomize.setBorrowNidSrch(form.getBorrowNid());
                Map<String,Object> param = new HashMap<>();
                String borrowNid = debtBorrowCommonCustomize.getBorrowNid();
                param.put("borrowNidSrch",borrowNid);
                List<DebtBorrowCustomizeVO> recordList = amTradeClient.searchDebtBorrowList4Protocol(param);
                if (recordList.size() != 1) {
                    logger.info(CreateAgreementController.class.toString(), "标的信息异常（0条或者大于1条信息）,下载汇盈金服互联网金融服务平台居间服务协议PDF失败。");
                    return;
                }
                contents.put("borrowNid", form.getBorrowNid());
                contents.put("nid", form.getNid());
                // 借款人用户名
                contents.put("borrowUsername", recordList.get(0).getUsername());
                if (StringUtils.isNotBlank(recordList.get(0).getRecoverLastTime())) {
                    // 最后一笔的放款完成时间 (协议签订日期)
                    contents.put("recoverTime", recordList.get(0).getRecoverLastTime());
                } else {
                    // 设置为满标时间
                    contents.put("recoverTime", recordList.get(0).getReverifyTime());
                }

                form.setUserId(userId.toString());
                // 用户出借列表
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("borrowNid", form.getBorrowNid());
                params.put("userId", form.getUserId());
                params.put("nid", form.getNid());
                List<WebUserInvestListCustomizeVO> tzList = amTradeClient.selectUserInvestList(params);
                if (tzList != null && tzList.size() > 0) {
                    contents.put("userInvest", tzList.get(0));
                }

                // 如果是分期还款，查询分期信息
                String borrowStyle = recordList.get(0).getBorrowStyle();// 还款模式
                if (borrowStyle != null) {
                    if ("month".equals(borrowStyle) || "principal".equals(borrowStyle)
                            || "endmonth".equals(borrowStyle)) {
                        Map<String,Object> paraBean = new HashMap<>();
                        paraBean.put("userId", form.getUserId());
                        paraBean.put("borrowNid", form.getBorrowNid());
                        paraBean.put("nid", form.getNid());
                        int recordTotal = amTradeClient.countProjectRepayPlanRecordTotal(paraBean);
                        if (recordTotal > 0) {
                            Paginator paginator = new Paginator(form.getPaginatorPage(), recordTotal);
                            List<WebProjectRepayListCustomizeVO> fqList = amTradeClient.selectProjectRepayPlanList(paraBean);
                            contents.put("paginator", paginator);
                            contents.put("repayList", fqList);
                        } else {
                            Paginator paginator = new Paginator(form.getPaginatorPage(), recordTotal);
                            contents.put("paginator", paginator);
                            contents.put("repayList", "");
                        }
                    }
                }
                // 导出PDF文件
                try {
                    PdfGenerator.generatePdf(request, response, form.getBorrowNid() + "_" + form.getNid() + ".pdf",
                            CustomConstants.TENDER_CONTRACT, contents);
                } catch (Exception e) {
                    logger.info(CreateAgreementController.class.toString(), "createAgreementPDF 导出PDF文件（汇盈金服互联网金融服务平台居间服务协议）", e);
                    logger.error(e.getMessage());
                }
            } else {
                List<BorrowCustomizeVO> recordList = recordList1;
                Map<String, Object> contents = new HashMap<String, Object>();
                // 借款人用户名
                if(recordList.size()>0){
                    contents.put("record", recordList.get(0));
                    // 借款人用户名
                    int userIds = recordList.get(0).getUserId();
                    UserInfoVO userInfo=amUserClient.findUserInfoById(userIds);
                    String borrowUsername = userInfo.getTruename();
                    if(!(userId+"").equals(userInfo.getUserId()+"") ){
                        borrowUsername = borrowUsername.substring(0,borrowUsername.length()-1)+"*";
                    }
                    contents.put("borrowUsername", borrowUsername);
                    contents.put("idCard", userInfo.getIdcard());
                    contents.put("recoverLastDay", recordList.get(0).getRecoverLastDay());// 最后一笔的放款完成时间
                    contents.put("recoverAccount", recordList.get(0).getAccount());
                    contents.put("borrowNid", form.getBorrowNid());
                    contents.put("nid", form.getNid());
                    if (StringUtils.isNotBlank(recordList.get(0).getRecoverLastTime())) {
                        // 最后一笔的放款完成时间 (协议签订日期)
                        contents.put("recoverTime", recordList.get(0).getRecoverLastTime());
                    } else {
                        // 设置为满标时间
                        contents.put("recoverTime", recordList.get(0).getReverifyTime());
                    }
                }
                form.setUserId(userId.toString());
                // 用户出借列表
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("borrowNid", form.getBorrowNid());
                params.put("userId", form.getUserId());
                params.put("nid", form.getNid());
                List<WebUserInvestListCustomizeVO> tzList = amTradeClient.selectUserInvestList(params);

                if (tzList != null && tzList.size() > 0) {
                    WebUserInvestListCustomizeVO userInvest = tzList.get(0);
                    if(((userId+"").equals(userInvest.getUserId()))){
                        UserInfoVO userInfo=amUserClient.findUserInfoById(Integer.parseInt(userInvest.getUserId()));
                        logger.info("userInvest:"+JSONObject.toJSONString(userInvest));
                        userInvest.setRealName(userInfo.getTruename().substring(0,1)+"**");
                        userInvest.setUsername(userInvest.getUsername().substring(0,1)+"*****");
                        userInvest.setIdCard(userInfo.getIdcard().substring(0,4)+"**************");
                    }
                    contents.put("userInvest", userInvest);
                }

                // 如果是分期还款，查询分期信息
                String borrowStyle = recordList.get(0).getBorrowStyle();// 还款模式
                if (borrowStyle != null) {
                    //计算历史回报
                    BigDecimal earnings = new BigDecimal("0");
                    // 收益率

                    String borrowAprString = StringUtils.isEmpty(recordList.get(0).getBorrowApr())?"0.00":recordList.get(0).getBorrowApr().replace("%", "");
                    BigDecimal borrowApr = new BigDecimal(borrowAprString);
                    //出借金额
                    String accountString = StringUtils.isEmpty(recordList.get(0).getAccount())?"0.00":recordList.get(0).getAccount().replace(",", "");
                    BigDecimal account = new BigDecimal(accountString);
                    // 周期
                    String borrowPeriodString = StringUtils.isEmpty(recordList.get(0).getBorrowPeriod())?"0":recordList.get(0).getBorrowPeriod();
                    String regEx="[^0-9]";
                    Pattern p = Pattern.compile(regEx);
                    Matcher m = p.matcher(borrowPeriodString);
                    borrowPeriodString = m.replaceAll("").trim();
                    Integer borrowPeriod = Integer.valueOf(borrowPeriodString);
                    if (StringUtils.equals("endday", borrowStyle)){
                        // 还款方式为”按天计息，到期还本还息“：历史回报=出借金额*年化收益÷365*锁定期；
                        earnings = DuePrincipalAndInterestUtils.getDayInterest(account, borrowApr.divide(new BigDecimal("100")), borrowPeriod).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);
                    } else {
                        // 还款方式为”按月计息，到期还本还息“：历史回报=出借金额*年化收益÷12*月数；
                        earnings = DuePrincipalAndInterestUtils.getMonthInterest(account, borrowApr.divide(new BigDecimal("100")), borrowPeriod).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);

                    }
                    contents.put("earnings", earnings);
                    if ("month".equals(borrowStyle) || "principal".equals(borrowStyle)
                            || "endmonth".equals(borrowStyle)) {
                        Map<String,Object> paraBean = new HashMap<>();
                        paraBean.put("userId", form.getUserId());
                        paraBean.put("borrowNid", form.getBorrowNid());
                        paraBean.put("nid", form.getNid());
                        int recordTotal = amTradeClient.countProjectRepayPlanRecordTotal(paraBean);
                        if (recordTotal > 0) {
                            Paginator paginator = new Paginator(form.getPaginatorPage(), recordTotal);
                            List<WebProjectRepayListCustomizeVO> fqList = amTradeClient.selectProjectRepayPlanList(paraBean);
                            contents.put("paginator", paginator);
                            contents.put("repayList", fqList);
                        } else {
                            Paginator paginator = new Paginator(form.getPaginatorPage(), recordTotal);
                            contents.put("paginator", paginator);
                            contents.put("repayList", "");
                        }
                    }
                }

                // 导出PDF文件
                try {
                    PdfGenerator.generatePdf(request, response, form.getBorrowNid() + "_" + form.getNid() + ".pdf",
                            CustomConstants.TENDER_CONTRACT, contents);
                } catch (Exception e) {
                    logger.info(CreateAgreementController.class.toString(), "createAgreementPDF 导出PDF文件（汇盈金服互联网金融服务平台居间服务协议）", e);
                    logger.error(e.getMessage());
                }

            }
        }
        logger.info(CreateAgreementController.class.toString(), "createAgreementPDF 导出PDF文件（汇盈金服互联网金融服务平台居间服务协议）");
    }
}
