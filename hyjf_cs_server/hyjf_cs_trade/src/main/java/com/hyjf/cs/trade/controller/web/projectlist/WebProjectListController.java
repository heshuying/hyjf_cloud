/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.web.projectlist;

import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.BorrowInvestReqBean;
import com.hyjf.cs.trade.bean.WebBorrowRequestBean;
import com.hyjf.cs.trade.bean.WebCreditRequestBean;
import com.hyjf.cs.trade.bean.WebPlanRequestBean;
import com.hyjf.cs.trade.controller.BaseTradeController;
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
@RequestMapping("/hyjf-web/projectlist")
public class WebProjectListController extends BaseTradeController {
    private static final Logger logger = LoggerFactory.getLogger(WebProjectListController.class);

     @Autowired
     private WebProjectListService webProjectListService;

    /**
     * 获取首页散标推荐列表(散标推荐和散标专区的散标投资，通用接口)(可能没有用了 ，后期废掉)
     * @param request
     * @return
     */
    @ApiOperation(value = "获取首页散标推荐列表", notes = "首页散标推荐列表")
    @PostMapping(value = "/homeBorrowProjectList", produces = "application/json; charset=utf-8")
    public Object homeBorrowProjectList(@RequestBody @Valid ProjectListRequest request){
        // controller 不做业务处理
        WebResult result =  webProjectListService.searchProjectList(request);
        return result;
    }

    /**
     * 获取新手专区列表(据说projectType = 14代表新手标)
     * @param request
     * @return
     */
    @ApiOperation(value = "获取新手专区列表", notes = "新手专区列表")
    @PostMapping(value = "/getNewProjectList", produces = "application/json; charset=utf-8")
    public Object newBorrowProjectList(@RequestBody @Valid ProjectListRequest request){
        // controller 不做业务处理
        request.setProjectType("HZT");
        request.setBorrowClass("NEW");
        WebResult result =  webProjectListService.searchProjectList(request);
        return result;
    }

    /**
     * 散标专区散标投资列表
     * @param request
     * @return
     */
    @ApiOperation(value = "获取散标专区散标投资列表", notes = "获取散标专区散标投资列表")
    @PostMapping(value = "/borrowProjectList", produces = "application/json; charset=utf-8")
    public Object borrowProjectList(@RequestBody @Valid ProjectListRequest request){
        // controller 不做业务处理
        request.setProjectType("HZT");
        WebResult result =  webProjectListService.searchProjectList(request);
        return result;
    }


    /**
     * web端新手标和散标标的详情
     * @author zhangyk
     * 原接口：com.hyjf.web.bank.web.borrow.BorrowController.searchProjectDetail()
     * @date 2018/6/22 16:06
     */
    @ApiOperation(value = "新手标和散标标的详情", notes = "新手标和散标标的详情")
    @PostMapping(value = "/getBorrowDetail", produces = "application/json; charset=utf-8")
    public Object webBorrowDetail(@RequestBody Map map, @RequestHeader(value = "userId",required = false) String userId){
        WebResult result =  webProjectListService.getBorrowDetail(map,userId);
        return result;
    }


    /**
     * 散标投资记录
     * @author zhangyk
     * 原接口：com.hyjf.web.bank.web.borrow.BorrowController.searchProjectInvestList()
     * @date 2018/8/20 14:06
     */
    @ApiOperation(value = "新手标和散标标的详情:投资记录" , notes = "新手标和散标标的详情:投资记录")
    @PostMapping(value = "/getBorrowInvest" , produces = "application/json; charset=utf-8")
    public Object getBorrowInvest(@RequestBody BorrowInvestReqBean form, @RequestHeader(value = "userId",required = false ) String userId){
        WebResult result = webProjectListService.getBorrowInvest(form,userId);
        return result;
    }




    /**
     * 散标专区-债权转让列表
     * @param request
     * @return
     */
    @ApiOperation(value = "散标专区债权转让列表", notes = "散标专区债权转让列表")
    @PostMapping(value = "/getCreditList", produces = "application/json; charset=utf-8")
    public Object getCredittList(@RequestBody @Valid CreditListRequest request){
        WebResult result =  webProjectListService.searchCreditList(request);
        return result;
    }


    /**
     * 散标专区-债权转让详情
     * 原接口：com.hyjf.web.bank.web.user.credit.CreditController.searchWebCreditTender()
     * @return
     */
    @ApiOperation(value = "散标专区债权转让详情", notes = "散标专区债权转让详情")
    @PostMapping(value = "/getCreditDetail", produces = "application/json; charset=utf-8")
    public Object creditDetail(@RequestBody Map map, @RequestHeader(value = "userId",required = false) String userId){
        WebResult result =  webProjectListService.getCreditDetail(map,userId);
        return result;
    }


    /**
     * 散标专区-债权转让详情-承接记录
     * 原接口：com.hyjf.web.bank.web.user.credit.CreditController.searchCreditTenderList()
     * @return
     */
    @ApiOperation(value = "散标专区债权转让详情:承接记录", notes = "散标专区债权转让详情:承接记录")
    @PostMapping(value = "/getCreditTenderList", produces = "application/json; charset=utf-8")
    public Object getCreditTenderList(@RequestBody WebCreditRequestBean requestBean){
        WebResult result =  webProjectListService.getCreditTenderList(requestBean);
        return result;
    }




    /**
     * 计划专区计划列表上部统计数据
     * @author zhangyk
     * @date 2018/6/21 15:18
     */
    @ApiOperation(value = "计划专区计划列表上部统计数据", notes = "计划专区计划列表上部统计数据")
    @PostMapping(value = "/initPlanData", produces = "application/json; charset=utf-8")
    public Object getPlanData(@RequestBody  ProjectListRequest request){
        WebResult result =  webProjectListService.searchPlanData(request);
        return result;
    }

    /**
     * 计划专区计划列表数据
     * @author zhangyk
     * @date 2018/6/21 15:18
     */
    @ApiOperation(value = "计划专区计划列表", notes = "计划专区计划列表")
    @PostMapping(value = "/getPlanList", produces = "application/json; charset=utf-8")
    public Object getPlanList(@RequestBody @Valid ProjectListRequest request){
        WebResult result =  webProjectListService.searchPlanList(request);
        return result;
    }


    /**
     * 计划详情
     * @author zhangyk
     * 原接口: com.hyjf.web.hjhdetail.HjhDetailController.searchPlanDetail()
     * @date 2018/6/27 18:53
     */
    @ApiOperation(value = "计划专区计划详情", notes = "计划专区计划详情")
    @PostMapping(value = "/getPlanDetail", produces = "application/json; charset=utf-8")
    public Object getPlanDetail(@RequestBody Map map, @RequestHeader(value = "userId",required = false) String userId){
        WebResult result =  webProjectListService.getPlanDetail(map,userId);
        return result;
    }

    /**
     *  计划详情标的组成
     * @author zhangyk
     * 原接口： com.hyjf.web.hjhdetail.HjhDetailController.searchPlanBorrow()
     * @date 2018/8/16 11:01
     */
    @ApiOperation(value = "计划详情标的组成" , notes = "计划详情标的组成")
    @PostMapping(value = "/getPlanBorrowList", produces = "application/json; charset=utf-8")
    public Object getPlanBorrowList(@RequestBody @Valid WebPlanRequestBean requestBean,@RequestHeader(value = "token",required = false) String token){
        WebResult result  = webProjectListService.getPlanBorrowList(requestBean);
        return result;
    }



    /**
     * 计划详情加入记录
     * @author zhangyk
     * @date 2018/8/16 11:01
     */
    @ApiOperation(value = "计划详情加入记录" , notes = "计划详情加入记录")
    @PostMapping(value = "/getPlanAccedeList", produces = "application/json; charset=utf-8")
    public Object getPlanAccedeList(@RequestBody @Valid WebPlanRequestBean requestBean,@RequestHeader(value = "userId",required = false) String userId){
        WebResult result  = webProjectListService.getPlanAccedeList(requestBean,userId);
        return result;
    }


    /**
     * 计划详情标的组成：标的详情
     * @author zhangyk
     * 原接口：com.hyjf.web.hjhdetail.HjhDetailController.searchProjectInvestList()
     * @date 2018/8/16 11:01
     */
    @ApiOperation(value = "计划详情标的组成：标的详情" , notes = "计划详情标的组成：标的详情")
    @PostMapping(value = "/hjh/getBorrowDetail", produces = "application/json; charset=utf-8")
    public Object getPlanBorrowDetail(@RequestBody  WebBorrowRequestBean requestBean, @RequestHeader(value = "userId",required = false) Integer userId){
        WebResult result  = webProjectListService.getPlanAccedeBorrowDetail(requestBean,userId);
        return result;
    }





    /**
     * 计划详情标的组成：投资记录
     * @author zhangyk
     * 原接口：com.hyjf.web.hjhdetail.HjhDetailController.searchBorrowUndertakeList()
     * @date 2018/8/24 10:13
     */
    @ApiOperation(value = "计划详情标的组成：投资记录" , notes = "计划详情标的组成：投资记录")
    @PostMapping(value = "/hjh/getBorrowUndertake", produces = "application/json; charset=utf-8")
    public Object getPlanBorrowUndertake(@RequestBody  WebBorrowRequestBean requestBean){
        WebResult result  = webProjectListService.getPlanBorrowUndertake(requestBean);
        return result;
    }
















}
