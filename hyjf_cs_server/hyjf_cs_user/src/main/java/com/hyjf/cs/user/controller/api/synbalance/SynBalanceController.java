package com.hyjf.cs.user.controller.api.synbalance;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.cs.user.bean.SynBalanceRequestBean;
import com.hyjf.cs.user.bean.SynBalanceResultBean;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.synbalance.SynBalanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pangchengchao
 * @version SynBalanceController, v0.1 2018/6/12 18:32
 */
@Api(value = "api端-同步余额信息接口",tags = "api端-同步余额信息接口")
@Controller
@RequestMapping("/hyjf-api/server/synbalance")
public class SynBalanceController extends BaseUserController {
    @Autowired
    private SynBalanceService synBalanceService;
    @Autowired
    SystemConfig systemConfig;
    @ApiOperation(value = "第三方同步余额", notes = "同步余额")
    @PostMapping(value = "/synbalance.do")
    @ResponseBody
    public SynBalanceResultBean synBalance(@RequestBody SynBalanceRequestBean synBalanceRequestBean, HttpServletRequest request) {
        logger.info(synBalanceRequestBean.getAccountId()+"第三方同步余额开始-----------------------------");
        logger.info("第三方请求参数："+JSONObject.toJSONString(synBalanceRequestBean));
        String ip = GetCilentIP.getIpAddr(request);
        SynBalanceResultBean resultBean = synBalanceService.synBalance(synBalanceRequestBean,ip);
        return resultBean;
    }

}
