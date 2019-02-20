package com.hyjf.am.trade.controller.admin.productcenter.plancenter.planrepay;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.HjhRepayResponse;
import com.hyjf.am.resquest.admin.HjhRepayRequest;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.trade.dao.model.auto.HjhPlan;
import com.hyjf.am.trade.service.admin.hjhplan.HjhRepayService;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 汇计划-订单退出
 * @Author : huanghui
 */
@Api(value = "汇计划")
@RestController
@RequestMapping("/am-trade/hjhRepay")
public class HjhRepayController {

    @Autowired
    private HjhRepayService hjhRepayService;

    private static Logger logger = LoggerFactory.getLogger(HjhRepayController.class);

    /**
     * 计划还款列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/hjhRepayList", method = RequestMethod.POST)
    public HjhRepayResponse hjhRepayList(@RequestBody HjhRepayRequest request){
        HjhRepayResponse response = new HjhRepayResponse();

        Map<String, Object> params = new HashedMap();
        if(StringUtils.isNotEmpty(request.getAccedeOrderIdSrch())){
            params.put("accedeOrderId", request.getAccedeOrderIdSrch());
        }
        if(StringUtils.isNotEmpty(request.getPlanNidSrch())){
            params.put("planNidSrch", request.getPlanNidSrch());
        }
        if(StringUtils.isNotEmpty(request.getUserNameSrch())){
            params.put("userName", request.getUserNameSrch());
        }
        if(StringUtils.isNotEmpty(request.getDebtLockPeriodSrch())){
            params.put("debtLockPeriodSrch", request.getDebtLockPeriodSrch());
        }
        if(StringUtils.isNotEmpty(request.getRepayStatusSrch())){
            params.put("repayStatus", Integer.valueOf(request.getRepayStatusSrch()));
        }
        if(StringUtils.isNotEmpty(request.getOrderStatusSrch())){
            params.put("orderStatusSrch", Integer.valueOf(request.getOrderStatusSrch()));
        }
        if(StringUtils.isNotEmpty(request.getBorrowStyleSrch())){
            params.put("borrowStyleSrch", request.getBorrowStyleSrch());
        }

        //清算时间
        if (StringUtils.isNotEmpty(request.getRepayTimeStart())){
            params.put("repayTimeStart", request.getRepayTimeStart() + " 00:00:00");
            params.put("repayTimeEnd", request.getRepayTimeEnd() + " 23:59:59");
        }

        //实际退出时间
        if (StringUtils.isNotEmpty(request.getActulRepayTimeStart())){
            params.put("actulRepayTimeStart", request.getActulRepayTimeStart() + " 00:00:00");
            params.put("actulRepayTimeEnd", request.getActulRepayTimeEnd() + " 23:59:59");
        }

        // 汇计划三期新增 推荐人查询
        if(StringUtils.isNotEmpty(request.getRefereeNameSrch())){
            params.put("refereeNameSrch", request.getRefereeNameSrch());
        }
        // 查询 总条数
        Integer count = this.hjhRepayService.getRepayCount(params);
        List<HjhRepayVO> repayVOListSum = new ArrayList<>();

        if(request.getCurrPage()>0){
            repayVOListSum = this.hjhRepayService.selectByExample(params);
            Paginator paginator = new Paginator(request.getCurrPage(), count, request.getPageSize());
            params.put("limitStart", paginator.getOffset());
            params.put("limitEnd", paginator.getLimit());
        }

        List<HjhRepayVO> repayVOList = this.hjhRepayService.selectByExample(params);

        //根据计划编号获取计划锁定期天月和还款方式
        for(int i = 0; i < repayVOList.size(); i++){
            String planNid = repayVOList.get(i).getPlanNid();
            HjhPlan hjhplan = hjhRepayService.getPlan(planNid);
            if (hjhplan != null){
                if (hjhplan.getIsMonth() != null){
                    repayVOList.get(i).setIsMonth(hjhplan.getIsMonth());
                }
                if (hjhplan.getBorrowStyle() != null){
                    repayVOList.get(i).setBorrowStyle(hjhplan.getBorrowStyle());
                }
            }
        }

        // 初始化总计数据
        BigDecimal sumAccedeAccount = BigDecimal.ZERO;
        BigDecimal sumRepayInterest = BigDecimal.ZERO;
        // 汇计划三期 实际收益 总计
        BigDecimal sumActualRevenue = BigDecimal.ZERO;
        // 汇计划三期 实际回款 总计
        BigDecimal sumActualPayTotal = BigDecimal.ZERO;
        // 汇计划三期 清算服务费 总计
        BigDecimal sumLqdServiceFee = BigDecimal.ZERO;

        for(int i = 0; i < repayVOListSum.size(); i++){
            if (repayVOListSum.get(i).getAccedeAccount() == null){
                sumAccedeAccount = BigDecimal.ZERO;
            }else {
                sumAccedeAccount = sumAccedeAccount.add(repayVOListSum.get(i).getAccedeAccount());
            }
            if (repayVOListSum.get(i).getRepayInterest() == null){
                sumRepayInterest = BigDecimal.ZERO;
            }else {
                sumRepayInterest = sumRepayInterest.add(repayVOListSum.get(i).getRepayInterest());
            }
            if (repayVOListSum.get(i).getActualRevenue() == null){
                sumActualRevenue = BigDecimal.ZERO;
            }else {
                sumActualRevenue = sumActualRevenue.add(repayVOListSum.get(i).getActualRevenue());
            }
            if (repayVOListSum.get(i).getActualPayTotal() == null){
                sumActualPayTotal = BigDecimal.ZERO;
            }else {
                sumActualPayTotal = sumActualPayTotal.add(repayVOListSum.get(i).getActualPayTotal());
            }
            if (repayVOListSum.get(i).getLqdServiceFee() == null){
                sumLqdServiceFee = BigDecimal.ZERO;
            }else {
                sumLqdServiceFee = sumLqdServiceFee.add(repayVOListSum.get(i).getLqdServiceFee());
            }
        }

        if (!CollectionUtils.isEmpty(repayVOList)){
            response.setResultList(repayVOList);
            response.setCount(count);
            HjhRepayVO sumHjhRepayVO = new HjhRepayVO();
            sumHjhRepayVO.setAccedeAccount(sumAccedeAccount);
            sumHjhRepayVO.setRepayInterest(sumRepayInterest);
            sumHjhRepayVO.setActualRevenue(sumActualRevenue);
            sumHjhRepayVO.setActualPayTotal(sumActualPayTotal);
            sumHjhRepayVO.setLqdServiceFee(sumLqdServiceFee);
            response.setSumHjhRepayVO(sumHjhRepayVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    @RequestMapping(value = "/hjhRepaymentDetails/{accedeOrderId}")
    public HjhRepayResponse hjhRepaymentDetails(@PathVariable String accedeOrderId){
        HjhRepayResponse response = new HjhRepayResponse();


        List<HjhRepayVO> hjhRepayMentDetailList = hjhRepayService.selectByAccedeOrderId(accedeOrderId);

        if (!CollectionUtils.isEmpty(hjhRepayMentDetailList)){
            List<HjhRepayVO> hjhRepayMentDetailVoList = CommonUtils.convertBeanList(hjhRepayMentDetailList, HjhRepayVO.class);
            response.setResultList(hjhRepayMentDetailVoList);
        }
        return  response;
    }
}
