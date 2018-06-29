package com.hyjf.am.trade.controller;


import com.hyjf.am.response.trade.BorrowHousesResponse;
import com.hyjf.am.trade.dao.model.auto.BorrowHouses;
import com.hyjf.am.trade.service.BorrowHousesService;
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
public class BorrowHousesController {

    private static final Logger logger = LoggerFactory.getLogger(BorrowHousesController.class);


    @Autowired
    private BorrowHousesService borrowHousesService;

    /**
     * 查询借款人房产抵押信息
     * @author zhangyk
     * @date 2018/6/25 16:35
     */
    @GetMapping("/borrowhouses/{borrowNid}")
    public BorrowHousesResponse getborrowHousesByNid(String borrowNid) {
        BorrowHousesResponse response = new BorrowHousesResponse();
        List<BorrowHouses> list = borrowHousesService.getBorrowHousesByNid(borrowNid);
        if (!CollectionUtils.isEmpty(list)) {
            List<BorrowHousesVO> result = CommonUtils.convertBeanList(list,BorrowHousesVO.class);
            response.setResultList(result);
        }
        return response;
    }


}