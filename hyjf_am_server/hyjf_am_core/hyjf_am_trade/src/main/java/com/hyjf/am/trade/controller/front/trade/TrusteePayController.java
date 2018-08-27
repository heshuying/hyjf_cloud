package com.hyjf.am.trade.controller.front.trade;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.trade.STZHWhiteListResponse;
import com.hyjf.am.trade.dao.model.auto.StzhWhiteList;
import com.hyjf.am.trade.service.front.trade.TrusteePayService;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/am-trade/trustee")
public class TrusteePayController {
    @Autowired
    private TrusteePayService trusteePayService;

    /**
     * 借款人受托支付申请异步回调更新
     *
     * @param borrowNid
     * @return
     */
    @GetMapping("/update/{borrowNid}")
    public BooleanResponse update(@PathVariable String borrowNid) {
        return trusteePayService.update(borrowNid);
    }

    @GetMapping("/getSTZHWhiteList/{instCode}/{receiptAccountId}")
    public STZHWhiteListResponse getSTZHWhiteList(@PathVariable String instCode, @PathVariable String receiptAccountId){
        STZHWhiteListResponse response = new STZHWhiteListResponse();
        StzhWhiteList stzhWhiteList = trusteePayService.getSTZHWhiteList(instCode, receiptAccountId);
        if(stzhWhiteList != null){
            STZHWhiteListVO vo = new STZHWhiteListVO();
            BeanUtils.copyProperties(stzhWhiteList, vo);
            response.setResult(vo);
        }
        return response;
    }
}
