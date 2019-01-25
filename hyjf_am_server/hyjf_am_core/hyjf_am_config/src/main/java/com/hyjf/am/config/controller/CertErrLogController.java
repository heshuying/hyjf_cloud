package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.CertErrLog;
import com.hyjf.am.config.service.CertErrorLogService;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.config.CertErrLogResponse;
import com.hyjf.am.vo.hgreportdata.cert.CertErrLogVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 插入错误日志表
     * @return
     */
    @PostMapping("/insertCertErrorLog")
    public BooleanResponse insertCertErrorLog(@RequestBody CertErrLogVO logVO){
        BooleanResponse response = new BooleanResponse();
        try{
            CertErrLog log = CommonUtils.convertBean(logVO,CertErrLog.class);
            certErrorLogService.insertCertErrorLog(log);
            response.setResultBoolean(true);
        }catch (Exception e){
            response.setResultBoolean(false);
        }
        return response;
    }

    /**
     * 删除错误日志
     * @param oldLogOrdId
     * @return
     */
    @GetMapping("/deleteCertErrByLogOrdId/{oldLogOrdId}")
    public BooleanResponse deleteCertErrByLogOrdId(@PathVariable("oldLogOrdId") String oldLogOrdId){
        BooleanResponse response = new BooleanResponse();
        try{
            certErrorLogService.deleteCertErrByLogOrdId(oldLogOrdId);
            response.setResultBoolean(true);
        }catch (Exception e){
            response.setResultBoolean(false);
        }
        return response;
    }

    /**
     * 修改错误次数加1
     * @param logVO
     * @return
     */
    @PostMapping("/updateErrorLogCount")
    public BooleanResponse updateErrorLogCount(@RequestBody CertErrLogVO logVO){
        BooleanResponse response = new BooleanResponse();
        try{
            CertErrLog log = CommonUtils.convertBean(logVO,CertErrLog.class);
            certErrorLogService.updateErrorLogCount(log);
            response.setResultBoolean(true);
        }catch (Exception e){
            response.setResultBoolean(false);
        }
        return response;
    }
}
