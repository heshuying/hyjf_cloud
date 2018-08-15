/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.message;

import com.hyjf.admin.controller.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fq
 * @version SmsCodeController, v0.1 2018/8/14 19:49
 */
@Api(description = "发送短信", tags = "发送短信")
@RestController
@RequestMapping("/hyjf-admin/message/message")
public class SmsCodeController extends BaseController {

   /* @ApiOperation(value = "初始化", notes = "初始化")
    @PostMapping("/init")

    }*/
}
