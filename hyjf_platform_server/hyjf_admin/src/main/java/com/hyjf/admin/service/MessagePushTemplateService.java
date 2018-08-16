/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.config.MessagePushTemplateResponse;
import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;

/**
 * @author yaoyong
 * @version MessagePushTemplateService, v0.1 2018/8/14 20:07
 */
public interface MessagePushTemplateService {
    /**
     * 获取消息模板列表
     * @param request
     * @return
     */
    MessagePushTemplateResponse searchList(MsgPushTemplateRequest request);

    /**
     * 根据id获取消息模板
     * @param id
     * @return
     */
    MessagePushTemplateResponse getRecord(Integer id);

    /**
     * 查询字典表
     * @param nameClass
     * @return
     */
    List<ParamNameVO> getParamNameList(String nameClass);

    /**
     * 插入模板
     * @param templateVO
     * @return
     */
    MessagePushTemplateResponse insertAction(MessagePushTemplateVO templateVO);

    /**
     * 修改模板
     * @param templateRequest
     * @return
     */
    MessagePushTemplateResponse updateRecord(MsgPushTemplateRequest templateRequest);

    /**
     * 删除模板
     * @param recordList
     * @return
     */
    MessagePushTemplateResponse deleteAction(List<Integer> recordList);

    /**
     * 根据条件查询模板
     * @param id
     * @param templateCode
     * @return
     */
    MessagePushTemplateResponse countByTemplateCode(Integer id, String templateCode);
}
