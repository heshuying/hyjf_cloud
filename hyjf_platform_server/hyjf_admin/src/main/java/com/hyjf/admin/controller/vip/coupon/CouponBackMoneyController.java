package com.hyjf.admin.controller.vip.coupon;

import com.hyjf.admin.beans.response.CouponBackMoneyContResponse;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.coupon.CouponBackMoneyService;
import com.hyjf.am.vo.admin.coupon.CouponBackMoneyCustomize;
import com.hyjf.am.vo.admin.coupon.CouponTenderVo;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

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

        CouponBackMoneyContResponse response = new CouponBackMoneyContResponse();
        response = couponBackMoneyService.getdjHzt(couponBackMoneyCustomize);

        return new AdminResult(response);
    }

    @ApiOperation(value = "页面初始化", notes = "汇直投-加息券-回款使用列表")
    @PostMapping("/backmoneyjx/hzt/init")
    public AdminResult<ListResult<CouponTenderVo>> couponJxListInit( @RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {

        CouponBackMoneyContResponse responsehzt = new CouponBackMoneyContResponse();

        responsehzt = couponBackMoneyService.getjxHzt(couponBackMoneyCustomize);

        return new AdminResult(responsehzt);
    }

    @ApiOperation(value = "页面初始化", notes = "汇直投-体验金-回款使用列表")
    @PostMapping("/backmoneyty/hzt/init")
    public AdminResult<ListResult<CouponTenderVo>> couponTyListInit( @RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponBackMoneyContResponse response = new CouponBackMoneyContResponse();

        response = couponBackMoneyService.gettyHzt(couponBackMoneyCustomize);
        return new AdminResult(response);
    }

    @ApiOperation(value = "页面初始化", notes = "汇计划-体验金-回款使用列表")
    @PostMapping("/backmoneyty/hjh/init")
    public AdminResult<ListResult<CouponTenderVo>> couponHjhTyListInit( @RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponBackMoneyContResponse response = new CouponBackMoneyContResponse();

        response = couponBackMoneyService.gettyHjh(couponBackMoneyCustomize);
        return new AdminResult(response);
    }

    @ApiOperation(value = "页面初始化", notes = "汇计划-代金券-回款使用列表")
    @PostMapping("/backmoneydj/hjh/init")
    public AdminResult<ListResult<CouponTenderVo>> coupontHjhDjListInit( @RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponBackMoneyContResponse response = new CouponBackMoneyContResponse();

        response = couponBackMoneyService.getdjHjh(couponBackMoneyCustomize);
        return new AdminResult(response);
    }

    @ApiOperation(value = "页面初始化", notes = "汇计划-加息券-回款使用列表")
    @PostMapping("/backmoneyjx/hjh/init")
    public AdminResult<ListResult<CouponTenderVo>> couponHjhJxListInit(@RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) {
        CouponBackMoneyContResponse response = new CouponBackMoneyContResponse();

        response = couponBackMoneyService.getjxHjh(couponBackMoneyCustomize);
        return new AdminResult(response);
    }

    /**
     * 导出功能
     *
     */
    @PostMapping("/exportAction")
    public void exportAction( HttpServletResponse response,@RequestBody CouponBackMoneyCustomize couponBackMoneyCustomize) throws Exception {
        String father = couponBackMoneyCustomize.getFather();
        String son = couponBackMoneyCustomize.getSon();
        if(StringUtils.isEmpty(father) || StringUtils.isEmpty(son)){
            return ;
        }

        CouponBackMoneyContResponse moneyContResponse = new CouponBackMoneyContResponse();

        // 表格sheet名称
        String sheetName = null;

        List<CouponBackMoneyCustomize> resultList = null;
        String recoverInterest = null;
        String investTotal = null;
        if("hzt".equals(father)){
            //体验金
            if("tyj".equals(son)){
                sheetName = "体验金回款列表";
                moneyContResponse = couponBackMoneyService.gettyHzt(couponBackMoneyCustomize);

            }
            //加息券
            else if("jxq".equals(son)){
                sheetName = "加息券回款列表";
                moneyContResponse = couponBackMoneyService.getjxHzt(couponBackMoneyCustomize);
            }

            //代金券
            else if("djq".equals(son)){
                sheetName = "代金券回款列表";
                moneyContResponse = couponBackMoneyService.getdjHzt(couponBackMoneyCustomize);
            }

        }else if("hjh".equals(father)){
            //体验金
            if("tyj".equals(son)){
                sheetName = "体验金回款列表";
                moneyContResponse = couponBackMoneyService.gettyHjh(couponBackMoneyCustomize);
            }
            //加息券
            else if("jxq".equals(son)){
                sheetName = "加息券回款列表";
                moneyContResponse = couponBackMoneyService.getjxHjh(couponBackMoneyCustomize);
            }

            //代金券
            else if("djq".equals(son)){
                sheetName = "代金券回款列表";
                moneyContResponse = couponBackMoneyService.getdjHjh(couponBackMoneyCustomize);
            }
        }

        if(moneyContResponse == null){
            return ;
        }
        resultList = moneyContResponse.getCouponTenderVo().getRecordList();
        recoverInterest =  moneyContResponse.getCouponTenderVo().getRecoverInterest();
        investTotal = moneyContResponse.getCouponTenderVo().getInvestTotal();

        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

        couponBackMoneyService.exportAction(sheetName,resultList,recoverInterest,investTotal,fileName,response,couponBackMoneyCustomize);
    }
}
