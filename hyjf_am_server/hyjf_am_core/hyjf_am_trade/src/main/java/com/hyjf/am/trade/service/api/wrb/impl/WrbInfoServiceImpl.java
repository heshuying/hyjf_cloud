/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.api.wrb.impl;

import com.hyjf.am.response.trade.wrbInvestRecoverPlanResponse;
import com.hyjf.am.response.user.WrbInvestSumResponse;
import com.hyjf.am.resquest.api.WrbInvestRequest;
import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.mapper.customize.WrbQueryCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.WrbBorrowListCustomize;
import com.hyjf.am.trade.dao.model.customize.WrbBorrowTenderCustomize;
import com.hyjf.am.trade.dao.model.customize.WrbInvestRecordCustomize;
import com.hyjf.am.trade.dao.model.customize.WrbRecoverCustomize;
import com.hyjf.am.trade.dao.model.customize.WrbBorrowTenderSumCustomize;
import com.hyjf.am.trade.service.api.wrb.WrbInfoService;
import com.hyjf.am.vo.api.WrbDaySumCustomize;
import com.hyjf.common.util.GetDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author fq
 * @version WrbInfoServiceImpl, v0.1 2018/9/25 11:12
 */
@Service
public class WrbInfoServiceImpl implements WrbInfoService {
    @Resource
    private WrbQueryCustomizeMapper wrbQueryCustomizeMapper;
    @Resource
    private BorrowTenderMapper borrowTenderMapper;

    @Autowired
    private CouponUserMapper couponUserMapper;

    @Autowired
    private CouponConfigMapper couponConfigMapper;

    @Autowired
    private BorrowMapper borrowMapper;

    @Autowired
    private BorrowRecoverMapper borrowRecoverMapper;

    @Autowired
    private BorrowRecoverPlanMapper borrowRecoverPlanMapper;

    @Autowired
    private AccountMapper accountMapper;


    @Override
    public List<WrbBorrowListCustomize> borrowList(String borrowNid)  {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("borrowClass", "");

        // 定向标过滤
        params.put("publishInstCode", "");
        if (StringUtils.isNotBlank(borrowNid)) {
            params.put("borrowNid", borrowNid);
        }else{
            params.put("projectType", "HZT");
            params.put("status", 2);// 获取 投资中
        }
        List<WrbBorrowListCustomize> customizeList = wrbQueryCustomizeMapper.searchBorrowListByNid(params);
        for (WrbBorrowListCustomize wrbBorrowListCustomize : customizeList) {
            String url = wrbBorrowListCustomize.getInvest_url();
            wrbBorrowListCustomize.setInvest_url(url);
        }
        return customizeList;
    }

    @Override
    public WrbInvestSumResponse getDaySum(Date date) {
        // 查询开始时间
        Integer timeStart = new Long(date.getTime() / 1000).intValue();
        // 查询结束时间
        Integer timeEnd = new Long(date.getTime() / 1000 + 24 * 60 * 60).intValue();
        WrbDaySumCustomize wrbSumCustomize = wrbQueryCustomizeMapper.getDaySum(timeStart, timeEnd);
        WrbInvestSumResponse response = new WrbInvestSumResponse();
        BigDecimal bd=new BigDecimal(0);
        if(wrbSumCustomize.getAll_wait_back_money() == null){
            wrbSumCustomize.setAll_wait_back_money(bd);
        }
        if(wrbSumCustomize.getFc_all_wait_back_money() == null){
            wrbSumCustomize.setFc_all_wait_back_money(bd);
        }
        if(wrbSumCustomize.getInvest_all_money() == null){
            wrbSumCustomize.setInvest_all_money(bd);
        }

        BeanUtils.copyProperties(wrbSumCustomize, response);
        return response;

    }

    @Override
    public List<CouponUser> getCouponInfo(String userId) {
        CouponUserExample example = new CouponUserExample();
        CouponUserExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(Integer.valueOf(userId));
        List<CouponUser> couponUserList = couponUserMapper.selectByExample(example);
        return couponUserList;
    }

    @Override
    public Account getAccountInfo(String userId) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(Integer.valueOf(userId));
        List<Account> accountList = accountMapper.selectByExample(example);
        if (accountList != null) {
            return accountList.get(0);
        }
        return null;
    }

    /**
     * 根据优惠券编号获取优惠券配置信息
     * @param couponCode
     * @return
     */
    @Override
    public CouponConfig getCouponByCouponCode(String couponCode) {
        CouponConfigExample example = new CouponConfigExample();
        CouponConfigExample.Criteria criteria = example.createCriteria();
        criteria.andCouponCodeEqualTo(couponCode);
        List<CouponConfig> couponConfigList = couponConfigMapper.selectByExample(example);
        if (couponConfigList.size() != 0) {
            return couponConfigList.get(0);
        }
        return null;
    }

    @Override
    public List<WrbInvestRecordCustomize> getWrbInvestRecord(Map<String, Object> params) {
        List<WrbInvestRecordCustomize> investRecordCustomizeList = wrbQueryCustomizeMapper.getInvestRecord(params);
        for (WrbInvestRecordCustomize wrbRecoverCustomize : investRecordCustomizeList) {
            String nid = wrbRecoverCustomize.getInvest_record_id();
            List<WrbRecoverCustomize> recoverList = wrbQueryCustomizeMapper.getRecover(nid);
            List<WrbRecoverCustomize> recoverPlanList = wrbQueryCustomizeMapper.getRecoverPlan(nid);
            if (!CollectionUtils.isEmpty(recoverPlanList)) {
                BeanUtils.copyProperties(recoverPlanList.get(0), wrbRecoverCustomize);
                if (!CollectionUtils.isEmpty(recoverList)) {
                    WrbRecoverCustomize recoverCustomize = recoverList.get(0);
                    wrbRecoverCustomize.setBack_early_state(recoverCustomize.getBack_early_state());
                    wrbRecoverCustomize.setBack_early_time(recoverCustomize.getBack_early_time());
                }
            } else if (!CollectionUtils.isEmpty(recoverList)){
                BeanUtils.copyProperties(recoverList.get(0), wrbRecoverCustomize);
            }
        }
        return investRecordCustomizeList;
    }

    @Override
    public List<wrbInvestRecoverPlanResponse.WrbRecoverRecord> getRecoverPlan(String userId, String investRecordId, String borrowNid) {
        List<wrbInvestRecoverPlanResponse.WrbRecoverRecord> recoverRecordList = new ArrayList<>();

        BorrowExample example = new BorrowExample();
        BorrowExample.Criteria criteria = example.createCriteria();
        criteria.andBorrowNidEqualTo(borrowNid);
        List<Borrow> borrowList = borrowMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(borrowList)) {
            Borrow borrow = borrowList.get(0);
            String borrowStyle = borrow.getBorrowStyle();
            if ("endday".equals(borrowStyle) || "end".equals(borrowStyle)) {
                // 不分期
                BorrowRecoverExample recoverExample = new BorrowRecoverExample();
                BorrowRecoverExample.Criteria criteria1 = recoverExample.createCriteria();
                criteria1.andBorrowNidEqualTo(borrowNid);
                criteria1.andUserIdEqualTo(Integer.valueOf(userId));
                criteria1.andNidEqualTo(investRecordId);
                List<BorrowRecover> borrowRecoverList = borrowRecoverMapper.selectByExample(recoverExample);
                if(!CollectionUtils.isEmpty(borrowRecoverList)) {
                    wrbInvestRecoverPlanResponse.WrbRecoverRecord wrbRecoverRecord = new wrbInvestRecoverPlanResponse.WrbRecoverRecord();
                    BorrowRecover borrowRecover = borrowRecoverList.get(0);
                    // 回款日期
                    wrbRecoverRecord.setBack_date(GetDate.timestamptoStrYYYYMMDD(Integer.valueOf(borrowRecover.getRecoverTime())));
                    // 回款本金
                    wrbRecoverRecord.setBack_principal(borrowRecover.getRecoverCapital());
                    // 回款利息
                    wrbRecoverRecord.setBack_interest(borrowRecover.getRecoverInterest());
                    // 回款金额
                    wrbRecoverRecord.setBack_money(borrowRecover.getRecoverAccount());
                    //0:未回款 1:已回款
                    wrbRecoverRecord.setBack_status(borrowRecover.getStatus());
                    // 服务费
                    wrbRecoverRecord.setService_fee(BigDecimal.ZERO);
                    recoverRecordList.add(wrbRecoverRecord);
                }
            } else {
                // 分期
                BorrowRecoverPlanExample borrowRecoverPlanExample = new BorrowRecoverPlanExample();
                BorrowRecoverPlanExample.Criteria borrowRecoverPlanCriteria = borrowRecoverPlanExample.createCriteria();
                borrowRecoverPlanCriteria.andUserIdEqualTo(Integer.parseInt(userId));
                borrowRecoverPlanCriteria.andBorrowNidEqualTo(borrowNid);
                borrowRecoverPlanCriteria.andNidEqualTo(investRecordId);
                List<BorrowRecoverPlan> borrowRecoverPlanList =  borrowRecoverPlanMapper.selectByExample(borrowRecoverPlanExample);
                if (!CollectionUtils.isEmpty(borrowRecoverPlanList)) {
                    for (BorrowRecoverPlan borrowRecoverPlan : borrowRecoverPlanList) {
                        wrbInvestRecoverPlanResponse.WrbRecoverRecord wrbRecoverRecord = new wrbInvestRecoverPlanResponse.WrbRecoverRecord();
                        // 回款日期
                        wrbRecoverRecord.setBack_date(GetDate.timestamptoStrYYYYMMDD(Integer.valueOf(borrowRecoverPlan.getRecoverTime())));
                        // 回款本金
                        wrbRecoverRecord.setBack_principal(borrowRecoverPlan.getRecoverCapital());
                        // 回款利息
                        wrbRecoverRecord.setBack_interest(borrowRecoverPlan.getRecoverInterest());
                        // 回款金额
                        wrbRecoverRecord.setBack_money(borrowRecoverPlan.getRecoverAccount());
                        //0:未回款 1:已回款
                        wrbRecoverRecord.setBack_status(borrowRecoverPlan.getRecoverStatus());
                        // 服务费
                        wrbRecoverRecord.setService_fee(BigDecimal.ZERO);
                        recoverRecordList.add(wrbRecoverRecord);
                    }
                }
            }
        }
        return recoverRecordList;
    }

    @Override
    public List<BorrowTender> getBorrowTenderList(WrbInvestRequest request) {
        Date date = request.getDate();
        Integer limit = request.getLimit();
        Integer page = request.getPage();
        BorrowTenderExample example = new BorrowTenderExample();
        BorrowTenderExample.Criteria criteria = example.createCriteria();
        // 查询开始条数
        Integer limitStart = (page-1) * limit;
        // 查询结束条数
        Integer limitEnd = limit;
        criteria.andCreateTimeBetween(GetDate.getSomeDayStart(date), GetDate.getSomeDayEnd(date));
        example.setLimitStart(limitStart);
        example.setLimitEnd(limitEnd);
        example.setOrderByClause("create_time desc");
        List<BorrowTender> borrowTenderList = borrowTenderMapper.selectByExample(example);

        return borrowTenderList;
    }

    @Override
    public List<WrbBorrowTenderCustomize> getBorrowTenderByBorrowNid(WrbInvestRequest request) {
        return wrbQueryCustomizeMapper.selectWrbBorrowTender(request.getBorrowNid(), request.getDate());
    }

    @Override
    public WrbBorrowTenderSumCustomize getBorrowTenderByBorrowNidAndTime(WrbInvestRequest request) {
        return wrbQueryCustomizeMapper.getBorrowTenderByBorrowNidAndTime(request.getBorrowNid(), request.getDate());
    }


}
