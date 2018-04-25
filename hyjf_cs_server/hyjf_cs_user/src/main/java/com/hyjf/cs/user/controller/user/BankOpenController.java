package com.hyjf.cs.user.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.constants.RegisterError;
import com.hyjf.cs.user.result.BaseResultBean;
import com.hyjf.cs.user.service.BankOpenService;
import com.hyjf.cs.user.service.UserService;
import com.hyjf.cs.user.vo.BankOpenVO;

/**
 * 
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/api/open")
public class BankOpenController {
	private static final Logger logger = LoggerFactory.getLogger(BankOpenController.class);

	@Autowired
	private BankOpenService bankOpenService;

	@RequestMapping(value = "/init")
	public String init(Model model) {
		
		return "bankopen/init";
	}

	/**
	 * 开户
	 * 
	 * @param registerVO
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/openBankAccount", produces = "application/json; charset=utf-8")
	public BaseResultBean openBankAccount(@RequestBody @Valid BankOpenVO bankOpenVO, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("openBankAccount start, bankOpenVO is :{}", JSONObject.toJSONString(bankOpenVO));
		BaseResultBean resultBean = new BaseResultBean();

		/*
		 * // 验证请求参数 // if (StringUtils.isEmpty(bankOpenVO.getUserId())) { //
		 * modelAndView.addObject("message", "用户未登陆，请先登陆！"); // return modelAndView; //
		 * } Users user = this.openAccountService.getUsers(userId);
		 * 
		 * 
		 * 
		 * if (Validator.isNull(user)) { modelAndView.addObject("message", "获取用户信息失败！");
		 * return modelAndView; }
		 * 
		 * // 手机号 if (Validator.isNull(accountBean.getMobile())) {
		 * modelAndView.addObject("message", "手机号不能为空！"); return modelAndView; } if
		 * (Validator.isNull(accountBean.getCardNo())) {
		 * modelAndView.addObject("message", "银行卡号不能为空！"); return modelAndView; } // 姓名
		 * if (Validator.isNull(accountBean.getTrueName())) {
		 * modelAndView.addObject("message", "真实姓名不能为空！"); return modelAndView; }else{
		 * //判断真实姓名是否包含空格 if
		 * (!ValidatorCheckUtil.verfiyChinaFormat(accountBean.getTrueName())) {
		 * modelAndView.addObject("message", "真实姓名不能包含空格！"); return modelAndView; }
		 * //判断真实姓名的长度,不能超过10位 if (accountBean.getTrueName().length() > 10) {
		 * modelAndView.addObject("message", "真实姓名不能超过10位！"); return modelAndView; } }
		 * // 身份证号 if (Validator.isNull(accountBean.getIdNo())) {
		 * modelAndView.addObject("message", "身份证号不能为空！"); return modelAndView; }
		 * 
		 * if (accountBean.getIdNo().length() != 18) { modelAndView.addObject("message",
		 * "身份证号格式不正确！"); return modelAndView; } String idNo =
		 * StringUtils.upperCase(accountBean.getIdNo().trim());
		 * accountBean.setIdNo(idNo); //增加身份证唯一性校验 boolean isOnly =
		 * openAccountService.checkIdNo(idNo); if (!isOnly) {
		 * modelAndView.addObject("message", "身份证已存在！"); return modelAndView; }
		 * if(!Validator.isMobile(accountBean.getMobile())){
		 * modelAndView.addObject("message", "手机号格式错误！"); return modelAndView; } String
		 * mobile = this.openAccountService.getUsersMobile(userId); if
		 * (StringUtils.isBlank(mobile)) { if
		 * (StringUtils.isNotBlank(accountBean.getMobile())) {
		 * if(!openAccountService.existMobile(accountBean.getMobile())){ mobile =
		 * accountBean.getMobile(); }else{ modelAndView.addObject("message",
		 * "用户信息错误，手机号码重复！"); return modelAndView; } } else {
		 * modelAndView.addObject("message", "用户信息错误，未获取到用户的手机号码！"); return
		 * modelAndView; } } else { if (StringUtils.isNotBlank(accountBean.getMobile())
		 * && !mobile.equals(accountBean.getMobile())) {
		 * modelAndView.addObject("message", "用户信息错误，用户的手机号码错误！"); return modelAndView;
		 * } } // 拼装参数 调用江西银行 // 同步调用路径 String retUrl =
		 * PropUtils.getSystem(CustomConstants.HYJF_WEB_URL) +
		 * BankOpenDefine.REQUEST_MAPPING + BankOpenDefine.RETURL_SYN_ACTION + ".do"; //
		 * 异步调用路 String bgRetUrl = PropUtils.getSystem(CustomConstants.HYJF_WEB_URL) +
		 * BankOpenDefine.REQUEST_MAPPING + BankOpenDefine.RETURL_ASY_ACTION +
		 * ".do?phone="+accountBean.getMobile();
		 * 
		 * OpenAccountPageBean openBean = new OpenAccountPageBean();
		 * PropertyUtils.copyProperties(openBean, accountBean);
		 * openBean.setChannel(BankCallConstant.CHANNEL_PC); openBean.setUserId(userId);
		 * openBean.setIp(CustomUtil.getIpAddr(request)); // 同步 异步
		 * openBean.setRetUrl(retUrl); openBean.setNotifyUrl(bgRetUrl);
		 * openBean.setCoinstName("汇盈金服"); openBean.setPlatform("0"); // 账户用途 写死
		 * 00000-普通账户 10000-红包账户（只能有一个） 01000-手续费账户（只能有一个） 00100-担保账户
		 * openBean.setAcctUse("00000");
		 *//**
			 * 1：出借角色 2：借款角色 3：代偿角色
			 *//*
				 * openBean.setIdentity("1"); modelAndView =
				 * accountPageService.getCallbankMV(openBean); //保存开户日志 boolean isUpdateFlag =
				 * this.openAccountService.updateUserAccountLog(userId, user.getUsername(),
				 * openBean.getMobile(), openBean.getOrderId(),CustomConstants.CLIENT_PC
				 * ,openBean.getTrueName(),openBean.getIdNo(),openBean.getCardNo()); if
				 * (!isUpdateFlag) { _log.info("保存开户日志失败,手机号:[" + openBean.getMobile() +
				 * "],用户ID:[" + userId + "]"); modelAndView.addObject("message", "操作失败！");
				 * return modelAndView; } _log.info("开户end"); return modelAndView; } catch
				 * (Exception e) { e.printStackTrace(); _log.info("开户异常,异常信息:[" + e.toString() +
				 * "]"); modelAndView.addObject("message", "开户异常！"); return modelAndView; }
				 */

		return resultBean;
	}
}
