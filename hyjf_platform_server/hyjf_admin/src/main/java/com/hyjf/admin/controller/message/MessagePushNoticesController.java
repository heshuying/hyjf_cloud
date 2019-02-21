package com.hyjf.admin.controller.message;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.MessagePushHistoryService;
import com.hyjf.admin.service.MessagePushNoticesService;
import com.hyjf.admin.service.MessagePushTagService;
import com.hyjf.admin.utils.FileUpLoadUtil;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.MessagePushNoticesResponse;
import com.hyjf.am.resquest.admin.MessagePushNoticesRequest;
import com.hyjf.am.vo.admin.MessagePushMsgVO;
import com.hyjf.am.vo.admin.coupon.ParamName;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 通知发送
 *
 * @author lisheng
 * @version MessagePushNoticesController, v0.1 2018/8/14 14:34
 */
@Api(value = "消息中心-app消息推送-通知发送", tags = "消息中心-app消息推送-通知发送")
@RestController
@RequestMapping("/hyjf-admin/msgpush/notices")
public class MessagePushNoticesController extends BaseController {

    @Autowired
    MessagePushNoticesService messagePushNoticesService;
    @Autowired
    MessagePushHistoryService messagePushHistoryService;
    @Autowired
    private MessagePushTagService messagePushTagService;

    @Value("${file.domain.url}")
    private String url;
    private static final String PERMISSIONS = "msgpushnotices";

    @ApiOperation(value = "通知发送列表查询", notes = "通知发送列表查询")
    @PostMapping(value = "/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public JSONObject init(@RequestBody MessagePushNoticesRequest messagePushNoticesRequest) {
        JSONObject jsonObject = new JSONObject();
            MessagePushNoticesResponse prs = messagePushNoticesService.getRecordList(messagePushNoticesRequest);
            List<MessagePushTagVO> allPushTagList = this.messagePushTagService.getTagList();
            if (prs == null) {
                jsonObject.put(FAIL, FAIL_DESC);
                return jsonObject;
            }
            if (!Response.isSuccess(prs)) {
                jsonObject.put(FAIL, prs.getMessage());
                return jsonObject;
            }
            prepareDatas(jsonObject);
            jsonObject.put("totalCount", prs.getRecordTotal());
            jsonObject.put("list", prs.getResultList());
            jsonObject.put("allPushTagList", allPushTagList);
            jsonObject.put("fileDomainUrl", url);
            return jsonObject;


    }


    @ApiOperation(value = "发送列表添加", notes = "发送列表添加")
    @PostMapping(value = "/add")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult<MessagePushMsgVO> add(@RequestBody MessagePushNoticesRequest form, HttpServletRequest request) {
            AdminSystemVO user = getUser(request);
            if (user != null) {
                String username = user.getUsername();
                form.setUserName(username);
            }
            MessagePushNoticesResponse messagePushNoticesResponse = messagePushNoticesService.insertRecord(form);
            if (Response.isSuccess(messagePushNoticesResponse)) {
                return new AdminResult<>(SUCCESS, SUCCESS_DESC);
            } else {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }

    }

    @ApiOperation(value = "发送列表删除", notes = "发送列表删除")
    @PostMapping(value = "/delete")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult<MessagePushMsgVO> delete(@RequestBody MessagePushNoticesRequest form) {
            MessagePushNoticesResponse messagePushNoticesResponse = messagePushNoticesService.deleteRecord(form);
            if (Response.isSuccess(messagePushNoticesResponse)) {
                return new AdminResult<>(SUCCESS, SUCCESS_DESC);
            } else {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
    }

    @ApiOperation(value = "发送列表修改", notes = "发送列表修改")
    @PostMapping(value = "/update")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult<MessagePushMsgVO> update(@RequestBody MessagePushNoticesRequest form,HttpServletRequest request) {
            AdminSystemVO user = getUser(request);
            if (user != null) {
                String username = user.getUsername();
                form.setUserName(username);
            }
            MessagePushNoticesResponse messagePushNoticesResponse = messagePushNoticesService.updateRecord(form);
            if (Response.isSuccess(messagePushNoticesResponse)) {
                return new AdminResult<>(SUCCESS, SUCCESS_DESC);
            } else {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
    }

    /**
     * 画面迁移(含有id更新，不含有id添加)
     *
     * @param form
     * @return
     */
    @ApiOperation(value = "画面迁移", notes = "画面迁移")
    @PostMapping(value = "/infoAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_RESEND,ShiroConstants.PERMISSION_ADD,ShiroConstants.PERMISSION_MODIFY})
    public JSONObject info(@RequestBody MessagePushNoticesRequest form) {
        JSONObject jsonObject = new JSONObject();
            if (StringUtils.isNotEmpty(form.getIds())) {
                    MessagePushNoticesResponse response = this.messagePushNoticesService.getRecord(form);
                MessagePushMsgVO record = response.getResult();
                BeanUtils.copyProperties(record, form);
                // String fileDomainUrl = UploadFileUtils.getDoPath(PropUtils.getSystem("file.domain.url"));
                jsonObject.put("fileDomainUrl", url);
                if (record.getMsgAction().intValue() == CustomConstants.MSG_PUSH_TEMP_ACT_0) {
                    form.setNoticesActionUrl1("");
                    form.setNoticesActionUrl2("");
                }
                if (record.getMsgAction().intValue() == CustomConstants.MSG_PUSH_TEMP_ACT_1) {
                    form.setNoticesActionUrl1(record.getMsgActionUrl());
                    form.setNoticesActionUrl2("");
                }
                if (record.getMsgAction().intValue() == CustomConstants.MSG_PUSH_TEMP_ACT_3) {
                    form.setNoticesActionUrl3(record.getMsgActionUrl());
                    form.setNoticesActionUrl2("");
                }
                if (record.getMsgAction().intValue() == CustomConstants.MSG_PUSH_TEMP_ACT_2) {
                    form.setNoticesActionUrl1("");
                    form.setNoticesActionUrl2(record.getMsgActionUrl());
                }
                // 如果是转发,则form的id应置为空
                if (StringUtils.isNotEmpty(form.getUpdateOrReSend()) && form.getUpdateOrReSend().equals("2")) {
                    form.setId(null);
                }
                if (form.getMsgSendType().intValue() == CustomConstants.MSG_PUSH_SEND_TYPE_1) {
                    if (form.getPreSendTime() != null) {
                        form.setNoticesPreSendTimeStr(GetDate.timestamptoStrYYYYMMDDHHMMSS(form.getPreSendTime()));
                    }
                }
            }
            jsonObject.put("msgPushNoticesForm", form);
            // 标签类型
            List<MessagePushTagVO> resultList = this.messagePushTagService.getTagList();
            jsonObject.put("noticesPushTags", resultList);
            prepareDatas(jsonObject);
            return jsonObject;

    }



    @Autowired
    private FileUpLoadUtil fileUpLoadUtil;

    /**
     * 资料上传
     *
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "资料上传", notes = "资料上传")
    @PostMapping(value = "/uploadFile")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_MODIFY,ShiroConstants.PERMISSION_ADD})
    public AdminResult<LinkedList<BorrowCommonImage>> uploadFile(HttpServletRequest request) throws Exception {
        AdminResult<LinkedList<BorrowCommonImage>> adminResult = new AdminResult<>();
            LinkedList<BorrowCommonImage> borrowCommonImages = fileUpLoadUtil.upLoad(request);
            adminResult.setData(borrowCommonImages);
            adminResult.setStatus(SUCCESS);
            adminResult.setStatusDesc(SUCCESS_DESC);
            return adminResult;

    }


    /**
     * 准备各种枚举
     */
    private void prepareDatas(JSONObject jsonObject) {
        {
            // ======================拼接枚举======================
            // 发送状态
            //List<ParamName> noticesSendStatus = this.messagePushNoticesService.getParamNameList("MSG_PUSH_MSG_STATUS");
            Map<String, String> noticesSendStatus = CacheUtil.getParamNameMap("MSG_PUSH_MSG_STATUS");
            jsonObject.put("noticesSendStatus", noticesSendStatus);
            // 后续动作
            //List<ParamName> noticesActions = this.messagePushNoticesService.getParamNameList("MSG_PUSH_TEMP_ACT");
            Map<String, String> noticesActions = CacheUtil.getParamNameMap("MSG_PUSH_TEMP_ACT");
            jsonObject.put("noticesActions", noticesActions);
            // 推送终端
            List<ParamName> plats = new ArrayList<ParamName>();
            ParamName paramName1 = new ParamName();
            paramName1.setNameCd(CustomConstants.CLIENT_ANDROID);
            paramName1.setName("AndroidAPP");
            ParamName paramName2 = new ParamName();
            paramName2.setNameCd(CustomConstants.CLIENT_IOS);
            paramName2.setName("IOSAPP");
            plats.add(paramName1);
            plats.add(paramName2);
            jsonObject.put("plats", plats);
            // 原生页面
            //List<ParamName> naturePages = this.messagePushNoticesService.getParamNameList("MSG_PUSH_NATUREURLS");
            Map<String, String> naturePages = CacheUtil.getParamNameMap("MSG_PUSH_NATUREURLS");
            jsonObject.put("naturePages", naturePages);
        }
    }
}
