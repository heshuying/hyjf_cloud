package com.hyjf.am.config.controller.app.version;

import com.hyjf.am.config.dao.model.auto.Version;
import com.hyjf.am.config.service.AppVersionService;
import com.hyjf.am.response.config.VersionConfigBeanResponse;
import com.hyjf.am.vo.config.VersionVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lisheng
 * @version VersionController, v0.1 2018/10/26 15:00
 */
@RestController
@RequestMapping("/am-config/appversion")
public class VersionController {
    @Autowired
    AppVersionService appVersionService;

    @RequestMapping("/getNewVersionByType/{type}")
    public VersionConfigBeanResponse getNewVersionByType(@PathVariable Integer type){
        VersionConfigBeanResponse response = new VersionConfigBeanResponse();
        Version version = appVersionService.getNewVersionByType(type);
        if (null!= version){
            VersionVO versionVO = CommonUtils.convertBean(version, VersionVO.class);
            response.setResult(versionVO);
        }
        return response;
    }


    @RequestMapping("/getUpdateversion/{type}/{isupdate}/{versionStr}")
    public VersionConfigBeanResponse getUpdateversion(@PathVariable Integer type,@PathVariable Integer isupdate,@PathVariable String versionStr ){
        VersionConfigBeanResponse response = new VersionConfigBeanResponse();
        Version version = appVersionService.getUpdateversion(type,isupdate,versionStr);
        if (null!= version){
            VersionVO versionVO = CommonUtils.convertBean(version, VersionVO.class);
            response.setResult(versionVO);
        }
        return response;
    }
}
