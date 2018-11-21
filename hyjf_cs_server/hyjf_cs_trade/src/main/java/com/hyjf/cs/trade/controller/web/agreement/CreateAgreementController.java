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


    private UserInvestListBeanRequest createUserInvestListBeanRequest(String random, String projectType, String nid, String borrowNid) {
        UserInvestListBeanRequest form=new UserInvestListBeanRequest();
        form.setRandom(random);
        form.setProjectType(projectType);
        form.setBorrowNid(borrowNid);
        form.setNid(nid);
        return form;
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
