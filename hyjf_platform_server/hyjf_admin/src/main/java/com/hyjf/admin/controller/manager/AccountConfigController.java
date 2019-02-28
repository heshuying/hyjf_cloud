package com.hyjf.admin.controller.manager;

import com.hyjf.admin.beans.request.MerchantAccountRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.MerchantAccountService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.MerchantAccountResponse;
import com.hyjf.am.resquest.admin.AdminMerchantAccountRequest;
import com.hyjf.am.vo.admin.MerchantAccountVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.util.CustomConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author by xiehuili on 2018/7/12.
 */
@Api(tags ="配置中心-平台账户配置--账户设置")
@RestController
@RequestMapping("/hyjf-admin/config/accountconfig")
public class AccountConfigController extends BaseController {
    //权限名称
    private static final String PERMISSIONS = "accountconfig";
    @Autowired
    private MerchantAccountService merchantAccountService;

    @ApiOperation(value = "查询配置中心账户平台设置", notes = "查询配置中心账户平台设置")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<MerchantAccountResponse> accountConfigInit(@RequestBody MerchantAccountRequestBean merchantAccountRequestBean) {
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
        return new AdminResult<MerchantAccountResponse>(response) ;
    }

    @ApiOperation(value = "查询配置中心账户平台设置列表检索", notes = "查询配置中心账户平台设置列表检索")
    @PostMapping("/searchAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<MerchantAccountResponse> accountConfigSearch(@RequestBody MerchantAccountRequestBean merchantAccountRequestBean) {
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
        return new AdminResult<MerchantAccountResponse>(response) ;
    }
    @ApiOperation(value = "账户平台设置详情页面", notes = "账户平台设置详情页面")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
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
    @ApiOperation(value = "账户平台设置添加", notes = "账户平台设置添加")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult  insertAccountConfig(@RequestBody MerchantAccountRequestBean merchantAccountRequestBean) {
        AdminMerchantAccountRequest req = new AdminMerchantAccountRequest();
        MerchantAccountResponse prs =new MerchantAccountResponse();
        // 表单校验
        String message= this.validatorFieldCheck(merchantAccountRequestBean);
        if (StringUtils.isNotBlank(message)) {
            // 子账户类型
            List<ParamNameVO> paramNameList= merchantAccountService.getParamNameList(CustomConstants.SUB_ACCOUNT_CLASS);
            prs.setParamNameList(paramNameList);
            prs.setRtn(Response.FAIL);
            prs.setMessage(message);
            return new  AdminResult<MerchantAccountResponse>(prs);
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

    @ApiOperation(value = "账户平台设置修改", notes = "账户平台设置修改")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult  updateAccountConfig(@RequestBody MerchantAccountRequestBean merchantAccountRequestBean) {
        AdminMerchantAccountRequest req = new AdminMerchantAccountRequest();
        MerchantAccountResponse prs =new MerchantAccountResponse();
        // 表单校验
       String message= this.validatorFieldCheck(merchantAccountRequestBean);
        if (StringUtils.isNotBlank(message)) {
            // 子账户类型
            List<ParamNameVO> paramNameList= merchantAccountService.getParamNameList(CustomConstants.SUB_ACCOUNT_CLASS);
            prs.setParamNameList(paramNameList);
            prs.setRtn(Response.FAIL);
            prs.setMessage(message);
            return new  AdminResult<MerchantAccountResponse>(prs);
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
    @ApiOperation(value = "账户平台设置 子账户检索", notes = "账户平台设置 子账户检索")
    @PostMapping("/checkAction")
//    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult<MerchantAccountVO>  checkAccountConfig(@RequestBody Map<String,Object> map) {
        String name =(String) map.get("name");
        String param =(String) map.get("param");
        String ids = (String)map.get("ids");
        AdminResult prs =new AdminResult();
        // 子账户名称
        if ("subAccountName".equals(name)) {
            if(StringUtils.isBlank(param)){
                prs.setStatus(BaseResult.FAIL);
                prs.setStatusDesc("子账户名称不能为空！");
                return prs;
            }
            // 根据子账户名称检索,是否重复
            int count = merchantAccountService.countAccountListInfoBySubAccountName(ids, param);
            if (count > 0) {
                prs.setStatus(BaseResult.FAIL);
                prs.setStatusDesc("子账户名称重复了！");
                return prs;
            }
        }
        // 子账户代号
        if ("subAccountCode".equals(name)) {
            if(StringUtils.isBlank(param)){
                prs.setStatus(BaseResult.FAIL);
                prs.setStatusDesc("子账户代号不能为空！");
                return prs;
            }
            // 根据子账户代号检索,是否重复
            int result = merchantAccountService.countAccountListInfoBySubAccountCode(ids, param);
            if (result > 0) {
                prs.setStatus(BaseResult.FAIL);
                prs.setStatusDesc("子账户代号重复了！");
                return prs;
            }
        }
        // 没有错误时,返回y
        prs.setStatusDesc("y");
        return prs;
    }
    /**
     * 画面校验
     *
     * @param form
     */
    private String validatorFieldCheck(MerchantAccountRequestBean form) {
        // 子账户名称
        if(StringUtils.isBlank(form.getSubAccountName())){
            return "子账户名称 不能为空！";
        }
        // 子账户类型
        if(StringUtils.isBlank(form.getSubAccountType())){
            return "子账户类型 不能为空！";
        }
        // 子账户代码
        if(StringUtils.isBlank(form.getSubAccountCode())){
            return "子账户代码 不能为空！";
        }
        // 排序
        if(null == form.getSort()){
            return "排序 不能为空！";
        }
        return "";
    }

}
