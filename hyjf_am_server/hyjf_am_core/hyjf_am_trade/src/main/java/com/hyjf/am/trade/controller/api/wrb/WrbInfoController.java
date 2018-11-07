/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.api.wrb;

import com.hyjf.am.response.trade.BorrowTenderResponse;
import com.hyjf.am.response.trade.WrbBorrowListResponse;
import com.hyjf.am.response.trade.WrbBorrowTenderCustomizeResponse;
import com.hyjf.am.resquest.api.WrbInvestRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.dao.model.customize.WrbBorrowListCustomize;
import com.hyjf.am.trade.dao.model.customize.WrbBorrowTenderCustomize;
import com.hyjf.am.trade.service.api.wrb.WrbInfoService;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.wrb.WrbBorrowListCustomizeVO;
import com.hyjf.am.vo.trade.wrb.WrbBorrowTenderCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fq
 * @version WrbinfoController, v0.1 2018/9/25 11:09
 */
@RestController
@RequestMapping("/am-trade/wrb")
public class WrbInfoController extends BaseController {
    @Autowired
    private WrbInfoService wrbInfoService;

    /**
     * 标的查询
     * @param borrowNid
     * @return
     */
    @RequestMapping("/borrow_list")
    public WrbBorrowListResponse borrowList(@PathVariable String borrowNid) {
        WrbBorrowListResponse response = new WrbBorrowListResponse();
        List<WrbBorrowListCustomize> list = wrbInfoService.borrowList(borrowNid);
        if (!CollectionUtils.isEmpty(list)) {
            List<WrbBorrowListCustomizeVO> voList = CommonUtils.convertBeanList(list, WrbBorrowListCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 获取某天投资数据
     * @param request
     * @return
     */
    @RequestMapping("/borrow_tender")
    public BorrowTenderResponse getBorrowTenderList(@RequestBody WrbInvestRequest request) {
        BorrowTenderResponse response = new BorrowTenderResponse();
        // 获取某天投资数据
        List<BorrowTender> list = wrbInfoService.getBorrowTenderList(request);
        if (!CollectionUtils.isEmpty(list)) {
            List<BorrowTenderVO> voList = CommonUtils.convertBeanList(list, BorrowTenderVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 查询标的投资情况
     * @return
     */
    @RequestMapping("/wrb_borrow_tender")
    public WrbBorrowTenderCustomizeResponse getBorrowTenderByBorrowNid(@RequestBody WrbInvestRequest request) {
        WrbBorrowTenderCustomizeResponse response = new WrbBorrowTenderCustomizeResponse();
        // 查询标的投资情况
        List<WrbBorrowTenderCustomize> list = wrbInfoService.getBorrowTenderByBorrowNid(request);
        if (!CollectionUtils.isEmpty(list)) {
            List<WrbBorrowTenderCustomizeVO> voList = CommonUtils.convertBeanList(list, WrbBorrowTenderCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }
}
