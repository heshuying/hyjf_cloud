package com.hyjf.admin.controller.repair.openaccountenquiryrepair;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.OpenAccountEnquiryDefineResultBean;
import com.hyjf.admin.beans.request.OpenAccountEnquiryDefineRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.OpenAccountEnquiryService;
import com.hyjf.am.vo.config.AdminSystemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @version OpenAccountEnquiryController, v0.1 2018/8/20 10:14
 * @Author: Zha Daojian
 */

@Api(value = "异常中心-开户掉单查询", tags = "异常中心-开户掉单查询")
@RestController
@RequestMapping("/hyjf-admin/exception/openaccount")
public class OpenAccountEnquiryController extends BaseController {
    @Autowired
    OpenAccountEnquiryService openAccountEnquiryService;

    /** 权限 */
    public static final String PERMISSIONS = "openaccountenquiry";

    @ApiOperation(value = "用户按照手机号和身份证号查询开户掉单", notes = "用户按照手机号和身份证号查询开户掉单")
    @PostMapping("/openaccountenquiryAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
    public AdminResult<OpenAccountEnquiryDefineResultBean> getList(HttpServletRequest request, @RequestBody OpenAccountEnquiryDefineRequestBean requestBean) {
        AdminSystemVO currUser = getUser(request);
        if(currUser == null){
            return new AdminResult(BaseResult.FAIL, "未获取到当前登录用户信息");
        }
        OpenAccountEnquiryDefineResultBean resultBean = openAccountEnquiryService.openAccountEnquiry(currUser,requestBean);
        return new AdminResult<>(resultBean);
    }

    @ApiOperation(value = "用户按照手机号和身份证号查询开户掉单后同步系统掉单信息，更改用户状态", notes = "用户按照手机号和身份证号查询开户掉单后同步系统掉单信息，更改用户状态")
    @PostMapping("/openAccountEnquiryUpdate")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
    public AdminResult<OpenAccountEnquiryDefineResultBean> enquiryUpdate(HttpServletRequest request,@RequestBody OpenAccountEnquiryDefineResultBean requestBean) {
        AdminSystemVO currUser = getUser(request);
        if(currUser == null){
            return new AdminResult(BaseResult.FAIL, "未获取到当前登录用户信息");
        }
        OpenAccountEnquiryDefineResultBean resultBean = openAccountEnquiryService.openAccountEnquiryUpdate(requestBean);
        logger.info("==========保存开户掉单user的数据requestBean：" +JSONObject.toJSONString(requestBean));
        return new AdminResult<>(resultBean);
    }
}
