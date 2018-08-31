package com.hyjf.admin.service.impl.promotion.channel;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.promotion.channel.ChannelService;
import com.hyjf.am.vo.admin.promotion.channel.ChannelCustomizeVO;
import com.hyjf.am.vo.admin.promotion.channel.UtmChannelVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import com.hyjf.common.file.UploadFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/14 10:47
 * @Description: ChannelServiceImpl
 */
@Service
public class ChannelServiceImpl implements ChannelService {
    private Logger logger = LoggerFactory.getLogger(ChannelServiceImpl.class);

    @Value("${file.domain.url}")
    private String FILEDOMAILURL;
    @Value("${file.physical.path}")
    private String FILEPHYSICALPATH;
    @Value("${file.upload.activity.img.path}")
    private String FILEUPLOADTEMPPATH;
    @Autowired
    private AmUserClient amUserClient;

    @Override
    public Integer countList(ChannelCustomizeVO channelCustomizeVO) {
        return amUserClient.getChannelCount(channelCustomizeVO);
    }

    @Override
    public List<ChannelCustomizeVO> getByPageList(ChannelCustomizeVO channelCustomizeVO) {
        return amUserClient.getChannelList(channelCustomizeVO);
    }

    @Override
    public List<UtmPlatVO> getUtmPlat(String sourceId) {
        return amUserClient.getUtmPlat(sourceId);
    }

    @Override
    public UtmChannelVO getRecord(String utmId) {
        return amUserClient.getRecord(utmId);
    }

    @Override
    public UserVO getUser(String utmReferrer, String userId) {
        return amUserClient.getUser(utmReferrer, userId);
    }

    @Override
    public boolean insertOrUpdateUtm(ChannelCustomizeVO channelCustomizeVO) {
        return amUserClient.insertOrUpdateUtm(channelCustomizeVO);
    }

    @Override
    public boolean deleteAction(ChannelCustomizeVO channelCustomizeVO) {
        return amUserClient.deleteAction(channelCustomizeVO);
    }

    @Override
    public String uploadFile(HttpServletRequest request, HttpServletResponse response) {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);

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
            String errorMessage = null;
            try {
                errorMessage = UploadFileUtils.upload4Stream(fileRealName, logoRealPathDir, multipartFile.getInputStream(), 5000000L);
            } catch (Exception e) {
                e.printStackTrace();
            }

            fileMeta = new BorrowCommonImage();
            int index = originalFilename.lastIndexOf(".");
            if (index != -1) {
                fileMeta.setImageName(originalFilename.substring(0, index));
            } else {
                fileMeta.setImageName(originalFilename);
            }

            fileMeta.setImageRealName(fileRealName);
            fileMeta.setImageSize(multipartFile.getSize() / 1024 + "");// KB
            fileMeta.setImageType(multipartFile.getContentType());
            fileMeta.setErrorMessage(errorMessage);
            // 获取文件路径
            fileMeta.setImagePath(fileUploadTempPath + fileRealName);
            fileMeta.setImageSrc(fileDomainUrl + fileUploadTempPath + fileRealName);
            files.add(fileMeta);
        }
        return JSONObject.toJSONString(files, true);
    }
}
