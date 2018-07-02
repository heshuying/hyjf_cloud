/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.client.ActivityListClient;
import com.hyjf.admin.service.ActivityListService;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.common.file.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * @author yaoy
 * @version ActivityListServiceImpl, v0.1 2018/6/26 17:45
 */
@Service
public class ActivityListServiceImpl implements ActivityListService {

    @Autowired
    ActivityListClient activityListClient;
    @Value("${http://cdn.huiyingdai.com/}")
    private String FILEDOMAILURL;
    @Value("${/hyjfdata}")
    private String FILEPHYSICALPATH;
    @Value("${/data/upfiles/image/activity}")
    private String FILEUPLOADTEMPPATH;

    @Override
    public List<ActivityListVO> getRecordList(ActivityListRequest activityListRequest) {
        List<ActivityListVO> activityListVOList = activityListClient.getRecordList(activityListRequest);
        return activityListVOList;
    }

    @Override
    public int insertRecord(Map<String, String> map) {
        ActivityListRequest request = null;
        if (null != map && map.size() > 0) {
            if (map.containsKey("title") && null != map.get("title")) {
                request.setTitle(map.get("title"));
            }
            if (map.containsKey("imgPc") && null != map.get("imgPc")) {
                request.setImgPc(map.get("imgPc"));
            }
            if (map.containsKey("imgApp") && null != map.get("imgApp")) {
                request.setImgApp(map.get("imgApp"));
            }
            if (map.containsKey("imgWei") && null != map.get("imgWei")) {
                request.setImgWei(map.get("imgWei"));
            }
            if (map.containsKey("qr") && null != map.get("qr")) {
                request.setQr(map.get("qr"));
            }
            if (map.containsKey("platform") && null != map.get("platform")) {
                request.setPlatform(map.get("platform"));
            }
            if (map.containsKey("activityPcUrl") && null != map.get("activityPcUrl")) {
                request.setActivityPcUrl(map.get("activityPcUrl"));
            }
            if (map.containsKey("activityAppUrl") && null != map.get("activityAppUrl")) {
                request.setActivityAppUrl(map.get("activityAppUrl"));
            }
            if (map.containsKey("activityWeiUrl") && null != map.get("activityWeiUrl")) {
                request.setActivityWeiUrl(map.get("activityWeiUrl"));
            }
            if (map.containsKey("urlBackground") && null != map.get("urlBackground")) {
                request.setUrlBackground(map.get("urlBackground"));
            }
            if (map.containsKey("description") && null != map.get("description")) {
                request.setDescription(map.get("description"));
            }
            if (map.containsKey("timeStart") && null != map.get("timeStart")) {
                request.setStartTime(Integer.parseInt(map.get("timeStart")));
            }
            if (map.containsKey("timeEnd") && null != map.get("timeEnd")) {
                request.setEndTime(Integer.parseInt(map.get("timeEnd")));
            }
            if (map.containsKey("createTime") && null != map.get("createTime")) {
                request.setCreateTime(Integer.parseInt(map.get("createTime")));
            }
            if (map.containsKey("updateTime") && null != map.get("updateTime")) {
                request.setUpdateTime(Integer.parseInt(map.get("updateTime")));
            }
        }
        int insertFlag = activityListClient.insertRecord(request);
        return insertFlag;
    }

    @Override
    public ActivityListVO selectActivityById(int id) {
        ActivityListVO activityListVO = activityListClient.selectActivityById(id);
        return activityListVO;
    }

    @Override
    public int updateActivity(Map<String, String> map) {
        ActivityListRequest request = null;
        if (null != map && map.size() > 0) {
            if (map.containsKey("id") && null != map.get("id")) {
                request.setId(Integer.parseInt(map.get("id")));
            }
            if (map.containsKey("title") && null != map.get("title")) {
                request.setTitle(map.get("title"));
            }
            if (map.containsKey("imgPc") && null != map.get("imgPc")) {
                request.setImgPc(map.get("imgPc"));
            }
            if (map.containsKey("imgApp") && null != map.get("imgApp")) {
                request.setImgApp(map.get("imgApp"));
            }
            if (map.containsKey("imgWei") && null != map.get("imgWei")) {
                request.setImgWei(map.get("imgWei"));
            }
            if (map.containsKey("qr") && null != map.get("qr")) {
                request.setQr(map.get("qr"));
            }
            if (map.containsKey("platform") && null != map.get("platform")) {
                request.setPlatform(map.get("platform"));
            }
            if (map.containsKey("activityPcUrl") && null != map.get("activityPcUrl")) {
                request.setActivityPcUrl(map.get("activityPcUrl"));
            }
            if (map.containsKey("activityAppUrl") && null != map.get("activityAppUrl")) {
                request.setActivityAppUrl(map.get("activityAppUrl"));
            }
            if (map.containsKey("activityWeiUrl") && null != map.get("activityWeiUrl")) {
                request.setActivityWeiUrl(map.get("activityWeiUrl"));
            }
            if (map.containsKey("urlBackground") && null != map.get("urlBackground")) {
                request.setUrlBackground(map.get("urlBackground"));
            }
            if (map.containsKey("description") && null != map.get("description")) {
                request.setDescription(map.get("description"));
            }
            if (map.containsKey("timeStart") && null != map.get("timeStart")) {
                request.setStartTime(Integer.parseInt(map.get("timeStart")));
            }
            if (map.containsKey("timeEnd") && null != map.get("timeEnd")) {
                request.setEndTime(Integer.parseInt(map.get("timeEnd")));
            }
        }
        int updateFlag = activityListClient.updateActivity(request);
        return updateFlag;
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
    public int deleteActivity(int id) {
        int deleteFlag = activityListClient.deleteActivity(id);
        if (deleteFlag > 0) {
            return deleteFlag;
        }
        return 0;
    }
}
