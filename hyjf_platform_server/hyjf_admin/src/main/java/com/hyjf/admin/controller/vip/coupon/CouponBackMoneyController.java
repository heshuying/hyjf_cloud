package com.hyjf.admin.controller.vip.coupon;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.coupon.CouponBackMoneyService;
import com.hyjf.am.vo.admin.coupon.CouponBackMoneyCustomize;
import com.hyjf.am.vo.admin.coupon.CouponTenderVo;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.common.util.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/06 15:17
 */
@Api(value = "VIP管理汇直投/汇计划回款",tags ="VIP管理汇直投/汇计划回款")
@RestController
@RequestMapping("/hyjf-admin/coupon")
public class CouponBackMoneyController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(CouponBackMoneyController.class);
    @Autowired
    private CouponBackMoneyService couponBackMoneyService;


    @ApiOperation(value = "页面初始化", notes = "汇直投代金券回款使用列表")
    @PostMapping("/backmoneydj/hzt/init")
    public AdminResult<ListResult<CouponTenderVo>> coupontDjListInit(HttpServletRequest request, HttpServletResponse response, @RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        ListResult<CouponTenderVo> lrs = new ListResult<CouponTenderVo>();
        // 项目状态
        Map<String, String> list =  CacheUtil.getParamNameMap(CustomConstants.COUPON_RECIVE_STATUS);
        couponTenderHztVo.setCouponReciveStatusList(list);
        couponBackMoneyCustomize.setCouponType("3");
        Integer count = this.couponBackMoneyService.countRecordHztDJ(couponBackMoneyCustomize);
        if (count != null && count > 0) {
            Integer start = couponBackMoneyCustomize.getLimitStart();
            Integer end = couponBackMoneyCustomize.getLimitEnd();
            couponBackMoneyCustomize.setLimitStart(start);
            couponBackMoneyCustomize.setLimitEnd(end);
            List<CouponBackMoneyCustomize>  recordList = this.couponBackMoneyService.getRecordListHztDJ(couponBackMoneyCustomize);
            String recoverInterest = this.couponBackMoneyService.queryHztRecoverInterestTotle(couponBackMoneyCustomize);
            String investTotal = this.couponBackMoneyService.queryHztInvestTotal(couponBackMoneyCustomize);
            couponTenderHztVo.setRecoverInterest(recoverInterest);
            couponTenderHztVo.setInvestTotal(investTotal);
            couponTenderHztVo.setRecordList(recordList);
        }
        lrs.setData(couponTenderHztVo);
        Page page = Page.initPage(couponBackMoneyCustomize.getCurrPage(), couponBackMoneyCustomize.getPageSize());
        page.setTotal(count);
        lrs.setPage(page);
        return new AdminResult<ListResult<CouponTenderVo>>(lrs);
    }

    @ApiOperation(value = "页面初始化", notes = "汇直投加息券回款使用列表")
    @PostMapping("/backmoneyjx/hzt/init")
    public AdminResult<ListResult<CouponTenderVo>> couponJxListInit(HttpServletRequest request, HttpServletResponse response, @RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        ListResult<CouponTenderVo> lrs = new ListResult<CouponTenderVo>();
        // 项目状态
        Map<String, String> list =  CacheUtil.getParamNameMap(CustomConstants.COUPON_RECIVE_STATUS);
        couponTenderHztVo.setCouponReciveStatusList(list);
        couponBackMoneyCustomize.setCouponType("2");
        Integer count = this.couponBackMoneyService.countRecordHztJX(couponBackMoneyCustomize);
        if (count != null && count > 0) {
            Integer start = couponBackMoneyCustomize.getLimitStart();
            Integer end = couponBackMoneyCustomize.getLimitEnd();
            couponBackMoneyCustomize.setLimitStart(start);
            couponBackMoneyCustomize.setLimitEnd(end);
            List<CouponBackMoneyCustomize>  recordList = this.couponBackMoneyService.getRecordListHztJX(couponBackMoneyCustomize);
            String recoverInterest = this.couponBackMoneyService.queryHztRecoverInterestTotle(couponBackMoneyCustomize);
            String investTotal = this.couponBackMoneyService.queryHztInvestTotal(couponBackMoneyCustomize);
            couponTenderHztVo.setRecoverInterest(recoverInterest);
            couponTenderHztVo.setInvestTotal(investTotal);
            couponTenderHztVo.setRecordList(recordList);
        }
        lrs.setData(couponTenderHztVo);
        Page page = Page.initPage(couponBackMoneyCustomize.getCurrPage(), couponBackMoneyCustomize.getPageSize());
        page.setTotal(count);
        lrs.setPage(page);
        return new AdminResult<ListResult<CouponTenderVo>>(lrs);
    }

    @ApiOperation(value = "页面初始化", notes = "汇直投体验金回款使用列表")
    @PostMapping("/backmoneyty/hzt/init")
    public AdminResult<ListResult<CouponTenderVo>> couponTyListInit(HttpServletRequest request, HttpServletResponse response, @RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        ListResult<CouponTenderVo> lrs = new ListResult<CouponTenderVo>();
        // 项目状态
        Map<String, String> list =  CacheUtil.getParamNameMap(CustomConstants.COUPON_RECIVE_STATUS);
        couponTenderHztVo.setCouponReciveStatusList(list);
        couponBackMoneyCustomize.setCouponType("1");
        Integer count = this.couponBackMoneyService.countRecordHztTY(couponBackMoneyCustomize);
        if (count != null && count > 0) {
            Integer start = couponBackMoneyCustomize.getLimitStart();
            Integer end = couponBackMoneyCustomize.getLimitEnd();
            couponBackMoneyCustomize.setLimitStart(start);
            couponBackMoneyCustomize.setLimitEnd(end);
            List<CouponBackMoneyCustomize>  recordList = this.couponBackMoneyService.getRecordListHztTY(couponBackMoneyCustomize);
            String recoverInterest = this.couponBackMoneyService.queryHztRecoverInterestTotle(couponBackMoneyCustomize);
            String investTotal = this.couponBackMoneyService.queryHztInvestTotal(couponBackMoneyCustomize);
            couponTenderHztVo.setRecoverInterest(recoverInterest);
            couponTenderHztVo.setInvestTotal(investTotal);
            couponTenderHztVo.setRecordList(recordList);
        }
        lrs.setData(couponTenderHztVo);
        Page page = Page.initPage(couponBackMoneyCustomize.getCurrPage(), couponBackMoneyCustomize.getPageSize());
        page.setTotal(count);
        lrs.setPage(page);
        return new AdminResult<ListResult<CouponTenderVo>>(lrs);
    }

    @ApiOperation(value = "页面初始化", notes = "汇计划体验金回款使用列表")
    @PostMapping("/backmoneyty/hjh/init")
    public AdminResult<ListResult<CouponTenderVo>> couponHjhTyListInit(HttpServletRequest request, HttpServletResponse response, @RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        ListResult<CouponTenderVo> lrs = new ListResult<CouponTenderVo>();
        // 项目状态
        Map<String, String> list =  CacheUtil.getParamNameMap(CustomConstants.COUPON_RECIVE_STATUS);
        couponTenderHztVo.setCouponReciveStatusList(list);
        couponBackMoneyCustomize.setCouponType("1");
        Integer count = this.couponBackMoneyService.countRecordHjhTY(couponBackMoneyCustomize);
        if (count != null && count > 0) {
            Integer start = couponBackMoneyCustomize.getLimitStart();
            Integer end = couponBackMoneyCustomize.getLimitEnd();
            couponBackMoneyCustomize.setLimitStart(start);
            couponBackMoneyCustomize.setLimitEnd(end);
            List<CouponBackMoneyCustomize>  recordList = this.couponBackMoneyService.getRecordListHjhTY(couponBackMoneyCustomize);
            String recoverInterest = this.couponBackMoneyService.queryHjhRecoverInterestTotle(couponBackMoneyCustomize);
            String investTotal = this.couponBackMoneyService.queryHjhInvestTotal(couponBackMoneyCustomize);
            couponTenderHztVo.setRecoverInterest(recoverInterest);
            couponTenderHztVo.setInvestTotal(investTotal);
            couponTenderHztVo.setRecordList(recordList);
        }
        lrs.setData(couponTenderHztVo);
        Page page = Page.initPage(couponBackMoneyCustomize.getCurrPage(), couponBackMoneyCustomize.getPageSize());
        page.setTotal(count);
        lrs.setPage(page);
        return new AdminResult<ListResult<CouponTenderVo>>(lrs);
    }

    @ApiOperation(value = "页面初始化", notes = "汇计划代金券回款使用列表")
    @PostMapping("/backmoneydj/hjh/init")
    public AdminResult<ListResult<CouponTenderVo>> coupontHjhDjListInit(HttpServletRequest request, HttpServletResponse response, @RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        ListResult<CouponTenderVo> lrs = new ListResult<CouponTenderVo>();
        // 项目状态
        Map<String, String> list =  CacheUtil.getParamNameMap(CustomConstants.COUPON_RECIVE_STATUS);
        couponTenderHztVo.setCouponReciveStatusList(list);
        couponBackMoneyCustomize.setCouponType("3");
        Integer count = this.couponBackMoneyService.countRecordHjhDJ(couponBackMoneyCustomize);
        if (count != null && count > 0) {
            Integer start = couponBackMoneyCustomize.getLimitStart();
            Integer end = couponBackMoneyCustomize.getLimitEnd();
            couponBackMoneyCustomize.setLimitStart(start);
            couponBackMoneyCustomize.setLimitEnd(end);
            List<CouponBackMoneyCustomize>  recordList = this.couponBackMoneyService.getRecordListHjhDJ(couponBackMoneyCustomize);
            String recoverInterest = this.couponBackMoneyService.queryHjhRecoverInterestTotle(couponBackMoneyCustomize);
            String investTotal = this.couponBackMoneyService.queryHjhInvestTotal(couponBackMoneyCustomize);
            couponTenderHztVo.setRecoverInterest(recoverInterest);
            couponTenderHztVo.setInvestTotal(investTotal);
            couponTenderHztVo.setRecordList(recordList);
        }
        lrs.setData(couponTenderHztVo);
        Page page = Page.initPage(couponBackMoneyCustomize.getCurrPage(), couponBackMoneyCustomize.getPageSize());
        page.setTotal(count);
        lrs.setPage(page);
        return new AdminResult<ListResult<CouponTenderVo>>(lrs);
    }

    @ApiOperation(value = "页面初始化", notes = "汇计划加息券回款使用列表")
    @PostMapping("/backmoneyjx/hjh/init")
    public AdminResult<ListResult<CouponTenderVo>> couponHjhJxListInit(HttpServletRequest request, HttpServletResponse response, @RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        ListResult<CouponTenderVo> lrs = new ListResult<CouponTenderVo>();
        // 项目状态
        Map<String, String> list =  CacheUtil.getParamNameMap(CustomConstants.COUPON_RECIVE_STATUS);
        couponTenderHztVo.setCouponReciveStatusList(list);
        couponBackMoneyCustomize.setCouponType("2");
        Integer count = this.couponBackMoneyService.countRecordHjhJX(couponBackMoneyCustomize);
        if (count != null && count > 0) {
            Integer start = couponBackMoneyCustomize.getLimitStart();
            Integer end = couponBackMoneyCustomize.getLimitEnd();
            couponBackMoneyCustomize.setLimitStart(start);
            couponBackMoneyCustomize.setLimitEnd(end);
            List<CouponBackMoneyCustomize>  recordList = this.couponBackMoneyService.getRecordListHjhJX(couponBackMoneyCustomize);
            String recoverInterest = this.couponBackMoneyService.queryHjhRecoverInterestTotle(couponBackMoneyCustomize);
            String investTotal = this.couponBackMoneyService.queryHjhInvestTotal(couponBackMoneyCustomize);
            couponTenderHztVo.setRecoverInterest(recoverInterest);
            couponTenderHztVo.setInvestTotal(investTotal);
            couponTenderHztVo.setRecordList(recordList);
        }
        lrs.setData(couponTenderHztVo);
        Page page = Page.initPage(couponBackMoneyCustomize.getCurrPage(), couponBackMoneyCustomize.getPageSize());
        page.setTotal(count);
        lrs.setPage(page);
        return new AdminResult<ListResult<CouponTenderVo>>(lrs);
    }
}
