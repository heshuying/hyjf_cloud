package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.CertLog;
import com.hyjf.am.config.service.CertErrorLogService;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.vo.hgreportdata.cert.CertLogVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 应急中心异常表
 */
@RestController
@RequestMapping("/am-config/certLog")
public class CertLogController extends BaseConfigController{

    @Autowired
    CertErrorLogService certErrorLogService;

    /**
     * 插入发送记录表
     * @return
     */
    @PostMapping("/insertCertLog")
    public BooleanResponse insertCertLog(@RequestBody CertLogVO logVO){
        BooleanResponse response = new BooleanResponse();
        try{
            CertLog log = CommonUtils.convertBean(logVO,CertLog.class);
            certErrorLogService.insertCertLog(log);
            response.setResultBoolean(true);
        }catch (Exception e){
            response.setResultBoolean(false);
        }
        return response;
    }
}
