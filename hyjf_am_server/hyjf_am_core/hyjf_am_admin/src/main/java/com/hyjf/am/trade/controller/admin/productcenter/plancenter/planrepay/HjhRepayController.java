package com.hyjf.am.trade.controller.admin.productcenter.plancenter.planrepay;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.HjhRepayResponse;
import com.hyjf.am.resquest.admin.HjhRepayRequest;
import com.hyjf.am.trade.dao.model.auto.HjhRepay;
import com.hyjf.am.trade.service.admin.hjhplan.HjhRepayService;
import com.hyjf.am.vo.trade.hjh.HjhRepayVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    @RequestMapping(value = "/getRepayCount")
    public Integer getRepayCount(@RequestBody HjhRepayRequest repayRequest){
        return hjhRepayService.getRepayCount(repayRequest);
    }

    @RequestMapping(value = "/hjhRepayList", method = RequestMethod.POST)
    public HjhRepayResponse hjhRepayList(@RequestBody HjhRepayRequest request){
        HjhRepayResponse response = new HjhRepayResponse();
        String returnCode = Response.FAIL;

        List<HjhRepay> hjhRepayVOList = hjhRepayService.selectByExample(request);

        if (!CollectionUtils.isEmpty(hjhRepayVOList)) {
            List<HjhRepayVO> hjhRepayVOLists = CommonUtils.convertBeanList(hjhRepayVOList, HjhRepayVO.class);
            response.setResultList(hjhRepayVOLists);
            returnCode = Response.SUCCESS;
        }
        response.setRtn(returnCode);
        return response;
    }

    @RequestMapping(value = "/hjhRepaymentDetails/{accedeOrderId}")
    public HjhRepayResponse hjhRepaymentDetails(@PathVariable String accedeOrderId){
        HjhRepayResponse response = new HjhRepayResponse();

        String returnCode = Response.FAIL;

        List<HjhRepayVO> hjhRepayMentDetailList = hjhRepayService.selectByAccedeOrderId(accedeOrderId);

        if (!CollectionUtils.isEmpty(hjhRepayMentDetailList)){
            List<HjhRepayVO> hjhRepayMentDetailVoList = CommonUtils.convertBeanList(hjhRepayMentDetailList, HjhRepayVO.class);
            response.setResultList(hjhRepayMentDetailVoList);
            returnCode = Response.SUCCESS;
        }
        response.setRtn(returnCode);
        return  response;
    }
}
