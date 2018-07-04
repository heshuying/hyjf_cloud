/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.exception.userauthexception;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.controller.BaseController;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: UserAuthExceptionController, v0.1 2018/7/2 10:04
 * 后台管理系统，异常中心->自动投资债转授权异常
 */
@RestController
@RequestMapping("/hyjf-admin/user_auth_exception")
@Api(value = "自动投资债转授权异常")
public class UserAuthExceptionController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserAuthExceptionService userAuthExceptionService;
    /**
     * 自动投资债转授权异常list查询
     * @auth sunpeikai
     * @param request 筛选条件请求参数
     * @return JSONObject 拼装的返回参数
     */
    @ApiOperation(value = "自动投资债转授权异常", notes = "自动投资债转授权异常list查询")
    @PostMapping(value = "/user_auth_list")
    public JSONObject userAuthException(@RequestBody AdminUserAuthListRequest request){
        JSONObject jsonObject = new JSONObject();
        AdminUserAuthListResponse response = userAuthExceptionService.selectUserAuthList(request);
        if(AdminResponse.isSuccess(response)){
            Integer recordTotal = response.getRecordTotal();
            List<AdminUserAuthListVO> resultList = response.getResultList();
            jsonObject.put(STATUS, SUCCESS);
            jsonObject.put(MSG, "成功");
            jsonObject.put(TRCORD, recordTotal);
            jsonObject.put(LIST, resultList);
        }else{
            jsonObject.put(MSG, "查询失败");
            jsonObject.put(STATUS, FAIL);
        }
        return jsonObject;
    }
    /**
     * 同步用户授权状态
     * @auth sunpeikai
     * @param userId 用户id
     * @param type 1自动投资授权  2债转授权
     * @return
     */
    @ApiOperation(value = "同步用户授权状态", notes = "同步用户授权状态")
    @PostMapping(value = "/syn_user_auth")
    public JSONObject synUserAuth(@RequestParam Integer userId , @RequestParam Integer type){
        logger.info("同步用户[{}]的授权状态,同步类型[{}]",userId,type);
        AdminUserAuthListResponse response = userAuthExceptionService.synUserAuth(userId, type);

        if(AdminResponse.isSuccess(response)){
            return success();
        }else{
            return fail("查询失败");
        }
    }

}
