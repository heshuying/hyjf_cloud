package com.hyjf.cs.trade.service.projectlist.impl;

import com.hyjf.am.vo.admin.WebProjectRepayListCustomizeVO;
import com.hyjf.am.vo.admin.WebUserInvestListCustomizeVO;
import com.hyjf.am.vo.trade.BorrowListVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.DebtBorrowCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.constants.FddGenerateContractConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.file.FavFTPUtil;
import com.hyjf.common.file.SFTPParameter;
import com.hyjf.common.file.ZIPGenerator;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.calculate.DuePrincipalAndInterestUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.util.Page;
import com.hyjf.cs.trade.bean.ProtocolRequest;
import com.hyjf.cs.trade.bean.repay.ProjectRepayListBean;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
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
import java.math.BigDecimal;
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

    /**
     * 下载脱敏后居间服务借款协议（原始标的）_计划投资人
     * @author zhangyk
     * @date 2018/10/18 11:34
     */
    @Override
    public File creditPaymentPlan(ProtocolRequest form, Integer userId, HttpServletRequest request, HttpServletResponse response) {
        logger.info(">>>>>>>>>>>>协议下载开始<<<<<<<<<<<");
        CheckUtil.check(StringUtils.isNotBlank(form.getBorrowNid()),MsgEnum.ERR_OBJECT_REQUIRED, "借款编号");
        Map<String,Object> param = new HashMap<>();
        String borrowNid = form.getBorrowNid();
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
                // 用户投资列表
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
                        UserInfoVO creditUserInfo = amUserClient.findUsersInfoById(Integer.valueOf(form.getCreditUserId()));
                        if(userInfo!=null&&!((userInfo.getUserId()+"").equals(userInvest.getUserId()))){

                            userInvest.setRealName(creditUserInfo.getTruename().substring(0,1)+"**");
                            userInvest.setUsername(userInvest.getUsername().substring(0,1)+"*****");
                            userInvest.setIdCard(creditUserInfo.getIdcard().substring(0,4)+"**************");
                        }
                        contents.put("userInvest", userInvest);
                    }else {
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
                    //投资金额
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
                        // 还款方式为”按天计息，到期还本还息“：历史回报=投资金额*年化收益÷365*锁定期；
                        earnings = DuePrincipalAndInterestUtils.getDayInterest(account, borrowApr.divide(new BigDecimal("100")), borrowPeriod).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);
                    } else {
                        // 还款方式为”按月计息，到期还本还息“：历史回报=投资金额*年化收益÷12*月数；
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
                    e.printStackTrace();
                }

            }

        }
        return null;
    }
    private List<WebUserInvestListCustomizeVO> myTenderSelectUserInvestList(ProtocolRequest form , int limitStart,int limitEnd){
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
}
