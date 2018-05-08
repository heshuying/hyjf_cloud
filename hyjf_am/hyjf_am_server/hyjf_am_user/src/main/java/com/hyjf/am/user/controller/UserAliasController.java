/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller;

import com.hyjf.am.response.user.UserAliasResponse;
import com.hyjf.am.user.dao.model.auto.MobileCode;
import com.hyjf.am.user.service.UserAliasService;
import com.hyjf.am.vo.user.UserAliasVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fuqiang
 * @version UserAliasController, v0.1 2018/5/8 10:50
 */
@RestController
@RequestMapping("/am-user/userAlias")
public class UserAliasController {

    private static Logger logger = LoggerFactory.getLogger(UserAliasController.class);

    @Autowired
    private UserAliasService userAliasService;

    /**
     * 根据手机号查询推送别名
     *
     * @param mobile
     * @return
     */
    @RequestMapping("/findAliasByMobile/{mobile}")
    public UserAliasResponse findAliasByMobile(@PathVariable String mobile) {
        logger.info("根据手机号查询推送别名开始... mobile is :{}", mobile);
        UserAliasResponse response = new UserAliasResponse();
        UserAliasVO userAliasVO = null;
        MobileCode mobileCode = userAliasService.findAliasByMobile(mobile);
        if (mobileCode != null) {
            userAliasVO = new UserAliasVO();
            BeanUtils.copyProperties(mobileCode, userAliasVO);
        }
        logger.info("userAliasVO is :{}", userAliasVO);
        response.setResult(userAliasVO);
        return response;
    }
}
