/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.client;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.MessagePushHistoryResponse;
import com.hyjf.am.response.admin.MessagePushTagResponse;
import com.hyjf.am.resquest.config.MessagePushErrorRequest;
import com.hyjf.am.vo.admin.MessagePushMsgHistoryVO;
import com.hyjf.am.vo.admin.MessagePushTagVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;
import com.hyjf.cs.message.bean.mc.MessagePushTag;
import com.hyjf.cs.message.service.message.MessagePushTemplateStaticsService;
import com.hyjf.cs.message.service.msgpush.MessagePushErrorService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author dangzw
 * @version MessagePushErrorController, v0.1 2018/8/14 22:39
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/msgpush/error")
public class MessagePushErrorController {

    @Value("file.domain.url")
    private String FILE_DOMAIN_URL;

    @Autowired
    private MessagePushErrorService messagePushErrorService;
    @Autowired
    private MessagePushTemplateStaticsService messagePushTemplateStaticsService;

    /**
     * 获取列表记录数
     *
     * @return
     */
    @RequestMapping("getRecordCount")
    private MessagePushHistoryResponse getRecordCount(@RequestBody MessagePushErrorRequest request) {
        MessagePushHistoryResponse response = new MessagePushHistoryResponse();
        Integer recordCount = messagePushErrorService.getRecordCount(request);
        response.setRecordTotal(recordCount);
        return response;
    }

    /**
     * 获取列表
     *
     * @return
     */
    @PostMapping("getRecordListT")
    public MessagePushHistoryResponse getRecordListT(@RequestBody MessagePushErrorRequest request) {
        MessagePushHistoryResponse response = new MessagePushHistoryResponse();
        List<MessagePushMsgHistory> messagePushMsgHistory = messagePushErrorService.getRecordList(request, request.getLimitStart(), request.getLimitEnd());

        if (!CollectionUtils.isEmpty(messagePushMsgHistory)){
            List<MessagePushMsgHistoryVO> messagePushErrorVO = CommonUtils.convertBeanList(messagePushMsgHistory, MessagePushMsgHistoryVO.class);

            for (MessagePushMsgHistoryVO message:messagePushErrorVO) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formatSendTime = simpleDateFormat.format(Long.valueOf(message.getSendTime()));
                String formatCreateTime = simpleDateFormat.format(Long.valueOf(message.getCreateTime()));
                String formatLastupdateTime = simpleDateFormat.format(Long.valueOf(message.getLastupdateTime()));
                message.setSendTime(formatSendTime);
                message.setCreateTime(formatCreateTime);
                message.setLastupdateTime(formatLastupdateTime);
                //获得tagName
                String tagName = messagePushTemplateStaticsService.selectTagName(request.getTagIdSrch());
                message.setTagName(tagName);
            }

            response.setResultList(messagePushErrorVO);
            return response;
        }
        response.setRtn(Response.FAIL);
        response.setMessage(Response.FAIL_MSG);
        return response;
    }

    /**
     * 获取标签列表
     *
     * @return
     */
    @RequestMapping("getTagList")
    public MessagePushTagResponse getTagList() {
        MessagePushTagResponse response = new MessagePushTagResponse();
        List<MessagePushTag> tagList = messagePushErrorService.getTagList();
        if (!CollectionUtils.isEmpty(tagList)){
            List<MessagePushTagVO> messagePushErrorVO = CommonUtils.convertBeanList(tagList, MessagePushTagVO.class);
            response.setResultList(messagePushErrorVO);
            return response;
        }
        response.setRtn(Response.FAIL);
        response.setMessage(Response.FAIL_MSG);
        return response;
    }

    /**
     * 获取单个信息
     *
     * @return
     */
    @GetMapping("getRecord/{id}")
    public MessagePushHistoryResponse getRecord(@PathVariable String id) {
        MessagePushHistoryResponse response = new MessagePushHistoryResponse();
        MessagePushMsgHistory msg = messagePushErrorService.getRecord(id);
        if (msg != null){
            MessagePushMsgHistoryVO messagePushErrorVO = CommonUtils.convertBean(msg, MessagePushMsgHistoryVO.class);
            response.setResult(messagePushErrorVO);
            return response;
        }
        response.setRtn(Response.FAIL);
        response.setMessage(Response.FAIL_MSG);
        return response;
    }

    /**
     * 推送极光消息
     * @param messagePushMsgHistoryVO
     * @return 成功返回消息id  失败返回 error
     * @author Michael
     */
    @RequestMapping("sendMessage")
    public void sendMessage(@RequestBody MessagePushMsgHistoryVO messagePushMsgHistoryVO) {
        MessagePushMsgHistory msg = new MessagePushMsgHistory();
        BeanUtils.copyProperties(messagePushMsgHistoryVO, msg);
        messagePushErrorService.sendMessage(msg);
    }
}
