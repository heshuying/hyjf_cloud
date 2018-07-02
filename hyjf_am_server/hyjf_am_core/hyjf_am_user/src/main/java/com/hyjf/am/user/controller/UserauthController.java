/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.AdminUserAuthListResponse;
import com.hyjf.am.response.user.AdminUserAuthLogListResponse;
import com.hyjf.am.resquest.user.AdminUserAuthListRequest;
import com.hyjf.am.resquest.user.AdminUserAuthLogListRequest;
import com.hyjf.am.user.dao.model.customize.AdminUserAuthListCustomize;
import com.hyjf.am.user.dao.model.customize.AdminUserAuthLogListCustomize;
import com.hyjf.am.user.service.UserauthService;
import com.hyjf.am.vo.user.AdminUserAuthListVO;
import com.hyjf.am.vo.user.AdminUserAuthLogListVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;

/**
 * @author DongZeShan
 * @version UserauthController.java, v0.1 2018年6月24日 下午2:17:00
 */
@RestController
@RequestMapping("/am-user/userauth")
public class UserauthController extends BaseController{
	@Autowired
	private UserauthService userauthService;

	/**
	 * 查询授权明细
 	 */
	@PostMapping("/userauthlist")
	public AdminUserAuthListResponse userauthlist(
			@RequestBody @Valid AdminUserAuthListRequest adminUserAuthListRequest) {

		Map<String, Object> authUser = this.buildQueryCondition(adminUserAuthListRequest);
		int recordTotal = this.userauthService.countRecordTotal(authUser);
		if (recordTotal > 0) {
			Paginator paginator = new Paginator(adminUserAuthListRequest.getPaginatorPage(), recordTotal);
			List<AdminUserAuthListCustomize> recordList = this.userauthService.getRecordList(authUser,
					paginator.getOffset(), paginator.getLimit());
			AdminUserAuthListResponse aualr = new AdminUserAuthListResponse();
			List<AdminUserAuthListVO> avo = CommonUtils.convertBeanList(recordList,AdminUserAuthListVO.class);
			if (recordList != null) {
				BeanUtils.copyProperties(recordList, avo);
				aualr.setResultList(avo);
				aualr.setRecordTotal(String.valueOf(recordTotal));
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

	/**
	 * 自动投资解约
	 *
	 * @param userId,解约id
	 */

	@RequestMapping("/userinvescancel")
	public AdminUserAuthListResponse cancelInvestAuth(@PathVariable int userId, @PathVariable String ordId) {
		// 返回结果
		AdminUserAuthListResponse result = new AdminUserAuthListResponse();
		logger.info("自动投资解约开始，用户：{}", userId);
		// 关闭授权操作
		userauthService.updateCancelInvestAuth(userId);
		String authType = "7";
		// 在auth_log表中插入解约记录
		userauthService.insertUserAuthLog2(userId, ordId, authType);
		result.setMessage("自动投资解约成功！");
		result.setRtn(AdminUserAuthListResponse.SUCCESS);

		return result;
	}

	/**
	 * 自动债转解约
	 *
	 * @param userId,解约id
	 */
	@RequestMapping("/usercreditcancel")
	public AdminUserAuthListResponse cancelCreditAuth(@PathVariable int userId, @PathVariable String ordId) {
		// 返回结果
		AdminUserAuthListResponse result = new AdminUserAuthListResponse();

		String authType = "8";
		// 关闭授权操作
		userauthService.updateCancelCreditAuth(userId);
		// 在auth_log表中插入解约记录
		userauthService.insertUserAuthLog2(userId, ordId, authType);
		result.setMessage("自动投资解约成功！");
		result.setRtn(AdminUserAuthListResponse.SUCCESS);
		return result;
	}

	@PostMapping("/userauthloglist")
	public AdminUserAuthLogListResponse userauthloglist(
			@RequestBody @Valid AdminUserAuthLogListRequest form) {
		AdminUserAuthLogListResponse ap=new AdminUserAuthLogListResponse();
		 Map<String, Object> authUser = new HashMap<String, Object>();
	        authUser.put("userName", form.getUserName());
	        authUser.put("authType", form.getAuthType());
	        authUser.put("orderId", form.getOrderId());
	        authUser.put("addTimeStart", form.getAddTimeStart());
	        authUser.put("addTimeEnd", form.getAddTimeEnd());
	        authUser.put("orderStatus", form.getOrderStatus());
	        int recordTotal = this.userauthService.countRecordTotalLog(authUser);
	        if (recordTotal > 0) {
	            Paginator paginator = new Paginator(form.getPaginatorPage(), recordTotal);
	            List<AdminUserAuthLogListCustomize> recordList = this.userauthService.getRecordListLog(authUser, paginator.getOffset(), paginator.getLimit());
	            List<AdminUserAuthLogListVO> avo = null;
				BeanUtils.copyProperties(recordList, avo);
	            ap.setResultList(avo);
	            ap.setRecordTotal(recordTotal);
	            ap.setRtn(Response.SUCCESS);
	            return ap;
	        }
	        return null;

	}
}
