package com.hyjf.am.user.controller.admin.margin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.user.UserInfoCustomizeResponse;
import com.hyjf.am.resquest.admin.AdminSubConfigRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.admin.margin.SubConfigService;
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
     * @param req
     * @return
     */
    @RequestMapping(value = "/queryUserInfoByUserName", method = RequestMethod.POST)
    public UserInfoCustomizeResponse selectUserInfoByUserName(@RequestBody AdminSubConfigRequest req) {
        return subConfigService.selectUserInfoByUserName(req.getUsername());
    }

}
