package com.hyjf.callcenter.controller.investHtj;

import com.hyjf.am.vo.callcenter.CallcenterHtjInvestVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.ResultListBean;
import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.beans.customizebean.HtjInvestBean;
import com.hyjf.callcenter.controller.base.CallcenterBaseController;
import com.hyjf.callcenter.result.BaseResultBean;
import com.hyjf.callcenter.service.HtjInvestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 呼叫中心:按照用户名/手机号查询投资明细（汇添金）Controller
 * @author pcc
 * @version hyjf 1.0
 * @since hyjf 1.0 2017年07月15日 
 */

@Api(value = "查询投资明细（汇添金）")
@RestController
@RequestMapping("/hyjf-callcenter/invest/htj")
public class HtjInvestServer extends CallcenterBaseController {
	private static final Logger logger = LoggerFactory.getLogger(HtjInvestServer.class);
	@Autowired
	private HtjInvestService htjInvestService;
	/**
	 * @param request,response,UserBean
	 * @Author: libin
	 * @Desc :按照用户名/手机号查询投资明细（汇添金）
	 * @Date: 16:39 2018/6/6
	 * @Return: ResultListBean
	 */
	@ApiOperation(value = "查询投资明细(汇添金)", notes = "查询投资明细(汇添金)")
	@PostMapping(value = "/getInvestInfo", produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResultListBean getContentOfHtjInvest(HttpServletRequest request, HttpServletResponse response,
			@RequestBody UserBean bean) {
		ResultListBean result = new ResultListBean();
		HtjInvestBean returnBean = new HtjInvestBean();
		//根据用户名或手机号取得用户信息
		UserVO user = this.getUser(bean, result);
		if (user == null) {
			if (result.getStatus()!=BaseResultBean.STATUS_FAIL) {
				result.statusMessage(BaseResultBean.STATUS_FAIL,"该用户不存在！");
			}
			return result;
		}
		//*************各自业务开始***************
		List<CallcenterHtjInvestVO> recordList = this.htjInvestService.getRecordList(
				user,bean.getLimitStart(),bean.getLimitSize());
		if (recordList == null) {
			result.statusMessage(ResultListBean.STATUS_FAIL,"该用户未投资汇天金！");
			return result;
		}
		
		//编辑返回信息
		for (CallcenterHtjInvestVO recordBean : recordList) {
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
