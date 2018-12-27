/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.auth.authrequire;

import com.hyjf.am.vo.trade.ProtocolTemplateVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.enums.ProtocolEnum;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.user.bean.AuthBean;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.controller.web.auth.mergeauth.MergeAuthPagePlusController;
import com.hyjf.cs.user.service.auth.AuthService;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author dangzw
 * @version AuthRequireController, v0.1 2018/12/26 14:11
 */
@Api(tags = {"web端-多合一授权（新）"})
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-web/user/auth/auth_require")
public class AuthRequireController extends BaseUserController {
    private static final Logger logger = LoggerFactory.getLogger(MergeAuthPagePlusController.class);
    //授权须知页面路径
    private static final String AUTH_REQUIRE_PAGE_PATH = "/user/autoplus/authorization";
    private static final String AUTH_REQUIRE_JUMP = "/jump";
    private static final String AUTH_REQUIRE_PATH = "/hyjf-web/user/auth/auth_require";
    @Autowired
    private AuthService authService;

    /**
     * 跳转授权须知接口
     * @param userId
     * @return
     */
    @RequestMapping("/jump")
    public ModelAndView jumpAuthRequirePage(@RequestHeader(value = "userId") Integer userId) {
        logger.info("跳转授权须知页面开始", "className："+ this.getClass().getName() + "methodPath："+ AUTH_REQUIRE_PATH + AUTH_REQUIRE_JUMP);
        logger.info("授权须知参数userId：{}", userId);
        ModelAndView modelAndView = new ModelAndView(AUTH_REQUIRE_PAGE_PATH);
        // 验证请求参数
        CheckUtil.check(userId != null,MsgEnum.ERR_USER_NOT_LOGIN);
        UserVO user = this.authService.getUsersById(userId);
        checkUserMessage(user);
        UserInfoVO userInfo = authService.getUserInfo(userId);
        //1、出借人（投资人）2、借款人3、担保机构授权
        modelAndView.addObject("roleId", userInfo.getRoleId());
        //协议名称 动态获得
        List<ProtocolTemplateVO> list = authService.getProtocolTypes();
        if(CollectionUtils.isNotEmpty(list)){
            //是否在枚举中有定义
            for (ProtocolTemplateVO p : list) {
                String protocolType = p.getProtocolType();
                String alia = ProtocolEnum.getAlias(protocolType);
                if (alia != null){
                    modelAndView.addObject(alia, p.getDisplayName());
                }
            }
        }
        logger.info("授权须知接口查询结束", "className："+ this.getClass().getName() + "methodPath："+ AUTH_REQUIRE_PATH + AUTH_REQUIRE_JUMP);
        return modelAndView;
    }

    private void checkUserMessage(UserVO user) {
        CheckUtil.check(user != null, MsgEnum.ERR_USER_NOT_EXISTS);
        // 判断用户是否开户
        CheckUtil.check(user.getBankOpenAccount() != 0,MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        // 判断用户是否设置过交易密码
        CheckUtil.check(user.getIsSetPassword() != 0,MsgEnum.ERR_TRADE_PASSWORD_NOT_SET);
        // 判断是否授权过
        CheckUtil.check(!authService.checkIsAuth(user.getUserId(), AuthBean.AUTH_TYPE_MERGE_AUTH),MsgEnum.ERR_AUTHORIZE_REPEAT);
    }
}
