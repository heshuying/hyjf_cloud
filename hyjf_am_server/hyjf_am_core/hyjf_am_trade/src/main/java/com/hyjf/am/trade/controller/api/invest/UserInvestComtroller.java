package com.hyjf.am.trade.controller.api.invest;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.ApiRepayListResponse;
import com.hyjf.am.resquest.api.ApiRepayListRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.api.UserInvest.UserInvestService;
import com.hyjf.am.vo.api.ApiRepayListCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * api端-获取回款记录接口，测试分支AEMS-S-F-20181203
 * @version UserInvestComtroller, v0.1 2018/9/1 14:58
 * @Author: Zha Daojian
 */
@RestController
@RequestMapping("/am-trade/invest")
public class UserInvestComtroller  extends BaseController {

    @Autowired
    private UserInvestService userInvestService;
    @RequestMapping(value = "/searchRepayList")
    public ApiRepayListResponse searchRepayList(@RequestBody @Valid ApiRepayListRequest request){
        logger.info("request:" +JSONObject.toJSON(request));
        ApiRepayListResponse response = new ApiRepayListResponse();
        String returnCode = Response.FAIL;
        request.setInstCode("");
        List<ApiRepayListCustomizeVO> manageList = userInvestService.searchRepayList(request);
        if (manageList!=null && manageList.size() > 0) {
            if (!CollectionUtils.isEmpty(manageList)) {
                response.setResultList(manageList);
                returnCode = Response.SUCCESS;
            }
        }
        response.setRtn(returnCode);
        return response;
    }

}
