package com.hyjf.cs.trade.controller.web.agreement;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.result.BaseResult;
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
import java.io.*;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
    @ApiOperation(value = "账户中心-资产管理-当前持有-- 投资协议(实际为散标居间协议)下载", notes = "账户中心-资产管理-当前持有-- 投资协议(实际为散标居间协议)下载")
    @GetMapping("/intermediaryAgreementPDF")
    public void intermediaryAgreementPDF(HttpServletRequest request, HttpServletResponse response,UserInvestListBeanRequest form) throws MalformedURLException {

        logger.info("web获取用户资产信息, nid is :{}", form.getNid());
        List<TenderAgreementVO> list=createAgreementService.getIntermediaryAgreementPDFUrl(form.getNid());
        if(list!=null&&list.size()>0){
            TenderAgreementVO agreementVO=list.get(0);
            if(agreementVO.getStatus()==3){
                // 下载网络文件
                int bytesum = 0;
                int byteread = 0;
                URL url = new URL(systemConfig.getBasePathurl()+systemConfig.getHyjfFtpBasepathPdf()+"/"+agreementVO.getPdfUrl());
                try {
                    URLConnection conn = url.openConnection();
                    InputStream inStream = conn.getInputStream();
                    FileOutputStream fs = new FileOutputStream("c:/abc.gif");

                    byte[] buffer = new byte[1204];
                    int length;
                    while ((byteread = inStream.read(buffer)) != -1) {
                        bytesum += byteread;
                        fs.write(buffer, 0, byteread);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                logger.info("下载成功");
            }else{
                logger.info("暂无可下载协议");
            }
        }else {
            logger.info("暂无可下载协议");
        }


    }


}
