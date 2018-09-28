/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.extensioncenter;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AdminUtmReadPermissionsService;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.AdminUtmReadPermissionsResponse;
import com.hyjf.am.response.user.UtmPlatResponse;
import com.hyjf.am.resquest.config.AdminUtmReadPermissionsRequest;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tanyy
 * @version AdminUtmReadPermissionsController, v0.1 2018/7/18 16:03
 */
@Api(tags ="推广中心-渠道帐号管理")
@RestController
@RequestMapping("/hyjf-admin/adminutmreadpermissions")
public class AdminUtmReadPermissionsController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(AdminUtmReadPermissionsController.class);
	@Resource
	private AdminUtmReadPermissionsService adminUtmReadPermissionsService;

	@ApiOperation(value = "渠道帐号管理", notes = "渠道帐号管理列表")
	@PostMapping("/searchaction")
	public AdminResult searchAction(@RequestBody AdminUtmReadPermissionsRequest request) {
		logger.info("渠道帐号管理查询开始......");
		AdminUtmReadPermissionsResponse response = adminUtmReadPermissionsService.searchAction(request);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		UtmPlatResponse utmPlatResponse = adminUtmReadPermissionsService.getUtmPlatList();
		List<UtmPlatVO> umtList  = utmPlatResponse.getResultList();
		List<AdminUtmReadPermissionsVO> list = response.getResultList();
		//数据库一个字段存放的是id  1,2,3,4 得手动转换为汉字以前在页面现在放在后台。
		for(AdminUtmReadPermissionsVO adminUtmReadPermissionsVO:list){
			String umtIds = adminUtmReadPermissionsVO.getUtmIds();
			if(StringUtils.isNotBlank(umtIds)) {
				String umtNames = "";
				String[] array = umtIds.split(",");
				for (int j = 0; j < array.length; j++) {
					String id = array[j];
					for (int k = 0; k < umtList.size(); k++) {
						if (Integer.valueOf(id).equals(umtList.get(k).getSourceId())) {
							umtNames +=","+umtList.get(k).getSourceName();
						}
					}
				}
				adminUtmReadPermissionsVO.setNames(umtNames.substring(1));
			}
		}
		response.setResultList(list);
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));

	}


	@ApiOperation(value = "检查编号唯一性", notes = "检查编号唯一性")
	@GetMapping ("/checkaction/{userName}")
	public IntegerResponse checkAction(@PathVariable String userName) {
		logger.info("检查编号唯一性......");
		IntegerResponse response = adminUtmReadPermissionsService.isExistsAdminUser(userName);
		return response;
	}
	@ApiOperation(value = "渠道权限列表转换接口", notes = "渠道权限列表转换接口")
	@GetMapping("/preparedatas")
	public AdminResult prepareDatas() {
		logger.info("渠道权限列表转换接口......");
		AdminResult adminResult = new AdminResult();
		UtmPlatResponse response = adminUtmReadPermissionsService.getUtmPlatList();
		adminResult.setData(response.getResultList());
		return adminResult;

	}

	@ApiOperation(value = "新增修改详情页", notes = "新增修改详情页")
	@PostMapping("/info")
	public AdminResult info(@RequestBody AdminUtmReadPermissionsRequest request) {
		logger.info("新增修改详情页......");
		AdminResult adminResult = new AdminResult();
		AdminUtmReadPermissionsResponse response = adminUtmReadPermissionsService.getRecord(request);
		adminResult.setData(response.getResult());
		return adminResult;

	}
	@ApiOperation(value = "渠道帐号管理", notes = "添加渠道帐号管理")
	@RequestMapping("/insert")
	public AdminResult add(@RequestBody AdminUtmReadPermissionsRequest requestBean) {
		AdminUtmReadPermissionsResponse response = adminUtmReadPermissionsService.insertAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "渠道帐号管理", notes = "修改渠道帐号管理")
	@RequestMapping("/update")
	public AdminResult update(@RequestBody AdminUtmReadPermissionsRequest requestBean) {
		AdminUtmReadPermissionsResponse response = adminUtmReadPermissionsService.updateAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "渠道帐号管理", notes = "删除渠道帐号管理")
	@RequestMapping("/delete/{id}")
	public AdminResult delete(@PathVariable Integer id) {
		AdminUtmReadPermissionsResponse response = adminUtmReadPermissionsService.deleteById(id);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

}
