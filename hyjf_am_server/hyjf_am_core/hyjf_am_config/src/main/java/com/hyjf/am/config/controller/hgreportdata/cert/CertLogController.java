package com.hyjf.am.config.controller.hgreportdata.cert;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.CertLog;
import com.hyjf.am.config.service.hgreportdata.cert.CertLogService;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CertReportLogResponse;
import com.hyjf.am.resquest.admin.CertLogRequestBean;
import com.hyjf.am.vo.hgreportdata.cert.CertLogVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 合规数据上报 CERT 应急中心上报记录
 */
@RestController
@RequestMapping("/am-config/certLog")
public class CertLogController extends BaseConfigController {

    @Autowired
    private CertLogService certLogService;

    /**
     * 查找上报记录
     * @return
     */
    @GetMapping("/selectCertReportLogList")
    public CertReportLogResponse selectCertReportLogList() {
        CertReportLogResponse response =new CertReportLogResponse();
        List<CertLog> certLogs=certLogService.selectCertLog();
        if (!CollectionUtils.isEmpty(certLogs)) {
            List<CertLogVO> certLogVOList = CommonUtils.convertBeanList(certLogs,CertLogVO.class);
            response.setResultList(certLogVOList);
            response.setRtn(Response.SUCCESS);
            response.setMessage(Response.SUCCESS_MSG);
        }
        return response;
    }

    /**
     * 根据id查找报送日志
     * @return
     */
    @GetMapping("/selectCertReportLogById/{logId}")
    public CertReportLogResponse selectCertReportLogById(@PathVariable int logId) {
        CertReportLogResponse response =new CertReportLogResponse();
        CertLog certLog = certLogService.selectCertLogById(logId);
        if(null!=certLog){
            CertLogVO certLogVO = new CertLogVO();
            BeanUtils.copyProperties(certLog,certLogVO);
            response.setResult(certLogVO);
            response.setRtn(Response.SUCCESS);
            response.setMessage(Response.SUCCESS_MSG);
        }
        return response;
    }

    /**
     * 更新操作日志
     * @param request
     * @return
     */
    @PostMapping("/updateCertLog")
    public IntegerResponse updateCertLog(@RequestBody @Valid CertLogRequestBean request) {
        IntegerResponse response =new IntegerResponse();
        try{
            CertLog certLog = new CertLog();
            if(null!=request){
                BeanUtils.copyProperties(request,certLog);
                int intFlg = certLogService.updateCertLog(certLog);
                response.setRtn(Response.SUCCESS);
                response.setResultInt(intFlg);
            }
        }catch (Exception e){
            response.setRtn(Response.FAIL);
            response.setResultInt(0);
        }
        return response;
    }
}
