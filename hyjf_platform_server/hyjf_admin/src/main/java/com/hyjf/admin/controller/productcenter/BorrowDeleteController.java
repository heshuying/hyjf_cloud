/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.BorrowDeleteService;
import com.hyjf.am.vo.admin.BorrowDeleteConfirmCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRegistCancelConfirmCustomizeVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 标的删除
 * @author hesy
 */

@Api(value = "产品中心-汇直投-标的删除", tags = "产品中心-汇直投-标的删除")
@RestController
@RequestMapping("/hyjf-admin/borrow_delete")
public class BorrowDeleteController extends BaseController {
    @Autowired
    BorrowDeleteService borrowDeleteService;

    @Autowired
    AdminCommonService adminCommonService;

    /** 权限 */
    public static final String PERMISSIONS = "borrow";

    /**
     * 标的删除确认页面
     */
    @ApiOperation(value = "标的删除确认页面", notes = "标的删除确认页面")
    @GetMapping("/delete_confirm/{borrowNid}")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<BorrowRegistCancelConfirmCustomizeVO> deleteConfirm(HttpServletRequest request, @PathVariable String borrowNid) {
        BorrowDeleteConfirmCustomizeVO responseBean = borrowDeleteService.selectDeleteConfirm(borrowNid);
        if(responseBean == null){
            return new AdminResult(BaseResult.FAIL, "未查询到标的信息");
        }
        return new AdminResult(responseBean);
    }

    @ApiOperation(value = "标的删除", notes = "标的删除")
    @ApiImplicitParam(name = "borrowNid", value = "标的编号", required = true, dataType = "String", paramType = "path")
    @GetMapping("/delete/{borrowNid}")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult delete(HttpServletRequest request, @PathVariable String borrowNid){
        AdminSystemVO currUser = getUser(request);
        if(currUser == null){
            return new AdminResult(BaseResult.FAIL, "未获取到当前登录用户信息");
        }
        return borrowDeleteService.borrowDelete(borrowNid, currUser.getId(), currUser.getUsername());
    }
}
