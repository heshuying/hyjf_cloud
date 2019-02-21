package com.hyjf.admin.controller.config;


import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.DebtConfigService;
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
    /** 查看权限 */
    public static final String PERMISSIONS = "debtconfig";
    /**
     * 债转配置页面初始化
     * @return
     */
    @ApiOperation(value = "债转配置页面查询", notes = "债转配置页面查询")
    @GetMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<DebtConfigVO> init(){
        AdminResult response = new AdminResult();
        List<DebtConfigVO> feeConfigs = debtConfigService.getDebtConfig();
        if(!CollectionUtils.isEmpty(feeConfigs)){
            response.setData(feeConfigs.get(0));
        }else{
            response.setData(new DebtConfigVO());
        }
        return response;
    }

    /**
     *  修改债转配置
     * @return
     */
    @ApiOperation(value = "债转配置页面更新", notes = "债转配置页面更新")
    @PostMapping("/update")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult<DebtConfigVO> update(@RequestBody DebtConfigRequest adminRequest, HttpServletRequest request) {
        logger.info("修改债转配置..." + JSONObject.toJSON(adminRequest));
        AdminSystemVO user = getUser(request);
        AdminResult  response =new AdminResult();
        adminRequest.setUpdateUser(Integer.valueOf(user.getId()));
        adminRequest.setUpdateUsername(user.getUsername());
        String ip = GetCilentIP.getIpAddr(request);
        adminRequest.setIpAddress(ip);
        debtConfigService.updateDebtConfig(adminRequest);
        return response;
    }

}
