/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter;

import com.hyjf.admin.beans.request.BorrowRegistRequestBean;
import com.hyjf.admin.beans.response.BorrowRegistResponseBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.BorrowRegistService;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.common.util.CustomConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version BorrowRegistController, v0.1 2018/6/29 11:18
 */

@Api(value = "汇直投-标的备案接口", description = "汇直投-标的备案接口")
@RestController
@RequestMapping("/hyjf-admin/borrow_regist")
public class BorrowRegistController extends BaseController {
    @Autowired
    BorrowRegistService borrowRegistService;

    @Autowired
    AdminCommonService adminCommonService;

    /** 权限 */
    public static final String PERMISSIONS = "borrowregist";

    @ApiOperation(value = "标的备案初始化", notes = "标的备案初始化")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<BorrowRegistResponseBean> init(@RequestBody BorrowRegistRequestBean borrowRegistRequestBean) {
        BorrowRegistListRequest borrowRegistListRequest = new BorrowRegistListRequest();
        BeanUtils.copyProperties(borrowRegistRequestBean, borrowRegistListRequest);
        BorrowRegistResponseBean responseBean = borrowRegistService.getRegistList(borrowRegistListRequest);
        //项目类型
        List<BorrowProjectTypeVO> borrowProjectTypeList = borrowRegistService.selectBorrowProjectList();
        responseBean.setBorrowProjectTypeList(borrowProjectTypeList);
        //还款方式
        List<BorrowStyleVO> borrowStyleList = adminCommonService.selectBorrowStyleList();
        responseBean.setBorrowStyleList(borrowStyleList);
        //备案状态
        Map<String, String> borrowRegistStatusList = adminCommonService.getParamNameMap(CustomConstants.REGIST_STATUS);
        responseBean.setBorrowRegistStatusList(borrowRegistStatusList);

        return new AdminResult(responseBean);
    }

    @ApiOperation(value = "获取标的备案列表", notes = "获取标的备案列表")
    @PostMapping("/search")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
    public AdminResult<BorrowRegistResponseBean> search(@RequestBody BorrowRegistRequestBean borrowRegistRequestBean) {
        BorrowRegistListRequest borrowRegistListRequest = new BorrowRegistListRequest();
        BeanUtils.copyProperties(borrowRegistRequestBean, borrowRegistListRequest);
        BorrowRegistResponseBean responseBean = borrowRegistService.getRegistList(borrowRegistListRequest);
        return new AdminResult(responseBean);
    }

    @ApiOperation(value = "标的备案", notes = "标的备案")
    @GetMapping("/debt_regist/{borrowNid}")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSIONS_DEBT_REGIST)
    public AdminResult debtRegist(HttpServletRequest request, @PathVariable String borrowNid) {
        AdminSystemVO currUser = getUser(request);
        return borrowRegistService.updateBorrowRegist(borrowNid, currUser.getId(), currUser.getUsername());
    }
}
