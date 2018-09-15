package com.hyjf.am.trade.controller.front.borrow;


import com.hyjf.am.response.trade.ApiInvestResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.vo.trade.InvestListCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hyjf.am.trade.service.front.borrow.BorrowListService;

import java.util.List;
import java.util.Map;

/**
 * 投资信息
 * @author wenxin
 * @version ProjectListController, v0.1 2018/8/29 11:15
 */
@RestController
@RequestMapping("/am-trade/investList")
public class BorrowListController  extends BaseController {

    @Autowired
    private BorrowListService borrowListService;

    /**
     * api： 投资记录查询列表
     * @author wenxin
     * @date 2018/8/29 17:47
     */
    @PostMapping("api/getInvestList")
    public ApiInvestResponse searchInvestRepaysListNew(@RequestBody Map<String,Object> params) {
        ApiInvestResponse response = new ApiInvestResponse();
        List<InvestListCustomizeVO> list = borrowListService.InvestRepaysList(params);
        response.setResultList(list);
        return response;
    }
}
