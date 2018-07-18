package com.hyjf.cs.user.controller.wechat.bankopen;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.common.bean.result.WeChatResult;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author sunss
 */
@Api(value = "微信端用户开户",description = "微信端用户开户")
@RestController
@RequestMapping("/wechat/user/open")
public class WeChatBankOpenController extends BaseUserController {
    private static final Logger logger = LoggerFactory.getLogger(WeChatBankOpenController.class);

    @Autowired
    private BankOpenService bankOpenService;

    /**
     * 获取开户信息
     *
     * @return
     * @Author: sunss
     */
    @ApiOperation(value = "微信端获取开户信息", notes = "微信端获取开户信息")
    @PostMapping(value = "/userInfo", produces = "application/json; charset=utf-8")
    public WeChatResult<String> userInfo(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
        logger.info("openAccount userInfo start, token is :{}", token);
        WeChatResult<String> result = new WeChatResult<String>();
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
            result.setStatusDesc(MsgEnum.ERR_SYSTEM_UNUSUAL.getMsg());
        }
        return result;
    }

    @ApiOperation(value = "微信端用户开户", notes = "微信端用户开户")
    @PostMapping(value = "/openBankAccount")
    public ModelAndView openBankAccount(@RequestHeader(value = "token", required = true) String token, @RequestBody @Valid BankOpenVO bankOpenVO, HttpServletRequest request) {
        logger.info("wechat openBankAccount start, bankOpenVO is :{}", JSONObject.toJSONString(bankOpenVO));
        ModelAndView reuslt = new ModelAndView();
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
        openBean.setChannel(BankCallConstant.CHANNEL_WEI);
        openBean.setUserId(user.getUserId());
        openBean.setIp(CustomUtil.getIpAddr(request));
        openBean.setClientHeader(ClientConstants.CLIENT_HEADER_WX);
        openBean.setPlatform(ClientConstants.WECHAT_CLIENT+"");
        // 组装调用江西银行的MV
        //reuslt = bankOpenService.getOpenAccountMV(openBean);
        //保存开户日志  银行卡号不必传了
        int uflag = this.bankOpenService.updateUserAccountLog(user.getUserId(), user.getUsername(), openBean.getMobile(), openBean.getOrderId(), CustomConstants.CLIENT_WECHAT, openBean.getTrueName(), openBean.getIdNo(), "");
        if (uflag == 0) {
            logger.info("保存开户日志失败,手机号:[" + openBean.getMobile() + "],用户ID:[" + user.getUserId() + "]");
            throw new ReturnMessageException(MsgEnum.ERR_SYSTEM_UNUSUAL);
        }
        logger.info("开户end");
        return reuslt;
    }

}