package com.hyjf.am.config.controller.admin.config;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.HjhUserAuthConfig;
import com.hyjf.am.config.dao.model.customize.HjhUserAuthConfigCustomize;
import com.hyjf.am.config.dao.model.customize.HjhUserAuthConfigLogCustomize;
import com.hyjf.am.config.service.AuthConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminAuthConfigCustomizeResponse;
import com.hyjf.am.response.admin.AdminAuthConfigLogResponse;
import com.hyjf.am.response.admin.AdminAuthConfigResponse;
import com.hyjf.am.vo.admin.HjhUserAuthConfigCustomizeVO;
import com.hyjf.am.vo.admin.HjhUserAuthConfigLogCustomizeVO;
import com.hyjf.am.vo.user.HjhUserAuthConfigVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * am-admin授权配置
 * @author by jijun on 2018/10/16.
 */
@RestController
@RequestMapping("/am-config/configCenter/authConfig")
public class AuthConfigController extends BaseConfigController {

    @Autowired
    private AuthConfigService authConfigService;


    /**
     * 配置中心版本配置列表
     * @return
     */
    @GetMapping("/getAuthConfigList")
    public AdminAuthConfigCustomizeResponse getAuthConfigList() {
        AdminAuthConfigCustomizeResponse response =new AdminAuthConfigCustomizeResponse();
        //查询版本配置列表条数
        int recordTotal = authConfigService.getAuthConfigCount();
        if (recordTotal > 0) {
            List<HjhUserAuthConfigCustomize> recordList = authConfigService.getAuthConfigList();
            if(CollectionUtils.isNotEmpty(recordList)){
                response.setResultList(CommonUtils.convertBeanList(recordList, HjhUserAuthConfigCustomizeVO.class));
                response.setCount(recordTotal);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }

    /**
     * 授权配置操作记录
     * @param request
     * @return
     */
    @PostMapping("/getAuthConfigLogList")
    public AdminAuthConfigLogResponse getAuthConfigLogList(@RequestBody HjhUserAuthConfigLogCustomizeVO request){
        AdminAuthConfigLogResponse response = new AdminAuthConfigLogResponse();
        int count = authConfigService.getAuthConfigLogCount();
        if(count==0){
            return response;
        }
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count);
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }

        List<HjhUserAuthConfigLogCustomize> results=authConfigService.getAuthConfigLogList(request);
        if (CollectionUtils.isNotEmpty(results)){
            response.setResultList(CommonUtils.convertBeanList(results,HjhUserAuthConfigLogCustomizeVO.class));
            response.setCount(count);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 授权配置详情
     * @param id
     * @return
     */
    @GetMapping("/getAuthConfigById/{id}")
    public AdminAuthConfigResponse getAuthConfigById(@PathVariable Integer id){
        AdminAuthConfigResponse response = new AdminAuthConfigResponse();
        HjhUserAuthConfigCustomize authConfig = authConfigService.getAuthConfigById(id);
        if (Validator.isNotNull(authConfig)){
            response.setResult(CommonUtils.convertBean(authConfig,HjhUserAuthConfigVO.class));
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 修改授权配置
     * @param form
     * @return
     */
    @PostMapping("/updateAuthConfig")
    public Integer updateAuthConfig(@RequestBody HjhUserAuthConfigVO form){
      return authConfigService.updateAuthConfig(CommonUtils.convertBean(form,HjhUserAuthConfig.class));
    }

}
