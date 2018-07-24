/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.ContentLandingPageRequestBean;
import com.hyjf.admin.beans.request.WhereaboutsPageRequestBean;
import com.hyjf.admin.client.ContentLandingPageClient;
import com.hyjf.admin.client.WhereaboutsPageClient;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.service.ContentLandingPageService;
import com.hyjf.admin.service.WhereaboutsPageService;
import com.hyjf.am.response.admin.CouponCheckResponse;
import com.hyjf.am.response.config.LandingPageResponse;
import com.hyjf.am.response.config.WhereaboutsPageResponse;
import com.hyjf.am.response.config.WhereaboutsPictureResponse;
import com.hyjf.am.resquest.admin.AdminCouponCheckRequest;
import com.hyjf.am.vo.config.WhereaboutsPagePictureVo;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author tanyy
 * @version WhereaboutsPageServiceImpl, v0.1 2018/7/16 14:14
 */
@Service
public class WhereaboutsPageServiceImpl implements WhereaboutsPageService {
	@Autowired
	private WhereaboutsPageClient whereaboutsPageClient;

	@Autowired
	SystemConfig systemConfig;
	@Value("${file.upload.activity.img.path}")
	private String FILEUPLOADTEMPPATH;
	@Override
	public WhereaboutsPageResponse searchAction(WhereaboutsPageRequestBean requestBean) {
		return whereaboutsPageClient.searchAction(requestBean);
	}

	@Override
	public WhereaboutsPageResponse insertAction(WhereaboutsPageRequestBean requestBean) {
		return whereaboutsPageClient.insertAction(requestBean);
	}

	@Override
	public WhereaboutsPageResponse updateAction(WhereaboutsPageRequestBean requestBean) {
		return whereaboutsPageClient.updateAction(requestBean);
	}
	@Override
	public WhereaboutsPageResponse updateStatus(WhereaboutsPageRequestBean requestBean) {
		return whereaboutsPageClient.updateStatus(requestBean);
	}

	@Override
	public WhereaboutsPageResponse deleteById(Integer id) {
		return whereaboutsPageClient.deleteById(id);
	}

	/**
	 * 资料上传
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public WhereaboutsPictureResponse uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		WhereaboutsPictureResponse checkResponse = new WhereaboutsPictureResponse();
		String errorMessage = "";
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
		String filePhysicalPath = UploadFileUtils.getDoPath(FILEUPLOADTEMPPATH);
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String today = format.format(date);

		String logoRealPathDir = filePhysicalPath + today;
		File logoSaveFile = new File(logoRealPathDir);
		if (!logoSaveFile.exists()) {
			logoSaveFile.mkdirs();
		}
		Iterator<String> itr = multipartRequest.getFileNames();
		MultipartFile multipartFile = null;
		WhereaboutsPagePictureVo fileMeta = null;
		LinkedList<WhereaboutsPagePictureVo> files = new LinkedList<WhereaboutsPagePictureVo>();
		while (itr.hasNext()) {
			multipartFile = multipartRequest.getFile(itr.next());
			String fileRealName = String.valueOf(System.currentTimeMillis() / 1000);
			String originalFilename = multipartFile.getOriginalFilename();
			String suffix = UploadFileUtils.getSuffix(multipartFile.getOriginalFilename());
			fileRealName = fileRealName + suffix;
				try {
					errorMessage = UploadFileUtils.upload4Stream(fileRealName, logoRealPathDir, multipartFile.getInputStream(), 5000000L);
				} catch (Exception e) {
					e.printStackTrace();
				}
			fileMeta = new WhereaboutsPagePictureVo();
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
			//
			fileMeta.setImagePath(logoRealPathDir + "/" + fileRealName);
			//fileMeta.setImageSrc(fileDomainUrl + fileUploadTempPath + fileRealName);
			files.add(fileMeta);

		}
		checkResponse.setResultList(files);
		return checkResponse;
	}
}
