/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.finance;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.TenderCommissionResponse;
import com.hyjf.am.response.trade.BorrowApicronResponse;
import com.hyjf.am.response.trade.PushMoneyResponse;
import com.hyjf.am.resquest.admin.PushMoneyRequest;
import com.hyjf.am.resquest.admin.TenderCommissionRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.dao.model.auto.PushMoney;
import com.hyjf.am.trade.dao.model.customize.PushMoneyCustomize;
import com.hyjf.am.trade.service.admin.finance.PushMoneyManageService;
import com.hyjf.am.trade.service.admin.finance.TenderCommissionService;
import com.hyjf.am.vo.trade.PushMoneyVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private TenderCommissionService tenderCommissionService;


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
        if(request.getCurrPage()>0){
            Paginator paginator = new Paginator(request.getCurrPage(),pushMoneyTotal,request.getPageSize());
            request.setLimitStart(paginator.getOffset());
            request.setLimitEnd(paginator.getLimit());
        }
        logger.info(request.getLimitStart() + "    " + request.getLimitEnd());
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
    @PostMapping(value = "/getPushMoney")
    public PushMoneyResponse getPushMoney(@RequestBody PushMoneyRequest request){
        PushMoneyResponse response = new PushMoneyResponse();
        List<PushMoney> pushMoneyList = pushMoneyManagerService.getPushMoney(request);
        List<PushMoneyVO> pushMoneyVOList = CommonUtils.convertBeanList(pushMoneyList,PushMoneyVO.class);
        response.setResultList(pushMoneyVOList);
        response.setRtn(Response.SUCCESS);
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
            Paginator paginator = new Paginator(request.getCurrPage(),count,request.getPageSize());
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

    /**
     * 取得借款API表
     * @param borrowNid
     * @return
     */
    @GetMapping("/selectBorrowApicronListByBorrowNid/{borrowNid}")
    public BorrowApicronResponse selectBorrowApicronListByBorrowNid(@PathVariable String borrowNid) {
        BorrowApicronResponse response = new BorrowApicronResponse();
        List<BorrowApicron> borrowApicronList = pushMoneyManagerService.selectBorrowApicronListByBorrowNid(borrowNid);
        if (!CollectionUtils.isEmpty(borrowApicronList)) {
            List<BorrowApicronVO> borrowApicronVoList = CommonUtils.convertBeanList(borrowApicronList,
                    BorrowApicronVO.class);
            response.setResultList(borrowApicronVoList);
        }
        return response;
    }

    /**
     * 根据BorrowTender表的id和TenderType查询条数
     * @param request
     * @return
     */
    @PostMapping("/countTenderCommissionByTenderIdAndTenderType")
    public TenderCommissionResponse countTenderCommissionByTenderIdAndTenderType(@RequestBody TenderCommissionRequest request){
        logger.info("根据BorrowTender表的id和TenderType查询条数countTenderCommissionByTenderIdAndTenderType::::::::::",JSONObject.toJSON(request));
        TenderCommissionResponse response =  new TenderCommissionResponse();
        int count = tenderCommissionService.countTenderCommissionByTenderIdAndTenderType(request);
        response.setCount(count);
        logger.info("根据BorrowTender表的id和TenderType查询条数count::::::::::",count);
        return  response;
    }

    /**
     * 添加提成
     * @param request
     * @return
     */
    @PostMapping("/insertTenderCommission")
    public TenderCommissionResponse insertTenderCommission(@RequestBody TenderCommissionRequest request){
        logger.info("添加提成insertTenderCommission::::::::::[{}]",JSONObject.toJSON(request));
        TenderCommissionResponse response =  new TenderCommissionResponse();
        int flag = tenderCommissionService.insertTenderCommission(request);
        response.setFlag(flag);
        return  response;
    }
}
