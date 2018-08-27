package com.hyjf.am.trade.controller.admin.productcenter.plancenter.daycreditdetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DayCreditDetailResponse;
import com.hyjf.am.resquest.admin.DayCreditDetailRequest;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.trade.service.admin.DayCreditDetailService;
import com.hyjf.am.vo.trade.hjh.DayCreditDetailVO;

import io.swagger.annotations.Api;

/**
 * 汇计划-资金计划-汇计划按天转让记录列表
 * @Author : huanghui
 */
@Api(value = "汇计划-资金计划")
@RestController
@RequestMapping("/am-trade/hjhDayCreditDetail")
public class DayCreditDetailController {

    @Autowired
    private DayCreditDetailService dayCreditDetailService;

    @RequestMapping(value = "/hjhDayCreditList", method = RequestMethod.POST)
    public DayCreditDetailResponse hjhDayCreditList(@RequestBody @Valid DayCreditDetailRequest request){
        DayCreditDetailResponse response = new DayCreditDetailResponse();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("planNid", request.getPlanNid());
//        params.put("planOrderId", request.getPlanOrderId());
//        params.put("planNidNew", request.getPlanNidNew());
//        params.put("userName", request.getUserName());
//        params.put("creditNid", request.getCreditNid());
//        params.put("borrowNid", request.getBorrowNid());
        params.put("repayStyle", request.getRepayStyle());
        params.put("creditStatus", request.getCreditStatus());
        params.put("repayStatus", request.getRepayStatus());
        params.put("liquidatesTimeStart", org.apache.commons.lang.StringUtils.isNotBlank(request.getDate())?request.getDate():null);
        params.put("liquidatesTimeEnd", org.apache.commons.lang.StringUtils.isNotBlank(request.getDate())?request.getDate():null);
        params.put("repayNextTimeStart", org.apache.commons.lang.StringUtils.isNotBlank(request.getRepayNextTimeStart())?request.getRepayNextTimeStart():null);
        params.put("repayNextTimeEnd", org.apache.commons.lang.StringUtils.isNotBlank(request.getRepayNextTimeEnd())?request.getRepayNextTimeEnd():null);
        params.put("endTimeStart", org.apache.commons.lang.StringUtils.isNotBlank(request.getEndTimeStart())?request.getEndTimeStart():null);
        params.put("endTimeEnd", StringUtils.isNotBlank(request.getEndTimeEnd())?request.getEndTimeEnd():null);

        //总计条数
        Integer count = this.dayCreditDetailService.countDebtCredit(params);

        //传分页
        Paginator paginator;

        if (request.getLimitStart() == 0){
            // 根据前台传入分页
            paginator = new Paginator(request.getCurrPage(), count);
        }else {
            // 未传入分页信息,使用默认分页信息
            paginator = new Paginator(request.getCurrPage(), count, request.getPageSize());
        }
        if (count > 0 && count != null) {

            params.put("limitStart", paginator.getOffset());
            params.put("limitEnd", paginator.getLimit());
            //按天转让记录列表
            List<DayCreditDetailVO> responseList = this.dayCreditDetailService.selectDebtCreditList(params);

            if (!CollectionUtils.isEmpty(responseList)){
                response.setResultList(responseList);
                response.setCount(count);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }
}
