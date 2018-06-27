package com.hyjf.admin.controller.user;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.UserauthService;
import com.hyjf.am.response.user.AdminUserAuthListResponse;
import com.hyjf.am.resquest.user.AdminUserAuthListRequest;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 董泽杉
 * @version V1.0  
 * @package
 * @date 2018/6/27
 */
@Api(value = "授权状态&授权记录")
@RestController
@RequestMapping("/hyjf-admin/userauth")
public class UserauthController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(UserauthController.class);
	@Autowired
	private UserauthService userauthService;

	/**
	 * 权限维护画面初始化
	 *
	 * @param request
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "授权状态", notes = "授权状态集合")
	// @RequestMapping(value = "/usersInit")
	@PostMapping(value = "/userauthlist")
	@ResponseBody
	public JSONObject userManagerInit(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, String> map) {
		JSONObject info = new JSONObject();
		AdminUserAuthListRequest adminUserAuthListRequest = new AdminUserAuthListRequest();
		adminUserAuthListRequest.setPaginatorPage(Integer.valueOf(map.get("paginatorPage")));
		adminUserAuthListRequest.setUserName(map.get("userName"));
		adminUserAuthListRequest.setRecommendName(map.get("recommendName"));
		adminUserAuthListRequest.setAutoInvesStatus(map.get("autoInvesStatus"));
		adminUserAuthListRequest.setAutoCreditStatus(map.get("autoCreditStatus"));
		adminUserAuthListRequest.setInvesAddTimeStart(map.get("invesAddTimeStart"));
		adminUserAuthListRequest.setInvestEndTimeEnd(map.get("investEndTimeEnd"));
		adminUserAuthListRequest.setInvesAddTimeEnd(map.get("invesAddTimeEnd"));
		adminUserAuthListRequest.setInvestEndTimeStart(map.get("investEndTimeStart"));
		AdminUserAuthListResponse rqes = userauthService.userauthlist(adminUserAuthListRequest);
		info.put("list", rqes.getResultList());
		info.put("recordTotal", rqes.getRecordTotal());
		info.put("status", "00");
		info.put("msg", "成功");
		return info;

	}

//	/**
//	 * 自动授权查询 - 调用江西银行接口查询
//	 *
//	 * @param userId
//	 */
//	@RequestMapping(value = UserauthDefine.USERAUTH_QUERY_ACTION)
//	@ResponseBody
//	public JSONObject queryUserAuth(@RequestParam Integer userId) {
//		// 返回结果
//		JSONObject result = new JSONObject();
//		logger.info("授权查询开始，查询用户：{}", userId);
//		BankCallBean retBean = autoPlusService.getTermsAuthQuery(userId, BankCallConstant.CHANNEL_PC);
//		try {
//			if (retBean != null
//					&& BankCallConstant.RESPCODE_SUCCESS.equals(retBean.get(BankCallConstant.PARAM_RETCODE))) {
//				this.autoPlusService.updateUserAuth(userId, retBean);
//				result.put("success", "0");
//				result.put("msg", "自动授权查询成功！");
//			} else {
//				String retCode = retBean != null ? retBean.getRetCode() : "";
//				String retMessage = this.autoPlusService.getBankRetMsg(retCode);
//				result.put("success", "1");
//				result.put("msg", StringUtils.isNotEmpty(retMessage) ? retMessage : "未知错误");
//			}
//		} catch (Exception e) {
//			logger.error("授权查询出错", e);
//			result.put("success", "1");
//			result.put("msg", e.getMessage());
//		}
//		logger.info("queryUserAuth result is: {}", result.toJSONString());
//		return result;
//	}
//
//	/**
//	 * 自动投资解约
//	 *
//	 * @param userId
//	 */
//	@RequestMapping(UserauthDefine.USER_INVEST_CANCEL_ACTION)
//	@ResponseBody
//	public JSONObject cancelInvestAuth(@RequestParam int userId) {
//		// 返回结果
//		JSONObject result = new JSONObject();
//		logger.info("自动投资解约开始，用户：{}", userId);
//		if (!this.autoPlusService.canCancelAuth(userId)) {
//			result.put("success", "1");
//			result.put("msg", "当前用户存在持有中计划，不能解约！");
//			return result;
//		}
//		String authType = "7";
//		BankCallBean retBean = this.autoPlusService.cancelInvestAuth(userId, BankCallConstant.CHANNEL_PC);
//		try {
//			if (retBean != null) {
//				if (BankCallConstant.RESPCODE_SUCCESS.equals(retBean.getRetCode())) {
//					// 关闭授权操作
//					autoPlusService.updateCancelInvestAuth(userId);
//					// 在auth_log表中插入解约记录
//					autoPlusService.insertUserAuthLog2(userId, retBean, authType);
//					result.put("success", "0");
//					result.put("msg", "自动投资解约成功！");
//				} else {
//					String retCode = retBean != null ? retBean.getRetCode() : "";
//					String retMessage = this.autoPlusService.getBankRetMsg(retCode);
//					result.put("success", "1");
//					result.put("msg", StringUtils.isNotEmpty(retMessage) ? retMessage : "未知错误");
//				}
//			} else {
//				result.put("success", "1");
//				result.put("msg", "调用银行接口失败");
//			}
//		} catch (Exception e) {
//			logger.error("自动投资解约出错", e);
//			result.put("success", "1");
//			result.put("msg", e.getMessage());
//		}
//		return result;
//	}
//
//	/**
//	 * 自动债转解约
//	 *
//	 * @param userId
//	 */
//	@RequestMapping(UserauthDefine.USER_CREDIT_CANCEL_ACTION)
//	@ResponseBody
//	public JSONObject cancelCreditAuth(@RequestParam int userId) {
//		// 返回结果
//		JSONObject result = new JSONObject();
//		logger.info("自动债转授权开始，用户：{}", userId);
//		if (!this.autoPlusService.canCancelAuth(userId)) {
//			result.put("success", "1");
//			result.put("msg", "当前用户存在持有中计划，不能解约！");
//			return result;
//		}
//		String authType = "8";
//		BankCallBean retBean = this.autoPlusService.cancelCreditAuth(userId, BankCallConstant.CHANNEL_PC);
//		try {
//			if (retBean != null) {
//				if (BankCallConstant.RESPCODE_SUCCESS.equals(retBean.getRetCode())) {
//					// 关闭授权操作
//					autoPlusService.updateCancelCreditAuth(userId);
//					// 在auth_log表中插入解约记录
//					autoPlusService.insertUserAuthLog2(userId, retBean, authType);
//					result.put("success", "0");
//					result.put("msg", "自动债转解约成功！");
//				} else {
//					String retCode = retBean != null ? retBean.getRetCode() : "";
//					String retMessage = this.autoPlusService.getBankRetMsg(retCode);
//					result.put("success", "1");
//					result.put("msg", StringUtils.isNotEmpty(retMessage) ? retMessage : "未知错误");
//				}
//			} else {
//				result.put("success", "1");
//				result.put("msg", "调用银行接口失败");
//			}
//		} catch (Exception e) {
//			logger.error("自动债转解约出错", e);
//			result.put("success", "1");
//			result.put("msg", e.getMessage());
//		}
//		return result;
//	}
//
//	/**
//	 * 构建查询条件
//	 *
//	 * @param form
//	 * @return
//	 */
//	private Map<String, Object> buildQueryCondition(UserauthListCustomizeBean form) {
//		// 封装查询条件
//		Map<String, Object> authUser = new HashMap<String, Object>();
//		authUser.put("userName", form.getUserName());
//		authUser.put("recommendName", form.getRecommendName());
//		// 投资授权状态
//		authUser.put("autoInvesStatus", form.getAutoInvesStatus());
//		// 债转授权状态
//		authUser.put("autoCreditStatus", form.getAutoCreditStatus());
//		// 授权时间
//		authUser.put("invesAddTimeStart", form.getInvesAddTimeStart());
//		authUser.put("invesAddTimeEnd", form.getInvesAddTimeEnd());
//		// 签约到期日
//		authUser.put("invesEndTimeStart", form.getInvestEndTimeStart());
//		authUser.put("invesEndTimeEnd", form.getInvestEndTimeEnd());
//		return authUser;
//	}

}
