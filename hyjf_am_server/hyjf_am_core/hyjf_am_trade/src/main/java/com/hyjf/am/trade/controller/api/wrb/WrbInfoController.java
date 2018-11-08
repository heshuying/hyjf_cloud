/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.api.wrb;

import com.hyjf.am.response.trade.BorrowTenderResponse;
import com.hyjf.am.response.trade.WrbBorrowListResponse;
import com.hyjf.am.response.trade.WrbInvestRecordResponse;
import com.hyjf.am.response.trade.wrbInvestRecoverPlanResponse;
import com.hyjf.am.response.user.WrbAccountResponse;
import com.hyjf.am.response.user.WrbInvestSumResponse;
import com.hyjf.am.resquest.api.WrbInvestRecordRequest;
import com.hyjf.am.response.trade.WrbBorrowTenderCustomizeResponse;
import com.hyjf.am.resquest.api.WrbInvestRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.CouponConfig;
import com.hyjf.am.trade.dao.model.auto.CouponUser;
import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.dao.model.customize.WrbBorrowListCustomize;
import com.hyjf.am.trade.dao.model.customize.WrbInvestRecordCustomize;
import com.hyjf.am.trade.dao.model.customize.WrbBorrowTenderCustomize;
import com.hyjf.am.trade.service.api.wrb.WrbInfoService;
import com.hyjf.am.vo.api.WrbInvestRecordVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.wrb.WrbBorrowListCustomizeVO;
import com.hyjf.am.vo.trade.wrb.WrbBorrowTenderCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fq
 * @version WrbinfoController, v0.1 2018/9/25 11:09
 */
@RestController
@RequestMapping("/am-trade/wrb")
public class WrbInfoController extends BaseController {
    @Autowired
    private WrbInfoService wrbInfoService;

    /**
     * 标的查询
     * @param borrowNid
     * @return
     */
    @RequestMapping("/borrow_list")
    public WrbBorrowListResponse borrowList(@PathVariable String borrowNid) {
        WrbBorrowListResponse response = new WrbBorrowListResponse();
        List<WrbBorrowListCustomize> list = wrbInfoService.borrowList(borrowNid);
        if (!CollectionUtils.isEmpty(list)) {
            List<WrbBorrowListCustomizeVO> voList = CommonUtils.convertBeanList(list, WrbBorrowListCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 获取某天投资情况汇总
     * @return
     */
    @RequestMapping("/ws_sum/{date}")
    public WrbInvestSumResponse borrowList(@PathVariable Date date) {
        return wrbInfoService.getDaySum(date);
    }

    /**
     * 获取用户优惠券信息
     * @param userId
     * @return
     */
    @RequestMapping("/getCouponInfo/{userId}")
    public WrbAccountResponse getCouponInfo(@PathVariable String userId) {
        WrbAccountResponse response = new WrbAccountResponse();
        List<CouponUser> couponUserList = wrbInfoService.getCouponInfo(userId);
        StringBuffer reward = new StringBuffer();
        if (!CollectionUtils.isEmpty(couponUserList)) {
            for (int i = 0; i < couponUserList.size(); i++) {
                CouponUser couponUser = couponUserList.get(i);
                reward.append(convertCouponInfo(couponUser));
                if (i != couponUserList.size() - 1) {
                    reward.append(";");
                }
            }
            response.setReward(reward.toString());
        }
        return response;
    }

    /**
     * 根据平台用户id获取账户信息
     * @param userId
     * @return
     */
    @RequestMapping("/getAccountInfo/{userId}")
    public WrbAccountResponse getAccountInfo(@PathVariable String userId) {
        WrbAccountResponse response = new WrbAccountResponse();
        Account account = wrbInfoService.getAccountInfo(userId);
        if (account != null) {
            response.setPf_user_id(String.valueOf(account.getUserId()));
            response.setAll_balance(account.getBankTotal());
            response.setAvailable_balance(account.getBankBalance());
            response.setFrozen_money(account.getBankFrost());
            response.setInvesting_principal(account.getBankAwaitCapital());
            response.setInvesting_interest(account.getBankAwaitInterest());
            response.setEarned_interest(account.getBankInterestSum());
        }
        response.setCurrent_money(BigDecimal.ZERO);
        return response;
    }

    /**
     * 投资记录查询
     * @param request
     * @return 投资记录
     * @throws Exception
     */
    @RequestMapping("/getInvestRecord")
    public WrbInvestRecordResponse getInvestRecord(@RequestBody WrbInvestRecordRequest request) {
        WrbInvestRecordResponse response = new WrbInvestRecordResponse();
        String recordId = request.getInvest_record_id();
        String userId = request.getPf_user_id();

        Map<String, Object> params = new HashMap<>();
        if (request.getStart_time() != null) {
            long startTime = GetDate.stringToDate(request.getStart_time()).getTime() / 1000;
            params.put("startTime", startTime);
        }
        if (request.getEnd_time() != null) {
            long endTime = GetDate.stringToDate(request.getEnd_time()).getTime() / 1000;
            params.put("endTime", endTime);
        }
        params.put("userId", userId);
        params.put("limitStart", request.getOffset());
        params.put("limitEnd",  request.getLimit());
        params.put("nid", recordId);
        params.put("status", request.getInvest_status());
        List<WrbInvestRecordCustomize> wrbInvestRecord = wrbInfoService.getWrbInvestRecord(params);
        List<WrbInvestRecordVO> wrbInvestRecordVOS = CommonUtils.convertBeanList(wrbInvestRecord, WrbInvestRecordVO.class);
        response.setInvest_records(wrbInvestRecordVOS);
        return response;
    }

    /**
     * 获取投资记录回款计划
     * @return
     */
    @RequestMapping("/getRecoverPlan/{userId}/{investRecordId}/{borrowNid}")
    public wrbInvestRecoverPlanResponse getRecoverPlan(@PathVariable String userId, @PathVariable String investRecordId, @PathVariable String borrowNid) {
        wrbInvestRecoverPlanResponse response = new wrbInvestRecoverPlanResponse();
        response.setBack_records(wrbInfoService.getRecoverPlan(userId, investRecordId, borrowNid));
        return response;
    }


    /**
     * 格式化优惠券信息
     * @param couponUser
     * @return
     */
    private StringBuffer convertCouponInfo(CouponUser couponUser) {
        StringBuffer sbf = new StringBuffer();
        // 优惠券编号
        String couponCode = couponUser.getCouponCode();
        CouponConfig couponConfig = wrbInfoService.getCouponByCouponCode(couponCode);
        if(couponConfig != null){
            Integer couponType = couponConfig.getCouponType();
            if (1 == couponType) {
                sbf.append("体验金-");
                sbf.append(couponConfig.getCouponQuota()).append("-元-");
            } else if (2 == couponType) {
                sbf.append("加息券-");
                sbf.append(couponConfig.getCouponQuota()).append("%-无-");
            } else {
                sbf.append("代金券-");
                sbf.append(couponConfig.getCouponQuota()).append("-元-");
            }

            // 获取优惠券开始时间
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date createTime = couponConfig.getCreateTime();
            long timestamp = new Timestamp(createTime.getTime()).getTime() / 1000;
            Integer add=Integer.valueOf(String.valueOf(timestamp));
            String addTime = "无";//开始和生效时间
            if(add != null){
                long addLong = add.longValue() * 1000;
                Date addTimedate = new Date(addLong);
                addTime = format.format(addTimedate);
            }

            // 获取优惠券结束时间
            Integer end = couponConfig.getExpirationDate();
            String endTime = "无";//结束时间
            if (end != null) {
                long endLong = end.longValue() * 1000;
                Date expirationDate = new Date(endLong);
                endTime = format.format(expirationDate);
            }
            sbf.append(addTime).append("-").append(addTime).append("-").append(endTime);//开始时间与生效时间一样
        }
        return sbf;
    }

    /**
     * 获取某天投资数据
     * @param request
     * @return
     */
    @RequestMapping("/borrow_tender")
    public BorrowTenderResponse getBorrowTenderList(@RequestBody WrbInvestRequest request) {
        BorrowTenderResponse response = new BorrowTenderResponse();
        // 获取某天投资数据
        List<BorrowTender> list = wrbInfoService.getBorrowTenderList(request);
        if (!CollectionUtils.isEmpty(list)) {
            List<BorrowTenderVO> voList = CommonUtils.convertBeanList(list, BorrowTenderVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 查询标的投资情况
     * @return
     */
    @RequestMapping("/wrb_borrow_tender")
    public WrbBorrowTenderCustomizeResponse getBorrowTenderByBorrowNid(@RequestBody WrbInvestRequest request) {
        WrbBorrowTenderCustomizeResponse response = new WrbBorrowTenderCustomizeResponse();
        // 查询标的投资情况
        List<WrbBorrowTenderCustomize> list = wrbInfoService.getBorrowTenderByBorrowNid(request);
        if (!CollectionUtils.isEmpty(list)) {
            List<WrbBorrowTenderCustomizeVO> voList = CommonUtils.convertBeanList(list, WrbBorrowTenderCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }
}
