/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.client.ActivityListClient;
import com.hyjf.admin.service.ActivityListService;
import com.hyjf.am.response.market.ActivityListResponse;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.common.file.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author yaoy
 * @version ActivityListServiceImpl, v0.1 2018/6/26 17:45
 */
@Service
public class ActivityListServiceImpl implements ActivityListService {

    @Autowired
    ActivityListClient activityListClient;
//    @Value("${http://cdn.huiyingdai.com/}")
    @Value("${file.domain.url}")
    private String FILEDOMAILURL;
//    @Value("${/hyjfdata}")
    @Value("${file.physical.path}")
    private String FILEPHYSICALPATH;
//    @Value("${/data/upfiles/image/activity}")
    @Value("${file.upload.activity.img.path}")
    private String FILEUPLOADTEMPPATH;

    @Override
    public ActivityListResponse getRecordList(ActivityListRequest activityListRequest) {
        return activityListClient.getRecordList(activityListRequest);
    }

    @Override
    public ActivityListResponse insertRecord(ActivityListRequest request) {
        return activityListClient.insertRecord(request);
    }

    @Override
    public ActivityListResponse selectActivityById(ActivityListRequest activityListRequest) {
        return activityListClient.selectActivityById(activityListRequest);
    }

    @Override
    public ActivityListResponse updateActivity(ActivityListRequest activityListRequest) {
        return activityListClient.updateActivity(activityListRequest);
    }

    @Override
    public String uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        String fileDomainUrl = UploadFileUtils.getDoPath(FILEDOMAILURL);
        String filePhysicalPath = UploadFileUtils.getDoPath(FILEPHYSICALPATH);
        String fileUploadTempPath = UploadFileUtils.getDoPath(FILEUPLOADTEMPPATH);

        String logoRealPathDir = filePhysicalPath + fileUploadTempPath;

        File logoSaveFile = new File(logoRealPathDir);
        if (!logoSaveFile.exists()) {
            logoSaveFile.mkdirs();
        }

        BorrowCommonImage fileMeta = null;
        LinkedList<BorrowCommonImage> files = new LinkedList<BorrowCommonImage>();

        Iterator<String> itr = multipartRequest.getFileNames();
        MultipartFile multipartFile = null;

        while (itr.hasNext()) {
            multipartFile = multipartRequest.getFile(itr.next());
            String fileRealName = String.valueOf(System.currentTimeMillis());
            String originalFilename = multipartFile.getOriginalFilename();
            fileRealName = fileRealName + UploadFileUtils.getSuffix(multipartFile.getOriginalFilename());

            // 文件大小
            String errorMessage = UploadFileUtils.upload4Stream(fileRealName, logoRealPathDir, multipartFile.getInputStream(), 5000000L);

            fileMeta = new BorrowCommonImage();
            int index = originalFilename.lastIndexOf(".");
            if (index != -1) {
                fileMeta.setImageName(originalFilename.substring(0, index));
            } else {
                fileMeta.setImageName(originalFilename);
            }

            fileMeta.setImageRealName(fileRealName);
            fileMeta.setImageSize(multipartFile.getSize() / 1024 + "");
            fileMeta.setImageType(multipartFile.getContentType());
            fileMeta.setErrorMessage(errorMessage);
            // 获取文件路径
            fileMeta.setImagePath(fileUploadTempPath + fileRealName);
            fileMeta.setImageSrc(fileDomainUrl + fileUploadTempPath + fileRealName);
            files.add(fileMeta);
        }
        return JSONObject.toJSONString(files, true);
    }

    @Override
    public ActivityListResponse deleteActivity(ActivityListRequest request) {
        return activityListClient.deleteActivity(request);
    }
}
