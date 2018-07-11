package com.hyjf.am.trade.service.impl.admin.coupon;

import com.hyjf.am.resquest.admin.CouponTenderRequest;
import com.hyjf.am.trade.dao.mapper.customize.admin.coupon.CouponTenderCustomizeMapper;
import com.hyjf.am.trade.service.admin.coupon.CouponTenderService;
import com.hyjf.am.vo.admin.coupon.CouponBackMoneyCustomize;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVo;
import com.hyjf.am.vo.admin.coupon.CouponTenderCustomize;
import com.hyjf.am.vo.admin.coupon.CouponTenderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author walter.limeng
 * @version UtmController, v0.1 2018/7/02 11:17
 */
@Service
public class CouponTenderServiceImpl implements CouponTenderService {

    @Resource
    private CouponTenderCustomizeMapper couponTenderCustomizeMapper;
    @Override
    public Integer countRecord(CouponTenderRequest request) {
        return couponTenderCustomizeMapper.countRecord(request);
    }

    @Override
    public String queryInvestTotalHzt(CouponTenderRequest request) {
        return couponTenderCustomizeMapper.queryInvestTotalHzt(request);
    }

    @Override
    public List<CouponTenderCustomize> getRecordList(CouponTenderRequest request) {
        return couponTenderCustomizeMapper.getRecordList(request);
    }

    @Override
    public CouponTenderDetailVo getCouponTenderDetailCustomize(Map<String, Object> paramMap) {
        return couponTenderCustomizeMapper.getCouponTenderDetailCustomize(paramMap);
    }

    @Override
    public List<CouponRecoverVo> getCouponRecoverCustomize(Map<String, Object> paramMap) {
        return couponTenderCustomizeMapper.getCouponRecoverCustomize(paramMap);
    }

    @Override
    public Integer countHjhRecord(CouponTenderRequest request) {
        return couponTenderCustomizeMapper.countHjhRecord(request);
    }

    @Override
    public String queryInvestTotalHjh(CouponTenderRequest request) {
        return couponTenderCustomizeMapper.queryInvestTotalHjh(request);
    }

    @Override
    public List<CouponTenderCustomize> getRecordListHjh(CouponTenderRequest request) {
        return couponTenderCustomizeMapper.getRecordListHjh(request);
    }

    @Override
    public CouponTenderDetailVo getHjhCouponTenderDetailCustomize(Map<String, Object> paramMap) {
        return couponTenderCustomizeMapper.getHjhCouponTenderDetailCustomize(paramMap);
    }

    @Override
    public List<CouponRecoverVo> getHjhCouponRecoverCustomize(Map<String, Object> paramMap) {
        return couponTenderCustomizeMapper.getHjhCouponRecoverCustomize(paramMap);
    }

    @Override
    public Integer countRecordHztDJ(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        return couponTenderCustomizeMapper.countRecordHztDJ(couponBackMoneyCustomize);
    }

    @Override
    public List<CouponBackMoneyCustomize> getRecordListHztDJ(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        return couponTenderCustomizeMapper.getRecordListHztDJ(couponBackMoneyCustomize);
    }

    @Override
    public String queryHztInvestTotal(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        return couponTenderCustomizeMapper.queryHztInvestTotal(couponBackMoneyCustomize);
    }

    @Override
    public String queryHztRecoverInterestTotle(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        return couponTenderCustomizeMapper.queryHztRecoverInterestTotle(couponBackMoneyCustomize);
    }

    @Override
    public Integer countRecordHztTY(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        return couponTenderCustomizeMapper.countRecordHztTY(couponBackMoneyCustomize);
    }

    @Override
    public List<CouponBackMoneyCustomize> getRecordListHztTY(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        return couponTenderCustomizeMapper.getRecordListHztTY(couponBackMoneyCustomize);
    }

    @Override
    public Integer countRecordHztJX(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        return couponTenderCustomizeMapper.countRecordHztJX(couponBackMoneyCustomize);
    }

    @Override
    public List<CouponBackMoneyCustomize> getRecordListHztJX(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        return couponTenderCustomizeMapper.getRecordListHztJX(couponBackMoneyCustomize);
    }

    @Override
    public Integer countRecordHjhDJ(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        return couponTenderCustomizeMapper.countRecordHjhDJ(couponBackMoneyCustomize);
    }

    @Override
    public List<CouponBackMoneyCustomize> getRecordListHjhDJ(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        return couponTenderCustomizeMapper.getRecordListHjhDJ(couponBackMoneyCustomize);
    }

    @Override
    public String queryHjhInvestTotal(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        return couponTenderCustomizeMapper.queryHjhInvestTotal(couponBackMoneyCustomize);
    }

    @Override
    public String queryHjhRecoverInterestTotle(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        return couponTenderCustomizeMapper.queryHjhRecoverInterestTotle(couponBackMoneyCustomize);
    }

    @Override
    public Integer countRecordHjhTY(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        return couponTenderCustomizeMapper.countRecordHjhTY(couponBackMoneyCustomize);
    }

    @Override
    public List<CouponBackMoneyCustomize> getRecordListHjhTY(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        return couponTenderCustomizeMapper.getRecordListHjhTY(couponBackMoneyCustomize);
    }

    @Override
    public Integer countRecordHjhJX(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        return couponTenderCustomizeMapper.countRecordHjhJX(couponBackMoneyCustomize);
    }

    @Override
    public List<CouponBackMoneyCustomize> getRecordListHjhJX(CouponBackMoneyCustomize couponBackMoneyCustomize) {
        return couponTenderCustomizeMapper.getRecordListHjhJX(couponBackMoneyCustomize);
    }
}
