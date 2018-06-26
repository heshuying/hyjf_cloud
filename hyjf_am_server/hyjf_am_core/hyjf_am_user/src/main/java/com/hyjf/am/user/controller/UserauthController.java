/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.AdminUserAuthListResponse;
import com.hyjf.am.resquest.user.AdminUserAuthListRequest;
import com.hyjf.am.user.dao.model.customize.AdminUserAuthListCustomize;
import com.hyjf.am.user.service.UserauthService;
import com.hyjf.am.vo.user.AdminUserAuthListVO;
import com.hyjf.common.paginator.Paginator;

/**
 * @author DongZeShan
 * @version UserauthController.java, v0.1 2018年6月24日 下午2:17:00
 */
@RestController
@RequestMapping("/am-user/userauth")
public class UserauthController {
	private UserauthService userauthService;
	private static Logger logger = LoggerFactory.getLogger(UserauthController.class);
	//查询授权明细
	@PostMapping("/userauthlist")
	public AdminUserAuthListResponse userauthlist(@RequestBody @Valid AdminUserAuthListRequest adminUserAuthListRequest) {
		
		
		 Map<String, Object> authUser = this.buildQueryCondition(adminUserAuthListRequest);
	        int recordTotal = this.userauthService.countRecordTotal(authUser);
	        if (recordTotal > 0) {
	            Paginator paginator = new Paginator(adminUserAuthListRequest.getPaginatorPage(), recordTotal);
	            List<AdminUserAuthListCustomize> recordList = this.userauthService.getRecordList(authUser,
	                    paginator.getOffset(), paginator.getLimit());
	            AdminUserAuthListResponse aualr=new AdminUserAuthListResponse();
	            List<AdminUserAuthListVO> avo=null;
				if (recordList != null) {
					BeanUtils.copyProperties(recordList, avo);
					aualr.setResultList(avo);
					aualr.setRecordTotal(recordTotal);
					aualr.setRtn(Response.SUCCESS);
				}
				return aualr;
	        }
		return null;
		
	}
	   /**
     * 构建查询条件
     *
     * @param form
     * @return
     */
    private Map<String, Object> buildQueryCondition(AdminUserAuthListRequest form) {
        // 封装查询条件
        Map<String, Object> authUser = new HashMap<String, Object>();
        authUser.put("userName", form.getUserName());
        authUser.put("recommendName", form.getRecommendName());
        // 投资授权状态
        authUser.put("autoInvesStatus", form.getAutoInvesStatus());
        // 债转授权状态
        authUser.put("autoCreditStatus", form.getAutoCreditStatus());
        // 授权时间
        authUser.put("invesAddTimeStart", form.getInvesAddTimeStart());
        authUser.put("invesAddTimeEnd", form.getInvesAddTimeEnd());
        // 签约到期日
        authUser.put("invesEndTimeStart", form.getInvestEndTimeStart());
        authUser.put("invesEndTimeEnd", form.getInvestEndTimeEnd());
        return authUser;
    }
}
