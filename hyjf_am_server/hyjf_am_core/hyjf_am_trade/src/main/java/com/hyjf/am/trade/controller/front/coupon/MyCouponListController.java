package com.hyjf.am.trade.controller.front.coupon;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.trade.MyBestCouponListResponse;
import com.hyjf.am.response.trade.MyCouponListResponse;
import com.hyjf.am.response.trade.coupon.CouponResponse;
import com.hyjf.am.response.trade.coupon.CouponResponseForCoupon;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.front.coupon.MyCouponListService;
import com.hyjf.am.vo.trade.coupon.BestCouponListVO;
import com.hyjf.am.vo.trade.coupon.CouponUserForAppCustomizeVO;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.common.util.CustomConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @Author walter.limeng
     * @Description  微信端查询我的优惠券列表
     * @Date 15:29 2018/9/28
     * @Param requestBean
     * @return
     */
    @RequestMapping(value = "/wechatcouponlist")
    public MyCouponListResponse wechatCouponList(@RequestBody @Valid MyCouponListRequest requestBean) {
        MyCouponListResponse responseBean = new MyCouponListResponse();

        List<MyCouponListCustomizeVO> resultList = myCouponListService.wechatCouponList(requestBean.getUserId(),requestBean.getUsedFlag(),requestBean.getLimitStart(),requestBean.getLimitEnd());
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
    public IntegerResponse myCouponCount(@RequestBody @Valid MyCouponListRequest requestBean){
        IntegerResponse responseBean = new IntegerResponse();
        Integer count = myCouponListService.countUserCouponList(requestBean.getUserId(),requestBean.getUsedFlag());
        responseBean.setResultInt(count);
        return responseBean;
    }

    /**
     * APP查询我的优惠券列表
     * @auther: walter.limeng
     * @date: 2018/7/9
     */
    @PostMapping(value = "/getmycouponbypage")
    public CouponResponseForCoupon getMyCouponByPage(@RequestBody @Valid MyCouponListRequest requestBean) {
        CouponResponseForCoupon responseBean = new CouponResponseForCoupon();

        List<CouponUserForAppCustomizeVO> resultList = myCouponListService.getMyCouponByPage(requestBean);
        if(resultList != null && !resultList.isEmpty()){
            myCouponListService.updateCouponReadFlag(Integer.parseInt(requestBean.getUserId()), CustomConstants.USER_COUPON_STATUS_USED);
        }
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
        try{
            return  myCouponListService.getHJHUserCouponAvailableCount(requestBean);
        }catch (Exception e){
            logger.error("---",e);
        }

        return 0;
    }

    /**
     * APP,PC,wechat散标出借查询优惠券列表
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
