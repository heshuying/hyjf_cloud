/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.authquery;

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

@Api(value = "api端授权状态查询",tags = "api端-授权状态查询")
@RestController
@RequestMapping("/hyjf-api/server/authState")
public class AutoStateQueryController extends BaseUserController {

    @Autowired
    AutoStateQueryService autoStateQueryService;

    @ApiOperation(value = " 授权状态查询",notes = " 授权状态查询")
    @PostMapping(value = "query.do")
    public  AutoStateQueryResultBean queryStatus(@RequestBody @Valid AutoStateQueryRequest autoStateQuery) {
        AutoStateQueryResultBean resultBean = autoStateQueryService.queryStatus(autoStateQuery);
        return resultBean;

    }

}
