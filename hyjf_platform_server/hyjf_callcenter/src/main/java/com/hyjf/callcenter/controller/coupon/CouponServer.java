package com.hyjf.callcenter.controller.coupon;


import com.hyjf.am.resquest.callcenter.CallCenterBaseRequest;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.*;
import com.hyjf.callcenter.controller.base.CallcenterBaseController;
import com.hyjf.callcenter.result.BaseResultBean;
import com.hyjf.callcenter.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author wangjun
 * @version CouponServer, v0.1 2018/6/15 16:25
 */
@Controller
@RequestMapping(value = "/hyjf-callcenter/coupon")
public class CouponServer extends CallcenterBaseController {
	
	@Autowired
	private CouponService couponService;
	
	/**
	 * 按照用户名/手机号查询优惠券
	 * @param request
	 * @param response
	 * @param bean
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserCouponInfoList", method = RequestMethod.POST)
	public ResultListBean getUserCouponInfoList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody UserBean bean) {
		ResultListBean result = new ResultListBean();

        CallCenterBaseRequest callCenterBaseRequest = new CallCenterBaseRequest();

		//根据用户名或手机号取得用户信息
		UserVO user = this.getUser(bean, result);
		if (user == null) {
		    result.setStatus(BaseResultBean.STATUS_FAIL);
            result.statusMessage(ResultListBean.STATUS_FAIL,"该用户不存在！");
			return result;
		}

        //查询条件赋值
        callCenterBaseRequest.setUserId(user.getUserId());
        callCenterBaseRequest.setLimitStart(bean.getLimitStart());
        callCenterBaseRequest.setLimitEnd(bean.getLimitSize());

        //赋值用
        callCenterBaseRequest.setUserName(user.getUsername());
        callCenterBaseRequest.setMobile(user.getMobile());

        result = this.couponService.selectCouponUserList(callCenterBaseRequest);
		return result;
	}
	
	
	/**
     * 按照用户名/手机号查询优惠券使用（直投产品）
     * @param request
     * @param response
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUserCouponTenderList", method = RequestMethod.POST)
    public ResultListBean getUserCouponTenderList(HttpServletRequest request, HttpServletResponse response,
    		@RequestBody UserBean bean) {
        ResultListBean result = new ResultListBean();

        CallCenterBaseRequest callCenterBaseRequest = new CallCenterBaseRequest();

        //根据用户名或手机号取得用户信息
        UserVO user = this.getUser(bean, result);
        if (user == null) {
            result.setStatus(BaseResultBean.STATUS_FAIL);
            result.statusMessage(ResultListBean.STATUS_FAIL,"该用户不存在！");
            return result;
        }

        //查询条件赋值
        callCenterBaseRequest.setUserId(user.getUserId());
        callCenterBaseRequest.setLimitStart(bean.getLimitStart());
        callCenterBaseRequest.setLimitEnd(bean.getLimitSize());

        //赋值用
        callCenterBaseRequest.setUserName(user.getUsername());
        callCenterBaseRequest.setMobile(user.getMobile());

        result = this.couponService.selectCouponTenderList(callCenterBaseRequest);
        return result;
    }


    /**
     * 按照用户名/手机号查询优惠券回款（直投产品）
     * @param request
     * @param response
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUserCouponBackMoneyList", method = RequestMethod.POST)
    public ResultListBean getUserCouponBackMoneyList(HttpServletRequest request, HttpServletResponse response,
                                                     @RequestBody UserBean bean) {
        ResultListBean result = new ResultListBean();

        CallCenterBaseRequest callCenterBaseRequest = new CallCenterBaseRequest();

        //根据用户名或手机号取得用户信息
        UserVO user = this.getUser(bean, result);
        if (user == null) {
            result.setStatus(BaseResultBean.STATUS_FAIL);
            result.statusMessage(ResultListBean.STATUS_FAIL,"该用户不存在！");
            return result;
        }

        //查询条件赋值
        callCenterBaseRequest.setUserId(user.getUserId());
        callCenterBaseRequest.setLimitStart(bean.getLimitStart());
        callCenterBaseRequest.setLimitEnd(bean.getLimitSize());

        //赋值用
        callCenterBaseRequest.setUserName(user.getUsername());
        callCenterBaseRequest.setMobile(user.getMobile());

        result = this.couponService.selectCouponBackMoneyList(callCenterBaseRequest);
        return result;
    }
}