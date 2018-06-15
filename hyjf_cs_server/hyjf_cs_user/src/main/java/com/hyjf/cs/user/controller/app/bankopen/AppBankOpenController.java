package com.hyjf.cs.user.controller.app.bankopen;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.user.bean.OpenAccountPageBean;
import com.hyjf.cs.user.constants.OpenAccountError;
import com.hyjf.cs.user.constants.RegisterError;
import com.hyjf.cs.user.result.ApiResult;
import com.hyjf.cs.user.result.AppResult;
import com.hyjf.cs.user.service.bankopen.BankOpenService;
import com.hyjf.cs.user.vo.BankOpenVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author sunss
 */
@Api(value = "app端用户开户")
@Controller
@RequestMapping("/app/user/open")
public class AppBankOpenController {
    private static final Logger logger = LoggerFactory.getLogger(AppBankOpenController.class);

    @Autowired
    private BankOpenService bankOpenService;


    /**
     * 获取开户信息
     *
     * @return
     * @Author: sunss
     */
    @ApiOperation(value = "app端获取开户信息", notes = "获取开户信息")
    @PostMapping(value = "/userInfo", produces = "application/json; charset=utf-8")
    public AppResult<String> userInfo(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
        logger.info("openAccount userInfo start, token is :{}", token);
        AppResult<String> result = new AppResult<String>();
        UserVO userVO = bankOpenService.getUsers(token);
        if (userVO != null) {
            logger.info("openAccount userInfo, success, userId is :{}", userVO.getUserId());
            String mobile = userVO.getMobile();
            if (StringUtils.isEmpty(mobile)) {
                mobile = "";
            }
            result.setResult(mobile);
        } else {
            logger.error("openAccount userInfo failed...");
            result.setStatus(ApiResult.STATUS_FAIL);
            result.setStatusDesc(RegisterError.REGISTER_ERROR.getMessage());
        }
        return result;
    }

    @ApiOperation(value = "app端用户开户", notes = "app端用户开户")
    @PostMapping(value = "/openBankAccount")
    public ModelAndView openBankAccount(@RequestHeader(value = "token", required = true) String token, @RequestBody @Valid BankOpenVO bankOpenVO, HttpServletRequest request) {
        logger.info("app openBankAccount start, bankOpenVO is :{}", JSONObject.toJSONString(bankOpenVO));
        String platform = request.getParameter("platform");
        ModelAndView reuslt = new ModelAndView();
        // 获取登录信息
        UserVO user = bankOpenService.getUsers(token);
        // 检查参数
        bankOpenService.checkRequestParam(user, bankOpenVO);
        // 拼装参数 调用江西银行
        // 同步调用路径
        OpenAccountPageBean openBean = new OpenAccountPageBean();

        try {
            PropertyUtils.copyProperties(openBean, bankOpenVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        openBean.setChannel(BankCallConstant.CHANNEL_APP);
        openBean.setUserId(user.getUserId());
        openBean.setIp(CustomUtil.getIpAddr(request));
        openBean.setCoinstName("汇盈金服");
        openBean.setClientHeader(ClientConstants.CLIENT_HEADER_APP);
        // 组装调用江西银行的MV
        reuslt = bankOpenService.getOpenAccountMV(openBean);
        //保存开户日志  银行卡号不必传了
        int uflag = this.bankOpenService.updateUserAccountLog(user.getUserId(), user.getUsername(), openBean.getMobile(), openBean.getOrderId(), platform, openBean.getTrueName(), openBean.getIdNo(), "");
        if (uflag == 0) {
            logger.info("保存开户日志失败,手机号:[" + openBean.getMobile() + "],用户ID:[" + user.getUserId() + "]");
            throw new ReturnMessageException(OpenAccountError.SYSTEM_ERROR);
        }
        logger.info("开户end");
        return reuslt;
    }

    /**
     * APP开户同步跳转地址
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "app端用户同步回调", notes = "app端用户开户")
    @PostMapping(value = "/return")
    public Map<String, String> returnPage(HttpServletRequest request, @RequestHeader(value = "token", required = true) String token) {
        String isSuccess = request.getParameter("isSuccess");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("token");
        }
        logger.info("app端开户同步请求,token:{},isSuccess:{}", token, isSuccess);
        Map<String, String> result = bankOpenService.openAccountReturn(token, isSuccess);
        logger.info("app端开户同步请求返回值：{}", JSONObject.toJSONString(result));
        return result;
    }

    /**
     * 页面开户异步处理
     *
     * @param bean
     * @return
     */
    @ApiOperation(value = "app端页面开户异步处理", notes = "页面开户异步处理")
    @PostMapping("/bgReturn")
    public BankCallResult openAccountBgReturn(@RequestBody @Valid BankCallBean bean, @RequestParam("phone") String mobile) {
        logger.info("开户异步处理start,userId:{}", bean.getLogUserId());
        bean.setMobile(mobile);
        BankCallResult result = bankOpenService.openAccountBgReturn(bean);
        return result;
    }

}