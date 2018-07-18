package com.hyjf.am.trade.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.response.trade.BorrowManinfoResponse;
import com.hyjf.am.trade.dao.model.auto.BorrowManinfo;
import com.hyjf.am.trade.service.BorrowManinfoService;
import com.hyjf.am.vo.trade.borrow.BorrowManinfoVO;
import com.hyjf.common.util.CommonUtils;

/**
 * @author xiasq
 * @version BorrowManinfoController, v0.1 2018/6/24 10:48
 */
@RestController
@RequestMapping("/am-trade/borrow")
public class BorrowManinfoController extends BaseController{

    @Autowired
    private BorrowManinfoService borrowManinfoService;

    /**
     * 查询借款人公司信息
     * @author zhangyk
     * @date 2018/6/25 16:35
     */
    @GetMapping("/borrowManinfo/{borrowNid}")
    public BorrowManinfoResponse getborrowUser(String borrowNid) {
        BorrowManinfoResponse response = new BorrowManinfoResponse();
        BorrowManinfo borrowManinfo = borrowManinfoService.getBorrowManinfoByNid(borrowNid);
        if (null != borrowManinfo) {
            BorrowManinfoVO borrowManinfoVO = CommonUtils.convertBean(borrowManinfo,BorrowManinfoVO.class);
            response.setResult(borrowManinfoVO);
        }
        return response;
    }
}
