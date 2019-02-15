/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.batch.operationaldata;

import com.hyjf.cs.user.service.batch.OperationalUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author yaoyong
 * @version OperationalUserDataController, v0.1 2018/12/18 17:20
 * 法大大电子签章批量做CA认证
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-user/batch")
public class OperationalUserDataController {

    @Autowired
    private OperationalUserService operationalUserService;
    private static final Logger logger = LoggerFactory.getLogger(OperationalUserDataController.class);

    @RequestMapping("/fddCertificate")
    public void fddCertificate() {
        logger.info("法大大电子签章CA认证...");
        operationalUserService.fddCertificate();
    }
}
