package com.hyjf.am.trade.controller;

import com.hyjf.am.response.admin.CouponRecoverResponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.trade.coupon.CouponResponse;
import com.hyjf.am.trade.dao.model.auto.BorrowTenderCpn;
import com.hyjf.am.trade.dao.model.auto.CouponRecover;
import com.hyjf.am.trade.dao.model.customize.trade.CouponCustomize;
import com.hyjf.am.trade.service.CouponService;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.coupon.CouponTenderVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description 优惠券相关
 * @Author sunss
 * @Version v0.1
 * @Date 2018/6/19 16:32
 */
@Api(value = "优惠券相关")
@RestController
@RequestMapping("/am-trade/coupon")
public class CouponController extends BaseController{

    @Autowired
    CouponService couponService;

    @ApiOperation(value = " 根据用户ID和优惠券编号查询优惠券")
    @GetMapping("/getCouponUser/{couponId}/{userId}")
    public CoupUserResponse getCouponUser(@PathVariable(value = "couponId") String couponId,
                                                       @PathVariable(value = "userId") Integer userId) {
        CoupUserResponse response = new CoupUserResponse();
        CouponCustomize couponUser  = couponService.getCouponUser(couponId,userId);
        if(null != couponUser){
            CouponUserVO couponUserVO = new CouponUserVO();
            BeanUtils.copyProperties(couponUser,couponUserVO);
            response.setResult(couponUserVO);
        }
        return response;
    }

    @ApiOperation(value = " 优惠券投资")
    @GetMapping("/updateCouponTender")
    public Integer updateCouponTender(CouponTenderVO couponTender) {
        try{
            couponService.updateCouponTender(couponTender);
            return 1;
        }catch (Exception e ) {
            return 0;
        }
    }

    @ApiOperation(value = "获取优惠券投资信息")
    @GetMapping("/getCouponTenderByTender/{userId}/{borrowNid}/{logOrdId}/{couponGrantId}")
    public BorrowTenderCpnResponse getCouponTenderByTender(Integer userId, String borrowNid, String logOrdId, Integer couponGrantId) {
        BorrowTenderCpnResponse response = new BorrowTenderCpnResponse();
        BorrowTenderCpn cpn = couponService.getCouponTenderByTender(userId,borrowNid,logOrdId,couponGrantId);
        if(null != cpn){
            BorrowTenderCpnVO borrowTenderCpnVO = new BorrowTenderCpnVO();
            BeanUtils.copyProperties(cpn,borrowTenderCpnVO);
            response.setResult(borrowTenderCpnVO);
        }
        return response;
    }

    @ApiOperation(value = "获取优惠券还款记录")
    @GetMapping("/getCouponRecoverByPrimaryKey/{id}")
    public CouponRecoverResponse getCouponRecoverByPrimaryKey(@PathVariable Integer id){
        CouponRecoverResponse response = new CouponRecoverResponse();
        CouponRecover recover=couponService.getCouponRecoverByPrimaryKey(id);
        if (Validator.isNotNull(recover)){
            response.setResult(CommonUtils.convertBean(recover,CouponRecoverVO.class));
        }
        return response;
    }

    @ApiOperation(value = "获取优惠券投资信息")
    @GetMapping("/getCouponTenderInfoByNid/{nid}")
    public BorrowTenderCpnResponse getCouponTenderInfoByNid(@PathVariable String nid){
        BorrowTenderCpnResponse response=new BorrowTenderCpnResponse();
        BorrowTenderCpn borrowTenderCpn=couponService.getCouponTenderInfoByNid(nid);
        if (Validator.isNotNull(borrowTenderCpn)){
            response.setResult(CommonUtils.convertBean(borrowTenderCpn,BorrowTenderCpnVO.class));
        }
        return response;
    }

    @ApiOperation(value = "获取汇计划投资列表（优惠券）")
    @GetMapping("/getborrowtendercpnhjhlist/{orderId}")
    public CouponResponse getBorrowTenderCpnHjhList(@PathVariable String orderId){
        CouponResponse response=new CouponResponse();
        List<BorrowTenderCpn> list = couponService.getBorrowTenderCpnHjhList(orderId);
        response.setResultList(CommonUtils.convertBeanList(list,BorrowTenderCpnVO.class));
        return response;
    }

    @ApiOperation(value = "优惠券单独投资时用")
    @GetMapping("/getborrowtendercpnhjhcoupononlylist/{couponOrderId}")
    public CouponResponse getBorrowTenderCpnHjhCouponOnlyList(@PathVariable String couponOrderId){
        CouponResponse response=new CouponResponse();
        List<BorrowTenderCpn> list = couponService.getBorrowTenderCpnHjhCouponOnlyList(couponOrderId);
        response.setResultList(CommonUtils.convertBeanList(list,BorrowTenderCpnVO.class));
        return response;
    }

    @ApiOperation(value = "更新放款状态(优惠券)")
    @GetMapping("/updateborrowtendercpn")
    public CouponResponse updateBorrowTenderCpn(@RequestBody BorrowTenderCpn borrowTenderCpn){
        CouponResponse response=new CouponResponse();
        Integer recode = couponService.updateBorrowTenderCpn(borrowTenderCpn);
        response.setTotalRecord(recode);
        return response;
    }

    /**
     * @Author walter.limeng
     * @Description  根据borrowNid获取优惠券放款数据
     * @Date 18:18 2018/7/18
     * @Param
     * @return
     */
    @ApiOperation(value = "根据borrowNid获取优惠券放款数据")
    @GetMapping("/getborrowtendercpnlist/{borrowNid}")
    public BorrowTenderCpnResponse getBorrowTenderCpnList(@PathVariable String borrowNid){
        BorrowTenderCpnResponse response=new BorrowTenderCpnResponse();
        List<BorrowTenderCpn> list = couponService.getBorrowTenderCpnList(borrowNid);
        response.setResultList(CommonUtils.convertBeanList(list,BorrowTenderCpnVO.class));
        return response;
    }

}
