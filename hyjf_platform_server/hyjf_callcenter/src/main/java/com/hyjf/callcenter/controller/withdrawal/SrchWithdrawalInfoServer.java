/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.controller.withdrawal;

import com.hyjf.am.resquest.callcenter.CallCenterBaseRequest;
import com.hyjf.am.vo.callcenter.CallCenterWithdrawVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.ResultListBean;
import com.hyjf.callcenter.beans.SrchWithdrawalInfoBean;
import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.controller.base.CallcenterBaseController;
import com.hyjf.callcenter.result.BaseResultBean;
import com.hyjf.callcenter.service.SrchWithdrawalInfoService;
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
 * @version SrchWithdrawalInfoServer, v0.1 2018/6/15 11:25
 */
@Controller
@RequestMapping(value = "/hyjf-callcenter/withdrawal")
public class SrchWithdrawalInfoServer extends CallcenterBaseController {
    @Autowired
    private SrchWithdrawalInfoService srchWithdrawalInfoService;
    /**
     * 查询提现明细
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/srchWithdrawalInfo", method = RequestMethod.POST)
    public ResultListBean getContentOfWithdrawalInfo(@RequestBody UserBean bean) {
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
            callCenterBaseRequest.setUserId(user.getUserId());//精确
            callCenterBaseRequest.setLimitStart(bean.getLimitStart());
            callCenterBaseRequest.setLimitEnd(bean.getLimitSize());
            List<CallCenterWithdrawVO> recordList = this.srchWithdrawalInfoService.getWithdrawRecordList(callCenterBaseRequest);
            if (recordList == null) {
                result.statusMessage(BaseResultBean.STATUS_FAIL,"该用户没有提现记录！");
                return result;
            }
            //编辑返回信息
            for (CallCenterWithdrawVO recordBean : recordList) {
                SrchWithdrawalInfoBean returnBean = new SrchWithdrawalInfoBean();
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
