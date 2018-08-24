/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.service.MessagePushErrorService;
import com.hyjf.am.resquest.config.MessagePushErrorRequest;
import com.hyjf.am.vo.admin.MessagePushMsgHistoryVO;
import com.hyjf.am.vo.admin.MessagePushTagVO;
import com.hyjf.am.vo.config.ParamNameVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dangzw
 * @version MessagePushErrorServiceImpl, v0.1 2018/8/14 22:09
 */
@Service
public class MessagePushErrorServiceImpl implements MessagePushErrorService {

    @Autowired
    private CsMessageClient csMessageClient;
    @Autowired
    private AmConfigClient amConfigClient;

    /**
     * 获取列表记录数
     *
     * @return
     */
    @Override
    public Integer getRecordCount(MessagePushErrorRequest request) {
        return csMessageClient.getRecordCount(request);
    }
    /**
     * 获取列表
     *
     * @return
     */
    @Override
    public List<MessagePushMsgHistoryVO> getRecordList(MessagePushErrorRequest request, int limitStart, int limitEnd) {
        return csMessageClient.getRecordListT(request, limitStart, limitEnd);
    }
    /**
     * 获取标签列表
     *
     * @return
     */
    @Override
    public List<MessagePushTagVO> getTagList() {
        return csMessageClient.getTagList();
    }
    /**
     * 获取数据字典名称
     *
     * @return
     */
    @Override
    public List<ParamNameVO> getParamNameList(String msg_push_send_status) {
        return amConfigClient.getParamNameList(msg_push_send_status);
    }
    /**
     * 获取单个信息
     *
     * @return
     */
    @Override
    public MessagePushMsgHistoryVO getRecord(Integer id) {
        return csMessageClient.getRecord(id);
    }
    /**
     * 推送极光消息
     * @param msg
     * @return 成功返回消息id  失败返回 error
     * @author Michael
     */
    @Override
    public void sendMessage(MessagePushMsgHistoryVO msg) {
        csMessageClient.sendMessage(msg);
    }
}
