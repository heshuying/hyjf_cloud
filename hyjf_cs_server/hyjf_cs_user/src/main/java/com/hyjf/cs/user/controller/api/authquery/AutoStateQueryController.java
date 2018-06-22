/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.authquery;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.common.enums.MsgEnum;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.user.bean.AutoStateQueryRequest;
import com.hyjf.cs.user.bean.AutoStateQueryResultBean;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.authquery.AutoStateQueryService;

import io.swagger.annotations.Api;

/**
 * @author zhangqingqing
 * @version AutoStateQueryController, v0.1 2018/6/12 9:21
 */

@Api(value = "api端授权状态查询")
@RestController
@RequestMapping("/api/user")
public class AutoStateQueryController extends BaseUserController {

    @Autowired
    AutoStateQueryService autoStateQueryService;

    @PostMapping(value = "query", produces = "application/json; charset=utf-8")
    public  ApiResult<AutoStateQueryResultBean> queryStatus(@RequestBody @Valid AutoStateQueryRequest autoStateQuery) {
        ApiResult<AutoStateQueryResultBean> result = new ApiResult<>();
        AutoStateQueryResultBean resultBean = autoStateQueryService.queryStatus(autoStateQuery);
        if (null != resultBean) {
            result.setData(resultBean);
        } else {
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(MsgEnum.ERR_USER_REGISTER.getMsg());
        }
        return result;
    }

}
