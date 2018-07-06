package com.hyjf.am.trade.controller.repay;

import com.hyjf.am.response.trade.BorrowAuthResponse;
import com.hyjf.am.resquest.trade.BorrowAuthRequest;
import com.hyjf.am.trade.service.repay.BorrowAuthService;
import com.hyjf.am.vo.trade.repay.BorrowAuthCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 借款人受托支付申请
 * @author hesy
 * @version BorrowAuthController, v0.1 2018/7/6 11:31
 */
@RestController
@RequestMapping("/am-trade/borrowauth")
public class BorrowAuthController {
    @Autowired
    BorrowAuthService borrowAuthService;

    /**
     * 获取借款人待授权总记录数
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/count_auth")
    public BorrowAuthResponse selectAuthCount(@RequestBody @Valid BorrowAuthRequest requestBean) {
        BorrowAuthResponse responseBean = new BorrowAuthResponse();
        List<BorrowAuthCustomizeVO> resultList = borrowAuthService.searchBorrowNeedAuthList(requestBean);
        responseBean.setResultList(resultList);

        return responseBean;
    }

    /**
     * 获取接口人待授权列表
     * @param: [requestBean]
     * @return: com.hyjf.am.response.trade.BorrowAuthResponse
     * @auther: hesy
     * @date: 2018/7/6 14:01
     */
    @RequestMapping(value = "/list_auth")
    public BorrowAuthResponse selectListAuth(@RequestBody @Valid BorrowAuthRequest requestBean) {
        BorrowAuthResponse responseBean = new BorrowAuthResponse();
        List<BorrowAuthCustomizeVO> resultList = borrowAuthService.searchBorrowNeedAuthList(requestBean);
        responseBean.setResultList(resultList);

        return responseBean;
    }

    /**
     * 获取借款人已授权总记录数
     * @auther: hesy
     * @date: 2018/7/6
     */
    @RequestMapping(value = "/count_authed")
    public BorrowAuthResponse selectAuthedCount(@RequestBody @Valid BorrowAuthRequest requestBean) {
        BorrowAuthResponse responseBean = new BorrowAuthResponse();
        List<BorrowAuthCustomizeVO> resultList = borrowAuthService.searchBorrowAuthedList(requestBean);
        responseBean.setResultList(resultList);

        return responseBean;
    }

    /**
     * 获取借款人已授权列表
     * @auther: hesy
     * @date: 2018/7/6
     */
    @RequestMapping(value = "/list_authed")
    public BorrowAuthResponse selectListAuthed(@RequestBody @Valid BorrowAuthRequest requestBean) {
        BorrowAuthResponse responseBean = new BorrowAuthResponse();
        List<BorrowAuthCustomizeVO> resultList = borrowAuthService.searchBorrowAuthedList(requestBean);
        responseBean.setResultList(resultList);

        return responseBean;
    }
}
