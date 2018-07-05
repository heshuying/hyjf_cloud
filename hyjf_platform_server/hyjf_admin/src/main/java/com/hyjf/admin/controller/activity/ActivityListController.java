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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class ActivityListController {
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
    public JSONObject selectActivityList(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        ActivityListRequest activityListRequest = setRequest(map);
        List<ActivityListVO> recordActivityList = activityListService.getRecordList(activityListRequest);
        List<ActivityListRequest> forBack = forBack(recordActivityList);
        String status = Response.FAIL;
        if (null != recordActivityList && recordActivityList.size() > 0) {
            jsonObject.put("record", recordActivityList);
            status = Response.SUCCESS;
        }
        jsonObject.put("status", status);
        return jsonObject;
    }

    private ActivityListRequest setRequest(Map<String, Object> mapParam) {
        ActivityListRequest activityListRequest = new ActivityListRequest();
        if (null != mapParam && mapParam.size() > 0) {
            if (mapParam.containsKey("title")) {
                activityListRequest.setTitle(mapParam.get("title").toString());
            }
            if (mapParam.containsKey("startTime")) {
                activityListRequest.setStartTime(Integer.valueOf(mapParam.get("startTime").toString()));
            }
            if (mapParam.containsKey("endTime")) {
                activityListRequest.setEndTime(Integer.valueOf(mapParam.get("endTime").toString()));
            }
            if (mapParam.containsKey("startCreate")) {
                activityListRequest.setStartCreate(mapParam.get("startCreate").toString());
            }
            if (mapParam.containsKey("endCreate")) {
                activityListRequest.setEndCreate(mapParam.get("endCreate").toString());
            }
            if (mapParam.containsKey("limit") && StringUtils.isNotBlank(mapParam.get("limit").toString())) {
                activityListRequest.setLimit(Integer.parseInt(mapParam.get("limit").toString()));
            }
        }
        return activityListRequest;
    }

    /**
     * 调用封装回显
     *
     * @param recordList
     * @return
     */
    private List<ActivityListRequest> forBack(List<ActivityListVO> recordList) {
        List<ActivityListRequest> forBack = new ArrayList<ActivityListRequest>();

        for (ActivityListVO activityList : recordList) {
            ActivityListRequest request = new ActivityListRequest();
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
            request.setStartTime(activityList.getTimeStart());
            request.setEndTime(activityList.getTimeEnd());
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
            request.setStartCreate(GetDate.getDateTimeMyTime(activityList.getCreateTime()));
//            if("汇盈F1大师赛".equals(activityList.getTitle())){
//                request.setIsMayActivity("1");
//            }
            forBack.add(request);
        }
        return forBack;
    }

    @ApiOperation(value = "活动列表", notes = "添加活动配置")
    @PostMapping("/insertAction")
    public JSONObject insertAction(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();
        //1代表插入成功， 0为失败
        int insertFlag = activityListService.insertRecord(map);
        if (insertFlag == 1) {
            jsonObject.put("insertFlag", "success");
        } else {
            jsonObject.put("insertFlag", "fail");
        }
        return jsonObject;
    }

    @ApiOperation(value = "活动列表", notes = "获取活动修改初始信息")
    @PostMapping("/initUpdateActivity")
    public JSONObject initUpdateActivity(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        int id = Integer.parseInt(map.get("id").toString());
        //根据活动id查询活动信息
        ActivityListVO activityListVO = activityListService.selectActivityById(id);
        jsonObject.put("updateActivityForm",activityListVO);
        return jsonObject;
    }

    @ApiOperation(value = "活动列表",notes = "修改活动信息")
    @PostMapping("/updateAction")
    public JSONObject updateActivity(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();
        //1代表插入成功， 0为失败
        int updateFlag = activityListService.updateActivity(map);
        if (updateFlag == 1) {
            jsonObject.put("updateFlag", "success");
        } else {
            jsonObject.put("updateFlag", "fail");
        }
        return jsonObject;
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
    @PostMapping("/deleteAction")
    public JSONObject deleteRecordAction(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();
        int id = Integer.parseInt(map.get("id").toString());
        int deleteFlag = activityListService.deleteActivity(id);
        if (deleteFlag == 1) {
            jsonObject.put("deleteFlag", "success");
        } else {
            jsonObject.put("deleteFlag", "fail");
        }
        return jsonObject;
    }

}
