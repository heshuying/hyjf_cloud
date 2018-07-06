/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter;

import com.hyjf.admin.beans.request.BorrowFirstRequestBean;
import com.hyjf.admin.beans.response.BorrowFirstResponseBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.BorrowFirstService;
import com.hyjf.am.resquest.admin.BorrowFirstRequest;
import com.hyjf.am.vo.user.HjhInstConfigVO;
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
 * @version borrowFirstController, v0.1 2018/7/3 9:48
 */
@Api(value = "汇直投-借款初审接口")
@RestController
@RequestMapping("/hyjf-admin/borrow_first")
public class BorrowFirstController extends BaseController {
    @Autowired
    AdminCommonService adminCommonService;

    @Autowired
    BorrowFirstService borrowFirstService;

    private static final String PARAM_NAME = "hyjf_param_name:";

    @ApiOperation(value = "借款初审初始化", notes = "标的备案初始化")
    @PostMapping("/init")
    @ResponseBody
    public AdminResult init(@RequestBody BorrowFirstRequestBean borrowFirstRequestBean) {
        BorrowFirstRequest borrowFirstRequest = new BorrowFirstRequest();
        BeanUtils.copyProperties(borrowFirstRequestBean, borrowFirstRequest);
        BorrowFirstResponseBean responseBean = borrowFirstService.getBorrowFirstList(borrowFirstRequest);
        // 资产来源
        List<HjhInstConfigVO> hjhInstConfigList = adminCommonService.selectHjhInstConfigList();
        responseBean.setHjhInstConfigList(hjhInstConfigList);
        // 初审状态
        Map<String, String> borrowStatusList = adminCommonService.getParamNameMap(PARAM_NAME + CustomConstants.VERIFY_STATUS);
        responseBean.setBorrowStatusList(borrowStatusList);
        return new AdminResult(responseBean);
    }

    @ApiOperation(value = "获取借款初审列表", notes = "获取借款初审列表")
    @PostMapping("/get_borrow_first_list")
    @ResponseBody
    public AdminResult getBorrowFirstList(@RequestBody BorrowFirstRequestBean borrowFirstRequestBean) {
        BorrowFirstRequest borrowFirstRequest = new BorrowFirstRequest();
        BeanUtils.copyProperties(borrowFirstRequestBean, borrowFirstRequest);
        BorrowFirstResponseBean responseBean = borrowFirstService.getBorrowFirstList(borrowFirstRequest);
        return new AdminResult(responseBean);
    }

    @ApiOperation(value = "已交保证金详细画面", notes = "已交保证金详细画面")
    @GetMapping("/get_bail_info/{borrowNid}")
    @ResponseBody
    public AdminResult getBailInfo(@PathVariable String borrowNid) {
        return borrowFirstService.getBailInfo(borrowNid);
    }

    @ApiOperation(value = "交保证金", notes = "交保证金")
    @GetMapping("/insert_borrow_bail/{borrowNid}")
    @ResponseBody
    public AdminResult insertBorrowBail(HttpServletRequest request, @PathVariable String borrowNid) {
        //todo wangjun 取得当前用户信息 备用 后期修改
//        AdminSystemVO currUser = getUser(request);
        return borrowFirstService.insertBorrowBail(borrowNid, "123");
    }

    @ApiOperation(value = "获取发标信息", notes = "获取发标信息")
    @GetMapping("/get_borrow_fire_info/{borrowNid}")
    @ResponseBody
    public AdminResult getBorrowFireInfo(@PathVariable String borrowNid) {
        return borrowFirstService.getFireInfo(borrowNid);
    }

    @ApiOperation(value = "发标", notes = "发标")
    @GetMapping("/get_fire_info/{borrowNid}/{verifyStatus}/{ontime}")
    @ResponseBody
    public AdminResult updateBorrowFireInfo(@PathVariable String borrowNid, @PathVariable String verifyStatus, @PathVariable String ontime) {
        //todo wangjun ontime暂时必传 后面改用实体bean？
        return borrowFirstService.updateBorrowFireInfo(borrowNid, verifyStatus, ontime);
    }
}
