/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.pointsshop.duiba.points;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.pointsshop.duiba.points.DuibaPointsService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DuibaPointsUserResponse;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.vo.admin.DuibaPointsUserVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 兑吧积分账户列表
 *
 * @author PC-LIUSHOUYI
 * @version DuibaPointsController, v0.1 2019/5/29 9:46
 */
@Api(value = "积分商城-兑吧积分账户", tags = "积分商城-兑吧积分账户")
@RestController
@RequestMapping("/hyjf-admin/duiba/points")
public class DuibaPointsController extends BaseController {

    @Autowired
    DuibaPointsService duibaPointsService;

    private static final String PERMISSIONS = "dbpoints";

    @ApiOperation(value = "兑吧积分账户查询", notes = "兑吧积分账户查询")
    @PostMapping("/selectDuibaPointsUser")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<DuibaPointsUserVO>> selectDuibaPointsUser(@RequestBody DuibaPointsRequest requestBean) {
        DuibaPointsUserResponse response = duibaPointsService.selectDuibaPointsUser(requestBean);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<DuibaPointsUserVO> vos = new ArrayList<DuibaPointsUserVO>();
        if (null != response.getResultList() && response.getResultList().size() > 0) {
            vos = CommonUtils.convertBeanList(response.getResultList(), DuibaPointsUserVO.class);
        }
        return new AdminResult<>(ListResult.build(vos, response.getRecordTotal()));
    }

    @ApiOperation(value = "兑吧批量调整积分", notes = "兑吧批量调整积分")
    @PostMapping("/modifyPointsByUserList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult modifyPointsByUserList(HttpServletRequest request, @RequestBody DuibaPointsRequest requestBean) {

        // 获取操作人id
        AdminSystemVO adminSystemVO = this.getUser(request);
        requestBean.setModifyName(adminSystemVO.getUsername());
        requestBean.setModifyId(adminSystemVO.getId());
        // 判断参数
        if (null == requestBean.getModifyType()) {
            return new AdminResult<>(FAIL, "请勾选调整类型！");
        }
        if (null == requestBean.getModifyPoints() || 0 == requestBean.getModifyPoints()) {
            return new AdminResult<>(FAIL, "请填写调整积分数额！");
        }
        if(null == requestBean.getUserIdList() || requestBean.getUserIdList().size() <= 0) {
            return new AdminResult<>(FAIL, "请勾选调整用户！");
        }

        // 调减的情况
        if (1 == requestBean.getModifyType()) {
            // 查询这些用户是否够扣分的
            boolean re = this.duibaPointsService.selectRemainPoints(requestBean);
            if (!re) {
                return new AdminResult<>(FAIL, "批量调整积分记录生成失败（存在用户积分不足）！");
            }
        }

        // 记录到积分调整列表
        // 后期接入工作流后、在审批表创建相应审批节点
        boolean re = this.duibaPointsService.insertPointsModifyList(requestBean);
        if (!re) {
            return new AdminResult<>(FAIL, "批量调整积分记录生成失败！");
        }
        return new AdminResult<>(SUCCESS, SUCCESS_DESC);
    }

}
