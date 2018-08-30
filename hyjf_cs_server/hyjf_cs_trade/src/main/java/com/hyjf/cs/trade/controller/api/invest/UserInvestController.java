package com.hyjf.cs.trade.controller.api.invest;

import com.hyjf.cs.trade.controller.BaseTradeController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 第三方查询投资信息,获取回款记录
 * @version UserInvestController, v0.1 2018/8/30 17:07
 * @Author: Zha Daojian
 */

@Api(value = "api端-获取回款记录接口",tags = "api端-获取回款记录接口")
@RestController
@RequestMapping("/hyjf-api/server/invest")
public class UserInvestController extends BaseTradeController {
}
