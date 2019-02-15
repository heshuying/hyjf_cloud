/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.api.aems.invest;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.api.ApiRepayListRequest;
import com.hyjf.am.vo.api.ApiRepayListCustomizeVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.trade.bean.AemsRepayListRequestBean;
import com.hyjf.cs.trade.bean.ResultApiBean;
import com.hyjf.cs.trade.bean.api.ApiInvestListReqBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.aems.invest.AemsBorrowListService;
import com.hyjf.cs.trade.service.svrcheck.CommonSvrChkService;
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
 * 第三方查询投资记录,回款记录查询
 *
 * @author liuyang
 * @version AemsUserInvestController, v0.1 2018/12/13 16:48
 */
@Api(value = "AEMS系统-获取回款记录接口",tags = "AEMS系统-获取回款记录接口")
@RestController
@RequestMapping("/hyjf-api/aems/invest")
public class AemsUserInvestController extends BaseTradeController {


    @Autowired
    private AemsBorrowListService userInvestService;

    @Autowired
    private CommonSvrChkService commonSvrChkService;


    @PostMapping("/repayList")
    @ApiParam(required = true, name = "repaymentInfoList", value = "第三方查询投资信息,获取回款记录")
    @ApiOperation(value = " 第三方查询投资信息,获取回款记录", httpMethod = "POST", notes = " 第三方查询投资信息,获取回款记录")
    public ResultApiBean repayList(@RequestBody AemsRepayListRequestBean bean) {
        logger.info("----------------------AEMS系统查询投资信息,获取回款记录AemsRepayListRequest："+ JSONObject.toJSON(bean));
        // 验证
        CheckUtil.check(Validator.isNotNull(bean.getInstCode()), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isNotNull(bean.getStartTime()), MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isNotNull(bean.getEndTime()), MsgEnum.STATUS_CE000001);

        logger.info("api端-获取回款记录接口bean:{}", JSONObject.toJSONString(bean));

        // 验签
        CheckUtil.check(SignUtil.AEMSVerifyRequestSign(bean, "/aems/invest/repayList"), MsgEnum.ERR_SIGN);

        ApiRepayListRequest request = new ApiRepayListRequest();
        BeanUtils.copyProperties(bean, request);

        // 返回查询结果
        return new ResultApiBean<List<ApiRepayListCustomizeVO>>(userInvestService.searchRepayList(request));
    }

    @ApiOperation(value = "查询投资信息的列表", notes = "查询投资信息的列表")
    @PostMapping("/investList.do")
    public ApiResult investList(@RequestBody ApiInvestListReqBean bean) {
        ApiResult result = new ApiResult();
        // 返回查询结果
        result = userInvestService.getInvestList(bean);
        return result;
    }
}
