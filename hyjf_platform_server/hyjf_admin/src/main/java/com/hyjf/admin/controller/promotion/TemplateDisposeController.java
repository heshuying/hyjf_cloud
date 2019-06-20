package com.hyjf.admin.controller.promotion;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.LandingManagerService;
import com.hyjf.admin.service.RegistRecordService;
import com.hyjf.am.response.admin.TemplateDisposeResponse;
import com.hyjf.am.response.user.RegistRecordResponse;
import com.hyjf.am.response.user.TemplateConfigResponse;
import com.hyjf.am.resquest.admin.TemplateDisposeRequest;
import com.hyjf.am.resquest.user.LandingManagerRequest;
import com.hyjf.am.resquest.user.RegistRcordRequest;
import com.hyjf.common.cache.CacheUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(tags = "推广中心-移动端着陆页配置")
@RestController
@RequestMapping("/hyjf-admin/promotion/templateDispose")
public class TemplateDisposeController {

    private Logger logger = LoggerFactory.getLogger(TemplateDisposeController.class);
    /**
     * 查看权限
     */
    public static final String PERMISSIONS = "templateDispose";
//	@Autowired
	//private TemplateDisposeService templateDisposeService;
	@Autowired
	private RegistRecordService registRecordService;
	@Autowired
	private LandingManagerService landingManagerService;
    @ApiOperation(value = "页面初始化", notes = "初始化下拉列表")
    @PostMapping("/init")
    public AdminResult utmListInit(HttpServletRequest request, HttpServletResponse response) {
        // 模板类型  redis 名修改
        Map<String, String> userRoles = CacheUtil.getParamNameMap("TEMP_TYPE");
        List<DropDownVO> listUserRoles = com.hyjf.admin.utils.ConvertUtils.convertParamMapToDropDown(userRoles);
        return new AdminResult(listUserRoles);
    }

    @ApiOperation(value = "新建页面初始化", notes = "新建页面初始化")
    @PostMapping("/infoInit")
    public AdminResult infoInit(HttpServletRequest request, HttpServletResponse response) {
        // 注册渠道
        RegistRcordRequest registerRcordeRequest = new RegistRcordRequest();
        RegistRecordResponse registRecordResponse = registRecordService.findUtmAll(registerRcordeRequest);
        LandingManagerRequest landingManagerRequest=new LandingManagerRequest();
        landingManagerRequest.setCurrPage(1000);
        landingManagerRequest.setPageSize(1);
        landingManagerRequest.setStatus(1);
		TemplateConfigResponse templateConfigResponse = landingManagerService.selectTempConfigList(landingManagerRequest);
		TemplateDisposeResponse tdr=new TemplateDisposeResponse();
		tdr.setTemplateConfigList(templateConfigResponse.getResultList());
		tdr.setUserRoles( registRecordResponse.getUtmPlatVOList());
        return new AdminResult(tdr);
    }
	/**
	 * 查询着陆页配置
 	 */
	@PostMapping("/templateDisposeList")
	public TemplateDisposeResponse templateDisposeList(@RequestBody  TemplateDisposeRequest templateDisposeRequest) {
		TemplateDisposeResponse templateDisposeResponse=templateDisposeService.templateDisposeList(templateDisposeRequest);
		return templateDisposeResponse;

	}
	/**
	 * 修改着陆页配置
 	 */
	@PostMapping("/updateTemplateDispose")
	public TemplateDisposeResponse updateTemplateDispose(@RequestBody  TemplateDisposeRequest templateDisposeRequest) {
		TemplateDisposeResponse templateDisposeResponse=templateDisposeService.updateTemplateDispose(templateDisposeRequest);
		return templateDisposeResponse;

	}
	/**
	 * 修改着陆页配置
 	 */
	@PostMapping("/insertTemplateDispose")
	public TemplateDisposeResponse insertTemplateDispose(@RequestBody  TemplateDisposeRequest templateDisposeRequest) {
		TemplateDisposeResponse templateDisposeResponse=templateDisposeService.insertTemplateDispose(templateDisposeRequest);
		return templateDisposeResponse;

	}
	/**
	 *删除或者修改状态 着陆页配置
 	 */
	@PostMapping("/deleteTemplateDispose")
	public TemplateDisposeResponse deleteTemplateDispose(@RequestBody  TemplateDisposeRequest templateDisposeRequest) {
		TemplateDisposeResponse templateDisposeResponse=templateDisposeService.deleteTemplateDispose(templateDisposeRequest);
		return templateDisposeResponse;

	}



}
