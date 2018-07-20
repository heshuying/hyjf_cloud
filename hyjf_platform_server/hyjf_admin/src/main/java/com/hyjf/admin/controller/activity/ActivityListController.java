/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.am.response.market.ActivityListResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.service.ActivityListService;
import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.GetDate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author yaoy
 * @version ActivityListController, v0.1 2018/6/26 16:13
 */
@Api(value = "活动列表接口")
@RestController
@RequestMapping("/hyjf-admin/activity")
public class ActivityListController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ActivityListController.class);

    @Value("${http://cdn.huiyingdai.com/}")
    private String fileDomainUrl;

    @Autowired
    private ActivityListService activityListService;

    @ApiOperation(value = "活动列表", notes = "页面初始化")
    @PostMapping("/activityListInit")
    public JSONObject activityListInit() {
        JSONObject jsonObject = new JSONObject();
        logger.info("上传文件url:{}", fileDomainUrl);
        String url = UploadFileUtils.getDoPath(fileDomainUrl);
        jsonObject.put("url", url);
        return jsonObject;
    }

    @PostMapping("/activityRecordList")
    public AdminResult<ListResult<ActivityListVO>> selectActivityList(HttpServletRequest request, ActivityListRequest activityListRequest) {
        ActivityListResponse response = activityListService.getRecordList(activityListRequest);
        List<ActivityListVO> forBack = forBack(response);
        if(response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<ListResult<ActivityListVO>>(ListResult.build(forBack, response.getCount())) ;
    }



    /**
     * 调用封装回显
     *
     * @param response
     * @return
     */
    private List<ActivityListVO> forBack(ActivityListResponse response) {
        List<ActivityListVO> forBack = new ArrayList<ActivityListVO>();

        for (ActivityListVO activityList : response.getResultList()) {
            ActivityListVO request = new ActivityListVO();
            BeanUtils.copyProperties(activityList, request);
            request.setId(activityList.getId());
            request.setTitle(activityList.getTitle());
            request.setUrlForeground(activityList.getUrlForeground());
            String platform = activityList.getPlatform();
            Map<String, String> map = new HashMap<>();
            map.put("0", "PC");
            map.put("1", "微官网");
            map.put("2", "Android");
            map.put("3", "iOS");
            map.put("4", "其他");
            map.put("5", "微可车贷");
            request.setPlatform(map.get(platform));
            request.setTimeStart(activityList.getTimeStart());
            request.setTimeEnd(activityList.getTimeEnd());
            if (activityList.getTimeStart() >= GetDate.getNowTime10()) {
                request.setStatus("未开始");
            }
            if (activityList.getTimeEnd() <= GetDate.getNowTime10()) {
                request.setStatus("已完成");
            }
            if (activityList.getTimeEnd() >= GetDate.getNowTime10()
                    && activityList.getTimeStart() <= GetDate.getNowTime10()) {
                request.setStatus("进行中");
            }
            request.setStartCreate(activityList.getCreateTime());
//            if("汇盈F1大师赛".equals(activityList.getTitle())){
//                request.setIsMayActivity("1");
//            }
            forBack.add(request);
        }
        return forBack;
    }

    @ApiOperation(value = "活动列表", notes = "添加活动配置")
    @PostMapping("/insertAction")
    public AdminResult insertAction(@RequestBody ActivityListRequest request) {
        //1代表插入成功， 0为失败
        ActivityListResponse response = activityListService.insertRecord(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "活动列表", notes = "获取活动修改初始信息")
    @RequestMapping("/initUpdateActivity/{id}")
    public AdminResult<ActivityListVO> initUpdateActivity(@RequestParam Integer id) {
        ActivityListRequest activityListRequest = new ActivityListRequest();
        activityListRequest.setId(id);
        //根据活动id查询活动信息
        ActivityListResponse response = activityListService.selectActivityById(activityListRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<ActivityListVO>(response.getResult());
    }

    @ApiOperation(value = "活动列表",notes = "修改活动信息")
    @PostMapping("/updateAction")
    public AdminResult updateActivity(@RequestBody ActivityListRequest activityListRequest) {
        ActivityListResponse response = activityListService.updateActivity(activityListRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "活动列表",notes = "资料上传")
    @PostMapping("/uploadFile")
    public JSONObject uploadFile(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        String file = null;
        try {
            file = activityListService.uploadFile(request,response);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("资料上传失败");
        }
        jsonObject.put("file",file);
        return jsonObject;
    }

    @ApiOperation(value = "活动列表",notes = "删除配置信息")
    @RequestMapping("/deleteAction")
    public AdminResult deleteRecordAction(@RequestParam int id) {
        ActivityListRequest request = new ActivityListRequest();
        request.setId(id);
        ActivityListResponse response = activityListService.deleteActivity(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>();
    }

}
