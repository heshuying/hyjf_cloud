/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.message;

import com.hyjf.am.response.admin.SmsCodeCustomizeResponse;
import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.SmsCodeCustomize;
import com.hyjf.am.trade.service.admin.message.SmsCodeService;
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
 * @version SmsCodeController, v0.1 2018/8/20 19:02
 */
@RestController
@RequestMapping("/am-trade/sms_code")
public class SmsCodeController extends BaseController {
    @Autowired
    private SmsCodeService smsCodeService;

    /**
     * 筛选符合条件的用户
     * @param request
     * @return
     */
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
