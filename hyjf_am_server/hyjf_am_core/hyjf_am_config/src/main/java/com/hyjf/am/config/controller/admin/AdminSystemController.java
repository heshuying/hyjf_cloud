package com.hyjf.am.config.controller.admin;

import java.util.List;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CouponTenderResponse;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hyjf.am.config.dao.model.customize.AdminSystem;
import com.hyjf.am.config.dao.model.customize.Tree;
import com.hyjf.am.config.service.AdminSystemService;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.response.config.TreeResponse;
import com.hyjf.am.resquest.config.AdminSystemRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.TreeVO;
import com.hyjf.common.security.util.MD5;
import com.hyjf.common.util.CommonUtils;

@RestController
@RequestMapping("/am-config/adminSystem")
public class AdminSystemController extends BaseConfigController {
	@Autowired
	private AdminSystemService adminSystemService;

	/**
	 * 获取该用户菜单
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("/selectLeftMenuTree/{userId}")
	public TreeResponse selectLeftMenuTree2(@PathVariable String userId) {

		TreeResponse adminR = new TreeResponse();
		List<Tree> tree = adminSystemService.selectLeftMenuTree2(userId);
		if (null != tree) {
			List<TreeVO> tvo = CommonUtils.convertBeanList(tree, TreeVO.class);
			adminR.setResultList(tvo);
		}
		return adminR;
	}

	/**
	 * 登陆用户
	 * 
	 * @param AdminSystemRequest
	 * @return
	 */
	@RequestMapping("/getuser")
	public AdminSystemResponse getuser(@RequestBody AdminSystemRequest adminSystemR) {
		AdminSystemResponse asr = new AdminSystemResponse();
		AdminSystem adminSystem = new AdminSystem();
		adminSystem.setUsername(adminSystemR.getUsername());
		adminSystem.setPassword(MD5.toMD5Code(adminSystemR.getPassword()));
		adminSystem.setState("NOT CHECK");
		AdminSystem adminSystemr = adminSystemService.getUserInfo(adminSystem);
		if (adminSystemr != null) {
			AdminSystemVO asv = new AdminSystemVO();
			// 如果状态不可用
			if ("1".equals(adminSystem.getState())) {
				asr.setMessage("该用户已禁用");
				return asr;
			}
			BeanUtils.copyProperties(adminSystemr, asv);
			asr.setResult(asv);
			return asr;
		} else {
			asr.setMessage("用户名或者密码无效");
			return asr;
		}

	}

	/**
	 * 登出
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("/loginOut/{userId}")
	public AdminSystemResponse loginOut(@PathVariable String userId) {

		AdminSystemResponse asr = new AdminSystemResponse();
		return asr;
	}

	/**
	 * 获取该用户权限
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("/getpermissions/{userName}")
	public AdminSystemResponse getpermissions(@PathVariable String userName) {
		AdminSystem adminSystem = new AdminSystem();
		adminSystem.setUsername(userName);
		adminSystem = this.adminSystemService.getUserInfo(adminSystem);
		AdminSystemResponse asr = new AdminSystemResponse();
		List<AdminSystem> permissionsList = this.adminSystemService.getUserPermission(adminSystem);
		List<AdminSystemVO> adminVo = CommonUtils.convertBeanList(permissionsList, AdminSystemVO.class);
		asr.setResultList(adminVo);
		return asr;
	}

	/**
	 * 根据userId查询admin用户信息
	 * @auth sunpeikai
	 * @param userId 用户id
	 * @return response admin用户信息
	 */
	@GetMapping(value = "/get_admin_system_by_userid/{userId}")
	public AdminSystemResponse getAdminSystemByUserId(@PathVariable Integer userId){
		logger.info("userId========{}",userId);
		AdminSystemResponse response = new AdminSystemResponse();
		AdminSystem adminSystem = adminSystemService.getUserInfoByUserId(userId);
		AdminSystemVO adminSystemVO = CommonUtils.convertBean(adminSystem,AdminSystemVO.class);
		response.setResult(adminSystemVO);
		return response;
	}

	/**
	 * 根据userId查询admin用户信息
	 * @auth walter.limeng
	 * @param userId 用户ID
	 * @return response admin用户信息
	 */
	@GetMapping(value = "/hztgetusername/{userId}")
	public CouponTenderResponse getUserNameByUserId(@PathVariable Integer userId){
		logger.info("userId========{}",userId);
		CouponTenderResponse response = new CouponTenderResponse();
		AdminSystem adminSystem = adminSystemService.getUserInfoByUserId(userId);
		response.setAttrbute(adminSystem==null?"":adminSystem.getUsername());
		return response;
	}
	/**
	 * 项目申请人是否存在
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/isexistsapplicant/{applicant}")
	public AdminSystemResponse isExistsApplicant(@PathVariable String applicant) {
		int ea=adminSystemService.isExistsApplicant(applicant);
		AdminSystemResponse response = new AdminSystemResponse();
		if (ea == 0) {
			response.setRtn(Response.FAIL);
		}
		return response;
	}
}
