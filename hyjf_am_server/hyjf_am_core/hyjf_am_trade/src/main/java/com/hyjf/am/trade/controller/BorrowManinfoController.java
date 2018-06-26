package com.hyjf.am.trade.controller;


import com.hyjf.am.response.trade.BorrowManinfoResponse;
import com.hyjf.am.response.trade.BorrowUserResponse;
import com.hyjf.am.trade.dao.model.auto.BorrowManinfo;
import com.hyjf.am.trade.dao.model.auto.BorrowUsers;
import com.hyjf.am.trade.service.BorrowManinfoService;
import com.hyjf.am.vo.trade.borrow.BorrowManinfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowUserVO;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/am-trade/borrow")
public class BorrowManinfoController {

    private static final Logger logger = LoggerFactory.getLogger(BorrowUserController.class);


    @Autowired
    private BorrowManinfoService borrowManinfoService;

    /**
     * 查询借款人公司信息
     * @author zhangyk
     * @date 2018/6/25 16:35
     */
    @GetMapping("/borrowmaninfo/{borrowNid}")
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
