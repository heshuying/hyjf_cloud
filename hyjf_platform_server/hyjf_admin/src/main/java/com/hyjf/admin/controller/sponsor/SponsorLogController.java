package com.hyjf.admin.controller.sponsor;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.SponsorLogService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.SponsorLogResponse;
import com.hyjf.am.resquest.trade.SponsorLogRequest;
import com.hyjf.am.vo.trade.SponsorLogVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value = "产品中心-担保户修改记录",tags = "产品中心-担保户修改记录")
@RestController
@RequestMapping("/hyjf-admin/sponsorLog")
public class SponsorLogController extends BaseController {
    @Autowired
    SponsorLogService sponsorLogService;
    
    private static final String PERMISSIONS = "sponsorLog";
	/**
	 * 查询列表
	 * @throws ParseException 
 	 */
	@ApiOperation(value = " 查询列表", notes = " 查询列表")
	@PostMapping(value = "/sponsorLogList")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    private AdminResult<ListResult<SponsorLogVO>> sponsorLogList(@RequestBody SponsorLogRequest sponsorLogRequest)  {
        SponsorLogResponse rs = sponsorLogService.sponsorLogList(sponsorLogRequest);
        return new AdminResult<ListResult<SponsorLogVO>>(ListResult.build(rs.getResultList(), rs.getRecordTotal()));
        
    }
	@ApiOperation(value = " 删除", notes = " 删除")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    @PostMapping(value = "/deleteSponsorLog")
    private AdminResult deleteSponsorLog(@RequestBody SponsorLogRequest sponsorLogRequest) {
		SponsorLogResponse rs = sponsorLogService.deleteSponsorLog(sponsorLogRequest);
	   	 if(!Response.isSuccess(rs)) {
			 return new AdminResult(AdminResult.FAIL, rs.getMessage());
		 }else {
			 return new AdminResult();
		 }
    }
	@ApiOperation(value = " 查询", notes = " 查询")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    @PostMapping(value = "/selectSponsorLog")
    private AdminResult selectSponsorLog(@RequestBody SponsorLogRequest sponsorLogRequest) {

		SponsorLogResponse rs =  sponsorLogService.selectSponsorLog(sponsorLogRequest);
	   	 if(!Response.isSuccess(rs)) {
			 return new AdminResult(AdminResult.FAIL, rs.getMessage());
		 }else {
			 return new AdminResult();
		 }
    }
	@ApiOperation(value = "新增", notes = " 新增")
	@ResponseBody
	@PostMapping(value = "/insertSponsorLog")
	//@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    private AdminResult insertSponsorLog(HttpServletRequest request,@RequestBody SponsorLogRequest sponsorLogRequest) {
		sponsorLogRequest.setAdminUserName(this.getUser(request).getUsername());
		sponsorLogRequest.setAdminUserId(Integer.valueOf(this.getUser(request).getId()));
		sponsorLogRequest.setStatus(0);
		sponsorLogRequest.setDelFlag(0);
    	 SponsorLogResponse rs = sponsorLogService.insertSponsorLog(sponsorLogRequest);
    		 return new AdminResult(AdminResult.SUCCESS, rs.getMessage());

    }

}
