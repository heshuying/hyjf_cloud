package com.hyjf.am.config.controller.admin.cert;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.CertErrLog;
import com.hyjf.am.config.dao.model.auto.CertLog;
import com.hyjf.am.config.service.cert.CertService;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CertErrorReportLogResponse;
import com.hyjf.am.response.admin.CertReportLogResponse;
import com.hyjf.am.resquest.admin.CertErrorReportLogRequestBean;
import com.hyjf.am.resquest.admin.CertReportLogRequestBean;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.vo.hgreportdata.cert.CertErrLogVO;
import com.hyjf.am.vo.hgreportdata.cert.CertLogVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 合规数据上报 CERT 应急中心上报记录
 */
@RestController
@RequestMapping("/am-config/cert")
public class CertController extends BaseConfigController {

    @Autowired
    private CertService certService;

    /**
     * 分页查询上报记录
     * @return
     */
    @PostMapping("/selectCertReportLogList")
    public CertReportLogResponse selectCertReportLogList(@RequestBody @Valid CertReportLogRequestBean request) {
        CertReportLogResponse response =new CertReportLogResponse();
        int recordTotal = certService.selectCertReportLogListCount(request);

        if (recordTotal > 0) {
            // 查询列表传入分页
            Paginator paginator;
            if(request.getPageSize() == 0){
                // 前台传分页
                paginator = new Paginator(request.getCurrPage(), recordTotal);
            } else {
                // 前台未传分页那默认 10
                paginator = new Paginator(request.getCurrPage(), recordTotal,request.getPageSize());
            }
            request.setPaginator(paginator);
            List<CertLog> recordList = certService.selectCertReportLogList(request);
            if(CollectionUtils.isNotEmpty(recordList)){
                response.setResultList(CommonUtils.convertBeanList(recordList, CertLogVO.class));
                response.setRecordTotal(recordTotal);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }

    /**
     * 分页查询上报记录
     * @return
     */
    @PostMapping("/selectCertErrorReportLogList")
    public CertErrorReportLogResponse selectCertErrorReportLogList(@RequestBody @Valid CertErrorReportLogRequestBean request) {
        CertErrorReportLogResponse response =new CertErrorReportLogResponse();
        int recordTotal = certService.selectCertErrorReportLogListCount(request);

        if (recordTotal > 0) {
            // 查询列表传入分页
            Paginator paginator;
            if(request.getPageSize() == 0){
                // 前台传分页
                paginator = new Paginator(request.getCurrPage(), recordTotal);
            } else {
                // 前台未传分页那默认 10
                paginator = new Paginator(request.getCurrPage(), recordTotal,request.getPageSize());
            }
            request.setPaginator(paginator);
            List<CertErrLog> recordList = certService.selectCertErrorReportLogList(request);
            if(CollectionUtils.isNotEmpty(recordList)){
                response.setResultList(CommonUtils.convertBeanList(recordList, CertErrLogVO.class));
                response.setRecordTotal(recordTotal);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }

    /**
     * 重新跑批
     * @param id
     * @return
     */
    @PostMapping("/updateCertErrorCount")
    public IntegerResponse updateCertErrorCount(@RequestBody @Valid Integer id) {
        IntegerResponse response =new IntegerResponse();
        try{
            certService.updateCertErrorCount(id);
            response.setRtn(Response.SUCCESS);
            response.setResultInt(1);
        }catch (Exception e){
            response.setRtn(Response.FAIL);
            response.setResultInt(0);
        }
        return response;
    }

    /**
     * 修改对账状态（重新对账）add by nxl
     * @param id
     * @return
     */
    @GetMapping("/againReconciliation")
    public IntegerResponse againReconciliation(@RequestBody @Valid Integer id) {
        IntegerResponse response =new IntegerResponse();
        try{
            certService.updateCertLogById(id);
            response.setRtn(Response.SUCCESS);
            response.setResultInt(1);
        }catch (Exception e){
            response.setRtn(Response.FAIL);
            response.setResultInt(0);
        }
        return response;
    }

    /**
     * 批量修改对账状态 add by nxl
     * @param request
     * @return
     */
    @PostMapping("/batchReconciliation")
    public IntegerResponse batchReconciliation(@RequestBody @Valid CertReportLogRequestBean request) {
        IntegerResponse response =new IntegerResponse();
        response.setResultInt(0);
        response.setRtn(Response.FAIL);
        request.setPaginator(null);
        List<CertLog> recordList = certService.selectCertReportLogList(request);
        List<Integer> ids = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(recordList)){
            for(CertLog certLog:recordList){
                ids.add(certLog.getId());
            }
        }
        if(CollectionUtils.isNotEmpty(ids)){
            CertLog certLogs = new CertLog();
            // 设置为初始状态
            certLogs.setQueryResult(0);
            int updFlg =certService.batchUpdateCertLogByIds(ids,certLogs);
            if(updFlg>0){
                response.setResultInt(1);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }
    /**
     * 批量修改对账状态 add by nxl
     * @param logOrderId
     * @return
     */
    @GetMapping("/insertCertErrorLogByLogOrderId")
    public IntegerResponse insertCertErrorLogByLogOrderId(@RequestBody @Valid String logOrderId){
        IntegerResponse response =new IntegerResponse();
        response.setResultInt(0);
        response.setRtn(Response.FAIL);
        int intflg =certService.insertCertErrorLogByLogOrderId(logOrderId);
        if(intflg>0){
            response.setResultInt(1);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
}
