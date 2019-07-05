package com.hyjf.cs.trade.controller.web.wjt;

import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.controller.BaseTradeController;
import com.hyjf.cs.trade.service.projectlist.CacheService;
import com.hyjf.cs.trade.service.wjt.WjtWebProjectListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author pangchengchao
 * @version WjtWebProjectListController,  v0.1  2019/5/14  11:56
 * @description 类说明
 */
@Api(tags = "web端-温金投项目列表")
@RestController
@RequestMapping("/hyjf-web/wjt/projectlist")
public class WjtWebProjectListController extends BaseTradeController {

    private static final Logger logger = LoggerFactory.getLogger(WjtWebProjectListController.class);

    @Autowired
    private WjtWebProjectListService wjtWebProjectListService;

    @Autowired
    private CacheService cacheService;
    /**
     * 散标专区散标出借列表
     * @param request
     *
     * @return
     */
    @ApiOperation(value = "获取散标专区散标出借列表", notes = "获取散标专区散标出借列表")
    @PostMapping(value = "/borrowProjectList", produces = "application/json; charset=utf-8")
    public Object borrowProjectList(@RequestBody @Valid ProjectListRequest request){
        // controller 不做业务处理
        request.setProjectType("HZT");
        WebResult result =  wjtWebProjectListService.searchWjtWebProjectListNew(request);
        return result;
    }
}
