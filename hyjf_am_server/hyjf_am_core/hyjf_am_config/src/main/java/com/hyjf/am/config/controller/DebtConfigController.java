package com.hyjf.am.config.controller;


import com.hyjf.am.config.service.DebtConfigService;
import com.hyjf.am.response.config.DebtConfigResponse;
import com.hyjf.am.vo.config.DebtConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tanyy
 * @version DebtConfigController, v0.1 2018/9/27 14:28
 */
@RestController
@RequestMapping("/am-config/debtconfig")
public class DebtConfigController{

    @Autowired
    private DebtConfigService debtConfigService;

    /**
     * 债转配置页面初始化
     * @return
     */
    @GetMapping("/init")
    public DebtConfigResponse init(){
        DebtConfigResponse response = new DebtConfigResponse();
        List<DebtConfigVO> feeConfigs = debtConfigService.getDebtConfig();
        if(!CollectionUtils.isEmpty(feeConfigs)){
            response.setResult(feeConfigs.get(0));
        }
        return response;
    }


    @GetMapping("/getDebtConfigList")
    public DebtConfigResponse getDebtConfigList(){
        DebtConfigResponse response = new DebtConfigResponse();
        List<DebtConfigVO> debtConfigVOList = debtConfigService.getDebtConfig();
        if(!CollectionUtils.isEmpty(debtConfigVOList)){
            response.setResultList(debtConfigVOList);
        }
        return response;
    }


}
