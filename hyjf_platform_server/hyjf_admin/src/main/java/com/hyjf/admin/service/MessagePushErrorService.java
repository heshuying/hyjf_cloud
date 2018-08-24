/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.resquest.config.MessagePushErrorRequest;
import com.hyjf.am.vo.admin.MessagePushMsgHistoryVO;
import com.hyjf.am.vo.admin.MessagePushTagVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;

/**
 * @author dangzw
 * @version MessagePushErrorService, v0.1 2018/8/14 22:08
 */
public interface MessagePushErrorService {

    /**
     * 获取列表记录数
     *
     * @return
     */
    Integer getRecordCount(MessagePushErrorRequest request);

    /**
     * 获取列表
     *
     * @return
     */
    List<MessagePushMsgHistoryVO> getRecordList(MessagePushErrorRequest request, int limitStart, int limitEnd);

    /**
     * 获取标签列表
     *
     * @return
     */
    List<MessagePushTagVO> getTagList();

    /**
     * 获取数据字典名称
     *
     * @return
     */
    List<ParamNameVO> getParamNameList(String msg_push_send_status);

    /**
     * 获取单个信息
     *
     * @return
     */
    MessagePushMsgHistoryVO getRecord(Integer id);

    /**
     * 推送极光消息
     * @param msg
     * @return 成功返回消息id  失败返回 error
     * @author Michael
     */
    void sendMessage(MessagePushMsgHistoryVO msg);
}
