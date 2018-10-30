package com.hyjf.admin.controller.mobileclient;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.AppPushManageRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.mobileclient.AppPushManageService;
import com.hyjf.am.response.AppPushManageResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.AppPushManageVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * App 推送管理
 * @Author : huanghui
 */
@Api(tags = "移动客户端-App推送管理")
@RestController
@RequestMapping("/hyjf-admin/app/pushmanage")
public class AppPushManageController extends BaseController {

    @Autowired
    AppPushManageService appPushManageService;

    /**
     *
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "App 推送管理", notes = "App 推送管理")
    @PostMapping(value = "/init")
    public AdminResult<ListResult<AppPushManageVO>> init(@RequestBody AppPushManageRequestBean requestBean) {

        AppPushManageResponse pushManageResponse = appPushManageService.getAppPushManageList(requestBean);
        // 初始化 返回List
        List<AppPushManageVO> returnList = new ArrayList<>();

        if (pushManageResponse == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        if (!Response.isSuccess(pushManageResponse)){
            return new AdminResult<>(FAIL, "获取列表失败!");
        }

        if (CollectionUtils.isNotEmpty(pushManageResponse.getResultList())){
            returnList = CommonUtils.convertBeanList(pushManageResponse.getResultList(), AppPushManageVO.class);
            return new AdminResult<ListResult<AppPushManageVO>>(ListResult.build(returnList, pushManageResponse.getCount()));
        }else {
            return new AdminResult<ListResult<AppPushManageVO>>(ListResult.build(returnList, 0));
        }
    }

    /**
     * 单条写入数据
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "App 推送管理添加", notes = "App 推送管理添加")
    @PostMapping(value = "/add")
    public AdminResult<AppPushManageVO> add(@RequestBody AppPushManageRequestBean requestBean){

        JSONObject jsonObject = new JSONObject();

        jsonObject = this.validatorFieldCheck(jsonObject, requestBean);
        // 判断是否为空
        if (jsonObject.size() > 0){
            return new AdminResult<>(FAIL, jsonObject.get("code").toString());
        }

        AppPushManageResponse response = this.appPushManageService.insertPushManage(requestBean);

        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>();

    }

    /**
     * 更新
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "App 推送管理更新", notes = "App 推送管理更新")
    @PostMapping(value = "/update")
    public AdminResult<AppPushManageVO> update(@RequestBody AppPushManageRequestBean requestBean){

        JSONObject jsonObject = new JSONObject();

        jsonObject = this.validatorFieldCheck(jsonObject, requestBean);
        // 判断是否为空
        if (jsonObject.size() > 0){
            return new AdminResult<>(FAIL, jsonObject.get("code").toString());
        }

        boolean response = this.appPushManageService.updatePushManage(requestBean);

        if (!response) {
            return new AdminResult<>(FAIL, "更新失败!");
        }
        return new AdminResult<>();
    }

    /**
     * 删除
     * @return
     */
    @ApiOperation(value = "App 推送管理删除", notes = "App 推送管理删除")
    @PostMapping(value = "/delete")
    public AdminResult<AppPushManageVO> delete(@RequestBody AppPushManageRequestBean requestBean){
        Integer id = requestBean.getId();

        boolean checkCode = this.appPushManageService.deletePushManage(id);
        if (!checkCode){
            return new AdminResult<>(FAIL, "删除失败!");
        }
        return new AdminResult<>();

    }

    private JSONObject validatorFieldCheck(JSONObject jsonObject, AppPushManageRequestBean requestBean){

        // 标题名称不能为空
        if (StringUtils.isBlank(requestBean.getTitle())){
            jsonObject.put("code", "标题名称不能为空!");
            return jsonObject;
        }

//        if (StringUtils.isNumeric(requestBean.getOrderId().toString())){
//            jsonObject.put("code", "排序必须为数字!");
//            return jsonObject;
//        }

        if (StringUtils.isBlank(requestBean.getTimeStart().toString())){
            jsonObject.put("code", "起始时间不能为空!");
            return jsonObject;
        }

        if (StringUtils.isBlank(requestBean.getTimeEnd().toString())){
            jsonObject.put("code", "结束时间不能为空!");
            return jsonObject;
        }

        return jsonObject;
    }
}
