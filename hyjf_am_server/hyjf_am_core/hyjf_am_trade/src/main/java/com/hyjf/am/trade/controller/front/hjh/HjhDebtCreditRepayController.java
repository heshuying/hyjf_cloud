package com.hyjf.am.trade.controller.front.hjh;


import com.hyjf.am.response.trade.HjhDebtCreditRepayResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCreditRepay;
import com.hyjf.am.trade.service.front.hjh.HjhDebtCreditRepayService;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditRepayVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 汇计划债转还款记录
 * @author hesy
 * @version HjhDebtCreditRepayController, v0.1 2018/6/24 10:48
 */
@RestController
@RequestMapping("/am-trade/hjhcreditrepay")
public class HjhDebtCreditRepayController extends BaseController {

    @Autowired
    HjhDebtCreditRepayService hjhDebtCreditRepayService;

    /**
     * 查询汇计划债转还款记录
     * @param borrowNid
     * @param tenderOrderId
     * @param periodNow
     * @param status
     * @return
     */
    @GetMapping("/get/{borrowNid}/{tenderOrderId}/{periodNow}/{status}")
    public HjhDebtCreditRepayResponse selectHjhDebtCreditRepay(@PathVariable String borrowNid, @PathVariable String tenderOrderId, @PathVariable Integer periodNow, @PathVariable Integer status) {
        HjhDebtCreditRepayResponse response = new HjhDebtCreditRepayResponse();
        List<HjhDebtCreditRepay> list = hjhDebtCreditRepayService.selectHjhDebtCreditRepay(borrowNid,tenderOrderId,periodNow,status);
        if (!CollectionUtils.isEmpty(list)) {
            List<HjhDebtCreditRepayVO> result = CommonUtils.convertBeanList(list,HjhDebtCreditRepayVO.class);
            response.setResultList(result);
        }
        return response;
    }


}
