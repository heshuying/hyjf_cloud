package com.hyjf.cs.user.controller.app.bankopen;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.user.bean.OpenAccountPageBean;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.bankopen.BankOpenService;
import com.hyjf.cs.user.vo.BankOpenVO;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author sunss
 */
@Api(value = "app端用户开户",description = "app端-用户开户")
@Controller
@RequestMapping("/hyjf-app/user/open")
public class AppBankOpenController extends BaseUserController {
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
            result.setData(mobile);
        } else {
            logger.error("openAccount userInfo failed...");
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(MsgEnum.ERR_USER_INFO_GET.getMsg());
        }
        return result;
    }

    @ApiOperation(value = "app端用户开户", notes = "app端-用户开户")
    @PostMapping(value = "/openBankAccount")
    public AppResult<Object> openBankAccount(@RequestHeader(value = "token", required = true) String token, @RequestBody @Valid BankOpenVO bankOpenVO, HttpServletRequest request) {
        logger.info("app openBankAccount start, bankOpenVO is :{}", JSONObject.toJSONString(bankOpenVO));
        AppResult<Object> result = new AppResult<Object>();
        // 验证请求参数
        if (token == null) {
            throw new ReturnMessageException(MsgEnum.ERR_USER_NOT_LOGIN);
        }
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
        openBean.setClientHeader(ClientConstants.CLIENT_HEADER_APP);
        // 开户角色
        openBean.setIdentity(BankCallConstant.ACCOUNT_USER_IDENTITY_3);
        // 组装调用江西银行的MV
        Map<String,Object> data = bankOpenService.getOpenAccountMV(openBean);
        result.setData(data);
        //保存开户日志  银行卡号不必传了
        int uflag = this.bankOpenService.updateUserAccountLog(user.getUserId(), user.getUsername(), openBean.getMobile(), openBean.getOrderId(), bankOpenVO.getPlatform(), openBean.getTrueName(), openBean.getIdNo(), "");
        if (uflag == 0) {
            logger.info("保存开户日志失败,手机号:[" + openBean.getMobile() + "],用户ID:[" + user.getUserId() + "]");
            throw new ReturnMessageException(MsgEnum.ERR_SYSTEM_UNUSUAL);
        }
        logger.info("开户end");
        return result;
    }
}