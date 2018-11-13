package com.hyjf.cs.trade.controller.web.agreement;

import com.alibaba.fastjson.JSONObject;
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
    @ApiOperation(value = "账户中心-资产管理-当前持有-- 投资协议(实际为散标居间协议)下载", notes = "账户中心-资产管理-当前持有-- 投资协议(实际为散标居间协议)下载")
    @GetMapping("/intermediaryAgreementPDF")
    public JSONObject intermediaryAgreementPDF(HttpServletRequest request, HttpServletResponse response,UserInvestListBeanRequest form) {
        JSONObject jsonObject = createAgreementService.getIntermediaryAgreementPDFUrl(form.getNid());
        return jsonObject;
    }


}
