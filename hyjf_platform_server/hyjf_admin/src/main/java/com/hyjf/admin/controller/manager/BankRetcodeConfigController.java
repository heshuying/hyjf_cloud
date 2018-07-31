package com.hyjf.admin.controller.manager;

import com.hyjf.admin.beans.request.BankRetcodeConfigRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BankRetcodeConfigService;
import com.hyjf.admin.utils.ValidatorFieldCheckUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.resquest.admin.AdminBankRetcodeConfigRequest;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author by xiehuili on 2018/7/17.
 */
@Api(value = "配置中心返回码配置",description ="配置中心返回码配置")
@RestController
@RequestMapping("/hyjf-admin/config/bankretcodeconfig")
public class BankRetcodeConfigController extends BaseController {

    //权限名称
    private static final String PERMISSIONS = "bankretcodeconfig";
    @Autowired
    private BankRetcodeConfigService bankRetcodeConfigService;

    @ApiOperation(value = "配置中心返回码配置", notes = "查询配置中心返回码配置")
    @RequestMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult bankRetcodeConfigInit(@RequestBody BankRetcodeConfigRequestBean bankRetcodeConfigRequestBean) {
        AdminBankRetcodeConfigRequest adminRequest= new AdminBankRetcodeConfigRequest();
        //可以直接使用
        BeanUtils.copyProperties(bankRetcodeConfigRequestBean, adminRequest);
        BankReturnCodeConfigResponse response =bankRetcodeConfigService.selectBankRetcodeListByPage(adminRequest);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<ListResult<BankReturnCodeConfigVO>>(ListResult.build(response.getResultList(), response.getResultList().size())) ;
    }

    @ApiOperation(value = "配置中心返回码配置--条件查询", notes = "查询配置中心返回码配置--条件查询")
    @RequestMapping("/searchAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult bankRetcodeConfigSearch(@RequestBody BankRetcodeConfigRequestBean bankRetcodeConfigRequestBean) {
        AdminBankRetcodeConfigRequest adminRequest= new AdminBankRetcodeConfigRequest();
        //可以直接使用
        BeanUtils.copyProperties(bankRetcodeConfigRequestBean, adminRequest);
        BankReturnCodeConfigResponse response =bankRetcodeConfigService.selectBankRetcodeListByPage(adminRequest);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<ListResult<BankReturnCodeConfigVO>>(ListResult.build(response.getResultList(), response.getResultList().size())) ;
    }

    @ApiOperation(value = "配置中心返回码配置", notes = "查询配置中心返回码配置详情页面")
    @RequestMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)//权限    todo
    public AdminResult bankRetcodeConfigInfo(@RequestBody BankRetcodeConfigRequestBean bankRetcodeConfigRequestBean) {
        BankReturnCodeConfigResponse response = null;
        AdminBankRetcodeConfigRequest adminRequest= new AdminBankRetcodeConfigRequest();
        //可以直接使用
        adminRequest.setId(bankRetcodeConfigRequestBean.getId());
        if (adminRequest.getId() != null) {
            // 根据条件判断该条数据在数据库中是否存在
            boolean isExists = this.bankRetcodeConfigService.isExistsRecord(adminRequest);

            // 没有添加权限 同时 也没能检索出数据的时候异常
            if (!isExists) {
                //todo
//                Subject currentUser = SecurityUtils.getSubject();
//                currentUser.checkPermission(ShiroConstants.PERMISSION_ADD);
            }
            // 根据主键检索数据
            response = this.bankRetcodeConfigService.selectBankRetcodeConfigInfo(adminRequest);
        }
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<BankReturnCodeConfigVO>(response.getResult()) ;
    }
    @ApiOperation(value = "配置中心返回码配置", notes = "返回码配置添加")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertBankReturnCodeConfig( @RequestBody BankRetcodeConfigRequestBean from) {
        BankReturnCodeConfigResponse response = null;
        AdminBankRetcodeConfigRequest adminRequest= new AdminBankRetcodeConfigRequest();
        BeanUtils.copyProperties(from,adminRequest);
        ModelAndView mv =new ModelAndView();
        // 画面验证
        this.validatorFieldCheck(mv, adminRequest);
        // 返回码功能按钮是否重复
        if (this.bankRetcodeConfigService.isExistsReturnCode(adminRequest)) {
            String message = ValidatorFieldCheckUtil.getErrorMessage("retCode.repeat", "返回码");
            ValidatorFieldCheckUtil.validateSpecialError(mv, "retCode", adminRequest.getRetCode(), message);
        }
        // 数据检索
        if (ValidatorFieldCheckUtil.hasValidateError(mv)) {
            // 失败返回
            return new AdminResult<>(FAIL, "校验失败");
        }
        // 插入数据----之后的分页查询，通过前端调用
        if (StringUtils.isNotEmpty(adminRequest.getRetCode())) {
            response = this.bankRetcodeConfigService.insertBankReturnCodeConfig(adminRequest);
        }
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "配置中心返回码配置", notes = "返回码配置修改")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateBankReturnCodeConfig( @RequestBody BankRetcodeConfigRequestBean from) {
        BankReturnCodeConfigResponse response = null;
        AdminBankRetcodeConfigRequest adminRequest= new AdminBankRetcodeConfigRequest();
        BeanUtils.copyProperties(from,adminRequest);
        ModelAndView mv =new ModelAndView();
        // 画面验证
        this.validatorFieldCheck(mv, adminRequest);
        // 返回码功能按钮是否重复
        if (this.bankRetcodeConfigService.isExistsReturnCode(adminRequest)) {
            String message = ValidatorFieldCheckUtil.getErrorMessage("retCode.repeat", "返回码");
            ValidatorFieldCheckUtil.validateSpecialError(mv, "retCode", adminRequest.getRetCode(), message);
        }
        // 数据检索
        if (ValidatorFieldCheckUtil.hasValidateError(mv)) {
            // 失败返回
            return new AdminResult<>(FAIL, "校验失败");
        }
        // 插入数据----之后的分页查询，通过前端调用
        if (StringUtils.isNotEmpty(adminRequest.getRetCode())) {
            response = this.bankRetcodeConfigService.updateBankReturnCodeConfig(adminRequest);
        }
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<>();
    }

    /**
     * 画面校验
     *
     * @param modelAndView
     * @param form
     */
    private void validatorFieldCheck(ModelAndView modelAndView, AdminBankRetcodeConfigRequest form) {
        // 权限检查用字段的校验
        ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "txCode", form.getTxCode(), 20, true);
        // 权限名字
        ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "retCode", form.getRetCode(), 20, true);

    }
}
