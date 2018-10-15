package com.hyjf.cs.user.controller.web.unbindcard;
import com.hyjf.cs.user.controller.BaseUserController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * 合规四期-解绑银行卡
 * @author nxl
 * @version WebUnBindCardPageController, v0.1 2018/10/15 14:26
 */
@Api(value = "web端-用户解绑卡接口(页面调用)",tags = "web端-用户解绑卡接口(页面调用)")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-web/user/deleteCardPage")
public class WebUnBindCardPageController extends BaseUserController{
}
