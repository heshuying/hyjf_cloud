package com.hyjf.am.trade.controller.front.coupon;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.BigDecimalResponse;
import com.hyjf.am.response.admin.CouponRecoverResponse;
import com.hyjf.am.response.trade.BorrowTenderCpnResponse;
import com.hyjf.am.response.trade.CoupUserResponse;
import com.hyjf.am.response.trade.CouponUserResponse;
import com.hyjf.am.response.trade.coupon.*;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.CouponCustomize;
import com.hyjf.am.trade.service.front.coupon.CouponService;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.coupon.*;
import com.hyjf.am.vo.trade.repay.CurrentHoldRepayMentPlanListVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description 优惠券相关
 * @Author sunss
 * @Version v0.1
 * @Date 2018/6/19 16:32
 */
@Api(value = "优惠券相关")
@RestController
@RequestMapping("/am-trade/coupon")
public class CouponController extends BaseController {

    @Autowired
    CouponService couponService;

    @ApiOperation(value = " 根据用户ID和优惠券编号查询优惠券")
    @GetMapping("/getCouponUser/{couponId}/{userId}")
    public CoupUserResponse getCouponUser(@PathVariable(value = "couponId") String couponId,
                                                       @PathVariable(value = "userId") Integer userId) {
        logger.info("根据用户ID和优惠券编号查询优惠券, couponId is: {}, userId is: {}", couponId, userId);
        CoupUserResponse response = new CoupUserResponse();
        CouponCustomize couponUser  = couponService.getCouponUser(couponId,userId);
        if(null != couponUser){
            CouponUserVO couponUserVO = new CouponUserVO();
            BeanUtils.copyProperties(couponUser,couponUserVO);
            response.setResult(couponUserVO);
            logger.debug("根据用户ID和优惠券编号查询优惠券, couponUserVO is: {}", JSONObject.toJSONString(couponUserVO));
        }
        return response;
    }

    @ApiOperation(value = " 优惠券出借")
    @PostMapping("/updateCouponTender")
    public Integer updateCouponTender(@RequestBody CouponTenderVO couponTender) {
        try{
            logger.info("优惠券出借开始。。。。。。。");
            couponService.updateCouponTender(couponTender);
            return 1;
        }catch (Exception e ) {
            logger.error(e.getMessage());
            logger.info("chucuole :::"+JSONObject.toJSONString(e));
            return 0;
        }
    }

    @ApiOperation(value = "获取优惠券出借信息")
    @GetMapping("/getCouponTenderByTender/{userId}/{borrowNid}/{logOrdId}/{couponGrantId}")
    public BorrowTenderCpnResponse getCouponTenderByTender(@PathVariable("userId") Integer userId,
                                                           @PathVariable("borrowNid") String borrowNid,
                                                           @PathVariable("logOrdId") String logOrdId,
                                                           @PathVariable("couponGrantId") Integer couponGrantId) {
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

    @ApiOperation(value = "获取优惠券出借信息")
    @GetMapping("/getCouponTenderInfoByNid/{nid}")
    public BorrowTenderCpnResponse getCouponTenderInfoByNid(@PathVariable String nid){
        BorrowTenderCpnResponse response=new BorrowTenderCpnResponse();
        BorrowTenderCpn borrowTenderCpn=couponService.getCouponTenderInfoByNid(nid);
        if (Validator.isNotNull(borrowTenderCpn)){
            response.setResult(CommonUtils.convertBean(borrowTenderCpn,BorrowTenderCpnVO.class));
        }
        return response;
    }

    @ApiOperation(value = "获取汇计划出借列表（优惠券）")
    @GetMapping("/getborrowtendercpnhjhlist/{orderId}")
    public HjhCouponLoansResponse getBorrowTenderCpnHjhList(@PathVariable String orderId){
        HjhCouponLoansResponse response=new HjhCouponLoansResponse();
        List<BorrowTenderCpn> list = couponService.getBorrowTenderCpnHjhList(orderId);
        response.setResultList(CommonUtils.convertBeanList(list,BorrowTenderCpnVO.class));
        return response;
    }

    @ApiOperation(value = "优惠券单独出借时用")
    @GetMapping("/getborrowtendercpnhjhcoupononlylist/{couponOrderId}")
    public HjhCouponLoansResponse getBorrowTenderCpnHjhCouponOnlyList(@PathVariable String couponOrderId){
        HjhCouponLoansResponse response=new HjhCouponLoansResponse();
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



    @ApiOperation(value = "根据Nid和userId获取优惠券放款数据")
    @PostMapping("/getborrowtendercpnByUserIdAndOrderId/")
    public AppCouponInfoResponse getborrowtendercpnByUserIdAndOrderId(@RequestBody @Valid Map<String,Object> params){
        AppCouponInfoResponse response = new AppCouponInfoResponse();
        AppCouponInfoCustomizeVO infoCustomizeVO = couponService.getCouponInfo(params);
        response.setResult(infoCustomizeVO);
        return response;
    }


    @ApiOperation(value = "根据Nid获取优惠券还款数据")
    @GetMapping("/getCounponRecoverList/{nid}")
    public CouponRepayResponse getCounponRecoverList(@PathVariable String nid){
        CouponRepayResponse response = new CouponRepayResponse();
        List<CurrentHoldRepayMentPlanListVO> list = couponService.getCouponRecoverList(nid);
        response.setResultList(list);
        return response;
    }

    @ApiOperation(value = "获取我的优惠券出借记录")
    @PostMapping("/getAppMyPlanCouponInfo")
    public AppCouponResponse getAppMyPlanCouponInfo(@RequestBody Map<String,Object> params){
        AppCouponResponse response = new AppCouponResponse();
        List<AppCouponCustomizeVO> list = couponService.getAppMyPlanCouponInfo(params);
        response.setResultList(list);
        return response;
    }

    /**
     * 根据订单号查询此笔出借是否使用优惠券
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/selectCouponRealTenderByOrderId/{orderId}")
    public CouponRealTenderResponse selectCouponRealTenderByOrderId(@PathVariable String orderId) {
        CouponRealTenderResponse response = new CouponRealTenderResponse();
        CouponRealTender couponRealTender = this.couponService.selectCouponRealTenderByOrderId(orderId);
        if (Validator.isNotNull(couponRealTender)){
            response.setResult(CommonUtils.convertBean(couponRealTender,CouponRealTenderVO.class));
        }
        return response;
    }

    /**
     * 根据优惠券出借ID获取优惠券出借信息
     *
     * @param couponTenderId
     * @return
     */
    @RequestMapping("/selectCouponTenderByCouponTenderId/{couponTenderId}")
    public com.hyjf.am.response.trade.coupon.CouponTenderResponse selectCouponTenderByCouponTenderId(@PathVariable String couponTenderId) {
        com.hyjf.am.response.trade.coupon.CouponTenderResponse response = new com.hyjf.am.response.trade.coupon.CouponTenderResponse();
        CouponTender couponTender = this.couponService.selectCouponTenderByCouponTenderId(couponTenderId);
        if (Validator.isNotNull(couponTender)) {
            response.setResult(CommonUtils.convertBean(couponTender, CouponTenderVO.class));
        }
        return response;
    }


    /**
     * 根据优惠券ID查询优惠券信息
     *
     * @param couponGrantId
     * @return
     */
    @RequestMapping("/selectCouponUserById/{couponGrantId}")
    public CouponUserResponse selectCouponUserById(@PathVariable Integer couponGrantId) {
        CouponUserResponse response = new CouponUserResponse();
        CouponUser couponUser = this.couponService.selectCouponUserById(couponGrantId);
        if (Validator.isNotNull(couponUser)) {
            response.setResult(CommonUtils.convertBean(couponUser, CouponUserVO.class));
        }
        return response;
    }

    /**
     * 根据优惠券出借ID获取优惠券出借信息
     *
     * @param couponTenderId
     * @return
     */
    @RequestMapping("/selectBorrowTenderCpnByCouponTenderId/{couponTenderId}")
    public BorrowTenderCpnResponse selectBorrowTenderCpnByCouponTenderId(@PathVariable String couponTenderId) {
        BorrowTenderCpnResponse response = new BorrowTenderCpnResponse();
        BorrowTenderCpn borrowTenderCpn = this.couponService.selectBorrowTenderCpnByCouponTenderId(couponTenderId);
        if (Validator.isNotNull(borrowTenderCpn)) {
            response.setResult(CommonUtils.convertBean(borrowTenderCpn, BorrowTenderCpnVO.class));
        }
        return response;
    }

    /**
     * 获取投资红包金额
     * add by nxl
     * @param realTenderId
     * @return
     */
    @RequestMapping("/getRedPackageSum/{realTenderId}")
    public BigDecimalResponse getRedPackageSum(@PathVariable String realTenderId) {
        BigDecimalResponse response = new BigDecimalResponse();
        BigDecimal sumPack = couponService.getRedPackageSum(realTenderId);
        response.setResultDec(sumPack);
        return response;
    }

}
