/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.content;

import com.hyjf.admin.beans.request.EventRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.EventService;
import com.hyjf.am.bean.commonimage.BorrowCommonImage;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.EventResponse;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.EventVO;
import com.hyjf.common.file.UploadFileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 公司管理-公司记事
 * 
 * @author fuqiang
 * @version EventController, v0.1 2018/7/11 17:20
 */
@Api(tags = "公司管理-公司记事")
@RestController
@RequestMapping("/hyjf-admin/content/contentevent")
public class EventController extends BaseController {
	@Value("${file.domain.url}")
	private String url;
	@Value("${file.physical.path}")
	private String physical;
	@Value("${file.upload.temp.path}")
	private String temppath;

	/** 权限关键字 */
	public static final String PERMISSIONS = "contentevents";

	@Autowired
	private EventService eventService;

	@ApiOperation(value = "公司管理-公司记事条件列表查询", notes = "公司管理-公司记事条件列表查询")
	@PostMapping("/searchaction")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult searchAction(@RequestBody EventRequestBean requestBean) {
		EventResponse response = eventService.searchAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult(response);
	}

	@ApiOperation(value = "添加公司管理-公司记事", notes = "添加公司管理-公司记事")
	@PostMapping("/insert")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
	public AdminResult add(@RequestBody EventRequestBean requestBean, HttpServletRequest request) {
		AdminSystemVO adminUser = getUser(request);
		requestBean.setAddAdmin(adminUser.getUsername());
		int num = eventService.insertAction(requestBean);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "修改公司管理-公司记事", notes = "修改公司管理-公司记事")
	@PostMapping("/update")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
	public AdminResult update(@RequestBody EventRequestBean requestBean, HttpServletRequest request) {
		AdminSystemVO adminUser = getUser(request);
		requestBean.setAddAdmin(adminUser.getUsername());
		int num = eventService.updateAction(requestBean);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "修改公司管理-公司记事", notes = "修改公司管理-公司记事")
	@PostMapping("/updatestatus")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
	public AdminResult updatestatus(@RequestBody EventRequestBean requestBean, HttpServletRequest request) {
		AdminSystemVO adminUser = getUser(request);
		requestBean.setAddAdmin(adminUser.getUsername());
		int num = eventService.updateStatus(requestBean);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "删除公司管理-公司记事", notes = "删除公司管理-公司记事")
	@GetMapping("/delete/{id}")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
	public AdminResult updatestatus(@PathVariable Integer id) {
		int num = eventService.deleteById(id);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "公司管理-公司记事初始化", notes = "公司管理-公司记事初始化")
	@PostMapping("/select_by_id")
	@AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD, ShiroConstants.PERMISSION_MODIFY})
	public AdminResult selectById(@RequestBody EventRequestBean requestBean) {
		EventVO vo = eventService.selectById(requestBean);
		return new AdminResult(vo);
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

		try {
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
		} catch (Exception e) {
			return new AdminResult<>(FAIL, "上传图片失败");
		}
	}
}
