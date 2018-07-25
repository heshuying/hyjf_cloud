package com.hyjf.callcenter.controller.capital;

import com.hyjf.am.resquest.callcenter.CallCenterAccountDetailRequest;
import com.hyjf.am.vo.callcenter.CallCenterAccountDetailVO;
import com.hyjf.am.vo.trade.RUserVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.ResultListBean;
import com.hyjf.callcenter.beans.SrchCapitalInfoBean;
import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.controller.base.CallcenterBaseController;
import com.hyjf.callcenter.result.BaseResultBean;
import com.hyjf.callcenter.service.SrchCapitalInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author wangjun
 * @version SrchCapitalInfoServer, v0.1 2018/6/13 15:32
 */
@Controller
@RequestMapping(value = "/hyjf-callcenter/capital")
public class SrchCapitalInfoServer extends CallcenterBaseController {
	@Autowired
	private SrchCapitalInfoService srchCapitalInfoService;
	
	/**
	 * 呼叫中心查询资金明细
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/srchCapitalInfo", method = RequestMethod.POST)
	public ResultListBean getContentOfCapitalInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody UserBean bean) {
		//初始化结果bean
		ResultListBean result = new ResultListBean();
		//初始化查询bean
		CallCenterAccountDetailRequest callCenterAccountDetailRequest = new CallCenterAccountDetailRequest();

		//根据用户名或手机号取得用户信息
		UserVO user = this.getUser(bean, result);
		if (user == null) {
			if (result.getStatus()!=BaseResultBean.STATUS_FAIL) {
				result.statusMessage(BaseResultBean.STATUS_FAIL,"该用户不存在！");
			}
			return result;
		}
		//*************各自业务开始***************
		if (user.getUsername() != null && !"".equals(user.getUsername().trim())) {
			//查询条件使用userId精确查询
			callCenterAccountDetailRequest.setUserId(user.getUserId());
			callCenterAccountDetailRequest.setUserName(user.getUsername());
			//查询推荐人信息
			RUserVO referrerUser = this.getRefereerInfoByUserId(user.getUserId());
			if(referrerUser != null){
				callCenterAccountDetailRequest.setReferrerName(referrerUser.getUsername());
			}
			// 分页开始结束
			callCenterAccountDetailRequest.setLimitStart(bean.getLimitStart());
			callCenterAccountDetailRequest.setLimitEnd(bean.getLimitSize());

			List<CallCenterAccountDetailVO> accountDetails = this.srchCapitalInfoService.queryAccountDetails(callCenterAccountDetailRequest);
			if (accountDetails == null) {
				result.statusMessage(BaseResultBean.STATUS_FAIL,"该用户没有资金信息！");
				return result;
			}
			//编辑返回信息
			for (CallCenterAccountDetailVO recordBean : accountDetails) {
				SrchCapitalInfoBean returnBean = new SrchCapitalInfoBean();
				//返回结果赋值
				BeanUtils.copyProperties(recordBean, returnBean);
				//用户名
				returnBean.setUsername(user.getUsername());
				//手机号
				returnBean.setMobile(user.getMobile());
				result.getDataList().add(returnBean);
			}
		} else {
			result.statusMessage(BaseResultBean.STATUS_FAIL,"该用户无用户名！");
		}
		result.statusMessage(BaseResultBean.STATUS_SUCCESS, BaseResultBean.STATUS_SUCCESS_DESC);
		return result;

	}
}
