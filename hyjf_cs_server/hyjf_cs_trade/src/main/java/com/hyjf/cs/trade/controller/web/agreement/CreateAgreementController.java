package com.hyjf.cs.trade.controller.web.agreement;

import com.hyjf.am.resquest.trade.UserInvestListBeanRequest;
import com.hyjf.am.vo.admin.BorrowCustomizeVO;
import com.hyjf.am.vo.admin.WebProjectRepayListCustomizeVO;
import com.hyjf.am.vo.admin.WebUserInvestListCustomizeVO;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.trade.DebtBorrowCommonCustomizeVO;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.ProjectRepayListBeanVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.DebtBorrowCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.file.FavFTPUtil;
import com.hyjf.common.file.SFTPParameter;
import com.hyjf.common.file.ZIPGenerator;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.calculate.DuePrincipalAndInterestUtils;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.agreement.CreateAgreementService;
import com.hyjf.cs.trade.util.PdfGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author pangchengchao
 * @version CreateAgreementController, v0.1 2018/11/6 9:29
 */
@Api(tags = "web端-新协议接口")
@RestController
@RequestMapping(value = "/hyjf-web/createAgreementPDF")
public class CreateAgreementController extends BaseTradeController {
    private static final Logger logger = LoggerFactory.getLogger(CreateAgreementController.class);

    @Autowired
    private CreateAgreementService createAgreementService;

    @Autowired
    SystemConfig systemConfig;
    /**
     * 账户中心-资产管理-当前持有-- 投资协议(实际为散标居间协议)下载
     * @return
     */
    @ApiOperation(value = "账户中心-资产管理-当前持有-- 投资协议(实际为散标居间协议)下载", httpMethod = "POST", notes = "账户中心-资产管理-当前持有-- 投资协议(实际为散标居间协议)下载")
    @GetMapping("/intermediaryAgreementPDF")
    public void downloadIntermediaryAgreementPDF(HttpServletRequest request, HttpServletResponse response,
                                                 @RequestBody @Valid UserInvestListBeanRequest form) {
        Integer userId=Integer.parseInt(form.getRandom());
        logger.info(CreateAgreementController.class.toString(), "createAgreementPDF 导出PDF文件（汇盈金服互联网金融服务平台居间服务协议）");
        if (form.getBorrowNid() == null || "".equals(form.getBorrowNid().trim())) {
            logger.info(CreateAgreementController.class.toString(), "标的信息不存在,下载汇盈金服互联网金融服务平台居间服务协议PDF失败。");
            return;
        }
        // 查询借款人用户名
        BorrowCommonCustomizeVO borrowCommonCustomize = new BorrowCommonCustomizeVO();
        // 借款编码
        borrowCommonCustomize.setBorrowNidSrch(form.getBorrowNid());
        List<BorrowCustomizeVO> recordList1 = createAgreementService.selectBorrowList(borrowCommonCustomize);
        String nid = form.getNid();
        //输出文件集合
        List<File> files = new ArrayList<File>();
        TenderAgreementVO tenderAgreement = new TenderAgreementVO();
        List<TenderAgreementVO> tenderAgreementsNid= createAgreementService.selectTenderAgreementByNid(nid);//居间协议
        /*******************************下载法大大合同************************************/
        //下载法大大协议--居间
        if(tenderAgreementsNid!=null && tenderAgreementsNid.size()>0){

            tenderAgreement = tenderAgreementsNid.get(0);
            if(tenderAgreement!=null){
                files = createFaddPDFImgFile(files,tenderAgreement);//下载脱敏
                if(files!=null && files.size()>0){
                    ZIPGenerator.generateZip(response, files, nid);
                }
                return;
            }

        }
        //下载平台老协议
        // 融通宝静态打印
        if (StringUtils.isNotEmpty(form.getProjectType()) && form.getProjectType().equals("13")) {
            Map<String, Object> contents = new HashMap<String, Object>();
            // 用户ID
            UserInfoVO userinfo = createAgreementService.getUsersInfoByUserId(userId);
            form.setUserId(userId + "");
            List<WebUserInvestListCustomizeVO> invest = createAgreementService.selectUserInvestList(form);
            if (invest != null && invest.size() > 0) {
                contents.put("investDeatil", invest.get(0));
            }
            ProjectCustomeDetailVO borrow = this.createAgreementService.selectProjectDetail(form.getBorrowNid());
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
                e.printStackTrace();
            }
        } else {
            // 判断是否是汇添金专属标的导出
            if (recordList1.size() != 1) {
                Map<String, Object> contents = new HashMap<String, Object>();
                // 查询借款人用户名
                DebtBorrowCommonCustomizeVO debtBorrowCommonCustomize = new DebtBorrowCommonCustomizeVO();
                // 借款编码
                debtBorrowCommonCustomize.setBorrowNidSrch(form.getBorrowNid());
                List<DebtBorrowCustomizeVO> recordList = createAgreementService.selectDebtBorrowList(debtBorrowCommonCustomize);
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
                // 用户投资列表
                List<WebUserInvestListCustomizeVO> tzList = createAgreementService.selectUserInvestList(form);
                if (tzList != null && tzList.size() > 0) {
                    contents.put("userInvest", tzList.get(0));
                }

                // 如果是分期还款，查询分期信息
                String borrowStyle = recordList.get(0).getBorrowStyle();// 还款模式
                if (borrowStyle != null) {
                    if ("month".equals(borrowStyle) || "principal".equals(borrowStyle)
                            || "endmonth".equals(borrowStyle)) {
                        ProjectRepayListBeanVO bean = new ProjectRepayListBeanVO();
                        bean.setUserId(userId.toString());
                        bean.setBorrowNid(form.getBorrowNid());
                        bean.setNid(form.getNid());
                        int recordTotal = this.createAgreementService.countProjectRepayPlanRecordTotal(bean);
                        if (recordTotal > 0) {
                            Paginator paginator = new Paginator(form.getPaginatorPage(), recordTotal);
                            List<WebProjectRepayListCustomizeVO> fqList = createAgreementService.selectProjectRepayPlanList(bean);
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
                    e.printStackTrace();
                }
            } else {
                List<BorrowCustomizeVO> recordList = recordList1;
                Map<String, Object> contents = new HashMap<String, Object>();
                // 借款人用户名
                if(recordList.size()>0){
                    contents.put("record", recordList.get(0));
                    // 借款人用户名
                    int userIds = recordList.get(0).getUserId();
                    UserInfoVO userInfo=createAgreementService.getUsersInfoByUserId(userIds);
                    String borrowUsername = userInfo.getTruename();
                    if(!(userId+"").equals(userInfo.getUserId()+"") ){
                        borrowUsername = borrowUsername.substring(0,borrowUsername.length()-1)+"*";
                    }
                    contents.put("borrowUsername", borrowUsername);
                    contents.put("idCard", userInfo.getIdcard());
                    contents.put("recoverLastDay", recordList.get(0).getRecoverLastDay());// 最后一笔的放款完成时间
                    contents.put("recoverAccount", form.getAccount());
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
                // 用户投资列表
                List<WebUserInvestListCustomizeVO> tzList = createAgreementService.selectUserInvestList(form);
                if (tzList != null && tzList.size() > 0) {
                    WebUserInvestListCustomizeVO userInvest = tzList.get(0);
                    if(((userId+"").equals(userInvest.getUserId()))){
                        userInvest.setRealName(userInvest.getRealName().substring(0,1)+"**");
                        userInvest.setUsername(userInvest.getUsername().substring(0,1)+"*****");
                        userInvest.setIdCard(userInvest.getIdCard().substring(0,4)+"**************");
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
                    //投资金额
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
                        // 还款方式为”按天计息，到期还本还息“：历史回报=投资金额*年化收益÷365*锁定期；
                        earnings = DuePrincipalAndInterestUtils.getDayInterest(account, borrowApr.divide(new BigDecimal("100")), borrowPeriod).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);
                    } else {
                        // 还款方式为”按月计息，到期还本还息“：历史回报=投资金额*年化收益÷12*月数；
                        earnings = DuePrincipalAndInterestUtils.getMonthInterest(account, borrowApr.divide(new BigDecimal("100")), borrowPeriod).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN);

                    }
                    contents.put("earnings", earnings);
                    if ("month".equals(borrowStyle) || "principal".equals(borrowStyle)
                            || "endmonth".equals(borrowStyle)) {
                        ProjectRepayListBeanVO bean = new ProjectRepayListBeanVO();
                        bean.setUserId(userId.toString());
                        bean.setBorrowNid(form.getBorrowNid());
                        bean.setNid(form.getNid());
                        int recordTotal = this.createAgreementService.countProjectRepayPlanRecordTotal(bean);
                        if (recordTotal > 0) {
                            Paginator paginator = new Paginator(form.getPaginatorPage(), recordTotal);
                            List<WebProjectRepayListCustomizeVO> fqList = createAgreementService.selectProjectRepayPlanList( bean);
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
                    e.printStackTrace();
                }

            }
        }
        logger.info(CreateAgreementController.class.toString(), "createAgreementPDF 导出PDF文件（汇盈金服互联网金融服务平台居间服务协议）");

    }

    public List<File> createFaddPDFImgFile(List<File> files,TenderAgreementVO tenderAgreement) {
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
        if(StringUtils.isNotBlank(pdfUrl)){
            //获取文件目录
            int index = pdfUrl.lastIndexOf("/");
            String pdfPath = pdfUrl.substring(0,index);
            //文件名称
            String pdfName = pdfUrl.substring(index+1);
            para.downloadPath = basePathPdf + "/" + pdfPath;
            para.sftpKeyFile = pdfName;
        }else if(StringUtils.isNotBlank(imgUrl)){
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
