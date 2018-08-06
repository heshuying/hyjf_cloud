package com.hyjf.admin.controller.manager;

import com.hyjf.admin.beans.request.BorrowStyleRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowStyleService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminBorrowStyleResponse;
import com.hyjf.am.resquest.admin.AdminBorrowStyleRequest;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by xiehuili on 2018/7/12.
 */
@Api(value = "配置中心还款方式",description ="配置中心还款方式")
@RestController
@RequestMapping("/hyjf-admin/config/borrowstyle")
public class BorrowStyleController extends BaseController {

    //权限名称
    private static final String PERMISSIONS = "borrowstyle";
    @Autowired
    private BorrowStyleService borrowStyleService;

    @ApiOperation(value = "配置中心还款方式", notes = "查询配置中心还款方式")
    @RequestMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<BorrowStyleVO>> borrowStyleInit(@RequestBody BorrowStyleRequestBean borrowStyleRequestBean) {
        AdminBorrowStyleRequest request = new AdminBorrowStyleRequest();
        //可以直接使用
        BeanUtils.copyProperties(borrowStyleRequestBean, request);
        AdminBorrowStyleResponse response=borrowStyleService.borrowStyelInit(request);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<ListResult<BorrowStyleVO>>(ListResult.build(response.getResultList(), response.getRecordTotal())) ;
    }
    @ApiOperation(value = "配置中心还款方式", notes = "保证金配置还款方式")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public AdminResult  searchBorrowStyleInfo(@RequestBody BorrowStyleRequestBean borrowStyleRequestBean) {
        AdminBorrowStyleRequest request = new AdminBorrowStyleRequest();
        //可以直接使用
        BeanUtils.copyProperties(borrowStyleRequestBean, request);
        AdminBorrowStyleResponse adminResponse= borrowStyleService.searchBorrowStyleInfo(request);
        if (adminResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(adminResponse)) {
            return new AdminResult<>(FAIL, adminResponse.getMessage());
        }
        return new AdminResult<BorrowStyleVO>(adminResponse.getResult()) ;
    }

    @ApiOperation(value = "配置中心还款方式", notes = "还款方式添加")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertBorrowStyle( @RequestBody BorrowStyleRequestBean from) {
        AdminBorrowStyleRequest request = new AdminBorrowStyleRequest();
        //可以直接使用
        BeanUtils.copyProperties(from, request);
        ModelAndView model =new ModelAndView();
        //表单字段校验
//        this.validatorFieldCheck(model, request);
        AdminBorrowStyleResponse result = borrowStyleService.insertBorrowStyle(request);
        if(result==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(result)) {
            return new AdminResult<>(FAIL, result.getMessage());

        }
        return new AdminResult<>();
    }
    @ApiOperation(value = "配置中心还款方式", notes = "还款方式修改")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateBorrowStyle(HttpServletRequest request,  @RequestBody BorrowStyleRequestBean from) {
        AdminBorrowStyleRequest adminRequest = new AdminBorrowStyleRequest();
        //可以直接使用
        BeanUtils.copyProperties(from, adminRequest);
        //测试联调需要放开
//        req.setUserId(Integer.valueOf(this.getUser(request).getId()));
        AdminBorrowStyleResponse result = borrowStyleService.updateBorrowStyle(adminRequest);
        if(result==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(result)) {
            return new AdminResult<>(FAIL, result.getMessage());

        }
        return new AdminResult<>();
    }
    @ApiOperation(value = "配置中心还款方式", notes = "还款方式修改状态")
    @PostMapping("/statusAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult modifyBorrowStyle(@RequestBody BorrowStyleRequestBean from) {
        AdminBorrowStyleResponse prs =new AdminBorrowStyleResponse();
        if(from.getId() != null){
            Integer id = from.getId() ;
            prs = borrowStyleService.modifyBorrowStyle(id);
        }
        if(prs==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(prs)) {
            return new AdminResult<>(FAIL, prs.getMessage());

        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "配置中心保证金配置", notes = "保证金配置删除")
    @PostMapping("/deleteAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteBorrowStyle(@RequestBody String id) {
        AdminBorrowStyleResponse prs =new AdminBorrowStyleResponse();
        if(StringUtils.isNotBlank(id)){
            Integer id2 = Integer.valueOf(id);
            prs = borrowStyleService.deleteBorrowStyle(id2);
        }
        if(prs==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(prs)) {
            return new AdminResult<>(FAIL, prs.getMessage());

        }
        return new AdminResult<>();
    }

    /**
     * 画面校验
     *
     * @param modelAndView
     * @param form
     */
    private void validatorFieldCheck(ModelAndView modelAndView, AdminBorrowStyleRequest form) {
        // 权限检查用字段的校验
        // ValidatorFieldCheckUtil.validateAlphaAndMaxLength(modelAndView,
        // "permission", form.getChargeType(), 20, true);
        // 权限名字
//		ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "permissionName", form.getNid(), 20, true);
        // 权限功能按钮是否重复
        if (this.borrowStyleService.isExistsPermission(form)) {
//            // String message = ValidatorFieldCheckUtil.getErrorMessage("",
//            // "权限");
//            // ValidatorFieldCheckUtil.validateSpecialError(modelAndView,
//            // "permission-repeat", form.getChargeType(), message);
        }
    }
}
