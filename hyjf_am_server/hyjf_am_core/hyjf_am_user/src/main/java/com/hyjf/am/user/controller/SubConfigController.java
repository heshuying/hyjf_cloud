package com.hyjf.am.user.controller;

import com.hyjf.am.response.user.UserInfoCustomizeResponse;
import com.hyjf.am.user.service.SubConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author by xiehuili on 2018/7/10.
 */
@RestController
@RequestMapping("/am-user/config")
public class SubConfigController extends BaseController {
    @Autowired
    private SubConfigService subConfigService;

    /**
     * 保证金配置，根据用户名称查询用户信息
     *
     * @param userName
     * @return
     */
    @RequestMapping(value = "/queryUserInfoByUserName/{userName}", method = RequestMethod.POST)
    public UserInfoCustomizeResponse selectUserInfoByUserName(@PathVariable String userName) {
        return subConfigService.selectUserInfoByUserName(userName);
    }

}
