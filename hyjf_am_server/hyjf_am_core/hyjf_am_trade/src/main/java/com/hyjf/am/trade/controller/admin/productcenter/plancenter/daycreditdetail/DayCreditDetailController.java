package com.hyjf.am.trade.controller.admin.productcenter.plancenter.daycreditdetail;

import com.hyjf.am.response.admin.DayCreditDetailResponse;
import com.hyjf.am.resquest.admin.DayCreditDetailRequest;
import com.hyjf.am.trade.service.admin.DayCreditDetailService;
import com.hyjf.am.vo.trade.hjh.DayCreditDetailVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 汇计划-资金计划-复投承接债权
 * @Author : huanghui
 */
@Api(value = "汇计划-资金计划")
@RestController
@RequestMapping("/am-trade/hjhDayCreditDetail")
public class DayCreditDetailController {

    @Autowired
    private DayCreditDetailService dayCreditDetailService;

    @RequestMapping(value = "/hjhDayCreditList", method = RequestMethod.POST)
    public DayCreditDetailResponse hjhDayCreditList(DayCreditDetailRequest request){
        DayCreditDetailResponse response = new DayCreditDetailResponse();

        List<DayCreditDetailVO> responseList = this.dayCreditDetailService.selectDebtCreditList(request);

        response.setResultList(responseList);
        return response;
    }
}
