/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.synbalance;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.service.synbalance.SynBalanceService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangqingqing
 * @version SynBalanceController, v0.1 2018/7/25 14:57
 */
@Api(value = "web端-同步余额",description = "web端-同步余额")
@RestController
@RequestMapping("/hyjf-web/user/bank/synbalance")
public class SynBalanceController {

    @Autowired
    SynBalanceService synBalanceService;
    /**
     * 用户同步余额
     */
    @PostMapping(value = "/init", produces = "application/json; charset=utf-8")
    public WebResult synBalance(@RequestHeader(value = "userId") Integer userId) {
        WebResult result = new WebResult();
        JSONObject ret = new JSONObject();
        UserVO user = synBalanceService.getUsersById(userId);
        CheckUtil.check(null!=user, MsgEnum.ERR_USER_NOT_LOGIN);
        JSONObject status=new JSONObject();
        CheckUtil.check(user.getBankOpenAccount()==1, MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        /***********同步线下充值记录 start***********/
        // TODO: 2018/7/25 pangchengchao 
        status= null;//CommonSoaUtils.synBalanceRetPost(user.getUserId());
        CheckUtil.check("成功".equals(status.get("statusDesc").toString()), MsgEnum.ERR_OBJECT_GET,"余额");
        //余额数据
        ret.put("info",status.get("bankBalance").toString());
        result.setData(ret);
        /***********同步线下充值记录 start***********/
        return result;
    }
}
