package com.hyjf.admin.controller.manager;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BusinessNameMgService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.BusinessNameMgResponse;
import com.hyjf.am.resquest.config.BusinessNameMgRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.WorkNameVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 业务名称管理
 * @Author: yinhui
 * @Date: 2019/4/15 9:47
 * @Version 1.0
 */
@Api(tags = "配置中心-业务名称管理")
@RestController
@RequestMapping("/hyjf-admin/manager/businessname")
public class BusinessNameMgController  extends BaseController {

    //权限名称
    private static final String PERMISSIONS = "businessname";

    private Logger logger = LoggerFactory.getLogger(BusinessNameMgController.class);

    @Autowired
    private BusinessNameMgService businessNameMgService;

    @ApiOperation(value = "查询", notes = "查询")
    @PostMapping("/search")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_VIEW , ShiroConstants.PERMISSION_SEARCH})
    public AdminResult search(@RequestBody BusinessNameMgRequest request) {
        BusinessNameMgResponse response = businessNameMgService.searchBusinessName(request);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<ListResult<WorkNameVO>>(ListResult.build(response.getResultList(), response.getCount())) ;

    }

    @ApiOperation(value = "添加", notes = "添加")
    @PostMapping("/insert")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD )
    public AdminResult insert(@RequestBody BusinessNameMgRequest request, HttpServletRequest httpServletRequest) {

        boolean uq = businessNameMgService.searchBusinessNameUq(request,"insert");
        if(!uq){
            return new AdminResult<>(FAIL, "业务名称重复");
        }

        AdminSystemVO user = getUser(httpServletRequest);
        request.setUsername(user.getTruename());
        boolean flag = businessNameMgService.insertBusinessName(request);
        if (!flag) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult();
    }

    @ApiOperation(value = "编辑业务名称管理详情界面", notes = "编辑业务名称管理详情界面")
    @PostMapping("/info")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_VIEW , ShiroConstants.PERMISSION_SEARCH})
    public AdminResult info(@RequestBody BusinessNameMgRequest request) {
        if(request.getId() == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        WorkNameVO vo = businessNameMgService.findBusinessNameById(request.getId());
        if (vo == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult(vo);
    }

    @ApiOperation(value = "修改", notes = "修改")
    @PostMapping("/update")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_MODIFY})
    public AdminResult update(@RequestBody BusinessNameMgRequest request, HttpServletRequest httpServletRequest) {

        if(request.getId() == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        boolean uq = businessNameMgService.searchBusinessNameUq(request,"update");
        if(!uq){
            return new AdminResult<>(FAIL, "业务名称重复");
        }

        AdminSystemVO user = getUser(httpServletRequest);
        request.setUsername(user.getTruename());
        boolean flag = businessNameMgService.updateBusinessName(request);
        if (!flag) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult();
    }

    @ApiOperation(value = "修改状态", notes = "修改状态")
    @PostMapping("/updatestate")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_MODIFY })
    public AdminResult updatestate(@RequestBody BusinessNameMgRequest request, HttpServletRequest httpServletRequest) {
        if(request.getId() == null || request.getStatus() == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        AdminSystemVO user = getUser(httpServletRequest);
        request.setUsername(user.getTruename());
        boolean flag = businessNameMgService.updateStatusBusinessName(request);
        if (!flag) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult();
    }

    @ApiOperation(value = "查询业务名称", notes = "查询业务名称")
    @PostMapping("/searchbusinessname")
    public AdminResult searchBusinessNameList(@RequestBody BusinessNameMgRequest request) {
        logger.info("查询可用的业务名称，业务名称："+request.getBsname());
        BusinessNameMgResponse response = businessNameMgService.searchBusinessName(request);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<BusinessNameMgResponse>(response) ;

    }
}
