/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.msgpush.error;

import com.hyjf.admin.beans.request.MessagePushErrorRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.MessagePushErrorService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.MessagePushErrorResponse;
import com.hyjf.am.resquest.config.MessagePushErrorRequest;
import com.hyjf.am.vo.admin.MessagePushErrorVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.schema.Collections;

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
    public AdminResult<ListResult<MessagePushErrorVO>> getListByConditions(@RequestBody MessagePushErrorRequestBean requestBean) {
        MessagePushErrorRequest request = new MessagePushErrorRequest();
        BeanUtils.copyProperties(requestBean, request);
        MessagePushErrorResponse response = new MessagePushErrorResponse();
        Integer count = this.messagePushErrorService.getRecordCount(request);
        if(count > 0){
            Paginator paginator = new Paginator(request.getPaginatorPage(), count);
            request.setPaginator(paginator);
            List<MessagePushErrorVO> recordList = this.messagePushErrorService.getRecordList(request, paginator.getOffset(), paginator.getLimit());
            String fileDomainUrl = UploadFileUtils.getDoPath(FILE_DOMAIN_URL);
            response.setFileDomainUrl(fileDomainUrl);
            if (CollectionUtils.isNotEmpty(recordList)){
                List<MessagePushErrorVO> messagePushErrorVOList = CommonUtils.convertBeanList(recordList, MessagePushErrorVO.class);
                response.setResultList(messagePushErrorVOList);
            }
        }
        // 标签
        List<MessagePushErrorVO> tagList = this.messagePushErrorService.getTagList();
        if (CollectionUtils.isNotEmpty(tagList)){
            List<MessagePushErrorVO> messagePushErrorVOListT = CommonUtils.convertBeanList(tagList, MessagePushErrorVO.class);
            response.setResultList(messagePushErrorVOListT);
        }
        // 发送状态
        List<ParamNameVO> messagesSendStatus = this.messagePushErrorService.getParamNameList("MSG_PUSH_SEND_STATUS");
        if (CollectionUtils.isNotEmpty(messagesSendStatus)){
            List<MessagePushErrorVO> messagePushErrorVOList = CommonUtils.convertBeanList(messagesSendStatus, MessagePushErrorVO.class);
            response.setResultList(messagePushErrorVOList);
        }
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }

    @PutMapping("/update")
    @ApiOperation(value = "数据修改 APP消息推送 异常处理", notes = "数据修改 APP消息推送 异常处理")
    public AdminResult<ListResult<MessagePushErrorVO>> update(
                                    @RequestBody MessagePushErrorRequestBean requestBean) {
        MessagePushErrorRequest request = new MessagePushErrorRequest();
        BeanUtils.copyProperties(requestBean, request);
        if (request == null) {
            return new AdminResult<>(FAIL, "请求为空");
        }
        MessagePushErrorResponse response = new MessagePushErrorResponse();
        // 重发此消息
        if (request.getIds() != null) {
            Integer id = Integer.valueOf(request.getIds());
            MessagePushErrorVO msg = this.messagePushErrorService.getRecord(id);
            this.messagePushErrorService.sendMessage(msg);
        }
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }
}
