package com.hyjf.callcenter.controller.investhzt;

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

import com.hyjf.am.vo.callcenter.CallcenterHztInvestVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.HztInvestBean;
import com.hyjf.callcenter.beans.ResultListBean;
import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.controller.base.CallcenterBaseController;
import com.hyjf.callcenter.result.BaseResultBean;
import com.hyjf.callcenter.service.HztInvestService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 呼叫中心:查询投资明细(直投产品)Controller
 * @author libin
 * @version v1.0
 * @since v1.0 2018年6月16日
 */
@Api(value = "查询投资明细(直投产品)")
@RestController
@RequestMapping("/hyjf-callcenter/invest/hzt")
public class HztInvestServer extends CallcenterBaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(HztInvestServer.class);
	@Autowired
	private HztInvestService hztInvestService;
	
	/**
	 * @param request,response,UserBean
	 * @Author: libin
	 * @Desc :查询呼叫中心未分配客服的用户名/手机号调用入口
	 * @Date: 16:39 2018/6/6
	 * @Return: ResultListBean
	 */
	@ApiOperation(value = "查询投资明细(直投产品)", notes = "查询投资明细(直投产品)")
	@PostMapping(value = "/getInvestInfo", produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResultListBean getContentOfHztInvest(HttpServletRequest request, HttpServletResponse response,@RequestBody UserBean bean) {
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
		HztInvestBean returnBean = new HztInvestBean();
		//查询投资明细(直投产品)
		List<CallcenterHztInvestVO> recordList = this.hztInvestService.getRecordList(user,bean.getLimitStart(),bean.getLimitSize());
		if (recordList == null) {
			result.statusMessage(ResultListBean.STATUS_FAIL,"该用户未投资汇直投！");
			return result;
		}
		
		//编辑返回信息
		for (CallcenterHztInvestVO recordBean : recordList) {
			//*************各自业务结束***************
			//检索bean→返回bean
			BeanUtils.copyProperties(recordBean, returnBean);
			//用户名
			returnBean.setUserName(user.getUsername());
			//手机号
			returnBean.setMobile(user.getMobile());	
			
			result.getDataList().add(returnBean);
		}
		result.statusMessage(ResultListBean.STATUS_SUCCESS, ResultListBean.STATUS_SUCCESS_DESC);
		return result;
	}
}
