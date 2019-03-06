package com.hyjf.am.user.controller.admin.membercentre;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.AdminPreRegistListResponse;
import com.hyjf.am.resquest.user.AdminPreRegistListRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.customize.AdminPreRegistListCustomize;
import com.hyjf.am.user.service.admin.membercentre.PreRegistService;
import com.hyjf.am.vo.user.AdminPreRegistListVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * admin预注册记录
 * @author dongzeeshan
 */
@RestController
@RequestMapping("/am-user/preregist")
public class PreRegistController extends BaseController {

	@Autowired
	private PreRegistService preRegistService;

	/**
	 * 权限维护画面初始化
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	 @RequestMapping("/preregistlist")
	public AdminPreRegistListResponse init(@RequestBody  AdminPreRegistListRequest adminPreRegistListRequest) {
		return this.createPage(adminPreRegistListRequest);
	}

	/**
	 * 创建权限维护分页机能
	 * 
	 * @param request
	 * @param modelAndView
	 * @param form
	 */
	private AdminPreRegistListResponse createPage(AdminPreRegistListRequest adminPreRegistListRequest) {

		Map<String, Object> registUser = new HashMap<String, Object>();
		registUser.put("mobile", adminPreRegistListRequest.getMobile());
		registUser.put("referrer", adminPreRegistListRequest.getReferrer());
		registUser.put("source", adminPreRegistListRequest.getSource());
		registUser.put("preRegistTime", adminPreRegistListRequest.getPreRegistTime());
		registUser.put("registFlag", adminPreRegistListRequest.getRegistFlag());
		registUser.put("registTime", adminPreRegistListRequest.getRegistTime());
		registUser.put("platformId", adminPreRegistListRequest.getPlatformId());
		registUser.put("platformName", adminPreRegistListRequest.getPlatformName());
		registUser.put("remark", adminPreRegistListRequest.getRemark());
		registUser.put("createTime", adminPreRegistListRequest.getCreateTime());
		registUser.put("updateTime", adminPreRegistListRequest.getUpdateTime());
		registUser.put("updateBy", adminPreRegistListRequest.getUpdateBy());
		registUser.put("preRegTimeStart", StringUtils.isNotBlank(adminPreRegistListRequest.getPreRegTimeStart())?adminPreRegistListRequest.getPreRegTimeStart():null);
		registUser.put("preRegTimeEnd", StringUtils.isNotBlank(adminPreRegistListRequest.getPreRegTimeEnd())?adminPreRegistListRequest.getPreRegTimeEnd():null);
		AdminPreRegistListResponse aprlr=new AdminPreRegistListResponse();
		int recordTotal = this.preRegistService.countRecordTotal(registUser);

		if (recordTotal > 0) {
			Paginator paginator = new Paginator(adminPreRegistListRequest.getCurrPage(),recordTotal,adminPreRegistListRequest.getPageSize());
			List<AdminPreRegistListCustomize> recordList = this.preRegistService.getRecordList(registUser, paginator.getOffset(), paginator.getLimit());
			if (recordList != null) {
				List<AdminPreRegistListVO> avo = CommonUtils.convertBeanList(recordList,AdminPreRegistListVO.class);
				aprlr.setResultList(avo);
				aprlr.setRecordTotal(recordTotal);
				aprlr.setRtn(Response.SUCCESS);
			}
			return aprlr;
		}
		return new AdminPreRegistListResponse();
	}

	/**
	 * 预注册用户编辑页面跳转
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping("/updatepreregistlist")
	public AdminPreRegistListResponse updatePreRegistList(@RequestBody  AdminPreRegistListRequest adminPreRegistListRequest) {
		AdminPreRegistListResponse arpr=new AdminPreRegistListResponse();
		if (!StringUtils.isEmpty(adminPreRegistListRequest.getId())) {
			AdminPreRegistListCustomize preRegist = this.preRegistService.getPreRegist(Integer.parseInt(adminPreRegistListRequest.getId()));
			if (preRegist != null) {
				AdminPreRegistListVO avo=new AdminPreRegistListVO();
				BeanUtils.copyProperties(preRegist, avo);
				arpr.setResult(avo);
			}
			return arpr;
		} else {
			return arpr;
		}

	}

	/**
	 * 预注册用户编辑保存
	 * 
	 * @param request
	 * @param form
	 * @return
	 */
	@RequestMapping("/savepreregistlist")
	public AdminPreRegistListResponse savePreRegistList(@RequestBody  AdminPreRegistListRequest adminPreRegistListRequest) {
		AdminPreRegistListResponse apr =new AdminPreRegistListResponse();
		try {
			if (!StringUtils.isEmpty(adminPreRegistListRequest.getId())) {
				 AdminPreRegistListCustomize aprlr=new AdminPreRegistListCustomize();
				BeanUtils.copyProperties(adminPreRegistListRequest,aprlr);
				Map<String, Object> resultMap = this.preRegistService.savePreRegist(aprlr);
				if ((Boolean) resultMap.get("success")) {
					return apr;
				} else {
					apr.setRtn("failed");
					apr.setMessage((String)resultMap.get("msg"));
					return apr;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		apr.setMessage("校验失败");
		return apr;
	}

}
