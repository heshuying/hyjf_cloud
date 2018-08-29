package com.hyjf.am.trade.controller.admin.productcenter.plancenter.planrepay;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.HjhRepayResponse;
import com.hyjf.am.resquest.admin.HjhRepayRequest;
import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.trade.dao.model.auto.HjhRepay;
import com.hyjf.am.trade.service.admin.hjhplan.HjhRepayService;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import io.swagger.annotations.Api;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        params.put("accedeOrderId", request.getAccedeOrderIdSrch());


        //清算时间
//        if (org.apache.commons.lang3.StringUtils.isNotEmpty(request.getRepayTimeStart())){
//            int repayTimeStart = GetDate.getDayEnd10(request.getRepayTimeStart() + " 00:00:00");
//            int repayTimeEnd;
//            if (org.apache.commons.lang3.StringUtils.isNotEmpty(request.getRepayTimeEnd())){
//                repayTimeEnd = GetDate.getDayEnd10(request.getRepayTimeEnd() + " 23:59:59");
//            }else {
//                Long logRepayTimeEnd = System.currentTimeMillis()/1000;
//                repayTimeEnd = logRepayTimeEnd.intValue();
//            }
            if (StringUtils.isNotEmpty(request.getRepayTimeStart())){
                params.put("repayTimeStart", request.getRepayTimeStart() + " 00:00:00");
                params.put("repayTimeEnd", request.getRepayTimeEnd() + " 23:59:59");
            }
//        }

        //实际退出时间
//        if(org.apache.commons.lang3.StringUtils.isNotEmpty(request.getActulRepayTimeStart())){
//            int actuTimeStart = GetDate.getDayStart10(request.getActulRepayTimeStart() + " 00:00:00");
//            int actuTimeEnd;
//            if (StringUtils.isNotEmpty(request.getActulRepayTimeEnd())){
//                actuTimeEnd = GetDate.getDayEnd10(request.getActulRepayTimeEnd() + "23:59:59");
//            }else {
//                Long logAutuTimeEnd = System.currentTimeMillis() / 1000;
//                actuTimeEnd = logAutuTimeEnd.intValue();
//            }
            if (StringUtils.isNotEmpty(request.getActulRepayTimeStart())){
                params.put("actulRepayTimeStart", request.getActulRepayTimeStart() + " 00:00:00");
                params.put("actulRepayTimeEnd", request.getActulRepayTimeEnd() + "23:59:59");
            }

//        }

        // 查询 总条数
        Integer count = this.hjhRepayService.getRepayCount(params);

        if (request.getCurrPage() > 0){

            Paginator paginator = new Paginator(request.getCurrPage(), count);
            params.put("limitStart", paginator.getOffset());
            if (request.getPageSize() > 0){
                params.put("limitEnd", request.getPageSize());
            }else {
                // paginator.getLimit() 默认 10条
                params.put("limitEnd", paginator.getLimit());
            }
        }

        List<HjhRepayVO> repayVOList = this.hjhRepayService.selectByExample(params);

        // 初始化总计数据
        BigDecimal sumAccedeAccount = BigDecimal.ZERO;
        BigDecimal sumRepayInterest = BigDecimal.ZERO;
        // 汇计划三期 实际收益 总计
        BigDecimal sumActualRevenue = BigDecimal.ZERO;
        // 汇计划三期 实际回款 总计
        BigDecimal sumActualPayTotal = BigDecimal.ZERO;
        // 汇计划三期 清算服务费 总计
        BigDecimal sumLqdServiceFee = BigDecimal.ZERO;

        for(int i = 0; i < repayVOList.size(); i++){
            if (repayVOList.get(i).getAccedeAccount() == null){
                sumAccedeAccount = BigDecimal.ZERO;
            }else {
                sumAccedeAccount = sumAccedeAccount.add(repayVOList.get(i).getAccedeAccount());
            }
            if (repayVOList.get(i).getRepayInterest() == null){
                sumRepayInterest = BigDecimal.ZERO;
            }else {
                sumRepayInterest = sumRepayInterest.add(repayVOList.get(i).getRepayInterest());
            }
            if (repayVOList.get(i).getActualRevenue() == null){
                sumActualRevenue = BigDecimal.ZERO;
            }else {
                sumActualRevenue = sumActualRevenue.add(repayVOList.get(i).getActualRevenue());
            }
            if (repayVOList.get(i).getActualPayTotal() == null){
                sumActualPayTotal = BigDecimal.ZERO;
            }else {
                sumActualPayTotal = sumActualPayTotal.add(repayVOList.get(i).getActualPayTotal());
            }
            if (repayVOList.get(i).getLqdServiceFee() == null){
                sumLqdServiceFee = BigDecimal.ZERO;
            }else {
                sumLqdServiceFee = sumLqdServiceFee.add(repayVOList.get(i).getLqdServiceFee());
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
