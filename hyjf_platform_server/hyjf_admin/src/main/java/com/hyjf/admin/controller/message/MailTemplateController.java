/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.message;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.MailTemplateService;
import com.hyjf.am.bean.commonimage.BorrowCommonImage;
import com.hyjf.am.response.config.SmsMailTemplateResponse;
import com.hyjf.am.resquest.config.MailTemplateRequest;
import com.hyjf.am.vo.config.SmsMailTemplateVO;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author fuqiang
 * @version MailTemplateController, v0.1 2018/6/25 14:46
 */
@Api(tags = "消息中心-邮件模板")
@RestController
@RequestMapping("/hyjf-admin/mail/template")
public class MailTemplateController extends BaseController {
	@Value("${file.domain.url}")
	private String url;
	@Value("${file.physical.path}")
	private String physical;
	@Value("${file.upload.temp.path}")
	private String temppath;

	/** 权限关键字 */
	public static final String PERMISSIONS = "mailtemplate";

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MailTemplateService mailTemplateService;

	/**
	 * 查询所有邮件配置模板
	 *
	 * @return
	 */
	@ApiOperation(value = "查询所有邮件配置模板", notes = "查询所有邮件配置模板")
	@GetMapping("/find_all")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<ListResult<SmsMailTemplateVO>> findAll() {
		List<SmsMailTemplateVO> voList = mailTemplateService.findAll();
		if (CollectionUtils.isEmpty(voList)) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<ListResult<SmsMailTemplateVO>>(ListResult.build(voList, voList.size()));
	}

	/**
	 * 根据条件查询邮件配置模板
	 *
	 * @return
	 */
	@ApiOperation(value = "根据条件查询邮件配置模板", notes = "根据条件查询邮件配置模板")
	@PostMapping("/find_mail_template")
	public AdminResult<ListResult<SmsMailTemplateVO>> findMailTemplate(@RequestBody MailTemplateRequest request) {
		SmsMailTemplateResponse response = mailTemplateService.findMailTemplate(request);
		return new AdminResult(response);
	}

	@ApiOperation(value = "模板详情", notes = "模板详情")
	@PostMapping("/infoAction")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
	public AdminResult infoAction(@RequestBody MailTemplateRequest request) {
		SmsMailTemplateVO vo = mailTemplateService.infoAction(request);
		return new AdminResult(vo);
	}

	/**
	 * 新增邮件模板
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "新增邮件模板", notes = "新增邮件模板")
	@PostMapping("/insert_mail_template")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
	public JSONObject insertMailTemplate(@RequestBody MailTemplateRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			//modify by cwyang 2019-2-21 增加空值校验
			boolean result = checkParam(jsonObject, request);
			if(!result){
				jsonObject.put("status", "99");
				return jsonObject;
			}
			int sum = mailTemplateService.insertMailTemplate(request);
			if (sum > 0) {
				jsonObject.put("status", "000");
				jsonObject.put("statusDesc", "添加邮件模板成功");
				return jsonObject;
			} else {
				jsonObject.put("status", "99");
				jsonObject.put("statusDesc", "添加邮件模板失败");
				return jsonObject;
			}
		} catch (Exception e) {
		    logger.error("添加邮件模板失败......", e);
            jsonObject.put("status", "99");
            jsonObject.put("statusDesc", "添加邮件模板失败");
            return jsonObject;
		}
	}


	/**
	 * modify by cwyang 2019-2-21 增加空值校验
	 * @param jsonObject
	 * @param request
	 * @return
	 */
	private boolean checkParam(JSONObject jsonObject, MailTemplateRequest request) {

		if(request !=  null){
			if(StringUtils.isBlank(request.getMailName())){
				jsonObject.put("statusDesc","请输入有效的模板名称！");
				return false;
			}
			if(StringUtils.isBlank(request.getMailValue())){
				jsonObject.put("statusDesc","请输入有效的模板标识！");
				return false;
			}
			if("<p></p>".equals(request.getMailContent().replace(" ",""))){
				jsonObject.put("statusDesc","请输入有效的模板内容！");
				return false;
			}
			return true;
		}

		return false;
	}

	/**
	 * 修改邮件模板
	 *
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "修改邮件模板", notes = "修改邮件模板")
	@PostMapping("/update_mailTemplate")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
	public JSONObject updateMailTemplate(@RequestBody MailTemplateRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			//modify by cwyang 2019-2-21 增加空值校验
			boolean result = checkParam(jsonObject, request);
			if(!result){
				jsonObject.put("status", "99");
				jsonObject.put("statusDesc", jsonObject.get("statusDesc"));
				return jsonObject;
			}
			int num = mailTemplateService.updateMailTemplate(request);
			if (num > 0) {
				jsonObject.put("status", "000");
				jsonObject.put("statusDesc", "修改邮件模板成功");
				return jsonObject;
			} else {
				jsonObject.put("status", "99");
				jsonObject.put("statusDesc", "修改邮件模板失败");
				return jsonObject;
			}
		} catch (Exception e) {
			logger.error("修改邮件模板失败......", e);
			jsonObject.put("status", "99");
			jsonObject.put("statusDesc", "修改邮件模板失败");
			return jsonObject;
		}
	}

	@ApiOperation(value = "开关闭模板", notes = "开关闭模板")
	@PostMapping("/open_action")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
	public JSONObject openAction(@RequestBody MailTemplateRequest request) {
		JSONObject jsonObject = new JSONObject();
		try {
			int sum = mailTemplateService.updateStatus(request);
			if (sum > 0) {
				jsonObject.put("status", "000");
				jsonObject.put("statusDesc", "开关闭模板成功");
				return jsonObject;
			} else {
				jsonObject.put("status", "99");
				jsonObject.put("statusDesc", "开关闭模板失败");
				return jsonObject;
			}
		} catch (Exception e) {
			logger.error("添加邮件模板失败......", e);
			jsonObject.put("status", "99");
			jsonObject.put("statusDesc", "开关闭模板失败");
			return jsonObject;
		}
	}

	/**
	 * 资料上传
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "资料上传", notes = "资料上传")
	@PostMapping("/uploadFile")
	@AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD, ShiroConstants.PERMISSION_MODIFY})
	public AdminResult<LinkedList<BorrowCommonImage>> uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		String fileDomainUrl = UploadFileUtils.getDoPath(url);
		String filePhysicalPath = UploadFileUtils.getDoPath(physical);
		String fileUploadTempPath = UploadFileUtils.getDoPath(temppath);
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
			fileMeta.setImageSize(multipartFile.getSize() / 1024 + "");// KB
			fileMeta.setImageType(multipartFile.getContentType());
			fileMeta.setErrorMessage(errorMessage);
			// 获取文件路径
			fileMeta.setImagePath(fileUploadTempPath + fileRealName);
			fileMeta.setImageSrc(fileDomainUrl + fileUploadTempPath + fileRealName);
			files.add(fileMeta);
		}
		return new AdminResult<LinkedList<BorrowCommonImage>>(files);
	}
}
