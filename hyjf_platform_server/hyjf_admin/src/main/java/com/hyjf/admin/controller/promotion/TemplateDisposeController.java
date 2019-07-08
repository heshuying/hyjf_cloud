package com.hyjf.admin.controller.promotion;

import java.util.Date;
import java.util.LinkedList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.admin.beans.BorrowCommonImage;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ContentAdsService;
import com.hyjf.admin.service.LandingManagerService;
import com.hyjf.admin.service.RegistRecordService;
import com.hyjf.admin.service.TemplateDisposeService;
import com.hyjf.am.response.Response;
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
public class TemplateDisposeController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(TemplateDisposeController.class);
    /**
     * 查看权限
     */
    public static final String PERMISSIONS = "templateDispose";
	@Autowired
	private TemplateDisposeService templateDisposeService;
	@Autowired
	private RegistRecordService registRecordService;
	@Autowired
	private LandingManagerService landingManagerService;
    @Autowired
    private ContentAdsService contentAdsService;
    
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
    public AdminResult infoInit(@RequestBody  TemplateDisposeRequest templateDisposeRequest) {
    	TemplateDisposeResponse tdr=new TemplateDisposeResponse();
    	if(templateDisposeRequest.getTempId()!=null) {
            LandingManagerRequest landingManagerRequest=new LandingManagerRequest();
            landingManagerRequest.setCurrPage(1);
            landingManagerRequest.setPageSize(1000);
            landingManagerRequest.setStatus(1);
    		TemplateConfigResponse templateConfigResponse = landingManagerService.selectTemplateById(templateDisposeRequest.getTempId());
    		tdr.setTemplateConfig(templateConfigResponse.getResult());
    		return new AdminResult(tdr);
    	}
        // 注册渠道
        RegistRcordRequest registerRcordeRequest = new RegistRcordRequest();
        RegistRecordResponse registRecordResponse = registRecordService.findUtmAll(registerRcordeRequest);
        LandingManagerRequest landingManagerRequest=new LandingManagerRequest();
        landingManagerRequest.setCurrPage(1);
        landingManagerRequest.setPageSize(1000);
        landingManagerRequest.setStatus(1);
		TemplateConfigResponse templateConfigResponse = landingManagerService.selectTempConfigList(landingManagerRequest);
		
		tdr.setTemplateConfigList(templateConfigResponse.getResultList());
		tdr.setUserRoles( registRecordResponse.getUtmPlatVOList());
        Map<String, String> userRoles = CacheUtil.getParamNameMap("TEMP_TYPE");
        List<DropDownVO> listUserRoles = com.hyjf.admin.utils.ConvertUtils.convertParamMapToDropDown(userRoles);
        tdr.setListUserRoles(listUserRoles);
		
		if(templateDisposeRequest.getId()!=null) {
			templateDisposeRequest.setCurrPage(1);
			templateDisposeRequest.setPageSize(1);
			TemplateDisposeResponse templateDisposeResponse=templateDisposeService.templateDisposeList(templateDisposeRequest);
			tdr.setResult(templateDisposeResponse.getResultList().get(0));
		}

        return new AdminResult(tdr);
    }
	/**
	 * 查询着陆页配置
 	 */
	@PostMapping("/templateDisposeList")
	public AdminResult templateDisposeList(@RequestBody  TemplateDisposeRequest templateDisposeRequest) {
		TemplateDisposeResponse templateDisposeResponse=templateDisposeService.templateDisposeList(templateDisposeRequest);
		return new AdminResult(ListResult.build(templateDisposeResponse.getResultList(), templateDisposeResponse.getRecordTotal()));

	}
	/**
	 * 修改着陆页配置
 	 */
	@PostMapping("/updateTemplateDispose")
	public AdminResult updateTemplateDispose(@RequestBody  TemplateDisposeRequest templateDisposeRequest, HttpServletRequest request) {
		String[] temp = templateDisposeRequest.getTempName().split("\\|");
		String[] utm = templateDisposeRequest.getUtmName().split("\\|");
		templateDisposeRequest.setTempId(Integer.valueOf(temp[0]));
		templateDisposeRequest.setTempName(temp[1]);
		templateDisposeRequest.setUtmId(Integer.valueOf(utm[0]));
		templateDisposeRequest.setUtmName(utm[1]);
		templateDisposeRequest.setUpdateTime(new Date());
		templateDisposeRequest.setUpdateUserId(Integer.valueOf(this.getUser(request).getId()));
		templateDisposeRequest.setUrl(systemConfig.getWechatHost()+"/landingPage?utm_source="+templateDisposeRequest.getUtmId()+"&templateId="+templateDisposeRequest.getId());
		TemplateDisposeResponse templateDisposeResponse=templateDisposeService.updateTemplateDispose(templateDisposeRequest);
		if(Response.isSuccess(templateDisposeResponse)) {
			return new AdminResult();
		}else {
			return new AdminResult(AdminResult.ERROR, templateDisposeResponse.getMessage());
		}

	}
	/**
	 * 修改着陆页配置
 	 */
	@PostMapping("/insertTemplateDispose")
	public AdminResult insertTemplateDispose(@RequestBody  TemplateDisposeRequest templateDisposeRequest, HttpServletRequest request) {
		String[] temp = templateDisposeRequest.getTempName().split("\\|");
		String[] utm = templateDisposeRequest.getUtmName().split("\\|");
		templateDisposeRequest.setTempId(Integer.valueOf(temp[0]));
		templateDisposeRequest.setTempName(temp[1]);
		templateDisposeRequest.setUtmId(Integer.valueOf(utm[0]));
		templateDisposeRequest.setUtmName(utm[1]);
		templateDisposeRequest.setUrl(systemConfig.getWechatHost()+"/landingPage?utm_source="+templateDisposeRequest.getUtmId()+"&templateId=");
		templateDisposeRequest.setCreateTime(new Date());
		templateDisposeRequest.setCreateUserId(Integer.valueOf(this.getUser(request).getId()));
		TemplateDisposeResponse templateDisposeResponse=templateDisposeService.insertTemplateDispose(templateDisposeRequest);
		if(Response.isSuccess(templateDisposeResponse)) {
			return new AdminResult();
		}else {
			return new AdminResult(AdminResult.ERROR, templateDisposeResponse.getMessage());
		}

	}
	/**
	 *删除或者修改状态 着陆页配置
 	 */
	@PostMapping("/deleteTemplateDispose")
	public AdminResult deleteTemplateDispose(@RequestBody  TemplateDisposeRequest templateDisposeRequest, HttpServletRequest request) {
		templateDisposeRequest.setUpdateTime(new Date());
		templateDisposeRequest.setUpdateUserId(Integer.valueOf(this.getUser(request).getId()));
		TemplateDisposeResponse templateDisposeResponse=templateDisposeService.deleteTemplateDispose(templateDisposeRequest);
		if(Response.isSuccess(templateDisposeResponse)) {
			return new AdminResult();
		}else {
			return new AdminResult(AdminResult.ERROR, templateDisposeResponse.getMessage());
		}

	}
    @ApiOperation(value = "图片上传", notes = "图片上传")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
  //  @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD , ShiroConstants.PERMISSION_MODIFY} )
    public  AdminResult<LinkedList<BorrowCommonImage>> uploadFile(HttpServletRequest request) throws Exception {
        AdminResult<LinkedList<BorrowCommonImage>> adminResult = new AdminResult<>();
        try {
            LinkedList<BorrowCommonImage> borrowCommonImages = contentAdsService.uploadFile(request);
            adminResult.setData(borrowCommonImages);
            adminResult.setStatus(SUCCESS);
            adminResult.setStatusDesc(SUCCESS_DESC);
            return adminResult;
        } catch (Exception e) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
    }


}
