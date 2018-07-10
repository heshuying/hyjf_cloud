/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter;

import com.hyjf.admin.beans.request.BorrowFullRequestBean;
import com.hyjf.admin.beans.response.BorrowFullResponseBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.BorrowFullService;
import com.hyjf.am.resquest.admin.BorrowFullRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author wangjun
 * @version BorrowFullController, v0.1 2018/7/6 9:31
 */
@Api(value = "汇直投-借款复审接口")
@RestController
@RequestMapping("/hyjf-admin/borrow_full")
public class BorrowFullController extends BaseController {
    @Autowired
    BorrowFullService borrowFullService;

    @ApiOperation(value = "借款复审初始化/获取列表共用接口", notes = "借款复审初始化/获取列表共用接口")
    @PostMapping("/get_borrow_full_list")
    @ResponseBody
    public AdminResult getBorrowFullList(@RequestBody BorrowFullRequestBean borrowFullRequestBean) {
        BorrowFullRequest borrowFullRequest = new BorrowFullRequest();
        BeanUtils.copyProperties(borrowFullRequestBean, borrowFullRequest);
        BorrowFullResponseBean responseBean = borrowFullService.getBorrowFullList(borrowFullRequest);
        return new AdminResult(responseBean);
    }

    @ApiOperation(value = "借款复审详细信息", notes = "借款复审详细信息")
    @PostMapping("/get_full_info")
    @ResponseBody
    public AdminResult getFullInfo(@RequestBody BorrowFullRequestBean borrowFullRequestBean) {
        BorrowFullRequest borrowFullRequest = new BorrowFullRequest();
        BeanUtils.copyProperties(borrowFullRequestBean, borrowFullRequest);
        return borrowFullService.getFullInfo(borrowFullRequest);
    }

    @ApiOperation(value = "复审提交", notes = "复审提交")
    @PostMapping("/update_borrow_full")
    @ResponseBody
    public AdminResult updateBorrowFull(HttpServletRequest request, @RequestBody @Valid BorrowFullRequestBean borrowFullRequestBean) {
        //todo wangjun 取得当前用户信息 备用 后期修改
//        AdminSystemVO currUser = getUser(request);
        BorrowFullRequest borrowFullRequest = new BorrowFullRequest();
        BeanUtils.copyProperties(borrowFullRequestBean, borrowFullRequest);
        borrowFullRequest.setCurrUserId("123");
        borrowFullRequest.setCurrUserName("admin");
        return borrowFullService.updateBorrowFull(borrowFullRequest);
    }

    @ApiOperation(value = "流标", notes = "流标")
    @GetMapping("/update_borrow_over/{borrowNid}")
    @ResponseBody
    public AdminResult updateBorrowOver(HttpServletRequest request, @PathVariable String borrowNid) {
        //todo wangjun 取得当前用户信息 备用 后期修改
//        AdminSystemVO currUser = getUser(request);
        BorrowFullRequest borrowFullRequest = new BorrowFullRequest();
        borrowFullRequest.setBorrowNidSrch(borrowNid);
        borrowFullRequest.setCurrUserId("123");
        borrowFullRequest.setCurrUserName("admin");
        return borrowFullService.updateBorrowOver(borrowFullRequest);
    }
}