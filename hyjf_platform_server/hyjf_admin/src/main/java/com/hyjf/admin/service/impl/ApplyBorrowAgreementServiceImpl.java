package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.request.ApplyBorrowAgreementRequestBean;
import com.hyjf.admin.beans.request.BorrowRepayAgreementRequestBean;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.BaseClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.service.ApplyBorrowAgreementService;
import com.hyjf.admin.service.AutoTenderExceptionService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.response.trade.ApplyBorrowAgreementResponse;
import com.hyjf.am.response.trade.ApplyBorrowInfoResponse;
import com.hyjf.am.response.trade.BorrowRepayAgreementResponse;
import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.vo.admin.BorrowRepayAgreementCustomizeVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.common.file.FavFTPUtil;
import com.hyjf.common.file.FileUtil;
import com.hyjf.common.file.SFTPParameter;
import com.hyjf.common.file.ZIPGenerator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @version ApplyAgreementServiceImpl, v0.1 2018/8/9 16:51
 * @Author: Zha Daojian
 */
@Service
public class ApplyBorrowAgreementServiceImpl implements ApplyBorrowAgreementService {
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


    public static final String BASE_URL = "http://AM-ADMIN/am-trade/applyBorrowAgreement";

    /**协议申请列表*/
    public static final String BORROW_AGREEMENT_LIST_URL = BASE_URL + "/getApplyBorrowAgreementList";

    /**协议申请列表页count*/
    public static final String BORROW_AGREEMENT_COUNT_URL = BASE_URL + "/getApplyBorrowAgreementCount";

    /**协议申请明细*/
    public static final String ADD_BORROW_AND_INFO_URL= BASE_URL + "/getApplyBorrowInfoDetail";


    /**
     * 查询协议申请列表
     * @author Zha Daojian
     * @date 2019/5/6 9:39
     * @param request
     * @return com.hyjf.admin.common.result.AdminResult
     **/
    @Override
    public AdminResult getApplyBorrowAgreementList(ApplyBorrowAgreementRequest request){
        AdminResult result = new AdminResult();
        ApplyBorrowAgreementRequestBean bean = new ApplyBorrowAgreementRequestBean();
        ApplyBorrowAgreementResponse response = baseClient.postExe(BORROW_AGREEMENT_COUNT_URL, request, ApplyBorrowAgreementResponse.class);
        Integer count = response.getCount();
        if (count > 0) {
            response = baseClient.postExe(BORROW_AGREEMENT_LIST_URL, request, ApplyBorrowAgreementResponse.class);
            List<ApplyBorrowAgreementVO> list = response.getResultList();
            bean.setRecordList(list);
        }
        bean.setTotal(count);
        result.setData(bean);
        return result;
    }


    /**
     *  协议申请明细列表页
     * @author Zha Daojian
     * @date 2019/5/6 9:39
     * @param request
     * @return com.hyjf.admin.common.result.AdminResult
     **/
    @Override
    public AdminResult getApplyBorrowInfoDetail(ApplyBorrowAgreementRequest request){
        AdminResult result = new AdminResult();
        if(StringUtils.isEmpty(request.getBorrowNid())){
            return new AdminResult(BaseResult.FAIL, "项目编号不能为空");
        }
        // 根据标的编号查询标的详情
        ApplyBorrowInfoResponse response = baseClient.postExe(ADD_BORROW_AND_INFO_URL, request, ApplyBorrowInfoResponse.class);
        ApplyBorrowInfoVO borrowVO = response.getResult();
        if (borrowVO == null) {
            return new AdminResult(BaseResult.FAIL, "未找到对应的编号的标");
        }else{
            result.setData(borrowVO);
        }
        return result;
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
        String password = systemConfig.getFtpPassword();
        String username = systemConfig.getFtpUsername();
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
