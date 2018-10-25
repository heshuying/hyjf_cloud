package com.hyjf.am.config.controller;


import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.service.DebtConfigService;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.config.DebtConfigLogResponse;
import com.hyjf.am.response.config.DebtConfigResponse;
import com.hyjf.am.resquest.admin.DebtConfigRequest;
import com.hyjf.am.vo.config.DebtConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tanyy
 * @version DebtConfigController, v0.1 2018/9/27 14:28
 */
@RestController
@RequestMapping("/am-admin/debtconfig")
public class DebtConfigController extends BaseConfigController{

    @Autowired
    private DebtConfigService debtConfigService;

    /**
     * 债转配置页面初始化查询
     * @return
     */
    @GetMapping("/init")
    public DebtConfigResponse init(){
        DebtConfigResponse response = new DebtConfigResponse();
        List<DebtConfigVO> feeConfigs = debtConfigService.getDebtConfig();
        response.setResultList(feeConfigs);
        return response;
    }

    /**
     * 修改债转配置
     * @return
     */
    @PostMapping("/update")
    public DebtConfigResponse update(@RequestBody DebtConfigRequest adminRequest) {
        logger.info("修改债转配置..." + JSONObject.toJSON(adminRequest));
        DebtConfigResponse  response =new DebtConfigResponse();
        debtConfigService.updateDebtConfig(adminRequest);
        return response;
    }

    @GetMapping("/countdebtconfiglogtotal")
    public IntegerResponse countDebtConfigLogTotal(){
        IntegerResponse response= debtConfigService.countDebtConfigLogTotal();
        return response;
    }
    @PostMapping("/getdebtconfigloglist")
    public DebtConfigLogResponse getDebtConfigLogList(@RequestBody DebtConfigRequest request){
        DebtConfigLogResponse response= debtConfigService.getDebtConfigLogList(request);
        return response;
    }
}
