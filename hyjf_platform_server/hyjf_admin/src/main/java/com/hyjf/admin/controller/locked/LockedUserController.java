/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.locked;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.locked.LockedUserService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.locked.LockedUserMgrResponse;
import com.hyjf.am.resquest.admin.locked.LockedeUserListRequest;
import com.hyjf.am.vo.admin.locked.LockedUserInfoVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cui
 * @version LockedUserController, v0.1 2018/9/25 11:26
 */
@Api(tags = "配置中心-登录失败锁定配置")
@RestController
@RequestMapping("/hyjf-admin/lockeduser/")
public class LockedUserController extends BaseController {

    @Autowired
    private LockedUserService lockedUserService;

    @ApiOperation(value = "前台锁定用户列表",notes = "前台账户锁定用户列表")
    @PostMapping(value = "/frontlist")
    @ResponseBody
    public BaseResult<LockedUserMgrResponse> frontList(@RequestBody LockedeUserListRequest request){

        LockedUserMgrResponse response= lockedUserService.getLockedUserList(request,true);

        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        return new BaseResult(response);
    }

    @ApiOperation(value = "后台锁定用户列表",notes = "后台锁定用户列表")
    @PostMapping(value = "/adminlist")
    @ResponseBody
    public BaseResult<LockedUserMgrResponse> adminList(@RequestBody LockedeUserListRequest request){

        LockedUserMgrResponse response = lockedUserService.getLockedUserList(request,false);

        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        return new BaseResult(response);
    }

    @ApiOperation(value = "前台解锁",notes = "前台解锁")
    @PostMapping(value = "/frontunlock")
    @ResponseBody
    public BaseResult<Boolean> frontUnlock(@RequestBody LockedUserInfoVO lockedUserInfoVO, HttpServletRequest request){

        buildVO(lockedUserInfoVO, request);

        Response response = lockedUserService.unlock(lockedUserInfoVO,false);

        Boolean isSuccess=response.getRtn().equals(Response.SUCCESS);

        if(isSuccess){
            return new BaseResult<>(true);
        }else{
            return new BaseResult<>(BaseResult.ERROR,response.getMessage());
        }
    }

    @ApiOperation(value = "后台解锁",notes = "后台解锁")
    @PostMapping(value = "/adminunlock")
    @ResponseBody
    public BaseResult<Boolean> adminUnlock(@RequestBody LockedUserInfoVO lockedUserInfoVO,HttpServletRequest request){

        buildVO(lockedUserInfoVO, request);

        Response response= lockedUserService.unlock(lockedUserInfoVO,false);

        Boolean isSuccess=response.getRtn().equals(Response.SUCCESS);

        if(isSuccess){
            return new BaseResult<>(true);
        }else{
            return new BaseResult<>(BaseResult.ERROR,response.getMessage());
        }
    }

    private void buildVO(LockedUserInfoVO lockedUserInfoVO, HttpServletRequest request) {
        AdminSystemVO user=getUser(request);
        String operatorId=user.getId();
        lockedUserInfoVO.setOperator(Integer.valueOf(operatorId));
    }
}

