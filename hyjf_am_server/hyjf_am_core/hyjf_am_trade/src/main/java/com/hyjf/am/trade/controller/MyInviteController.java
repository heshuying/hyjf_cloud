package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.MyCouponListResponse;
import com.hyjf.am.response.trade.MyInviteListResponse;
import com.hyjf.am.response.trade.MyRewardListResponse;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.trade.service.MyInviteService;
import com.hyjf.am.vo.trade.MyInviteListCustomizeVO;
import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 我的邀请及奖励
 * @author hesy
 * @version MyInviteController, v0.1 2018/6/23 12:19
 */
@RestController
@RequestMapping("/am-trade/invite")
public class MyInviteController {
    @Autowired
    MyInviteService myInviteService;

    /**
     * 我的邀请
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/myInviteList", method = RequestMethod.POST)
    public MyInviteListResponse myInviteList(@RequestBody @Valid MyInviteListRequest requestBean) {
        MyInviteListResponse responseBean = new MyInviteListResponse();

        List<MyInviteListCustomizeVO> resultList = myInviteService.selectMyInviteList(requestBean.getUserId(),requestBean.getLimitStart(),requestBean.getLimitEnd());
        responseBean.setResultList(resultList);

        return responseBean;
    }

    /**
     * 我的奖励
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/myRewardList", method = RequestMethod.POST)
    public MyRewardListResponse myRewardList(@RequestBody @Valid MyInviteListRequest requestBean) {
        MyRewardListResponse responseBean = new MyRewardListResponse();

        List<MyRewardRecordCustomizeVO> resultList = myInviteService.selectMyRewardList(requestBean.getUserId(),requestBean.getLimitStart(),requestBean.getLimitEnd());
        responseBean.setResultList(resultList);

        return responseBean;
    }
}
