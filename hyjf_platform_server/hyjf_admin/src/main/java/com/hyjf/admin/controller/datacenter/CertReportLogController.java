/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.datacenter;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.cert.CertReportLogService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CertReportLogResponse;
import com.hyjf.am.resquest.admin.CertReportLogRequestBean;
import com.hyjf.am.vo.hgreportdata.cert.CertLogVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 合规数据上报 CERT 应急中心上报记录
 */
@Api(value = "数据中心-应急中心上报记录", tags = "数据中心-应急中心上报记录")
@RestController
@RequestMapping("/hyjf-admin/datacenter/certreportlog")
public class CertReportLogController extends BaseController{

    @Autowired
    private CertReportLogService certReportLogService;
    Logger _log = LoggerFactory.getLogger(CertReportLogController.class);

    private static final String PERMISSIONS = "certlog";


    @ApiOperation(value = "应急中心日志列表显示", notes = "应急中心日志列表显示")
    @PostMapping("/selectCertReportLogList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<CertLogVO>> selectCertReportLogList(@RequestBody CertReportLogRequestBean requestBean){
        CertReportLogResponse response = certReportLogService.selectCertReportLogList(requestBean);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<CertLogVO> certLogVOS = new ArrayList<CertLogVO>();
        if(null!=response.getResultList()&&response.getResultList().size()>0){
            certLogVOS = CommonUtils.convertBeanList(response.getResultList(),CertLogVO.class);
        }
        return new AdminResult<>(ListResult.build(certLogVOS, response.getRecordTotal())) ;
    }

    @ApiOperation(value = "应急中心重新对账", notes = "应急中心重新对账")
    @PostMapping("/againReconciliation")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_CHULI)
    public AdminResult againReconciliation(@RequestBody CertReportLogRequestBean requestBean){
        int intFlg = certReportLogService.againReconciliation(requestBean.getId());
        if(intFlg<=0) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(SUCCESS, "重新对账成功");
    }
    @ApiOperation(value = "应急中心批量对账", notes = "应急中心批量对账")
    @PostMapping("/batchReconciliation")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public AdminResult batchReconciliation(@RequestBody CertReportLogRequestBean requestBean){
        CertReportLogResponse response = certReportLogService.selectCertReportLogList(requestBean);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        int intTotle = response.getRecordTotal();
        if(intTotle>1000){
            return new AdminResult<>(FAIL, "对账数量大于1000条，请重新筛选！");
        }
        int intFlg = certReportLogService.batchReconciliation(requestBean);
        if(intFlg<=0) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(SUCCESS, "批量对账成功");
    }
    @ApiOperation(value = "应急中心重新上报", notes = "应急中心重新上报")
    @PostMapping("/insertCertErrorLogByLogOrderId")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult insertCertErrorLogByLogOrderId(@RequestBody CertReportLogRequestBean requestBean){
        int intFlg = certReportLogService.insertCertErrorLogByLogOrderId(requestBean.getLogOrdId());
        if(intFlg<=0) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if(intFlg==2){
            return new AdminResult<>(SUCCESS, "该记录已存在！");
        }
        return new AdminResult<>(SUCCESS, "重新上报成功");
    }
}
