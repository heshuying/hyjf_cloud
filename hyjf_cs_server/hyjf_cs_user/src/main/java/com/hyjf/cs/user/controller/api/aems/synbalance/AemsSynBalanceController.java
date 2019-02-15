package com.hyjf.cs.user.controller.api.aems.synbalance;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.user.bean.*;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.aems.synbalance.AemsSynBalanceService;
import com.hyjf.cs.user.service.synbalance.SynBalanceService;
import com.hyjf.cs.user.util.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Zha Daojian
 * @date 2018/12/14 15:29
 * @param 
 * @return 
 **/
@Api(value = "api端-AEMS用户余额查询接口",tags = "api端-AEMS用户余额查询接口")
@Controller
@RequestMapping("/hyjf-api/aems/synbalance")
public class AemsSynBalanceController extends BaseUserController {
    @Autowired
    private AemsSynBalanceService synBalanceService;

    @ApiOperation(value = "AEMS用户余额查询接口", notes = "AEMS用户余额查询接口")
    @PostMapping(value = "/synbalance")
    @ResponseBody
    public AemsSynBalanceResultBean synBalance(@RequestBody AemsSynBalanceRequestBean synBalanceRequestBean, HttpServletRequest request) {
        logger.info(synBalanceRequestBean.getAccountId()+"第三方同步余额开始-----------------------------");
        logger.info("第三方请求参数："+JSONObject.toJSONString(synBalanceRequestBean));
        //手机号未填写
        CheckUtil.check(StringUtils.isNotEmpty(synBalanceRequestBean.getAccountId())||StringUtils.isNotEmpty(synBalanceRequestBean.getInstCode()), MsgEnum.STATUS_ZC000001);
        CheckUtil.check(SignUtil.AEMSVerifyRequestSign(synBalanceRequestBean, "/aems/synbalance/synbalance"), MsgEnum.STATUS_CE000002);

        String ip = GetCilentIP.getIpAddr(request);
        AemsSynBalanceResultBean resultBean = synBalanceService.synBalance(synBalanceRequestBean,ip);
        return resultBean;
    }

}
