/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.message;

import com.hyjf.am.response.admin.SmsCodeCustomizeResponse;
import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.customize.SmsCodeCustomize;
import com.hyjf.am.user.service.admin.message.SmsCodeService;
import com.hyjf.am.vo.admin.SmsCodeCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fq
 * @version SmsCodeController, v0.1 2018/8/20 20:27
 */
@RestController
@RequestMapping("/am-user/sms_code")
public class SmsCodeUserController extends BaseController {
    @Autowired
    private SmsCodeService smsCodeService;

    @RequestMapping("/query_user")
    public SmsCodeCustomizeResponse queryUser(@RequestBody SmsCodeUserRequest request) {
        SmsCodeCustomizeResponse response = new SmsCodeCustomizeResponse();
        List<SmsCodeCustomize> list = smsCodeService.queryUser(request);
        if (!CollectionUtils.isEmpty(list)) {
            List<SmsCodeCustomizeVO> voList = CommonUtils.convertBeanList(list, SmsCodeCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;

    }
}
