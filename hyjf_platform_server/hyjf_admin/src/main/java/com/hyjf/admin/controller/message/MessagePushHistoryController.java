package com.hyjf.admin.controller.message;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.MessagePushHistoryService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.MessagePushHistoryResponse;
import com.hyjf.am.resquest.admin.MessagePushHistoryRequest;
import com.hyjf.am.vo.admin.coupon.ParamName;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lisheng
 * @version MessagePushHistoryController, v0.1 2018/8/14 19:58
 */
@Api(value = "消息中心-app消息推送-发送历史", tags = "消息中心-app消息推送-发送历史")
@RestController
@RequestMapping("/hyjf-admin/msgpush/history")
public class MessagePushHistoryController extends BaseController {
    @Autowired
    MessagePushHistoryService messagePushHistoryService;
    @Value("${file.domain.url}")
    private String url;

    @ApiOperation(value = "发送历史列表查询", notes = "发送历史列表查询")
    @PostMapping(value = "/init")
    @ResponseBody
    public JSONObject init(@RequestBody  MessagePushHistoryRequest form) {
            JSONObject jsonObject = new JSONObject();
            MessagePushHistoryResponse prs = messagePushHistoryService.getRecordList(form);
            List<MessagePushTagVO> allPushTagList = messagePushHistoryService.getAllPushTagList();
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
            jsonObject.put("allPushTagList",allPushTagList);
            jsonObject.put("fileDomainUrl", url);
            return jsonObject;
    }




    /**
     * 准备各种枚举
     **/
    private void prepareDatas(JSONObject jsonObject) {
        {
            // ======================拼接枚举======================
            // 发送状态
            //List<ParamName> historySendStatus = this.messagePushHistoryService.getParamNameList("MSG_PUSH_SEND_STATUS");
            Map<String, String> historySendStatus = CacheUtil.getParamNameMap("MSG_PUSH_SEND_STATUS");

            jsonObject.put("historySendStatus", historySendStatus);
            // 后续动作
            //List<ParamName> historyActions = this.messagePushHistoryService.getParamNameList("MSG_PUSH_TEMP_ACT");
            Map<String, String> historyActions = CacheUtil.getParamNameMap("MSG_PUSH_TEMP_ACT");
            jsonObject.put("historyActions", historyActions);
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
            //List<ParamName> naturePages = this.messagePushHistoryService.getParamNameList("MSG_PUSH_NATUREURLS");
            Map<String, String> naturePages = CacheUtil.getParamNameMap("MSG_PUSH_NATUREURLS");
            jsonObject.put("naturePages", naturePages);
        }
    }
}
