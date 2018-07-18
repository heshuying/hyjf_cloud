package com.hyjf.am.trade.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.trade.BorrowCarinfoResponse;
import com.hyjf.am.trade.dao.model.auto.BorrowCarinfo;
import com.hyjf.am.trade.service.BorrowCarinfoService;
import com.hyjf.am.vo.trade.borrow.BorrowCarinfoVO;
import com.hyjf.common.util.CommonUtils;

/**
 * @author $xiasq
 * @version BorrowCarinfoController, v0.1 2018/6/14 16:44
 */
@RestController
@RequestMapping("/am-trade/borrow")
public class BorrowCarinfoController extends BaseController {

    @Autowired
    private BorrowCarinfoService borrowCarinfoService;

    /**
     * 查询散标借款人汽车抵押信息
     * @author zhangyk
     * @date 2018/6/25 16:35
     */
    @GetMapping("/borrowCarinfo/{borrowNid}")
    public BorrowCarinfoResponse getBorrowCarinfoByNid(@PathVariable String borrowNid) {
        BorrowCarinfoResponse response = new BorrowCarinfoResponse();
        List<BorrowCarinfo> list = borrowCarinfoService.getBorrowCarinfoListByNid(borrowNid);
        if (!CollectionUtils.isEmpty(list)) {
            List<BorrowCarinfoVO> result = CommonUtils.convertBeanList(list,BorrowCarinfoVO.class);
            response.setResultList(result);
        }
        return response;
    }


}
