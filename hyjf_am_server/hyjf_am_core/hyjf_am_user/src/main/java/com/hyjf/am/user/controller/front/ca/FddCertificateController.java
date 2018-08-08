/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.ca;

import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.front.ca.FddCertificateService;
import com.hyjf.common.exception.MQException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * @author zhangqingqing
 * @version FddCertificateController, v0.1 2018/6/27 16:52
 */
@RestController
@RequestMapping("/am-user/batch")
public class FddCertificateController extends BaseController {
    @Autowired
    FddCertificateService fddCertificateService;

    @RequestMapping("/fddCertificate")
    public void fddCertificate() throws MQException, ParseException {
        logger.info("法大大电子签章CA认证...");
        fddCertificateService.fddCertificateAuthority();

    }
}
