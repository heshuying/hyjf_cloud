package com.hyjf.callcenter.controller.userinfo;

import com.hyjf.am.vo.callcenter.CallCenterServiceUsersVO;
import com.hyjf.callcenter.beans.JsonBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hyjf.am.vo.callcenter.CallCenterUserBaseVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.ResultListBean;
import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.beans.UserInfoBean;
import com.hyjf.callcenter.beans.Users;
import com.hyjf.callcenter.controller.base.CallcenterBaseController;
import com.hyjf.callcenter.result.BaseResultBean;
import com.hyjf.callcenter.service.UserInfoService;

import java.util.List;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 呼叫中心:查询分配客服 & 按照用户名/手机号查询会员资料Controller
 * @author libin
 * @version v1.0
 * @since v1.0 2018年6月6日
 */
@Api(value = "查询分配客服&按照用户名/手机号查询会员资料")
@RestController
@RequestMapping("/hyjf-callcenter/user")
public class UserInfoServer extends CallcenterBaseController {
	private static final Logger logger = LoggerFactory.getLogger(UserInfoServer.class);

	@Autowired
	private UserInfoService userInfoService;

	/**
	 * @param request,response,UserBean
	 * @Author: libin
	 * @Desc :查询呼叫中心未分配客服的用户名/手机号调用入口
	 * @Date: 16:39 2018/6/6
	 * @Return: ResultListBean
	 */
	@ApiOperation(value = "查询分配客服&按照用户名", notes = "查询分配客服&按照用户名")
	@PostMapping(value = "/getNewUsers", produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResultListBean getNewUsers(HttpServletRequest request, HttpServletResponse response,@RequestBody UserBean bean) {
		ResultListBean result = new ResultListBean();

		//唯一识别号验证
		if (!checkUniqueNo(bean, result)) {
			return result;
		}
		//分页验证
		if (!checkLimit(bean, result)) {
			return result;
		}
		//取得分配客服的用户名和手机号
		List<CallCenterUserBaseVO> userList = this.userInfoService.getNoServiceUsersList(bean);
		if (userList == null) {
			result.statusMessage(BaseResultBean.STATUS_FAIL,"未查询到该用户基本信息！");
			return result;
		}
		//编辑返回结果
		for (CallCenterUserBaseVO recordBean : userList) {
			UserInfoBean returnBean = new UserInfoBean();
			//用户名
			returnBean.setUserName(recordBean.getUserName());
			//手机号
			returnBean.setMobile(recordBean.getMobile());
			result.getDataList().add(returnBean);
		}
		result.statusMessage(BaseResultBean.STATUS_SUCCESS, BaseResultBean.STATUS_SUCCESS_DESC);
		return result;
	}

	/**
	 * @param request,response,UserBean
	 * @Author: wangjun
	 * @Desc :更新分配客服状态
	 * @Date: 10:01 2018/6/13
	 * @Return: ResultListBean
	 */
	@ApiOperation(value = "更新分配客服状态", notes = "更新分配客服状态")
	@ResponseBody
	@PostMapping(value = "/setServedUsers" ,produces = "application/json; charset=utf-8")
	public BaseResultBean setServedUsers(HttpServletRequest request, HttpServletResponse response,
										 @RequestBody JsonBean bean) {
		BaseResultBean result = new BaseResultBean();

		//参数非空判断
		if (bean == null || bean.getUserJsonArray() == null) {
			result.statusMessage(BaseResultBean.STATUS_FAIL,"传入参数为空！");
			return result;
		}

		//唯一识别号验证
		if (!this.checkUniqueNo(bean, result)) {
			return result;
		}

		//解析json到list
		List<CallCenterServiceUsersVO> userList = bean.getUserJsonArray();

		//更新呼叫中心用户分配客服的状态
		Integer rowCount = this.userInfoService.executeRecord(userList);
		if (rowCount == null) {
			result.statusMessage(BaseResultBean.STATUS_FAIL, "用户分配结果操作失败！");
			return result;
		}
		result.statusMessage(BaseResultBean.STATUS_SUCCESS, BaseResultBean.STATUS_SUCCESS_DESC+"。操作记录数："+rowCount);
		return result;
	}


	/**
	 * @param request,response,UserBean
	 * @Author: libin
	 * @Desc :查询会员资料调用入口
	 * @Date: 16:39 2018/6/13
	 * @Return: ResultListBean
	 */
	@ApiOperation(value = "查询会员资料调用入口", notes = "查询会员资料调用入口")
	@PostMapping(value = "/getUserInfo", produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResultListBean getUserInfo(HttpServletRequest request, HttpServletResponse response,@RequestBody UserBean bean) {
		ResultListBean result = new ResultListBean();
		//根据用户名或手机号取得用户信息
		UserVO user = this.getUser(bean, result);
		if (user == null) {
			if (result.getStatus()!=BaseResultBean.STATUS_FAIL) {
				result.statusMessage(BaseResultBean.STATUS_FAIL,"该用户不存在！");
			}
			return result;
		}
		//*************各自业务开始***************
		UserInfoBean returnBean = new UserInfoBean();
		//取得用户基本信息
		List<CallCenterUserBaseVO> userBaseList = this.userInfoService.getUserBaseList(user);
		if (userBaseList == null || userBaseList.size()<=0) {
			result.statusMessage(BaseResultBean.STATUS_FAIL,"未查询到该用户基本信息！");
			return result;
		}

		//取得用户详细信息
		List<CallCenterUserBaseVO> userDetailList = this.userInfoService.getUserDetailList(user);
		if (userDetailList == null) {
			result.statusMessage(BaseResultBean.STATUS_FAIL,"未查询到该用户详细信息！");
			return result;
		}

		//编辑返回结果
		//用户详细信息
		if (userDetailList.size()==1) {
			BeanUtils.copyProperties(userDetailList.get(0), returnBean);
		}

		//用户基本信息
		if (userBaseList.size()==1) {
			//分公司
			returnBean.setRegionName(userBaseList.get(0).getRegionName());
			//分部
			returnBean.setBranchName(userBaseList.get(0).getBranchName());
			//团队
			returnBean.setDepartmentName(userBaseList.get(0).getDepartmentName());
			//汇付开户状态
			returnBean.setOpenAccount(userBaseList.get(0).getOpenAccount());
			//江西银行开户状态
			returnBean.setBankOpenAccount(userBaseList.get(0).getBankOpenAccount());
			//江西银行电子账号
			returnBean.setBankAccount(userBaseList.get(0).getBankAccount());
			//江西银行开户时间
			returnBean.setBankOpenTime(userBaseList.get(0).getBankOpenTime());
		}
		//用户名
		returnBean.setUserName(user.getUsername());
		//手机号
		returnBean.setMobile(user.getMobile());
		result.getDataList().add(returnBean);
		result.statusMessage(BaseResultBean.STATUS_SUCCESS, BaseResultBean.STATUS_SUCCESS_DESC);
		return result;
	}
}
