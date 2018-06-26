package com.hyjf.am.trade.controller;


import com.hyjf.am.response.trade.BorrowCarinfoResponse;
import com.hyjf.am.response.trade.BorrowHousesResponse;
import com.hyjf.am.trade.dao.model.auto.BorrowCarinfo;
import com.hyjf.am.trade.dao.model.auto.BorrowHouses;
import com.hyjf.am.trade.service.BorrowCarinfoService;
import com.hyjf.am.trade.service.BorrowHousesService;
import com.hyjf.am.vo.trade.borrow.BorrowCarinfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowHousesVO;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/am-trade/borrow")
public class BorrowCarinfoController {

    private static final Logger logger = LoggerFactory.getLogger(BorrowCarinfoController.class);


    @Autowired
    private BorrowCarinfoService borrowCarinfoService;

    /**
     * 查询借款人房产抵押信息
     * @author zhangyk
     * @date 2018/6/25 16:35
     */
    @GetMapping("/borrowcarinfo/{borrowNid}")
    public BorrowCarinfoResponse getborrowHousesByNid(String borrowNid) {
        BorrowCarinfoResponse response = new BorrowCarinfoResponse();
        List<BorrowCarinfo> list = borrowCarinfoService.getBorrowCarinfoListByNid(borrowNid);
        if (!CollectionUtils.isEmpty(list)) {
            List<BorrowCarinfoVO> result = CommonUtils.convertBeanList(list,BorrowCarinfoVO.class);
            response.setResultList(result);
        }
        return response;
    }
}
