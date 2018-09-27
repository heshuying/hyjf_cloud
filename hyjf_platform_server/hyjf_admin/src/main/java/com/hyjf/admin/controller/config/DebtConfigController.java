package com.hyjf.admin.controller.config;


import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.DebtConfigService;
import com.hyjf.am.response.config.DebtConfigResponse;
import com.hyjf.am.resquest.admin.DebtConfigRequest;
import com.hyjf.am.vo.config.DebtConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tanyy
 * @version DebtConfigController, v0.1 2018/9/27 14:28
 */
@RestController
@RequestMapping("/hyjf-admin/debtconfig")
public class DebtConfigController extends BaseController {

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

    /**
     *  修改债转配置
     * @return
     */
    @PostMapping("/update")
    public DebtConfigResponse update(@RequestBody DebtConfigRequest adminRequest) {
        logger.info("修改债转配置..." + JSONObject.toJSON(adminRequest));
        DebtConfigResponse  response =new DebtConfigResponse();
        debtConfigService.updateDebtConfig(adminRequest);
        return response;
    }

}
