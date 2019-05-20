package com.hyjf.am.config.controller.admin;

import com.hyjf.am.bean.admin.LockedConfig;
import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.Admin;
import com.hyjf.am.config.dao.model.auto.AdminAndRole;
import com.hyjf.am.config.dao.model.auto.AdminRole;
import com.hyjf.am.config.dao.model.auto.ConfigApplicant;
import com.hyjf.am.config.dao.model.customize.AdminSystem;
import com.hyjf.am.config.dao.model.customize.Tree;
import com.hyjf.am.config.service.AdminRoleService;
import com.hyjf.am.config.service.AdminSystemService;
import com.hyjf.am.market.dao.model.auto.Ads;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CouponTenderResponse;
import com.hyjf.am.response.config.AdminSystemResponse;
import com.hyjf.am.response.config.ConfigApplicantResponse;
import com.hyjf.am.response.config.TreeResponse;
import com.hyjf.am.resquest.config.AdminSystemRequest;
import com.hyjf.am.resquest.config.ConfigApplicantRequest;
import com.hyjf.am.user.controller.admin.locked.LockedConfigManager;
import com.hyjf.am.user.service.admin.locked.LockedUserService;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.ConfigApplicantVO;
import com.hyjf.am.vo.config.TreeVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.calculate.DateUtils;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/am-config/adminSystem")
public class AdminSystemController extends BaseConfigController {
	@Autowired
	private AdminSystemService adminSystemService;
	@Autowired
	private LockedUserService lockedUserService;
	@Autowired
	private AdminRoleService adminRoleService;

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
	 * @param adminSystemR
	 * @return
	 */
	@RequestMapping("/getuser")
	public AdminSystemResponse getuser(@RequestBody AdminSystemRequest adminSystemR) {
		AdminSystemResponse asr = new AdminSystemResponse();
		AdminSystem adminSystem = new AdminSystem();
		adminSystem.setUsername(adminSystemR.getUsername());
		adminSystem.setPassword(adminSystemR.getPassword());
		AdminSystem adminSystemr = adminSystemService.getUserInfo(adminSystem);
		if (adminSystemr != null) {
			AdminSystemVO asv = new AdminSystemVO();
			// 如果状态不可用
			if ("1".equals(adminSystem.getState())) {
				asr.setMessage("该用户已禁用");
				asr.setRtn(Response.ERROR);
				return asr;
			}
			//1.获取该用户密码错误次数
			String passwordErrorNum=RedisUtils.get(RedisConstants.PASSWORD_ERR_COUNT_ADMIN + adminSystemr.getUsername());
			//2.获取用户允许输入的最大错误次数
			Integer maxLoginErrorNum=LockedConfigManager.getInstance().getAdminConfig().getMaxLoginErrorNum();
			//3.redis配置的超限有效时间
			long retTime  = RedisUtils.ttl(RedisConstants.PASSWORD_ERR_COUNT_ADMIN + adminSystemr.getUsername());
			//判断密码错误次数是否超限
			if (!StringUtils.isEmpty(passwordErrorNum)&&Integer.parseInt(passwordErrorNum)>=maxLoginErrorNum) {
				asr.setMessage("您的登录失败次数超限，请"+DateUtils.SToHMSStr(retTime)+"之后重试!");
				asr.setRtn(Response.ERROR);
				return asr;
			}
			BeanUtils.copyProperties(adminSystemr, asv);
			asr.setResult(asv);
			return asr;
		} else {
			AdminSystem adminSystem1 = new AdminSystem();
			adminSystem1.setUsername(adminSystemR.getUsername());
			Admin admin = adminSystemService.getUserInfoAll(adminSystem1);
			if (admin!=null){
				Integer id = admin.getId();
				AdminAndRole adminAndRole = adminRoleService.getRole(id);
				// 是否禁用
				if ("1".equals(adminSystem.getState())) {
					asr.setMessage("该用户已禁用");
					asr.setRtn(Response.ERROR);
					return asr;
				}
				if (adminAndRole != null) {
					AdminRole role = adminRoleService.getRecord(Integer.valueOf(adminAndRole.getRoleId()));
					if(role.getStatus()!=0) {
						asr.setRtn(Response.ERROR);
						asr.setMessage("该用户角色状态异常");
						return asr;
					}
				}
				//判断用户输入的密码错误次数---开始
				Map<String, String> errorInfo=lockedUserService.insertErrorPassword(adminSystemR.getUsername(),adminSystemR.getPassword(),admin);
				if (!errorInfo.isEmpty()){
					asr.setMessage(errorInfo.get("info"));
					asr.setRtn(Response.ERROR);
					return asr;
				}
				//判断用户输入的密码错误次数---结束


			}

			asr.setRtn(Response.ERROR);
			asr.setMessage("用户名或者密码无效");
			return asr;
		}

	}
	/**
	 * 获取该用户权限
	 * 
	 * @param userName
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
	 * @param applicant
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
    @RequestMapping("/updatePasswordAction")
    public AdminSystemResponse updatePasswordAction(@RequestBody AdminSystemRequest adminSystemR) {
    	AdminSystemResponse response = new AdminSystemResponse();
    	if(adminSystemR.getId()!=null&&adminSystemR.getId().equals("1")) {
    		AdminSystem adminSystem = new AdminSystem();
    		adminSystem.setUsername(adminSystemR.getUsername());
    		adminSystem.setPassword(adminSystemR.getOldPassword());
    		AdminSystem adminSystemr = adminSystemService.getUserInfo(adminSystem);
                if (adminSystemr == null) {
                	response.setRtn(Response.FAIL);
                	response.setMessage("旧密码不正确");
                }
                return response;
    	}
        if (Validator.isNull(adminSystemR.getPassword()) && Validator.isNull(adminSystemR.getOldPassword())) {
        	response.setRtn(Response.FAIL);
        	response.setMessage("新密码和旧密码都不能为空！");
        } else {
//            if (Validator.isNumber(adminSystemR.getPassword()) || adminSystemR.getPassword().length() < 8 || adminSystemR.getPassword().length() > 16) {
//            	response.setRtn(Response.FAIL);
//            	response.setMessage("密码8-16位，必须有数字字母组成，区分大小写");
//            } else {
        		AdminSystem adminSystem = new AdminSystem();
        		adminSystem.setUsername(adminSystemR.getUsername());
        		adminSystem.setPassword(adminSystemR.getOldPassword());
        		AdminSystem adminSystemr = adminSystemService.getUserInfo(adminSystem);
                    if (adminSystemr == null) {
                    	response.setRtn(Response.FAIL);
                    	response.setMessage("旧密码不正确");
                    }

                    adminSystemService.updatePassword(adminSystemR.getUsername(),adminSystemR.getPassword());

            //}
        }
        return response;
    }


	/**
	 * 修改项目申请人配置
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/updateApplicantConfigList")
	public ConfigApplicantResponse updateApplicantConfigList(@RequestBody ConfigApplicantRequest request) {
		int result=adminSystemService.updateApplicantConfigList(request);
		ConfigApplicantResponse response = new ConfigApplicantResponse();
		if (result == 0) {
			response.setRtn(Response.FAIL);
		}
		return response;
	}

	/**
	 * 添加项目申请人配置
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/addApplicantConfigList")
	public ConfigApplicantResponse addApplicantConfigList(@RequestBody ConfigApplicantRequest request) {
		Integer result=adminSystemService.addApplicantConfigList(request);
		ConfigApplicantResponse response = new ConfigApplicantResponse();
		if (result == 0) {
			response.setRtn(Response.FAIL);
		}
		return response;
	}

	/**
	 * 获取项目申请人配置列表
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/getApplicantConfigList")
	public ConfigApplicantResponse getApplicantConfigList(@RequestBody ConfigApplicantRequest request) {
		ConfigApplicantResponse response = new ConfigApplicantResponse();
		Integer count=adminSystemService.countApplicantConfigList(request);
		if (count > 0) {
			Paginator paginator = new Paginator(request.getCurrPage(), count,request.getPageSize());
			List<ConfigApplicant> applicantConfigList = adminSystemService.getApplicantConfigList(request, paginator.getOffset(), paginator.getLimit());
			List<ConfigApplicantVO> list = CommonUtils.convertBeanList(applicantConfigList, ConfigApplicantVO.class);
			response.setResultList(list);

		}
		response.setCount(count);
		return response;
	}

	/**
	 * 获取项目申请人配置详情
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/findConfigApplicant")
	public ConfigApplicantResponse findConfigApplicant(@RequestBody ConfigApplicantRequest request) {
		ConfigApplicant configApplicant = adminSystemService.findConfigApplicant(request);
		ConfigApplicantResponse response = new ConfigApplicantResponse();
		if (configApplicant == null) {
			response.setRtn(Response.FAIL);
		}else {
			response.setResult(CommonUtils.convertBean(configApplicant, ConfigApplicantVO.class));
		}
		return response;
	}
}
