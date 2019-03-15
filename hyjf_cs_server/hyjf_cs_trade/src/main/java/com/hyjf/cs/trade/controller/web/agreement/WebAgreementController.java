package com.hyjf.cs.trade.controller.web.agreement;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.ProtocolTemplateVO;
import com.hyjf.common.enums.ProtocolEnum;
import com.hyjf.cs.trade.bean.newagreement.NewAgreementResultBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.controller.wechat.agreement.AgreementController;
import com.hyjf.cs.trade.service.newagreement.NewAgreementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

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
    @ApiOperation(value = "web-获得协议模板pdf显示地址", httpMethod = "POST", notes = "web-获得协议模板pdf显示地址")
    @PostMapping("/getAgreementPdfOrImg")
    public NewAgreementResultBean gotAgreementPdfOrImg(@RequestParam(value="aliasName",required = true) String aliasName) {
        return agreementService.getAgreementPdf(aliasName);
    }

    @ApiOperation(value = "web-获得前台展示协议名称", httpMethod = "GET", notes = "web-协议名称-动态获得")
    @GetMapping("/getdisplayNameDynamic")
    public JSONObject getdisplayNameDynamic() {
        JSONObject jsonObject = agreementService.getdisplayNameDynamicMethod();
        return jsonObject;
    }
    /**
     * 获取所有在帮助中心显示的模板列表
     * add by nxl 20190313
     * PC 1.1.2
     * @return
     */
    @ApiOperation(value = "web-获取在帮助中心显示的协议模板名称", httpMethod = "GET", notes = "app-获取在帮助中心显示的协议模板名称")
    @GetMapping("/getShowProtocolTemp")
    public JSONObject getShowProtocolTemp() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        logger.info("*******************************协议名称-动态获得************************************");
        JSONObject jsonObject = new JSONObject();
        try {
            List<ProtocolTemplateVO> list = agreementService.selectAllShowProtocolTemplate();
            //是否在枚举中有定义
            if (CollectionUtils.isNotEmpty(list)) {
                for (ProtocolTemplateVO p : list) {
                    String protocolType = p.getProtocolType();
                    String alia = ProtocolEnum.getAlias(protocolType);
                    if (alia != null) {
                        map.put(alia, p.getDisplayName());
                    }
                }
            }
            jsonObject.put("status", "000");
            jsonObject.put("statusDesc", "成功");
            jsonObject.put("displayName", map);
        } catch (Exception e) {
            logger.error("协议查询异常：" + e);
            jsonObject.put("status", "99");
            jsonObject.put("statusDesc", "失败");
        }
        return jsonObject;
    }
}
