package com.hyjf.admin.service;

import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.am.response.admin.MessagePushNoticesResponse;
import com.hyjf.am.response.admin.MessagePushTagResponse;
import com.hyjf.am.resquest.admin.MessagePushNoticesRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

/**
 * @author lisheng
 * @version MessagePushNoticesService, v0.1 2018/8/14 17:11
 */

public interface MessagePushNoticesService {
    /**
     * 获取消息发送列表
     *
     * @return
     */
    MessagePushNoticesResponse getRecordList(MessagePushNoticesRequest bean);

    /**
     * 添加消息发送列表
     * @param bean
     * @return
     */
    MessagePushNoticesResponse insertRecord(MessagePushNoticesRequest bean);

    /**
     * 上传文件
     * @param request
     * @return
     * @throws Exception
     */
    LinkedList<BorrowCommonImage> uploadFile(HttpServletRequest request) throws Exception;


    /**
     * 根据id查询单个消息
     * @param bean
     * @return
     */
    MessagePushNoticesResponse getRecord(MessagePushNoticesRequest bean);

    /**
     * 查询标签
     * @return
     */
    MessagePushTagResponse getTagList();


    /**
     * 删除标签
     * @param bean
     * @return
     */
    MessagePushNoticesResponse deleteRecord(MessagePushNoticesRequest bean);

    /**
     * 修改标签
     * @param bean
     * @return
     */
    MessagePushNoticesResponse updateRecord(MessagePushNoticesRequest bean);
}
