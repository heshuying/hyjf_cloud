/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.message;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.admin.SmsCodeCustomizeResponse;
import com.hyjf.am.response.user.SmsCodeResponse;
import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.resquest.user.SmsCodeRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.user.dao.model.customize.SmsCodeCustomize;
import com.hyjf.am.user.service.admin.message.SmsCodeService;
import com.hyjf.am.vo.admin.SmsCodeCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    /**
     * 校验千乐登录验证码
     * @param request
     * @return
     */
    @RequestMapping("/qianle_check")
    public IntegerResponse qianleCheckMobileCode(@RequestBody @Valid SmsCodeRequest request) {
        IntegerResponse response = new IntegerResponse();
        logger.info("onlyCheckMobileCode...param is :{}", JSONObject.toJSONString(request));
        String mobile = request.getMobile();
        String verificationCode = request.getVerificationCode();
        int i = smsCodeService.checkQianleMobileCode(mobile, verificationCode);
        response.setResultInt(i);
        return response;
    }

    /**
     * 短信验证码保存
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public SmsCodeResponse saveSmsCode(@RequestBody @Valid SmsCodeRequest request) {
        SmsCodeResponse response = new SmsCodeResponse();
        int cnt = smsCodeService.save(request.getMobile(), request.getVerificationType(), request.getVerificationCode(),
                request.getPlatform(), request.getStatus());
        response.setCnt(cnt);
        return response;
    }
}
