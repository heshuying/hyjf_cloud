/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.finance;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.PushMoneyResponse;
import com.hyjf.am.resquest.admin.PushMoneyRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.customize.trade.PushMoneyCustomize;
import com.hyjf.am.trade.service.admin.finance.PushMoneyManageService;
import com.hyjf.am.vo.trade.PushMoneyVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author zdj
 * @version PushMoneyManageController, v0.1 2018/67/7 9:13
 *          后台资金中心-提成管理
 */

@RestController
@RequestMapping("/am-trade/pushMoneyRecord")
public class PushMoneyManageController extends BaseController {
    @Autowired
    private PushMoneyManageService pushMoneyManagerService;

    /**
     * 根据筛选条件查找(查找提成管理)
     *
     * @param request
     * @return
     */
    @RequestMapping("/findPushMoneyRecordList")
    public PushMoneyResponse findPushMoneyRecordList(@RequestBody PushMoneyRequest request) {
        logger.info("---findPushMoneyRecordList by param---  " + JSONObject.toJSON(request));
        PushMoneyResponse response = new PushMoneyResponse();
        int pushMoneyTotal = pushMoneyManagerService.countPushMoney(request);
        // 查询列表传入分页
        Paginator paginator;
        if(request.getLimit() == 0){
            // 前台传分页
            paginator = new Paginator(request.getCurrPage(), pushMoneyTotal);
            request.setLimitStart(paginator.getOffset());
        } else {
            // 前台未传分页那默认 10
            paginator = new Paginator(request.getCurrPage(), pushMoneyTotal,request.getPageSize());
            request.setLimitEnd(paginator.getLimit());
        }
        if (pushMoneyTotal > 0) {
            List<PushMoneyCustomize> pushMoneyCustomizeList = pushMoneyManagerService.selectPushMoneyList(request);
            if (!CollectionUtils.isEmpty(pushMoneyCustomizeList)) {
                List<PushMoneyVO> userBankRecord = CommonUtils.convertBeanList(pushMoneyCustomizeList, PushMoneyVO.class);
                response.setResultList(userBankRecord);
                response.setCount(pushMoneyTotal);
                response.setRtn(Response.SUCCESS);//代表成功
            }
        }
        return response;
    }

    /**
     * 直投提成列表count
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/getPushMoneyListCount")
    public PushMoneyResponse getPushMoneyListCount(@RequestBody PushMoneyRequest request){
        PushMoneyResponse response = new PushMoneyResponse();
        int count = pushMoneyManagerService.getPushMoneyListCount(request);
        response.setCount(count);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    /**
     * 直投提成列表list
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/searchPushMoneyList")
    public PushMoneyResponse searchPushMoneyList(@RequestBody PushMoneyRequest request){
        PushMoneyResponse response = new PushMoneyResponse();
        Integer count = pushMoneyManagerService.getPushMoneyListCount(request);
        // currPage<0 为全部,currPage>0 为具体某一页
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),count);
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info("searchPushMoneyList::::::::::currPage=[{}],limitStart=[{}],limitEnd=[{}]",request.getCurrPage(),request.getLimitStart(),request.getLimitEnd());
        List<PushMoneyCustomize> pushMoneyCustomizeList = pushMoneyManagerService.searchPushMoneyList(request);
        if(!CollectionUtils.isEmpty(pushMoneyCustomizeList)){
            List<PushMoneyVO> pushMoneyVOList = CommonUtils.convertBeanList(pushMoneyCustomizeList,PushMoneyVO.class);
            response.setResultList(pushMoneyVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 直投提成列表总金额
     * @auth sunpeikai
     * @param
     * @return
     */
    @PostMapping(value = "/queryPushMoneyTotle")
    public PushMoneyResponse queryPushMoneyTotle(@RequestBody PushMoneyRequest request){
        PushMoneyResponse response = new PushMoneyResponse();
        Map<String,Object> result = pushMoneyManagerService.queryPushMoneyTotle(request);
        if(result != null){
            response.setMap(result);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 根据userid查询 crm  cuttype
     * @auth sunpeikai
     * @param
     * @return
     */
    @GetMapping(value = "/queryCrmCuttype/{userId}")
    public PushMoneyResponse queryCrmCuttype(@PathVariable Integer userId){
        PushMoneyResponse response = new PushMoneyResponse();
        int cuttype = pushMoneyManagerService.queryCrmCuttype(userId);
        response.setCuttype(cuttype);
        response.setRtn(Response.SUCCESS);
        return response;
    }

    @PostMapping(value = "/updateTenderCommissionRecord")
    public PushMoneyResponse updateTenderCommissionRecord(@RequestBody PushMoneyRequest request){
        PushMoneyResponse response = new PushMoneyResponse();
        int cnt = pushMoneyManagerService.updateTenderCommissionRecord(request);
        response.setCnt(cnt);
        response.setRtn(Response.SUCCESS);
        return response;
    }
}
