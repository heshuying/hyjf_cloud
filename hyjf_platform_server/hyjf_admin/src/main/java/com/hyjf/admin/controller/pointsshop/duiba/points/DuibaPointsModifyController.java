/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.pointsshop.duiba.points;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.pointsshop.duiba.points.DuibaPointsModifyService;
import com.hyjf.admin.service.pointsshop.duiba.points.DuibaPointsService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DuibaPointsModifyResponse;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.util.CustomConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    DuibaPointsService duibaPointsService;

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
        // 积分类型
        List<ParamNameVO> integralTypeList = duibaPointsModifyService.getParamNameList(CustomConstants.INTEGRAL_TYPE);
        if (null != integralTypeList && integralTypeList.size() > 0) {
            response.setIntegralTypeList(integralTypeList);
        }
        // 积分业务名称
        List<ParamNameVO> integralBusinessNameList = duibaPointsModifyService.getParamNameList(CustomConstants.INTEGRAL_BUSINESS_NAME);
        if (null != integralBusinessNameList && integralBusinessNameList.size() > 0) {
            response.setIntegralBusinessNameList(integralBusinessNameList);
        }
        // 审核状态
        List<ParamNameVO> auditStatusList = duibaPointsModifyService.getParamNameList(CustomConstants.AUDIT_STATUS);
        if (null != auditStatusList && auditStatusList.size() > 0) {
            response.setAuditStatusList(auditStatusList);
        }
        // 操作类型
        List<ParamNameVO> operationTypeList = duibaPointsModifyService.getParamNameList(CustomConstants.OPERATION_TYPE);
        if (null != operationTypeList && operationTypeList.size() > 0) {
            response.setOperationTypeList(operationTypeList);
        }
        return new AdminResult<DuibaPointsModifyResponse>(response);
    }

    @ApiOperation(value = "兑吧批量调整积分审核", notes = "兑吧批量调整积分审核")
    @PostMapping("/auditModifyPoints")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_AUDIT)
    public AdminResult auditModifyPoints(HttpServletRequest request, @RequestBody DuibaPointsRequest requestBean) {

        // 获取操作人id
        AdminSystemVO adminSystemVO = this.getUser(request);
        requestBean.setModifyName(adminSystemVO.getUsername());
        requestBean.setModifyId(adminSystemVO.getId());

        // 判断参数
        if (null == requestBean.getAuditStatus()) {
            return new AdminResult<>(FAIL, "未传送审核结果！");
        }

        // 后期对接工作流后调用工作流接口上送审批状态并获取最终审批结果

        // 审核不通过 更新修改明细表相关审核明细的状态
        if (2 == requestBean.getAuditStatus()) {
            boolean re = this.duibaPointsModifyService.updatePointsModifyStatus(requestBean);
            if (!re) {
                logger.error("积分调整审核不通过处理失败，订单号:" + requestBean.getOrderId());
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
        }

        // 审核通过
        if (1 == requestBean.getAuditStatus()) {
            // 调减的情况
            if (1 == requestBean.getModifyType()) {
                // 查询用户是否够扣分的
                boolean re = this.duibaPointsService.selectRemainPoints(requestBean);
                if (!re) {
                    return new AdminResult<>(FAIL, "审核失败（用户积分不足）");
                }
            }
            // 更新账户剩余积分
            boolean re = this.duibaPointsModifyService.updateDuibaPoints(requestBean);
            if (!re) {
                logger.error("审核通过后调整用户积分失败，订单号:" + requestBean.getOrderId());
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            // 积分调整成功后更新审核状态
            re = this.duibaPointsModifyService.updatePointsModifyStatus(requestBean);
            if (!re) {
                logger.error("积分调整审核通过处理失败，订单号:" + requestBean.getOrderId());
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            // 插入明细表
            re = this.duibaPointsModifyService.insertDuibaPoints(requestBean);
            if (!re) {
                logger.error("积分调整审核通过处理失败，订单号:" + requestBean.getOrderId());
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
        }
        return new AdminResult<>(SUCCESS, SUCCESS_DESC);
    }
}
