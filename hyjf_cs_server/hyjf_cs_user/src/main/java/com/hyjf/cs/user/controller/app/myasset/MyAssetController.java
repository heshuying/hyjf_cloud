/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.myasset;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.myasset.MyAssetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;

/**
 * app端账户总览
 * @author jun
 * @version MyAssetController, v0.1 2018/7/5 17:04
 */
@Api(value = "app端账户总览",tags = "app端-账户总览")
@Controller
@RequestMapping(value = "/hyjf-app/myasset")
public class MyAssetController extends BaseUserController {
	
	@Autowired
	private MyAssetService myAssetService;
	
	/**
	 * 获取我的财富
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@ApiOperation(value = "获取我的财富")
	@RequestMapping(value ="/info", method = RequestMethod.POST)
	public JSONObject getMyAsset(HttpServletRequest request, HttpServletResponse response) {
		logger.info("获取我的财富...myasset...info");
		JSONObject ret = new JSONObject();
		ret.put("request","/hyjf-app/myasset/info");
		// 版本号
		String version = request.getParameter("version");
		// 网络状态
		String netStatus = request.getParameter("netStatus");
		// 平台
		String platform = request.getParameter("platform");
		// 唯一标识
		String sign = request.getParameter("sign");
		// 金额显示格式
		DecimalFormat moneyFormat = null;
		// 检查参数正确性
		if (Validator.isNull(version) || Validator.isNull(netStatus) || Validator.isNull(platform) || Validator.isNull(sign)) {
			ret.put("status", "1");
			ret.put("statusDesc", "请求参数非法");
			return ret;
		}
		// 取得加密用的Key
		String key = SecretUtil.getKey(sign);
		if (Validator.isNull(key)) {
			ret.put("status", "1");
			ret.put("statusDesc", "请求参数非法");
			return ret;
		}
		// 判断选择哪种金融样式
		if (version.contains(CustomConstants.APP_VERSION_NUM)) {
			moneyFormat = CustomConstants.DF_FOR_VIEW_V1; // 按照满超新需求，我的财富接口用这种金融格式。
			// moneyFormat =CustomConstants.DF_FOR_VIEW;
		} else {
			moneyFormat = CustomConstants.DF_FOR_VIEW;
		}

		// 取得用户iD
		Integer userId = SecretUtil.getUserId(sign);
		
		// 取得用户信息
		UserInfoVO userinfo = this.myAssetService.getUsersInfoByUserId(userId);
		AccountVO account = this.myAssetService.getAccount(userId);
		
		try {
			if (userinfo != null && account != null) {
				ret.put("status", "0");
				ret.put("statusDesc", "成功");
				ret.put("balance", moneyFormat.format(account.getBankBalance()));
				ret.put("frost", moneyFormat.format(account.getBankFrost()));
				ret.put("waitCapital", moneyFormat.format(account.getBankAwaitCapital().add(account.getPlanCapitalWait())));
				ret.put("await", moneyFormat.format(account.getBankAwait().add(account.getPlanAccountWait())));
				ret.put("recoverInterest", moneyFormat.format(account.getBankInterestSum()));
				ret.put("waitInterest", moneyFormat.format(account.getBankAwaitInterest().add(account.getPlanInterestWait())));
				ret.put("investTotal", moneyFormat.format(account.getBankInvestSum()));
				if (userinfo.getRoleId() == 1) {// 出借人
					ret.put("accountType", "1");
					ret.put("total", moneyFormat.format(account.getBankTotal()));
				} else if (userinfo.getRoleId() == 2) {// 借款人
					ret.put("accountType", "2");
					ret.put("repay", moneyFormat.format(account.getBankWaitRepay()));
					ret.put("total", moneyFormat.format(account.getBankTotal()));
				}

			} else {
				ret.put("status", "2");
				ret.put("statusDesc", "用户账户信息异常");
				return ret;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.info("用户账户信息异常");
		}
		logger.info("info:"+ret);
		return ret;
	}
	
	
	
}
