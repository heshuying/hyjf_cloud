package com.hyjf.callcenter.controller.userinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hyjf.am.vo.callcenter.CallCenterUserBaseVO;
import com.hyjf.callcenter.beans.ResultListBean;
import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.beans.UserInfoBean;
import com.hyjf.callcenter.beans.customizebean.CallcenterUserBaseCustomize;
import com.hyjf.callcenter.controller.base.CallcenterBaseController;
import com.hyjf.callcenter.result.BaseResultBean;
import com.hyjf.callcenter.service.UserInfoService;

import java.util.List;

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

}
