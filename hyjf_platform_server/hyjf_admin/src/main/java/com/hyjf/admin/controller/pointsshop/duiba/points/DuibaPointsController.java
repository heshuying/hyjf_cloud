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
import com.hyjf.common.util.CommonUtils;
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

}
