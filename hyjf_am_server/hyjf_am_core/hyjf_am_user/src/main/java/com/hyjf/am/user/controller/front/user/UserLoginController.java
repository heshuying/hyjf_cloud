package com.hyjf.am.user.controller.front.user;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.resquest.trade.UserTenderRequest;
import com.hyjf.am.user.service.front.user.UserLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author xiasq
 * @version UserLoginController, v0.1 2019/5/5 13:56
 */
@RestController
@RequestMapping("/am-user/userLogin")
public class UserLoginController {
    Logger logger = LoggerFactory.getLogger(UserLoginController.class);
    @Autowired
    private UserLoginService userLoginService;

    /**
     * 查询用户在某段时间内是否有过登录
     * @param request
     * @return
     */
    @PostMapping("/hasLogin")
    public BooleanResponse hasLogin(@RequestBody @Valid UserTenderRequest request) {
        logger.info("hasLogin, request is: {}", request);
        BooleanResponse response = new BooleanResponse();
        boolean hasLogin = userLoginService.hasLogin(request.getUserId(), request.getStartDate(), request.getEndDate());
        logger.info("user: {} hasLogin: {}", request.getUserId(), hasLogin);
        response.setResultBoolean(hasLogin);
        return response;
    }
}
