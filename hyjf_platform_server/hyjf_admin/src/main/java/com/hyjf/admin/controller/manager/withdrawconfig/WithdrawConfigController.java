package com.hyjf.admin.controller.manager.withdrawconfig;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.config.WithdrawConfigService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.config.WithdrawRuleConfigResponse;
import com.hyjf.am.response.admin.config.WithdrawTimeConfigResponse;
import com.hyjf.am.vo.admin.config.WithdrawRuleConfigVO;
import com.hyjf.am.vo.admin.config.WithdrawTimeConfigVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.user.HjhUserAuthConfigVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.DateUtils;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 提现配置（假期时间配置/提现规则配置）
 * @author jun
 * @version WithdrawConfigController, v0.1 2019/4/18 14:17
 */
@Api(tags = "配置中心-提现配置（假期时间配置/提现规则配置）")
@RestController
@RequestMapping("/hyjf-admin/manager/withdrawconfig")
public class WithdrawConfigController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(WithdrawConfigController.class);
    //提现配置列表权限控制
    public static final String PERMISSIONS = "withdrawconfig";

    @Autowired
    private WithdrawConfigService withdrawConfigService;


    @ApiOperation(value = "提现规则配置列表", notes = "提现规则配置列表")
    @GetMapping(value = "/getWithdrawRuleConfigList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<WithdrawRuleConfigVO>> getWithdrawRuleConfigList() {
        WithdrawRuleConfigResponse response = withdrawConfigService.getWithdrawRuleConfigList();
        if (response == null || !Response.SUCCESS.equals(response.getRtn())){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<ListResult<WithdrawRuleConfigVO>>(ListResult.build(response.getResultList(), response.getCount()));
    }


    @ApiOperation(value = "提现规则配置详情", notes = "提现规则配置详情")
    @GetMapping(value = "/getWithdrawRuleConfigById/{id}")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<WithdrawRuleConfigVO>  getWithdrawRuleConfigById(@PathVariable Integer id){
        if (id == null){
            return new AdminResult<>(FAIL, "传入id不能为空！");
        }
        WithdrawRuleConfigResponse response = withdrawConfigService.getWithdrawRuleConfigById(id);
        if (response == null || !Response.SUCCESS.equals(response.getRtn())){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(response.getResult());
    }



    @ApiOperation(value = "提现规则配置修改", notes = "提现规则配置修改")
    @PostMapping(value = "/updateWithdrawRuleConfig")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateWithdrawRuleConfig(HttpServletRequest request, @RequestBody WithdrawRuleConfigVO form){

        if (!Validator.isTimeFormat(form.getStartTime())){
            return new AdminResult<>(FAIL, "工作日开始时间格式不正确，请重新输入！");
        }
        if (!Validator.isTimeFormat(form.getEndTime())){
            return new AdminResult<>(FAIL, "工作日结束时间格式不正确，请重新输入！");
        }

        AdminSystemVO loginUser = getUser(request);
        form.setUpdateBy(loginUser.getUsername());
        form.setUpdateTime(GetDate.getDate());
        int updateRecord = withdrawConfigService.updateWithdrawRuleConfig(form);
        if(updateRecord>0){
            return new AdminResult<>(SUCCESS, SUCCESS_DESC);
        }
        return new AdminResult<>(FAIL, FAIL_DESC);
    }


    @ApiOperation(value = "假期时间配置列表", notes = "假期时间配置列表")
    @GetMapping(value = "/withdrawTimeConfigList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<WithdrawTimeConfigVO>> getWithdrawTimeConfigList() {
        WithdrawTimeConfigResponse response = withdrawConfigService.getWithdrawTimeConfigList();
        if (response == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }else if(!Response.isSuccess(response)){
            return new AdminResult<>(FAIL, response.getMessage());
        }

        return new AdminResult<ListResult<WithdrawTimeConfigVO>>(ListResult.build(response.getResultList(), response.getCount()));
    }

}
