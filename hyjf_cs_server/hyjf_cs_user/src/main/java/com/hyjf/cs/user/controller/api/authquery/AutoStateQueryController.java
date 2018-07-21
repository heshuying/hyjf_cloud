/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.authquery;

import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.user.bean.AutoStateQueryRequest;
import com.hyjf.cs.user.bean.AutoStateQueryResultBean;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.authquery.AutoStateQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author zhangqingqing
 * @version AutoStateQueryController, v0.1 2018/6/12 9:21
 */

@Api(value = "api端授权状态查询",description = "api端-授权状态查询")
@RestController
@RequestMapping("/hyjf-api/user")
public class AutoStateQueryController extends BaseUserController {

    @Autowired
    AutoStateQueryService autoStateQueryService;

    @ApiOperation(value = " 授权状态查询",notes = " 授权状态查询")
    @PostMapping(value = "query", produces = "application/json; charset=utf-8")
    public  ApiResult<AutoStateQueryResultBean> queryStatus(@RequestBody @Valid AutoStateQueryRequest autoStateQuery) {
        ApiResult<AutoStateQueryResultBean> result = new ApiResult<>();
        AutoStateQueryResultBean resultBean = autoStateQueryService.queryStatus(autoStateQuery);
        if (null != resultBean) {
            result.setData(resultBean);
        } else {
            throw new CheckException(MsgEnum.STATUS_CE999999);
        }
        return result;
    }

}
