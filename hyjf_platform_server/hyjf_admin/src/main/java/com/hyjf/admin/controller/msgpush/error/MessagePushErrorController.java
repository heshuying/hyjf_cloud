/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.msgpush.error;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.MessagePushErrorRequestBean;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.MessagePushErrorService;
import com.hyjf.am.resquest.config.MessagePushErrorRequest;
import com.hyjf.am.vo.admin.MessagePushMsgHistoryVO;
import com.hyjf.am.vo.admin.MessagePushTagVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dangzw
 * @version MessagePushErrorController, v0.1 2018/8/14 21:37
 */
@Api(description = "消息中心-APP消息推送 异常处理", tags = "消息中心-APP消息推送 异常处理")
@RestController
@RequestMapping("/hyjf-admin/msgpush/error")
public class MessagePushErrorController extends BaseController {

    @Value("file.domain.url")
    private String FILE_DOMAIN_URL;

    @Autowired
    private MessagePushErrorService messagePushErrorService;

    @PostMapping("/getListByConditions")
    @ApiParam(required = false, name = "requestBean", value = "查询条件")
    @ApiOperation(value = "(条件)查询 APP消息推送 异常处理 列表", httpMethod = "POST",
                                  notes = "(条件)查询 APP消息推送 异常处理 列表")
    public JSONObject getListByConditions(@RequestBody MessagePushErrorRequestBean requestBean) {
        MessagePushErrorRequest request = new MessagePushErrorRequest();
        BeanUtils.copyProperties(requestBean, request);
        JSONObject jsonObject = new JSONObject();
        Integer count = this.messagePushErrorService.getRecordCount(request);
        if(count > 0){
            Paginator paginator = new Paginator(request.getPaginatorPage(), count);
            request.setPaginator(paginator);
            List<MessagePushMsgHistoryVO> recordList = this.messagePushErrorService.getRecordList(request, paginator.getOffset(), paginator.getLimit());
            request.setRecordList(recordList);
            String fileDomainUrl = UploadFileUtils.getDoPath(FILE_DOMAIN_URL);
            jsonObject.put("fileDomainUrl", fileDomainUrl);
        }
        // 标签
        List<MessagePushTagVO> tagList = this.messagePushErrorService.getTagList();
        jsonObject.put("tagList", tagList);
        // 发送状态
        List<ParamNameVO> messagesSendStatus = this.messagePushErrorService.getParamNameList("MSG_PUSH_SEND_STATUS");
        jsonObject.put("messagesSendStatus", messagesSendStatus);
        jsonObject.put("msgErrorForm", request);
        //返回状态
        jsonObject.put("count", count);
        jsonObject.put("status", "000");
        jsonObject.put("statusDesc", "成功");
        return jsonObject;
    }

    @PutMapping("/update")
    @ApiOperation(value = "数据修改 APP消息推送 异常处理", notes = "数据修改 APP消息推送 异常处理")
    public JSONObject update(@RequestBody Integer id) {
        // 重发此消息
        if (id != null) {
            MessagePushMsgHistoryVO msg = this.messagePushErrorService.getRecord(id);
            this.messagePushErrorService.sendMessage(msg);
        }
        JSONObject jsonObject = new JSONObject();
        //返回状态
        jsonObject.put("status", "000");
        jsonObject.put("statusDesc", "成功");
        jsonObject.put("RE_LIST_PATH", "redirect:/msgpush/error/init");
        return jsonObject;
    }
}
