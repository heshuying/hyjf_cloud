/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.trans;

import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.user.bean.BankMobileModifyBean;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.trans.MobileModifyService;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author PC-LIUSHOUYI
 * @version WechatMobileModifyController, v0.1 2019/5/10 10:04
 */
@Api(value = "修改手机号", tags = "weChat端-修改手机号")
@RestController
@RequestMapping("/hyjf-wechat/wx/user")
public class WechatMobileModifyController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(WechatMobileModifyController.class);

    @Autowired
    MobileModifyService mobileModifyService;

    /**
     * 修改银行预留手机号
     *
     * @param userId
     * @return
     * @Author liushouyi
     */
    @ApiOperation(value = "app银行预留手机号码修改", notes = "app银行预留手机号码修改")
    @PostMapping(value = "/bankMobileModify", produces = "application/json; charset=utf-8")
    @ResponseBody
    public AppResult<Object> bankMobileModify(@RequestHeader(value = "userId") int userId, @RequestHeader(value = "sign") String sign, HttpServletRequest request) {
        logger.info("app银行预留手机号修改,userId:" + userId);
        AppResult<Object> result = new AppResult<Object>();
        // 获取用户信息
        UserVO user = this.mobileModifyService.getUsersById(userId);
        if (user == null) {
            // 获取用户信息失败
            throw new ReturnMessageException(MsgEnum.ERR_USER_NOT_LOGIN);
        }
        BankOpenAccountVO bankOpenAccountVO = this.mobileModifyService.getBankOpenAccount(userId);
        if (null == bankOpenAccountVO || StringUtils.isBlank(bankOpenAccountVO.getAccount())) {
            // 用户未开户
            throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }

        BankMobileModifyBean bean = new BankMobileModifyBean();
        bean.setAccountId(bankOpenAccountVO.getAccount());
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        bean.setUserId(user.getUserId());
        bean.setIp(CustomUtil.getIpAddr(request));
        // 0：web 1:wechat 2:android 3:ios
        bean.setPlatform(ClientConstants.WECHAT_CLIENT+"");
        bean.setClientHeader(ClientConstants.CLIENT_HEADER_WX);
        bean.setBankMobile(user.getBankMobile());

        // 组装参数
        Map<String, Object> data = mobileModifyService.getBankMobileModify(bean, sign);
        result.setData(data);
        // 插入修改日志表
        boolean re = mobileModifyService.insertBankMobileModify(bankOpenAccountVO.getAccount(), user.getBankMobile(), userId);
        if (!re) {
            logger.info("app保存修改预留手机号日志失败,手机号:[" + user.getBankMobile() + "],用户ID:[" + user.getUserId() + "]");
            throw new CheckException(MsgEnum.STATUS_CE999999);
        }
        logger.info("app修改银行预留手机号结束。");
        return result;
    }


}
