/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.config.MessagePushTagResponse;
import com.hyjf.am.resquest.config.MessagePushTagRequest;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;

/**
 * @author yaoyong
 * @version MessagePushTagService, v0.1 2018/8/14 14:53
 */
public interface MessagePushTagService {
    /**
     * 根据条件查询消息推送标签管理表
     * @param request
     * @return
     */
    MessagePushTagResponse searchList(MessagePushTagRequest request);

    /**
     * 查询标签状态
     * @param nameClass
     * @return
     */
    List<ParamNameVO> getParamNameList(String nameClass);

    /**
     * 根据id查询标签详情
     * @param id
     * @return
     */
    MessagePushTagResponse getRecord(Integer id);

    /**
     * 添加标签信息
     * @param request
     * @return
     */
    MessagePushTagResponse insertMessagePushTag(MessagePushTagRequest request);

    /**
     * 修改标签信息
     * @param tagRequest
     * @return
     */
    MessagePushTagResponse updateMessagePushTag(MessagePushTagRequest tagRequest);

    /**
     * 删除信息
     * @param id
     * @return
     */
    MessagePushTagResponse deleteAction(Integer id);

    /**
     * 修改信息
     * @param record
     * @return
     */
    MessagePushTagResponse updatePushTag(MessagePushTagVO record);

    /**
     * 检查标签编码是否唯一
     *
     * @param id
     * @param tagCode
     */
    MessagePushTagResponse countByTagCode(Integer id, String tagCode);

    /**
     *获取标签类型
     * @return
     */
    List<MessagePushTagVO> getAllPushTagList();

    /**
     * 获取标签类型
     * @return
     */
    List<MessagePushTagVO> getTagList();

    /**
     * 根据标签id查询标签
     * @param tagId
     * @return
     */
    MessagePushTagVO getPushTagByTagId(Integer tagId);
}
