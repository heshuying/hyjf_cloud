/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.BankSettingClient;
import com.hyjf.admin.service.BankSettingService;
import com.hyjf.am.response.admin.AdminBankSettingResponse;
import com.hyjf.am.resquest.admin.AdminBankSettingRequest;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.common.file.UploadFileUtils;
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
 * @author dangzw
 * @version BankSettingServiceImpl, v0.1 2018/7/24 22:53
 */
@Service
public class BankSettingServiceImpl implements BankSettingService {

    @Autowired
    private AmConfigClient amConfigClient;

    @Value("${file.domain.url}")
    private String FILEDOMAILURL;
    @Value("${file.physical.path}")
    private String FILEPHYSICALPATH;
    @Value("${file.upload.activity.img.path}")
    private String FILEUPLOADTEMPPATH;

    /**
     *（条件）列表查询
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse selectBankSettingList(AdminBankSettingRequest request) {
        return amConfigClient.selectBankSettingList(request);
    }

    /**
     * 画面迁移(含有id更新，不含有id添加)
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse getRecord(AdminBankSettingRequest request) {
        return amConfigClient.getRecord(request);
    }

    /**
     * （条件）数据查询
     * @param bank
     * @param limitStart
     * @param limitEnd
     * @return
     */
    @Override
    public List<JxBankConfigVO> getRecordList(JxBankConfigVO bank, int limitStart, int limitEnd) {
        return amConfigClient.getRecordList(bank, limitStart, limitEnd);
    }

    /**
     * 添加
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse insertRecord(AdminBankSettingRequest request) {
        return amConfigClient.insertRecord(request);
    }

    /**
     * 修改 江西银行 银行卡配置
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse updateRecord(AdminBankSettingRequest request) {
        return amConfigClient.updateRecord(request);
    }

    /**
     * 删除 江西银行 银行卡配置
     * @param request
     * @return
     */
    @Override
    public AdminBankSettingResponse deleteRecord(AdminBankSettingRequest request) {
        return amConfigClient.deleteRecord(request);
    }

    /**
     * 江西银行 资料上传
     * @param request
     * @return
     */
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
