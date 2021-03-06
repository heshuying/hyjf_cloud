package com.hyjf.am.trade.controller.admin.productcenter.plancenter.daycreditdetail;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DayCreditDetailResponse;
import com.hyjf.am.resquest.admin.DayCreditDetailRequest;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.trade.dao.model.customize.HjhDayCreditDetailCustomize;
import com.hyjf.am.trade.dao.model.customize.HjhReInvestDebtCustomize;
import com.hyjf.am.trade.service.admin.DayCreditDetailService;
import com.hyjf.am.vo.trade.hjh.DayCreditDetailVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static Logger logger = LoggerFactory.getLogger(DayCreditDetailController.class);

    @RequestMapping(value = "/hjhDayCreditList", method = RequestMethod.POST)
    public DayCreditDetailResponse hjhDayCreditList(@RequestBody @Valid DayCreditDetailRequest request){
        DayCreditDetailResponse response = new DayCreditDetailResponse();


        // Redis中 承接状态列表
        Map<String, String> hjhDebtCreditStatus = RedisUtils.hgetall(RedisConstants.CACHE_PARAM_NAME + "HJH_DEBT_CREDIT_STATUS");
        // Redis中 还款状态列表
        Map<String, String> hjhDebtRepayStatus = RedisUtils.hgetall(RedisConstants.CACHE_PARAM_NAME + "HJH_DEBT_REPAY_STATUS");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("planNid", request.getPlanNid());
        params.put("planOrderId", request.getPlanOrderId());
//        params.put("planNidNew", request.getPlanNidNew());
        params.put("userName", request.getUserName());
        params.put("creditNid", request.getCreditNid());
        params.put("borrowNid", request.getBorrowNid());
        params.put("repayStyle", request.getRepayStyle());
        params.put("creditStatus", request.getCreditStatus());
        params.put("repayStatus", request.getRepayStatus());
        params.put("liquidatesTimeStart", StringUtils.isNotBlank(request.getDate())?request.getDate():null);
        params.put("liquidatesTimeEnd", StringUtils.isNotBlank(request.getDate())?request.getDate():null);
        params.put("repayNextTimeStart", StringUtils.isNotBlank(request.getRepayNextTimeStart())?request.getRepayNextTimeStart():null);
        params.put("repayNextTimeEnd",StringUtils.isNotBlank(request.getRepayNextTimeEnd())?request.getRepayNextTimeEnd():null);
        params.put("endTimeStart", StringUtils.isNotBlank(request.getEndTimeStart())?request.getEndTimeStart():null);
        params.put("endTimeEnd", StringUtils.isNotBlank(request.getEndTimeEnd())?request.getEndTimeEnd():null);
        logger.info(DayCreditDetailController.class + ":检索条件:" + params.toString());
        //总计条数
        Integer count = this.dayCreditDetailService.countDebtCredit(params);

        //传分页
        Paginator paginator;

        if (count > 0 && count != null) {

            // 统计
            HjhReInvestDebtCustomize sumRecord = this.dayCreditDetailService.sumRecord(params);

            paginator = new Paginator(request.getCurrPage(), count, request.getPageSize());

            params.put("limitStart", paginator.getOffset());
            params.put("limitEnd", paginator.getLimit());

            //按天转让记录列表(时间段内所有)
            List<HjhDayCreditDetailCustomize> responseList = this.dayCreditDetailService.selectDebtCreditList(params);

            // 初始化总计数据
            BigDecimal sumCreditCapital         = BigDecimal.ZERO;
            BigDecimal sumLiquidationFairValue  = BigDecimal.ZERO;
            BigDecimal sumAssignCapital         = BigDecimal.ZERO;
            BigDecimal sumAssignAdvanceInterest = BigDecimal.ZERO;

            //遍历列表,重设承接状态和还款状态值
            for (HjhDayCreditDetailCustomize vo : responseList) {
                for (Map.Entry<String, String> hjhDebtCreditStatusEntry : hjhDebtCreditStatus.entrySet()){
                    if (vo.getCreditStatus().equals(Integer.valueOf(hjhDebtCreditStatusEntry.getKey()))){
                        vo.setCreditStatusName(hjhDebtCreditStatusEntry.getValue());
                    }
                }

                for (Map.Entry<String, String> hjhDebtRepayStatusEntry : hjhDebtRepayStatus.entrySet()){
                    if (vo.getRepayStatus().equals(Integer.valueOf(hjhDebtRepayStatusEntry.getKey()))){
                        vo.setRepayStatusName(hjhDebtRepayStatusEntry.getValue());
                    }
                }
            }

            if (!CollectionUtils.isEmpty(responseList)){
                DayCreditDetailVO dayCreditDetailVO = new DayCreditDetailVO();
                List<DayCreditDetailVO> responseLista = CommonUtils.convertBeanList(responseList, DayCreditDetailVO.class);
                dayCreditDetailVO.setCreditCapital(sumRecord.getSumCreditCapital());
                dayCreditDetailVO.setLiquidationFairValue(sumRecord.getSumLiquidationFairValue());
                dayCreditDetailVO.setAssignCapital(sumRecord.getSumAssignCapital());
                dayCreditDetailVO.setAssignAdvanceInterest(sumRecord.getSumAssignAdvanceInterest());
                response.setSumDayCreditDetailVO(dayCreditDetailVO);
                response.setResultList(responseLista);
                response.setCount(count);
                response.setRtn(Response.SUCCESS);
            }
        }
        return response;
    }
}
