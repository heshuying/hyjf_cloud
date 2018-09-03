package com.hyjf.cs.trade.controller.api.invest;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.api.ApiRepayListRequest;
import com.hyjf.am.vo.api.ApiRepayListCustomizeVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.ApiRepayListRequestBean;
import com.hyjf.cs.trade.bean.ResultApiBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.invest.UserInvestService;
import com.hyjf.cs.trade.util.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 第三方查询投资信息,获取回款记录
 * @version UserInvestController, v0.1 2018/8/30 17:07
 * @Author: Zha Daojian
 */

@Api(value = "api端-获取回款记录接口",tags = "api端-获取回款记录接口")
@RestController
@RequestMapping("/hyjf-api/server/invest")
public class UserInvestController extends BaseTradeController {

    @Autowired
    private UserInvestService userInvestService;
    @PostMapping("/repayList")
    @ApiParam(required = true, name = "repaymentInfoList", value = " 第三方查询投资信息,获取回款记录")
    @ApiOperation(value = " 第三方查询投资信息,获取回款记录", httpMethod = "POST", notes = " 第三方查询投资信息,获取回款记录")
    public ResultApiBean repayList(@RequestBody ApiRepayListRequestBean bean) {
        // 验证
        CheckUtil.check(Validator.isNotNull(bean.getInstCode()), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isNotNull(bean.getStartTime()), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isNotNull(bean.getEndTime()), MsgEnum.STATUS_CE000001);

        logger.info("bean:{}", JSONObject.toJSONString(bean));

        // 验签
        CheckUtil.check(SignUtil.verifyRequestSign(bean, "repayList"), MsgEnum.ERR_SIGN);

        ApiRepayListRequest request = new ApiRepayListRequest();
        BeanUtils.copyProperties(bean, request);

        // 返回查询结果
        return new ResultApiBean<List<ApiRepayListCustomizeVO>>(userInvestService.searchRepayList(request));
    }
}
