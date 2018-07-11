package com.hyjf.admin.controller.vip.coupon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.coupon.CouponTenderHjhService;
import com.hyjf.am.resquest.admin.CouponTenderRequest;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.admin.coupon.CouponTenderCustomize;
import com.hyjf.am.vo.admin.coupon.CouponTenderDetailVo;
import com.hyjf.am.vo.admin.coupon.CouponTenderVo;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/03 16:17
 */
@Api(value = "VIP管理汇计划列表")
@RestController
@RequestMapping("/hyjf-admin/coupon/tender/hjh")
public class CouponTenderHjhController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(CouponTenderHjhController.class);
    @Autowired
    private CouponTenderHjhService couponTenderHjhService;

    @ApiOperation(value = "页面初始化", notes = "汇计划使用列表")
    @PostMapping("/init")
    public AdminResult<ListResult<CouponTenderVo>> couponListInit(HttpServletRequest request, HttpServletResponse response, @RequestBody CouponTenderRequest couponTenderRequest) {
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        ListResult<CouponTenderVo> lrs = new ListResult<CouponTenderVo>();
        // 项目状态
        Map<String, String> list =  CacheUtil.getParamNameMap(CustomConstants.COUPON_RECIVE_STATUS);
        couponTenderHztVo.setCouponReciveStatusList(list);
        Integer count = this.couponTenderHjhService.countRecord(couponTenderRequest);
        lrs.setCount(count);
        if (count != null && count > 0) {
            String investTotal=this.couponTenderHjhService.queryInvestTotalHjh(couponTenderRequest);
            List<CouponTenderCustomize> recordList = this.couponTenderHjhService.getRecordList(couponTenderRequest);
            couponTenderHztVo.setInvestTotal(investTotal);
            couponTenderHztVo.setRecordList(recordList);
        }
        return new AdminResult<ListResult<CouponTenderVo>>(lrs);
    }

    @ApiOperation(value = "页面详情", notes = "汇计划页面详情")
    @PostMapping("/coupondetail")
    public AdminResult<ListResult<CouponTenderVo>> couponDetail(HttpServletRequest request, HttpServletResponse response, @RequestBody CouponTenderRequest couponTenderHRequest) {
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        ListResult<CouponTenderVo> lrs = new ListResult<CouponTenderVo>();
        if(null != couponTenderHRequest.getCouponUserId()){
            Map<String,Object> paramMap = new HashMap<String,Object>();
            // 优惠券发放编号
            paramMap.put("couponUserId", couponTenderHRequest.getCouponUserId());
            paramMap.put("userFlag", 1);

            //详情
            CouponTenderDetailVo detail=new CouponTenderDetailVo();
            detail=couponTenderHjhService.getCouponTenderDetailCustomize(paramMap);
            //回款列表
            List<CouponRecoverVO> list=
                    couponTenderHjhService.getCouponRecoverCustomize(paramMap);
            //操作平台
            Map<String, String> map =  CacheUtil.getParamNameMap("CLIENT");
            for (String key : map.keySet()) {
                System.out.println("key= "+ key + " and value= " + map.get(key));
            }

            //处理优惠券使用平台，使用项目
            detail = couponTenderHjhService.dealDetail(detail,map);

            couponTenderHztVo.setDetail(detail);
            couponTenderHztVo.setCouponRecoverlist(list);
            return new AdminResult<>(lrs);
        }else{
            return new AdminResult(BaseResult.FAIL,"请选择用户优惠券");
        }
    }
}
