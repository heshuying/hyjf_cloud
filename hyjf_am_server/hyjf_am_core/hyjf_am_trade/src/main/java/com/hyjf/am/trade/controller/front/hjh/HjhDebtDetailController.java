/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.hjh;

import com.hyjf.am.response.trade.HjhDebtDetailResponse;
import com.hyjf.am.trade.dao.model.auto.HjhDebtDetail;
import com.hyjf.am.trade.service.front.hjh.HjhDebtDetailService;
import com.hyjf.am.vo.trade.hjh.HjhDebtDetailVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangjun
 * @version HjhDebtDetailController, v0.1 2018/7/17 10:04
 */
@RestController
@RequestMapping("/am-trade/hjhDebtDetail")
public class HjhDebtDetailController {
    @Autowired
    HjhDebtDetailService hjhDebtDetailService;

    @GetMapping("/selectHjhDebtDetailListByAccedeOrderId/{accedeOrderId}")
    public HjhDebtDetailResponse selectHjhDebtDetailListByAccedeOrderId(@PathVariable String accedeOrderId){
        HjhDebtDetailResponse response = new HjhDebtDetailResponse();
        List<HjhDebtDetail> list=hjhDebtDetailService.selectHjhDebtDetailListByAccedeOrderId(accedeOrderId);
        if(CollectionUtils.isNotEmpty(list)){
            List<HjhDebtDetailVO> voList = CommonUtils.convertBeanList(list,HjhDebtDetailVO.class);
            response.setResultList(voList);
        }
        return response;
    }
}
