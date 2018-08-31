package com.hyjf.admin.controller.vip.coupon;

import com.hyjf.admin.beans.response.CouponBackMoneyContResponse;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.coupon.CouponBackMoneyService;
import com.hyjf.am.vo.admin.coupon.CouponBackMoneyCustomize;
import com.hyjf.am.vo.admin.coupon.CouponTenderVo;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CustomConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author yinhui
 * @version UtmController, v0.1 2018/7/06 15:17
 */
@Api(value = "VIP管理汇-直投/汇计划回款",tags ="VIP管理-汇直投/汇计划回款")
@RestController
@RequestMapping("/hyjf-admin/coupon")
public class CouponBackMoneyController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(CouponBackMoneyController.class);
    @Autowired
    private CouponBackMoneyService couponBackMoneyService;


    @ApiOperation(value = "页面初始化", notes = "汇直投-代金券-回款使用列表")
    @PostMapping("/backmoneydj/hzt/init")
    public AdminResult coupontDjListInit( @RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        CouponBackMoneyContResponse response = new CouponBackMoneyContResponse();

        // 项目状态
        Map<String, String> list =  CacheUtil.getParamNameMap(CustomConstants.COUPON_RECIVE_STATUS);
        couponTenderHztVo.setCouponReciveStatusList(list);
        couponBackMoneyCustomize.setCouponType("3");
        Integer count = this.couponBackMoneyService.countRecordHztDJ(couponBackMoneyCustomize);
        response.setCount(count);
        if (count != null && count > 0) {
            Paginator paginator = new Paginator(couponBackMoneyCustomize.getCurrPage(), count,couponBackMoneyCustomize.getPageSize()==0?10:couponBackMoneyCustomize.getPageSize());

            couponBackMoneyCustomize.setLimitStart(paginator.getOffset());
            couponBackMoneyCustomize.setLimitEnd(paginator.getLimit());
            List<CouponBackMoneyCustomize>  recordList = this.couponBackMoneyService.getRecordListHztDJ(couponBackMoneyCustomize);
            String recoverInterest = this.couponBackMoneyService.queryHztRecoverInterestTotle(couponBackMoneyCustomize);
            String investTotal = this.couponBackMoneyService.queryHztInvestTotal(couponBackMoneyCustomize);
            couponTenderHztVo.setRecoverInterest(recoverInterest);
            couponTenderHztVo.setInvestTotal(investTotal);
            couponTenderHztVo.setRecordList(recordList);
            response.setCouponTenderVo(couponTenderHztVo);
        }
//        lrs.setData(couponTenderHztVo);
//        Page page = Page.initPage(couponBackMoneyCustomize.getCurrPage(), couponBackMoneyCustomize.getPageSize());
//        page.setTotal(count);
//        lrs.setPage(page);
        return new AdminResult(response);
    }

    @ApiOperation(value = "页面初始化", notes = "汇直投-加息券-回款使用列表")
    @PostMapping("/backmoneyjx/hzt/init")
    public AdminResult<ListResult<CouponTenderVo>> couponJxListInit( @RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        CouponBackMoneyContResponse responsehzt = new CouponBackMoneyContResponse();
        // 项目状态
        Map<String, String> list =  CacheUtil.getParamNameMap(CustomConstants.COUPON_RECIVE_STATUS);
        couponTenderHztVo.setCouponReciveStatusList(list);
        couponBackMoneyCustomize.setCouponType("2");
        Integer count = this.couponBackMoneyService.countRecordHztJX(couponBackMoneyCustomize);
        responsehzt.setCount(count);
        if (count != null && count > 0) {

            Paginator paginator = new Paginator(couponBackMoneyCustomize.getCurrPage(), count,couponBackMoneyCustomize.getPageSize()==0?10:couponBackMoneyCustomize.getPageSize());

            couponBackMoneyCustomize.setLimitStart(paginator.getOffset());
            couponBackMoneyCustomize.setLimitEnd(paginator.getLimit());

            List<CouponBackMoneyCustomize>  recordList = this.couponBackMoneyService.getRecordListHztJX(couponBackMoneyCustomize);
            String recoverInterest = this.couponBackMoneyService.queryHztRecoverInterestTotle(couponBackMoneyCustomize);
            String investTotal = this.couponBackMoneyService.queryHztInvestTotal(couponBackMoneyCustomize);
            couponTenderHztVo.setRecoverInterest(recoverInterest);
            couponTenderHztVo.setInvestTotal(investTotal);
            couponTenderHztVo.setRecordList(recordList);

            responsehzt.setCouponTenderVo(couponTenderHztVo);
        }
//        lrs.setData(couponTenderHztVo);
//        Page page = Page.initPage(couponBackMoneyCustomize.getCurrPage(), couponBackMoneyCustomize.getPageSize());
//        page.setTotal(count);
//        lrs.setPage(page);
        return new AdminResult(responsehzt);
    }

    @ApiOperation(value = "页面初始化", notes = "汇直投-体验金-回款使用列表")
    @PostMapping("/backmoneyty/hzt/init")
    public AdminResult<ListResult<CouponTenderVo>> couponTyListInit( @RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        CouponBackMoneyContResponse response = new CouponBackMoneyContResponse();
        // 项目状态
        Map<String, String> list =  CacheUtil.getParamNameMap(CustomConstants.COUPON_RECIVE_STATUS);
        couponTenderHztVo.setCouponReciveStatusList(list);
        couponBackMoneyCustomize.setCouponType("1");
        Integer count = this.couponBackMoneyService.countRecordHztTY(couponBackMoneyCustomize);
        response.setCount(count);
        if (count != null && count > 0) {
            Paginator paginator = new Paginator(couponBackMoneyCustomize.getCurrPage(), count,couponBackMoneyCustomize.getPageSize()==0?10:couponBackMoneyCustomize.getPageSize());

            couponBackMoneyCustomize.setLimitStart(paginator.getOffset());
            couponBackMoneyCustomize.setLimitEnd(paginator.getLimit());

            List<CouponBackMoneyCustomize>  recordList = this.couponBackMoneyService.getRecordListHztTY(couponBackMoneyCustomize);
            String recoverInterest = this.couponBackMoneyService.queryHztRecoverInterestTotle(couponBackMoneyCustomize);
            String investTotal = this.couponBackMoneyService.queryHztInvestTotal(couponBackMoneyCustomize);
            couponTenderHztVo.setRecoverInterest(recoverInterest);
            couponTenderHztVo.setInvestTotal(investTotal);
            couponTenderHztVo.setRecordList(recordList);

            response.setCouponTenderVo(couponTenderHztVo);
        }
//        lrs.setData(couponTenderHztVo);
//        Page page = Page.initPage(couponBackMoneyCustomize.getCurrPage(), couponBackMoneyCustomize.getPageSize());
//        page.setTotal(count);
//        lrs.setPage(page);
        return new AdminResult(response);
    }

    @ApiOperation(value = "页面初始化", notes = "汇计划-体验金-回款使用列表")
    @PostMapping("/backmoneyty/hjh/init")
    public AdminResult<ListResult<CouponTenderVo>> couponHjhTyListInit( @RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        CouponBackMoneyContResponse response = new CouponBackMoneyContResponse();
        // 项目状态
        Map<String, String> list =  CacheUtil.getParamNameMap(CustomConstants.COUPON_RECIVE_STATUS);
        couponTenderHztVo.setCouponReciveStatusList(list);
        couponBackMoneyCustomize.setCouponType("1");
        Integer count = this.couponBackMoneyService.countRecordHjhTY(couponBackMoneyCustomize);
        response.setCount(count);
        if (count != null && count > 0) {
            Paginator paginator = new Paginator(couponBackMoneyCustomize.getCurrPage(), count,couponBackMoneyCustomize.getPageSize()==0?10:couponBackMoneyCustomize.getPageSize());

            couponBackMoneyCustomize.setLimitStart(paginator.getOffset());
            couponBackMoneyCustomize.setLimitEnd(paginator.getLimit());

            List<CouponBackMoneyCustomize>  recordList = this.couponBackMoneyService.getRecordListHjhTY(couponBackMoneyCustomize);
            String recoverInterest = this.couponBackMoneyService.queryHjhRecoverInterestTotle(couponBackMoneyCustomize);
            String investTotal = this.couponBackMoneyService.queryHjhInvestTotal(couponBackMoneyCustomize);
            couponTenderHztVo.setRecoverInterest(recoverInterest);
            couponTenderHztVo.setInvestTotal(investTotal);
            couponTenderHztVo.setRecordList(recordList);

            response.setCouponTenderVo(couponTenderHztVo);
        }
//        lrs.setData(couponTenderHztVo);
//        Page page = Page.initPage(couponBackMoneyCustomize.getCurrPage(), couponBackMoneyCustomize.getPageSize());
//        page.setTotal(count);
//        lrs.setPage(page);
        return new AdminResult(response);
    }

    @ApiOperation(value = "页面初始化", notes = "汇计划-代金券-回款使用列表")
    @PostMapping("/backmoneydj/hjh/init")
    public AdminResult<ListResult<CouponTenderVo>> coupontHjhDjListInit( @RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        CouponBackMoneyContResponse response = new CouponBackMoneyContResponse();
        // 项目状态
        Map<String, String> list =  CacheUtil.getParamNameMap(CustomConstants.COUPON_RECIVE_STATUS);
        couponTenderHztVo.setCouponReciveStatusList(list);
        couponBackMoneyCustomize.setCouponType("3");
        Integer count = this.couponBackMoneyService.countRecordHjhDJ(couponBackMoneyCustomize);
        response.setCount(count);
        if (count != null && count > 0) {
            Paginator paginator = new Paginator(couponBackMoneyCustomize.getCurrPage(), count,couponBackMoneyCustomize.getPageSize()==0?10:couponBackMoneyCustomize.getPageSize());

            couponBackMoneyCustomize.setLimitStart(paginator.getOffset());
            couponBackMoneyCustomize.setLimitEnd(paginator.getLimit());

            List<CouponBackMoneyCustomize>  recordList = this.couponBackMoneyService.getRecordListHjhDJ(couponBackMoneyCustomize);
            String recoverInterest = this.couponBackMoneyService.queryHjhRecoverInterestTotle(couponBackMoneyCustomize);
            String investTotal = this.couponBackMoneyService.queryHjhInvestTotal(couponBackMoneyCustomize);
            couponTenderHztVo.setRecoverInterest(recoverInterest);
            couponTenderHztVo.setInvestTotal(investTotal);
            couponTenderHztVo.setRecordList(recordList);

            response.setCouponTenderVo(couponTenderHztVo);
        }
//        lrs.setData(couponTenderHztVo);
//        Page page = Page.initPage(couponBackMoneyCustomize.getCurrPage(), couponBackMoneyCustomize.getPageSize());
//        page.setTotal(count);
//        lrs.setPage(page);
        return new AdminResult(response);
    }

    @ApiOperation(value = "页面初始化", notes = "汇计划-加息券-回款使用列表")
    @PostMapping("/backmoneyjx/hjh/init")
    public AdminResult<ListResult<CouponTenderVo>> couponHjhJxListInit(@RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponTenderVo couponTenderHztVo = new CouponTenderVo();
        CouponBackMoneyContResponse response = new CouponBackMoneyContResponse();
        // 项目状态
        Map<String, String> list =  CacheUtil.getParamNameMap(CustomConstants.COUPON_RECIVE_STATUS);
        couponTenderHztVo.setCouponReciveStatusList(list);
        couponBackMoneyCustomize.setCouponType("2");
        Integer count = this.couponBackMoneyService.countRecordHjhJX(couponBackMoneyCustomize);
        response.setCount(count);
        if (count != null && count > 0) {
            Paginator paginator = new Paginator(couponBackMoneyCustomize.getCurrPage(), count,couponBackMoneyCustomize.getPageSize()==0?10:couponBackMoneyCustomize.getPageSize());

            couponBackMoneyCustomize.setLimitStart(paginator.getOffset());
            couponBackMoneyCustomize.setLimitEnd(paginator.getLimit());

            List<CouponBackMoneyCustomize>  recordList = this.couponBackMoneyService.getRecordListHjhJX(couponBackMoneyCustomize);
            String recoverInterest = this.couponBackMoneyService.queryHjhRecoverInterestTotle(couponBackMoneyCustomize);
            String investTotal = this.couponBackMoneyService.queryHjhInvestTotal(couponBackMoneyCustomize);
            couponTenderHztVo.setRecoverInterest(recoverInterest);
            couponTenderHztVo.setInvestTotal(investTotal);
            couponTenderHztVo.setRecordList(recordList);

            response.setCouponTenderVo(couponTenderHztVo);
        }
//        lrs.setData(couponTenderHztVo);
//        Page page = Page.initPage(couponBackMoneyCustomize.getCurrPage(), couponBackMoneyCustomize.getPageSize());
//        page.setTotal(count);
//        lrs.setPage(page);
        return new AdminResult(response);
    }
}
