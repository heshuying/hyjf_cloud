package com.hyjf.admin.utils;

import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.common.file.UploadFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author xiasq
 * @version FileUpLoadUtil, v0.1 2018/9/27 14:17
 */
@Component
public class FileUpLoadUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUpLoadUtil.class);
    @Value("${file.domain.url}")
    private String DOMAIN_URL;

    @Value("${file.physical.path}")
    private String PHYSICAL_PATH;

    @Value("${file.upload.temp.path}")
    private String TEMP_PATH;


    public LinkedList<BorrowCommonImage> upLoad(HttpServletRequest request) {
        BorrowCommonImage fileMeta = null;
        LinkedList<BorrowCommonImage> files = new LinkedList<>();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        String logoRealPathDir = PHYSICAL_PATH + TEMP_PATH;

        File logoSaveFile = new File(logoRealPathDir);
        if (!logoSaveFile.exists()) {
            logoSaveFile.mkdirs();
        }
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
                logger.error(e.getMessage());
            }

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
            fileMeta.setImagePath(TEMP_PATH + fileRealName);
            fileMeta.setImageSrc(DOMAIN_URL + TEMP_PATH + fileRealName);
            files.add(fileMeta);
        }

        return files;
    }


}
