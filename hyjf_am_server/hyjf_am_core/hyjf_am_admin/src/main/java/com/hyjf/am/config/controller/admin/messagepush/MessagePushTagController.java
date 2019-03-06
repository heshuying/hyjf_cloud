/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.admin.messagepush;

import com.hyjf.am.config.dao.model.auto.MessagePushTag;
import com.hyjf.am.config.service.MessagePushTagService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.MessagePushTagResponse;
import com.hyjf.am.resquest.config.MessagePushTagRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.vo.config.MessagePushTagVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yaoyong
 * @version MessagePushTagController, v0.1 2018/9/26 15:51
 */
@RestController
@RequestMapping("/am-config/messagePushTag")
public class MessagePushTagController extends BaseController {

    @Autowired
    private MessagePushTagService messagePushTagService;

    /**
     * 获取消息推送标签管理列表
     * @param request
     * @return
     */
    @PostMapping("/searchList")
    public MessagePushTagResponse searchList(@RequestBody MessagePushTagRequest request) {
        MessagePushTagResponse response = new MessagePushTagResponse();
        Integer count = messagePushTagService.countRecord(request);
        Paginator paginator = new Paginator(request.getCurrPage(), count, request.getPageSize());
        if (request.getPageSize() == 0) {
            paginator = new Paginator(request.getCurrPage(), count);
        }
        List<MessagePushTag> messagePushTagList = messagePushTagService.searchList(request,paginator.getOffset(),paginator.getLimit());
        response.setCount(count);
        if (count > 0) {
            List<MessagePushTagVO> list = CommonUtils.convertBeanList(messagePushTagList,MessagePushTagVO.class);
            response.setResultList(list);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 根据id获取标签详情
     * @param id
     * @return
     */
    @GetMapping("/getRecord/{id}")
    public MessagePushTagResponse getRecord(@PathVariable Integer id) {
        MessagePushTagResponse response = new MessagePushTagResponse();
        MessagePushTag messagePushTag = messagePushTagService.getRecord(id);
        if (messagePushTag != null) {
            MessagePushTagVO record = new MessagePushTagVO();
            BeanUtils.copyProperties(messagePushTag,record);
            response.setResult(record);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 添加标签
     * @param request
     * @return
     */
    @PostMapping("/insertMessagePushTag")
    public MessagePushTagResponse insertAction(@RequestBody MessagePushTagRequest request) {
        MessagePushTagResponse response = new MessagePushTagResponse();
        MessagePushTag messagePushTag = new MessagePushTag();
        BeanUtils.copyProperties(request,messagePushTag);
        messagePushTag.setCreateTime(GetDate.getDate());
        messagePushTag.setStatus(0);
        try {
            Integer result = messagePushTagService.insertAction(messagePushTag);
            if (result > 0) {
                response.setRtn(Response.SUCCESS);
                return response;
            }
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        response.setRtn(Response.FAIL);
        return response;
    }

    /**
     * 修改标签信息
     * @param request
     * @return
     */
    @PostMapping("/updateMessagePushTag")
    public MessagePushTagResponse updateAction(@RequestBody MessagePushTagRequest request) {
        MessagePushTagResponse response = new MessagePushTagResponse();
        MessagePushTag messagePushTag = new MessagePushTag();
        BeanUtils.copyProperties(request,messagePushTag);
        messagePushTag.setUpdateTime(GetDate.getDate());
        try {
            Integer result = messagePushTagService.updateAction(messagePushTag);
            if (result > 0) {
                response.setRtn(Response.SUCCESS);
                return response;
            }
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        response.setRtn(Response.FAIL);
        response.setMessage(Response.FAIL_MSG);
        return response;
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @GetMapping("/deleteMessagePushTag/{id}")
    public MessagePushTagResponse deleteAction(@PathVariable Integer id) {
        MessagePushTagResponse response = new MessagePushTagResponse();
        try {
            Integer result = messagePushTagService.deleteAction(id);
            if (result > 0) {
                response.setRtn(Response.SUCCESS);
                return response;
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        response.setRtn(Response.FAIL);
        response.setMessage(Response.FAIL_MSG);
        return response;
    }

    /**
     * 修改信息
     * @param messagePushTagVO
     * @return
     */
    @PostMapping("/updatePushTag")
    public MessagePushTagResponse updatePushTag(@RequestBody MessagePushTagVO messagePushTagVO) {
        MessagePushTagResponse response = new MessagePushTagResponse();
        MessagePushTag messagePushTag = new MessagePushTag();
        BeanUtils.copyProperties(messagePushTagVO,messagePushTag);
        try {
            Integer result = messagePushTagService.updateAction(messagePushTag);
            if (result > 0) {
                response.setRtn(Response.SUCCESS);
                return response;
            }
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        response.setRtn(Response.FAIL);
        return response;
    }

    /**
     * 检查标签编码是否唯一
     * @param id
     * @param tagCode
     * @return
     */
    @GetMapping("/countByTagCode/{id}/{tagCode}")
    public MessagePushTagResponse countByTagCode(Integer id, @PathVariable String tagCode) {
        MessagePushTagResponse response = new MessagePushTagResponse();
        int result = 0;
        try {
            result = messagePushTagService.countByTagCode(id, tagCode);
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        response.setCount(result);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 获取标签类型
     * @return
     */
    @RequestMapping("/getAllPushTagList")
    public MessagePushTagResponse getAllPushTagList() {
        MessagePushTagResponse response = new MessagePushTagResponse();
        List<MessagePushTag> list = messagePushTagService.getAllPushTagList();
        if (!CollectionUtils.isEmpty(list)) {
            List<MessagePushTagVO> messagePushTagVOS = CommonUtils.convertBeanList(list,MessagePushTagVO.class);
            response.setResultList(messagePushTagVOS);
        }
        return response;
    }

    @RequestMapping("/getTagList")
    public MessagePushTagResponse getTagList() {
        MessagePushTagResponse response = new MessagePushTagResponse();
        List<MessagePushTag> list = messagePushTagService.getTagList();
        if (!CollectionUtils.isEmpty(list)) {
            List<MessagePushTagVO> messagePushTagVOS = CommonUtils.convertBeanList(list,MessagePushTagVO.class);
            response.setResultList(messagePushTagVOS);
        }
        return response;
    }

    /**
     * 根据名称获取消息模板标签
     *
     * @param tagName
     * @return
     */
    @RequestMapping("/findMsgTagByTagName/{tagName}")
    public MessagePushTagResponse findMsgTagByTagName(@PathVariable String tagName) {
        MessagePushTagResponse response = new MessagePushTagResponse();
        MessagePushTag messagePushTag = messagePushTagService.findMsgTagByTagName(tagName);
        if (messagePushTag != null) {
            MessagePushTagVO messagePushTagVO = new MessagePushTagVO();
            BeanUtils.copyProperties(messagePushTag, messagePushTagVO);
            response.setResult(messagePushTagVO);
        }
        return response;
    }

    /**
     * 根据消息标签id查询消息标签
     * @param tagId
     * @return
     */
    @RequestMapping("/getTagByTagId/{tagId}")
    public MessagePushTagResponse getTagByTagId(@PathVariable Integer tagId) {
        MessagePushTagResponse response = new MessagePushTagResponse();
        MessagePushTag messagePushTag = messagePushTagService.getTagByTagId(tagId);
        if (messagePushTag != null) {
            MessagePushTagVO messagePushTagVO = new MessagePushTagVO();
            BeanUtils.copyProperties(messagePushTag,messagePushTagVO);
            response.setResult(messagePushTagVO);
        }
        return response;
    }
}


