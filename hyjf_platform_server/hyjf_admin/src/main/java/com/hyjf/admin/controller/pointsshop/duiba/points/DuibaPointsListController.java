/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.pointsshop.duiba.points;

import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.pointsshop.duiba.points.DuibaPointsListService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DuibaPointsResponse;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.vo.admin.DuibaPointsVO;
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
 * 兑吧积分明细表
 *
 * @author PC-LIUSHOUYI
 * @version DuibaPointsListController, v0.1 2019/5/29 9:46
 */
@Api(value = "积分商城-兑吧积分账户明细", tags = "积分商城-兑吧积分账户明细")
@RestController
@RequestMapping("/hyjf-admin/duiba/pointslist")
public class DuibaPointsListController extends BaseController {

    @Autowired
    DuibaPointsListService duibaPointsListService;

    @Autowired
    private AdminCommonService adminCommonService;

    private static final String PERMISSIONS = "dbpointslist";

    @ApiOperation(value = "兑吧积分账户明细查询", notes = "兑吧积分账户明细查询")
    @PostMapping("/selectDuibaPointsList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<DuibaPointsVO>> selectDuibaPointsList(@RequestBody DuibaPointsRequest requestBean) {
        DuibaPointsResponse response = duibaPointsListService.selectDuibaPointsList(requestBean);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<DuibaPointsVO> vos = new ArrayList<DuibaPointsVO>();
        if (null != response.getResultList() && response.getResultList().size() > 0) {
            vos = CommonUtils.convertBeanList(response.getResultList(), DuibaPointsVO.class);
        }
        return new AdminResult<>(ListResult.build(vos, response.getRecordTotal()));
    }

    /**
     * 获取积分类型下拉框数据
     *
     * @param
     * @return
     * @auth liushouyi
     */
    @ApiOperation(value = "积分类型下拉框数据", notes = "积分类型下拉框数据")
    @PostMapping(value = "/getIntegralType")
    public AdminResult<ListResult<DropDownVO>> getIntegralType() {
        List<DropDownVO> status = adminCommonService.getParamNameList(CustomConstants.INTEGRAL_TYPE);
        return new AdminResult<>(ListResult.build(status, 0));
    }

    /**
     * 获取积分业务名称下拉框数据
     *
     * @param
     * @return
     * @auth liushouyi
     */
    @ApiOperation(value = "积分业务名称下拉框数据", notes = "积分业务名称下拉框数据")
    @PostMapping(value = "/getIntegralBusinessName")
    public AdminResult<ListResult<DropDownVO>> getIntegralBusinessName() {
        List<DropDownVO> status = adminCommonService.getParamNameList(CustomConstants.INTEGRAL_BUSINESS_NAME);
        return new AdminResult<>(ListResult.build(status, 0));
    }

    /**
     * 获取审核状态下拉框数据
     *
     * @param
     * @return
     * @auth liushouyi
     */
    @ApiOperation(value = "审核状态下拉框数据", notes = "审核状态下拉框数据")
    @PostMapping(value = "/getAuditStatus")
    public AdminResult<ListResult<DropDownVO>> getAuditStatus() {
        List<DropDownVO> status = adminCommonService.getParamNameList(CustomConstants.AUDIT_STATUS);
        return new AdminResult<>(ListResult.build(status, 0));
    }

    /**
     * 获取操作类型下拉框数据
     *
     * @param
     * @return
     * @auth liushouyi
     */
    @ApiOperation(value = "操作类型下拉框数据", notes = "操作类型下拉框数据")
    @PostMapping(value = "/getOperationType")
    public AdminResult<ListResult<DropDownVO>> getOperationType() {
        List<DropDownVO> status = adminCommonService.getParamNameList(CustomConstants.OPERATION_TYPE);
        return new AdminResult<>(ListResult.build(status, 0));
    }

}
