/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.activity;

import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.ActivityListService;
import com.hyjf.admin.utils.FileUpLoadUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.common.util.GetDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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


    @ApiOperation(value = "活动详情", notes = "活动详情")
    @PostMapping("/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD, ShiroConstants.PERMISSION_MODIFY})
    public AdminResult getInfoList(@RequestBody ActivityListRequest request) {
        ActivityListResponse response = new ActivityListResponse();
        List<ParamNameVO> clients = activityListService.getParamNameList("CLIENT");
        response.setClients(clients);
        if (request.getId() != null) {
            response = activityListService.getRecordById(request.getId());
            // modify by libin sonar start
            if (response == null) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            // modify by libin sonar end
            // 拆分平台
            String[] split = response.getResult().getPlatform().split(",");
            response.setPlatforms(split);
            response.setFileDomainUrl(fileDomainUrl);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult(response);
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
            String platform = activityList.getPlatform();
            List<ParamNameVO> paramNames = activityListService.getParamNameList("CLIENT");
            for (ParamNameVO param : paramNames) {
                platform = platform.replace(param.getNameCd(), param.getName());
            }
            activityList.setPlatform(platform);
            if (activityList.getTimeStart() >= GetDate.getNowTime10()) {
                activityList.setStatus("未开始");
            }
            if (activityList.getTimeEnd() <= GetDate.getNowTime10()) {
                activityList.setStatus("已完成");
            }
            if (activityList.getTimeEnd() >= GetDate.getNowTime10()
                    && activityList.getTimeStart() <= GetDate.getNowTime10()) {
                activityList.setStatus("进行中");
            }
            activityList.setAcStartTime(GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(activityList.getTimeStart()));
            activityList.setAcEndTime(GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(activityList.getTimeEnd()));
            activityList.setStartCreate(activityList.getCreateTime());
            forBack.add(activityList);
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
        if (StringUtils.isBlank(request.getQr())) {
            request.setQr("");
        }
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
    @RequestMapping(value = "/initUpdateActivity/{id}", method = RequestMethod.GET)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult<ActivityListVO> initUpdateActivity(@PathVariable Integer id) {
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
        ActivityListVO activityListVO = response.getResult();
        activityListVO.setAcStartTime(GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(activityListVO.getTimeStart()));
        activityListVO.setAcEndTime(GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(activityListVO.getTimeEnd()));
        return new AdminResult<ActivityListVO>(activityListVO);
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

    @Autowired
    private FileUpLoadUtil fileUpLoadUtil;


    @ApiOperation(value = "资料上传", notes = "资料上传")
    @PostMapping("/uploadFile")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD, ShiroConstants.PERMISSION_MODIFY})
    public AdminResult<LinkedList<BorrowCommonImage>> uploadFile(HttpServletRequest request) throws Exception {
        AdminResult<LinkedList<BorrowCommonImage>> adminResult = new AdminResult<>();
        try {
            adminResult.setData(fileUpLoadUtil.upLoad(request));
            adminResult.setStatus(SUCCESS);
            adminResult.setStatusDesc(SUCCESS_DESC);
            return adminResult;
        } catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }

    @ApiOperation(value = "删除配置信息", notes = "删除配置信息")
    @RequestMapping(value = "/deleteAction/{id}", method = RequestMethod.GET)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteRecordAction(@PathVariable int id) {
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
