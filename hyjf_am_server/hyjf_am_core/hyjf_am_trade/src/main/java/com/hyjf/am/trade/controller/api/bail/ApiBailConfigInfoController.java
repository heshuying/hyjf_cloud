/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.api.bail;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BailConfigInfoCustomizeResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.api.bail.ApiBailConfigInfoService;
import com.hyjf.am.vo.admin.BailConfigInfoCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author PC-LIUSHOUYI
 * @version ApiBailConfigInfoController, v0.1 2018/9/29 10:52
 */
@RestController
@RequestMapping("/am-trade/bail_config")
public class ApiBailConfigInfoController extends BaseController {

    @Autowired
    ApiBailConfigInfoService apiBailConfigInfoService;

    /**
     * 根据资产来源查询保证金配置
     *
     * @param instCode
     * @return
     */
    @GetMapping(value = "/select_bail_config_by_instcode/{instCode}")
    public BailConfigInfoCustomizeResponse selectBailConfigByInstCode(@PathVariable String instCode) {
        logger.info("request:" + JSONObject.toJSON(instCode));
        BailConfigInfoCustomizeResponse response = new BailConfigInfoCustomizeResponse();
        String returnCode = Response.FAIL;
        BailConfigInfoCustomizeVO bailConfigInfoCustomizeVOList = apiBailConfigInfoService.selectBailConfigByInstCode(instCode);
        if (null != bailConfigInfoCustomizeVOList) {
            response.setResult(bailConfigInfoCustomizeVOList);
            returnCode = Response.SUCCESS;
        }
        response.setRtn(returnCode);
        return response;
    }
}
