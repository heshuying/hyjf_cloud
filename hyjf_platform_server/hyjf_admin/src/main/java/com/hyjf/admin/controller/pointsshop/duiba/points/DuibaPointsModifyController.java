/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.pointsshop.duiba.points;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.pointsshop.duiba.points.DuibaPointsModifyService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DuibaPointsModifyResponse;
import com.hyjf.am.response.admin.DuibaPointsResponse;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.vo.admin.DuibaPointsModifyVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 兑吧积分调整表
 *
 * @author PC-LIUSHOUYI
 * @version DuibaPointsModifyController, v0.1 2019/5/29 9:47
 */
@Api(value = "积分商城-兑吧积分账户修改明细", tags = "积分商城-兑吧积分账户修改明细")
@RestController
@RequestMapping("/hyjf-admin/duiba/pointslistmodify")
public class DuibaPointsModifyController extends BaseController {

    @Autowired
    DuibaPointsModifyService duibaPointsModifyService;

    private static final String PERMISSIONS = "dbpointslistmodify";

    @ApiOperation(value = "兑吧积分账户修改明细", notes = "兑吧积分账户修改明细")
    @PostMapping("/selectDuibaPointsModifyList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<DuibaPointsModifyResponse> selectDuibaPointsModifyList(@RequestBody DuibaPointsRequest requestBean) {
        DuibaPointsModifyResponse response = duibaPointsModifyService.selectDuibaPointsModifyList(requestBean);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }

        List<ParamNameVO> integralTypeList = duibaPointsModifyService.getParamNameList(CustomConstants.INTEGRAL_TYPE);
        if (null != integralTypeList && integralTypeList.size() > 0) {
            response.setIntegralTypeList(integralTypeList);
        }
        List<ParamNameVO> integralBusinessNameList = duibaPointsModifyService.getParamNameList(CustomConstants.INTEGRAL_BUSINESS_NAME);
        if (null != integralBusinessNameList && integralBusinessNameList.size() > 0) {
            response.setIntegralBusinessNameList(integralBusinessNameList);
        }
        List<ParamNameVO> auditStatusList = duibaPointsModifyService.getParamNameList(CustomConstants.AUDIT_STATUS);
        if (null != auditStatusList && auditStatusList.size() > 0) {
            response.setAuditStatusList(auditStatusList);
        }
        List<ParamNameVO> operationTypeList = duibaPointsModifyService.getParamNameList(CustomConstants.OPERATION_TYPE);
        if (null != operationTypeList && operationTypeList.size() > 0) {
            response.setOperationTypeList(operationTypeList);
        }
        return new AdminResult<DuibaPointsModifyResponse>(response);
    }



}
