/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.msgpush;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.MessagePushTagService;
import com.hyjf.admin.utils.AdminValidatorFieldCheckUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.MessagePushTagResponse;
import com.hyjf.am.resquest.config.MessagePushTagRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowCommonImageVO;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.CustomConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
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

    @ApiOperation(value = "初始化页面", notes = "标签管理初始化页面")
    @RequestMapping(value = "/init",method = RequestMethod.POST)
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
    @RequestMapping(value = "/infoAction",method = RequestMethod.GET)
    public AdminResult infoAction(Integer id) {
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
    @RequestMapping(value = "insertAction",method = RequestMethod.POST)
    public AdminResult insertAction(HttpServletRequest request, @RequestBody MessagePushTagRequest tagRequest) {
        AdminSystemVO user = getUser(request);
        String userName = user.getUsername();
        tagRequest.setCreateUserName(userName);
        JSONObject json = new JSONObject();
        json = this.validatorFieldCheckAudit(json, tagRequest);
        if (AdminValidatorFieldCheckUtil.hasValidateError(json)) {
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
    @RequestMapping(value = "/updateAction",method = RequestMethod.POST)
    public AdminResult updateAction(HttpServletRequest request, @RequestBody MessagePushTagRequest tagRequest) {
        AdminSystemVO user = getUser(request);
        String userName = user.getUsername();
        tagRequest.setCreateUserName(userName);
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
    @RequestMapping(value = "/deleteAction",method = RequestMethod.GET)
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

    @ApiOperation(value = "修改状态", notes = "修改装态")
    @RequestMapping(value = "/statusAction",method = RequestMethod.GET)
    public AdminResult updateStatus(Integer id) {
        if (id != null) {
            MessagePushTagResponse response = messagePushTagService.getRecord(id);
            MessagePushTagVO record = response.getResult();
            // 新建状态只能启用，启用只能禁用，禁用只能启用
            if (record.getStatus().intValue() == CustomConstants.MSG_PUSH_STATUS_0) {
                record.setStatus(CustomConstants.MSG_PUSH_STATUS_1);
            } else if (record.getStatus() == CustomConstants.MSG_PUSH_STATUS_1) {
                record.setStatus(CustomConstants.MSG_PUSH_STATUS_2);
            } else if (record.getStatus() == CustomConstants.MSG_PUSH_STATUS_2) {
                record.setStatus(CustomConstants.MSG_PUSH_STATUS_1);
            }
            MessagePushTagResponse tagResponse = messagePushTagService.updatePushTag(record);
            if (response == null) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            if (!Response.isSuccess(response)) {
                return new AdminResult<>(FAIL, response.getMessage());
            }
            return new AdminResult<>(response);
        }
        return new AdminResult<>(FAIL, FAIL_DESC);
    }


    @ApiOperation(value = "检查名称唯一性", notes = "检查名称唯一")
    @RequestMapping(value = "/checkAction",method = RequestMethod.POST)
    public AdminResult checkAction(@RequestBody MessagePushTagRequest request) {
        Integer id = request.getId();
        String tagCode = request.getTagCode();
        MessagePushTagResponse response = messagePushTagService.countByTagCode(id, tagCode);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        if (response.getCount() > 0) {
            String message = AdminValidatorFieldCheckUtil.getErrorMessage("repeat", "");
            message = message.replace("{label}", "标签编码");
            response.setMessage(message);
        }
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "文件上传",notes = "文件上传")
    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    public String uploadFile(HttpServletRequest request) throws Exception {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
        String fileDomainUrl = FILEDOMAINURL;
        String filePhysicalPath = FILEPHYSICALPATH;
        String fileUploadTempPath = FILEUPLOADTEMPPATH;

        String logoRealPathDir = filePhysicalPath + fileUploadTempPath;

        File logoSaveFile = new File(logoRealPathDir);
        if (!logoSaveFile.exists()) {
            logoSaveFile.mkdirs();
        }

        BorrowCommonImageVO fileMeta = null;
        LinkedList<BorrowCommonImageVO> files = new LinkedList<BorrowCommonImageVO>();

        Iterator<String> itr = multipartRequest.getFileNames();
        MultipartFile multipartFile = null;

        while (itr.hasNext()) {
            multipartFile = multipartRequest.getFile(itr.next());
            String fileRealName = String.valueOf(new Date().getTime());
            String originalFilename = multipartFile.getOriginalFilename();
            fileRealName = fileRealName + UploadFileUtils.getSuffix(multipartFile.getOriginalFilename());
            // 图片上传
            String errorMessage = UploadFileUtils.upload4Stream(fileRealName, logoRealPathDir, multipartFile.getInputStream(), 5000000L);

            fileMeta = new BorrowCommonImageVO();
            int index = originalFilename.lastIndexOf(".");
            if (index != -1) {
                fileMeta.setImageName(originalFilename.substring(0, index));
            } else {
                fileMeta.setImageName(originalFilename);
            }

            fileMeta.setImageRealName(fileRealName);
            fileMeta.setImageSize(multipartFile.getSize() / 1024 + "");// KB
            fileMeta.setImageType(multipartFile.getContentType());
            fileMeta.setErrorMessage(errorMessage);
            // 获取文件路径
            fileMeta.setImagePath(fileUploadTempPath + fileRealName);
            fileMeta.setImageSrc(fileDomainUrl + fileUploadTempPath + fileRealName);
            files.add(fileMeta);
        }
        return JSONObject.toJSONString(files, true);
    }

    /**
     * 参数校验
     *
     * @param json
     * @param tagRequest
     * @return
     */
    private JSONObject validatorFieldCheckAudit(JSONObject json, MessagePushTagRequest tagRequest) {
        AdminValidatorFieldCheckUtil.validateRequired(json, "tagName", tagRequest.getTagName());
        AdminValidatorFieldCheckUtil.validateRequired(json, "tagCode", tagRequest.getTagCode());
        AdminValidatorFieldCheckUtil.validateRequired(json, "introduction", tagRequest.getIntroduction());
        AdminValidatorFieldCheckUtil.validateRequired(json, "iconUrl", tagRequest.getIconUrl());
        return json;
    }
}
