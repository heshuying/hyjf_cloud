/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.wechat.agreement;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.trade.bean.newagreement.NewAgreementResultBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.newagreement.NewAgreementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author libin
 * @version AgreementController.java, v0.1 2018年7月27日 上午10:26:08
 */
@Api(tags = "weChat端-协议接口")
@RestController
@RequestMapping("/hyjf-wechat/wx/agreement")
public class AgreementController extends BaseTradeController{
	
    @Autowired
    private NewAgreementService agreementService;

    /**
     * 获得 协议模板pdf显示地址
     * @param aliasName
     * @return
     */
    @ApiOperation(value = "wechat-获得协议模板pdf显示地址", httpMethod = "POST", notes = "wechat-获得协议模板pdf显示地址")
    @PostMapping("/getAgreementPdfOrImg")
    public NewAgreementResultBean gotAgreementPdfOrImg(@RequestParam(value="aliasName",required = true) String aliasName) {
        return agreementService.setProtocolImg(aliasName);
    }

    @ApiOperation(value = "wechat-获得前台展示协议名称", httpMethod = "GET", notes = "wechat-获得前台展示协议名称")
    @GetMapping("/getdisplayNameDynamic")
    public JSONObject getdisplayNameDynamic() {
        JSONObject jsonObject = agreementService.getdisplayNameDynamicMethod();
        return jsonObject;
    }

}
