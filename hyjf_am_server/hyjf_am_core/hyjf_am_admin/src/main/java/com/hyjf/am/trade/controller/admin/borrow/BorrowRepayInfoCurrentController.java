package com.hyjf.am.trade.controller.admin.borrow;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BorrowRepayInfoCurrentExportResponse;
import com.hyjf.am.response.admin.BorrowRepayInfoCurrentResponse;
import com.hyjf.am.resquest.admin.BorrowRepayInfoCurrentRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.admin.borrow.BorrowRepayInfoCurrentService;
import com.hyjf.am.vo.admin.BorrowRepayInfoCurrentCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepayInfoCurrentExportCustomizeVO;
import com.hyjf.common.paginator.Paginator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 当前债权还款明细
 * @author hesy
 */
@RestController
@RequestMapping("/am-admin/repayinfo_current")
public class BorrowRepayInfoCurrentController extends BaseController {
    @Autowired
    BorrowRepayInfoCurrentService borrowRepayInfoCurrentService;

    /**
     * 当前债权还款明细页面数据获取
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/getData",method = RequestMethod.POST)
    public BorrowRepayInfoCurrentResponse getData(@RequestBody @Valid BorrowRepayInfoCurrentRequest requestBean){
        logger.info("当前债权还款明细-start, requestBean:{}", JSON.toJSONString(requestBean));
        BorrowRepayInfoCurrentResponse response = new BorrowRepayInfoCurrentResponse();

        //请求参数校验
        if(requestBean.getCurrPage() <= 0){
            requestBean.setCurrPage(1);
        }
        if(requestBean.getPageSize() <= 0){
            requestBean.setPageSize(10);
        }
        //borrowNid为必须传的参数
//        if(StringUtils.isBlank(requestBean.getBorrowNid())){
//            response.setRtn(Response.FAIL);
//            response.setMessage("请求参数错误，borrowNid为空");
//            return response;
//        }
        Map<String,Object> paraMap = getQueryParamMap(requestBean);

        // 查询列表总记录数
        Integer count = borrowRepayInfoCurrentService.getRepayInfoCurrentCount(paraMap);
        Paginator paginator = new Paginator(requestBean.getCurrPage(), count,requestBean.getPageSize());

        // 查询列表数据
        List<BorrowRepayInfoCurrentCustomizeVO> resultList;
        if(count > 0){
            paraMap.put("limitStart", paginator.getOffset());
            paraMap.put("limitEnd", paginator.getLimit());
            resultList = borrowRepayInfoCurrentService.getRepayInfoCurrentList(paraMap);
        }else{
            resultList = new ArrayList<>();
        }

        // 查询列表统计数据
        Map<String,Object> sumInfo = borrowRepayInfoCurrentService.getRepayInfoCurrentSum(paraMap);

        response.setResultList(resultList);
        response.setCount(count);

        response.setSumInfo(sumInfo);
        response.setRtn(Response.SUCCESS);

        logger.info("当前债权还款明细-end, response:{}", JSON.toJSONString(response));
        return response;
    }

    /**
     * 请求参数处理
     * @param requestBean
     * @return
     */
    private Map<String,Object> getQueryParamMap(BorrowRepayInfoCurrentRequest requestBean){
        Map<String,Object> paraMap = new HashMap<>();
        paraMap.put("borrowNid", requestBean.getBorrowNid());
        if(StringUtils.isNotBlank(requestBean.getAssignOrderId())){
            paraMap.put("assignOrderId",requestBean.getAssignOrderId());
        }
        if(StringUtils.isNotBlank(requestBean.getTenderOrderId())){
            paraMap.put("tenderOrderId",requestBean.getTenderOrderId());
        }
        if(StringUtils.isNotBlank(requestBean.getRepayPeriod())){
            paraMap.put("repayPeriod",requestBean.getRepayPeriod());
        }
        if(StringUtils.isNotBlank(requestBean.getRepayStatus())){
            paraMap.put("repayStatus",requestBean.getRepayStatus());
        }
        if(StringUtils.isNotBlank(requestBean.getRepayTimeStart())){
            paraMap.put("repayTimeStart",requestBean.getRepayTimeStart());
        }
        if(StringUtils.isNotBlank(requestBean.getRepayTimeEnd())){
            paraMap.put("repayTimeEnd",requestBean.getRepayTimeEnd());
        }
        if(StringUtils.isNotBlank(requestBean.getRepayedTimeStart())){
            paraMap.put("repayedTimeStart",requestBean.getRepayedTimeStart());
        }
        if(StringUtils.isNotBlank(requestBean.getRepayedTimeEnd())){
            paraMap.put("repayedTimeEnd",requestBean.getRepayedTimeEnd());
        }

        return paraMap;
    }

    /**
     * 当前债权还款明细页面Excel导出数据获取
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/getExportData",method = RequestMethod.POST)
    public BorrowRepayInfoCurrentExportResponse getExportData(@RequestBody @Valid BorrowRepayInfoCurrentRequest requestBean){
        logger.info("当前债权还款明细导出数据接口-start, requestBean:{}", JSON.toJSONString(requestBean));
        BorrowRepayInfoCurrentExportResponse response = new BorrowRepayInfoCurrentExportResponse();

        //请求参数校验
        if(requestBean.getCurrPage() <= 0){
            requestBean.setCurrPage(1);
        }
        if(requestBean.getPageSize() <= 0){
            requestBean.setPageSize(10);
        }
        //borrowNid为必须传的参数
//        if(StringUtils.isBlank(requestBean.getBorrowNid())){
//            response.setRtn(Response.FAIL);
//            response.setMessage("请求参数错误，borrowNid为空");
//            return response;
//        }
        if(requestBean.getCount() == null || requestBean.getCount() ==0){
            response.setResultList(new ArrayList<>());
            return response;
        }
        Map<String,Object> paraMap = getQueryParamMap(requestBean);
        Paginator paginator = new Paginator(requestBean.getCurrPage(), requestBean.getCount(),requestBean.getPageSize());
        paraMap.put("limitStart", paginator.getOffset());
        paraMap.put("limitEnd", paginator.getLimit());
        // 查询列表数据
        List<BorrowRepayInfoCurrentExportCustomizeVO> resultList = borrowRepayInfoCurrentService.getRepayInfoCurrentListExport(paraMap);

        response.setResultList(resultList);
        response.setRtn(Response.SUCCESS);

        return response;
    }

    /**
     * 当前债权还款明细页面Excel导出总记录数
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/getExportCount",method = RequestMethod.POST)
    public IntegerResponse getExportCount(@RequestBody @Valid BorrowRepayInfoCurrentRequest requestBean){
        logger.info("当前债权还款明细导出总记录数接口-start, requestBean:{}", JSON.toJSONString(requestBean));
        IntegerResponse response = new IntegerResponse();

        //borrowNid为必须传的参数
//        if(StringUtils.isBlank(requestBean.getBorrowNid())){
//            response.setRtn(Response.FAIL);
//            response.setMessage("请求参数错误，borrowNid为空");
//            return response;
//        }
        Map<String,Object> paraMap = getQueryParamMap(requestBean);

        // 查询列表数据
        Integer count = borrowRepayInfoCurrentService.getRepayInfoCurrentCountExport(paraMap);

        response.setResultInt(count);
        response.setRtn(Response.SUCCESS);

        logger.info("当前债权还款明细导出总记录数接口-end, response:{}", JSON.toJSONString(response));
        return response;
    }

}
