/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.msgpush;


import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.MessagePushMsgService;
import com.hyjf.admin.service.MessagePushTagService;
import com.hyjf.admin.service.MessagePushTemplateService;
import com.hyjf.admin.utils.FileUpLoadUtil;
import com.hyjf.admin.utils.GetMessageIdUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.MessagePushMsgResponse;
import com.hyjf.am.resquest.message.MessagePushMsgRequest;
import com.hyjf.am.vo.admin.MessagePushMsgVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
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
 * @version MessagePushMessageController, v0.1 2018/8/15 13:54
 */
@Api(tags = "消息中心-app消息推送-手动发放消息")
@RestController
@RequestMapping("/hyjf-admin/msgPush/message")
public class MessagePushMessageController extends BaseController {

    @Autowired
    private MessagePushMsgService messagePushMsgService;
    @Autowired
    private MessagePushTagService messagePushTagService;
    @Autowired
    private MessagePushTemplateService messagePushTemplateService;

    /** 权限关键字 */
    public static final String PERMISSIONS = "msgpushmessages";

    @ApiOperation(value = "页面初始化", notes = "页面初始化")
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
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
    @RequestMapping(value = "/infoAction", method = RequestMethod.POST)
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD, ShiroConstants.PERMISSION_MODIFY, ShiroConstants.PERMISSION_RESEND})
    public AdminResult<MessagePushMsgResponse> infoAction(@RequestBody MessagePushMsgRequest form) {
        MessagePushMsgResponse response = new MessagePushMsgResponse();
        try {
            response = messagePushMsgService.getRecord(form.getId());
            if (response.getResult() != null) {
                MessagePushMsgVO record = response.getResult();
                if (record.getMsgAction().intValue() == CustomConstants.MSG_PUSH_TEMP_ACT_2) {
                    form.setMsgActionUrl1("");
                    if ("hyjf://jumpZXH".equals(record.getMsgActionUrl())) {
                        record.setMsgActionUrl("1");
                    }
                    if ("hyjf://jumpMine".equals(record.getMsgActionUrl())) {
                        record.setMsgActionUrl("2");
                    }
                    if ("hyjf://jumpCouponsList".equals(record.getMsgActionUrl())) {
                        record.setMsgActionUrl("3");
                    }
                    if ("hyjf://jumpTransactionDetail".equals(record.getMsgActionUrl())) {
                        record.setMsgActionUrl("4");
                    }
                    if ("hyjf://jumpInvest".equals(record.getMsgActionUrl())) {
                        record.setMsgActionUrl("5");
                    }
                }
                if (record.getMsgCode() != null) {
                    record.setTagCode(record.getMsgCode().substring(0,4));
                }
                // 如果是转发,则form的id应置为空
                if (StringUtils.isNotEmpty(form.getUpdateOrReSend()) && form.getUpdateOrReSend().equals("2")) {
                    record.setId(null);
                }
            }
        } catch (Exception e) {
            logger.error("获取手动发送消息详情失败, {}", e);
        }
        List<MessagePushTagVO> templatePushTags = this.messagePushTagService.getTagList();
        response.setTemplatePushTags(templatePushTags);
        prepareDatas(response);
        return new AdminResult<>(response);
    }


    @ApiOperation(value = "添加手动发送短信", notes = "添加手动发送短信")
    @RequestMapping(value = "/insertAction", method = RequestMethod.POST)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult<MessagePushMsgResponse> insertAction(HttpServletRequest request, @RequestBody MessagePushMsgRequest templateRequest) {
        MessagePushMsgResponse response = new MessagePushMsgResponse();
        AdminSystemVO user = getUser(request);
        String username = user.getUsername();

        templateRequest.setCreateUserName(username);
        templateRequest.setLastupdateUserName(username);
        templateRequest.setLastupdateUserId(Integer.parseInt(user.getId()));
        templateRequest.setCreateUserId(Integer.parseInt(user.getId()));
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

        MessagePushTagVO messagePushTagVO = messagePushTagService.getPushTagByTagId(templateRequest.getTagId());
        templateRequest.setTagCode(messagePushTagVO.getTagCode());
        MessagePushMsgVO templateVO = new MessagePushMsgVO();
        BeanUtils.copyProperties(templateRequest, templateVO);
        templateVO.setMsgTerminal(templateRequest.getMsgTerminal());
        if (templateRequest.getMsgAction() == CustomConstants.MSG_PUSH_TEMP_ACT_0) {
            templateVO.setMsgActionUrl("");
        }
        if (templateRequest.getMsgAction() == CustomConstants.MSG_PUSH_TEMP_ACT_1) {
            templateVO.setMsgActionUrl(templateRequest.getMsgActionUrl1());
        }
        if (templateRequest.getMsgAction() == CustomConstants.MSG_PUSH_TEMP_ACT_3) {
            templateVO.setMsgActionUrl(templateRequest.getMsgActionUrl3());
        }
        if (templateRequest.getMsgAction() == CustomConstants.MSG_PUSH_TEMP_ACT_2) {
            if (templateRequest.getMsgActionUrl2().equals("1")) {
                templateRequest.setMsgActionUrl2("hyjf://jumpZXH");
            } else if (templateRequest.getMsgActionUrl2().equals("5")) {
                templateRequest.setMsgActionUrl2("hyjf://jumpInvest");
            } else if (templateRequest.getMsgActionUrl2().equals("新手汇")) {
                templateRequest.setMsgActionUrl2("hyjf://jumpXSH");
            } else if (templateRequest.getMsgActionUrl2().equals("2")) {
                templateRequest.setMsgActionUrl2("hyjf://jumpMine");
            } else if (templateRequest.getMsgActionUrl2().equals("3")) {
                templateRequest.setMsgActionUrl2("hyjf://jumpCouponsList");
            } else if (templateRequest.getMsgActionUrl2().equals("4")) {
                templateRequest.setMsgActionUrl2("hyjf://jumpTransactionDetail");
            } else if (templateRequest.getMsgActionUrl2().equals("债券转让-已承接")) {
                templateRequest.setMsgActionUrl2("hyjf://jumpTransfer");
            } else if (templateRequest.getMsgActionUrl2().equals("债权转让-转让记录")) {
                templateRequest.setMsgActionUrl2("hyjf://jumpTransferRecord");
            }
            templateVO.setMsgActionUrl(templateRequest.getMsgActionUrl2());
        }
        if (templateRequest.getMsgSendType() == CustomConstants.MSG_PUSH_SEND_TYPE_1) {
            templateVO.setSendTime(GetDate.getNowTime10());
            if (StringUtils.isNotEmpty(templateRequest.getMessagesPreSendTimeStr())) {
                try {
                    Integer time = GetDate.strYYYYMMDDHHMMSS2Timestamp2(templateRequest.getMessagesPreSendTimeStr());
                    if (time != 0) {
                        templateVO.setPreSendTime(time);
                        templateVO.setSendTime(time);
                    }
                } catch (Exception e) {
                }
            }
        } else {
            templateVO.setPreSendTime(null);
            templateVO.setSendTime(GetDate.getNowTime10());
        }
        String msgCode = GetMessageIdUtil.getNewMsgCode(templateVO.getTagCode());
        templateVO.setMsgCode(msgCode);// 设置ID
        templateVO.setMsgSendStatus(CustomConstants.MSG_PUSH_MSG_STATUS_0);// 设置默认状态
        templateVO.setMsgDestinationType(CustomConstants.MSG_PUSH_DESTINATION_TYPE_1);
        response = this.messagePushMsgService.insertRecord(templateVO);
        return new AdminResult<>(response);
    }


    @ApiOperation(value = "修改手动发放消息", notes = "修改手动发放消息")
    @RequestMapping(value = "/updateAction", method = RequestMethod.POST)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateAction(HttpServletRequest request, @RequestBody MessagePushMsgRequest templateRequest) {
        MessagePushMsgResponse response = new MessagePushMsgResponse();
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
            if (templateRequest.getMsgActionUrl2().equals("1")) {
                templateRequest.setMsgActionUrl("hyjf://jumpZXH");
            } else if (templateRequest.getMsgActionUrl2().equals("5")) {
                templateRequest.setMsgActionUrl("hyjf://jumpInvest");
            } else if (templateRequest.getMsgActionUrl2().equals("2")) {
                templateRequest.setMsgActionUrl("hyjf://jumpMine");
            } else if (templateRequest.getMsgActionUrl2().equals("3")) {
                templateRequest.setMsgActionUrl("hyjf://jumpCouponsList");
            } else if (templateRequest.getMsgActionUrl2().equals("4")) {
                templateRequest.setMsgActionUrl("hyjf://jumpTransactionDetail");
            }
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
            templateRequest.setSendTime(GetDate.getMyTimeInMillis());
        }
        templateRequest.setMsgDestinationType(CustomConstants.MSG_PUSH_DESTINATION_TYPE_1);
        response = messagePushMsgService.updateMessagePushMsg(templateRequest);
        return new AdminResult<>(response);
    }


    @ApiOperation(value = "删除手动发送消息", notes = "删除手动发送消息")
    @RequestMapping(value = "/deleteAction/{ids}", method = RequestMethod.GET)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteAction(@PathVariable String ids) {
        if (StringUtils.isBlank(ids)) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        String msgIds[] = ids.split(",");
        List<MessagePushMsgVO> recordList = new ArrayList<>();
        for (String id : msgIds) {
            MessagePushMsgResponse msgResponse = messagePushMsgService.getRecord(id);
            MessagePushMsgVO msgVO = msgResponse.getResult();
            recordList.add(msgVO);
        }
        MessagePushMsgRequest request = new MessagePushMsgRequest();
        request.setRecordList(recordList);
        MessagePushMsgResponse response = messagePushMsgService.deleteAction(request);
        if (response.getCount() > 0) {
            return new AdminResult<>(response);
        }
        return new AdminResult<>(FAIL, FAIL_DESC);
    }


    @ApiOperation(value = "检查是否是url", notes = "检查是否是url")
    @RequestMapping(value = "/checkUrlAction", method = RequestMethod.POST)
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD, ShiroConstants.PERMISSION_MODIFY})
    public AdminResult checkUrlAction(@RequestBody MessagePushMsgRequest templateRequest, HttpServletRequest request) {
        MessagePushMsgResponse response = new MessagePushMsgResponse();
        // 检查着名称唯一性
        Boolean isUrl = false;
        if (StringUtils.isNotBlank(templateRequest.getMsgActionUrl3()) || StringUtils.isNotBlank(templateRequest.getMsgActionUrl1())) {
            isUrl = Validator.isUrl2(templateRequest.getMsgActionUrl3() == null ? templateRequest.getMsgActionUrl1() : templateRequest.getMsgActionUrl3());
        }
        if (!isUrl) {
            response.setRtn(Response.FAIL);
            response.setMessage("请输入正确的http地址（全路径）!");
        }
        // 没有错误时,返回y
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "检查是否是电话号码", notes = "检查是否是电话号码")
    @RequestMapping(value = "/checkMobile", method = RequestMethod.POST)
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD, ShiroConstants.PERMISSION_MODIFY})
    public AdminResult checkMobile(@RequestParam String msgDestination) {
        MessagePushMsgResponse response = new MessagePushMsgResponse();
        // 检查是否是电话号码
        String[] mobileStrs = msgDestination.split(",");
        for (int i = 0; i < mobileStrs.length; i++) {
            String message = validatorFieldCheckMobile(mobileStrs[i]);
            if (message != null) {
                return new AdminResult(FAIL,mobileStrs[i] + "不是正确的电话号码");
            }
        }
        return new AdminResult(response);
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
     * 准备各种枚举
     *
     * @param response
     */
    private MessagePushMsgResponse prepareDatas(MessagePushMsgResponse response) {
        String nameClass = "MSG_PUSH_MSG_STATUS";
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
        naturePages.remove(0);
        response.setNaturePages(naturePages);
        return response;
    }

    /**
     * 画面校验
     *
     * @param request
     * @return
     */
    private String validatorFieldCheck(MessagePushMsgRequest request) {
        String message = null;
        if (request.getTagId() == null) {
            message = "标签id不能为空";
        }
        if (request.getMsgTitle() == null || request.getMsgTitle().length() > 20) {
            message = "消息名称不能为空或长度大于20字符";
        }
        if (request.getMsgImageUrl() != null && request.getMsgImageUrl().length() > 100) {
            message = "图片url长度不能大于100字符";
        }
        if (request.getMsgContent() == null || request.getMsgContent().length() > 4000) {
            message = "消息内容不能为空或内容长度不能大于4000字符";
        }
        if (request.getMsgTerminal() == null || request.getMsgTerminal().length() > 20) {
            message = "消息终端不能为空或长度不能大于20字符";
        }
        if (request.getMsgAction() == null) {
            message = "后续动作不能为空";
        }
        if (request.getMsgAction() == CustomConstants.MSG_PUSH_TEMP_ACT_1) {
            if (request.getMsgActionUrl1() == null || request.getMsgActionUrl1().length() > 100) {
                message = "后续动作url1不能为空或长度不能大于100字符";
            }
        }
        if (request.getMsgAction() == CustomConstants.MSG_PUSH_TEMP_ACT_3) {
            if (request.getMsgActionUrl3() == null || request.getMsgActionUrl3().length() > 100) {
                message = "后续动作url3不能为空或长度不能大于100字符";
            }
        }
        if (request.getMsgAction() == CustomConstants.MSG_PUSH_TEMP_ACT_2) {
            if (request.getMsgActionUrl2() == null || request.getMsgActionUrl2().length() > 100) {
                message = "后续动作url2不能为空或长度不能大于100字符";
            }
        }
        if (request.getMsgSendType() == null) {
            message = "消息发送类型不能为空";
        }
        if (request.getMsgSendType() == CustomConstants.MSG_PUSH_SEND_TYPE_1) {
            if (request.getMessagesPreSendTimeStr() == null || request.getMessagesPreSendTimeStr().length() > 100) {
                message = "预发送时间不能为空或长度不能大于100";
            }
        }
        if (request.getMsgDestination() == null) {
            message = "用户手机号不能为空";
        } else {
            String[] mobiles = request.getMsgDestination().split(",");
            for (int i = 0; i < mobiles.length; i++) {
                if (mobiles[i].length() != 11) {
                    message = "手机号码校验错误";
                } else {
                    String reg = "^[1][0-9]{10}$";
                    if (!mobiles[i].matches(reg)) {
                        message = "手机号错误";
                    }
                }
            }
        }
        return message;
    }

    /**
     * 校验手机号码
     *
     * @param mobile
     * @return
     */
    private String validatorFieldCheckMobile(String mobile) {
        String message = null;
        if (mobile.length() != 11) {
            message = "手机号码校验错误";
        } else {
            String reg = "^[1][0-9]{10}$";
            if (!mobile.matches(reg)) {
                message = "手机号错误";
            }
        }
        return message;
    }
}
