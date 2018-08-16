/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.msgpush;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.MessagePushMsgService;
import com.hyjf.admin.service.MessagePushTagService;
import com.hyjf.admin.service.MessagePushTemplateService;
import com.hyjf.admin.utils.AdminValidatorFieldCheckUtil;
import com.hyjf.admin.utils.ValidatorFieldCheckUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.MessagePushMsgResponse;
import com.hyjf.am.response.config.MessagePushTemplateResponse;
import com.hyjf.am.resquest.message.MessagePushMsgRequest;
import com.hyjf.am.vo.admin.MessagePushMsgVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowCommonImageVO;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.util.GetMessageIdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * @author yaoyong
 * @version MessagePushMessageController, v0.1 2018/8/15 13:54
 */
@Api(tags = "消息中心-app消息推送-手动发放消息")
@RestController
@RequestMapping("/hyjf-admin/msgPush/message")
public class MessagePushMessageController extends BaseController {

    @Value("${file.domain.url}")
    private String FILEDOMAINURL;
    @Value("${file.physical.path}")
    private String FILEPHYSICALPATH;
    @Value("${file.upload.temp.path}")
    private String FILEUPLOADTEMPPATH;

    @Autowired
    private MessagePushMsgService messagePushMsgService;
    @Autowired
    private MessagePushTagService messagePushTagService;
    @Autowired
    private MessagePushTemplateService messagePushTemplateService;

    @ApiOperation(value = "页面初始化",notes = "页面初始化")
    @RequestMapping(value = "/init",method = RequestMethod.POST)
    public AdminResult<MessagePushMsgResponse> init(@RequestBody MessagePushMsgRequest request) {
        MessagePushMsgResponse response = messagePushMsgService.selectMessagePushMsg(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        // 标签类型
        List<MessagePushTagVO> messagesPushTags = this.messagePushTagService.getAllPushTagList();
        response.setTemplatePushTags(messagesPushTags);
        prepareDatas(response);
        return new AdminResult<MessagePushMsgResponse>(response);
    }

    @ApiOperation(value = "详情页信息", notes = "详情页信息")
    @RequestMapping(value = "/infoAction",method = RequestMethod.POST)
    public AdminResult<MessagePushMsgResponse> infoAction(Integer id, MessagePushMsgRequest form) {
        MessagePushMsgResponse response = new MessagePushMsgResponse();
        try {
            response = messagePushMsgService.getRecord(id);
            if (response.getResult() != null) {
                MessagePushMsgVO record = response.getResult();
                BeanUtils.copyProperties(record, form);
                if (record.getMsgAction() == CustomConstants.MSG_PUSH_TEMP_ACT_0) {
                    form.setMsgActionUrl1("");
                    form.setMsgActionUrl2("");
                }
                if (record.getMsgAction() == CustomConstants.MSG_PUSH_TEMP_ACT_1) {
                    form.setMsgActionUrl1(record.getMsgActionUrl());
                    form.setMsgActionUrl2("");
                }
                if (record.getMsgAction() == CustomConstants.MSG_PUSH_TEMP_ACT_3) {
                    form.setMsgActionUrl3(record.getMsgActionUrl());
                    form.setMsgActionUrl2("");
                }
                if (record.getMsgAction().intValue() == CustomConstants.MSG_PUSH_TEMP_ACT_2) {
                    form.setMsgActionUrl1("");
                    form.setMsgActionUrl2(record.getMsgActionUrl());
                }
                // 如果是转发,则form的id应置为空
                if (StringUtils.isNotEmpty(form.getUpdateOrReSend()) && form.getUpdateOrReSend().equals("2")) {
                    form.setId(null);
                }
                if (form.getMsgSendType().intValue() == CustomConstants.MSG_PUSH_SEND_TYPE_1) {
                    if (form.getPreSendTime() != null) {
                        form.setMessagesPreSendTimeStr(GetDate.timestamptoStrYYYYMMDDHHMMSS(form.getPreSendTime()));
                    }
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


    @ApiOperation(value = "添加手动发送短信", notes = "添加手动发送短信")
    @RequestMapping(value = "/insertAction",method = RequestMethod.POST)
    public AdminResult<MessagePushMsgResponse> insertAction(HttpServletRequest request, MessagePushMsgRequest templateRequest) {
        MessagePushMsgResponse response = new MessagePushMsgResponse();
        AdminSystemVO user = getUser(request);
        String username = user.getUsername();

        // 调用校验
        ModelAndView modelAndView = new ModelAndView();
        if (validatorFieldCheck(modelAndView, templateRequest)) {
            // 标签类型
            List<MessagePushTagVO> templatePushTags = this.messagePushTagService.getTagList();
            response.setTemplatePushTags(templatePushTags);
            prepareDatas(response);
            return new AdminResult<>(response);
        }
        MessagePushMsgVO templateVO = new MessagePushMsgVO();
        BeanUtils.copyProperties(request, templateVO);
        if (templateRequest.getMsgAction() == CustomConstants.MSG_PUSH_TEMP_ACT_0) {
            templateVO.setMsgActionUrl("");
        }
        if (templateRequest.getMsgAction() == CustomConstants.MSG_PUSH_TEMP_ACT_1 || templateRequest.getMsgAction() == CustomConstants.MSG_PUSH_TEMP_ACT_3) {
            templateVO.setMsgActionUrl(templateRequest.getMsgActionUrl1());
        }
        if (templateRequest.getMsgAction() == CustomConstants.MSG_PUSH_TEMP_ACT_2) {
            templateVO.setMsgActionUrl(templateRequest.getMsgActionUrl2());
        }
        if (templateRequest.getMsgSendType() == CustomConstants.MSG_PUSH_SEND_TYPE_1) {
            templateVO.setSendTime(GetDate.timestamptoStrYYYYMMDD(GetDate.getNowTime10()));
            if (StringUtils.isNotEmpty(templateRequest.getMessagesPreSendTimeStr())) {
                try {
                    Integer time = GetDate.strYYYYMMDDHHMMSS2Timestamp2(templateRequest.getMessagesPreSendTimeStr());
                    if (time != 0) {
                        templateVO.setPreSendTime(time);
                        templateVO.setSendTime(GetDate.getDateMyTimeInMillis(time));
                    }
                } catch (Exception e) {
                }
            }
        } else {
            templateVO.setPreSendTime(null);
            templateVO.setSendTime(GetDate.getDateMyTimeInMillis(GetDate.getNowTime10()));
        }
        String msgCode = GetMessageIdUtil.getNewMsgCode(templateVO.getTagCode());
        templateVO.setMsgCode(msgCode);// 设置ID
        templateVO.setMsgSendStatus(CustomConstants.MSG_PUSH_MSG_STATUS_0);// 设置默认状态
        templateVO.setMsgDestinationType(CustomConstants.MSG_PUSH_DESTINATION_TYPE_1);
        response = this.messagePushMsgService.insertRecord(templateVO);
        return new AdminResult<>(response);
    }


    @ApiOperation(value = "修改手动发放消息", notes = "修改手动发放消息")
    @RequestMapping(value = "/updateAction",method = RequestMethod.POST)
    public AdminResult updateAction(HttpServletRequest request, MessagePushMsgRequest templateRequest) {
        MessagePushMsgResponse response = new MessagePushMsgResponse();
        AdminSystemVO user = getUser(request);
        String username = user.getUsername();
        // 调用校验
        ModelAndView modelAndView = new ModelAndView();
        if (validatorFieldCheck(modelAndView, templateRequest)) {
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
        if (templateRequest.getMsgAction() == CustomConstants.MSG_PUSH_TEMP_ACT_0) {
            templateRequest.setMsgActionUrl("");
        }
        if (templateRequest.getMsgAction() == CustomConstants.MSG_PUSH_TEMP_ACT_1) {
            templateRequest.setMsgActionUrl(templateRequest.getMsgActionUrl1());
        }
        if (templateRequest.getMsgAction() == CustomConstants.MSG_PUSH_TEMP_ACT_3) {
            templateRequest.setMsgActionUrl(templateRequest.getMsgActionUrl3());
        }
        if (templateRequest.getMsgAction() == CustomConstants.MSG_PUSH_TEMP_ACT_2) {
            templateRequest.setMsgActionUrl(templateRequest.getMsgActionUrl2());
        }
        if (templateRequest.getMsgSendType().intValue() == CustomConstants.MSG_PUSH_SEND_TYPE_1) {
            templateRequest.setSendTime(GetDate.getMyTimeInMillis());
            if (StringUtils.isNotEmpty(templateRequest.getMessagesPreSendTimeStr())) {
                try {
                    Integer time = GetDate.strYYYYMMDDHHMMSS2Timestamp2(templateRequest.getMessagesPreSendTimeStr());
                    if (time != 0) {
                        templateRequest.setPreSendTime(time);
                        templateRequest.setSendTime(time);
                    }
                } catch (Exception e) {
                }
            }
        } else {
            templateRequest.setPreSendTime(null);
            templateRequest.setSendTime(GetDate.getNowTime10());
        }

        response = messagePushMsgService.updateMessagePushMsg(templateRequest);
        return new AdminResult<>(response);
    }


    @ApiOperation(value = "删除手动发送消息",notes = "删除手动发送消息")
    @RequestMapping(value = "/deleteAction", method = RequestMethod.GET)
    public AdminResult deleteAction(String ids) {
        if (ids == null) {
            return new AdminResult<>(FAIL,FAIL_DESC);
        }
        List<Integer> recordList = JSONArray.parseArray(ids, Integer.class);
        MessagePushMsgResponse response = messagePushMsgService.deleteAction(recordList);
        if (response.getCount() > 0) {
            return new AdminResult<>(response);
        }
        return new AdminResult<>(FAIL,FAIL_DESC);
    }


    @ApiOperation(value = "检查是否是url",notes = "检查是否是url")
    @RequestMapping(value = "/checkUrlAction",method = RequestMethod.POST)
    public AdminResult checkUrlAction(HttpServletRequest request) {
        MessagePushMsgResponse response = new MessagePushMsgResponse();
        // 检查着名称唯一性
        Boolean isUrl = true;// TODO Validator.isUrl2(templateActionUrl1);
        if (!isUrl) {
            response.setRtn(Response.FAIL);
            response.setMessage("请输入正确的http地址（全路径）!");
        }
        // 没有错误时,返回y
        return new AdminResult<>(response);
    }



    @ApiOperation(value = "资料上传",notes = "资料上传")
    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
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
     * 准备各种枚举
     *
     * @param response
     */
    private MessagePushMsgResponse prepareDatas(MessagePushMsgResponse response) {
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

    /**
     * @param request
     * @return
     */
    private boolean validatorFieldCheck(ModelAndView modelAndView, MessagePushMsgRequest request) {
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "tagId", request.getTagId().toString());
        ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "msgTitle", request.getMsgTitle(),20,true);
        ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "msgImageUrl", request.getMsgImageUrl(),100,false);
        ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "msgContent", request.getMsgContent(),4000,true);
        ValidatorFieldCheckUtil.validateMaxLength(modelAndView, "msgTerminal", request.getMsgTerminal(),20,true);
        ValidatorFieldCheckUtil.validateRequired(modelAndView, "msgAction", request.getMsgAction().toString());
        return true;
    }


}
