package com.hyjf.callcenter.controller.recharge;

import com.hyjf.am.resquest.callcenter.CallCenterBaseRequest;
import com.hyjf.am.vo.callcenter.CallCenterRechargeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.ResultListBean;
import com.hyjf.callcenter.beans.SrchRechargeInfoBean;
import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.controller.base.CallcenterBaseController;
import com.hyjf.callcenter.result.BaseResultBean;
import com.hyjf.callcenter.service.SrchRechargeInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * @author wangjun
 * @version SrchCapitalInfoServer, v0.1 2018/6/13 15:32
 */
@Controller
@RequestMapping(value = "/hyjf-callcenter/recharge")
public class SrchRechargeInfoServer extends CallcenterBaseController {
	@Autowired
	private SrchRechargeInfoService srchRechargeInfoService;
	/**
	 * 呼叫中心接口调用入口
	 * @param bean
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/srchRechargeInfo", method = RequestMethod.POST)
	public ResultListBean getContentOfRechargeInfo(@RequestBody UserBean bean) {
		//初始化结果bean
		ResultListBean result = new ResultListBean();
		//初始化查询bean
		CallCenterBaseRequest callCenterBaseRequest = new CallCenterBaseRequest();

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
			callCenterBaseRequest.setUserId(user.getUserId());
			callCenterBaseRequest.setMobile(user.getMobile());
			callCenterBaseRequest.setLimitStart(bean.getLimitStart());
			callCenterBaseRequest.setLimitEnd(bean.getLimitSize());

			List<CallCenterRechargeVO> rechargeCustomize = this.srchRechargeInfoService.queryRechargeList(callCenterBaseRequest);
			if (rechargeCustomize == null) {
				result.statusMessage(BaseResultBean.STATUS_FAIL,"该用户没有充值记录！");
				return result;
			}
			//编辑返回信息
			for (CallCenterRechargeVO recordBean : rechargeCustomize) {
				SrchRechargeInfoBean returnBean = new SrchRechargeInfoBean();
				//查询bean赋值
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
