/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.MessagePushMsgResponse;
import com.hyjf.am.resquest.message.MessagePushMsgRequest;
import com.hyjf.am.vo.admin.MessagePushMsgVO;

import java.util.List; /**
 * @author yaoyong
 * @version MessagePushMsgService, v0.1 2018/8/15 16:02
 */
public interface MessagePushMsgService {
    /**
     * 查询手动发放短信列表
     * @param request
     * @return
     */
    MessagePushMsgResponse selectMessagePushMsg(MessagePushMsgRequest request);

    /**
     * 根据id查询手动发放的信息
     * @param id
     * @return
     */
    MessagePushMsgResponse getRecord(Integer id);

    /**
     * 添加手动发送短信
     * @param templateVO
     * @return
     */
    MessagePushMsgResponse insertRecord(MessagePushMsgVO templateVO);

    /**
     * 修改手动发送短信
     * @param templateRequest
     * @return
     */
    MessagePushMsgResponse updateMessagePushMsg(MessagePushMsgRequest templateRequest);

    /**
     * 删除手动发送短信
     * @param recordList
     * @return
     */
    MessagePushMsgResponse deleteAction(List<Integer> recordList);
}
