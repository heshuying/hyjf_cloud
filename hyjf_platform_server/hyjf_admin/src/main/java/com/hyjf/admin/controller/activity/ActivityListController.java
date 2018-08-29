/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.activity;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.ActivityListService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.common.util.GetDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author yaoy
 * @version ActivityListController, v0.1 2018/6/26 16:13
 */
@Api(tags = "活动中心-活动列表")
@RestController
@RequestMapping("/hyjf-admin/activity")
public class ActivityListController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ActivityListController.class);

    /**
     * 权限关键字
     */
    public static final String PERMISSIONS = "activitylist";

    @Value("${file.domain.url}")
    private String fileDomainUrl;

    @Autowired
    private ActivityListService activityListService;

//    @ApiOperation(value = "页面初始化", notes = "页面初始化")
//    @PostMapping(value = "/activityListInit")
//    @AuthorityAnnotation(key = PERMISSIONS,value = ShiroConstants.PERMISSION_VIEW)
//    public JSONObject activityListInit() {
//        JSONObject jsonObject = new JSONObject();
//        logger.info("上传文件url:{}", fileDomainUrl);
//        String url = UploadFileUtils.getDoPath(fileDomainUrl);
//        jsonObject.put("url", url);
//        return jsonObject;
//    }

    @ApiOperation(value = "查询列表", notes = "查询列表")
    @PostMapping("/activityRecordList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<ActivityListVO>> selectActivityList(HttpServletRequest request, @RequestBody ActivityListRequest activityListRequest) {
        ActivityListResponse response = activityListService.getRecordList(activityListRequest);
        List<ActivityListVO> forBack = forBack(response);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<ListResult<ActivityListVO>>(ListResult.build(forBack, response.getCount()));
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
            List<ParamNameVO> paramNames = activityListService.getParamNameList("CLIENT");
            for (ParamNameVO param : paramNames) {
                platform = platform.replace(param.getNameCd(), param.getName());
            }
            request.setPlatform(platform);
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

    @ApiOperation(value = "添加活动配置", notes = "添加活动配置")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertAction(@RequestBody ActivityListRequest request) {
        ActivityListResponse response = new ActivityListResponse();
        String message = validatorFieldCheck(request);
        if (message != null) {
            response.setRtn(Response.FAIL);
            response.setMessage(message);
            return new AdminResult<>(FAIL,response.getMessage());
        }
        //1代表插入成功， 0为失败
        response = activityListService.insertRecord(request);

        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>();
    }


    @ApiOperation(value = "活动修改初始页面", notes = "活动修改初始页面")
    @RequestMapping(value = "/initUpdateActivity", method = RequestMethod.GET)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
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

    @ApiOperation(value = "修改活动信息", notes = "修改活动信息")
    @PostMapping("/updateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateActivity(@RequestBody ActivityListRequest activityListRequest) {
        ActivityListResponse response = new ActivityListResponse();
        String message = validatorFieldCheck(activityListRequest);
        if (message != null) {
            response.setMessage(message);
            return new AdminResult<>(FAIL,response.getMessage());
        }
        response = activityListService.updateActivity(activityListRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "资料上传", notes = "资料上传")
    @PostMapping("/uploadFile")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public JSONObject uploadFile(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject();
        try {
            LinkedList<BorrowCommonImage> borrowCommonImages = activityListService.uploadFile(request, response);
            jsonObject.put("file", borrowCommonImages);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("资料上传失败");
        }
        return jsonObject;
    }

    @ApiOperation(value = "删除配置信息", notes = "删除配置信息")
    @RequestMapping(value = "/deleteAction", method = RequestMethod.GET)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
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


    /**
     * 表单校验
     * @param request
     * @return
     */
    private String validatorFieldCheck(ActivityListRequest request) {
        String message = null;
        if (request.getTitle() == null) {
            message = "活动名称不能为空";
            return message;
        }
        if (request.getQr() == null) {
            message = "主图不能为空";
            return message;
        }
        if (request.getStartTime() == null) {
            message = "活动起始时间不能为空";
            return message;
        }
        if (request.getEndTime() == null) {
            message = "活动结束时间不能为空";
            return message;
        }
        if (request.getPlatform() == null) {
            message = "平台不能为空";
            return message;
        }
        if (request.getTitle().length() > 30) {
            message = "活动名称过长";
            return message;
        }
        if (request.getDescription() != null) {
            if (request.getDescription().length() > 200) {
                message = "描述过长";
                return message;
            }
        }
        return message;
    }

}
