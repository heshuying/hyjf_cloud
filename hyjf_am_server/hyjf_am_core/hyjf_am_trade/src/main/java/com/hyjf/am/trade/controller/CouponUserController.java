/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.CouponUserResponse;
import com.hyjf.am.trade.dao.model.auto.CouponUser;
import com.hyjf.am.trade.service.CouponUserService;
import com.hyjf.am.vo.trade.CouponUserVo;
import com.hyjf.common.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yaoy
 * @version CouponUserController, v0.1 2018/6/19 18:48
 */
@RestController
@RequestMapping("/am-trade/couponUser")
public class CouponUserController {

    private static final Logger logger = LoggerFactory.getLogger(CouponUserController.class);
    @Autowired
    private CouponUserService couponUserService;

    @RequestMapping("/selectCouponUser/{nowBeginDate}/{nowEndDate}")
    public CouponUserResponse selectCouponUser(@PathVariable int nowBeginDate,int nowEndDate) {
        CouponUserResponse response = new CouponUserResponse();
        List<CouponUser> couponUserList = couponUserService.selectCouponUser(nowBeginDate,nowEndDate);
        if (!CollectionUtils.isEmpty(couponUserList)) {
            List<CouponUserVo> couponUserVoList = CommonUtils.convertBeanList(couponUserList,CouponUserVo.class);
            response.setResultList(couponUserVoList);
        }
        return response;
    }
}
