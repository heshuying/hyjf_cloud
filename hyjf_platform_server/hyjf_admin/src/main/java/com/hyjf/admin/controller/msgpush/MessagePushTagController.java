/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.msgpush;

import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ActivityListService;
import com.hyjf.admin.service.MessagePushNoticesService;
import com.hyjf.admin.service.MessagePushTagService;
import com.hyjf.admin.utils.AdminValidatorFieldCheckUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.MessagePushTagResponse;
import com.hyjf.am.resquest.config.MessagePushTagRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetterUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.LinkedList;
import java.util.List;

/**
 * @author yaoyong
 * @version MessagePushTagController, v0.1 2018/8/14 14:30
 */
@Api(tags = "消息中心-app消息推送-标签管理")
@RestController
@RequestMapping("/hyjf-admin/msgPush/tagManage")
public class MessagePushTagController extends BaseController {

    @Value("${file.domain.url}")
    private String FILEDOMAINURL;
    @Value("${file.physical.path}")
    private String FILEPHYSICALPATH;
    @Value("${file.upload.temp.path}")
    private String FILEUPLOADTEMPPATH;
    @Autowired
    private MessagePushTagService messagePushTagService;
    @Autowired
    private MessagePushNoticesService messagePushNoticesService;

    @ApiOperation(value = "初始化页面", notes = "标签管理初始化页面")
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public AdminResult<MessagePushTagResponse> init(@RequestBody MessagePushTagRequest request) {
        MessagePushTagResponse response = messagePushTagService.searchList(request);
        String nameClass = "MSG_PUSH_STATUS";
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<ParamNameVO> tagStatus = messagePushTagService.getParamNameList(nameClass);
        response.setParamNameVOS(tagStatus);

        return new AdminResult<MessagePushTagResponse>(response);
    }


    @ApiOperation(value = "详情页", notes = "详情页")
    @RequestMapping(value = "/infoAction", method = RequestMethod.GET)
    public AdminResult infoAction(@RequestParam Integer id) {
        MessagePushTagResponse response = messagePushTagService.getRecord(id);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        response.setFileDomainUrl(FILEDOMAINURL);
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "添加信息", notes = "添加信息")
    @RequestMapping(value = "insertAction", method = RequestMethod.POST)
    public AdminResult insertAction(HttpServletRequest request, @RequestBody MessagePushTagRequest tagRequest) {
        AdminSystemVO user = getUser(request);
        String userName = user.getUsername();
        tagRequest.setCreateUserName(userName);
        tagRequest.setCreateUserId(Integer.parseInt(user.getId()));
        String message = validatorFieldCheckAudit(tagRequest);
        if (message != null) {
            return new AdminResult<>(FAIL, "校验失败");
        }
        MessagePushTagResponse response = messagePushTagService.insertMessagePushTag(tagRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "修改信息", notes = "修改信息")
    @RequestMapping(value = "/updateAction", method = RequestMethod.POST)
    public AdminResult updateAction(HttpServletRequest request, @RequestBody MessagePushTagRequest tagRequest) {
        AdminSystemVO user = getUser(request);
        String userName = user.getUsername();
        tagRequest.setUpdateUserName(userName);
        tagRequest.setUpdateUserId(Integer.parseInt(user.getId()));
        String message = validatorFieldCheckAudit(tagRequest);
        if (message != null) {
            return new AdminResult<>(FAIL, "校验失败");
        }
        MessagePushTagResponse response = messagePushTagService.updateMessagePushTag(tagRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "删除信息", notes = "删除信息")
    @RequestMapping(value = "/deleteAction", method = RequestMethod.GET)
    public AdminResult deleteAction(Integer id) {
        MessagePushTagResponse response = messagePushTagService.deleteAction(id);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "修改状态", notes = "修改状态")
    @RequestMapping(value = "/statusAction", method = RequestMethod.GET)
    public AdminResult updateStatus(@RequestParam Integer id) {
        MessagePushTagResponse response = messagePushTagService.getRecord(id);
        MessagePushTagVO record = response.getResult();
        // 新建状态只能启用，启用只能禁用，禁用只能启用
        if (record.getStatus() == CustomConstants.MSG_PUSH_STATUS_0) {
            record.setStatus(CustomConstants.MSG_PUSH_STATUS_1);
        } else if (record.getStatus() == CustomConstants.MSG_PUSH_STATUS_1) {
            record.setStatus(CustomConstants.MSG_PUSH_STATUS_2);
        } else if (record.getStatus() == CustomConstants.MSG_PUSH_STATUS_2) {
            record.setStatus(CustomConstants.MSG_PUSH_STATUS_1);
        }
        MessagePushTagResponse tagResponse = messagePushTagService.updatePushTag(record);
        if (tagResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(tagResponse)) {
            return new AdminResult<>(FAIL, tagResponse.getMessage());
        }
        return new AdminResult<>(tagResponse);
    }


    @ApiOperation(value = "检查名称唯一性", notes = "检查名称唯一")
    @RequestMapping(value = "/checkAction", method = RequestMethod.POST)
    public AdminResult checkAction(@RequestBody MessagePushTagRequest request) {
        Integer id = 0;
        if (request.getId() != null) {
            id = request.getId();
        }
        String tagCode = request.getTagCode();
        MessagePushTagResponse response = messagePushTagService.countByTagCode(id, tagCode);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        if (response.getCount() > 0) {
            String message = "标签重复";
            response.setMessage(message);
        }
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "文件上传", notes = "文件上传")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public AdminResult<LinkedList<BorrowCommonImage>> uploadFile(HttpServletRequest request) throws Exception {
        AdminResult<LinkedList<BorrowCommonImage>> adminResult = new AdminResult<>();
        try {
            LinkedList<BorrowCommonImage> borrowCommonImages = messagePushNoticesService.uploadFile(request);
            adminResult.setData(borrowCommonImages);
            adminResult.setStatus(SUCCESS);
            adminResult.setStatusDesc(SUCCESS_DESC);
            return adminResult;
        } catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }

    /**
     * 参数校验
     *
     * @param tagRequest
     * @return
     */
    private String validatorFieldCheckAudit(MessagePushTagRequest tagRequest) {
        String message = null;
        if (tagRequest.getTagName() == null) {
            message = "标签名不能为空";
        }
        if (tagRequest.getTagCode() == null) {
            message = "标签编号不能为空";
        }
        if (tagRequest.getIntroduction() == null) {
            message = "描述不能为空";
        }
        if (tagRequest.getIconUrl() == null) {
            message = "icon图标不能为空";
        }
        return message;
    }
}
