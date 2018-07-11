package com.hyjf.am.trade.controller.coupon;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.trade.MyBestCouponListResponse;
import com.hyjf.am.response.trade.MyCouponListResponse;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.response.trade.coupon.CouponResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.coupon.MyCouponListService;
import com.hyjf.am.vo.coupon.CouponBeanVo;
import com.hyjf.am.vo.trade.coupon.BestCouponListVO;
import com.hyjf.am.vo.trade.coupon.CouponUserForAppCustomizeVO;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 我的优惠券列表
 * @author hesy
 * @version MyCouponListController, v0.1 2018/6/23 11:41
 */
@RestController
@RequestMapping("/am-trade/coupon")
public class MyCouponListController extends BaseController {
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


    /**
     * 查询最优优惠券
     * @author zhangyk
     * @date 2018/6/25 11:36
     */
    @RequestMapping(value = "/myBestCouponList", method = RequestMethod.POST)
    public MyBestCouponListResponse myBestCouponList(@RequestBody @Valid MyCouponListRequest requestBean) {
        MyBestCouponListResponse responseBean = new MyBestCouponListResponse();

        BestCouponListVO result = myCouponListService.selectBestCouponList(requestBean);
        responseBean.setResult(result);
        return responseBean;
    }



    /**
     * 查询可用优惠券
     * @author zhangyk
     * @date 2018/6/25 11:36
     */
    @RequestMapping(value = "/countAvaliableCoupon", method = RequestMethod.POST)
    public MyBestCouponListResponse countAvaliableCoupon(@RequestBody @Valid MyCouponListRequest requestBean) {
        MyBestCouponListResponse responseBean = new MyBestCouponListResponse();
        Integer count = myCouponListService.countAvaliableCoupon(requestBean);
        responseBean.setCouponCount(count);
        return responseBean;
    }

    /**
     * 统计优惠券总数
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/myCouponCount")
    public Integer myCouponCount(@RequestBody @Valid MyCouponListRequest requestBean){
        MyCouponListResponse responseBean = new MyCouponListResponse();
        return myCouponListService.countUserCouponList(requestBean.getUserId(),requestBean.getUsedFlag());
    }

    /**
     * APP查询我的优惠券列表
     * @auther: walter.limeng
     * @date: 2018/7/9
     */
    @RequestMapping(value = "/getmycouponbypage")
    public CouponResponse getMyCouponByPage(@RequestBody @Valid MyCouponListRequest requestBean) {
        CouponResponse responseBean = new CouponResponse();

        List<CouponUserForAppCustomizeVO> resultList = myCouponListService.getMyCouponByPage(requestBean);
        responseBean.setResultList(resultList);

        return responseBean;
    }

    /**
     * 查询汇计划最优优惠券
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/selectHJHBestCoupon", method = RequestMethod.POST)
    public MyBestCouponListResponse selectHJHBestCoupon(@RequestBody @Valid MyCouponListRequest requestBean) {
        MyBestCouponListResponse responseBean = new MyBestCouponListResponse();

        BestCouponListVO result = myCouponListService.selectHJHBestCoupon(requestBean);
        responseBean.setResult(result);
        return responseBean;
    }

    /**
     * 获取当前标的可用优惠券数量
     * @param requestBean
     * @return
     */
    @RequestMapping(value = "/getHJHUserCouponAvailableCount", method = RequestMethod.POST)
    public Integer getHJHUserCouponAvailableCount(@RequestBody @Valid MyCouponListRequest requestBean) {
        return myCouponListService.getHJHUserCouponAvailableCount(requestBean);
    }

    /**
     * APP,PC,wechat散标投资查询优惠券列表
     * @author walter.limeng
     * @date 2018/7/10 10:36
     */
    @RequestMapping(value = "/getborrowcoupon", method = RequestMethod.POST)
    public CouponResponse getBorrowCoupon(@RequestBody @Valid MyCouponListRequest requestBean) {
        CouponResponse responseBean = new CouponResponse();
        JSONObject jsonObject = new JSONObject();
        jsonObject = myCouponListService.getBorrowCoupon(requestBean);
        responseBean.setResult(jsonObject);
        return responseBean;
    }

    /**
     * APP,PC,wechat加入计划查询优惠券列表
     * @author walter.limeng
     * @date 2018/7/10 14:36
     */
    @RequestMapping(value = "/getplancoupon", method = RequestMethod.POST)
    public CouponResponse getPlanCoupon(@RequestBody @Valid MyCouponListRequest requestBean) {
        CouponResponse responseBean = new CouponResponse();
        JSONObject jsonObject = new JSONObject();
        jsonObject = myCouponListService.getPlanCouponoupon(requestBean);
        responseBean.setResult(jsonObject);
        return responseBean;
    }
}
