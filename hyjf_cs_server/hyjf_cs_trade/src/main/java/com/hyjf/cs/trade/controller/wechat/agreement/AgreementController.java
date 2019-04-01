/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.wechat.agreement;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.ProtocolTemplateVO;
import com.hyjf.common.enums.ProtocolEnum;
import com.hyjf.cs.trade.bean.newagreement.NewAgreementResultBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.newagreement.NewAgreementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 获取所有在帮助中心显示的模板列表
     * add by nxl 20190313
     * PC 1.1.2
     * @return
     */
    @ApiOperation(value = "wechat-获取在帮助中心显示的协议模板名称", httpMethod = "GET", notes = "wechat-获取在帮助中心显示的协议模板名称")
    @GetMapping("/getShowProtocolTemp")
    public JSONObject getShowProtocolTemp() {
        Map<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
        logger.info("*******************************协议名称-获取在帮助中心显示的协议模板名称************************************");
        JSONObject jsonObject = new JSONObject();
        try {
            List<ProtocolTemplateVO> list = agreementService.selectAllShowProtocolTemplate();
            //是否在枚举中有定义
            if (null!=list&&list.size()>0) {
                for (ProtocolTemplateVO p : list) {
                    String protocolType = p.getProtocolType();
                    String alia = ProtocolEnum.getAlias(protocolType);
                    if (alia != null) {
                        linkedHashMap.put(alia, p.getDisplayName());
                    }
                }
            }
            jsonObject.put("status", "000");
            jsonObject.put("statusDesc", "成功");
            jsonObject.put("displayName", linkedHashMap);
        } catch (Exception e) {
            logger.error("协议查询异常：" + e);
            jsonObject.put("status", "99");
            jsonObject.put("statusDesc", "失败");
        }
        return jsonObject;
    }
}
