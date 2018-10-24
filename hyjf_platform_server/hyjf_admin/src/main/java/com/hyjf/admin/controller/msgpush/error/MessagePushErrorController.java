/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.msgpush.error;

import com.hyjf.admin.beans.request.MessagePushErrorRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.MessagePushErrorService;
import com.hyjf.admin.service.MessagePushHistoryService;
import com.hyjf.am.response.admin.MessagePushErrorResponse;
import com.hyjf.am.resquest.config.MessagePushErrorRequest;
import com.hyjf.am.vo.admin.MessagePushMsgHistoryVO;
import com.hyjf.am.vo.config.MessagePushTagVO;
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
    @Autowired
    MessagePushHistoryService messagePushHistoryService;


    @PostMapping("/getListByConditions")
    @ApiParam(required = false, name = "requestBean", value = "查询条件")
    @ApiOperation(value = "(条件)查询 APP消息推送 异常处理 列表", httpMethod = "POST", notes = "(条件)查询 APP消息推送 异常处理 列表")
    public AdminResult getListByConditions(@RequestBody MessagePushErrorRequestBean requestBean) {
        MessagePushErrorRequest request = new MessagePushErrorRequest();
        MessagePushErrorResponse response = new MessagePushErrorResponse();
        try {
            BeanUtils.copyProperties(requestBean, request);
            Integer count = this.messagePushErrorService.getRecordCount(request);
            if(count > 0){
                Paginator paginator = new Paginator(request.getCurrPage(), count, request.getPageSize() == 0 ? 10 : request.getPageSize());
                request.setPaginator(paginator);
                List<MessagePushMsgHistoryVO> recordList = this.messagePushErrorService.getRecordList(request, paginator.getOffset(), paginator.getLimit());
                request.setRecordList(recordList);
                String fileDomainUrl = UploadFileUtils.getDoPath(FILE_DOMAIN_URL);
                response.setFileDomainUrl(fileDomainUrl);
            }
            // 标签
            List<MessagePushTagVO> tagList =  messagePushHistoryService.getAllPushTagList();
            response.setTagList(tagList);
            // 发送状态
            List<ParamNameVO> messagesSendStatus = this.messagePushErrorService.getParamNameList("MSG_PUSH_SEND_STATUS");
            response.setMessagesSendStatus(messagesSendStatus);
            response.setMsgErrorForm(request);
            //返回状态
            response.setCount(count);
        }catch (Exception e){
            e.printStackTrace();
            return new AdminResult(FAIL, FAIL_DESC);
        }
        return new AdminResult(response);
    }

    @PutMapping("/update")
    @ApiOperation(value = "重发 APP消息推送 异常处理", httpMethod = "PUT", notes = "重发 APP消息推送 异常处理")
    public AdminResult update(@RequestParam("id") String id) {
        MessagePushErrorResponse response = new MessagePushErrorResponse();
        try {
            // 重发此消息
            MessagePushMsgHistoryVO msg = this.messagePushErrorService.getRecord(id);
            //推送极光消息（暂不开启）
            this.messagePushErrorService.sendMessage(msg);
        }catch (Exception e){
            return new AdminResult(FAIL, FAIL_DESC);
        }
        return new AdminResult(response);
    }
}
