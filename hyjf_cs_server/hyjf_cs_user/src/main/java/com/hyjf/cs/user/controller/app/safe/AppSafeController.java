/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.safe;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.safe.SafeService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangqingqing
 * @version AppUserController, v0.1 2018/6/11 14:51
 */

@Api(value = "app端用户接口")
@RestController
@RequestMapping("/app/user/appUser")
public class AppSafeController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(AppSafeController.class);

    @Autowired
    private SafeService safeService;

    /**
     * 上传头像
     *
     * @param request
     * @param
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/uploadAvatarAction", produces = "application/json; charset=utf-8")
    public JSONObject uploadAvatarAction(@RequestHeader String key, @RequestHeader(value = "token") String token, HttpServletRequest request) {
        JSONObject ret = new JSONObject();
        ret.put("request", "/user/appUser/uploadAvatarAction");
        // 转型为MultipartHttpRequest(重点的所在)
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
        MultipartFile iconImg = safeService.checkParam(multipartRequest, key, token);
        try {
            String iconUrl = safeService.updateAvatar(token, iconImg);
            ret.put("iconUrl", iconUrl);
            ret.put("status", "0");
            ret.put("statusDesc", "头像上传成功");
        } catch (Exception e) {
            throw new CheckException(MsgEnum.STATUS_EV000002);
        }
        return ret;
    }

}
