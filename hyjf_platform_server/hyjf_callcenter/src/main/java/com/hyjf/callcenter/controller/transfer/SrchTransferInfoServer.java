package com.hyjf.callcenter.controller.transfer;

import com.hyjf.am.vo.callcenter.CallCenterBorrowCreditVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.ResultListBean;
import com.hyjf.callcenter.beans.SrchTransferInfoBean;
import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.controller.base.CallcenterBaseController;
import com.hyjf.callcenter.result.BaseResultBean;
import com.hyjf.callcenter.service.SrchTransferInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 呼叫中心:按照用户名/手机号查询转让信息Controller
 * @author libin
 * @version v1.0
 * @since v1.0 2018年6月6日
 */
@Api(value = "按照用户名/手机号查询转让信息")
@RestController
@RequestMapping("/hyjf-callcenter/transfer")
public class SrchTransferInfoServer extends CallcenterBaseController {
	private static final Logger logger = LoggerFactory.getLogger(SrchTransferInfoServer.class);
	
	@Autowired
	private SrchTransferInfoService srchTransferInfoService;
	
	/**
	 * @param request,response,UserBean
	 * @Author: libin
	 * @Desc :按照用户名/手机号查询转让信息
	 * @Date: 16:39 2018/6/6
	 * @Return: ResultListBean
	 */
	@ApiOperation(value = "按照用户名/手机号查询转让信息", notes = "按照用户名/手机号查询转让信息")
	@PostMapping(value = "/srchTransferInfo", produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResultListBean getContentOfTransferInfo(HttpServletRequest request, HttpServletResponse response,@RequestBody UserBean bean) {
		//初始化結果bean
		ResultListBean result = new ResultListBean();
		//初始化查詢bean
		CallCenterBorrowCreditVO callCenterBorrowCreditVO = new CallCenterBorrowCreditVO();
		
		SrchTransferInfoBean srchTransferInfoBean = new SrchTransferInfoBean();
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
			List<CallCenterBorrowCreditVO> recordList = this.srchTransferInfoService.selectBorrowCreditList(callCenterBorrowCreditVO);
			//根據useranme如果沒有檢到記錄
			if (recordList == null) {
				result.statusMessage(BaseResultBean.STATUS_FAIL,"该用户没有转让信息！");
				return result;
			}
			//编辑返回信息
			for (CallCenterBorrowCreditVO customize : recordList) {
				srchTransferInfoBean.setUsername(user.getUsername());//用户名
				srchTransferInfoBean.setMobile(user.getMobile());//手机号
				srchTransferInfoBean.setCreditNid(customize.getCreditNid());//债转编号
				srchTransferInfoBean.setBidNid(customize.getBidNid());//原始项目编号
				srchTransferInfoBean.setCreditCapital(customize.getCreditCapital());//债权本金
				srchTransferInfoBean.setCreditCapitalPrice(customize.getCreditCapitalPrice());//转让本金
				srchTransferInfoBean.setCreditDiscount(customize.getCreditDiscount());//折让率
				srchTransferInfoBean.setCreditPrice(customize.getCreditPrice());//转让价格
				srchTransferInfoBean.setCreditCapitalAssigned(customize.getCreditCapitalAssigned());//已转让金额
				srchTransferInfoBean.setCreditStatus(customize.getCreditStatus());//转让状态
				srchTransferInfoBean.setClient(customize.getClient());//操作平台
				srchTransferInfoBean.setAddTime(customize.getAddTime());//转让时间
				result.getDataList().add(srchTransferInfoBean);
			}
		} else {
			result.statusMessage(BaseResultBean.STATUS_FAIL,"该用户无用户名！");
		}
		result.statusMessage(BaseResultBean.STATUS_SUCCESS, BaseResultBean.STATUS_SUCCESS_DESC);
		return result;
	}
}
