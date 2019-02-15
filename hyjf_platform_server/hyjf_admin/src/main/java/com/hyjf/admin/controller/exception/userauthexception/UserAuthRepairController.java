/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.exception.userauthexception;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.UserAuthExceptionService;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.user.AdminUserAuthListResponse;
import com.hyjf.am.resquest.user.AdminUserAuthListRequest;
import com.hyjf.am.vo.user.AdminUserAuthListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.hyjf.admin.common.util.ShiroConstants.PERMISSION_VIEW;

/**
 * @author: sunpeikai
 * @version: UserAuthExceptionController, v0.1 2018/7/2 10:04
 * 后台管理系统，异常中心->自动出借债转授权异常
 */
@RestController
@RequestMapping("/hyjf-admin/exception/user_auth_exception")
@Api(value = "异常中心-自动出借债转授权异常",tags = "异常中心-自动出借债转授权异常")
public class UserAuthRepairController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserAuthExceptionService userAuthExceptionService;
    private static final String PERMISSIONS = "exceptionuserauth";

    /**
     * 自动出借债转授权异常list查询
     * @auth sunpeikai
     * @param request 筛选条件请求参数
     * @return
     */
    @ApiOperation(value = "自动出借债转授权异常", notes = "自动出借债转授权异常list查询")
    @PostMapping(value = "/user_auth_list")
    @AuthorityAnnotation(key = PERMISSIONS,value = PERMISSION_VIEW)
    public AdminResult<ListResult<AdminUserAuthListVO>> userAuthException(@RequestBody AdminUserAuthListRequest request){
        Integer recordTotal = 0;
        List<AdminUserAuthListVO> resultList = new ArrayList<>();
        AdminUserAuthListResponse response = userAuthExceptionService.selectUserAuthList(request);
        if(AdminResponse.isSuccess(response)){
            recordTotal = response.getRecordTotal();
            resultList = response.getResultList();
        }
        return new AdminResult<>(ListResult.build(resultList,recordTotal));
    }
    /**
     * 同步用户授权状态
     * @auth sunpeikai
     * @return
     */
    @ApiOperation(value = "同步用户授权状态", notes = "同步用户授权状态")
    @PostMapping(value = "/syn_user_auth")
    public AdminResult synUserAuth(@RequestBody AdminUserAuthListRequest request){
        Integer userId = Integer.valueOf(request.getUserId());
        Integer type = request.getType();
        //logger.info("同步用户[{}]的授权状态,同步类型[{}]",userId,type);
        AdminUserAuthListResponse response = userAuthExceptionService.synUserAuth(userId, type);

        if(AdminResponse.isSuccess(response)){
            return new AdminResult(SUCCESS,response.getMessage());
        }else{
            return new AdminResult(FAIL,response.getMessage());
        }
    }

}
