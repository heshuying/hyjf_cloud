/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.batch;

import com.hyjf.am.response.trade.BorrowTenderCpnResponse;
import com.hyjf.am.response.trade.CouponRecoverCustomizeResponse;
import com.hyjf.am.resquest.trade.CouponRecoverCustomizeRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowTenderCpn;
import com.hyjf.am.trade.dao.model.auto.CouponRecover;
import com.hyjf.am.trade.dao.model.customize.CouponRecoverCustomize;
import com.hyjf.am.trade.service.task.CouponRepayService;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.coupon.CouponRecoverCustomizeVO;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 * @version BatchCouponRepayController, v0.1 2018/8/2 16:10
 * 体验金收益期限还款
 */
@RestController
@RequestMapping("/am-trade/couponperiodrepay")
public class BatchCouponRepayController extends BaseController {

    @Autowired
    private CouponRepayService couponRepayService;
    @Value("${hyjf.bank.merrp.account}")
    private String BANK_MERRP_ACCOUNT;

    /**
     *优惠券单独出借还款
     * @return
     */
    @PostMapping("/selectnidforcoupononly")
    public List<String> selectNidForCouponOnly() {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        List<String> recoverNidList = couponRepayService.selectNidForCouponOnly(paramMap);
        if (!CollectionUtils.isEmpty(recoverNidList)) {
            return recoverNidList;
        }
        return null;
    }

    /**
     * 根据订单编号取得该订单的还款列表
     * @param couponTenderNid
     * @param periodNow
     * @return
     */
    @GetMapping("/selectcurrentcouponrecover/{couponTenderNid}/{periodNow}")
    public CouponRecoverCustomizeResponse selectCurrentCouponRecover(@PathVariable String couponTenderNid,
                                                                     @PathVariable int periodNow) {
        logger.info("selectCurrentCouponRecover run, couponTenderNid is :{}, periodNow is: {}", couponTenderNid, periodNow);
        CouponRecoverCustomizeResponse response = new CouponRecoverCustomizeResponse();
        Map<String,Object> map = new HashMap<>();
        map.put("tenderNid",couponTenderNid);
        map.put("periodNow",periodNow);
        CouponRecoverCustomize customize = couponRepayService.selectCurrentCouponRecover(map);
        logger.info("return customize is : {}", customize);
        if (customize != null) {
            CouponRecoverCustomizeVO customizeVO = new CouponRecoverCustomizeVO();
            BeanUtils.copyProperties(customize,customizeVO);
            response.setResult(customizeVO);
        }
        return response;
    }

    /**
     * 取得优惠券出借信息
     * @param couponTenderNid
     * @return
     */
    @GetMapping("/getcoupontenderinfo/{couponTenderNid}")
    public BorrowTenderCpnResponse getCouponTenderInfo(@PathVariable String couponTenderNid) {
        BorrowTenderCpnResponse response = new BorrowTenderCpnResponse();
        BorrowTenderCpn borrowTenderCpn = couponRepayService.getCouponTenderInfo(couponTenderNid);
        response.setResult(CommonUtils.convertBean(borrowTenderCpn,BorrowTenderCpnVO.class));
        return response;
    }

    /**
     * 更新优惠券还款
     * @param recoverVO
     * @return
     */
    @PostMapping("/updatecouponrecover")
    public Integer updateCouponRecover(@RequestBody CouponRecoverVO recoverVO) {
        CouponRecover couponRecover = new CouponRecover();
        BeanUtils.copyProperties(recoverVO,couponRecover);
        try{
            couponRepayService.updateCouponRecover(couponRecover);
            return 1;
        }catch (Exception e ) {
            return 0;
        }
    }

    /**
     * 体验金按收益期限还款
     * @param request
     * @return
     */
    @PostMapping("/updatecoupononlyrecover")
    public Integer updateCouponOnlyRecover(@RequestBody CouponRecoverCustomizeRequest request) {
        Integer result = null;
        try {
            result = couponRepayService.updateCouponOnlyRecover(request);
        } catch (MQException e) {
            logger.error(e.getMessage());
            return 0;
        }
        return result;




    }

}
