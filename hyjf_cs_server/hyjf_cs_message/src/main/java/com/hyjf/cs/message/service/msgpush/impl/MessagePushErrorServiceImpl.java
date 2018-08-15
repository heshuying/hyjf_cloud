/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service.msgpush.impl;

import com.hyjf.am.resquest.config.MessagePushErrorRequest;
import com.hyjf.am.vo.admin.coupon.ParamName;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;
import com.hyjf.cs.message.bean.mc.MessagePushTag;
import com.hyjf.cs.message.service.msgpush.MessagePushErrorService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author dangzw
 * @version MessagePushErrorServiceImpl, v0.1 2018/8/14 22:45
 */
@Service
public class MessagePushErrorServiceImpl implements MessagePushErrorService {

    /**
     * 获取列表记录数
     *
     * @return
     */
    @Override
    public Integer getRecordCount(MessagePushErrorRequest request) {
        return null;
    }

    /**
     * 获取列表
     *
     * @return
     */
    @Override
    public List<MessagePushMsgHistory> getRecordList(MessagePushErrorRequest request, int offset, int limit) {
        return null;
    }

    /**
     * 获取标签列表
     *
     * @return
     */
    @Override
    public List<MessagePushTag> getTagList() {
        return null;
    }

    /**
     * 获取数据字典名称
     *
     * @return
     */
    @Override
    public List<ParamName> getParamNameList(String msg_push_send_status) {
        return null;
    }

    /**
     * 获取单个信息
     *
     * @return
     */
    @Override
    public MessagePushMsgHistory getRecord(Integer id) {
        return null;
    }

    /**
     * 推送极光消息
     * @param msg
     * @return 成功返回消息id  失败返回 error
     * @author Michael
     */
    @Override
    public void sendMessage(MessagePushMsgHistory msg) {

    }
}
