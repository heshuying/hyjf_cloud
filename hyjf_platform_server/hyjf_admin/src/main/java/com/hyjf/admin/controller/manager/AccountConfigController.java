package com.hyjf.admin.controller.manager;

import com.hyjf.admin.Utils.ValidatorFieldCheckUtil;
import com.hyjf.admin.beans.request.MerchantAccountRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.MerchantAccountService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.MerchantAccountResponse;
import com.hyjf.am.resquest.admin.AdminMerchantAccountRequest;
import com.hyjf.am.vo.admin.MerchantAccountVO;
import com.hyjf.am.vo.admin.coupon.ParamName;
import com.hyjf.common.util.CustomConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/12.
 */
@Api(value = "配置中心账号平台设置")
@RestController
@RequestMapping("/hyjf-admin/config/accountconfig")
public class AccountConfigController extends BaseController {
    //权限名称
    private static final String PERMISSIONS = "accountconfig";
    @Autowired
    private MerchantAccountService merchantAccountService;

    @ApiOperation(value = "配置中心账号平台设置", notes = "查询配置中心账号平台设置")
    @RequestMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<MerchantAccountVO>> accountConfigInit(@RequestBody MerchantAccountRequestBean merchantAccountRequestBean) {
        AdminMerchantAccountRequest request = new AdminMerchantAccountRequest();
        //可以直接使用
        BeanUtils.copyProperties(merchantAccountRequestBean, request);
        MerchantAccountResponse response=merchantAccountService.selectMerchantAccountListByPage(request);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<ListResult<MerchantAccountVO>>(ListResult.build(response.getResultList(), response.getRecordTotal())) ;
    }

    @ApiOperation(value = "配置中心账号平台设置", notes = "查询配置中心账号平台设置列表检索")
    @RequestMapping("/searchAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<MerchantAccountVO>> accountConfigSearch(@RequestBody MerchantAccountRequestBean merchantAccountRequestBean) {
        AdminMerchantAccountRequest request = new AdminMerchantAccountRequest();
        //可以直接使用
        BeanUtils.copyProperties(merchantAccountRequestBean, request);
        MerchantAccountResponse response=merchantAccountService.selectMerchantAccountListByPage(request);
        if(response==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());

        }
        return new AdminResult<ListResult<MerchantAccountVO>>(ListResult.build(response.getResultList(), response.getRecordTotal())) ;
    }
    @ApiOperation(value = "配置中心账号平台设置", notes = "账号平台设置详情页面")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public AdminResult<MerchantAccountVO>  accountConfigInfo(@RequestBody MerchantAccountRequestBean merchantAccountRequestBean) {
//        AdminMerchantAccountRequest adminRequest = new AdminMerchantAccountRequest();
//        //可以直接使用
//        adminRequest.setId(merchantAccountRequestBean.getId());
        Integer id=merchantAccountRequestBean.getId();
        MerchantAccountResponse adminResponse= merchantAccountService.searchAccountConfigInfo(id);
        if (adminResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(adminResponse)) {
            return new AdminResult<>(FAIL, adminResponse.getMessage());
        }
        return new AdminResult<MerchantAccountVO>(adminResponse.getResult()) ;
    }
    @ApiOperation(value = "配置中心账号平台设置", notes = "账号平台设置添加")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult<MerchantAccountVO>  insertAccountConfig(@RequestBody MerchantAccountRequestBean merchantAccountRequestBean) {
        AdminMerchantAccountRequest req = new AdminMerchantAccountRequest();
        MerchantAccountResponse prs =new MerchantAccountResponse();
         ModelAndView modelAndView= new ModelAndView();
        // 表单校验
        this.validatorFieldCheck(modelAndView, merchantAccountRequestBean);
        if (ValidatorFieldCheckUtil.hasValidateError(modelAndView)) {
            // 子账户类型 ---todo(返回子账号类型)
            List<ParamName> paramNameList= merchantAccountService.getParamNameList(CustomConstants.SUB_ACCOUNT_CLASS);
            String paramName =paramNameList.toString();
            return new AdminResult<>(prs.getMessage(),paramName);
        }
        BeanUtils.copyProperties(merchantAccountRequestBean, req);
        prs = merchantAccountService.saveAccountConfig(req);
        if(prs==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(prs)) {
            return new AdminResult<>(FAIL, prs.getMessage());

        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "配置中心账号平台设置", notes = "账号平台设置修改")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult<MerchantAccountVO>  updateAccountConfig(@RequestBody MerchantAccountRequestBean merchantAccountRequestBean) {
        AdminMerchantAccountRequest req = new AdminMerchantAccountRequest();
        MerchantAccountResponse prs =new MerchantAccountResponse();
        ModelAndView modelAndView= new ModelAndView();
        // 表单校验
        this.validatorFieldCheck(modelAndView, merchantAccountRequestBean);
        if (ValidatorFieldCheckUtil.hasValidateError(modelAndView)) {
            // 子账户类型 ---todo(返回子账号类型)
            List<ParamName> paramNameList= merchantAccountService.getParamNameList(CustomConstants.SUB_ACCOUNT_CLASS);
            String paramName =paramNameList.toString();
            return new AdminResult<>(prs.getMessage(),paramName);
        }
        BeanUtils.copyProperties(merchantAccountRequestBean, req);
        prs = merchantAccountService.updateAccountConfig(req);
        if(prs==null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(prs)) {
            return new AdminResult<>(FAIL, prs.getMessage());

        }
        return new AdminResult<>();
    }
    @ApiOperation(value = "配置中心账号平台设置", notes = "账号平台设置修改")
    @PostMapping("/checkAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult<MerchantAccountVO>  checkAccountConfig(HttpServletRequest request) {
        MerchantAccountResponse prs =new MerchantAccountResponse();
        String name = request.getParameter("name");
        String param = request.getParameter("param");
        String ids = request.getParameter("ids");
        // 子账户名称
        if ("subAccountName".equals(name)) {
            // 根据子账户名称检索,是否重复
            int count = merchantAccountService.countAccountListInfoBySubAccountName(ids, param);
            if (count > 0) {
                String message = ValidatorFieldCheckUtil.getErrorMessage("repeat", "");
                message = message.replace("{label}", "子账户名称");
                return new AdminResult<>(SUCCESS,message);
            }
        }
        // 子账户代号
        if ("subAccountCode".equals(name)) {
            // 根据子账户代号检索,是否重复
            int result = merchantAccountService.countAccountListInfoBySubAccountCode(ids, param);
            if (result > 0) {
                String message = ValidatorFieldCheckUtil.getErrorMessage("repeat", "");
                message = message.replace("{label}", "子账户代号");
                return new AdminResult<>(SUCCESS,message);
            }
        }
        // 没有错误时,返回y
        return new AdminResult<>(SUCCESS,"y");
    }
    /**
     * 画面校验
     *
     * @param modelAndView
     * @param form
     */
    private void validatorFieldCheck(ModelAndView modelAndView, MerchantAccountRequestBean form) {
        // 子账户名称
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "subAccountName", form.getSubAccountName());

        // 子账户类型
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "subAccountType", form.getSubAccountType());

        // 子账户代码
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "subAccountCode", form.getSubAccountCode());

        // 排序
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "order", String.valueOf(form.getSort()));
    }

}
