package com.hyjf.am.trade.controller;


import com.hyjf.am.response.trade.BorrowApicronResponse;
import com.hyjf.am.response.trade.BorrowUserResponse;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.dao.model.auto.BorrowUsers;
import com.hyjf.am.trade.service.BorrowUserService;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.trade.borrow.BorrowUserVO;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/am-trade/borrowuser")
public class BorrowUserController {

    private static final Logger logger = LoggerFactory.getLogger(BorrowUserController.class);

    @Autowired
    private BorrowUserService borrowUserService;

    /**
     * 查询借款人公司信息
     * @author zhangyk
     * @date 2018/6/25 16:35
     */
    @GetMapping("/borrowuserinfo/{borrowNid}")
    public BorrowUserResponse getborrowUser(String borrowNid) {
        BorrowUserResponse response = new BorrowUserResponse();
        BorrowUsers borrowUsers = borrowUserService.getBorrowUserByNid(borrowNid);
        if (null != borrowUsers) {
            BorrowUserVO borrowUserVO = CommonUtils.convertBean(borrowUsers,BorrowUserVO.class);
            response.setResult(borrowUserVO);
        }
        return response;
    }
}
