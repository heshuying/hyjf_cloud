/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.content;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.ContentPartnerRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.ContentPartnerService;
import com.hyjf.admin.utils.FileUpLoadUtil;
import com.hyjf.am.bean.commonimage.BorrowCommonImage;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CategoryResponse;
import com.hyjf.am.response.config.LinkResponse;
import com.hyjf.am.vo.admin.CategoryVO;
import com.hyjf.am.vo.config.LinkVO;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 合作伙伴type为2
 * 
 * @author fuqiang
 * @version ContentPartnerController, v0.1 2018/7/12 9:52
 */
@Api(tags = "公司管理-合作伙伴")
@RestController
@RequestMapping("/hyjf-admin/content/contentpartner")
public class ContentPartnerController extends BaseController {
	@Value("${file.domain.url}")
	private String url;
	@Value("${file.physical.path}")
	private String physical;
	@Value("${file.upload.temp.path}")
	private String temppath;
	@Autowired
	private FileUpLoadUtil fileUpLoadUtil;

	/** 权限关键字 */
	public static final String PERMISSIONS = "contentpartner";

	@Autowired
	private ContentPartnerService contentPartnerService;

	@ApiOperation(value = "公司管理-合作伙伴列表查询", notes = "公司管理-合作伙伴列表查询")
	@PostMapping("/searchaction")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult searchAction(@RequestBody ContentPartnerRequestBean requestBean) {
		LinkResponse response = contentPartnerService.searchAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		List<LinkVO> resultList = response.getResultList();
		for (LinkVO vo : resultList) {
			Integer id = vo.getPartnerType();
			switch (id) {
				case 11:
					vo.setPartnerName("法律支持");
					continue;
				case 7:
					vo.setPartnerName("金融机构");
					continue;
				case 12:
					vo.setPartnerName("其他");
					continue;
				case 10:
					vo.setPartnerName("服务支持");
					continue;
				case 8:
					vo.setPartnerName("联系我们");
					continue;
			}
		}
		return new AdminResult(response);
	}

	@ApiOperation(value = "添加公司管理-合作伙伴", notes = "添加公司管理-合作伙伴")
	@PostMapping("/insert")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
	public AdminResult add(@RequestBody ContentPartnerRequestBean requestBean) {
		int num = 0;
		try {
			num = contentPartnerService.insertAction(requestBean);
		} catch (Exception e) {
			logger.error("添加公司管理-合作伙伴失败......", e);
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "修改公司管理-合作伙伴", notes = "修改公司管理-合作伙伴")
	@PostMapping("/update")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
	public AdminResult update(@RequestBody ContentPartnerRequestBean requestBean) {
		int num = contentPartnerService.updateAction(requestBean);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "修改公司管理-合作伙伴", notes = "修改公司管理-合作伙伴")
	@PostMapping("/updatestatus")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
	public AdminResult updatestatus(@RequestBody ContentPartnerRequestBean requestBean) {
		int num = contentPartnerService.updateStatus(requestBean);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "删除公司管理-合作伙伴", notes = "删除公司管理-合作伙伴")
	@GetMapping("/delete/{id}")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
	public AdminResult delete(@PathVariable Integer id) {
		int num = contentPartnerService.deleteById(id);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "获得伙伴类别", notes = "获得伙伴类别")
	@GetMapping("/getcategory")
	private AdminResult putCategoryVO() {
		List<CategoryVO> CategoryVOList = new ArrayList<CategoryVO>();
		{
			CategoryVO CategoryVO = new CategoryVO();
			CategoryVO.setId(11);
			CategoryVO.setTitle("法律支持");
			CategoryVOList.add(CategoryVO);
		}
		{
			CategoryVO CategoryVO = new CategoryVO();
			CategoryVO.setId(7);
			CategoryVO.setTitle("金融机构");
			CategoryVOList.add(CategoryVO);
		}
		{
			CategoryVO CategoryVO = new CategoryVO();
			CategoryVO.setId(12);
			CategoryVO.setTitle("其他");
			CategoryVOList.add(CategoryVO);
		}
		{
			CategoryVO CategoryVO = new CategoryVO();
			CategoryVO.setId(10);
			CategoryVO.setTitle("服务支持");
			CategoryVOList.add(CategoryVO);
		}
		{
			CategoryVO CategoryVO = new CategoryVO();
			CategoryVO.setId(8);
			CategoryVO.setTitle("联系我们");
			CategoryVOList.add(CategoryVO);
		}
		CategoryResponse response = new CategoryResponse();
			response.setResultList(CategoryVOList);
		return new AdminResult(response);
	}

	@ApiOperation(value = "公司管理-合作伙伴初始化", notes = "公司管理-合作伙伴初始化")
	@PostMapping("/select_by_id")
	@AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD, ShiroConstants.PERMISSION_MODIFY})
	public AdminResult selectById(@RequestBody ContentPartnerRequestBean requestBean) {
		LinkVO vo = contentPartnerService.selectById(requestBean);
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
	public JSONObject uploadFile(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("statusDesc", SUCCESS_DESC);
		jsonObject.put("status", SUCCESS);
		jsonObject.put("data", fileUpLoadUtil.upLoad(request));
		return jsonObject;
	}
}
