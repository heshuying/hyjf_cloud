/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.msgpush;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.MessagePushTagService;
import com.hyjf.admin.service.MessagePushTemplateService;
import com.hyjf.admin.utils.FileUpLoadUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.MessagePushTemplateResponse;
import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author yaoyong
 * @version MessagePushTemplateController, v0.1 2018/8/14 19:58
 */
@Api(tags = "消息中心-app消息推送-消息模板")
@RestController
@RequestMapping("/hyjf-admin/msgPush/template")
public class MessagePushTemplateController extends BaseController {

    @Autowired
    private MessagePushTemplateService messagePushTemplateService;
    @Autowired
    private MessagePushTagService messagePushTagService;

    /** 权限关键字 */
    public static final String PERMISSIONS = "msgpushtemplate";

    @ApiOperation(value = "页面初始化", notes = "页面初始化")
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<MessagePushTemplateResponse> init(@RequestBody MsgPushTemplateRequest request) {
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
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD, ShiroConstants.PERMISSION_MODIFY})
    public AdminResult<MessagePushTemplateResponse> infoAction(@RequestBody MsgPushTemplateRequest form) {
        MessagePushTemplateResponse response = new MessagePushTemplateResponse();
        if (form.getId() != null) {
            try {
                response = messagePushTemplateService.getRecord(form.getId());
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
                        form.setTemplateActionUrl("");
                        if ("hyjf://jumpZXH".equals(record.getTemplateActionUrl())) {
                            form.setTemplateActionUrl2("1");
                        }
                        if ("hyjf://jumpMine".equals(record.getTemplateActionUrl())) {
                            form.setTemplateActionUrl2("2");
                        }
                        if ("hyjf://jumpCouponsList".equals(record.getTemplateActionUrl())) {
                            form.setTemplateActionUrl2("3");
                        }
                        if ("hyjf://jumpTransactionDetail".equals(record.getTemplateActionUrl())) {
                            form.setTemplateActionUrl2("4");
                        }
                        if ("hyjf://jumpInvest".equals(record.getTemplateActionUrl())) {
                            form.setTemplateActionUrl2("5");
                        }
                    }
                    if (StringUtils.isNotEmpty(record.getTemplateCode()) && record.getTemplateCode().contains("_")) {
                        form.setTemplateCode(record.getTemplateCode().substring(record.getTemplateCode().indexOf("_") + 1, record.getTemplateCode().length()));
                    }
                    BeanUtils.copyProperties(form, record);
                    if (record.getTemplateAction() == CustomConstants.MSG_PUSH_TEMP_ACT_2) {
                        record.setTemplateActionUrl(form.getTemplateActionUrl2());
                    }
                }
            } catch (Exception e) {
                logger.error("获取消息模板详情失败，失败原因：{}", e);
            }
        }
        List<MessagePushTagVO> templatePushTags = this.messagePushTagService.getTagList();
        response.setTemplatePushTags(templatePushTags);
        prepareDatas(response);
        return new AdminResult<>(response);
    }


    @ApiOperation(value = "添加模板", notes = "添加模板")
    @RequestMapping(value = "/insertAction", method = RequestMethod.POST)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult<MessagePushTemplateResponse> insertAction(HttpServletRequest request, @RequestBody MsgPushTemplateRequest templateRequest) {
        MessagePushTemplateResponse response = new MessagePushTemplateResponse();
        AdminSystemVO user = getUser(request);
        String userId = user.getId();
        templateRequest.setStatus(0);

        // 调用校验
        String message = validatorFieldCheck(templateRequest);
        if (message != null) {
            // 标签类型
            List<MessagePushTagVO> templatePushTags = this.messagePushTagService.getTagList();
            response.setTemplatePushTags(templatePushTags);
            response.setMessage(message);
            prepareDatas(response);
            return new AdminResult<>(response);
        }
        MessagePushTemplateVO templateVO = new MessagePushTemplateVO();
        BeanUtils.copyProperties(templateRequest, templateVO);
        templateVO.setTagId(templateRequest.getTagId());
        if (templateRequest.getTemplateAction() == CustomConstants.MSG_PUSH_TEMP_ACT_0) {
            templateVO.setTemplateActionUrl("");
        }
        if (templateRequest.getTemplateAction() == CustomConstants.MSG_PUSH_TEMP_ACT_1 || templateRequest.getTemplateAction() == CustomConstants.MSG_PUSH_TEMP_ACT_3) {
            templateVO.setTemplateActionUrl(templateRequest.getTemplateActionUrl1());
        }
        if (templateRequest.getTemplateAction() == CustomConstants.MSG_PUSH_TEMP_ACT_2) {
            if(templateRequest.getTemplateActionUrl2().equals("1")){
                templateRequest.setTemplateActionUrl2("hyjf://jumpZXH" );
            }else if (templateRequest.getTemplateActionUrl2().equals("5")) {
                templateRequest.setTemplateActionUrl2("hyjf://jumpInvest");
            }else if (templateRequest.getTemplateActionUrl2().equals("新手汇")) {
                templateRequest.setTemplateActionUrl2("hyjf://jumpXSH");
            }else if (templateRequest.getTemplateActionUrl2().equals("2")) {
                templateRequest.setTemplateActionUrl2("hyjf://jumpMine");
            }else if (templateRequest.getTemplateActionUrl2().equals("3")) {
                templateRequest.setTemplateActionUrl2("hyjf://jumpCouponsList");
            }else if (templateRequest.getTemplateActionUrl2().equals("4")) {
                templateRequest.setTemplateActionUrl2("hyjf://jumpTransactionDetail");
            }
            templateVO.setTemplateActionUrl(templateRequest.getTemplateActionUrl2());
        }
        templateVO.setTagCode(templateRequest.getTemplateCode().substring(0, 4));
        templateVO.setTemplateCode(templateRequest.getTemplateCode());
        templateVO.setCreateUserId(Integer.parseInt(userId));
        templateVO.setLastupdateUserId(Integer.parseInt(userId));
        response = messagePushTemplateService.insertAction(templateVO);
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "修改模板", notes = "修改模板")
    @RequestMapping(value = "/updateAction", method = RequestMethod.POST)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateAction(HttpServletRequest request, @RequestBody MsgPushTemplateRequest templateRequest) {
        MessagePushTemplateResponse response = new MessagePushTemplateResponse();
        AdminSystemVO user = getUser(request);
        String username = user.getUsername();
        // 调用校验
        String message = validatorFieldCheck(templateRequest);
        if (message != null) {
            // 标签类型
            List<MessagePushTagVO> templatePushTags = this.messagePushTagService.getTagList();
            response.setTemplatePushTags(templatePushTags);
            response.setMessage(message);
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
            if(templateRequest.getTemplateActionUrl2().equals("1")){
                templateRequest.setTemplateActionUrl("hyjf://jumpZXH" );
            }else if (templateRequest.getTemplateActionUrl2().equals("5")) {
                templateRequest.setTemplateActionUrl("hyjf://jumpInvest");
            }else if (templateRequest.getTemplateActionUrl2().equals("新手汇")) {
                templateRequest.setTemplateActionUrl("hyjf://jumpXSH");
            }else if (templateRequest.getTemplateActionUrl2().equals("2")) {
                templateRequest.setTemplateActionUrl("hyjf://jumpMine");
            }else if (templateRequest.getTemplateActionUrl2().equals("3")) {
                templateRequest.setTemplateActionUrl("hyjf://jumpCouponsList");
            }else if (templateRequest.getTemplateActionUrl2().equals("4")) {
                templateRequest.setTemplateActionUrl("hyjf://jumpTransactionDetail");
            }
        }
        templateRequest.setTagCode(templateRequest.getTagCode());
        templateRequest.setTemplateCode(templateRequest.getTemplateCode());
        templateRequest.setCreateUserName(username);
        response = this.messagePushTemplateService.updateRecord(templateRequest);
        RedisUtils.del(RedisConstants.MESSAGE_PUSH_TEMPLATE);
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "删除模板", notes = "删除模板")
    @RequestMapping(value = "/deleteAction/{ids}", method = RequestMethod.GET)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteAction(@PathVariable String ids) {
        if (ids == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        List<Integer> recordList = JSONArray.parseArray(ids, Integer.class);
        MessagePushTemplateResponse response = messagePushTemplateService.deleteAction(recordList);
        if (response.getCount() > 0) {
            RedisUtils.del(RedisConstants.MESSAGE_PUSH_TEMPLATE);
            return new AdminResult<>(response);
        }
        return new AdminResult<>(FAIL, FAIL_DESC);

    }


    @ApiOperation(value = "修改状态", notes = "修改状态")
    @RequestMapping(value = "/statusAction/{id}", method = RequestMethod.GET)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateStatus(@PathVariable Integer id) {
        if (id != null) {
            MessagePushTemplateResponse response = messagePushTemplateService.getRecord(id);
            MessagePushTemplateVO record = response.getResult();
            // 新建状态只能启用，启用只能禁用，禁用只能启用
            if (record.getStatus() == CustomConstants.MSG_PUSH_STATUS_0) {
                record.setStatus(CustomConstants.MSG_PUSH_STATUS_1);
            } else if (record.getStatus() == CustomConstants.MSG_PUSH_STATUS_1) {
                record.setStatus(CustomConstants.MSG_PUSH_STATUS_2);
            } else if (record.getStatus() == CustomConstants.MSG_PUSH_STATUS_2) {
                record.setStatus(CustomConstants.MSG_PUSH_STATUS_1);
            }
            MsgPushTemplateRequest request = new MsgPushTemplateRequest();
            BeanUtils.copyProperties(record, request);
            MessagePushTemplateResponse tagResponse = messagePushTemplateService.updateRecord(request);
            if (tagResponse == null) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            if (!Response.isSuccess(tagResponse)) {
                return new AdminResult<>(FAIL, tagResponse.getMessage());
            }
            RedisUtils.del(RedisConstants.MESSAGE_PUSH_TEMPLATE);
            return new AdminResult<>(tagResponse);
        }
        return new AdminResult<>(FAIL, FAIL_DESC);
    }

    @ApiOperation(value = "检查名称唯一性", notes = "检查名称唯一")
    @RequestMapping(value = "/checkAction", method = RequestMethod.POST)
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD, ShiroConstants.PERMISSION_MODIFY})
    public AdminResult checkAction(@RequestBody MsgPushTemplateRequest request) {
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
            String message = "标签重复";
            response.setMessage(message);
            return new AdminResult(FAIL, response.getMessage());
        }
        return new AdminResult<>(response);
    }


    @ApiOperation(value = "检查是否是url", notes = "检查是否是url")
    @RequestMapping(value = "/checkUrlAction", method = RequestMethod.POST)
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD, ShiroConstants.PERMISSION_MODIFY})
    public AdminResult checkUrlAction(@RequestBody MsgPushTemplateRequest templateRequest, HttpServletRequest request) {
        MessagePushTemplateResponse response = new MessagePushTemplateResponse();
        // 检查着名称唯一性
        Boolean isUrl = false;
        if (StringUtils.isNotBlank(templateRequest.getTemplateActionUrl())) {
            isUrl = Validator.isUrl2(templateRequest.getTemplateActionUrl());
        }
        if (!isUrl) {
            response.setRtn(Response.FAIL);
            response.setMessage("请输入正确的http地址（全路径）!");
        }
        return new AdminResult<>(response);
    }

    @Autowired
    private FileUpLoadUtil fileUpLoadUtil;

    @ApiOperation(value = "资料上传", notes = "资料上传")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD, ShiroConstants.PERMISSION_MODIFY})
    public AdminResult<LinkedList<BorrowCommonImage>> uploadFile(HttpServletRequest request) throws Exception {
        AdminResult<LinkedList<BorrowCommonImage>> adminResult = new AdminResult<>();
        try {
            LinkedList<BorrowCommonImage> borrowCommonImages = fileUpLoadUtil.upLoad(request);
            adminResult.setData(borrowCommonImages);
            adminResult.setStatus(SUCCESS);
            adminResult.setStatusDesc(SUCCESS_DESC);
            return adminResult;
        } catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }


    /**
     * 画面校验
     *
     * @param request
     * @return
     */
    private String validatorFieldCheck(MsgPushTemplateRequest request) {
        String message = null;
        if (request.getTagId() == null) {
            message = "标签id不能为空";
        }
        if (StringUtils.isBlank(request.getTemplateCode().substring(4)) || request.getTemplateCode().length() > 40) {
            message = "模板编码不能为空或长度大于40字符";
        }
        if (StringUtils.isBlank(request.getTemplateTitle()) || request.getTemplateTitle().length() > 20) {
            message = "模板名称不能为空或长度大于20字符";
        }
        if (StringUtils.isNotBlank(request.getTemplateImageUrl())  && request.getTemplateImageUrl().length() > 100) {
            message = "图片url长度不能大于100字符";
        }
        if (StringUtils.isBlank(request.getTemplateContent())|| request.getTemplateContent().length() > 4000) {
            message = "模板内容不能为空或内容长度不能大于4000字符";
        }
        if (StringUtils.isBlank(request.getTemplateTerminal()) || request.getTemplateTerminal().length() > 20) {
            message = "推送终端不能为空或长度不能大于20字符";
        }
        if (request.getTemplateAction() == null) {
            message = "后续动作不能为空";
        }
        if (request.getStatus() == null) {
            message = "状态不能为空";
        }
        if (request.getTemplateAction() == CustomConstants.MSG_PUSH_TEMP_ACT_1) {
            if (StringUtils.isBlank(request.getTemplateActionUrl1()) || request.getTemplateActionUrl1().length() > 100) {
                message = "后续动作url1不能为空或长度不能大于100字符";
            }
        }
        if (request.getTemplateAction() == CustomConstants.MSG_PUSH_TEMP_ACT_3) {
            if (StringUtils.isBlank(request.getTemplateActionUrl3()) || request.getTemplateActionUrl3().length() > 100) {
                message = "后续动作url3不能为空或长度不能大于100字符";
            }
        }
        if (request.getTemplateAction() == CustomConstants.MSG_PUSH_TEMP_ACT_2) {
            if (StringUtils.isBlank(request.getTemplateActionUrl2()) || request.getTemplateActionUrl2().length() > 100) {
                message = "后续动作url2不能为空或长度不能大于100字符";
            }
        }

        return message;
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
