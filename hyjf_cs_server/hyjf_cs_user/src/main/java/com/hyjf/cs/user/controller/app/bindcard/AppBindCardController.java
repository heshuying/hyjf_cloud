package com.hyjf.cs.user.controller.app.bindcard;

import com.hyjf.cs.user.controller.BaseUserController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * App端绑卡
 * @author hesy
 * @version AppBindCardController, v0.1 2018/7/19 9:34
 */
@Api(value = "app端-绑卡",description = "app端-绑卡")
@RestController
@RequestMapping("/hyjf-app/user/card")
public class AppBindCardController extends BaseUserController {

}
