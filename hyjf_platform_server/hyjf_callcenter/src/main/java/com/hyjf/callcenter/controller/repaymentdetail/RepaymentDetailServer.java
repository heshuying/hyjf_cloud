/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.controller.repaymentdetail;

import com.hyjf.am.resquest.callcenter.CallCenterRepaymentRequest;
import com.hyjf.am.vo.callcenter.CallCenterHtjRepaymentDetailVO;
import com.hyjf.am.vo.callcenter.CallCenterHztRepaymentDetailVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.*;
import com.hyjf.callcenter.controller.base.CallcenterBaseController;
import com.hyjf.callcenter.result.BaseResultBean;
import com.hyjf.callcenter.service.RepaymentDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author wangjun
 * @version RepaymentDetailServer, v0.1 2018/6/11 11:38
 */
@Api(value = "查询还款明细")
@Controller
@RequestMapping(value = "/hyjf-callcenter/repaymentdetail")
public class RepaymentDetailServer extends CallcenterBaseController {
    @Autowired
    RepaymentDetailService repaymentDetailService;

    @ApiOperation(value = "查询还款明细（直投产品，含承接的债权）",
            notes = "按照用户名/手机号查询还款明细（直投产品，含承接的债权）")
    @ResponseBody
    @PostMapping(value = RepaymentDetailDefine.GET_HZT_REPAYMENT_DETAIL_LIST_ACTION, produces = "application/json; charset=utf-8")
    public ResultListBean getHztRepaymentDetailList(HttpServletRequest request, HttpServletResponse response,
                                                    @RequestBody UserBean bean) {
        ResultListBean result = new ResultListBean();

        //根据用户名或手机号取得用户信息
        UserVO user = this.getUser(bean, result);
        if (user == null) {
            result.setStatus(BaseResultBean.STATUS_FAIL);
            result.statusMessage(ResultListBean.STATUS_FAIL,"该用户不存在！");
            return result;
        }

        //*************各自业务开始***************
        //根据用户信息查询用户优惠券信息
        CallCenterRepaymentRequest callCenterRepaymentRequest = new CallCenterRepaymentRequest();
        callCenterRepaymentRequest.setUserId(user.getUserId());
        callCenterRepaymentRequest.setLimitStart(bean.getLimitStart());
        callCenterRepaymentRequest.setLimitEnd(bean.getLimitSize());
        List<CallCenterHztRepaymentDetailVO> recordList =
                this.repaymentDetailService.getHztRepaymentDetailList(callCenterRepaymentRequest);

        if (recordList == null) {
            result.statusMessage(BaseResultBean.STATUS_FAIL,"该用户无还款明细！");
            return result;
        }

        //编辑返回信息
        for (CallCenterHztRepaymentDetailVO recordBean : recordList) {
            RepaymentDetailHztBean returnBean = new RepaymentDetailHztBean();
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


    /**
     * 按照用户名/手机号查询还款明细（汇添金）
     * @param request
     * @param response
     * @param bean
     * @return
     */
    @ApiOperation(value = "查询还款明细（汇添金）", notes = "按照用户名/手机号查询还款明细（汇添金）")
    @ResponseBody
    @PostMapping(value = RepaymentDetailDefine.GET_HTJ_REPAYMENT_DETAIL_LIST_ACTION, produces = "application/json; charset=utf-8")
    public ResultListBean getHtjRepaymentDetailList(HttpServletRequest request, HttpServletResponse response,
                                                    @RequestBody UserBean bean) {
        ResultListBean result = new ResultListBean();

        //根据用户名或手机号取得用户信息
        UserVO user = this.getUser(bean, result);
        if (user == null) {
            result.setStatus(BaseResultBean.STATUS_FAIL);
            result.statusMessage(ResultListBean.STATUS_FAIL,"该用户不存在！");
            return result;
        }

        //*************各自业务开始***************
        //根据用户信息查询用户优惠券信息
        CallCenterRepaymentRequest callCenterRepaymentRequest = new CallCenterRepaymentRequest();
        callCenterRepaymentRequest.setUserId(user.getUserId());
        callCenterRepaymentRequest.setLimitStart(bean.getLimitStart());
        callCenterRepaymentRequest.setLimitEnd(bean.getLimitSize());
        List<CallCenterHtjRepaymentDetailVO> recordList = this.repaymentDetailService.getHtjRepaymentDetailList(
                callCenterRepaymentRequest);

        if (recordList == null) {
            result.statusMessage(BaseResultBean.STATUS_FAIL,"该用户无还款明细！");
            return result;
        }

        //编辑返回信息
        for (CallCenterHtjRepaymentDetailVO recordBean : recordList) {
            RepaymentDetailHtjBean returnBean = new RepaymentDetailHtjBean();
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
