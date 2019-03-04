/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.web.projectlist;

import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.*;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.projectlist.CacheService;
import com.hyjf.cs.trade.service.projectlist.WebProjectListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * web端项目列表
 *
 * @author liuyang
 * @version WebProjectListController, v0.1 2018/6/13 10:21
 */
@Api(tags = "web端-项目列表")
@RestController
@RequestMapping("/hyjf-web/test")
public class TestController extends BaseTradeController {
    private static final Logger logger = LoggerFactory.getLogger(WebProjectListController.class);

    @Autowired
    private WebProjectListService webProjectListService;

    @Autowired
    private CacheService cacheService;



    /**
     * web端新手标和散标标的详情
     * @author zhangyk
     * 原接口：com.hyjf.web.bank.web.borrow.BorrowController.searchProjectDetail()
     * @date 2018/6/22 16:06
     */
    @ApiOperation(value = "新手标和散标标的详情", notes = "新手标和散标标的详情")
    @PostMapping(value = "/getBorrowDetail", produces = "application/json; charset=utf-8")
    public Object webBorrowDetail(@RequestBody Map map, @RequestHeader(value = "userId",required = false) String userId){
        WebResult result =  webProjectListService.getBorrowDetail4Test(map,userId);
        return result;
    }
















}
