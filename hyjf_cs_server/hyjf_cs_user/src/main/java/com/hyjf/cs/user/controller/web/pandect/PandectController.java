/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.pandect;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.pandect.PandectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author zhangqingqing
 * @version PandectController, v0.1 2018/6/21 14:31
 */
@Api(value = "web端账户总览",tags = "web端-账户总览")
@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/hyjf-web/user")
public class PandectController extends BaseUserController{

    @Autowired
    PandectService pandectService;

    /**
     * 账户总览
     *
     * @param
     * @return
     */
    @ApiOperation(value = "账户总览", notes = "账户总览")
    @PostMapping(value = "/pandect")
        public String pandect(@RequestHeader(value = "userId") Integer userId,Model model) {
            WebResult<Map<String,Object>> result = new WebResult<>();
//            UserVO user = pandectService.getUsersById(userId);
//            CheckUtil.check(user!=null, MsgEnum.ERR_USER_NOT_LOGIN);
            JSONObject map = pandectService.pandect(userId);
            result.setData(map);
            model.addAttribute("datas",result);
        return "pandect";
    }
}
