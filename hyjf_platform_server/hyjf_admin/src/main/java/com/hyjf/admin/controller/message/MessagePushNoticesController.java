package com.hyjf.admin.controller.message;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.MessagePushHistoryService;
import com.hyjf.admin.service.MessagePushNoticesService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.MessagePushNoticesResponse;
import com.hyjf.am.response.admin.MessagePushTagResponse;
import com.hyjf.am.resquest.admin.MessagePushNoticesRequest;
import com.hyjf.am.vo.admin.MessagePushMsgVO;
import com.hyjf.am.vo.admin.MessagePushTagVO;
import com.hyjf.am.vo.admin.coupon.ParamName;
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
import org.springframework.web.multipart.MultipartFile;

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
    @Value("${file.domain.url}")
    private String url;

    @ApiOperation(value = "通知发送列表查询", notes = "通知发送列表查询")
    @PostMapping(value = "/init")
    @ResponseBody
    public JSONObject init(@RequestBody MessagePushNoticesRequest messagePushNoticesRequest) {
        JSONObject jsonObject = new JSONObject();
        try {
            MessagePushNoticesResponse prs = messagePushNoticesService.getRecordList(messagePushNoticesRequest);
            MessagePushTagResponse allPushTagList = messagePushHistoryService.getAllPushTagList();
            if (prs == null) {
                jsonObject.put(FAIL, FAIL_DESC);
                return jsonObject;
            }
            if (!Response.isSuccess(prs)) {
                jsonObject.put(FAIL, prs.getMessage());
                return jsonObject;
            }
            prepareDatas(jsonObject);
            MessagePushTagResponse tagList = messagePushNoticesService.getTagList();
            List<MessagePushTagVO> resultList = tagList.getResultList();
            jsonObject.put("noticesPushTags", resultList);
            jsonObject.put("totalCount", prs.getRecordTotal());
            jsonObject.put("list", prs.getResultList());
            jsonObject.put("allPushTagList", allPushTagList.getResultList());
            jsonObject.put("fileDomainUrl", url);
            return jsonObject;
        } catch (Exception e) {
            jsonObject.put(FAIL, FAIL_DESC);
            return jsonObject;
        }

    }


    @ApiOperation(value = "发送列表添加", notes = "发送列表添加")
    @PostMapping(value = "/add")
    @ResponseBody
    public AdminResult<MessagePushMsgVO> add(@RequestBody MessagePushNoticesRequest form) {
        try {
            MessagePushNoticesResponse messagePushNoticesResponse = messagePushNoticesService.insertRecord(form);
            if (Response.isSuccess(messagePushNoticesResponse)) {
                return new AdminResult<>(SUCCESS, SUCCESS_DESC);
            } else {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
        } catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }

    @ApiOperation(value = "发送列表删除", notes = "发送列表删除")
    @PostMapping(value = "/delete")
    @ResponseBody
    public AdminResult<MessagePushMsgVO> delete(@RequestBody MessagePushNoticesRequest form) {
        try {
            MessagePushNoticesResponse messagePushNoticesResponse = messagePushNoticesService.deleteRecord(form);
            if (Response.isSuccess(messagePushNoticesResponse)) {
                return new AdminResult<>(SUCCESS, SUCCESS_DESC);
            } else {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
        } catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }

    @ApiOperation(value = "发送列表修改", notes = "发送列表修改")
    @PostMapping(value = "/update")
    @ResponseBody
    public AdminResult<MessagePushMsgVO> update(@RequestBody MessagePushNoticesRequest form) {
        try {
            MessagePushNoticesResponse messagePushNoticesResponse = messagePushNoticesService.updateRecord(form);
            if (Response.isSuccess(messagePushNoticesResponse)) {
                return new AdminResult<>(SUCCESS, SUCCESS_DESC);
            } else {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
        } catch (Exception e) {
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
    @ResponseBody
    public JSONObject info(@RequestBody MessagePushNoticesRequest form) {
        JSONObject jsonObject = new JSONObject();

        try {
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
            MessagePushTagResponse tagList = messagePushNoticesService.getTagList();
            List<MessagePushTagVO> resultList = tagList.getResultList();
            jsonObject.put("noticesPushTags", resultList);
            prepareDatas(jsonObject);
            return jsonObject;
        } catch (Exception e) {
            jsonObject.put(FAIL, FAIL_DESC);
            return jsonObject;

        }
    }



    /**
     * 检查是否是电话号码
     *
     * @param request
     * @return
     */
    /*public String checkMobilesAction(ModelAndView modelAndView, HttpServletRequest request,@RequestParam("mobile") String mobile) {
        String mobiles = request.getParameter("param");
        JSONObject ret = new JSONObject();
        // 检查是否是电话号码
        if (!com.hyjf.admin.utils.ValidatorFieldCheckUtil.validateRequired(modelAndView, "msgDestination", mobiles)) {
            ret.put("info", "电话号码不能为空.");
            return ret.toString();
        }
        String[] mobileStrs = mobiles.split(",");
        for (int i = 0; i < mobileStrs.length; i++) {
            if (!com.hyjf.admin.utils.ValidatorFieldCheckUtil.validateMobile(modelAndView, "msgDestination", mobileStrs[i], true)) {
                ret.put("info", mobileStrs[i] + "不是正确的电话号码");
                return ret.toString();
            }
        }
        // 没有错误时,返回y
        if (!ret.containsKey("info")) {
            ret.put("status", "y");
        }
        return ret.toString();
    }*/

    /**
     * 资料上传
     *
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "资料上传", notes = "资料上传")
    @PostMapping(value = "/uploadFile")
    @ResponseBody
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
