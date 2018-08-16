/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.msgpush;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.MessagePushTagService;
import com.hyjf.admin.service.MessagePushTemplateService;
import com.hyjf.admin.utils.AdminValidatorFieldCheckUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.MessagePushTemplateResponse;
import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowCommonImageVO;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.CustomConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author yaoyong
 * @version MessagePushTemplateController, v0.1 2018/8/14 19:58
 */
@Api(tags = "消息推送消息模板")
@RestController
@RequestMapping("/hyjf-admin/msgPush/template")
public class MessagePushTemplateController extends BaseController {

    @Value("${file.domain.url}")
    private String FILEDOMAINURL;
    @Value("${file.physical.path}")
    private String FILEPHYSICALPATH;
    @Value("${file.upload.temp.path}")
    private String FILEUPLOADTEMPPATH;

    @Autowired
    private MessagePushTemplateService messagePushTemplateService;
    @Autowired
    private MessagePushTagService messagePushTagService;

    @ApiOperation(value = "页面初始化", notes = "页面初始化")
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public AdminResult<MessagePushTemplateResponse> init(MsgPushTemplateRequest request) {
        MessagePushTemplateResponse response = messagePushTemplateService.searchList(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        // 标签类型
        List<MessagePushTagVO> templatePushTags = this.messagePushTagService.getAllPushTagList();
        response.setTemplatePushTags(templatePushTags);
        return new AdminResult<MessagePushTemplateResponse>(response);
    }

    @ApiOperation(value = "详情页信息", notes = "详情页信息")
    @RequestMapping(value = "/infoAction", method = RequestMethod.POST)
    public AdminResult<MessagePushTemplateResponse> infoAction(Integer id, MsgPushTemplateRequest form) {
        MessagePushTemplateResponse response = new MessagePushTemplateResponse();
        try {
            response = messagePushTemplateService.getRecord(id);
            if (response.getResult() != null) {
                MessagePushTemplateVO record = response.getResult();
                BeanUtils.copyProperties(record, form);
                if (record.getTemplateAction() == CustomConstants.MSG_PUSH_TEMP_ACT_0) {
                    form.setTemplateActionUrl1("");
                    form.setTemplateActionUrl2("");
                }
                if (record.getTemplateAction() == CustomConstants.MSG_PUSH_TEMP_ACT_1) {
                    form.setTemplateActionUrl1(record.getTemplateActionUrl());
                    form.setTemplateActionUrl2("");
                }
                if (record.getTemplateAction() == CustomConstants.MSG_PUSH_TEMP_ACT_3) {
                    form.setTemplateActionUrl3(record.getTemplateActionUrl());
                    form.setTemplateActionUrl2("");
                }
                if (record.getTemplateAction() == CustomConstants.MSG_PUSH_TEMP_ACT_2) {
                    form.setTemplateActionUrl1("");
                    form.setTemplateActionUrl2(record.getTemplateActionUrl());
                }
                if (StringUtils.isNotEmpty(record.getTemplateCode()) && record.getTemplateCode().contains("_")) {
                    form.setTemplateCode(record.getTemplateCode().substring(record.getTemplateCode().indexOf("_") + 1, record.getTemplateCode().length()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<MessagePushTagVO> templatePushTags = this.messagePushTagService.getTagList();
        response.setTemplatePushTags(templatePushTags);
        prepareDatas(response);
        return new AdminResult<>(response);
    }


    @ApiOperation(value = "添加模板", notes = "添加模板")
    @RequestMapping(value = "/insertAction", method = RequestMethod.POST)
    public AdminResult<MessagePushTemplateResponse> insertAction(HttpServletRequest request, MsgPushTemplateRequest templateRequest) {
        MessagePushTemplateResponse response = new MessagePushTemplateResponse();
        AdminSystemVO user = getUser(request);
        String username = user.getUsername();

        // 调用校验
        JSONObject jsonObject = new JSONObject();
        if (validatorFieldCheck(jsonObject, templateRequest) != null) {
            // 标签类型
            List<MessagePushTagVO> templatePushTags = this.messagePushTagService.getTagList();
            response.setTemplatePushTags(templatePushTags);
            prepareDatas(response);
            return new AdminResult<>(response);
        }
        MessagePushTemplateVO templateVO = new MessagePushTemplateVO();
        BeanUtils.copyProperties(request, templateVO);
        if (templateRequest.getTemplateAction().intValue() == CustomConstants.MSG_PUSH_TEMP_ACT_0) {
            templateVO.setTemplateActionUrl("");
        }
        if (templateRequest.getTemplateAction().intValue() == CustomConstants.MSG_PUSH_TEMP_ACT_1 || templateRequest.getTemplateAction().intValue() == CustomConstants.MSG_PUSH_TEMP_ACT_3) {
            templateVO.setTemplateActionUrl(templateRequest.getTemplateActionUrl1());
        }
        if (templateRequest.getTemplateAction().intValue() == CustomConstants.MSG_PUSH_TEMP_ACT_2) {
            templateVO.setTemplateActionUrl(templateRequest.getTemplateActionUrl2());
        }
        templateVO.setTemplateCode(templateRequest.getTagCode() + "_" + templateRequest.getTemplateCode());
        templateVO.setCreateUserName(username);
        templateVO.setLastupdateUserName(username);
        response = messagePushTemplateService.insertAction(templateVO);
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "修改模板", notes = "修改模板")
    @RequestMapping(value = "/updateAction", method = RequestMethod.POST)
    public AdminResult updateAction(HttpServletRequest request, MsgPushTemplateRequest templateRequest) {
        MessagePushTemplateResponse response = new MessagePushTemplateResponse();
        AdminSystemVO user = getUser(request);
        String username = user.getUsername();
        // 调用校验
        JSONObject jsonObject = new JSONObject();
        if (validatorFieldCheck(jsonObject, templateRequest) != null) {
            // 标签类型
            List<MessagePushTagVO> templatePushTags = this.messagePushTagService.getTagList();
            response.setTemplatePushTags(templatePushTags);
            prepareDatas(response);
            return new AdminResult<>(response);
        }
        if (templateRequest.getId() == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        // 跟新
        if (templateRequest.getTemplateAction() == CustomConstants.MSG_PUSH_TEMP_ACT_0) {
            templateRequest.setTemplateActionUrl("");
        }
        if (templateRequest.getTemplateAction() == CustomConstants.MSG_PUSH_TEMP_ACT_1) {
            templateRequest.setTemplateActionUrl(templateRequest.getTemplateActionUrl1());
        }
        if (templateRequest.getTemplateAction() == CustomConstants.MSG_PUSH_TEMP_ACT_3) {
            templateRequest.setTemplateActionUrl(templateRequest.getTemplateActionUrl3());
        }
        if (templateRequest.getTemplateAction() == CustomConstants.MSG_PUSH_TEMP_ACT_2) {
            templateRequest.setTemplateActionUrl(templateRequest.getTemplateActionUrl2());
        }
        templateRequest.setTemplateCode(templateRequest.getTagCode() + "_" + templateRequest.getTemplateCode());
        templateRequest.setCreateUserName(username);
        response = this.messagePushTemplateService.updateRecord(templateRequest);
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "删除模板", notes = "删除模板")
    @RequestMapping(value = "/deleteAction", method = RequestMethod.GET)
    public AdminResult deleteAction(String ids) {
        if (ids == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        List<Integer> recordList = JSONArray.parseArray(ids, Integer.class);
        MessagePushTemplateResponse response = messagePushTemplateService.deleteAction(recordList);
        if (response.getCount() > 0) {
            return new AdminResult<>(response);
        }
        return new AdminResult<>(FAIL, FAIL_DESC);

    }


    @ApiOperation(value = "修改状态", notes = "修改装态")
    @RequestMapping(value = "/statusAction", method = RequestMethod.POST)
    public AdminResult updateStatus(Integer id) {
        if (id != null) {
            MessagePushTemplateResponse response = messagePushTemplateService.getRecord(id);
            MessagePushTemplateVO record = response.getResult();
            // 新建状态只能启用，启用只能禁用，禁用只能启用
            if (record.getStatus().intValue() == CustomConstants.MSG_PUSH_STATUS_0) {
                record.setStatus(CustomConstants.MSG_PUSH_STATUS_1);
            } else if (record.getStatus() == CustomConstants.MSG_PUSH_STATUS_1) {
                record.setStatus(CustomConstants.MSG_PUSH_STATUS_2);
            } else if (record.getStatus() == CustomConstants.MSG_PUSH_STATUS_2) {
                record.setStatus(CustomConstants.MSG_PUSH_STATUS_1);
            }
            MsgPushTemplateRequest request = new MsgPushTemplateRequest();
            BeanUtils.copyProperties(record, request);
            MessagePushTemplateResponse tagResponse = messagePushTemplateService.updateRecord(request);
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
    @RequestMapping(value = "/checkAction", method = RequestMethod.POST)
    public AdminResult checkAction(MsgPushTemplateRequest request) {
        Integer id = request.getId();
        String tagCode = request.getTagCode();
        MessagePushTemplateResponse response = messagePushTemplateService.countByTemplateCode(id, request.getTemplateCode());
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


    @ApiOperation(value = "检查是否是url", notes = "检查是否是url")
    @RequestMapping(value = "/checkUrlAction", method = RequestMethod.POST)
    public AdminResult checkUrlAction(HttpServletRequest request) {
        MessagePushTemplateResponse response = new MessagePushTemplateResponse();
        // 检查着名称唯一性
        Boolean isUrl = true;// TODO Validator.isUrl2(templateActionUrl1);
        if (!isUrl) {
            response.setRtn(Response.FAIL);
            response.setMessage("请输入正确的http地址（全路径）!");
        }
        // 没有错误时,返回y
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "资料上传", notes = "资料上传")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
     * @param request
     * @return
     */
    private JSONObject validatorFieldCheck(JSONObject jsonObject, MsgPushTemplateRequest request) {
        AdminValidatorFieldCheckUtil.validateRequired(jsonObject, "tagId", request.getTagId().toString());
        AdminValidatorFieldCheckUtil.validateRequired(jsonObject, "templateCode", request.getTemplateCode());
        AdminValidatorFieldCheckUtil.validateRequired(jsonObject, "templateCode", request.getTemplateCode());
        AdminValidatorFieldCheckUtil.validateRequired(jsonObject, "templateCode", request.getTemplateCode());
        AdminValidatorFieldCheckUtil.validateRequired(jsonObject, "templateCode", request.getTemplateCode());
        AdminValidatorFieldCheckUtil.validateRequired(jsonObject, "tagCode", request.getTagCode());
        return jsonObject;
    }


    /**
     * 准备各种枚举
     *
     * @param response
     */
    private MessagePushTemplateResponse prepareDatas(MessagePushTemplateResponse response) {

        String nameClass = "MSG_PUSH_STATUS";
        String nameClass2 = "MSG_PUSH_TEMP_ACT";
        String nameClass3 = "MSG_PUSH_NATUREURLS";
        // ======================拼接枚举======================
        // 标签状态
        List<ParamNameVO> templateStatus = this.messagePushTemplateService.getParamNameList(nameClass);
        response.setTemplateStatus(templateStatus);
        // 后续动作
        List<ParamNameVO> templateActions = this.messagePushTemplateService.getParamNameList(nameClass2);
        response.setTemplateActions(templateActions);
        // 推送终端
        List<ParamNameVO> plats = new ArrayList<ParamNameVO>();
        ParamNameVO paramName1 = new ParamNameVO();
        paramName1.setNameCd(CustomConstants.CLIENT_ANDROID);
        paramName1.setName("AndroidAPP");
        ParamNameVO paramName2 = new ParamNameVO();
        paramName2.setNameCd(CustomConstants.CLIENT_IOS);
        paramName2.setName("IOSAPP");
        plats.add(paramName1);
        plats.add(paramName2);
        response.setPlats(plats);
        // 原生页面
        List<ParamNameVO> naturePages = this.messagePushTemplateService.getParamNameList(nameClass3);
        response.setNaturePages(naturePages);
        return response;
    }
}