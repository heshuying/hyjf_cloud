/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.AdminUserAuthListResponse;
import com.hyjf.am.response.user.AdminUserAuthLogListResponse;
import com.hyjf.am.resquest.user.AdminUserAuthListRequest;
import com.hyjf.am.resquest.user.AdminUserAuthLogListRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.customize.AdminUserAuthListCustomize;
import com.hyjf.am.user.dao.model.customize.AdminUserAuthLogListCustomize;
import com.hyjf.am.user.service.front.user.UserauthService;
import com.hyjf.am.vo.user.AdminUserAuthListVO;
import com.hyjf.am.vo.user.AdminUserAuthLogListVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DongZeShan
 * @version UserauthController.java, v0.1 2018年6月24日 下午2:17:00
 */
@RestController
@RequestMapping("/am-user/userauth")
public class UserauthController extends BaseController {
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
			Paginator paginator = new Paginator(adminUserAuthListRequest.getCurrPage(),recordTotal,adminUserAuthListRequest.getPageSize());
			List<AdminUserAuthListCustomize> recordList = this.userauthService.getRecordList(authUser,
					paginator.getOffset(), paginator.getLimit());
			AdminUserAuthListResponse aualr = new AdminUserAuthListResponse();
			List<AdminUserAuthListVO> avo = CommonUtils.convertBeanList(recordList,AdminUserAuthListVO.class);
			if (recordList != null) {
				aualr.setResultList(avo);
				aualr.setRecordTotal(recordTotal);
				aualr.setRtn(Response.SUCCESS);
			}
			return aualr;
		}
		return new AdminUserAuthListResponse();

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
		// 出借授权状态
		authUser.put("autoInvesStatus", form.getAutoInvesStatus());
		// 债转授权状态
		authUser.put("autoCreditStatus", form.getAutoCreditStatus());
		// 授权时间
		authUser.put("invesAddTimeStart", form.getInvesAddTimeStart());
		authUser.put("invesAddTimeEnd", form.getInvesAddTimeEnd());
		// 签约到期日
		if(form.getInvestEndTimeStart()!=null) {
			authUser.put("investEndTimeStart", form.getInvestEndTimeStart().replace("-",""));	
		}
		if( form.getInvestEndTimeEnd()!=null) {
			authUser.put("investEndTimeEnd", form.getInvestEndTimeEnd().replace("-",""));
		}
		
		return authUser;
	}

	/**
	 * 同步用户授权状态
	 * @auth sunpeikai
	 * @param userId 用户id
	 * @param type 1自动出借授权  2债转授权
	 * @return
	 */
	@ApiOperation(value = "同步用户授权状态", notes = "同步用户授权状态")
	@GetMapping(value = "/synuserauth/{userId}/{type}")
	public JSONObject synUserAuth(@PathVariable Integer userId , @PathVariable Integer type){
		JSONObject jsonObject = new JSONObject();
		logger.info("同步用户[{}]的授权状态,同步类型[{}]",userId,type);
		BankCallBean retBean = userauthService.getUserAuthQuery(userId, String.valueOf(type));
		logger.debug(JSON.toJSONString(retBean));
		try {
			if (retBean != null && BankCallConstant.RESPCODE_SUCCESS.equals(retBean.get(BankCallConstant.PARAM_RETCODE))) {
				this.userauthService.updateUserAuthState(userId, retBean);
				jsonObject.put("status",Response.SUCCESS);
				jsonObject.put("msg","查询成功");
			} else {
				String retCode = retBean != null ? retBean.getRetCode() : "";
				jsonObject.put("status","1");
				jsonObject.put("msg","错误");
				jsonObject.put("retCode",retCode);
			}
		} catch (Exception e) {
			logger.error("授权查询出错", e);
			jsonObject.put("status","1");
			jsonObject.put("msg",e.getMessage());
		}
		return jsonObject;
	}

	/**
	 * 自动出借解约
	 *
	 * @param userId,解约id
	 */

	@RequestMapping("/userinvescancel/{userId}")
	public AdminUserAuthListResponse cancelInvestAuth(@PathVariable int userId) {
		// 返回结果
		AdminUserAuthListResponse result = new AdminUserAuthListResponse();
		logger.info("自动出借解约开始，用户：{}", userId);
		
		BankCallBean retBean = userauthService.cancelInvestAuth(userId, "000002");
		
		
		if (retBean != null) {
            if (BankCallConstant.RESPCODE_SUCCESS.equals(retBean.getRetCode())) {
            	// 关闭授权操作
        		userauthService.updateCancelInvestAuth(userId);
        		String authType = "7";
        		// 在auth_log表中插入解约记录
        		userauthService.insertUserAuthLog2(userId, retBean.getRetCode(), authType);
        		result.setMessage("自动出借解约成功！");
        		result.setRtn(AdminUserAuthListResponse.SUCCESS);
            } else {
                String retCode = retBean != null ? retBean.getRetCode() : "";
        		result.setMessage(retCode);
        		result.setRtn(AdminUserAuthListResponse.ERROR);
            }
        } else {
    		result.setMessage("自动出借解约失败！");
    		result.setRtn(AdminUserAuthListResponse.ERROR);
        }


		return result;
	}

	/**
	 * 自动债转解约
	 *
	 * @param userId,解约id
	 */
	@RequestMapping("/usercreditcancel/{userId}")
	public AdminUserAuthListResponse cancelCreditAuth(@PathVariable int userId) {
		// 返回结果
		AdminUserAuthListResponse result = new AdminUserAuthListResponse();
		BankCallBean retBean = userauthService.cancelCreditAuth(userId, "000002");
		
        if (retBean != null) {
            if (BankCallConstant.RESPCODE_SUCCESS.equals(retBean.getRetCode())) {
            	String authType = "8";
        		// 关闭授权操作
        		userauthService.updateCancelCreditAuth(userId);
        		// 在auth_log表中插入解约记录
        		userauthService.insertUserAuthLog2(userId, retBean.getLogOrderId(), authType);
        		result.setMessage("自动债转解约成功！");
        		result.setRtn(AdminUserAuthListResponse.SUCCESS);
            } else {
                String retCode = retBean != null ? retBean.getRetCode() : "";
        		result.setMessage(retCode);
        		result.setRtn(AdminUserAuthListResponse.ERROR);
            }
        } else {
    		result.setMessage("自动债转解约失败！");
    		result.setRtn(AdminUserAuthListResponse.ERROR);
        }
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
	            Paginator paginator = new Paginator(form.getCurrPage(),recordTotal,form.getPageSize());
	            List<AdminUserAuthLogListCustomize> recordList = this.userauthService.getRecordListLog(authUser, paginator.getOffset(), paginator.getLimit());

	            ap.setResultList(CommonUtils.convertBeanList(recordList,AdminUserAuthLogListVO.class));
	            ap.setRecordTotal(recordTotal);
	            ap.setRtn(Response.SUCCESS);
	            return ap;
	        }
	        return new AdminUserAuthLogListResponse();

	}
}
