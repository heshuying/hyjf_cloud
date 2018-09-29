package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.client.AmMarketClient;
import com.hyjf.admin.service.ContentAdsService;
import com.hyjf.am.response.admin.ContentAdsResponse;
import com.hyjf.am.resquest.admin.ContentAdsRequest;
import com.hyjf.am.vo.admin.AdsVO;
import com.hyjf.common.file.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 内容中心 -广告管理
 *
 * @author：yinhui
 * @Date: 2018/7/19  14:22
 */
@Service
public class ContentAdsServiceImpl implements ContentAdsService {

    @Value("${file.domain.url}")
    private String FILEDOMAINURL;
    @Value("${file.physical.path}")
    private String FILEPHYSICALPATH;
    @Value("${file.upload.temp.path}")
    private String FILEUPLOADTEMPPATH;

    @Autowired
    private AmMarketClient amMarketClient;

    @Override
    public ContentAdsResponse searchAction(ContentAdsRequest request) {
        return amMarketClient.searchAction(request);
    }

    @Override
    public ContentAdsResponse inserAction(ContentAdsRequest request) {
        return amMarketClient.inserAction(request);
    }

    @Override
    public ContentAdsResponse infoaction(Integer id) {
        return amMarketClient.infoaction(id);
    }

    @Override
    public ContentAdsResponse updateAction(ContentAdsRequest request) {
        return amMarketClient.updateAction(request);
    }

    @Override
    public ContentAdsResponse statusaction(Integer id) {
        ContentAdsResponse adsresponse = amMarketClient.infoaction(id);
        AdsVO adsVO = adsresponse.getResult().getRecordList().get(0);
        if (adsVO.getStatus() == 1) {
            adsVO.setStatus(0);
        } else {
            adsVO.setStatus(1);
        }
        ContentAdsRequest request = new ContentAdsRequest();
        request.setAds(adsVO);
        ContentAdsResponse response = amMarketClient.updateAction(request);
        return response;
    }

    @Override
    public ContentAdsResponse deleteById(Integer id) {
        return amMarketClient.deleteById(id);
    }

    @Override
    public ContentAdsResponse adsTypeList() {
        return amMarketClient.getAdsTypeList();
    }

    /**
     * 资料上传
     *
     * @return
     * @throws Exception
     */
    @Override
    public LinkedList<BorrowCommonImage> uploadFile(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        String fileDomainUrl = UploadFileUtils.getDoPath(FILEDOMAINURL);
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
            String fileRealName = String.valueOf(new Date().getTime());
            String originalFilename = multipartFile.getOriginalFilename();
            fileRealName = fileRealName + UploadFileUtils.getSuffix(multipartFile.getOriginalFilename());
            // 图片上传
            String errorMessage = UploadFileUtils.upload4Stream(fileRealName, logoRealPathDir, multipartFile.getInputStream(), 5000000L);

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
        return files;
    }
}
