/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.message;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.message.SmsCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fq
 * @version SmsCodeController, v0.1 2018/8/20 19:02
 */
@RestController
@RequestMapping("/am-trade/smsCode")
public class SmsCodeController extends BaseController {
    @Autowired
    private SmsCodeService smsCodeService;

    /**
     * 筛选符合条件的用户
     * @param request
     * @return
     */
    @RequestMapping("/queryUser")
    public Response queryUser(@RequestBody SmsCodeUserRequest request) {
        Response response = new Response();
        List<String> list = smsCodeService.queryUser(request);
        if (!CollectionUtils.isEmpty(list)) {
            response.setResultList(list);
        }
        return response;
    }

    /**
     * 筛选符合条件的用户数量
     * @param request
     * @return
     */
    @RequestMapping("/countUser")
    public IntegerResponse countUser(@RequestBody SmsCodeUserRequest request) {
        int count = smsCodeService.countUser(request);
        return new IntegerResponse(count);
    }
}
