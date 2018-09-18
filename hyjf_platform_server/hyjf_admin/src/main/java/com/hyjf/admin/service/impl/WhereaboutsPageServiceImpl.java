/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.beans.request.WhereaboutsPageRequestBean;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.service.WhereaboutsPageService;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.config.WhereaboutsPageResponse;
import com.hyjf.common.file.UploadFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author tanyy
 * @version WhereaboutsPageServiceImpl, v0.1 2018/7/16 14:14
 */
@Service
public class WhereaboutsPageServiceImpl extends BaseServiceImpl implements WhereaboutsPageService {
	@Autowired
	private AmUserClient amUserClient;

	@Autowired
	SystemConfig systemConfig;

	@Value("${file.domain.url}")
	private String FILEDOMAILURL;
	@Value("${file.physical.path}")
	private String FILEPHYSICALPATH;
	@Value("${file.upload.whereabouts.page.path}")
	private String FILEUPLOADTEMPPATH;
	@Override
	public WhereaboutsPageResponse searchAction(WhereaboutsPageRequestBean requestBean) {
		return amUserClient.searchAction(requestBean);
	}

	@Override
	public WhereaboutsPageResponse insertAction(WhereaboutsPageRequestBean requestBean) {
		return amUserClient.insertAction(requestBean);
	}

	@Override
	public WhereaboutsPageResponse updateAction(WhereaboutsPageRequestBean requestBean) {
		return amUserClient.updateAction(requestBean);
	}
	@Override
	public WhereaboutsPageResponse updateStatus(WhereaboutsPageRequestBean requestBean) {
		return amUserClient.updateStatus(requestBean);
	}

	@Override
	public WhereaboutsPageResponse deleteById(Integer id) {
		return amUserClient.deleteById(id);
	}

	/**
	 * 资料上传
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public  LinkedList<BorrowCommonImage> uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		return files;
	}
	@Override
	public 	WhereaboutsPageResponse getWhereaboutsPageConfigById(WhereaboutsPageRequestBean form){
		form.setDomain(UploadFileUtils.getDoPath(FILEDOMAILURL));
		WhereaboutsPageResponse response = amUserClient.getWhereaboutsPageConfigById(form);
		return response;

	}
    @Override
    public StringResponse checkUtmId(Integer utmId){
        return amUserClient.checkUtmId(utmId);
    }
    @Override
    public StringResponse checkReferrer(String referrer){
        return amUserClient.checkReferrer(referrer);
    }
}
