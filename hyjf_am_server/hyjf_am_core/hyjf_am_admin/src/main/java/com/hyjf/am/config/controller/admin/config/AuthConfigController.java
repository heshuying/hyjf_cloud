package com.hyjf.am.config.controller.admin.config;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfig;
import com.hyjf.am.config.dao.model.auto.Version;
import com.hyjf.am.config.dao.model.customize.HjhUserAuthConfigCustomize;
import com.hyjf.am.config.dao.model.customize.HjhUserAuthConfigLogCustomize;
import com.hyjf.am.config.service.AuthConfigService;
import com.hyjf.am.config.service.VersionConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminAuthConfigLogResponse;
import com.hyjf.am.response.admin.AdminAuthConfigResponse;
import com.hyjf.am.response.admin.AdminVersionResponse;
import com.hyjf.am.response.config.VersionConfigBeanResponse;
import com.hyjf.am.resquest.admin.AdminVersionRequest;
import com.hyjf.am.vo.admin.HjhUserAuthConfigCustomizeVO;
import com.hyjf.am.vo.admin.HjhUserAuthConfigLogCustomizeVO;
import com.hyjf.am.vo.admin.VersionVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * am-admin授权配置
 * @author by jijun on 2018/10/16.
 */
@RestController
@RequestMapping("/am-admin/configCenter/authConfig")
public class AuthConfigController extends BaseConfigController {

    @Autowired
    private AuthConfigService authConfigService;


    /**
     * 配置中心版本配置列表
     * @return
     */
    @GetMapping("/getAuthConfigList")
    public AdminAuthConfigResponse getAuthConfigList() {
        AdminAuthConfigResponse  response =new AdminAuthConfigResponse();
        //查询版本配置列表条数
        int recordTotal = authConfigService.getAuthConfigCount();
        List<HjhUserAuthConfigCustomize> recordList = authConfigService.getAuthConfigList();
        if (recordTotal > 0) {
            if(CollectionUtils.isNotEmpty(recordList)){
                response.setResultList(CommonUtils.convertBeanList(recordList, HjhUserAuthConfigCustomizeVO.class));
                response.setCount(recordTotal);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }


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
        String returnCode = "0";
        if (CollectionUtils.isNotEmpty(results)){
            response.setResultList(CommonUtils.convertBeanList(results,HjhUserAuthConfigLogCustomizeVO.class));
            response.setCount(count);
            response.setRtn(returnCode);
        }
        return response;



    }


}
