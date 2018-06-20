package com.hyjf.callcenter.controller.undertake;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.vo.callcenter.CallCenterBorrowCreditVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.ResultListBean;
import com.hyjf.callcenter.beans.SrchUndertakeInfoBean;
import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.controller.base.CallcenterBaseController;
import com.hyjf.callcenter.result.BaseResultBean;
import com.hyjf.callcenter.service.SrchUndertakeInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 呼叫中心:按照用户名/手机号查询承接债权信息Controller
 * @author LIBIN
 * @version hyjf 1.0
 * @since hyjf 1.0 2017年07月15日 
 */
@Api(value = "按照用户名/手机号查询承接债权信息")
@RestController
@RequestMapping("/hyjf-callcenter/undertake")
public class SrchUndertakeInfoServer extends CallcenterBaseController {
	private static final Logger logger = LoggerFactory.getLogger(SrchUndertakeInfoServer.class);
	@Autowired
	private SrchUndertakeInfoService srchUndertakeInfoService;
	
	/**
	 * @param request,response,UserBean
	 * @Author: libin
	 * @Desc :按照用户名/手机号查询承接债权信息
	 * @Date: 16:39 2018/6/6
	 * @Return: ResultListBean
	 */
	@ApiOperation(value = "按照用户名/手机号查询承接债权信息", notes = "按照用户名/手机号查询承接债权信息")
	@PostMapping(value = "/srchUndertakeInfo", produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResultListBean getContentOfUndertakeInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody UserBean bean) {
		//初始化結果bean
		ResultListBean result = new ResultListBean();
		//初始化查詢bean
		CallCenterBorrowCreditVO callCenterBorrowCreditVO = new CallCenterBorrowCreditVO();
		// 返回bean
		SrchUndertakeInfoBean returnBean = new SrchUndertakeInfoBean();
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
			// 用户名(查詢條件) 判空：当只给手机号时,查詢的記錄里username可能為空，這裡不是查全部，所以必須有username
			callCenterBorrowCreditVO.setUsernameSrch(user.getUsername());
			callCenterBorrowCreditVO.setUserId(user.getUserId());
			// 分页开始结束
			callCenterBorrowCreditVO.setLimitStart(bean.getLimitStart());
			callCenterBorrowCreditVO.setLimitEnd(bean.getLimitSize());
			List<CallCenterBorrowCreditVO> recordList = this.srchUndertakeInfoService.selectBorrowCreditTenderList(callCenterBorrowCreditVO);
			//根據useranme如果沒有檢到記錄
			if (recordList == null) {
				result.statusMessage(BaseResultBean.STATUS_FAIL,"该用户没有转让信息！");
				return result;
			}
			//编辑返回信息
			for (CallCenterBorrowCreditVO recordBean : recordList) {
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
