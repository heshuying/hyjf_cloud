package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.client.AmMarketClient;
import com.hyjf.admin.service.MessagePushNoticesService;
import com.hyjf.am.response.admin.MessagePushNoticesResponse;
import com.hyjf.am.response.admin.MessagePushTagResponse;
import com.hyjf.am.resquest.admin.MessagePushNoticesRequest;
import com.hyjf.common.file.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author lisheng
 * @version MessagePushNoticesServiceImpl, v0.1 2018/8/14 17:12
 */
@Service
public class MessagePushNoticesServiceImpl implements MessagePushNoticesService {
    @Autowired
    AmMarketClient amMarketClient;

    @Value("${file.domain.url}")
    private String url;
    @Value("${file.physical.path}")
    private String physical;
    @Value("${file.upload.real.path}")
    private String real;

    /**
     * 获取发送消息列表
     * @param bean
     * @return
     */
    @Override
    public MessagePushNoticesResponse getRecordList(MessagePushNoticesRequest bean) {
        MessagePushNoticesResponse recordList = amMarketClient.getRecordList(bean);
        return recordList;
    }

    /**
     * 添加消息发送列表
     * @param bean
     * @return
     */
    @Override
    public MessagePushNoticesResponse insertRecord(MessagePushNoticesRequest bean) {

        return amMarketClient.insertRecord(bean);
    }

    /**
     * 根据id查询单条消息
     * @param bean
     * @return
     */
    @Override
    public MessagePushNoticesResponse getRecord(MessagePushNoticesRequest bean) {
        return amMarketClient.getRecord(bean);
    }

    @Override
    public MessagePushTagResponse getTagList() {
        return amMarketClient.getTagList();
    }

    /**
     * 删除消息
     * @param bean
     * @return
     */
    @Override
    public MessagePushNoticesResponse deleteRecord(MessagePushNoticesRequest bean) {
        return amMarketClient.deleteRecord(bean);
    }

    /**
     * 修改消息
     * @param bean
     * @return
     */
    @Override
    public MessagePushNoticesResponse updateRecord(MessagePushNoticesRequest bean) {
        return amMarketClient.updateRecord(bean);
    }

    /**
     * 资料上传
     *
     * @return
     * @throws Exception
     */
    @Override
    public String uploadFile(MultipartFile iconImg) throws Exception {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        // 单位字节
        Long allowFileLength = 5000000L;
        String fileDomainUrl = UploadFileUtils.getDoPath(url);
        String filePhysicalPath = UploadFileUtils.getDoPath(physical);
        String fileUploadTempPath = UploadFileUtils.getDoPath(real);
        String logoRealPathDir = filePhysicalPath + fileUploadTempPath;
        File logoSaveFile = new File(logoRealPathDir);
        if (!logoSaveFile.exists()) {
            logoSaveFile.mkdirs();
        }
        BorrowCommonImage fileMeta = null;
        // 生成图片文件名
        String fileRealName = String.valueOf(System.currentTimeMillis());
        fileRealName = fileRealName + UploadFileUtils.getSuffix(iconImg.getOriginalFilename());
        // 上传至服务器
        String returnMessage = UploadFileUtils.upload4Stream(fileRealName, logoRealPathDir, iconImg.getInputStream(), allowFileLength);
        return returnMessage;
    }


}