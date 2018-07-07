package com.hyjf.am.trade.controller.admin.coupon;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.admin.CouponTenderResponse;
import com.hyjf.am.resquest.admin.CouponTenderRequest;
import com.hyjf.am.trade.controller.admin.AdminAccountDetailController;
import com.hyjf.am.trade.service.admin.coupon.CouponTenderService;
import com.hyjf.am.vo.admin.coupon.CouponBackMoneyCustomize;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVo;
import com.hyjf.am.vo.admin.coupon.CouponTenderCustomize;
import com.hyjf.am.vo.admin.coupon.CouponTenderDetailVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 */
@RestController
@RequestMapping("/am-trade/coupon/tender")
public class CouponTenderController {
    private static Logger logger = LoggerFactory.getLogger(AdminAccountDetailController.class);

    @Autowired
    private CouponTenderService couponTenderService;
    /**
     * 根据条件获取总条数
     * @param request CouponTenderHztRequest
     * @return
     */
    @RequestMapping(value = "/hztcountcoupontender", method = RequestMethod.POST)
    public CouponTenderResponse countRecord(@RequestBody @Valid CouponTenderRequest request) {
        logger.info("---hztcountcoupontender by param---  " + JSONObject.toJSON(request));
        CouponTenderResponse couponTenderResponse = new CouponTenderResponse();
        Integer count = couponTenderService.countRecord(request);
        couponTenderResponse.setRecordTotal(count);
        return couponTenderResponse;
    }
    /**
     * 根据条件获取投资总金额
     * @param request CouponTenderHztRequest
     * @return
     */
    @RequestMapping(value = "/hztquerynvesttotal", method = RequestMethod.POST)
    public CouponTenderResponse queryInvestTotalHzt(@RequestBody @Valid CouponTenderRequest request) {
        logger.info("---hztquerynvesttotal by param---  " + JSONObject.toJSON(request));
        CouponTenderResponse couponTenderResponse = new CouponTenderResponse();
        String investTotal = couponTenderService.queryInvestTotalHzt(request);
        couponTenderResponse.setAmountTotal(investTotal);
        return couponTenderResponse;
    }
    /**
     * 根据查询条件获取分页数据
     * @param request CouponTenderHztRequest
     * @return
     */
    @RequestMapping(value = "/hztgetrecordlist", method = RequestMethod.POST)
    public CouponTenderResponse getRecordList(@RequestBody @Valid CouponTenderRequest request) {
        logger.info("---hztgetrecordlist by param---  " + JSONObject.toJSON(request));
        CouponTenderResponse couponTenderResponse = new CouponTenderResponse();
        List<CouponTenderCustomize> list = couponTenderService.getRecordList(request);
        couponTenderResponse.setResultList(list);
        return couponTenderResponse;
    }
    /**
     * 根据条件查询优惠券使用详情
     * @param  paramMap couponUserId 优惠券发放编号，userFlag ：1
     * @return CouponTenderResponse
     */
    @RequestMapping(value = "/hztcoupontenderdetail", method = RequestMethod.POST)
    public CouponTenderResponse getCouponTenderDetailCustomize(@RequestBody @Valid Map<String, Object> paramMap) {
        logger.info("---hztcoupontenderdetail by param---  " + JSONObject.toJSON(paramMap));
        CouponTenderResponse couponTenderResponse = new CouponTenderResponse();
        CouponTenderDetailVo couponTenderDetailVo = couponTenderService.getCouponTenderDetailCustomize(paramMap);
        couponTenderResponse.setDetail(couponTenderDetailVo);
        return couponTenderResponse;
    }
    /**
     * 回款列表
     * @param paramMap couponUserId 优惠券发放编号，userFlag ：1
     * @return CouponTenderResponse
     */
    @RequestMapping(value = "/hztcouponrecover", method = RequestMethod.POST)
    public CouponTenderResponse getCouponRecoverCustomize(@RequestBody @Valid Map<String, Object> paramMap) {
        logger.info("---hztcouponrecover by param---  " + JSONObject.toJSON(paramMap));
        CouponTenderResponse couponTenderResponse = new CouponTenderResponse();
        List<CouponRecoverVo> list = couponTenderService.getCouponRecoverCustomize(paramMap);
        couponTenderResponse.setCouponRecoverList(list);
        return couponTenderResponse;
    }

    /**
     * 根据条件获取总条数
     * @param request CouponTenderHztRequest
     * @return CouponTenderResponse
     */
    @RequestMapping(value = "/hjhcountcoupontender", method = RequestMethod.POST)
    public CouponTenderResponse countHjhRecord(@RequestBody @Valid CouponTenderRequest request) {
        logger.info("---hjhcountcoupontender by param---  " + JSONObject.toJSON(request));
        CouponTenderResponse couponTenderResponse = new CouponTenderResponse();
        Integer count = couponTenderService.countHjhRecord(request);
        couponTenderResponse.setRecordTotal(count);
        return couponTenderResponse;
    }
    /**
     * 根据条件获取投资总金额
     * @param request CouponTenderHztRequest
     * @return CouponTenderResponse
     */
    @RequestMapping(value = "/hjhquerynvesttotal", method = RequestMethod.POST)
    public CouponTenderResponse queryInvestTotalHjh(@RequestBody @Valid CouponTenderRequest request) {
        logger.info("---hjhquerynvesttotal by param---  " + JSONObject.toJSON(request));
        CouponTenderResponse couponTenderResponse = new CouponTenderResponse();
        String investTotal = couponTenderService.queryInvestTotalHjh(request);
        couponTenderResponse.setAmountTotal(investTotal);
        return couponTenderResponse;
    }
    /**
     * 根据查询条件获取分页数据
     * @param request CouponTenderHztRequest
     * @return CouponTenderResponse
     */
    @RequestMapping(value = "/hjhgetrecordlist", method = RequestMethod.POST)
    public CouponTenderResponse getRecordListHjh(@RequestBody @Valid CouponTenderRequest request) {
        logger.info("---hjhgetrecordlist by param---  " + JSONObject.toJSON(request));
        CouponTenderResponse couponTenderResponse = new CouponTenderResponse();
        List<CouponTenderCustomize> list = couponTenderService.getRecordListHjh(request);
        couponTenderResponse.setResultList(list);
        return couponTenderResponse;
    }

    /**
     * 根据条件查询优惠券使用详情
     * @param  paramMap couponUserId 优惠券发放编号，userFlag ：1
     * @return CouponTenderResponse
     */
    @RequestMapping(value = "/hjhcoupontenderdetail", method = RequestMethod.POST)
    public CouponTenderResponse getHjhCouponTenderDetailCustomize(@RequestBody @Valid Map<String, Object> paramMap) {
        logger.info("---hjhcoupontenderdetail by param---  " + JSONObject.toJSON(paramMap));
        CouponTenderResponse couponTenderResponse = new CouponTenderResponse();
        CouponTenderDetailVo couponTenderDetailVo = couponTenderService.getHjhCouponTenderDetailCustomize(paramMap);
        couponTenderResponse.setDetail(couponTenderDetailVo);
        return couponTenderResponse;
    }
    /**
     * 回款列表
     * @param paramMap couponUserId 优惠券发放编号，userFlag ：1
     * @return CouponTenderResponse
     */
    @RequestMapping(value = "/hjhcouponrecover", method = RequestMethod.POST)
    public CouponTenderResponse getHjhCouponRecoverCustomize(@RequestBody @Valid Map<String, Object> paramMap) {
        logger.info("---hjhcouponrecover by param---  " + JSONObject.toJSON(paramMap));
        CouponTenderResponse couponTenderResponse = new CouponTenderResponse();
        List<CouponRecoverVo> list = couponTenderService.getHjhCouponRecoverCustomize(paramMap);
        couponTenderResponse.setCouponRecoverList(list);
        return couponTenderResponse;
    }

    /**
     * 汇直投代金券回款总数
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    @RequestMapping(value = "/countrecordhztdj", method = RequestMethod.POST)
    public CouponTenderResponse countRecordHztDJ(@RequestBody @Valid CouponBackMoneyCustomize couponBackMoneyCustomize) {
        logger.info("---countrecordhztdj by param---  " + JSONObject.toJSON(couponBackMoneyCustomize));
        CouponTenderResponse couponTenderResponse = new CouponTenderResponse();
        Integer investTotal = couponTenderService.countRecordHztDJ(couponBackMoneyCustomize);
        couponTenderResponse.setRecordTotal(investTotal);
        return couponTenderResponse;
    }

    /**
     * 汇直投代金券回款列表分页数据
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    @RequestMapping(value = "/getrecordlisthztdj", method = RequestMethod.POST)
    public CouponTenderResponse getRecordListHztDJ(@RequestBody @Valid CouponBackMoneyCustomize couponBackMoneyCustomize) {
        logger.info("---getrecordlisthztdj by param---  " + JSONObject.toJSON(couponBackMoneyCustomize));
        CouponTenderResponse couponTenderResponse = new CouponTenderResponse();
        List<CouponBackMoneyCustomize> backmoneyList = couponTenderService.getRecordListHztDJ(couponBackMoneyCustomize);
        couponTenderResponse.setBackMoneyCustomizeList(backmoneyList);
        return couponTenderResponse;
    }

    /**
     * 汇直投应还本息总额
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    @RequestMapping(value = "/queryhztinvesttotal", method = RequestMethod.POST)
    public CouponTenderResponse queryHztInvestTotal(@RequestBody @Valid CouponBackMoneyCustomize couponBackMoneyCustomize) {
        logger.info("---queryhztinvesttotal by param---  " + JSONObject.toJSON(couponBackMoneyCustomize));
        CouponTenderResponse couponTenderResponse = new CouponTenderResponse();
        String backmoney = couponTenderService.queryHztInvestTotal(couponBackMoneyCustomize);
        couponTenderResponse.setAttrbute(backmoney);
        return couponTenderResponse;
    }

    /**
     * 汇直投优惠券回款应回款总金额
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    @RequestMapping(value = "/queryhztrecoverinteresttotle", method = RequestMethod.POST)
    public CouponTenderResponse queryHztRecoverInterestTotle(@RequestBody @Valid CouponBackMoneyCustomize couponBackMoneyCustomize) {
        logger.info("---queryhztrecoverinteresttotle by param---  " + JSONObject.toJSON(couponBackMoneyCustomize));
        CouponTenderResponse couponTenderResponse = new CouponTenderResponse();
        String amount = couponTenderService.queryHztRecoverInterestTotle(couponBackMoneyCustomize);
        couponTenderResponse.setAmountTotal(amount);
        return couponTenderResponse;
    }

    /**
     * 汇直投体验金回款总数
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    @RequestMapping(value = "/countrecordhztty", method = RequestMethod.POST)
    public CouponTenderResponse countRecordHztTY(@RequestBody @Valid CouponBackMoneyCustomize couponBackMoneyCustomize) {
        logger.info("---countrecordhztty by param---  " + JSONObject.toJSON(couponBackMoneyCustomize));
        CouponTenderResponse couponTenderResponse = new CouponTenderResponse();
        Integer investTotal = couponTenderService.countRecordHztTY(couponBackMoneyCustomize);
        couponTenderResponse.setRecordTotal(investTotal);
        return couponTenderResponse;
    }

    /**
     * 汇直投体验金回款列表分页数据
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    @RequestMapping(value = "/getrecordlisthztty", method = RequestMethod.POST)
    public CouponTenderResponse getRecordListHztTY(@RequestBody @Valid CouponBackMoneyCustomize couponBackMoneyCustomize) {
        logger.info("---getrecordlisthztty by param---  " + JSONObject.toJSON(couponBackMoneyCustomize));
        CouponTenderResponse couponTenderResponse = new CouponTenderResponse();
        List<CouponBackMoneyCustomize> backmoneyList = couponTenderService.getRecordListHztTY(couponBackMoneyCustomize);
        couponTenderResponse.setBackMoneyCustomizeList(backmoneyList);
        return couponTenderResponse;
    }

    /**
     * 汇直投加息券回款总数
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    @RequestMapping(value = "/countrecordhztjx", method = RequestMethod.POST)
    public CouponTenderResponse countRecordHztJX(@RequestBody @Valid CouponBackMoneyCustomize couponBackMoneyCustomize) {
        logger.info("---countrecordhztjx by param---  " + JSONObject.toJSON(couponBackMoneyCustomize));
        CouponTenderResponse couponTenderResponse = new CouponTenderResponse();
        Integer investTotal = couponTenderService.countRecordHztJX(couponBackMoneyCustomize);
        couponTenderResponse.setRecordTotal(investTotal);
        return couponTenderResponse;
    }

    /**
     * 汇直投加息券回款列表分页数据
     * @param couponBackMoneyCustomize
     * @return CouponTenderResponse
     */
    @RequestMapping(value = "/getrecordlisthztjx", method = RequestMethod.POST)
    public CouponTenderResponse getRecordListHztJX(@RequestBody @Valid CouponBackMoneyCustomize couponBackMoneyCustomize) {
        logger.info("---getrecordlisthztjx by param---  " + JSONObject.toJSON(couponBackMoneyCustomize));
        CouponTenderResponse couponTenderResponse = new CouponTenderResponse();
        List<CouponBackMoneyCustomize> backmoneyList = couponTenderService.getRecordListHztJX(couponBackMoneyCustomize);
        couponTenderResponse.setBackMoneyCustomizeList(backmoneyList);
        return couponTenderResponse;
    }
}
