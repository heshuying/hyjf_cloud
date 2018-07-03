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
import java.util.Map;

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
     * @auth 孙沛凯
     * @param requestMap 筛选条件请求参数
     * @return JSONObject 拼装的返回参数
     */
    @ApiOperation(value = "自动投资债转授权异常", notes = "自动投资债转授权异常list查询")
    @PostMapping(value = "/user_auth_list")
    public JSONObject userAuthException(@RequestBody Map<String,Object> requestMap){
        AdminUserAuthListRequest request = setParam(requestMap);
        AdminUserAuthListResponse response = userAuthExceptionService.selectUserAuthList(request);
        if(AdminResponse.isSuccess(response)){
            String recordTotal = response.getRecordTotal();
            List<AdminUserAuthListVO> resultList = response.getResultList();
            return success(recordTotal,resultList);
        }else{
            return fail("查询失败");
        }
    }
    /**
     * 同步用户授权状态
     * @auth 孙沛凯
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
    /**
     * 请求参数map转成bean
     * @auth 孙沛凯
     * @param requestMap 待转换的map
     * @return 转换完成的bean
     */
    private AdminUserAuthListRequest setParam(Map<String,Object> requestMap){
        AdminUserAuthListRequest request = new AdminUserAuthListRequest();
        if(null != requestMap && requestMap.size() > 0){
            // 用户名
            if (requestMap.containsKey("userName")) {
                request.setUserName(requestMap.get("userName").toString());
            }
            // 推荐人
            if (requestMap.containsKey("recommendName")) {
                request.setRecommendName(requestMap.get("recommendName").toString());
            }
            // 自动投标授权状态
            if (requestMap.containsKey("autoInvesStatus")) {
                request.setAutoInvesStatus(requestMap.get("autoInvesStatus").toString());
            }
            // 自动债转授权状态
            if (requestMap.containsKey("autoCreditStatus")) {
                request.setAutoCreditStatus(requestMap.get("autoCreditStatus").toString());
            }
            // 授权时间开始时间
            if (requestMap.containsKey("invesAddTimeStart")) {
                request.setInvesAddTimeStart(requestMap.get("invesAddTimeStart").toString());
            }
            // 授权时间结束时间
            if (requestMap.containsKey("invesAddTimeEnd")) {
                request.setInvesAddTimeEnd(requestMap.get("invesAddTimeEnd").toString());
            }
            // 签约到期日开始时间
            if (requestMap.containsKey("investEndTimeStart")) {
                request.setInvestEndTimeStart(requestMap.get("investEndTimeStart").toString());
            }
            // 签约到期日结束时间
            if (requestMap.containsKey("investEndTimeEnd")) {
                request.setInvestEndTimeEnd(requestMap.get("investEndTimeEnd").toString());
            }
            // 当前第几页(翻页机能)
            if(requestMap.containsKey("paginatorPage")){
                request.setPaginatorPage(Integer.valueOf(requestMap.get("paginatorPage").toString()));
            }

        }
        return request;
    }
}