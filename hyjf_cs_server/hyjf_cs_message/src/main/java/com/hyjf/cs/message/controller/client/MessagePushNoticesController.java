package com.hyjf.cs.message.controller.client;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.MessagePushNoticesResponse;
import com.hyjf.am.resquest.admin.MessagePushNoticesRequest;
import com.hyjf.am.vo.admin.MessagePushMsgVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.mc.MessagePushMsg;
import com.hyjf.cs.message.service.MessagePushNoticesService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author lisheng
 * @version MessagePushNoticesController, v0.1 2018/8/14 15:01
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/admin_message")
public class MessagePushNoticesController {
    @Autowired
    MessagePushNoticesService messagePushNoticesService;

    /**
     * 获取发送消息列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/message_push_list")
    public MessagePushNoticesResponse selectMessagePushList(
            @RequestBody MessagePushNoticesRequest request) {
        MessagePushNoticesResponse response = new MessagePushNoticesResponse();
        Integer count = messagePushNoticesService.getRecordCount(request);
        if (count > 0) {
            Paginator paginator = new Paginator(request.getCurrPage(), count,request.getPageSize());
            List<MessagePushMsg> list = messagePushNoticesService.getRecordList(request, paginator.getOffset(), paginator.getLimit());
            if (!CollectionUtils.isEmpty(list)) {
                List<MessagePushMsgVO> voList = CommonUtils.convertBeanList(list,
                        MessagePushMsgVO.class);
                response.setResultList(voList);
                response.setRecordTotal(count);
            }
        }
        return response;
    }

    /**
     * 添加消息发送列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/insert_push_list")
    public MessagePushNoticesResponse insertMessagePushList(
            @RequestBody MessagePushNoticesRequest request) {
        MessagePushNoticesResponse response = new MessagePushNoticesResponse();
        messagePushNoticesService.insertRecord(request);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 删除消息发送数据
     *
     * @return
     */
    @RequestMapping("/delete_push_list")
    public MessagePushNoticesResponse deleteRecordAction(@RequestBody MessagePushNoticesRequest request) {
        String ids = request.getIds();
        List<String> recordList = JSONArray.parseArray(ids, String.class);
        MessagePushNoticesResponse response = new MessagePushNoticesResponse();
        for (String id : recordList) {
            messagePushNoticesService.deleteRecord(id);
        }
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 获取消息发送数据
     *
     * @return
     */
    @RequestMapping("/get_push_record")
    public MessagePushNoticesResponse getRecordAction(@RequestBody MessagePushNoticesRequest request) {
        MessagePushNoticesResponse response = new MessagePushNoticesResponse();
        MessagePushMsg record = messagePushNoticesService.getRecord(request);
        MessagePushMsgVO messagePushMsgVO = CommonUtils.convertBean(record, MessagePushMsgVO.class);
        response.setResult(messagePushMsgVO);
        response.setRtn(Response.SUCCESS);
        return response;
    }


    /**
     * 更新消息发送数据
     *
     * @param form
     * @return
     */
    @RequestMapping("/update_push_list")
    public MessagePushNoticesResponse updateAction(@RequestBody MessagePushNoticesRequest form) {
        MessagePushNoticesResponse response = new MessagePushNoticesResponse();
        // 更新
        MessagePushMsg record = new MessagePushMsg();
        BeanUtils.copyProperties(form, record);
        if (form.getMsgAction().intValue() == CustomConstants.MSG_PUSH_TEMP_ACT_0) {
            record.setMsgActionUrl("");
        }
        if (form.getMsgAction().intValue() == CustomConstants.MSG_PUSH_TEMP_ACT_1) {
            record.setMsgActionUrl(form.getNoticesActionUrl1());
        }
        if (form.getMsgAction().intValue() == CustomConstants.MSG_PUSH_TEMP_ACT_3) {
            record.setMsgActionUrl(form.getNoticesActionUrl3());
        }
        if (form.getMsgAction().intValue() == CustomConstants.MSG_PUSH_TEMP_ACT_2) {
            record.setMsgActionUrl(form.getNoticesActionUrl2());
        }
        if (form.getMsgSendType().intValue() == CustomConstants.MSG_PUSH_SEND_TYPE_1) {
            record.setSendTime(GetDate.getMyTimeInMillis());
            if (StringUtils.isNotEmpty(form.getNoticesPreSendTimeStr())) {
                try {
                    Integer time = GetDate.strYYYYMMDDHHMMSS2Timestamp2(form.getNoticesPreSendTimeStr());
                    if (time != 0) {
                        record.setPreSendTime(time);
                        record.setSendTime(time);
                    }
                } catch (Exception e) {
                }
            }
        } else {
            record.setPreSendTime(null);
            record.setSendTime(GetDate.getNowTime10());
        }
        messagePushNoticesService.updateRecord(record);
        response.setRtn(Response.SUCCESS);
        return response;
    }



}
