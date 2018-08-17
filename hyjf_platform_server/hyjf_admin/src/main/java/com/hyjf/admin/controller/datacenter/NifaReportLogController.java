/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.datacenter;

import com.hyjf.admin.beans.request.NifaReportLogRequestBean;
import com.hyjf.admin.beans.vo.NifaReportLogCustomizeVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.NifaReportLogService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.NifaReportLogResponse;
import com.hyjf.am.resquest.admin.NifaReportLogRequest;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nxl
 * @version NifaReportLogController, v0.1 2018/8/17 9:27
 */
@Api(value = "数据中心_互金协会报送日志", tags = "数据中心_互金协会报送日志")
@RestController
@RequestMapping("/hyjf-admin/datacenter/nifareportlog")
public class NifaReportLogController extends BaseController {
    @Autowired
    private NifaReportLogService nifaReportLogService;

    private static final String PERMISSIONS = "nifareportlog";

    /**
     * 互金字段定义列表
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "互金协会报送日志列表显示", notes = "互金协会报送日志列表显示")
    @PostMapping("/selectNifaReportLogList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<NifaReportLogCustomizeVO>> selectNifaReportLogList(@RequestBody NifaReportLogRequestBean requestBean){
        NifaReportLogRequest request = new NifaReportLogRequest();
        BeanUtils.copyProperties(requestBean, request);
        NifaReportLogResponse response = nifaReportLogService.selectNifaReportLogList(request);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<NifaReportLogCustomizeVO> nifaReportLogCustomizeVOList = new ArrayList<NifaReportLogCustomizeVO>();
        if(null!=response.getResultList()&&response.getResultList().size()>0){
            nifaReportLogCustomizeVOList = CommonUtils.convertBeanList(response.getResultList(),NifaReportLogCustomizeVO.class);
        }
        SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(null!=nifaReportLogCustomizeVOList&&nifaReportLogCustomizeVOList.size()>0){
            for(NifaReportLogCustomizeVO nifaReportLogCustomizeVO:nifaReportLogCustomizeVOList){
                //日期处理
                String strCreate = smp.format(nifaReportLogCustomizeVO.getCreateTime());
                nifaReportLogCustomizeVO.setUploadTime(strCreate);
            }
        }
        return new AdminResult<ListResult<NifaReportLogCustomizeVO>>(ListResult.build(nifaReportLogCustomizeVOList, response.getRecordTotal())) ;
    }
}
