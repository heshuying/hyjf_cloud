package com.hyjf.am.trade.controller.front.borrow;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.trade.BorrowHousesResponse;
import com.hyjf.am.trade.dao.model.auto.BorrowHouses;
import com.hyjf.am.trade.service.BorrowHousesService;
import com.hyjf.am.vo.trade.borrow.BorrowHousesVO;
import com.hyjf.common.util.CommonUtils;

/**
 * @author xiasq
 * @version BorrowHousesController, v0.1 2018/6/24 10:48
 */
@RestController
@RequestMapping("/am-trade/borrow")
public class BorrowHousesController extends BaseController{

    @Autowired
    private BorrowHousesService borrowHousesService;

    /**
     * 查询借款人房产抵押信息
     * @author zhangyk
     * @date 2018/6/25 16:35
     */
    @GetMapping("/borrowhouses/{borrowNid}")
    public BorrowHousesResponse getborrowHousesByNid(@PathVariable  String borrowNid) {
        BorrowHousesResponse response = new BorrowHousesResponse();
        List<BorrowHouses> list = borrowHousesService.getBorrowHousesByNid(borrowNid);
        if (!CollectionUtils.isEmpty(list)) {
            List<BorrowHousesVO> result = CommonUtils.convertBeanList(list,BorrowHousesVO.class);
            response.setResultList(result);
        }
        return response;
    }


}
