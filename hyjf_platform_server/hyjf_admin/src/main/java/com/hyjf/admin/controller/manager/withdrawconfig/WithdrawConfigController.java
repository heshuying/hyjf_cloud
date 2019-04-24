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
import com.hyjf.am.resquest.admin.config.WithdrawRuleConfigRequest;
import com.hyjf.am.resquest.admin.config.WithdrawTimeConfigRequest;
import com.hyjf.am.vo.admin.config.WithdrawRuleConfigVO;
import com.hyjf.am.vo.admin.config.WithdrawTimeConfigVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
    @PostMapping(value = "/getWithdrawRuleConfigList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<WithdrawRuleConfigVO>> getWithdrawRuleConfigList(@RequestBody WithdrawRuleConfigRequest request) {
        WithdrawRuleConfigResponse response = withdrawConfigService.getWithdrawRuleConfigList(request);
        if (response == null || !Response.isSuccess(response)){
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
        if (response == null || !Response.isSuccess(response)){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(response.getResult());
    }



    @ApiOperation(value = "提现规则配置修改", notes = "提现规则配置修改")
    @PostMapping(value = "/updateWithdrawRuleConfig")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateWithdrawRuleConfig(HttpServletRequest request, @RequestBody WithdrawRuleConfigVO form){

       /* if (!Validator.isTimeFormat(form.getStartTime())){
            return new AdminResult<>(FAIL, "工作日开始时间格式不正确，请重新输入！");
        }*/
        /*if (!Validator.isTimeFormat(form.getEndTime())){
            return new AdminResult<>(FAIL, "工作日结束时间格式不正确，请重新输入！");
        }*/

        AdminSystemVO loginUser = getUser(request);
        if (loginUser==null){
            return new AdminResult<>(FAIL, "用户未登陆！");
        }
        form.setUpdateBy(loginUser.getUsername());
        form.setUpdateTime(GetDate.getDate());
        int updateRecord = withdrawConfigService.updateWithdrawRuleConfig(form);
        if(updateRecord>0){
            return new AdminResult<>(SUCCESS, SUCCESS_DESC);
        }
        return new AdminResult<>(FAIL, FAIL_DESC);
    }


    @ApiOperation(value = "假期时间配置列表", notes = "假期时间配置列表")
    @PostMapping(value = "/getWithdrawTimeConfigList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<WithdrawTimeConfigVO>> getWithdrawTimeConfigList(@RequestBody WithdrawTimeConfigRequest request) {
        WithdrawTimeConfigResponse response = withdrawConfigService.getWithdrawTimeConfigList(request);
        if (response == null || !Response.isSuccess(response)){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<ListResult<WithdrawTimeConfigVO>>(ListResult.build(response.getResultList(), response.getCount()));
    }


    @ApiOperation(value = "保存假期时间配置", notes = "保存假期时间配置")
    @PostMapping(value = "/saveWithdrawTimeConfig")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult saveWithdrawTimeConfig(HttpServletRequest request,@RequestBody WithdrawTimeConfigVO form){
        AdminSystemVO loginUser = getUser(request);
        if (loginUser==null){
            return new AdminResult<>(FAIL, "用户未登陆！");
        }

        if (StringUtils.isEmpty(form.getYear())){
            return new AdminResult<>(FAIL, "年份不能为空！");
        }
        if (StringUtils.isEmpty(form.getHolidayName())){
            return new AdminResult<>(FAIL, "假期名称不能为空！");
        }
        if (form.getStartDate()==null){
            return new AdminResult<>(FAIL, "假期起始时间不能为空！");
        }
        if (form.getEndDate()==null){
            return new AdminResult<>(FAIL, "假期结束时间不能为空！");
        }
        if (form.getHolidayType()==null){
            return new AdminResult<>(FAIL, "假期类型不能为空！");
        }

        if (form.getId()==null){
            Date currDate =GetDate.getDate();
            form.setCreateBy(loginUser.getUsername());
            form.setCreateTime(currDate);
            form.setUpdateBy(loginUser.getUsername());
            form.setUpdateTime(currDate);
        }else {
            form.setUpdateBy(loginUser.getUsername());
            form.setUpdateTime(GetDate.getDate());
        }

        int num = withdrawConfigService.saveWithdrawTimeConfig(form);
        if(num>0){
            return new AdminResult<>(SUCCESS, SUCCESS_DESC);
        }
        return new AdminResult<>(FAIL, FAIL_DESC);

    }

    @ApiOperation(value = "提现时间配置详情", notes = "提现时间配置详情")
    @GetMapping(value = "/getWithdrawTimeConfigById/{id}")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<WithdrawTimeConfigVO> getWithdrawTimeConfigById(@PathVariable Integer id){
        if (id == null){
            return new AdminResult<>(FAIL, "传入id不能为空！");
        }
        WithdrawTimeConfigResponse response = withdrawConfigService.getWithdrawTimeConfigById(id);
        if (response == null || !Response.isSuccess(response)){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(response.getResult());
    }


}
