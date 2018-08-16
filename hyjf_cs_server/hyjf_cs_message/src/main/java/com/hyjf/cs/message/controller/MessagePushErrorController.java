/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller;

import com.hyjf.am.response.admin.MessagePushErrorResponse;
import com.hyjf.am.resquest.config.MessagePushErrorRequest;
import com.hyjf.am.vo.admin.MessagePushErrorVO;
import com.hyjf.am.vo.admin.coupon.ParamName;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;
import com.hyjf.cs.message.bean.mc.MessagePushTag;
import com.hyjf.cs.message.service.msgpush.MessagePushErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author dangzw
 * @version MessagePushErrorController, v0.1 2018/8/14 22:39
 */
@RestController
@RequestMapping("/cs-message/app_message")
public class MessagePushErrorController {

    @Value("file.domain.url")
    private String FILE_DOMAIN_URL;

    @Autowired
    private MessagePushErrorService messagePushErrorService;

    /**
     * (条件)查询 APP消息推送 异常处理 列表
     *
     * @param request
     */
    @RequestMapping("getListByConditions")
    private MessagePushErrorResponse getListByConditions(MessagePushErrorRequest request) {
        MessagePushErrorResponse response = new MessagePushErrorResponse();
        Integer count = this.messagePushErrorService.getRecordCount(request);
        if(count > 0){
            Paginator paginator = new Paginator(request.getPaginatorPage(), count);
            request.setPaginator(paginator);
            List<MessagePushMsgHistory> recordList = this.messagePushErrorService.getRecordList(request, paginator.getOffset(), paginator.getLimit());
            String fileDomainUrl = UploadFileUtils.getDoPath(FILE_DOMAIN_URL);
            response.setFileDomainUrl(fileDomainUrl);
            List<MessagePushErrorVO> messagePushErrorVOList = CommonUtils.convertBeanList(recordList, MessagePushErrorVO.class);
            response.setResultList(messagePushErrorVOList);
        }
        // 标签
        List<MessagePushTag> tagList = this.messagePushErrorService.getTagList();
        List<MessagePushErrorVO> messagePushErrorVOListT = CommonUtils.convertBeanList(tagList, MessagePushErrorVO.class);
        response.setResultList(messagePushErrorVOListT);
        // 发送状态
        List<ParamName> messagesSendStatus = this.messagePushErrorService.getParamNameList("MSG_PUSH_SEND_STATUS");
        List<MessagePushErrorVO> messagePushErrorVOList = CommonUtils.convertBeanList(messagesSendStatus, MessagePushErrorVO.class);
        response.setResultList(messagePushErrorVOList);
        return response;
    }

    /**
     * 数据修改 APP消息推送 异常处理
     *
     * @param request
     * @return
     */
    @RequestMapping()
    public MessagePushErrorResponse resendAction(MessagePushErrorRequest request) {
        MessagePushErrorResponse response = new MessagePushErrorResponse();
        // 重发此消息
        if (request.getIds() != null) {
            Integer id = Integer.valueOf(request.getIds());
            MessagePushMsgHistory msg = this.messagePushErrorService.getRecord(id);
            this.messagePushErrorService.sendMessage(msg);
        }
        return response;
    }
}
