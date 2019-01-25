package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.CertErrLog;
import com.hyjf.am.config.dao.model.auto.GatewayApiConfig;
import com.hyjf.am.config.service.CertErrorLogService;
import com.hyjf.am.config.service.GatewayApiConfigService;
import com.hyjf.am.response.config.CertErrLogResponse;
import com.hyjf.am.response.config.GatewayApiConfigResponse;
import com.hyjf.am.vo.config.GatewayApiConfigVO;
import com.hyjf.am.vo.hgreportdata.cert.CertErrLogVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 应急中心异常表
 */
@RestController
@RequestMapping("/am-config/certError")
public class CertErrLogController extends BaseConfigController{

    @Autowired
    CertErrorLogService certErrorLogService;

    /**
     * 检索上报失败的记录
     * @return
     */
    @GetMapping("/getCertErrLogs")
    public CertErrLogResponse getCertErrLogs(){
        CertErrLogResponse response = new CertErrLogResponse();

        List<CertErrLogVO> configVOs = null;
        List<CertErrLog> configs = certErrorLogService.getCertErrLogs();
        if (!CollectionUtils.isEmpty(configs)) {
            configVOs = CommonUtils.convertBeanList(configs,CertErrLogVO.class);
        }
        response.setResultList(configVOs);
        return response;
    }
}
