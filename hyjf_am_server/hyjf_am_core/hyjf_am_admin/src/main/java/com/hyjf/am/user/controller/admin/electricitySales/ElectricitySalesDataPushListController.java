/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.admin.electricitySales;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.AccountMobileSynchResponse;
import com.hyjf.am.response.user.ElectricitySalesDataPushListResponse;
import com.hyjf.am.resquest.config.ElectricitySalesDataPushListRequest;
import com.hyjf.am.resquest.user.AccountMobileSynchRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.dao.model.auto.AccountMobileSynch;
import com.hyjf.am.user.dao.model.auto.ElectricitySalesDataPushList;
import com.hyjf.am.user.service.admin.electricitySales.ElectricitySalesDataPushListService;
import com.hyjf.am.vo.config.ElectricitySalesDataPushListVO;
import com.hyjf.am.vo.user.AccountMobileSynchVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: dzs
 * @version: AccountMobileSynchController, v0.1 2018/8/15 14:08
 */
@RestController(value = "electricitySalesDataPushListController")
@RequestMapping("/am-user/electricitySales")
public class ElectricitySalesDataPushListController extends BaseController {

    @Autowired
    private  ElectricitySalesDataPushListService electricitySalesDataPushListService;

   
    /**
     * 线下修改信息同步查询列表list
     * @auth dzs
     * @param
     * @return
     */
    @ApiOperation(value = "ist查询",notes = "list查询")
    @PostMapping(value = "/electricitySalesDataPushList")
    public ElectricitySalesDataPushListResponse searchModifyInfoList(@RequestBody ElectricitySalesDataPushListRequest request){
    	ElectricitySalesDataPushListResponse response = new ElectricitySalesDataPushListResponse();
        Integer count = electricitySalesDataPushListService.getCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        List<ElectricitySalesDataPushList> list=new ArrayList<ElectricitySalesDataPushList>();
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count,request.getPageSize());
            list = electricitySalesDataPushListService.searchList(request,paginator.getOffset(),paginator.getLimit());
        }
        
        if(!CollectionUtils.isEmpty(list)){
            List<ElectricitySalesDataPushListVO> VOList = CommonUtils.convertBeanList(list,ElectricitySalesDataPushListVO.class);
            response.setResultList(VOList);
            response.setRtn(Response.SUCCESS);
            response.setRecordTotal(count);
        }
        return response;
    }

    /**
     * 添加信息
     * @auth dzs
     * @param
     * @return
     */
    @ApiOperation(value = "插入多条信息",notes = "插入多条信息")
    @PostMapping(value = "/insertAlectricitySalesDataPushList")
    public AccountMobileSynchResponse insertAccountMobileSynch(@RequestBody ElectricitySalesDataPushListRequest request){
        AccountMobileSynchResponse response = new AccountMobileSynchResponse();
        int count = electricitySalesDataPushListService.insertElectricitySalesDataPushList(request);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }



}
