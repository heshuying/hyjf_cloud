package com.hyjf.am.trade.controller.repay;

import com.hyjf.am.response.trade.MyCouponListResponse;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.trade.service.coupon.MyCouponListService;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 还款管理
 * @author hesy
 * @version RepayManageController, v0.1 2018/6/26 17:41
 */
@RestController
@RequestMapping("/am-trade/repay")
public class RepayManageController {
    @Autowired
    MyCouponListService myCouponListService;

    /**
     * 查询我的优惠券列表
     * @auther: hesy
     * @date: 2018/6/23
     */
    @RequestMapping(value = "/myCouponList")
    public MyCouponListResponse myCouponList(@RequestBody @Valid MyCouponListRequest requestBean) {
        MyCouponListResponse responseBean = new MyCouponListResponse();

        List<MyCouponListCustomizeVO> resultList = myCouponListService.selectUserCouponList(requestBean.getUserId(),requestBean.getUsedFlag(),requestBean.getLimitStart(),requestBean.getLimitEnd());
        responseBean.setResultList(resultList);

        return responseBean;
    }



}
