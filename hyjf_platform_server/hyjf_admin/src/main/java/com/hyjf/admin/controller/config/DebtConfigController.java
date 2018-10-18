package com.hyjf.admin.controller.config;


import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.DebtConfigService;
import com.hyjf.am.response.config.DebtConfigResponse;
import com.hyjf.am.resquest.admin.DebtConfigRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.DebtConfigVO;
import com.hyjf.common.util.GetCilentIP;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author tanyy
 * @version DebtConfigController, v0.1 2018/9/27 14:28
 */
@Api(value = "汇转让-债转配置", tags = "汇转让-债转配置")
@RestController
@RequestMapping("/hyjf-admin/debtconfig")
public class DebtConfigController extends BaseController {

    @Autowired
    private DebtConfigService debtConfigService;

    /**
     * 债转配置页面初始化
     * @return
     */
    @ApiOperation(value = "债转配置页面查询", notes = "债转配置页面查询")
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
    @ApiOperation(value = "债转配置页面更新", notes = "债转配置页面更新")
    @PostMapping("/update")
    public DebtConfigResponse update(@RequestBody DebtConfigRequest adminRequest, HttpServletRequest request) {
        logger.info("修改债转配置..." + JSONObject.toJSON(adminRequest));
        AdminSystemVO user = getUser(request);
        DebtConfigResponse  response =new DebtConfigResponse();
        adminRequest.setUpdateUser(Integer.valueOf(user.getId()));
        adminRequest.setUpdateUsername(user.getUsername());
        String ip = GetCilentIP.getIpAddr(request);
        adminRequest.setIpAddress(ip);
        debtConfigService.updateDebtConfig(adminRequest);
        return response;
    }

}
