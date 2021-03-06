/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter;

import com.hyjf.admin.beans.request.BorrowFullRequestBean;
import com.hyjf.admin.beans.response.BorrowFullInfoResponseBean;
import com.hyjf.admin.beans.response.BorrowFullResponseBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowFullService;
import com.hyjf.am.resquest.admin.BorrowFullRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author wangjun
 * @version BorrowFullController, v0.1 2018/7/6 9:31
 */
@Api(value = "产品中心-汇直投-借款复审", tags = "产品中心-汇直投-借款复审")
@RestController
@RequestMapping("/hyjf-admin/borrow_full")
public class BorrowFullController extends BaseController {
    @Autowired
    BorrowFullService borrowFullService;

    /** 权限 */
    public static final String PERMISSIONS = "borrowfull";

    /**
     * 借款复审初始化
     *
     * @param borrowFullRequestBean
     * @return
     */
    @ApiOperation(value = "借款复审初始化", notes = "借款复审初始化")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
    public AdminResult<BorrowFullResponseBean> init(@RequestBody BorrowFullRequestBean borrowFullRequestBean) {
        BorrowFullRequest borrowFullRequest = new BorrowFullRequest();
        BeanUtils.copyProperties(borrowFullRequestBean, borrowFullRequest);
        BorrowFullResponseBean responseBean = borrowFullService.getBorrowFullList(borrowFullRequest);
        return new AdminResult(responseBean);
    }

    /**
     * 查询列表接口
     *
     * @param borrowFullRequestBean
     * @return
     */
    @ApiOperation(value = "查询列表接口", notes = "查询列表接口")
    @PostMapping("/search")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<BorrowFullResponseBean> getBorrowFullList(@RequestBody BorrowFullRequestBean borrowFullRequestBean) {
        BorrowFullRequest borrowFullRequest = new BorrowFullRequest();
        BeanUtils.copyProperties(borrowFullRequestBean, borrowFullRequest);
        BorrowFullResponseBean responseBean = borrowFullService.getBorrowFullList(borrowFullRequest);
        return new AdminResult(responseBean);
    }

    /**
     * 借款复审详细信息
     *
     * @param borrowFullRequestBean
     * @return
     */
    @ApiOperation(value = "借款复审详细信息", notes = "借款复审详细信息")
    @PostMapping("/get_full_info")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.BORROW_FULL)
    public AdminResult<BorrowFullInfoResponseBean> getFullInfo(@RequestBody BorrowFullRequestBean borrowFullRequestBean) {
        BorrowFullRequest borrowFullRequest = new BorrowFullRequest();
        BeanUtils.copyProperties(borrowFullRequestBean, borrowFullRequest);
        return borrowFullService.getFullInfo(borrowFullRequest);
    }

    /**
     * 复审提交
     *
     * @param request
     * @param borrowFullRequestBean
     * @return
     */
    @ApiOperation(value = "复审提交", notes = "复审提交")
    @PostMapping("/update_borrow_full")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.BORROW_FULL)
    public AdminResult updateBorrowFull(HttpServletRequest request, @RequestBody @Valid BorrowFullRequestBean borrowFullRequestBean) {
        AdminSystemVO currUser = getUser(request);
        if(currUser == null){
            return new AdminResult(BaseResult.FAIL, "未获取到当前登录用户信息");
        }
        BorrowFullRequest borrowFullRequest = new BorrowFullRequest();
        BeanUtils.copyProperties(borrowFullRequestBean, borrowFullRequest);
        borrowFullRequest.setCurrUserId(currUser.getId());
        borrowFullRequest.setCurrUserName(currUser.getUsername());
        return borrowFullService.updateBorrowFull(borrowFullRequest);
    }

    //流标为汇付使用功能，原代码有此功能但是没有用到，暂时删除
//    @ApiOperation(value = "流标", notes = "流标")
//    @GetMapping("/update_borrow_over/{borrowNid}")
//    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.BORROW_OVER)
//    public AdminResult updateBorrowOver(HttpServletRequest request, @PathVariable String borrowNid) {
//        AdminSystemVO currUser = getUser(request);
//        if(currUser == null){
//            return new AdminResult(BaseResult.FAIL, "未获取到当前登录用户信息");
//        }
//        BorrowFullRequest borrowFullRequest = new BorrowFullRequest();
//        borrowFullRequest.setBorrowNidSrch(borrowNid);
//        borrowFullRequest.setCurrUserId(currUser.getId());
//        borrowFullRequest.setCurrUserName(currUser.getUsername());
//        return borrowFullService.updateBorrowOver(borrowFullRequest);
//    }
}