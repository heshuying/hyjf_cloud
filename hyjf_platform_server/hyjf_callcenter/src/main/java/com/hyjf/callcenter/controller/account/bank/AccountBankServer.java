/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.controller.account.bank;

import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.beans.AccountBankBean;
import com.hyjf.callcenter.beans.AccountBankDefine;
import com.hyjf.callcenter.beans.ResultListBean;
import com.hyjf.callcenter.beans.UserBean;
import com.hyjf.callcenter.controller.base.CallcenterBaseController;
import com.hyjf.callcenter.result.BaseResultBean;
import com.hyjf.callcenter.service.AccountBankService;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author wangjun
 * @version AccountBankServer, v0.1 2018/6/6 13:38
 */
@Controller
@RequestMapping(value = AccountBankDefine.REQUEST_MAPPING)
public class AccountBankServer extends CallcenterBaseController {
    @Autowired
    private AccountBankService accountBankService;

    /**
     * 呼叫中心接口调用入口
     * @param request
     * @param response
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = AccountBankDefine.INIT_TIED_CARD_ACTION, method = RequestMethod.POST)
    public ResultListBean getContentOfAccountBank(HttpServletRequest request, HttpServletResponse response,
                                                  @RequestBody UserBean bean) {
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
        //根据用户信息查询江西银行绑卡关系
        List<BankCardVO> recordList = accountBankService.getTiedCardOfAccountBank(user);
        if (recordList == null) {
            result.statusMessage(BaseResultBean.STATUS_FAIL,"该用户在江西银行未绑卡！");
            return result;
        }

        //编辑返回信息
        for (BankCardVO recordBean : recordList) {
            AccountBankBean returnBean = new AccountBankBean();

            //检索bean→返回bean
            BeanUtils.copyProperties(recordBean, returnBean);
            //用户名
            returnBean.setUserName(user.getUsername());
            //手机号
            returnBean.setMobile(user.getMobile());
            //添加时间
            if (recordBean.getCreateTime()!=null) {
                returnBean.setCreateTime(GetDate.formatDateTime(recordBean.getCreateTime().getTime()));
            }

            result.getDataList().add(returnBean);
        }
        //*************各自业务结束***************
        result.statusMessage(BaseResultBean.STATUS_SUCCESS, BaseResultBean.STATUS_SUCCESS_DESC);
        return result;
    }
}
