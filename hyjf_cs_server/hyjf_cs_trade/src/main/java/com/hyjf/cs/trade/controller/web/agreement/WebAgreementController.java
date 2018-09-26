package com.hyjf.cs.trade.controller.web.agreement;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.trade.bean.newagreement.NewAgreementResultBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.controller.wechat.agreement.AgreementController;
import com.hyjf.cs.trade.service.newagreement.NewAgreementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * web端协议controller
 * @author yinhui
 * @version AgreementController.java
 */
@Api(tags = "web端-协议接口")
@RestController
@RequestMapping(value = "/hyjf-web/agreement")
public class WebAgreementController extends BaseTradeController {

    private static final Logger logger = LoggerFactory.getLogger(AgreementController.class);

    @Autowired
    private NewAgreementService agreementService;

    /**
     * 获得 协议模板pdf显示地址
     * @param aliasName
     * @return
     */
    @ApiOperation(value = "获得协议模板pdf显示地址", httpMethod = "POST", notes = "获得协议模板pdf显示地址")
    @PostMapping("/getAgreementPdfOrImg")
    public NewAgreementResultBean gotAgreementPdfOrImg(@RequestParam(value="aliasName",required = true) String aliasName) {
        return agreementService.getAgreementPdf(aliasName);
    }

    @ApiOperation(value = "协议名称-动态获得", httpMethod = "GET", notes = "协议名称-动态获得")
    @GetMapping("/getdisplayNameDynamic")
    public JSONObject getdisplayNameDynamic() {
        JSONObject jsonObject = agreementService.getdisplayNameDynamicMethod();
        return jsonObject;
    }
}
